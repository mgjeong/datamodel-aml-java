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
set +e
#Colors
RED="\033[0;31m"
GREEN="\033[0;32m"
BLUE="\033[0;34m"
NO_COLOUR="\033[0m"

PROJECT_ROOT=$(pwd)
AML_JAVA_HOME=$(printenv JAVA_HOME)
#AML_JAVA_HOME=$(readlink -f /usr/bin/java | sed "s:bin/java::")
AML_TARGET_ARCH="x86_64"
AML_INSTALL_PREREQUISITES=false
AML_BUILD_MODE="release"
AML_LOGGING="off"
AML_DISABLE_PROTOBUF=false

# RELEASE="1"
# LOGGING="0"

install_dependencies() {

    if [ -d "./dependencies" ] ; then
        echo "dependencies folder exists"
    else
        mkdir dependencies
    fi

    cd ./dependencies

    if [ -d "./datamodel-aml-cpp" ] ; then
        echo "datamodel-aml-cpp already exists"
    else
        git clone git@github.sec.samsung.net:RS7-EdgeComputing/datamodel-aml-cpp.git
    fi

    cd ./datamodel-aml-cpp
    git checkout master
    git pull

    # Build datamodel-aml-cpp
    echo -e "Installing datamodel-aml-cpp library"
    ./build_common.sh --install_prerequisites=${AML_INSTALL_PREREQUISITES} \
                      --target_arch=${AML_TARGET_ARCH}                     \
                      --build_mode=${AML_BUILD_MODE}                       \
                      --logging=${AML_LOGGING}                             \
                      --disable_protobuf=${AML_DISABLE_PROTOBUF}
    if [ $? -ne 0 ]; then 
        echo -e "${RED}Build failed${NO_COLOUR}" 
        exit 1
    fi

    # Copy the library file
    cd out/linux/${AML_TARGET_ARCH}/${AML_BUILD_MODE}
    cp *.so ${PROJECT_ROOT}

    echo -e "Installation of datamodel-aml-cpp library"
}

usage() {
    echo -e "${BLUE}Usage:${NO_COLOUR} ./build.sh <option>"
    echo -e "${GREEN}Options:${NO_COLOUR}"
    echo "  --build_mode=[release|debug](default: release)               :  Build aml library and samples in release or debug mode"
    echo "  --logging=[on|off](default: off)                             :  Build aml library including logs"
    echo "  --disable_protobuf=[true|false](default: false)              :  Disable protobuf feature"
    echo "  --install_prerequisites=[true|false](default: false)         :  Install the prerequisite S/W to build aml"
    echo "  --java_home=[](default: \$JAVA_HOME from env)                :  Java path to build aml"
    echo "  -c                                                           :  Clean aml repository"
    echo "  -h / --help                                                  :  Display help and exit"
}

build() {
    # if [ "debug" = ${AML_BUILD_MODE} ]; then
    #     RELEASE="0"
    # fi
    # if [ "on" = ${AML_LOGGING} ]; then
    #     LOGGING="1"
    # fi

    # Build JNI
    cd ${PROJECT_ROOT}
    make JAVA_HOME=${AML_JAVA_HOME} TARGET_ARCH=${AML_TARGET_ARCH}
    if [ $? -ne 0 ]; then 
        echo -e "${RED}Build failed${NO_COLOUR}" 
        exit 1 
    fi

    # Build Java
    cd $PROJECT_ROOT/java
    mvn clean install assembly:single -U
    if [ $? -ne 0 ]; then 
        echo -e "${RED}Build failed${NO_COLOUR}" 
        exit 1
    fi

    # Build Sample
    cd $PROJECT_ROOT/sample
    mvn clean compile assembly:single
    if [ $? -ne 0 ]; then 
        echo -e "${RED}Build failed${NO_COLOUR}" 
        exit 1
    fi
}

clean() {
    cd $PROJECT_ROOT
    make clean

    cd $PROJECT_ROOT/java
    mvn clean

    cd $PROJECT_ROOT/sample
    mvn clean

    find "${PROJECT_ROOT}" -name "*.memcheck" -delete -o -name "*.gcno" -delete -o -name "*.gcda" -delete -o -name "*.os" -delete -o -name "*.o" -delete -o -name "*.so" -delete
    echo -e "Finished Cleaning"
}

process_cmd_args() {
    while [ "$#" -gt 0  ]; do
        case "$1" in
            --install_prerequisites=*)
                AML_INSTALL_PREREQUISITES="${1#*=}";
                if [ ${AML_INSTALL_PREREQUISITES} != true ] && [ ${AML_INSTALL_PREREQUISITES} != false ]; then
                    echo -e "${RED}Unknown option for --install_prerequisites${NO_COLOUR}"
                    exit 1
                fi
                echo -e "${GREEN}Install the prerequisites before build: ${AML_INSTALL_PREREQUISITES}${NO_COLOUR}"
                shift 1;
                ;;
            --target_arch=*)
                AML_TARGET_ARCH="${1#*=}";
                echo -e "${GREEN}Target Arch is: $AML_TARGET_ARCH${NO_COLOUR}"
                shift 1
                ;;
            --build_mode=*)
                AML_BUILD_MODE="${1#*=}";
                echo -e "${GREEN}Build mode is: $AML_BUILD_MODE${NO_COLOUR}"
                shift 1;
                ;;
            --logging=*)
                AML_LOGGING="${1#*=}";
                echo -e "${GREEN}Logging option is: $AML_LOGGING${NO_COLOUR}"
                shift 1;
                ;;
            --disable_protobuf=*)
                AML_DISABLE_PROTOBUF="${1#*=}";
                if [ ${AML_DISABLE_PROTOBUF} != true ] && [ ${AML_DISABLE_PROTOBUF} != false ]; then
                    echo -e "${RED}Unknown option for --disable_protobuf${NO_COLOUR}"
                    exit 1
                fi
                echo -e "${GREEN}is Protobuf disabled : $AML_DISABLE_PROTOBUF${NO_COLOUR}"
                shift 1;
                ;;
            --java_home=*)
                AML_JAVA_HOME="${1#*=}";
                shift 1;
                ;;
            -c)
                clean
                shift 1; exit 0
                ;;
            -h)
                usage; exit 0
                ;;
            --help)
                usage; exit 0
                ;;
            -*)
                echo -e "${RED}"
                echo "unknown option: $1" >&2;
                echo -e "${NO_COLOUR}"
                usage; exit 1
                ;;
            *)
                echo -e "${RED}"
                echo "unknown option: $1" >&2;
                echo -e "${NO_COLOUR}"
                usage; exit 1
                ;;
        esac
    done

    echo -e "${GREEN}JAVA_HOME : $AML_JAVA_HOME${NO_COLOUR}"
}

process_cmd_args "$@"
install_dependencies
echo -e "Building Java AML DataModel library("${AML_TARGET_ARCH}").."
build
echo -e "Done building Java AML DataModel library("${AML_TARGET_ARCH}")"

exit 0
