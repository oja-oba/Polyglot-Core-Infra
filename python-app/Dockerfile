# --- Stage 1: The Builder (Force 3.11) ---
    FROM python:3.11-slim-bookworm AS builder
    WORKDIR /app
    COPY requirements.txt .
    
    # Install dependencies into a dedicated 'deps' folder
    RUN pip install --no-cache-dir --target=/app/deps -r requirements.txt
    
    # --- Stage 2: The Runtime (The actual 3.11 environment in your logs) ---
    FROM gcr.io/distroless/python3-debian12:nonroot
    WORKDIR /app
    
    # Copy the dependencies and your code
    COPY --from=builder /app/deps ./deps
    COPY main.py .
    
    # Point Python specifically to the deps folder
    ENV PYTHONPATH=/app/deps
    
    EXPOSE 8000
    # Call the internal python3 binary
    ENTRYPOINT ["/usr/bin/python3", "-m", "uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]