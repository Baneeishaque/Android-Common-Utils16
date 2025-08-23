<#
.SYNOPSIS
  Sets pipeline variables for platform-specific mise cache/data directories
  and ensures they exist to prevent post-job cache save errors.
#>
. (Join-Path $PSScriptRoot 'common.ps1')

$os = Get-OS
$misePaths = Get-MisePaths -os $os

Write-Host "Detected OS: $os"
Write-Host "Setting pipeline variable 'MiseDataPath' to: $($misePaths.Data)"
Write-Host "Setting pipeline variable 'MiseCachePath' to: $($misePaths.Cache)"

# Set variables for subsequent tasks in the job
Write-Host "##vso[task.setvariable variable=MiseDataPath]$($misePaths.Data)"
Write-Host "##vso[task.setvariable variable=MiseCachePath]$($misePaths.Cache)"

# Ensure directories exist to prevent tar errors when saving cache on a cache miss
if (-not (Test-Path $misePaths.Data)) { New-Item -ItemType Directory -Force -Path $misePaths.Data | Out-Null }
if (-not (Test-Path $misePaths.Cache)) { New-Item -ItemType Directory -Force -Path $misePaths.Cache | Out-Null }
