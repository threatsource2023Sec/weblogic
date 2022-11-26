package com.oracle.xmlns.weblogic.weblogicApplicationClient;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface WeblogicApplicationClientType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicApplicationClientType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("weblogicapplicationclienttype5104type");

   String getServerApplicationName();

   XmlString xgetServerApplicationName();

   boolean isSetServerApplicationName();

   void setServerApplicationName(String var1);

   void xsetServerApplicationName(XmlString var1);

   void unsetServerApplicationName();

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

   MessageDestinationDescriptorType[] getMessageDestinationDescriptorArray();

   MessageDestinationDescriptorType getMessageDestinationDescriptorArray(int var1);

   int sizeOfMessageDestinationDescriptorArray();

   void setMessageDestinationDescriptorArray(MessageDestinationDescriptorType[] var1);

   void setMessageDestinationDescriptorArray(int var1, MessageDestinationDescriptorType var2);

   MessageDestinationDescriptorType insertNewMessageDestinationDescriptor(int var1);

   MessageDestinationDescriptorType addNewMessageDestinationDescriptor();

   void removeMessageDestinationDescriptor(int var1);

   CdiDescriptorType getCdiDescriptor();

   boolean isSetCdiDescriptor();

   void setCdiDescriptor(CdiDescriptorType var1);

   CdiDescriptorType addNewCdiDescriptor();

   void unsetCdiDescriptor();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   String getVersion();

   XmlString xgetVersion();

   boolean isSetVersion();

   void setVersion(String var1);

   void xsetVersion(XmlString var1);

   void unsetVersion();

   public static final class Factory {
      public static WeblogicApplicationClientType newInstance() {
         return (WeblogicApplicationClientType)XmlBeans.getContextTypeLoader().newInstance(WeblogicApplicationClientType.type, (XmlOptions)null);
      }

      public static WeblogicApplicationClientType newInstance(XmlOptions options) {
         return (WeblogicApplicationClientType)XmlBeans.getContextTypeLoader().newInstance(WeblogicApplicationClientType.type, options);
      }

      public static WeblogicApplicationClientType parse(String xmlAsString) throws XmlException {
         return (WeblogicApplicationClientType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicApplicationClientType.type, (XmlOptions)null);
      }

      public static WeblogicApplicationClientType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicApplicationClientType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicApplicationClientType.type, options);
      }

      public static WeblogicApplicationClientType parse(File file) throws XmlException, IOException {
         return (WeblogicApplicationClientType)XmlBeans.getContextTypeLoader().parse(file, WeblogicApplicationClientType.type, (XmlOptions)null);
      }

      public static WeblogicApplicationClientType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicApplicationClientType)XmlBeans.getContextTypeLoader().parse(file, WeblogicApplicationClientType.type, options);
      }

      public static WeblogicApplicationClientType parse(URL u) throws XmlException, IOException {
         return (WeblogicApplicationClientType)XmlBeans.getContextTypeLoader().parse(u, WeblogicApplicationClientType.type, (XmlOptions)null);
      }

      public static WeblogicApplicationClientType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicApplicationClientType)XmlBeans.getContextTypeLoader().parse(u, WeblogicApplicationClientType.type, options);
      }

      public static WeblogicApplicationClientType parse(InputStream is) throws XmlException, IOException {
         return (WeblogicApplicationClientType)XmlBeans.getContextTypeLoader().parse(is, WeblogicApplicationClientType.type, (XmlOptions)null);
      }

      public static WeblogicApplicationClientType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicApplicationClientType)XmlBeans.getContextTypeLoader().parse(is, WeblogicApplicationClientType.type, options);
      }

      public static WeblogicApplicationClientType parse(Reader r) throws XmlException, IOException {
         return (WeblogicApplicationClientType)XmlBeans.getContextTypeLoader().parse(r, WeblogicApplicationClientType.type, (XmlOptions)null);
      }

      public static WeblogicApplicationClientType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicApplicationClientType)XmlBeans.getContextTypeLoader().parse(r, WeblogicApplicationClientType.type, options);
      }

      public static WeblogicApplicationClientType parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicApplicationClientType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicApplicationClientType.type, (XmlOptions)null);
      }

      public static WeblogicApplicationClientType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicApplicationClientType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicApplicationClientType.type, options);
      }

      public static WeblogicApplicationClientType parse(Node node) throws XmlException {
         return (WeblogicApplicationClientType)XmlBeans.getContextTypeLoader().parse(node, WeblogicApplicationClientType.type, (XmlOptions)null);
      }

      public static WeblogicApplicationClientType parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicApplicationClientType)XmlBeans.getContextTypeLoader().parse(node, WeblogicApplicationClientType.type, options);
      }

      /** @deprecated */
      public static WeblogicApplicationClientType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicApplicationClientType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicApplicationClientType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicApplicationClientType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicApplicationClientType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicApplicationClientType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicApplicationClientType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicApplicationClientType.type, options);
      }

      private Factory() {
      }
   }
}
