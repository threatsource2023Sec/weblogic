package com.bea.wls.jms.message;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlDouble;
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

public interface DoubleType extends XmlDouble {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DoubleType.class.getClassLoader(), "schemacom_bea_xml.system.wls_jms").resolveHandle("doubletype0afctype");

   public static final class Factory {
      public static DoubleType newValue(Object obj) {
         return (DoubleType)DoubleType.type.newValue(obj);
      }

      public static DoubleType newInstance() {
         return (DoubleType)XmlBeans.getContextTypeLoader().newInstance(DoubleType.type, (XmlOptions)null);
      }

      public static DoubleType newInstance(XmlOptions options) {
         return (DoubleType)XmlBeans.getContextTypeLoader().newInstance(DoubleType.type, options);
      }

      public static DoubleType parse(String xmlAsString) throws XmlException {
         return (DoubleType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DoubleType.type, (XmlOptions)null);
      }

      public static DoubleType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DoubleType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DoubleType.type, options);
      }

      public static DoubleType parse(File file) throws XmlException, IOException {
         return (DoubleType)XmlBeans.getContextTypeLoader().parse(file, DoubleType.type, (XmlOptions)null);
      }

      public static DoubleType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DoubleType)XmlBeans.getContextTypeLoader().parse(file, DoubleType.type, options);
      }

      public static DoubleType parse(URL u) throws XmlException, IOException {
         return (DoubleType)XmlBeans.getContextTypeLoader().parse(u, DoubleType.type, (XmlOptions)null);
      }

      public static DoubleType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DoubleType)XmlBeans.getContextTypeLoader().parse(u, DoubleType.type, options);
      }

      public static DoubleType parse(InputStream is) throws XmlException, IOException {
         return (DoubleType)XmlBeans.getContextTypeLoader().parse(is, DoubleType.type, (XmlOptions)null);
      }

      public static DoubleType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DoubleType)XmlBeans.getContextTypeLoader().parse(is, DoubleType.type, options);
      }

      public static DoubleType parse(Reader r) throws XmlException, IOException {
         return (DoubleType)XmlBeans.getContextTypeLoader().parse(r, DoubleType.type, (XmlOptions)null);
      }

      public static DoubleType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DoubleType)XmlBeans.getContextTypeLoader().parse(r, DoubleType.type, options);
      }

      public static DoubleType parse(XMLStreamReader sr) throws XmlException {
         return (DoubleType)XmlBeans.getContextTypeLoader().parse(sr, DoubleType.type, (XmlOptions)null);
      }

      public static DoubleType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DoubleType)XmlBeans.getContextTypeLoader().parse(sr, DoubleType.type, options);
      }

      public static DoubleType parse(Node node) throws XmlException {
         return (DoubleType)XmlBeans.getContextTypeLoader().parse(node, DoubleType.type, (XmlOptions)null);
      }

      public static DoubleType parse(Node node, XmlOptions options) throws XmlException {
         return (DoubleType)XmlBeans.getContextTypeLoader().parse(node, DoubleType.type, options);
      }

      /** @deprecated */
      public static DoubleType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DoubleType)XmlBeans.getContextTypeLoader().parse(xis, DoubleType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DoubleType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DoubleType)XmlBeans.getContextTypeLoader().parse(xis, DoubleType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DoubleType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DoubleType.type, options);
      }

      private Factory() {
      }
   }
}
