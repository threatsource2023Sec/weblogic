package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlAnyURI;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface XsdAnyURIType extends XmlAnyURI {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(XsdAnyURIType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("xsdanyuritype69c4type");

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static XsdAnyURIType newInstance() {
         return (XsdAnyURIType)XmlBeans.getContextTypeLoader().newInstance(XsdAnyURIType.type, (XmlOptions)null);
      }

      public static XsdAnyURIType newInstance(XmlOptions options) {
         return (XsdAnyURIType)XmlBeans.getContextTypeLoader().newInstance(XsdAnyURIType.type, options);
      }

      public static XsdAnyURIType parse(java.lang.String xmlAsString) throws XmlException {
         return (XsdAnyURIType)XmlBeans.getContextTypeLoader().parse(xmlAsString, XsdAnyURIType.type, (XmlOptions)null);
      }

      public static XsdAnyURIType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (XsdAnyURIType)XmlBeans.getContextTypeLoader().parse(xmlAsString, XsdAnyURIType.type, options);
      }

      public static XsdAnyURIType parse(File file) throws XmlException, IOException {
         return (XsdAnyURIType)XmlBeans.getContextTypeLoader().parse(file, XsdAnyURIType.type, (XmlOptions)null);
      }

      public static XsdAnyURIType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (XsdAnyURIType)XmlBeans.getContextTypeLoader().parse(file, XsdAnyURIType.type, options);
      }

      public static XsdAnyURIType parse(URL u) throws XmlException, IOException {
         return (XsdAnyURIType)XmlBeans.getContextTypeLoader().parse(u, XsdAnyURIType.type, (XmlOptions)null);
      }

      public static XsdAnyURIType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XsdAnyURIType)XmlBeans.getContextTypeLoader().parse(u, XsdAnyURIType.type, options);
      }

      public static XsdAnyURIType parse(InputStream is) throws XmlException, IOException {
         return (XsdAnyURIType)XmlBeans.getContextTypeLoader().parse(is, XsdAnyURIType.type, (XmlOptions)null);
      }

      public static XsdAnyURIType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XsdAnyURIType)XmlBeans.getContextTypeLoader().parse(is, XsdAnyURIType.type, options);
      }

      public static XsdAnyURIType parse(Reader r) throws XmlException, IOException {
         return (XsdAnyURIType)XmlBeans.getContextTypeLoader().parse(r, XsdAnyURIType.type, (XmlOptions)null);
      }

      public static XsdAnyURIType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XsdAnyURIType)XmlBeans.getContextTypeLoader().parse(r, XsdAnyURIType.type, options);
      }

      public static XsdAnyURIType parse(XMLStreamReader sr) throws XmlException {
         return (XsdAnyURIType)XmlBeans.getContextTypeLoader().parse(sr, XsdAnyURIType.type, (XmlOptions)null);
      }

      public static XsdAnyURIType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (XsdAnyURIType)XmlBeans.getContextTypeLoader().parse(sr, XsdAnyURIType.type, options);
      }

      public static XsdAnyURIType parse(Node node) throws XmlException {
         return (XsdAnyURIType)XmlBeans.getContextTypeLoader().parse(node, XsdAnyURIType.type, (XmlOptions)null);
      }

      public static XsdAnyURIType parse(Node node, XmlOptions options) throws XmlException {
         return (XsdAnyURIType)XmlBeans.getContextTypeLoader().parse(node, XsdAnyURIType.type, options);
      }

      /** @deprecated */
      public static XsdAnyURIType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XsdAnyURIType)XmlBeans.getContextTypeLoader().parse(xis, XsdAnyURIType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XsdAnyURIType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XsdAnyURIType)XmlBeans.getContextTypeLoader().parse(xis, XsdAnyURIType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XsdAnyURIType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XsdAnyURIType.type, options);
      }

      private Factory() {
      }
   }
}
