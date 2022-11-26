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

public interface ConcurrentLockTypeType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConcurrentLockTypeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("concurrentlocktypetype1e45type");
   Enum READ = ConcurrentLockTypeType.Enum.forString("Read");
   Enum WRITE = ConcurrentLockTypeType.Enum.forString("Write");
   int INT_READ = 1;
   int INT_WRITE = 2;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static ConcurrentLockTypeType newInstance() {
         return (ConcurrentLockTypeType)XmlBeans.getContextTypeLoader().newInstance(ConcurrentLockTypeType.type, (XmlOptions)null);
      }

      public static ConcurrentLockTypeType newInstance(XmlOptions options) {
         return (ConcurrentLockTypeType)XmlBeans.getContextTypeLoader().newInstance(ConcurrentLockTypeType.type, options);
      }

      public static ConcurrentLockTypeType parse(java.lang.String xmlAsString) throws XmlException {
         return (ConcurrentLockTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConcurrentLockTypeType.type, (XmlOptions)null);
      }

      public static ConcurrentLockTypeType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ConcurrentLockTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConcurrentLockTypeType.type, options);
      }

      public static ConcurrentLockTypeType parse(File file) throws XmlException, IOException {
         return (ConcurrentLockTypeType)XmlBeans.getContextTypeLoader().parse(file, ConcurrentLockTypeType.type, (XmlOptions)null);
      }

      public static ConcurrentLockTypeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConcurrentLockTypeType)XmlBeans.getContextTypeLoader().parse(file, ConcurrentLockTypeType.type, options);
      }

      public static ConcurrentLockTypeType parse(URL u) throws XmlException, IOException {
         return (ConcurrentLockTypeType)XmlBeans.getContextTypeLoader().parse(u, ConcurrentLockTypeType.type, (XmlOptions)null);
      }

      public static ConcurrentLockTypeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConcurrentLockTypeType)XmlBeans.getContextTypeLoader().parse(u, ConcurrentLockTypeType.type, options);
      }

      public static ConcurrentLockTypeType parse(InputStream is) throws XmlException, IOException {
         return (ConcurrentLockTypeType)XmlBeans.getContextTypeLoader().parse(is, ConcurrentLockTypeType.type, (XmlOptions)null);
      }

      public static ConcurrentLockTypeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConcurrentLockTypeType)XmlBeans.getContextTypeLoader().parse(is, ConcurrentLockTypeType.type, options);
      }

      public static ConcurrentLockTypeType parse(Reader r) throws XmlException, IOException {
         return (ConcurrentLockTypeType)XmlBeans.getContextTypeLoader().parse(r, ConcurrentLockTypeType.type, (XmlOptions)null);
      }

      public static ConcurrentLockTypeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConcurrentLockTypeType)XmlBeans.getContextTypeLoader().parse(r, ConcurrentLockTypeType.type, options);
      }

      public static ConcurrentLockTypeType parse(XMLStreamReader sr) throws XmlException {
         return (ConcurrentLockTypeType)XmlBeans.getContextTypeLoader().parse(sr, ConcurrentLockTypeType.type, (XmlOptions)null);
      }

      public static ConcurrentLockTypeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConcurrentLockTypeType)XmlBeans.getContextTypeLoader().parse(sr, ConcurrentLockTypeType.type, options);
      }

      public static ConcurrentLockTypeType parse(Node node) throws XmlException {
         return (ConcurrentLockTypeType)XmlBeans.getContextTypeLoader().parse(node, ConcurrentLockTypeType.type, (XmlOptions)null);
      }

      public static ConcurrentLockTypeType parse(Node node, XmlOptions options) throws XmlException {
         return (ConcurrentLockTypeType)XmlBeans.getContextTypeLoader().parse(node, ConcurrentLockTypeType.type, options);
      }

      /** @deprecated */
      public static ConcurrentLockTypeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConcurrentLockTypeType)XmlBeans.getContextTypeLoader().parse(xis, ConcurrentLockTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConcurrentLockTypeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConcurrentLockTypeType)XmlBeans.getContextTypeLoader().parse(xis, ConcurrentLockTypeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConcurrentLockTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConcurrentLockTypeType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_READ = 1;
      static final int INT_WRITE = 2;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("Read", 1), new Enum("Write", 2)});
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
