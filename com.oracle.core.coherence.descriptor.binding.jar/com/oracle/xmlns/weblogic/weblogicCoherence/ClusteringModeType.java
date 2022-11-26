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

public interface ClusteringModeType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ClusteringModeType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("clusteringmodetypec6f5type");
   Enum MULTICAST = ClusteringModeType.Enum.forString("multicast");
   Enum UNICAST = ClusteringModeType.Enum.forString("unicast");
   int INT_MULTICAST = 1;
   int INT_UNICAST = 2;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static ClusteringModeType newValue(Object obj) {
         return (ClusteringModeType)ClusteringModeType.type.newValue(obj);
      }

      public static ClusteringModeType newInstance() {
         return (ClusteringModeType)XmlBeans.getContextTypeLoader().newInstance(ClusteringModeType.type, (XmlOptions)null);
      }

      public static ClusteringModeType newInstance(XmlOptions options) {
         return (ClusteringModeType)XmlBeans.getContextTypeLoader().newInstance(ClusteringModeType.type, options);
      }

      public static ClusteringModeType parse(String xmlAsString) throws XmlException {
         return (ClusteringModeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ClusteringModeType.type, (XmlOptions)null);
      }

      public static ClusteringModeType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ClusteringModeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ClusteringModeType.type, options);
      }

      public static ClusteringModeType parse(File file) throws XmlException, IOException {
         return (ClusteringModeType)XmlBeans.getContextTypeLoader().parse(file, ClusteringModeType.type, (XmlOptions)null);
      }

      public static ClusteringModeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ClusteringModeType)XmlBeans.getContextTypeLoader().parse(file, ClusteringModeType.type, options);
      }

      public static ClusteringModeType parse(URL u) throws XmlException, IOException {
         return (ClusteringModeType)XmlBeans.getContextTypeLoader().parse(u, ClusteringModeType.type, (XmlOptions)null);
      }

      public static ClusteringModeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ClusteringModeType)XmlBeans.getContextTypeLoader().parse(u, ClusteringModeType.type, options);
      }

      public static ClusteringModeType parse(InputStream is) throws XmlException, IOException {
         return (ClusteringModeType)XmlBeans.getContextTypeLoader().parse(is, ClusteringModeType.type, (XmlOptions)null);
      }

      public static ClusteringModeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ClusteringModeType)XmlBeans.getContextTypeLoader().parse(is, ClusteringModeType.type, options);
      }

      public static ClusteringModeType parse(Reader r) throws XmlException, IOException {
         return (ClusteringModeType)XmlBeans.getContextTypeLoader().parse(r, ClusteringModeType.type, (XmlOptions)null);
      }

      public static ClusteringModeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ClusteringModeType)XmlBeans.getContextTypeLoader().parse(r, ClusteringModeType.type, options);
      }

      public static ClusteringModeType parse(XMLStreamReader sr) throws XmlException {
         return (ClusteringModeType)XmlBeans.getContextTypeLoader().parse(sr, ClusteringModeType.type, (XmlOptions)null);
      }

      public static ClusteringModeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ClusteringModeType)XmlBeans.getContextTypeLoader().parse(sr, ClusteringModeType.type, options);
      }

      public static ClusteringModeType parse(Node node) throws XmlException {
         return (ClusteringModeType)XmlBeans.getContextTypeLoader().parse(node, ClusteringModeType.type, (XmlOptions)null);
      }

      public static ClusteringModeType parse(Node node, XmlOptions options) throws XmlException {
         return (ClusteringModeType)XmlBeans.getContextTypeLoader().parse(node, ClusteringModeType.type, options);
      }

      /** @deprecated */
      public static ClusteringModeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ClusteringModeType)XmlBeans.getContextTypeLoader().parse(xis, ClusteringModeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ClusteringModeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ClusteringModeType)XmlBeans.getContextTypeLoader().parse(xis, ClusteringModeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ClusteringModeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ClusteringModeType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_MULTICAST = 1;
      static final int INT_UNICAST = 2;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("multicast", 1), new Enum("unicast", 2)});
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
