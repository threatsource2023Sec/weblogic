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

public interface ApplicationType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ApplicationType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("applicationtypee5b1type");

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

   ModuleType[] getModuleArray();

   ModuleType getModuleArray(int var1);

   int sizeOfModuleArray();

   void setModuleArray(ModuleType[] var1);

   void setModuleArray(int var1, ModuleType var2);

   ModuleType insertNewModule(int var1);

   ModuleType addNewModule();

   void removeModule(int var1);

   SecurityRoleType[] getSecurityRoleArray();

   SecurityRoleType getSecurityRoleArray(int var1);

   int sizeOfSecurityRoleArray();

   void setSecurityRoleArray(SecurityRoleType[] var1);

   void setSecurityRoleArray(int var1, SecurityRoleType var2);

   SecurityRoleType insertNewSecurityRole(int var1);

   SecurityRoleType addNewSecurityRole();

   void removeSecurityRole(int var1);

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
      public static ApplicationType newInstance() {
         return (ApplicationType)XmlBeans.getContextTypeLoader().newInstance(ApplicationType.type, (XmlOptions)null);
      }

      public static ApplicationType newInstance(XmlOptions options) {
         return (ApplicationType)XmlBeans.getContextTypeLoader().newInstance(ApplicationType.type, options);
      }

      public static ApplicationType parse(java.lang.String xmlAsString) throws XmlException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ApplicationType.type, (XmlOptions)null);
      }

      public static ApplicationType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ApplicationType.type, options);
      }

      public static ApplicationType parse(File file) throws XmlException, IOException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(file, ApplicationType.type, (XmlOptions)null);
      }

      public static ApplicationType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(file, ApplicationType.type, options);
      }

      public static ApplicationType parse(URL u) throws XmlException, IOException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(u, ApplicationType.type, (XmlOptions)null);
      }

      public static ApplicationType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(u, ApplicationType.type, options);
      }

      public static ApplicationType parse(InputStream is) throws XmlException, IOException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(is, ApplicationType.type, (XmlOptions)null);
      }

      public static ApplicationType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(is, ApplicationType.type, options);
      }

      public static ApplicationType parse(Reader r) throws XmlException, IOException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(r, ApplicationType.type, (XmlOptions)null);
      }

      public static ApplicationType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(r, ApplicationType.type, options);
      }

      public static ApplicationType parse(XMLStreamReader sr) throws XmlException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(sr, ApplicationType.type, (XmlOptions)null);
      }

      public static ApplicationType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(sr, ApplicationType.type, options);
      }

      public static ApplicationType parse(Node node) throws XmlException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(node, ApplicationType.type, (XmlOptions)null);
      }

      public static ApplicationType parse(Node node, XmlOptions options) throws XmlException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(node, ApplicationType.type, options);
      }

      /** @deprecated */
      public static ApplicationType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(xis, ApplicationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ApplicationType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ApplicationType)XmlBeans.getContextTypeLoader().parse(xis, ApplicationType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ApplicationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ApplicationType.type, options);
      }

      private Factory() {
      }
   }
}
