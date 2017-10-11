include env.mk

ANSIBLE_CONFIG ?= ansible/ansible.cfg
ENV ?= dev
KEY_NAME ?=
PWD = $(shell pwd)
REGION ?= eu-west-1
VENV ?= $(PWD)/ansible/.venv
VERSION ?= $(shell git describe --tags)

PATH := $(VENV)/bin:$(shell printenv PATH)
SHELL := env PATH=$(PATH) /bin/bash

export ANSIBLE_CONFIG
export AWS_DEFAULT_REGION=$(REGION)
export AWS_REGION=$(REGION)
export AWS_KEY_NAME=$(KEY_NAME)
export PATH

.PHONY: .phony

## Prints this help
help:
	@awk -v skip=1 \
		'/^##/ { sub(/^[#[:blank:]]*/, "", $$0); doc_h=$$0; doc=""; skip=0; next } \
		 skip  { next } \
		 /^#/  { doc=doc "\n" substr($$0, 2); next } \
		 /:/   { sub(/:.*/, "", $$0); printf "\033[34m%-30s\033[0m\033[1m%s\033[0m %s\n\n", $$0, doc_h, doc; skip=1 }' \
		$(MAKEFILE_LIST)

## Initialise local project
init: .git/.local-hooks-installed $(VENV)

## Sudo for AWS Roles
# Usage:
#   $(make aws-sudo PROFILE=profile-name)
#   $(make aws-sudo PROFILE=profile-with-mfa TOKEN=123789)
aws-sudo: $(VENV)
	@(printenv TOKEN > /dev/null && aws-sudo -m $(TOKEN) $(PROFILE) ) || ( \
		aws-sudo $(PROFILE) \
	)

## Lint all the code
lint: lint.ansible

## Lint all Ansible code
lint.ansible: $(VENV)
	@echo "Ansible Playbooks Lint..."
	@find ansible/playbooks -name "*.yml" -print0 | \
		xargs -n1 -0 -I% \
		ansible-lint %
	@echo "Ansible Roles Lint..."
	@find ansible/roles/service -name "*.yml" -not -path "*/files/*.yml" -print0 | \
		xargs -n1 -0 -I% \
		ansible-lint % \
			--exclude=ansible/roles/vendor

## Manage CloudFormation stack
# Usage: make stack ROLE=hippocms
stack: $(VENV) ansible/vars/$(ENV)/secrets.yml ansible/roles/vendor
	@printenv ROLE || ( \
		echo "please specify ROLE, example: make stack ROLE=hippocms" \
		&& exit 1 \
	)
	cd ansible && ansible-playbook \
		--inventory inventories/localhost \
		--extra-vars pwd=$(PWD) \
		--extra-vars role=$(ROLE) \
		--extra-vars @$(PWD)/ansible/vars/$(ENV)/environment.yml \
		--extra-vars @$(PWD)/ansible/vars/$(ENV)/secrets.yml \
		$(EXTRAS) \
		playbooks/stack.yml

## Create new version tag based on the nearest tag
version.bumpup:
	@git tag $$((git describe --abbrev=0 --tags | grep $$(cat .version) || echo $$(cat .version).-1) | perl -pe 's/^(v(\d+\.)*)(-?\d+)(.*)$$/$$1.($$3+1).$$4/e')
	$(MAKE) version.print

## Prints current version
version.print:
	@echo "- - -"
	@echo "Current version: $(VERSION)"
	@echo "- - -"

ansible/roles/vendor: $(VENV) .phony
	ansible-galaxy install -p ansible/roles/vendor -r ansible/roles/vendor.yml --ignore-errors

## Delete all downloaded or generated files
clean:
	rm -rf $(VENV)

# generates empty env.mk file if not present
env.mk:
	touch env.mk

# install hooks and local git config
.git/.local-hooks-installed:
	@bash .git-local/install

# install dependencies in virtualenv
$(VENV):
	@which virtualenv > /dev/null || (\
		echo "please install virtualenv: http://docs.python-guide.org/en/latest/dev/virtualenvs/" \
		&& exit 1 \
	)
	virtualenv $(VENV)
	$(VENV)/bin/pip install -U "pip<9.0"
	$(VENV)/bin/pip install pyopenssl urllib3[secure] requests[security]
	$(VENV)/bin/pip install -r ansible/requirements.txt --ignore-installed
	virtualenv --relocatable $(VENV)
