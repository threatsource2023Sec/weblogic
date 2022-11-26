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

public interface EntityBeanType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EntityBeanType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("entitybeantype6abbtype");

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

   EjbNameType getEjbName();

   void setEjbName(EjbNameType var1);

   EjbNameType addNewEjbName();

   HomeType getHome();

   boolean isSetHome();

   void setHome(HomeType var1);

   HomeType addNewHome();

   void unsetHome();

   RemoteType getRemote();

   boolean isSetRemote();

   void setRemote(RemoteType var1);

   RemoteType addNewRemote();

   void unsetRemote();

   LocalHomeType getLocalHome();

   boolean isSetLocalHome();

   void setLocalHome(LocalHomeType var1);

   LocalHomeType addNewLocalHome();

   void unsetLocalHome();

   LocalType getLocal();

   boolean isSetLocal();

   void setLocal(LocalType var1);

   LocalType addNewLocal();

   void unsetLocal();

   EjbClassType getEjbClass();

   void setEjbClass(EjbClassType var1);

   EjbClassType addNewEjbClass();

   PersistenceTypeType getPersistenceType();

   void setPersistenceType(PersistenceTypeType var1);

   PersistenceTypeType addNewPersistenceType();

   FullyQualifiedClassType getPrimKeyClass();

   void setPrimKeyClass(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewPrimKeyClass();

   TrueFalseType getReentrant();

   void setReentrant(TrueFalseType var1);

   TrueFalseType addNewReentrant();

   CmpVersionType getCmpVersion();

   boolean isSetCmpVersion();

   void setCmpVersion(CmpVersionType var1);

   CmpVersionType addNewCmpVersion();

   void unsetCmpVersion();

   JavaIdentifierType getAbstractSchemaName();

   boolean isSetAbstractSchemaName();

   void setAbstractSchemaName(JavaIdentifierType var1);

   JavaIdentifierType addNewAbstractSchemaName();

   void unsetAbstractSchemaName();

   CmpFieldType[] getCmpFieldArray();

   CmpFieldType getCmpFieldArray(int var1);

   int sizeOfCmpFieldArray();

   void setCmpFieldArray(CmpFieldType[] var1);

   void setCmpFieldArray(int var1, CmpFieldType var2);

   CmpFieldType insertNewCmpField(int var1);

   CmpFieldType addNewCmpField();

   void removeCmpField(int var1);

   String getPrimkeyField();

   boolean isSetPrimkeyField();

   void setPrimkeyField(String var1);

   String addNewPrimkeyField();

   void unsetPrimkeyField();

   EnvEntryType[] getEnvEntryArray();

   EnvEntryType getEnvEntryArray(int var1);

   int sizeOfEnvEntryArray();

   void setEnvEntryArray(EnvEntryType[] var1);

   void setEnvEntryArray(int var1, EnvEntryType var2);

   EnvEntryType insertNewEnvEntry(int var1);

   EnvEntryType addNewEnvEntry();

   void removeEnvEntry(int var1);

   EjbRefType[] getEjbRefArray();

   EjbRefType getEjbRefArray(int var1);

   int sizeOfEjbRefArray();

   void setEjbRefArray(EjbRefType[] var1);

   void setEjbRefArray(int var1, EjbRefType var2);

   EjbRefType insertNewEjbRef(int var1);

   EjbRefType addNewEjbRef();

   void removeEjbRef(int var1);

   EjbLocalRefType[] getEjbLocalRefArray();

   EjbLocalRefType getEjbLocalRefArray(int var1);

   int sizeOfEjbLocalRefArray();

   void setEjbLocalRefArray(EjbLocalRefType[] var1);

   void setEjbLocalRefArray(int var1, EjbLocalRefType var2);

   EjbLocalRefType insertNewEjbLocalRef(int var1);

   EjbLocalRefType addNewEjbLocalRef();

   void removeEjbLocalRef(int var1);

   ServiceRefType[] getServiceRefArray();

   ServiceRefType getServiceRefArray(int var1);

   int sizeOfServiceRefArray();

   void setServiceRefArray(ServiceRefType[] var1);

   void setServiceRefArray(int var1, ServiceRefType var2);

   ServiceRefType insertNewServiceRef(int var1);

   ServiceRefType addNewServiceRef();

   void removeServiceRef(int var1);

   ResourceRefType[] getResourceRefArray();

   ResourceRefType getResourceRefArray(int var1);

   int sizeOfResourceRefArray();

   void setResourceRefArray(ResourceRefType[] var1);

   void setResourceRefArray(int var1, ResourceRefType var2);

   ResourceRefType insertNewResourceRef(int var1);

   ResourceRefType addNewResourceRef();

   void removeResourceRef(int var1);

   ResourceEnvRefType[] getResourceEnvRefArray();

   ResourceEnvRefType getResourceEnvRefArray(int var1);

   int sizeOfResourceEnvRefArray();

   void setResourceEnvRefArray(ResourceEnvRefType[] var1);

   void setResourceEnvRefArray(int var1, ResourceEnvRefType var2);

   ResourceEnvRefType insertNewResourceEnvRef(int var1);

   ResourceEnvRefType addNewResourceEnvRef();

   void removeResourceEnvRef(int var1);

   MessageDestinationRefType[] getMessageDestinationRefArray();

   MessageDestinationRefType getMessageDestinationRefArray(int var1);

   int sizeOfMessageDestinationRefArray();

   void setMessageDestinationRefArray(MessageDestinationRefType[] var1);

   void setMessageDestinationRefArray(int var1, MessageDestinationRefType var2);

   MessageDestinationRefType insertNewMessageDestinationRef(int var1);

   MessageDestinationRefType addNewMessageDestinationRef();

   void removeMessageDestinationRef(int var1);

   SecurityRoleRefType[] getSecurityRoleRefArray();

   SecurityRoleRefType getSecurityRoleRefArray(int var1);

   int sizeOfSecurityRoleRefArray();

   void setSecurityRoleRefArray(SecurityRoleRefType[] var1);

   void setSecurityRoleRefArray(int var1, SecurityRoleRefType var2);

   SecurityRoleRefType insertNewSecurityRoleRef(int var1);

   SecurityRoleRefType addNewSecurityRoleRef();

   void removeSecurityRoleRef(int var1);

   SecurityIdentityType getSecurityIdentity();

   boolean isSetSecurityIdentity();

   void setSecurityIdentity(SecurityIdentityType var1);

   SecurityIdentityType addNewSecurityIdentity();

   void unsetSecurityIdentity();

   QueryType[] getQueryArray();

   QueryType getQueryArray(int var1);

   int sizeOfQueryArray();

   void setQueryArray(QueryType[] var1);

   void setQueryArray(int var1, QueryType var2);

   QueryType insertNewQuery(int var1);

   QueryType addNewQuery();

   void removeQuery(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static EntityBeanType newInstance() {
         return (EntityBeanType)XmlBeans.getContextTypeLoader().newInstance(EntityBeanType.type, (XmlOptions)null);
      }

      public static EntityBeanType newInstance(XmlOptions options) {
         return (EntityBeanType)XmlBeans.getContextTypeLoader().newInstance(EntityBeanType.type, options);
      }

      public static EntityBeanType parse(java.lang.String xmlAsString) throws XmlException {
         return (EntityBeanType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EntityBeanType.type, (XmlOptions)null);
      }

      public static EntityBeanType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (EntityBeanType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EntityBeanType.type, options);
      }

      public static EntityBeanType parse(File file) throws XmlException, IOException {
         return (EntityBeanType)XmlBeans.getContextTypeLoader().parse(file, EntityBeanType.type, (XmlOptions)null);
      }

      public static EntityBeanType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EntityBeanType)XmlBeans.getContextTypeLoader().parse(file, EntityBeanType.type, options);
      }

      public static EntityBeanType parse(URL u) throws XmlException, IOException {
         return (EntityBeanType)XmlBeans.getContextTypeLoader().parse(u, EntityBeanType.type, (XmlOptions)null);
      }

      public static EntityBeanType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EntityBeanType)XmlBeans.getContextTypeLoader().parse(u, EntityBeanType.type, options);
      }

      public static EntityBeanType parse(InputStream is) throws XmlException, IOException {
         return (EntityBeanType)XmlBeans.getContextTypeLoader().parse(is, EntityBeanType.type, (XmlOptions)null);
      }

      public static EntityBeanType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EntityBeanType)XmlBeans.getContextTypeLoader().parse(is, EntityBeanType.type, options);
      }

      public static EntityBeanType parse(Reader r) throws XmlException, IOException {
         return (EntityBeanType)XmlBeans.getContextTypeLoader().parse(r, EntityBeanType.type, (XmlOptions)null);
      }

      public static EntityBeanType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EntityBeanType)XmlBeans.getContextTypeLoader().parse(r, EntityBeanType.type, options);
      }

      public static EntityBeanType parse(XMLStreamReader sr) throws XmlException {
         return (EntityBeanType)XmlBeans.getContextTypeLoader().parse(sr, EntityBeanType.type, (XmlOptions)null);
      }

      public static EntityBeanType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EntityBeanType)XmlBeans.getContextTypeLoader().parse(sr, EntityBeanType.type, options);
      }

      public static EntityBeanType parse(Node node) throws XmlException {
         return (EntityBeanType)XmlBeans.getContextTypeLoader().parse(node, EntityBeanType.type, (XmlOptions)null);
      }

      public static EntityBeanType parse(Node node, XmlOptions options) throws XmlException {
         return (EntityBeanType)XmlBeans.getContextTypeLoader().parse(node, EntityBeanType.type, options);
      }

      /** @deprecated */
      public static EntityBeanType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EntityBeanType)XmlBeans.getContextTypeLoader().parse(xis, EntityBeanType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EntityBeanType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EntityBeanType)XmlBeans.getContextTypeLoader().parse(xis, EntityBeanType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EntityBeanType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EntityBeanType.type, options);
      }

      private Factory() {
      }
   }
}
