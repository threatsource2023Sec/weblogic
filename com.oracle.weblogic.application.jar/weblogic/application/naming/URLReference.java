package weblogic.application.naming;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

public class URLReference extends Reference {
   private static final long serialVersionUID = -6403136352910974574L;
   private String urlString;

   public URLReference(String urlString) throws MalformedURLException {
      super("java.net.URL", "weblogic.application.naming.URLReference$URLObjectFactory", (String)null);
      new URL(urlString);
      this.urlString = urlString;
   }

   public static class URLObjectFactory implements ObjectFactory {
      public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable environment) throws Exception {
         if (obj instanceof URLReference) {
            URLReference urlRef = (URLReference)obj;

            try {
               return new URL(urlRef.urlString);
            } catch (MalformedURLException var7) {
               throw new AssertionError("Unexpected MalformedURLException since we check the URL for correctness in the URLReferenceConstructor", var7);
            }
         } else {
            throw new AssertionError("Unable to produce a URL instance from an instance of " + obj.getClass());
         }
      }
   }
}
