package com.sun.java.xml.ns.javaee;

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

public interface ActivationConfigPropertyType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ActivationConfigPropertyType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("activationconfigpropertytype64f6type");

   XsdStringType getActivationConfigPropertyName();

   void setActivationConfigPropertyName(XsdStringType var1);

   XsdStringType addNewActivationConfigPropertyName();

   XsdStringType getActivationConfigPropertyValue();

   void setActivationConfigPropertyValue(XsdStringType var1);

   XsdStringType addNewActivationConfigPropertyValue();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ActivationConfigPropertyType newInstance() {
         return (ActivationConfigPropertyType)XmlBeans.getContextTypeLoader().newInstance(ActivationConfigPropertyType.type, (XmlOptions)null);
      }

      public static ActivationConfigPropertyType newInstance(XmlOptions options) {
         return (ActivationConfigPropertyType)XmlBeans.getContextTypeLoader().newInstance(ActivationConfigPropertyType.type, options);
      }

      public static ActivationConfigPropertyType parse(java.lang.String xmlAsString) throws XmlException {
         return (ActivationConfigPropertyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ActivationConfigPropertyType.type, (XmlOptions)null);
      }

      public static ActivationConfigPropertyType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ActivationConfigPropertyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ActivationConfigPropertyType.type, options);
      }

      public static ActivationConfigPropertyType parse(File file) throws XmlException, IOException {
         return (ActivationConfigPropertyType)XmlBeans.getContextTypeLoader().parse(file, ActivationConfigPropertyType.type, (XmlOptions)null);
      }

      public static ActivationConfigPropertyType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ActivationConfigPropertyType)XmlBeans.getContextTypeLoader().parse(file, ActivationConfigPropertyType.type, options);
      }

      public static ActivationConfigPropertyType parse(URL u) throws XmlException, IOException {
         return (ActivationConfigPropertyType)XmlBeans.getContextTypeLoader().parse(u, ActivationConfigPropertyType.type, (XmlOptions)null);
      }

      public static ActivationConfigPropertyType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ActivationConfigPropertyType)XmlBeans.getContextTypeLoader().parse(u, ActivationConfigPropertyType.type, options);
      }

      public static ActivationConfigPropertyType parse(InputStream is) throws XmlException, IOException {
         return (ActivationConfigPropertyType)XmlBeans.getContextTypeLoader().parse(is, ActivationConfigPropertyType.type, (XmlOptions)null);
      }

      public static ActivationConfigPropertyType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ActivationConfigPropertyType)XmlBeans.getContextTypeLoader().parse(is, ActivationConfigPropertyType.type, options);
      }

      public static ActivationConfigPropertyType parse(Reader r) throws XmlException, IOException {
         return (ActivationConfigPropertyType)XmlBeans.getContextTypeLoader().parse(r, ActivationConfigPropertyType.type, (XmlOptions)null);
      }

      public static ActivationConfigPropertyType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ActivationConfigPropertyType)XmlBeans.getContextTypeLoader().parse(r, ActivationConfigPropertyType.type, options);
      }

      public static ActivationConfigPropertyType parse(XMLStreamReader sr) throws XmlException {
         return (ActivationConfigPropertyType)XmlBeans.getContextTypeLoader().parse(sr, ActivationConfigPropertyType.type, (XmlOptions)null);
      }

      public static ActivationConfigPropertyType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ActivationConfigPropertyType)XmlBeans.getContextTypeLoader().parse(sr, ActivationConfigPropertyType.type, options);
      }

      public static ActivationConfigPropertyType parse(Node node) throws XmlException {
         return (ActivationConfigPropertyType)XmlBeans.getContextTypeLoader().parse(node, ActivationConfigPropertyType.type, (XmlOptions)null);
      }

      public static ActivationConfigPropertyType parse(Node node, XmlOptions options) throws XmlException {
         return (ActivationConfigPropertyType)XmlBeans.getContextTypeLoader().parse(node, ActivationConfigPropertyType.type, options);
      }

      /** @deprecated */
      public static ActivationConfigPropertyType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ActivationConfigPropertyType)XmlBeans.getContextTypeLoader().parse(xis, ActivationConfigPropertyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ActivationConfigPropertyType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ActivationConfigPropertyType)XmlBeans.getContextTypeLoader().parse(xis, ActivationConfigPropertyType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ActivationConfigPropertyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ActivationConfigPropertyType.type, options);
      }

      private Factory() {
      }
   }
}
