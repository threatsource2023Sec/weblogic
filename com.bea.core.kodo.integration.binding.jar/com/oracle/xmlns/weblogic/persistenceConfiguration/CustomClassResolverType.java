package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface CustomClassResolverType extends ClassResolverType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomClassResolverType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("customclassresolvertype8435type");

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
      public static CustomClassResolverType newInstance() {
         return (CustomClassResolverType)XmlBeans.getContextTypeLoader().newInstance(CustomClassResolverType.type, (XmlOptions)null);
      }

      public static CustomClassResolverType newInstance(XmlOptions options) {
         return (CustomClassResolverType)XmlBeans.getContextTypeLoader().newInstance(CustomClassResolverType.type, options);
      }

      public static CustomClassResolverType parse(String xmlAsString) throws XmlException {
         return (CustomClassResolverType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomClassResolverType.type, (XmlOptions)null);
      }

      public static CustomClassResolverType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomClassResolverType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomClassResolverType.type, options);
      }

      public static CustomClassResolverType parse(File file) throws XmlException, IOException {
         return (CustomClassResolverType)XmlBeans.getContextTypeLoader().parse(file, CustomClassResolverType.type, (XmlOptions)null);
      }

      public static CustomClassResolverType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomClassResolverType)XmlBeans.getContextTypeLoader().parse(file, CustomClassResolverType.type, options);
      }

      public static CustomClassResolverType parse(URL u) throws XmlException, IOException {
         return (CustomClassResolverType)XmlBeans.getContextTypeLoader().parse(u, CustomClassResolverType.type, (XmlOptions)null);
      }

      public static CustomClassResolverType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomClassResolverType)XmlBeans.getContextTypeLoader().parse(u, CustomClassResolverType.type, options);
      }

      public static CustomClassResolverType parse(InputStream is) throws XmlException, IOException {
         return (CustomClassResolverType)XmlBeans.getContextTypeLoader().parse(is, CustomClassResolverType.type, (XmlOptions)null);
      }

      public static CustomClassResolverType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomClassResolverType)XmlBeans.getContextTypeLoader().parse(is, CustomClassResolverType.type, options);
      }

      public static CustomClassResolverType parse(Reader r) throws XmlException, IOException {
         return (CustomClassResolverType)XmlBeans.getContextTypeLoader().parse(r, CustomClassResolverType.type, (XmlOptions)null);
      }

      public static CustomClassResolverType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomClassResolverType)XmlBeans.getContextTypeLoader().parse(r, CustomClassResolverType.type, options);
      }

      public static CustomClassResolverType parse(XMLStreamReader sr) throws XmlException {
         return (CustomClassResolverType)XmlBeans.getContextTypeLoader().parse(sr, CustomClassResolverType.type, (XmlOptions)null);
      }

      public static CustomClassResolverType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomClassResolverType)XmlBeans.getContextTypeLoader().parse(sr, CustomClassResolverType.type, options);
      }

      public static CustomClassResolverType parse(Node node) throws XmlException {
         return (CustomClassResolverType)XmlBeans.getContextTypeLoader().parse(node, CustomClassResolverType.type, (XmlOptions)null);
      }

      public static CustomClassResolverType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomClassResolverType)XmlBeans.getContextTypeLoader().parse(node, CustomClassResolverType.type, options);
      }

      /** @deprecated */
      public static CustomClassResolverType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomClassResolverType)XmlBeans.getContextTypeLoader().parse(xis, CustomClassResolverType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomClassResolverType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomClassResolverType)XmlBeans.getContextTypeLoader().parse(xis, CustomClassResolverType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomClassResolverType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomClassResolverType.type, options);
      }

      private Factory() {
      }
   }
}
