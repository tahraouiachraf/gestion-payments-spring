package org.demo.dtos;

import lombok.*;
import org.demo.entities.PaymentStatus;
import org.demo.entities.PaymentType;
import org.demo.entities.Student;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PaymentDTO {
    private Long id;
    private LocalDate date;
    private double amount;
    private PaymentType type;
    private PaymentStatus status;
}
