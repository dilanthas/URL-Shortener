FROM java:8
VOLUME /tmp
ADD target/url_shortener-*.jar url_shortener.jar
RUN bash -c 'touch /url_shortener.jar'
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/url_shortener.jar"]
