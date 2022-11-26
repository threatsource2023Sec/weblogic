package org.apache.xml.security.utils;

public abstract class XPathFactory {
   private static boolean xalanInstalled;

   protected static synchronized boolean isXalanInstalled() {
      return xalanInstalled;
   }

   public static XPathFactory newInstance() {
      if (!isXalanInstalled()) {
         return new JDKXPathFactory();
      } else {
         return (XPathFactory)(XalanXPathAPI.isInstalled() ? new XalanXPathFactory() : new JDKXPathFactory());
      }
   }

   public abstract XPathAPI newXPathAPI();

   static {
      try {
         Class funcTableClass = ClassLoaderUtils.loadClass("org.apache.xpath.compiler.FunctionTable", XPathFactory.class);
         if (funcTableClass != null) {
            xalanInstalled = true;
         }
      } catch (Exception var1) {
      }

   }
}
