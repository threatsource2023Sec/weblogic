package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
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

public interface TimeSeededSeqType extends SequenceType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TimeSeededSeqType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("timeseededseqtype654atype");

   int getType();

   XmlInt xgetType();

   boolean isSetType();

   void setType(int var1);

   void xsetType(XmlInt var1);

   void unsetType();

   int getIncrement();

   XmlInt xgetIncrement();

   boolean isSetIncrement();

   void setIncrement(int var1);

   void xsetIncrement(XmlInt var1);

   void unsetIncrement();

   public static final class Factory {
      public static TimeSeededSeqType newInstance() {
         return (TimeSeededSeqType)XmlBeans.getContextTypeLoader().newInstance(TimeSeededSeqType.type, (XmlOptions)null);
      }

      public static TimeSeededSeqType newInstance(XmlOptions options) {
         return (TimeSeededSeqType)XmlBeans.getContextTypeLoader().newInstance(TimeSeededSeqType.type, options);
      }

      public static TimeSeededSeqType parse(String xmlAsString) throws XmlException {
         return (TimeSeededSeqType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TimeSeededSeqType.type, (XmlOptions)null);
      }

      public static TimeSeededSeqType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TimeSeededSeqType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TimeSeededSeqType.type, options);
      }

      public static TimeSeededSeqType parse(File file) throws XmlException, IOException {
         return (TimeSeededSeqType)XmlBeans.getContextTypeLoader().parse(file, TimeSeededSeqType.type, (XmlOptions)null);
      }

      public static TimeSeededSeqType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TimeSeededSeqType)XmlBeans.getContextTypeLoader().parse(file, TimeSeededSeqType.type, options);
      }

      public static TimeSeededSeqType parse(URL u) throws XmlException, IOException {
         return (TimeSeededSeqType)XmlBeans.getContextTypeLoader().parse(u, TimeSeededSeqType.type, (XmlOptions)null);
      }

      public static TimeSeededSeqType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TimeSeededSeqType)XmlBeans.getContextTypeLoader().parse(u, TimeSeededSeqType.type, options);
      }

      public static TimeSeededSeqType parse(InputStream is) throws XmlException, IOException {
         return (TimeSeededSeqType)XmlBeans.getContextTypeLoader().parse(is, TimeSeededSeqType.type, (XmlOptions)null);
      }

      public static TimeSeededSeqType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TimeSeededSeqType)XmlBeans.getContextTypeLoader().parse(is, TimeSeededSeqType.type, options);
      }

      public static TimeSeededSeqType parse(Reader r) throws XmlException, IOException {
         return (TimeSeededSeqType)XmlBeans.getContextTypeLoader().parse(r, TimeSeededSeqType.type, (XmlOptions)null);
      }

      public static TimeSeededSeqType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TimeSeededSeqType)XmlBeans.getContextTypeLoader().parse(r, TimeSeededSeqType.type, options);
      }

      public static TimeSeededSeqType parse(XMLStreamReader sr) throws XmlException {
         return (TimeSeededSeqType)XmlBeans.getContextTypeLoader().parse(sr, TimeSeededSeqType.type, (XmlOptions)null);
      }

      public static TimeSeededSeqType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TimeSeededSeqType)XmlBeans.getContextTypeLoader().parse(sr, TimeSeededSeqType.type, options);
      }

      public static TimeSeededSeqType parse(Node node) throws XmlException {
         return (TimeSeededSeqType)XmlBeans.getContextTypeLoader().parse(node, TimeSeededSeqType.type, (XmlOptions)null);
      }

      public static TimeSeededSeqType parse(Node node, XmlOptions options) throws XmlException {
         return (TimeSeededSeqType)XmlBeans.getContextTypeLoader().parse(node, TimeSeededSeqType.type, options);
      }

      /** @deprecated */
      public static TimeSeededSeqType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TimeSeededSeqType)XmlBeans.getContextTypeLoader().parse(xis, TimeSeededSeqType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TimeSeededSeqType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TimeSeededSeqType)XmlBeans.getContextTypeLoader().parse(xis, TimeSeededSeqType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TimeSeededSeqType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TimeSeededSeqType.type, options);
      }

      private Factory() {
      }
   }
}
