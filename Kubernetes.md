# Kubernetes

From the [Kubernetes tutorial](https://kubernetes.io/docs/tutorials/hello-minikube/)

# Steps

## Create a deployment
The kubernetes unit of deployment is a pod. Usually a pod only contains one docker iamge.
```shell
kubectl create deployment hello-node --image=registry.k8s.io/echoserver:1.4
```

## Create a service
By default, the Pod is only accessible by its internal IP address within the 
Kubernetes cluster. To make the pod accessible from outside the Kubernetes 
virtual network, you have to expose the Pod as a Kubernetes Service.
```shell
kubectl expose deployment hello-node --type=LoadBalancer --port=8080
```
To find the ip address of the service
```shell
minikube service hello-node
```
This opens the browser with ip address and port number