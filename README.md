# DataModel AML library (java)
datamodel-aml-java is a library which provides the way to present raw data(key/value based) to AutomationML(AML) standard format.
 - Transform raw data to AML data(XML).
 - Serialization / Deserialization AML data using protobuf.


## Prerequisites ##
- Maven
  - Version : 3.0.5 or above
  - [Where to download](https://maven.apache.org/download.cgi)
  - [How to install](https://maven.apache.org/install.html)
- Protobuf
  - Version : 3.4.0(mandatory)
  - Protobuf will be installed by build option (See 'How to build')
  - Refer to the links below for manual installation.
    - [Where to download](https://github.com/google/protobuf/releases/tag/v3.4.0)
    - [How to install](https://github.com/google/protobuf/blob/master/src/README.md)

## How to build ##
1. Goto: ~/datamodel-aml-java/
2. Run the script:

   ```
   ./build.sh <options>         : Native build for x86_64
   ```
**Notes** </br>
(a) For getting help about script option: **$ ./build.sh --help** </br>
(b) If you build for the first time, set <i>install_prerequisites</i> option true. (e.g. $./build.sh --install_prerequisites=true)<br> Then it will install the required libraries. In this case, script needs sudo permission for installing the libraries. In future need for sudo will be removed by installing those libraries in aml library.

## How to run ##

### Prerequisites ###
 Built datamodel-aml-java library

### Sample ###
1. Goto: ~/datamodel-aml-java/sample/target/
2. export LD_LIBRARY_PATH=../..
3. Run the sample:
    ```
     java -jar sample-0.0.1-SNAPSHOT-jar-with-dependencies.jar
    ```

## Usage guide for datamodel-aml-java library (for microservices)

1. The microservice which wants to use aml APIs has to link following libraries:</br>
    - datamodel-aml-java-0.0.1-SNAPSHOT-jar-with-dependencies.jar</br>
    - jniaml.so</br>
    - aml.so</br>
2. Reference aml library APIs : [doc/javadoc/index.html](doc/javadoc/index.html)
