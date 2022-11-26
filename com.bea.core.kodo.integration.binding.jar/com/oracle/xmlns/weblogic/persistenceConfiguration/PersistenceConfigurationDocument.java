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

public interface PersistenceConfigurationDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PersistenceConfigurationDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("persistenceconfiguration700edoctype");

   PersistenceConfigurationType getPersistenceConfiguration();

   void setPersistenceConfiguration(PersistenceConfigurationType var1);

   PersistenceConfigurationType addNewPersistenceConfiguration();

   public static final class Factory {
      public static PersistenceConfigurationDocument newInstance() {
         return (PersistenceConfigurationDocument)XmlBeans.getContextTypeLoader().newInstance(PersistenceConfigurationDocument.type, (XmlOptions)null);
      }

      public static PersistenceConfigurationDocument newInstance(XmlOptions options) {
         return (PersistenceConfigurationDocument)XmlBeans.getContextTypeLoader().newInstance(PersistenceConfigurationDocument.type, options);
      }

      public static PersistenceConfigurationDocument parse(String xmlAsString) throws XmlException {
         return (PersistenceConfigurationDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceConfigurationDocument.type, (XmlOptions)null);
      }

      public static PersistenceConfigurationDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PersistenceConfigurationDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceConfigurationDocument.type, options);
      }

      public static PersistenceConfigurationDocument parse(File file) throws XmlException, IOException {
         return (PersistenceConfigurationDocument)XmlBeans.getContextTypeLoader().parse(file, PersistenceConfigurationDocument.type, (XmlOptions)null);
      }

      public static PersistenceConfigurationDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PersistenceConfigurationDocument)XmlBeans.getContextTypeLoader().parse(file, PersistenceConfigurationDocument.type, options);
      }

      public static PersistenceConfigurationDocument parse(URL u) throws XmlException, IOException {
         return (PersistenceConfigurationDocument)XmlBeans.getContextTypeLoader().parse(u, PersistenceConfigurationDocument.type, (XmlOptions)null);
      }

      public static PersistenceConfigurationDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PersistenceConfigurationDocument)XmlBeans.getContextTypeLoader().parse(u, PersistenceConfigurationDocument.type, options);
      }

      public static PersistenceConfigurationDocument parse(InputStream is) throws XmlException, IOException {
         return (PersistenceConfigurationDocument)XmlBeans.getContextTypeLoader().parse(is, PersistenceConfigurationDocument.type, (XmlOptions)null);
      }

      public static PersistenceConfigurationDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PersistenceConfigurationDocument)XmlBeans.getContextTypeLoader().parse(is, PersistenceConfigurationDocument.type, options);
      }

      public static PersistenceConfigurationDocument parse(Reader r) throws XmlException, IOException {
         return (PersistenceConfigurationDocument)XmlBeans.getContextTypeLoader().parse(r, PersistenceConfigurationDocument.type, (XmlOptions)null);
      }

      public static PersistenceConfigurationDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PersistenceConfigurationDocument)XmlBeans.getContextTypeLoader().parse(r, PersistenceConfigurationDocument.type, options);
      }

      public static PersistenceConfigurationDocument parse(XMLStreamReader sr) throws XmlException {
         return (PersistenceConfigurationDocument)XmlBeans.getContextTypeLoader().parse(sr, PersistenceConfigurationDocument.type, (XmlOptions)null);
      }

      public static PersistenceConfigurationDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PersistenceConfigurationDocument)XmlBeans.getContextTypeLoader().parse(sr, PersistenceConfigurationDocument.type, options);
      }

      public static PersistenceConfigurationDocument parse(Node node) throws XmlException {
         return (PersistenceConfigurationDocument)XmlBeans.getContextTypeLoader().parse(node, PersistenceConfigurationDocument.type, (XmlOptions)null);
      }

      public static PersistenceConfigurationDocument parse(Node node, XmlOptions options) throws XmlException {
         return (PersistenceConfigurationDocument)XmlBeans.getContextTypeLoader().parse(node, PersistenceConfigurationDocument.type, options);
      }

      /** @deprecated */
      public static PersistenceConfigurationDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PersistenceConfigurationDocument)XmlBeans.getContextTypeLoader().parse(xis, PersistenceConfigurationDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PersistenceConfigurationDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PersistenceConfigurationDocument)XmlBeans.getContextTypeLoader().parse(xis, PersistenceConfigurationDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceConfigurationDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceConfigurationDocument.type, options);
      }

      private Factory() {
      }
   }
}
