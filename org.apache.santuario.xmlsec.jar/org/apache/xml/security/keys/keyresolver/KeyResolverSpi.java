package org.apache.xml.security.keys.keyresolver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xml.security.keys.storage.StorageResolver;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public abstract class KeyResolverSpi {
   protected Map properties;
   protected boolean globalResolver = false;
   protected boolean secureValidation;

   public void setSecureValidation(boolean secureValidation) {
      this.secureValidation = secureValidation;
   }

   public boolean engineCanResolve(Element element, String baseURI, StorageResolver storage) {
      throw new UnsupportedOperationException();
   }

   public PublicKey engineResolvePublicKey(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      throw new UnsupportedOperationException();
   }

   public PublicKey engineLookupAndResolvePublicKey(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      KeyResolverSpi tmp = this.cloneIfNeeded();
      return !tmp.engineCanResolve(element, baseURI, storage) ? null : tmp.engineResolvePublicKey(element, baseURI, storage);
   }

   private KeyResolverSpi cloneIfNeeded() throws KeyResolverException {
      KeyResolverSpi tmp = this;
      if (this.globalResolver) {
         try {
            tmp = (KeyResolverSpi)this.getClass().newInstance();
         } catch (InstantiationException var3) {
            throw new KeyResolverException(var3, "");
         } catch (IllegalAccessException var4) {
            throw new KeyResolverException(var4, "");
         }
      }

      return tmp;
   }

   public X509Certificate engineResolveX509Certificate(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      throw new UnsupportedOperationException();
   }

   public X509Certificate engineLookupResolveX509Certificate(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      KeyResolverSpi tmp = this.cloneIfNeeded();
      return !tmp.engineCanResolve(element, baseURI, storage) ? null : tmp.engineResolveX509Certificate(element, baseURI, storage);
   }

   public SecretKey engineResolveSecretKey(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      throw new UnsupportedOperationException();
   }

   public SecretKey engineLookupAndResolveSecretKey(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      KeyResolverSpi tmp = this.cloneIfNeeded();
      return !tmp.engineCanResolve(element, baseURI, storage) ? null : tmp.engineResolveSecretKey(element, baseURI, storage);
   }

   public PrivateKey engineLookupAndResolvePrivateKey(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      return null;
   }

   public void engineSetProperty(String key, String value) {
      if (this.properties == null) {
         this.properties = new HashMap();
      }

      this.properties.put(key, value);
   }

   public String engineGetProperty(String key) {
      return this.properties == null ? null : (String)this.properties.get(key);
   }

   public boolean understandsProperty(String propertyToTest) {
      if (this.properties == null) {
         return false;
      } else {
         return this.properties.get(propertyToTest) != null;
      }
   }

   public void setGlobalResolver(boolean globalResolver) {
      this.globalResolver = globalResolver;
   }

   protected static Element getDocFromBytes(byte[] bytes, boolean secureValidation) throws KeyResolverException {
      DocumentBuilder db = null;

      Element var6;
      try {
         InputStream is = new ByteArrayInputStream(bytes);
         Throwable var4 = null;

         try {
            db = XMLUtils.createDocumentBuilder(false, secureValidation);
            Document doc = db.parse(is);
            var6 = doc.getDocumentElement();
         } catch (Throwable var28) {
            var4 = var28;
            throw var28;
         } finally {
            if (var4 != null) {
               try {
                  is.close();
               } catch (Throwable var27) {
                  var4.addSuppressed(var27);
               }
            } else {
               is.close();
            }

         }
      } catch (SAXException var30) {
         throw new KeyResolverException(var30);
      } catch (IOException var31) {
         throw new KeyResolverException(var31);
      } catch (ParserConfigurationException var32) {
         throw new KeyResolverException(var32);
      } finally {
         if (db != null) {
            XMLUtils.repoolDocumentBuilder(db);
         }

      }

      return var6;
   }
}
