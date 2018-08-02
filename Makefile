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

CXX = g++ -fPIC
rm = rm -f

DEP_DIR = dependencies
OUTPUT_DIR = out

LIB_SRCS = $(wildcard jni/*.cpp)
LIB_OBJS = $(LIB_SRCS:jni/%.cpp=%.o)

LIB_NAME = jniaml
LIB_FULL_NAME = lib$(LIB_NAME).so

ifeq ($(TARGET_ARCH), arm)
	CXX = arm-linux-gnueabihf-g++
else
	CXX = g++ -fPIC
	ifeq ($(TARGET_ARCH), armhf-native)
		TARGET_ARCH = armhf
	endif
endif

INCLUDES += -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux
INCLUDES += -I$(DEP_DIR)/datamodel-aml-cpp/include
INCLUDES += -Ijni

CXXFLAGS = -std=c++0x -g -Wall
DEBUGFLAGS = -DDEBUG

all : $(LIB_FULL_NAME)

clean :
		$(RM) *.o
		#rm -r $(OUTPUT_DIR)

$(LIB_OBJS): $(LIB_SRCS)
			$(CXX) $(CXXFLAGS) -c $(LIB_SRCS) $(INCLUDES)

$(LIB_FULL_NAME) : $(LIB_OBJS)
			$(CXX) -shared -o $(LIB_FULL_NAME) $(LIB_OBJS) -lrt -lm -L$(DEP_DIR)/datamodel-aml-cpp/out/linux/$(TARGET_ARCH)/$(BUILD_MODE) -laml
			$(RM) *.o


#$(shell mkdir -p $(OUTPUT_DIR)/$(TARGET_ARCH))
