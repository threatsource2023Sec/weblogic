package com.oracle.xmlns.weblogic.weblogicWebApp;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface ContextRootType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ContextRootType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("contextroottype4a73type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ContextRootType newInstance() {
         return (ContextRootType)XmlBeans.getContextTypeLoader().newInstance(ContextRootType.type, (XmlOptions)null);
      }

      public static ContextRootType newInstance(XmlOptions options) {
         return (ContextRootType)XmlBeans.getContextTypeLoader().newInstance(ContextRootType.type, options);
      }

      public static ContextRootType parse(String xmlAsString) throws XmlException {
         return (ContextRootType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ContextRootType.type, (XmlOptions)null);
      }

      public static ContextRootType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ContextRootType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ContextRootType.type, options);
      }

      public static ContextRootType parse(File file) throws XmlException, IOException {
         return (ContextRootType)XmlBeans.getContextTypeLoader().parse(file, ContextRootType.type, (XmlOptions)null);
      }

      public static ContextRootType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ContextRootType)XmlBeans.getContextTypeLoader().parse(file, ContextRootType.type, options);
      }

      public static ContextRootType parse(URL u) throws XmlException, IOException {
         return (ContextRootType)XmlBeans.getContextTypeLoader().parse(u, ContextRootType.type, (XmlOptions)null);
      }

      public static ContextRootType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ContextRootType)XmlBeans.getContextTypeLoader().parse(u, ContextRootType.type, options);
      }

      public static ContextRootType parse(InputStream is) throws XmlException, IOException {
         return (ContextRootType)XmlBeans.getContextTypeLoader().parse(is, ContextRootType.type, (XmlOptions)null);
      }

      public static ContextRootType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ContextRootType)XmlBeans.getContextTypeLoader().parse(is, ContextRootType.type, options);
      }

      public static ContextRootType parse(Reader r) throws XmlException, IOException {
         return (ContextRootType)XmlBeans.getContextTypeLoader().parse(r, ContextRootType.type, (XmlOptions)null);
      }

      public static ContextRootType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ContextRootType)XmlBeans.getContextTypeLoader().parse(r, ContextRootType.type, options);
      }

      public static ContextRootType parse(XMLStreamReader sr) throws XmlException {
         return (ContextRootType)XmlBeans.getContextTypeLoader().parse(sr, ContextRootType.type, (XmlOptions)null);
      }

      public static ContextRootType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ContextRootType)XmlBeans.getContextTypeLoader().parse(sr, ContextRootType.type, options);
      }

      public static ContextRootType parse(Node node) throws XmlException {
         return (ContextRootType)XmlBeans.getContextTypeLoader().parse(node, ContextRootType.type, (XmlOptions)null);
      }

      public static ContextRootType parse(Node node, XmlOptions options) throws XmlException {
         return (ContextRootType)XmlBeans.getContextTypeLoader().parse(node, ContextRootType.type, options);
      }

      /** @deprecated */
      public static ContextRootType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ContextRootType)XmlBeans.getContextTypeLoader().parse(xis, ContextRootType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ContextRootType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ContextRootType)XmlBeans.getContextTypeLoader().parse(xis, ContextRootType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ContextRootType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ContextRootType.type, options);
      }

      private Factory() {
      }
   }
}
