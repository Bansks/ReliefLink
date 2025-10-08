async function loadMatches() {
    try {
        const response = await fetch('/api/matches');
        const matches = await response.json();
        
        const matchesList = document.getElementById('matchesList');
        matchesList.innerHTML = matches.map(m => `
            <div class="item-card">
                <div class="item-header">
                    <span class="item-title">${m.itemName}</span>
                    <span class="item-badge badge-matched">Matched</span>
                </div>
                <div class="item-details">
                    Donor: ${m.donorName} → Requester: ${m.requesterName}<br>
                    ${m.category} - Quantity: ${m.quantity} - ${m.location}
                </div>
            </div>
        `).join('');
    } catch (error) {
        console.error('Error:', error);
    }
}

document.getElementById('findMatchesBtn').addEventListener('click', async () => {
    try {
        await fetch('/api/matches/find', { method: 'POST' });
        loadMatches();
    } catch (error) {
        console.error('Error:', error);
    }
});

loadMatches();
