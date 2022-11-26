package com.oracle.xmlns.weblogic.deploymentConfigOverridesInput;

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

public interface DeploymentConfigOverridesInputType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DeploymentConfigOverridesInputType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("deploymentconfigoverridesinputtype5ac4type");

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
      public static DeploymentConfigOverridesInputType newInstance() {
         return (DeploymentConfigOverridesInputType)XmlBeans.getContextTypeLoader().newInstance(DeploymentConfigOverridesInputType.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesInputType newInstance(XmlOptions options) {
         return (DeploymentConfigOverridesInputType)XmlBeans.getContextTypeLoader().newInstance(DeploymentConfigOverridesInputType.type, options);
      }

      public static DeploymentConfigOverridesInputType parse(String xmlAsString) throws XmlException {
         return (DeploymentConfigOverridesInputType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DeploymentConfigOverridesInputType.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesInputType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DeploymentConfigOverridesInputType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DeploymentConfigOverridesInputType.type, options);
      }

      public static DeploymentConfigOverridesInputType parse(File file) throws XmlException, IOException {
         return (DeploymentConfigOverridesInputType)XmlBeans.getContextTypeLoader().parse(file, DeploymentConfigOverridesInputType.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesInputType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DeploymentConfigOverridesInputType)XmlBeans.getContextTypeLoader().parse(file, DeploymentConfigOverridesInputType.type, options);
      }

      public static DeploymentConfigOverridesInputType parse(URL u) throws XmlException, IOException {
         return (DeploymentConfigOverridesInputType)XmlBeans.getContextTypeLoader().parse(u, DeploymentConfigOverridesInputType.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesInputType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DeploymentConfigOverridesInputType)XmlBeans.getContextTypeLoader().parse(u, DeploymentConfigOverridesInputType.type, options);
      }

      public static DeploymentConfigOverridesInputType parse(InputStream is) throws XmlException, IOException {
         return (DeploymentConfigOverridesInputType)XmlBeans.getContextTypeLoader().parse(is, DeploymentConfigOverridesInputType.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesInputType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DeploymentConfigOverridesInputType)XmlBeans.getContextTypeLoader().parse(is, DeploymentConfigOverridesInputType.type, options);
      }

      public static DeploymentConfigOverridesInputType parse(Reader r) throws XmlException, IOException {
         return (DeploymentConfigOverridesInputType)XmlBeans.getContextTypeLoader().parse(r, DeploymentConfigOverridesInputType.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesInputType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DeploymentConfigOverridesInputType)XmlBeans.getContextTypeLoader().parse(r, DeploymentConfigOverridesInputType.type, options);
      }

      public static DeploymentConfigOverridesInputType parse(XMLStreamReader sr) throws XmlException {
         return (DeploymentConfigOverridesInputType)XmlBeans.getContextTypeLoader().parse(sr, DeploymentConfigOverridesInputType.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesInputType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DeploymentConfigOverridesInputType)XmlBeans.getContextTypeLoader().parse(sr, DeploymentConfigOverridesInputType.type, options);
      }

      public static DeploymentConfigOverridesInputType parse(Node node) throws XmlException {
         return (DeploymentConfigOverridesInputType)XmlBeans.getContextTypeLoader().parse(node, DeploymentConfigOverridesInputType.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesInputType parse(Node node, XmlOptions options) throws XmlException {
         return (DeploymentConfigOverridesInputType)XmlBeans.getContextTypeLoader().parse(node, DeploymentConfigOverridesInputType.type, options);
      }

      /** @deprecated */
      public static DeploymentConfigOverridesInputType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DeploymentConfigOverridesInputType)XmlBeans.getContextTypeLoader().parse(xis, DeploymentConfigOverridesInputType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DeploymentConfigOverridesInputType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DeploymentConfigOverridesInputType)XmlBeans.getContextTypeLoader().parse(xis, DeploymentConfigOverridesInputType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DeploymentConfigOverridesInputType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DeploymentConfigOverridesInputType.type, options);
      }

      private Factory() {
      }
   }
}
