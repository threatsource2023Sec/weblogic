package com.oracle.xmlns.weblogic.weblogicJms;

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

public interface AcknowledgePolicyType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AcknowledgePolicyType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("acknowledgepolicytypee813type");
   Enum ALL = AcknowledgePolicyType.Enum.forString("All");
   Enum PREVIOUS = AcknowledgePolicyType.Enum.forString("Previous");
   Enum ONE = AcknowledgePolicyType.Enum.forString("One");
   int INT_ALL = 1;
   int INT_PREVIOUS = 2;
   int INT_ONE = 3;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static AcknowledgePolicyType newValue(Object obj) {
         return (AcknowledgePolicyType)AcknowledgePolicyType.type.newValue(obj);
      }

      public static AcknowledgePolicyType newInstance() {
         return (AcknowledgePolicyType)XmlBeans.getContextTypeLoader().newInstance(AcknowledgePolicyType.type, (XmlOptions)null);
      }

      public static AcknowledgePolicyType newInstance(XmlOptions options) {
         return (AcknowledgePolicyType)XmlBeans.getContextTypeLoader().newInstance(AcknowledgePolicyType.type, options);
      }

      public static AcknowledgePolicyType parse(String xmlAsString) throws XmlException {
         return (AcknowledgePolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AcknowledgePolicyType.type, (XmlOptions)null);
      }

      public static AcknowledgePolicyType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AcknowledgePolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AcknowledgePolicyType.type, options);
      }

      public static AcknowledgePolicyType parse(File file) throws XmlException, IOException {
         return (AcknowledgePolicyType)XmlBeans.getContextTypeLoader().parse(file, AcknowledgePolicyType.type, (XmlOptions)null);
      }

      public static AcknowledgePolicyType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AcknowledgePolicyType)XmlBeans.getContextTypeLoader().parse(file, AcknowledgePolicyType.type, options);
      }

      public static AcknowledgePolicyType parse(URL u) throws XmlException, IOException {
         return (AcknowledgePolicyType)XmlBeans.getContextTypeLoader().parse(u, AcknowledgePolicyType.type, (XmlOptions)null);
      }

      public static AcknowledgePolicyType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AcknowledgePolicyType)XmlBeans.getContextTypeLoader().parse(u, AcknowledgePolicyType.type, options);
      }

      public static AcknowledgePolicyType parse(InputStream is) throws XmlException, IOException {
         return (AcknowledgePolicyType)XmlBeans.getContextTypeLoader().parse(is, AcknowledgePolicyType.type, (XmlOptions)null);
      }

      public static AcknowledgePolicyType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AcknowledgePolicyType)XmlBeans.getContextTypeLoader().parse(is, AcknowledgePolicyType.type, options);
      }

      public static AcknowledgePolicyType parse(Reader r) throws XmlException, IOException {
         return (AcknowledgePolicyType)XmlBeans.getContextTypeLoader().parse(r, AcknowledgePolicyType.type, (XmlOptions)null);
      }

      public static AcknowledgePolicyType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AcknowledgePolicyType)XmlBeans.getContextTypeLoader().parse(r, AcknowledgePolicyType.type, options);
      }

      public static AcknowledgePolicyType parse(XMLStreamReader sr) throws XmlException {
         return (AcknowledgePolicyType)XmlBeans.getContextTypeLoader().parse(sr, AcknowledgePolicyType.type, (XmlOptions)null);
      }

      public static AcknowledgePolicyType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AcknowledgePolicyType)XmlBeans.getContextTypeLoader().parse(sr, AcknowledgePolicyType.type, options);
      }

      public static AcknowledgePolicyType parse(Node node) throws XmlException {
         return (AcknowledgePolicyType)XmlBeans.getContextTypeLoader().parse(node, AcknowledgePolicyType.type, (XmlOptions)null);
      }

      public static AcknowledgePolicyType parse(Node node, XmlOptions options) throws XmlException {
         return (AcknowledgePolicyType)XmlBeans.getContextTypeLoader().parse(node, AcknowledgePolicyType.type, options);
      }

      /** @deprecated */
      public static AcknowledgePolicyType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AcknowledgePolicyType)XmlBeans.getContextTypeLoader().parse(xis, AcknowledgePolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AcknowledgePolicyType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AcknowledgePolicyType)XmlBeans.getContextTypeLoader().parse(xis, AcknowledgePolicyType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AcknowledgePolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AcknowledgePolicyType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_ALL = 1;
      static final int INT_PREVIOUS = 2;
      static final int INT_ONE = 3;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("All", 1), new Enum("Previous", 2), new Enum("One", 3)});
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
