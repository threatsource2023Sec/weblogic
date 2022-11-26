package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface LockManagerType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LockManagerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("lockmanagertypef54ftype");

   public static final class Factory {
      public static LockManagerType newInstance() {
         return (LockManagerType)XmlBeans.getContextTypeLoader().newInstance(LockManagerType.type, (XmlOptions)null);
      }

      public static LockManagerType newInstance(XmlOptions options) {
         return (LockManagerType)XmlBeans.getContextTypeLoader().newInstance(LockManagerType.type, options);
      }

      public static LockManagerType parse(String xmlAsString) throws XmlException {
         return (LockManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LockManagerType.type, (XmlOptions)null);
      }

      public static LockManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (LockManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LockManagerType.type, options);
      }

      public static LockManagerType parse(File file) throws XmlException, IOException {
         return (LockManagerType)XmlBeans.getContextTypeLoader().parse(file, LockManagerType.type, (XmlOptions)null);
      }

      public static LockManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LockManagerType)XmlBeans.getContextTypeLoader().parse(file, LockManagerType.type, options);
      }

      public static LockManagerType parse(URL u) throws XmlException, IOException {
         return (LockManagerType)XmlBeans.getContextTypeLoader().parse(u, LockManagerType.type, (XmlOptions)null);
      }

      public static LockManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LockManagerType)XmlBeans.getContextTypeLoader().parse(u, LockManagerType.type, options);
      }

      public static LockManagerType parse(InputStream is) throws XmlException, IOException {
         return (LockManagerType)XmlBeans.getContextTypeLoader().parse(is, LockManagerType.type, (XmlOptions)null);
      }

      public static LockManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LockManagerType)XmlBeans.getContextTypeLoader().parse(is, LockManagerType.type, options);
      }

      public static LockManagerType parse(Reader r) throws XmlException, IOException {
         return (LockManagerType)XmlBeans.getContextTypeLoader().parse(r, LockManagerType.type, (XmlOptions)null);
      }

      public static LockManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LockManagerType)XmlBeans.getContextTypeLoader().parse(r, LockManagerType.type, options);
      }

      public static LockManagerType parse(XMLStreamReader sr) throws XmlException {
         return (LockManagerType)XmlBeans.getContextTypeLoader().parse(sr, LockManagerType.type, (XmlOptions)null);
      }

      public static LockManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LockManagerType)XmlBeans.getContextTypeLoader().parse(sr, LockManagerType.type, options);
      }

      public static LockManagerType parse(Node node) throws XmlException {
         return (LockManagerType)XmlBeans.getContextTypeLoader().parse(node, LockManagerType.type, (XmlOptions)null);
      }

      public static LockManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (LockManagerType)XmlBeans.getContextTypeLoader().parse(node, LockManagerType.type, options);
      }

      /** @deprecated */
      public static LockManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LockManagerType)XmlBeans.getContextTypeLoader().parse(xis, LockManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LockManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LockManagerType)XmlBeans.getContextTypeLoader().parse(xis, LockManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LockManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LockManagerType.type, options);
      }

      private Factory() {
      }
   }
}
