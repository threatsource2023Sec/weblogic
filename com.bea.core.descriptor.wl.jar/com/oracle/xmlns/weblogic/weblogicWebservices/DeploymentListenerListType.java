package com.oracle.xmlns.weblogic.weblogicWebservices;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.j2Ee.String;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface DeploymentListenerListType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DeploymentListenerListType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("deploymentlistenerlisttype090ctype");

   String[] getDeploymentListenerArray();

   String getDeploymentListenerArray(int var1);

   int sizeOfDeploymentListenerArray();

   void setDeploymentListenerArray(String[] var1);

   void setDeploymentListenerArray(int var1, String var2);

   String insertNewDeploymentListener(int var1);

   String addNewDeploymentListener();

   void removeDeploymentListener(int var1);

   public static final class Factory {
      public static DeploymentListenerListType newInstance() {
         return (DeploymentListenerListType)XmlBeans.getContextTypeLoader().newInstance(DeploymentListenerListType.type, (XmlOptions)null);
      }

      public static DeploymentListenerListType newInstance(XmlOptions options) {
         return (DeploymentListenerListType)XmlBeans.getContextTypeLoader().newInstance(DeploymentListenerListType.type, options);
      }

      public static DeploymentListenerListType parse(java.lang.String xmlAsString) throws XmlException {
         return (DeploymentListenerListType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DeploymentListenerListType.type, (XmlOptions)null);
      }

      public static DeploymentListenerListType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (DeploymentListenerListType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DeploymentListenerListType.type, options);
      }

      public static DeploymentListenerListType parse(File file) throws XmlException, IOException {
         return (DeploymentListenerListType)XmlBeans.getContextTypeLoader().parse(file, DeploymentListenerListType.type, (XmlOptions)null);
      }

      public static DeploymentListenerListType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DeploymentListenerListType)XmlBeans.getContextTypeLoader().parse(file, DeploymentListenerListType.type, options);
      }

      public static DeploymentListenerListType parse(URL u) throws XmlException, IOException {
         return (DeploymentListenerListType)XmlBeans.getContextTypeLoader().parse(u, DeploymentListenerListType.type, (XmlOptions)null);
      }

      public static DeploymentListenerListType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DeploymentListenerListType)XmlBeans.getContextTypeLoader().parse(u, DeploymentListenerListType.type, options);
      }

      public static DeploymentListenerListType parse(InputStream is) throws XmlException, IOException {
         return (DeploymentListenerListType)XmlBeans.getContextTypeLoader().parse(is, DeploymentListenerListType.type, (XmlOptions)null);
      }

      public static DeploymentListenerListType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DeploymentListenerListType)XmlBeans.getContextTypeLoader().parse(is, DeploymentListenerListType.type, options);
      }

      public static DeploymentListenerListType parse(Reader r) throws XmlException, IOException {
         return (DeploymentListenerListType)XmlBeans.getContextTypeLoader().parse(r, DeploymentListenerListType.type, (XmlOptions)null);
      }

      public static DeploymentListenerListType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DeploymentListenerListType)XmlBeans.getContextTypeLoader().parse(r, DeploymentListenerListType.type, options);
      }

      public static DeploymentListenerListType parse(XMLStreamReader sr) throws XmlException {
         return (DeploymentListenerListType)XmlBeans.getContextTypeLoader().parse(sr, DeploymentListenerListType.type, (XmlOptions)null);
      }

      public static DeploymentListenerListType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DeploymentListenerListType)XmlBeans.getContextTypeLoader().parse(sr, DeploymentListenerListType.type, options);
      }

      public static DeploymentListenerListType parse(Node node) throws XmlException {
         return (DeploymentListenerListType)XmlBeans.getContextTypeLoader().parse(node, DeploymentListenerListType.type, (XmlOptions)null);
      }

      public static DeploymentListenerListType parse(Node node, XmlOptions options) throws XmlException {
         return (DeploymentListenerListType)XmlBeans.getContextTypeLoader().parse(node, DeploymentListenerListType.type, options);
      }

      /** @deprecated */
      public static DeploymentListenerListType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DeploymentListenerListType)XmlBeans.getContextTypeLoader().parse(xis, DeploymentListenerListType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DeploymentListenerListType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DeploymentListenerListType)XmlBeans.getContextTypeLoader().parse(xis, DeploymentListenerListType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DeploymentListenerListType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DeploymentListenerListType.type, options);
      }

      private Factory() {
      }
   }
}
