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

public interface EnvEntryType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EnvEntryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("enventrytype18b3type");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   JndiNameType getEnvEntryName();

   void setEnvEntryName(JndiNameType var1);

   JndiNameType addNewEnvEntryName();

   EnvEntryTypeValuesType getEnvEntryType();

   void setEnvEntryType(EnvEntryTypeValuesType var1);

   EnvEntryTypeValuesType addNewEnvEntryType();

   XsdStringType getEnvEntryValue();

   boolean isSetEnvEntryValue();

   void setEnvEntryValue(XsdStringType var1);

   XsdStringType addNewEnvEntryValue();

   void unsetEnvEntryValue();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static EnvEntryType newInstance() {
         return (EnvEntryType)XmlBeans.getContextTypeLoader().newInstance(EnvEntryType.type, (XmlOptions)null);
      }

      public static EnvEntryType newInstance(XmlOptions options) {
         return (EnvEntryType)XmlBeans.getContextTypeLoader().newInstance(EnvEntryType.type, options);
      }

      public static EnvEntryType parse(java.lang.String xmlAsString) throws XmlException {
         return (EnvEntryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EnvEntryType.type, (XmlOptions)null);
      }

      public static EnvEntryType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (EnvEntryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EnvEntryType.type, options);
      }

      public static EnvEntryType parse(File file) throws XmlException, IOException {
         return (EnvEntryType)XmlBeans.getContextTypeLoader().parse(file, EnvEntryType.type, (XmlOptions)null);
      }

      public static EnvEntryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EnvEntryType)XmlBeans.getContextTypeLoader().parse(file, EnvEntryType.type, options);
      }

      public static EnvEntryType parse(URL u) throws XmlException, IOException {
         return (EnvEntryType)XmlBeans.getContextTypeLoader().parse(u, EnvEntryType.type, (XmlOptions)null);
      }

      public static EnvEntryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EnvEntryType)XmlBeans.getContextTypeLoader().parse(u, EnvEntryType.type, options);
      }

      public static EnvEntryType parse(InputStream is) throws XmlException, IOException {
         return (EnvEntryType)XmlBeans.getContextTypeLoader().parse(is, EnvEntryType.type, (XmlOptions)null);
      }

      public static EnvEntryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EnvEntryType)XmlBeans.getContextTypeLoader().parse(is, EnvEntryType.type, options);
      }

      public static EnvEntryType parse(Reader r) throws XmlException, IOException {
         return (EnvEntryType)XmlBeans.getContextTypeLoader().parse(r, EnvEntryType.type, (XmlOptions)null);
      }

      public static EnvEntryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EnvEntryType)XmlBeans.getContextTypeLoader().parse(r, EnvEntryType.type, options);
      }

      public static EnvEntryType parse(XMLStreamReader sr) throws XmlException {
         return (EnvEntryType)XmlBeans.getContextTypeLoader().parse(sr, EnvEntryType.type, (XmlOptions)null);
      }

      public static EnvEntryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EnvEntryType)XmlBeans.getContextTypeLoader().parse(sr, EnvEntryType.type, options);
      }

      public static EnvEntryType parse(Node node) throws XmlException {
         return (EnvEntryType)XmlBeans.getContextTypeLoader().parse(node, EnvEntryType.type, (XmlOptions)null);
      }

      public static EnvEntryType parse(Node node, XmlOptions options) throws XmlException {
         return (EnvEntryType)XmlBeans.getContextTypeLoader().parse(node, EnvEntryType.type, options);
      }

      /** @deprecated */
      public static EnvEntryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EnvEntryType)XmlBeans.getContextTypeLoader().parse(xis, EnvEntryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EnvEntryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EnvEntryType)XmlBeans.getContextTypeLoader().parse(xis, EnvEntryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EnvEntryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EnvEntryType.type, options);
      }

      private Factory() {
      }
   }
}
