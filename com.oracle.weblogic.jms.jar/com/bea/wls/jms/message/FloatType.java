package com.bea.wls.jms.message;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlFloat;
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

public interface FloatType extends XmlFloat {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(FloatType.class.getClassLoader(), "schemacom_bea_xml.system.wls_jms").resolveHandle("floattype1703type");

   public static final class Factory {
      public static FloatType newValue(Object obj) {
         return (FloatType)FloatType.type.newValue(obj);
      }

      public static FloatType newInstance() {
         return (FloatType)XmlBeans.getContextTypeLoader().newInstance(FloatType.type, (XmlOptions)null);
      }

      public static FloatType newInstance(XmlOptions options) {
         return (FloatType)XmlBeans.getContextTypeLoader().newInstance(FloatType.type, options);
      }

      public static FloatType parse(String xmlAsString) throws XmlException {
         return (FloatType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FloatType.type, (XmlOptions)null);
      }

      public static FloatType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (FloatType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FloatType.type, options);
      }

      public static FloatType parse(File file) throws XmlException, IOException {
         return (FloatType)XmlBeans.getContextTypeLoader().parse(file, FloatType.type, (XmlOptions)null);
      }

      public static FloatType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (FloatType)XmlBeans.getContextTypeLoader().parse(file, FloatType.type, options);
      }

      public static FloatType parse(URL u) throws XmlException, IOException {
         return (FloatType)XmlBeans.getContextTypeLoader().parse(u, FloatType.type, (XmlOptions)null);
      }

      public static FloatType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (FloatType)XmlBeans.getContextTypeLoader().parse(u, FloatType.type, options);
      }

      public static FloatType parse(InputStream is) throws XmlException, IOException {
         return (FloatType)XmlBeans.getContextTypeLoader().parse(is, FloatType.type, (XmlOptions)null);
      }

      public static FloatType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (FloatType)XmlBeans.getContextTypeLoader().parse(is, FloatType.type, options);
      }

      public static FloatType parse(Reader r) throws XmlException, IOException {
         return (FloatType)XmlBeans.getContextTypeLoader().parse(r, FloatType.type, (XmlOptions)null);
      }

      public static FloatType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (FloatType)XmlBeans.getContextTypeLoader().parse(r, FloatType.type, options);
      }

      public static FloatType parse(XMLStreamReader sr) throws XmlException {
         return (FloatType)XmlBeans.getContextTypeLoader().parse(sr, FloatType.type, (XmlOptions)null);
      }

      public static FloatType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (FloatType)XmlBeans.getContextTypeLoader().parse(sr, FloatType.type, options);
      }

      public static FloatType parse(Node node) throws XmlException {
         return (FloatType)XmlBeans.getContextTypeLoader().parse(node, FloatType.type, (XmlOptions)null);
      }

      public static FloatType parse(Node node, XmlOptions options) throws XmlException {
         return (FloatType)XmlBeans.getContextTypeLoader().parse(node, FloatType.type, options);
      }

      /** @deprecated */
      public static FloatType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (FloatType)XmlBeans.getContextTypeLoader().parse(xis, FloatType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static FloatType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (FloatType)XmlBeans.getContextTypeLoader().parse(xis, FloatType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FloatType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FloatType.type, options);
      }

      private Factory() {
      }
   }
}
