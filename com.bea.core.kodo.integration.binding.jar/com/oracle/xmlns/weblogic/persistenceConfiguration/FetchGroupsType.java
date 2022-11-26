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

public interface FetchGroupsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(FetchGroupsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("fetchgroupstypec293type");

   String[] getFetchGroupArray();

   String getFetchGroupArray(int var1);

   XmlString[] xgetFetchGroupArray();

   XmlString xgetFetchGroupArray(int var1);

   boolean isNilFetchGroupArray(int var1);

   int sizeOfFetchGroupArray();

   void setFetchGroupArray(String[] var1);

   void setFetchGroupArray(int var1, String var2);

   void xsetFetchGroupArray(XmlString[] var1);

   void xsetFetchGroupArray(int var1, XmlString var2);

   void setNilFetchGroupArray(int var1);

   void insertFetchGroup(int var1, String var2);

   void addFetchGroup(String var1);

   XmlString insertNewFetchGroup(int var1);

   XmlString addNewFetchGroup();

   void removeFetchGroup(int var1);

   public static final class Factory {
      public static FetchGroupsType newInstance() {
         return (FetchGroupsType)XmlBeans.getContextTypeLoader().newInstance(FetchGroupsType.type, (XmlOptions)null);
      }

      public static FetchGroupsType newInstance(XmlOptions options) {
         return (FetchGroupsType)XmlBeans.getContextTypeLoader().newInstance(FetchGroupsType.type, options);
      }

      public static FetchGroupsType parse(String xmlAsString) throws XmlException {
         return (FetchGroupsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FetchGroupsType.type, (XmlOptions)null);
      }

      public static FetchGroupsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (FetchGroupsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FetchGroupsType.type, options);
      }

      public static FetchGroupsType parse(File file) throws XmlException, IOException {
         return (FetchGroupsType)XmlBeans.getContextTypeLoader().parse(file, FetchGroupsType.type, (XmlOptions)null);
      }

      public static FetchGroupsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (FetchGroupsType)XmlBeans.getContextTypeLoader().parse(file, FetchGroupsType.type, options);
      }

      public static FetchGroupsType parse(URL u) throws XmlException, IOException {
         return (FetchGroupsType)XmlBeans.getContextTypeLoader().parse(u, FetchGroupsType.type, (XmlOptions)null);
      }

      public static FetchGroupsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (FetchGroupsType)XmlBeans.getContextTypeLoader().parse(u, FetchGroupsType.type, options);
      }

      public static FetchGroupsType parse(InputStream is) throws XmlException, IOException {
         return (FetchGroupsType)XmlBeans.getContextTypeLoader().parse(is, FetchGroupsType.type, (XmlOptions)null);
      }

      public static FetchGroupsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (FetchGroupsType)XmlBeans.getContextTypeLoader().parse(is, FetchGroupsType.type, options);
      }

      public static FetchGroupsType parse(Reader r) throws XmlException, IOException {
         return (FetchGroupsType)XmlBeans.getContextTypeLoader().parse(r, FetchGroupsType.type, (XmlOptions)null);
      }

      public static FetchGroupsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (FetchGroupsType)XmlBeans.getContextTypeLoader().parse(r, FetchGroupsType.type, options);
      }

      public static FetchGroupsType parse(XMLStreamReader sr) throws XmlException {
         return (FetchGroupsType)XmlBeans.getContextTypeLoader().parse(sr, FetchGroupsType.type, (XmlOptions)null);
      }

      public static FetchGroupsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (FetchGroupsType)XmlBeans.getContextTypeLoader().parse(sr, FetchGroupsType.type, options);
      }

      public static FetchGroupsType parse(Node node) throws XmlException {
         return (FetchGroupsType)XmlBeans.getContextTypeLoader().parse(node, FetchGroupsType.type, (XmlOptions)null);
      }

      public static FetchGroupsType parse(Node node, XmlOptions options) throws XmlException {
         return (FetchGroupsType)XmlBeans.getContextTypeLoader().parse(node, FetchGroupsType.type, options);
      }

      /** @deprecated */
      public static FetchGroupsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (FetchGroupsType)XmlBeans.getContextTypeLoader().parse(xis, FetchGroupsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static FetchGroupsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (FetchGroupsType)XmlBeans.getContextTypeLoader().parse(xis, FetchGroupsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FetchGroupsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FetchGroupsType.type, options);
      }

      private Factory() {
      }
   }
}
