package com.oracle.xmlns.weblogic.deploymentPlan;

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

public interface DeploymentPlanDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DeploymentPlanDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("deploymentplan45eedoctype");

   DeploymentPlanType getDeploymentPlan();

   void setDeploymentPlan(DeploymentPlanType var1);

   DeploymentPlanType addNewDeploymentPlan();

   public static final class Factory {
      public static DeploymentPlanDocument newInstance() {
         return (DeploymentPlanDocument)XmlBeans.getContextTypeLoader().newInstance(DeploymentPlanDocument.type, (XmlOptions)null);
      }

      public static DeploymentPlanDocument newInstance(XmlOptions options) {
         return (DeploymentPlanDocument)XmlBeans.getContextTypeLoader().newInstance(DeploymentPlanDocument.type, options);
      }

      public static DeploymentPlanDocument parse(String xmlAsString) throws XmlException {
         return (DeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, DeploymentPlanDocument.type, (XmlOptions)null);
      }

      public static DeploymentPlanDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, DeploymentPlanDocument.type, options);
      }

      public static DeploymentPlanDocument parse(File file) throws XmlException, IOException {
         return (DeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(file, DeploymentPlanDocument.type, (XmlOptions)null);
      }

      public static DeploymentPlanDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(file, DeploymentPlanDocument.type, options);
      }

      public static DeploymentPlanDocument parse(URL u) throws XmlException, IOException {
         return (DeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(u, DeploymentPlanDocument.type, (XmlOptions)null);
      }

      public static DeploymentPlanDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(u, DeploymentPlanDocument.type, options);
      }

      public static DeploymentPlanDocument parse(InputStream is) throws XmlException, IOException {
         return (DeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(is, DeploymentPlanDocument.type, (XmlOptions)null);
      }

      public static DeploymentPlanDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(is, DeploymentPlanDocument.type, options);
      }

      public static DeploymentPlanDocument parse(Reader r) throws XmlException, IOException {
         return (DeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(r, DeploymentPlanDocument.type, (XmlOptions)null);
      }

      public static DeploymentPlanDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(r, DeploymentPlanDocument.type, options);
      }

      public static DeploymentPlanDocument parse(XMLStreamReader sr) throws XmlException {
         return (DeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(sr, DeploymentPlanDocument.type, (XmlOptions)null);
      }

      public static DeploymentPlanDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(sr, DeploymentPlanDocument.type, options);
      }

      public static DeploymentPlanDocument parse(Node node) throws XmlException {
         return (DeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(node, DeploymentPlanDocument.type, (XmlOptions)null);
      }

      public static DeploymentPlanDocument parse(Node node, XmlOptions options) throws XmlException {
         return (DeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(node, DeploymentPlanDocument.type, options);
      }

      /** @deprecated */
      public static DeploymentPlanDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(xis, DeploymentPlanDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DeploymentPlanDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(xis, DeploymentPlanDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DeploymentPlanDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DeploymentPlanDocument.type, options);
      }

      private Factory() {
      }
   }
}
