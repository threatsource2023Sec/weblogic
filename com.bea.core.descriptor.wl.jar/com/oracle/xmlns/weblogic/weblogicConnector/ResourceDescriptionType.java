package com.oracle.xmlns.weblogic.weblogicConnector;

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

public interface ResourceDescriptionType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ResourceDescriptionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("resourcedescriptiontype6655type");

   JndiNameType getResRefName();

   void setResRefName(JndiNameType var1);

   JndiNameType addNewResRefName();

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

   DefaultResourcePrincipalType getDefaultResourcePrincipal();

   boolean isSetDefaultResourcePrincipal();

   void setDefaultResourcePrincipal(DefaultResourcePrincipalType var1);

   DefaultResourcePrincipalType addNewDefaultResourcePrincipal();

   void unsetDefaultResourcePrincipal();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ResourceDescriptionType newInstance() {
         return (ResourceDescriptionType)XmlBeans.getContextTypeLoader().newInstance(ResourceDescriptionType.type, (XmlOptions)null);
      }

      public static ResourceDescriptionType newInstance(XmlOptions options) {
         return (ResourceDescriptionType)XmlBeans.getContextTypeLoader().newInstance(ResourceDescriptionType.type, options);
      }

      public static ResourceDescriptionType parse(java.lang.String xmlAsString) throws XmlException {
         return (ResourceDescriptionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResourceDescriptionType.type, (XmlOptions)null);
      }

      public static ResourceDescriptionType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ResourceDescriptionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResourceDescriptionType.type, options);
      }

      public static ResourceDescriptionType parse(File file) throws XmlException, IOException {
         return (ResourceDescriptionType)XmlBeans.getContextTypeLoader().parse(file, ResourceDescriptionType.type, (XmlOptions)null);
      }

      public static ResourceDescriptionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ResourceDescriptionType)XmlBeans.getContextTypeLoader().parse(file, ResourceDescriptionType.type, options);
      }

      public static ResourceDescriptionType parse(URL u) throws XmlException, IOException {
         return (ResourceDescriptionType)XmlBeans.getContextTypeLoader().parse(u, ResourceDescriptionType.type, (XmlOptions)null);
      }

      public static ResourceDescriptionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ResourceDescriptionType)XmlBeans.getContextTypeLoader().parse(u, ResourceDescriptionType.type, options);
      }

      public static ResourceDescriptionType parse(InputStream is) throws XmlException, IOException {
         return (ResourceDescriptionType)XmlBeans.getContextTypeLoader().parse(is, ResourceDescriptionType.type, (XmlOptions)null);
      }

      public static ResourceDescriptionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ResourceDescriptionType)XmlBeans.getContextTypeLoader().parse(is, ResourceDescriptionType.type, options);
      }

      public static ResourceDescriptionType parse(Reader r) throws XmlException, IOException {
         return (ResourceDescriptionType)XmlBeans.getContextTypeLoader().parse(r, ResourceDescriptionType.type, (XmlOptions)null);
      }

      public static ResourceDescriptionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ResourceDescriptionType)XmlBeans.getContextTypeLoader().parse(r, ResourceDescriptionType.type, options);
      }

      public static ResourceDescriptionType parse(XMLStreamReader sr) throws XmlException {
         return (ResourceDescriptionType)XmlBeans.getContextTypeLoader().parse(sr, ResourceDescriptionType.type, (XmlOptions)null);
      }

      public static ResourceDescriptionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ResourceDescriptionType)XmlBeans.getContextTypeLoader().parse(sr, ResourceDescriptionType.type, options);
      }

      public static ResourceDescriptionType parse(Node node) throws XmlException {
         return (ResourceDescriptionType)XmlBeans.getContextTypeLoader().parse(node, ResourceDescriptionType.type, (XmlOptions)null);
      }

      public static ResourceDescriptionType parse(Node node, XmlOptions options) throws XmlException {
         return (ResourceDescriptionType)XmlBeans.getContextTypeLoader().parse(node, ResourceDescriptionType.type, options);
      }

      /** @deprecated */
      public static ResourceDescriptionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ResourceDescriptionType)XmlBeans.getContextTypeLoader().parse(xis, ResourceDescriptionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ResourceDescriptionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ResourceDescriptionType)XmlBeans.getContextTypeLoader().parse(xis, ResourceDescriptionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResourceDescriptionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResourceDescriptionType.type, options);
      }

      private Factory() {
      }
   }
}
