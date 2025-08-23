<#
.SYNOPSIS
  Cross‑OS Java setup via mise, with drift guards and unified caching.
  Relies on helper functions from ci/common.ps1.
#>

# Import shared helpers
. (Join-Path $PSScriptRoot 'common.ps1')

function Get-MisePinVersion {
    $tomlPath = Join-Path $env:BUILD_SOURCESDIRECTORY "mise.toml"
    if (-not (Test-Path $tomlPath)) { throw "mise.toml not found at '$tomlPath'" }

    try {
        $tomlContent = Get-Content -Raw -Path $tomlPath | ConvertFrom-Toml
        $javaVersion = $tomlContent.tools.java
    }
    catch {
        throw "Failed to parse mise.toml. Error: $_"
    }

    if ([string]::IsNullOrWhiteSpace($javaVersion)) {
        throw "Could not find 'tools.java' in mise.toml or it is empty."
    }
    return $javaVersion
}

function Install-Java-And-Guard([string]$pin) {
    mise install

    # Pin drift check
    $current = (mise ls --current java) -join "`n"
    Write-Host "mise ls --current java:`n$current"
    if ($current -notmatch [regex]::Escape($pin)) {
        throw "Pin drift: expected '$pin' but got:`n$current"
    }

    $jHome = (mise where java).Trim()
    if (-not $jHome) { throw "Failed to resolve JAVA_HOME via 'mise where java'" }
    Write-Host "##vso[task.setvariable variable=JAVA_HOME]$jHome"
    Add-Path (Join-Path $jHome 'bin')

    # Guard 1: PATH java matches mise java
    $activeJava = (Get-Command java -ErrorAction Stop).Source
    $miseJava   = (mise which java).Trim()
    if ($activeJava -ne $miseJava) {
        throw "Mismatch: PATH java != mise java"
    }

    # Guard 2: JVM java.home matches JAVA_HOME
    $props = & java -XshowSettings:properties -version 2>&1
    $jvmHome = ($props | Select-String -Pattern '^\s*java\.home = (.*)$').Matches.Groups[1].Value.Trim()
    if ($jvmHome -ne $env:JAVA_HOME) {
        throw "Mismatch: JVM java.home != JAVA_HOME"
    }

    # Guard 3: Major version match
    # TODO: Exact version match
    $pinMajor = ([regex]::Match($pin, '\d+(\.\d+)?').Value)
    $ver = & java -version 2>&1
    if ($pinMajor -and ($ver -notmatch [regex]::Escape($pinMajor))) {
        throw "Java version does not contain expected major '$pinMajor'"
    }
}

# ---- MAIN EXECUTION ----
$os = Get-OS

# Bootstrap managers and mise
Ensure-PackageManagers -os $os
Ensure-Mise -os $os
Ensure-PSModule 'powershell-toml'

# Install and verify Java
$pin = Get-MisePinVersion
Write-Host "Detected java pin => '$pin'"
Install-Java-And-Guard -pin $pin

# ---- EXTRA AUDIT ----
Write-Host "`n=== Audit: All java binaries on PATH ==="
Get-Command -All java | ForEach-Object {
    "{0} => {1}" -f $_.Name, $_.Source
}

Write-Host "`n=== Audit: Gradle sees ==="
Gradle-Wrapper '-version'
