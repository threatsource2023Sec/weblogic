package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlQName;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface XsdQNameType extends XmlQName {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(XsdQNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("xsdqnametype2bc0type");

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static XsdQNameType newInstance() {
         return (XsdQNameType)XmlBeans.getContextTypeLoader().newInstance(XsdQNameType.type, (XmlOptions)null);
      }

      public static XsdQNameType newInstance(XmlOptions options) {
         return (XsdQNameType)XmlBeans.getContextTypeLoader().newInstance(XsdQNameType.type, options);
      }

      public static XsdQNameType parse(java.lang.String xmlAsString) throws XmlException {
         return (XsdQNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, XsdQNameType.type, (XmlOptions)null);
      }

      public static XsdQNameType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (XsdQNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, XsdQNameType.type, options);
      }

      public static XsdQNameType parse(File file) throws XmlException, IOException {
         return (XsdQNameType)XmlBeans.getContextTypeLoader().parse(file, XsdQNameType.type, (XmlOptions)null);
      }

      public static XsdQNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (XsdQNameType)XmlBeans.getContextTypeLoader().parse(file, XsdQNameType.type, options);
      }

      public static XsdQNameType parse(URL u) throws XmlException, IOException {
         return (XsdQNameType)XmlBeans.getContextTypeLoader().parse(u, XsdQNameType.type, (XmlOptions)null);
      }

      public static XsdQNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XsdQNameType)XmlBeans.getContextTypeLoader().parse(u, XsdQNameType.type, options);
      }

      public static XsdQNameType parse(InputStream is) throws XmlException, IOException {
         return (XsdQNameType)XmlBeans.getContextTypeLoader().parse(is, XsdQNameType.type, (XmlOptions)null);
      }

      public static XsdQNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XsdQNameType)XmlBeans.getContextTypeLoader().parse(is, XsdQNameType.type, options);
      }

      public static XsdQNameType parse(Reader r) throws XmlException, IOException {
         return (XsdQNameType)XmlBeans.getContextTypeLoader().parse(r, XsdQNameType.type, (XmlOptions)null);
      }

      public static XsdQNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XsdQNameType)XmlBeans.getContextTypeLoader().parse(r, XsdQNameType.type, options);
      }

      public static XsdQNameType parse(XMLStreamReader sr) throws XmlException {
         return (XsdQNameType)XmlBeans.getContextTypeLoader().parse(sr, XsdQNameType.type, (XmlOptions)null);
      }

      public static XsdQNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (XsdQNameType)XmlBeans.getContextTypeLoader().parse(sr, XsdQNameType.type, options);
      }

      public static XsdQNameType parse(Node node) throws XmlException {
         return (XsdQNameType)XmlBeans.getContextTypeLoader().parse(node, XsdQNameType.type, (XmlOptions)null);
      }

      public static XsdQNameType parse(Node node, XmlOptions options) throws XmlException {
         return (XsdQNameType)XmlBeans.getContextTypeLoader().parse(node, XsdQNameType.type, options);
      }

      /** @deprecated */
      public static XsdQNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XsdQNameType)XmlBeans.getContextTypeLoader().parse(xis, XsdQNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XsdQNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XsdQNameType)XmlBeans.getContextTypeLoader().parse(xis, XsdQNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XsdQNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XsdQNameType.type, options);
      }

      private Factory() {
      }
   }
}
