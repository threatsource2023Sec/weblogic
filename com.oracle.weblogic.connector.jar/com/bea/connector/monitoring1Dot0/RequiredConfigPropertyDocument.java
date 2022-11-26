package com.bea.connector.monitoring1Dot0;

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

public interface RequiredConfigPropertyDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(RequiredConfigPropertyDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("requiredconfigproperty539cdoctype");

   RequiredConfigProperty getRequiredConfigProperty();

   void setRequiredConfigProperty(RequiredConfigProperty var1);

   RequiredConfigProperty addNewRequiredConfigProperty();

   public static final class Factory {
      public static RequiredConfigPropertyDocument newInstance() {
         return (RequiredConfigPropertyDocument)XmlBeans.getContextTypeLoader().newInstance(RequiredConfigPropertyDocument.type, (XmlOptions)null);
      }

      public static RequiredConfigPropertyDocument newInstance(XmlOptions options) {
         return (RequiredConfigPropertyDocument)XmlBeans.getContextTypeLoader().newInstance(RequiredConfigPropertyDocument.type, options);
      }

      public static RequiredConfigPropertyDocument parse(String xmlAsString) throws XmlException {
         return (RequiredConfigPropertyDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, RequiredConfigPropertyDocument.type, (XmlOptions)null);
      }

      public static RequiredConfigPropertyDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (RequiredConfigPropertyDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, RequiredConfigPropertyDocument.type, options);
      }

      public static RequiredConfigPropertyDocument parse(File file) throws XmlException, IOException {
         return (RequiredConfigPropertyDocument)XmlBeans.getContextTypeLoader().parse(file, RequiredConfigPropertyDocument.type, (XmlOptions)null);
      }

      public static RequiredConfigPropertyDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (RequiredConfigPropertyDocument)XmlBeans.getContextTypeLoader().parse(file, RequiredConfigPropertyDocument.type, options);
      }

      public static RequiredConfigPropertyDocument parse(URL u) throws XmlException, IOException {
         return (RequiredConfigPropertyDocument)XmlBeans.getContextTypeLoader().parse(u, RequiredConfigPropertyDocument.type, (XmlOptions)null);
      }

      public static RequiredConfigPropertyDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (RequiredConfigPropertyDocument)XmlBeans.getContextTypeLoader().parse(u, RequiredConfigPropertyDocument.type, options);
      }

      public static RequiredConfigPropertyDocument parse(InputStream is) throws XmlException, IOException {
         return (RequiredConfigPropertyDocument)XmlBeans.getContextTypeLoader().parse(is, RequiredConfigPropertyDocument.type, (XmlOptions)null);
      }

      public static RequiredConfigPropertyDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (RequiredConfigPropertyDocument)XmlBeans.getContextTypeLoader().parse(is, RequiredConfigPropertyDocument.type, options);
      }

      public static RequiredConfigPropertyDocument parse(Reader r) throws XmlException, IOException {
         return (RequiredConfigPropertyDocument)XmlBeans.getContextTypeLoader().parse(r, RequiredConfigPropertyDocument.type, (XmlOptions)null);
      }

      public static RequiredConfigPropertyDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (RequiredConfigPropertyDocument)XmlBeans.getContextTypeLoader().parse(r, RequiredConfigPropertyDocument.type, options);
      }

      public static RequiredConfigPropertyDocument parse(XMLStreamReader sr) throws XmlException {
         return (RequiredConfigPropertyDocument)XmlBeans.getContextTypeLoader().parse(sr, RequiredConfigPropertyDocument.type, (XmlOptions)null);
      }

      public static RequiredConfigPropertyDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (RequiredConfigPropertyDocument)XmlBeans.getContextTypeLoader().parse(sr, RequiredConfigPropertyDocument.type, options);
      }

      public static RequiredConfigPropertyDocument parse(Node node) throws XmlException {
         return (RequiredConfigPropertyDocument)XmlBeans.getContextTypeLoader().parse(node, RequiredConfigPropertyDocument.type, (XmlOptions)null);
      }

      public static RequiredConfigPropertyDocument parse(Node node, XmlOptions options) throws XmlException {
         return (RequiredConfigPropertyDocument)XmlBeans.getContextTypeLoader().parse(node, RequiredConfigPropertyDocument.type, options);
      }

      /** @deprecated */
      public static RequiredConfigPropertyDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (RequiredConfigPropertyDocument)XmlBeans.getContextTypeLoader().parse(xis, RequiredConfigPropertyDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static RequiredConfigPropertyDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (RequiredConfigPropertyDocument)XmlBeans.getContextTypeLoader().parse(xis, RequiredConfigPropertyDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RequiredConfigPropertyDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RequiredConfigPropertyDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface RequiredConfigProperty extends XmlObject {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(RequiredConfigProperty.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("requiredconfigpropertycc0aelemtype");

      String[] getDescriptionArray();

      String getDescriptionArray(int var1);

      XmlString[] xgetDescriptionArray();

      XmlString xgetDescriptionArray(int var1);

      int sizeOfDescriptionArray();

      void setDescriptionArray(String[] var1);

      void setDescriptionArray(int var1, String var2);

      void xsetDescriptionArray(XmlString[] var1);

      void xsetDescriptionArray(int var1, XmlString var2);

      void insertDescription(int var1, String var2);

      void addDescription(String var1);

      XmlString insertNewDescription(int var1);

      XmlString addNewDescription();

      void removeDescription(int var1);

      String getConfigPropertyName();

      XmlString xgetConfigPropertyName();

      void setConfigPropertyName(String var1);

      void xsetConfigPropertyName(XmlString var1);

      public static final class Factory {
         public static RequiredConfigProperty newInstance() {
            return (RequiredConfigProperty)XmlBeans.getContextTypeLoader().newInstance(RequiredConfigPropertyDocument.RequiredConfigProperty.type, (XmlOptions)null);
         }

         public static RequiredConfigProperty newInstance(XmlOptions options) {
            return (RequiredConfigProperty)XmlBeans.getContextTypeLoader().newInstance(RequiredConfigPropertyDocument.RequiredConfigProperty.type, options);
         }

         private Factory() {
         }
      }
   }
}
