package weblogic.net.http;

import weblogic.security.SSL.SSLSocketFactory;

class WLSSSLSocketFactoryAdapter extends SSLSocketFactory {
   public WLSSSLSocketFactoryAdapter(javax.net.ssl.SSLSocketFactory factory) {
      super(factory);
   }
}
