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

public interface JavaFieldName extends XmlToken {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JavaFieldName.class.getClassLoader(), "schemacom_bea_xml.system.bea_staxb_runtime").resolveHandle("javafieldnamed2f4type");

   public static final class Factory {
      public static JavaFieldName newValue(Object obj) {
         return (JavaFieldName)JavaFieldName.type.newValue(obj);
      }

      public static JavaFieldName newInstance() {
         return (JavaFieldName)XmlBeans.getContextTypeLoader().newInstance(JavaFieldName.type, (XmlOptions)null);
      }

      public static JavaFieldName newInstance(XmlOptions options) {
         return (JavaFieldName)XmlBeans.getContextTypeLoader().newInstance(JavaFieldName.type, options);
      }

      public static JavaFieldName parse(String xmlAsString) throws XmlException {
         return (JavaFieldName)XmlBeans.getContextTypeLoader().parse(xmlAsString, JavaFieldName.type, (XmlOptions)null);
      }

      public static JavaFieldName parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (JavaFieldName)XmlBeans.getContextTypeLoader().parse(xmlAsString, JavaFieldName.type, options);
      }

      public static JavaFieldName parse(File file) throws XmlException, IOException {
         return (JavaFieldName)XmlBeans.getContextTypeLoader().parse(file, JavaFieldName.type, (XmlOptions)null);
      }

      public static JavaFieldName parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JavaFieldName)XmlBeans.getContextTypeLoader().parse(file, JavaFieldName.type, options);
      }

      public static JavaFieldName parse(URL u) throws XmlException, IOException {
         return (JavaFieldName)XmlBeans.getContextTypeLoader().parse(u, JavaFieldName.type, (XmlOptions)null);
      }

      public static JavaFieldName parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JavaFieldName)XmlBeans.getContextTypeLoader().parse(u, JavaFieldName.type, options);
      }

      public static JavaFieldName parse(InputStream is) throws XmlException, IOException {
         return (JavaFieldName)XmlBeans.getContextTypeLoader().parse(is, JavaFieldName.type, (XmlOptions)null);
      }

      public static JavaFieldName parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JavaFieldName)XmlBeans.getContextTypeLoader().parse(is, JavaFieldName.type, options);
      }

      public static JavaFieldName parse(Reader r) throws XmlException, IOException {
         return (JavaFieldName)XmlBeans.getContextTypeLoader().parse(r, JavaFieldName.type, (XmlOptions)null);
      }

      public static JavaFieldName parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JavaFieldName)XmlBeans.getContextTypeLoader().parse(r, JavaFieldName.type, options);
      }

      public static JavaFieldName parse(XMLStreamReader sr) throws XmlException {
         return (JavaFieldName)XmlBeans.getContextTypeLoader().parse(sr, JavaFieldName.type, (XmlOptions)null);
      }

      public static JavaFieldName parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JavaFieldName)XmlBeans.getContextTypeLoader().parse(sr, JavaFieldName.type, options);
      }

      public static JavaFieldName parse(Node node) throws XmlException {
         return (JavaFieldName)XmlBeans.getContextTypeLoader().parse(node, JavaFieldName.type, (XmlOptions)null);
      }

      public static JavaFieldName parse(Node node, XmlOptions options) throws XmlException {
         return (JavaFieldName)XmlBeans.getContextTypeLoader().parse(node, JavaFieldName.type, options);
      }

      /** @deprecated */
      public static JavaFieldName parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JavaFieldName)XmlBeans.getContextTypeLoader().parse(xis, JavaFieldName.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JavaFieldName parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JavaFieldName)XmlBeans.getContextTypeLoader().parse(xis, JavaFieldName.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JavaFieldName.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JavaFieldName.type, options);
      }

      private Factory() {
      }
   }
}
