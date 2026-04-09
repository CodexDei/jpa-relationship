package com.codexdei.springboot.jpa.relationship.repositories;

import org.springframework.data.repository.CrudRepository;

import com.codexdei.springboot.jpa.relationship.entities.Invoice;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

    

}
