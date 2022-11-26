package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
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

public interface SecurityParamsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SecurityParamsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("securityparamstypeefd5type");

   boolean getAttachJmsxUserId();

   XmlBoolean xgetAttachJmsxUserId();

   boolean isSetAttachJmsxUserId();

   void setAttachJmsxUserId(boolean var1);

   void xsetAttachJmsxUserId(XmlBoolean var1);

   void unsetAttachJmsxUserId();

   SecurityPolicyType.Enum getSecurityPolicy();

   SecurityPolicyType xgetSecurityPolicy();

   boolean isSetSecurityPolicy();

   void setSecurityPolicy(SecurityPolicyType.Enum var1);

   void xsetSecurityPolicy(SecurityPolicyType var1);

   void unsetSecurityPolicy();

   public static final class Factory {
      public static SecurityParamsType newInstance() {
         return (SecurityParamsType)XmlBeans.getContextTypeLoader().newInstance(SecurityParamsType.type, (XmlOptions)null);
      }

      public static SecurityParamsType newInstance(XmlOptions options) {
         return (SecurityParamsType)XmlBeans.getContextTypeLoader().newInstance(SecurityParamsType.type, options);
      }

      public static SecurityParamsType parse(String xmlAsString) throws XmlException {
         return (SecurityParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SecurityParamsType.type, (XmlOptions)null);
      }

      public static SecurityParamsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SecurityParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SecurityParamsType.type, options);
      }

      public static SecurityParamsType parse(File file) throws XmlException, IOException {
         return (SecurityParamsType)XmlBeans.getContextTypeLoader().parse(file, SecurityParamsType.type, (XmlOptions)null);
      }

      public static SecurityParamsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SecurityParamsType)XmlBeans.getContextTypeLoader().parse(file, SecurityParamsType.type, options);
      }

      public static SecurityParamsType parse(URL u) throws XmlException, IOException {
         return (SecurityParamsType)XmlBeans.getContextTypeLoader().parse(u, SecurityParamsType.type, (XmlOptions)null);
      }

      public static SecurityParamsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SecurityParamsType)XmlBeans.getContextTypeLoader().parse(u, SecurityParamsType.type, options);
      }

      public static SecurityParamsType parse(InputStream is) throws XmlException, IOException {
         return (SecurityParamsType)XmlBeans.getContextTypeLoader().parse(is, SecurityParamsType.type, (XmlOptions)null);
      }

      public static SecurityParamsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SecurityParamsType)XmlBeans.getContextTypeLoader().parse(is, SecurityParamsType.type, options);
      }

      public static SecurityParamsType parse(Reader r) throws XmlException, IOException {
         return (SecurityParamsType)XmlBeans.getContextTypeLoader().parse(r, SecurityParamsType.type, (XmlOptions)null);
      }

      public static SecurityParamsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SecurityParamsType)XmlBeans.getContextTypeLoader().parse(r, SecurityParamsType.type, options);
      }

      public static SecurityParamsType parse(XMLStreamReader sr) throws XmlException {
         return (SecurityParamsType)XmlBeans.getContextTypeLoader().parse(sr, SecurityParamsType.type, (XmlOptions)null);
      }

      public static SecurityParamsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SecurityParamsType)XmlBeans.getContextTypeLoader().parse(sr, SecurityParamsType.type, options);
      }

      public static SecurityParamsType parse(Node node) throws XmlException {
         return (SecurityParamsType)XmlBeans.getContextTypeLoader().parse(node, SecurityParamsType.type, (XmlOptions)null);
      }

      public static SecurityParamsType parse(Node node, XmlOptions options) throws XmlException {
         return (SecurityParamsType)XmlBeans.getContextTypeLoader().parse(node, SecurityParamsType.type, options);
      }

      /** @deprecated */
      public static SecurityParamsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SecurityParamsType)XmlBeans.getContextTypeLoader().parse(xis, SecurityParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SecurityParamsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SecurityParamsType)XmlBeans.getContextTypeLoader().parse(xis, SecurityParamsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SecurityParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SecurityParamsType.type, options);
      }

      private Factory() {
      }
   }
}
