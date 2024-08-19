package arcus.app.common.basic;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Item implements Serializable {
    @Id
    private Long id;
    private int price;
    private String name;

    public Item(Long id, int price, String name) {
        this.id = id;
        this.price = price;
        this.name = name;
    }

    public Item() {}

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
