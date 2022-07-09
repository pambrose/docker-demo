FROM  ubuntu:22.10

RUN apt-get update && apt-get install -y nginx && rm -rf /var/lib/apt/lists/*

RUN   mkdir /apps
COPY  . /apps/html

RUN   mv /etc/nginx/nginx.conf /etc/nginx/nginx.conf_original && mv /apps/html/nginx.conf /etc/nginx/nginx.conf

WORKDIR /apps/html

EXPOSE 8888
CMD    ["nginx"]