const API = "/api";
let usuarioActual = null;

function mostrarMensaje(texto) {
    document.getElementById("mensaje").textContent = texto;
}

// ---------- Registro ----------
document.getElementById("formRegistro").addEventListener("submit", async (e) => {
    e.preventDefault();
    const usuario = {
        nombreUsuario: document.getElementById("regNombre").value,
        emailUsuario: document.getElementById("regEmail").value,
        contraUsuario: document.getElementById("regContra").value
    };
    const res = await fetch(`${API}/usuarios`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(usuario)
    });
    if (res.ok) {
        mostrarMensaje("Cuenta creada. Ya puedes iniciar sesión.");
        e.target.reset();
    } else {
        mostrarMensaje("No se pudo registrar. Revisa los datos.");
    }
});

// ---------- Inicio de sesión ----------
document.getElementById("formLogin").addEventListener("submit", async (e) => {
    e.preventDefault();
    const datos = {
        emailUsuario: document.getElementById("logEmail").value,
        contraUsuario: document.getElementById("logContra").value
    };
    const res = await fetch(`${API}/usuarios/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(datos)
    });
    if (res.ok) {
        const data = await res.json();
        usuarioActual = { idUsuario: data.idUsuario, nombreUsuario: data.nombreUsuario };
        sessionStorage.setItem("usuario", JSON.stringify(usuarioActual));
        e.target.reset();
        mostrarMensaje("");
        mostrarApp();
    } else {
        mostrarMensaje("Email o contraseña incorrectos.");
    }
});

// ---------- Cierre de sesión ----------
document.getElementById("btnLogout").addEventListener("click", () => {
    sessionStorage.removeItem("usuario");
    usuarioActual = null;
    mostrarLogin();
    mostrarMensaje("Sesión cerrada.");
});

// ---------- Cambio de pantalla ----------
function mostrarApp() {
    document.getElementById("seccionAuth").classList.add("oculto");
    document.getElementById("seccionApp").classList.remove("oculto");
    document.getElementById("btnLogout").classList.remove("oculto");
    document.getElementById("saludo").textContent = "Hola, " + usuarioActual.nombreUsuario;
    cargarMascotas();
}

function mostrarLogin() {
    document.getElementById("seccionApp").classList.add("oculto");
    document.getElementById("btnLogout").classList.add("oculto");
    document.getElementById("seccionAuth").classList.remove("oculto");
    document.getElementById("listaMascotas").innerHTML = "";
}

// ---------- Al abrir la página ----------
const guardado = sessionStorage.getItem("usuario");
if (guardado) {
    usuarioActual = JSON.parse(guardado);
    mostrarApp();
}
// ---------- Listar mascotas ----------
async function cargarMascotas() {
    const res = await fetch(`${API}/mascotas/usuario/${usuarioActual.idUsuario}`);
    if (!res.ok) {
        mostrarMensaje("No se pudieron cargar las mascotas.");
        return;
    }
    dibujarMascotas(await res.json());
}

function dibujarMascotas(mascotas) {
    const lista = document.getElementById("listaMascotas");
    lista.innerHTML = "";
    if (mascotas.length === 0) {
        lista.textContent = "No hay mascotas para mostrar.";
        return;
    }
    mascotas.forEach((m) => {
        const tarjeta = document.createElement("div");
        tarjeta.className = "tarjeta";

        const titulo = document.createElement("strong");
        titulo.textContent = m.nombreMascota;

        const detalle = document.createElement("p");
        detalle.textContent =
            `${m.especieMascota} | ${m.generoMascota || "sin género"} | ` +
            `${m.tamanioMascota || "sin tamaño"} | ${m.edadMascota} años | ` +
            `Castrada: ${m.mascotaCastrada ? "Sí" : "No"} | ` +
            `Vacunada: ${m.mascotaVacunada ? "Sí" : "No"}`;

        const btnEditar = document.createElement("button");
        btnEditar.textContent = "Editar";
        btnEditar.addEventListener("click", () => editarMascota(m));

        const btnEliminar = document.createElement("button");
        btnEliminar.textContent = "Eliminar";
        btnEliminar.addEventListener("click", () => eliminarMascota(m.idMascota));

        tarjeta.append(titulo, detalle, btnEditar, btnEliminar);
        lista.appendChild(tarjeta);
    });
}

// ---------- Crear y modificar ----------
document.getElementById("formMascota").addEventListener("submit", async (e) => {
    e.preventDefault();
    const id = document.getElementById("idMascota").value;
    const mascota = {
        nombreMascota: document.getElementById("nombreMascota").value,
        especieMascota: document.getElementById("especieMascota").value,
        generoMascota: document.getElementById("generoMascota").value,
        tamanioMascota: document.getElementById("tamanioMascota").value,
        edadMascota: Number(document.getElementById("edadMascota").value) || 0,
        mascotaCastrada: document.getElementById("mascotaCastrada").checked,
        mascotaVacunada: document.getElementById("mascotaVacunada").checked
    };
    let url = `${API}/mascotas`;
    let metodo = "POST";
    if (id) {
        url = `${API}/mascotas/${id}`;
        metodo = "PUT";
    } else {
        mascota.idUsuario = usuarioActual.idUsuario;
    }
    const res = await fetch(url, {
        method: metodo,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(mascota)
    });
    if (res.ok) {
        mostrarMensaje(id ? "Mascota modificada." : "Mascota creada.");
        limpiarFormulario();
        cargarMascotas();
    } else {
        mostrarMensaje("No se pudo guardar la mascota. Revisa los datos.");
    }
});

function editarMascota(m) {
    document.getElementById("idMascota").value = m.idMascota;
    document.getElementById("nombreMascota").value = m.nombreMascota;
    document.getElementById("especieMascota").value = m.especieMascota;
    document.getElementById("generoMascota").value = m.generoMascota || "";
    document.getElementById("tamanioMascota").value = m.tamanioMascota || "";
    document.getElementById("edadMascota").value = m.edadMascota;
    document.getElementById("mascotaCastrada").checked = m.mascotaCastrada;
    document.getElementById("mascotaVacunada").checked = m.mascotaVacunada;
    document.getElementById("tituloForm").textContent = "Editar mascota";
    document.getElementById("btnCancelar").classList.remove("oculto");
    document.getElementById("formMascota").scrollIntoView();
}

function limpiarFormulario() {
    document.getElementById("formMascota").reset();
    document.getElementById("idMascota").value = "";
    document.getElementById("tituloForm").textContent = "Nueva mascota";
    document.getElementById("btnCancelar").classList.add("oculto");
}

document.getElementById("btnCancelar").addEventListener("click", limpiarFormulario);

// ---------- Eliminar ----------
async function eliminarMascota(id) {
    if (!confirm("¿Seguro que quieres eliminar esta mascota?")) return;
    const res = await fetch(`${API}/mascotas/${id}`, { method: "DELETE" });
    if (res.ok) {
        mostrarMensaje("Mascota eliminada.");
        cargarMascotas();
    } else {
        mostrarMensaje("No se pudo eliminar la mascota.");
    }
}

// ---------- Búsqueda ----------
document.getElementById("formBusqueda").addEventListener("submit", async (e) => {
    e.preventDefault();
    const nombre = document.getElementById("busqueda").value.trim();
    if (nombre === "") {
        cargarMascotas();
        return;
    }
    const res = await fetch(
        `${API}/mascotas/usuario/${usuarioActual.idUsuario}/buscar?nombre=${encodeURIComponent(nombre)}`
    );
    if (res.ok) {
        dibujarMascotas(await res.json());
    } else {
        mostrarMensaje("No se pudo realizar la búsqueda.");
    }
});

document.getElementById("btnTodas").addEventListener("click", () => {
    document.getElementById("busqueda").value = "";
    cargarMascotas();
});