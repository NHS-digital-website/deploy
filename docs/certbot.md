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

```
certbot certonly \
  --config-dir ./certbot/conf \
  --work-dir ./certbot/work \
  --logs-dir ./certbot/log \
  --manual \
  --preferred-challenges dns \
  --manual-public-ip-logging-ok \
  -d cms-uat.nhsd.io
```

```
certbot certonly \
  --config-dir ./certbot/conf \
  --work-dir ./certbot/work \
  --logs-dir ./certbot/log \
  --manual \
  --preferred-challenges dns \
  --manual-public-ip-logging-ok \
  -d cms-tst.nhsd.io
```

```
certbot certonly \
  --config-dir ./certbot/conf \
  --work-dir ./certbot/work \
  --logs-dir ./certbot/log \
  --manual \
  --preferred-challenges dns \
  --manual-public-ip-logging-ok \
  -d uat.nhsd.io
```

```
certbot certonly \
  --config-dir ./certbot/conf \
  --work-dir ./certbot/work \
  --logs-dir ./certbot/log \
  --manual \
  --preferred-challenges dns \
  --manual-public-ip-logging-ok \
  -d tst.nhsd.io
```

You will be asked to set new DNS entry, you can do it via R53 in AWS

once done tgz all folder

```
tar -zcf tst.nhsd.io.tgz certbot/conf/archive/tst.nhsd.io
tar -zcf uat.nhsd.io.tgz certbot/conf/archive/uat.nhsd.io
tar -zcf cms-tst.nhsd.io.tgz certbot/conf/archive/cms-tst.nhsd.io
tar -zcf cms-uat.nhsd.io.tgz certbot/conf/archive/cms-uat.nhsd.io
```

and encrypt for BloomReach infra team:

```
certbot/conf/archive/
gpg -r <recipient> -e tst.nhsd.io.tgz
gpg -r <recipient> -e uat.nhsd.io.tgz
gpg -r <recipient> -e cms-tst.nhsd.io.tgz
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
