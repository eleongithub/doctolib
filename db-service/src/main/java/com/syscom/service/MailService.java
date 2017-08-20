package com.syscom.service;

import com.syscom.domains.dto.MailDTO;

/**
 * Created by ansible on 15/08/17.
 */
public interface MailService {

    void sendMessage(MailDTO mailDTO);
}
