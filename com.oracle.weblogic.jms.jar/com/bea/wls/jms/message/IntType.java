package com.bea.wls.jms.message;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface IntType extends XmlInteger {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(IntType.class.getClassLoader(), "schemacom_bea_xml.system.wls_jms").resolveHandle("inttype0050type");

   public static final class Factory {
      public static IntType newValue(Object obj) {
         return (IntType)IntType.type.newValue(obj);
      }

      public static IntType newInstance() {
         return (IntType)XmlBeans.getContextTypeLoader().newInstance(IntType.type, (XmlOptions)null);
      }

      public static IntType newInstance(XmlOptions options) {
         return (IntType)XmlBeans.getContextTypeLoader().newInstance(IntType.type, options);
      }

      public static IntType parse(String xmlAsString) throws XmlException {
         return (IntType)XmlBeans.getContextTypeLoader().parse(xmlAsString, IntType.type, (XmlOptions)null);
      }

      public static IntType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (IntType)XmlBeans.getContextTypeLoader().parse(xmlAsString, IntType.type, options);
      }

      public static IntType parse(File file) throws XmlException, IOException {
         return (IntType)XmlBeans.getContextTypeLoader().parse(file, IntType.type, (XmlOptions)null);
      }

      public static IntType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (IntType)XmlBeans.getContextTypeLoader().parse(file, IntType.type, options);
      }

      public static IntType parse(URL u) throws XmlException, IOException {
         return (IntType)XmlBeans.getContextTypeLoader().parse(u, IntType.type, (XmlOptions)null);
      }

      public static IntType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (IntType)XmlBeans.getContextTypeLoader().parse(u, IntType.type, options);
      }

      public static IntType parse(InputStream is) throws XmlException, IOException {
         return (IntType)XmlBeans.getContextTypeLoader().parse(is, IntType.type, (XmlOptions)null);
      }

      public static IntType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (IntType)XmlBeans.getContextTypeLoader().parse(is, IntType.type, options);
      }

      public static IntType parse(Reader r) throws XmlException, IOException {
         return (IntType)XmlBeans.getContextTypeLoader().parse(r, IntType.type, (XmlOptions)null);
      }

      public static IntType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (IntType)XmlBeans.getContextTypeLoader().parse(r, IntType.type, options);
      }

      public static IntType parse(XMLStreamReader sr) throws XmlException {
         return (IntType)XmlBeans.getContextTypeLoader().parse(sr, IntType.type, (XmlOptions)null);
      }

      public static IntType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (IntType)XmlBeans.getContextTypeLoader().parse(sr, IntType.type, options);
      }

      public static IntType parse(Node node) throws XmlException {
         return (IntType)XmlBeans.getContextTypeLoader().parse(node, IntType.type, (XmlOptions)null);
      }

      public static IntType parse(Node node, XmlOptions options) throws XmlException {
         return (IntType)XmlBeans.getContextTypeLoader().parse(node, IntType.type, options);
      }

      /** @deprecated */
      public static IntType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (IntType)XmlBeans.getContextTypeLoader().parse(xis, IntType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static IntType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (IntType)XmlBeans.getContextTypeLoader().parse(xis, IntType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, IntType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, IntType.type, options);
      }

      private Factory() {
      }
   }
}
