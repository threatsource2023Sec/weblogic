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

public interface InitMethodType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(InitMethodType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("initmethodtype673ftype");

   NamedMethodType getCreateMethod();

   void setCreateMethod(NamedMethodType var1);

   NamedMethodType addNewCreateMethod();

   NamedMethodType getBeanMethod();

   void setBeanMethod(NamedMethodType var1);

   NamedMethodType addNewBeanMethod();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static InitMethodType newInstance() {
         return (InitMethodType)XmlBeans.getContextTypeLoader().newInstance(InitMethodType.type, (XmlOptions)null);
      }

      public static InitMethodType newInstance(XmlOptions options) {
         return (InitMethodType)XmlBeans.getContextTypeLoader().newInstance(InitMethodType.type, options);
      }

      public static InitMethodType parse(java.lang.String xmlAsString) throws XmlException {
         return (InitMethodType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InitMethodType.type, (XmlOptions)null);
      }

      public static InitMethodType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (InitMethodType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InitMethodType.type, options);
      }

      public static InitMethodType parse(File file) throws XmlException, IOException {
         return (InitMethodType)XmlBeans.getContextTypeLoader().parse(file, InitMethodType.type, (XmlOptions)null);
      }

      public static InitMethodType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (InitMethodType)XmlBeans.getContextTypeLoader().parse(file, InitMethodType.type, options);
      }

      public static InitMethodType parse(URL u) throws XmlException, IOException {
         return (InitMethodType)XmlBeans.getContextTypeLoader().parse(u, InitMethodType.type, (XmlOptions)null);
      }

      public static InitMethodType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (InitMethodType)XmlBeans.getContextTypeLoader().parse(u, InitMethodType.type, options);
      }

      public static InitMethodType parse(InputStream is) throws XmlException, IOException {
         return (InitMethodType)XmlBeans.getContextTypeLoader().parse(is, InitMethodType.type, (XmlOptions)null);
      }

      public static InitMethodType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (InitMethodType)XmlBeans.getContextTypeLoader().parse(is, InitMethodType.type, options);
      }

      public static InitMethodType parse(Reader r) throws XmlException, IOException {
         return (InitMethodType)XmlBeans.getContextTypeLoader().parse(r, InitMethodType.type, (XmlOptions)null);
      }

      public static InitMethodType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (InitMethodType)XmlBeans.getContextTypeLoader().parse(r, InitMethodType.type, options);
      }

      public static InitMethodType parse(XMLStreamReader sr) throws XmlException {
         return (InitMethodType)XmlBeans.getContextTypeLoader().parse(sr, InitMethodType.type, (XmlOptions)null);
      }

      public static InitMethodType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (InitMethodType)XmlBeans.getContextTypeLoader().parse(sr, InitMethodType.type, options);
      }

      public static InitMethodType parse(Node node) throws XmlException {
         return (InitMethodType)XmlBeans.getContextTypeLoader().parse(node, InitMethodType.type, (XmlOptions)null);
      }

      public static InitMethodType parse(Node node, XmlOptions options) throws XmlException {
         return (InitMethodType)XmlBeans.getContextTypeLoader().parse(node, InitMethodType.type, options);
      }

      /** @deprecated */
      public static InitMethodType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (InitMethodType)XmlBeans.getContextTypeLoader().parse(xis, InitMethodType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static InitMethodType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (InitMethodType)XmlBeans.getContextTypeLoader().parse(xis, InitMethodType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InitMethodType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InitMethodType.type, options);
      }

      private Factory() {
      }
   }
}
