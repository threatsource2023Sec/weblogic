package com.oracle.xmlns.weblogic.weblogicRdbmsJar;

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

public interface DelayDatabaseInsertUntilType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DelayDatabaseInsertUntilType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("delaydatabaseinsertuntiltypea3cftype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static DelayDatabaseInsertUntilType newInstance() {
         return (DelayDatabaseInsertUntilType)XmlBeans.getContextTypeLoader().newInstance(DelayDatabaseInsertUntilType.type, (XmlOptions)null);
      }

      public static DelayDatabaseInsertUntilType newInstance(XmlOptions options) {
         return (DelayDatabaseInsertUntilType)XmlBeans.getContextTypeLoader().newInstance(DelayDatabaseInsertUntilType.type, options);
      }

      public static DelayDatabaseInsertUntilType parse(String xmlAsString) throws XmlException {
         return (DelayDatabaseInsertUntilType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DelayDatabaseInsertUntilType.type, (XmlOptions)null);
      }

      public static DelayDatabaseInsertUntilType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DelayDatabaseInsertUntilType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DelayDatabaseInsertUntilType.type, options);
      }

      public static DelayDatabaseInsertUntilType parse(File file) throws XmlException, IOException {
         return (DelayDatabaseInsertUntilType)XmlBeans.getContextTypeLoader().parse(file, DelayDatabaseInsertUntilType.type, (XmlOptions)null);
      }

      public static DelayDatabaseInsertUntilType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DelayDatabaseInsertUntilType)XmlBeans.getContextTypeLoader().parse(file, DelayDatabaseInsertUntilType.type, options);
      }

      public static DelayDatabaseInsertUntilType parse(URL u) throws XmlException, IOException {
         return (DelayDatabaseInsertUntilType)XmlBeans.getContextTypeLoader().parse(u, DelayDatabaseInsertUntilType.type, (XmlOptions)null);
      }

      public static DelayDatabaseInsertUntilType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DelayDatabaseInsertUntilType)XmlBeans.getContextTypeLoader().parse(u, DelayDatabaseInsertUntilType.type, options);
      }

      public static DelayDatabaseInsertUntilType parse(InputStream is) throws XmlException, IOException {
         return (DelayDatabaseInsertUntilType)XmlBeans.getContextTypeLoader().parse(is, DelayDatabaseInsertUntilType.type, (XmlOptions)null);
      }

      public static DelayDatabaseInsertUntilType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DelayDatabaseInsertUntilType)XmlBeans.getContextTypeLoader().parse(is, DelayDatabaseInsertUntilType.type, options);
      }

      public static DelayDatabaseInsertUntilType parse(Reader r) throws XmlException, IOException {
         return (DelayDatabaseInsertUntilType)XmlBeans.getContextTypeLoader().parse(r, DelayDatabaseInsertUntilType.type, (XmlOptions)null);
      }

      public static DelayDatabaseInsertUntilType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DelayDatabaseInsertUntilType)XmlBeans.getContextTypeLoader().parse(r, DelayDatabaseInsertUntilType.type, options);
      }

      public static DelayDatabaseInsertUntilType parse(XMLStreamReader sr) throws XmlException {
         return (DelayDatabaseInsertUntilType)XmlBeans.getContextTypeLoader().parse(sr, DelayDatabaseInsertUntilType.type, (XmlOptions)null);
      }

      public static DelayDatabaseInsertUntilType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DelayDatabaseInsertUntilType)XmlBeans.getContextTypeLoader().parse(sr, DelayDatabaseInsertUntilType.type, options);
      }

      public static DelayDatabaseInsertUntilType parse(Node node) throws XmlException {
         return (DelayDatabaseInsertUntilType)XmlBeans.getContextTypeLoader().parse(node, DelayDatabaseInsertUntilType.type, (XmlOptions)null);
      }

      public static DelayDatabaseInsertUntilType parse(Node node, XmlOptions options) throws XmlException {
         return (DelayDatabaseInsertUntilType)XmlBeans.getContextTypeLoader().parse(node, DelayDatabaseInsertUntilType.type, options);
      }

      /** @deprecated */
      public static DelayDatabaseInsertUntilType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DelayDatabaseInsertUntilType)XmlBeans.getContextTypeLoader().parse(xis, DelayDatabaseInsertUntilType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DelayDatabaseInsertUntilType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DelayDatabaseInsertUntilType)XmlBeans.getContextTypeLoader().parse(xis, DelayDatabaseInsertUntilType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DelayDatabaseInsertUntilType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DelayDatabaseInsertUntilType.type, options);
      }

      private Factory() {
      }
   }
}
