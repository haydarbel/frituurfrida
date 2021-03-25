// Dubbele submit vermijden:
document.querySelector("form").onsubmit = function() {
    this.querySelector("button").disabled = true; }