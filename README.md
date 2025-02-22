# 📅 **Simplify Booking** - Sistema Inteligente de Gestión de Reservas

**Simplify Booking** es una solución completa para la automatización de reservas, adaptable a cualquier tipo de negocio: clínicas médicas, peluquerías, academias, talleres entre otros.  
El sistema está desarrollado en **Spring Boot** e integra **Supabase**, **WhatsApp API** y **DeepSeek (IA)** para gestionar reservas, cancelaciones, consultas en tiempo real y proporcionar información básica del negocio.

---

### 🎥 **Vista previa del Chatbot**
¡Simplify Booking incluye una interfaz web con un chatbot basico hecho en html, css y js para gestionar reservas y responder preguntas básicas sin depender de la API de WhatsApp!  
![interfaz](https://github.com/user-attachments/assets/edb4a46b-5c84-4c71-a1c0-5137e8a6b4dd)
![interfaz2](https://github.com/user-attachments/assets/776c65a2-55c0-4abd-a817-6ac9c0a2f938)
![interfaz3](https://github.com/user-attachments/assets/46012859-1edf-4069-a372-1a149fe13ec4)

---

## 🚀 **Tecnologías utilizadas**
- 💻 **Java 17** (Spring Boot 3.4.2)
- 🏗 **Spring MVC**, **Spring REST**
- 🛢 **Supabase** (PostgreSQL)
- 💬 **WhatsApp Business API** para interacción automatizada
- 🤖 **DeepSeek AI** para análisis de intenciones y conversación contextual
- 🌐 **HTML, CSS, JS** para el chatbot web
- 🔗 **Maven** para gestión de dependencias

---

## ⚡️ **Características principales**
- 🤖 **Detección automática de intenciones:** Identificación de solicitudes de reservas, cancelaciones y consultas mediante IA.  
- 📅 **Gestión de disponibilidad:** Consulta de horarios en tiempo real a través de Supabase.  
- ℹ️ **Información básica del establecimiento:** Responde preguntas sobre horarios, ubicación, servicios ofrecidos y datos de contacto.  
- 🔄 **Respuestas automáticas y personalizadas:** Confirmaciones, reprogramaciones y cancelaciones gestionadas automáticamente.  
- 🔐 **Integración segura:** Control de autenticación y validación mediante claves API.  
- 💡 **Versatilidad para múltiples negocios:** Configurable para cualquier empresa que gestione reservas de citas.  

---

## ⚙️ **Instalación y configuración**

### 1️⃣ **Clona el repositorio**
```bash
git clone https://github.com/tu_usuario/simplify-booking.git
cd simplify-booking
```

### 2️⃣ **Configuración de Supabase**
1. Crea un proyecto en [Supabase](https://supabase.io/).
2. Crea la siguiente tabla en tu base de datos:

| column_name      | data_type                | is_nullable | column_default    |
| ---------------- | ------------------------ | ----------- | ----------------- |
| id               | uuid                     | NO          | gen_random_uuid() |
| appointment_time | text                     | NO          | null              |
| user_name        | text                     | NO          | null              |
| created_at       | timestamp with time zone | YES         | now()             |

🔑 **Nota:** Asegúrate de obtener tu `SUPABASE_URL` y `SUPABASE_API_KEY` para la configuración posterior.

---

### 3️⃣ **Configuración del proyecto**
- Crea un archivo `application.properties` en `src/main/resources/` con la siguiente información:
```properties
server.port=8080

# Supabase Config
supabase.url=TU_SUPABASE_URL
supabase.key=TU_SUPABASE_API_KEY

# DeepSeek API
deepseek.api.url=TU_DEEPSEEK_API_URL
deepseek.api.key=TU_DEEPSEEK_API_KEY
```

---

### 4️⃣ **Ejecuta la aplicación**
```bash
./mvnw spring-boot:run
```
O si prefieres desde un IDE como **IntelliJ** o **Eclipse**, ejecuta la clase `SimplifyBookingApplication`.

---

## 💬 **Uso del Chatbot Web**  
Accede al chatbot a través del navegador:
```
http://localhost:8080/chat.html
```
💡 *El chatbot gestiona reservas, cancelaciones, consultas básicas y proporciona información sobre el negocio, incluyendo horarios, ubicación y servicios ofrecidos.*

---

## 🌐 **Integración con WhatsApp API (Opcional)**  
1. Crea una cuenta en [Meta for Developers](https://developers.facebook.com/).
2. Obtén tus credenciales para la API de WhatsApp Business.
3. Configura el endpoint `/webhook/whatsapp` en tu proyecto.
4. Sigue las instrucciones oficiales para registrar tu webhook en el entorno de producción.

---

## 🏃 **Pruebas básicas con CURL**
Puedes realizar pruebas rápidas con `curl`:
```bash
curl -X POST http://localhost:8080/deepseek/test \
-H "Content-Type: application/json" \
-d "{\"mensaje\":\"Hola, quiero reservar una cita para mañana a las 10\"}"
```

---

## 🛡 **Licencia**
Distribuido bajo la licencia MIT. Consulta el archivo `LICENSE` para más información.

---


## ✉️ **🤝 Contribuciones**
¡Las contribuciones son bienvenidas! Por favor, abre un *Pull Request* o crea un *Issue* para sugerencias y mejoras.

---

## 👨‍⚕️ **Desarrollado por:**
**Gaspar Cruañes Martínez**  
🔗 [LinkedIn](https://www.linkedin.com/in/gaspar-crua%C3%B1es/)  
🐙 [GitHub](https://github.com/GasparCM/)  


Con **Simplify Booking**, llevar la gestión de citas, reservas y atención informativa de cualquier negocio al siguiente nivel es fácil, rápido y eficiente. 💙✨
