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

public interface OwsmSecurityPolicyType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(OwsmSecurityPolicyType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("owsmsecuritypolicytype5a02type");

   String getUri();

   void setUri(String var1);

   String addNewUri();

   StatusType.Enum getStatus();

   StatusType xgetStatus();

   boolean isSetStatus();

   void setStatus(StatusType.Enum var1);

   void xsetStatus(StatusType var1);

   void unsetStatus();

   public static final class Factory {
      public static OwsmSecurityPolicyType newInstance() {
         return (OwsmSecurityPolicyType)XmlBeans.getContextTypeLoader().newInstance(OwsmSecurityPolicyType.type, (XmlOptions)null);
      }

      public static OwsmSecurityPolicyType newInstance(XmlOptions options) {
         return (OwsmSecurityPolicyType)XmlBeans.getContextTypeLoader().newInstance(OwsmSecurityPolicyType.type, options);
      }

      public static OwsmSecurityPolicyType parse(java.lang.String xmlAsString) throws XmlException {
         return (OwsmSecurityPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OwsmSecurityPolicyType.type, (XmlOptions)null);
      }

      public static OwsmSecurityPolicyType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (OwsmSecurityPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OwsmSecurityPolicyType.type, options);
      }

      public static OwsmSecurityPolicyType parse(File file) throws XmlException, IOException {
         return (OwsmSecurityPolicyType)XmlBeans.getContextTypeLoader().parse(file, OwsmSecurityPolicyType.type, (XmlOptions)null);
      }

      public static OwsmSecurityPolicyType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (OwsmSecurityPolicyType)XmlBeans.getContextTypeLoader().parse(file, OwsmSecurityPolicyType.type, options);
      }

      public static OwsmSecurityPolicyType parse(URL u) throws XmlException, IOException {
         return (OwsmSecurityPolicyType)XmlBeans.getContextTypeLoader().parse(u, OwsmSecurityPolicyType.type, (XmlOptions)null);
      }

      public static OwsmSecurityPolicyType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (OwsmSecurityPolicyType)XmlBeans.getContextTypeLoader().parse(u, OwsmSecurityPolicyType.type, options);
      }

      public static OwsmSecurityPolicyType parse(InputStream is) throws XmlException, IOException {
         return (OwsmSecurityPolicyType)XmlBeans.getContextTypeLoader().parse(is, OwsmSecurityPolicyType.type, (XmlOptions)null);
      }

      public static OwsmSecurityPolicyType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (OwsmSecurityPolicyType)XmlBeans.getContextTypeLoader().parse(is, OwsmSecurityPolicyType.type, options);
      }

      public static OwsmSecurityPolicyType parse(Reader r) throws XmlException, IOException {
         return (OwsmSecurityPolicyType)XmlBeans.getContextTypeLoader().parse(r, OwsmSecurityPolicyType.type, (XmlOptions)null);
      }

      public static OwsmSecurityPolicyType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (OwsmSecurityPolicyType)XmlBeans.getContextTypeLoader().parse(r, OwsmSecurityPolicyType.type, options);
      }

      public static OwsmSecurityPolicyType parse(XMLStreamReader sr) throws XmlException {
         return (OwsmSecurityPolicyType)XmlBeans.getContextTypeLoader().parse(sr, OwsmSecurityPolicyType.type, (XmlOptions)null);
      }

      public static OwsmSecurityPolicyType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (OwsmSecurityPolicyType)XmlBeans.getContextTypeLoader().parse(sr, OwsmSecurityPolicyType.type, options);
      }

      public static OwsmSecurityPolicyType parse(Node node) throws XmlException {
         return (OwsmSecurityPolicyType)XmlBeans.getContextTypeLoader().parse(node, OwsmSecurityPolicyType.type, (XmlOptions)null);
      }

      public static OwsmSecurityPolicyType parse(Node node, XmlOptions options) throws XmlException {
         return (OwsmSecurityPolicyType)XmlBeans.getContextTypeLoader().parse(node, OwsmSecurityPolicyType.type, options);
      }

      /** @deprecated */
      public static OwsmSecurityPolicyType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (OwsmSecurityPolicyType)XmlBeans.getContextTypeLoader().parse(xis, OwsmSecurityPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static OwsmSecurityPolicyType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (OwsmSecurityPolicyType)XmlBeans.getContextTypeLoader().parse(xis, OwsmSecurityPolicyType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OwsmSecurityPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OwsmSecurityPolicyType.type, options);
      }

      private Factory() {
      }
   }
}
