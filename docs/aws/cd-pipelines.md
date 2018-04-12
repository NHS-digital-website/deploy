# Build and Maintain Continuous Deployment Pipelines

In order to (re)build Hippo Continuous Delivery pipelines simply run the following
commands

* Authenticate with AWS using your MFA token
  ```
  $(make aws-sudo PROFILE=nhsd-profile-name TOKEN=123456)
  ```
* Create (or update) `aws_config` and `aws_cd` Cloud Formation stacks
  ```
  make stack ROLE=aws_config ENV=mgt
  make stack ROLE=aws_cd ENV=mgt
  ```

This will create (or update) the following CF stacks

* part of `aws_cd` role
  * mgt-aws-cd-code-pipeline-hippo
  * mgt-aws-cd-container-registry
  * mgt-artefacts
* part of `aws_config` role
  * mgt-config




## Manual Steps

One of the manual setup is setting secure parameters in:
https://eu-west-1.console.aws.amazon.com/systems-manager/parameters?region=eu-west-1

* GITHUB_TOKEN
* HIPPO_MAVEN_PASSWORD
* HIPPO_MAVEN_USERNAME
