package com.oracle.xmlns.weblogic.weblogicRdbmsJar;

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

public interface CachingNameType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CachingNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("cachingnametype5808type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static CachingNameType newInstance() {
         return (CachingNameType)XmlBeans.getContextTypeLoader().newInstance(CachingNameType.type, (XmlOptions)null);
      }

      public static CachingNameType newInstance(XmlOptions options) {
         return (CachingNameType)XmlBeans.getContextTypeLoader().newInstance(CachingNameType.type, options);
      }

      public static CachingNameType parse(String xmlAsString) throws XmlException {
         return (CachingNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CachingNameType.type, (XmlOptions)null);
      }

      public static CachingNameType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CachingNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CachingNameType.type, options);
      }

      public static CachingNameType parse(File file) throws XmlException, IOException {
         return (CachingNameType)XmlBeans.getContextTypeLoader().parse(file, CachingNameType.type, (XmlOptions)null);
      }

      public static CachingNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CachingNameType)XmlBeans.getContextTypeLoader().parse(file, CachingNameType.type, options);
      }

      public static CachingNameType parse(URL u) throws XmlException, IOException {
         return (CachingNameType)XmlBeans.getContextTypeLoader().parse(u, CachingNameType.type, (XmlOptions)null);
      }

      public static CachingNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CachingNameType)XmlBeans.getContextTypeLoader().parse(u, CachingNameType.type, options);
      }

      public static CachingNameType parse(InputStream is) throws XmlException, IOException {
         return (CachingNameType)XmlBeans.getContextTypeLoader().parse(is, CachingNameType.type, (XmlOptions)null);
      }

      public static CachingNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CachingNameType)XmlBeans.getContextTypeLoader().parse(is, CachingNameType.type, options);
      }

      public static CachingNameType parse(Reader r) throws XmlException, IOException {
         return (CachingNameType)XmlBeans.getContextTypeLoader().parse(r, CachingNameType.type, (XmlOptions)null);
      }

      public static CachingNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CachingNameType)XmlBeans.getContextTypeLoader().parse(r, CachingNameType.type, options);
      }

      public static CachingNameType parse(XMLStreamReader sr) throws XmlException {
         return (CachingNameType)XmlBeans.getContextTypeLoader().parse(sr, CachingNameType.type, (XmlOptions)null);
      }

      public static CachingNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CachingNameType)XmlBeans.getContextTypeLoader().parse(sr, CachingNameType.type, options);
      }

      public static CachingNameType parse(Node node) throws XmlException {
         return (CachingNameType)XmlBeans.getContextTypeLoader().parse(node, CachingNameType.type, (XmlOptions)null);
      }

      public static CachingNameType parse(Node node, XmlOptions options) throws XmlException {
         return (CachingNameType)XmlBeans.getContextTypeLoader().parse(node, CachingNameType.type, options);
      }

      /** @deprecated */
      public static CachingNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CachingNameType)XmlBeans.getContextTypeLoader().parse(xis, CachingNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CachingNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CachingNameType)XmlBeans.getContextTypeLoader().parse(xis, CachingNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CachingNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CachingNameType.type, options);
      }

      private Factory() {
      }
   }
}
