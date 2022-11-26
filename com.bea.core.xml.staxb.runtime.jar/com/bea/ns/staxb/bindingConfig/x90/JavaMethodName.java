package com.bea.ns.staxb.bindingConfig.x90;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlToken;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface JavaMethodName extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JavaMethodName.class.getClassLoader(), "schemacom_bea_xml.system.bea_staxb_runtime").resolveHandle("javamethodname88e9type");

   String getMethodName();

   XmlToken xgetMethodName();

   void setMethodName(String var1);

   void xsetMethodName(XmlToken var1);

   String[] getParamTypeArray();

   String getParamTypeArray(int var1);

   JavaClassName[] xgetParamTypeArray();

   JavaClassName xgetParamTypeArray(int var1);

   int sizeOfParamTypeArray();

   void setParamTypeArray(String[] var1);

   void setParamTypeArray(int var1, String var2);

   void xsetParamTypeArray(JavaClassName[] var1);

   void xsetParamTypeArray(int var1, JavaClassName var2);

   void insertParamType(int var1, String var2);

   void addParamType(String var1);

   JavaClassName insertNewParamType(int var1);

   JavaClassName addNewParamType();

   void removeParamType(int var1);

   public static final class Factory {
      public static JavaMethodName newInstance() {
         return (JavaMethodName)XmlBeans.getContextTypeLoader().newInstance(JavaMethodName.type, (XmlOptions)null);
      }

      public static JavaMethodName newInstance(XmlOptions options) {
         return (JavaMethodName)XmlBeans.getContextTypeLoader().newInstance(JavaMethodName.type, options);
      }

      public static JavaMethodName parse(String xmlAsString) throws XmlException {
         return (JavaMethodName)XmlBeans.getContextTypeLoader().parse(xmlAsString, JavaMethodName.type, (XmlOptions)null);
      }

      public static JavaMethodName parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (JavaMethodName)XmlBeans.getContextTypeLoader().parse(xmlAsString, JavaMethodName.type, options);
      }

      public static JavaMethodName parse(File file) throws XmlException, IOException {
         return (JavaMethodName)XmlBeans.getContextTypeLoader().parse(file, JavaMethodName.type, (XmlOptions)null);
      }

      public static JavaMethodName parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JavaMethodName)XmlBeans.getContextTypeLoader().parse(file, JavaMethodName.type, options);
      }

      public static JavaMethodName parse(URL u) throws XmlException, IOException {
         return (JavaMethodName)XmlBeans.getContextTypeLoader().parse(u, JavaMethodName.type, (XmlOptions)null);
      }

      public static JavaMethodName parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JavaMethodName)XmlBeans.getContextTypeLoader().parse(u, JavaMethodName.type, options);
      }

      public static JavaMethodName parse(InputStream is) throws XmlException, IOException {
         return (JavaMethodName)XmlBeans.getContextTypeLoader().parse(is, JavaMethodName.type, (XmlOptions)null);
      }

      public static JavaMethodName parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JavaMethodName)XmlBeans.getContextTypeLoader().parse(is, JavaMethodName.type, options);
      }

      public static JavaMethodName parse(Reader r) throws XmlException, IOException {
         return (JavaMethodName)XmlBeans.getContextTypeLoader().parse(r, JavaMethodName.type, (XmlOptions)null);
      }

      public static JavaMethodName parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JavaMethodName)XmlBeans.getContextTypeLoader().parse(r, JavaMethodName.type, options);
      }

      public static JavaMethodName parse(XMLStreamReader sr) throws XmlException {
         return (JavaMethodName)XmlBeans.getContextTypeLoader().parse(sr, JavaMethodName.type, (XmlOptions)null);
      }

      public static JavaMethodName parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JavaMethodName)XmlBeans.getContextTypeLoader().parse(sr, JavaMethodName.type, options);
      }

      public static JavaMethodName parse(Node node) throws XmlException {
         return (JavaMethodName)XmlBeans.getContextTypeLoader().parse(node, JavaMethodName.type, (XmlOptions)null);
      }

      public static JavaMethodName parse(Node node, XmlOptions options) throws XmlException {
         return (JavaMethodName)XmlBeans.getContextTypeLoader().parse(node, JavaMethodName.type, options);
      }

      /** @deprecated */
      public static JavaMethodName parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JavaMethodName)XmlBeans.getContextTypeLoader().parse(xis, JavaMethodName.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JavaMethodName parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JavaMethodName)XmlBeans.getContextTypeLoader().parse(xis, JavaMethodName.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JavaMethodName.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JavaMethodName.type, options);
      }

      private Factory() {
      }
   }
}
