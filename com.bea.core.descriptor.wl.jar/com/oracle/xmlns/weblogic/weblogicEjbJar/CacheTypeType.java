package com.oracle.xmlns.weblogic.weblogicEjbJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface CacheTypeType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CacheTypeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("cachetypetyped3dbtype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static CacheTypeType newInstance() {
         return (CacheTypeType)XmlBeans.getContextTypeLoader().newInstance(CacheTypeType.type, (XmlOptions)null);
      }

      public static CacheTypeType newInstance(XmlOptions options) {
         return (CacheTypeType)XmlBeans.getContextTypeLoader().newInstance(CacheTypeType.type, options);
      }

      public static CacheTypeType parse(String xmlAsString) throws XmlException {
         return (CacheTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CacheTypeType.type, (XmlOptions)null);
      }

      public static CacheTypeType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CacheTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CacheTypeType.type, options);
      }

      public static CacheTypeType parse(File file) throws XmlException, IOException {
         return (CacheTypeType)XmlBeans.getContextTypeLoader().parse(file, CacheTypeType.type, (XmlOptions)null);
      }

      public static CacheTypeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CacheTypeType)XmlBeans.getContextTypeLoader().parse(file, CacheTypeType.type, options);
      }

      public static CacheTypeType parse(URL u) throws XmlException, IOException {
         return (CacheTypeType)XmlBeans.getContextTypeLoader().parse(u, CacheTypeType.type, (XmlOptions)null);
      }

      public static CacheTypeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CacheTypeType)XmlBeans.getContextTypeLoader().parse(u, CacheTypeType.type, options);
      }

      public static CacheTypeType parse(InputStream is) throws XmlException, IOException {
         return (CacheTypeType)XmlBeans.getContextTypeLoader().parse(is, CacheTypeType.type, (XmlOptions)null);
      }

      public static CacheTypeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CacheTypeType)XmlBeans.getContextTypeLoader().parse(is, CacheTypeType.type, options);
      }

      public static CacheTypeType parse(Reader r) throws XmlException, IOException {
         return (CacheTypeType)XmlBeans.getContextTypeLoader().parse(r, CacheTypeType.type, (XmlOptions)null);
      }

      public static CacheTypeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CacheTypeType)XmlBeans.getContextTypeLoader().parse(r, CacheTypeType.type, options);
      }

      public static CacheTypeType parse(XMLStreamReader sr) throws XmlException {
         return (CacheTypeType)XmlBeans.getContextTypeLoader().parse(sr, CacheTypeType.type, (XmlOptions)null);
      }

      public static CacheTypeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CacheTypeType)XmlBeans.getContextTypeLoader().parse(sr, CacheTypeType.type, options);
      }

      public static CacheTypeType parse(Node node) throws XmlException {
         return (CacheTypeType)XmlBeans.getContextTypeLoader().parse(node, CacheTypeType.type, (XmlOptions)null);
      }

      public static CacheTypeType parse(Node node, XmlOptions options) throws XmlException {
         return (CacheTypeType)XmlBeans.getContextTypeLoader().parse(node, CacheTypeType.type, options);
      }

      /** @deprecated */
      public static CacheTypeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CacheTypeType)XmlBeans.getContextTypeLoader().parse(xis, CacheTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CacheTypeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CacheTypeType)XmlBeans.getContextTypeLoader().parse(xis, CacheTypeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CacheTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CacheTypeType.type, options);
      }

      private Factory() {
      }
   }
}
