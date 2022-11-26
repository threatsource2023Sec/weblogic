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

public interface EjbNameType extends XsdNMTOKENType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EjbNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("ejbnametype1068type");

   public static final class Factory {
      public static EjbNameType newInstance() {
         return (EjbNameType)XmlBeans.getContextTypeLoader().newInstance(EjbNameType.type, (XmlOptions)null);
      }

      public static EjbNameType newInstance(XmlOptions options) {
         return (EjbNameType)XmlBeans.getContextTypeLoader().newInstance(EjbNameType.type, options);
      }

      public static EjbNameType parse(java.lang.String xmlAsString) throws XmlException {
         return (EjbNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EjbNameType.type, (XmlOptions)null);
      }

      public static EjbNameType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (EjbNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EjbNameType.type, options);
      }

      public static EjbNameType parse(File file) throws XmlException, IOException {
         return (EjbNameType)XmlBeans.getContextTypeLoader().parse(file, EjbNameType.type, (XmlOptions)null);
      }

      public static EjbNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EjbNameType)XmlBeans.getContextTypeLoader().parse(file, EjbNameType.type, options);
      }

      public static EjbNameType parse(URL u) throws XmlException, IOException {
         return (EjbNameType)XmlBeans.getContextTypeLoader().parse(u, EjbNameType.type, (XmlOptions)null);
      }

      public static EjbNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EjbNameType)XmlBeans.getContextTypeLoader().parse(u, EjbNameType.type, options);
      }

      public static EjbNameType parse(InputStream is) throws XmlException, IOException {
         return (EjbNameType)XmlBeans.getContextTypeLoader().parse(is, EjbNameType.type, (XmlOptions)null);
      }

      public static EjbNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EjbNameType)XmlBeans.getContextTypeLoader().parse(is, EjbNameType.type, options);
      }

      public static EjbNameType parse(Reader r) throws XmlException, IOException {
         return (EjbNameType)XmlBeans.getContextTypeLoader().parse(r, EjbNameType.type, (XmlOptions)null);
      }

      public static EjbNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EjbNameType)XmlBeans.getContextTypeLoader().parse(r, EjbNameType.type, options);
      }

      public static EjbNameType parse(XMLStreamReader sr) throws XmlException {
         return (EjbNameType)XmlBeans.getContextTypeLoader().parse(sr, EjbNameType.type, (XmlOptions)null);
      }

      public static EjbNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EjbNameType)XmlBeans.getContextTypeLoader().parse(sr, EjbNameType.type, options);
      }

      public static EjbNameType parse(Node node) throws XmlException {
         return (EjbNameType)XmlBeans.getContextTypeLoader().parse(node, EjbNameType.type, (XmlOptions)null);
      }

      public static EjbNameType parse(Node node, XmlOptions options) throws XmlException {
         return (EjbNameType)XmlBeans.getContextTypeLoader().parse(node, EjbNameType.type, options);
      }

      /** @deprecated */
      public static EjbNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EjbNameType)XmlBeans.getContextTypeLoader().parse(xis, EjbNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EjbNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EjbNameType)XmlBeans.getContextTypeLoader().parse(xis, EjbNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EjbNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EjbNameType.type, options);
      }

      private Factory() {
      }
   }
}
