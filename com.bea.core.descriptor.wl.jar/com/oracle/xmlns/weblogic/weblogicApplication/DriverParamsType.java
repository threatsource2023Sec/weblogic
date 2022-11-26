package com.oracle.xmlns.weblogic.weblogicApplication;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
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

public interface DriverParamsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DriverParamsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("driverparamstype70b4type");

   StatementType getStatement();

   boolean isSetStatement();

   void setStatement(StatementType var1);

   StatementType addNewStatement();

   void unsetStatement();

   PreparedStatementType getPreparedStatement();

   boolean isSetPreparedStatement();

   void setPreparedStatement(PreparedStatementType var1);

   PreparedStatementType addNewPreparedStatement();

   void unsetPreparedStatement();

   TrueFalseType getRowPrefetchEnabled();

   boolean isSetRowPrefetchEnabled();

   void setRowPrefetchEnabled(TrueFalseType var1);

   TrueFalseType addNewRowPrefetchEnabled();

   void unsetRowPrefetchEnabled();

   int getRowPrefetchSize();

   XmlInt xgetRowPrefetchSize();

   boolean isSetRowPrefetchSize();

   void setRowPrefetchSize(int var1);

   void xsetRowPrefetchSize(XmlInt var1);

   void unsetRowPrefetchSize();

   int getStreamChunkSize();

   XmlInt xgetStreamChunkSize();

   boolean isSetStreamChunkSize();

   void setStreamChunkSize(int var1);

   void xsetStreamChunkSize(XmlInt var1);

   void unsetStreamChunkSize();

   public static final class Factory {
      public static DriverParamsType newInstance() {
         return (DriverParamsType)XmlBeans.getContextTypeLoader().newInstance(DriverParamsType.type, (XmlOptions)null);
      }

      public static DriverParamsType newInstance(XmlOptions options) {
         return (DriverParamsType)XmlBeans.getContextTypeLoader().newInstance(DriverParamsType.type, options);
      }

      public static DriverParamsType parse(String xmlAsString) throws XmlException {
         return (DriverParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DriverParamsType.type, (XmlOptions)null);
      }

      public static DriverParamsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DriverParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DriverParamsType.type, options);
      }

      public static DriverParamsType parse(File file) throws XmlException, IOException {
         return (DriverParamsType)XmlBeans.getContextTypeLoader().parse(file, DriverParamsType.type, (XmlOptions)null);
      }

      public static DriverParamsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DriverParamsType)XmlBeans.getContextTypeLoader().parse(file, DriverParamsType.type, options);
      }

      public static DriverParamsType parse(URL u) throws XmlException, IOException {
         return (DriverParamsType)XmlBeans.getContextTypeLoader().parse(u, DriverParamsType.type, (XmlOptions)null);
      }

      public static DriverParamsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DriverParamsType)XmlBeans.getContextTypeLoader().parse(u, DriverParamsType.type, options);
      }

      public static DriverParamsType parse(InputStream is) throws XmlException, IOException {
         return (DriverParamsType)XmlBeans.getContextTypeLoader().parse(is, DriverParamsType.type, (XmlOptions)null);
      }

      public static DriverParamsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DriverParamsType)XmlBeans.getContextTypeLoader().parse(is, DriverParamsType.type, options);
      }

      public static DriverParamsType parse(Reader r) throws XmlException, IOException {
         return (DriverParamsType)XmlBeans.getContextTypeLoader().parse(r, DriverParamsType.type, (XmlOptions)null);
      }

      public static DriverParamsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DriverParamsType)XmlBeans.getContextTypeLoader().parse(r, DriverParamsType.type, options);
      }

      public static DriverParamsType parse(XMLStreamReader sr) throws XmlException {
         return (DriverParamsType)XmlBeans.getContextTypeLoader().parse(sr, DriverParamsType.type, (XmlOptions)null);
      }

      public static DriverParamsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DriverParamsType)XmlBeans.getContextTypeLoader().parse(sr, DriverParamsType.type, options);
      }

      public static DriverParamsType parse(Node node) throws XmlException {
         return (DriverParamsType)XmlBeans.getContextTypeLoader().parse(node, DriverParamsType.type, (XmlOptions)null);
      }

      public static DriverParamsType parse(Node node, XmlOptions options) throws XmlException {
         return (DriverParamsType)XmlBeans.getContextTypeLoader().parse(node, DriverParamsType.type, options);
      }

      /** @deprecated */
      public static DriverParamsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DriverParamsType)XmlBeans.getContextTypeLoader().parse(xis, DriverParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DriverParamsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DriverParamsType)XmlBeans.getContextTypeLoader().parse(xis, DriverParamsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DriverParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DriverParamsType.type, options);
      }

      private Factory() {
      }
   }
}
