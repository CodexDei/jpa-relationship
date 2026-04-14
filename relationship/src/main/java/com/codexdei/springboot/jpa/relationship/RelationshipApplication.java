package com.codexdei.springboot.jpa.relationship;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.codexdei.springboot.jpa.relationship.entities.Address;
import com.codexdei.springboot.jpa.relationship.entities.Client;
import com.codexdei.springboot.jpa.relationship.entities.ClientDetails;
import com.codexdei.springboot.jpa.relationship.entities.Invoice;
import com.codexdei.springboot.jpa.relationship.repositories.InvoiceRepository;
import com.codexdei.springboot.jpa.relationship.repositories.ClientDetailsRepository;
import com.codexdei.springboot.jpa.relationship.repositories.ClientRepository;

@SpringBootApplication
public class RelationshipApplication implements CommandLineRunner {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private ClientDetailsRepository clientDetailsRepository;

	public static void main(String[] args) {
		SpringApplication.run(RelationshipApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		/*
		 * manyToOne();
		 * manyToOneFindByIdClient();
		 * OneToMany();
		 * removeAddressFindById();
		 * OneToManyBidirectional();
		 * OneToManyBidirectionalFindById();
		 * removeBidirectionalFindById();
		 * removeBidirectional();
		 * OneToOne();
		 */
		OneToOneFindById();
	}

	@Transactional
	public void OneToOneFindById() {

		Long id = 1L;
		ClientDetails clientDetails = new ClientDetails(true, 5000);
		clientDetailsRepository.save(clientDetails);

		Optional<Client> clientOptional = clientRepository.findOne(id);

		clientOptional.ifPresentOrElse(client -> {

			client.setClientDetails(clientDetails);
			clientRepository.save(client);

			System.out.println(client);

		}, () -> System.out.println("Client with ID '" + id + "' not found"));
	}

	@Transactional
	public void OneToOne() {

		ClientDetails clientDetails = new ClientDetails(true, 5000);
		clientDetailsRepository.save(clientDetails);

		Client client = new Client("Sara", "Carden");
		client.setClientDetails(clientDetails);
		clientRepository.save(client);

		System.out.println(client);
	}

	@Transactional
	public void removeBidirectional() {

		Long id = 3L;

		Client client = new Client("Wilmer", "Fariet");

		Optional<Client> clientOPtional = Optional.of(client);

		clientOPtional.ifPresentOrElse(c -> {

			Invoice invoice1 = new Invoice("invoice Iphone 17 pro", 1000L);
			Invoice invoice2 = new Invoice("invoice Samsung S26 Ultra", 899L);
			Invoice invoice3 = new Invoice("invoice Lenovo IdeaPad 2026", 6000L);
			Invoice invoice4 = new Invoice("invoice PS5", 500L);
			// una forma de agregar las facturas al cliente en una relacion oneToMany
			// Bidirecccional, creando y usando el metodo 'addInvoice' */
			c.addInvoice(invoice1).addInvoice(invoice2).addInvoice(invoice3).addInvoice(invoice4);

			// guardando solo el cliente tambien se persiste(ó guarda) las facturas
			// por el Cascade.ALL
			clientRepository.save(c);

			System.out.println(clientOPtional);

		}, () -> System.out.println("¡¡¡¡¡¡¡ Client with ID: '" + id + "' not found in the Database !!!!!!!!")

		);

		Optional<Client> clientOPtionalDb = clientRepository.findOneWithInvoices(id);

		clientOPtionalDb.ifPresentOrElse(c -> {

			Optional<Invoice> invoiceOptional = invoiceRepository.findById(3L);

			invoiceOptional.ifPresentOrElse(invoice -> {

				// se creo el metodo removeInvoce para optimizar este metodo
				c.removeInvoice(invoice);
				clientRepository.save(c);
				System.out.println(c);

			}, () -> System.out.println("¡¡¡¡¡¡¡ Invoice with ID: '" + id + "' not found in the Database !!!!!!!!")

			);

		}, () -> System.out.println("¡¡¡¡¡¡¡ Client with ID: '" + id + "' not found in the Database !!!!!!!!"));
	}

	@Transactional
	public void removeBidirectionalFindById() {

		Long id = 1L;

		Optional<Client> clientOPtional = clientRepository.findOneWithInvoices(id);

		clientOPtional.ifPresentOrElse(client -> {

			Invoice invoice1 = new Invoice("invoice Iphone 17 pro", 1000L);
			Invoice invoice2 = new Invoice("invoice Samsung S26 Ultra", 899L);
			Invoice invoice3 = new Invoice("invoice Lenovo IdeaPad 2026", 6000L);
			Invoice invoice4 = new Invoice("invoice PS5", 500L);
			// una forma de agregar las facturas al cliente en una relacion oneToMany
			// Bidirecccional, creando y usando el metodo 'addInvoice' */
			client.addInvoice(invoice1).addInvoice(invoice2).addInvoice(invoice3).addInvoice(invoice4);

			// guardando solo el cliente tambien se persiste(ó guarda) las facturas
			// por el Cascade.ALL
			clientRepository.save(client);

			System.out.println(clientOPtional);

		}, () -> System.out.println("¡¡¡¡¡¡¡ Client with ID: '" + id + "' not found in the Database !!!!!!!!")

		);

		Optional<Client> clientOPtionalDb = clientRepository.findOneWithInvoices(id);

		clientOPtionalDb.ifPresentOrElse(client -> {

			Optional<Invoice> invoiceOptional = invoiceRepository.findById(3L);

			invoiceOptional.ifPresentOrElse(invoice -> {

				// se creo el metodo removeInvoce para optimizar este metodo
				client.removeInvoice(invoice);
				clientRepository.save(client);
				System.out.println(client);

			}, () -> System.out.println("¡¡¡¡¡¡¡ Invoice with ID: '" + id + "' not found in the Database !!!!!!!!")

			);

		}, () -> System.out.println("¡¡¡¡¡¡¡ Client with ID: '" + id + "' not found in the Database !!!!!!!!"));
	}

	@Transactional
	public void OneToManyBidirectionalFindById() {

		Long id = 1L;

		Optional<Client> clientOPtional = clientRepository.findOneWithInvoices(id);

		System.out.println("cuando se busca: " + clientOPtional);

		clientOPtional.ifPresentOrElse(client -> {

			Invoice invoice1 = new Invoice("invoice Iphone 17 pro", 1000L);
			Invoice invoice2 = new Invoice("invoice Samsung S26 Ultra", 899L);
			Invoice invoice3 = new Invoice("invoice Lenovo IdeaPad 2026", 6000L);
			Invoice invoice4 = new Invoice("invoice PS5", 500L);
			// una forma de agregar las facturas al cliente en una relacion oneToMany
			// Bidirecccional, creando y usando el metodo 'addInvoice' */
			client.addInvoice(invoice1).addInvoice(invoice2).addInvoice(invoice3).addInvoice(invoice4);

			System.out.println("Antes de guardarlo: " + clientOPtional.get());

			// guardando solo el cliente tambien se persiste(ó guarda) las facturas
			// por el Cascade.ALL
			clientRepository.save(client);

			System.out.println(clientOPtional);

		}, () -> System.out.println("¡¡¡¡¡¡¡ Client with ID: '" + id + "' not found in the Database !!!!!!!!")

		);
	}

	@Transactional
	public void OneToManyBidirectional() {

		Client client = new Client("Kevin", "Roll");

		Invoice invoice1 = new Invoice("invoice Iphone 17 pro", 1000L);
		Invoice invoice2 = new Invoice("invoice Samsung S26 Ultra", 899L);
		Invoice invoice3 = new Invoice("invoice Lenovo IdeaPad 2026", 6000L);
		Invoice invoice4 = new Invoice("invoice PS5", 500L);

		// una forma de agregar las facturas al cliente en una relacion oneToMany
		// Bidirecccional
		/*
		 * client.setInvoices(Arrays.asList(invoice1, invoice2));
		 * invoice1.setClient(client);
		 * invoice2.setClient(client);
		 * invoice3.setClient(client);
		 * invoice4.setClient(client);
		 * // una forma de agregar las facturas al cliente en una relacion oneToMany
		 * // Bidirecccional, creando y usando el metodo 'addInvoice'
		 */
		client.addInvoice(invoice1).addInvoice(invoice2).addInvoice(invoice3).addInvoice(invoice4);

		// guardando solo el cliente tambien se persiste(ó guarda) las facturas
		// por el Cascade.ALL
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

			Set adressSet = new HashSet<>();
			adressSet.add(address1);
			adressSet.add(address2);
			client.setAddresses((adressSet));

			clientRepository.save(client);

			System.out.println(client);

			Optional<Client> optionalClient2 = clientRepository.findOneWithAddresses(id);
			optionalClient2.ifPresentOrElse(c -> {

				Address addressRemove = (Address) c.getAddresses();
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

		Optional<Client> optionalClient = clientRepository.findOneWithAddresses(3L);
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

			Set adressSet = new HashSet<>();
			adressSet.add(address1);
			adressSet.add(address2);
			client.setAddresses(adressSet);

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
