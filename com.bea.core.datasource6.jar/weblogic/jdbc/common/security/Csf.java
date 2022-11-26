package weblogic.jdbc.common.security;

import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.Properties;
import oracle.security.jps.JpsContext;
import oracle.security.jps.JpsContextFactory;
import oracle.security.jps.service.credstore.Credential;
import oracle.security.jps.service.credstore.CredentialStore;
import oracle.security.jps.service.credstore.DataSourceCredential;

public class Csf {
   public static void getCredential(Properties props) throws Exception {
      final String url = props.getProperty("url");
      String saveurl = url;
      if (url == null) {
         throw new Exception("URL not set");
      } else {
         int i = url.indexOf(":");
         if (i != -1 && url.substring(0, i + 1).toLowerCase().equals("csf:")) {
            url = url.substring(i + 1);
            i = url.indexOf(":");
            if (i == -1) {
               throw new Exception("Invalid URL; must be csf:map:key;" + saveurl);
            } else {
               final String map = url.substring(0, i);
               final String key = url.substring(i + 1);
               DataSourceCredential dbcred = (DataSourceCredential)AccessController.doPrivileged(new PrivilegedExceptionAction() {
                  public DataSourceCredential run() throws Exception {
                     return Csf.doOp(url, map, key);
                  }
               });
               String username = dbcred.getName();
               char[] password = dbcred.getPassword();
               url = dbcred.getURL();
               if (username != null && password != null && url != null) {
                  props.setProperty("user", username);
                  props.setProperty("password", new String(password));
                  props.setProperty("url", url);
               } else {
                  throw new Exception("Failed to get credentials for Map/key \"" + map + "/" + key + "\"");
               }
            }
         } else {
            throw new Exception("Invalid URL:" + url);
         }
      }
   }

   public static DataSourceCredential doOp(String url, String map, String key) throws Exception {
      Credential cred = null;
      CredentialStore credentialStore = null;
      JpsContextFactory ctxFactory = null;
      JpsContext ctx = null;

      try {
         ctxFactory = JpsContextFactory.getContextFactory();
      } catch (NoClassDefFoundError var11) {
         throw new Exception("Credential software is not available");
      } catch (Throwable var12) {
         throw new Exception("Credential software is not available", var12);
      }

      try {
         ctx = ctxFactory.getContext();
      } catch (Exception var10) {
         throw new Exception("Credential store is not available: ctxFactory.getContext() failed", var10);
      }

      try {
         credentialStore = (CredentialStore)ctx.getServiceInstance(CredentialStore.class);
      } catch (Exception var9) {
         throw new Exception("Credential store is not available: ctx.getServiceInstance(CredentialStore.class failed", var9);
      }

      try {
         cred = credentialStore.getCredential(map, key);
      } catch (Exception var8) {
         throw new Exception("Failed to get DataSourceCredential for map/key \"" + map + "/" + key + "\"", var8);
      }

      if (cred instanceof DataSourceCredential) {
         DataSourceCredential dbcred = (DataSourceCredential)cred;
         return dbcred;
      } else {
         throw new Exception("Map/key \"" + map + "/" + key + "\" is not a DataSourceCredential");
      }
   }
}
