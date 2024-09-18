package com.HouseMovers.UserApp.ModelClass;

public class OrderModelClass {
    private String categoryNameFurniture;
    private String categoryQuantityFurniture;
    private String categoryDescriptionFurniture;
    private String categoryNameElectronics;
    private String categoryQuantityElectronics;
    private String categoryDescriptionElectronics;
    private String categoryNameKitchen;
    private String categoryQuantityKitchen;
    private String categoryDescriptionKitchen;
    private String categoryNameAccessories;
    private String categoryQuantityAccessories;
    private String categoryDescriptionAccessories;
    private String categoryNameMiscellaneous;
    private String categoryQuantityMiscellaneous;
    private String categoryDescriptionMiscellaneous;
    private String categoryNameVehicles;
    private String categoryQuantityVehicles;
    private String categoryDescriptionVehicles;
    private String orderId;
    private String orderUserId;
    private String orderInQue;
    private Long timestamp;
    private String orderNumber;
    private String locationFrom;
    private String locationTo;
    private String shiftQategory;
    private String coast;

    public OrderModelClass() {
    }

    public OrderModelClass(String categoryNameFurniture, String categoryQuantityFurniture, String categoryDescriptionFurniture, String categoryNameElectronics, String categoryQuantityElectronics, String categoryDescriptionElectronics, String categoryNameKitchen, String categoryQuantityKitchen, String categoryDescriptionKitchen, String categoryNameAccessories, String categoryQuantityAccessories, String categoryDescriptionAccessories, String categoryNameMiscellaneous, String categoryQuantityMiscellaneous, String categoryDescriptionMiscellaneous, String categoryNameVehicles, String categoryQuantityVehicles, String categoryDescriptionVehicles, String orderId, String orderUserId, String orderInQue, Long timestamp, String orderNumber, String locationFrom, String locationTo) {
        this.categoryNameFurniture = categoryNameFurniture;
        this.categoryQuantityFurniture = categoryQuantityFurniture;
        this.categoryDescriptionFurniture = categoryDescriptionFurniture;
        this.categoryNameElectronics = categoryNameElectronics;
        this.categoryQuantityElectronics = categoryQuantityElectronics;
        this.categoryDescriptionElectronics = categoryDescriptionElectronics;
        this.categoryNameKitchen = categoryNameKitchen;
        this.categoryQuantityKitchen = categoryQuantityKitchen;
        this.categoryDescriptionKitchen = categoryDescriptionKitchen;
        this.categoryNameAccessories = categoryNameAccessories;
        this.categoryQuantityAccessories = categoryQuantityAccessories;
        this.categoryDescriptionAccessories = categoryDescriptionAccessories;
        this.categoryNameMiscellaneous = categoryNameMiscellaneous;
        this.categoryQuantityMiscellaneous = categoryQuantityMiscellaneous;
        this.categoryDescriptionMiscellaneous = categoryDescriptionMiscellaneous;
        this.categoryNameVehicles = categoryNameVehicles;
        this.categoryQuantityVehicles = categoryQuantityVehicles;
        this.categoryDescriptionVehicles = categoryDescriptionVehicles;
        this.orderId = orderId;
        this.orderUserId = orderUserId;
        this.orderInQue = orderInQue;
        this.timestamp = timestamp;
        this.orderNumber = orderNumber;
        this.locationFrom = locationFrom;
        this.locationTo = locationTo;
    }

    public OrderModelClass(String categoryNameFurniture, String categoryQuantityFurniture, String categoryDescriptionFurniture, String categoryNameElectronics, String categoryQuantityElectronics, String categoryDescriptionElectronics, String categoryNameKitchen, String categoryQuantityKitchen, String categoryDescriptionKitchen, String categoryNameAccessories, String categoryQuantityAccessories, String categoryDescriptionAccessories, String categoryNameMiscellaneous, String categoryQuantityMiscellaneous, String categoryDescriptionMiscellaneous, String categoryNameVehicles, String categoryQuantityVehicles, String categoryDescriptionVehicles, String orderId, String orderUserId, String orderInQue, Long timestamp, String orderNumber, String locationFrom, String locationTo, String shiftQategory, String coast) {
        this.categoryNameFurniture = categoryNameFurniture;
        this.categoryQuantityFurniture = categoryQuantityFurniture;
        this.categoryDescriptionFurniture = categoryDescriptionFurniture;
        this.categoryNameElectronics = categoryNameElectronics;
        this.categoryQuantityElectronics = categoryQuantityElectronics;
        this.categoryDescriptionElectronics = categoryDescriptionElectronics;
        this.categoryNameKitchen = categoryNameKitchen;
        this.categoryQuantityKitchen = categoryQuantityKitchen;
        this.categoryDescriptionKitchen = categoryDescriptionKitchen;
        this.categoryNameAccessories = categoryNameAccessories;
        this.categoryQuantityAccessories = categoryQuantityAccessories;
        this.categoryDescriptionAccessories = categoryDescriptionAccessories;
        this.categoryNameMiscellaneous = categoryNameMiscellaneous;
        this.categoryQuantityMiscellaneous = categoryQuantityMiscellaneous;
        this.categoryDescriptionMiscellaneous = categoryDescriptionMiscellaneous;
        this.categoryNameVehicles = categoryNameVehicles;
        this.categoryQuantityVehicles = categoryQuantityVehicles;
        this.categoryDescriptionVehicles = categoryDescriptionVehicles;
        this.orderId = orderId;
        this.orderUserId = orderUserId;
        this.orderInQue = orderInQue;
        this.timestamp = timestamp;
        this.orderNumber = orderNumber;
        this.locationFrom = locationFrom;
        this.locationTo = locationTo;
        this.shiftQategory = shiftQategory;
        this.coast = coast;
    }

    public String getShiftQategory() {
        return shiftQategory;
    }

    public void setShiftQategory(String shiftQategory) {
        this.shiftQategory = shiftQategory;
    }

    public String getCoast() {
        return coast;
    }

    public void setCoast(String coast) {
        this.coast = coast;
    }

    public String getCategoryNameFurniture() {
        return categoryNameFurniture;
    }

    public void setCategoryNameFurniture(String categoryNameFurniture) {
        this.categoryNameFurniture = categoryNameFurniture;
    }

    public String getCategoryQuantityFurniture() {
        return categoryQuantityFurniture;
    }

    public void setCategoryQuantityFurniture(String categoryQuantityFurniture) {
        this.categoryQuantityFurniture = categoryQuantityFurniture;
    }

    public String getCategoryDescriptionFurniture() {
        return categoryDescriptionFurniture;
    }

    public void setCategoryDescriptionFurniture(String categoryDescriptionFurniture) {
        this.categoryDescriptionFurniture = categoryDescriptionFurniture;
    }

    public String getCategoryNameElectronics() {
        return categoryNameElectronics;
    }

    public void setCategoryNameElectronics(String categoryNameElectronics) {
        this.categoryNameElectronics = categoryNameElectronics;
    }

    public String getCategoryQuantityElectronics() {
        return categoryQuantityElectronics;
    }

    public void setCategoryQuantityElectronics(String categoryQuantityElectronics) {
        this.categoryQuantityElectronics = categoryQuantityElectronics;
    }

    public String getCategoryDescriptionElectronics() {
        return categoryDescriptionElectronics;
    }

    public void setCategoryDescriptionElectronics(String categoryDescriptionElectronics) {
        this.categoryDescriptionElectronics = categoryDescriptionElectronics;
    }

    public String getCategoryNameKitchen() {
        return categoryNameKitchen;
    }

    public void setCategoryNameKitchen(String categoryNameKitchen) {
        this.categoryNameKitchen = categoryNameKitchen;
    }

    public String getCategoryQuantityKitchen() {
        return categoryQuantityKitchen;
    }

    public void setCategoryQuantityKitchen(String categoryQuantityKitchen) {
        this.categoryQuantityKitchen = categoryQuantityKitchen;
    }

    public String getCategoryDescriptionKitchen() {
        return categoryDescriptionKitchen;
    }

    public void setCategoryDescriptionKitchen(String categoryDescriptionKitchen) {
        this.categoryDescriptionKitchen = categoryDescriptionKitchen;
    }

    public String getCategoryNameAccessories() {
        return categoryNameAccessories;
    }

    public void setCategoryNameAccessories(String categoryNameAccessories) {
        this.categoryNameAccessories = categoryNameAccessories;
    }

    public String getCategoryQuantityAccessories() {
        return categoryQuantityAccessories;
    }

    public void setCategoryQuantityAccessories(String categoryQuantityAccessories) {
        this.categoryQuantityAccessories = categoryQuantityAccessories;
    }

    public String getCategoryDescriptionAccessories() {
        return categoryDescriptionAccessories;
    }

    public void setCategoryDescriptionAccessories(String categoryDescriptionAccessories) {
        this.categoryDescriptionAccessories = categoryDescriptionAccessories;
    }

    public String getCategoryNameMiscellaneous() {
        return categoryNameMiscellaneous;
    }

    public void setCategoryNameMiscellaneous(String categoryNameMiscellaneous) {
        this.categoryNameMiscellaneous = categoryNameMiscellaneous;
    }

    public String getCategoryQuantityMiscellaneous() {
        return categoryQuantityMiscellaneous;
    }

    public void setCategoryQuantityMiscellaneous(String categoryQuantityMiscellaneous) {
        this.categoryQuantityMiscellaneous = categoryQuantityMiscellaneous;
    }

    public String getCategoryDescriptionMiscellaneous() {
        return categoryDescriptionMiscellaneous;
    }

    public void setCategoryDescriptionMiscellaneous(String categoryDescriptionMiscellaneous) {
        this.categoryDescriptionMiscellaneous = categoryDescriptionMiscellaneous;
    }

    public String getCategoryNameVehicles() {
        return categoryNameVehicles;
    }

    public void setCategoryNameVehicles(String categoryNameVehicles) {
        this.categoryNameVehicles = categoryNameVehicles;
    }

    public String getCategoryQuantityVehicles() {
        return categoryQuantityVehicles;
    }

    public void setCategoryQuantityVehicles(String categoryQuantityVehicles) {
        this.categoryQuantityVehicles = categoryQuantityVehicles;
    }

    public String getCategoryDescriptionVehicles() {
        return categoryDescriptionVehicles;
    }

    public void setCategoryDescriptionVehicles(String categoryDescriptionVehicles) {
        this.categoryDescriptionVehicles = categoryDescriptionVehicles;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderUserId() {
        return orderUserId;
    }

    public void setOrderUserId(String orderUserId) {
        this.orderUserId = orderUserId;
    }

    public String getOrderInQue() {
        return orderInQue;
    }

    public void setOrderInQue(String orderInQue) {
        this.orderInQue = orderInQue;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getLocationFrom() {
        return locationFrom;
    }

    public void setLocationFrom(String locationFrom) {
        this.locationFrom = locationFrom;
    }

    public String getLocationTo() {
        return locationTo;
    }

    public void setLocationTo(String locationTo) {
        this.locationTo = locationTo;
    }
}
