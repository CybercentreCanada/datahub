FROM ubuntu:24.04

# prepare tools:
RUN apt-get update
RUN apt-get install wget -y

# [Option] Install zsh
ARG INSTALL_ZSH="false"
# [Option] Upgrade OS packages to their latest versions
ARG UPGRADE_PACKAGES="true"
# [Option] Enable non-root Docker access in container
ARG ENABLE_NONROOT_DOCKER="true"

# Install needed packages and setup non-root user.
ARG USERNAME=vscode
ARG USER_UID=1000
ARG USER_GID=$USER_UID

ENV JAVA_HOME /usr/lib/jvm/java-17-openjdk-amd64/
RUN export JAVA_HOME

# Add custom CA certs
ARG extraCaCertsDir
ADD ${extraCaCertsDir} /usr/local/share/ca-certificates/

# Setting the ENTRYPOINT to docker-init.sh will configure non-root access to
# the Docker socket if "overrideCommand": false is set in devcontainer.json.
# The script will also execute CMD if you need to alter startup behaviors.
# ENTRYPOINT [ "/usr/local/share/docker-init.sh" ]
CMD [ "sleep", "infinity" ]

# Install additional OS packages.
RUN curl -fsSL https://deb.nodesource.com/setup_16.x | bash - \
    && apt-get update \
    && apt-get -y install --no-install-recommends \
    git \
    openjdk-17-jdk \
    bash-completion \
    libsasl2-dev \
    libldap2-dev \
    libssl-dev \
    nodejs \
    python3-dev \
    python3-full \
    python3-venv \
    python3-pip \
    vim \
    && apt-get autoremove -y \
    && apt-get clean -y \
    && rm -rf /var/lib/apt/lists/* \
    && rm -rf /var/cache/apt

RUN python3 -m pip --disable-pip-version-check --no-cache-dir install --upgrade \
    pip \
    wheel \
    setuptools \
    certifi \
    && rm -rf /tmp/pip-tmp
