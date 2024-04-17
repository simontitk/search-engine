function query() {
    fetch("/search?q=" + seachBox.value)
    .then((response) => response.json())
    .then((data) => {
        const responsesizeContainer = document.getElementById("responsesize");
        const urlListContainer = document.getElementById("urllist");
        if (data.length === 0) {
            responsesizeContainer.innerHTML = "<p>No web page contains the query word.</p>";
            urlListContainer.innerHTML = "<div></div>"
        }
        else {
            responsesizeContainer.innerHTML = "<p>" + data.length + " websites retrieved.</p>";
            let results = data.map((page) => `<li><a href="${page.url}">${page.title}</a></li>`).join("\n");
            urlListContainer.innerHTML = `<ul>${results}</ul>`;
        }
    });
};

const searchButton = document.getElementById('searchbutton');
const seachBox = document.getElementById('searchbox');

searchButton.onclick =  query

seachBox.addEventListener("keydown", (event)=> {
    if (event.key === "Enter") {
        query();
    }
});

