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

public interface PersistentStoreDirType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PersistentStoreDirType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("persistentstoredirtype4b61type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static PersistentStoreDirType newInstance() {
         return (PersistentStoreDirType)XmlBeans.getContextTypeLoader().newInstance(PersistentStoreDirType.type, (XmlOptions)null);
      }

      public static PersistentStoreDirType newInstance(XmlOptions options) {
         return (PersistentStoreDirType)XmlBeans.getContextTypeLoader().newInstance(PersistentStoreDirType.type, options);
      }

      public static PersistentStoreDirType parse(String xmlAsString) throws XmlException {
         return (PersistentStoreDirType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistentStoreDirType.type, (XmlOptions)null);
      }

      public static PersistentStoreDirType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PersistentStoreDirType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistentStoreDirType.type, options);
      }

      public static PersistentStoreDirType parse(File file) throws XmlException, IOException {
         return (PersistentStoreDirType)XmlBeans.getContextTypeLoader().parse(file, PersistentStoreDirType.type, (XmlOptions)null);
      }

      public static PersistentStoreDirType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PersistentStoreDirType)XmlBeans.getContextTypeLoader().parse(file, PersistentStoreDirType.type, options);
      }

      public static PersistentStoreDirType parse(URL u) throws XmlException, IOException {
         return (PersistentStoreDirType)XmlBeans.getContextTypeLoader().parse(u, PersistentStoreDirType.type, (XmlOptions)null);
      }

      public static PersistentStoreDirType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PersistentStoreDirType)XmlBeans.getContextTypeLoader().parse(u, PersistentStoreDirType.type, options);
      }

      public static PersistentStoreDirType parse(InputStream is) throws XmlException, IOException {
         return (PersistentStoreDirType)XmlBeans.getContextTypeLoader().parse(is, PersistentStoreDirType.type, (XmlOptions)null);
      }

      public static PersistentStoreDirType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PersistentStoreDirType)XmlBeans.getContextTypeLoader().parse(is, PersistentStoreDirType.type, options);
      }

      public static PersistentStoreDirType parse(Reader r) throws XmlException, IOException {
         return (PersistentStoreDirType)XmlBeans.getContextTypeLoader().parse(r, PersistentStoreDirType.type, (XmlOptions)null);
      }

      public static PersistentStoreDirType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PersistentStoreDirType)XmlBeans.getContextTypeLoader().parse(r, PersistentStoreDirType.type, options);
      }

      public static PersistentStoreDirType parse(XMLStreamReader sr) throws XmlException {
         return (PersistentStoreDirType)XmlBeans.getContextTypeLoader().parse(sr, PersistentStoreDirType.type, (XmlOptions)null);
      }

      public static PersistentStoreDirType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PersistentStoreDirType)XmlBeans.getContextTypeLoader().parse(sr, PersistentStoreDirType.type, options);
      }

      public static PersistentStoreDirType parse(Node node) throws XmlException {
         return (PersistentStoreDirType)XmlBeans.getContextTypeLoader().parse(node, PersistentStoreDirType.type, (XmlOptions)null);
      }

      public static PersistentStoreDirType parse(Node node, XmlOptions options) throws XmlException {
         return (PersistentStoreDirType)XmlBeans.getContextTypeLoader().parse(node, PersistentStoreDirType.type, options);
      }

      /** @deprecated */
      public static PersistentStoreDirType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PersistentStoreDirType)XmlBeans.getContextTypeLoader().parse(xis, PersistentStoreDirType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PersistentStoreDirType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PersistentStoreDirType)XmlBeans.getContextTypeLoader().parse(xis, PersistentStoreDirType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistentStoreDirType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistentStoreDirType.type, options);
      }

      private Factory() {
      }
   }
}
