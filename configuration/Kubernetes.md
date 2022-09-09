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

### Scaling
From module 5

Define desired number of replicas
```shell
kubectl scale deployments/kubernetes-bootcamp --replicas=4
kubectl get deployments
kubectl get pods -o wide
kubectl describe deployments/kkubectl describe deployments/kubernetes-bootcampubernetes-bootcamp
```

format kubectl scale deployments/<name> --replicas=x

### Deployment
From module 6

Change image of a deployment, for instance with bugfix, or new version
```shell
kubectl set image deployments/kubernetes-bootcamp kubernetes-bootcamp=jocatalin/kubernetes-bootcamp:v2

# check rollout status
kubectl rollout status deployments/kubernetes-bootcamp
```
Version 2 of kubernets-bootcamp is set.

### Deployment using configuration files
From Configuring a Kubernetes Microservice

```shell
kubectl apply -f kubernetes.yaml
```

content of kubernetes.yaml

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: system-deployment
  labels:
    app: system
spec:
  selector:
    matchLabels:
      app: system
  template:
    metadata:
      labels:
        app: system
    spec:
      containers:
        - name: system-container
          image: system:1.0-SNAPSHOT
          ports:
            - containerPort: 9080
          readinessProbe:
            httpGet:
              path: /health/ready
              port: 9080
            initialDelaySeconds: 30
            periodSeconds: 10
            timeoutSeconds: 3
            failureThreshold: 1
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: inventory-deployment
  labels:
    app: inventory
spec:
  selector:
    matchLabels:
      app: inventory
  template:
    metadata:
      labels:
        app: inventory
    spec:
      containers:
        - name: inventory-container
          image: inventory:1.0-SNAPSHOT
          ports:
            - containerPort: 9080
          readinessProbe:
            httpGet:
              path: /health/ready
              port: 9080
            initialDelaySeconds: 30
            periodSeconds: 10
            timeoutSeconds: 3
            failureThreshold: 1
---
apiVersion: v1
kind: Service
metadata:
  name: system-service
spec:
  type: NodePort
  selector:
    app: system
  ports:
    - protocol: TCP
      port: 9080
      targetPort: 9080
      nodePort: 31000
---
apiVersion: v1
kind: Service
metadata:
  name: inventory-service
spec:
  type: NodePort
  selector:
    app: inventory
  ports:
    - protocol: TCP
      port: 9080
      targetPort: 9080
      nodePort: 32000
```
check if pod is ready
```shell
kubectl wait --for=condition=ready pod -l app=inventory
kubectl wait --for=condition=ready pod -l app=system
```

### Configmap
Create a configmap with key-value pairs

```shell
kubectl create configmap sys-app-name --from-literal name=my-system
```
Creates configmap sys-app-name with key value pair:
- name: my-system

Create a secret configmap
```shell
kubectl create secret generic sys-app-credentials --from-literal username=bob --from-literal password=bobpwd
kubectl describe secret sys-app-credentials
```
Creates secret sys-app-credentials configmap with 

### Replace kubernetes deployment
After changing kubernetes.yaml and/or the packages used, replace existing kubernetes configuration
```shell
kubectl replace --force -f kubernetes.yaml
```