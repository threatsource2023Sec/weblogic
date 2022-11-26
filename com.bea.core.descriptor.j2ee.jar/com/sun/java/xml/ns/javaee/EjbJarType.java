package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
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

public interface EjbJarType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EjbJarType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("ejbjartypef8a6type");

   String getModuleName();

   boolean isSetModuleName();

   void setModuleName(String var1);

   String addNewModuleName();

   void unsetModuleName();

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

   EnterpriseBeansType getEnterpriseBeans();

   boolean isSetEnterpriseBeans();

   void setEnterpriseBeans(EnterpriseBeansType var1);

   EnterpriseBeansType addNewEnterpriseBeans();

   void unsetEnterpriseBeans();

   InterceptorsType getInterceptors();

   boolean isSetInterceptors();

   void setInterceptors(InterceptorsType var1);

   InterceptorsType addNewInterceptors();

   void unsetInterceptors();

   RelationshipsType getRelationships();

   boolean isSetRelationships();

   void setRelationships(RelationshipsType var1);

   RelationshipsType addNewRelationships();

   void unsetRelationships();

   AssemblyDescriptorType getAssemblyDescriptor();

   boolean isSetAssemblyDescriptor();

   void setAssemblyDescriptor(AssemblyDescriptorType var1);

   AssemblyDescriptorType addNewAssemblyDescriptor();

   void unsetAssemblyDescriptor();

   PathType getEjbClientJar();

   boolean isSetEjbClientJar();

   void setEjbClientJar(PathType var1);

   PathType addNewEjbClientJar();

   void unsetEjbClientJar();

   java.lang.String getVersion();

   DeweyVersionType xgetVersion();

   void setVersion(java.lang.String var1);

   void xsetVersion(DeweyVersionType var1);

   boolean getMetadataComplete();

   XmlBoolean xgetMetadataComplete();

   boolean isSetMetadataComplete();

   void setMetadataComplete(boolean var1);

   void xsetMetadataComplete(XmlBoolean var1);

   void unsetMetadataComplete();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static EjbJarType newInstance() {
         return (EjbJarType)XmlBeans.getContextTypeLoader().newInstance(EjbJarType.type, (XmlOptions)null);
      }

      public static EjbJarType newInstance(XmlOptions options) {
         return (EjbJarType)XmlBeans.getContextTypeLoader().newInstance(EjbJarType.type, options);
      }

      public static EjbJarType parse(java.lang.String xmlAsString) throws XmlException {
         return (EjbJarType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EjbJarType.type, (XmlOptions)null);
      }

      public static EjbJarType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (EjbJarType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EjbJarType.type, options);
      }

      public static EjbJarType parse(File file) throws XmlException, IOException {
         return (EjbJarType)XmlBeans.getContextTypeLoader().parse(file, EjbJarType.type, (XmlOptions)null);
      }

      public static EjbJarType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EjbJarType)XmlBeans.getContextTypeLoader().parse(file, EjbJarType.type, options);
      }

      public static EjbJarType parse(URL u) throws XmlException, IOException {
         return (EjbJarType)XmlBeans.getContextTypeLoader().parse(u, EjbJarType.type, (XmlOptions)null);
      }

      public static EjbJarType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EjbJarType)XmlBeans.getContextTypeLoader().parse(u, EjbJarType.type, options);
      }

      public static EjbJarType parse(InputStream is) throws XmlException, IOException {
         return (EjbJarType)XmlBeans.getContextTypeLoader().parse(is, EjbJarType.type, (XmlOptions)null);
      }

      public static EjbJarType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EjbJarType)XmlBeans.getContextTypeLoader().parse(is, EjbJarType.type, options);
      }

      public static EjbJarType parse(Reader r) throws XmlException, IOException {
         return (EjbJarType)XmlBeans.getContextTypeLoader().parse(r, EjbJarType.type, (XmlOptions)null);
      }

      public static EjbJarType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EjbJarType)XmlBeans.getContextTypeLoader().parse(r, EjbJarType.type, options);
      }

      public static EjbJarType parse(XMLStreamReader sr) throws XmlException {
         return (EjbJarType)XmlBeans.getContextTypeLoader().parse(sr, EjbJarType.type, (XmlOptions)null);
      }

      public static EjbJarType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EjbJarType)XmlBeans.getContextTypeLoader().parse(sr, EjbJarType.type, options);
      }

      public static EjbJarType parse(Node node) throws XmlException {
         return (EjbJarType)XmlBeans.getContextTypeLoader().parse(node, EjbJarType.type, (XmlOptions)null);
      }

      public static EjbJarType parse(Node node, XmlOptions options) throws XmlException {
         return (EjbJarType)XmlBeans.getContextTypeLoader().parse(node, EjbJarType.type, options);
      }

      /** @deprecated */
      public static EjbJarType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EjbJarType)XmlBeans.getContextTypeLoader().parse(xis, EjbJarType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EjbJarType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EjbJarType)XmlBeans.getContextTypeLoader().parse(xis, EjbJarType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EjbJarType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EjbJarType.type, options);
      }

      private Factory() {
      }
   }
}
