// Establecer SOLO la fecha actual por defecto si no hay valor
document.addEventListener('DOMContentLoaded', function () {
  const fechaInput = document.getElementById('fechaIngreso');

  if (!fechaInput.value) {
    const today = new Date();

    // Convertir a formato yyyy-MM-dd
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const day = String(today.getDate()).padStart(2, '0');

    fechaInput.value = `${year}-${month}-${day}`;
  }
});
