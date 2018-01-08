# CI and CD Overview

Publication System is powered by 3 repositories: `ps-hippo`, `ps-build` and
`ps-deploy`. Each repository is using [Travis] for testing pull requests.
Once approved it can be merged to master branch which triggers specific
[AWS CodePipeline] pipeline.




## Continuous Integration

Each Pull Request created in `ps-hippo`, `ps-build` or `ps-deploy` repo triggers
[Travis] build. This means that there is no need to build or maintain any
infrastructure for CI.

![Continuous Integration Overview][ci-overview]




## Continuous Delivery

Each commit to `master` branch in `ps-hippo` repository triggers CD pipeline. Both
[AWS CodePipeline] pipelines and [AWS CodeBuild] jobs are build and managed from
code.

![Continuous Delivery Overview][pipeline-overview]




[ci-overview]: ./dot/ci-overview.dot.svg
[pipeline-overview]: ./dot/cd-pipeline-overview.dot.svg
[Travis]: https://travis-ci.org/NHS-digital-website/
[AWS CodePipeline]: https://aws.amazon.com/codepipeline
[AWS CodeBuild]: https://aws.amazon.com/codebuild
