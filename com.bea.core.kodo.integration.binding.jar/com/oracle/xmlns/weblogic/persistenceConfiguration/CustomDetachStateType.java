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

public interface CustomDetachStateType extends DetachStateType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomDetachStateType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("customdetachstatetype16d9type");

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
      public static CustomDetachStateType newInstance() {
         return (CustomDetachStateType)XmlBeans.getContextTypeLoader().newInstance(CustomDetachStateType.type, (XmlOptions)null);
      }

      public static CustomDetachStateType newInstance(XmlOptions options) {
         return (CustomDetachStateType)XmlBeans.getContextTypeLoader().newInstance(CustomDetachStateType.type, options);
      }

      public static CustomDetachStateType parse(String xmlAsString) throws XmlException {
         return (CustomDetachStateType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomDetachStateType.type, (XmlOptions)null);
      }

      public static CustomDetachStateType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomDetachStateType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomDetachStateType.type, options);
      }

      public static CustomDetachStateType parse(File file) throws XmlException, IOException {
         return (CustomDetachStateType)XmlBeans.getContextTypeLoader().parse(file, CustomDetachStateType.type, (XmlOptions)null);
      }

      public static CustomDetachStateType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomDetachStateType)XmlBeans.getContextTypeLoader().parse(file, CustomDetachStateType.type, options);
      }

      public static CustomDetachStateType parse(URL u) throws XmlException, IOException {
         return (CustomDetachStateType)XmlBeans.getContextTypeLoader().parse(u, CustomDetachStateType.type, (XmlOptions)null);
      }

      public static CustomDetachStateType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomDetachStateType)XmlBeans.getContextTypeLoader().parse(u, CustomDetachStateType.type, options);
      }

      public static CustomDetachStateType parse(InputStream is) throws XmlException, IOException {
         return (CustomDetachStateType)XmlBeans.getContextTypeLoader().parse(is, CustomDetachStateType.type, (XmlOptions)null);
      }

      public static CustomDetachStateType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomDetachStateType)XmlBeans.getContextTypeLoader().parse(is, CustomDetachStateType.type, options);
      }

      public static CustomDetachStateType parse(Reader r) throws XmlException, IOException {
         return (CustomDetachStateType)XmlBeans.getContextTypeLoader().parse(r, CustomDetachStateType.type, (XmlOptions)null);
      }

      public static CustomDetachStateType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomDetachStateType)XmlBeans.getContextTypeLoader().parse(r, CustomDetachStateType.type, options);
      }

      public static CustomDetachStateType parse(XMLStreamReader sr) throws XmlException {
         return (CustomDetachStateType)XmlBeans.getContextTypeLoader().parse(sr, CustomDetachStateType.type, (XmlOptions)null);
      }

      public static CustomDetachStateType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomDetachStateType)XmlBeans.getContextTypeLoader().parse(sr, CustomDetachStateType.type, options);
      }

      public static CustomDetachStateType parse(Node node) throws XmlException {
         return (CustomDetachStateType)XmlBeans.getContextTypeLoader().parse(node, CustomDetachStateType.type, (XmlOptions)null);
      }

      public static CustomDetachStateType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomDetachStateType)XmlBeans.getContextTypeLoader().parse(node, CustomDetachStateType.type, options);
      }

      /** @deprecated */
      public static CustomDetachStateType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomDetachStateType)XmlBeans.getContextTypeLoader().parse(xis, CustomDetachStateType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomDetachStateType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomDetachStateType)XmlBeans.getContextTypeLoader().parse(xis, CustomDetachStateType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomDetachStateType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomDetachStateType.type, options);
      }

      private Factory() {
      }
   }
}
