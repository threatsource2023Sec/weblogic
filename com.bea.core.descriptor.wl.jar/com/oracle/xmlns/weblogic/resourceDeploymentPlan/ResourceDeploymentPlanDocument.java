package com.oracle.xmlns.weblogic.resourceDeploymentPlan;

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

public interface ResourceDeploymentPlanDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ResourceDeploymentPlanDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("resourcedeploymentplan12e8doctype");

   ResourceDeploymentPlanType getResourceDeploymentPlan();

   void setResourceDeploymentPlan(ResourceDeploymentPlanType var1);

   ResourceDeploymentPlanType addNewResourceDeploymentPlan();

   public static final class Factory {
      public static ResourceDeploymentPlanDocument newInstance() {
         return (ResourceDeploymentPlanDocument)XmlBeans.getContextTypeLoader().newInstance(ResourceDeploymentPlanDocument.type, (XmlOptions)null);
      }

      public static ResourceDeploymentPlanDocument newInstance(XmlOptions options) {
         return (ResourceDeploymentPlanDocument)XmlBeans.getContextTypeLoader().newInstance(ResourceDeploymentPlanDocument.type, options);
      }

      public static ResourceDeploymentPlanDocument parse(String xmlAsString) throws XmlException {
         return (ResourceDeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResourceDeploymentPlanDocument.type, (XmlOptions)null);
      }

      public static ResourceDeploymentPlanDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ResourceDeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResourceDeploymentPlanDocument.type, options);
      }

      public static ResourceDeploymentPlanDocument parse(File file) throws XmlException, IOException {
         return (ResourceDeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(file, ResourceDeploymentPlanDocument.type, (XmlOptions)null);
      }

      public static ResourceDeploymentPlanDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ResourceDeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(file, ResourceDeploymentPlanDocument.type, options);
      }

      public static ResourceDeploymentPlanDocument parse(URL u) throws XmlException, IOException {
         return (ResourceDeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(u, ResourceDeploymentPlanDocument.type, (XmlOptions)null);
      }

      public static ResourceDeploymentPlanDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ResourceDeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(u, ResourceDeploymentPlanDocument.type, options);
      }

      public static ResourceDeploymentPlanDocument parse(InputStream is) throws XmlException, IOException {
         return (ResourceDeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(is, ResourceDeploymentPlanDocument.type, (XmlOptions)null);
      }

      public static ResourceDeploymentPlanDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ResourceDeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(is, ResourceDeploymentPlanDocument.type, options);
      }

      public static ResourceDeploymentPlanDocument parse(Reader r) throws XmlException, IOException {
         return (ResourceDeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(r, ResourceDeploymentPlanDocument.type, (XmlOptions)null);
      }

      public static ResourceDeploymentPlanDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ResourceDeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(r, ResourceDeploymentPlanDocument.type, options);
      }

      public static ResourceDeploymentPlanDocument parse(XMLStreamReader sr) throws XmlException {
         return (ResourceDeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(sr, ResourceDeploymentPlanDocument.type, (XmlOptions)null);
      }

      public static ResourceDeploymentPlanDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ResourceDeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(sr, ResourceDeploymentPlanDocument.type, options);
      }

      public static ResourceDeploymentPlanDocument parse(Node node) throws XmlException {
         return (ResourceDeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(node, ResourceDeploymentPlanDocument.type, (XmlOptions)null);
      }

      public static ResourceDeploymentPlanDocument parse(Node node, XmlOptions options) throws XmlException {
         return (ResourceDeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(node, ResourceDeploymentPlanDocument.type, options);
      }

      /** @deprecated */
      public static ResourceDeploymentPlanDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ResourceDeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(xis, ResourceDeploymentPlanDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ResourceDeploymentPlanDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ResourceDeploymentPlanDocument)XmlBeans.getContextTypeLoader().parse(xis, ResourceDeploymentPlanDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResourceDeploymentPlanDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResourceDeploymentPlanDocument.type, options);
      }

      private Factory() {
      }
   }
}
