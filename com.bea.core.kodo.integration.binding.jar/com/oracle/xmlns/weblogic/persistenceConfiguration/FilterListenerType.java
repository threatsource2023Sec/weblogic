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

public interface FilterListenerType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(FilterListenerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("filterlistenertype1fe3type");

   public static final class Factory {
      public static FilterListenerType newInstance() {
         return (FilterListenerType)XmlBeans.getContextTypeLoader().newInstance(FilterListenerType.type, (XmlOptions)null);
      }

      public static FilterListenerType newInstance(XmlOptions options) {
         return (FilterListenerType)XmlBeans.getContextTypeLoader().newInstance(FilterListenerType.type, options);
      }

      public static FilterListenerType parse(String xmlAsString) throws XmlException {
         return (FilterListenerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FilterListenerType.type, (XmlOptions)null);
      }

      public static FilterListenerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (FilterListenerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FilterListenerType.type, options);
      }

      public static FilterListenerType parse(File file) throws XmlException, IOException {
         return (FilterListenerType)XmlBeans.getContextTypeLoader().parse(file, FilterListenerType.type, (XmlOptions)null);
      }

      public static FilterListenerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (FilterListenerType)XmlBeans.getContextTypeLoader().parse(file, FilterListenerType.type, options);
      }

      public static FilterListenerType parse(URL u) throws XmlException, IOException {
         return (FilterListenerType)XmlBeans.getContextTypeLoader().parse(u, FilterListenerType.type, (XmlOptions)null);
      }

      public static FilterListenerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (FilterListenerType)XmlBeans.getContextTypeLoader().parse(u, FilterListenerType.type, options);
      }

      public static FilterListenerType parse(InputStream is) throws XmlException, IOException {
         return (FilterListenerType)XmlBeans.getContextTypeLoader().parse(is, FilterListenerType.type, (XmlOptions)null);
      }

      public static FilterListenerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (FilterListenerType)XmlBeans.getContextTypeLoader().parse(is, FilterListenerType.type, options);
      }

      public static FilterListenerType parse(Reader r) throws XmlException, IOException {
         return (FilterListenerType)XmlBeans.getContextTypeLoader().parse(r, FilterListenerType.type, (XmlOptions)null);
      }

      public static FilterListenerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (FilterListenerType)XmlBeans.getContextTypeLoader().parse(r, FilterListenerType.type, options);
      }

      public static FilterListenerType parse(XMLStreamReader sr) throws XmlException {
         return (FilterListenerType)XmlBeans.getContextTypeLoader().parse(sr, FilterListenerType.type, (XmlOptions)null);
      }

      public static FilterListenerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (FilterListenerType)XmlBeans.getContextTypeLoader().parse(sr, FilterListenerType.type, options);
      }

      public static FilterListenerType parse(Node node) throws XmlException {
         return (FilterListenerType)XmlBeans.getContextTypeLoader().parse(node, FilterListenerType.type, (XmlOptions)null);
      }

      public static FilterListenerType parse(Node node, XmlOptions options) throws XmlException {
         return (FilterListenerType)XmlBeans.getContextTypeLoader().parse(node, FilterListenerType.type, options);
      }

      /** @deprecated */
      public static FilterListenerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (FilterListenerType)XmlBeans.getContextTypeLoader().parse(xis, FilterListenerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static FilterListenerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (FilterListenerType)XmlBeans.getContextTypeLoader().parse(xis, FilterListenerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FilterListenerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FilterListenerType.type, options);
      }

      private Factory() {
      }
   }
}
