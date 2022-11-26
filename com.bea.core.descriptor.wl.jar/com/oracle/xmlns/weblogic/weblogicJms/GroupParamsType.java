package com.oracle.xmlns.weblogic.weblogicJms;

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

public interface GroupParamsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(GroupParamsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("groupparamstype12a2type");

   String getErrorDestination();

   XmlString xgetErrorDestination();

   boolean isNilErrorDestination();

   boolean isSetErrorDestination();

   void setErrorDestination(String var1);

   void xsetErrorDestination(XmlString var1);

   void setNilErrorDestination();

   void unsetErrorDestination();

   String getSubDeploymentName();

   XmlString xgetSubDeploymentName();

   void setSubDeploymentName(String var1);

   void xsetSubDeploymentName(XmlString var1);

   public static final class Factory {
      public static GroupParamsType newInstance() {
         return (GroupParamsType)XmlBeans.getContextTypeLoader().newInstance(GroupParamsType.type, (XmlOptions)null);
      }

      public static GroupParamsType newInstance(XmlOptions options) {
         return (GroupParamsType)XmlBeans.getContextTypeLoader().newInstance(GroupParamsType.type, options);
      }

      public static GroupParamsType parse(String xmlAsString) throws XmlException {
         return (GroupParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, GroupParamsType.type, (XmlOptions)null);
      }

      public static GroupParamsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (GroupParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, GroupParamsType.type, options);
      }

      public static GroupParamsType parse(File file) throws XmlException, IOException {
         return (GroupParamsType)XmlBeans.getContextTypeLoader().parse(file, GroupParamsType.type, (XmlOptions)null);
      }

      public static GroupParamsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (GroupParamsType)XmlBeans.getContextTypeLoader().parse(file, GroupParamsType.type, options);
      }

      public static GroupParamsType parse(URL u) throws XmlException, IOException {
         return (GroupParamsType)XmlBeans.getContextTypeLoader().parse(u, GroupParamsType.type, (XmlOptions)null);
      }

      public static GroupParamsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (GroupParamsType)XmlBeans.getContextTypeLoader().parse(u, GroupParamsType.type, options);
      }

      public static GroupParamsType parse(InputStream is) throws XmlException, IOException {
         return (GroupParamsType)XmlBeans.getContextTypeLoader().parse(is, GroupParamsType.type, (XmlOptions)null);
      }

      public static GroupParamsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (GroupParamsType)XmlBeans.getContextTypeLoader().parse(is, GroupParamsType.type, options);
      }

      public static GroupParamsType parse(Reader r) throws XmlException, IOException {
         return (GroupParamsType)XmlBeans.getContextTypeLoader().parse(r, GroupParamsType.type, (XmlOptions)null);
      }

      public static GroupParamsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (GroupParamsType)XmlBeans.getContextTypeLoader().parse(r, GroupParamsType.type, options);
      }

      public static GroupParamsType parse(XMLStreamReader sr) throws XmlException {
         return (GroupParamsType)XmlBeans.getContextTypeLoader().parse(sr, GroupParamsType.type, (XmlOptions)null);
      }

      public static GroupParamsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (GroupParamsType)XmlBeans.getContextTypeLoader().parse(sr, GroupParamsType.type, options);
      }

      public static GroupParamsType parse(Node node) throws XmlException {
         return (GroupParamsType)XmlBeans.getContextTypeLoader().parse(node, GroupParamsType.type, (XmlOptions)null);
      }

      public static GroupParamsType parse(Node node, XmlOptions options) throws XmlException {
         return (GroupParamsType)XmlBeans.getContextTypeLoader().parse(node, GroupParamsType.type, options);
      }

      /** @deprecated */
      public static GroupParamsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (GroupParamsType)XmlBeans.getContextTypeLoader().parse(xis, GroupParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static GroupParamsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (GroupParamsType)XmlBeans.getContextTypeLoader().parse(xis, GroupParamsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, GroupParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, GroupParamsType.type, options);
      }

      private Factory() {
      }
   }
}
