package com.bea.connector.monitoring1Dot0;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
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

public interface ConfigPropertyType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConfigPropertyType.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("configpropertytype5a56type");

   String[] getDescriptionArray();

   String getDescriptionArray(int var1);

   XmlString[] xgetDescriptionArray();

   XmlString xgetDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(String[] var1);

   void setDescriptionArray(int var1, String var2);

   void xsetDescriptionArray(XmlString[] var1);

   void xsetDescriptionArray(int var1, XmlString var2);

   void insertDescription(int var1, String var2);

   void addDescription(String var1);

   XmlString insertNewDescription(int var1);

   XmlString addNewDescription();

   void removeDescription(int var1);

   String getName();

   XmlString xgetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   String getType();

   XmlString xgetType();

   void setType(String var1);

   void xsetType(XmlString var1);

   String getValue();

   XmlString xgetValue();

   void setValue(String var1);

   void xsetValue(XmlString var1);

   boolean getIgnore();

   XmlBoolean xgetIgnore();

   void setIgnore(boolean var1);

   void xsetIgnore(XmlBoolean var1);

   boolean getSupportsDynamicUpdates();

   XmlBoolean xgetSupportsDynamicUpdates();

   void setSupportsDynamicUpdates(boolean var1);

   void xsetSupportsDynamicUpdates(XmlBoolean var1);

   boolean getConfidential();

   XmlBoolean xgetConfidential();

   void setConfidential(boolean var1);

   void xsetConfidential(XmlBoolean var1);

   public static final class Factory {
      public static ConfigPropertyType newInstance() {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().newInstance(ConfigPropertyType.type, (XmlOptions)null);
      }

      public static ConfigPropertyType newInstance(XmlOptions options) {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().newInstance(ConfigPropertyType.type, options);
      }

      public static ConfigPropertyType parse(String xmlAsString) throws XmlException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConfigPropertyType.type, (XmlOptions)null);
      }

      public static ConfigPropertyType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConfigPropertyType.type, options);
      }

      public static ConfigPropertyType parse(File file) throws XmlException, IOException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(file, ConfigPropertyType.type, (XmlOptions)null);
      }

      public static ConfigPropertyType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(file, ConfigPropertyType.type, options);
      }

      public static ConfigPropertyType parse(URL u) throws XmlException, IOException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(u, ConfigPropertyType.type, (XmlOptions)null);
      }

      public static ConfigPropertyType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(u, ConfigPropertyType.type, options);
      }

      public static ConfigPropertyType parse(InputStream is) throws XmlException, IOException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(is, ConfigPropertyType.type, (XmlOptions)null);
      }

      public static ConfigPropertyType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(is, ConfigPropertyType.type, options);
      }

      public static ConfigPropertyType parse(Reader r) throws XmlException, IOException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(r, ConfigPropertyType.type, (XmlOptions)null);
      }

      public static ConfigPropertyType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(r, ConfigPropertyType.type, options);
      }

      public static ConfigPropertyType parse(XMLStreamReader sr) throws XmlException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(sr, ConfigPropertyType.type, (XmlOptions)null);
      }

      public static ConfigPropertyType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(sr, ConfigPropertyType.type, options);
      }

      public static ConfigPropertyType parse(Node node) throws XmlException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(node, ConfigPropertyType.type, (XmlOptions)null);
      }

      public static ConfigPropertyType parse(Node node, XmlOptions options) throws XmlException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(node, ConfigPropertyType.type, options);
      }

      /** @deprecated */
      public static ConfigPropertyType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(xis, ConfigPropertyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConfigPropertyType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(xis, ConfigPropertyType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConfigPropertyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConfigPropertyType.type, options);
      }

      private Factory() {
      }
   }
}
