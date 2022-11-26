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

public interface AssemblyDescriptorType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AssemblyDescriptorType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("assemblydescriptortype85c7type");

   SecurityRoleType[] getSecurityRoleArray();

   SecurityRoleType getSecurityRoleArray(int var1);

   int sizeOfSecurityRoleArray();

   void setSecurityRoleArray(SecurityRoleType[] var1);

   void setSecurityRoleArray(int var1, SecurityRoleType var2);

   SecurityRoleType insertNewSecurityRole(int var1);

   SecurityRoleType addNewSecurityRole();

   void removeSecurityRole(int var1);

   MethodPermissionType[] getMethodPermissionArray();

   MethodPermissionType getMethodPermissionArray(int var1);

   int sizeOfMethodPermissionArray();

   void setMethodPermissionArray(MethodPermissionType[] var1);

   void setMethodPermissionArray(int var1, MethodPermissionType var2);

   MethodPermissionType insertNewMethodPermission(int var1);

   MethodPermissionType addNewMethodPermission();

   void removeMethodPermission(int var1);

   ContainerTransactionType[] getContainerTransactionArray();

   ContainerTransactionType getContainerTransactionArray(int var1);

   int sizeOfContainerTransactionArray();

   void setContainerTransactionArray(ContainerTransactionType[] var1);

   void setContainerTransactionArray(int var1, ContainerTransactionType var2);

   ContainerTransactionType insertNewContainerTransaction(int var1);

   ContainerTransactionType addNewContainerTransaction();

   void removeContainerTransaction(int var1);

   InterceptorBindingType[] getInterceptorBindingArray();

   InterceptorBindingType getInterceptorBindingArray(int var1);

   int sizeOfInterceptorBindingArray();

   void setInterceptorBindingArray(InterceptorBindingType[] var1);

   void setInterceptorBindingArray(int var1, InterceptorBindingType var2);

   InterceptorBindingType insertNewInterceptorBinding(int var1);

   InterceptorBindingType addNewInterceptorBinding();

   void removeInterceptorBinding(int var1);

   MessageDestinationType[] getMessageDestinationArray();

   MessageDestinationType getMessageDestinationArray(int var1);

   int sizeOfMessageDestinationArray();

   void setMessageDestinationArray(MessageDestinationType[] var1);

   void setMessageDestinationArray(int var1, MessageDestinationType var2);

   MessageDestinationType insertNewMessageDestination(int var1);

   MessageDestinationType addNewMessageDestination();

   void removeMessageDestination(int var1);

   ExcludeListType getExcludeList();

   boolean isSetExcludeList();

   void setExcludeList(ExcludeListType var1);

   ExcludeListType addNewExcludeList();

   void unsetExcludeList();

   ApplicationExceptionType[] getApplicationExceptionArray();

   ApplicationExceptionType getApplicationExceptionArray(int var1);

   int sizeOfApplicationExceptionArray();

   void setApplicationExceptionArray(ApplicationExceptionType[] var1);

   void setApplicationExceptionArray(int var1, ApplicationExceptionType var2);

   ApplicationExceptionType insertNewApplicationException(int var1);

   ApplicationExceptionType addNewApplicationException();

   void removeApplicationException(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static AssemblyDescriptorType newInstance() {
         return (AssemblyDescriptorType)XmlBeans.getContextTypeLoader().newInstance(AssemblyDescriptorType.type, (XmlOptions)null);
      }

      public static AssemblyDescriptorType newInstance(XmlOptions options) {
         return (AssemblyDescriptorType)XmlBeans.getContextTypeLoader().newInstance(AssemblyDescriptorType.type, options);
      }

      public static AssemblyDescriptorType parse(java.lang.String xmlAsString) throws XmlException {
         return (AssemblyDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AssemblyDescriptorType.type, (XmlOptions)null);
      }

      public static AssemblyDescriptorType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (AssemblyDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AssemblyDescriptorType.type, options);
      }

      public static AssemblyDescriptorType parse(File file) throws XmlException, IOException {
         return (AssemblyDescriptorType)XmlBeans.getContextTypeLoader().parse(file, AssemblyDescriptorType.type, (XmlOptions)null);
      }

      public static AssemblyDescriptorType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AssemblyDescriptorType)XmlBeans.getContextTypeLoader().parse(file, AssemblyDescriptorType.type, options);
      }

      public static AssemblyDescriptorType parse(URL u) throws XmlException, IOException {
         return (AssemblyDescriptorType)XmlBeans.getContextTypeLoader().parse(u, AssemblyDescriptorType.type, (XmlOptions)null);
      }

      public static AssemblyDescriptorType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AssemblyDescriptorType)XmlBeans.getContextTypeLoader().parse(u, AssemblyDescriptorType.type, options);
      }

      public static AssemblyDescriptorType parse(InputStream is) throws XmlException, IOException {
         return (AssemblyDescriptorType)XmlBeans.getContextTypeLoader().parse(is, AssemblyDescriptorType.type, (XmlOptions)null);
      }

      public static AssemblyDescriptorType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AssemblyDescriptorType)XmlBeans.getContextTypeLoader().parse(is, AssemblyDescriptorType.type, options);
      }

      public static AssemblyDescriptorType parse(Reader r) throws XmlException, IOException {
         return (AssemblyDescriptorType)XmlBeans.getContextTypeLoader().parse(r, AssemblyDescriptorType.type, (XmlOptions)null);
      }

      public static AssemblyDescriptorType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AssemblyDescriptorType)XmlBeans.getContextTypeLoader().parse(r, AssemblyDescriptorType.type, options);
      }

      public static AssemblyDescriptorType parse(XMLStreamReader sr) throws XmlException {
         return (AssemblyDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, AssemblyDescriptorType.type, (XmlOptions)null);
      }

      public static AssemblyDescriptorType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AssemblyDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, AssemblyDescriptorType.type, options);
      }

      public static AssemblyDescriptorType parse(Node node) throws XmlException {
         return (AssemblyDescriptorType)XmlBeans.getContextTypeLoader().parse(node, AssemblyDescriptorType.type, (XmlOptions)null);
      }

      public static AssemblyDescriptorType parse(Node node, XmlOptions options) throws XmlException {
         return (AssemblyDescriptorType)XmlBeans.getContextTypeLoader().parse(node, AssemblyDescriptorType.type, options);
      }

      /** @deprecated */
      public static AssemblyDescriptorType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AssemblyDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, AssemblyDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AssemblyDescriptorType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AssemblyDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, AssemblyDescriptorType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AssemblyDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AssemblyDescriptorType.type, options);
      }

      private Factory() {
      }
   }
}
