// frases-motivadoras.js
const frasesMotivadoras = (function() {
    // Frases financieras locales como respaldo
    const frasesLocales = [
        {
            texto: "No ahorres lo que te queda después de gastar, gasta lo que te queda después de ahorrar.",
            autor: "Warren Buffett"
        },
        {
            texto: "La riqueza no se mide por el dinero que ganas, sino por cómo lo administras.",
            autor: "Robert Kiyosaki"
        },
        {
            texto: "Pequeños gastos conscientes hoy, grandes oportunidades mañana.",
            autor: "Gestor Finanzas"
        },
        {
            texto: "El presupuesto no es una restricción, es un plan para tu libertad financiera.",
            autor: "Gestor Finanzas"
        },
        {
            texto: "Invierte en tu conocimiento, es el activo que nadie puede quitarte.",
            autor: "Benjamin Franklin"
        },
        {
            texto: "La disciplina financiera hoy, es la tranquilidad del mañana.",
            autor: "Gestor Finanzas"
        },
        {
            texto: "El control de tus finanzas es el control de tu vida.",
            autor: "Gestor Finanzas"
        },
        {
            texto: "Cada euro ahorrado es un soldado que lucha por tu libertad.",
            autor: "Gestor Finanzas"
        }
    ];

    // Configuración
    const config = {
        apiUrl: 'https://zenquotes.io/api/random',
        intervaloCambio: 30000, // 30 segundos
        elementoContenedor: 'fraseMotivadora'
    };

    // Mostrar loading
    function mostrarLoading() {
        const contenedor = document.getElementById(config.elementoContenedor);
        if (contenedor) {
            contenedor.innerHTML = `
                <div class="text-center">
                    <div class="spinner-border spinner-border-sm text-light" role="status">
                        <span class="visually-hidden">Cargando...</span>
                    </div>
                    <div class="mt-2">Cargando frase inspiradora...</div>
                </div>
            `;
        }
    }

    // Mostrar frase en el HTML
    function mostrarFrase(texto, autor) {
        const contenedor = document.getElementById(config.elementoContenedor);
        if (contenedor) {
            contenedor.innerHTML = `
                <div class="frase-text">"${texto}"</div>
                <div class="frase-autor">- ${autor}</div>
            `;
        }
    }

    // Obtener frase aleatoria local
    function obtenerFraseLocal() {
        const fraseAleatoria = frasesLocales[Math.floor(Math.random() * frasesLocales.length)];
        return {
            texto: fraseAleatoria.texto,
            autor: fraseAleatoria.autor
        };
    }

    // Cargar frase de la API
    function cargarFrase() {
        mostrarLoading();

        // Intentar con la API externa primero
        fetch(config.apiUrl)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error en la respuesta de la API');
                }
                return response.json();
            })
            .then(data => {
                if (data && data[0] && data[0].q) {
                    mostrarFrase(data[0].q, data[0].a);
                } else {
                    throw new Error('Estructura de datos inválida');
                }
            })
            .catch(error => {
                console.warn('Error con API externa, usando frases locales:', error);
                // Usar frases locales como respaldo
                const fraseLocal = obtenerFraseLocal();
                mostrarFrase(fraseLocal.texto, fraseLocal.autor);
            });
    }

    // Inicializar el sistema de frases
    function inicializar() {
        // Cargar primera frase
        cargarFrase();

        // Configurar intervalo para cambiar frases automáticamente
        setInterval(cargarFrase, config.intervaloCambio);

        console.log('Sistema de frases motivadoras inicializado');
    }

    // API pública
    return {
        inicializar: inicializar,
        cargarFrase: cargarFrase,
        obtenerFraseLocal: obtenerFraseLocal
    };
})();