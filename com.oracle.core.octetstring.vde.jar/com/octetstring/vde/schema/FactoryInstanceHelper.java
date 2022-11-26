package com.octetstring.vde.schema;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;

public class FactoryInstanceHelper {
   private static final String SAX_EXTERNAL_GENERAL_ENTITIES = "http://xml.org/sax/features/external-general-entities";
   private static final String SAX_EXTERNAL_PARAMETER_ENTITIES = "http://xml.org/sax/features/external-parameter-entities";
   private static final String XERCES2_EXTERNAL_GENERAL_ENTITIES = "http://xerces.apache.org/xerces2-j/features.html#external-general-entities";
   private static final String XERCES2_EXTERNAL_PARAMETER_ENTITIES = "http://xerces.apache.org/xerces2-j/features.html#external-parameter-entities";
   private static final String XERCES1_EXTERNAL_GENERAL_ENTITIES = "http://xerces.apache.org/xerces-j/features.html#external-general-entities";
   private static final String XERCES1_EXTERNAL_PARAMETER_ENTITIES = "http://xerces.apache.org/xerces-j/features.html#external-parameter-entities";
   private static DocumentBuilderFactory documentBuilderFactory;
   private static SAXParserFactory saxParserFactory;

   static DocumentBuilderFactory getDocumentBuilderInstance() {
      return documentBuilderFactory;
   }

   static SAXParserFactory getSAXParserInstance() {
      return saxParserFactory;
   }

   static {
      Class wlSAXParserFactory;
      try {
         wlSAXParserFactory = Class.forName("weblogic.xml.jaxp.WebLogicDocumentBuilderFactory");
         documentBuilderFactory = (DocumentBuilderFactory)((DocumentBuilderFactory)wlSAXParserFactory.newInstance());
      } catch (Exception var9) {
         documentBuilderFactory = DocumentBuilderFactory.newInstance();
      }

      try {
         wlSAXParserFactory = Class.forName("weblogic.xml.jaxp.WebLogicSAXParserFactory");
         saxParserFactory = (SAXParserFactory)((SAXParserFactory)wlSAXParserFactory.newInstance());
      } catch (Exception var8) {
         saxParserFactory = SAXParserFactory.newInstance();
      }

      try {
         saxParserFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
      } catch (Throwable var7) {
         try {
            saxParserFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            saxParserFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
         } catch (Throwable var6) {
            try {
               saxParserFactory.setFeature("http://xerces.apache.org/xerces2-j/features.html#external-general-entities", false);
               saxParserFactory.setFeature("http://xerces.apache.org/xerces2-j/features.html#external-parameter-entities", false);
            } catch (Throwable var5) {
               try {
                  saxParserFactory.setFeature("http://xerces.apache.org/xerces-j/features.html#external-general-entities", false);
                  saxParserFactory.setFeature("http://xerces.apache.org/xerces-j/features.html#external-parameter-entities", false);
               } catch (Throwable var4) {
               }
            }
         }
      }

   }
}
