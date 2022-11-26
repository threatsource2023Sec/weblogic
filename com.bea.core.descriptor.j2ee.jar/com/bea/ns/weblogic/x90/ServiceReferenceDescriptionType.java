package com.bea.ns.weblogic.x90;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.j2Ee.JndiNameType;
import com.sun.java.xml.ns.j2Ee.String;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface ServiceReferenceDescriptionType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ServiceReferenceDescriptionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("servicereferencedescriptiontypec46ctype");

   JndiNameType getServiceRefName();

   void setServiceRefName(JndiNameType var1);

   JndiNameType addNewServiceRefName();

   String getWsdlUrl();

   void setWsdlUrl(String var1);

   String addNewWsdlUrl();

   PropertyNamevalueType[] getCallPropertyArray();

   PropertyNamevalueType getCallPropertyArray(int var1);

   int sizeOfCallPropertyArray();

   void setCallPropertyArray(PropertyNamevalueType[] var1);

   void setCallPropertyArray(int var1, PropertyNamevalueType var2);

   PropertyNamevalueType insertNewCallProperty(int var1);

   PropertyNamevalueType addNewCallProperty();

   void removeCallProperty(int var1);

   PortInfoType[] getPortInfoArray();

   PortInfoType getPortInfoArray(int var1);

   int sizeOfPortInfoArray();

   void setPortInfoArray(PortInfoType[] var1);

   void setPortInfoArray(int var1, PortInfoType var2);

   PortInfoType insertNewPortInfo(int var1);

   PortInfoType addNewPortInfo();

   void removePortInfo(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ServiceReferenceDescriptionType newInstance() {
         return (ServiceReferenceDescriptionType)XmlBeans.getContextTypeLoader().newInstance(ServiceReferenceDescriptionType.type, (XmlOptions)null);
      }

      public static ServiceReferenceDescriptionType newInstance(XmlOptions options) {
         return (ServiceReferenceDescriptionType)XmlBeans.getContextTypeLoader().newInstance(ServiceReferenceDescriptionType.type, options);
      }

      public static ServiceReferenceDescriptionType parse(java.lang.String xmlAsString) throws XmlException {
         return (ServiceReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ServiceReferenceDescriptionType.type, (XmlOptions)null);
      }

      public static ServiceReferenceDescriptionType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ServiceReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ServiceReferenceDescriptionType.type, options);
      }

      public static ServiceReferenceDescriptionType parse(File file) throws XmlException, IOException {
         return (ServiceReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(file, ServiceReferenceDescriptionType.type, (XmlOptions)null);
      }

      public static ServiceReferenceDescriptionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ServiceReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(file, ServiceReferenceDescriptionType.type, options);
      }

      public static ServiceReferenceDescriptionType parse(URL u) throws XmlException, IOException {
         return (ServiceReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(u, ServiceReferenceDescriptionType.type, (XmlOptions)null);
      }

      public static ServiceReferenceDescriptionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ServiceReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(u, ServiceReferenceDescriptionType.type, options);
      }

      public static ServiceReferenceDescriptionType parse(InputStream is) throws XmlException, IOException {
         return (ServiceReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(is, ServiceReferenceDescriptionType.type, (XmlOptions)null);
      }

      public static ServiceReferenceDescriptionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ServiceReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(is, ServiceReferenceDescriptionType.type, options);
      }

      public static ServiceReferenceDescriptionType parse(Reader r) throws XmlException, IOException {
         return (ServiceReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(r, ServiceReferenceDescriptionType.type, (XmlOptions)null);
      }

      public static ServiceReferenceDescriptionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ServiceReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(r, ServiceReferenceDescriptionType.type, options);
      }

      public static ServiceReferenceDescriptionType parse(XMLStreamReader sr) throws XmlException {
         return (ServiceReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(sr, ServiceReferenceDescriptionType.type, (XmlOptions)null);
      }

      public static ServiceReferenceDescriptionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ServiceReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(sr, ServiceReferenceDescriptionType.type, options);
      }

      public static ServiceReferenceDescriptionType parse(Node node) throws XmlException {
         return (ServiceReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(node, ServiceReferenceDescriptionType.type, (XmlOptions)null);
      }

      public static ServiceReferenceDescriptionType parse(Node node, XmlOptions options) throws XmlException {
         return (ServiceReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(node, ServiceReferenceDescriptionType.type, options);
      }

      /** @deprecated */
      public static ServiceReferenceDescriptionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ServiceReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(xis, ServiceReferenceDescriptionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ServiceReferenceDescriptionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ServiceReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(xis, ServiceReferenceDescriptionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ServiceReferenceDescriptionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ServiceReferenceDescriptionType.type, options);
      }

      private Factory() {
      }
   }
}
