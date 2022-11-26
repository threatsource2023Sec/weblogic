package com.bea.xbean.soap;

import com.bea.xml.SystemProperties;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

class FactoryFinder {
   private static Object newInstance(String factoryClassName) throws SOAPException {
      ClassLoader classloader = null;

      try {
         classloader = Thread.currentThread().getContextClassLoader();
      } catch (Exception var7) {
         throw new SOAPException(var7.toString(), var7);
      }

      try {
         Class factory = null;
         if (classloader == null) {
            factory = Class.forName(factoryClassName);
         } else {
            try {
               factory = classloader.loadClass(factoryClassName);
            } catch (ClassNotFoundException var4) {
            }
         }

         if (factory == null) {
            classloader = FactoryFinder.class.getClassLoader();
            factory = classloader.loadClass(factoryClassName);
         }

         return factory.newInstance();
      } catch (ClassNotFoundException var5) {
         throw new SOAPException("Provider " + factoryClassName + " not found", var5);
      } catch (Exception var6) {
         throw new SOAPException("Provider " + factoryClassName + " could not be instantiated: " + var6, var6);
      }
   }

   static Object find(String factoryPropertyName, String defaultFactoryClassName) throws SOAPException {
      String factoryResource;
      try {
         factoryResource = SystemProperties.getProperty(factoryPropertyName);
         if (factoryResource != null) {
            return newInstance(factoryResource);
         }
      } catch (SecurityException var9) {
      }

      try {
         factoryResource = SystemProperties.getProperty("java.home") + File.separator + "lib" + File.separator + "jaxm.properties";
         File file = new File(factoryResource);
         if (file.exists()) {
            FileInputStream fileInput = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(fileInput);
            fileInput.close();
            String factoryClassName = properties.getProperty(factoryPropertyName);
            return newInstance(factoryClassName);
         }
      } catch (Exception var8) {
      }

      factoryResource = "META-INF/services/" + factoryPropertyName;

      try {
         InputStream inputstream = getResource(factoryResource);
         if (inputstream != null) {
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream, "UTF-8"));
            String factoryClassName = bufferedreader.readLine();
            bufferedreader.close();
            if (factoryClassName != null && !"".equals(factoryClassName)) {
               return newInstance(factoryClassName);
            }
         }
      } catch (Exception var7) {
      }

      if (defaultFactoryClassName == null) {
         throw new SOAPException("Provider for " + factoryPropertyName + " cannot be found", (Throwable)null);
      } else {
         return newInstance(defaultFactoryClassName);
      }
   }

   private static InputStream getResource(String factoryResource) {
      ClassLoader classloader = null;

      try {
         classloader = Thread.currentThread().getContextClassLoader();
      } catch (SecurityException var3) {
      }

      InputStream inputstream;
      if (classloader == null) {
         inputstream = ClassLoader.getSystemResourceAsStream(factoryResource);
      } else {
         inputstream = classloader.getResourceAsStream(factoryResource);
      }

      if (inputstream == null) {
         inputstream = FactoryFinder.class.getClassLoader().getResourceAsStream(factoryResource);
      }

      return inputstream;
   }
}
