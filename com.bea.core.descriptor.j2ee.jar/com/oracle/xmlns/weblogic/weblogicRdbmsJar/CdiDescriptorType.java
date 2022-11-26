package com.oracle.xmlns.weblogic.weblogicRdbmsJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.String;
import com.sun.java.xml.ns.javaee.XsdBooleanType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface CdiDescriptorType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CdiDescriptorType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("cdidescriptortype2323type");

   XsdBooleanType getPojoAnnotationEnabled();

   boolean isSetPojoAnnotationEnabled();

   void setPojoAnnotationEnabled(XsdBooleanType var1);

   XsdBooleanType addNewPojoAnnotationEnabled();

   void unsetPojoAnnotationEnabled();

   XsdBooleanType getImplicitBeanDiscoveryEnabled();

   boolean isSetImplicitBeanDiscoveryEnabled();

   void setImplicitBeanDiscoveryEnabled(XsdBooleanType var1);

   XsdBooleanType addNewImplicitBeanDiscoveryEnabled();

   void unsetImplicitBeanDiscoveryEnabled();

   String getPolicy();

   boolean isSetPolicy();

   void setPolicy(String var1);

   String addNewPolicy();

   void unsetPolicy();

   public static final class Factory {
      public static CdiDescriptorType newInstance() {
         return (CdiDescriptorType)XmlBeans.getContextTypeLoader().newInstance(CdiDescriptorType.type, (XmlOptions)null);
      }

      public static CdiDescriptorType newInstance(XmlOptions options) {
         return (CdiDescriptorType)XmlBeans.getContextTypeLoader().newInstance(CdiDescriptorType.type, options);
      }

      public static CdiDescriptorType parse(java.lang.String xmlAsString) throws XmlException {
         return (CdiDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CdiDescriptorType.type, (XmlOptions)null);
      }

      public static CdiDescriptorType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (CdiDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CdiDescriptorType.type, options);
      }

      public static CdiDescriptorType parse(File file) throws XmlException, IOException {
         return (CdiDescriptorType)XmlBeans.getContextTypeLoader().parse(file, CdiDescriptorType.type, (XmlOptions)null);
      }

      public static CdiDescriptorType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CdiDescriptorType)XmlBeans.getContextTypeLoader().parse(file, CdiDescriptorType.type, options);
      }

      public static CdiDescriptorType parse(URL u) throws XmlException, IOException {
         return (CdiDescriptorType)XmlBeans.getContextTypeLoader().parse(u, CdiDescriptorType.type, (XmlOptions)null);
      }

      public static CdiDescriptorType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CdiDescriptorType)XmlBeans.getContextTypeLoader().parse(u, CdiDescriptorType.type, options);
      }

      public static CdiDescriptorType parse(InputStream is) throws XmlException, IOException {
         return (CdiDescriptorType)XmlBeans.getContextTypeLoader().parse(is, CdiDescriptorType.type, (XmlOptions)null);
      }

      public static CdiDescriptorType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CdiDescriptorType)XmlBeans.getContextTypeLoader().parse(is, CdiDescriptorType.type, options);
      }

      public static CdiDescriptorType parse(Reader r) throws XmlException, IOException {
         return (CdiDescriptorType)XmlBeans.getContextTypeLoader().parse(r, CdiDescriptorType.type, (XmlOptions)null);
      }

      public static CdiDescriptorType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CdiDescriptorType)XmlBeans.getContextTypeLoader().parse(r, CdiDescriptorType.type, options);
      }

      public static CdiDescriptorType parse(XMLStreamReader sr) throws XmlException {
         return (CdiDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, CdiDescriptorType.type, (XmlOptions)null);
      }

      public static CdiDescriptorType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CdiDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, CdiDescriptorType.type, options);
      }

      public static CdiDescriptorType parse(Node node) throws XmlException {
         return (CdiDescriptorType)XmlBeans.getContextTypeLoader().parse(node, CdiDescriptorType.type, (XmlOptions)null);
      }

      public static CdiDescriptorType parse(Node node, XmlOptions options) throws XmlException {
         return (CdiDescriptorType)XmlBeans.getContextTypeLoader().parse(node, CdiDescriptorType.type, options);
      }

      /** @deprecated */
      public static CdiDescriptorType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CdiDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, CdiDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CdiDescriptorType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CdiDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, CdiDescriptorType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CdiDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CdiDescriptorType.type, options);
      }

      private Factory() {
      }
   }
}
