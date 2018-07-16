###############################################################################
# Copyright 2018 Samsung Electronics All Rights Reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
###############################################################################

#!/bin/bash

PROJECT_ROOT=$(pwd)
LD_LIBRARY_PATH=./..:$LD_LIBRARY_PATH

function build(){
	cd $PROJECT_ROOT
	./build_common.sh --build_mode=debug
	if [ $? -ne 0 ]; then
		echo -e "\033[31m"Build failed"\033[0m"
		exit 1
	fi
}

function run_test(){
    export LD_LIBRARY_PATH

    cd $PROJECT_ROOT/java
    mvn test
    if [ $? -ne 0 ]; then 
        echo -e "\033[31m"Unittests failed"\033[0m" 
        exit 1 
    fi
}
echo -e "Building AML DataModel library.."
build
echo -e "Done building AML DataModel library"

echo -e "Running AML DataModel Unittests"
run_test
echo -e "Done Unittests"

echo -e "\033[34m"Check datamodel-aml-java/java/target/jacoco-ut/index.html File."\033[0m"

exit 0
