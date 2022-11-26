package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
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

public interface JavaTypeType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JavaTypeType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("javatypetype5ec0type");

   public static final class Factory {
      public static JavaTypeType newInstance() {
         return (JavaTypeType)XmlBeans.getContextTypeLoader().newInstance(JavaTypeType.type, (XmlOptions)null);
      }

      public static JavaTypeType newInstance(XmlOptions options) {
         return (JavaTypeType)XmlBeans.getContextTypeLoader().newInstance(JavaTypeType.type, options);
      }

      public static JavaTypeType parse(java.lang.String xmlAsString) throws XmlException {
         return (JavaTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JavaTypeType.type, (XmlOptions)null);
      }

      public static JavaTypeType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (JavaTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JavaTypeType.type, options);
      }

      public static JavaTypeType parse(File file) throws XmlException, IOException {
         return (JavaTypeType)XmlBeans.getContextTypeLoader().parse(file, JavaTypeType.type, (XmlOptions)null);
      }

      public static JavaTypeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JavaTypeType)XmlBeans.getContextTypeLoader().parse(file, JavaTypeType.type, options);
      }

      public static JavaTypeType parse(URL u) throws XmlException, IOException {
         return (JavaTypeType)XmlBeans.getContextTypeLoader().parse(u, JavaTypeType.type, (XmlOptions)null);
      }

      public static JavaTypeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JavaTypeType)XmlBeans.getContextTypeLoader().parse(u, JavaTypeType.type, options);
      }

      public static JavaTypeType parse(InputStream is) throws XmlException, IOException {
         return (JavaTypeType)XmlBeans.getContextTypeLoader().parse(is, JavaTypeType.type, (XmlOptions)null);
      }

      public static JavaTypeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JavaTypeType)XmlBeans.getContextTypeLoader().parse(is, JavaTypeType.type, options);
      }

      public static JavaTypeType parse(Reader r) throws XmlException, IOException {
         return (JavaTypeType)XmlBeans.getContextTypeLoader().parse(r, JavaTypeType.type, (XmlOptions)null);
      }

      public static JavaTypeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JavaTypeType)XmlBeans.getContextTypeLoader().parse(r, JavaTypeType.type, options);
      }

      public static JavaTypeType parse(XMLStreamReader sr) throws XmlException {
         return (JavaTypeType)XmlBeans.getContextTypeLoader().parse(sr, JavaTypeType.type, (XmlOptions)null);
      }

      public static JavaTypeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JavaTypeType)XmlBeans.getContextTypeLoader().parse(sr, JavaTypeType.type, options);
      }

      public static JavaTypeType parse(Node node) throws XmlException {
         return (JavaTypeType)XmlBeans.getContextTypeLoader().parse(node, JavaTypeType.type, (XmlOptions)null);
      }

      public static JavaTypeType parse(Node node, XmlOptions options) throws XmlException {
         return (JavaTypeType)XmlBeans.getContextTypeLoader().parse(node, JavaTypeType.type, options);
      }

      /** @deprecated */
      public static JavaTypeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JavaTypeType)XmlBeans.getContextTypeLoader().parse(xis, JavaTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JavaTypeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JavaTypeType)XmlBeans.getContextTypeLoader().parse(xis, JavaTypeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JavaTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JavaTypeType.type, options);
      }

      private Factory() {
      }
   }
}
