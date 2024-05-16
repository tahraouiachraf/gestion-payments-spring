package org.demo.services;

import jakarta.transaction.Transactional;
import org.demo.dtos.NewPaymentDTO;
import org.demo.entities.Payment;
import org.demo.entities.PaymentStatus;
import org.demo.entities.PaymentType;
import org.demo.entities.Student;
import org.demo.repository.PaymentRepository;
import org.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {
    private StudentRepository studentRepository;
    private PaymentRepository paymentRepository;

    public PaymentService(StudentRepository studentRepository,PaymentRepository paymentRepository) {
        this.studentRepository=studentRepository;
        this.paymentRepository=paymentRepository;
    }

    public Payment savePayment(MultipartFile file, NewPaymentDTO newPaymentDTO) throws IOException {
        Path forlderPath = Paths.get(System.getProperty("user.home"),"enset-data","payments");
        if (!Files.exists(forlderPath)) {
            Files.createDirectories(forlderPath);
        }
        String fileName = UUID.randomUUID().toString();
        Path filePath = Paths.get(System.getProperty("user.home"),"enset-data","payments",fileName+".pdf");
        Files.copy(file.getInputStream(),filePath);
        Student student = studentRepository.findByCode(newPaymentDTO.getStudnetCode());
        Payment payment = Payment.builder()
                .type(newPaymentDTO.getType())
                .status(PaymentStatus.CREATED)
                .date(newPaymentDTO.getDate())
                .student(student)
                .amount(newPaymentDTO.getAmount())
                .file(filePath.toUri().toString())
                .build();
        return paymentRepository.save(payment);
    }

    public Payment updatePaymentStatus(PaymentStatus status,Long id) {
        Payment payment = paymentRepository.findById(id).get();
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }

    public byte[] getPaymentFile(Long paymentId) throws IOException {
        Payment payment = paymentRepository.findById(paymentId).get();
        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));
    }
}
