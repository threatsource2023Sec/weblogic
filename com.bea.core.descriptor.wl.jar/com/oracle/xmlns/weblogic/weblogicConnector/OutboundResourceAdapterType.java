package com.oracle.xmlns.weblogic.weblogicConnector;

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

public interface OutboundResourceAdapterType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(OutboundResourceAdapterType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("outboundresourceadaptertypec733type");

   ConnectionDefinitionPropertiesType getDefaultConnectionProperties();

   boolean isSetDefaultConnectionProperties();

   void setDefaultConnectionProperties(ConnectionDefinitionPropertiesType var1);

   ConnectionDefinitionPropertiesType addNewDefaultConnectionProperties();

   void unsetDefaultConnectionProperties();

   ConnectionDefinitionType[] getConnectionDefinitionGroupArray();

   ConnectionDefinitionType getConnectionDefinitionGroupArray(int var1);

   int sizeOfConnectionDefinitionGroupArray();

   void setConnectionDefinitionGroupArray(ConnectionDefinitionType[] var1);

   void setConnectionDefinitionGroupArray(int var1, ConnectionDefinitionType var2);

   ConnectionDefinitionType insertNewConnectionDefinitionGroup(int var1);

   ConnectionDefinitionType addNewConnectionDefinitionGroup();

   void removeConnectionDefinitionGroup(int var1);

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static OutboundResourceAdapterType newInstance() {
         return (OutboundResourceAdapterType)XmlBeans.getContextTypeLoader().newInstance(OutboundResourceAdapterType.type, (XmlOptions)null);
      }

      public static OutboundResourceAdapterType newInstance(XmlOptions options) {
         return (OutboundResourceAdapterType)XmlBeans.getContextTypeLoader().newInstance(OutboundResourceAdapterType.type, options);
      }

      public static OutboundResourceAdapterType parse(String xmlAsString) throws XmlException {
         return (OutboundResourceAdapterType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OutboundResourceAdapterType.type, (XmlOptions)null);
      }

      public static OutboundResourceAdapterType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (OutboundResourceAdapterType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OutboundResourceAdapterType.type, options);
      }

      public static OutboundResourceAdapterType parse(File file) throws XmlException, IOException {
         return (OutboundResourceAdapterType)XmlBeans.getContextTypeLoader().parse(file, OutboundResourceAdapterType.type, (XmlOptions)null);
      }

      public static OutboundResourceAdapterType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (OutboundResourceAdapterType)XmlBeans.getContextTypeLoader().parse(file, OutboundResourceAdapterType.type, options);
      }

      public static OutboundResourceAdapterType parse(URL u) throws XmlException, IOException {
         return (OutboundResourceAdapterType)XmlBeans.getContextTypeLoader().parse(u, OutboundResourceAdapterType.type, (XmlOptions)null);
      }

      public static OutboundResourceAdapterType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (OutboundResourceAdapterType)XmlBeans.getContextTypeLoader().parse(u, OutboundResourceAdapterType.type, options);
      }

      public static OutboundResourceAdapterType parse(InputStream is) throws XmlException, IOException {
         return (OutboundResourceAdapterType)XmlBeans.getContextTypeLoader().parse(is, OutboundResourceAdapterType.type, (XmlOptions)null);
      }

      public static OutboundResourceAdapterType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (OutboundResourceAdapterType)XmlBeans.getContextTypeLoader().parse(is, OutboundResourceAdapterType.type, options);
      }

      public static OutboundResourceAdapterType parse(Reader r) throws XmlException, IOException {
         return (OutboundResourceAdapterType)XmlBeans.getContextTypeLoader().parse(r, OutboundResourceAdapterType.type, (XmlOptions)null);
      }

      public static OutboundResourceAdapterType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (OutboundResourceAdapterType)XmlBeans.getContextTypeLoader().parse(r, OutboundResourceAdapterType.type, options);
      }

      public static OutboundResourceAdapterType parse(XMLStreamReader sr) throws XmlException {
         return (OutboundResourceAdapterType)XmlBeans.getContextTypeLoader().parse(sr, OutboundResourceAdapterType.type, (XmlOptions)null);
      }

      public static OutboundResourceAdapterType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (OutboundResourceAdapterType)XmlBeans.getContextTypeLoader().parse(sr, OutboundResourceAdapterType.type, options);
      }

      public static OutboundResourceAdapterType parse(Node node) throws XmlException {
         return (OutboundResourceAdapterType)XmlBeans.getContextTypeLoader().parse(node, OutboundResourceAdapterType.type, (XmlOptions)null);
      }

      public static OutboundResourceAdapterType parse(Node node, XmlOptions options) throws XmlException {
         return (OutboundResourceAdapterType)XmlBeans.getContextTypeLoader().parse(node, OutboundResourceAdapterType.type, options);
      }

      /** @deprecated */
      public static OutboundResourceAdapterType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (OutboundResourceAdapterType)XmlBeans.getContextTypeLoader().parse(xis, OutboundResourceAdapterType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static OutboundResourceAdapterType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (OutboundResourceAdapterType)XmlBeans.getContextTypeLoader().parse(xis, OutboundResourceAdapterType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OutboundResourceAdapterType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OutboundResourceAdapterType.type, options);
      }

      private Factory() {
      }
   }
}
