#!/usr/bin/env bash
set -euo pipefail

#Script to install various k8s security monitoring engines for testing 

#minikube start --driver=docker --cpus=4 --memory=8192

echo "Checking Kubectl installation"
echo "*****************************"
if ! kubectl version; then
  echo "ERROR: Kubectl not found."
  exit 1
fi

echo ""
echo "Checking Helm installation"
echo "**************************"
if ! helm version; then
  echo "ERROR: Kubectl not found."
  exit 1
fi

echo ""
echo "Checking cluster status"
echo "***********************"
if ! kubectl cluster-info; then
  echo "ERROR: Cannot connect to cluster."
  exit 1
fi
kubectl get nodes -o wide

echo ""
echo "Adding Helm repos"
echo "*****************"
helm repo add cilium https://helm.cilium.io
helm repo add falcosecurity https://falcosecurity.github.io/charts
helm repo add aqua https://aquasecurity.github.io/helm-charts
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo add grafana https://grafana.github.io/helm-charts
helm repo update

echo ""
echo "Installing Tetragon"
echo "*******************"
helm upgrade --install tetragon cilium/tetragon \
  --namespace tetragon --create-namespace

echo ""
echo "Installing Falco"
echo "****************"
helm upgrade --install falco falcosecurity/falco \
  --namespace falco --create-namespace \
  --set falcosidekick.enabled=true \
  --set falcosidekick.webui.enabled=true \
  --set driver.kind=modern_ebpf \
  --set "falcoctl.config.artifact.install.refs={falco-rules:5,falco-incubating-rules:5,falco-sandbox-rules:5}" \
  --set "falco.rules_files={/etc/falco/rules.d,/etc/falco/falco_rules.yaml,/etc/falco/falco-incubating_rules.yaml,/etc/falco/falco-sandbox_rules.yaml}"

echo ""
echo "Installing Tracee"
echo "*****************"
helm upgrade --install tracee aqua/tracee \
  --namespace tracee --create-namespace

echo ""
echo "Installing Prometheus/Grafana"
echo "*****************************"
helm upgrade --install kube-prom-stack prometheus-community/kube-prometheus-stack \
  --namespace monitoring --create-namespace \
  --set grafana.adminPassword=admin \
  --set grafana.service.type=NodePort \
  --set grafana.service.nodePort=30080 \
  --set prometheus.service.type=NodePort \
  --set prometheus.service.nodePort=30090 \
  --set prometheus.prometheusSpec.retention=15d

echo ""
echo "Adding test pod"
echo "***************"
kubectl create namespace test || true
kubectl run test --image=alpine:latest --namespace test --command -- sleep infinity || true

echo ""
echo "Waiting for pods to come up"
echo "***************************"
kubectl wait --for=condition=ready pod -l app.kubernetes.io/name=tetragon -n tetragon --timeout=120s 2>/dev/null || true
kubectl wait --for=condition=ready pod -l app.kubernetes.io/name=falco -n falco --timeout=120s 2>/dev/null || true
kubectl wait --for=condition=ready pod -l app.kubernetes.io/name=prometheus -n monitoring --timeout=120s 2>/dev/null || true
kubectl wait --for=condition=ready pod -l app.kubernetes.io/name=grafana -n monitoring --timeout=120s 2>/dev/null || true
kubectl wait --for=condition=ready pod/test -n test --timeout=60s 2>/dev/null || true

echo ""
echo "Configuring network"
echo "*******************"
kubectl port-forward -n falco svc/falco-falcosidekick-ui 2802:2802 > /dev/null 2>&1 &
kubectl port-forward -n falco svc/falco-falcosidekick-ui 3366:3366 > /dev/null 2>&1 &
kubectl port-forward -n monitoring svc/kube-prom-stack-grafana 3000:80 > /dev/null 2>&1 &
kubectl port-forward -n monitoring svc/kube-prom-stack-kube-prome-prometheus 9090:9090 > /dev/null 2>&1 &
kubectl port-forward -n monitoring svc/kube-prom-stack-kube-prome-alertmanager 9093:9093 > /dev/null 2>&1 &
echo "Grafana:           http://localhost:3000 (admin:admin)"
echo "Prometheus:        http://localhost:9090"
echo "Alertmanager:      http://localhost:9093"
echo "Falco Sidekick UI: http://localhost:2802 (admin:admin)"

echo ""
echo ""
echo "Setup complete :)"
echo "*View Falco logs manually with 'kubectl logs -n falco -l app.kubernetes.io/name=falco -f'"
echo "*View Tracee logs manually with 'kubectl logs -f daemonset/tracee -n tracee -f'"
echo "*View Tetragon logs manually with 'kubectl logs -n tetragon -l app.kubernetes.io/name=tetragon -f'"
echo " or 'kubectl logs -n tetragon -l app.kubernetes.io/name=tetragon -c export-stdout -f | tetra getevents -o compact'"
