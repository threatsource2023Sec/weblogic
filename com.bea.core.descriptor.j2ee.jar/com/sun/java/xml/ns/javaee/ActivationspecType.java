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

public interface ActivationspecType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ActivationspecType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("activationspectype54fetype");

   FullyQualifiedClassType getActivationspecClass();

   void setActivationspecClass(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewActivationspecClass();

   RequiredConfigPropertyType[] getRequiredConfigPropertyArray();

   RequiredConfigPropertyType getRequiredConfigPropertyArray(int var1);

   int sizeOfRequiredConfigPropertyArray();

   void setRequiredConfigPropertyArray(RequiredConfigPropertyType[] var1);

   void setRequiredConfigPropertyArray(int var1, RequiredConfigPropertyType var2);

   RequiredConfigPropertyType insertNewRequiredConfigProperty(int var1);

   RequiredConfigPropertyType addNewRequiredConfigProperty();

   void removeRequiredConfigProperty(int var1);

   ConfigPropertyType[] getConfigPropertyArray();

   ConfigPropertyType getConfigPropertyArray(int var1);

   int sizeOfConfigPropertyArray();

   void setConfigPropertyArray(ConfigPropertyType[] var1);

   void setConfigPropertyArray(int var1, ConfigPropertyType var2);

   ConfigPropertyType insertNewConfigProperty(int var1);

   ConfigPropertyType addNewConfigProperty();

   void removeConfigProperty(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ActivationspecType newInstance() {
         return (ActivationspecType)XmlBeans.getContextTypeLoader().newInstance(ActivationspecType.type, (XmlOptions)null);
      }

      public static ActivationspecType newInstance(XmlOptions options) {
         return (ActivationspecType)XmlBeans.getContextTypeLoader().newInstance(ActivationspecType.type, options);
      }

      public static ActivationspecType parse(java.lang.String xmlAsString) throws XmlException {
         return (ActivationspecType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ActivationspecType.type, (XmlOptions)null);
      }

      public static ActivationspecType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ActivationspecType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ActivationspecType.type, options);
      }

      public static ActivationspecType parse(File file) throws XmlException, IOException {
         return (ActivationspecType)XmlBeans.getContextTypeLoader().parse(file, ActivationspecType.type, (XmlOptions)null);
      }

      public static ActivationspecType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ActivationspecType)XmlBeans.getContextTypeLoader().parse(file, ActivationspecType.type, options);
      }

      public static ActivationspecType parse(URL u) throws XmlException, IOException {
         return (ActivationspecType)XmlBeans.getContextTypeLoader().parse(u, ActivationspecType.type, (XmlOptions)null);
      }

      public static ActivationspecType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ActivationspecType)XmlBeans.getContextTypeLoader().parse(u, ActivationspecType.type, options);
      }

      public static ActivationspecType parse(InputStream is) throws XmlException, IOException {
         return (ActivationspecType)XmlBeans.getContextTypeLoader().parse(is, ActivationspecType.type, (XmlOptions)null);
      }

      public static ActivationspecType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ActivationspecType)XmlBeans.getContextTypeLoader().parse(is, ActivationspecType.type, options);
      }

      public static ActivationspecType parse(Reader r) throws XmlException, IOException {
         return (ActivationspecType)XmlBeans.getContextTypeLoader().parse(r, ActivationspecType.type, (XmlOptions)null);
      }

      public static ActivationspecType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ActivationspecType)XmlBeans.getContextTypeLoader().parse(r, ActivationspecType.type, options);
      }

      public static ActivationspecType parse(XMLStreamReader sr) throws XmlException {
         return (ActivationspecType)XmlBeans.getContextTypeLoader().parse(sr, ActivationspecType.type, (XmlOptions)null);
      }

      public static ActivationspecType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ActivationspecType)XmlBeans.getContextTypeLoader().parse(sr, ActivationspecType.type, options);
      }

      public static ActivationspecType parse(Node node) throws XmlException {
         return (ActivationspecType)XmlBeans.getContextTypeLoader().parse(node, ActivationspecType.type, (XmlOptions)null);
      }

      public static ActivationspecType parse(Node node, XmlOptions options) throws XmlException {
         return (ActivationspecType)XmlBeans.getContextTypeLoader().parse(node, ActivationspecType.type, options);
      }

      /** @deprecated */
      public static ActivationspecType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ActivationspecType)XmlBeans.getContextTypeLoader().parse(xis, ActivationspecType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ActivationspecType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ActivationspecType)XmlBeans.getContextTypeLoader().parse(xis, ActivationspecType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ActivationspecType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ActivationspecType.type, options);
      }

      private Factory() {
      }
   }
}
