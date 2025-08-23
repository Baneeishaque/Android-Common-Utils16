. (Join-Path $PSScriptRoot 'common.ps1')

Write-Host "Running Gradle build via 'gradlew build'..."
Gradle-Wrapper 'build'
