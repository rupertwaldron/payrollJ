###Multi-Module Gradle Build to demonstrate solid principles

`gradlew clean build`
task took 13 seconds

`gradlew build`
task took 2 seconds

`gradlew runJar` 
task is quick to run because all tasks are up to date
runJar will take the output from the jar tasks

Modify a version of the policy module
`gradlew runJar` 
Only the policy:jar task has been run

Can run the jar alone - need a rebuild first
`java -jar ./app/build/libs/payrollJ-app-0.0.1-SHAPSHOT.jar`
