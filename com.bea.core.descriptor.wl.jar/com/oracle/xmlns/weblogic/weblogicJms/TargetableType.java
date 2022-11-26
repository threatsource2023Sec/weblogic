package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
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

public interface TargetableType extends NamedEntityType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TargetableType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("targetabletype652btype");

   String getSubDeploymentName();

   XmlString xgetSubDeploymentName();

   boolean isSetSubDeploymentName();

   void setSubDeploymentName(String var1);

   void xsetSubDeploymentName(XmlString var1);

   void unsetSubDeploymentName();

   boolean getDefaultTargetingEnabled();

   XmlBoolean xgetDefaultTargetingEnabled();

   boolean isSetDefaultTargetingEnabled();

   void setDefaultTargetingEnabled(boolean var1);

   void xsetDefaultTargetingEnabled(XmlBoolean var1);

   void unsetDefaultTargetingEnabled();

   public static final class Factory {
      public static TargetableType newInstance() {
         return (TargetableType)XmlBeans.getContextTypeLoader().newInstance(TargetableType.type, (XmlOptions)null);
      }

      public static TargetableType newInstance(XmlOptions options) {
         return (TargetableType)XmlBeans.getContextTypeLoader().newInstance(TargetableType.type, options);
      }

      public static TargetableType parse(String xmlAsString) throws XmlException {
         return (TargetableType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TargetableType.type, (XmlOptions)null);
      }

      public static TargetableType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TargetableType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TargetableType.type, options);
      }

      public static TargetableType parse(File file) throws XmlException, IOException {
         return (TargetableType)XmlBeans.getContextTypeLoader().parse(file, TargetableType.type, (XmlOptions)null);
      }

      public static TargetableType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TargetableType)XmlBeans.getContextTypeLoader().parse(file, TargetableType.type, options);
      }

      public static TargetableType parse(URL u) throws XmlException, IOException {
         return (TargetableType)XmlBeans.getContextTypeLoader().parse(u, TargetableType.type, (XmlOptions)null);
      }

      public static TargetableType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TargetableType)XmlBeans.getContextTypeLoader().parse(u, TargetableType.type, options);
      }

      public static TargetableType parse(InputStream is) throws XmlException, IOException {
         return (TargetableType)XmlBeans.getContextTypeLoader().parse(is, TargetableType.type, (XmlOptions)null);
      }

      public static TargetableType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TargetableType)XmlBeans.getContextTypeLoader().parse(is, TargetableType.type, options);
      }

      public static TargetableType parse(Reader r) throws XmlException, IOException {
         return (TargetableType)XmlBeans.getContextTypeLoader().parse(r, TargetableType.type, (XmlOptions)null);
      }

      public static TargetableType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TargetableType)XmlBeans.getContextTypeLoader().parse(r, TargetableType.type, options);
      }

      public static TargetableType parse(XMLStreamReader sr) throws XmlException {
         return (TargetableType)XmlBeans.getContextTypeLoader().parse(sr, TargetableType.type, (XmlOptions)null);
      }

      public static TargetableType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TargetableType)XmlBeans.getContextTypeLoader().parse(sr, TargetableType.type, options);
      }

      public static TargetableType parse(Node node) throws XmlException {
         return (TargetableType)XmlBeans.getContextTypeLoader().parse(node, TargetableType.type, (XmlOptions)null);
      }

      public static TargetableType parse(Node node, XmlOptions options) throws XmlException {
         return (TargetableType)XmlBeans.getContextTypeLoader().parse(node, TargetableType.type, options);
      }

      /** @deprecated */
      public static TargetableType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TargetableType)XmlBeans.getContextTypeLoader().parse(xis, TargetableType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TargetableType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TargetableType)XmlBeans.getContextTypeLoader().parse(xis, TargetableType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TargetableType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TargetableType.type, options);
      }

      private Factory() {
      }
   }
}
