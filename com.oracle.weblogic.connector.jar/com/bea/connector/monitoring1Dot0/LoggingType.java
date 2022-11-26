package com.bea.connector.monitoring1Dot0;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInteger;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigInteger;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface LoggingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LoggingType.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("loggingtype75d7type");

   String getLogFilename();

   XmlString xgetLogFilename();

   boolean isSetLogFilename();

   void setLogFilename(String var1);

   void xsetLogFilename(XmlString var1);

   void unsetLogFilename();

   boolean getLoggingEnabled();

   XmlBoolean xgetLoggingEnabled();

   boolean isSetLoggingEnabled();

   void setLoggingEnabled(boolean var1);

   void xsetLoggingEnabled(XmlBoolean var1);

   void unsetLoggingEnabled();

   String getRotationType();

   XmlString xgetRotationType();

   boolean isSetRotationType();

   void setRotationType(String var1);

   void xsetRotationType(XmlString var1);

   void unsetRotationType();

   boolean getNumberOfFilesLimited();

   XmlBoolean xgetNumberOfFilesLimited();

   boolean isSetNumberOfFilesLimited();

   void setNumberOfFilesLimited(boolean var1);

   void xsetNumberOfFilesLimited(XmlBoolean var1);

   void unsetNumberOfFilesLimited();

   BigInteger getFileCount();

   XmlInteger xgetFileCount();

   boolean isSetFileCount();

   void setFileCount(BigInteger var1);

   void xsetFileCount(XmlInteger var1);

   void unsetFileCount();

   BigInteger getFileSizeLimit();

   XmlInteger xgetFileSizeLimit();

   boolean isSetFileSizeLimit();

   void setFileSizeLimit(BigInteger var1);

   void xsetFileSizeLimit(XmlInteger var1);

   void unsetFileSizeLimit();

   boolean getRotateLogOnStartup();

   XmlBoolean xgetRotateLogOnStartup();

   boolean isSetRotateLogOnStartup();

   void setRotateLogOnStartup(boolean var1);

   void xsetRotateLogOnStartup(XmlBoolean var1);

   void unsetRotateLogOnStartup();

   String getLogFileRotationDir();

   XmlString xgetLogFileRotationDir();

   boolean isSetLogFileRotationDir();

   void setLogFileRotationDir(String var1);

   void xsetLogFileRotationDir(XmlString var1);

   void unsetLogFileRotationDir();

   String getRotationTime();

   XmlString xgetRotationTime();

   boolean isSetRotationTime();

   void setRotationTime(String var1);

   void xsetRotationTime(XmlString var1);

   void unsetRotationTime();

   BigInteger getFileTimeSpan();

   XmlInteger xgetFileTimeSpan();

   boolean isSetFileTimeSpan();

   void setFileTimeSpan(BigInteger var1);

   void xsetFileTimeSpan(XmlInteger var1);

   void unsetFileTimeSpan();

   String getDateFormatPattern();

   XmlString xgetDateFormatPattern();

   boolean isSetDateFormatPattern();

   void setDateFormatPattern(String var1);

   void xsetDateFormatPattern(XmlString var1);

   void unsetDateFormatPattern();

   public static final class Factory {
      public static LoggingType newInstance() {
         return (LoggingType)XmlBeans.getContextTypeLoader().newInstance(LoggingType.type, (XmlOptions)null);
      }

      public static LoggingType newInstance(XmlOptions options) {
         return (LoggingType)XmlBeans.getContextTypeLoader().newInstance(LoggingType.type, options);
      }

      public static LoggingType parse(String xmlAsString) throws XmlException {
         return (LoggingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LoggingType.type, (XmlOptions)null);
      }

      public static LoggingType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (LoggingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LoggingType.type, options);
      }

      public static LoggingType parse(File file) throws XmlException, IOException {
         return (LoggingType)XmlBeans.getContextTypeLoader().parse(file, LoggingType.type, (XmlOptions)null);
      }

      public static LoggingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LoggingType)XmlBeans.getContextTypeLoader().parse(file, LoggingType.type, options);
      }

      public static LoggingType parse(URL u) throws XmlException, IOException {
         return (LoggingType)XmlBeans.getContextTypeLoader().parse(u, LoggingType.type, (XmlOptions)null);
      }

      public static LoggingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LoggingType)XmlBeans.getContextTypeLoader().parse(u, LoggingType.type, options);
      }

      public static LoggingType parse(InputStream is) throws XmlException, IOException {
         return (LoggingType)XmlBeans.getContextTypeLoader().parse(is, LoggingType.type, (XmlOptions)null);
      }

      public static LoggingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LoggingType)XmlBeans.getContextTypeLoader().parse(is, LoggingType.type, options);
      }

      public static LoggingType parse(Reader r) throws XmlException, IOException {
         return (LoggingType)XmlBeans.getContextTypeLoader().parse(r, LoggingType.type, (XmlOptions)null);
      }

      public static LoggingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LoggingType)XmlBeans.getContextTypeLoader().parse(r, LoggingType.type, options);
      }

      public static LoggingType parse(XMLStreamReader sr) throws XmlException {
         return (LoggingType)XmlBeans.getContextTypeLoader().parse(sr, LoggingType.type, (XmlOptions)null);
      }

      public static LoggingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LoggingType)XmlBeans.getContextTypeLoader().parse(sr, LoggingType.type, options);
      }

      public static LoggingType parse(Node node) throws XmlException {
         return (LoggingType)XmlBeans.getContextTypeLoader().parse(node, LoggingType.type, (XmlOptions)null);
      }

      public static LoggingType parse(Node node, XmlOptions options) throws XmlException {
         return (LoggingType)XmlBeans.getContextTypeLoader().parse(node, LoggingType.type, options);
      }

      /** @deprecated */
      public static LoggingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LoggingType)XmlBeans.getContextTypeLoader().parse(xis, LoggingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LoggingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LoggingType)XmlBeans.getContextTypeLoader().parse(xis, LoggingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LoggingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LoggingType.type, options);
      }

      private Factory() {
      }
   }
}
