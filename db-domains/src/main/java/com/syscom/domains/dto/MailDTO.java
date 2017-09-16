package com.syscom.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Eric Legba on 15/08/17.
 */
@Data
@EqualsAndHashCode(exclude = {"attachments", "datas"})
@ToString(exclude = {"attachments", "datas"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailDTO implements Serializable {

    private String from;
    private String to;
    private String subject;
    private String template;
    private List<File> attachments;
    private transient Map<String, Object> datas;
}
