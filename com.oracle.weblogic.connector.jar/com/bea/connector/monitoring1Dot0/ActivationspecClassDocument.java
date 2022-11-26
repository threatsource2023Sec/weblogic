package com.bea.connector.monitoring1Dot0;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
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

public interface ActivationspecClassDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ActivationspecClassDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("activationspecclassa6b2doctype");

   String getActivationspecClass();

   XmlString xgetActivationspecClass();

   void setActivationspecClass(String var1);

   void xsetActivationspecClass(XmlString var1);

   public static final class Factory {
      public static ActivationspecClassDocument newInstance() {
         return (ActivationspecClassDocument)XmlBeans.getContextTypeLoader().newInstance(ActivationspecClassDocument.type, (XmlOptions)null);
      }

      public static ActivationspecClassDocument newInstance(XmlOptions options) {
         return (ActivationspecClassDocument)XmlBeans.getContextTypeLoader().newInstance(ActivationspecClassDocument.type, options);
      }

      public static ActivationspecClassDocument parse(String xmlAsString) throws XmlException {
         return (ActivationspecClassDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ActivationspecClassDocument.type, (XmlOptions)null);
      }

      public static ActivationspecClassDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ActivationspecClassDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ActivationspecClassDocument.type, options);
      }

      public static ActivationspecClassDocument parse(File file) throws XmlException, IOException {
         return (ActivationspecClassDocument)XmlBeans.getContextTypeLoader().parse(file, ActivationspecClassDocument.type, (XmlOptions)null);
      }

      public static ActivationspecClassDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ActivationspecClassDocument)XmlBeans.getContextTypeLoader().parse(file, ActivationspecClassDocument.type, options);
      }

      public static ActivationspecClassDocument parse(URL u) throws XmlException, IOException {
         return (ActivationspecClassDocument)XmlBeans.getContextTypeLoader().parse(u, ActivationspecClassDocument.type, (XmlOptions)null);
      }

      public static ActivationspecClassDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ActivationspecClassDocument)XmlBeans.getContextTypeLoader().parse(u, ActivationspecClassDocument.type, options);
      }

      public static ActivationspecClassDocument parse(InputStream is) throws XmlException, IOException {
         return (ActivationspecClassDocument)XmlBeans.getContextTypeLoader().parse(is, ActivationspecClassDocument.type, (XmlOptions)null);
      }

      public static ActivationspecClassDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ActivationspecClassDocument)XmlBeans.getContextTypeLoader().parse(is, ActivationspecClassDocument.type, options);
      }

      public static ActivationspecClassDocument parse(Reader r) throws XmlException, IOException {
         return (ActivationspecClassDocument)XmlBeans.getContextTypeLoader().parse(r, ActivationspecClassDocument.type, (XmlOptions)null);
      }

      public static ActivationspecClassDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ActivationspecClassDocument)XmlBeans.getContextTypeLoader().parse(r, ActivationspecClassDocument.type, options);
      }

      public static ActivationspecClassDocument parse(XMLStreamReader sr) throws XmlException {
         return (ActivationspecClassDocument)XmlBeans.getContextTypeLoader().parse(sr, ActivationspecClassDocument.type, (XmlOptions)null);
      }

      public static ActivationspecClassDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ActivationspecClassDocument)XmlBeans.getContextTypeLoader().parse(sr, ActivationspecClassDocument.type, options);
      }

      public static ActivationspecClassDocument parse(Node node) throws XmlException {
         return (ActivationspecClassDocument)XmlBeans.getContextTypeLoader().parse(node, ActivationspecClassDocument.type, (XmlOptions)null);
      }

      public static ActivationspecClassDocument parse(Node node, XmlOptions options) throws XmlException {
         return (ActivationspecClassDocument)XmlBeans.getContextTypeLoader().parse(node, ActivationspecClassDocument.type, options);
      }

      /** @deprecated */
      public static ActivationspecClassDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ActivationspecClassDocument)XmlBeans.getContextTypeLoader().parse(xis, ActivationspecClassDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ActivationspecClassDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ActivationspecClassDocument)XmlBeans.getContextTypeLoader().parse(xis, ActivationspecClassDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ActivationspecClassDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ActivationspecClassDocument.type, options);
      }

      private Factory() {
      }
   }
}
