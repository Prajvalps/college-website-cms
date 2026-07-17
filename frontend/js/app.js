/* =====================================
   📅 GLOBAL VARIABLES
===================================== */
let globalNewsData = [];
let globalNotificationData = [];

/* =====================================
   📅 DATE FORMAT
===================================== */
function formatDate(dateStr) {
    const d = new Date(dateStr);
    return `${String(d.getDate()).padStart(2, '0')}-${String(d.getMonth()+1).padStart(2, '0')}-${d.getFullYear()}`;
}

/* =====================================
   📖 MODAL
===================================== */
function openNewsModal(title, content, date) {
    document.getElementById("modalTitle").innerText = title;
    document.getElementById("modalContent").innerText = content;
    document.getElementById("modalDate").innerText = formatDate(date);

    const modal = new bootstrap.Modal(document.getElementById('newsModal'));
    modal.show();
}

/* =====================================
   📰 LOAD NEWS (SAFE VERSION)
===================================== */
function loadNews() {

    const container = document.getElementById("newsContainer");
    if (!container) return;

    const cached = localStorage.getItem("newsData");

    // ✅ 1. If cache exists → ALWAYS show immediately
    if (cached) {
        renderNews(JSON.parse(cached));
    } else {
        // ✅ 2. If no cache → show loading or fallback
        container.innerHTML = `<p class="text-muted">Loading news...</p>`;
    }

    // ✅ 3. ALWAYS attempt API (no page restriction for now)
fetch("http://localhost:8080/api/news/latest")
        .then(res => res.json())
        .then(data => {

            if (!Array.isArray(data)) throw new Error("Blocked");

            // ✅ update cache + UI
            localStorage.setItem("newsData", JSON.stringify(data));
            renderNews(data);

        })
        .catch(() => {

            // ❌ API failed → fallback
            if (!cached) {
                container.innerHTML = `<p class="text-danger">No news available</p>`;
            }

            console.warn("API blocked, using cache");
        });
}


/* =====================================
   🔔 LOAD NOTIFICATIONS (SAFE)
===================================== */
function loadNotifications() {

    const dropdown = document.getElementById("notificationDropdown");
    if (!dropdown) return;

    const cached = localStorage.getItem("notifData");

    if (cached) {
        renderNotifications(JSON.parse(cached));
    } else {
        dropdown.innerHTML = `<li class="p-2 text-muted">Loading...</li>`;
    }

    fetch("http://localhost:8080/api/notifications/active")
        .then(res => res.json())
        .then(data => {

            if (!Array.isArray(data)) throw new Error("Blocked");

            localStorage.setItem("notifData", JSON.stringify(data));
            renderNotifications(data);

        })
        .catch(() => {

            if (!cached) {
                dropdown.innerHTML = `<li class="p-2 text-danger">No notifications</li>`;
            }

            console.warn("API blocked, using cache");
        });
}

/* =====================================
   📰 RENDER NEWS
===================================== */
function renderNews(data) {

    const container = document.getElementById("newsContainer");
    if (!container) return;

    
    globalNewsData = data;

    container.innerHTML = "";

    data.forEach((n, index) => {

        const title = n.title || "No Title";
        const content = n.content || "No Content";
        const date = n.date || "";

        const shortContent = content.length > 120
            ? content.substring(0, 120) + "..."
            : content;

        container.innerHTML += `
            <div class="col-md-4 mb-3">
                <div class="card shadow-sm h-100">

                    <div class="card-body">
                        <h5 class="fw-bold">${title}</h5>
                        <p class="text-muted small">${shortContent}</p>
                    </div>

                    <div class="d-flex justify-content-between p-2">
                        <span class="badge bg-light text-dark border">
                            ${formatDate(date)}
                        </span>

                        <button class="btn btn-sm btn-primary read-more-btn"
                            data-index="${index}">
                            Read More
                        </button>
                    </div>

                </div>
            </div>
        `;
    });
}

/* =====================================
   🔔 RENDER NOTIFICATIONS
===================================== */
function renderNotifications(data) {

    const dropdown = document.getElementById("notificationDropdown");
    if (!dropdown) return;

    dropdown.innerHTML = "";

    data.slice(0, 5).forEach((n, index) => {

        dropdown.innerHTML += `
            <li>
                <div class="notification-row notif-btn"
                     data-index="${index}"
                     style="cursor:pointer">
                    ${n.title}
                </div>
            </li>
        `;
    });

    // ✅ ADD VIEW ALL (SAFE POSITION)
    dropdown.innerHTML += `
        <li class="text-center p-2 border-top">
            <a href="notifications.html" class="text-decoration-none fw-bold">
                View All Notifications →
            </a>
        </li>
    `;

    globalNotificationData = data;
}

/* =====================================
   🚀 INIT (ONLY INDEX PAGE LOADS API)
===================================== */
document.addEventListener("click", function (e) {

    // 📰 NEWS CLICK
    const newsBtn = e.target.closest(".read-more-btn");
    if (newsBtn) {
        const index = newsBtn.dataset.index;
        const n = globalNewsData[index];
        if (!n) return;

        openNewsModal(n.title, n.content, n.date);
    }

    // 🔔 NOTIFICATION CLICK
    const notifBtn = e.target.closest(".notif-btn");
    if (notifBtn) {
        const index = notifBtn.dataset.index;
        const n = globalNotificationData[index];
        if (!n) return;

        openNewsModal(n.title, n.content, n.date);
    }

});
/* =====================================
   🔕 STATIC MESSAGE FOR OTHER PAGES
===================================== */
function showStaticMessage() {

    // News section
    const container = document.getElementById("newsContainer");
    if (container) {
        container.innerHTML = `
            <div class="text-center text-muted">
                📢 Check latest news on <b>ಮುಖಪುಟ Page</b>
            </div>
        `;
    }

    function showStaticMessage() {

    // 🔔 Notifications message
    const dropdown = document.getElementById("notificationDropdown");
    if (dropdown) {
        dropdown.innerHTML = `
            <li class="p-2 text-center">
                <a href="index.html" class="text-decoration-none">
                    🔔 Check new notifications on ಮುಖಪುಟ page
                </a>
            </li>
        `;
    }

    // 📰 News message (optional)
    const container = document.getElementById("newsContainer");
    if (container) {
        container.innerHTML = `
            <div class="text-center text-muted">
                📢 Check latest news on <b>ಮುಖಪುಟ Page</b>
            </div>
        `;
    }
}

    // Notifications dropdown
    const dropdown = document.getElementById("notificationDropdown");
    if (dropdown) {
       dropdown.innerHTML = `
    <li class="p-2 text-center">
        <a href="index.html" class="text-decoration-none">
            🔔 Check notifications on ಮುಖಪುಟ page
        </a>
    </li>
`;
    }
}
document.addEventListener("DOMContentLoaded", () => {

    const isಮುಖಪುಟPage =
        window.location.pathname.includes("index") ||
        window.location.pathname === "/" ||
        window.location.pathname === "/index.html";

    if (isಮುಖಪುಟPage) {
        loadNews();
        loadNotifications();
    } else {
        showStaticMessage();
    }

});

function openModal(title, content, date) {

  document.getElementById("modalTitle").innerText = title;
  document.getElementById("modalContent").innerText = content;
  document.getElementById("modalDate").innerText =
      new Date(date).toLocaleDateString();

  const modal = new bootstrap.Modal(document.getElementById('newsModal'));
  modal.show();
}