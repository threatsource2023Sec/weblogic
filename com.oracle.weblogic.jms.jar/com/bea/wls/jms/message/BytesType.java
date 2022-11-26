package com.bea.wls.jms.message;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBase64Binary;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface BytesType extends XmlBase64Binary {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(BytesType.class.getClassLoader(), "schemacom_bea_xml.system.wls_jms").resolveHandle("bytestyped614type");

   public static final class Factory {
      public static BytesType newValue(Object obj) {
         return (BytesType)BytesType.type.newValue(obj);
      }

      public static BytesType newInstance() {
         return (BytesType)XmlBeans.getContextTypeLoader().newInstance(BytesType.type, (XmlOptions)null);
      }

      public static BytesType newInstance(XmlOptions options) {
         return (BytesType)XmlBeans.getContextTypeLoader().newInstance(BytesType.type, options);
      }

      public static BytesType parse(String xmlAsString) throws XmlException {
         return (BytesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, BytesType.type, (XmlOptions)null);
      }

      public static BytesType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (BytesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, BytesType.type, options);
      }

      public static BytesType parse(File file) throws XmlException, IOException {
         return (BytesType)XmlBeans.getContextTypeLoader().parse(file, BytesType.type, (XmlOptions)null);
      }

      public static BytesType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (BytesType)XmlBeans.getContextTypeLoader().parse(file, BytesType.type, options);
      }

      public static BytesType parse(URL u) throws XmlException, IOException {
         return (BytesType)XmlBeans.getContextTypeLoader().parse(u, BytesType.type, (XmlOptions)null);
      }

      public static BytesType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (BytesType)XmlBeans.getContextTypeLoader().parse(u, BytesType.type, options);
      }

      public static BytesType parse(InputStream is) throws XmlException, IOException {
         return (BytesType)XmlBeans.getContextTypeLoader().parse(is, BytesType.type, (XmlOptions)null);
      }

      public static BytesType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (BytesType)XmlBeans.getContextTypeLoader().parse(is, BytesType.type, options);
      }

      public static BytesType parse(Reader r) throws XmlException, IOException {
         return (BytesType)XmlBeans.getContextTypeLoader().parse(r, BytesType.type, (XmlOptions)null);
      }

      public static BytesType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (BytesType)XmlBeans.getContextTypeLoader().parse(r, BytesType.type, options);
      }

      public static BytesType parse(XMLStreamReader sr) throws XmlException {
         return (BytesType)XmlBeans.getContextTypeLoader().parse(sr, BytesType.type, (XmlOptions)null);
      }

      public static BytesType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (BytesType)XmlBeans.getContextTypeLoader().parse(sr, BytesType.type, options);
      }

      public static BytesType parse(Node node) throws XmlException {
         return (BytesType)XmlBeans.getContextTypeLoader().parse(node, BytesType.type, (XmlOptions)null);
      }

      public static BytesType parse(Node node, XmlOptions options) throws XmlException {
         return (BytesType)XmlBeans.getContextTypeLoader().parse(node, BytesType.type, options);
      }

      /** @deprecated */
      public static BytesType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (BytesType)XmlBeans.getContextTypeLoader().parse(xis, BytesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static BytesType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (BytesType)XmlBeans.getContextTypeLoader().parse(xis, BytesType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, BytesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, BytesType.type, options);
      }

      private Factory() {
      }
   }
}
