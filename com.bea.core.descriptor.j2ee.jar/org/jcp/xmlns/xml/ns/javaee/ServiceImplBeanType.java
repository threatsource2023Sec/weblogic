package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface ServiceImplBeanType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ServiceImplBeanType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("serviceimplbeantype70b8type");

   EjbLinkType getEjbLink();

   boolean isSetEjbLink();

   void setEjbLink(EjbLinkType var1);

   EjbLinkType addNewEjbLink();

   void unsetEjbLink();

   ServletLinkType getServletLink();

   boolean isSetServletLink();

   void setServletLink(ServletLinkType var1);

   ServletLinkType addNewServletLink();

   void unsetServletLink();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ServiceImplBeanType newInstance() {
         return (ServiceImplBeanType)XmlBeans.getContextTypeLoader().newInstance(ServiceImplBeanType.type, (XmlOptions)null);
      }

      public static ServiceImplBeanType newInstance(XmlOptions options) {
         return (ServiceImplBeanType)XmlBeans.getContextTypeLoader().newInstance(ServiceImplBeanType.type, options);
      }

      public static ServiceImplBeanType parse(java.lang.String xmlAsString) throws XmlException {
         return (ServiceImplBeanType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ServiceImplBeanType.type, (XmlOptions)null);
      }

      public static ServiceImplBeanType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ServiceImplBeanType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ServiceImplBeanType.type, options);
      }

      public static ServiceImplBeanType parse(File file) throws XmlException, IOException {
         return (ServiceImplBeanType)XmlBeans.getContextTypeLoader().parse(file, ServiceImplBeanType.type, (XmlOptions)null);
      }

      public static ServiceImplBeanType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ServiceImplBeanType)XmlBeans.getContextTypeLoader().parse(file, ServiceImplBeanType.type, options);
      }

      public static ServiceImplBeanType parse(URL u) throws XmlException, IOException {
         return (ServiceImplBeanType)XmlBeans.getContextTypeLoader().parse(u, ServiceImplBeanType.type, (XmlOptions)null);
      }

      public static ServiceImplBeanType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ServiceImplBeanType)XmlBeans.getContextTypeLoader().parse(u, ServiceImplBeanType.type, options);
      }

      public static ServiceImplBeanType parse(InputStream is) throws XmlException, IOException {
         return (ServiceImplBeanType)XmlBeans.getContextTypeLoader().parse(is, ServiceImplBeanType.type, (XmlOptions)null);
      }

      public static ServiceImplBeanType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ServiceImplBeanType)XmlBeans.getContextTypeLoader().parse(is, ServiceImplBeanType.type, options);
      }

      public static ServiceImplBeanType parse(Reader r) throws XmlException, IOException {
         return (ServiceImplBeanType)XmlBeans.getContextTypeLoader().parse(r, ServiceImplBeanType.type, (XmlOptions)null);
      }

      public static ServiceImplBeanType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ServiceImplBeanType)XmlBeans.getContextTypeLoader().parse(r, ServiceImplBeanType.type, options);
      }

      public static ServiceImplBeanType parse(XMLStreamReader sr) throws XmlException {
         return (ServiceImplBeanType)XmlBeans.getContextTypeLoader().parse(sr, ServiceImplBeanType.type, (XmlOptions)null);
      }

      public static ServiceImplBeanType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ServiceImplBeanType)XmlBeans.getContextTypeLoader().parse(sr, ServiceImplBeanType.type, options);
      }

      public static ServiceImplBeanType parse(Node node) throws XmlException {
         return (ServiceImplBeanType)XmlBeans.getContextTypeLoader().parse(node, ServiceImplBeanType.type, (XmlOptions)null);
      }

      public static ServiceImplBeanType parse(Node node, XmlOptions options) throws XmlException {
         return (ServiceImplBeanType)XmlBeans.getContextTypeLoader().parse(node, ServiceImplBeanType.type, options);
      }

      /** @deprecated */
      public static ServiceImplBeanType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ServiceImplBeanType)XmlBeans.getContextTypeLoader().parse(xis, ServiceImplBeanType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ServiceImplBeanType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ServiceImplBeanType)XmlBeans.getContextTypeLoader().parse(xis, ServiceImplBeanType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ServiceImplBeanType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ServiceImplBeanType.type, options);
      }

      private Factory() {
      }
   }
}
