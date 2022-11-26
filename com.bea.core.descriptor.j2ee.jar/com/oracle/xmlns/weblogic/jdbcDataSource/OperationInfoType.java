package com.oracle.xmlns.weblogic.jdbcDataSource;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.String;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface OperationInfoType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(OperationInfoType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("operationinfotyped62etype");

   String getName();

   void setName(String var1);

   String addNewName();

   WsatConfigType getWsatConfig();

   boolean isSetWsatConfig();

   void setWsatConfig(WsatConfigType var1);

   WsatConfigType addNewWsatConfig();

   void unsetWsatConfig();

   public static final class Factory {
      public static OperationInfoType newInstance() {
         return (OperationInfoType)XmlBeans.getContextTypeLoader().newInstance(OperationInfoType.type, (XmlOptions)null);
      }

      public static OperationInfoType newInstance(XmlOptions options) {
         return (OperationInfoType)XmlBeans.getContextTypeLoader().newInstance(OperationInfoType.type, options);
      }

      public static OperationInfoType parse(java.lang.String xmlAsString) throws XmlException {
         return (OperationInfoType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OperationInfoType.type, (XmlOptions)null);
      }

      public static OperationInfoType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (OperationInfoType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OperationInfoType.type, options);
      }

      public static OperationInfoType parse(File file) throws XmlException, IOException {
         return (OperationInfoType)XmlBeans.getContextTypeLoader().parse(file, OperationInfoType.type, (XmlOptions)null);
      }

      public static OperationInfoType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (OperationInfoType)XmlBeans.getContextTypeLoader().parse(file, OperationInfoType.type, options);
      }

      public static OperationInfoType parse(URL u) throws XmlException, IOException {
         return (OperationInfoType)XmlBeans.getContextTypeLoader().parse(u, OperationInfoType.type, (XmlOptions)null);
      }

      public static OperationInfoType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (OperationInfoType)XmlBeans.getContextTypeLoader().parse(u, OperationInfoType.type, options);
      }

      public static OperationInfoType parse(InputStream is) throws XmlException, IOException {
         return (OperationInfoType)XmlBeans.getContextTypeLoader().parse(is, OperationInfoType.type, (XmlOptions)null);
      }

      public static OperationInfoType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (OperationInfoType)XmlBeans.getContextTypeLoader().parse(is, OperationInfoType.type, options);
      }

      public static OperationInfoType parse(Reader r) throws XmlException, IOException {
         return (OperationInfoType)XmlBeans.getContextTypeLoader().parse(r, OperationInfoType.type, (XmlOptions)null);
      }

      public static OperationInfoType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (OperationInfoType)XmlBeans.getContextTypeLoader().parse(r, OperationInfoType.type, options);
      }

      public static OperationInfoType parse(XMLStreamReader sr) throws XmlException {
         return (OperationInfoType)XmlBeans.getContextTypeLoader().parse(sr, OperationInfoType.type, (XmlOptions)null);
      }

      public static OperationInfoType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (OperationInfoType)XmlBeans.getContextTypeLoader().parse(sr, OperationInfoType.type, options);
      }

      public static OperationInfoType parse(Node node) throws XmlException {
         return (OperationInfoType)XmlBeans.getContextTypeLoader().parse(node, OperationInfoType.type, (XmlOptions)null);
      }

      public static OperationInfoType parse(Node node, XmlOptions options) throws XmlException {
         return (OperationInfoType)XmlBeans.getContextTypeLoader().parse(node, OperationInfoType.type, options);
      }

      /** @deprecated */
      public static OperationInfoType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (OperationInfoType)XmlBeans.getContextTypeLoader().parse(xis, OperationInfoType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static OperationInfoType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (OperationInfoType)XmlBeans.getContextTypeLoader().parse(xis, OperationInfoType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OperationInfoType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OperationInfoType.type, options);
      }

      private Factory() {
      }
   }
}
