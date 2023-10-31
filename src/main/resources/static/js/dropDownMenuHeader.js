function toggleDropdown(liElement) {
    // Находим элемент с классом "nav-item dropdown"
    var dropdownElement = document.querySelector(".nav-item.dropdown");
    var dropdownElement2 = document.querySelector(".dropdown-menu");

    // Проверяем наличие элемента
    if (dropdownElement) {
        // Переключаем классы
        dropdownElement.classList.toggle("show");
        dropdownElement2.classList.toggle("show");
    }
}