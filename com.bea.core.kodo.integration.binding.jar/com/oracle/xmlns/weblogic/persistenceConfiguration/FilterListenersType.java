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

public interface FilterListenersType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(FilterListenersType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("filterlistenerstypeccd0type");

   CustomFilterListenerType[] getCustomFilterListenerArray();

   CustomFilterListenerType getCustomFilterListenerArray(int var1);

   boolean isNilCustomFilterListenerArray(int var1);

   int sizeOfCustomFilterListenerArray();

   void setCustomFilterListenerArray(CustomFilterListenerType[] var1);

   void setCustomFilterListenerArray(int var1, CustomFilterListenerType var2);

   void setNilCustomFilterListenerArray(int var1);

   CustomFilterListenerType insertNewCustomFilterListener(int var1);

   CustomFilterListenerType addNewCustomFilterListener();

   void removeCustomFilterListener(int var1);

   public static final class Factory {
      public static FilterListenersType newInstance() {
         return (FilterListenersType)XmlBeans.getContextTypeLoader().newInstance(FilterListenersType.type, (XmlOptions)null);
      }

      public static FilterListenersType newInstance(XmlOptions options) {
         return (FilterListenersType)XmlBeans.getContextTypeLoader().newInstance(FilterListenersType.type, options);
      }

      public static FilterListenersType parse(String xmlAsString) throws XmlException {
         return (FilterListenersType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FilterListenersType.type, (XmlOptions)null);
      }

      public static FilterListenersType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (FilterListenersType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FilterListenersType.type, options);
      }

      public static FilterListenersType parse(File file) throws XmlException, IOException {
         return (FilterListenersType)XmlBeans.getContextTypeLoader().parse(file, FilterListenersType.type, (XmlOptions)null);
      }

      public static FilterListenersType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (FilterListenersType)XmlBeans.getContextTypeLoader().parse(file, FilterListenersType.type, options);
      }

      public static FilterListenersType parse(URL u) throws XmlException, IOException {
         return (FilterListenersType)XmlBeans.getContextTypeLoader().parse(u, FilterListenersType.type, (XmlOptions)null);
      }

      public static FilterListenersType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (FilterListenersType)XmlBeans.getContextTypeLoader().parse(u, FilterListenersType.type, options);
      }

      public static FilterListenersType parse(InputStream is) throws XmlException, IOException {
         return (FilterListenersType)XmlBeans.getContextTypeLoader().parse(is, FilterListenersType.type, (XmlOptions)null);
      }

      public static FilterListenersType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (FilterListenersType)XmlBeans.getContextTypeLoader().parse(is, FilterListenersType.type, options);
      }

      public static FilterListenersType parse(Reader r) throws XmlException, IOException {
         return (FilterListenersType)XmlBeans.getContextTypeLoader().parse(r, FilterListenersType.type, (XmlOptions)null);
      }

      public static FilterListenersType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (FilterListenersType)XmlBeans.getContextTypeLoader().parse(r, FilterListenersType.type, options);
      }

      public static FilterListenersType parse(XMLStreamReader sr) throws XmlException {
         return (FilterListenersType)XmlBeans.getContextTypeLoader().parse(sr, FilterListenersType.type, (XmlOptions)null);
      }

      public static FilterListenersType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (FilterListenersType)XmlBeans.getContextTypeLoader().parse(sr, FilterListenersType.type, options);
      }

      public static FilterListenersType parse(Node node) throws XmlException {
         return (FilterListenersType)XmlBeans.getContextTypeLoader().parse(node, FilterListenersType.type, (XmlOptions)null);
      }

      public static FilterListenersType parse(Node node, XmlOptions options) throws XmlException {
         return (FilterListenersType)XmlBeans.getContextTypeLoader().parse(node, FilterListenersType.type, options);
      }

      /** @deprecated */
      public static FilterListenersType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (FilterListenersType)XmlBeans.getContextTypeLoader().parse(xis, FilterListenersType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static FilterListenersType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (FilterListenersType)XmlBeans.getContextTypeLoader().parse(xis, FilterListenersType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FilterListenersType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FilterListenersType.type, options);
      }

      private Factory() {
      }
   }
}
