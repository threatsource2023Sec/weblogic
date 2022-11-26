package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
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

public interface PropertiesType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PropertiesType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("propertiestype71e7type");

   PropertyType[] getPropertyArray();

   PropertyType getPropertyArray(int var1);

   boolean isNilPropertyArray(int var1);

   int sizeOfPropertyArray();

   void setPropertyArray(PropertyType[] var1);

   void setPropertyArray(int var1, PropertyType var2);

   void setNilPropertyArray(int var1);

   PropertyType insertNewProperty(int var1);

   PropertyType addNewProperty();

   void removeProperty(int var1);

   public static final class Factory {
      public static PropertiesType newInstance() {
         return (PropertiesType)XmlBeans.getContextTypeLoader().newInstance(PropertiesType.type, (XmlOptions)null);
      }

      public static PropertiesType newInstance(XmlOptions options) {
         return (PropertiesType)XmlBeans.getContextTypeLoader().newInstance(PropertiesType.type, options);
      }

      public static PropertiesType parse(String xmlAsString) throws XmlException {
         return (PropertiesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PropertiesType.type, (XmlOptions)null);
      }

      public static PropertiesType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PropertiesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PropertiesType.type, options);
      }

      public static PropertiesType parse(File file) throws XmlException, IOException {
         return (PropertiesType)XmlBeans.getContextTypeLoader().parse(file, PropertiesType.type, (XmlOptions)null);
      }

      public static PropertiesType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PropertiesType)XmlBeans.getContextTypeLoader().parse(file, PropertiesType.type, options);
      }

      public static PropertiesType parse(URL u) throws XmlException, IOException {
         return (PropertiesType)XmlBeans.getContextTypeLoader().parse(u, PropertiesType.type, (XmlOptions)null);
      }

      public static PropertiesType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PropertiesType)XmlBeans.getContextTypeLoader().parse(u, PropertiesType.type, options);
      }

      public static PropertiesType parse(InputStream is) throws XmlException, IOException {
         return (PropertiesType)XmlBeans.getContextTypeLoader().parse(is, PropertiesType.type, (XmlOptions)null);
      }

      public static PropertiesType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PropertiesType)XmlBeans.getContextTypeLoader().parse(is, PropertiesType.type, options);
      }

      public static PropertiesType parse(Reader r) throws XmlException, IOException {
         return (PropertiesType)XmlBeans.getContextTypeLoader().parse(r, PropertiesType.type, (XmlOptions)null);
      }

      public static PropertiesType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PropertiesType)XmlBeans.getContextTypeLoader().parse(r, PropertiesType.type, options);
      }

      public static PropertiesType parse(XMLStreamReader sr) throws XmlException {
         return (PropertiesType)XmlBeans.getContextTypeLoader().parse(sr, PropertiesType.type, (XmlOptions)null);
      }

      public static PropertiesType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PropertiesType)XmlBeans.getContextTypeLoader().parse(sr, PropertiesType.type, options);
      }

      public static PropertiesType parse(Node node) throws XmlException {
         return (PropertiesType)XmlBeans.getContextTypeLoader().parse(node, PropertiesType.type, (XmlOptions)null);
      }

      public static PropertiesType parse(Node node, XmlOptions options) throws XmlException {
         return (PropertiesType)XmlBeans.getContextTypeLoader().parse(node, PropertiesType.type, options);
      }

      /** @deprecated */
      public static PropertiesType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PropertiesType)XmlBeans.getContextTypeLoader().parse(xis, PropertiesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PropertiesType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PropertiesType)XmlBeans.getContextTypeLoader().parse(xis, PropertiesType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PropertiesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PropertiesType.type, options);
      }

      private Factory() {
      }
   }
}
