package com.oracle.xmlns.weblogic.weblogicWebApp;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.String;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface PropertyNamevalueType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PropertyNamevalueType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("propertynamevaluetypedec1type");

   String getName();

   void setName(String var1);

   String addNewName();

   String getValue();

   void setValue(String var1);

   String addNewValue();

   public static final class Factory {
      public static PropertyNamevalueType newInstance() {
         return (PropertyNamevalueType)XmlBeans.getContextTypeLoader().newInstance(PropertyNamevalueType.type, (XmlOptions)null);
      }

      public static PropertyNamevalueType newInstance(XmlOptions options) {
         return (PropertyNamevalueType)XmlBeans.getContextTypeLoader().newInstance(PropertyNamevalueType.type, options);
      }

      public static PropertyNamevalueType parse(java.lang.String xmlAsString) throws XmlException {
         return (PropertyNamevalueType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PropertyNamevalueType.type, (XmlOptions)null);
      }

      public static PropertyNamevalueType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (PropertyNamevalueType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PropertyNamevalueType.type, options);
      }

      public static PropertyNamevalueType parse(File file) throws XmlException, IOException {
         return (PropertyNamevalueType)XmlBeans.getContextTypeLoader().parse(file, PropertyNamevalueType.type, (XmlOptions)null);
      }

      public static PropertyNamevalueType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PropertyNamevalueType)XmlBeans.getContextTypeLoader().parse(file, PropertyNamevalueType.type, options);
      }

      public static PropertyNamevalueType parse(URL u) throws XmlException, IOException {
         return (PropertyNamevalueType)XmlBeans.getContextTypeLoader().parse(u, PropertyNamevalueType.type, (XmlOptions)null);
      }

      public static PropertyNamevalueType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PropertyNamevalueType)XmlBeans.getContextTypeLoader().parse(u, PropertyNamevalueType.type, options);
      }

      public static PropertyNamevalueType parse(InputStream is) throws XmlException, IOException {
         return (PropertyNamevalueType)XmlBeans.getContextTypeLoader().parse(is, PropertyNamevalueType.type, (XmlOptions)null);
      }

      public static PropertyNamevalueType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PropertyNamevalueType)XmlBeans.getContextTypeLoader().parse(is, PropertyNamevalueType.type, options);
      }

      public static PropertyNamevalueType parse(Reader r) throws XmlException, IOException {
         return (PropertyNamevalueType)XmlBeans.getContextTypeLoader().parse(r, PropertyNamevalueType.type, (XmlOptions)null);
      }

      public static PropertyNamevalueType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PropertyNamevalueType)XmlBeans.getContextTypeLoader().parse(r, PropertyNamevalueType.type, options);
      }

      public static PropertyNamevalueType parse(XMLStreamReader sr) throws XmlException {
         return (PropertyNamevalueType)XmlBeans.getContextTypeLoader().parse(sr, PropertyNamevalueType.type, (XmlOptions)null);
      }

      public static PropertyNamevalueType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PropertyNamevalueType)XmlBeans.getContextTypeLoader().parse(sr, PropertyNamevalueType.type, options);
      }

      public static PropertyNamevalueType parse(Node node) throws XmlException {
         return (PropertyNamevalueType)XmlBeans.getContextTypeLoader().parse(node, PropertyNamevalueType.type, (XmlOptions)null);
      }

      public static PropertyNamevalueType parse(Node node, XmlOptions options) throws XmlException {
         return (PropertyNamevalueType)XmlBeans.getContextTypeLoader().parse(node, PropertyNamevalueType.type, options);
      }

      /** @deprecated */
      public static PropertyNamevalueType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PropertyNamevalueType)XmlBeans.getContextTypeLoader().parse(xis, PropertyNamevalueType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PropertyNamevalueType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PropertyNamevalueType)XmlBeans.getContextTypeLoader().parse(xis, PropertyNamevalueType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PropertyNamevalueType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PropertyNamevalueType.type, options);
      }

      private Factory() {
      }
   }
}
