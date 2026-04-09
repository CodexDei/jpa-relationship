package com.codexdei.springboot.jpa.relationship.entities;

import java.util.Optional;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String description;
    private Long total;

    // establece el nombre para la llave foranea sino existe, si ya existe debe ser
    // el mismo de la tabla en la BD
    @ManyToOne
    @JoinColumn(name = "id_client_temp")
    private Client client;

    public Invoice() {
    }

    public Invoice(String description, Long total) {
        this.description = description;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client2) {
        this.client = client2;
    }

    @Override
    public String toString() {
        return "{id=" + id +
                ", description=" + description +
                ", total=" + total + "}";
    }

}
