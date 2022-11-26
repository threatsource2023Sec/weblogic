package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
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

public interface OracleDictionaryType extends BuiltInDbdictionaryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(OracleDictionaryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("oracledictionarytype2d47type");

   boolean getUseTriggersForAutoAssign();

   XmlBoolean xgetUseTriggersForAutoAssign();

   boolean isSetUseTriggersForAutoAssign();

   void setUseTriggersForAutoAssign(boolean var1);

   void xsetUseTriggersForAutoAssign(XmlBoolean var1);

   void unsetUseTriggersForAutoAssign();

   boolean getAutoAssignSequenceName();

   XmlBoolean xgetAutoAssignSequenceName();

   boolean isSetAutoAssignSequenceName();

   void setAutoAssignSequenceName(boolean var1);

   void xsetAutoAssignSequenceName(XmlBoolean var1);

   void unsetAutoAssignSequenceName();

   boolean getUseSetFormOfUseForUnicode();

   XmlBoolean xgetUseSetFormOfUseForUnicode();

   boolean isSetUseSetFormOfUseForUnicode();

   void setUseSetFormOfUseForUnicode(boolean var1);

   void xsetUseSetFormOfUseForUnicode(XmlBoolean var1);

   void unsetUseSetFormOfUseForUnicode();

   public static final class Factory {
      public static OracleDictionaryType newInstance() {
         return (OracleDictionaryType)XmlBeans.getContextTypeLoader().newInstance(OracleDictionaryType.type, (XmlOptions)null);
      }

      public static OracleDictionaryType newInstance(XmlOptions options) {
         return (OracleDictionaryType)XmlBeans.getContextTypeLoader().newInstance(OracleDictionaryType.type, options);
      }

      public static OracleDictionaryType parse(String xmlAsString) throws XmlException {
         return (OracleDictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OracleDictionaryType.type, (XmlOptions)null);
      }

      public static OracleDictionaryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (OracleDictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OracleDictionaryType.type, options);
      }

      public static OracleDictionaryType parse(File file) throws XmlException, IOException {
         return (OracleDictionaryType)XmlBeans.getContextTypeLoader().parse(file, OracleDictionaryType.type, (XmlOptions)null);
      }

      public static OracleDictionaryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (OracleDictionaryType)XmlBeans.getContextTypeLoader().parse(file, OracleDictionaryType.type, options);
      }

      public static OracleDictionaryType parse(URL u) throws XmlException, IOException {
         return (OracleDictionaryType)XmlBeans.getContextTypeLoader().parse(u, OracleDictionaryType.type, (XmlOptions)null);
      }

      public static OracleDictionaryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (OracleDictionaryType)XmlBeans.getContextTypeLoader().parse(u, OracleDictionaryType.type, options);
      }

      public static OracleDictionaryType parse(InputStream is) throws XmlException, IOException {
         return (OracleDictionaryType)XmlBeans.getContextTypeLoader().parse(is, OracleDictionaryType.type, (XmlOptions)null);
      }

      public static OracleDictionaryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (OracleDictionaryType)XmlBeans.getContextTypeLoader().parse(is, OracleDictionaryType.type, options);
      }

      public static OracleDictionaryType parse(Reader r) throws XmlException, IOException {
         return (OracleDictionaryType)XmlBeans.getContextTypeLoader().parse(r, OracleDictionaryType.type, (XmlOptions)null);
      }

      public static OracleDictionaryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (OracleDictionaryType)XmlBeans.getContextTypeLoader().parse(r, OracleDictionaryType.type, options);
      }

      public static OracleDictionaryType parse(XMLStreamReader sr) throws XmlException {
         return (OracleDictionaryType)XmlBeans.getContextTypeLoader().parse(sr, OracleDictionaryType.type, (XmlOptions)null);
      }

      public static OracleDictionaryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (OracleDictionaryType)XmlBeans.getContextTypeLoader().parse(sr, OracleDictionaryType.type, options);
      }

      public static OracleDictionaryType parse(Node node) throws XmlException {
         return (OracleDictionaryType)XmlBeans.getContextTypeLoader().parse(node, OracleDictionaryType.type, (XmlOptions)null);
      }

      public static OracleDictionaryType parse(Node node, XmlOptions options) throws XmlException {
         return (OracleDictionaryType)XmlBeans.getContextTypeLoader().parse(node, OracleDictionaryType.type, options);
      }

      /** @deprecated */
      public static OracleDictionaryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (OracleDictionaryType)XmlBeans.getContextTypeLoader().parse(xis, OracleDictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static OracleDictionaryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (OracleDictionaryType)XmlBeans.getContextTypeLoader().parse(xis, OracleDictionaryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OracleDictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OracleDictionaryType.type, options);
      }

      private Factory() {
      }
   }
}
