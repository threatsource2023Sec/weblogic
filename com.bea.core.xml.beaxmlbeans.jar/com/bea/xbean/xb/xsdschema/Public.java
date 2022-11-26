package com.bea.xbean.xb.xsdschema;

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

public interface Public extends XmlToken {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Public.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("publicf3catype");

   public static final class Factory {
      public static Public newValue(Object obj) {
         return (Public)Public.type.newValue(obj);
      }

      public static Public newInstance() {
         return (Public)XmlBeans.getContextTypeLoader().newInstance(Public.type, (XmlOptions)null);
      }

      public static Public newInstance(XmlOptions options) {
         return (Public)XmlBeans.getContextTypeLoader().newInstance(Public.type, options);
      }

      public static Public parse(String xmlAsString) throws XmlException {
         return (Public)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, Public.type, (XmlOptions)null);
      }

      public static Public parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (Public)XmlBeans.getContextTypeLoader().parse(xmlAsString, Public.type, options);
      }

      public static Public parse(File file) throws XmlException, IOException {
         return (Public)XmlBeans.getContextTypeLoader().parse((File)file, Public.type, (XmlOptions)null);
      }

      public static Public parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Public)XmlBeans.getContextTypeLoader().parse(file, Public.type, options);
      }

      public static Public parse(URL u) throws XmlException, IOException {
         return (Public)XmlBeans.getContextTypeLoader().parse((URL)u, Public.type, (XmlOptions)null);
      }

      public static Public parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Public)XmlBeans.getContextTypeLoader().parse(u, Public.type, options);
      }

      public static Public parse(InputStream is) throws XmlException, IOException {
         return (Public)XmlBeans.getContextTypeLoader().parse((InputStream)is, Public.type, (XmlOptions)null);
      }

      public static Public parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Public)XmlBeans.getContextTypeLoader().parse(is, Public.type, options);
      }

      public static Public parse(Reader r) throws XmlException, IOException {
         return (Public)XmlBeans.getContextTypeLoader().parse((Reader)r, Public.type, (XmlOptions)null);
      }

      public static Public parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Public)XmlBeans.getContextTypeLoader().parse(r, Public.type, options);
      }

      public static Public parse(XMLStreamReader sr) throws XmlException {
         return (Public)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, Public.type, (XmlOptions)null);
      }

      public static Public parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Public)XmlBeans.getContextTypeLoader().parse(sr, Public.type, options);
      }

      public static Public parse(Node node) throws XmlException {
         return (Public)XmlBeans.getContextTypeLoader().parse((Node)node, Public.type, (XmlOptions)null);
      }

      public static Public parse(Node node, XmlOptions options) throws XmlException {
         return (Public)XmlBeans.getContextTypeLoader().parse(node, Public.type, options);
      }

      /** @deprecated */
      public static Public parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Public)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, Public.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Public parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Public)XmlBeans.getContextTypeLoader().parse(xis, Public.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Public.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Public.type, options);
      }

      private Factory() {
      }
   }
}
