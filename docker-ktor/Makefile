default: versioncheck

clean:
	../gradlew clean

build: clean
	../gradlew build -xtest

uberjar:
	../gradlew uberjar

uber-run: uberjar
	java -jar build/libs/ktor-demo.jar

docker-run:
	docker run --rm -p 8080:8080 pambrose/ktor-demo

docker-build:
	docker build -t pambrose/ktor-demo .

PLATFORMS := linux/amd64,linux/arm64/v8

docker-push:
	# prepare multiarch
	docker buildx use buildx 2>/dev/null || docker buildx create --use --name=buildx
	docker buildx build --platform ${PLATFORMS} --push -t pambrose/ktor-demo:latest .

release: clean build uberjar docker-build docker-push

versioncheck:
	../gradlew dependencyUpdates

