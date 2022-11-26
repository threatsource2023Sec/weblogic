package com.oracle.xmlns.weblogic.weblogicEjbJar;

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

public interface SkipStateReplicationMethodsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SkipStateReplicationMethodsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("skipstatereplicationmethodstype09ddtype");

   MethodType[] getMethodArray();

   MethodType getMethodArray(int var1);

   int sizeOfMethodArray();

   void setMethodArray(MethodType[] var1);

   void setMethodArray(int var1, MethodType var2);

   MethodType insertNewMethod(int var1);

   MethodType addNewMethod();

   void removeMethod(int var1);

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static SkipStateReplicationMethodsType newInstance() {
         return (SkipStateReplicationMethodsType)XmlBeans.getContextTypeLoader().newInstance(SkipStateReplicationMethodsType.type, (XmlOptions)null);
      }

      public static SkipStateReplicationMethodsType newInstance(XmlOptions options) {
         return (SkipStateReplicationMethodsType)XmlBeans.getContextTypeLoader().newInstance(SkipStateReplicationMethodsType.type, options);
      }

      public static SkipStateReplicationMethodsType parse(String xmlAsString) throws XmlException {
         return (SkipStateReplicationMethodsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SkipStateReplicationMethodsType.type, (XmlOptions)null);
      }

      public static SkipStateReplicationMethodsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SkipStateReplicationMethodsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SkipStateReplicationMethodsType.type, options);
      }

      public static SkipStateReplicationMethodsType parse(File file) throws XmlException, IOException {
         return (SkipStateReplicationMethodsType)XmlBeans.getContextTypeLoader().parse(file, SkipStateReplicationMethodsType.type, (XmlOptions)null);
      }

      public static SkipStateReplicationMethodsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SkipStateReplicationMethodsType)XmlBeans.getContextTypeLoader().parse(file, SkipStateReplicationMethodsType.type, options);
      }

      public static SkipStateReplicationMethodsType parse(URL u) throws XmlException, IOException {
         return (SkipStateReplicationMethodsType)XmlBeans.getContextTypeLoader().parse(u, SkipStateReplicationMethodsType.type, (XmlOptions)null);
      }

      public static SkipStateReplicationMethodsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SkipStateReplicationMethodsType)XmlBeans.getContextTypeLoader().parse(u, SkipStateReplicationMethodsType.type, options);
      }

      public static SkipStateReplicationMethodsType parse(InputStream is) throws XmlException, IOException {
         return (SkipStateReplicationMethodsType)XmlBeans.getContextTypeLoader().parse(is, SkipStateReplicationMethodsType.type, (XmlOptions)null);
      }

      public static SkipStateReplicationMethodsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SkipStateReplicationMethodsType)XmlBeans.getContextTypeLoader().parse(is, SkipStateReplicationMethodsType.type, options);
      }

      public static SkipStateReplicationMethodsType parse(Reader r) throws XmlException, IOException {
         return (SkipStateReplicationMethodsType)XmlBeans.getContextTypeLoader().parse(r, SkipStateReplicationMethodsType.type, (XmlOptions)null);
      }

      public static SkipStateReplicationMethodsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SkipStateReplicationMethodsType)XmlBeans.getContextTypeLoader().parse(r, SkipStateReplicationMethodsType.type, options);
      }

      public static SkipStateReplicationMethodsType parse(XMLStreamReader sr) throws XmlException {
         return (SkipStateReplicationMethodsType)XmlBeans.getContextTypeLoader().parse(sr, SkipStateReplicationMethodsType.type, (XmlOptions)null);
      }

      public static SkipStateReplicationMethodsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SkipStateReplicationMethodsType)XmlBeans.getContextTypeLoader().parse(sr, SkipStateReplicationMethodsType.type, options);
      }

      public static SkipStateReplicationMethodsType parse(Node node) throws XmlException {
         return (SkipStateReplicationMethodsType)XmlBeans.getContextTypeLoader().parse(node, SkipStateReplicationMethodsType.type, (XmlOptions)null);
      }

      public static SkipStateReplicationMethodsType parse(Node node, XmlOptions options) throws XmlException {
         return (SkipStateReplicationMethodsType)XmlBeans.getContextTypeLoader().parse(node, SkipStateReplicationMethodsType.type, options);
      }

      /** @deprecated */
      public static SkipStateReplicationMethodsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SkipStateReplicationMethodsType)XmlBeans.getContextTypeLoader().parse(xis, SkipStateReplicationMethodsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SkipStateReplicationMethodsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SkipStateReplicationMethodsType)XmlBeans.getContextTypeLoader().parse(xis, SkipStateReplicationMethodsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SkipStateReplicationMethodsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SkipStateReplicationMethodsType.type, options);
      }

      private Factory() {
      }
   }
}
