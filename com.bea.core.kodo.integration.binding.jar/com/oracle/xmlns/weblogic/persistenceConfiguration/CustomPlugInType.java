package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface CustomPlugInType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomPlugInType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("customplugintype5592type");

   String getClassname();

   XmlString xgetClassname();

   boolean isNilClassname();

   boolean isSetClassname();

   void setClassname(String var1);

   void xsetClassname(XmlString var1);

   void setNilClassname();

   void unsetClassname();

   PropertiesType getProperties();

   boolean isNilProperties();

   boolean isSetProperties();

   void setProperties(PropertiesType var1);

   PropertiesType addNewProperties();

   void setNilProperties();

   void unsetProperties();

   public static final class Factory {
      public static CustomPlugInType newInstance() {
         return (CustomPlugInType)XmlBeans.getContextTypeLoader().newInstance(CustomPlugInType.type, (XmlOptions)null);
      }

      public static CustomPlugInType newInstance(XmlOptions options) {
         return (CustomPlugInType)XmlBeans.getContextTypeLoader().newInstance(CustomPlugInType.type, options);
      }

      public static CustomPlugInType parse(String xmlAsString) throws XmlException {
         return (CustomPlugInType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomPlugInType.type, (XmlOptions)null);
      }

      public static CustomPlugInType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomPlugInType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomPlugInType.type, options);
      }

      public static CustomPlugInType parse(File file) throws XmlException, IOException {
         return (CustomPlugInType)XmlBeans.getContextTypeLoader().parse(file, CustomPlugInType.type, (XmlOptions)null);
      }

      public static CustomPlugInType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomPlugInType)XmlBeans.getContextTypeLoader().parse(file, CustomPlugInType.type, options);
      }

      public static CustomPlugInType parse(URL u) throws XmlException, IOException {
         return (CustomPlugInType)XmlBeans.getContextTypeLoader().parse(u, CustomPlugInType.type, (XmlOptions)null);
      }

      public static CustomPlugInType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomPlugInType)XmlBeans.getContextTypeLoader().parse(u, CustomPlugInType.type, options);
      }

      public static CustomPlugInType parse(InputStream is) throws XmlException, IOException {
         return (CustomPlugInType)XmlBeans.getContextTypeLoader().parse(is, CustomPlugInType.type, (XmlOptions)null);
      }

      public static CustomPlugInType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomPlugInType)XmlBeans.getContextTypeLoader().parse(is, CustomPlugInType.type, options);
      }

      public static CustomPlugInType parse(Reader r) throws XmlException, IOException {
         return (CustomPlugInType)XmlBeans.getContextTypeLoader().parse(r, CustomPlugInType.type, (XmlOptions)null);
      }

      public static CustomPlugInType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomPlugInType)XmlBeans.getContextTypeLoader().parse(r, CustomPlugInType.type, options);
      }

      public static CustomPlugInType parse(XMLStreamReader sr) throws XmlException {
         return (CustomPlugInType)XmlBeans.getContextTypeLoader().parse(sr, CustomPlugInType.type, (XmlOptions)null);
      }

      public static CustomPlugInType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomPlugInType)XmlBeans.getContextTypeLoader().parse(sr, CustomPlugInType.type, options);
      }

      public static CustomPlugInType parse(Node node) throws XmlException {
         return (CustomPlugInType)XmlBeans.getContextTypeLoader().parse(node, CustomPlugInType.type, (XmlOptions)null);
      }

      public static CustomPlugInType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomPlugInType)XmlBeans.getContextTypeLoader().parse(node, CustomPlugInType.type, options);
      }

      /** @deprecated */
      public static CustomPlugInType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomPlugInType)XmlBeans.getContextTypeLoader().parse(xis, CustomPlugInType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomPlugInType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomPlugInType)XmlBeans.getContextTypeLoader().parse(xis, CustomPlugInType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomPlugInType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomPlugInType.type, options);
      }

      private Factory() {
      }
   }
}
