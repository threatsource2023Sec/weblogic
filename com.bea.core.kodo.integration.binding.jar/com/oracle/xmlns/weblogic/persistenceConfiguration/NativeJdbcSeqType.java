package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
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

public interface NativeJdbcSeqType extends SequenceType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(NativeJdbcSeqType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("nativejdbcseqtype1c89type");

   int getType();

   XmlInt xgetType();

   boolean isSetType();

   void setType(int var1);

   void xsetType(XmlInt var1);

   void unsetType();

   int getAllocate();

   XmlInt xgetAllocate();

   boolean isSetAllocate();

   void setAllocate(int var1);

   void xsetAllocate(XmlInt var1);

   void unsetAllocate();

   String getTableName();

   XmlString xgetTableName();

   boolean isNilTableName();

   boolean isSetTableName();

   void setTableName(String var1);

   void xsetTableName(XmlString var1);

   void setNilTableName();

   void unsetTableName();

   int getInitialValue();

   XmlInt xgetInitialValue();

   boolean isSetInitialValue();

   void setInitialValue(int var1);

   void xsetInitialValue(XmlInt var1);

   void unsetInitialValue();

   String getSequence();

   XmlString xgetSequence();

   boolean isNilSequence();

   boolean isSetSequence();

   void setSequence(String var1);

   void xsetSequence(XmlString var1);

   void setNilSequence();

   void unsetSequence();

   String getSequenceName();

   XmlString xgetSequenceName();

   boolean isNilSequenceName();

   boolean isSetSequenceName();

   void setSequenceName(String var1);

   void xsetSequenceName(XmlString var1);

   void setNilSequenceName();

   void unsetSequenceName();

   String getFormat();

   XmlString xgetFormat();

   boolean isNilFormat();

   boolean isSetFormat();

   void setFormat(String var1);

   void xsetFormat(XmlString var1);

   void setNilFormat();

   void unsetFormat();

   int getIncrement();

   XmlInt xgetIncrement();

   boolean isSetIncrement();

   void setIncrement(int var1);

   void xsetIncrement(XmlInt var1);

   void unsetIncrement();

   public static final class Factory {
      public static NativeJdbcSeqType newInstance() {
         return (NativeJdbcSeqType)XmlBeans.getContextTypeLoader().newInstance(NativeJdbcSeqType.type, (XmlOptions)null);
      }

      public static NativeJdbcSeqType newInstance(XmlOptions options) {
         return (NativeJdbcSeqType)XmlBeans.getContextTypeLoader().newInstance(NativeJdbcSeqType.type, options);
      }

      public static NativeJdbcSeqType parse(String xmlAsString) throws XmlException {
         return (NativeJdbcSeqType)XmlBeans.getContextTypeLoader().parse(xmlAsString, NativeJdbcSeqType.type, (XmlOptions)null);
      }

      public static NativeJdbcSeqType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (NativeJdbcSeqType)XmlBeans.getContextTypeLoader().parse(xmlAsString, NativeJdbcSeqType.type, options);
      }

      public static NativeJdbcSeqType parse(File file) throws XmlException, IOException {
         return (NativeJdbcSeqType)XmlBeans.getContextTypeLoader().parse(file, NativeJdbcSeqType.type, (XmlOptions)null);
      }

      public static NativeJdbcSeqType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (NativeJdbcSeqType)XmlBeans.getContextTypeLoader().parse(file, NativeJdbcSeqType.type, options);
      }

      public static NativeJdbcSeqType parse(URL u) throws XmlException, IOException {
         return (NativeJdbcSeqType)XmlBeans.getContextTypeLoader().parse(u, NativeJdbcSeqType.type, (XmlOptions)null);
      }

      public static NativeJdbcSeqType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (NativeJdbcSeqType)XmlBeans.getContextTypeLoader().parse(u, NativeJdbcSeqType.type, options);
      }

      public static NativeJdbcSeqType parse(InputStream is) throws XmlException, IOException {
         return (NativeJdbcSeqType)XmlBeans.getContextTypeLoader().parse(is, NativeJdbcSeqType.type, (XmlOptions)null);
      }

      public static NativeJdbcSeqType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (NativeJdbcSeqType)XmlBeans.getContextTypeLoader().parse(is, NativeJdbcSeqType.type, options);
      }

      public static NativeJdbcSeqType parse(Reader r) throws XmlException, IOException {
         return (NativeJdbcSeqType)XmlBeans.getContextTypeLoader().parse(r, NativeJdbcSeqType.type, (XmlOptions)null);
      }

      public static NativeJdbcSeqType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (NativeJdbcSeqType)XmlBeans.getContextTypeLoader().parse(r, NativeJdbcSeqType.type, options);
      }

      public static NativeJdbcSeqType parse(XMLStreamReader sr) throws XmlException {
         return (NativeJdbcSeqType)XmlBeans.getContextTypeLoader().parse(sr, NativeJdbcSeqType.type, (XmlOptions)null);
      }

      public static NativeJdbcSeqType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (NativeJdbcSeqType)XmlBeans.getContextTypeLoader().parse(sr, NativeJdbcSeqType.type, options);
      }

      public static NativeJdbcSeqType parse(Node node) throws XmlException {
         return (NativeJdbcSeqType)XmlBeans.getContextTypeLoader().parse(node, NativeJdbcSeqType.type, (XmlOptions)null);
      }

      public static NativeJdbcSeqType parse(Node node, XmlOptions options) throws XmlException {
         return (NativeJdbcSeqType)XmlBeans.getContextTypeLoader().parse(node, NativeJdbcSeqType.type, options);
      }

      /** @deprecated */
      public static NativeJdbcSeqType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (NativeJdbcSeqType)XmlBeans.getContextTypeLoader().parse(xis, NativeJdbcSeqType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static NativeJdbcSeqType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (NativeJdbcSeqType)XmlBeans.getContextTypeLoader().parse(xis, NativeJdbcSeqType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NativeJdbcSeqType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NativeJdbcSeqType.type, options);
      }

      private Factory() {
      }
   }
}
