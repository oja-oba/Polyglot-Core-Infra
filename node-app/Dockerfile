# --- Stage 1: The Builder ---
    FROM node:20-bookworm-slim AS builder
    WORKDIR /app
    
    # Copy package files and install dependencies
    COPY package*.json ./
    RUN npm install --only=production
    
    # --- Stage 2: The Runtime (Distroless) ---
    FROM gcr.io/distroless/nodejs20-debian12:nonroot
    WORKDIR /app
    
    # In Node, we copy the node_modules folder. 
    COPY --from=builder /app/node_modules ./node_modules
    COPY index.js .
    
    EXPOSE 8000
    
    # The Entrypoint Theory applied to Node:
    # 1. /nodejs/bin/node is the absolute path to the binary in this image.
    # 2. index.js is the file we want to execute.
    ENTRYPOINT ["/nodejs/bin/node", "index.js"]