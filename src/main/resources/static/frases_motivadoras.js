function cargarFrase() {
    fetch("https://api.adviceslip.com/advice")
        .then(response => response.json())
        .then(data => {
            const frase = data.slip.advice;
            document.getElementById("fraseTexto").textContent = `"${frase}"`;
            document.getElementById("fraseAutor").textContent = "- Consejero Aleatorio";
        })
        .catch(error => console.error("Error al obtener el consejo:", error));
}

document.addEventListener("DOMContentLoaded", () => {
    cargarFrase();
    document.getElementById("btnRefreshFrase").addEventListener("click", cargarFrase);
});
