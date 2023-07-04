package com.mahdi.rumi.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.redis.core.RedisHash;

@Data
@Entity
@Table(name ="quotetbl")
@AllArgsConstructor
@NoArgsConstructor
public class Quote {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
        @Column(nullable = false, updatable = false)
        private UUID id;

        @NotNull(message = "Quote text is required")
        private String text;
        private String AddedAt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss z")
                .format(new Date());

}
