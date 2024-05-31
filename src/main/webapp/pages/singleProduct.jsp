<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Product Card/Page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/stylesheet/singleProduct.css">
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<div class="product-container">
    <c:forEach var="product" items="${products}">
        <div class="card">
            <!-- card left -->
            <div class="product-img">
                <img
                    src="${pageContext.request.contextPath}/resources/images/${product.getImageUrlFromPart()}"
                    alt="shoe image">
            </div>
            <!-- card right -->
            <div class="product-content">
                <h2 class="product-name">${product.productName}</h2>
                <div class="product-price">
                    <p class="price">
                        <strong>Price:</strong> <span>Rs. ${product.price}</span>
                    </p>
                    <p>
                        <strong>Stock: </strong> <span class="hh">${product.stockQuantity}</span>
                    </p>
                </div>
                <div class="product-detail">
                    <h2>Product description:</h2>
                    <p>${product.productDescription}</p>
                    <ul>
                        <li>Brand: <span>${product.brand}</span></li>
                        <li>Color: <span>${product.color}</span></li>
                        <li>Noise Cancellation: <span>${product.noiseCancellation}</span></li>
                        <li>Connectivity: <span>${product.connectivity}</span></li>
                        <li>Cable Length: <span>${product.cableLength}</span></li>
                        <li>Driver: <span>${product.driver}</span></li>
                        <li>Weight: <span>${product.weight}</span></li>
                    </ul>
                </div>
                <div class="purchase-info">
                    <input type="number" min="1" max="${product.stockQuantity}" value="1">
                    <button type="button" class="btn">
                        Add to Cart <i class="fas fa-shopping-cart"></i>
                    </button>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
