package com.bea.wls.jms.message;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlShort;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface ShortType extends XmlShort {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ShortType.class.getClassLoader(), "schemacom_bea_xml.system.wls_jms").resolveHandle("shorttype90e3type");

   public static final class Factory {
      public static ShortType newValue(Object obj) {
         return (ShortType)ShortType.type.newValue(obj);
      }

      public static ShortType newInstance() {
         return (ShortType)XmlBeans.getContextTypeLoader().newInstance(ShortType.type, (XmlOptions)null);
      }

      public static ShortType newInstance(XmlOptions options) {
         return (ShortType)XmlBeans.getContextTypeLoader().newInstance(ShortType.type, options);
      }

      public static ShortType parse(String xmlAsString) throws XmlException {
         return (ShortType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ShortType.type, (XmlOptions)null);
      }

      public static ShortType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ShortType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ShortType.type, options);
      }

      public static ShortType parse(File file) throws XmlException, IOException {
         return (ShortType)XmlBeans.getContextTypeLoader().parse(file, ShortType.type, (XmlOptions)null);
      }

      public static ShortType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ShortType)XmlBeans.getContextTypeLoader().parse(file, ShortType.type, options);
      }

      public static ShortType parse(URL u) throws XmlException, IOException {
         return (ShortType)XmlBeans.getContextTypeLoader().parse(u, ShortType.type, (XmlOptions)null);
      }

      public static ShortType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ShortType)XmlBeans.getContextTypeLoader().parse(u, ShortType.type, options);
      }

      public static ShortType parse(InputStream is) throws XmlException, IOException {
         return (ShortType)XmlBeans.getContextTypeLoader().parse(is, ShortType.type, (XmlOptions)null);
      }

      public static ShortType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ShortType)XmlBeans.getContextTypeLoader().parse(is, ShortType.type, options);
      }

      public static ShortType parse(Reader r) throws XmlException, IOException {
         return (ShortType)XmlBeans.getContextTypeLoader().parse(r, ShortType.type, (XmlOptions)null);
      }

      public static ShortType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ShortType)XmlBeans.getContextTypeLoader().parse(r, ShortType.type, options);
      }

      public static ShortType parse(XMLStreamReader sr) throws XmlException {
         return (ShortType)XmlBeans.getContextTypeLoader().parse(sr, ShortType.type, (XmlOptions)null);
      }

      public static ShortType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ShortType)XmlBeans.getContextTypeLoader().parse(sr, ShortType.type, options);
      }

      public static ShortType parse(Node node) throws XmlException {
         return (ShortType)XmlBeans.getContextTypeLoader().parse(node, ShortType.type, (XmlOptions)null);
      }

      public static ShortType parse(Node node, XmlOptions options) throws XmlException {
         return (ShortType)XmlBeans.getContextTypeLoader().parse(node, ShortType.type, options);
      }

      /** @deprecated */
      public static ShortType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ShortType)XmlBeans.getContextTypeLoader().parse(xis, ShortType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ShortType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ShortType)XmlBeans.getContextTypeLoader().parse(xis, ShortType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ShortType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ShortType.type, options);
      }

      private Factory() {
      }
   }
}
