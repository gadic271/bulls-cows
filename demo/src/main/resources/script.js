const API_BASE = 'http://localhost:5500/api/game';

const guessInput = document.getElementById('guessInput');
const guessBtn = document.getElementById('guessBtn');
const newGameBtn = document.getElementById('newGameBtn');
const historyDiv = document.getElementById('history');
const messageDiv = document.getElementById('message');

let history = [];

function addHistory(guess, bulls, cows, gameOver) {
    history.push({ guess, bulls, cows, gameOver });
    renderHistory();
}

function renderHistory() {
    historyDiv.innerHTML = '';
    history.forEach(entry => {
        const entryDiv = document.createElement('div');
        entryDiv.textContent = `${entry.guess} → Быки: ${entry.bulls}, Коровы: ${entry.cows}`;
        if (entry.gameOver) {
            entryDiv.style.fontWeight = 'bold';
            entryDiv.style.color = '#27ae60';
        }
        historyDiv.appendChild(entryDiv);
    });
    historyDiv.scrollTop = historyDiv.scrollHeight;
}

async function makeGuess() {
    const guess = guessInput.value.trim();
    if (!/^\d{4}$/.test(guess)) {
        messageDiv.textContent = 'Введите ровно 4 цифры';
        messageDiv.style.color = 'red';
        return;
    }
    messageDiv.textContent = '';

    try {
        const response = await fetch(`${API_BASE}/guess`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ guess })
        });
        if (!response.ok) throw new Error('Ошибка сервера');
        const data = await response.json();

        addHistory(guess, data.bulls, data.cows, data.gameOver);
        messageDiv.textContent = data.message;
        messageDiv.style.color = data.gameOver ? 'green' : '#2c3e50';

        if (data.gameOver) {
            guessBtn.disabled = true;
        } else {
            guessInput.value = '';
            guessInput.focus();
        }
    } catch (error) {
        messageDiv.textContent = 'Ошибка соединения с сервером';
        console.error(error);
    }
}

async function newGame() {
    try {
        await fetch(`${API_BASE}/new`, { method: 'POST' });
        history = [];
        renderHistory();
        messageDiv.textContent = 'Новая игра начата! Загадано новое число.';
        messageDiv.style.color = '#2c3e50';
        guessBtn.disabled = false;
        guessInput.value = '';
        guessInput.focus();
    } catch (error) {
        messageDiv.textContent = 'Не удалось начать новую игру';
    }
}

guessBtn.addEventListener('click', makeGuess);
newGameBtn.addEventListener('click', newGame);
guessInput.addEventListener('keypress', (e) => {
    if (e.key === 'Enter') makeGuess();
});

newGame();