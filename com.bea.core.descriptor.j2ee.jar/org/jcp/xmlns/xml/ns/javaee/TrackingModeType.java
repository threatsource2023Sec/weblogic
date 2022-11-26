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

public interface TrackingModeType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TrackingModeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("trackingmodetype61e4type");
   Enum COOKIE = TrackingModeType.Enum.forString("COOKIE");
   Enum URL = TrackingModeType.Enum.forString("URL");
   Enum SSL = TrackingModeType.Enum.forString("SSL");
   int INT_COOKIE = 1;
   int INT_URL = 2;
   int INT_SSL = 3;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static TrackingModeType newInstance() {
         return (TrackingModeType)XmlBeans.getContextTypeLoader().newInstance(TrackingModeType.type, (XmlOptions)null);
      }

      public static TrackingModeType newInstance(XmlOptions options) {
         return (TrackingModeType)XmlBeans.getContextTypeLoader().newInstance(TrackingModeType.type, options);
      }

      public static TrackingModeType parse(java.lang.String xmlAsString) throws XmlException {
         return (TrackingModeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TrackingModeType.type, (XmlOptions)null);
      }

      public static TrackingModeType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (TrackingModeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TrackingModeType.type, options);
      }

      public static TrackingModeType parse(File file) throws XmlException, IOException {
         return (TrackingModeType)XmlBeans.getContextTypeLoader().parse(file, TrackingModeType.type, (XmlOptions)null);
      }

      public static TrackingModeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TrackingModeType)XmlBeans.getContextTypeLoader().parse(file, TrackingModeType.type, options);
      }

      public static TrackingModeType parse(URL u) throws XmlException, IOException {
         return (TrackingModeType)XmlBeans.getContextTypeLoader().parse(u, TrackingModeType.type, (XmlOptions)null);
      }

      public static TrackingModeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TrackingModeType)XmlBeans.getContextTypeLoader().parse(u, TrackingModeType.type, options);
      }

      public static TrackingModeType parse(InputStream is) throws XmlException, IOException {
         return (TrackingModeType)XmlBeans.getContextTypeLoader().parse(is, TrackingModeType.type, (XmlOptions)null);
      }

      public static TrackingModeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TrackingModeType)XmlBeans.getContextTypeLoader().parse(is, TrackingModeType.type, options);
      }

      public static TrackingModeType parse(Reader r) throws XmlException, IOException {
         return (TrackingModeType)XmlBeans.getContextTypeLoader().parse(r, TrackingModeType.type, (XmlOptions)null);
      }

      public static TrackingModeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TrackingModeType)XmlBeans.getContextTypeLoader().parse(r, TrackingModeType.type, options);
      }

      public static TrackingModeType parse(XMLStreamReader sr) throws XmlException {
         return (TrackingModeType)XmlBeans.getContextTypeLoader().parse(sr, TrackingModeType.type, (XmlOptions)null);
      }

      public static TrackingModeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TrackingModeType)XmlBeans.getContextTypeLoader().parse(sr, TrackingModeType.type, options);
      }

      public static TrackingModeType parse(Node node) throws XmlException {
         return (TrackingModeType)XmlBeans.getContextTypeLoader().parse(node, TrackingModeType.type, (XmlOptions)null);
      }

      public static TrackingModeType parse(Node node, XmlOptions options) throws XmlException {
         return (TrackingModeType)XmlBeans.getContextTypeLoader().parse(node, TrackingModeType.type, options);
      }

      /** @deprecated */
      public static TrackingModeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TrackingModeType)XmlBeans.getContextTypeLoader().parse(xis, TrackingModeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TrackingModeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TrackingModeType)XmlBeans.getContextTypeLoader().parse(xis, TrackingModeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TrackingModeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TrackingModeType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_COOKIE = 1;
      static final int INT_URL = 2;
      static final int INT_SSL = 3;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("COOKIE", 1), new Enum("URL", 2), new Enum("SSL", 3)});
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
