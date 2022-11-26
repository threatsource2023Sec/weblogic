package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlNMTOKEN;
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

public interface XsdNMTOKENType extends XmlNMTOKEN {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(XsdNMTOKENType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("xsdnmtokentype863etype");

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static XsdNMTOKENType newInstance() {
         return (XsdNMTOKENType)XmlBeans.getContextTypeLoader().newInstance(XsdNMTOKENType.type, (XmlOptions)null);
      }

      public static XsdNMTOKENType newInstance(XmlOptions options) {
         return (XsdNMTOKENType)XmlBeans.getContextTypeLoader().newInstance(XsdNMTOKENType.type, options);
      }

      public static XsdNMTOKENType parse(java.lang.String xmlAsString) throws XmlException {
         return (XsdNMTOKENType)XmlBeans.getContextTypeLoader().parse(xmlAsString, XsdNMTOKENType.type, (XmlOptions)null);
      }

      public static XsdNMTOKENType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (XsdNMTOKENType)XmlBeans.getContextTypeLoader().parse(xmlAsString, XsdNMTOKENType.type, options);
      }

      public static XsdNMTOKENType parse(File file) throws XmlException, IOException {
         return (XsdNMTOKENType)XmlBeans.getContextTypeLoader().parse(file, XsdNMTOKENType.type, (XmlOptions)null);
      }

      public static XsdNMTOKENType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (XsdNMTOKENType)XmlBeans.getContextTypeLoader().parse(file, XsdNMTOKENType.type, options);
      }

      public static XsdNMTOKENType parse(URL u) throws XmlException, IOException {
         return (XsdNMTOKENType)XmlBeans.getContextTypeLoader().parse(u, XsdNMTOKENType.type, (XmlOptions)null);
      }

      public static XsdNMTOKENType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XsdNMTOKENType)XmlBeans.getContextTypeLoader().parse(u, XsdNMTOKENType.type, options);
      }

      public static XsdNMTOKENType parse(InputStream is) throws XmlException, IOException {
         return (XsdNMTOKENType)XmlBeans.getContextTypeLoader().parse(is, XsdNMTOKENType.type, (XmlOptions)null);
      }

      public static XsdNMTOKENType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XsdNMTOKENType)XmlBeans.getContextTypeLoader().parse(is, XsdNMTOKENType.type, options);
      }

      public static XsdNMTOKENType parse(Reader r) throws XmlException, IOException {
         return (XsdNMTOKENType)XmlBeans.getContextTypeLoader().parse(r, XsdNMTOKENType.type, (XmlOptions)null);
      }

      public static XsdNMTOKENType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XsdNMTOKENType)XmlBeans.getContextTypeLoader().parse(r, XsdNMTOKENType.type, options);
      }

      public static XsdNMTOKENType parse(XMLStreamReader sr) throws XmlException {
         return (XsdNMTOKENType)XmlBeans.getContextTypeLoader().parse(sr, XsdNMTOKENType.type, (XmlOptions)null);
      }

      public static XsdNMTOKENType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (XsdNMTOKENType)XmlBeans.getContextTypeLoader().parse(sr, XsdNMTOKENType.type, options);
      }

      public static XsdNMTOKENType parse(Node node) throws XmlException {
         return (XsdNMTOKENType)XmlBeans.getContextTypeLoader().parse(node, XsdNMTOKENType.type, (XmlOptions)null);
      }

      public static XsdNMTOKENType parse(Node node, XmlOptions options) throws XmlException {
         return (XsdNMTOKENType)XmlBeans.getContextTypeLoader().parse(node, XsdNMTOKENType.type, options);
      }

      /** @deprecated */
      public static XsdNMTOKENType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XsdNMTOKENType)XmlBeans.getContextTypeLoader().parse(xis, XsdNMTOKENType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XsdNMTOKENType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XsdNMTOKENType)XmlBeans.getContextTypeLoader().parse(xis, XsdNMTOKENType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XsdNMTOKENType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XsdNMTOKENType.type, options);
      }

      private Factory() {
      }
   }
}
