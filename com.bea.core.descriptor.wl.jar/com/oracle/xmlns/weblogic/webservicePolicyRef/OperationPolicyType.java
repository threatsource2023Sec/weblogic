package com.oracle.xmlns.weblogic.webservicePolicyRef;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
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

public interface OperationPolicyType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(OperationPolicyType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("operationpolicytype317etype");

   String getOperationName();

   void setOperationName(String var1);

   String addNewOperationName();

   String getServiceLink();

   boolean isSetServiceLink();

   void setServiceLink(String var1);

   String addNewServiceLink();

   void unsetServiceLink();

   WsPolicyType[] getWsPolicyArray();

   WsPolicyType getWsPolicyArray(int var1);

   int sizeOfWsPolicyArray();

   void setWsPolicyArray(WsPolicyType[] var1);

   void setWsPolicyArray(int var1, WsPolicyType var2);

   WsPolicyType insertNewWsPolicy(int var1);

   WsPolicyType addNewWsPolicy();

   void removeWsPolicy(int var1);

   public static final class Factory {
      public static OperationPolicyType newInstance() {
         return (OperationPolicyType)XmlBeans.getContextTypeLoader().newInstance(OperationPolicyType.type, (XmlOptions)null);
      }

      public static OperationPolicyType newInstance(XmlOptions options) {
         return (OperationPolicyType)XmlBeans.getContextTypeLoader().newInstance(OperationPolicyType.type, options);
      }

      public static OperationPolicyType parse(java.lang.String xmlAsString) throws XmlException {
         return (OperationPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OperationPolicyType.type, (XmlOptions)null);
      }

      public static OperationPolicyType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (OperationPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OperationPolicyType.type, options);
      }

      public static OperationPolicyType parse(File file) throws XmlException, IOException {
         return (OperationPolicyType)XmlBeans.getContextTypeLoader().parse(file, OperationPolicyType.type, (XmlOptions)null);
      }

      public static OperationPolicyType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (OperationPolicyType)XmlBeans.getContextTypeLoader().parse(file, OperationPolicyType.type, options);
      }

      public static OperationPolicyType parse(URL u) throws XmlException, IOException {
         return (OperationPolicyType)XmlBeans.getContextTypeLoader().parse(u, OperationPolicyType.type, (XmlOptions)null);
      }

      public static OperationPolicyType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (OperationPolicyType)XmlBeans.getContextTypeLoader().parse(u, OperationPolicyType.type, options);
      }

      public static OperationPolicyType parse(InputStream is) throws XmlException, IOException {
         return (OperationPolicyType)XmlBeans.getContextTypeLoader().parse(is, OperationPolicyType.type, (XmlOptions)null);
      }

      public static OperationPolicyType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (OperationPolicyType)XmlBeans.getContextTypeLoader().parse(is, OperationPolicyType.type, options);
      }

      public static OperationPolicyType parse(Reader r) throws XmlException, IOException {
         return (OperationPolicyType)XmlBeans.getContextTypeLoader().parse(r, OperationPolicyType.type, (XmlOptions)null);
      }

      public static OperationPolicyType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (OperationPolicyType)XmlBeans.getContextTypeLoader().parse(r, OperationPolicyType.type, options);
      }

      public static OperationPolicyType parse(XMLStreamReader sr) throws XmlException {
         return (OperationPolicyType)XmlBeans.getContextTypeLoader().parse(sr, OperationPolicyType.type, (XmlOptions)null);
      }

      public static OperationPolicyType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (OperationPolicyType)XmlBeans.getContextTypeLoader().parse(sr, OperationPolicyType.type, options);
      }

      public static OperationPolicyType parse(Node node) throws XmlException {
         return (OperationPolicyType)XmlBeans.getContextTypeLoader().parse(node, OperationPolicyType.type, (XmlOptions)null);
      }

      public static OperationPolicyType parse(Node node, XmlOptions options) throws XmlException {
         return (OperationPolicyType)XmlBeans.getContextTypeLoader().parse(node, OperationPolicyType.type, options);
      }

      /** @deprecated */
      public static OperationPolicyType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (OperationPolicyType)XmlBeans.getContextTypeLoader().parse(xis, OperationPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static OperationPolicyType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (OperationPolicyType)XmlBeans.getContextTypeLoader().parse(xis, OperationPolicyType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OperationPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OperationPolicyType.type, options);
      }

      private Factory() {
      }
   }
}
