package com.sun.java.xml.ns.j2Ee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface LocaleType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LocaleType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("localetype8e6dtype");

   public static final class Factory {
      public static LocaleType newValue(Object obj) {
         return (LocaleType)LocaleType.type.newValue(obj);
      }

      public static LocaleType newInstance() {
         return (LocaleType)XmlBeans.getContextTypeLoader().newInstance(LocaleType.type, (XmlOptions)null);
      }

      public static LocaleType newInstance(XmlOptions options) {
         return (LocaleType)XmlBeans.getContextTypeLoader().newInstance(LocaleType.type, options);
      }

      public static LocaleType parse(java.lang.String xmlAsString) throws XmlException {
         return (LocaleType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LocaleType.type, (XmlOptions)null);
      }

      public static LocaleType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (LocaleType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LocaleType.type, options);
      }

      public static LocaleType parse(File file) throws XmlException, IOException {
         return (LocaleType)XmlBeans.getContextTypeLoader().parse(file, LocaleType.type, (XmlOptions)null);
      }

      public static LocaleType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LocaleType)XmlBeans.getContextTypeLoader().parse(file, LocaleType.type, options);
      }

      public static LocaleType parse(URL u) throws XmlException, IOException {
         return (LocaleType)XmlBeans.getContextTypeLoader().parse(u, LocaleType.type, (XmlOptions)null);
      }

      public static LocaleType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LocaleType)XmlBeans.getContextTypeLoader().parse(u, LocaleType.type, options);
      }

      public static LocaleType parse(InputStream is) throws XmlException, IOException {
         return (LocaleType)XmlBeans.getContextTypeLoader().parse(is, LocaleType.type, (XmlOptions)null);
      }

      public static LocaleType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LocaleType)XmlBeans.getContextTypeLoader().parse(is, LocaleType.type, options);
      }

      public static LocaleType parse(Reader r) throws XmlException, IOException {
         return (LocaleType)XmlBeans.getContextTypeLoader().parse(r, LocaleType.type, (XmlOptions)null);
      }

      public static LocaleType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LocaleType)XmlBeans.getContextTypeLoader().parse(r, LocaleType.type, options);
      }

      public static LocaleType parse(XMLStreamReader sr) throws XmlException {
         return (LocaleType)XmlBeans.getContextTypeLoader().parse(sr, LocaleType.type, (XmlOptions)null);
      }

      public static LocaleType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LocaleType)XmlBeans.getContextTypeLoader().parse(sr, LocaleType.type, options);
      }

      public static LocaleType parse(Node node) throws XmlException {
         return (LocaleType)XmlBeans.getContextTypeLoader().parse(node, LocaleType.type, (XmlOptions)null);
      }

      public static LocaleType parse(Node node, XmlOptions options) throws XmlException {
         return (LocaleType)XmlBeans.getContextTypeLoader().parse(node, LocaleType.type, options);
      }

      /** @deprecated */
      public static LocaleType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LocaleType)XmlBeans.getContextTypeLoader().parse(xis, LocaleType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LocaleType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LocaleType)XmlBeans.getContextTypeLoader().parse(xis, LocaleType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LocaleType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LocaleType.type, options);
      }

      private Factory() {
      }
   }
}
