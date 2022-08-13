# Edge

This service is the entry point to internal services

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