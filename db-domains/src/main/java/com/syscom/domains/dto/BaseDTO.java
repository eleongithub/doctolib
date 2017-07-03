package com.syscom.domains.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * Created by ansible on 01/07/17.
 */
@Data
@ToString(exclude = {})
@EqualsAndHashCode(exclude = {})
public class BaseDTO implements Serializable {

    protected Date createDate;

    protected Date updateDate;
}
