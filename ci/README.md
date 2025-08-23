# CI Scripts

This folder contains reusable PowerShell scripts and helpers for our Azure Pipelines builds.
They are **cross‑platform** (Windows / Linux / macOS) and compatible with **PowerShell 5.1+ and 7+**.

---

## Files

### `common.ps1`
Central helper library dot‑sourced by all other scripts.

**Provides:**
- `Get-OS` – Detects current OS.
- `Add-Path` – Prepends a directory to `PATH` for the current pipeline/job.
- `Ensure-BrewOnPath` – Ensures Homebrew paths are in `PATH` for macOS/Linux.
- `Invoke-WebContent` – Safe web content fetcher (works in PS5 and PS7).
- `Ensure-PackageManagers` – Installs or ensures Scoop (Windows) / Brew (POSIX).
- `Ensure-Mise` – Installs mise via Scoop or Brew and adds shims to `PATH`.
- `Gradle-Wrapper` – Runs `gradlew` or `gradlew.bat` if present.

---

### `setup-mise-java.ps1`
Sets up **Java** via [mise](https://mise.jdx.dev/) with guardrails and audit signals.

**What it does:**
1. Dot‑sources `common.ps1` helpers.
2. Sets `MISE_DATA_DIR` and `MISE_CACHE_DIR` for the unified cache step in the pipeline.
3. Ensures package managers and installs mise.
4. Reads the Java pin from `mise.toml`.
5. Installs Java and validates:
   - Pin drift (current version matches `mise.toml`).
   - Active `java` on PATH matches mise’s.
   - `JAVA_HOME` and JVM’s `java.home` agree.
   - Version contains the pinned major.
6. **Audit output**:
   - Lists all `java` binaries found on `PATH`.
   - Shows the JDK Gradle will use (`gradlew -version`).

**Run locally:**
```powershell
pwsh ./ci/setup-mise-java.ps1
```

---

### `gradle-stop.ps1`
Stops the Gradle daemon after builds.

**What it does:**
- Dot‑sources `common.ps1` for `Gradle-Wrapper`.
- Calls `gradlew --stop` if wrapper exists.

**Run locally:**
```powershell
pwsh ./ci/gradle-stop.ps1
```

---

## Pipeline Wiring
Typical Azure Pipelines YAML usage:

```yaml
- task: Cache@2
  displayName: mise cache
  inputs:
    key: mise | $(Agent.OS) | $(Agent.OSArchitecture) | $(Build.SourcesDirectory)/mise.toml
    path: |
      $(MISE_DATA_DIR)
      $(MISE_CACHE_DIR)

- task: PowerShell@2
  displayName: Setup runtimes via mise and verify
  inputs:
    filePath: $(Build.SourcesDirectory)/ci/setup-mise-java.ps1
    pwsh: true

- task: Gradle@3
  displayName: Gradle build
  env:
    JAVA_HOME: $(JAVA_HOME)

- task: PowerShell@2
  displayName: Stop Gradle daemon
  inputs:
    filePath: $(Build.SourcesDirectory)/ci/gradle-stop.ps1
    pwsh: true
```

---

## Notes for Contributors
- No inline script duplication — all helpers live in `common.ps1`.
- Keep `mise.toml` in repo root as the single source of truth for Java version.
- Scripts are self‑auditing — read the log output for PATH changes, Java binaries, and Gradle’s JDK.
- You can run these scripts locally before pushing changes to test the exact pipeline logic.
