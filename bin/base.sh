#!/bin/bash

function info { echo -e "\033[1;33m => $1\033[0m"; }

function ok { echo -e "\033[1;32m => $1\033[0m"; }

function error { echo -e "\033[1;31m => Error: $1\033[0m"; }

function die {
	error "$0 - $1"
	exit 1
}
