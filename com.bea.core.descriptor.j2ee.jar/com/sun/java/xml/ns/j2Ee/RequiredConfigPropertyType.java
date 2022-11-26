package com.sun.java.xml.ns.j2Ee;

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

public interface RequiredConfigPropertyType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(RequiredConfigPropertyType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("requiredconfigpropertytype81a5type");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   ConfigPropertyNameType getConfigPropertyName();

   void setConfigPropertyName(ConfigPropertyNameType var1);

   ConfigPropertyNameType addNewConfigPropertyName();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static RequiredConfigPropertyType newInstance() {
         return (RequiredConfigPropertyType)XmlBeans.getContextTypeLoader().newInstance(RequiredConfigPropertyType.type, (XmlOptions)null);
      }

      public static RequiredConfigPropertyType newInstance(XmlOptions options) {
         return (RequiredConfigPropertyType)XmlBeans.getContextTypeLoader().newInstance(RequiredConfigPropertyType.type, options);
      }

      public static RequiredConfigPropertyType parse(java.lang.String xmlAsString) throws XmlException {
         return (RequiredConfigPropertyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RequiredConfigPropertyType.type, (XmlOptions)null);
      }

      public static RequiredConfigPropertyType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (RequiredConfigPropertyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RequiredConfigPropertyType.type, options);
      }

      public static RequiredConfigPropertyType parse(File file) throws XmlException, IOException {
         return (RequiredConfigPropertyType)XmlBeans.getContextTypeLoader().parse(file, RequiredConfigPropertyType.type, (XmlOptions)null);
      }

      public static RequiredConfigPropertyType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (RequiredConfigPropertyType)XmlBeans.getContextTypeLoader().parse(file, RequiredConfigPropertyType.type, options);
      }

      public static RequiredConfigPropertyType parse(URL u) throws XmlException, IOException {
         return (RequiredConfigPropertyType)XmlBeans.getContextTypeLoader().parse(u, RequiredConfigPropertyType.type, (XmlOptions)null);
      }

      public static RequiredConfigPropertyType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (RequiredConfigPropertyType)XmlBeans.getContextTypeLoader().parse(u, RequiredConfigPropertyType.type, options);
      }

      public static RequiredConfigPropertyType parse(InputStream is) throws XmlException, IOException {
         return (RequiredConfigPropertyType)XmlBeans.getContextTypeLoader().parse(is, RequiredConfigPropertyType.type, (XmlOptions)null);
      }

      public static RequiredConfigPropertyType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (RequiredConfigPropertyType)XmlBeans.getContextTypeLoader().parse(is, RequiredConfigPropertyType.type, options);
      }

      public static RequiredConfigPropertyType parse(Reader r) throws XmlException, IOException {
         return (RequiredConfigPropertyType)XmlBeans.getContextTypeLoader().parse(r, RequiredConfigPropertyType.type, (XmlOptions)null);
      }

      public static RequiredConfigPropertyType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (RequiredConfigPropertyType)XmlBeans.getContextTypeLoader().parse(r, RequiredConfigPropertyType.type, options);
      }

      public static RequiredConfigPropertyType parse(XMLStreamReader sr) throws XmlException {
         return (RequiredConfigPropertyType)XmlBeans.getContextTypeLoader().parse(sr, RequiredConfigPropertyType.type, (XmlOptions)null);
      }

      public static RequiredConfigPropertyType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (RequiredConfigPropertyType)XmlBeans.getContextTypeLoader().parse(sr, RequiredConfigPropertyType.type, options);
      }

      public static RequiredConfigPropertyType parse(Node node) throws XmlException {
         return (RequiredConfigPropertyType)XmlBeans.getContextTypeLoader().parse(node, RequiredConfigPropertyType.type, (XmlOptions)null);
      }

      public static RequiredConfigPropertyType parse(Node node, XmlOptions options) throws XmlException {
         return (RequiredConfigPropertyType)XmlBeans.getContextTypeLoader().parse(node, RequiredConfigPropertyType.type, options);
      }

      /** @deprecated */
      public static RequiredConfigPropertyType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (RequiredConfigPropertyType)XmlBeans.getContextTypeLoader().parse(xis, RequiredConfigPropertyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static RequiredConfigPropertyType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (RequiredConfigPropertyType)XmlBeans.getContextTypeLoader().parse(xis, RequiredConfigPropertyType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RequiredConfigPropertyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RequiredConfigPropertyType.type, options);
      }

      private Factory() {
      }
   }
}
