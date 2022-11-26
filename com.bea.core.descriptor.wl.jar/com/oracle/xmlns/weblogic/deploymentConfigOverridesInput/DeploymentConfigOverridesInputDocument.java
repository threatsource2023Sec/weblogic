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

public interface DeploymentConfigOverridesInputDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DeploymentConfigOverridesInputDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("deploymentconfigoverridesinput8e6edoctype");

   DeploymentConfigOverridesInputType getDeploymentConfigOverridesInput();

   void setDeploymentConfigOverridesInput(DeploymentConfigOverridesInputType var1);

   DeploymentConfigOverridesInputType addNewDeploymentConfigOverridesInput();

   public static final class Factory {
      public static DeploymentConfigOverridesInputDocument newInstance() {
         return (DeploymentConfigOverridesInputDocument)XmlBeans.getContextTypeLoader().newInstance(DeploymentConfigOverridesInputDocument.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesInputDocument newInstance(XmlOptions options) {
         return (DeploymentConfigOverridesInputDocument)XmlBeans.getContextTypeLoader().newInstance(DeploymentConfigOverridesInputDocument.type, options);
      }

      public static DeploymentConfigOverridesInputDocument parse(String xmlAsString) throws XmlException {
         return (DeploymentConfigOverridesInputDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, DeploymentConfigOverridesInputDocument.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesInputDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DeploymentConfigOverridesInputDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, DeploymentConfigOverridesInputDocument.type, options);
      }

      public static DeploymentConfigOverridesInputDocument parse(File file) throws XmlException, IOException {
         return (DeploymentConfigOverridesInputDocument)XmlBeans.getContextTypeLoader().parse(file, DeploymentConfigOverridesInputDocument.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesInputDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DeploymentConfigOverridesInputDocument)XmlBeans.getContextTypeLoader().parse(file, DeploymentConfigOverridesInputDocument.type, options);
      }

      public static DeploymentConfigOverridesInputDocument parse(URL u) throws XmlException, IOException {
         return (DeploymentConfigOverridesInputDocument)XmlBeans.getContextTypeLoader().parse(u, DeploymentConfigOverridesInputDocument.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesInputDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DeploymentConfigOverridesInputDocument)XmlBeans.getContextTypeLoader().parse(u, DeploymentConfigOverridesInputDocument.type, options);
      }

      public static DeploymentConfigOverridesInputDocument parse(InputStream is) throws XmlException, IOException {
         return (DeploymentConfigOverridesInputDocument)XmlBeans.getContextTypeLoader().parse(is, DeploymentConfigOverridesInputDocument.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesInputDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DeploymentConfigOverridesInputDocument)XmlBeans.getContextTypeLoader().parse(is, DeploymentConfigOverridesInputDocument.type, options);
      }

      public static DeploymentConfigOverridesInputDocument parse(Reader r) throws XmlException, IOException {
         return (DeploymentConfigOverridesInputDocument)XmlBeans.getContextTypeLoader().parse(r, DeploymentConfigOverridesInputDocument.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesInputDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DeploymentConfigOverridesInputDocument)XmlBeans.getContextTypeLoader().parse(r, DeploymentConfigOverridesInputDocument.type, options);
      }

      public static DeploymentConfigOverridesInputDocument parse(XMLStreamReader sr) throws XmlException {
         return (DeploymentConfigOverridesInputDocument)XmlBeans.getContextTypeLoader().parse(sr, DeploymentConfigOverridesInputDocument.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesInputDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DeploymentConfigOverridesInputDocument)XmlBeans.getContextTypeLoader().parse(sr, DeploymentConfigOverridesInputDocument.type, options);
      }

      public static DeploymentConfigOverridesInputDocument parse(Node node) throws XmlException {
         return (DeploymentConfigOverridesInputDocument)XmlBeans.getContextTypeLoader().parse(node, DeploymentConfigOverridesInputDocument.type, (XmlOptions)null);
      }

      public static DeploymentConfigOverridesInputDocument parse(Node node, XmlOptions options) throws XmlException {
         return (DeploymentConfigOverridesInputDocument)XmlBeans.getContextTypeLoader().parse(node, DeploymentConfigOverridesInputDocument.type, options);
      }

      /** @deprecated */
      public static DeploymentConfigOverridesInputDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DeploymentConfigOverridesInputDocument)XmlBeans.getContextTypeLoader().parse(xis, DeploymentConfigOverridesInputDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DeploymentConfigOverridesInputDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DeploymentConfigOverridesInputDocument)XmlBeans.getContextTypeLoader().parse(xis, DeploymentConfigOverridesInputDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DeploymentConfigOverridesInputDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DeploymentConfigOverridesInputDocument.type, options);
      }

      private Factory() {
      }
   }
}
