package com.oracle.xmlns.weblogic.weblogicEjbJar;

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

public interface OwsmPolicyType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(OwsmPolicyType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("owsmpolicytype4ea1type");

   String getUri();

   void setUri(String var1);

   String addNewUri();

   String getStatus();

   boolean isSetStatus();

   void setStatus(String var1);

   String addNewStatus();

   void unsetStatus();

   String getCategory();

   void setCategory(String var1);

   String addNewCategory();

   PropertyNamevalueType[] getSecurityConfigurationPropertyArray();

   PropertyNamevalueType getSecurityConfigurationPropertyArray(int var1);

   int sizeOfSecurityConfigurationPropertyArray();

   void setSecurityConfigurationPropertyArray(PropertyNamevalueType[] var1);

   void setSecurityConfigurationPropertyArray(int var1, PropertyNamevalueType var2);

   PropertyNamevalueType insertNewSecurityConfigurationProperty(int var1);

   PropertyNamevalueType addNewSecurityConfigurationProperty();

   void removeSecurityConfigurationProperty(int var1);

   public static final class Factory {
      public static OwsmPolicyType newInstance() {
         return (OwsmPolicyType)XmlBeans.getContextTypeLoader().newInstance(OwsmPolicyType.type, (XmlOptions)null);
      }

      public static OwsmPolicyType newInstance(XmlOptions options) {
         return (OwsmPolicyType)XmlBeans.getContextTypeLoader().newInstance(OwsmPolicyType.type, options);
      }

      public static OwsmPolicyType parse(java.lang.String xmlAsString) throws XmlException {
         return (OwsmPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OwsmPolicyType.type, (XmlOptions)null);
      }

      public static OwsmPolicyType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (OwsmPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OwsmPolicyType.type, options);
      }

      public static OwsmPolicyType parse(File file) throws XmlException, IOException {
         return (OwsmPolicyType)XmlBeans.getContextTypeLoader().parse(file, OwsmPolicyType.type, (XmlOptions)null);
      }

      public static OwsmPolicyType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (OwsmPolicyType)XmlBeans.getContextTypeLoader().parse(file, OwsmPolicyType.type, options);
      }

      public static OwsmPolicyType parse(URL u) throws XmlException, IOException {
         return (OwsmPolicyType)XmlBeans.getContextTypeLoader().parse(u, OwsmPolicyType.type, (XmlOptions)null);
      }

      public static OwsmPolicyType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (OwsmPolicyType)XmlBeans.getContextTypeLoader().parse(u, OwsmPolicyType.type, options);
      }

      public static OwsmPolicyType parse(InputStream is) throws XmlException, IOException {
         return (OwsmPolicyType)XmlBeans.getContextTypeLoader().parse(is, OwsmPolicyType.type, (XmlOptions)null);
      }

      public static OwsmPolicyType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (OwsmPolicyType)XmlBeans.getContextTypeLoader().parse(is, OwsmPolicyType.type, options);
      }

      public static OwsmPolicyType parse(Reader r) throws XmlException, IOException {
         return (OwsmPolicyType)XmlBeans.getContextTypeLoader().parse(r, OwsmPolicyType.type, (XmlOptions)null);
      }

      public static OwsmPolicyType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (OwsmPolicyType)XmlBeans.getContextTypeLoader().parse(r, OwsmPolicyType.type, options);
      }

      public static OwsmPolicyType parse(XMLStreamReader sr) throws XmlException {
         return (OwsmPolicyType)XmlBeans.getContextTypeLoader().parse(sr, OwsmPolicyType.type, (XmlOptions)null);
      }

      public static OwsmPolicyType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (OwsmPolicyType)XmlBeans.getContextTypeLoader().parse(sr, OwsmPolicyType.type, options);
      }

      public static OwsmPolicyType parse(Node node) throws XmlException {
         return (OwsmPolicyType)XmlBeans.getContextTypeLoader().parse(node, OwsmPolicyType.type, (XmlOptions)null);
      }

      public static OwsmPolicyType parse(Node node, XmlOptions options) throws XmlException {
         return (OwsmPolicyType)XmlBeans.getContextTypeLoader().parse(node, OwsmPolicyType.type, options);
      }

      /** @deprecated */
      public static OwsmPolicyType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (OwsmPolicyType)XmlBeans.getContextTypeLoader().parse(xis, OwsmPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static OwsmPolicyType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (OwsmPolicyType)XmlBeans.getContextTypeLoader().parse(xis, OwsmPolicyType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OwsmPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OwsmPolicyType.type, options);
      }

      private Factory() {
      }
   }
}
