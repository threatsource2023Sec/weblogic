package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface WebserviceDescriptionType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WebserviceDescriptionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("webservicedescriptiontype97fdtype");

   DescriptionType getDescription();

   boolean isSetDescription();

   void setDescription(DescriptionType var1);

   DescriptionType addNewDescription();

   void unsetDescription();

   DisplayNameType getDisplayName();

   boolean isSetDisplayName();

   void setDisplayName(DisplayNameType var1);

   DisplayNameType addNewDisplayName();

   void unsetDisplayName();

   IconType getIcon();

   boolean isSetIcon();

   void setIcon(IconType var1);

   IconType addNewIcon();

   void unsetIcon();

   String getWebserviceDescriptionName();

   void setWebserviceDescriptionName(String var1);

   String addNewWebserviceDescriptionName();

   PathType getWsdlFile();

   boolean isSetWsdlFile();

   void setWsdlFile(PathType var1);

   PathType addNewWsdlFile();

   void unsetWsdlFile();

   PathType getJaxrpcMappingFile();

   boolean isSetJaxrpcMappingFile();

   void setJaxrpcMappingFile(PathType var1);

   PathType addNewJaxrpcMappingFile();

   void unsetJaxrpcMappingFile();

   PortComponentType[] getPortComponentArray();

   PortComponentType getPortComponentArray(int var1);

   int sizeOfPortComponentArray();

   void setPortComponentArray(PortComponentType[] var1);

   void setPortComponentArray(int var1, PortComponentType var2);

   PortComponentType insertNewPortComponent(int var1);

   PortComponentType addNewPortComponent();

   void removePortComponent(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static WebserviceDescriptionType newInstance() {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().newInstance(WebserviceDescriptionType.type, (XmlOptions)null);
      }

      public static WebserviceDescriptionType newInstance(XmlOptions options) {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().newInstance(WebserviceDescriptionType.type, options);
      }

      public static WebserviceDescriptionType parse(java.lang.String xmlAsString) throws XmlException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WebserviceDescriptionType.type, (XmlOptions)null);
      }

      public static WebserviceDescriptionType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WebserviceDescriptionType.type, options);
      }

      public static WebserviceDescriptionType parse(File file) throws XmlException, IOException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(file, WebserviceDescriptionType.type, (XmlOptions)null);
      }

      public static WebserviceDescriptionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(file, WebserviceDescriptionType.type, options);
      }

      public static WebserviceDescriptionType parse(URL u) throws XmlException, IOException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(u, WebserviceDescriptionType.type, (XmlOptions)null);
      }

      public static WebserviceDescriptionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(u, WebserviceDescriptionType.type, options);
      }

      public static WebserviceDescriptionType parse(InputStream is) throws XmlException, IOException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(is, WebserviceDescriptionType.type, (XmlOptions)null);
      }

      public static WebserviceDescriptionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(is, WebserviceDescriptionType.type, options);
      }

      public static WebserviceDescriptionType parse(Reader r) throws XmlException, IOException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(r, WebserviceDescriptionType.type, (XmlOptions)null);
      }

      public static WebserviceDescriptionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(r, WebserviceDescriptionType.type, options);
      }

      public static WebserviceDescriptionType parse(XMLStreamReader sr) throws XmlException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(sr, WebserviceDescriptionType.type, (XmlOptions)null);
      }

      public static WebserviceDescriptionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(sr, WebserviceDescriptionType.type, options);
      }

      public static WebserviceDescriptionType parse(Node node) throws XmlException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(node, WebserviceDescriptionType.type, (XmlOptions)null);
      }

      public static WebserviceDescriptionType parse(Node node, XmlOptions options) throws XmlException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(node, WebserviceDescriptionType.type, options);
      }

      /** @deprecated */
      public static WebserviceDescriptionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(xis, WebserviceDescriptionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WebserviceDescriptionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(xis, WebserviceDescriptionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WebserviceDescriptionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WebserviceDescriptionType.type, options);
      }

      private Factory() {
      }
   }
}
