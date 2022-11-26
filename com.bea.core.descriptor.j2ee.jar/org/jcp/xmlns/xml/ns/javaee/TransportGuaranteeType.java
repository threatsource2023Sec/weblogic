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

public interface TransportGuaranteeType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TransportGuaranteeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("transportguaranteetype6ad9type");
   Enum NONE = TransportGuaranteeType.Enum.forString("NONE");
   Enum INTEGRAL = TransportGuaranteeType.Enum.forString("INTEGRAL");
   Enum CONFIDENTIAL = TransportGuaranteeType.Enum.forString("CONFIDENTIAL");
   int INT_NONE = 1;
   int INT_INTEGRAL = 2;
   int INT_CONFIDENTIAL = 3;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static TransportGuaranteeType newInstance() {
         return (TransportGuaranteeType)XmlBeans.getContextTypeLoader().newInstance(TransportGuaranteeType.type, (XmlOptions)null);
      }

      public static TransportGuaranteeType newInstance(XmlOptions options) {
         return (TransportGuaranteeType)XmlBeans.getContextTypeLoader().newInstance(TransportGuaranteeType.type, options);
      }

      public static TransportGuaranteeType parse(java.lang.String xmlAsString) throws XmlException {
         return (TransportGuaranteeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TransportGuaranteeType.type, (XmlOptions)null);
      }

      public static TransportGuaranteeType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (TransportGuaranteeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TransportGuaranteeType.type, options);
      }

      public static TransportGuaranteeType parse(File file) throws XmlException, IOException {
         return (TransportGuaranteeType)XmlBeans.getContextTypeLoader().parse(file, TransportGuaranteeType.type, (XmlOptions)null);
      }

      public static TransportGuaranteeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TransportGuaranteeType)XmlBeans.getContextTypeLoader().parse(file, TransportGuaranteeType.type, options);
      }

      public static TransportGuaranteeType parse(URL u) throws XmlException, IOException {
         return (TransportGuaranteeType)XmlBeans.getContextTypeLoader().parse(u, TransportGuaranteeType.type, (XmlOptions)null);
      }

      public static TransportGuaranteeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TransportGuaranteeType)XmlBeans.getContextTypeLoader().parse(u, TransportGuaranteeType.type, options);
      }

      public static TransportGuaranteeType parse(InputStream is) throws XmlException, IOException {
         return (TransportGuaranteeType)XmlBeans.getContextTypeLoader().parse(is, TransportGuaranteeType.type, (XmlOptions)null);
      }

      public static TransportGuaranteeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TransportGuaranteeType)XmlBeans.getContextTypeLoader().parse(is, TransportGuaranteeType.type, options);
      }

      public static TransportGuaranteeType parse(Reader r) throws XmlException, IOException {
         return (TransportGuaranteeType)XmlBeans.getContextTypeLoader().parse(r, TransportGuaranteeType.type, (XmlOptions)null);
      }

      public static TransportGuaranteeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TransportGuaranteeType)XmlBeans.getContextTypeLoader().parse(r, TransportGuaranteeType.type, options);
      }

      public static TransportGuaranteeType parse(XMLStreamReader sr) throws XmlException {
         return (TransportGuaranteeType)XmlBeans.getContextTypeLoader().parse(sr, TransportGuaranteeType.type, (XmlOptions)null);
      }

      public static TransportGuaranteeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TransportGuaranteeType)XmlBeans.getContextTypeLoader().parse(sr, TransportGuaranteeType.type, options);
      }

      public static TransportGuaranteeType parse(Node node) throws XmlException {
         return (TransportGuaranteeType)XmlBeans.getContextTypeLoader().parse(node, TransportGuaranteeType.type, (XmlOptions)null);
      }

      public static TransportGuaranteeType parse(Node node, XmlOptions options) throws XmlException {
         return (TransportGuaranteeType)XmlBeans.getContextTypeLoader().parse(node, TransportGuaranteeType.type, options);
      }

      /** @deprecated */
      public static TransportGuaranteeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TransportGuaranteeType)XmlBeans.getContextTypeLoader().parse(xis, TransportGuaranteeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TransportGuaranteeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TransportGuaranteeType)XmlBeans.getContextTypeLoader().parse(xis, TransportGuaranteeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TransportGuaranteeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TransportGuaranteeType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_NONE = 1;
      static final int INT_INTEGRAL = 2;
      static final int INT_CONFIDENTIAL = 3;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("NONE", 1), new Enum("INTEGRAL", 2), new Enum("CONFIDENTIAL", 3)});
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
