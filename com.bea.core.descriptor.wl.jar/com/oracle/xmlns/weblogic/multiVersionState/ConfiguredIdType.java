package com.oracle.xmlns.weblogic.multiVersionState;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
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

public interface ConfiguredIdType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConfiguredIdType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("configuredidtype9562type");

   InferredIdType[] getInferredIdArray();

   InferredIdType getInferredIdArray(int var1);

   int sizeOfInferredIdArray();

   void setInferredIdArray(InferredIdType[] var1);

   void setInferredIdArray(int var1, InferredIdType var2);

   InferredIdType insertNewInferredId(int var1);

   InferredIdType addNewInferredId();

   void removeInferredId(int var1);

   String getId();

   XmlString xgetId();

   void setId(String var1);

   void xsetId(XmlString var1);

   public static final class Factory {
      public static ConfiguredIdType newInstance() {
         return (ConfiguredIdType)XmlBeans.getContextTypeLoader().newInstance(ConfiguredIdType.type, (XmlOptions)null);
      }

      public static ConfiguredIdType newInstance(XmlOptions options) {
         return (ConfiguredIdType)XmlBeans.getContextTypeLoader().newInstance(ConfiguredIdType.type, options);
      }

      public static ConfiguredIdType parse(String xmlAsString) throws XmlException {
         return (ConfiguredIdType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConfiguredIdType.type, (XmlOptions)null);
      }

      public static ConfiguredIdType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConfiguredIdType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConfiguredIdType.type, options);
      }

      public static ConfiguredIdType parse(File file) throws XmlException, IOException {
         return (ConfiguredIdType)XmlBeans.getContextTypeLoader().parse(file, ConfiguredIdType.type, (XmlOptions)null);
      }

      public static ConfiguredIdType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConfiguredIdType)XmlBeans.getContextTypeLoader().parse(file, ConfiguredIdType.type, options);
      }

      public static ConfiguredIdType parse(URL u) throws XmlException, IOException {
         return (ConfiguredIdType)XmlBeans.getContextTypeLoader().parse(u, ConfiguredIdType.type, (XmlOptions)null);
      }

      public static ConfiguredIdType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConfiguredIdType)XmlBeans.getContextTypeLoader().parse(u, ConfiguredIdType.type, options);
      }

      public static ConfiguredIdType parse(InputStream is) throws XmlException, IOException {
         return (ConfiguredIdType)XmlBeans.getContextTypeLoader().parse(is, ConfiguredIdType.type, (XmlOptions)null);
      }

      public static ConfiguredIdType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConfiguredIdType)XmlBeans.getContextTypeLoader().parse(is, ConfiguredIdType.type, options);
      }

      public static ConfiguredIdType parse(Reader r) throws XmlException, IOException {
         return (ConfiguredIdType)XmlBeans.getContextTypeLoader().parse(r, ConfiguredIdType.type, (XmlOptions)null);
      }

      public static ConfiguredIdType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConfiguredIdType)XmlBeans.getContextTypeLoader().parse(r, ConfiguredIdType.type, options);
      }

      public static ConfiguredIdType parse(XMLStreamReader sr) throws XmlException {
         return (ConfiguredIdType)XmlBeans.getContextTypeLoader().parse(sr, ConfiguredIdType.type, (XmlOptions)null);
      }

      public static ConfiguredIdType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConfiguredIdType)XmlBeans.getContextTypeLoader().parse(sr, ConfiguredIdType.type, options);
      }

      public static ConfiguredIdType parse(Node node) throws XmlException {
         return (ConfiguredIdType)XmlBeans.getContextTypeLoader().parse(node, ConfiguredIdType.type, (XmlOptions)null);
      }

      public static ConfiguredIdType parse(Node node, XmlOptions options) throws XmlException {
         return (ConfiguredIdType)XmlBeans.getContextTypeLoader().parse(node, ConfiguredIdType.type, options);
      }

      /** @deprecated */
      public static ConfiguredIdType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConfiguredIdType)XmlBeans.getContextTypeLoader().parse(xis, ConfiguredIdType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConfiguredIdType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConfiguredIdType)XmlBeans.getContextTypeLoader().parse(xis, ConfiguredIdType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConfiguredIdType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConfiguredIdType.type, options);
      }

      private Factory() {
      }
   }
}
