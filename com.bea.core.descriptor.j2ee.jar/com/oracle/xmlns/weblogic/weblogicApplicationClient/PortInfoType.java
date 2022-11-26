package com.oracle.xmlns.weblogic.weblogicApplicationClient;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.String;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface PortInfoType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PortInfoType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("portinfotypeddb5type");

   String getPortName();

   void setPortName(String var1);

   String addNewPortName();

   PropertyNamevalueType[] getStubPropertyArray();

   PropertyNamevalueType getStubPropertyArray(int var1);

   int sizeOfStubPropertyArray();

   void setStubPropertyArray(PropertyNamevalueType[] var1);

   void setStubPropertyArray(int var1, PropertyNamevalueType var2);

   PropertyNamevalueType insertNewStubProperty(int var1);

   PropertyNamevalueType addNewStubProperty();

   void removeStubProperty(int var1);

   PropertyNamevalueType[] getCallPropertyArray();

   PropertyNamevalueType getCallPropertyArray(int var1);

   int sizeOfCallPropertyArray();

   void setCallPropertyArray(PropertyNamevalueType[] var1);

   void setCallPropertyArray(int var1, PropertyNamevalueType var2);

   PropertyNamevalueType insertNewCallProperty(int var1);

   PropertyNamevalueType addNewCallProperty();

   void removeCallProperty(int var1);

   WsatConfigType getWsatConfig();

   boolean isSetWsatConfig();

   void setWsatConfig(WsatConfigType var1);

   WsatConfigType addNewWsatConfig();

   void unsetWsatConfig();

   OwsmPolicyType[] getOwsmPolicyArray();

   OwsmPolicyType getOwsmPolicyArray(int var1);

   int sizeOfOwsmPolicyArray();

   void setOwsmPolicyArray(OwsmPolicyType[] var1);

   void setOwsmPolicyArray(int var1, OwsmPolicyType var2);

   OwsmPolicyType insertNewOwsmPolicy(int var1);

   OwsmPolicyType addNewOwsmPolicy();

   void removeOwsmPolicy(int var1);

   OperationInfoType[] getOperationArray();

   OperationInfoType getOperationArray(int var1);

   int sizeOfOperationArray();

   void setOperationArray(OperationInfoType[] var1);

   void setOperationArray(int var1, OperationInfoType var2);

   OperationInfoType insertNewOperation(int var1);

   OperationInfoType addNewOperation();

   void removeOperation(int var1);

   public static final class Factory {
      public static PortInfoType newInstance() {
         return (PortInfoType)XmlBeans.getContextTypeLoader().newInstance(PortInfoType.type, (XmlOptions)null);
      }

      public static PortInfoType newInstance(XmlOptions options) {
         return (PortInfoType)XmlBeans.getContextTypeLoader().newInstance(PortInfoType.type, options);
      }

      public static PortInfoType parse(java.lang.String xmlAsString) throws XmlException {
         return (PortInfoType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PortInfoType.type, (XmlOptions)null);
      }

      public static PortInfoType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (PortInfoType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PortInfoType.type, options);
      }

      public static PortInfoType parse(File file) throws XmlException, IOException {
         return (PortInfoType)XmlBeans.getContextTypeLoader().parse(file, PortInfoType.type, (XmlOptions)null);
      }

      public static PortInfoType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PortInfoType)XmlBeans.getContextTypeLoader().parse(file, PortInfoType.type, options);
      }

      public static PortInfoType parse(URL u) throws XmlException, IOException {
         return (PortInfoType)XmlBeans.getContextTypeLoader().parse(u, PortInfoType.type, (XmlOptions)null);
      }

      public static PortInfoType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PortInfoType)XmlBeans.getContextTypeLoader().parse(u, PortInfoType.type, options);
      }

      public static PortInfoType parse(InputStream is) throws XmlException, IOException {
         return (PortInfoType)XmlBeans.getContextTypeLoader().parse(is, PortInfoType.type, (XmlOptions)null);
      }

      public static PortInfoType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PortInfoType)XmlBeans.getContextTypeLoader().parse(is, PortInfoType.type, options);
      }

      public static PortInfoType parse(Reader r) throws XmlException, IOException {
         return (PortInfoType)XmlBeans.getContextTypeLoader().parse(r, PortInfoType.type, (XmlOptions)null);
      }

      public static PortInfoType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PortInfoType)XmlBeans.getContextTypeLoader().parse(r, PortInfoType.type, options);
      }

      public static PortInfoType parse(XMLStreamReader sr) throws XmlException {
         return (PortInfoType)XmlBeans.getContextTypeLoader().parse(sr, PortInfoType.type, (XmlOptions)null);
      }

      public static PortInfoType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PortInfoType)XmlBeans.getContextTypeLoader().parse(sr, PortInfoType.type, options);
      }

      public static PortInfoType parse(Node node) throws XmlException {
         return (PortInfoType)XmlBeans.getContextTypeLoader().parse(node, PortInfoType.type, (XmlOptions)null);
      }

      public static PortInfoType parse(Node node, XmlOptions options) throws XmlException {
         return (PortInfoType)XmlBeans.getContextTypeLoader().parse(node, PortInfoType.type, options);
      }

      /** @deprecated */
      public static PortInfoType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PortInfoType)XmlBeans.getContextTypeLoader().parse(xis, PortInfoType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PortInfoType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PortInfoType)XmlBeans.getContextTypeLoader().parse(xis, PortInfoType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PortInfoType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PortInfoType.type, options);
      }

      private Factory() {
      }
   }
}
