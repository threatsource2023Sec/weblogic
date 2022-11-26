package com.bea.common.security.service;

import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.List;

public interface SAMLKeyInfoSpi {
   PrivateKey getKey();

   Certificate getCert();

   Certificate[] getChain();

   List getCertAsList();

   boolean isValid();
}
