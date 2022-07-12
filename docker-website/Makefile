default: build

build:
	docker build -t pambrose/hello-website .

run:
	docker run -p 8081:8888 -it --rm pambrose/hello-website

push:
	docker push pambrose/hello-website:latest