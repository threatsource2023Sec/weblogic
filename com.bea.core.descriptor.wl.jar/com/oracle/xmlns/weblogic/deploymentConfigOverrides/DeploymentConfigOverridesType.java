package com.oracle.xmlns.weblogic.deploymentConfigOverrides;

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

public interface DeploymentConfigOverridesType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DeploymentConfigOverridesType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("deploymentconfigoverridestypee424type");

   String getCommandLineOptions();

   XmlString xgetCommandLineOptions();

   boolean isSetCommandLineOptions();

   void setCommandLineOptions(String var1);

   void xsetCommandLineOptions(XmlString var1);

   void unsetCommandLineOptions();

   AppDeploymentType[] getAppDeploymentArray();

   AppDeploymentType getAppDeploymentArray(int var1);

   boolean isNilAppDeploymentArray(int var1);

   int sizeOfAppDeploymentArray();

   void setAppDeploymentArray(AppDeploymentType[] var1);

   void setAppDeploymentArray(int var1, AppDeploymentType var2);

   void setNilAppDeploymentArray(int var1);

   AppDeploymentType insertNewAppDeployment(int var1);

   AppDeploymentType addNewAppDeployment();

   void removeAppDeployment(int var1);

   LibraryType[] getLibraryArray();

   LibraryType getLibraryArray(int var1);

   boolean isNilLibraryArray(int var1);

   int sizeOfLibraryArray();

   void setLibraryArray(LibraryType[] var1);

   void setLibraryArray(int var1, LibraryType var2);

   void setNilLibraryArray(int var1);

   LibraryType insertNewLibrary(int var1);

   LibraryType addNewLibrary();

   void removeLibrary(int var1);

   public static final class Factory {
      public static DeploymentConfigOverridesType newInstance() {
         return (DeploymentConfigOverridesType)XmlBeans.getContextTypeLoader().newInstance(DeploymentConfigOverridesType.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesType newInstance(XmlOptions options) {
         return (DeploymentConfigOverridesType)XmlBeans.getContextTypeLoader().newInstance(DeploymentConfigOverridesType.type, options);
      }

      public static DeploymentConfigOverridesType parse(String xmlAsString) throws XmlException {
         return (DeploymentConfigOverridesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DeploymentConfigOverridesType.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DeploymentConfigOverridesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DeploymentConfigOverridesType.type, options);
      }

      public static DeploymentConfigOverridesType parse(File file) throws XmlException, IOException {
         return (DeploymentConfigOverridesType)XmlBeans.getContextTypeLoader().parse(file, DeploymentConfigOverridesType.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DeploymentConfigOverridesType)XmlBeans.getContextTypeLoader().parse(file, DeploymentConfigOverridesType.type, options);
      }

      public static DeploymentConfigOverridesType parse(URL u) throws XmlException, IOException {
         return (DeploymentConfigOverridesType)XmlBeans.getContextTypeLoader().parse(u, DeploymentConfigOverridesType.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DeploymentConfigOverridesType)XmlBeans.getContextTypeLoader().parse(u, DeploymentConfigOverridesType.type, options);
      }

      public static DeploymentConfigOverridesType parse(InputStream is) throws XmlException, IOException {
         return (DeploymentConfigOverridesType)XmlBeans.getContextTypeLoader().parse(is, DeploymentConfigOverridesType.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DeploymentConfigOverridesType)XmlBeans.getContextTypeLoader().parse(is, DeploymentConfigOverridesType.type, options);
      }

      public static DeploymentConfigOverridesType parse(Reader r) throws XmlException, IOException {
         return (DeploymentConfigOverridesType)XmlBeans.getContextTypeLoader().parse(r, DeploymentConfigOverridesType.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DeploymentConfigOverridesType)XmlBeans.getContextTypeLoader().parse(r, DeploymentConfigOverridesType.type, options);
      }

      public static DeploymentConfigOverridesType parse(XMLStreamReader sr) throws XmlException {
         return (DeploymentConfigOverridesType)XmlBeans.getContextTypeLoader().parse(sr, DeploymentConfigOverridesType.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DeploymentConfigOverridesType)XmlBeans.getContextTypeLoader().parse(sr, DeploymentConfigOverridesType.type, options);
      }

      public static DeploymentConfigOverridesType parse(Node node) throws XmlException {
         return (DeploymentConfigOverridesType)XmlBeans.getContextTypeLoader().parse(node, DeploymentConfigOverridesType.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesType parse(Node node, XmlOptions options) throws XmlException {
         return (DeploymentConfigOverridesType)XmlBeans.getContextTypeLoader().parse(node, DeploymentConfigOverridesType.type, options);
      }

      /** @deprecated */
      public static DeploymentConfigOverridesType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DeploymentConfigOverridesType)XmlBeans.getContextTypeLoader().parse(xis, DeploymentConfigOverridesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DeploymentConfigOverridesType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DeploymentConfigOverridesType)XmlBeans.getContextTypeLoader().parse(xis, DeploymentConfigOverridesType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DeploymentConfigOverridesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DeploymentConfigOverridesType.type, options);
      }

      private Factory() {
      }
   }
}
