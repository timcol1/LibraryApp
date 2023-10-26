function checkUserCode() {
    let userInput = document.getElementById("code");
    let secretCode = document.getElementById("secret_code");
    // Получить значение, введенное пользователем
    let userValue = userInput.value;
    let secretCodeValue = secretCode.value;

    // Выполнить проверку, например, проверить, что введенное значение не пустое
    if (userValue.trim() === "" || userValue !== secretCodeValue) {
        alert("Ваше значення невірне!");
    } else {
        alert("Введенное значение: " + userValue);
        // Здесь вы можете выполнить другие проверки или обработку данных
    }
}