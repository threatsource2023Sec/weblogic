package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface PropertyType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PropertyType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("propertytype74c2type");

   XsdStringType getName();

   void setName(XsdStringType var1);

   XsdStringType addNewName();

   XsdStringType getValue();

   void setValue(XsdStringType var1);

   XsdStringType addNewValue();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static PropertyType newInstance() {
         return (PropertyType)XmlBeans.getContextTypeLoader().newInstance(PropertyType.type, (XmlOptions)null);
      }

      public static PropertyType newInstance(XmlOptions options) {
         return (PropertyType)XmlBeans.getContextTypeLoader().newInstance(PropertyType.type, options);
      }

      public static PropertyType parse(java.lang.String xmlAsString) throws XmlException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PropertyType.type, (XmlOptions)null);
      }

      public static PropertyType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PropertyType.type, options);
      }

      public static PropertyType parse(File file) throws XmlException, IOException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(file, PropertyType.type, (XmlOptions)null);
      }

      public static PropertyType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(file, PropertyType.type, options);
      }

      public static PropertyType parse(URL u) throws XmlException, IOException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(u, PropertyType.type, (XmlOptions)null);
      }

      public static PropertyType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(u, PropertyType.type, options);
      }

      public static PropertyType parse(InputStream is) throws XmlException, IOException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(is, PropertyType.type, (XmlOptions)null);
      }

      public static PropertyType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(is, PropertyType.type, options);
      }

      public static PropertyType parse(Reader r) throws XmlException, IOException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(r, PropertyType.type, (XmlOptions)null);
      }

      public static PropertyType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(r, PropertyType.type, options);
      }

      public static PropertyType parse(XMLStreamReader sr) throws XmlException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(sr, PropertyType.type, (XmlOptions)null);
      }

      public static PropertyType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(sr, PropertyType.type, options);
      }

      public static PropertyType parse(Node node) throws XmlException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(node, PropertyType.type, (XmlOptions)null);
      }

      public static PropertyType parse(Node node, XmlOptions options) throws XmlException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(node, PropertyType.type, options);
      }

      /** @deprecated */
      public static PropertyType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(xis, PropertyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PropertyType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PropertyType)XmlBeans.getContextTypeLoader().parse(xis, PropertyType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PropertyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PropertyType.type, options);
      }

      private Factory() {
      }
   }
}
