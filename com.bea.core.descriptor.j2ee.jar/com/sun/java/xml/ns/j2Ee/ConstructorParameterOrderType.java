package com.sun.java.xml.ns.j2Ee;

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

public interface ConstructorParameterOrderType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConstructorParameterOrderType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("constructorparameterordertype2b38type");

   String[] getElementNameArray();

   String getElementNameArray(int var1);

   int sizeOfElementNameArray();

   void setElementNameArray(String[] var1);

   void setElementNameArray(int var1, String var2);

   String insertNewElementName(int var1);

   String addNewElementName();

   void removeElementName(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ConstructorParameterOrderType newInstance() {
         return (ConstructorParameterOrderType)XmlBeans.getContextTypeLoader().newInstance(ConstructorParameterOrderType.type, (XmlOptions)null);
      }

      public static ConstructorParameterOrderType newInstance(XmlOptions options) {
         return (ConstructorParameterOrderType)XmlBeans.getContextTypeLoader().newInstance(ConstructorParameterOrderType.type, options);
      }

      public static ConstructorParameterOrderType parse(java.lang.String xmlAsString) throws XmlException {
         return (ConstructorParameterOrderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConstructorParameterOrderType.type, (XmlOptions)null);
      }

      public static ConstructorParameterOrderType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ConstructorParameterOrderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConstructorParameterOrderType.type, options);
      }

      public static ConstructorParameterOrderType parse(File file) throws XmlException, IOException {
         return (ConstructorParameterOrderType)XmlBeans.getContextTypeLoader().parse(file, ConstructorParameterOrderType.type, (XmlOptions)null);
      }

      public static ConstructorParameterOrderType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConstructorParameterOrderType)XmlBeans.getContextTypeLoader().parse(file, ConstructorParameterOrderType.type, options);
      }

      public static ConstructorParameterOrderType parse(URL u) throws XmlException, IOException {
         return (ConstructorParameterOrderType)XmlBeans.getContextTypeLoader().parse(u, ConstructorParameterOrderType.type, (XmlOptions)null);
      }

      public static ConstructorParameterOrderType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConstructorParameterOrderType)XmlBeans.getContextTypeLoader().parse(u, ConstructorParameterOrderType.type, options);
      }

      public static ConstructorParameterOrderType parse(InputStream is) throws XmlException, IOException {
         return (ConstructorParameterOrderType)XmlBeans.getContextTypeLoader().parse(is, ConstructorParameterOrderType.type, (XmlOptions)null);
      }

      public static ConstructorParameterOrderType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConstructorParameterOrderType)XmlBeans.getContextTypeLoader().parse(is, ConstructorParameterOrderType.type, options);
      }

      public static ConstructorParameterOrderType parse(Reader r) throws XmlException, IOException {
         return (ConstructorParameterOrderType)XmlBeans.getContextTypeLoader().parse(r, ConstructorParameterOrderType.type, (XmlOptions)null);
      }

      public static ConstructorParameterOrderType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConstructorParameterOrderType)XmlBeans.getContextTypeLoader().parse(r, ConstructorParameterOrderType.type, options);
      }

      public static ConstructorParameterOrderType parse(XMLStreamReader sr) throws XmlException {
         return (ConstructorParameterOrderType)XmlBeans.getContextTypeLoader().parse(sr, ConstructorParameterOrderType.type, (XmlOptions)null);
      }

      public static ConstructorParameterOrderType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConstructorParameterOrderType)XmlBeans.getContextTypeLoader().parse(sr, ConstructorParameterOrderType.type, options);
      }

      public static ConstructorParameterOrderType parse(Node node) throws XmlException {
         return (ConstructorParameterOrderType)XmlBeans.getContextTypeLoader().parse(node, ConstructorParameterOrderType.type, (XmlOptions)null);
      }

      public static ConstructorParameterOrderType parse(Node node, XmlOptions options) throws XmlException {
         return (ConstructorParameterOrderType)XmlBeans.getContextTypeLoader().parse(node, ConstructorParameterOrderType.type, options);
      }

      /** @deprecated */
      public static ConstructorParameterOrderType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConstructorParameterOrderType)XmlBeans.getContextTypeLoader().parse(xis, ConstructorParameterOrderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConstructorParameterOrderType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConstructorParameterOrderType)XmlBeans.getContextTypeLoader().parse(xis, ConstructorParameterOrderType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConstructorParameterOrderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConstructorParameterOrderType.type, options);
      }

      private Factory() {
      }
   }
}
