package model;

import java.io.File;
import javax.servlet.http.Part;

import util.StringUtils;

public class ProductModel {
    private int productId;
    private String productName;
    private String productDescription;
    private String ImageUrlFromPart;
    private int stockQuantity;
    private int price;
    private String brand;
    private String color;
    private String connectivity;
    private String noiseCancellation;
    private int cableLength;
    private String driver;
    private String weight;

    // Constructor
    public ProductModel(String productName, String productDescription, Part imagePart, int stockQuantity,
    		int price, String brand, String color, String connectivity,
                        String noiseCancellation, int cableLength, String driver, String weight) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.ImageUrlFromPart = getImageUrl(imagePart);
        this.stockQuantity = stockQuantity;
        this.price = price;
        this.brand = brand;
        this.color = color;
        this.connectivity = connectivity;
        this.noiseCancellation = noiseCancellation;
        this.cableLength = cableLength;
        this.driver = driver;
        this.weight = weight;
    }
    
    public ProductModel() {
    
    }

    public int getProductId() {
		return productId;
	}



	public void setProductId(int productId) {
		this.productId = productId;
	}



	public String getProductName() {
		return productName;
	}



	public void setProductName(String productName) {
		this.productName = productName;
	}



	public String getProductDescription() {
		return productDescription;
	}



	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}



	public String getImageUrlFromPart() {
		return ImageUrlFromPart;
	}



	public void setImageUrlFromPart(String imageUrlFromPart) {
		ImageUrlFromPart = imageUrlFromPart;
	}



	public int getStockQuantity() {
		return stockQuantity;
	}



	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}



	public int getPrice() {
		return price;
	}



	public void setPrice(int price) {
		this.price = price;
	}



	public String getBrand() {
		return brand;
	}



	public void setBrand(String brand) {
		this.brand = brand;
	}


	public String getColor() {
		return color;
	}



	public void setColor(String color) {
		this.color = color;
	}



	public String getConnectivity() {
		return connectivity;
	}



	public void setConnectivity(String connectivity) {
		this.connectivity = connectivity;
	}



	public String getNoiseCancellation() {
		return noiseCancellation;
	}



	public void setNoiseCancellation(String noiseCancellation) {
		this.noiseCancellation = noiseCancellation;
	}



	public int getCableLength() {
		return cableLength;
	}



	public void setCableLength(int cableLength) {
		this.cableLength = cableLength;
	}



	public String getDriver() {
		return driver;
	}



	public void setDriver(String driver) {
		this.driver = driver;
	}



	public String getWeight() {
		return weight;
	}



	public void setWeight(String weight) {
		this.weight = weight;
	}



	private String getImageUrl(Part imagePart) {
		String savePath = StringUtils.SAVE_PATH;
		File fileSaveDir = new File(savePath);
		String imageUrlFromPart = null;
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir();
		}
		String contentDisp = imagePart.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				imageUrlFromPart = s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		if (imageUrlFromPart == null || imageUrlFromPart.isEmpty()) {
			return "a";
		}
		return imageUrlFromPart;
	}

}
