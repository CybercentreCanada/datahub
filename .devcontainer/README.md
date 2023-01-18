# Using this devcontainer
This devcontainer is meant to easily set up a Java 11 / Gradle environment that works on a FALCON laptop with WSL2 (v1.2+) installed. In order for it to "just work", it does make a few assumptions:
## Base image is in a private registry
The "parent" image for this devcontainer's Dockerfile is stored in an ACR that requires authentication. These steps should be executed in WSL2 before attempting to launch the devcontainer:
```bash
az login
az acr login --name uchimera --subscription <subscription_name>
```
## SSH_AUTH_SOCK
The WSL2 v1.2+ installer maps the `gpg-agent` SSH Agent to `$HOME/.ssh/agent.sock`. That file is assumed to exist and be a valid SSH agent socket.
## Scratch space
This devcontainer will create a folder named `scratch/` in your `$HOME` directory, if one doesn't already exist. This folder can be used to store temporary / transient data and to share files with other devcontainers that also map the `scratch/` folder.
## Caches
This devcontainer is configured to persist its Gradle, Yarn, nvm, etc. caches on the host (WSL2). That way, re-building the container won't cause overly long build times.
## Debugger Setup
**NOTE**: The venv which you create from following the debugger setup guide can also be used to run datahub ingestion jobs locally. I sourced the commands from two separate guides, [DataHub Developer's Guide](https://github.com/datahub-project/datahub/blob/master/docs/developers.md#deploying-local-versions) and [Developing on Metadata Ingestion](https://github.com/datahub-project/datahub/blob/master/metadata-ingestion/developing.md#testing)

In order to make use of the debugger built into the devcontainer, there are a few steps you need to follow:
1) Once the devcontainer has been built, traverse to the `/datahub/smoke-test` directory. Then, enter the following commands:
```shell
python3 -m venv venv
source venv/bin/activate
pip install --upgrade pip wheel setuptools
pip install -r requirements.txt

cd ../metadata-ingestion
pip install -e '.[dev]'
```
2) Create a file called `.env` in `/workspace/scratch/datahub/` and add any environment variables you need for the recipe to it. Write them in key value pairs (e.g., `GMS_TOKEN=XXXXXX`).
3) Once the commands listed above have been entered, you can open a recipe that uses a source you would like to debug and then press the `Start Debugging` button beside the `Datahub Ingest` debug configuration (don't forget to put a breakpoint in the ingestion source you want to debug). 
