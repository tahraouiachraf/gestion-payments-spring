package org.demo;

import org.demo.entities.Payment;
import org.demo.entities.PaymentStatus;
import org.demo.entities.PaymentType;
import org.demo.entities.Student;
import org.demo.repository.PaymentRepository;
import org.demo.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository,
										PaymentRepository paymentRepository){
		return args -> {
			studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
							.firstName("Achraf").code("7836").programId("SDIA").build());
			studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
					.firstName("Mohamed").code("111220").programId("SDIA").build());
			studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
					.firstName("Zakaria").code("1112").programId("SDIA").build());
			studentRepository.save(Student.builder().id(UUID.randomUUID().toString())
					.firstName("Iliass").code("122").programId("SDIA").build());

			PaymentType[] paymentTypes = PaymentType.values();
			Random random = new Random();
			studentRepository.findAll().forEach(st->{
				for (int i=0;i<10;i++){
					int index = random.nextInt(paymentTypes.length);
					Payment payment = Payment.builder()
							.amount(1000+(int)(Math.random()*20000))
							.type(paymentTypes[index])
							.status(PaymentStatus.CREATED)
							.date(LocalDate.now())
							.student(st)
							.build();
					paymentRepository.save(payment);
				}
			});
		};
	}
}
