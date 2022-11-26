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

public interface FormLoginConfigType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(FormLoginConfigType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("formloginconfigtype230etype");

   WarPathType getFormLoginPage();

   void setFormLoginPage(WarPathType var1);

   WarPathType addNewFormLoginPage();

   WarPathType getFormErrorPage();

   void setFormErrorPage(WarPathType var1);

   WarPathType addNewFormErrorPage();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static FormLoginConfigType newInstance() {
         return (FormLoginConfigType)XmlBeans.getContextTypeLoader().newInstance(FormLoginConfigType.type, (XmlOptions)null);
      }

      public static FormLoginConfigType newInstance(XmlOptions options) {
         return (FormLoginConfigType)XmlBeans.getContextTypeLoader().newInstance(FormLoginConfigType.type, options);
      }

      public static FormLoginConfigType parse(java.lang.String xmlAsString) throws XmlException {
         return (FormLoginConfigType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FormLoginConfigType.type, (XmlOptions)null);
      }

      public static FormLoginConfigType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (FormLoginConfigType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FormLoginConfigType.type, options);
      }

      public static FormLoginConfigType parse(File file) throws XmlException, IOException {
         return (FormLoginConfigType)XmlBeans.getContextTypeLoader().parse(file, FormLoginConfigType.type, (XmlOptions)null);
      }

      public static FormLoginConfigType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (FormLoginConfigType)XmlBeans.getContextTypeLoader().parse(file, FormLoginConfigType.type, options);
      }

      public static FormLoginConfigType parse(URL u) throws XmlException, IOException {
         return (FormLoginConfigType)XmlBeans.getContextTypeLoader().parse(u, FormLoginConfigType.type, (XmlOptions)null);
      }

      public static FormLoginConfigType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (FormLoginConfigType)XmlBeans.getContextTypeLoader().parse(u, FormLoginConfigType.type, options);
      }

      public static FormLoginConfigType parse(InputStream is) throws XmlException, IOException {
         return (FormLoginConfigType)XmlBeans.getContextTypeLoader().parse(is, FormLoginConfigType.type, (XmlOptions)null);
      }

      public static FormLoginConfigType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (FormLoginConfigType)XmlBeans.getContextTypeLoader().parse(is, FormLoginConfigType.type, options);
      }

      public static FormLoginConfigType parse(Reader r) throws XmlException, IOException {
         return (FormLoginConfigType)XmlBeans.getContextTypeLoader().parse(r, FormLoginConfigType.type, (XmlOptions)null);
      }

      public static FormLoginConfigType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (FormLoginConfigType)XmlBeans.getContextTypeLoader().parse(r, FormLoginConfigType.type, options);
      }

      public static FormLoginConfigType parse(XMLStreamReader sr) throws XmlException {
         return (FormLoginConfigType)XmlBeans.getContextTypeLoader().parse(sr, FormLoginConfigType.type, (XmlOptions)null);
      }

      public static FormLoginConfigType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (FormLoginConfigType)XmlBeans.getContextTypeLoader().parse(sr, FormLoginConfigType.type, options);
      }

      public static FormLoginConfigType parse(Node node) throws XmlException {
         return (FormLoginConfigType)XmlBeans.getContextTypeLoader().parse(node, FormLoginConfigType.type, (XmlOptions)null);
      }

      public static FormLoginConfigType parse(Node node, XmlOptions options) throws XmlException {
         return (FormLoginConfigType)XmlBeans.getContextTypeLoader().parse(node, FormLoginConfigType.type, options);
      }

      /** @deprecated */
      public static FormLoginConfigType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (FormLoginConfigType)XmlBeans.getContextTypeLoader().parse(xis, FormLoginConfigType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static FormLoginConfigType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (FormLoginConfigType)XmlBeans.getContextTypeLoader().parse(xis, FormLoginConfigType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FormLoginConfigType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FormLoginConfigType.type, options);
      }

      private Factory() {
      }
   }
}
