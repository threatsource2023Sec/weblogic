package weblogic.security.SSL.jsseadapter;

import javax.net.ssl.SSLContext;
import weblogic.security.utils.SSLContextDelegate2;

abstract class JaSSLContext implements SSLContextDelegate2 {
   abstract SSLContext getSSLContext();
}
