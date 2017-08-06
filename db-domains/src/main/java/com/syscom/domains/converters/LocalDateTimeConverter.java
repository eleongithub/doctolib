package com.syscom.domains.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Classe converter pour convertir les dates de type java {@LocalDateTime} vers le type SQL {@Timestamp} et vice-versa.
 *
 * Created by Eric Legba on 30/07/17.
 */
@Converter
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public java.sql.Timestamp convertToDatabaseColumn(java.time.LocalDateTime entityValue) {
        return entityValue == null ? null : java.sql.Timestamp.valueOf(entityValue);
    }

    @Override
    public java.time.LocalDateTime convertToEntityAttribute(java.sql.Timestamp dbValue) {
        return dbValue == null ? null : dbValue.toLocalDateTime();
    }
}
