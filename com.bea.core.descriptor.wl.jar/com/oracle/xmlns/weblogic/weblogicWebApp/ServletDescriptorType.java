package com.oracle.xmlns.weblogic.weblogicWebApp;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.ServletNameType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface ServletDescriptorType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ServletDescriptorType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("servletdescriptortype162atype");

   ServletNameType getServletName();

   void setServletName(ServletNameType var1);

   ServletNameType addNewServletName();

   RunAsPrincipalNameType getRunAsPrincipalName();

   boolean isSetRunAsPrincipalName();

   void setRunAsPrincipalName(RunAsPrincipalNameType var1);

   RunAsPrincipalNameType addNewRunAsPrincipalName();

   void unsetRunAsPrincipalName();

   InitAsPrincipalNameType getInitAsPrincipalName();

   boolean isSetInitAsPrincipalName();

   void setInitAsPrincipalName(InitAsPrincipalNameType var1);

   InitAsPrincipalNameType addNewInitAsPrincipalName();

   void unsetInitAsPrincipalName();

   DestroyAsPrincipalNameType getDestroyAsPrincipalName();

   boolean isSetDestroyAsPrincipalName();

   void setDestroyAsPrincipalName(DestroyAsPrincipalNameType var1);

   DestroyAsPrincipalNameType addNewDestroyAsPrincipalName();

   void unsetDestroyAsPrincipalName();

   DispatchPolicyType getDispatchPolicy();

   boolean isSetDispatchPolicy();

   void setDispatchPolicy(DispatchPolicyType var1);

   DispatchPolicyType addNewDispatchPolicy();

   void unsetDispatchPolicy();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ServletDescriptorType newInstance() {
         return (ServletDescriptorType)XmlBeans.getContextTypeLoader().newInstance(ServletDescriptorType.type, (XmlOptions)null);
      }

      public static ServletDescriptorType newInstance(XmlOptions options) {
         return (ServletDescriptorType)XmlBeans.getContextTypeLoader().newInstance(ServletDescriptorType.type, options);
      }

      public static ServletDescriptorType parse(String xmlAsString) throws XmlException {
         return (ServletDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ServletDescriptorType.type, (XmlOptions)null);
      }

      public static ServletDescriptorType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ServletDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ServletDescriptorType.type, options);
      }

      public static ServletDescriptorType parse(File file) throws XmlException, IOException {
         return (ServletDescriptorType)XmlBeans.getContextTypeLoader().parse(file, ServletDescriptorType.type, (XmlOptions)null);
      }

      public static ServletDescriptorType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ServletDescriptorType)XmlBeans.getContextTypeLoader().parse(file, ServletDescriptorType.type, options);
      }

      public static ServletDescriptorType parse(URL u) throws XmlException, IOException {
         return (ServletDescriptorType)XmlBeans.getContextTypeLoader().parse(u, ServletDescriptorType.type, (XmlOptions)null);
      }

      public static ServletDescriptorType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ServletDescriptorType)XmlBeans.getContextTypeLoader().parse(u, ServletDescriptorType.type, options);
      }

      public static ServletDescriptorType parse(InputStream is) throws XmlException, IOException {
         return (ServletDescriptorType)XmlBeans.getContextTypeLoader().parse(is, ServletDescriptorType.type, (XmlOptions)null);
      }

      public static ServletDescriptorType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ServletDescriptorType)XmlBeans.getContextTypeLoader().parse(is, ServletDescriptorType.type, options);
      }

      public static ServletDescriptorType parse(Reader r) throws XmlException, IOException {
         return (ServletDescriptorType)XmlBeans.getContextTypeLoader().parse(r, ServletDescriptorType.type, (XmlOptions)null);
      }

      public static ServletDescriptorType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ServletDescriptorType)XmlBeans.getContextTypeLoader().parse(r, ServletDescriptorType.type, options);
      }

      public static ServletDescriptorType parse(XMLStreamReader sr) throws XmlException {
         return (ServletDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, ServletDescriptorType.type, (XmlOptions)null);
      }

      public static ServletDescriptorType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ServletDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, ServletDescriptorType.type, options);
      }

      public static ServletDescriptorType parse(Node node) throws XmlException {
         return (ServletDescriptorType)XmlBeans.getContextTypeLoader().parse(node, ServletDescriptorType.type, (XmlOptions)null);
      }

      public static ServletDescriptorType parse(Node node, XmlOptions options) throws XmlException {
         return (ServletDescriptorType)XmlBeans.getContextTypeLoader().parse(node, ServletDescriptorType.type, options);
      }

      /** @deprecated */
      public static ServletDescriptorType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ServletDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, ServletDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ServletDescriptorType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ServletDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, ServletDescriptorType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ServletDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ServletDescriptorType.type, options);
      }

      private Factory() {
      }
   }
}
