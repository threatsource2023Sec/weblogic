package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlInteger;
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

public interface XsdIntegerType extends XmlInteger {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(XsdIntegerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("xsdintegertype4ce2type");

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static XsdIntegerType newInstance() {
         return (XsdIntegerType)XmlBeans.getContextTypeLoader().newInstance(XsdIntegerType.type, (XmlOptions)null);
      }

      public static XsdIntegerType newInstance(XmlOptions options) {
         return (XsdIntegerType)XmlBeans.getContextTypeLoader().newInstance(XsdIntegerType.type, options);
      }

      public static XsdIntegerType parse(java.lang.String xmlAsString) throws XmlException {
         return (XsdIntegerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, XsdIntegerType.type, (XmlOptions)null);
      }

      public static XsdIntegerType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (XsdIntegerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, XsdIntegerType.type, options);
      }

      public static XsdIntegerType parse(File file) throws XmlException, IOException {
         return (XsdIntegerType)XmlBeans.getContextTypeLoader().parse(file, XsdIntegerType.type, (XmlOptions)null);
      }

      public static XsdIntegerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (XsdIntegerType)XmlBeans.getContextTypeLoader().parse(file, XsdIntegerType.type, options);
      }

      public static XsdIntegerType parse(URL u) throws XmlException, IOException {
         return (XsdIntegerType)XmlBeans.getContextTypeLoader().parse(u, XsdIntegerType.type, (XmlOptions)null);
      }

      public static XsdIntegerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XsdIntegerType)XmlBeans.getContextTypeLoader().parse(u, XsdIntegerType.type, options);
      }

      public static XsdIntegerType parse(InputStream is) throws XmlException, IOException {
         return (XsdIntegerType)XmlBeans.getContextTypeLoader().parse(is, XsdIntegerType.type, (XmlOptions)null);
      }

      public static XsdIntegerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XsdIntegerType)XmlBeans.getContextTypeLoader().parse(is, XsdIntegerType.type, options);
      }

      public static XsdIntegerType parse(Reader r) throws XmlException, IOException {
         return (XsdIntegerType)XmlBeans.getContextTypeLoader().parse(r, XsdIntegerType.type, (XmlOptions)null);
      }

      public static XsdIntegerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XsdIntegerType)XmlBeans.getContextTypeLoader().parse(r, XsdIntegerType.type, options);
      }

      public static XsdIntegerType parse(XMLStreamReader sr) throws XmlException {
         return (XsdIntegerType)XmlBeans.getContextTypeLoader().parse(sr, XsdIntegerType.type, (XmlOptions)null);
      }

      public static XsdIntegerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (XsdIntegerType)XmlBeans.getContextTypeLoader().parse(sr, XsdIntegerType.type, options);
      }

      public static XsdIntegerType parse(Node node) throws XmlException {
         return (XsdIntegerType)XmlBeans.getContextTypeLoader().parse(node, XsdIntegerType.type, (XmlOptions)null);
      }

      public static XsdIntegerType parse(Node node, XmlOptions options) throws XmlException {
         return (XsdIntegerType)XmlBeans.getContextTypeLoader().parse(node, XsdIntegerType.type, options);
      }

      /** @deprecated */
      public static XsdIntegerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XsdIntegerType)XmlBeans.getContextTypeLoader().parse(xis, XsdIntegerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XsdIntegerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XsdIntegerType)XmlBeans.getContextTypeLoader().parse(xis, XsdIntegerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XsdIntegerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XsdIntegerType.type, options);
      }

      private Factory() {
      }
   }
}
