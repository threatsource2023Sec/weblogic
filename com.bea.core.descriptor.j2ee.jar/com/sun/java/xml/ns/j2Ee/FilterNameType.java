package com.sun.java.xml.ns.j2Ee;

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

public interface FilterNameType extends NonEmptyStringType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(FilterNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("filternametypea421type");

   public static final class Factory {
      public static FilterNameType newInstance() {
         return (FilterNameType)XmlBeans.getContextTypeLoader().newInstance(FilterNameType.type, (XmlOptions)null);
      }

      public static FilterNameType newInstance(XmlOptions options) {
         return (FilterNameType)XmlBeans.getContextTypeLoader().newInstance(FilterNameType.type, options);
      }

      public static FilterNameType parse(java.lang.String xmlAsString) throws XmlException {
         return (FilterNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FilterNameType.type, (XmlOptions)null);
      }

      public static FilterNameType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (FilterNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FilterNameType.type, options);
      }

      public static FilterNameType parse(File file) throws XmlException, IOException {
         return (FilterNameType)XmlBeans.getContextTypeLoader().parse(file, FilterNameType.type, (XmlOptions)null);
      }

      public static FilterNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (FilterNameType)XmlBeans.getContextTypeLoader().parse(file, FilterNameType.type, options);
      }

      public static FilterNameType parse(URL u) throws XmlException, IOException {
         return (FilterNameType)XmlBeans.getContextTypeLoader().parse(u, FilterNameType.type, (XmlOptions)null);
      }

      public static FilterNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (FilterNameType)XmlBeans.getContextTypeLoader().parse(u, FilterNameType.type, options);
      }

      public static FilterNameType parse(InputStream is) throws XmlException, IOException {
         return (FilterNameType)XmlBeans.getContextTypeLoader().parse(is, FilterNameType.type, (XmlOptions)null);
      }

      public static FilterNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (FilterNameType)XmlBeans.getContextTypeLoader().parse(is, FilterNameType.type, options);
      }

      public static FilterNameType parse(Reader r) throws XmlException, IOException {
         return (FilterNameType)XmlBeans.getContextTypeLoader().parse(r, FilterNameType.type, (XmlOptions)null);
      }

      public static FilterNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (FilterNameType)XmlBeans.getContextTypeLoader().parse(r, FilterNameType.type, options);
      }

      public static FilterNameType parse(XMLStreamReader sr) throws XmlException {
         return (FilterNameType)XmlBeans.getContextTypeLoader().parse(sr, FilterNameType.type, (XmlOptions)null);
      }

      public static FilterNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (FilterNameType)XmlBeans.getContextTypeLoader().parse(sr, FilterNameType.type, options);
      }

      public static FilterNameType parse(Node node) throws XmlException {
         return (FilterNameType)XmlBeans.getContextTypeLoader().parse(node, FilterNameType.type, (XmlOptions)null);
      }

      public static FilterNameType parse(Node node, XmlOptions options) throws XmlException {
         return (FilterNameType)XmlBeans.getContextTypeLoader().parse(node, FilterNameType.type, options);
      }

      /** @deprecated */
      public static FilterNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (FilterNameType)XmlBeans.getContextTypeLoader().parse(xis, FilterNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static FilterNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (FilterNameType)XmlBeans.getContextTypeLoader().parse(xis, FilterNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FilterNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FilterNameType.type, options);
      }

      private Factory() {
      }
   }
}
