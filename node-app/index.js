const express = require('express');
const os = require('os');
const app = express();
const port = 8000;

app.get('/health', (req, res) => {
    res.json({
        status: "healthy",
        container_id: os.hostname(),
        // This dynamically reports the Node version
        runtime: `Node.js ${process.version}` 
    });
});

app.get('/security', (req, res) => {
    res.json({
        user_id: process.getuid(),
        group_id: process.getgid(),
        is_root: process.getuid() === 0,
        current_directory: process.cwd()
    });
});

app.listen(port, '0.0.0.0', () => {
    console.log(`Node app listening at http://0.0.0.0:${port}`);
});