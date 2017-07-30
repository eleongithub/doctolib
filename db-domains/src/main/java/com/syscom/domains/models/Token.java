package com.syscom.domains.models;

import com.syscom.domains.converters.LocalDateTimeConverter;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Eric Legba on 27/07/17.
 */
@Data
@Builder
@ToString(exclude = {"id"})
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {})
@Entity
@Table(name = "T_TOKEN")
public class Token extends BaseBean {

    @Id
    @SequenceGenerator(name = "TOKEN_SEQUENCE_GENERATOR", sequenceName = "TOKEN_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TOKEN_SEQUENCE_GENERATOR")
    @Column(name = "T_ID")
    private Long id;

    @Column(name = "T_VALUE", nullable = false)
    private String value;

    @Column(name = "T_EXPIRATION_DATE", nullable = false)
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime dateExpiration;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "T_USER_ID")
    private User user;

}
