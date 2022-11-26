package com.oracle.xmlns.weblogic.deploymentConfigOverridesInput;

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

public interface AppDeploymentType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AppDeploymentType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("appdeploymenttype8ec2type");

   String getName();

   XmlString xgetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   String getSourcePath();

   XmlString xgetSourcePath();

   void setSourcePath(String var1);

   void xsetSourcePath(XmlString var1);

   String getRetireTimeout();

   XmlString xgetRetireTimeout();

   boolean isSetRetireTimeout();

   void setRetireTimeout(String var1);

   void xsetRetireTimeout(XmlString var1);

   void unsetRetireTimeout();

   String getGeneratedVersion();

   XmlString xgetGeneratedVersion();

   boolean isSetGeneratedVersion();

   void setGeneratedVersion(String var1);

   void xsetGeneratedVersion(XmlString var1);

   void unsetGeneratedVersion();

   public static final class Factory {
      public static AppDeploymentType newInstance() {
         return (AppDeploymentType)XmlBeans.getContextTypeLoader().newInstance(AppDeploymentType.type, (XmlOptions)null);
      }

      public static AppDeploymentType newInstance(XmlOptions options) {
         return (AppDeploymentType)XmlBeans.getContextTypeLoader().newInstance(AppDeploymentType.type, options);
      }

      public static AppDeploymentType parse(String xmlAsString) throws XmlException {
         return (AppDeploymentType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AppDeploymentType.type, (XmlOptions)null);
      }

      public static AppDeploymentType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AppDeploymentType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AppDeploymentType.type, options);
      }

      public static AppDeploymentType parse(File file) throws XmlException, IOException {
         return (AppDeploymentType)XmlBeans.getContextTypeLoader().parse(file, AppDeploymentType.type, (XmlOptions)null);
      }

      public static AppDeploymentType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AppDeploymentType)XmlBeans.getContextTypeLoader().parse(file, AppDeploymentType.type, options);
      }

      public static AppDeploymentType parse(URL u) throws XmlException, IOException {
         return (AppDeploymentType)XmlBeans.getContextTypeLoader().parse(u, AppDeploymentType.type, (XmlOptions)null);
      }

      public static AppDeploymentType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AppDeploymentType)XmlBeans.getContextTypeLoader().parse(u, AppDeploymentType.type, options);
      }

      public static AppDeploymentType parse(InputStream is) throws XmlException, IOException {
         return (AppDeploymentType)XmlBeans.getContextTypeLoader().parse(is, AppDeploymentType.type, (XmlOptions)null);
      }

      public static AppDeploymentType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AppDeploymentType)XmlBeans.getContextTypeLoader().parse(is, AppDeploymentType.type, options);
      }

      public static AppDeploymentType parse(Reader r) throws XmlException, IOException {
         return (AppDeploymentType)XmlBeans.getContextTypeLoader().parse(r, AppDeploymentType.type, (XmlOptions)null);
      }

      public static AppDeploymentType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AppDeploymentType)XmlBeans.getContextTypeLoader().parse(r, AppDeploymentType.type, options);
      }

      public static AppDeploymentType parse(XMLStreamReader sr) throws XmlException {
         return (AppDeploymentType)XmlBeans.getContextTypeLoader().parse(sr, AppDeploymentType.type, (XmlOptions)null);
      }

      public static AppDeploymentType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AppDeploymentType)XmlBeans.getContextTypeLoader().parse(sr, AppDeploymentType.type, options);
      }

      public static AppDeploymentType parse(Node node) throws XmlException {
         return (AppDeploymentType)XmlBeans.getContextTypeLoader().parse(node, AppDeploymentType.type, (XmlOptions)null);
      }

      public static AppDeploymentType parse(Node node, XmlOptions options) throws XmlException {
         return (AppDeploymentType)XmlBeans.getContextTypeLoader().parse(node, AppDeploymentType.type, options);
      }

      /** @deprecated */
      public static AppDeploymentType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AppDeploymentType)XmlBeans.getContextTypeLoader().parse(xis, AppDeploymentType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AppDeploymentType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AppDeploymentType)XmlBeans.getContextTypeLoader().parse(xis, AppDeploymentType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AppDeploymentType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AppDeploymentType.type, options);
      }

      private Factory() {
      }
   }
}
