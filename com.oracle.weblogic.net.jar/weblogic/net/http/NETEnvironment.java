package weblogic.net.http;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import weblogic.management.configuration.SSLMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;

public abstract class NETEnvironment {
   private static NETEnvironment singleton;

   public static NETEnvironment getNETEnvironment() {
      if (singleton == null) {
         try {
            singleton = (NETEnvironment)Class.forName("weblogic.net.http.WLSNETEnvironmentImpl").newInstance();
         } catch (Exception var3) {
            try {
               singleton = (NETEnvironment)Class.forName("weblogic.net.http.WLSClientNETEnvironmentImpl").newInstance();
            } catch (Exception var2) {
               throw new IllegalArgumentException(var2.toString());
            }
         }
      }

      return singleton;
   }

   static void setNETEnvironment(NETEnvironment helper) {
      singleton = helper;
   }

   public abstract boolean useSunHttpHandler();

   public abstract SSLMBean getSSLMBean(AuthenticatedSubject var1);

   public abstract URLConnection getHttpsURLConnection(URL var1, Proxy var2) throws IOException;
}
