# Edge

This service is the entry point to internal services

## References
- [spring boot maven plugin](https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/htmlsingle/)

## Proxy
Usage: http://localhost:9999/proxy -> http://localhost:8080/customers

## Graphql
Using graphql
- http://localhost:9999/graphiql?path=/graphql

example query using interactive graphql console
```graphql
query {
  customers {
    id
  }
}
```

```graphql
query {
  customers {
    id,
    name
  }
}
```

## Create docker image

```shell
bvpelt@pluto:~/Develop/monitoring/edge$ mvn clean package spring-boot:build-image
[INFO] Scanning for projects...
[INFO] 
[INFO] ---------------------------< com.bsoft:edge >---------------------------
[INFO] Building edge 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:3.2.0:clean (default-clean) @ edge ---
[INFO] Deleting /home/bvpelt/Develop/monitoring/edge/target
[INFO] 
[INFO] --- maven-resources-plugin:3.2.0:resources (default-resources) @ edge ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Using 'UTF-8' encoding to copy filtered properties files.
[INFO] Copying 1 resource
[INFO] Copying 1 resource
[INFO] 
[INFO] --- maven-compiler-plugin:3.10.1:compile (default-compile) @ edge ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /home/bvpelt/Develop/monitoring/edge/target/classes
[INFO] 
[INFO] --- maven-resources-plugin:3.2.0:testResources (default-testResources) @ edge ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Using 'UTF-8' encoding to copy filtered properties files.
[INFO] skip non existing resourceDirectory /home/bvpelt/Develop/monitoring/edge/src/test/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.10.1:testCompile (default-testCompile) @ edge ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /home/bvpelt/Develop/monitoring/edge/target/test-classes
[INFO] 
[INFO] --- maven-surefire-plugin:2.22.2:test (default-test) @ edge ---
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.bsoft.edge.EdgeApplicationTests
19:45:32.111 [main] DEBUG org.springframework.test.context.BootstrapUtils - Instantiating CacheAwareContextLoaderDelegate from class [org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate]
19:45:32.117 [main] DEBUG org.springframework.test.context.BootstrapUtils - Instantiating BootstrapContext using constructor [public org.springframework.test.context.support.DefaultBootstrapContext(java.lang.Class,org.springframework.test.context.CacheAwareContextLoaderDelegate)]
19:45:32.137 [main] DEBUG org.springframework.test.context.BootstrapUtils - Instantiating TestContextBootstrapper for test class [com.bsoft.edge.EdgeApplicationTests] from class [org.springframework.boot.test.context.SpringBootTestContextBootstrapper]
19:45:32.143 [main] INFO org.springframework.boot.test.context.SpringBootTestContextBootstrapper - Neither @ContextConfiguration nor @ContextHierarchy found for test class [com.bsoft.edge.EdgeApplicationTests], using SpringBootContextLoader
19:45:32.145 [main] DEBUG org.springframework.test.context.support.AbstractContextLoader - Did not detect default resource location for test class [com.bsoft.edge.EdgeApplicationTests]: class path resource [com/bsoft/edge/EdgeApplicationTests-context.xml] does not exist
19:45:32.145 [main] DEBUG org.springframework.test.context.support.AbstractContextLoader - Did not detect default resource location for test class [com.bsoft.edge.EdgeApplicationTests]: class path resource [com/bsoft/edge/EdgeApplicationTestsContext.groovy] does not exist
19:45:32.145 [main] INFO org.springframework.test.context.support.AbstractContextLoader - Could not detect default resource locations for test class [com.bsoft.edge.EdgeApplicationTests]: no resource found for suffixes {-context.xml, Context.groovy}.
19:45:32.146 [main] INFO org.springframework.test.context.support.AnnotationConfigContextLoaderUtils - Could not detect default configuration classes for test class [com.bsoft.edge.EdgeApplicationTests]: EdgeApplicationTests does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
19:45:32.283 [main] DEBUG org.springframework.test.context.support.ActiveProfilesUtils - Could not find an 'annotation declaring class' for annotation type [org.springframework.test.context.ActiveProfiles] and class [com.bsoft.edge.EdgeApplicationTests]
19:45:32.390 [main] DEBUG org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider - Identified candidate component class: file [/home/bvpelt/Develop/monitoring/edge/target/classes/com/bsoft/edge/EdgeApplication.class]
19:45:32.391 [main] INFO org.springframework.boot.test.context.SpringBootTestContextBootstrapper - Found @SpringBootConfiguration com.bsoft.edge.EdgeApplication for test class com.bsoft.edge.EdgeApplicationTests
19:45:32.482 [main] DEBUG org.springframework.boot.test.context.SpringBootTestContextBootstrapper - @TestExecutionListeners is not present for class [com.bsoft.edge.EdgeApplicationTests]: using defaults.
19:45:32.483 [main] INFO org.springframework.boot.test.context.SpringBootTestContextBootstrapper - Loaded default TestExecutionListener class names from location [META-INF/spring.factories]: [org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener, org.springframework.boot.test.mock.mockito.ResetMocksTestExecutionListener, org.springframework.boot.test.autoconfigure.restdocs.RestDocsTestExecutionListener, org.springframework.boot.test.autoconfigure.web.client.MockRestServiceServerResetTestExecutionListener, org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrintOnlyOnFailureTestExecutionListener, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverTestExecutionListener, org.springframework.boot.test.autoconfigure.webservices.client.MockWebServiceServerTestExecutionListener, org.springframework.test.context.web.ServletTestExecutionListener, org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener, org.springframework.test.context.event.ApplicationEventsTestExecutionListener, org.springframework.test.context.support.DependencyInjectionTestExecutionListener, org.springframework.test.context.support.DirtiesContextTestExecutionListener, org.springframework.test.context.transaction.TransactionalTestExecutionListener, org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener, org.springframework.test.context.event.EventPublishingTestExecutionListener]
19:45:32.491 [main] DEBUG org.springframework.boot.test.context.SpringBootTestContextBootstrapper - Skipping candidate TestExecutionListener [org.springframework.test.context.web.ServletTestExecutionListener] due to a missing dependency. Specify custom listener classes or make the default listener classes and their required dependencies available. Offending class: [javax/servlet/ServletContext]
19:45:32.492 [main] DEBUG org.springframework.boot.test.context.SpringBootTestContextBootstrapper - Skipping candidate TestExecutionListener [org.springframework.test.context.transaction.TransactionalTestExecutionListener] due to a missing dependency. Specify custom listener classes or make the default listener classes and their required dependencies available. Offending class: [org/springframework/transaction/interceptor/TransactionAttributeSource]
19:45:32.492 [main] DEBUG org.springframework.boot.test.context.SpringBootTestContextBootstrapper - Skipping candidate TestExecutionListener [org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener] due to a missing dependency. Specify custom listener classes or make the default listener classes and their required dependencies available. Offending class: [org/springframework/transaction/interceptor/TransactionAttribute]
19:45:32.492 [main] INFO org.springframework.boot.test.context.SpringBootTestContextBootstrapper - Using TestExecutionListeners: [org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener@67b9b51a, org.springframework.test.context.event.ApplicationEventsTestExecutionListener@1205bd62, org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener@7ef27d7f, org.springframework.boot.test.autoconfigure.SpringBootDependencyInjectionTestExecutionListener@490caf5f, org.springframework.test.context.support.DirtiesContextTestExecutionListener@6337c201, org.springframework.test.context.event.EventPublishingTestExecutionListener@5c669da8, org.springframework.boot.test.mock.mockito.ResetMocksTestExecutionListener@31920ade, org.springframework.boot.test.autoconfigure.restdocs.RestDocsTestExecutionListener@1d483de4, org.springframework.boot.test.autoconfigure.web.client.MockRestServiceServerResetTestExecutionListener@4032d386, org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrintOnlyOnFailureTestExecutionListener@28d18df5, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverTestExecutionListener@934b6cb, org.springframework.boot.test.autoconfigure.webservices.client.MockWebServiceServerTestExecutionListener@55cf0d14]
19:45:32.493 [main] DEBUG org.springframework.test.context.support.AbstractDirtiesContextTestExecutionListener - Before test class: context [DefaultTestContext@27d5a580 testClass = EdgeApplicationTests, testInstance = [null], testMethod = [null], testException = [null], mergedContextConfiguration = [ReactiveWebMergedContextConfiguration@198d6542 testClass = EdgeApplicationTests, locations = '{}', classes = '{class com.bsoft.edge.EdgeApplication}', contextInitializerClasses = '[]', activeProfiles = '{}', propertySourceLocations = '{}', propertySourceProperties = '{org.springframework.boot.test.context.SpringBootTestContextBootstrapper=true}', contextCustomizers = set[org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@672872e1, org.springframework.boot.test.graphql.tester.HttpGraphQlTesterContextCustomizer@55740540, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@50caa560, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@0, org.springframework.boot.test.web.client.TestRestTemplateContextCustomizer@1356d4d4, org.springframework.boot.test.web.reactive.server.WebTestClientContextCustomizer@6497b078, org.springframework.boot.test.autoconfigure.actuate.metrics.MetricsExportContextCustomizerFactory$DisableMetricExportContextCustomizer@5f683daf, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizerFactory$Customizer@55b53d44, org.springframework.boot.test.context.SpringBootTestArgs@1, org.springframework.boot.test.context.SpringBootTestWebEnvironment@6f1fba17], contextLoader = 'org.springframework.boot.test.context.SpringBootContextLoader', parent = [null]], attributes = map[[empty]]], class annotated with @DirtiesContext [false] with mode [null].
19:45:32.512 [main] DEBUG org.springframework.test.context.support.DependencyInjectionTestExecutionListener - Performing dependency injection for test context [[DefaultTestContext@27d5a580 testClass = EdgeApplicationTests, testInstance = com.bsoft.edge.EdgeApplicationTests@48d61b48, testMethod = [null], testException = [null], mergedContextConfiguration = [ReactiveWebMergedContextConfiguration@198d6542 testClass = EdgeApplicationTests, locations = '{}', classes = '{class com.bsoft.edge.EdgeApplication}', contextInitializerClasses = '[]', activeProfiles = '{}', propertySourceLocations = '{}', propertySourceProperties = '{org.springframework.boot.test.context.SpringBootTestContextBootstrapper=true}', contextCustomizers = set[org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@672872e1, org.springframework.boot.test.graphql.tester.HttpGraphQlTesterContextCustomizer@55740540, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@50caa560, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@0, org.springframework.boot.test.web.client.TestRestTemplateContextCustomizer@1356d4d4, org.springframework.boot.test.web.reactive.server.WebTestClientContextCustomizer@6497b078, org.springframework.boot.test.autoconfigure.actuate.metrics.MetricsExportContextCustomizerFactory$DisableMetricExportContextCustomizer@5f683daf, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizerFactory$Customizer@55b53d44, org.springframework.boot.test.context.SpringBootTestArgs@1, org.springframework.boot.test.context.SpringBootTestWebEnvironment@6f1fba17], contextLoader = 'org.springframework.boot.test.context.SpringBootContextLoader', parent = [null]], attributes = map['org.springframework.test.context.event.ApplicationEventsTestExecutionListener.recordApplicationEvents' -> false]]].

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.7.2)

2022-08-15 19:45:32.818  INFO 21832 --- [           main] com.bsoft.edge.EdgeApplicationTests      : Starting EdgeApplicationTests using Java 18.0.2 on pluto with PID 21832 (started by bvpelt in /home/bvpelt/Develop/monitoring/edge)
2022-08-15 19:45:32.819  INFO 21832 --- [           main] com.bsoft.edge.EdgeApplicationTests      : No active profile set, falling back to 1 default profile: "default"
2022-08-15 19:45:33.754  INFO 21832 --- [           main] o.s.cloud.context.scope.GenericScope     : BeanFactory id=d4b848e3-c697-35a4-973e-ef8586bc2211
2022-08-15 19:45:35.443  INFO 21832 --- [           main] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [After]
2022-08-15 19:45:35.443  INFO 21832 --- [           main] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [Before]
2022-08-15 19:45:35.443  INFO 21832 --- [           main] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [Between]
2022-08-15 19:45:35.443  INFO 21832 --- [           main] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [Cookie]
2022-08-15 19:45:35.443  INFO 21832 --- [           main] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [Header]
2022-08-15 19:45:35.443  INFO 21832 --- [           main] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [Host]
2022-08-15 19:45:35.443  INFO 21832 --- [           main] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [Method]
2022-08-15 19:45:35.443  INFO 21832 --- [           main] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [Path]
2022-08-15 19:45:35.443  INFO 21832 --- [           main] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [Query]
2022-08-15 19:45:35.443  INFO 21832 --- [           main] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [ReadBody]
2022-08-15 19:45:35.443  INFO 21832 --- [           main] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [RemoteAddr]
2022-08-15 19:45:35.443  INFO 21832 --- [           main] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [XForwardedRemoteAddr]
2022-08-15 19:45:35.443  INFO 21832 --- [           main] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [Weight]
2022-08-15 19:45:35.443  INFO 21832 --- [           main] o.s.c.g.r.RouteDefinitionRouteLocator    : Loaded RoutePredicateFactory [CloudFoundryRouteService]
2022-08-15 19:45:35.852  INFO 21832 --- [           main] .b.a.g.r.GraphQlWebFluxAutoConfiguration : GraphQL endpoint HTTP POST /graphql
2022-08-15 19:45:36.034  INFO 21832 --- [           main] o.s.b.a.e.web.EndpointLinksResolver      : Exposing 16 endpoint(s) beneath base path '/actuator'
2022-08-15 19:45:36.169  INFO 21832 --- [           main] com.bsoft.edge.EdgeApplicationTests      : Started EdgeApplicationTests in 3.64 seconds (JVM running for 4.821)
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 4.569 s - in com.bsoft.edge.EdgeApplicationTests
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] 
[INFO] --- maven-jar-plugin:3.2.2:jar (default-jar) @ edge ---
[INFO] Building jar: /home/bvpelt/Develop/monitoring/edge/target/edge-0.0.1-SNAPSHOT.jar
[INFO] 
[INFO] --- spring-boot-maven-plugin:2.7.2:repackage (repackage) @ edge ---
[INFO] Replacing main artifact with repackaged archive
[INFO] 
[INFO] >>> spring-boot-maven-plugin:2.7.2:build-image (default-cli) > package @ edge >>>
[INFO] 
[INFO] --- maven-resources-plugin:3.2.0:resources (default-resources) @ edge ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Using 'UTF-8' encoding to copy filtered properties files.
[INFO] Copying 1 resource
[INFO] Copying 1 resource
[INFO] 
[INFO] --- maven-compiler-plugin:3.10.1:compile (default-compile) @ edge ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /home/bvpelt/Develop/monitoring/edge/target/classes
[INFO] 
[INFO] --- maven-resources-plugin:3.2.0:testResources (default-testResources) @ edge ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Using 'UTF-8' encoding to copy filtered properties files.
[INFO] skip non existing resourceDirectory /home/bvpelt/Develop/monitoring/edge/src/test/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.10.1:testCompile (default-testCompile) @ edge ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /home/bvpelt/Develop/monitoring/edge/target/test-classes
[INFO] 
[INFO] --- maven-surefire-plugin:2.22.2:test (default-test) @ edge ---
[INFO] Skipping execution of surefire because it has already been run for this configuration
[INFO] 
[INFO] --- maven-jar-plugin:3.2.2:jar (default-jar) @ edge ---
[INFO] Building jar: /home/bvpelt/Develop/monitoring/edge/target/edge-0.0.1-SNAPSHOT.jar
[INFO] 
[INFO] --- spring-boot-maven-plugin:2.7.2:repackage (repackage) @ edge ---
[INFO] Replacing main artifact with repackaged archive
[INFO] 
[INFO] <<< spring-boot-maven-plugin:2.7.2:build-image (default-cli) < package @ edge <<<
[INFO] 
[INFO] 
[INFO] --- spring-boot-maven-plugin:2.7.2:build-image (default-cli) @ edge ---
[INFO] Building image 'docker.io/library/edge:0.0.1-SNAPSHOT'
[INFO] 
[INFO]  > Pulling builder image 'docker.io/paketobuildpacks/builder:base' 100%
[INFO]  > Pulled builder image 'paketobuildpacks/builder@sha256:4957046aa29d333ba2d37659a3bf43354766089312089d3991050f7f4b652c18'
[INFO]  > Pulling run image 'docker.io/paketobuildpacks/run:base-cnb' 100%
[INFO]  > Pulled run image 'paketobuildpacks/run@sha256:caad76fd4c158792e3000f94cc00f9c6c4fe9476557e08a83fdee8fc2caac2bf'
[INFO]  > Executing lifecycle version v0.14.1
[INFO]  > Using build cache volume 'pack-cache-bb3d757b6cb3.build'
[INFO] 
[INFO]  > Running creator
[INFO]     [creator]     Previous image with name "docker.io/library/edge:0.0.1-SNAPSHOT" not found
[INFO]     [creator]     ===> DETECTING
[INFO]     [creator]     6 of 24 buildpacks participating
[INFO]     [creator]     paketo-buildpacks/ca-certificates   3.2.5
[INFO]     [creator]     paketo-buildpacks/bellsoft-liberica 9.4.1
[INFO]     [creator]     paketo-buildpacks/syft              1.16.0
[INFO]     [creator]     paketo-buildpacks/executable-jar    6.2.5
[INFO]     [creator]     paketo-buildpacks/dist-zip          5.2.5
[INFO]     [creator]     paketo-buildpacks/spring-boot       5.15.0
[INFO]     [creator]     ===> RESTORING
[INFO]     [creator]     ===> BUILDING
[INFO]     [creator]     
[INFO]     [creator]     Paketo CA Certificates Buildpack 3.2.5
[INFO]     [creator]       https://github.com/paketo-buildpacks/ca-certificates
[INFO]     [creator]       Launch Helper: Contributing to layer
[INFO]     [creator]         Creating /layers/paketo-buildpacks_ca-certificates/helper/exec.d/ca-certificates-helper
[INFO]     [creator]     
[INFO]     [creator]     Paketo BellSoft Liberica Buildpack 9.4.1
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
[INFO]     [creator]         Downloading from https://github.com/bell-sw/Liberica/releases/download/17.0.4+8/bellsoft-jre17.0.4+8-linux-amd64.tar.gz
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
[INFO]     [creator]     Paketo Syft Buildpack 1.16.0
[INFO]     [creator]       https://github.com/paketo-buildpacks/syft
[INFO]     [creator]         Downloading from https://github.com/anchore/syft/releases/download/v0.53.4/syft_0.53.4_linux_amd64.tar.gz
[INFO]     [creator]         Verifying checksum
[INFO]     [creator]         Writing env.build/SYFT_CHECK_FOR_APP_UPDATE.default
[INFO]     [creator]     
[INFO]     [creator]     Paketo Executable JAR Buildpack 6.2.5
[INFO]     [creator]       https://github.com/paketo-buildpacks/executable-jar
[INFO]     [creator]       Class Path: Contributing to layer
[INFO]     [creator]         Writing env/CLASSPATH.delim
[INFO]     [creator]         Writing env/CLASSPATH.prepend
[INFO]     [creator]       Process types:
[INFO]     [creator]         executable-jar: java org.springframework.boot.loader.JarLauncher (direct)
[INFO]     [creator]         task:           java org.springframework.boot.loader.JarLauncher (direct)
[INFO]     [creator]         web:            java org.springframework.boot.loader.JarLauncher (direct)
[INFO]     [creator]     
[INFO]     [creator]     Paketo Spring Boot Buildpack 5.15.0
[INFO]     [creator]       https://github.com/paketo-buildpacks/spring-boot
[INFO]     [creator]       Build Configuration:
[INFO]     [creator]         $BP_SPRING_CLOUD_BINDINGS_DISABLED   false  whether to contribute Spring Boot cloud bindings support
[INFO]     [creator]       Launch Configuration:
[INFO]     [creator]         $BPL_SPRING_CLOUD_BINDINGS_DISABLED  false  whether to auto-configure Spring Boot environment properties from bindings
[INFO]     [creator]         $BPL_SPRING_CLOUD_BINDINGS_ENABLED   true   Deprecated - whether to auto-configure Spring Boot environment properties from bindings
[INFO]     [creator]       Creating slices from layers index
[INFO]     [creator]         dependencies (35.9 MB)
[INFO]     [creator]         spring-boot-loader (283.6 KB)
[INFO]     [creator]         snapshot-dependencies (0.0 B)
[INFO]     [creator]         application (52.0 KB)
[INFO]     [creator]       Launch Helper: Contributing to layer
[INFO]     [creator]         Creating /layers/paketo-buildpacks_spring-boot/helper/exec.d/spring-cloud-bindings
[INFO]     [creator]       Spring Cloud Bindings 1.10.0: Contributing to layer
[INFO]     [creator]         Downloading from https://repo.spring.io/release/org/springframework/cloud/spring-cloud-bindings/1.10.0/spring-cloud-bindings-1.10.0.jar
[INFO]     [creator]         Verifying checksum
[INFO]     [creator]         Copying to /layers/paketo-buildpacks_spring-boot/spring-cloud-bindings
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
[INFO]     [creator]     Adding layer 'paketo-buildpacks/bellsoft-liberica:java-security-properties'
[INFO]     [creator]     Adding layer 'paketo-buildpacks/bellsoft-liberica:jre'
[INFO]     [creator]     Adding layer 'paketo-buildpacks/executable-jar:classpath'
[INFO]     [creator]     Adding layer 'paketo-buildpacks/spring-boot:helper'
[INFO]     [creator]     Adding layer 'paketo-buildpacks/spring-boot:spring-cloud-bindings'
[INFO]     [creator]     Adding layer 'paketo-buildpacks/spring-boot:web-application-type'
[INFO]     [creator]     Adding layer 'launch.sbom'
[INFO]     [creator]     Adding 5/5 app layer(s)
[INFO]     [creator]     Adding layer 'launcher'
[INFO]     [creator]     Adding layer 'config'
[INFO]     [creator]     Adding layer 'process-types'
[INFO]     [creator]     Adding label 'io.buildpacks.lifecycle.metadata'
[INFO]     [creator]     Adding label 'io.buildpacks.build.metadata'
[INFO]     [creator]     Adding label 'io.buildpacks.project.metadata'
[INFO]     [creator]     Adding label 'org.opencontainers.image.title'
[INFO]     [creator]     Adding label 'org.opencontainers.image.version'
[INFO]     [creator]     Adding label 'org.springframework.boot.version'
[INFO]     [creator]     Setting default process type 'web'
[INFO]     [creator]     Saving docker.io/library/edge:0.0.1-SNAPSHOT...
[INFO]     [creator]     *** Images (ee4acdbd1bef):
[INFO]     [creator]           docker.io/library/edge:0.0.1-SNAPSHOT
[INFO]     [creator]     Adding cache layer 'paketo-buildpacks/syft:syft'
[INFO]     [creator]     Adding cache layer 'cache.sbom'
[INFO] 
[INFO] Successfully built image 'docker.io/library/edge:0.0.1-SNAPSHOT'
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  01:20 min
[INFO] Finished at: 2022-08-15T19:46:43+02:00
[INFO] ------------------------------------------------------------------------
```


## Upload image to minikube

```shell
bvpelt@pluto:~/Develop/monitoring/edge$ minikube image load docker.io/library/edge:0.0.1-SNAPSHOT
bvpelt@pluto:~/Develop/monitoring/edge$ 
```

## Use image in minikube
```shell
bvpelt@pluto:~/Develop/monitoring/edge$ kubectl create deployment edge-service --image=docker.io/library/edge:0.0.1-SNAPSHOT
deployment.apps/edge-service created
bvpelt@pluto:~/Develop/monitoring/edge$ 
bvpelt@pluto:~/Develop/monitoring/edge$ kubectl expose deployment edge-service --type=NodePort --port=9999
service/edge-service exposed
bvpelt@pluto:~/Develop/monitoring/edge$ 
bvpelt@pluto:~/Develop/monitoring/edge$ minikube service edge-service
|-----------|--------------|-------------|---------------------------|
| NAMESPACE |     NAME     | TARGET PORT |            URL            |
|-----------|--------------|-------------|---------------------------|
| default   | edge-service |        9999 | http://192.168.49.2:30185 |
|-----------|--------------|-------------|---------------------------|
🎉  Opening service default/edge-service in default browser...
bvpelt@pluto:~/Develop/monitoring/edge$ libva error: vaGetDriverNameByIndex() failed with unknown libva error, driver_name = (null)
Opening in existing browser session.
```

Open graphql:
```shell
http://192.168.49.2:30185/graphiql?path=/graphql 
```