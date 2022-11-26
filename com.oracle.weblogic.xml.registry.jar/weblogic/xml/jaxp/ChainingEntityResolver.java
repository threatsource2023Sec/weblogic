package weblogic.xml.jaxp;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.EmptyStackException;
import java.util.ListIterator;
import java.util.Stack;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import weblogic.xml.registry.RegistryEntityResolver;
import weblogic.xml.util.XMLConstants;

public class ChainingEntityResolver implements EntityResolver, XMLConstants {
   private Stack resolvers = new Stack();
   private Stack clonedResolvers;
   private boolean clone = true;
   private RegistryEntityResolver registryResolver;

   public void pushEntityResolver(EntityResolver er) {
      this.resolvers.push(er);
      this.clone = true;
      if (er instanceof RegistryEntityResolver) {
         this.registryResolver = (RegistryEntityResolver)er;
      }

   }

   public EntityResolver popEntityResolver() {
      EntityResolver popped = null;

      try {
         popped = (EntityResolver)this.resolvers.pop();
         if (popped instanceof RegistryEntityResolver && popped == this.registryResolver) {
            this.registryResolver = null;
         }
      } catch (EmptyStackException var3) {
      }

      this.clone = true;
      return popped;
   }

   public int getResolverCount() {
      return this.resolvers.size();
   }

   public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
      InputSource s = null;
      if (this.clone) {
         this.clonedResolvers = (Stack)this.resolvers.clone();
      }

      ListIterator i = this.clonedResolvers.listIterator(this.resolvers.size());

      while(i.hasPrevious()) {
         EntityResolver er = (EntityResolver)i.previous();

         try {
            s = er.resolveEntity(publicId, systemId);
         } catch (IOException var7) {
            throw new SAXException(var7);
         }

         if (s != null) {
            return this.getValidInputSource(er, s);
         }
      }

      return s;
   }

   public static String getEntityDescriptor(String publicID, String systemID) {
      return getEntityDescriptor(publicID, systemID, (String)null);
   }

   public static String getEntityDescriptor(String publicID, String systemID, String root) {
      if (root == null) {
         root = "";
      }

      String descr = "publicId = " + publicID + ", systemId = " + systemID;
      if (root != null && root.length() > 0) {
         descr = descr + ", root = " + root;
      }

      return descr;
   }

   public EntityResolver peekEntityResolver() {
      return (EntityResolver)this.resolvers.peek();
   }

   private InputSource getValidInputSource(EntityResolver er, InputSource s) {
      if (!(er instanceof RegistryEntityResolver) && !(er instanceof ChainingEntityResolver)) {
         if (this.registryResolver == null) {
            return s;
         } else if (s.getByteStream() == null && s.getCharacterStream() == null) {
            if (s.getSystemId() == null) {
               return s;
            } else {
               InputStream stream = null;

               try {
                  URL url = new URL(s.getSystemId());
                  if (url.getProtocol().equalsIgnoreCase("http")) {
                     URLConnection connection = url.openConnection();
                     stream = connection.getInputStream();
                     stream.read();
                  }

                  InputSource var25 = s;
                  return var25;
               } catch (MalformedURLException var21) {
               } catch (IOException var22) {
               } finally {
                  if (stream != null) {
                     try {
                        stream.close();
                     } catch (IOException var20) {
                     }
                  }

               }

               try {
                  InputSource s1 = this.registryResolver.resolveEntity(s.getPublicId(), s.getSystemId());
                  if (s1 != null) {
                     return s1;
                  }
               } catch (SAXException var18) {
               } catch (IOException var19) {
               }

               return s;
            }
         } else {
            return s;
         }
      } else {
         return s;
      }
   }
}
