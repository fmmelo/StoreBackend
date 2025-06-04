package com.myproject.myproject.api.DTO;

public class CartOperationDTO {

    private Long itemId;
    private int quantity;

    public CartOperationDTO() {
    }

    public CartOperationDTO(Long id, int quantity) {
        this.itemId = id;
        this.quantity = quantity;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
