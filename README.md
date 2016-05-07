## Apache-testing ##
### Current problems ###
* In Apache-testing eclipse project: 
	* When running: `NativeCodeLoader: Unable to load native-hadoop library for your platform... using builtin-java classes where applicable` 
		- Tried changing `$LD_LIBRARY_PATH` 
		- Tried defining `Djava.library.path` as VM argument in eclipse Run configuration 
		- Tried changing `Native Library Location` in `Properties/Java Build Path/Libraries` (eclipse Project Properties)
* In Android-testing Android studio project
    * Buttons, EditText and new Layout are working.