package com.bea.common.security.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class SAML2ClassLoader extends URLClassLoader {
   private static String FEATURENAME = "weblogic.saml2.modules";
   private static final String SAML2_PROVIDER_LIB = "saml2-security-providers.jar";
   private static List urlsProviderIncluded = new ArrayList();
   private static List urlsProviderNotIncluded = new ArrayList();
   private static URL featureSetURL;
   private static volatile Exception exceptionToThrow = null;
   private ClassLoader cl = null;

   public SAML2ClassLoader(ClassLoader parent, boolean providerIncluded) throws Exception {
      super(getFilterJars(providerIncluded), new ClassLoaderDelegate(parent));
   }

   public void setThreadConextClassLoader(ClassLoader classloader) {
      this.cl = classloader;
   }

   public ClassLoader getThreadConextClassLoader() {
      return this.cl;
   }

   private static URL[] getFilterJars(boolean providerIncluded) throws Exception {
      if (exceptionToThrow != null) {
         throw exceptionToThrow;
      } else {
         return providerIncluded ? (URL[])urlsProviderIncluded.toArray(new URL[0]) : (URL[])urlsProviderNotIncluded.toArray(new URL[0]);
      }
   }

   static {
      try {
         Class homeClass = Class.forName("weblogic.Home");
         File wlsHome = (File)homeClass.getMethod("getFile").invoke((Object)null);
         File directory = new File(new File(wlsHome.getParentFile(), "modules"), "features");
         String featurejar = FEATURENAME + ".jar";
         File featureSetFile = new File(directory, featurejar);
         if (featureSetFile.exists()) {
            featureSetURL = featureSetFile.toURL();
         }

         if (featureSetURL == null) {
            exceptionToThrow = new Exception("Can not find the saml2 featureset manifest file: " + (featureSetFile != null ? featureSetFile.getPath() : featurejar));
         }

         if (featureSetURL != null) {
            urlsProviderIncluded.add(featureSetURL);
            urlsProviderNotIncluded.add(featureSetURL);
         }

         urlsProviderIncluded.add((new File(new File(new File(wlsHome, "lib"), "mbeantypes"), "saml2-security-providers.jar")).toURL());
      } catch (Exception var5) {
         exceptionToThrow = var5;
      }

   }

   private static class ClassLoaderDelegate extends ClassLoader {
      private static final List filterList = Arrays.asList("com.bea.common.security.saml2.utils", "weblogic.security.utils.SAML", "com.bea.security.saml2.", "org.opensaml.", "com.bea.core.log4j.", "org.apache.log4j.", "org.apache.commons.logging.", "org.apache.xml.serializer.", "org.apache.bcel.", "org.apache.regexp.", "org.apache.xalan.", "org.apache.xml.", "org.apache.xpath.", "org.apache.env.", "org.apache.xmlcommons.", "org.apache.html.dom.", "org.apache.wml.", "java_cup.runtime.", "javolution.", "org.joda.time.");
      private static final List unlessList = Arrays.asList("weblogic.security.utils.SAMLAssertionInfo", "weblogic.security.utils.SAMLAssertionInfoFactory", "com.bea.security.saml2.providers.registry.", "com.bea.security.saml2.providers.SAML2CredentialMapperMBean", "com.bea.security.saml2.providers.SAML2IdentityAsserterMBean", "com.bea.security.saml2.providers.SAML2IdPPartnerRegistryMBean", "com.bea.security.saml2.providers.SAML2InvalidConfigExceptionMBean", "com.bea.security.saml2.providers.SAML2PartnerRegistryMBean", "com.bea.security.saml2.providers.SAML2SPPartnerRegistryMBean", "com.bea.security.saml2.servlet.", "com.bea.security.saml2.service.sso.AuthnRequestWrapper", "com.bea.security.saml2.providers.SAML2CredentialNameMapper", "com.bea.security.saml2.providers.SAML2IdentityAsserterNameMapper", "com.bea.security.saml2.providers.SAML2NameMapperInfo", "com.bea.security.saml2.providers.SAML2AttributeInfo", "com.bea.security.saml2.providers.SAML2AttributeStatementInfo", "com.bea.security.saml2.providers.SAML2CredentialAttributeMapper", "com.bea.security.saml2.providers.SAML2IdentityAsserterAttributeMapper");
      private static final List resourceFilterList = Arrays.asList("commons-logging.properties", "log4j.", "org/apache/xml/security", "common-config.xml", "saml2-", "soap11-", "schema/xmltooling-config.xsd", "org/joda/time");
      private final ClassLoader parent;

      public ClassLoaderDelegate(ClassLoader parent) {
         this.parent = parent;
      }

      private void matchesFilter(String name) throws ClassNotFoundException {
         Iterator it = filterList.iterator();

         while(it.hasNext()) {
            if (name.startsWith((String)it.next())) {
               Iterator uit = unlessList.iterator();

               while(uit.hasNext()) {
                  if (name.startsWith((String)uit.next())) {
                     return;
                  }
               }

               throw new ClassNotFoundException(name);
            }
         }

      }

      public Class loadClass(String name) throws ClassNotFoundException {
         return this.loadClass(name, false);
      }

      public Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
         Class c = this.findClass(name);
         if (resolve) {
            this.resolveClass(c);
         }

         return c;
      }

      protected Class findClass(String name) throws ClassNotFoundException {
         this.matchesFilter(name);
         return this.getParent().loadClass(name);
      }

      private boolean matchesResourceFilter(String name) {
         Iterator it = resourceFilterList.iterator();

         do {
            if (!it.hasNext()) {
               return false;
            }
         } while(!name.startsWith((String)it.next()));

         return true;
      }

      public URL getResource(String name) {
         return this.matchesResourceFilter(name) ? null : this.parent.getResource(name);
      }

      public Enumeration getResources(String name) throws IOException {
         return this.matchesResourceFilter(name) ? new Enumeration() {
            public boolean hasMoreElements() {
               return false;
            }

            public Object nextElement() {
               throw new NoSuchElementException();
            }
         } : this.parent.getResources(name);
      }
   }
}
