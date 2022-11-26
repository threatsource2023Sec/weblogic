package com.bea.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface XmlToken extends XmlNormalizedString {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_token");

   public static final class Factory {
      public static XmlToken newInstance() {
         return (XmlToken)XmlBeans.getContextTypeLoader().newInstance(XmlToken.type, (XmlOptions)null);
      }

      public static XmlToken newInstance(XmlOptions options) {
         return (XmlToken)XmlBeans.getContextTypeLoader().newInstance(XmlToken.type, options);
      }

      public static XmlToken newValue(Object obj) {
         return (XmlToken)XmlToken.type.newValue(obj);
      }

      public static XmlToken parse(String s) throws XmlException {
         return (XmlToken)XmlBeans.getContextTypeLoader().parse((String)s, XmlToken.type, (XmlOptions)null);
      }

      public static XmlToken parse(String s, XmlOptions options) throws XmlException {
         return (XmlToken)XmlBeans.getContextTypeLoader().parse(s, XmlToken.type, options);
      }

      public static XmlToken parse(File f) throws XmlException, IOException {
         return (XmlToken)XmlBeans.getContextTypeLoader().parse((File)f, XmlToken.type, (XmlOptions)null);
      }

      public static XmlToken parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlToken)XmlBeans.getContextTypeLoader().parse(f, XmlToken.type, options);
      }

      public static XmlToken parse(URL u) throws XmlException, IOException {
         return (XmlToken)XmlBeans.getContextTypeLoader().parse((URL)u, XmlToken.type, (XmlOptions)null);
      }

      public static XmlToken parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlToken)XmlBeans.getContextTypeLoader().parse(u, XmlToken.type, options);
      }

      public static XmlToken parse(InputStream is) throws XmlException, IOException {
         return (XmlToken)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlToken.type, (XmlOptions)null);
      }

      public static XmlToken parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlToken)XmlBeans.getContextTypeLoader().parse(is, XmlToken.type, options);
      }

      public static XmlToken parse(Reader r) throws XmlException, IOException {
         return (XmlToken)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlToken.type, (XmlOptions)null);
      }

      public static XmlToken parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlToken)XmlBeans.getContextTypeLoader().parse(r, XmlToken.type, options);
      }

      public static XmlToken parse(Node node) throws XmlException {
         return (XmlToken)XmlBeans.getContextTypeLoader().parse((Node)node, XmlToken.type, (XmlOptions)null);
      }

      public static XmlToken parse(Node node, XmlOptions options) throws XmlException {
         return (XmlToken)XmlBeans.getContextTypeLoader().parse(node, XmlToken.type, options);
      }

      /** @deprecated */
      public static XmlToken parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlToken)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlToken.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlToken parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlToken)XmlBeans.getContextTypeLoader().parse(xis, XmlToken.type, options);
      }

      public static XmlToken parse(XMLStreamReader xsr) throws XmlException {
         return (XmlToken)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlToken.type, (XmlOptions)null);
      }

      public static XmlToken parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlToken)XmlBeans.getContextTypeLoader().parse(xsr, XmlToken.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlToken.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlToken.type, options);
      }

      private Factory() {
      }
   }
}
