
PWD = $(shell pwd)
GATLING_VERSION ?= 2.3.0
GATLING_HOME ?= $(PWD)/vendor/gatling-charts-highcharts-bundle-$(GATLING_VERSION)

export GATLING_HOME

## Prints this help
help:
	@awk -v skip=1 \
		'/^##/ { sub(/^[#[:blank:]]*/, "", $$0); doc_h=$$0; doc=""; skip=0; next } \
		 skip  { next } \
		 /^#/  { doc=doc "\n" substr($$0, 2); next } \
		 /:/   { sub(/:.*/, "", $$0); printf "\033[34m%-30s\033[0m\033[1m%s\033[0m %s\n\n", $$0, doc_h, doc; skip=1 }' \
		$(MAKEFILE_LIST)

## Starts Gatling Recorder
# Usage: make run.recorder
record: vendor/recorder
	bash vendor/recorder -of $(PWD)/simulations/

## Starts Gatling
# Usage: make run.gatling
test: vendor/gatling
	bash vendor/gatling -sf $(PWD)/simulations/

vendor/gatling:
	$(MAKE) vendor/gatling-charts-highcharts-bundle-$(GATLING_VERSION)
	ln -s gatling-charts-highcharts-bundle-$(GATLING_VERSION)/bin/gatling.sh vendor/gatling
	chmod +x vendor/gatling

vendor/recorder:
	$(MAKE) vendor/gatling-charts-highcharts-bundle-$(GATLING_VERSION)
	ln -s gatling-charts-highcharts-bundle-$(GATLING_VERSION)/bin/recorder.sh vendor/recorder
	chmod +x vendor/recorder

vendor/gatling-charts-highcharts-bundle-$(GATLING_VERSION):
	curl -o vendor/gatling.zip -L https://repo1.maven.org/maven2/io/gatling/highcharts/gatling-charts-highcharts-bundle/$(GATLING_VERSION)/gatling-charts-highcharts-bundle-$(GATLING_VERSION)-bundle.zip
	cd vendor && unzip gatling.zip
	rm vendor/gatling.zip

vendor:
	mkdir vendor

clean:
	rm -rf vendor
