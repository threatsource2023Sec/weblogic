package com.oracle.xmlns.weblogic.deploymentConfigOverrides;

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

public interface DeploymentConfigOverridesDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DeploymentConfigOverridesDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("deploymentconfigoverridese1cedoctype");

   DeploymentConfigOverridesType getDeploymentConfigOverrides();

   void setDeploymentConfigOverrides(DeploymentConfigOverridesType var1);

   DeploymentConfigOverridesType addNewDeploymentConfigOverrides();

   public static final class Factory {
      public static DeploymentConfigOverridesDocument newInstance() {
         return (DeploymentConfigOverridesDocument)XmlBeans.getContextTypeLoader().newInstance(DeploymentConfigOverridesDocument.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesDocument newInstance(XmlOptions options) {
         return (DeploymentConfigOverridesDocument)XmlBeans.getContextTypeLoader().newInstance(DeploymentConfigOverridesDocument.type, options);
      }

      public static DeploymentConfigOverridesDocument parse(String xmlAsString) throws XmlException {
         return (DeploymentConfigOverridesDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, DeploymentConfigOverridesDocument.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DeploymentConfigOverridesDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, DeploymentConfigOverridesDocument.type, options);
      }

      public static DeploymentConfigOverridesDocument parse(File file) throws XmlException, IOException {
         return (DeploymentConfigOverridesDocument)XmlBeans.getContextTypeLoader().parse(file, DeploymentConfigOverridesDocument.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DeploymentConfigOverridesDocument)XmlBeans.getContextTypeLoader().parse(file, DeploymentConfigOverridesDocument.type, options);
      }

      public static DeploymentConfigOverridesDocument parse(URL u) throws XmlException, IOException {
         return (DeploymentConfigOverridesDocument)XmlBeans.getContextTypeLoader().parse(u, DeploymentConfigOverridesDocument.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DeploymentConfigOverridesDocument)XmlBeans.getContextTypeLoader().parse(u, DeploymentConfigOverridesDocument.type, options);
      }

      public static DeploymentConfigOverridesDocument parse(InputStream is) throws XmlException, IOException {
         return (DeploymentConfigOverridesDocument)XmlBeans.getContextTypeLoader().parse(is, DeploymentConfigOverridesDocument.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DeploymentConfigOverridesDocument)XmlBeans.getContextTypeLoader().parse(is, DeploymentConfigOverridesDocument.type, options);
      }

      public static DeploymentConfigOverridesDocument parse(Reader r) throws XmlException, IOException {
         return (DeploymentConfigOverridesDocument)XmlBeans.getContextTypeLoader().parse(r, DeploymentConfigOverridesDocument.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DeploymentConfigOverridesDocument)XmlBeans.getContextTypeLoader().parse(r, DeploymentConfigOverridesDocument.type, options);
      }

      public static DeploymentConfigOverridesDocument parse(XMLStreamReader sr) throws XmlException {
         return (DeploymentConfigOverridesDocument)XmlBeans.getContextTypeLoader().parse(sr, DeploymentConfigOverridesDocument.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DeploymentConfigOverridesDocument)XmlBeans.getContextTypeLoader().parse(sr, DeploymentConfigOverridesDocument.type, options);
      }

      public static DeploymentConfigOverridesDocument parse(Node node) throws XmlException {
         return (DeploymentConfigOverridesDocument)XmlBeans.getContextTypeLoader().parse(node, DeploymentConfigOverridesDocument.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesDocument parse(Node node, XmlOptions options) throws XmlException {
         return (DeploymentConfigOverridesDocument)XmlBeans.getContextTypeLoader().parse(node, DeploymentConfigOverridesDocument.type, options);
      }

      /** @deprecated */
      public static DeploymentConfigOverridesDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DeploymentConfigOverridesDocument)XmlBeans.getContextTypeLoader().parse(xis, DeploymentConfigOverridesDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DeploymentConfigOverridesDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DeploymentConfigOverridesDocument)XmlBeans.getContextTypeLoader().parse(xis, DeploymentConfigOverridesDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DeploymentConfigOverridesDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DeploymentConfigOverridesDocument.type, options);
      }

      private Factory() {
      }
   }
}
