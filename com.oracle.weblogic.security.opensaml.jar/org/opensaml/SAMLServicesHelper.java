package org.opensaml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;

public class SAMLServicesHelper {
   private static DocumentBuilderFactory documentBuilderFactory = null;
   private static TransformerFactory transformerFactory = null;
   private static DebugLogger debugLogger = null;

   public static void setDocumentBuilderFactory(DocumentBuilderFactory var0) {
      documentBuilderFactory = var0;
   }

   public static DocumentBuilderFactory getDocumentBuilderFactory() {
      return documentBuilderFactory;
   }

   public static void setTransformerFactory(TransformerFactory var0) {
      transformerFactory = var0;
   }

   public static TransformerFactory getTransformerFactory() {
      return transformerFactory;
   }

   public static void setDebugLogger(DebugLogger var0) {
      debugLogger = var0;
   }

   public static DebugLogger getDebugLogger() {
      return debugLogger;
   }
}
