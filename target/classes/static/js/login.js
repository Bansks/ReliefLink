document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    
    try {
        const response = await fetch('/login', {
            method: 'POST',
            body: formData
        });
        const result = await response.json();
        
        const messageDiv = document.getElementById('message');
        if (result.success) {
            messageDiv.className = 'message success';
            messageDiv.textContent = 'Login successful!';
            setTimeout(() => {
                window.location.href = '/dashboard';
            }, 500);
        } else {
            messageDiv.className = 'message error';
            messageDiv.textContent = result.message || 'Login failed';
        }
    } catch (error) {
        console.error('Error:', error);
    }
});
