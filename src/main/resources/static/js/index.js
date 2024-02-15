console.log("index.js")
function addToCart(productId, quantity) {
    // Kiểm tra xem localStorage đã có dữ liệu giỏ hàng chưa
    let cart = JSON.parse(localStorage.getItem('cart')) || {};

    // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng chưa
    if (cart[productId]) {
        // Nếu sản phẩm đã tồn tại, cập nhật số lượng
        cart[productId] += quantity;
    } else {
        // Nếu sản phẩm chưa tồn tại, thêm mới vào giỏ hàng
        cart[productId] = quantity;
    }

    // Lưu thông tin giỏ hàng vào localStorage
    localStorage.setItem('cart', JSON.stringify(cart));
}


$(document).ready(function(){
    $('[data-bs-toggle="tooltip"]').each(function () {
        new bootstrap.Tooltip(this);
    });
});

