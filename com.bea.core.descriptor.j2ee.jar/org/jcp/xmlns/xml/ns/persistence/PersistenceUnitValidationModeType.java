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

public interface PersistenceUnitValidationModeType extends XmlToken {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PersistenceUnitValidationModeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("persistenceunitvalidationmodetype18b5type");
   Enum AUTO = PersistenceUnitValidationModeType.Enum.forString("AUTO");
   Enum CALLBACK = PersistenceUnitValidationModeType.Enum.forString("CALLBACK");
   Enum NONE = PersistenceUnitValidationModeType.Enum.forString("NONE");
   int INT_AUTO = 1;
   int INT_CALLBACK = 2;
   int INT_NONE = 3;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static PersistenceUnitValidationModeType newValue(Object obj) {
         return (PersistenceUnitValidationModeType)PersistenceUnitValidationModeType.type.newValue(obj);
      }

      public static PersistenceUnitValidationModeType newInstance() {
         return (PersistenceUnitValidationModeType)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitValidationModeType.type, (XmlOptions)null);
      }

      public static PersistenceUnitValidationModeType newInstance(XmlOptions options) {
         return (PersistenceUnitValidationModeType)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitValidationModeType.type, options);
      }

      public static PersistenceUnitValidationModeType parse(String xmlAsString) throws XmlException {
         return (PersistenceUnitValidationModeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceUnitValidationModeType.type, (XmlOptions)null);
      }

      public static PersistenceUnitValidationModeType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PersistenceUnitValidationModeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceUnitValidationModeType.type, options);
      }

      public static PersistenceUnitValidationModeType parse(File file) throws XmlException, IOException {
         return (PersistenceUnitValidationModeType)XmlBeans.getContextTypeLoader().parse(file, PersistenceUnitValidationModeType.type, (XmlOptions)null);
      }

      public static PersistenceUnitValidationModeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PersistenceUnitValidationModeType)XmlBeans.getContextTypeLoader().parse(file, PersistenceUnitValidationModeType.type, options);
      }

      public static PersistenceUnitValidationModeType parse(URL u) throws XmlException, IOException {
         return (PersistenceUnitValidationModeType)XmlBeans.getContextTypeLoader().parse(u, PersistenceUnitValidationModeType.type, (XmlOptions)null);
      }

      public static PersistenceUnitValidationModeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PersistenceUnitValidationModeType)XmlBeans.getContextTypeLoader().parse(u, PersistenceUnitValidationModeType.type, options);
      }

      public static PersistenceUnitValidationModeType parse(InputStream is) throws XmlException, IOException {
         return (PersistenceUnitValidationModeType)XmlBeans.getContextTypeLoader().parse(is, PersistenceUnitValidationModeType.type, (XmlOptions)null);
      }

      public static PersistenceUnitValidationModeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PersistenceUnitValidationModeType)XmlBeans.getContextTypeLoader().parse(is, PersistenceUnitValidationModeType.type, options);
      }

      public static PersistenceUnitValidationModeType parse(Reader r) throws XmlException, IOException {
         return (PersistenceUnitValidationModeType)XmlBeans.getContextTypeLoader().parse(r, PersistenceUnitValidationModeType.type, (XmlOptions)null);
      }

      public static PersistenceUnitValidationModeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PersistenceUnitValidationModeType)XmlBeans.getContextTypeLoader().parse(r, PersistenceUnitValidationModeType.type, options);
      }

      public static PersistenceUnitValidationModeType parse(XMLStreamReader sr) throws XmlException {
         return (PersistenceUnitValidationModeType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceUnitValidationModeType.type, (XmlOptions)null);
      }

      public static PersistenceUnitValidationModeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PersistenceUnitValidationModeType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceUnitValidationModeType.type, options);
      }

      public static PersistenceUnitValidationModeType parse(Node node) throws XmlException {
         return (PersistenceUnitValidationModeType)XmlBeans.getContextTypeLoader().parse(node, PersistenceUnitValidationModeType.type, (XmlOptions)null);
      }

      public static PersistenceUnitValidationModeType parse(Node node, XmlOptions options) throws XmlException {
         return (PersistenceUnitValidationModeType)XmlBeans.getContextTypeLoader().parse(node, PersistenceUnitValidationModeType.type, options);
      }

      /** @deprecated */
      public static PersistenceUnitValidationModeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PersistenceUnitValidationModeType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceUnitValidationModeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PersistenceUnitValidationModeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PersistenceUnitValidationModeType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceUnitValidationModeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceUnitValidationModeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceUnitValidationModeType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_AUTO = 1;
      static final int INT_CALLBACK = 2;
      static final int INT_NONE = 3;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("AUTO", 1), new Enum("CALLBACK", 2), new Enum("NONE", 3)});
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
