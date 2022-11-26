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

public interface PreparedStatementType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PreparedStatementType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("preparedstatementtypea03atype");

   TrueFalseType getProfilingEnabled();

   boolean isSetProfilingEnabled();

   void setProfilingEnabled(TrueFalseType var1);

   TrueFalseType addNewProfilingEnabled();

   void unsetProfilingEnabled();

   int getCacheProfilingThreshold();

   XmlInt xgetCacheProfilingThreshold();

   boolean isSetCacheProfilingThreshold();

   void setCacheProfilingThreshold(int var1);

   void xsetCacheProfilingThreshold(XmlInt var1);

   void unsetCacheProfilingThreshold();

   int getCacheSize();

   XmlInt xgetCacheSize();

   boolean isSetCacheSize();

   void setCacheSize(int var1);

   void xsetCacheSize(XmlInt var1);

   void unsetCacheSize();

   TrueFalseType getParameterLoggingEnabled();

   boolean isSetParameterLoggingEnabled();

   void setParameterLoggingEnabled(TrueFalseType var1);

   TrueFalseType addNewParameterLoggingEnabled();

   void unsetParameterLoggingEnabled();

   int getMaxParameterLength();

   XmlInt xgetMaxParameterLength();

   boolean isSetMaxParameterLength();

   void setMaxParameterLength(int var1);

   void xsetMaxParameterLength(XmlInt var1);

   void unsetMaxParameterLength();

   int getCacheType();

   XmlInt xgetCacheType();

   boolean isSetCacheType();

   void setCacheType(int var1);

   void xsetCacheType(XmlInt var1);

   void unsetCacheType();

   public static final class Factory {
      public static PreparedStatementType newInstance() {
         return (PreparedStatementType)XmlBeans.getContextTypeLoader().newInstance(PreparedStatementType.type, (XmlOptions)null);
      }

      public static PreparedStatementType newInstance(XmlOptions options) {
         return (PreparedStatementType)XmlBeans.getContextTypeLoader().newInstance(PreparedStatementType.type, options);
      }

      public static PreparedStatementType parse(String xmlAsString) throws XmlException {
         return (PreparedStatementType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PreparedStatementType.type, (XmlOptions)null);
      }

      public static PreparedStatementType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PreparedStatementType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PreparedStatementType.type, options);
      }

      public static PreparedStatementType parse(File file) throws XmlException, IOException {
         return (PreparedStatementType)XmlBeans.getContextTypeLoader().parse(file, PreparedStatementType.type, (XmlOptions)null);
      }

      public static PreparedStatementType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PreparedStatementType)XmlBeans.getContextTypeLoader().parse(file, PreparedStatementType.type, options);
      }

      public static PreparedStatementType parse(URL u) throws XmlException, IOException {
         return (PreparedStatementType)XmlBeans.getContextTypeLoader().parse(u, PreparedStatementType.type, (XmlOptions)null);
      }

      public static PreparedStatementType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PreparedStatementType)XmlBeans.getContextTypeLoader().parse(u, PreparedStatementType.type, options);
      }

      public static PreparedStatementType parse(InputStream is) throws XmlException, IOException {
         return (PreparedStatementType)XmlBeans.getContextTypeLoader().parse(is, PreparedStatementType.type, (XmlOptions)null);
      }

      public static PreparedStatementType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PreparedStatementType)XmlBeans.getContextTypeLoader().parse(is, PreparedStatementType.type, options);
      }

      public static PreparedStatementType parse(Reader r) throws XmlException, IOException {
         return (PreparedStatementType)XmlBeans.getContextTypeLoader().parse(r, PreparedStatementType.type, (XmlOptions)null);
      }

      public static PreparedStatementType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PreparedStatementType)XmlBeans.getContextTypeLoader().parse(r, PreparedStatementType.type, options);
      }

      public static PreparedStatementType parse(XMLStreamReader sr) throws XmlException {
         return (PreparedStatementType)XmlBeans.getContextTypeLoader().parse(sr, PreparedStatementType.type, (XmlOptions)null);
      }

      public static PreparedStatementType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PreparedStatementType)XmlBeans.getContextTypeLoader().parse(sr, PreparedStatementType.type, options);
      }

      public static PreparedStatementType parse(Node node) throws XmlException {
         return (PreparedStatementType)XmlBeans.getContextTypeLoader().parse(node, PreparedStatementType.type, (XmlOptions)null);
      }

      public static PreparedStatementType parse(Node node, XmlOptions options) throws XmlException {
         return (PreparedStatementType)XmlBeans.getContextTypeLoader().parse(node, PreparedStatementType.type, options);
      }

      /** @deprecated */
      public static PreparedStatementType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PreparedStatementType)XmlBeans.getContextTypeLoader().parse(xis, PreparedStatementType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PreparedStatementType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PreparedStatementType)XmlBeans.getContextTypeLoader().parse(xis, PreparedStatementType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PreparedStatementType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PreparedStatementType.type, options);
      }

      private Factory() {
      }
   }
}
