package com.oracle.xmlns.weblogic.weblogicApplication;

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

public interface ApplicationParamType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ApplicationParamType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("applicationparamtype43f9type");

   String getDescription();

   XmlString xgetDescription();

   boolean isSetDescription();

   void setDescription(String var1);

   void xsetDescription(XmlString var1);

   void unsetDescription();

   String getParamName();

   XmlString xgetParamName();

   void setParamName(String var1);

   void xsetParamName(XmlString var1);

   String getParamValue();

   XmlString xgetParamValue();

   void setParamValue(String var1);

   void xsetParamValue(XmlString var1);

   public static final class Factory {
      public static ApplicationParamType newInstance() {
         return (ApplicationParamType)XmlBeans.getContextTypeLoader().newInstance(ApplicationParamType.type, (XmlOptions)null);
      }

      public static ApplicationParamType newInstance(XmlOptions options) {
         return (ApplicationParamType)XmlBeans.getContextTypeLoader().newInstance(ApplicationParamType.type, options);
      }

      public static ApplicationParamType parse(String xmlAsString) throws XmlException {
         return (ApplicationParamType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ApplicationParamType.type, (XmlOptions)null);
      }

      public static ApplicationParamType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ApplicationParamType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ApplicationParamType.type, options);
      }

      public static ApplicationParamType parse(File file) throws XmlException, IOException {
         return (ApplicationParamType)XmlBeans.getContextTypeLoader().parse(file, ApplicationParamType.type, (XmlOptions)null);
      }

      public static ApplicationParamType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ApplicationParamType)XmlBeans.getContextTypeLoader().parse(file, ApplicationParamType.type, options);
      }

      public static ApplicationParamType parse(URL u) throws XmlException, IOException {
         return (ApplicationParamType)XmlBeans.getContextTypeLoader().parse(u, ApplicationParamType.type, (XmlOptions)null);
      }

      public static ApplicationParamType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ApplicationParamType)XmlBeans.getContextTypeLoader().parse(u, ApplicationParamType.type, options);
      }

      public static ApplicationParamType parse(InputStream is) throws XmlException, IOException {
         return (ApplicationParamType)XmlBeans.getContextTypeLoader().parse(is, ApplicationParamType.type, (XmlOptions)null);
      }

      public static ApplicationParamType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ApplicationParamType)XmlBeans.getContextTypeLoader().parse(is, ApplicationParamType.type, options);
      }

      public static ApplicationParamType parse(Reader r) throws XmlException, IOException {
         return (ApplicationParamType)XmlBeans.getContextTypeLoader().parse(r, ApplicationParamType.type, (XmlOptions)null);
      }

      public static ApplicationParamType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ApplicationParamType)XmlBeans.getContextTypeLoader().parse(r, ApplicationParamType.type, options);
      }

      public static ApplicationParamType parse(XMLStreamReader sr) throws XmlException {
         return (ApplicationParamType)XmlBeans.getContextTypeLoader().parse(sr, ApplicationParamType.type, (XmlOptions)null);
      }

      public static ApplicationParamType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ApplicationParamType)XmlBeans.getContextTypeLoader().parse(sr, ApplicationParamType.type, options);
      }

      public static ApplicationParamType parse(Node node) throws XmlException {
         return (ApplicationParamType)XmlBeans.getContextTypeLoader().parse(node, ApplicationParamType.type, (XmlOptions)null);
      }

      public static ApplicationParamType parse(Node node, XmlOptions options) throws XmlException {
         return (ApplicationParamType)XmlBeans.getContextTypeLoader().parse(node, ApplicationParamType.type, options);
      }

      /** @deprecated */
      public static ApplicationParamType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ApplicationParamType)XmlBeans.getContextTypeLoader().parse(xis, ApplicationParamType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ApplicationParamType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ApplicationParamType)XmlBeans.getContextTypeLoader().parse(xis, ApplicationParamType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ApplicationParamType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ApplicationParamType.type, options);
      }

      private Factory() {
      }
   }
}
