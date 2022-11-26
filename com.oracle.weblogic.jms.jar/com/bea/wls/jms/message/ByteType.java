package com.bea.wls.jms.message;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlByte;
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

public interface ByteType extends XmlByte {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ByteType.class.getClassLoader(), "schemacom_bea_xml.system.wls_jms").resolveHandle("bytetype5fa5type");

   public static final class Factory {
      public static ByteType newValue(Object obj) {
         return (ByteType)ByteType.type.newValue(obj);
      }

      public static ByteType newInstance() {
         return (ByteType)XmlBeans.getContextTypeLoader().newInstance(ByteType.type, (XmlOptions)null);
      }

      public static ByteType newInstance(XmlOptions options) {
         return (ByteType)XmlBeans.getContextTypeLoader().newInstance(ByteType.type, options);
      }

      public static ByteType parse(String xmlAsString) throws XmlException {
         return (ByteType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ByteType.type, (XmlOptions)null);
      }

      public static ByteType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ByteType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ByteType.type, options);
      }

      public static ByteType parse(File file) throws XmlException, IOException {
         return (ByteType)XmlBeans.getContextTypeLoader().parse(file, ByteType.type, (XmlOptions)null);
      }

      public static ByteType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ByteType)XmlBeans.getContextTypeLoader().parse(file, ByteType.type, options);
      }

      public static ByteType parse(URL u) throws XmlException, IOException {
         return (ByteType)XmlBeans.getContextTypeLoader().parse(u, ByteType.type, (XmlOptions)null);
      }

      public static ByteType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ByteType)XmlBeans.getContextTypeLoader().parse(u, ByteType.type, options);
      }

      public static ByteType parse(InputStream is) throws XmlException, IOException {
         return (ByteType)XmlBeans.getContextTypeLoader().parse(is, ByteType.type, (XmlOptions)null);
      }

      public static ByteType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ByteType)XmlBeans.getContextTypeLoader().parse(is, ByteType.type, options);
      }

      public static ByteType parse(Reader r) throws XmlException, IOException {
         return (ByteType)XmlBeans.getContextTypeLoader().parse(r, ByteType.type, (XmlOptions)null);
      }

      public static ByteType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ByteType)XmlBeans.getContextTypeLoader().parse(r, ByteType.type, options);
      }

      public static ByteType parse(XMLStreamReader sr) throws XmlException {
         return (ByteType)XmlBeans.getContextTypeLoader().parse(sr, ByteType.type, (XmlOptions)null);
      }

      public static ByteType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ByteType)XmlBeans.getContextTypeLoader().parse(sr, ByteType.type, options);
      }

      public static ByteType parse(Node node) throws XmlException {
         return (ByteType)XmlBeans.getContextTypeLoader().parse(node, ByteType.type, (XmlOptions)null);
      }

      public static ByteType parse(Node node, XmlOptions options) throws XmlException {
         return (ByteType)XmlBeans.getContextTypeLoader().parse(node, ByteType.type, options);
      }

      /** @deprecated */
      public static ByteType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ByteType)XmlBeans.getContextTypeLoader().parse(xis, ByteType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ByteType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ByteType)XmlBeans.getContextTypeLoader().parse(xis, ByteType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ByteType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ByteType.type, options);
      }

      private Factory() {
      }
   }
}
