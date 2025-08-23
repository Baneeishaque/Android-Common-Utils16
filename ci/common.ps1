<#
.SYNOPSIS
  Common helper functions for CI/CD PowerShell scripts (PS 5.1+ / 7+).

.DESCRIPTION
  Centralizes:
    - OS detection
    - Safe path prepending
    - Homebrew path injection
    - Web content fetcher (PS5-safe)
    - Package manager bootstrap (brew/scoop)
    - mise install helper
    - Gradle wrapper runner (cross‑OS)
#>

function Get-OS {
    $plat = [System.Runtime.InteropServices.RuntimeInformation]::OSDescription
    if ($plat -match 'Windows') { return 'Windows' }
    if ($plat -match 'Darwin|Mac OS') { return 'Mac' }
    return 'Linux'
}

function Add-Path([string]$p) {
    if (-not [string]::IsNullOrWhiteSpace($p) -and (Test-Path $p)) {
        Write-Host "##vso[task.prependpath]$p"
        $env:PATH = "$p$([System.IO.Path]::PathSeparator)$($env:PATH)"
    }
}

function Ensure-BrewOnPath([string]$os) {
    if ($os -eq 'Linux') {
        Add-Path '/home/linuxbrew/.linuxbrew/bin'
        Add-Path '/home/linuxbrew/.linuxbrew/sbin'
    } elseif ($os -eq 'Mac') {
        Add-Path '/opt/homebrew/bin'
        Add-Path '/usr/local/bin'
    }
}

function Invoke-WebContent([string]$uri) {
    if ($PSVersionTable.PSVersion.Major -ge 6) {
        return (Invoke-WebRequest -Uri $uri).Content
    } else {
        return (Invoke-WebRequest -UseBasicParsing -Uri $uri).Content
    }
}

function Ensure-PackageManagers([string]$os) {
    if ($os -eq 'Windows') {
        if (-not (Get-Command scoop -ErrorAction SilentlyContinue)) {
            Invoke-Expression "& {$(Invoke-WebContent 'https://get.scoop.sh')} -RunAsAdmin"
        }
        scoop bucket add extras | Out-Null
    } else {
        Ensure-BrewOnPath -os $os
        if (-not (Get-Command brew -ErrorAction SilentlyContinue)) {
            throw "Homebrew not found on $os. Preinstall or add to PATH."
        }
    }
}

function Ensure-Mise([string]$os) {
    if ($os -eq 'Windows') { scoop install main/mise extras/vcredist2022 }
    else { brew update; brew install mise }
    Add-Path "$HOME/.local/bin"
    Add-Path "$HOME/.local/share/mise/shims"
    (& mise --version) | Write-Host
}

function Ensure-PSModule([string]$moduleName) {
    if (-not (Get-Module -ListAvailable -Name $moduleName)) {
        Write-Host "Installing PowerShell module '$moduleName'..."
        Install-Module -Name $moduleName -Scope CurrentUser -Repository PSGallery -Force -Confirm:$false
    }
}

function Get-MisePaths([string]$os) {
    $userHomePath = $env:HOME
    if ([string]::IsNullOrWhiteSpace($userHomePath)) {
        # Fallback for non-standard environments, e.g. Windows PowerShell 5.1
        $userHomePath = $env:USERPROFILE
    }

    $paths = @{}
    if ($os -eq 'Windows') {
        $localAppData = $env:LOCALAPPDATA
        if ([string]::IsNullOrWhiteSpace($localAppData)) { throw "LOCALAPPDATA env var not found on Windows." }
        $paths.Data = Join-Path $localAppData 'mise\data'
        $paths.Cache = Join-Path $localAppData 'mise\cache'
    } elseif ($os -eq 'Mac') {
        $paths.Data = Join-Path $userHomePath 'Library/Application Support/mise'
        $paths.Cache = Join-Path $userHomePath 'Library/Caches/mise'
    } else { # Linux and other POSIX
        $paths.Data = Join-Path $userHomePath '.local/share/mise'
        $paths.Cache = Join-Path $userHomePath '.cache/mise'
    }
    return $paths
}

function Gradle-Wrapper([string]$executableArgs) {
    $os = Get-OS

    if ($os -eq 'Windows') {
        $wrapperPath = '.\gradlew.bat'
        if (Test-Path $wrapperPath) {
            & $wrapperPath $executableArgs
        } else {
            Write-Host "Gradle wrapper ($wrapperPath) not found in current directory. Please run from repo root. Skipping."
        }
    } else { # Linux or Mac
        $wrapperPath = './gradlew'
        if (Test-Path $wrapperPath) {
            & $wrapperPath $executableArgs
        } else {
            Write-Host "Gradle wrapper ($wrapperPath) not found in current directory. Please run from repo root. Skipping."
        }
    }
}
