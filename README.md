# NHS Digital Publication System

[![Build Status](https://travis-ci.org/NHS-digital-website/deploy.svg?branch=master)](https://travis-ci.org/NHS-digital-website/deploy)

* [Introduction](#introduction)
* [Usage](#usage)
* [Installation](#installation)
* [Contributing](#contributing)
* [Overview](#overview)




## Introduction

This project allows you to build infrastructure needed for building, testing and
deploying NHS Digital Website.

Currently we are supporting two platforms

* **BloomReach onDemand**
* **AWS**




## Usage

Run `make help` to get a list of available commands.

In most cases you will run something like this:

```
# authenticate your terminal for 1h to access `nhsd-profile-name` AWS profile using
# your MFA token.
$(make aws-sudo PROFILE=nhsd-profile-name TOKEN=...)

# run commands...
make stack ROLE=aws_foundation ENV=mgt
make stack ...
```




## Documentation

* [Build and Maintain Continuous Deployment Pipelines]




## Installation


### Requirements

In order to use this project you need:

* [python virtualenv](http://docs.python-guide.org/en/latest/dev/virtualenvs/).
* Access to NHSD AWS account.


### Installation

Everything you need will be installed locally. Simply run:

```
make init
```


### Configuration

Configuration is kept in `env.mk` file. You can set/override any variable defined
in `Makefile`

```
# your user name for SSH
USERNAME ?= firstname.lastname
# AWS (ssh) ec2 key pair name
KEY_NAME ?= firstname.lastname
```





## Contributing

* [Contributors' Workflow](/docs/contributors-workflow.md)
* [Before `push` Checklist](/docs/before-push-checklist.md)




# Overview

* [CI and CD Overview](docs/ci-cd-overview.md)




[Build and Maintain Continuous Deployment Pipelines]: docs/aws/cd-pipelines.md
