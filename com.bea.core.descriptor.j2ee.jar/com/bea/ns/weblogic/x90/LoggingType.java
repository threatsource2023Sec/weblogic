package com.bea.ns.weblogic.x90;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.j2Ee.String;
import com.sun.java.xml.ns.j2Ee.XsdPositiveIntegerType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface LoggingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LoggingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("loggingtype11e9type");

   String getLogFilename();

   boolean isSetLogFilename();

   void setLogFilename(String var1);

   String addNewLogFilename();

   void unsetLogFilename();

   TrueFalseType getLoggingEnabled();

   boolean isSetLoggingEnabled();

   void setLoggingEnabled(TrueFalseType var1);

   TrueFalseType addNewLoggingEnabled();

   void unsetLoggingEnabled();

   String getRotationType();

   boolean isSetRotationType();

   void setRotationType(String var1);

   String addNewRotationType();

   void unsetRotationType();

   TrueFalseType getNumberOfFilesLimited();

   boolean isSetNumberOfFilesLimited();

   void setNumberOfFilesLimited(TrueFalseType var1);

   TrueFalseType addNewNumberOfFilesLimited();

   void unsetNumberOfFilesLimited();

   XsdPositiveIntegerType getFileCount();

   boolean isSetFileCount();

   void setFileCount(XsdPositiveIntegerType var1);

   XsdPositiveIntegerType addNewFileCount();

   void unsetFileCount();

   XsdPositiveIntegerType getFileSizeLimit();

   boolean isSetFileSizeLimit();

   void setFileSizeLimit(XsdPositiveIntegerType var1);

   XsdPositiveIntegerType addNewFileSizeLimit();

   void unsetFileSizeLimit();

   TrueFalseType getRotateLogOnStartup();

   boolean isSetRotateLogOnStartup();

   void setRotateLogOnStartup(TrueFalseType var1);

   TrueFalseType addNewRotateLogOnStartup();

   void unsetRotateLogOnStartup();

   String getLogFileRotationDir();

   boolean isSetLogFileRotationDir();

   void setLogFileRotationDir(String var1);

   String addNewLogFileRotationDir();

   void unsetLogFileRotationDir();

   String getRotationTime();

   boolean isSetRotationTime();

   void setRotationTime(String var1);

   String addNewRotationTime();

   void unsetRotationTime();

   XsdPositiveIntegerType getFileTimeSpan();

   boolean isSetFileTimeSpan();

   void setFileTimeSpan(XsdPositiveIntegerType var1);

   XsdPositiveIntegerType addNewFileTimeSpan();

   void unsetFileTimeSpan();

   String getDateFormatPattern();

   boolean isSetDateFormatPattern();

   void setDateFormatPattern(String var1);

   String addNewDateFormatPattern();

   void unsetDateFormatPattern();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static LoggingType newInstance() {
         return (LoggingType)XmlBeans.getContextTypeLoader().newInstance(LoggingType.type, (XmlOptions)null);
      }

      public static LoggingType newInstance(XmlOptions options) {
         return (LoggingType)XmlBeans.getContextTypeLoader().newInstance(LoggingType.type, options);
      }

      public static LoggingType parse(java.lang.String xmlAsString) throws XmlException {
         return (LoggingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LoggingType.type, (XmlOptions)null);
      }

      public static LoggingType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
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
