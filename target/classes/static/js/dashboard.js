async function loadDashboard() {
    try {
        const [donationsRes, requestsRes, matchesRes] = await Promise.all([
            fetch('/api/donations'),
            fetch('/api/requests'),
            fetch('/api/matches')
        ]);
        
        const donations = await donationsRes.json();
        const requests = await requestsRes.json();
        const matches = await matchesRes.json();
        
        document.getElementById('totalDonations').textContent = donations.length;
        document.getElementById('totalRequests').textContent = requests.length;
        document.getElementById('totalMatches').textContent = matches.length;
        
        const donationsList = document.getElementById('donationsList');
        donationsList.innerHTML = donations.slice(0, 5).map(d => `
            <div class="item-card">
                <div class="item-header">
                    <span class="item-title">${d.itemName}</span>
                   
                </div>
                <div class="item-details">
                    ${d.category} - Quantity: ${d.quantity} - ${d.location}
                </div>
            </div>
        `).join('');
        
        const requestsList = document.getElementById('requestsList');
        requestsList.innerHTML = requests.slice(0, 5).map(r => `
            <div class="item-card">
                <div class="item-header">
                    <span class="item-title">${r.itemName}</span>
                    <span class="item-badge badge-${r.urgency.toLowerCase()}">${r.urgency}</span>
                </div>
                <div class="item-details">
                    ${r.category} - Quantity: ${r.quantity} - ${r.location}
                </div>
            </div>
        `).join('');
    } catch (error) {
        console.error('Error:', error);
    }
}

loadDashboard();
