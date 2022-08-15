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
