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

public interface PersistenceUnitRefType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PersistenceUnitRefType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("persistenceunitreftype6765type");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   JndiNameType getPersistenceUnitRefName();

   void setPersistenceUnitRefName(JndiNameType var1);

   JndiNameType addNewPersistenceUnitRefName();

   String getPersistenceUnitName();

   boolean isSetPersistenceUnitName();

   void setPersistenceUnitName(String var1);

   String addNewPersistenceUnitName();

   void unsetPersistenceUnitName();

   XsdStringType getMappedName();

   boolean isSetMappedName();

   void setMappedName(XsdStringType var1);

   XsdStringType addNewMappedName();

   void unsetMappedName();

   InjectionTargetType[] getInjectionTargetArray();

   InjectionTargetType getInjectionTargetArray(int var1);

   int sizeOfInjectionTargetArray();

   void setInjectionTargetArray(InjectionTargetType[] var1);

   void setInjectionTargetArray(int var1, InjectionTargetType var2);

   InjectionTargetType insertNewInjectionTarget(int var1);

   InjectionTargetType addNewInjectionTarget();

   void removeInjectionTarget(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static PersistenceUnitRefType newInstance() {
         return (PersistenceUnitRefType)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitRefType.type, (XmlOptions)null);
      }

      public static PersistenceUnitRefType newInstance(XmlOptions options) {
         return (PersistenceUnitRefType)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitRefType.type, options);
      }

      public static PersistenceUnitRefType parse(java.lang.String xmlAsString) throws XmlException {
         return (PersistenceUnitRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceUnitRefType.type, (XmlOptions)null);
      }

      public static PersistenceUnitRefType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (PersistenceUnitRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceUnitRefType.type, options);
      }

      public static PersistenceUnitRefType parse(File file) throws XmlException, IOException {
         return (PersistenceUnitRefType)XmlBeans.getContextTypeLoader().parse(file, PersistenceUnitRefType.type, (XmlOptions)null);
      }

      public static PersistenceUnitRefType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PersistenceUnitRefType)XmlBeans.getContextTypeLoader().parse(file, PersistenceUnitRefType.type, options);
      }

      public static PersistenceUnitRefType parse(URL u) throws XmlException, IOException {
         return (PersistenceUnitRefType)XmlBeans.getContextTypeLoader().parse(u, PersistenceUnitRefType.type, (XmlOptions)null);
      }

      public static PersistenceUnitRefType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PersistenceUnitRefType)XmlBeans.getContextTypeLoader().parse(u, PersistenceUnitRefType.type, options);
      }

      public static PersistenceUnitRefType parse(InputStream is) throws XmlException, IOException {
         return (PersistenceUnitRefType)XmlBeans.getContextTypeLoader().parse(is, PersistenceUnitRefType.type, (XmlOptions)null);
      }

      public static PersistenceUnitRefType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PersistenceUnitRefType)XmlBeans.getContextTypeLoader().parse(is, PersistenceUnitRefType.type, options);
      }

      public static PersistenceUnitRefType parse(Reader r) throws XmlException, IOException {
         return (PersistenceUnitRefType)XmlBeans.getContextTypeLoader().parse(r, PersistenceUnitRefType.type, (XmlOptions)null);
      }

      public static PersistenceUnitRefType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PersistenceUnitRefType)XmlBeans.getContextTypeLoader().parse(r, PersistenceUnitRefType.type, options);
      }

      public static PersistenceUnitRefType parse(XMLStreamReader sr) throws XmlException {
         return (PersistenceUnitRefType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceUnitRefType.type, (XmlOptions)null);
      }

      public static PersistenceUnitRefType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PersistenceUnitRefType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceUnitRefType.type, options);
      }

      public static PersistenceUnitRefType parse(Node node) throws XmlException {
         return (PersistenceUnitRefType)XmlBeans.getContextTypeLoader().parse(node, PersistenceUnitRefType.type, (XmlOptions)null);
      }

      public static PersistenceUnitRefType parse(Node node, XmlOptions options) throws XmlException {
         return (PersistenceUnitRefType)XmlBeans.getContextTypeLoader().parse(node, PersistenceUnitRefType.type, options);
      }

      /** @deprecated */
      public static PersistenceUnitRefType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PersistenceUnitRefType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceUnitRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PersistenceUnitRefType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PersistenceUnitRefType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceUnitRefType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceUnitRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceUnitRefType.type, options);
      }

      private Factory() {
      }
   }
}
