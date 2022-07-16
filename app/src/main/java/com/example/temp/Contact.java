package com.example.temp;

public class Contact {
    int id;
    String name;
    String phone_number;
    String till;

    public Contact(int id, String name, String phone_number, String till) {
        this.id = id;
        this.name = name;
        this.phone_number = phone_number;
        this.till = till;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getTill() {
        return till;
    }

    public void setTill(String till) {
        this.till = till;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", till='" + till + '\'' +
                '}';
    }
}
