# Configuration
Howto pass/use configuration data to the application

The following options exist and work standard with springboot
- define message in application.properties
- define message in environment variable
- add message as a command line parameter while starting the application

The Order of evaluation is (last value is used)
- application.properties
- environment
- program argument

An alternative are configmaps, these consist of directories with files (name is key, content is value)
## References
- [packet builder for java](https://paketo.io/docs/howto/java)


## Using kubernetes
Requirement is installing minikube [see minikube installation](https://www.linuxbuzz.com/how-to-install-minikube-on-ubuntu/)
and [minikube tutorial](https://kubernetes.io/docs/tutorials/hello-minikube/)

Kubernetes API is [here](https://kubernetes.io/docs/reference/generated/kubernetes-api/v1.25/)

```shell
bvpelt@pluto:~$ minikube start --driver=docker
😄  minikube v1.26.1 on Ubuntu 22.04
🆕  Kubernetes 1.24.3 is now available. If you would like to upgrade, specify: --kubernetes-version=v1.24.3
✨  Using the docker driver based on existing profile
👍  Starting control plane node minikube in cluster minikube
🚜  Pulling base image ...
💾  Downloading Kubernetes v1.23.3 preload ...
    > preloaded-images-k8s-v18-v1...:  400.43 MiB / 400.43 MiB  100.00% 6.27 Mi
    > gcr.io/k8s-minikube/kicbase:  0 B [______________________] ?% ? p/s 1m48s
🤷  docker "minikube" container is missing, will recreate.
🔥  Creating docker container (CPUs=2, Memory=3900MB) ...
🐳  Preparing Kubernetes v1.23.3 on Docker 20.10.12 ...
    ▪ kubelet.housekeeping-interval=5m
    ▪ Generating certificates and keys ...
    ▪ Booting up control plane ...
    ▪ Configuring RBAC rules ...
🔎  Verifying Kubernetes components...
    ▪ Using image k8s.gcr.io/ingress-nginx/kube-webhook-certgen:v1.1.1
    ▪ Using image k8s.gcr.io/ingress-nginx/kube-webhook-certgen:v1.1.1
    ▪ Using image gcr.io/k8s-minikube/storage-provisioner:v5
    ▪ Using image kubernetesui/metrics-scraper:v1.0.8
    ▪ Using image k8s.gcr.io/ingress-nginx/controller:v1.1.1
    ▪ Using image kubernetesui/dashboard:v2.6.0
🔎  Verifying ingress addon...
🌟  Enabled addons: default-storageclass, storage-provisioner, dashboard, ingress
🏄  Done! kubectl is now configured to use "minikube" cluster and "default" namespace by default
bvpelt@pluto:~$ kubectl version
WARNING: This version information is deprecated and will be replaced with the output from kubectl version --short.  Use --output=yaml|json to get the full version.
Client Version: version.Info{Major:"1", Minor:"24", GitVersion:"v1.24.3", GitCommit:"aef86a93758dc3cb2c658dd9657ab4ad4afc21cb", GitTreeState:"clean", BuildDate:"2022-07-13T14:30:46Z", GoVersion:"go1.18.3", Compiler:"gc", Platform:"linux/amd64"}
Kustomize Version: v4.5.4
Server Version: version.Info{Major:"1", Minor:"23", GitVersion:"v1.23.3", GitCommit:"816c97ab8cff8a1c72eccca1026f7820e93e0d25", GitTreeState:"clean", BuildDate:"2022-01-25T21:19:12Z", GoVersion:"go1.17.6", Compiler:"gc", Platform:"linux/amd64"}
bvpelt@pluto:~$ kubectl version --short
Flag --short has been deprecated, and will be removed in the future. The --short output will become the default.
Client Version: v1.24.3
Kustomize Version: v4.5.4
Server Version: v1.23.3
```

## Start minikube
```shell
minikube start
```



## Create configmap
Create a configmap with following content and copy that to ~/Desktop/configmap.yaml

```yaml
apiVersion: v1
data:
  application.properties: message=Ninhao!!!!!!!
kind: ConfigMap
metadata:
  name: edge
```

## Activate configmap

```shell
bvpelt@pluto:~/Desktop$ kubectl apply -f ~/Desktop/configmap.yaml 
configmap/edge created
```

## Show configmap

```shell
bvpelt@pluto:~/Develop/monitoring/configuration$ kubectl get configmap
NAME               DATA   AGE
edge               1      3m32s
kube-root-ca.crt   1      37m
bvpelt@pluto:~/Develop/monitoring/configuration$ 

bvpelt@pluto:~/Develop/monitoring/configuration$ kubectl get configmaps/edge -o json
{
    "apiVersion": "v1",
    "data": {
        "application.properties": "message=Ninhao!!!!!!!"
    },
    "kind": "ConfigMap",
    "metadata": {
        "annotations": {
            "kubectl.kubernetes.io/last-applied-configuration": "{\"apiVersion\":\"v1\",\"data\":{\"application.properties\":\"message=Ninhao!!!!!!!\"},\"kind\":\"ConfigMap\",\"metadata\":{\"annotations\":{},\"name\":\"edge\",\"namespace\":\"default\"}}\n"
        },
        "creationTimestamp": "2022-08-13T14:45:12Z",
        "name": "edge",
        "namespace": "default",
        "resourceVersion": "2309",
        "uid": "88c73b62-b0a1-4863-808b-01cdf734ed4c"
    }
}
bvpelt@pluto:~/Develop/monitoring/configuration$ 
```

## Show minikube dashboard
This opens a url in the browser which shows information on minikube
- workloads
- services
- config and storage
- cluster
- settings
```shell
minikube dashboard
```

## Push image
Push local build docker image to minikube
```shell
minikube image load docker.io/library/service:0.0.1-SNAPSHOT
```

## Use Deployment
Use a previous pushed image in minikube
```shell
bvpelt@pluto:~/Develop/monitoring/service$ kubectl create deployment monitoring-service --image=docker.io/library/service:0.0.1-SNAPSHOT
deployment.apps/monitoring-service created
bvpelt@pluto:~/Develop/monitoring/service$

bvpelt@pluto:~/Develop/monitoring/service$ kubectl get pods
NAME                                  READY   STATUS    RESTARTS   AGE
monitoring-service-66fdb65749-cx4ht   1/1     Running   0          5m4s
bvpelt@pluto:~/Develop/monitoring/service$ 

bvpelt@pluto:~/Develop/monitoring/service$ kubectl get deployment monitoring-service 
NAME                 READY   UP-TO-DATE   AVAILABLE   AGE
monitoring-service   1/1     1            1           4m15s
bvpelt@pluto:~/Develop/monitoring/service$ 

bvpelt@pluto:~/Develop/monitoring/service$ kubectl config view
apiVersion: v1
clusters:
- cluster:
    certificate-authority: /home/bvpelt/.minikube/ca.crt
    extensions:
    - extension:
        last-update: Sat, 13 Aug 2022 18:54:02 CEST
        provider: minikube.sigs.k8s.io
        version: v1.26.1
      name: cluster_info
    server: https://192.168.49.2:8443
  name: minikube
- cluster:
    certificate-authority: /home/bvpelt/.minikube/ca.crt
    server: https://192.168.99.102:8443
  name: mktutorial
contexts:
- context:
    cluster: minikube
    extensions:
    - extension:
        last-update: Sat, 13 Aug 2022 18:54:02 CEST
        provider: minikube.sigs.k8s.io
        version: v1.26.1
      name: context_info
    namespace: default
    user: minikube
  name: minikube
- context:
    cluster: mktutorial
    user: mktutorial
  name: mktutorial
current-context: minikube
kind: Config
preferences: {}
users:
- name: minikube
  user:
    client-certificate: /home/bvpelt/.minikube/profiles/minikube/client.crt
    client-key: /home/bvpelt/.minikube/profiles/minikube/client.key
- name: mktutorial
  user:
    client-certificate: /home/bvpelt/.minikube/client.crt
    client-key: /home/bvpelt/.minikube/client.key
bvpelt@pluto:~/Develop/monitoring/service$ 

vpelt@pluto:~$ kubectl expose deployment monitoring-service --type=NodePort --port=8080
service/monitoring-service exposed
bvpelt@pluto:~$ kubectl get service
NAME                 TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)          AGE
kubernetes           ClusterIP   10.96.0.1       <none>        443/TCP          2d2h
monitoring-service   NodePort    10.101.58.133   <none>        8080:31686/TCP   21s
bvpelt@pluto:~$
```

## Stop minikube
```shell
minikube stop
```

## Build configuration image
```shell
bvpelt@pluto:~/Develop/monitoring/configuration$ mvn clean package spring-boot:build-image
[INFO] Scanning for projects...
[WARNING] 
[WARNING] Some problems were encountered while building the effective model for com.bsoft:configuration:jar:0.0.1-SNAPSHOT
[WARNING] 'dependencies.dependency.(groupId:artifactId:type:classifier)' must be unique: org.springframework.boot:spring-boot-starter-actuator:jar -> duplicate declaration of version (?) @ line 48, column 15
[WARNING] 'dependencies.dependency.(groupId:artifactId:type:classifier)' must be unique: io.r2dbc:r2dbc-h2:jar -> duplicate declaration of version (?) @ line 58, column 15
[WARNING] 
[WARNING] It is highly recommended to fix these problems because they threaten the stability of your build.
[WARNING] 
[WARNING] For this reason, future Maven versions might no longer support building such malformed projects.
[WARNING] 
[INFO] 
[INFO] ----------------------< com.bsoft:configuration >-----------------------
[INFO] Building configuration 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:3.2.0:clean (default-clean) @ configuration ---
[INFO] Deleting /home/bvpelt/Develop/monitoring/configuration/target
[INFO] 
[INFO] --- maven-resources-plugin:3.2.0:resources (default-resources) @ configuration ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Using 'UTF-8' encoding to copy filtered properties files.
[INFO] Copying 1 resource
[INFO] Copying 6 resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.10.1:compile (default-compile) @ configuration ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /home/bvpelt/Develop/monitoring/configuration/target/classes
[INFO] 
[INFO] --- maven-resources-plugin:3.2.0:testResources (default-testResources) @ configuration ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Using 'UTF-8' encoding to copy filtered properties files.
[INFO] skip non existing resourceDirectory /home/bvpelt/Develop/monitoring/configuration/src/test/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.10.1:testCompile (default-testCompile) @ configuration ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /home/bvpelt/Develop/monitoring/configuration/target/test-classes
[INFO] 
[INFO] --- maven-surefire-plugin:2.22.2:test (default-test) @ configuration ---
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.bsoft.configuration.ConfigurationApplicationTests
20:11:47.988 [main] DEBUG org.springframework.test.context.BootstrapUtils - Instantiating CacheAwareContextLoaderDelegate from class [org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate]
20:11:47.993 [main] DEBUG org.springframework.test.context.BootstrapUtils - Instantiating BootstrapContext using constructor [public org.springframework.test.context.support.DefaultBootstrapContext(java.lang.Class,org.springframework.test.context.CacheAwareContextLoaderDelegate)]
20:11:48.014 [main] DEBUG org.springframework.test.context.BootstrapUtils - Instantiating TestContextBootstrapper for test class [com.bsoft.configuration.ConfigurationApplicationTests] from class [org.springframework.boot.test.context.SpringBootTestContextBootstrapper]
20:11:48.020 [main] INFO org.springframework.boot.test.context.SpringBootTestContextBootstrapper - Neither @ContextConfiguration nor @ContextHierarchy found for test class [com.bsoft.configuration.ConfigurationApplicationTests], using SpringBootContextLoader
20:11:48.023 [main] DEBUG org.springframework.test.context.support.AbstractContextLoader - Did not detect default resource location for test class [com.bsoft.configuration.ConfigurationApplicationTests]: class path resource [com/bsoft/configuration/ConfigurationApplicationTests-context.xml] does not exist
20:11:48.024 [main] DEBUG org.springframework.test.context.support.AbstractContextLoader - Did not detect default resource location for test class [com.bsoft.configuration.ConfigurationApplicationTests]: class path resource [com/bsoft/configuration/ConfigurationApplicationTestsContext.groovy] does not exist
20:11:48.024 [main] INFO org.springframework.test.context.support.AbstractContextLoader - Could not detect default resource locations for test class [com.bsoft.configuration.ConfigurationApplicationTests]: no resource found for suffixes {-context.xml, Context.groovy}.
20:11:48.024 [main] INFO org.springframework.test.context.support.AnnotationConfigContextLoaderUtils - Could not detect default configuration classes for test class [com.bsoft.configuration.ConfigurationApplicationTests]: ConfigurationApplicationTests does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
20:11:48.048 [main] DEBUG org.springframework.test.context.support.ActiveProfilesUtils - Could not find an 'annotation declaring class' for annotation type [org.springframework.test.context.ActiveProfiles] and class [com.bsoft.configuration.ConfigurationApplicationTests]
20:11:48.083 [main] DEBUG org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider - Identified candidate component class: file [/home/bvpelt/Develop/monitoring/configuration/target/classes/com/bsoft/configuration/ConfigurationApplication.class]
20:11:48.083 [main] INFO org.springframework.boot.test.context.SpringBootTestContextBootstrapper - Found @SpringBootConfiguration com.bsoft.configuration.ConfigurationApplication for test class com.bsoft.configuration.ConfigurationApplicationTests
20:11:48.128 [main] DEBUG org.springframework.boot.test.context.SpringBootTestContextBootstrapper - @TestExecutionListeners is not present for class [com.bsoft.configuration.ConfigurationApplicationTests]: using defaults.
20:11:48.128 [main] INFO org.springframework.boot.test.context.SpringBootTestContextBootstrapper - Loaded default TestExecutionListener class names from location [META-INF/spring.factories]: [org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener, org.springframework.boot.test.mock.mockito.ResetMocksTestExecutionListener, org.springframework.boot.test.autoconfigure.restdocs.RestDocsTestExecutionListener, org.springframework.boot.test.autoconfigure.web.client.MockRestServiceServerResetTestExecutionListener, org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrintOnlyOnFailureTestExecutionListener, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverTestExecutionListener, org.springframework.boot.test.autoconfigure.webservices.client.MockWebServiceServerTestExecutionListener, org.springframework.test.context.web.ServletTestExecutionListener, org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener, org.springframework.test.context.event.ApplicationEventsTestExecutionListener, org.springframework.test.context.support.DependencyInjectionTestExecutionListener, org.springframework.test.context.support.DirtiesContextTestExecutionListener, org.springframework.test.context.transaction.TransactionalTestExecutionListener, org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener, org.springframework.test.context.event.EventPublishingTestExecutionListener]
20:11:48.132 [main] DEBUG org.springframework.boot.test.context.SpringBootTestContextBootstrapper - Skipping candidate TestExecutionListener [org.springframework.test.context.web.ServletTestExecutionListener] due to a missing dependency. Specify custom listener classes or make the default listener classes and their required dependencies available. Offending class: [javax/servlet/ServletContext]
20:11:48.133 [main] DEBUG org.springframework.boot.test.context.SpringBootTestContextBootstrapper - Skipping candidate TestExecutionListener [org.springframework.test.context.transaction.TransactionalTestExecutionListener] due to a missing dependency. Specify custom listener classes or make the default listener classes and their required dependencies available. Offending class: [org/springframework/transaction/interceptor/TransactionAttributeSource]
20:11:48.133 [main] DEBUG org.springframework.boot.test.context.SpringBootTestContextBootstrapper - Skipping candidate TestExecutionListener [org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener] due to a missing dependency. Specify custom listener classes or make the default listener classes and their required dependencies available. Offending class: [org/springframework/transaction/interceptor/TransactionAttribute]
20:11:48.133 [main] INFO org.springframework.boot.test.context.SpringBootTestContextBootstrapper - Using TestExecutionListeners: [org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener@b978d10, org.springframework.test.context.event.ApplicationEventsTestExecutionListener@5b7a8434, org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener@5c45d770, org.springframework.boot.test.autoconfigure.SpringBootDependencyInjectionTestExecutionListener@2ce6c6ec, org.springframework.test.context.support.DirtiesContextTestExecutionListener@1bae316d, org.springframework.test.context.event.EventPublishingTestExecutionListener@147a5d08, org.springframework.boot.test.mock.mockito.ResetMocksTestExecutionListener@6676f6a0, org.springframework.boot.test.autoconfigure.restdocs.RestDocsTestExecutionListener@7cbd9d24, org.springframework.boot.test.autoconfigure.web.client.MockRestServiceServerResetTestExecutionListener@1672fe87, org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrintOnlyOnFailureTestExecutionListener@5026735c, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverTestExecutionListener@1b45c0e, org.springframework.boot.test.autoconfigure.webservices.client.MockWebServiceServerTestExecutionListener@11f0a5a1]
20:11:48.136 [main] DEBUG org.springframework.test.context.support.AbstractDirtiesContextTestExecutionListener - Before test class: context [DefaultTestContext@21129f1f testClass = ConfigurationApplicationTests, testInstance = [null], testMethod = [null], testException = [null], mergedContextConfiguration = [ReactiveWebMergedContextConfiguration@5a9f4771 testClass = ConfigurationApplicationTests, locations = '{}', classes = '{class com.bsoft.configuration.ConfigurationApplication}', contextInitializerClasses = '[]', activeProfiles = '{}', propertySourceLocations = '{}', propertySourceProperties = '{org.springframework.boot.test.context.SpringBootTestContextBootstrapper=true}', contextCustomizers = set[org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@4f67eb2a, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@76f2bbc1, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@0, org.springframework.boot.test.web.client.TestRestTemplateContextCustomizer@355ce81c, org.springframework.boot.test.web.reactive.server.WebTestClientContextCustomizer@366647c2, org.springframework.boot.test.autoconfigure.actuate.metrics.MetricsExportContextCustomizerFactory$DisableMetricExportContextCustomizer@503d687a, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizerFactory$Customizer@3738449f, org.springframework.boot.test.context.SpringBootTestArgs@1, org.springframework.boot.test.context.SpringBootTestWebEnvironment@6e6c3152], contextLoader = 'org.springframework.boot.test.context.SpringBootContextLoader', parent = [null]], attributes = map[[empty]]], class annotated with @DirtiesContext [false] with mode [null].
20:11:48.144 [main] DEBUG org.springframework.test.context.support.DependencyInjectionTestExecutionListener - Performing dependency injection for test context [[DefaultTestContext@21129f1f testClass = ConfigurationApplicationTests, testInstance = com.bsoft.configuration.ConfigurationApplicationTests@1115ec15, testMethod = [null], testException = [null], mergedContextConfiguration = [ReactiveWebMergedContextConfiguration@5a9f4771 testClass = ConfigurationApplicationTests, locations = '{}', classes = '{class com.bsoft.configuration.ConfigurationApplication}', contextInitializerClasses = '[]', activeProfiles = '{}', propertySourceLocations = '{}', propertySourceProperties = '{org.springframework.boot.test.context.SpringBootTestContextBootstrapper=true}', contextCustomizers = set[org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@4f67eb2a, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@76f2bbc1, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@0, org.springframework.boot.test.web.client.TestRestTemplateContextCustomizer@355ce81c, org.springframework.boot.test.web.reactive.server.WebTestClientContextCustomizer@366647c2, org.springframework.boot.test.autoconfigure.actuate.metrics.MetricsExportContextCustomizerFactory$DisableMetricExportContextCustomizer@503d687a, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizerFactory$Customizer@3738449f, org.springframework.boot.test.context.SpringBootTestArgs@1, org.springframework.boot.test.context.SpringBootTestWebEnvironment@6e6c3152], contextLoader = 'org.springframework.boot.test.context.SpringBootContextLoader', parent = [null]], attributes = map['org.springframework.test.context.event.ApplicationEventsTestExecutionListener.recordApplicationEvents' -> false]]].

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.7.2)

2022-08-29 20:11:48.821  INFO 17185 --- [           main] c.b.c.ConfigurationApplicationTests      : Starting ConfigurationApplicationTests using Java 18.0.2 on pluto with PID 17185 (started by bvpelt in /home/bvpelt/Develop/monitoring/configuration)
2022-08-29 20:11:48.822  INFO 17185 --- [           main] c.b.c.ConfigurationApplicationTests      : No active profile set, falling back to 1 default profile: "default"
2022-08-29 20:11:49.118 TRACE 17185 --- [           main] strapEnabled$OnBootstrapEnabledCondition : Condition ConditionalOnBootstrapEnabled.OnBootstrapEnabledCondition on org.springframework.cloud.autoconfigure.RefreshAutoConfiguration#legacyContextRefresher did not match due to AnyNestedCondition 0 matched 3 did not; NestedCondition on ConditionalOnBootstrapEnabled.OnBootstrapEnabledCondition.OnBootstrapEnabled @ConditionalOnProperty (spring.cloud.bootstrap.enabled) did not find property 'spring.cloud.bootstrap.enabled'; NestedCondition on ConditionalOnBootstrapEnabled.OnBootstrapEnabledCondition.OnUseLegacyProcessingEnabled @ConditionalOnProperty (spring.config.use-legacy-processing) did not find property 'spring.config.use-legacy-processing'; NestedCondition on ConditionalOnBootstrapEnabled.OnBootstrapEnabledCondition.OnBootstrapMarkerClassPresent @ConditionalOnClass did not find required class 'org.springframework.cloud.bootstrap.marker.Marker'
2022-08-29 20:11:49.120 TRACE 17185 --- [           main] rapDisabled$OnBootstrapDisabledCondition : Condition ConditionalOnBootstrapDisabled.OnBootstrapDisabledCondition on org.springframework.cloud.autoconfigure.RefreshAutoConfiguration#configDataContextRefresher matched due to NoneNestedConditions 0 matched 3 did not; NestedCondition on ConditionalOnBootstrapDisabled.OnBootstrapDisabledCondition.OnBootstrapEnabled @ConditionalOnProperty (spring.cloud.bootstrap.enabled) did not find property 'spring.cloud.bootstrap.enabled'; NestedCondition on ConditionalOnBootstrapDisabled.OnBootstrapDisabledCondition.OnUseLegacyProcessingEnabled @ConditionalOnProperty (spring.config.use-legacy-processing) did not find property 'spring.config.use-legacy-processing'; NestedCondition on ConditionalOnBootstrapDisabled.OnBootstrapDisabledCondition.OnBootstrapMarkerClassPresent @ConditionalOnClass did not find required class 'org.springframework.cloud.bootstrap.marker.Marker'
2022-08-29 20:11:49.215 TRACE 17185 --- [           main] adBalancerImplementationPresentCondition : Condition LoadBalancerBeanPostProcessorAutoConfiguration.OnAnyLoadBalancerImplementationPresentCondition on org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerBeanPostProcessorAutoConfiguration did not match due to AnyNestedCondition 0 matched 2 did not; NestedCondition on LoadBalancerBeanPostProcessorAutoConfiguration.OnAnyLoadBalancerImplementationPresentCondition.LoadBalancerClientPresent @ConditionalOnBean (types: org.springframework.cloud.client.loadbalancer.LoadBalancerClient; SearchStrategy: all) did not find any beans of type org.springframework.cloud.client.loadbalancer.LoadBalancerClient; NestedCondition on LoadBalancerBeanPostProcessorAutoConfiguration.OnAnyLoadBalancerImplementationPresentCondition.ReactiveLoadBalancerFactoryPresent @ConditionalOnBean (types: org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancer$Factory; SearchStrategy: all) did not find any beans of type org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancer$Factory
2022-08-29 20:11:49.271 DEBUG 17185 --- [           main] o.s.cloud.context.scope.GenericScope     : Generating bean factory id from names: [applicationAvailability, applicationTaskExecutor, availabilityProbesHealthEndpointGroupsPostProcessor, beansEndpoint, cachesEndpoint, cachesEndpointWebExtension, classLoaderMetrics, clientConnectorCustomizer, commonsFeatures, compositeCompatibilityVerifier, compositeDiscoveryClient, conditionsReportEndpoint, configDataContextRefresher, configurationApplication, configurationPropertiesBeans, configurationPropertiesRebinder, configurationPropertiesReportEndpoint, configurationPropertiesReportEndpointWebExtension, connPoolFactory, connectionFactory, controllerEndpointDiscoverer, controllerEndpointHandlerMapping, controllerExposeExcludePropertyEndpointFilter, defaultCodecCustomizer, defaultPartHttpMessageReaderCustomizer, defaultWebClientExchangeTagsProvider, defaultsBindHandlerAdvisor, discoveryClientHealthIndicator, discoveryCompositeHealthContributor, diskSpaceHealthIndicator, diskSpaceMetrics, dispatcherHandlerMappingDescriptionProvider, dumpEndpoint, endpointCachingOperationInvokerAdvisor, endpointMediaTypes, endpointOperationParameterMapper, environmentEndpoint, environmentEndpointWebExtension, environmentManager, errorAttributes, errorWebExceptionHandler, exchangeStrategiesCustomizer, featuresEndpoint, fileDescriptorMetrics, forceAutoProxyCreatorToUseClassProxying, handlerFunctionAdapter, healthContributorRegistry, healthEndpoint, healthEndpointGroups, healthEndpointGroupsBeanPostProcessor, healthEndpointWebFluxHandlerMapping, healthHttpCodeStatusMapper, healthStatusAggregator, heapDumpWebEndpoint, httpHandler, inetUtils, inetUtilsProperties, infoEndpoint, jacksonCodecCustomizer, jacksonObjectMapper, jacksonObjectMapperBuilder, jsonComponentModule, jsonMixinModule, jvmGcMetrics, jvmHeapPressureMetrics, jvmMemoryMetrics, jvmThreadMetrics, kubernetesClient, kubernetesClientConfig, kubernetesHealthIndicator, kubernetesInfoContributor, kubernetesPodUtils, lifecycleProcessor, livenessStateHealthIndicator, loadBalancerClientsDefaultsMappingsProvider, localeContextResolver, logbackMetrics, loggersEndpoint, loggingRebinder, management.endpoint.configprops-org.springframework.boot.actuate.autoconfigure.context.properties.ConfigurationPropertiesReportEndpointProperties, management.endpoint.env-org.springframework.boot.actuate.autoconfigure.env.EnvironmentEndpointProperties, management.endpoint.health-org.springframework.boot.actuate.autoconfigure.health.HealthEndpointProperties, management.endpoint.logfile-org.springframework.boot.actuate.autoconfigure.logging.LogFileWebEndpointProperties, management.endpoints.web-org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties, management.endpoints.web.cors-org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties, management.health.diskspace-org.springframework.boot.actuate.autoconfigure.system.DiskSpaceHealthIndicatorProperties, management.info-org.springframework.boot.actuate.autoconfigure.info.InfoContributorProperties, management.metrics-org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties, management.metrics.export.simple-org.springframework.boot.actuate.autoconfigure.metrics.export.simple.SimpleProperties, management.server-org.springframework.boot.actuate.autoconfigure.web.server.ManagementServerProperties, mappingsEndpoint, meterRegistryPostProcessor, metricsEndpoint, metricsHttpClientUriTagFilter, metricsHttpServerUriTagFilter, metricsWebClientCustomizer, micrometerClock, nettyReactiveWebServerFactory, nettyWebServerFactoryCustomizer, okHttpClientBuilder, okHttpClientFactory, org.springframework.boot.actuate.autoconfigure.audit.AuditEventsEndpointAutoConfiguration, org.springframework.boot.actuate.autoconfigure.availability.AvailabilityHealthContributorAutoConfiguration, org.springframework.boot.actuate.autoconfigure.availability.AvailabilityProbesAutoConfiguration, org.springframework.boot.actuate.autoconfigure.beans.BeansEndpointAutoConfiguration, org.springframework.boot.actuate.autoconfigure.cache.CachesEndpointAutoConfiguration, org.springframework.boot.actuate.autoconfigure.condition.ConditionsReportEndpointAutoConfiguration, org.springframework.boot.actuate.autoconfigure.context.properties.ConfigurationPropertiesReportEndpointAutoConfiguration, org.springframework.boot.actuate.autoconfigure.endpoint.EndpointAutoConfiguration, org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointAutoConfiguration, org.springframework.boot.actuate.autoconfigure.endpoint.web.reactive.WebFluxEndpointManagementContextConfiguration, org.springframework.boot.actuate.autoconfigure.env.EnvironmentEndpointAutoConfiguration, org.springframework.boot.actuate.autoconfigure.health.HealthContributorAutoConfiguration, org.springframework.boot.actuate.autoconfigure.health.HealthEndpointAutoConfiguration, org.springframework.boot.actuate.autoconfigure.health.HealthEndpointConfiguration, org.springframework.boot.actuate.autoconfigure.health.HealthEndpointReactiveWebExtensionConfiguration, org.springframework.boot.actuate.autoconfigure.health.HealthEndpointReactiveWebExtensionConfiguration$WebFluxAdditionalHealthEndpointPathsConfiguration, org.springframework.boot.actuate.autoconfigure.health.ReactiveHealthEndpointConfiguration, org.springframework.boot.actuate.autoconfigure.info.InfoContributorAutoConfiguration, org.springframework.boot.actuate.autoconfigure.info.InfoEndpointAutoConfiguration, org.springframework.boot.actuate.autoconfigure.logging.LogFileWebEndpointAutoConfiguration, org.springframework.boot.actuate.autoconfigure.logging.LoggersEndpointAutoConfiguration, org.springframework.boot.actuate.autoconfigure.management.HeapDumpWebEndpointAutoConfiguration, org.springframework.boot.actuate.autoconfigure.management.ThreadDumpEndpointAutoConfiguration, org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration, org.springframework.boot.actuate.autoconfigure.metrics.JvmMetricsAutoConfiguration, org.springframework.boot.actuate.autoconfigure.metrics.LogbackMetricsAutoConfiguration, org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration, org.springframework.boot.actuate.autoconfigure.metrics.MetricsEndpointAutoConfiguration, org.springframework.boot.actuate.autoconfigure.metrics.SystemMetricsAutoConfiguration, org.springframework.boot.actuate.autoconfigure.metrics.export.simple.SimpleMetricsExportAutoConfiguration, org.springframework.boot.actuate.autoconfigure.metrics.integration.IntegrationMetricsAutoConfiguration, org.springframework.boot.actuate.autoconfigure.metrics.startup.StartupTimeMetricsListenerAutoConfiguration, org.springframework.boot.actuate.autoconfigure.metrics.task.TaskExecutorMetricsAutoConfiguration, org.springframework.boot.actuate.autoconfigure.metrics.web.client.HttpClientMetricsAutoConfiguration, org.springframework.boot.actuate.autoconfigure.metrics.web.client.WebClientMetricsConfiguration, org.springframework.boot.actuate.autoconfigure.metrics.web.reactive.WebFluxMetricsAutoConfiguration, org.springframework.boot.actuate.autoconfigure.r2dbc.ConnectionFactoryHealthContributorAutoConfiguration, org.springframework.boot.actuate.autoconfigure.scheduling.ScheduledTasksEndpointAutoConfiguration, org.springframework.boot.actuate.autoconfigure.system.DiskSpaceHealthContributorAutoConfiguration, org.springframework.boot.actuate.autoconfigure.trace.http.HttpTraceEndpointAutoConfiguration, org.springframework.boot.actuate.autoconfigure.web.mappings.MappingsEndpointAutoConfiguration, org.springframework.boot.actuate.autoconfigure.web.mappings.MappingsEndpointAutoConfiguration$ReactiveWebConfiguration, org.springframework.boot.actuate.autoconfigure.web.reactive.ReactiveManagementContextAutoConfiguration, org.springframework.boot.actuate.autoconfigure.web.server.ManagementContextAutoConfiguration, org.springframework.boot.actuate.autoconfigure.web.server.ManagementContextAutoConfiguration$SameManagementContextConfiguration, org.springframework.boot.actuate.autoconfigure.web.server.ManagementContextAutoConfiguration$SameManagementContextConfiguration$EnableSameManagementContextConfiguration, org.springframework.boot.autoconfigure.AutoConfigurationPackages, org.springframework.boot.autoconfigure.aop.AopAutoConfiguration, org.springframework.boot.autoconfigure.aop.AopAutoConfiguration$ClassProxyingConfiguration, org.springframework.boot.autoconfigure.availability.ApplicationAvailabilityAutoConfiguration, org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration, org.springframework.boot.autoconfigure.context.LifecycleAutoConfiguration, org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration, org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration, org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration$DefaultCodecsConfiguration, org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration$JacksonCodecConfiguration, org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration, org.springframework.boot.autoconfigure.internalCachingMetadataReaderFactory, org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration, org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration$Jackson2ObjectMapperBuilderCustomizerConfiguration, org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration$JacksonObjectMapperBuilderConfiguration, org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration$JacksonObjectMapperConfiguration, org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration$ParameterNamesModuleConfiguration, org.springframework.boot.autoconfigure.netty.NettyAutoConfiguration, org.springframework.boot.autoconfigure.r2dbc.ConnectionFactoryConfigurations$GenericConfiguration, org.springframework.boot.autoconfigure.r2dbc.ConnectionFactoryConfigurations$PoolConfiguration, org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration, org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration, org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration, org.springframework.boot.autoconfigure.task.TaskSchedulingAutoConfiguration, org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration, org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration$NettyWebServerFactoryCustomizerConfiguration, org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration, org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration$AnnotationConfig, org.springframework.boot.autoconfigure.web.reactive.ReactiveMultipartAutoConfiguration, org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration, org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryConfiguration$EmbeddedNetty, org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration, org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration$EnableWebFluxConfiguration, org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration$WebFluxConfig, org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration$WelcomePageConfiguration, org.springframework.boot.autoconfigure.web.reactive.WebSessionIdResolverAutoConfiguration, org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration, org.springframework.boot.autoconfigure.web.reactive.function.client.ClientHttpConnectorAutoConfiguration, org.springframework.boot.autoconfigure.web.reactive.function.client.ClientHttpConnectorConfiguration$ReactorNetty, org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration, org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration$WebClientCodecsConfiguration, org.springframework.boot.context.internalConfigurationPropertiesBinder, org.springframework.boot.context.internalConfigurationPropertiesBinderFactory, org.springframework.boot.context.properties.BoundConfigurationProperties, org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor, org.springframework.boot.context.properties.EnableConfigurationPropertiesRegistrar.methodValidationExcludeFilter, org.springframework.boot.sql.init.dependency.DatabaseInitializationDependencyConfigurer$DependsOnDatabaseInitializationPostProcessor, org.springframework.boot.test.mock.mockito.MockitoPostProcessor, org.springframework.boot.test.mock.mockito.MockitoPostProcessor$SpyPostProcessor, org.springframework.cloud.autoconfigure.ConfigurationPropertiesRebinderAutoConfiguration, org.springframework.cloud.autoconfigure.LifecycleMvcEndpointAutoConfiguration, org.springframework.cloud.autoconfigure.PauseResumeEndpointsConfiguration, org.springframework.cloud.autoconfigure.RefreshAutoConfiguration, org.springframework.cloud.autoconfigure.RefreshAutoConfiguration$RefreshScopeBeanDefinitionEnhancer, org.springframework.cloud.autoconfigure.RefreshEndpointAutoConfiguration, org.springframework.cloud.autoconfigure.RefreshEndpointAutoConfiguration$RefreshEndpointConfiguration, org.springframework.cloud.autoconfigure.RestartEndpointWithoutIntegrationConfiguration, org.springframework.cloud.client.CommonsClientAutoConfiguration, org.springframework.cloud.client.CommonsClientAutoConfiguration$ActuatorConfiguration, org.springframework.cloud.client.CommonsClientAutoConfiguration$DiscoveryLoadBalancerConfiguration, org.springframework.cloud.client.ReactiveCommonsClientAutoConfiguration, org.springframework.cloud.client.ReactiveCommonsClientAutoConfiguration$ReactiveDiscoveryLoadBalancerConfiguration, org.springframework.cloud.client.discovery.composite.CompositeDiscoveryClientAutoConfiguration, org.springframework.cloud.client.discovery.composite.reactive.ReactiveCompositeDiscoveryClientAutoConfiguration, org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClientAutoConfiguration, org.springframework.cloud.client.discovery.simple.reactive.SimpleReactiveDiscoveryClientAutoConfiguration, org.springframework.cloud.client.discovery.simple.reactive.SimpleReactiveDiscoveryClientAutoConfiguration$HealthConfiguration, org.springframework.cloud.client.loadbalancer.LoadBalancerDefaultMappingsProviderAutoConfiguration, org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationAutoConfiguration, org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationConfiguration, org.springframework.cloud.client.serviceregistry.ServiceRegistryAutoConfiguration, org.springframework.cloud.commons.config.CommonsConfigAutoConfiguration, org.springframework.cloud.commons.httpclient.HttpClientConfiguration, org.springframework.cloud.commons.httpclient.HttpClientConfiguration$OkHttpClientConfiguration, org.springframework.cloud.commons.util.UtilAutoConfiguration, org.springframework.cloud.configuration.CompatibilityVerifierAutoConfiguration, org.springframework.cloud.kubernetes.KubernetesAutoConfiguration, org.springframework.cloud.kubernetes.KubernetesAutoConfiguration$KubernetesActuatorConfiguration, org.springframework.cloud.kubernetes.config.reload.ConfigReloadAutoConfiguration, org.springframework.context.annotation.internalAutowiredAnnotationProcessor, org.springframework.context.annotation.internalCommonAnnotationProcessor, org.springframework.context.annotation.internalConfigurationAnnotationProcessor, org.springframework.context.event.internalEventListenerFactory, org.springframework.context.event.internalEventListenerProcessor, parameterNamesModule, pathMappedEndpoints, pingHealthContributor, processorMetrics, propertiesMeterFilter, propertySourcesPlaceholderConfigurer, r2dbcHealthContributor, reactiveCommonsFeatures, reactiveCompositeDiscoveryClient, reactiveDiscoveryClients, reactiveHealthContributorRegistry, reactiveHealthEndpointWebExtension, reactiveWebChildContextFactory, reactiveWebServerFactoryCustomizer, reactorClientHttpConnector, reactorServerResourceFactory, readinessStateHealthIndicator, refreshEndpoint, refreshEventListener, refreshScope, refreshScopeHealthIndicator, requestMappingHandlerAdapter, requestMappingHandlerMapping, resourceHandlerMapping, resourceUrlProvider, responseBodyResultHandler, responseEntityResultHandler, responseStatusExceptionHandler, routerFunctionMapping, scheduledBeanLazyInitializationExcludeFilter, scheduledTasksEndpoint, server-org.springframework.boot.autoconfigure.web.ServerProperties, serverCodecConfigurer, serverResponseResultHandler, simpleConfig, simpleDiscoveryClient, simpleDiscoveryProperties, simpleHandlerAdapter, simpleMeterRegistry, simpleReactiveDiscoveryClient, simpleReactiveDiscoveryClientHealthIndicator, simpleReactiveDiscoveryProperties, spring.cloud.compatibility-verifier-org.springframework.cloud.configuration.CompatibilityVerifierProperties, spring.cloud.discovery.client.health-indicator-org.springframework.cloud.client.discovery.health.DiscoveryClientHealthIndicatorProperties, spring.cloud.kubernetes.client-org.springframework.cloud.kubernetes.KubernetesClientProperties, spring.cloud.kubernetes.reload-org.springframework.cloud.kubernetes.config.reload.ConfigReloadProperties, spring.cloud.refresh-org.springframework.cloud.autoconfigure.RefreshAutoConfiguration$RefreshProperties, spring.cloud.service-registry.auto-registration-org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationProperties, spring.codec-org.springframework.boot.autoconfigure.codec.CodecProperties, spring.info-org.springframework.boot.autoconfigure.info.ProjectInfoProperties, spring.jackson-org.springframework.boot.autoconfigure.jackson.JacksonProperties, spring.lifecycle-org.springframework.boot.autoconfigure.context.LifecycleProperties, spring.netty-org.springframework.boot.autoconfigure.netty.NettyProperties, spring.r2dbc-org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties, spring.sql.init-org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties, spring.task.execution-org.springframework.boot.autoconfigure.task.TaskExecutionProperties, spring.task.scheduling-org.springframework.boot.autoconfigure.task.TaskSchedulingProperties, spring.web-org.springframework.boot.autoconfigure.web.WebProperties, spring.webflux-org.springframework.boot.autoconfigure.web.reactive.WebFluxProperties, spring.webflux.multipart-org.springframework.boot.autoconfigure.web.reactive.ReactiveMultipartProperties, springBootVersionVerifier, standardJacksonObjectMapperBuilderCustomizer, startupTimeMetrics, taskExecutorBuilder, taskSchedulerBuilder, uptimeMetrics, viewResolutionResultHandler, webClientBuilder, webEndpointDiscoverer, webEndpointPathMapper, webEndpointReactiveHandlerMapping, webExposeExcludePropertyEndpointFilter, webFluxAdapterRegistry, webFluxContentTypeResolver, webFluxConversionService, webFluxTagsProvider, webFluxValidator, webFluxWebSocketHandlerAdapter, webHandler, webServerFactoryCustomizerBeanPostProcessor, webSessionIdResolver, webSessionManager, webfluxMetrics, welcomePageRouterFunctionMapping]
2022-08-29 20:11:49.273  INFO 17185 --- [           main] o.s.cloud.context.scope.GenericScope     : BeanFactory id=270c11e3-e142-362c-8b0e-34603330601c
2022-08-29 20:11:49.764 TRACE 17185 --- [           main] o.s.cloud.commons.util.InetUtils         : Testing interface: br-fc606ba7953c
2022-08-29 20:11:49.765 TRACE 17185 --- [           main] o.s.cloud.commons.util.InetUtils         : Found non-loopback interface: br-fc606ba7953c
2022-08-29 20:11:49.765 TRACE 17185 --- [           main] o.s.cloud.commons.util.InetUtils         : Testing interface: br-8ab75c133cd0
2022-08-29 20:11:49.765 TRACE 17185 --- [           main] o.s.cloud.commons.util.InetUtils         : Found non-loopback interface: br-8ab75c133cd0
2022-08-29 20:11:49.765 TRACE 17185 --- [           main] o.s.cloud.commons.util.InetUtils         : Testing interface: enp3s0
2022-08-29 20:11:49.765 TRACE 17185 --- [           main] o.s.cloud.commons.util.InetUtils         : Found non-loopback interface: enp3s0
2022-08-29 20:11:49.765 TRACE 17185 --- [           main] o.s.cloud.commons.util.InetUtils         : Testing interface: lo
2022-08-29 20:11:49.942  INFO 17185 --- [           main] o.s.b.a.e.web.EndpointLinksResolver      : Exposing 15 endpoint(s) beneath base path '/actuator'
2022-08-29 20:11:50.093 TRACE 17185 --- [           main] o.s.cloud.commons.util.InetUtils         : Testing interface: br-fc606ba7953c
2022-08-29 20:11:50.094 TRACE 17185 --- [           main] o.s.cloud.commons.util.InetUtils         : Found non-loopback interface: br-fc606ba7953c
2022-08-29 20:11:50.094 TRACE 17185 --- [           main] o.s.cloud.commons.util.InetUtils         : Testing interface: br-8ab75c133cd0
2022-08-29 20:11:50.094 TRACE 17185 --- [           main] o.s.cloud.commons.util.InetUtils         : Found non-loopback interface: br-8ab75c133cd0
2022-08-29 20:11:50.094 TRACE 17185 --- [           main] o.s.cloud.commons.util.InetUtils         : Testing interface: enp3s0
2022-08-29 20:11:50.094 TRACE 17185 --- [           main] o.s.cloud.commons.util.InetUtils         : Found non-loopback interface: enp3s0
2022-08-29 20:11:50.094 TRACE 17185 --- [           main] o.s.cloud.commons.util.InetUtils         : Testing interface: lo
2022-08-29 20:11:50.127 DEBUG 17185 --- [           main] o.s.c.c.SpringBootVersionVerifier        : Version found in Boot manifest [2.7.2]
2022-08-29 20:11:50.127 DEBUG 17185 --- [           main] o.s.c.c.SpringBootVersionVerifier        : Version found in Boot manifest [2.7.2]
2022-08-29 20:11:50.127 DEBUG 17185 --- [           main] o.s.c.c.CompositeCompatibilityVerifier   : All conditions are passing
2022-08-29 20:11:50.154  INFO 17185 --- [           main] c.b.c.ConfigurationApplicationTests      : Started ConfigurationApplicationTests in 1.992 seconds (JVM running for 2.657)
the message is: null
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.506 s - in com.bsoft.configuration.ConfigurationApplicationTests
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] 
[INFO] --- maven-jar-plugin:3.2.2:jar (default-jar) @ configuration ---
[INFO] Building jar: /home/bvpelt/Develop/monitoring/configuration/target/configuration-0.0.1-SNAPSHOT.jar
[INFO] 
[INFO] --- spring-boot-maven-plugin:2.7.2:repackage (repackage) @ configuration ---
[INFO] Replacing main artifact with repackaged archive
[INFO] 
[INFO] >>> spring-boot-maven-plugin:2.7.2:build-image (default-cli) > package @ configuration >>>
[INFO] 
[INFO] --- maven-resources-plugin:3.2.0:resources (default-resources) @ configuration ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Using 'UTF-8' encoding to copy filtered properties files.
[INFO] Copying 1 resource
[INFO] Copying 6 resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.10.1:compile (default-compile) @ configuration ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /home/bvpelt/Develop/monitoring/configuration/target/classes
[INFO] 
[INFO] --- maven-resources-plugin:3.2.0:testResources (default-testResources) @ configuration ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Using 'UTF-8' encoding to copy filtered properties files.
[INFO] skip non existing resourceDirectory /home/bvpelt/Develop/monitoring/configuration/src/test/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.10.1:testCompile (default-testCompile) @ configuration ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /home/bvpelt/Develop/monitoring/configuration/target/test-classes
[INFO] 
[INFO] --- maven-surefire-plugin:2.22.2:test (default-test) @ configuration ---
[INFO] Skipping execution of surefire because it has already been run for this configuration
[INFO] 
[INFO] --- maven-jar-plugin:3.2.2:jar (default-jar) @ configuration ---
[INFO] Building jar: /home/bvpelt/Develop/monitoring/configuration/target/configuration-0.0.1-SNAPSHOT.jar
[INFO] 
[INFO] --- spring-boot-maven-plugin:2.7.2:repackage (repackage) @ configuration ---
[INFO] Replacing main artifact with repackaged archive
[INFO] 
[INFO] <<< spring-boot-maven-plugin:2.7.2:build-image (default-cli) < package @ configuration <<<
[INFO] 
[INFO] 
[INFO] --- spring-boot-maven-plugin:2.7.2:build-image (default-cli) @ configuration ---
[INFO] Building image 'docker.io/library/configuration:0.0.1-SNAPSHOT'
[INFO] 
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 0%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 2%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 3%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 4%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 5%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 6%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 7%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 9%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 10%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 11%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 12%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 12%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 14%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 15%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 16%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 17%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 18%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 21%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 23%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 27%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 32%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 36%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 41%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 44%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 48%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 53%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 56%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 58%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 61%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 63%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 65%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 69%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 74%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 78%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 81%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 83%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 86%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 88%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 91%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 94%
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 100%
[INFO]  > Pulled builder image 'paketobuildpacks/builder@sha256:b400a1f983e70280a3cb70d72929e279cee89f07e85170163556a4fad90dc34c'
[INFO]  > Pulling run image 'docker.io/paketobuildpacks/run:base-cnb' 8%
[INFO]  > Pulling run image 'docker.io/paketobuildpacks/run:base-cnb' 41%
[INFO]  > Pulling run image 'docker.io/paketobuildpacks/run:base-cnb' 100%
[INFO]  > Pulled run image 'paketobuildpacks/run@sha256:5f8002178a59387a10cee057224bb05160e09c068091d3a18c98df53e0e69c3d'
[INFO]  > Executing lifecycle version v0.14.1
[INFO]  > Using build cache volume 'pack-cache-e4bf2b8a9bff.build'
[INFO] 
[INFO]  > Running creator
[INFO]     [creator]     Restoring data for SBOM from previous image
[INFO]     [creator]     ===> DETECTING
[INFO]     [creator]     6 of 24 buildpacks participating
[INFO]     [creator]     paketo-buildpacks/ca-certificates   3.3.0
[INFO]     [creator]     paketo-buildpacks/bellsoft-liberica 9.6.1
[INFO]     [creator]     paketo-buildpacks/syft              1.17.0
[INFO]     [creator]     paketo-buildpacks/executable-jar    6.4.0
[INFO]     [creator]     paketo-buildpacks/dist-zip          5.3.0
[INFO]     [creator]     paketo-buildpacks/spring-boot       5.17.0
[INFO]     [creator]     ===> RESTORING
[INFO]     [creator]     Restoring metadata for "paketo-buildpacks/ca-certificates:helper" from app image
[INFO]     [creator]     Restoring metadata for "paketo-buildpacks/bellsoft-liberica:jre" from app image
[INFO]     [creator]     Restoring metadata for "paketo-buildpacks/bellsoft-liberica:helper" from app image
[INFO]     [creator]     Restoring metadata for "paketo-buildpacks/bellsoft-liberica:java-security-properties" from app image
[INFO]     [creator]     Restoring metadata for "paketo-buildpacks/syft:syft" from cache
[INFO]     [creator]     Restoring metadata for "paketo-buildpacks/spring-boot:web-application-type" from app image
[INFO]     [creator]     Restoring metadata for "paketo-buildpacks/spring-boot:helper" from app image
[INFO]     [creator]     Restoring metadata for "paketo-buildpacks/spring-boot:spring-cloud-bindings" from app image
[INFO]     [creator]     Restoring data for "paketo-buildpacks/syft:syft" from cache
[INFO]     [creator]     Restoring data for SBOM from cache
[INFO]     [creator]     ===> BUILDING
[INFO]     [creator]     
[INFO]     [creator]     Paketo CA Certificates Buildpack 3.3.0
[INFO]     [creator]       https://github.com/paketo-buildpacks/ca-certificates
[INFO]     [creator]       Launch Helper: Contributing to layer
[INFO]     [creator]         Creating /layers/paketo-buildpacks_ca-certificates/helper/exec.d/ca-certificates-helper
[INFO]     [creator]     
[INFO]     [creator]     Paketo BellSoft Liberica Buildpack 9.6.1
[INFO]     [creator]       https://github.com/paketo-buildpacks/bellsoft-liberica
[INFO]     [creator]       Build Configuration:
[INFO]     [creator]         $BP_JVM_JLINK_ARGS           --no-man-pages --no-header-files --strip-debug --compress=1  configure custom link arguments (--output must be omitted)
[INFO]     [creator]         $BP_JVM_JLINK_ENABLED        false                                                        enables running jlink tool to generate custom JRE
[INFO]     [creator]         $BP_JVM_TYPE                 JRE                                                          the JVM type - JDK or JRE
[INFO]     [creator]         $BP_JVM_VERSION              17.*                                                         the Java version
[INFO]     [creator]       Launch Configuration:
[INFO]     [creator]         $BPL_DEBUG_ENABLED           false                                                        enables Java remote debugging support
[INFO]     [creator]         $BPL_DEBUG_PORT              8000                                                         configure the remote debugging port
[INFO]     [creator]         $BPL_DEBUG_SUSPEND           false                                                        configure whether to suspend execution until a debugger has attached
[INFO]     [creator]         $BPL_HEAP_DUMP_PATH                                                                       write heap dumps on error to this path
[INFO]     [creator]         $BPL_JAVA_NMT_ENABLED        true                                                         enables Java Native Memory Tracking (NMT)
[INFO]     [creator]         $BPL_JAVA_NMT_LEVEL          summary                                                      configure level of NMT, summary or detail
[INFO]     [creator]         $BPL_JFR_ARGS                                                                             configure custom Java Flight Recording (JFR) arguments
[INFO]     [creator]         $BPL_JFR_ENABLED             false                                                        enables Java Flight Recording (JFR)
[INFO]     [creator]         $BPL_JMX_ENABLED             false                                                        enables Java Management Extensions (JMX)
[INFO]     [creator]         $BPL_JMX_PORT                5000                                                         configure the JMX port
[INFO]     [creator]         $BPL_JVM_HEAD_ROOM           0                                                            the headroom in memory calculation
[INFO]     [creator]         $BPL_JVM_LOADED_CLASS_COUNT  35% of classes                                               the number of loaded classes in memory calculation
[INFO]     [creator]         $BPL_JVM_THREAD_COUNT        250                                                          the number of threads in memory calculation
[INFO]     [creator]         $JAVA_TOOL_OPTIONS                                                                        the JVM launch flags
[INFO]     [creator]         Using Java version 17.* from BP_JVM_VERSION
[INFO]     [creator]       BellSoft Liberica JRE 17.0.4: Contributing to layer
[INFO]     [creator]         Downloading from https://github.com/bell-sw/Liberica/releases/download/17.0.4.1+1/bellsoft-jre17.0.4.1+1-linux-amd64.tar.gz
[INFO]     [creator]         Verifying checksum
[INFO]     [creator]         Expanding to /layers/paketo-buildpacks_bellsoft-liberica/jre
[INFO]     [creator]         Adding 127 container CA certificates to JVM truststore
[INFO]     [creator]         Writing env.launch/BPI_APPLICATION_PATH.default
[INFO]     [creator]         Writing env.launch/BPI_JVM_CACERTS.default
[INFO]     [creator]         Writing env.launch/BPI_JVM_CLASS_COUNT.default
[INFO]     [creator]         Writing env.launch/BPI_JVM_SECURITY_PROVIDERS.default
[INFO]     [creator]         Writing env.launch/JAVA_HOME.default
[INFO]     [creator]         Writing env.launch/JAVA_TOOL_OPTIONS.append
[INFO]     [creator]         Writing env.launch/JAVA_TOOL_OPTIONS.delim
[INFO]     [creator]         Writing env.launch/MALLOC_ARENA_MAX.default
[INFO]     [creator]       Launch Helper: Contributing to layer
[INFO]     [creator]         Creating /layers/paketo-buildpacks_bellsoft-liberica/helper/exec.d/active-processor-count
[INFO]     [creator]         Creating /layers/paketo-buildpacks_bellsoft-liberica/helper/exec.d/java-opts
[INFO]     [creator]         Creating /layers/paketo-buildpacks_bellsoft-liberica/helper/exec.d/jvm-heap
[INFO]     [creator]         Creating /layers/paketo-buildpacks_bellsoft-liberica/helper/exec.d/link-local-dns
[INFO]     [creator]         Creating /layers/paketo-buildpacks_bellsoft-liberica/helper/exec.d/memory-calculator
[INFO]     [creator]         Creating /layers/paketo-buildpacks_bellsoft-liberica/helper/exec.d/security-providers-configurer
[INFO]     [creator]         Creating /layers/paketo-buildpacks_bellsoft-liberica/helper/exec.d/jmx
[INFO]     [creator]         Creating /layers/paketo-buildpacks_bellsoft-liberica/helper/exec.d/jfr
[INFO]     [creator]         Creating /layers/paketo-buildpacks_bellsoft-liberica/helper/exec.d/security-providers-classpath-9
[INFO]     [creator]         Creating /layers/paketo-buildpacks_bellsoft-liberica/helper/exec.d/debug-9
[INFO]     [creator]         Creating /layers/paketo-buildpacks_bellsoft-liberica/helper/exec.d/nmt
[INFO]     [creator]         Creating /layers/paketo-buildpacks_bellsoft-liberica/helper/exec.d/openssl-certificate-loader
[INFO]     [creator]       Java Security Properties: Contributing to layer
[INFO]     [creator]         Writing env.launch/JAVA_SECURITY_PROPERTIES.default
[INFO]     [creator]         Writing env.launch/JAVA_TOOL_OPTIONS.append
[INFO]     [creator]         Writing env.launch/JAVA_TOOL_OPTIONS.delim
[INFO]     [creator]     
[INFO]     [creator]     Paketo Syft Buildpack 1.17.0
[INFO]     [creator]       https://github.com/paketo-buildpacks/syft
[INFO]     [creator]         Downloading from https://github.com/anchore/syft/releases/download/v0.54.0/syft_0.54.0_linux_amd64.tar.gz
[INFO]     [creator]         Verifying checksum
[INFO]     [creator]         Writing env.build/SYFT_CHECK_FOR_APP_UPDATE.default
[INFO]     [creator]     
[INFO]     [creator]     Paketo Executable JAR Buildpack 6.4.0
[INFO]     [creator]       https://github.com/paketo-buildpacks/executable-jar
[INFO]     [creator]       Class Path: Contributing to layer
[INFO]     [creator]         Writing env/CLASSPATH.delim
[INFO]     [creator]         Writing env/CLASSPATH.prepend
[INFO]     [creator]       Process types:
[INFO]     [creator]         executable-jar: java org.springframework.boot.loader.JarLauncher (direct)
[INFO]     [creator]         task:           java org.springframework.boot.loader.JarLauncher (direct)
[INFO]     [creator]         web:            java org.springframework.boot.loader.JarLauncher (direct)
[INFO]     [creator]     
[INFO]     [creator]     Paketo Spring Boot Buildpack 5.17.0
[INFO]     [creator]       https://github.com/paketo-buildpacks/spring-boot
[INFO]     [creator]       Build Configuration:
[INFO]     [creator]         $BP_SPRING_CLOUD_BINDINGS_DISABLED   false  whether to contribute Spring Boot cloud bindings support
[INFO]     [creator]       Launch Configuration:
[INFO]     [creator]         $BPL_SPRING_CLOUD_BINDINGS_DISABLED  false  whether to auto-configure Spring Boot environment properties from bindings
[INFO]     [creator]         $BPL_SPRING_CLOUD_BINDINGS_ENABLED   true   Deprecated - whether to auto-configure Spring Boot environment properties from bindings
[INFO]     [creator]       Creating slices from layers index
[INFO]     [creator]         dependencies (45.9 MB)
[INFO]     [creator]         spring-boot-loader (283.6 KB)
[INFO]     [creator]         snapshot-dependencies (0.0 B)
[INFO]     [creator]         application (44.9 KB)
[INFO]     [creator]       Launch Helper: Contributing to layer
[INFO]     [creator]         Creating /layers/paketo-buildpacks_spring-boot/helper/exec.d/spring-cloud-bindings
[INFO]     [creator]       Spring Cloud Bindings 1.10.0: Reusing cached layer
[INFO]     [creator]       Web Application Type: Contributing to layer
[INFO]     [creator]         Reactive web application detected
[INFO]     [creator]         Writing env.launch/BPL_JVM_THREAD_COUNT.default
[INFO]     [creator]       4 application slices
[INFO]     [creator]       Image labels:
[INFO]     [creator]         org.opencontainers.image.title
[INFO]     [creator]         org.opencontainers.image.version
[INFO]     [creator]         org.springframework.boot.version
[INFO]     [creator]     ===> EXPORTING
[INFO]     [creator]     Adding layer 'paketo-buildpacks/ca-certificates:helper'
[INFO]     [creator]     Adding layer 'paketo-buildpacks/bellsoft-liberica:helper'
[INFO]     [creator]     Reusing layer 'paketo-buildpacks/bellsoft-liberica:java-security-properties'
[INFO]     [creator]     Adding layer 'paketo-buildpacks/bellsoft-liberica:jre'
[INFO]     [creator]     Reusing layer 'paketo-buildpacks/executable-jar:classpath'
[INFO]     [creator]     Adding layer 'paketo-buildpacks/spring-boot:helper'
[INFO]     [creator]     Reusing layer 'paketo-buildpacks/spring-boot:spring-cloud-bindings'
[INFO]     [creator]     Reusing layer 'paketo-buildpacks/spring-boot:web-application-type'
[INFO]     [creator]     Adding layer 'launch.sbom'
[INFO]     [creator]     Reusing 3/5 app layer(s)
[INFO]     [creator]     Adding 2/5 app layer(s)
[INFO]     [creator]     Reusing layer 'launcher'
[INFO]     [creator]     Adding layer 'config'
[INFO]     [creator]     Reusing layer 'process-types'
[INFO]     [creator]     Adding label 'io.buildpacks.lifecycle.metadata'
[INFO]     [creator]     Adding label 'io.buildpacks.build.metadata'
[INFO]     [creator]     Adding label 'io.buildpacks.project.metadata'
[INFO]     [creator]     Adding label 'org.opencontainers.image.title'
[INFO]     [creator]     Adding label 'org.opencontainers.image.version'
[INFO]     [creator]     Adding label 'org.springframework.boot.version'
[INFO]     [creator]     Setting default process type 'web'
[INFO]     [creator]     Saving docker.io/library/configuration:0.0.1-SNAPSHOT...
[INFO]     [creator]     *** Images (9e2c9a490e54):
[INFO]     [creator]           docker.io/library/configuration:0.0.1-SNAPSHOT
[INFO]     [creator]     Adding cache layer 'paketo-buildpacks/syft:syft'
[INFO]     [creator]     Adding cache layer 'cache.sbom'
[INFO] 
[INFO] Successfully built image 'docker.io/library/configuration:0.0.1-SNAPSHOT'
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  02:32 min
[INFO] Finished at: 2022-08-29T20:14:01+02:00
[INFO] ------------------------------------------------------------------------
bvpelt@pluto:~/Develop/monitoring/configuration$ 
```