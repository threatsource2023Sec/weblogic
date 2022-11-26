package com.oracle.xmlns.weblogic.webservicePolicyRef;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
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

public interface WebservicePolicyRefType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WebservicePolicyRefType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("webservicepolicyreftype96e4type");

   String getRefName();

   boolean isSetRefName();

   void setRefName(String var1);

   String addNewRefName();

   void unsetRefName();

   PortPolicyType[] getPortPolicyArray();

   PortPolicyType getPortPolicyArray(int var1);

   int sizeOfPortPolicyArray();

   void setPortPolicyArray(PortPolicyType[] var1);

   void setPortPolicyArray(int var1, PortPolicyType var2);

   PortPolicyType insertNewPortPolicy(int var1);

   PortPolicyType addNewPortPolicy();

   void removePortPolicy(int var1);

   OperationPolicyType[] getOperationPolicyArray();

   OperationPolicyType getOperationPolicyArray(int var1);

   int sizeOfOperationPolicyArray();

   void setOperationPolicyArray(OperationPolicyType[] var1);

   void setOperationPolicyArray(int var1, OperationPolicyType var2);

   OperationPolicyType insertNewOperationPolicy(int var1);

   OperationPolicyType addNewOperationPolicy();

   void removeOperationPolicy(int var1);

   java.lang.String getVersion();

   XmlString xgetVersion();

   boolean isSetVersion();

   void setVersion(java.lang.String var1);

   void xsetVersion(XmlString var1);

   void unsetVersion();

   public static final class Factory {
      public static WebservicePolicyRefType newInstance() {
         return (WebservicePolicyRefType)XmlBeans.getContextTypeLoader().newInstance(WebservicePolicyRefType.type, (XmlOptions)null);
      }

      public static WebservicePolicyRefType newInstance(XmlOptions options) {
         return (WebservicePolicyRefType)XmlBeans.getContextTypeLoader().newInstance(WebservicePolicyRefType.type, options);
      }

      public static WebservicePolicyRefType parse(java.lang.String xmlAsString) throws XmlException {
         return (WebservicePolicyRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WebservicePolicyRefType.type, (XmlOptions)null);
      }

      public static WebservicePolicyRefType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (WebservicePolicyRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WebservicePolicyRefType.type, options);
      }

      public static WebservicePolicyRefType parse(File file) throws XmlException, IOException {
         return (WebservicePolicyRefType)XmlBeans.getContextTypeLoader().parse(file, WebservicePolicyRefType.type, (XmlOptions)null);
      }

      public static WebservicePolicyRefType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WebservicePolicyRefType)XmlBeans.getContextTypeLoader().parse(file, WebservicePolicyRefType.type, options);
      }

      public static WebservicePolicyRefType parse(URL u) throws XmlException, IOException {
         return (WebservicePolicyRefType)XmlBeans.getContextTypeLoader().parse(u, WebservicePolicyRefType.type, (XmlOptions)null);
      }

      public static WebservicePolicyRefType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WebservicePolicyRefType)XmlBeans.getContextTypeLoader().parse(u, WebservicePolicyRefType.type, options);
      }

      public static WebservicePolicyRefType parse(InputStream is) throws XmlException, IOException {
         return (WebservicePolicyRefType)XmlBeans.getContextTypeLoader().parse(is, WebservicePolicyRefType.type, (XmlOptions)null);
      }

      public static WebservicePolicyRefType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WebservicePolicyRefType)XmlBeans.getContextTypeLoader().parse(is, WebservicePolicyRefType.type, options);
      }

      public static WebservicePolicyRefType parse(Reader r) throws XmlException, IOException {
         return (WebservicePolicyRefType)XmlBeans.getContextTypeLoader().parse(r, WebservicePolicyRefType.type, (XmlOptions)null);
      }

      public static WebservicePolicyRefType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WebservicePolicyRefType)XmlBeans.getContextTypeLoader().parse(r, WebservicePolicyRefType.type, options);
      }

      public static WebservicePolicyRefType parse(XMLStreamReader sr) throws XmlException {
         return (WebservicePolicyRefType)XmlBeans.getContextTypeLoader().parse(sr, WebservicePolicyRefType.type, (XmlOptions)null);
      }

      public static WebservicePolicyRefType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WebservicePolicyRefType)XmlBeans.getContextTypeLoader().parse(sr, WebservicePolicyRefType.type, options);
      }

      public static WebservicePolicyRefType parse(Node node) throws XmlException {
         return (WebservicePolicyRefType)XmlBeans.getContextTypeLoader().parse(node, WebservicePolicyRefType.type, (XmlOptions)null);
      }

      public static WebservicePolicyRefType parse(Node node, XmlOptions options) throws XmlException {
         return (WebservicePolicyRefType)XmlBeans.getContextTypeLoader().parse(node, WebservicePolicyRefType.type, options);
      }

      /** @deprecated */
      public static WebservicePolicyRefType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WebservicePolicyRefType)XmlBeans.getContextTypeLoader().parse(xis, WebservicePolicyRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WebservicePolicyRefType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WebservicePolicyRefType)XmlBeans.getContextTypeLoader().parse(xis, WebservicePolicyRefType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WebservicePolicyRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WebservicePolicyRefType.type, options);
      }

      private Factory() {
      }
   }
}
