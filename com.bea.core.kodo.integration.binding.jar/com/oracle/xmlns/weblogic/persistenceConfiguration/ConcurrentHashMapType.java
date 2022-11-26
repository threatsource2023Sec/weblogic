package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface ConcurrentHashMapType extends QueryCompilationCacheType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConcurrentHashMapType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("concurrenthashmaptype6e19type");

   int getMaxSize();

   XmlInt xgetMaxSize();

   boolean isSetMaxSize();

   void setMaxSize(int var1);

   void xsetMaxSize(XmlInt var1);

   void unsetMaxSize();

   public static final class Factory {
      public static ConcurrentHashMapType newInstance() {
         return (ConcurrentHashMapType)XmlBeans.getContextTypeLoader().newInstance(ConcurrentHashMapType.type, (XmlOptions)null);
      }

      public static ConcurrentHashMapType newInstance(XmlOptions options) {
         return (ConcurrentHashMapType)XmlBeans.getContextTypeLoader().newInstance(ConcurrentHashMapType.type, options);
      }

      public static ConcurrentHashMapType parse(String xmlAsString) throws XmlException {
         return (ConcurrentHashMapType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConcurrentHashMapType.type, (XmlOptions)null);
      }

      public static ConcurrentHashMapType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConcurrentHashMapType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConcurrentHashMapType.type, options);
      }

      public static ConcurrentHashMapType parse(File file) throws XmlException, IOException {
         return (ConcurrentHashMapType)XmlBeans.getContextTypeLoader().parse(file, ConcurrentHashMapType.type, (XmlOptions)null);
      }

      public static ConcurrentHashMapType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConcurrentHashMapType)XmlBeans.getContextTypeLoader().parse(file, ConcurrentHashMapType.type, options);
      }

      public static ConcurrentHashMapType parse(URL u) throws XmlException, IOException {
         return (ConcurrentHashMapType)XmlBeans.getContextTypeLoader().parse(u, ConcurrentHashMapType.type, (XmlOptions)null);
      }

      public static ConcurrentHashMapType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConcurrentHashMapType)XmlBeans.getContextTypeLoader().parse(u, ConcurrentHashMapType.type, options);
      }

      public static ConcurrentHashMapType parse(InputStream is) throws XmlException, IOException {
         return (ConcurrentHashMapType)XmlBeans.getContextTypeLoader().parse(is, ConcurrentHashMapType.type, (XmlOptions)null);
      }

      public static ConcurrentHashMapType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConcurrentHashMapType)XmlBeans.getContextTypeLoader().parse(is, ConcurrentHashMapType.type, options);
      }

      public static ConcurrentHashMapType parse(Reader r) throws XmlException, IOException {
         return (ConcurrentHashMapType)XmlBeans.getContextTypeLoader().parse(r, ConcurrentHashMapType.type, (XmlOptions)null);
      }

      public static ConcurrentHashMapType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConcurrentHashMapType)XmlBeans.getContextTypeLoader().parse(r, ConcurrentHashMapType.type, options);
      }

      public static ConcurrentHashMapType parse(XMLStreamReader sr) throws XmlException {
         return (ConcurrentHashMapType)XmlBeans.getContextTypeLoader().parse(sr, ConcurrentHashMapType.type, (XmlOptions)null);
      }

      public static ConcurrentHashMapType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConcurrentHashMapType)XmlBeans.getContextTypeLoader().parse(sr, ConcurrentHashMapType.type, options);
      }

      public static ConcurrentHashMapType parse(Node node) throws XmlException {
         return (ConcurrentHashMapType)XmlBeans.getContextTypeLoader().parse(node, ConcurrentHashMapType.type, (XmlOptions)null);
      }

      public static ConcurrentHashMapType parse(Node node, XmlOptions options) throws XmlException {
         return (ConcurrentHashMapType)XmlBeans.getContextTypeLoader().parse(node, ConcurrentHashMapType.type, options);
      }

      /** @deprecated */
      public static ConcurrentHashMapType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConcurrentHashMapType)XmlBeans.getContextTypeLoader().parse(xis, ConcurrentHashMapType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConcurrentHashMapType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConcurrentHashMapType)XmlBeans.getContextTypeLoader().parse(xis, ConcurrentHashMapType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConcurrentHashMapType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConcurrentHashMapType.type, options);
      }

      private Factory() {
      }
   }
}
