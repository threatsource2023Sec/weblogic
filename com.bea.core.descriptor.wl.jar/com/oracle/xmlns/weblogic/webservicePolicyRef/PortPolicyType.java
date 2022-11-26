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

public interface PortPolicyType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PortPolicyType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("portpolicytype83catype");

   String getPortName();

   void setPortName(String var1);

   String addNewPortName();

   WsPolicyType[] getWsPolicyArray();

   WsPolicyType getWsPolicyArray(int var1);

   int sizeOfWsPolicyArray();

   void setWsPolicyArray(WsPolicyType[] var1);

   void setWsPolicyArray(int var1, WsPolicyType var2);

   WsPolicyType insertNewWsPolicy(int var1);

   WsPolicyType addNewWsPolicy();

   void removeWsPolicy(int var1);

   OwsmSecurityPolicyType[] getOwsmSecurityPolicyArray();

   OwsmSecurityPolicyType getOwsmSecurityPolicyArray(int var1);

   int sizeOfOwsmSecurityPolicyArray();

   void setOwsmSecurityPolicyArray(OwsmSecurityPolicyType[] var1);

   void setOwsmSecurityPolicyArray(int var1, OwsmSecurityPolicyType var2);

   OwsmSecurityPolicyType insertNewOwsmSecurityPolicy(int var1);

   OwsmSecurityPolicyType addNewOwsmSecurityPolicy();

   void removeOwsmSecurityPolicy(int var1);

   public static final class Factory {
      public static PortPolicyType newInstance() {
         return (PortPolicyType)XmlBeans.getContextTypeLoader().newInstance(PortPolicyType.type, (XmlOptions)null);
      }

      public static PortPolicyType newInstance(XmlOptions options) {
         return (PortPolicyType)XmlBeans.getContextTypeLoader().newInstance(PortPolicyType.type, options);
      }

      public static PortPolicyType parse(java.lang.String xmlAsString) throws XmlException {
         return (PortPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PortPolicyType.type, (XmlOptions)null);
      }

      public static PortPolicyType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (PortPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PortPolicyType.type, options);
      }

      public static PortPolicyType parse(File file) throws XmlException, IOException {
         return (PortPolicyType)XmlBeans.getContextTypeLoader().parse(file, PortPolicyType.type, (XmlOptions)null);
      }

      public static PortPolicyType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PortPolicyType)XmlBeans.getContextTypeLoader().parse(file, PortPolicyType.type, options);
      }

      public static PortPolicyType parse(URL u) throws XmlException, IOException {
         return (PortPolicyType)XmlBeans.getContextTypeLoader().parse(u, PortPolicyType.type, (XmlOptions)null);
      }

      public static PortPolicyType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PortPolicyType)XmlBeans.getContextTypeLoader().parse(u, PortPolicyType.type, options);
      }

      public static PortPolicyType parse(InputStream is) throws XmlException, IOException {
         return (PortPolicyType)XmlBeans.getContextTypeLoader().parse(is, PortPolicyType.type, (XmlOptions)null);
      }

      public static PortPolicyType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PortPolicyType)XmlBeans.getContextTypeLoader().parse(is, PortPolicyType.type, options);
      }

      public static PortPolicyType parse(Reader r) throws XmlException, IOException {
         return (PortPolicyType)XmlBeans.getContextTypeLoader().parse(r, PortPolicyType.type, (XmlOptions)null);
      }

      public static PortPolicyType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PortPolicyType)XmlBeans.getContextTypeLoader().parse(r, PortPolicyType.type, options);
      }

      public static PortPolicyType parse(XMLStreamReader sr) throws XmlException {
         return (PortPolicyType)XmlBeans.getContextTypeLoader().parse(sr, PortPolicyType.type, (XmlOptions)null);
      }

      public static PortPolicyType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PortPolicyType)XmlBeans.getContextTypeLoader().parse(sr, PortPolicyType.type, options);
      }

      public static PortPolicyType parse(Node node) throws XmlException {
         return (PortPolicyType)XmlBeans.getContextTypeLoader().parse(node, PortPolicyType.type, (XmlOptions)null);
      }

      public static PortPolicyType parse(Node node, XmlOptions options) throws XmlException {
         return (PortPolicyType)XmlBeans.getContextTypeLoader().parse(node, PortPolicyType.type, options);
      }

      /** @deprecated */
      public static PortPolicyType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PortPolicyType)XmlBeans.getContextTypeLoader().parse(xis, PortPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PortPolicyType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PortPolicyType)XmlBeans.getContextTypeLoader().parse(xis, PortPolicyType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PortPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PortPolicyType.type, options);
      }

      private Factory() {
      }
   }
}
