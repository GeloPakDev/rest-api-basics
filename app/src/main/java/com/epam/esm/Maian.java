package com.epam.esm;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Maian {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        GiftCertificateDao giftCertificateDao = context.getBean(GiftCertificateDao.class);
        List<GiftCertificate> list = giftCertificateDao.findAll();
        System.out.println(list);


    }
}
