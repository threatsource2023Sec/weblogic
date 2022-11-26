package com.bea.common.security.servicecfg;

import javax.net.ssl.SSLContext;

public interface LDAPSSLSocketFactoryLookupServiceConfig {
   SSLContext getSSLContext();
}
