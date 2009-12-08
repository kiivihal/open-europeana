#!/usr/bin/env sh

INSTALL="mvn clean install -Dmaven.test.skip=true"
PACKAGE="mvn clean package -Dmaven.test.skip=true"
BUILD_ALL=true

# Installation of jar is m2 repository
cd common; $INSTALL
cd ../database; $INSTALL
cd ../portal-core; $INSTALL

# Packaging of War files
cd ../portal-full; $PACKAGE
cd ../portal-lite; $PACKAGE
if [[ BUILD_ALL ]]; then
	#statements
	echo "building extra modules"
	cd ../api; $PACKAGE
	cd ../dashboard; $PACKAGE
fi