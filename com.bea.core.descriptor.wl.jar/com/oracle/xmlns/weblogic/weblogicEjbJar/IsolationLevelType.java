package com.oracle.xmlns.weblogic.weblogicEjbJar;

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

public interface IsolationLevelType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(IsolationLevelType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("isolationleveltypec9c9type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static IsolationLevelType newInstance() {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().newInstance(IsolationLevelType.type, (XmlOptions)null);
      }

      public static IsolationLevelType newInstance(XmlOptions options) {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().newInstance(IsolationLevelType.type, options);
      }

      public static IsolationLevelType parse(String xmlAsString) throws XmlException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(xmlAsString, IsolationLevelType.type, (XmlOptions)null);
      }

      public static IsolationLevelType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(xmlAsString, IsolationLevelType.type, options);
      }

      public static IsolationLevelType parse(File file) throws XmlException, IOException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(file, IsolationLevelType.type, (XmlOptions)null);
      }

      public static IsolationLevelType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(file, IsolationLevelType.type, options);
      }

      public static IsolationLevelType parse(URL u) throws XmlException, IOException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(u, IsolationLevelType.type, (XmlOptions)null);
      }

      public static IsolationLevelType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(u, IsolationLevelType.type, options);
      }

      public static IsolationLevelType parse(InputStream is) throws XmlException, IOException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(is, IsolationLevelType.type, (XmlOptions)null);
      }

      public static IsolationLevelType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(is, IsolationLevelType.type, options);
      }

      public static IsolationLevelType parse(Reader r) throws XmlException, IOException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(r, IsolationLevelType.type, (XmlOptions)null);
      }

      public static IsolationLevelType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(r, IsolationLevelType.type, options);
      }

      public static IsolationLevelType parse(XMLStreamReader sr) throws XmlException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(sr, IsolationLevelType.type, (XmlOptions)null);
      }

      public static IsolationLevelType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(sr, IsolationLevelType.type, options);
      }

      public static IsolationLevelType parse(Node node) throws XmlException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(node, IsolationLevelType.type, (XmlOptions)null);
      }

      public static IsolationLevelType parse(Node node, XmlOptions options) throws XmlException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(node, IsolationLevelType.type, options);
      }

      /** @deprecated */
      public static IsolationLevelType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(xis, IsolationLevelType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static IsolationLevelType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(xis, IsolationLevelType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, IsolationLevelType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, IsolationLevelType.type, options);
      }

      private Factory() {
      }
   }
}
