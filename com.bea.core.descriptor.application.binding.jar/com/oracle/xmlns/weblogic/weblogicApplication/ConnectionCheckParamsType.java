package com.oracle.xmlns.weblogic.weblogicApplication;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
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

public interface ConnectionCheckParamsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConnectionCheckParamsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("connectioncheckparamstypeb4e3type");

   String getTableName();

   XmlString xgetTableName();

   boolean isSetTableName();

   void setTableName(String var1);

   void xsetTableName(XmlString var1);

   void unsetTableName();

   TrueFalseType getCheckOnReserveEnabled();

   boolean isSetCheckOnReserveEnabled();

   void setCheckOnReserveEnabled(TrueFalseType var1);

   TrueFalseType addNewCheckOnReserveEnabled();

   void unsetCheckOnReserveEnabled();

   TrueFalseType getCheckOnReleaseEnabled();

   boolean isSetCheckOnReleaseEnabled();

   void setCheckOnReleaseEnabled(TrueFalseType var1);

   TrueFalseType addNewCheckOnReleaseEnabled();

   void unsetCheckOnReleaseEnabled();

   int getRefreshMinutes();

   XmlInt xgetRefreshMinutes();

   boolean isSetRefreshMinutes();

   void setRefreshMinutes(int var1);

   void xsetRefreshMinutes(XmlInt var1);

   void unsetRefreshMinutes();

   TrueFalseType getCheckOnCreateEnabled();

   boolean isSetCheckOnCreateEnabled();

   void setCheckOnCreateEnabled(TrueFalseType var1);

   TrueFalseType addNewCheckOnCreateEnabled();

   void unsetCheckOnCreateEnabled();

   int getConnectionReserveTimeoutSeconds();

   XmlInt xgetConnectionReserveTimeoutSeconds();

   boolean isSetConnectionReserveTimeoutSeconds();

   void setConnectionReserveTimeoutSeconds(int var1);

   void xsetConnectionReserveTimeoutSeconds(XmlInt var1);

   void unsetConnectionReserveTimeoutSeconds();

   int getConnectionCreationRetryFrequencySeconds();

   XmlInt xgetConnectionCreationRetryFrequencySeconds();

   boolean isSetConnectionCreationRetryFrequencySeconds();

   void setConnectionCreationRetryFrequencySeconds(int var1);

   void xsetConnectionCreationRetryFrequencySeconds(XmlInt var1);

   void unsetConnectionCreationRetryFrequencySeconds();

   int getInactiveConnectionTimeoutSeconds();

   XmlInt xgetInactiveConnectionTimeoutSeconds();

   boolean isSetInactiveConnectionTimeoutSeconds();

   void setInactiveConnectionTimeoutSeconds(int var1);

   void xsetInactiveConnectionTimeoutSeconds(XmlInt var1);

   void unsetInactiveConnectionTimeoutSeconds();

   int getTestFrequencySeconds();

   XmlInt xgetTestFrequencySeconds();

   boolean isSetTestFrequencySeconds();

   void setTestFrequencySeconds(int var1);

   void xsetTestFrequencySeconds(XmlInt var1);

   void unsetTestFrequencySeconds();

   String getInitSql();

   XmlString xgetInitSql();

   boolean isSetInitSql();

   void setInitSql(String var1);

   void xsetInitSql(XmlString var1);

   void unsetInitSql();

   public static final class Factory {
      public static ConnectionCheckParamsType newInstance() {
         return (ConnectionCheckParamsType)XmlBeans.getContextTypeLoader().newInstance(ConnectionCheckParamsType.type, (XmlOptions)null);
      }

      public static ConnectionCheckParamsType newInstance(XmlOptions options) {
         return (ConnectionCheckParamsType)XmlBeans.getContextTypeLoader().newInstance(ConnectionCheckParamsType.type, options);
      }

      public static ConnectionCheckParamsType parse(String xmlAsString) throws XmlException {
         return (ConnectionCheckParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionCheckParamsType.type, (XmlOptions)null);
      }

      public static ConnectionCheckParamsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConnectionCheckParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionCheckParamsType.type, options);
      }

      public static ConnectionCheckParamsType parse(File file) throws XmlException, IOException {
         return (ConnectionCheckParamsType)XmlBeans.getContextTypeLoader().parse(file, ConnectionCheckParamsType.type, (XmlOptions)null);
      }

      public static ConnectionCheckParamsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConnectionCheckParamsType)XmlBeans.getContextTypeLoader().parse(file, ConnectionCheckParamsType.type, options);
      }

      public static ConnectionCheckParamsType parse(URL u) throws XmlException, IOException {
         return (ConnectionCheckParamsType)XmlBeans.getContextTypeLoader().parse(u, ConnectionCheckParamsType.type, (XmlOptions)null);
      }

      public static ConnectionCheckParamsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConnectionCheckParamsType)XmlBeans.getContextTypeLoader().parse(u, ConnectionCheckParamsType.type, options);
      }

      public static ConnectionCheckParamsType parse(InputStream is) throws XmlException, IOException {
         return (ConnectionCheckParamsType)XmlBeans.getContextTypeLoader().parse(is, ConnectionCheckParamsType.type, (XmlOptions)null);
      }

      public static ConnectionCheckParamsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConnectionCheckParamsType)XmlBeans.getContextTypeLoader().parse(is, ConnectionCheckParamsType.type, options);
      }

      public static ConnectionCheckParamsType parse(Reader r) throws XmlException, IOException {
         return (ConnectionCheckParamsType)XmlBeans.getContextTypeLoader().parse(r, ConnectionCheckParamsType.type, (XmlOptions)null);
      }

      public static ConnectionCheckParamsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConnectionCheckParamsType)XmlBeans.getContextTypeLoader().parse(r, ConnectionCheckParamsType.type, options);
      }

      public static ConnectionCheckParamsType parse(XMLStreamReader sr) throws XmlException {
         return (ConnectionCheckParamsType)XmlBeans.getContextTypeLoader().parse(sr, ConnectionCheckParamsType.type, (XmlOptions)null);
      }

      public static ConnectionCheckParamsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConnectionCheckParamsType)XmlBeans.getContextTypeLoader().parse(sr, ConnectionCheckParamsType.type, options);
      }

      public static ConnectionCheckParamsType parse(Node node) throws XmlException {
         return (ConnectionCheckParamsType)XmlBeans.getContextTypeLoader().parse(node, ConnectionCheckParamsType.type, (XmlOptions)null);
      }

      public static ConnectionCheckParamsType parse(Node node, XmlOptions options) throws XmlException {
         return (ConnectionCheckParamsType)XmlBeans.getContextTypeLoader().parse(node, ConnectionCheckParamsType.type, options);
      }

      /** @deprecated */
      public static ConnectionCheckParamsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConnectionCheckParamsType)XmlBeans.getContextTypeLoader().parse(xis, ConnectionCheckParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConnectionCheckParamsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConnectionCheckParamsType)XmlBeans.getContextTypeLoader().parse(xis, ConnectionCheckParamsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionCheckParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionCheckParamsType.type, options);
      }

      private Factory() {
      }
   }
}
