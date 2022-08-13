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

## Using kubernetes
Requirement is installing minikube [see minikube installation](https://www.linuxbuzz.com/how-to-install-minikube-on-ubuntu/)

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