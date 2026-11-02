async function callApi(method, url, body) {
    const out = document.getElementById('output');
    try {
        const res = await fetch(url, {
            method,
            headers: { 'Content-Type': 'application/json' },
            body: body ? JSON.stringify(body) : undefined
        });
        out.textContent = JSON.stringify(await res.json(), null, 2);
    } catch (e) {
        out.textContent = 'Error: ' + e.message;
    }
}

fetch('/api/health')
    .then(r => r.json())
    .then(d => document.getElementById('status').textContent = '✅ ' + d.system + ' is ' + d.status)
    .catch(() => document.getElementById('status').textContent = '❌ Server unreachable');