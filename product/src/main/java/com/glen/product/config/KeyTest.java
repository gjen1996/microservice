package com.glen.appcustomerlogin.config;

import com.glen.appcustomerlogin.config.BPwdEncoderUtil;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

@Slf4j
public class KeyTest {
    public static void main(String[] args) throws FileNotFoundException, CertificateException {
        log.info("++++" + BPwdEncoderUtil.BCryptPassword("123456"));
    }
}