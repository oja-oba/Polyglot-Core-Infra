# Polyglot Monorepo: Phase 1 (Infrastructure & Containers)

This repository serves as a high-performance, secure monorepo demonstrating microservices architecture across three major runtimes: **Python**, **Node.js**, and **Java**.

## 🚀 Project Overview
The goal of this phase was to achieve **Environment Parity** and **Hardened Security** across different language stacks using a unified containerization strategy.

### **Core Tech Stack**
| Service | Runtime | Framework | Build Tool |
| :--- | :--- | :--- | :--- |
| **Python App** | Python 3.11.2 | FastAPI | pip (Targeted) |
| **Node App** | Node.js 20.x | Express | npm |
| **Java App** | Java 17 (JRE) | Native HttpServer | Maven (Fat JAR) |

---

## 🛡️ Security Standards
Every service in this monorepo follows industry best practices for container security:
* **Distroless Base Images:** We use `gcr.io/distroless` to minimize the attack surface by removing shells (bash/sh) and unnecessary package managers.
* **Non-Root Execution:** All containers run under the `nonroot` user (UID 65532), ensuring that even if a service is compromised, the attacker has no root privileges.
* **Multi-Stage Builds:** We separate the "Build" environment (compilers, SDKs) from the "Runtime" environment to keep images lightweight and secure.

---

## 📂 Directory Structure
```text
PolyProject/
├── docker-compose.yml       # Root Orchestrator
├── python-app/              # FastAPI + Pydantic v2
│   ├── Dockerfile           # 3.11-to-3.11 Sync
│   └── main.py              # Dynamic Version Reporting
├── node-app/                # Express.js
│   ├── Dockerfile           # Distroless Node 20
│   └── index.js
└── java-app/                # Maven-based Java
    ├── pom.xml              # Dependency Management
    ├── src/main/java/com/polyglot/Main.java
    └── Dockerfile           # Multi-stage Maven Build


## 🛠️ Local Development & Orchestration

To manage the polyglot stack efficiently, we use **Docker Compose** as the primary orchestrator. This ensures that all services—Python, Node.js, and Java—are built and run within a unified network using a single command.

### **Quick Start**
Ensure you are in the root directory of the project (`PolyProject/`) and execute the following:

```bash
# 1. Stop and remove any existing project containers
docker compose down

# 2. Build and launch the entire stack in detached mode
docker compose up --build -d

# 3. View real-time logs for all services
docker compose logs -f

### **Service Access & Network Mapping**

Each service is configured to run as a **non-root** user on a hardened **Distroless** base image. While all services listen on port `8000` internally, they are mapped to unique external ports on the host machine to avoid network conflicts during local development.

| Service | Runtime | External Port (Host) | Internal Port (Container) | Health Check Endpoint |
| :--- | :--- | :--- | :--- | :--- |
| **Python** | Python 3.11.2 | `8000` | `8000` | `http://localhost:8000/health` |
| **Node.js** | Node.js 20.x | `8001` | `8000` | `http://localhost:8001/health` |
| **Java** | Java 17 | `8002` | `8000` | `http://localhost:8002/health` |



#### **Network Configuration Details**
* **Network Name:** `polyglot-network`
* **Driver:** `bridge`
* **DNS Resolution:** Services can communicate with each other using their service names defined in `docker-compose.yml` (e.g., the Node app can reach the Python app at `http://python-app:8000`).