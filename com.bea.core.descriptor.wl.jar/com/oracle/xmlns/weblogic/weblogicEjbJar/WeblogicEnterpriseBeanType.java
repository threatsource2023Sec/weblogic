package com.oracle.xmlns.weblogic.weblogicEjbJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.EjbNameType;
import com.sun.java.xml.ns.javaee.JndiNameType;
import com.sun.java.xml.ns.javaee.XsdNonNegativeIntegerType;
import com.sun.java.xml.ns.javaee.XsdStringType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface WeblogicEnterpriseBeanType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicEnterpriseBeanType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("weblogicenterprisebeantype31cftype");

   EjbNameType getEjbName();

   void setEjbName(EjbNameType var1);

   EjbNameType addNewEjbName();

   EntityDescriptorType getEntityDescriptor();

   boolean isSetEntityDescriptor();

   void setEntityDescriptor(EntityDescriptorType var1);

   EntityDescriptorType addNewEntityDescriptor();

   void unsetEntityDescriptor();

   StatelessSessionDescriptorType getStatelessSessionDescriptor();

   boolean isSetStatelessSessionDescriptor();

   void setStatelessSessionDescriptor(StatelessSessionDescriptorType var1);

   StatelessSessionDescriptorType addNewStatelessSessionDescriptor();

   void unsetStatelessSessionDescriptor();

   StatefulSessionDescriptorType getStatefulSessionDescriptor();

   boolean isSetStatefulSessionDescriptor();

   void setStatefulSessionDescriptor(StatefulSessionDescriptorType var1);

   StatefulSessionDescriptorType addNewStatefulSessionDescriptor();

   void unsetStatefulSessionDescriptor();

   SingletonSessionDescriptorType getSingletonSessionDescriptor();

   boolean isSetSingletonSessionDescriptor();

   void setSingletonSessionDescriptor(SingletonSessionDescriptorType var1);

   SingletonSessionDescriptorType addNewSingletonSessionDescriptor();

   void unsetSingletonSessionDescriptor();

   MessageDrivenDescriptorType getMessageDrivenDescriptor();

   boolean isSetMessageDrivenDescriptor();

   void setMessageDrivenDescriptor(MessageDrivenDescriptorType var1);

   MessageDrivenDescriptorType addNewMessageDrivenDescriptor();

   void unsetMessageDrivenDescriptor();

   TransactionDescriptorType getTransactionDescriptor();

   boolean isSetTransactionDescriptor();

   void setTransactionDescriptor(TransactionDescriptorType var1);

   TransactionDescriptorType addNewTransactionDescriptor();

   void unsetTransactionDescriptor();

   IiopSecurityDescriptorType getIiopSecurityDescriptor();

   boolean isSetIiopSecurityDescriptor();

   void setIiopSecurityDescriptor(IiopSecurityDescriptorType var1);

   IiopSecurityDescriptorType addNewIiopSecurityDescriptor();

   void unsetIiopSecurityDescriptor();

   ResourceDescriptionType[] getResourceDescriptionArray();

   ResourceDescriptionType getResourceDescriptionArray(int var1);

   int sizeOfResourceDescriptionArray();

   void setResourceDescriptionArray(ResourceDescriptionType[] var1);

   void setResourceDescriptionArray(int var1, ResourceDescriptionType var2);

   ResourceDescriptionType insertNewResourceDescription(int var1);

   ResourceDescriptionType addNewResourceDescription();

   void removeResourceDescription(int var1);

   ResourceEnvDescriptionType[] getResourceEnvDescriptionArray();

   ResourceEnvDescriptionType getResourceEnvDescriptionArray(int var1);

   int sizeOfResourceEnvDescriptionArray();

   void setResourceEnvDescriptionArray(ResourceEnvDescriptionType[] var1);

   void setResourceEnvDescriptionArray(int var1, ResourceEnvDescriptionType var2);

   ResourceEnvDescriptionType insertNewResourceEnvDescription(int var1);

   ResourceEnvDescriptionType addNewResourceEnvDescription();

   void removeResourceEnvDescription(int var1);

   EjbReferenceDescriptionType[] getEjbReferenceDescriptionArray();

   EjbReferenceDescriptionType getEjbReferenceDescriptionArray(int var1);

   int sizeOfEjbReferenceDescriptionArray();

   void setEjbReferenceDescriptionArray(EjbReferenceDescriptionType[] var1);

   void setEjbReferenceDescriptionArray(int var1, EjbReferenceDescriptionType var2);

   EjbReferenceDescriptionType insertNewEjbReferenceDescription(int var1);

   EjbReferenceDescriptionType addNewEjbReferenceDescription();

   void removeEjbReferenceDescription(int var1);

   ServiceReferenceDescriptionType[] getServiceReferenceDescriptionArray();

   ServiceReferenceDescriptionType getServiceReferenceDescriptionArray(int var1);

   int sizeOfServiceReferenceDescriptionArray();

   void setServiceReferenceDescriptionArray(ServiceReferenceDescriptionType[] var1);

   void setServiceReferenceDescriptionArray(int var1, ServiceReferenceDescriptionType var2);

   ServiceReferenceDescriptionType insertNewServiceReferenceDescription(int var1);

   ServiceReferenceDescriptionType addNewServiceReferenceDescription();

   void removeServiceReferenceDescription(int var1);

   TrueFalseType getEnableCallByReference();

   boolean isSetEnableCallByReference();

   void setEnableCallByReference(TrueFalseType var1);

   TrueFalseType addNewEnableCallByReference();

   void unsetEnableCallByReference();

   XsdStringType getNetworkAccessPoint();

   boolean isSetNetworkAccessPoint();

   void setNetworkAccessPoint(XsdStringType var1);

   XsdStringType addNewNetworkAccessPoint();

   void unsetNetworkAccessPoint();

   TrueFalseType getClientsOnSameServer();

   boolean isSetClientsOnSameServer();

   void setClientsOnSameServer(TrueFalseType var1);

   TrueFalseType addNewClientsOnSameServer();

   void unsetClientsOnSameServer();

   RunAsPrincipalNameType getRunAsPrincipalName();

   boolean isSetRunAsPrincipalName();

   void setRunAsPrincipalName(RunAsPrincipalNameType var1);

   RunAsPrincipalNameType addNewRunAsPrincipalName();

   void unsetRunAsPrincipalName();

   CreateAsPrincipalNameType getCreateAsPrincipalName();

   boolean isSetCreateAsPrincipalName();

   void setCreateAsPrincipalName(CreateAsPrincipalNameType var1);

   CreateAsPrincipalNameType addNewCreateAsPrincipalName();

   void unsetCreateAsPrincipalName();

   RemoveAsPrincipalNameType getRemoveAsPrincipalName();

   boolean isSetRemoveAsPrincipalName();

   void setRemoveAsPrincipalName(RemoveAsPrincipalNameType var1);

   RemoveAsPrincipalNameType addNewRemoveAsPrincipalName();

   void unsetRemoveAsPrincipalName();

   PassivateAsPrincipalNameType getPassivateAsPrincipalName();

   boolean isSetPassivateAsPrincipalName();

   void setPassivateAsPrincipalName(PassivateAsPrincipalNameType var1);

   PassivateAsPrincipalNameType addNewPassivateAsPrincipalName();

   void unsetPassivateAsPrincipalName();

   JndiNameType getJndiName();

   boolean isSetJndiName();

   void setJndiName(JndiNameType var1);

   JndiNameType addNewJndiName();

   void unsetJndiName();

   JndiNameType getLocalJndiName();

   boolean isSetLocalJndiName();

   void setLocalJndiName(JndiNameType var1);

   JndiNameType addNewLocalJndiName();

   void unsetLocalJndiName();

   DispatchPolicyType getDispatchPolicy();

   boolean isSetDispatchPolicy();

   void setDispatchPolicy(DispatchPolicyType var1);

   DispatchPolicyType addNewDispatchPolicy();

   void unsetDispatchPolicy();

   XsdNonNegativeIntegerType getRemoteClientTimeout();

   boolean isSetRemoteClientTimeout();

   void setRemoteClientTimeout(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewRemoteClientTimeout();

   void unsetRemoteClientTimeout();

   TrueFalseType getStickToFirstServer();

   boolean isSetStickToFirstServer();

   void setStickToFirstServer(TrueFalseType var1);

   TrueFalseType addNewStickToFirstServer();

   void unsetStickToFirstServer();

   JndiBindingType[] getJndiBindingArray();

   JndiBindingType getJndiBindingArray(int var1);

   int sizeOfJndiBindingArray();

   void setJndiBindingArray(JndiBindingType[] var1);

   void setJndiBindingArray(int var1, JndiBindingType var2);

   JndiBindingType insertNewJndiBinding(int var1);

   JndiBindingType addNewJndiBinding();

   void removeJndiBinding(int var1);

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static WeblogicEnterpriseBeanType newInstance() {
         return (WeblogicEnterpriseBeanType)XmlBeans.getContextTypeLoader().newInstance(WeblogicEnterpriseBeanType.type, (XmlOptions)null);
      }

      public static WeblogicEnterpriseBeanType newInstance(XmlOptions options) {
         return (WeblogicEnterpriseBeanType)XmlBeans.getContextTypeLoader().newInstance(WeblogicEnterpriseBeanType.type, options);
      }

      public static WeblogicEnterpriseBeanType parse(String xmlAsString) throws XmlException {
         return (WeblogicEnterpriseBeanType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicEnterpriseBeanType.type, (XmlOptions)null);
      }

      public static WeblogicEnterpriseBeanType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicEnterpriseBeanType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicEnterpriseBeanType.type, options);
      }

      public static WeblogicEnterpriseBeanType parse(File file) throws XmlException, IOException {
         return (WeblogicEnterpriseBeanType)XmlBeans.getContextTypeLoader().parse(file, WeblogicEnterpriseBeanType.type, (XmlOptions)null);
      }

      public static WeblogicEnterpriseBeanType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicEnterpriseBeanType)XmlBeans.getContextTypeLoader().parse(file, WeblogicEnterpriseBeanType.type, options);
      }

      public static WeblogicEnterpriseBeanType parse(URL u) throws XmlException, IOException {
         return (WeblogicEnterpriseBeanType)XmlBeans.getContextTypeLoader().parse(u, WeblogicEnterpriseBeanType.type, (XmlOptions)null);
      }

      public static WeblogicEnterpriseBeanType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicEnterpriseBeanType)XmlBeans.getContextTypeLoader().parse(u, WeblogicEnterpriseBeanType.type, options);
      }

      public static WeblogicEnterpriseBeanType parse(InputStream is) throws XmlException, IOException {
         return (WeblogicEnterpriseBeanType)XmlBeans.getContextTypeLoader().parse(is, WeblogicEnterpriseBeanType.type, (XmlOptions)null);
      }

      public static WeblogicEnterpriseBeanType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicEnterpriseBeanType)XmlBeans.getContextTypeLoader().parse(is, WeblogicEnterpriseBeanType.type, options);
      }

      public static WeblogicEnterpriseBeanType parse(Reader r) throws XmlException, IOException {
         return (WeblogicEnterpriseBeanType)XmlBeans.getContextTypeLoader().parse(r, WeblogicEnterpriseBeanType.type, (XmlOptions)null);
      }

      public static WeblogicEnterpriseBeanType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicEnterpriseBeanType)XmlBeans.getContextTypeLoader().parse(r, WeblogicEnterpriseBeanType.type, options);
      }

      public static WeblogicEnterpriseBeanType parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicEnterpriseBeanType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicEnterpriseBeanType.type, (XmlOptions)null);
      }

      public static WeblogicEnterpriseBeanType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicEnterpriseBeanType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicEnterpriseBeanType.type, options);
      }

      public static WeblogicEnterpriseBeanType parse(Node node) throws XmlException {
         return (WeblogicEnterpriseBeanType)XmlBeans.getContextTypeLoader().parse(node, WeblogicEnterpriseBeanType.type, (XmlOptions)null);
      }

      public static WeblogicEnterpriseBeanType parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicEnterpriseBeanType)XmlBeans.getContextTypeLoader().parse(node, WeblogicEnterpriseBeanType.type, options);
      }

      /** @deprecated */
      public static WeblogicEnterpriseBeanType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicEnterpriseBeanType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicEnterpriseBeanType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicEnterpriseBeanType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicEnterpriseBeanType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicEnterpriseBeanType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicEnterpriseBeanType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicEnterpriseBeanType.type, options);
      }

      private Factory() {
      }
   }
}
