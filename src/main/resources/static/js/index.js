console.log("index.js")

function getCart() {
    let cart = localStorage.getItem('cart');
    if (!cart) {
        return [];
    }
    return JSON.parse(cart);
}

function saveCart(cart) {
    localStorage.setItem('cart', JSON.stringify(cart));
}

$(document).ready(function(){
    $('[data-bs-toggle="tooltip"]').each(function () {
        new bootstrap.Tooltip(this);
    });
});

