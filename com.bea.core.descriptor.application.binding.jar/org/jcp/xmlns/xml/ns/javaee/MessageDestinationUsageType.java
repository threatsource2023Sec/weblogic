package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface MessageDestinationUsageType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MessageDestinationUsageType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("messagedestinationusagetypedb21type");
   Enum CONSUMES = MessageDestinationUsageType.Enum.forString("Consumes");
   Enum PRODUCES = MessageDestinationUsageType.Enum.forString("Produces");
   Enum CONSUMES_PRODUCES = MessageDestinationUsageType.Enum.forString("ConsumesProduces");
   int INT_CONSUMES = 1;
   int INT_PRODUCES = 2;
   int INT_CONSUMES_PRODUCES = 3;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static MessageDestinationUsageType newInstance() {
         return (MessageDestinationUsageType)XmlBeans.getContextTypeLoader().newInstance(MessageDestinationUsageType.type, (XmlOptions)null);
      }

      public static MessageDestinationUsageType newInstance(XmlOptions options) {
         return (MessageDestinationUsageType)XmlBeans.getContextTypeLoader().newInstance(MessageDestinationUsageType.type, options);
      }

      public static MessageDestinationUsageType parse(java.lang.String xmlAsString) throws XmlException {
         return (MessageDestinationUsageType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MessageDestinationUsageType.type, (XmlOptions)null);
      }

      public static MessageDestinationUsageType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (MessageDestinationUsageType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MessageDestinationUsageType.type, options);
      }

      public static MessageDestinationUsageType parse(File file) throws XmlException, IOException {
         return (MessageDestinationUsageType)XmlBeans.getContextTypeLoader().parse(file, MessageDestinationUsageType.type, (XmlOptions)null);
      }

      public static MessageDestinationUsageType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MessageDestinationUsageType)XmlBeans.getContextTypeLoader().parse(file, MessageDestinationUsageType.type, options);
      }

      public static MessageDestinationUsageType parse(URL u) throws XmlException, IOException {
         return (MessageDestinationUsageType)XmlBeans.getContextTypeLoader().parse(u, MessageDestinationUsageType.type, (XmlOptions)null);
      }

      public static MessageDestinationUsageType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MessageDestinationUsageType)XmlBeans.getContextTypeLoader().parse(u, MessageDestinationUsageType.type, options);
      }

      public static MessageDestinationUsageType parse(InputStream is) throws XmlException, IOException {
         return (MessageDestinationUsageType)XmlBeans.getContextTypeLoader().parse(is, MessageDestinationUsageType.type, (XmlOptions)null);
      }

      public static MessageDestinationUsageType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MessageDestinationUsageType)XmlBeans.getContextTypeLoader().parse(is, MessageDestinationUsageType.type, options);
      }

      public static MessageDestinationUsageType parse(Reader r) throws XmlException, IOException {
         return (MessageDestinationUsageType)XmlBeans.getContextTypeLoader().parse(r, MessageDestinationUsageType.type, (XmlOptions)null);
      }

      public static MessageDestinationUsageType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MessageDestinationUsageType)XmlBeans.getContextTypeLoader().parse(r, MessageDestinationUsageType.type, options);
      }

      public static MessageDestinationUsageType parse(XMLStreamReader sr) throws XmlException {
         return (MessageDestinationUsageType)XmlBeans.getContextTypeLoader().parse(sr, MessageDestinationUsageType.type, (XmlOptions)null);
      }

      public static MessageDestinationUsageType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MessageDestinationUsageType)XmlBeans.getContextTypeLoader().parse(sr, MessageDestinationUsageType.type, options);
      }

      public static MessageDestinationUsageType parse(Node node) throws XmlException {
         return (MessageDestinationUsageType)XmlBeans.getContextTypeLoader().parse(node, MessageDestinationUsageType.type, (XmlOptions)null);
      }

      public static MessageDestinationUsageType parse(Node node, XmlOptions options) throws XmlException {
         return (MessageDestinationUsageType)XmlBeans.getContextTypeLoader().parse(node, MessageDestinationUsageType.type, options);
      }

      /** @deprecated */
      public static MessageDestinationUsageType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MessageDestinationUsageType)XmlBeans.getContextTypeLoader().parse(xis, MessageDestinationUsageType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MessageDestinationUsageType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MessageDestinationUsageType)XmlBeans.getContextTypeLoader().parse(xis, MessageDestinationUsageType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MessageDestinationUsageType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MessageDestinationUsageType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_CONSUMES = 1;
      static final int INT_PRODUCES = 2;
      static final int INT_CONSUMES_PRODUCES = 3;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("Consumes", 1), new Enum("Produces", 2), new Enum("ConsumesProduces", 3)});
      private static final long serialVersionUID = 1L;

      public static Enum forString(java.lang.String s) {
         return (Enum)table.forString(s);
      }

      public static Enum forInt(int i) {
         return (Enum)table.forInt(i);
      }

      private Enum(java.lang.String s, int i) {
         super(s, i);
      }

      private Object readResolve() {
         return forInt(this.intValue());
      }
   }
}
