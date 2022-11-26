package org.jcp.xmlns.xml.ns.javaee;

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

public interface PersistenceContextRefType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PersistenceContextRefType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("persistencecontextreftypefc96type");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   JndiNameType getPersistenceContextRefName();

   void setPersistenceContextRefName(JndiNameType var1);

   JndiNameType addNewPersistenceContextRefName();

   String getPersistenceUnitName();

   boolean isSetPersistenceUnitName();

   void setPersistenceUnitName(String var1);

   String addNewPersistenceUnitName();

   void unsetPersistenceUnitName();

   PersistenceContextTypeType getPersistenceContextType();

   boolean isSetPersistenceContextType();

   void setPersistenceContextType(PersistenceContextTypeType var1);

   PersistenceContextTypeType addNewPersistenceContextType();

   void unsetPersistenceContextType();

   PersistenceContextSynchronizationType getPersistenceContextSynchronization();

   boolean isSetPersistenceContextSynchronization();

   void setPersistenceContextSynchronization(PersistenceContextSynchronizationType var1);

   PersistenceContextSynchronizationType addNewPersistenceContextSynchronization();

   void unsetPersistenceContextSynchronization();

   PropertyType[] getPersistencePropertyArray();

   PropertyType getPersistencePropertyArray(int var1);

   int sizeOfPersistencePropertyArray();

   void setPersistencePropertyArray(PropertyType[] var1);

   void setPersistencePropertyArray(int var1, PropertyType var2);

   PropertyType insertNewPersistenceProperty(int var1);

   PropertyType addNewPersistenceProperty();

   void removePersistenceProperty(int var1);

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
      public static PersistenceContextRefType newInstance() {
         return (PersistenceContextRefType)XmlBeans.getContextTypeLoader().newInstance(PersistenceContextRefType.type, (XmlOptions)null);
      }

      public static PersistenceContextRefType newInstance(XmlOptions options) {
         return (PersistenceContextRefType)XmlBeans.getContextTypeLoader().newInstance(PersistenceContextRefType.type, options);
      }

      public static PersistenceContextRefType parse(java.lang.String xmlAsString) throws XmlException {
         return (PersistenceContextRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceContextRefType.type, (XmlOptions)null);
      }

      public static PersistenceContextRefType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (PersistenceContextRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceContextRefType.type, options);
      }

      public static PersistenceContextRefType parse(File file) throws XmlException, IOException {
         return (PersistenceContextRefType)XmlBeans.getContextTypeLoader().parse(file, PersistenceContextRefType.type, (XmlOptions)null);
      }

      public static PersistenceContextRefType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PersistenceContextRefType)XmlBeans.getContextTypeLoader().parse(file, PersistenceContextRefType.type, options);
      }

      public static PersistenceContextRefType parse(URL u) throws XmlException, IOException {
         return (PersistenceContextRefType)XmlBeans.getContextTypeLoader().parse(u, PersistenceContextRefType.type, (XmlOptions)null);
      }

      public static PersistenceContextRefType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PersistenceContextRefType)XmlBeans.getContextTypeLoader().parse(u, PersistenceContextRefType.type, options);
      }

      public static PersistenceContextRefType parse(InputStream is) throws XmlException, IOException {
         return (PersistenceContextRefType)XmlBeans.getContextTypeLoader().parse(is, PersistenceContextRefType.type, (XmlOptions)null);
      }

      public static PersistenceContextRefType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PersistenceContextRefType)XmlBeans.getContextTypeLoader().parse(is, PersistenceContextRefType.type, options);
      }

      public static PersistenceContextRefType parse(Reader r) throws XmlException, IOException {
         return (PersistenceContextRefType)XmlBeans.getContextTypeLoader().parse(r, PersistenceContextRefType.type, (XmlOptions)null);
      }

      public static PersistenceContextRefType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PersistenceContextRefType)XmlBeans.getContextTypeLoader().parse(r, PersistenceContextRefType.type, options);
      }

      public static PersistenceContextRefType parse(XMLStreamReader sr) throws XmlException {
         return (PersistenceContextRefType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceContextRefType.type, (XmlOptions)null);
      }

      public static PersistenceContextRefType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PersistenceContextRefType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceContextRefType.type, options);
      }

      public static PersistenceContextRefType parse(Node node) throws XmlException {
         return (PersistenceContextRefType)XmlBeans.getContextTypeLoader().parse(node, PersistenceContextRefType.type, (XmlOptions)null);
      }

      public static PersistenceContextRefType parse(Node node, XmlOptions options) throws XmlException {
         return (PersistenceContextRefType)XmlBeans.getContextTypeLoader().parse(node, PersistenceContextRefType.type, options);
      }

      /** @deprecated */
      public static PersistenceContextRefType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PersistenceContextRefType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceContextRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PersistenceContextRefType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PersistenceContextRefType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceContextRefType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceContextRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceContextRefType.type, options);
      }

      private Factory() {
      }
   }
}
