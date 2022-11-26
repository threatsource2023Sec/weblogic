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

public interface DetachStateType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DetachStateType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("detachstatetypeb303type");

   public static final class Factory {
      public static DetachStateType newInstance() {
         return (DetachStateType)XmlBeans.getContextTypeLoader().newInstance(DetachStateType.type, (XmlOptions)null);
      }

      public static DetachStateType newInstance(XmlOptions options) {
         return (DetachStateType)XmlBeans.getContextTypeLoader().newInstance(DetachStateType.type, options);
      }

      public static DetachStateType parse(String xmlAsString) throws XmlException {
         return (DetachStateType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DetachStateType.type, (XmlOptions)null);
      }

      public static DetachStateType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DetachStateType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DetachStateType.type, options);
      }

      public static DetachStateType parse(File file) throws XmlException, IOException {
         return (DetachStateType)XmlBeans.getContextTypeLoader().parse(file, DetachStateType.type, (XmlOptions)null);
      }

      public static DetachStateType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DetachStateType)XmlBeans.getContextTypeLoader().parse(file, DetachStateType.type, options);
      }

      public static DetachStateType parse(URL u) throws XmlException, IOException {
         return (DetachStateType)XmlBeans.getContextTypeLoader().parse(u, DetachStateType.type, (XmlOptions)null);
      }

      public static DetachStateType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DetachStateType)XmlBeans.getContextTypeLoader().parse(u, DetachStateType.type, options);
      }

      public static DetachStateType parse(InputStream is) throws XmlException, IOException {
         return (DetachStateType)XmlBeans.getContextTypeLoader().parse(is, DetachStateType.type, (XmlOptions)null);
      }

      public static DetachStateType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DetachStateType)XmlBeans.getContextTypeLoader().parse(is, DetachStateType.type, options);
      }

      public static DetachStateType parse(Reader r) throws XmlException, IOException {
         return (DetachStateType)XmlBeans.getContextTypeLoader().parse(r, DetachStateType.type, (XmlOptions)null);
      }

      public static DetachStateType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DetachStateType)XmlBeans.getContextTypeLoader().parse(r, DetachStateType.type, options);
      }

      public static DetachStateType parse(XMLStreamReader sr) throws XmlException {
         return (DetachStateType)XmlBeans.getContextTypeLoader().parse(sr, DetachStateType.type, (XmlOptions)null);
      }

      public static DetachStateType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DetachStateType)XmlBeans.getContextTypeLoader().parse(sr, DetachStateType.type, options);
      }

      public static DetachStateType parse(Node node) throws XmlException {
         return (DetachStateType)XmlBeans.getContextTypeLoader().parse(node, DetachStateType.type, (XmlOptions)null);
      }

      public static DetachStateType parse(Node node, XmlOptions options) throws XmlException {
         return (DetachStateType)XmlBeans.getContextTypeLoader().parse(node, DetachStateType.type, options);
      }

      /** @deprecated */
      public static DetachStateType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DetachStateType)XmlBeans.getContextTypeLoader().parse(xis, DetachStateType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DetachStateType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DetachStateType)XmlBeans.getContextTypeLoader().parse(xis, DetachStateType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DetachStateType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DetachStateType.type, options);
      }

      private Factory() {
      }
   }
}
