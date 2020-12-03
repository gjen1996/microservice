package com.glen.glensystem.config;


import com.glen.glencommonsystem.util.BPwdEncoderUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.security.cert.CertificateException;
@Slf4j
public class KeyTest {
    public static void main(String[] args) throws FileNotFoundException, CertificateException {
        log.info("++++" + BPwdEncoderUtil.BCryptPassword("123456"));
    }
}