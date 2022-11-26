package weblogic.jms.safclient.jndi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Hashtable;
import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import weblogic.jms.extensions.ClientSAF;
import weblogic.jms.extensions.ClientSAFDuplicateException;
import weblogic.jms.extensions.ClientSAFFactory;

public class InitialContextFactoryImpl implements InitialContextFactory {
   private static NamingException getNamingException(Throwable th) {
      NamingException retVal = new NamingException(th.getMessage());
      retVal.setRootCause(th);
      return retVal;
   }

   public Context getInitialContext(Hashtable environment) throws NamingException {
      String providerURL = (String)environment.get("java.naming.provider.url");
      ClientSAF provider;
      if (providerURL == null) {
         try {
            provider = ClientSAFFactory.getClientSAF();
         } catch (ClientSAFDuplicateException var58) {
            provider = var58.getDuplicate();
         } catch (JMSException var59) {
            throw getNamingException(var59);
         }
      } else {
         URL url;
         try {
            url = new URL(providerURL);
         } catch (MalformedURLException var57) {
            throw getNamingException(var57);
         }

         URI uri;
         try {
            uri = new URI(url.toString());
         } catch (URISyntaxException var56) {
            uri = null;
         }

         File parentDirectory = null;
         if (uri != null) {
            File uriFile;
            try {
               uriFile = new File(uri);
            } catch (IllegalArgumentException var55) {
               uriFile = null;
            }

            if (uriFile != null) {
               parentDirectory = uriFile.getParentFile();
            }
         }

         InputStream stream;
         try {
            stream = url.openStream();
         } catch (IOException var54) {
            throw getNamingException(var54);
         }

         try {
            if (parentDirectory == null) {
               try {
                  provider = ClientSAFFactory.getClientSAF(stream);
               } catch (ClientSAFDuplicateException var51) {
                  provider = var51.getDuplicate();
               } catch (JMSException var52) {
                  throw getNamingException(var52);
               }
            } else {
               try {
                  provider = ClientSAFFactory.getClientSAF(parentDirectory, stream);
               } catch (ClientSAFDuplicateException var49) {
                  provider = var49.getDuplicate();
               } catch (JMSException var50) {
                  throw getNamingException(var50);
               }
            }
         } finally {
            try {
               stream.close();
            } catch (IOException var46) {
               throw getNamingException(var46);
            }
         }
      }

      Object pw = environment.get("java.naming.security.credentials");
      char[] internalPW = null;
      boolean isLocal = false;
      if (pw != null) {
         if (pw instanceof char[]) {
            internalPW = (char[])((char[])pw);
         } else {
            if (!(pw instanceof String)) {
               throw new NamingException("The SECURITY_CREDENTIALS field must either be a String or char[].    Instead, it is of type " + pw.getClass().getName());
            }

            internalPW = ((String)pw).toCharArray();
            isLocal = true;
         }
      }

      boolean var29 = false;

      try {
         var29 = true;
         provider.open(internalPW);
         var29 = false;
      } catch (JMSException var48) {
         throw getNamingException(var48);
      } finally {
         if (var29) {
            if (isLocal) {
               for(int lcv = 0; lcv < internalPW.length; ++lcv) {
                  internalPW[lcv] = 'y';
               }
            }

         }
      }

      if (isLocal) {
         for(int lcv = 0; lcv < internalPW.length; ++lcv) {
            internalPW[lcv] = 'y';
         }
      }

      try {
         return provider.getContext();
      } catch (JMSException var47) {
         throw getNamingException(var47);
      }
   }
}
