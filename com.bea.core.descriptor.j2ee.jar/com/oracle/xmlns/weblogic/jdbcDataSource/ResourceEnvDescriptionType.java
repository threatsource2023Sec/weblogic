package com.oracle.xmlns.weblogic.jdbcDataSource;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.JndiNameType;
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

public interface ResourceEnvDescriptionType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ResourceEnvDescriptionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("resourceenvdescriptiontyped837type");

   JndiNameType getResourceEnvRefName();

   void setResourceEnvRefName(JndiNameType var1);

   JndiNameType addNewResourceEnvRefName();

   String getJndiName();

   boolean isSetJndiName();

   void setJndiName(String var1);

   String addNewJndiName();

   void unsetJndiName();

   String getResourceLink();

   boolean isSetResourceLink();

   void setResourceLink(String var1);

   String addNewResourceLink();

   void unsetResourceLink();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ResourceEnvDescriptionType newInstance() {
         return (ResourceEnvDescriptionType)XmlBeans.getContextTypeLoader().newInstance(ResourceEnvDescriptionType.type, (XmlOptions)null);
      }

      public static ResourceEnvDescriptionType newInstance(XmlOptions options) {
         return (ResourceEnvDescriptionType)XmlBeans.getContextTypeLoader().newInstance(ResourceEnvDescriptionType.type, options);
      }

      public static ResourceEnvDescriptionType parse(java.lang.String xmlAsString) throws XmlException {
         return (ResourceEnvDescriptionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResourceEnvDescriptionType.type, (XmlOptions)null);
      }

      public static ResourceEnvDescriptionType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ResourceEnvDescriptionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResourceEnvDescriptionType.type, options);
      }

      public static ResourceEnvDescriptionType parse(File file) throws XmlException, IOException {
         return (ResourceEnvDescriptionType)XmlBeans.getContextTypeLoader().parse(file, ResourceEnvDescriptionType.type, (XmlOptions)null);
      }

      public static ResourceEnvDescriptionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ResourceEnvDescriptionType)XmlBeans.getContextTypeLoader().parse(file, ResourceEnvDescriptionType.type, options);
      }

      public static ResourceEnvDescriptionType parse(URL u) throws XmlException, IOException {
         return (ResourceEnvDescriptionType)XmlBeans.getContextTypeLoader().parse(u, ResourceEnvDescriptionType.type, (XmlOptions)null);
      }

      public static ResourceEnvDescriptionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ResourceEnvDescriptionType)XmlBeans.getContextTypeLoader().parse(u, ResourceEnvDescriptionType.type, options);
      }

      public static ResourceEnvDescriptionType parse(InputStream is) throws XmlException, IOException {
         return (ResourceEnvDescriptionType)XmlBeans.getContextTypeLoader().parse(is, ResourceEnvDescriptionType.type, (XmlOptions)null);
      }

      public static ResourceEnvDescriptionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ResourceEnvDescriptionType)XmlBeans.getContextTypeLoader().parse(is, ResourceEnvDescriptionType.type, options);
      }

      public static ResourceEnvDescriptionType parse(Reader r) throws XmlException, IOException {
         return (ResourceEnvDescriptionType)XmlBeans.getContextTypeLoader().parse(r, ResourceEnvDescriptionType.type, (XmlOptions)null);
      }

      public static ResourceEnvDescriptionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ResourceEnvDescriptionType)XmlBeans.getContextTypeLoader().parse(r, ResourceEnvDescriptionType.type, options);
      }

      public static ResourceEnvDescriptionType parse(XMLStreamReader sr) throws XmlException {
         return (ResourceEnvDescriptionType)XmlBeans.getContextTypeLoader().parse(sr, ResourceEnvDescriptionType.type, (XmlOptions)null);
      }

      public static ResourceEnvDescriptionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ResourceEnvDescriptionType)XmlBeans.getContextTypeLoader().parse(sr, ResourceEnvDescriptionType.type, options);
      }

      public static ResourceEnvDescriptionType parse(Node node) throws XmlException {
         return (ResourceEnvDescriptionType)XmlBeans.getContextTypeLoader().parse(node, ResourceEnvDescriptionType.type, (XmlOptions)null);
      }

      public static ResourceEnvDescriptionType parse(Node node, XmlOptions options) throws XmlException {
         return (ResourceEnvDescriptionType)XmlBeans.getContextTypeLoader().parse(node, ResourceEnvDescriptionType.type, options);
      }

      /** @deprecated */
      public static ResourceEnvDescriptionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ResourceEnvDescriptionType)XmlBeans.getContextTypeLoader().parse(xis, ResourceEnvDescriptionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ResourceEnvDescriptionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ResourceEnvDescriptionType)XmlBeans.getContextTypeLoader().parse(xis, ResourceEnvDescriptionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResourceEnvDescriptionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResourceEnvDescriptionType.type, options);
      }

      private Factory() {
      }
   }
}
