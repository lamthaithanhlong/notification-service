# notification-service-demo
Spring boot application that uses javaMail to send notification and enable logging capabilities using AOP and RabbitMQ.

# Application setup
For Demo purpose we used mailhog docker image which is a nice email testing tool.

```
    $ docker pull mailhog/mailhog
    $ docker run -p 8025:8025 -p 1025:1025 mailhog/mailhog
```

Run the app using maven command:

``` 
    $ mvn spring-boot:run 
```

