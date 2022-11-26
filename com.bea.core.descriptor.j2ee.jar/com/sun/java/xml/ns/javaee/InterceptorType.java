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

public interface InterceptorType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(InterceptorType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("interceptortypeff20type");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   FullyQualifiedClassType getInterceptorClass();

   void setInterceptorClass(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewInterceptorClass();

   AroundInvokeType[] getAroundInvokeArray();

   AroundInvokeType getAroundInvokeArray(int var1);

   int sizeOfAroundInvokeArray();

   void setAroundInvokeArray(AroundInvokeType[] var1);

   void setAroundInvokeArray(int var1, AroundInvokeType var2);

   AroundInvokeType insertNewAroundInvoke(int var1);

   AroundInvokeType addNewAroundInvoke();

   void removeAroundInvoke(int var1);

   AroundTimeoutType[] getAroundTimeoutArray();

   AroundTimeoutType getAroundTimeoutArray(int var1);

   int sizeOfAroundTimeoutArray();

   void setAroundTimeoutArray(AroundTimeoutType[] var1);

   void setAroundTimeoutArray(int var1, AroundTimeoutType var2);

   AroundTimeoutType insertNewAroundTimeout(int var1);

   AroundTimeoutType addNewAroundTimeout();

   void removeAroundTimeout(int var1);

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

   PersistenceContextRefType[] getPersistenceContextRefArray();

   PersistenceContextRefType getPersistenceContextRefArray(int var1);

   int sizeOfPersistenceContextRefArray();

   void setPersistenceContextRefArray(PersistenceContextRefType[] var1);

   void setPersistenceContextRefArray(int var1, PersistenceContextRefType var2);

   PersistenceContextRefType insertNewPersistenceContextRef(int var1);

   PersistenceContextRefType addNewPersistenceContextRef();

   void removePersistenceContextRef(int var1);

   PersistenceUnitRefType[] getPersistenceUnitRefArray();

   PersistenceUnitRefType getPersistenceUnitRefArray(int var1);

   int sizeOfPersistenceUnitRefArray();

   void setPersistenceUnitRefArray(PersistenceUnitRefType[] var1);

   void setPersistenceUnitRefArray(int var1, PersistenceUnitRefType var2);

   PersistenceUnitRefType insertNewPersistenceUnitRef(int var1);

   PersistenceUnitRefType addNewPersistenceUnitRef();

   void removePersistenceUnitRef(int var1);

   LifecycleCallbackType[] getPostConstructArray();

   LifecycleCallbackType getPostConstructArray(int var1);

   int sizeOfPostConstructArray();

   void setPostConstructArray(LifecycleCallbackType[] var1);

   void setPostConstructArray(int var1, LifecycleCallbackType var2);

   LifecycleCallbackType insertNewPostConstruct(int var1);

   LifecycleCallbackType addNewPostConstruct();

   void removePostConstruct(int var1);

   LifecycleCallbackType[] getPreDestroyArray();

   LifecycleCallbackType getPreDestroyArray(int var1);

   int sizeOfPreDestroyArray();

   void setPreDestroyArray(LifecycleCallbackType[] var1);

   void setPreDestroyArray(int var1, LifecycleCallbackType var2);

   LifecycleCallbackType insertNewPreDestroy(int var1);

   LifecycleCallbackType addNewPreDestroy();

   void removePreDestroy(int var1);

   DataSourceType[] getDataSourceArray();

   DataSourceType getDataSourceArray(int var1);

   int sizeOfDataSourceArray();

   void setDataSourceArray(DataSourceType[] var1);

   void setDataSourceArray(int var1, DataSourceType var2);

   DataSourceType insertNewDataSource(int var1);

   DataSourceType addNewDataSource();

   void removeDataSource(int var1);

   LifecycleCallbackType[] getPostActivateArray();

   LifecycleCallbackType getPostActivateArray(int var1);

   int sizeOfPostActivateArray();

   void setPostActivateArray(LifecycleCallbackType[] var1);

   void setPostActivateArray(int var1, LifecycleCallbackType var2);

   LifecycleCallbackType insertNewPostActivate(int var1);

   LifecycleCallbackType addNewPostActivate();

   void removePostActivate(int var1);

   LifecycleCallbackType[] getPrePassivateArray();

   LifecycleCallbackType getPrePassivateArray(int var1);

   int sizeOfPrePassivateArray();

   void setPrePassivateArray(LifecycleCallbackType[] var1);

   void setPrePassivateArray(int var1, LifecycleCallbackType var2);

   LifecycleCallbackType insertNewPrePassivate(int var1);

   LifecycleCallbackType addNewPrePassivate();

   void removePrePassivate(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static InterceptorType newInstance() {
         return (InterceptorType)XmlBeans.getContextTypeLoader().newInstance(InterceptorType.type, (XmlOptions)null);
      }

      public static InterceptorType newInstance(XmlOptions options) {
         return (InterceptorType)XmlBeans.getContextTypeLoader().newInstance(InterceptorType.type, options);
      }

      public static InterceptorType parse(java.lang.String xmlAsString) throws XmlException {
         return (InterceptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InterceptorType.type, (XmlOptions)null);
      }

      public static InterceptorType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (InterceptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InterceptorType.type, options);
      }

      public static InterceptorType parse(File file) throws XmlException, IOException {
         return (InterceptorType)XmlBeans.getContextTypeLoader().parse(file, InterceptorType.type, (XmlOptions)null);
      }

      public static InterceptorType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (InterceptorType)XmlBeans.getContextTypeLoader().parse(file, InterceptorType.type, options);
      }

      public static InterceptorType parse(URL u) throws XmlException, IOException {
         return (InterceptorType)XmlBeans.getContextTypeLoader().parse(u, InterceptorType.type, (XmlOptions)null);
      }

      public static InterceptorType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (InterceptorType)XmlBeans.getContextTypeLoader().parse(u, InterceptorType.type, options);
      }

      public static InterceptorType parse(InputStream is) throws XmlException, IOException {
         return (InterceptorType)XmlBeans.getContextTypeLoader().parse(is, InterceptorType.type, (XmlOptions)null);
      }

      public static InterceptorType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (InterceptorType)XmlBeans.getContextTypeLoader().parse(is, InterceptorType.type, options);
      }

      public static InterceptorType parse(Reader r) throws XmlException, IOException {
         return (InterceptorType)XmlBeans.getContextTypeLoader().parse(r, InterceptorType.type, (XmlOptions)null);
      }

      public static InterceptorType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (InterceptorType)XmlBeans.getContextTypeLoader().parse(r, InterceptorType.type, options);
      }

      public static InterceptorType parse(XMLStreamReader sr) throws XmlException {
         return (InterceptorType)XmlBeans.getContextTypeLoader().parse(sr, InterceptorType.type, (XmlOptions)null);
      }

      public static InterceptorType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (InterceptorType)XmlBeans.getContextTypeLoader().parse(sr, InterceptorType.type, options);
      }

      public static InterceptorType parse(Node node) throws XmlException {
         return (InterceptorType)XmlBeans.getContextTypeLoader().parse(node, InterceptorType.type, (XmlOptions)null);
      }

      public static InterceptorType parse(Node node, XmlOptions options) throws XmlException {
         return (InterceptorType)XmlBeans.getContextTypeLoader().parse(node, InterceptorType.type, options);
      }

      /** @deprecated */
      public static InterceptorType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (InterceptorType)XmlBeans.getContextTypeLoader().parse(xis, InterceptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static InterceptorType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (InterceptorType)XmlBeans.getContextTypeLoader().parse(xis, InterceptorType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InterceptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InterceptorType.type, options);
      }

      private Factory() {
      }
   }
}
