package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlToken;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface HttpMethodType extends XmlToken {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(HttpMethodType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("httpmethodtype4201type");

   public static final class Factory {
      public static HttpMethodType newValue(Object obj) {
         return (HttpMethodType)HttpMethodType.type.newValue(obj);
      }

      public static HttpMethodType newInstance() {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().newInstance(HttpMethodType.type, (XmlOptions)null);
      }

      public static HttpMethodType newInstance(XmlOptions options) {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().newInstance(HttpMethodType.type, options);
      }

      public static HttpMethodType parse(java.lang.String xmlAsString) throws XmlException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(xmlAsString, HttpMethodType.type, (XmlOptions)null);
      }

      public static HttpMethodType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(xmlAsString, HttpMethodType.type, options);
      }

      public static HttpMethodType parse(File file) throws XmlException, IOException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(file, HttpMethodType.type, (XmlOptions)null);
      }

      public static HttpMethodType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(file, HttpMethodType.type, options);
      }

      public static HttpMethodType parse(URL u) throws XmlException, IOException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(u, HttpMethodType.type, (XmlOptions)null);
      }

      public static HttpMethodType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(u, HttpMethodType.type, options);
      }

      public static HttpMethodType parse(InputStream is) throws XmlException, IOException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(is, HttpMethodType.type, (XmlOptions)null);
      }

      public static HttpMethodType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(is, HttpMethodType.type, options);
      }

      public static HttpMethodType parse(Reader r) throws XmlException, IOException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(r, HttpMethodType.type, (XmlOptions)null);
      }

      public static HttpMethodType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(r, HttpMethodType.type, options);
      }

      public static HttpMethodType parse(XMLStreamReader sr) throws XmlException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(sr, HttpMethodType.type, (XmlOptions)null);
      }

      public static HttpMethodType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(sr, HttpMethodType.type, options);
      }

      public static HttpMethodType parse(Node node) throws XmlException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(node, HttpMethodType.type, (XmlOptions)null);
      }

      public static HttpMethodType parse(Node node, XmlOptions options) throws XmlException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(node, HttpMethodType.type, options);
      }

      /** @deprecated */
      public static HttpMethodType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(xis, HttpMethodType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static HttpMethodType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(xis, HttpMethodType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, HttpMethodType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, HttpMethodType.type, options);
      }

      private Factory() {
      }
   }
}
