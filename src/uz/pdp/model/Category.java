package uz.pdp.model;

import java.util.UUID;

public class Category extends BaseModel{
    private UUID parentCategoryId;

    public Category() {
    }

    public Category(String name, UUID parentCategoryId) {
        super(name);
        this.parentCategoryId = parentCategoryId;
    }

    public UUID getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(UUID parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}
