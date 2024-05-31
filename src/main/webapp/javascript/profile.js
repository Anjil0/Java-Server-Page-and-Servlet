document.querySelector("#editBtn").addEventListener("click", function () {
    document.querySelector("#edit-form").style.display = "block";
    document.querySelector(".profile").style.display = "none";
});

document.querySelector("#cancel").addEventListener("click", function () {
    document.querySelector("#edit-form").style.display = "none";
    document.querySelector(".profile").style.display = "block";
});

