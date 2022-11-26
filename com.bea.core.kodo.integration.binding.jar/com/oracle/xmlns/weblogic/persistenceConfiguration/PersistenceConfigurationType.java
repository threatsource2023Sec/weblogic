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

public interface PersistenceConfigurationType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PersistenceConfigurationType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("persistenceconfigurationtypefde4type");

   PersistenceUnitConfigurationType[] getPersistenceUnitConfigurationArray();

   PersistenceUnitConfigurationType getPersistenceUnitConfigurationArray(int var1);

   boolean isNilPersistenceUnitConfigurationArray(int var1);

   int sizeOfPersistenceUnitConfigurationArray();

   void setPersistenceUnitConfigurationArray(PersistenceUnitConfigurationType[] var1);

   void setPersistenceUnitConfigurationArray(int var1, PersistenceUnitConfigurationType var2);

   void setNilPersistenceUnitConfigurationArray(int var1);

   PersistenceUnitConfigurationType insertNewPersistenceUnitConfiguration(int var1);

   PersistenceUnitConfigurationType addNewPersistenceUnitConfiguration();

   void removePersistenceUnitConfiguration(int var1);

   public static final class Factory {
      public static PersistenceConfigurationType newInstance() {
         return (PersistenceConfigurationType)XmlBeans.getContextTypeLoader().newInstance(PersistenceConfigurationType.type, (XmlOptions)null);
      }

      public static PersistenceConfigurationType newInstance(XmlOptions options) {
         return (PersistenceConfigurationType)XmlBeans.getContextTypeLoader().newInstance(PersistenceConfigurationType.type, options);
      }

      public static PersistenceConfigurationType parse(String xmlAsString) throws XmlException {
         return (PersistenceConfigurationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceConfigurationType.type, (XmlOptions)null);
      }

      public static PersistenceConfigurationType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PersistenceConfigurationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceConfigurationType.type, options);
      }

      public static PersistenceConfigurationType parse(File file) throws XmlException, IOException {
         return (PersistenceConfigurationType)XmlBeans.getContextTypeLoader().parse(file, PersistenceConfigurationType.type, (XmlOptions)null);
      }

      public static PersistenceConfigurationType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PersistenceConfigurationType)XmlBeans.getContextTypeLoader().parse(file, PersistenceConfigurationType.type, options);
      }

      public static PersistenceConfigurationType parse(URL u) throws XmlException, IOException {
         return (PersistenceConfigurationType)XmlBeans.getContextTypeLoader().parse(u, PersistenceConfigurationType.type, (XmlOptions)null);
      }

      public static PersistenceConfigurationType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PersistenceConfigurationType)XmlBeans.getContextTypeLoader().parse(u, PersistenceConfigurationType.type, options);
      }

      public static PersistenceConfigurationType parse(InputStream is) throws XmlException, IOException {
         return (PersistenceConfigurationType)XmlBeans.getContextTypeLoader().parse(is, PersistenceConfigurationType.type, (XmlOptions)null);
      }

      public static PersistenceConfigurationType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PersistenceConfigurationType)XmlBeans.getContextTypeLoader().parse(is, PersistenceConfigurationType.type, options);
      }

      public static PersistenceConfigurationType parse(Reader r) throws XmlException, IOException {
         return (PersistenceConfigurationType)XmlBeans.getContextTypeLoader().parse(r, PersistenceConfigurationType.type, (XmlOptions)null);
      }

      public static PersistenceConfigurationType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PersistenceConfigurationType)XmlBeans.getContextTypeLoader().parse(r, PersistenceConfigurationType.type, options);
      }

      public static PersistenceConfigurationType parse(XMLStreamReader sr) throws XmlException {
         return (PersistenceConfigurationType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceConfigurationType.type, (XmlOptions)null);
      }

      public static PersistenceConfigurationType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PersistenceConfigurationType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceConfigurationType.type, options);
      }

      public static PersistenceConfigurationType parse(Node node) throws XmlException {
         return (PersistenceConfigurationType)XmlBeans.getContextTypeLoader().parse(node, PersistenceConfigurationType.type, (XmlOptions)null);
      }

      public static PersistenceConfigurationType parse(Node node, XmlOptions options) throws XmlException {
         return (PersistenceConfigurationType)XmlBeans.getContextTypeLoader().parse(node, PersistenceConfigurationType.type, options);
      }

      /** @deprecated */
      public static PersistenceConfigurationType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PersistenceConfigurationType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceConfigurationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PersistenceConfigurationType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PersistenceConfigurationType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceConfigurationType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceConfigurationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceConfigurationType.type, options);
      }

      private Factory() {
      }
   }
}
