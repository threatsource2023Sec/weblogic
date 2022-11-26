package com.oracle.xmlns.weblogic.persistenceConfiguration;

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

public interface AutoDetachType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AutoDetachType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("autodetachtype6d5btype");

   String[] getAutoDetachArray();

   String getAutoDetachArray(int var1);

   XmlString[] xgetAutoDetachArray();

   XmlString xgetAutoDetachArray(int var1);

   boolean isNilAutoDetachArray(int var1);

   int sizeOfAutoDetachArray();

   void setAutoDetachArray(String[] var1);

   void setAutoDetachArray(int var1, String var2);

   void xsetAutoDetachArray(XmlString[] var1);

   void xsetAutoDetachArray(int var1, XmlString var2);

   void setNilAutoDetachArray(int var1);

   void insertAutoDetach(int var1, String var2);

   void addAutoDetach(String var1);

   XmlString insertNewAutoDetach(int var1);

   XmlString addNewAutoDetach();

   void removeAutoDetach(int var1);

   public static final class Factory {
      public static AutoDetachType newInstance() {
         return (AutoDetachType)XmlBeans.getContextTypeLoader().newInstance(AutoDetachType.type, (XmlOptions)null);
      }

      public static AutoDetachType newInstance(XmlOptions options) {
         return (AutoDetachType)XmlBeans.getContextTypeLoader().newInstance(AutoDetachType.type, options);
      }

      public static AutoDetachType parse(String xmlAsString) throws XmlException {
         return (AutoDetachType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AutoDetachType.type, (XmlOptions)null);
      }

      public static AutoDetachType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AutoDetachType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AutoDetachType.type, options);
      }

      public static AutoDetachType parse(File file) throws XmlException, IOException {
         return (AutoDetachType)XmlBeans.getContextTypeLoader().parse(file, AutoDetachType.type, (XmlOptions)null);
      }

      public static AutoDetachType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AutoDetachType)XmlBeans.getContextTypeLoader().parse(file, AutoDetachType.type, options);
      }

      public static AutoDetachType parse(URL u) throws XmlException, IOException {
         return (AutoDetachType)XmlBeans.getContextTypeLoader().parse(u, AutoDetachType.type, (XmlOptions)null);
      }

      public static AutoDetachType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AutoDetachType)XmlBeans.getContextTypeLoader().parse(u, AutoDetachType.type, options);
      }

      public static AutoDetachType parse(InputStream is) throws XmlException, IOException {
         return (AutoDetachType)XmlBeans.getContextTypeLoader().parse(is, AutoDetachType.type, (XmlOptions)null);
      }

      public static AutoDetachType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AutoDetachType)XmlBeans.getContextTypeLoader().parse(is, AutoDetachType.type, options);
      }

      public static AutoDetachType parse(Reader r) throws XmlException, IOException {
         return (AutoDetachType)XmlBeans.getContextTypeLoader().parse(r, AutoDetachType.type, (XmlOptions)null);
      }

      public static AutoDetachType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AutoDetachType)XmlBeans.getContextTypeLoader().parse(r, AutoDetachType.type, options);
      }

      public static AutoDetachType parse(XMLStreamReader sr) throws XmlException {
         return (AutoDetachType)XmlBeans.getContextTypeLoader().parse(sr, AutoDetachType.type, (XmlOptions)null);
      }

      public static AutoDetachType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AutoDetachType)XmlBeans.getContextTypeLoader().parse(sr, AutoDetachType.type, options);
      }

      public static AutoDetachType parse(Node node) throws XmlException {
         return (AutoDetachType)XmlBeans.getContextTypeLoader().parse(node, AutoDetachType.type, (XmlOptions)null);
      }

      public static AutoDetachType parse(Node node, XmlOptions options) throws XmlException {
         return (AutoDetachType)XmlBeans.getContextTypeLoader().parse(node, AutoDetachType.type, options);
      }

      /** @deprecated */
      public static AutoDetachType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AutoDetachType)XmlBeans.getContextTypeLoader().parse(xis, AutoDetachType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AutoDetachType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AutoDetachType)XmlBeans.getContextTypeLoader().parse(xis, AutoDetachType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AutoDetachType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AutoDetachType.type, options);
      }

      private Factory() {
      }
   }
}
