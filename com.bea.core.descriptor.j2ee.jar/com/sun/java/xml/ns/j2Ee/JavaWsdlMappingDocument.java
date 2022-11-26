package com.sun.java.xml.ns.j2Ee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
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

public interface JavaWsdlMappingDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JavaWsdlMappingDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("javawsdlmapping7d67doctype");

   JavaWsdlMappingType getJavaWsdlMapping();

   void setJavaWsdlMapping(JavaWsdlMappingType var1);

   JavaWsdlMappingType addNewJavaWsdlMapping();

   public static final class Factory {
      public static JavaWsdlMappingDocument newInstance() {
         return (JavaWsdlMappingDocument)XmlBeans.getContextTypeLoader().newInstance(JavaWsdlMappingDocument.type, (XmlOptions)null);
      }

      public static JavaWsdlMappingDocument newInstance(XmlOptions options) {
         return (JavaWsdlMappingDocument)XmlBeans.getContextTypeLoader().newInstance(JavaWsdlMappingDocument.type, options);
      }

      public static JavaWsdlMappingDocument parse(java.lang.String xmlAsString) throws XmlException {
         return (JavaWsdlMappingDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, JavaWsdlMappingDocument.type, (XmlOptions)null);
      }

      public static JavaWsdlMappingDocument parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (JavaWsdlMappingDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, JavaWsdlMappingDocument.type, options);
      }

      public static JavaWsdlMappingDocument parse(File file) throws XmlException, IOException {
         return (JavaWsdlMappingDocument)XmlBeans.getContextTypeLoader().parse(file, JavaWsdlMappingDocument.type, (XmlOptions)null);
      }

      public static JavaWsdlMappingDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JavaWsdlMappingDocument)XmlBeans.getContextTypeLoader().parse(file, JavaWsdlMappingDocument.type, options);
      }

      public static JavaWsdlMappingDocument parse(URL u) throws XmlException, IOException {
         return (JavaWsdlMappingDocument)XmlBeans.getContextTypeLoader().parse(u, JavaWsdlMappingDocument.type, (XmlOptions)null);
      }

      public static JavaWsdlMappingDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JavaWsdlMappingDocument)XmlBeans.getContextTypeLoader().parse(u, JavaWsdlMappingDocument.type, options);
      }

      public static JavaWsdlMappingDocument parse(InputStream is) throws XmlException, IOException {
         return (JavaWsdlMappingDocument)XmlBeans.getContextTypeLoader().parse(is, JavaWsdlMappingDocument.type, (XmlOptions)null);
      }

      public static JavaWsdlMappingDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JavaWsdlMappingDocument)XmlBeans.getContextTypeLoader().parse(is, JavaWsdlMappingDocument.type, options);
      }

      public static JavaWsdlMappingDocument parse(Reader r) throws XmlException, IOException {
         return (JavaWsdlMappingDocument)XmlBeans.getContextTypeLoader().parse(r, JavaWsdlMappingDocument.type, (XmlOptions)null);
      }

      public static JavaWsdlMappingDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JavaWsdlMappingDocument)XmlBeans.getContextTypeLoader().parse(r, JavaWsdlMappingDocument.type, options);
      }

      public static JavaWsdlMappingDocument parse(XMLStreamReader sr) throws XmlException {
         return (JavaWsdlMappingDocument)XmlBeans.getContextTypeLoader().parse(sr, JavaWsdlMappingDocument.type, (XmlOptions)null);
      }

      public static JavaWsdlMappingDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JavaWsdlMappingDocument)XmlBeans.getContextTypeLoader().parse(sr, JavaWsdlMappingDocument.type, options);
      }

      public static JavaWsdlMappingDocument parse(Node node) throws XmlException {
         return (JavaWsdlMappingDocument)XmlBeans.getContextTypeLoader().parse(node, JavaWsdlMappingDocument.type, (XmlOptions)null);
      }

      public static JavaWsdlMappingDocument parse(Node node, XmlOptions options) throws XmlException {
         return (JavaWsdlMappingDocument)XmlBeans.getContextTypeLoader().parse(node, JavaWsdlMappingDocument.type, options);
      }

      /** @deprecated */
      public static JavaWsdlMappingDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JavaWsdlMappingDocument)XmlBeans.getContextTypeLoader().parse(xis, JavaWsdlMappingDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JavaWsdlMappingDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JavaWsdlMappingDocument)XmlBeans.getContextTypeLoader().parse(xis, JavaWsdlMappingDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JavaWsdlMappingDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JavaWsdlMappingDocument.type, options);
      }

      private Factory() {
      }
   }
}
