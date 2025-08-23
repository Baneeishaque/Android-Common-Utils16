# 🚀 Android Common Utils for API 16 (Jelly Bean)

[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-ready--to--code-blue?logo=gitpod)](https://gitpod.io/#https://github.com/Baneeishaque/Android-Common-Utils16)
[![Build Status](https://dev.azure.com/banee-ishaque-k-github-works/Android-Common-Utils16/_apis/build/status%2FBaneeishaque.Android-Common-Utils16%20Android%20Library?branchName=master)](https://dev.azure.com/banee-ishaque-k-github-works/Android-Common-Utils16/_build/latest?definitionId=43&branchName=master)
[![CI Scripts Doc](https://img.shields.io/badge/CI%20Scripts-Docs-blue?logo=powershell&logoColor=white)](./ci/README.md)

<!-- [![CI Scripts Doc](https://img.shields.io/badge/CI%20Scripts-Docs-green?logo=powershell&logoColor=white)](./ci/README.md) -->
<!-- [![CI Scripts Doc](https://img.shields.io/badge/CI%20Scripts-Docs-yellow?logo=powershell&logoColor=black)](./ci/README.md) -->
<!-- [![CI Scripts Doc](https://img.shields.io/badge/CI%20Scripts-Docs-8A2BE2?logo=powershell&logoColor=white)](./ci/README.md) -->

**Build once. Run anywhere. Audit always.**

**Reproducible builds. Explicit automation. Friendly onboarding.**

---

## 🛠 Overview

This repository delivers:

- **Cross‑platform** CI/CD pipelines with guardrails and audits
- **One‑command setup** for runtimes and tooling via mise
- **Clear documentation** and onboarding diagrams for contributors

## 💬 Why This Project

We obsess over:

- **Zero guesswork**: every environment is explicit and reproducible
- **Fast onboarding**: scripts and diagrams turn newcomers into contributors in minutes
- **Bulletproof automation**: pinned versions, guard checks, and live audits baked in

## 📌 About

This project is built for **clarity, reproducibility, and resilience**:

- Modular, **cross‑platform** CI/CD pipelines.
- Explicit guards, **live audit signals**, and safe automation.
- Contributor‑friendly onboarding with well‑documented scripts.

## Quick Start

This project uses a modular, cross‑platform CI setup powered by PowerShell 5.1+/7+ and mise. All pipeline setup, build, and teardown logic lives in `ci/` with clear docs, diagrams, and troubleshooting tips.

To get started:

1. **Read `ci/README.md`** — it explains:
    - What each CI script does (`common.ps1`, `setup-mise-java.ps1`, `gradle-stop.ps1`)
    - How they link together in Azure Pipelines
    - Visual flow & sequence diagrams
    - Self‑test commands you can run locally
2. **Prerequisites**: Make sure you have PowerShell installed (Core or Windows), plus Homebrew (macOS/Linux) or Scoop (Windows) if you’re testing locally.
3. **Run a full setup with audits**:

    ```powershell
    pwsh ./ci/setup-mise-java.ps1
    ```

4. **Build with**:

    ```powershell
    ./gradlew build
    ```

5. **Stop the Gradle daemon**:

    ```powershell
    pwsh ./ci/gradle-stop.ps1
    ```

💡 These scripts are self‑auditing — check the log for PATH changes, Java pins, and Gradle’s detected JDK.
