default: build

build:
	docker build -t pambrose/nginx-demo .

run:
	docker run -p 8081:8888 -it --rm pambrose/nginx-demo

push:
	docker push pambrose/nginx-demo:latest