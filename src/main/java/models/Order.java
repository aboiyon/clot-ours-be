package models;

import java.sql.Timestamp;
import java.util.Objects;

public class Order {
    int id;
    String name;
    int age;
    Timestamp birthday;

    public Order(int id, String name, int age, Timestamp birthday) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return getId() == order.getId() && getAge() == order.getAge() && Objects.equals(getName(), order.getName()) && Objects.equals(getBirthday(), order.getBirthday());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAge(), getBirthday());
    }
}
