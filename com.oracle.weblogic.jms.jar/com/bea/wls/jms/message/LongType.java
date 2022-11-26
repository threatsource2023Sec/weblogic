package com.bea.wls.jms.message;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlLong;
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

public interface LongType extends XmlLong {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LongType.class.getClassLoader(), "schemacom_bea_xml.system.wls_jms").resolveHandle("longtype7fb1type");

   public static final class Factory {
      public static LongType newValue(Object obj) {
         return (LongType)LongType.type.newValue(obj);
      }

      public static LongType newInstance() {
         return (LongType)XmlBeans.getContextTypeLoader().newInstance(LongType.type, (XmlOptions)null);
      }

      public static LongType newInstance(XmlOptions options) {
         return (LongType)XmlBeans.getContextTypeLoader().newInstance(LongType.type, options);
      }

      public static LongType parse(String xmlAsString) throws XmlException {
         return (LongType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LongType.type, (XmlOptions)null);
      }

      public static LongType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (LongType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LongType.type, options);
      }

      public static LongType parse(File file) throws XmlException, IOException {
         return (LongType)XmlBeans.getContextTypeLoader().parse(file, LongType.type, (XmlOptions)null);
      }

      public static LongType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LongType)XmlBeans.getContextTypeLoader().parse(file, LongType.type, options);
      }

      public static LongType parse(URL u) throws XmlException, IOException {
         return (LongType)XmlBeans.getContextTypeLoader().parse(u, LongType.type, (XmlOptions)null);
      }

      public static LongType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LongType)XmlBeans.getContextTypeLoader().parse(u, LongType.type, options);
      }

      public static LongType parse(InputStream is) throws XmlException, IOException {
         return (LongType)XmlBeans.getContextTypeLoader().parse(is, LongType.type, (XmlOptions)null);
      }

      public static LongType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LongType)XmlBeans.getContextTypeLoader().parse(is, LongType.type, options);
      }

      public static LongType parse(Reader r) throws XmlException, IOException {
         return (LongType)XmlBeans.getContextTypeLoader().parse(r, LongType.type, (XmlOptions)null);
      }

      public static LongType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LongType)XmlBeans.getContextTypeLoader().parse(r, LongType.type, options);
      }

      public static LongType parse(XMLStreamReader sr) throws XmlException {
         return (LongType)XmlBeans.getContextTypeLoader().parse(sr, LongType.type, (XmlOptions)null);
      }

      public static LongType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LongType)XmlBeans.getContextTypeLoader().parse(sr, LongType.type, options);
      }

      public static LongType parse(Node node) throws XmlException {
         return (LongType)XmlBeans.getContextTypeLoader().parse(node, LongType.type, (XmlOptions)null);
      }

      public static LongType parse(Node node, XmlOptions options) throws XmlException {
         return (LongType)XmlBeans.getContextTypeLoader().parse(node, LongType.type, options);
      }

      /** @deprecated */
      public static LongType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LongType)XmlBeans.getContextTypeLoader().parse(xis, LongType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LongType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LongType)XmlBeans.getContextTypeLoader().parse(xis, LongType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LongType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LongType.type, options);
      }

      private Factory() {
      }
   }
}
