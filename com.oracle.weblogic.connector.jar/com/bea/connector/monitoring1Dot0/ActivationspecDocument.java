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

public interface ActivationspecDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ActivationspecDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("activationspecb2bddoctype");

   Activationspec getActivationspec();

   void setActivationspec(Activationspec var1);

   Activationspec addNewActivationspec();

   public static final class Factory {
      public static ActivationspecDocument newInstance() {
         return (ActivationspecDocument)XmlBeans.getContextTypeLoader().newInstance(ActivationspecDocument.type, (XmlOptions)null);
      }

      public static ActivationspecDocument newInstance(XmlOptions options) {
         return (ActivationspecDocument)XmlBeans.getContextTypeLoader().newInstance(ActivationspecDocument.type, options);
      }

      public static ActivationspecDocument parse(String xmlAsString) throws XmlException {
         return (ActivationspecDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ActivationspecDocument.type, (XmlOptions)null);
      }

      public static ActivationspecDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ActivationspecDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ActivationspecDocument.type, options);
      }

      public static ActivationspecDocument parse(File file) throws XmlException, IOException {
         return (ActivationspecDocument)XmlBeans.getContextTypeLoader().parse(file, ActivationspecDocument.type, (XmlOptions)null);
      }

      public static ActivationspecDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ActivationspecDocument)XmlBeans.getContextTypeLoader().parse(file, ActivationspecDocument.type, options);
      }

      public static ActivationspecDocument parse(URL u) throws XmlException, IOException {
         return (ActivationspecDocument)XmlBeans.getContextTypeLoader().parse(u, ActivationspecDocument.type, (XmlOptions)null);
      }

      public static ActivationspecDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ActivationspecDocument)XmlBeans.getContextTypeLoader().parse(u, ActivationspecDocument.type, options);
      }

      public static ActivationspecDocument parse(InputStream is) throws XmlException, IOException {
         return (ActivationspecDocument)XmlBeans.getContextTypeLoader().parse(is, ActivationspecDocument.type, (XmlOptions)null);
      }

      public static ActivationspecDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ActivationspecDocument)XmlBeans.getContextTypeLoader().parse(is, ActivationspecDocument.type, options);
      }

      public static ActivationspecDocument parse(Reader r) throws XmlException, IOException {
         return (ActivationspecDocument)XmlBeans.getContextTypeLoader().parse(r, ActivationspecDocument.type, (XmlOptions)null);
      }

      public static ActivationspecDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ActivationspecDocument)XmlBeans.getContextTypeLoader().parse(r, ActivationspecDocument.type, options);
      }

      public static ActivationspecDocument parse(XMLStreamReader sr) throws XmlException {
         return (ActivationspecDocument)XmlBeans.getContextTypeLoader().parse(sr, ActivationspecDocument.type, (XmlOptions)null);
      }

      public static ActivationspecDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ActivationspecDocument)XmlBeans.getContextTypeLoader().parse(sr, ActivationspecDocument.type, options);
      }

      public static ActivationspecDocument parse(Node node) throws XmlException {
         return (ActivationspecDocument)XmlBeans.getContextTypeLoader().parse(node, ActivationspecDocument.type, (XmlOptions)null);
      }

      public static ActivationspecDocument parse(Node node, XmlOptions options) throws XmlException {
         return (ActivationspecDocument)XmlBeans.getContextTypeLoader().parse(node, ActivationspecDocument.type, options);
      }

      /** @deprecated */
      public static ActivationspecDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ActivationspecDocument)XmlBeans.getContextTypeLoader().parse(xis, ActivationspecDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ActivationspecDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ActivationspecDocument)XmlBeans.getContextTypeLoader().parse(xis, ActivationspecDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ActivationspecDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ActivationspecDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface Activationspec extends XmlObject {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Activationspec.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("activationspec1e6aelemtype");

      String getActivationspecClass();

      XmlString xgetActivationspecClass();

      void setActivationspecClass(String var1);

      void xsetActivationspecClass(XmlString var1);

      RequiredConfigPropertyDocument.RequiredConfigProperty[] getRequiredConfigPropertyArray();

      RequiredConfigPropertyDocument.RequiredConfigProperty getRequiredConfigPropertyArray(int var1);

      int sizeOfRequiredConfigPropertyArray();

      void setRequiredConfigPropertyArray(RequiredConfigPropertyDocument.RequiredConfigProperty[] var1);

      void setRequiredConfigPropertyArray(int var1, RequiredConfigPropertyDocument.RequiredConfigProperty var2);

      RequiredConfigPropertyDocument.RequiredConfigProperty insertNewRequiredConfigProperty(int var1);

      RequiredConfigPropertyDocument.RequiredConfigProperty addNewRequiredConfigProperty();

      void removeRequiredConfigProperty(int var1);

      ConfigPropertiesType getProperties();

      void setProperties(ConfigPropertiesType var1);

      ConfigPropertiesType addNewProperties();

      public static final class Factory {
         public static Activationspec newInstance() {
            return (Activationspec)XmlBeans.getContextTypeLoader().newInstance(ActivationspecDocument.Activationspec.type, (XmlOptions)null);
         }

         public static Activationspec newInstance(XmlOptions options) {
            return (Activationspec)XmlBeans.getContextTypeLoader().newInstance(ActivationspecDocument.Activationspec.type, options);
         }

         private Factory() {
         }
      }
   }
}
