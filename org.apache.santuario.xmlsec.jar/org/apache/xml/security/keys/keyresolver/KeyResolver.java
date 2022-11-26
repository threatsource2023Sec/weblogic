package org.apache.xml.security.keys.keyresolver;

import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.crypto.SecretKey;
import org.apache.xml.security.keys.keyresolver.implementations.DEREncodedKeyValueResolver;
import org.apache.xml.security.keys.keyresolver.implementations.DSAKeyValueResolver;
import org.apache.xml.security.keys.keyresolver.implementations.ECKeyValueResolver;
import org.apache.xml.security.keys.keyresolver.implementations.KeyInfoReferenceResolver;
import org.apache.xml.security.keys.keyresolver.implementations.RSAKeyValueResolver;
import org.apache.xml.security.keys.keyresolver.implementations.RetrievalMethodResolver;
import org.apache.xml.security.keys.keyresolver.implementations.X509CertificateResolver;
import org.apache.xml.security.keys.keyresolver.implementations.X509DigestResolver;
import org.apache.xml.security.keys.keyresolver.implementations.X509IssuerSerialResolver;
import org.apache.xml.security.keys.keyresolver.implementations.X509SKIResolver;
import org.apache.xml.security.keys.keyresolver.implementations.X509SubjectNameResolver;
import org.apache.xml.security.keys.storage.StorageResolver;
import org.apache.xml.security.utils.ClassLoaderUtils;
import org.apache.xml.security.utils.JavaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class KeyResolver {
   private static final Logger LOG = LoggerFactory.getLogger(KeyResolver.class);
   private static List resolverVector = new CopyOnWriteArrayList();
   private final KeyResolverSpi resolverSpi;

   private KeyResolver(KeyResolverSpi keyResolverSpi) {
      this.resolverSpi = keyResolverSpi;
   }

   public static int length() {
      return resolverVector.size();
   }

   public static final X509Certificate getX509Certificate(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      Iterator var3 = resolverVector.iterator();

      while(var3.hasNext()) {
         KeyResolver resolver = (KeyResolver)var3.next();
         if (resolver == null) {
            Object[] exArgs = new Object[]{element != null && element.getNodeType() == 1 ? element.getTagName() : "null"};
            throw new KeyResolverException("utils.resolver.noClass", exArgs);
         }

         LOG.debug("check resolvability by class {}", resolver.getClass());
         X509Certificate cert = resolver.resolveX509Certificate(element, baseURI, storage);
         if (cert != null) {
            return cert;
         }
      }

      Object[] exArgs = new Object[]{element != null && element.getNodeType() == 1 ? element.getTagName() : "null"};
      throw new KeyResolverException("utils.resolver.noClass", exArgs);
   }

   public static final PublicKey getPublicKey(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      Iterator var3 = resolverVector.iterator();

      while(var3.hasNext()) {
         KeyResolver resolver = (KeyResolver)var3.next();
         if (resolver == null) {
            Object[] exArgs = new Object[]{element != null && element.getNodeType() == 1 ? element.getTagName() : "null"};
            throw new KeyResolverException("utils.resolver.noClass", exArgs);
         }

         LOG.debug("check resolvability by class {}", resolver.getClass());
         PublicKey cert = resolver.resolvePublicKey(element, baseURI, storage);
         if (cert != null) {
            return cert;
         }
      }

      Object[] exArgs = new Object[]{element != null && element.getNodeType() == 1 ? element.getTagName() : "null"};
      throw new KeyResolverException("utils.resolver.noClass", exArgs);
   }

   public static void register(String className, boolean globalResolver) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
      JavaUtils.checkRegisterPermission();
      KeyResolverSpi keyResolverSpi = (KeyResolverSpi)ClassLoaderUtils.loadClass(className, KeyResolver.class).newInstance();
      keyResolverSpi.setGlobalResolver(globalResolver);
      register(keyResolverSpi, false);
   }

   public static void registerAtStart(String className, boolean globalResolver) {
      JavaUtils.checkRegisterPermission();
      KeyResolverSpi keyResolverSpi = null;
      Exception ex = null;

      try {
         keyResolverSpi = (KeyResolverSpi)ClassLoaderUtils.loadClass(className, KeyResolver.class).newInstance();
         keyResolverSpi.setGlobalResolver(globalResolver);
         register(keyResolverSpi, true);
      } catch (ClassNotFoundException var5) {
         ex = var5;
      } catch (IllegalAccessException var6) {
         ex = var6;
      } catch (InstantiationException var7) {
         ex = var7;
      }

      if (ex != null) {
         throw (IllegalArgumentException)(new IllegalArgumentException("Invalid KeyResolver class name")).initCause((Throwable)ex);
      }
   }

   public static void register(KeyResolverSpi keyResolverSpi, boolean start) {
      JavaUtils.checkRegisterPermission();
      KeyResolver resolver = new KeyResolver(keyResolverSpi);
      if (start) {
         resolverVector.add(0, resolver);
      } else {
         resolverVector.add(resolver);
      }

   }

   public static void registerClassNames(List classNames) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
      JavaUtils.checkRegisterPermission();
      List keyResolverList = new ArrayList(classNames.size());
      Iterator var2 = classNames.iterator();

      while(var2.hasNext()) {
         String className = (String)var2.next();
         KeyResolverSpi keyResolverSpi = (KeyResolverSpi)ClassLoaderUtils.loadClass(className, KeyResolver.class).newInstance();
         keyResolverSpi.setGlobalResolver(false);
         keyResolverList.add(new KeyResolver(keyResolverSpi));
      }

      resolverVector.addAll(keyResolverList);
   }

   public static void registerDefaultResolvers() {
      List keyResolverList = new ArrayList();
      keyResolverList.add(new KeyResolver(new RSAKeyValueResolver()));
      keyResolverList.add(new KeyResolver(new DSAKeyValueResolver()));
      keyResolverList.add(new KeyResolver(new X509CertificateResolver()));
      keyResolverList.add(new KeyResolver(new X509SKIResolver()));
      keyResolverList.add(new KeyResolver(new RetrievalMethodResolver()));
      keyResolverList.add(new KeyResolver(new X509SubjectNameResolver()));
      keyResolverList.add(new KeyResolver(new X509IssuerSerialResolver()));
      keyResolverList.add(new KeyResolver(new DEREncodedKeyValueResolver()));
      keyResolverList.add(new KeyResolver(new KeyInfoReferenceResolver()));
      keyResolverList.add(new KeyResolver(new X509DigestResolver()));
      keyResolverList.add(new KeyResolver(new ECKeyValueResolver()));
      resolverVector.addAll(keyResolverList);
   }

   public PublicKey resolvePublicKey(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      return this.resolverSpi.engineLookupAndResolvePublicKey(element, baseURI, storage);
   }

   public X509Certificate resolveX509Certificate(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      return this.resolverSpi.engineLookupResolveX509Certificate(element, baseURI, storage);
   }

   public SecretKey resolveSecretKey(Element element, String baseURI, StorageResolver storage) throws KeyResolverException {
      return this.resolverSpi.engineLookupAndResolveSecretKey(element, baseURI, storage);
   }

   public void setProperty(String key, String value) {
      this.resolverSpi.engineSetProperty(key, value);
   }

   public String getProperty(String key) {
      return this.resolverSpi.engineGetProperty(key);
   }

   public boolean understandsProperty(String propertyToTest) {
      return this.resolverSpi.understandsProperty(propertyToTest);
   }

   public String resolverClassName() {
      return this.resolverSpi.getClass().getName();
   }

   public static Iterator iterator() {
      return new ResolverIterator(resolverVector);
   }

   static class ResolverIterator implements Iterator {
      List res;
      Iterator it;

      public ResolverIterator(List list) {
         this.res = list;
         this.it = this.res.iterator();
      }

      public boolean hasNext() {
         return this.it.hasNext();
      }

      public KeyResolverSpi next() {
         KeyResolver resolver = (KeyResolver)this.it.next();
         if (resolver == null) {
            throw new RuntimeException("utils.resolver.noClass");
         } else {
            return resolver.resolverSpi;
         }
      }

      public void remove() {
         throw new UnsupportedOperationException("Can't remove resolvers using the iterator");
      }
   }
}
