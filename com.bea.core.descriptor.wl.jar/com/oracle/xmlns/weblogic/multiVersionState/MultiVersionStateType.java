package com.oracle.xmlns.weblogic.multiVersionState;

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

public interface MultiVersionStateType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MultiVersionStateType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("multiversionstatetypee5a4type");

   UnresponsiveType getUnresponsive();

   boolean isSetUnresponsive();

   void setUnresponsive(UnresponsiveType var1);

   UnresponsiveType addNewUnresponsive();

   void unsetUnresponsive();

   ConfiguredIdType[] getConfiguredIdArray();

   ConfiguredIdType getConfiguredIdArray(int var1);

   int sizeOfConfiguredIdArray();

   void setConfiguredIdArray(ConfiguredIdType[] var1);

   void setConfiguredIdArray(int var1, ConfiguredIdType var2);

   ConfiguredIdType insertNewConfiguredId(int var1);

   ConfiguredIdType addNewConfiguredId();

   void removeConfiguredId(int var1);

   public static final class Factory {
      public static MultiVersionStateType newInstance() {
         return (MultiVersionStateType)XmlBeans.getContextTypeLoader().newInstance(MultiVersionStateType.type, (XmlOptions)null);
      }

      public static MultiVersionStateType newInstance(XmlOptions options) {
         return (MultiVersionStateType)XmlBeans.getContextTypeLoader().newInstance(MultiVersionStateType.type, options);
      }

      public static MultiVersionStateType parse(String xmlAsString) throws XmlException {
         return (MultiVersionStateType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MultiVersionStateType.type, (XmlOptions)null);
      }

      public static MultiVersionStateType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (MultiVersionStateType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MultiVersionStateType.type, options);
      }

      public static MultiVersionStateType parse(File file) throws XmlException, IOException {
         return (MultiVersionStateType)XmlBeans.getContextTypeLoader().parse(file, MultiVersionStateType.type, (XmlOptions)null);
      }

      public static MultiVersionStateType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MultiVersionStateType)XmlBeans.getContextTypeLoader().parse(file, MultiVersionStateType.type, options);
      }

      public static MultiVersionStateType parse(URL u) throws XmlException, IOException {
         return (MultiVersionStateType)XmlBeans.getContextTypeLoader().parse(u, MultiVersionStateType.type, (XmlOptions)null);
      }

      public static MultiVersionStateType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MultiVersionStateType)XmlBeans.getContextTypeLoader().parse(u, MultiVersionStateType.type, options);
      }

      public static MultiVersionStateType parse(InputStream is) throws XmlException, IOException {
         return (MultiVersionStateType)XmlBeans.getContextTypeLoader().parse(is, MultiVersionStateType.type, (XmlOptions)null);
      }

      public static MultiVersionStateType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MultiVersionStateType)XmlBeans.getContextTypeLoader().parse(is, MultiVersionStateType.type, options);
      }

      public static MultiVersionStateType parse(Reader r) throws XmlException, IOException {
         return (MultiVersionStateType)XmlBeans.getContextTypeLoader().parse(r, MultiVersionStateType.type, (XmlOptions)null);
      }

      public static MultiVersionStateType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MultiVersionStateType)XmlBeans.getContextTypeLoader().parse(r, MultiVersionStateType.type, options);
      }

      public static MultiVersionStateType parse(XMLStreamReader sr) throws XmlException {
         return (MultiVersionStateType)XmlBeans.getContextTypeLoader().parse(sr, MultiVersionStateType.type, (XmlOptions)null);
      }

      public static MultiVersionStateType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MultiVersionStateType)XmlBeans.getContextTypeLoader().parse(sr, MultiVersionStateType.type, options);
      }

      public static MultiVersionStateType parse(Node node) throws XmlException {
         return (MultiVersionStateType)XmlBeans.getContextTypeLoader().parse(node, MultiVersionStateType.type, (XmlOptions)null);
      }

      public static MultiVersionStateType parse(Node node, XmlOptions options) throws XmlException {
         return (MultiVersionStateType)XmlBeans.getContextTypeLoader().parse(node, MultiVersionStateType.type, options);
      }

      /** @deprecated */
      public static MultiVersionStateType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MultiVersionStateType)XmlBeans.getContextTypeLoader().parse(xis, MultiVersionStateType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MultiVersionStateType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MultiVersionStateType)XmlBeans.getContextTypeLoader().parse(xis, MultiVersionStateType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MultiVersionStateType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MultiVersionStateType.type, options);
      }

      private Factory() {
      }
   }
}
