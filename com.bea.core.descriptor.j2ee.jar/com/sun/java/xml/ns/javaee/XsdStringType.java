package com.sun.java.xml.ns.javaee;

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

public interface XsdStringType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(XsdStringType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("xsdstringtypea535type");

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static XsdStringType newInstance() {
         return (XsdStringType)XmlBeans.getContextTypeLoader().newInstance(XsdStringType.type, (XmlOptions)null);
      }

      public static XsdStringType newInstance(XmlOptions options) {
         return (XsdStringType)XmlBeans.getContextTypeLoader().newInstance(XsdStringType.type, options);
      }

      public static XsdStringType parse(java.lang.String xmlAsString) throws XmlException {
         return (XsdStringType)XmlBeans.getContextTypeLoader().parse(xmlAsString, XsdStringType.type, (XmlOptions)null);
      }

      public static XsdStringType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (XsdStringType)XmlBeans.getContextTypeLoader().parse(xmlAsString, XsdStringType.type, options);
      }

      public static XsdStringType parse(File file) throws XmlException, IOException {
         return (XsdStringType)XmlBeans.getContextTypeLoader().parse(file, XsdStringType.type, (XmlOptions)null);
      }

      public static XsdStringType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (XsdStringType)XmlBeans.getContextTypeLoader().parse(file, XsdStringType.type, options);
      }

      public static XsdStringType parse(URL u) throws XmlException, IOException {
         return (XsdStringType)XmlBeans.getContextTypeLoader().parse(u, XsdStringType.type, (XmlOptions)null);
      }

      public static XsdStringType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XsdStringType)XmlBeans.getContextTypeLoader().parse(u, XsdStringType.type, options);
      }

      public static XsdStringType parse(InputStream is) throws XmlException, IOException {
         return (XsdStringType)XmlBeans.getContextTypeLoader().parse(is, XsdStringType.type, (XmlOptions)null);
      }

      public static XsdStringType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XsdStringType)XmlBeans.getContextTypeLoader().parse(is, XsdStringType.type, options);
      }

      public static XsdStringType parse(Reader r) throws XmlException, IOException {
         return (XsdStringType)XmlBeans.getContextTypeLoader().parse(r, XsdStringType.type, (XmlOptions)null);
      }

      public static XsdStringType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XsdStringType)XmlBeans.getContextTypeLoader().parse(r, XsdStringType.type, options);
      }

      public static XsdStringType parse(XMLStreamReader sr) throws XmlException {
         return (XsdStringType)XmlBeans.getContextTypeLoader().parse(sr, XsdStringType.type, (XmlOptions)null);
      }

      public static XsdStringType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (XsdStringType)XmlBeans.getContextTypeLoader().parse(sr, XsdStringType.type, options);
      }

      public static XsdStringType parse(Node node) throws XmlException {
         return (XsdStringType)XmlBeans.getContextTypeLoader().parse(node, XsdStringType.type, (XmlOptions)null);
      }

      public static XsdStringType parse(Node node, XmlOptions options) throws XmlException {
         return (XsdStringType)XmlBeans.getContextTypeLoader().parse(node, XsdStringType.type, options);
      }

      /** @deprecated */
      public static XsdStringType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XsdStringType)XmlBeans.getContextTypeLoader().parse(xis, XsdStringType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XsdStringType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XsdStringType)XmlBeans.getContextTypeLoader().parse(xis, XsdStringType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XsdStringType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XsdStringType.type, options);
      }

      private Factory() {
      }
   }
}
