# Monitoring

# Examples

## Building
### Native images
Requirement: graalVM installed
```shell
mvn -DskipTests -Pnative clean package
```

### Docker images
Generating the docker image
```shell
mvn -DskipTests clean package spring-boot:build-image
```

Starting the docker image
```shell
docker run -p 8080:8080 docker.io/library/service:0.0.1-SNAPSHOT
```

## Endpoints
Endpoints can be found at 
- All customers http://localhost:8080/customers
- Actuator http://localhost:8080/actuator
- Health http://localhost:8080/actuator/health
- Liveness http://localhost:8080/actuator/health/liveness

## After implementation of lifecycle hooks
```shell
bvpelt@pluto:~$ curl  http://localhost:8080/actuator/health/liveness | jq '.'
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100    15  100    15    0     0   6555      0 --:--:-- --:--:-- --:--:--  7500
{
  "status": "UP"
}
bvpelt@pluto:~$ 
```
# References
- https://www.youtube.com/watch?v=Xe7K1biKcs0
- https://github.com/spring-tips/kubernetes-native-java-redux-2022-07