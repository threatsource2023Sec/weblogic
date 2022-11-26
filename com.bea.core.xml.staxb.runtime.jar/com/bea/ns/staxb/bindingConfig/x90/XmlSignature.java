package com.bea.ns.staxb.bindingConfig.x90;

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

public interface XmlSignature extends XmlToken {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(XmlSignature.class.getClassLoader(), "schemacom_bea_xml.system.bea_staxb_runtime").resolveHandle("xmlsignaturee36dtype");

   public static final class Factory {
      public static XmlSignature newValue(Object obj) {
         return (XmlSignature)XmlSignature.type.newValue(obj);
      }

      public static XmlSignature newInstance() {
         return (XmlSignature)XmlBeans.getContextTypeLoader().newInstance(XmlSignature.type, (XmlOptions)null);
      }

      public static XmlSignature newInstance(XmlOptions options) {
         return (XmlSignature)XmlBeans.getContextTypeLoader().newInstance(XmlSignature.type, options);
      }

      public static XmlSignature parse(String xmlAsString) throws XmlException {
         return (XmlSignature)XmlBeans.getContextTypeLoader().parse(xmlAsString, XmlSignature.type, (XmlOptions)null);
      }

      public static XmlSignature parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (XmlSignature)XmlBeans.getContextTypeLoader().parse(xmlAsString, XmlSignature.type, options);
      }

      public static XmlSignature parse(File file) throws XmlException, IOException {
         return (XmlSignature)XmlBeans.getContextTypeLoader().parse(file, XmlSignature.type, (XmlOptions)null);
      }

      public static XmlSignature parse(File file, XmlOptions options) throws XmlException, IOException {
         return (XmlSignature)XmlBeans.getContextTypeLoader().parse(file, XmlSignature.type, options);
      }

      public static XmlSignature parse(URL u) throws XmlException, IOException {
         return (XmlSignature)XmlBeans.getContextTypeLoader().parse(u, XmlSignature.type, (XmlOptions)null);
      }

      public static XmlSignature parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlSignature)XmlBeans.getContextTypeLoader().parse(u, XmlSignature.type, options);
      }

      public static XmlSignature parse(InputStream is) throws XmlException, IOException {
         return (XmlSignature)XmlBeans.getContextTypeLoader().parse(is, XmlSignature.type, (XmlOptions)null);
      }

      public static XmlSignature parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlSignature)XmlBeans.getContextTypeLoader().parse(is, XmlSignature.type, options);
      }

      public static XmlSignature parse(Reader r) throws XmlException, IOException {
         return (XmlSignature)XmlBeans.getContextTypeLoader().parse(r, XmlSignature.type, (XmlOptions)null);
      }

      public static XmlSignature parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlSignature)XmlBeans.getContextTypeLoader().parse(r, XmlSignature.type, options);
      }

      public static XmlSignature parse(XMLStreamReader sr) throws XmlException {
         return (XmlSignature)XmlBeans.getContextTypeLoader().parse(sr, XmlSignature.type, (XmlOptions)null);
      }

      public static XmlSignature parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (XmlSignature)XmlBeans.getContextTypeLoader().parse(sr, XmlSignature.type, options);
      }

      public static XmlSignature parse(Node node) throws XmlException {
         return (XmlSignature)XmlBeans.getContextTypeLoader().parse(node, XmlSignature.type, (XmlOptions)null);
      }

      public static XmlSignature parse(Node node, XmlOptions options) throws XmlException {
         return (XmlSignature)XmlBeans.getContextTypeLoader().parse(node, XmlSignature.type, options);
      }

      /** @deprecated */
      public static XmlSignature parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlSignature)XmlBeans.getContextTypeLoader().parse(xis, XmlSignature.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlSignature parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlSignature)XmlBeans.getContextTypeLoader().parse(xis, XmlSignature.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlSignature.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlSignature.type, options);
      }

      private Factory() {
      }
   }
}
