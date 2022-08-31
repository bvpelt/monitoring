# Kubernetes

See the [tutorial](https://kubernetes.io/docs/tutorials/)

## Usefull scripts

### Get POD_NAME
From module 2
```shell
export POD_NAME=$(kubectl get pods -o go-template --template '{{range .items}}{{.metadata.name}}{{"\n"}}{{end}}')
echo Name of the Pod: $POD_NAME
```

### Execute command in containter
From module 3
```shell
kubectl exec $POD_NAME -- env
```

### Start bash in a POD
From module 3
```shell
kubectl exec -ti $POD_NAME -- bash
```

### Get NODE_PORT
From module 4
```shell
export NODE_PORT=$(kubectl get services/kubernetes-bootcamp -o go-template='{{(index .spec.ports 0).nodePort}}')
echo NODE_PORT=$NODE_PORT
```

### Use service with NODE_PORT
From module 4
```shell
export NODE_PORT=$(kubectl get services/kubernetes-bootcamp -o go-template='{{(index .spec.ports 0).nodePort}}')
curl $(minikube ip):$NODE_PORT
```

### Add label to PODS
From module 4

Add label 'version=v1' to all specified pods
```shell
kubectl label pods $POD_NAME version=v1

### Using label to get PODS
kubectl get pods -l version=v1

### Delete service using specified label
kubectl delete service -l app=kubernetes-bootcamp
```