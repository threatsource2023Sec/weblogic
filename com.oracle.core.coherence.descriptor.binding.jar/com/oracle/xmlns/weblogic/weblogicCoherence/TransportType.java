package com.oracle.xmlns.weblogic.weblogicCoherence;

import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface TransportType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TransportType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("transporttypefb03type");
   Enum UDP = TransportType.Enum.forString("udp");
   Enum TCP = TransportType.Enum.forString("tcp");
   Enum SSL = TransportType.Enum.forString("ssl");
   Enum TMB = TransportType.Enum.forString("tmb");
   Enum SDMB = TransportType.Enum.forString("sdmb");
   Enum IMB = TransportType.Enum.forString("imb");
   int INT_UDP = 1;
   int INT_TCP = 2;
   int INT_SSL = 3;
   int INT_TMB = 4;
   int INT_SDMB = 5;
   int INT_IMB = 6;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static TransportType newValue(Object obj) {
         return (TransportType)TransportType.type.newValue(obj);
      }

      public static TransportType newInstance() {
         return (TransportType)XmlBeans.getContextTypeLoader().newInstance(TransportType.type, (XmlOptions)null);
      }

      public static TransportType newInstance(XmlOptions options) {
         return (TransportType)XmlBeans.getContextTypeLoader().newInstance(TransportType.type, options);
      }

      public static TransportType parse(String xmlAsString) throws XmlException {
         return (TransportType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TransportType.type, (XmlOptions)null);
      }

      public static TransportType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TransportType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TransportType.type, options);
      }

      public static TransportType parse(File file) throws XmlException, IOException {
         return (TransportType)XmlBeans.getContextTypeLoader().parse(file, TransportType.type, (XmlOptions)null);
      }

      public static TransportType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TransportType)XmlBeans.getContextTypeLoader().parse(file, TransportType.type, options);
      }

      public static TransportType parse(URL u) throws XmlException, IOException {
         return (TransportType)XmlBeans.getContextTypeLoader().parse(u, TransportType.type, (XmlOptions)null);
      }

      public static TransportType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TransportType)XmlBeans.getContextTypeLoader().parse(u, TransportType.type, options);
      }

      public static TransportType parse(InputStream is) throws XmlException, IOException {
         return (TransportType)XmlBeans.getContextTypeLoader().parse(is, TransportType.type, (XmlOptions)null);
      }

      public static TransportType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TransportType)XmlBeans.getContextTypeLoader().parse(is, TransportType.type, options);
      }

      public static TransportType parse(Reader r) throws XmlException, IOException {
         return (TransportType)XmlBeans.getContextTypeLoader().parse(r, TransportType.type, (XmlOptions)null);
      }

      public static TransportType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TransportType)XmlBeans.getContextTypeLoader().parse(r, TransportType.type, options);
      }

      public static TransportType parse(XMLStreamReader sr) throws XmlException {
         return (TransportType)XmlBeans.getContextTypeLoader().parse(sr, TransportType.type, (XmlOptions)null);
      }

      public static TransportType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TransportType)XmlBeans.getContextTypeLoader().parse(sr, TransportType.type, options);
      }

      public static TransportType parse(Node node) throws XmlException {
         return (TransportType)XmlBeans.getContextTypeLoader().parse(node, TransportType.type, (XmlOptions)null);
      }

      public static TransportType parse(Node node, XmlOptions options) throws XmlException {
         return (TransportType)XmlBeans.getContextTypeLoader().parse(node, TransportType.type, options);
      }

      /** @deprecated */
      public static TransportType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TransportType)XmlBeans.getContextTypeLoader().parse(xis, TransportType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TransportType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TransportType)XmlBeans.getContextTypeLoader().parse(xis, TransportType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TransportType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TransportType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_UDP = 1;
      static final int INT_TCP = 2;
      static final int INT_SSL = 3;
      static final int INT_TMB = 4;
      static final int INT_SDMB = 5;
      static final int INT_IMB = 6;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("udp", 1), new Enum("tcp", 2), new Enum("ssl", 3), new Enum("tmb", 4), new Enum("sdmb", 5), new Enum("imb", 6)});
      private static final long serialVersionUID = 1L;

      public static Enum forString(String s) {
         return (Enum)table.forString(s);
      }

      public static Enum forInt(int i) {
         return (Enum)table.forInt(i);
      }

      private Enum(String s, int i) {
         super(s, i);
      }

      private Object readResolve() {
         return forInt(this.intValue());
      }
   }
}
