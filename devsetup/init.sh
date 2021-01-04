#!/usr/bin/env bash

USERNAME="admin"
PASSWORD="123qwe"

docker run -d --rm --name gtr-couchbase -p 8091-8094:8091-8094 -p 11210:11210 couchbase:6.0.1

sleep 20

docker exec -it gtr-couchbase /opt/couchbase/bin/couchbase-cli cluster-init -c 127.0.0.1 \
  --cluster-username=${USERNAME} \
  --cluster-password=${PASSWORD} \
  --cluster-port=8091 \
  --cluster-ramsize=512 \
  --services=data,query,index

docker exec -it gtr-couchbase /opt/couchbase/bin/couchbase-cli bucket-create -c 127.0.0.1:8091 \
  --bucket=customers \
  --bucket-type=couchbase \
  --bucket-ramsize=128 \
  --bucket-replica=1 \
  -u ${USERNAME} -p ${PASSWORD}

docker exec -it gtr-couchbase /opt/couchbase/bin/couchbase-cli user-manage -c 127.0.0.1:8091 \
  -u ${USERNAME} \
  -p ${PASSWORD} \
  --set --rbac-username customers --rbac-password customers \
  --rbac-name "customers" \
  --roles admin \
  --auth-domain local

  docker exec -it gtr-couchbase /opt/couchbase/bin/couchbase-cli bucket-create -c 127.0.0.1:8091 \
  --bucket=orders \
  --bucket-type=couchbase \
  --bucket-ramsize=128 \
  --bucket-replica=1 \
  -u ${USERNAME} -p ${PASSWORD}

docker exec -it gtr-couchbase /opt/couchbase/bin/couchbase-cli user-manage -c 127.0.0.1:8091 \
  -u ${USERNAME} \
  -p ${PASSWORD} \
  --set --rbac-username orders --rbac-password orders \
  --rbac-name "orders" \
  --roles admin \
  --auth-domain local

docker exec -it gtr-couchbase /opt/couchbase/bin/couchbase-cli bucket-create -c 127.0.0.1:8091 \
  --bucket=books \
  --bucket-type=couchbase \
  --bucket-ramsize=128 \
  --bucket-replica=1 \
  -u ${USERNAME} -p ${PASSWORD}

docker exec -it gtr-couchbase /opt/couchbase/bin/couchbase-cli user-manage -c 127.0.0.1:8091 \
  -u ${USERNAME} \
  -p ${PASSWORD} \
  --set --rbac-username books --rbac-password 123qwe \
  --rbac-name "books" \
  --roles admin \
  --auth-domain local
