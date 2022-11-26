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

public interface AggregateListenersType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AggregateListenersType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("aggregatelistenerstype4e1btype");

   CustomAggregateListenerType[] getCustomAggregateListenerArray();

   CustomAggregateListenerType getCustomAggregateListenerArray(int var1);

   boolean isNilCustomAggregateListenerArray(int var1);

   int sizeOfCustomAggregateListenerArray();

   void setCustomAggregateListenerArray(CustomAggregateListenerType[] var1);

   void setCustomAggregateListenerArray(int var1, CustomAggregateListenerType var2);

   void setNilCustomAggregateListenerArray(int var1);

   CustomAggregateListenerType insertNewCustomAggregateListener(int var1);

   CustomAggregateListenerType addNewCustomAggregateListener();

   void removeCustomAggregateListener(int var1);

   public static final class Factory {
      public static AggregateListenersType newInstance() {
         return (AggregateListenersType)XmlBeans.getContextTypeLoader().newInstance(AggregateListenersType.type, (XmlOptions)null);
      }

      public static AggregateListenersType newInstance(XmlOptions options) {
         return (AggregateListenersType)XmlBeans.getContextTypeLoader().newInstance(AggregateListenersType.type, options);
      }

      public static AggregateListenersType parse(String xmlAsString) throws XmlException {
         return (AggregateListenersType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AggregateListenersType.type, (XmlOptions)null);
      }

      public static AggregateListenersType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AggregateListenersType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AggregateListenersType.type, options);
      }

      public static AggregateListenersType parse(File file) throws XmlException, IOException {
         return (AggregateListenersType)XmlBeans.getContextTypeLoader().parse(file, AggregateListenersType.type, (XmlOptions)null);
      }

      public static AggregateListenersType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AggregateListenersType)XmlBeans.getContextTypeLoader().parse(file, AggregateListenersType.type, options);
      }

      public static AggregateListenersType parse(URL u) throws XmlException, IOException {
         return (AggregateListenersType)XmlBeans.getContextTypeLoader().parse(u, AggregateListenersType.type, (XmlOptions)null);
      }

      public static AggregateListenersType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AggregateListenersType)XmlBeans.getContextTypeLoader().parse(u, AggregateListenersType.type, options);
      }

      public static AggregateListenersType parse(InputStream is) throws XmlException, IOException {
         return (AggregateListenersType)XmlBeans.getContextTypeLoader().parse(is, AggregateListenersType.type, (XmlOptions)null);
      }

      public static AggregateListenersType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AggregateListenersType)XmlBeans.getContextTypeLoader().parse(is, AggregateListenersType.type, options);
      }

      public static AggregateListenersType parse(Reader r) throws XmlException, IOException {
         return (AggregateListenersType)XmlBeans.getContextTypeLoader().parse(r, AggregateListenersType.type, (XmlOptions)null);
      }

      public static AggregateListenersType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AggregateListenersType)XmlBeans.getContextTypeLoader().parse(r, AggregateListenersType.type, options);
      }

      public static AggregateListenersType parse(XMLStreamReader sr) throws XmlException {
         return (AggregateListenersType)XmlBeans.getContextTypeLoader().parse(sr, AggregateListenersType.type, (XmlOptions)null);
      }

      public static AggregateListenersType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AggregateListenersType)XmlBeans.getContextTypeLoader().parse(sr, AggregateListenersType.type, options);
      }

      public static AggregateListenersType parse(Node node) throws XmlException {
         return (AggregateListenersType)XmlBeans.getContextTypeLoader().parse(node, AggregateListenersType.type, (XmlOptions)null);
      }

      public static AggregateListenersType parse(Node node, XmlOptions options) throws XmlException {
         return (AggregateListenersType)XmlBeans.getContextTypeLoader().parse(node, AggregateListenersType.type, options);
      }

      /** @deprecated */
      public static AggregateListenersType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AggregateListenersType)XmlBeans.getContextTypeLoader().parse(xis, AggregateListenersType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AggregateListenersType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AggregateListenersType)XmlBeans.getContextTypeLoader().parse(xis, AggregateListenersType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AggregateListenersType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AggregateListenersType.type, options);
      }

      private Factory() {
      }
   }
}
