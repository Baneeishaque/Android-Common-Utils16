. (Join-Path $PSScriptRoot 'common.ps1')

Write-Host "Stopping Gradle daemon..."
Gradle-Wrapper '--stop'
