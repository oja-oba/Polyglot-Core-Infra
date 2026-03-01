import os
import socket
import platform
from fastapi import FastAPI

app = FastAPI(title="Polyglot-Core-Python")

@app.get("/health")
def health_check():
    # Crucial for K8s Liveness/Readiness probes
    return {
        "status": "healthy", 
        "container_id": socket.gethostname(),
        "runtime": f"Python {platform.python_version()}"
    }

@app.get("/security")
def security_check():

    return {
        "user_id": os.getuid(),
        "group_id": os.getgid(),
        "is_root": os.getuid() == 0,
        "current_directory": os.getcwd()
    }

@app.get("/compute")
def compute():
   
    # Performs a simple CPU-intensive task
    result = sum(i*i for i in range(10**6))
    return {"result": "computation_complete", "value": result}