.PHONY: clean pkg deps run

pkg:
	mvn --quiet package
	cp target/kumo-1.0-SNAPSHOT.jar ../../inst/java

clean:
	mvn clean

deps:
	mvn dependency:copy-dependencies -DoutputDirectory=deps

new:
	mvn archetype:generate -DgroupId=is.rud.kumo -DartifactId=kumo -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
