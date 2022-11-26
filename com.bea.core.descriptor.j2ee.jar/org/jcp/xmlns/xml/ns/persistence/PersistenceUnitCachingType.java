package org.jcp.xmlns.xml.ns.persistence;

import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlToken;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface PersistenceUnitCachingType extends XmlToken {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PersistenceUnitCachingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("persistenceunitcachingtypee26dtype");
   Enum ALL = PersistenceUnitCachingType.Enum.forString("ALL");
   Enum NONE = PersistenceUnitCachingType.Enum.forString("NONE");
   Enum ENABLE_SELECTIVE = PersistenceUnitCachingType.Enum.forString("ENABLE_SELECTIVE");
   Enum DISABLE_SELECTIVE = PersistenceUnitCachingType.Enum.forString("DISABLE_SELECTIVE");
   Enum UNSPECIFIED = PersistenceUnitCachingType.Enum.forString("UNSPECIFIED");
   int INT_ALL = 1;
   int INT_NONE = 2;
   int INT_ENABLE_SELECTIVE = 3;
   int INT_DISABLE_SELECTIVE = 4;
   int INT_UNSPECIFIED = 5;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static PersistenceUnitCachingType newValue(Object obj) {
         return (PersistenceUnitCachingType)PersistenceUnitCachingType.type.newValue(obj);
      }

      public static PersistenceUnitCachingType newInstance() {
         return (PersistenceUnitCachingType)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitCachingType.type, (XmlOptions)null);
      }

      public static PersistenceUnitCachingType newInstance(XmlOptions options) {
         return (PersistenceUnitCachingType)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitCachingType.type, options);
      }

      public static PersistenceUnitCachingType parse(String xmlAsString) throws XmlException {
         return (PersistenceUnitCachingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceUnitCachingType.type, (XmlOptions)null);
      }

      public static PersistenceUnitCachingType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PersistenceUnitCachingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceUnitCachingType.type, options);
      }

      public static PersistenceUnitCachingType parse(File file) throws XmlException, IOException {
         return (PersistenceUnitCachingType)XmlBeans.getContextTypeLoader().parse(file, PersistenceUnitCachingType.type, (XmlOptions)null);
      }

      public static PersistenceUnitCachingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PersistenceUnitCachingType)XmlBeans.getContextTypeLoader().parse(file, PersistenceUnitCachingType.type, options);
      }

      public static PersistenceUnitCachingType parse(URL u) throws XmlException, IOException {
         return (PersistenceUnitCachingType)XmlBeans.getContextTypeLoader().parse(u, PersistenceUnitCachingType.type, (XmlOptions)null);
      }

      public static PersistenceUnitCachingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PersistenceUnitCachingType)XmlBeans.getContextTypeLoader().parse(u, PersistenceUnitCachingType.type, options);
      }

      public static PersistenceUnitCachingType parse(InputStream is) throws XmlException, IOException {
         return (PersistenceUnitCachingType)XmlBeans.getContextTypeLoader().parse(is, PersistenceUnitCachingType.type, (XmlOptions)null);
      }

      public static PersistenceUnitCachingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PersistenceUnitCachingType)XmlBeans.getContextTypeLoader().parse(is, PersistenceUnitCachingType.type, options);
      }

      public static PersistenceUnitCachingType parse(Reader r) throws XmlException, IOException {
         return (PersistenceUnitCachingType)XmlBeans.getContextTypeLoader().parse(r, PersistenceUnitCachingType.type, (XmlOptions)null);
      }

      public static PersistenceUnitCachingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PersistenceUnitCachingType)XmlBeans.getContextTypeLoader().parse(r, PersistenceUnitCachingType.type, options);
      }

      public static PersistenceUnitCachingType parse(XMLStreamReader sr) throws XmlException {
         return (PersistenceUnitCachingType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceUnitCachingType.type, (XmlOptions)null);
      }

      public static PersistenceUnitCachingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PersistenceUnitCachingType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceUnitCachingType.type, options);
      }

      public static PersistenceUnitCachingType parse(Node node) throws XmlException {
         return (PersistenceUnitCachingType)XmlBeans.getContextTypeLoader().parse(node, PersistenceUnitCachingType.type, (XmlOptions)null);
      }

      public static PersistenceUnitCachingType parse(Node node, XmlOptions options) throws XmlException {
         return (PersistenceUnitCachingType)XmlBeans.getContextTypeLoader().parse(node, PersistenceUnitCachingType.type, options);
      }

      /** @deprecated */
      public static PersistenceUnitCachingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PersistenceUnitCachingType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceUnitCachingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PersistenceUnitCachingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PersistenceUnitCachingType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceUnitCachingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceUnitCachingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceUnitCachingType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_ALL = 1;
      static final int INT_NONE = 2;
      static final int INT_ENABLE_SELECTIVE = 3;
      static final int INT_DISABLE_SELECTIVE = 4;
      static final int INT_UNSPECIFIED = 5;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("ALL", 1), new Enum("NONE", 2), new Enum("ENABLE_SELECTIVE", 3), new Enum("DISABLE_SELECTIVE", 4), new Enum("UNSPECIFIED", 5)});
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
