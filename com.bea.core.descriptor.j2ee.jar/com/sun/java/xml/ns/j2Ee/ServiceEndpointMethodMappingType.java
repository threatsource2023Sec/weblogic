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

public interface ServiceEndpointMethodMappingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ServiceEndpointMethodMappingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("serviceendpointmethodmappingtyped1e3type");

   String getJavaMethodName();

   void setJavaMethodName(String var1);

   String addNewJavaMethodName();

   String getWsdlOperation();

   void setWsdlOperation(String var1);

   String addNewWsdlOperation();

   EmptyType getWrappedElement();

   boolean isSetWrappedElement();

   void setWrappedElement(EmptyType var1);

   EmptyType addNewWrappedElement();

   void unsetWrappedElement();

   MethodParamPartsMappingType[] getMethodParamPartsMappingArray();

   MethodParamPartsMappingType getMethodParamPartsMappingArray(int var1);

   int sizeOfMethodParamPartsMappingArray();

   void setMethodParamPartsMappingArray(MethodParamPartsMappingType[] var1);

   void setMethodParamPartsMappingArray(int var1, MethodParamPartsMappingType var2);

   MethodParamPartsMappingType insertNewMethodParamPartsMapping(int var1);

   MethodParamPartsMappingType addNewMethodParamPartsMapping();

   void removeMethodParamPartsMapping(int var1);

   WsdlReturnValueMappingType getWsdlReturnValueMapping();

   boolean isSetWsdlReturnValueMapping();

   void setWsdlReturnValueMapping(WsdlReturnValueMappingType var1);

   WsdlReturnValueMappingType addNewWsdlReturnValueMapping();

   void unsetWsdlReturnValueMapping();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ServiceEndpointMethodMappingType newInstance() {
         return (ServiceEndpointMethodMappingType)XmlBeans.getContextTypeLoader().newInstance(ServiceEndpointMethodMappingType.type, (XmlOptions)null);
      }

      public static ServiceEndpointMethodMappingType newInstance(XmlOptions options) {
         return (ServiceEndpointMethodMappingType)XmlBeans.getContextTypeLoader().newInstance(ServiceEndpointMethodMappingType.type, options);
      }

      public static ServiceEndpointMethodMappingType parse(java.lang.String xmlAsString) throws XmlException {
         return (ServiceEndpointMethodMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ServiceEndpointMethodMappingType.type, (XmlOptions)null);
      }

      public static ServiceEndpointMethodMappingType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ServiceEndpointMethodMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ServiceEndpointMethodMappingType.type, options);
      }

      public static ServiceEndpointMethodMappingType parse(File file) throws XmlException, IOException {
         return (ServiceEndpointMethodMappingType)XmlBeans.getContextTypeLoader().parse(file, ServiceEndpointMethodMappingType.type, (XmlOptions)null);
      }

      public static ServiceEndpointMethodMappingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ServiceEndpointMethodMappingType)XmlBeans.getContextTypeLoader().parse(file, ServiceEndpointMethodMappingType.type, options);
      }

      public static ServiceEndpointMethodMappingType parse(URL u) throws XmlException, IOException {
         return (ServiceEndpointMethodMappingType)XmlBeans.getContextTypeLoader().parse(u, ServiceEndpointMethodMappingType.type, (XmlOptions)null);
      }

      public static ServiceEndpointMethodMappingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ServiceEndpointMethodMappingType)XmlBeans.getContextTypeLoader().parse(u, ServiceEndpointMethodMappingType.type, options);
      }

      public static ServiceEndpointMethodMappingType parse(InputStream is) throws XmlException, IOException {
         return (ServiceEndpointMethodMappingType)XmlBeans.getContextTypeLoader().parse(is, ServiceEndpointMethodMappingType.type, (XmlOptions)null);
      }

      public static ServiceEndpointMethodMappingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ServiceEndpointMethodMappingType)XmlBeans.getContextTypeLoader().parse(is, ServiceEndpointMethodMappingType.type, options);
      }

      public static ServiceEndpointMethodMappingType parse(Reader r) throws XmlException, IOException {
         return (ServiceEndpointMethodMappingType)XmlBeans.getContextTypeLoader().parse(r, ServiceEndpointMethodMappingType.type, (XmlOptions)null);
      }

      public static ServiceEndpointMethodMappingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ServiceEndpointMethodMappingType)XmlBeans.getContextTypeLoader().parse(r, ServiceEndpointMethodMappingType.type, options);
      }

      public static ServiceEndpointMethodMappingType parse(XMLStreamReader sr) throws XmlException {
         return (ServiceEndpointMethodMappingType)XmlBeans.getContextTypeLoader().parse(sr, ServiceEndpointMethodMappingType.type, (XmlOptions)null);
      }

      public static ServiceEndpointMethodMappingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ServiceEndpointMethodMappingType)XmlBeans.getContextTypeLoader().parse(sr, ServiceEndpointMethodMappingType.type, options);
      }

      public static ServiceEndpointMethodMappingType parse(Node node) throws XmlException {
         return (ServiceEndpointMethodMappingType)XmlBeans.getContextTypeLoader().parse(node, ServiceEndpointMethodMappingType.type, (XmlOptions)null);
      }

      public static ServiceEndpointMethodMappingType parse(Node node, XmlOptions options) throws XmlException {
         return (ServiceEndpointMethodMappingType)XmlBeans.getContextTypeLoader().parse(node, ServiceEndpointMethodMappingType.type, options);
      }

      /** @deprecated */
      public static ServiceEndpointMethodMappingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ServiceEndpointMethodMappingType)XmlBeans.getContextTypeLoader().parse(xis, ServiceEndpointMethodMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ServiceEndpointMethodMappingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ServiceEndpointMethodMappingType)XmlBeans.getContextTypeLoader().parse(xis, ServiceEndpointMethodMappingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ServiceEndpointMethodMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ServiceEndpointMethodMappingType.type, options);
      }

      private Factory() {
      }
   }
}
