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

public interface JavaWsdlMappingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JavaWsdlMappingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("javawsdlmappingtypea951type");

   PackageMappingType[] getPackageMappingArray();

   PackageMappingType getPackageMappingArray(int var1);

   int sizeOfPackageMappingArray();

   void setPackageMappingArray(PackageMappingType[] var1);

   void setPackageMappingArray(int var1, PackageMappingType var2);

   PackageMappingType insertNewPackageMapping(int var1);

   PackageMappingType addNewPackageMapping();

   void removePackageMapping(int var1);

   JavaXmlTypeMappingType[] getJavaXmlTypeMappingArray();

   JavaXmlTypeMappingType getJavaXmlTypeMappingArray(int var1);

   int sizeOfJavaXmlTypeMappingArray();

   void setJavaXmlTypeMappingArray(JavaXmlTypeMappingType[] var1);

   void setJavaXmlTypeMappingArray(int var1, JavaXmlTypeMappingType var2);

   JavaXmlTypeMappingType insertNewJavaXmlTypeMapping(int var1);

   JavaXmlTypeMappingType addNewJavaXmlTypeMapping();

   void removeJavaXmlTypeMapping(int var1);

   ExceptionMappingType[] getExceptionMappingArray();

   ExceptionMappingType getExceptionMappingArray(int var1);

   int sizeOfExceptionMappingArray();

   void setExceptionMappingArray(ExceptionMappingType[] var1);

   void setExceptionMappingArray(int var1, ExceptionMappingType var2);

   ExceptionMappingType insertNewExceptionMapping(int var1);

   ExceptionMappingType addNewExceptionMapping();

   void removeExceptionMapping(int var1);

   ServiceInterfaceMappingType[] getServiceInterfaceMappingArray();

   ServiceInterfaceMappingType getServiceInterfaceMappingArray(int var1);

   int sizeOfServiceInterfaceMappingArray();

   void setServiceInterfaceMappingArray(ServiceInterfaceMappingType[] var1);

   void setServiceInterfaceMappingArray(int var1, ServiceInterfaceMappingType var2);

   ServiceInterfaceMappingType insertNewServiceInterfaceMapping(int var1);

   ServiceInterfaceMappingType addNewServiceInterfaceMapping();

   void removeServiceInterfaceMapping(int var1);

   ServiceEndpointInterfaceMappingType[] getServiceEndpointInterfaceMappingArray();

   ServiceEndpointInterfaceMappingType getServiceEndpointInterfaceMappingArray(int var1);

   int sizeOfServiceEndpointInterfaceMappingArray();

   void setServiceEndpointInterfaceMappingArray(ServiceEndpointInterfaceMappingType[] var1);

   void setServiceEndpointInterfaceMappingArray(int var1, ServiceEndpointInterfaceMappingType var2);

   ServiceEndpointInterfaceMappingType insertNewServiceEndpointInterfaceMapping(int var1);

   ServiceEndpointInterfaceMappingType addNewServiceEndpointInterfaceMapping();

   void removeServiceEndpointInterfaceMapping(int var1);

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
      public static JavaWsdlMappingType newInstance() {
         return (JavaWsdlMappingType)XmlBeans.getContextTypeLoader().newInstance(JavaWsdlMappingType.type, (XmlOptions)null);
      }

      public static JavaWsdlMappingType newInstance(XmlOptions options) {
         return (JavaWsdlMappingType)XmlBeans.getContextTypeLoader().newInstance(JavaWsdlMappingType.type, options);
      }

      public static JavaWsdlMappingType parse(java.lang.String xmlAsString) throws XmlException {
         return (JavaWsdlMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JavaWsdlMappingType.type, (XmlOptions)null);
      }

      public static JavaWsdlMappingType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (JavaWsdlMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JavaWsdlMappingType.type, options);
      }

      public static JavaWsdlMappingType parse(File file) throws XmlException, IOException {
         return (JavaWsdlMappingType)XmlBeans.getContextTypeLoader().parse(file, JavaWsdlMappingType.type, (XmlOptions)null);
      }

      public static JavaWsdlMappingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JavaWsdlMappingType)XmlBeans.getContextTypeLoader().parse(file, JavaWsdlMappingType.type, options);
      }

      public static JavaWsdlMappingType parse(URL u) throws XmlException, IOException {
         return (JavaWsdlMappingType)XmlBeans.getContextTypeLoader().parse(u, JavaWsdlMappingType.type, (XmlOptions)null);
      }

      public static JavaWsdlMappingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JavaWsdlMappingType)XmlBeans.getContextTypeLoader().parse(u, JavaWsdlMappingType.type, options);
      }

      public static JavaWsdlMappingType parse(InputStream is) throws XmlException, IOException {
         return (JavaWsdlMappingType)XmlBeans.getContextTypeLoader().parse(is, JavaWsdlMappingType.type, (XmlOptions)null);
      }

      public static JavaWsdlMappingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JavaWsdlMappingType)XmlBeans.getContextTypeLoader().parse(is, JavaWsdlMappingType.type, options);
      }

      public static JavaWsdlMappingType parse(Reader r) throws XmlException, IOException {
         return (JavaWsdlMappingType)XmlBeans.getContextTypeLoader().parse(r, JavaWsdlMappingType.type, (XmlOptions)null);
      }

      public static JavaWsdlMappingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JavaWsdlMappingType)XmlBeans.getContextTypeLoader().parse(r, JavaWsdlMappingType.type, options);
      }

      public static JavaWsdlMappingType parse(XMLStreamReader sr) throws XmlException {
         return (JavaWsdlMappingType)XmlBeans.getContextTypeLoader().parse(sr, JavaWsdlMappingType.type, (XmlOptions)null);
      }

      public static JavaWsdlMappingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JavaWsdlMappingType)XmlBeans.getContextTypeLoader().parse(sr, JavaWsdlMappingType.type, options);
      }

      public static JavaWsdlMappingType parse(Node node) throws XmlException {
         return (JavaWsdlMappingType)XmlBeans.getContextTypeLoader().parse(node, JavaWsdlMappingType.type, (XmlOptions)null);
      }

      public static JavaWsdlMappingType parse(Node node, XmlOptions options) throws XmlException {
         return (JavaWsdlMappingType)XmlBeans.getContextTypeLoader().parse(node, JavaWsdlMappingType.type, options);
      }

      /** @deprecated */
      public static JavaWsdlMappingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JavaWsdlMappingType)XmlBeans.getContextTypeLoader().parse(xis, JavaWsdlMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JavaWsdlMappingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JavaWsdlMappingType)XmlBeans.getContextTypeLoader().parse(xis, JavaWsdlMappingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JavaWsdlMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JavaWsdlMappingType.type, options);
      }

      private Factory() {
      }
   }
}
