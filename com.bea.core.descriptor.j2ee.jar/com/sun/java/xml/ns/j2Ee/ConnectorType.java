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
import java.math.BigDecimal;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface ConnectorType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConnectorType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("connectortype0beetype");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   DisplayNameType[] getDisplayNameArray();

   DisplayNameType getDisplayNameArray(int var1);

   int sizeOfDisplayNameArray();

   void setDisplayNameArray(DisplayNameType[] var1);

   void setDisplayNameArray(int var1, DisplayNameType var2);

   DisplayNameType insertNewDisplayName(int var1);

   DisplayNameType addNewDisplayName();

   void removeDisplayName(int var1);

   IconType[] getIconArray();

   IconType getIconArray(int var1);

   int sizeOfIconArray();

   void setIconArray(IconType[] var1);

   void setIconArray(int var1, IconType var2);

   IconType insertNewIcon(int var1);

   IconType addNewIcon();

   void removeIcon(int var1);

   XsdStringType getVendorName();

   void setVendorName(XsdStringType var1);

   XsdStringType addNewVendorName();

   XsdStringType getEisType();

   void setEisType(XsdStringType var1);

   XsdStringType addNewEisType();

   XsdStringType getResourceadapterVersion();

   void setResourceadapterVersion(XsdStringType var1);

   XsdStringType addNewResourceadapterVersion();

   LicenseType getLicense();

   boolean isSetLicense();

   void setLicense(LicenseType var1);

   LicenseType addNewLicense();

   void unsetLicense();

   ResourceadapterType getResourceadapter();

   void setResourceadapter(ResourceadapterType var1);

   ResourceadapterType addNewResourceadapter();

   BigDecimal getVersion();

   DeweyVersionType xgetVersion();

   void setVersion(BigDecimal var1);

   void xsetVersion(DeweyVersionType var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ConnectorType newInstance() {
         return (ConnectorType)XmlBeans.getContextTypeLoader().newInstance(ConnectorType.type, (XmlOptions)null);
      }

      public static ConnectorType newInstance(XmlOptions options) {
         return (ConnectorType)XmlBeans.getContextTypeLoader().newInstance(ConnectorType.type, options);
      }

      public static ConnectorType parse(java.lang.String xmlAsString) throws XmlException {
         return (ConnectorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectorType.type, (XmlOptions)null);
      }

      public static ConnectorType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ConnectorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectorType.type, options);
      }

      public static ConnectorType parse(File file) throws XmlException, IOException {
         return (ConnectorType)XmlBeans.getContextTypeLoader().parse(file, ConnectorType.type, (XmlOptions)null);
      }

      public static ConnectorType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConnectorType)XmlBeans.getContextTypeLoader().parse(file, ConnectorType.type, options);
      }

      public static ConnectorType parse(URL u) throws XmlException, IOException {
         return (ConnectorType)XmlBeans.getContextTypeLoader().parse(u, ConnectorType.type, (XmlOptions)null);
      }

      public static ConnectorType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConnectorType)XmlBeans.getContextTypeLoader().parse(u, ConnectorType.type, options);
      }

      public static ConnectorType parse(InputStream is) throws XmlException, IOException {
         return (ConnectorType)XmlBeans.getContextTypeLoader().parse(is, ConnectorType.type, (XmlOptions)null);
      }

      public static ConnectorType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConnectorType)XmlBeans.getContextTypeLoader().parse(is, ConnectorType.type, options);
      }

      public static ConnectorType parse(Reader r) throws XmlException, IOException {
         return (ConnectorType)XmlBeans.getContextTypeLoader().parse(r, ConnectorType.type, (XmlOptions)null);
      }

      public static ConnectorType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConnectorType)XmlBeans.getContextTypeLoader().parse(r, ConnectorType.type, options);
      }

      public static ConnectorType parse(XMLStreamReader sr) throws XmlException {
         return (ConnectorType)XmlBeans.getContextTypeLoader().parse(sr, ConnectorType.type, (XmlOptions)null);
      }

      public static ConnectorType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConnectorType)XmlBeans.getContextTypeLoader().parse(sr, ConnectorType.type, options);
      }

      public static ConnectorType parse(Node node) throws XmlException {
         return (ConnectorType)XmlBeans.getContextTypeLoader().parse(node, ConnectorType.type, (XmlOptions)null);
      }

      public static ConnectorType parse(Node node, XmlOptions options) throws XmlException {
         return (ConnectorType)XmlBeans.getContextTypeLoader().parse(node, ConnectorType.type, options);
      }

      /** @deprecated */
      public static ConnectorType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConnectorType)XmlBeans.getContextTypeLoader().parse(xis, ConnectorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConnectorType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConnectorType)XmlBeans.getContextTypeLoader().parse(xis, ConnectorType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectorType.type, options);
      }

      private Factory() {
      }
   }
}
