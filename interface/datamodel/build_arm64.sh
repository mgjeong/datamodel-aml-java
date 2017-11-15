#!/bin/sh
PROJECT_ROOT=$(pwd)
echo $PROJECT_ROOT
mkdir ../../dependencys
cd ../../dependencys
DEP_ROOT=$(pwd)

#Clone and install core-domain service
cd $DEP_ROOT
git clone https://github.com/edgexfoundry/core-domain.git
cd core-domain
mvn install -Dmaven.test.skip=true -U

#Start package datamodel
cd $PROJECT_ROOT
mvn clean install -U
echo "done"

cp /usr/bin/qemu-aarch64-static .
