package com.bea.common.security.service;

import javax.net.ssl.SSLSocketFactory;

public interface LDAPSSLSocketFactoryLookupService {
   SSLSocketFactory getSSLSocketFactory();
}
