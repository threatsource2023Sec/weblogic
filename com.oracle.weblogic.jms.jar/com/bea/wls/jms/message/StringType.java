package com.bea.wls.jms.message;

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

public interface StringType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(StringType.class.getClassLoader(), "schemacom_bea_xml.system.wls_jms").resolveHandle("stringtypedebctype");

   public static final class Factory {
      public static StringType newValue(Object obj) {
         return (StringType)StringType.type.newValue(obj);
      }

      public static StringType newInstance() {
         return (StringType)XmlBeans.getContextTypeLoader().newInstance(StringType.type, (XmlOptions)null);
      }

      public static StringType newInstance(XmlOptions options) {
         return (StringType)XmlBeans.getContextTypeLoader().newInstance(StringType.type, options);
      }

      public static StringType parse(String xmlAsString) throws XmlException {
         return (StringType)XmlBeans.getContextTypeLoader().parse(xmlAsString, StringType.type, (XmlOptions)null);
      }

      public static StringType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (StringType)XmlBeans.getContextTypeLoader().parse(xmlAsString, StringType.type, options);
      }

      public static StringType parse(File file) throws XmlException, IOException {
         return (StringType)XmlBeans.getContextTypeLoader().parse(file, StringType.type, (XmlOptions)null);
      }

      public static StringType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (StringType)XmlBeans.getContextTypeLoader().parse(file, StringType.type, options);
      }

      public static StringType parse(URL u) throws XmlException, IOException {
         return (StringType)XmlBeans.getContextTypeLoader().parse(u, StringType.type, (XmlOptions)null);
      }

      public static StringType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (StringType)XmlBeans.getContextTypeLoader().parse(u, StringType.type, options);
      }

      public static StringType parse(InputStream is) throws XmlException, IOException {
         return (StringType)XmlBeans.getContextTypeLoader().parse(is, StringType.type, (XmlOptions)null);
      }

      public static StringType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (StringType)XmlBeans.getContextTypeLoader().parse(is, StringType.type, options);
      }

      public static StringType parse(Reader r) throws XmlException, IOException {
         return (StringType)XmlBeans.getContextTypeLoader().parse(r, StringType.type, (XmlOptions)null);
      }

      public static StringType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (StringType)XmlBeans.getContextTypeLoader().parse(r, StringType.type, options);
      }

      public static StringType parse(XMLStreamReader sr) throws XmlException {
         return (StringType)XmlBeans.getContextTypeLoader().parse(sr, StringType.type, (XmlOptions)null);
      }

      public static StringType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (StringType)XmlBeans.getContextTypeLoader().parse(sr, StringType.type, options);
      }

      public static StringType parse(Node node) throws XmlException {
         return (StringType)XmlBeans.getContextTypeLoader().parse(node, StringType.type, (XmlOptions)null);
      }

      public static StringType parse(Node node, XmlOptions options) throws XmlException {
         return (StringType)XmlBeans.getContextTypeLoader().parse(node, StringType.type, options);
      }

      /** @deprecated */
      public static StringType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (StringType)XmlBeans.getContextTypeLoader().parse(xis, StringType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static StringType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (StringType)XmlBeans.getContextTypeLoader().parse(xis, StringType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, StringType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, StringType.type, options);
      }

      private Factory() {
      }
   }
}
