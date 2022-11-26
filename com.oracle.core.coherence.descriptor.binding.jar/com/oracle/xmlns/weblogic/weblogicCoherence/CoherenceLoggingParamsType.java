package com.oracle.xmlns.weblogic.weblogicCoherence;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import com.sun.java.xml.ns.javaee.TrueFalseType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface CoherenceLoggingParamsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CoherenceLoggingParamsType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("coherenceloggingparamstype876ctype");

   TrueFalseType getEnabled();

   boolean isSetEnabled();

   void setEnabled(TrueFalseType var1);

   TrueFalseType addNewEnabled();

   void unsetEnabled();

   String getLoggerName();

   XmlString xgetLoggerName();

   boolean isNilLoggerName();

   boolean isSetLoggerName();

   void setLoggerName(String var1);

   void xsetLoggerName(XmlString var1);

   void setNilLoggerName();

   void unsetLoggerName();

   String getMessageFormat();

   XmlString xgetMessageFormat();

   boolean isNilMessageFormat();

   boolean isSetMessageFormat();

   void setMessageFormat(String var1);

   void xsetMessageFormat(XmlString var1);

   void setNilMessageFormat();

   void unsetMessageFormat();

   public static final class Factory {
      public static CoherenceLoggingParamsType newInstance() {
         return (CoherenceLoggingParamsType)XmlBeans.getContextTypeLoader().newInstance(CoherenceLoggingParamsType.type, (XmlOptions)null);
      }

      public static CoherenceLoggingParamsType newInstance(XmlOptions options) {
         return (CoherenceLoggingParamsType)XmlBeans.getContextTypeLoader().newInstance(CoherenceLoggingParamsType.type, options);
      }

      public static CoherenceLoggingParamsType parse(String xmlAsString) throws XmlException {
         return (CoherenceLoggingParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CoherenceLoggingParamsType.type, (XmlOptions)null);
      }

      public static CoherenceLoggingParamsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CoherenceLoggingParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CoherenceLoggingParamsType.type, options);
      }

      public static CoherenceLoggingParamsType parse(File file) throws XmlException, IOException {
         return (CoherenceLoggingParamsType)XmlBeans.getContextTypeLoader().parse(file, CoherenceLoggingParamsType.type, (XmlOptions)null);
      }

      public static CoherenceLoggingParamsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CoherenceLoggingParamsType)XmlBeans.getContextTypeLoader().parse(file, CoherenceLoggingParamsType.type, options);
      }

      public static CoherenceLoggingParamsType parse(URL u) throws XmlException, IOException {
         return (CoherenceLoggingParamsType)XmlBeans.getContextTypeLoader().parse(u, CoherenceLoggingParamsType.type, (XmlOptions)null);
      }

      public static CoherenceLoggingParamsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CoherenceLoggingParamsType)XmlBeans.getContextTypeLoader().parse(u, CoherenceLoggingParamsType.type, options);
      }

      public static CoherenceLoggingParamsType parse(InputStream is) throws XmlException, IOException {
         return (CoherenceLoggingParamsType)XmlBeans.getContextTypeLoader().parse(is, CoherenceLoggingParamsType.type, (XmlOptions)null);
      }

      public static CoherenceLoggingParamsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CoherenceLoggingParamsType)XmlBeans.getContextTypeLoader().parse(is, CoherenceLoggingParamsType.type, options);
      }

      public static CoherenceLoggingParamsType parse(Reader r) throws XmlException, IOException {
         return (CoherenceLoggingParamsType)XmlBeans.getContextTypeLoader().parse(r, CoherenceLoggingParamsType.type, (XmlOptions)null);
      }

      public static CoherenceLoggingParamsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CoherenceLoggingParamsType)XmlBeans.getContextTypeLoader().parse(r, CoherenceLoggingParamsType.type, options);
      }

      public static CoherenceLoggingParamsType parse(XMLStreamReader sr) throws XmlException {
         return (CoherenceLoggingParamsType)XmlBeans.getContextTypeLoader().parse(sr, CoherenceLoggingParamsType.type, (XmlOptions)null);
      }

      public static CoherenceLoggingParamsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CoherenceLoggingParamsType)XmlBeans.getContextTypeLoader().parse(sr, CoherenceLoggingParamsType.type, options);
      }

      public static CoherenceLoggingParamsType parse(Node node) throws XmlException {
         return (CoherenceLoggingParamsType)XmlBeans.getContextTypeLoader().parse(node, CoherenceLoggingParamsType.type, (XmlOptions)null);
      }

      public static CoherenceLoggingParamsType parse(Node node, XmlOptions options) throws XmlException {
         return (CoherenceLoggingParamsType)XmlBeans.getContextTypeLoader().parse(node, CoherenceLoggingParamsType.type, options);
      }

      /** @deprecated */
      public static CoherenceLoggingParamsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CoherenceLoggingParamsType)XmlBeans.getContextTypeLoader().parse(xis, CoherenceLoggingParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CoherenceLoggingParamsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CoherenceLoggingParamsType)XmlBeans.getContextTypeLoader().parse(xis, CoherenceLoggingParamsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CoherenceLoggingParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CoherenceLoggingParamsType.type, options);
      }

      private Factory() {
      }
   }
}
