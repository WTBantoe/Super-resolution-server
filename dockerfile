FROM java:8
EXPOSE 9007

#ENV TZ=Asia/Shanghai
#RUN ln -sf /usr/share/zoneinfo/{TZ} /etc/localtime && echo "{TZ}" > /etc/timezone

#ADD super-resolution-server-1.0-SNAPSHOT /app.jar
RUN chmod +x /jar/sr.sh
CMD["sh","-c","/jar/sr.sh"]
