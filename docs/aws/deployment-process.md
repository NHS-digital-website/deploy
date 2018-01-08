# Deployment Process

Each environment is composed from multiple layers of Cloud Formation stacks. Each
application stack should be independent from each other. The existing dependencies
(CF exports) are listed in this doc, and should be kept to minimum.

```
 +-----------------+ +----------------+ +---------+ +-- - -  -
 | hippo authoring | | hippo delivery | | bastion | | ... ?
 +-----------------+ +----------------+ +---------+ +-- - -  -

 +--------+ +-----+ +-----+
 | config | | nat | | dns |
 +--------+-+-----+-+------- - -  -   -
 |             vpc
 +-------------------------- - -  -   -
```

To (re)deploy each of these stacks you need

* Appropriate AWS permissions.
* Build artefact (for application stacks).




## Make Stack

(re)Deploying stack is as simple as running `make stack ROLE=bastion`.




### Artefacts

Some of the stacks require "build artefacts" (yaml files). It will specify
application AMI Id to be deployed and potentially any other release specific config.

Current stacks (roles) that require build artefact are:

* bastion




## Cloud Formation Exports

This section lists CF stacks that exports resources that are used by other stacks.

* vpc
* dns
