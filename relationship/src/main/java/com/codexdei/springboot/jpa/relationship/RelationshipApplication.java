package com.codexdei.springboot.jpa.relationship;

import java.util.HashSet;
import java.util.List;
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
import com.codexdei.springboot.jpa.relationship.entities.Course;
import com.codexdei.springboot.jpa.relationship.entities.Invoice;
import com.codexdei.springboot.jpa.relationship.entities.Student;
import com.codexdei.springboot.jpa.relationship.repositories.InvoiceRepository;
import com.codexdei.springboot.jpa.relationship.repositories.StudentRepository;
import com.codexdei.springboot.jpa.relationship.repositories.ClientDetailsRepository;
import com.codexdei.springboot.jpa.relationship.repositories.ClientRepository;
import com.codexdei.springboot.jpa.relationship.repositories.CourseRepository;

@SpringBootApplication
public class RelationshipApplication implements CommandLineRunner {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private ClientDetailsRepository clientDetailsRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;

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
		 * OneToOneFindById();
		 * OneToOneBidirectional();
		 * OneToOneBidirectionalFindById();
		 * manyToMany();
		 * manyToManyFindById();
		 * manyToManyRemoveFind();
		 * manyToManyBidirectional();
		 * manyToManyBidirectionalRemove();
		 * manyToManyRemoveFindById();
		 * manyToManyBidirectionalFind();
		 */
		manyToManyRemoveBidirectionalFind();
	}

	@Transactional
	public void manyToManyRemoveBidirectionalFind() {

		Optional<Student> studentOptional1 = studentRepository.findOneWithCourses(1L);
		Optional<Student> studentOptional2 = studentRepository.findOneWithCourses(2L);
		Optional<Student> studentOptional3 = studentRepository.findOneWithCourses(3L);
		Optional<Student> studentOptional4 = studentRepository.findOneWithCourses(4L);

		Student student1 = studentOptional1.orElse(null);
		Student student2 = studentOptional2.orElse(null);
		Student student3 = studentOptional3.orElse(null);
		Student student4 = studentOptional4.orElse(null);

		Optional<Course> course1 = courseRepository.findOneWithStudents(1L);
		Optional<Course> course2 = courseRepository.findOneWithStudents(2L);
		Optional<Course> course3 = courseRepository.findOneWithStudents(3L);
		Optional<Course> course4 = courseRepository.findOneWithStudents(4L);

		student1.addCourse(course1.orElse(null))
				.addCourse(course2.orElse(null))
				.addCourse(course3.orElse(null))
				.addCourse(course4.orElse(null));

		student2.addCourse(course3.orElse(null));

		student3.addCourse(course1.orElse(null))
				.addCourse(course3.orElse(null))
				.addCourse(course4.orElse(null));

		student4.addCourse(course3.orElse(null))
				.addCourse(course4.orElse(null));

		studentRepository.saveAll(List.of(student1, student2, student3, student4));

		System.out.println(student1);
		System.out.println(student2);
		System.out.println(student3);
		System.out.println(student4);

		Optional<Student> studentOptionalDB = studentRepository.findOneWithCourses(2L);

		if (studentOptionalDB.isPresent()) {

			Student studentDB = studentOptionalDB.get();

			Optional<Course> curOptional = courseRepository.findOneWithStudents(3L);

			curOptional.ifPresentOrElse(courseDB -> {

				studentDB.removeCourse(courseDB);

				studentRepository.save(studentDB);

				System.out.println("DELETE COURSE:");
				System.out.println(studentDB);

			}, () -> System.out.println("Course not found"));

		} else {

			System.out.println("Student not found");
		}

	}

	@Transactional
	public void manyToManyBidirectionalFind() {

		Optional<Student> studentOptional1 = studentRepository.findOneWithCourses(1L);
		Optional<Student> studentOptional2 = studentRepository.findOneWithCourses(2L);
		Optional<Student> studentOptional3 = studentRepository.findOneWithCourses(3L);
		Optional<Student> studentOptional4 = studentRepository.findOneWithCourses(4L);

		Student student1 = studentOptional1.orElse(null);
		Student student2 = studentOptional2.orElse(null);
		Student student3 = studentOptional3.orElse(null);
		Student student4 = studentOptional4.orElse(null);

		Optional<Course> course1 = courseRepository.findOneWithStudents(1L);
		Optional<Course> course2 = courseRepository.findOneWithStudents(2L);
		Optional<Course> course3 = courseRepository.findOneWithStudents(3L);
		Optional<Course> course4 = courseRepository.findOneWithStudents(4L);

		student1.addCourse(course1.orElse(null))
				.addCourse(course2.orElse(null))
				.addCourse(course3.orElse(null))
				.addCourse(course4.orElse(null));

		student2.addCourse(course3.orElse(null));

		student3.addCourse(course1.orElse(null))
				.addCourse(course3.orElse(null))
				.addCourse(course4.orElse(null));

		student4.addCourse(course3.orElse(null))
				.addCourse(course4.orElse(null));

		studentRepository.saveAll(List.of(student1, student2, student3, student4));

		System.out.println(student1);
		System.out.println(student2);
		System.out.println(student3);
		System.out.println(student4);

	}

	@Transactional
	public void manyToManyRemoveFindById() {

		Optional<Student> studentOptional1 = studentRepository.findById(1L);
		Optional<Student> studentOptional2 = studentRepository.findById(2L);
		Optional<Student> studentOptional3 = studentRepository.findById(3L);
		Optional<Student> studentOptional4 = studentRepository.findById(4L);

		Student student1 = studentOptional1.orElse(null);
		Student student2 = studentOptional2.orElse(null);
		Student student3 = studentOptional3.orElse(null);
		Student student4 = studentOptional4.orElse(null);

		Optional<Course> course1 = courseRepository.findById(1L);
		Optional<Course> course2 = courseRepository.findById(2L);
		Optional<Course> course3 = courseRepository.findById(3L);
		Optional<Course> course4 = courseRepository.findById(4L);

		student1.addCourse(course1.orElse(null))
				.addCourse(course2.orElse(null))
				.addCourse(course3.orElse(null))
				.addCourse(course4.orElse(null));

		student2.addCourse(course3.orElse(null));

		student3.addCourse(course1.orElse(null))
				.addCourse(course3.orElse(null))
				.addCourse(course4.orElse(null));

		student4.addCourse(course3.orElse(null))
				.addCourse(course4.orElse(null));

		studentRepository.saveAll(List.of(student1, student2, student3, student4));

		System.out.println(student1);
		System.out.println(student2);
		System.out.println(student3);
		System.out.println(student4);

		Optional<Student> studentOptionalDB = studentRepository.findOneWithCourses(6L);

		if (studentOptionalDB.isPresent()) {

			Student studentDB = studentOptionalDB.get();

			Optional<Course> curOptional = courseRepository.findOneWithStudents(5L);

			curOptional.ifPresentOrElse(courseDB -> {

				studentDB.removeCourse(courseDB);

				studentRepository.save(studentDB);

				System.out.println("DELETE:");
				System.out.println(studentDB);

			}, () -> System.out.println("Course not found"));

		} else {

			System.out.println("Student not found");
		}

	}

	@Transactional
	public void manyToManyBidirectionalRemove() {

		// Son student5 y student6 porque en el import.sql ya vienen 4 estudiantes
		// Son course5 y course6 porque en el import.sql ya vienen 4 cursos
		Student student5 = new Student("Eduard", "Nava");
		Student student6 = new Student("Migue", "Cayam");

		Course course5 = new Course("Calculo", "Eli Pavon");
		Course course6 = new Course("PYthon", "David Curva");

		/*
		 * student1.setCourses(Set.of(course1, course2, course3, course4));
		 * student2.setCourses(Set.of(course3));
		 * student3.setCourses(Set.of(course1, course3, course4));
		 * student4.setCourses(Set.of(course3, course4));
		 */
		student5.addCourse(course5).addCourse(course6);
		student6.addCourse(course5);

		studentRepository.saveAll(List.of(student5, student6));

		System.out.println(student5);
		System.out.println(student6);

		Optional<Student> studentOptionalDB = studentRepository.findOneWithCourses(6L);

		if (studentOptionalDB.isPresent()) {

			Student studentDB = studentOptionalDB.get();

			Optional<Course> curOptional = courseRepository.findOneWithStudents(5L);

			curOptional.ifPresentOrElse(courseDB -> {

				studentDB.removeCourse(courseDB);

				studentRepository.save(studentDB);

				System.out.println("DELETE:");
				System.out.println(studentDB);

			}, () -> System.out.println("Course not found"));

		} else {

			System.out.println("Student not found");
		}

	}

	@Transactional
	public void manyToManyBidirectional() {

		// Son student5 y student6 porque en el import.sql ya vienen 4 estudiantes
		// Son course5 y course6 porque en el import.sql ya vienen 4 cursos
		Student student5 = new Student("Eduard", "Nava");
		Student student6 = new Student("Migue", "Cayam");

		Course course5 = new Course("Calculo", "Eli Pavon");
		Course course6 = new Course("PYthon", "David Curva");

		/*
		 * student1.setCourses(Set.of(course1, course2, course3, course4));
		 * student2.setCourses(Set.of(course3));
		 * student3.setCourses(Set.of(course1, course3, course4));
		 * student4.setCourses(Set.of(course3, course4));
		 */
		student5.addCourse(course5).addCourse(course6);
		student6.addCourse(course5);

		studentRepository.saveAll(List.of(student5, student6));

		System.out.println(student5);
		System.out.println(student6);
	}

	@Transactional
	public void manyToManyRemoveFind() {

		Optional<Student> studentOptional1 = studentRepository.findById(1L);
		Optional<Student> studentOptional2 = studentRepository.findById(2L);
		Optional<Student> studentOptional3 = studentRepository.findById(3L);
		Optional<Student> studentOptional4 = studentRepository.findById(4L);

		Student student1 = studentOptional1.orElse(null);
		Student student2 = studentOptional2.orElse(null);
		Student student3 = studentOptional3.orElse(null);
		Student student4 = studentOptional4.orElse(null);

		Optional<Course> course1 = courseRepository.findById(1L);
		Optional<Course> course2 = courseRepository.findById(2L);
		Optional<Course> course3 = courseRepository.findById(3L);
		Optional<Course> course4 = courseRepository.findById(4L);

		student1.setCourses(
				Set.of(course1.orElse(null), course2.orElse(null), course3.orElse(null), course4.orElse(null)));
		student2.setCourses(Set.of(course3.orElse(null)));
		student3.setCourses(Set.of(course1.orElse(null), course3.orElse(null), course4.orElse(null)));
		student4.setCourses(Set.of(course3.orElse(null), course4.orElse(null)));

		studentRepository.saveAll(List.of(student1, student2, student3, student4));

		System.out.println(student1);
		System.out.println(student2);
		System.out.println(student3);
		System.out.println(student4);

		// Remover un curso a un estudiante

		Optional<Student> studentOptionalDB = studentRepository.findOneWithCourses(1L);

		if (studentOptionalDB.isPresent()) {

			Student studentDB = studentOptionalDB.get();

			Optional<Course> curOptional = courseRepository.findById(3L);

			curOptional.ifPresentOrElse(course -> {

				studentDB.getCourses().remove(course);

				studentRepository.save(studentDB);

				System.out.println("DELETE:");
				System.out.println(studentDB);

			}, () -> System.out.println("Course not found"));

		} else {

			System.out.println("Student not found");
		}

	}

	@Transactional
	public void manyToManyFindById() {

		Optional<Student> studentOptional1 = studentRepository.findById(1L);
		Optional<Student> studentOptional2 = studentRepository.findById(2L);
		Optional<Student> studentOptional3 = studentRepository.findById(3L);
		Optional<Student> studentOptional4 = studentRepository.findById(4L);

		Student student1 = studentOptional1.orElse(null);
		Student student2 = studentOptional2.orElse(null);
		Student student3 = studentOptional3.orElse(null);
		Student student4 = studentOptional4.orElse(null);

		Optional<Course> course1 = courseRepository.findById(1L);
		Optional<Course> course2 = courseRepository.findById(2L);
		Optional<Course> course3 = courseRepository.findById(3L);
		Optional<Course> course4 = courseRepository.findById(4L);

		student1.setCourses(
				Set.of(course1.orElse(null), course2.orElse(null), course3.orElse(null), course4.orElse(null)));
		student2.setCourses(Set.of(course3.orElse(null)));
		student3.setCourses(Set.of(course1.orElse(null), course3.orElse(null), course4.orElse(null)));
		student4.setCourses(Set.of(course3.orElse(null), course4.orElse(null)));

		studentRepository.saveAll(List.of(student1, student2, student3, student4));

		System.out.println(student1);
		System.out.println(student2);
		System.out.println(student3);
		System.out.println(student4);

	}

	@Transactional
	public void manyToMany() {

		Student student1 = new Student("Samy", "Lacost");
		Student student2 = new Student("Sofi", "Curva");
		Student student3 = new Student("Oscar", "Castill");
		Student student4 = new Student("Jaz", "Castill");

		Course course1 = new Course("Hacking", "Yorking Master");
		Course course2 = new Course("SpringBoot", "Edison Lozan");
		Course course3 = new Course("Psicomotricidad", "Marye Mor");
		Course course4 = new Course("Matematicas", "Edwin Barbos");

		student1.setCourses(Set.of(course1, course2, course3, course4));
		student2.setCourses(Set.of(course3));
		student3.setCourses(Set.of(course1, course3, course4));
		student4.setCourses(Set.of(course3, course4));

		studentRepository.saveAll(List.of(student1, student2, student3, student4));

		System.out.println(student1);
		System.out.println(student2);
		System.out.println(student3);
		System.out.println(student4);

	}

	@Transactional
	public void OneToOneBidirectionalFindById() {

		Long id = 2L;

		Optional<Client> clientOptional = clientRepository.findOne(id);

		clientOptional.ifPresentOrElse(client -> {

			ClientDetails clientDetails = new ClientDetails(true, 5000);
			client.setClientDetails(clientDetails);
			clientDetails.setClient(client);
			// solo se guarda en cliente porque es el padre
			clientRepository.save(client);

			System.out.println(client);

		}, () -> System.out.println("Client with ID '" + id + "' not found"));
	}

	@Transactional
	public void OneToOneBidirectional() {

		ClientDetails clientDetails = new ClientDetails(true, 5000);
		Client client = new Client("Sara", "Carden");

		client.setClientDetails(clientDetails);
		clientDetails.setClient(client);
		// solo se guarda en cliente porque es el padre
		clientRepository.save(client);

		System.out.println(client);
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
