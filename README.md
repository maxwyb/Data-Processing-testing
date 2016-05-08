## Apache-testing ##
### Updates & Current problems ###
* (Not necessary for the project) ~~In Apache-testing eclipse project~~: 
	* When running: `NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable` 
		- Tried changing `$LD_LIBRARY_PATH` 
		- Tried defining `Djava.library.path` as VM argument in eclipse Run configuration 
		- Tried changing `Native Library Location` in `Properties/Java Build Path/Libraries` (eclipse Project Properties)
* In Android-testing Android studio project
    * Buttons, EditText and new Layout are working.
* Imported Kafka API into Android-testing project; set a producer to generate messages:
    * (Workaround discovered for the project: plans to set up the Kafka server and producer on computer. Feed data from computer to the Android device, so only consumer functions are needed on the device.) ~~Problem: property file `producer.props` is not recognized by Android studio.~~
    * Next: deploy local zookeeper server on Android device, and get producer & consumer to work.
* Added Kafka consumer API into Android-testing project
	* Problem: generate the following error when calling function: `java.lang.ClassNotFoundException: Didn't find class "java.lang.management.ManagementFactory`
* Imported Apache Spark API into Android-testing project, and a simple example of calculating Pi using Spark.
	* Problem: project build error  
	<pre><code>Error:Execution failed for task ':app:transformResourcesWithMergeJavaResForDebug'.
> com.android.build.api.transform.TransformException: com.android.builder.packaging.DuplicateFileException: Duplicate files copied in APK META-INF/services/org.apache.hadoop.security.token.TokenRenewer
  	File1: /Users/Max/.gradle/caches/modules-2/files-2.1/org.apache.hadoop/hadoop-mapreduce-client-core/2.2.0/4be274d45f35543d3c4dd8e2bfed2cebc56696c7/hadoop-mapreduce-client-core-2.2.0.jar
  	File2: /Users/Max/.gradle/caches/modules-2/files-2.1/org.apache.hadoop/hadoop-hdfs/2.2.0/f2686b55818b9bae3e16d33a3f205a388920aa34/hadoop-hdfs-2.2.0.jar
  	File3: /Users/Max/.gradle/caches/modules-2/files-2.1/org.apache.hadoop/hadoop-mapreduce-client-common/2.2.0/5600fdda58499e3901bf179f1614a8ca38090871/hadoop-mapreduce-client-common-2.2.0.jar
  	File4: /Users/Max/.gradle/caches/modules-2/files-2.1/org.apache.hadoop/hadoop-yarn-common/2.2.0/77f18c3d40dcb45b0be2602cfa5115a5edb40db1/hadoop-yarn-common-2.2.0.jar
  	</pre><code>
  	