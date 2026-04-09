package com.codexdei.springboot.jpa.relationship;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.codexdei.springboot.jpa.relationship.entities.Address;
import com.codexdei.springboot.jpa.relationship.entities.Client;
import com.codexdei.springboot.jpa.relationship.entities.Invoice;
import com.codexdei.springboot.jpa.relationship.repositories.InvoiceRepository;
import com.codexdei.springboot.jpa.relationship.repositories.ClientRepository;

@SpringBootApplication
public class RelationshipApplication implements CommandLineRunner {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	public static void main(String[] args) {
		SpringApplication.run(RelationshipApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		/*
		 * manyToOne();
		 * manyToOneFindByIdClient();
		 * OneToMany();
		removeAddressFindById();
		*/
		OneToManyBidirectional();
	}

	@Transactional
	public void OneToManyBidirectional() {

		Client client = new Client("Kevin", "Roll");

		Invoice invoice1 = new Invoice("invoice Iphone 17 pro", 1000L);
		Invoice invoice2 = new Invoice("invoice Samsung S26 Ultra", 899L);

		client.setInvoices(Arrays.asList(invoice1,invoice2));

		invoice1.setClient(client);
		invoice2.setClient(client);

		//guardando solo el cliente tambien se persiste(ó guarda) las facturas
		//por el Cascade.ALL
		clientRepository.save(client);

		System.out.println(client);
	}

	@Transactional
	public void removeAddressFindById() {

		Long id = 2L;

		Optional<Client> optionalClient = clientRepository.findById(id);

		Address address1 = new Address("Av Caracas", 13);
		Address address2 = new Address("Circunvalar", 123);

		optionalClient.ifPresentOrElse(client -> {

			client.setAddresses(Arrays.asList(address1, address2));

			clientRepository.save(client);

			System.out.println(client);

			Optional<Client> optionalClient2 = clientRepository.findOne(id);
			optionalClient2.ifPresentOrElse(c -> {

				Address addressRemove = c.getAddresses().get(1);
				System.out.println("Adress Remove: " + addressRemove);

				c.getAddresses().remove(addressRemove);
				clientRepository.save(c);
				System.out.println(c);
				System.out.println("popo");

			}, () -> {
				System.out
						.println("¡¡¡¡¡¡¡ FINDONE => Client with ID: '" + id + "' not found in the Database !!!!!!!!");
			});

		}, () -> {
			System.out.println("¡¡¡¡¡¡¡ Client with ID: '" + id + "' not found in the Database !!!!!!!!");
		});

	}

	@Transactional
	public void removeAddress() {

		Client client = new Client("Jey", "Oyol");

		Address address1 = new Address("Av Caracas", 13);
		Address address2 = new Address("Circunvalar", 123);

		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		Client clientPersist = clientRepository.save(client);

		System.out.println(clientPersist);

		Optional<Client> optionalClient = clientRepository.findOne(3L);
		optionalClient.ifPresent(c -> {

			c.getAddresses().remove(address1);
			clientRepository.save(c);
			System.out.println(c);
		}

		);

	}

	@Transactional
	public void OneToManyFindById() {

		Long id = 2L;

		Optional<Client> optionalClient = clientRepository.findById(id);

		Address address1 = new Address("Av Caracas", 13);
		Address address2 = new Address("Circunvalar", 123);

		optionalClient.ifPresentOrElse(client -> {

			client.setAddresses(Arrays.asList(address1, address2));

			clientRepository.save(client);

			System.out.println(client);

		}, () -> {
			System.out.println("¡¡¡¡¡¡¡ Client with ID: '" + id + "' not found in the Database !!!!!!!!");
		});

	}

	@Transactional
	public void OneToMany() {

		Client client = new Client("Jey", "Oyol");

		Address address1 = new Address("Av Caracas", 13);
		Address address2 = new Address("Circunvalar", 123);

		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		Client clientPersist = clientRepository.save(client);

		System.out.println(clientPersist);

	}

	@Transactional
	public void manyToOne() {

		Client client = new Client("Melvin", "Cayam");
		clientRepository.save(client);

		Invoice invoice = new Invoice("Office Purchases", 1000L);
		invoice.setClient(client);
		Invoice invoiceDB = invoiceRepository.save(invoice);
		System.out.println(invoiceDB);
	}

	@Transactional
	public void manyToOneFindByIdClient() {

		Long id = 1L;

		Optional<Client> optionalClient = clientRepository.findById(id);

		if (optionalClient.isPresent()) {

			Client client = optionalClient.orElseThrow();

			Invoice invoice = new Invoice();
			invoice.setDescription("Office Purchases");
			invoice.setTotal(1000L);
			invoice.setClient(client);
			Invoice invoiceDB = invoiceRepository.save(invoice);
			System.out.println(invoiceDB);

		} else {

			System.out.println("¡¡¡¡¡¡¡ Client with ID: '" + id + "' not found in the Database !!!!!!!!");
		}

	}

}
