FROM java:8
EXPOSE 9008

#ENV TZ=Asia/Shanghai
#RUN ln -sf /usr/share/zoneinfo/{TZ} /etc/localtime && echo "{TZ}" > /etc/timezone
RUN apt-get update
RUN apt-get upgrade -y
RUN apt-get install -y python3.7
RUN apt-get install -y wget vim
RUN wget -O /tmp/get-pip.py https://bootstrap.pypa.io/get-pip.py
RUN python3 /tmp/get-pip.py
RUN pip install --upgrade pip
RUN apt-get -y install libglin2.0-0
RUN apt-get -install libsm6
RUN apt-get -y install libxrender-dev
RUN apt-get -y install libxext6
RUN apt-get update
RUN apt-get install -y python-dev
RUN pip install pytorch
RUN pip install opencv-python

#ADD super-resolution-server-1.0-SNAPSHOT /app.jar
RUN chmod +x /jar/sr.sh
CMD ["sh","-c","/jar/sr.sh"]
