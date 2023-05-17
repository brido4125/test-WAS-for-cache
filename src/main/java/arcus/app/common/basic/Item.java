package arcus.app.common.basic;

import java.io.Serializable;

public class Item implements Serializable {
    private Long id;
    private int price;
    private String name;

    public Item(Long id, int price, String name) {
        this.id = id;
        this.price = price;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
