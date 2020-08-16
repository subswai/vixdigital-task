package com.vix.digital.services.online.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Service {

    private static final long serialVersionUID = 1871977717947082854L;

    public Service() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "name is mandatory")
    @Size(min = 2, message = "name should have at least 2 characters")
    private String name;

    @NotNull(message = "url is mandatory")
    @Size(min = 2, message = "name should have at least 2 characters")
    private String url;

    @Enumerated(EnumType.STRING)
    @JsonDeserialize(as = ServiceState.class)
    private ServiceState serviceState;

    @Enumerated(EnumType.STRING)
    private HttpMethodType methodType;

    //@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "CREATED_TIME", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdTime;

    //@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @UpdateTimestamp
    private LocalDateTime lastUpdatedTime;


}
