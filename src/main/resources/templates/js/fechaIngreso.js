 // Establecer fecha y hora actual por defecto solo si no hay valor
  document.addEventListener('DOMContentLoaded', function() {
    const fechaInput = document.getElementById('fechaIngreso');
    if (!fechaInput.value) {
      const now = new Date();
      // Ajustar para el desfase de zona horaria
      const timezoneOffset = now.getTimezoneOffset() * 60000;
      const localISOTime = new Date(now - timezoneOffset).toISOString().slice(0, 16);
      fechaInput.value = localISOTime;
    }
  });