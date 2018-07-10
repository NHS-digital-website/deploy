# Lets Encrypt

All non-production environments are using free SSL certificates provided by
Lets Encrypt.

To install `certbot` command line tool visit https://certbot.eff.org/




## New Certificate

First open terminal and go to this repository root folder

```
make init
$(make aws-sudo PROFILE=nhsd TOKEN=...)
mkdir -p certbot/conf/accounts
ansible/.venv/bin/aws s3 \
  sync \
  s3://config.mgt.nhsd.io/certbot/conf/accounts \
  certbot/conf/accounts
```

A certificate is created which covers multiple Subject Alternative Name (SAN)

```
certbot certonly \
  --config-dir ./certbot/conf \
  --work-dir ./certbot/work \
  --logs-dir ./certbot/log \
  --manual \
  --preferred-challenges dns \
  --manual-public-ip-logging-ok \
  -d cms-uat.nhsd.io \
  -d cms-tst.nhsd.io \
  -d cms-dev.nhsd.io \
  -d cms-training.nhsd.io \
  -d uat.nhsd.io \
  -d tst.nhsd.io \
  -d dev.nhsd.io \
  -d training.nhsd.io
```


For each SAN, you will be asked to set a new DNS txt entry in order for certbot to 
confirm domain ownership.  
This can done in Route53 in AWS Console

once done tgz all folder

```
tar -zcf cms-uat.nhsd.io.tgz certbot/conf/archive/cms-uat.nhsd.io
```

and encrypt for BloomReach infra team:

```
certbot/conf/archive/
gpg -r <recipient> -e cms-uat.nhsd.io.tgz
```




## Upload To AWS

If you need to upload certificate to AWS

```
aws iam upload-server-certificate \
  --path /cloudfront/tst/
  --server-certificate-name files.tst.nhsd.io-20180321 \
  --certificate-body file:///etc/letsencrypt/live/files.tst.nhsd.io/fullchain.pem \
  --private-key file:///etc/letsencrypt/live/files.tst.nhsd.io/privkey.pem \
```
