# Accessing AWS Servers

It's easy as `make jump ROLE=hippo_authoring`



## The Easy Way

The simplest way to SSH to given box is to run:

```
make jump ROLE=hippo_authoring ENV=dev USERNAME=wojtek.oledzki
```

You can also edit `evn.mk` file and set default `USERNAME` and `ENV`

```
USERNAME ?= wojtek.oledzki
ENV ?= dev
```

now, it's simple as `make jump ROLE=hippo_authoring`.




## The Hard Way

If you want/have to SSH to the box manually, here's what you need to do...

1.  Find **private IP** address of the box you want to SSH into. To do so you can
    * login to AWS web console and find your box in "EC2" service in https://eu-west-1.console.aws.amazon.com/ec2/v2/home?region=eu-west-1#Instances:sort=instanceState
    * use AWS CLI
      ```
      aws --profile ps-hippo ec2 describe-instances \
        --region eu-west-1 \
        --filters "Name=tag:Environment,Values=dev" "Name=tag:Role,Values=hippo-delivery" \
        --output text \
        --query 'Reservations[*].Instances[*].PrivateIpAddress')
      ```
1.  Ensure you have ssh agent running, and it has your private key.
    ```
    ssh-add -l
    ```
1.  Ssh to bastion - make sure you enabled `ForwardAgent yes`.
    ```
    ssh -A first.last@bastdion.dev.ps.nhsd.io
    ```
1.  Ssh to the box of your choice using private IP

![SSH Access][ssh-access]




[ssh-access]: ../dot/ssh-access.dot.svg
