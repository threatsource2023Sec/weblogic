package com.oracle.xmlns.weblogic.weblogicApplication;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
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

public interface StatementType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(StatementType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("statementtypec9d8type");

   TrueFalseType getProfilingEnabled();

   boolean isSetProfilingEnabled();

   void setProfilingEnabled(TrueFalseType var1);

   TrueFalseType addNewProfilingEnabled();

   void unsetProfilingEnabled();

   public static final class Factory {
      public static StatementType newInstance() {
         return (StatementType)XmlBeans.getContextTypeLoader().newInstance(StatementType.type, (XmlOptions)null);
      }

      public static StatementType newInstance(XmlOptions options) {
         return (StatementType)XmlBeans.getContextTypeLoader().newInstance(StatementType.type, options);
      }

      public static StatementType parse(String xmlAsString) throws XmlException {
         return (StatementType)XmlBeans.getContextTypeLoader().parse(xmlAsString, StatementType.type, (XmlOptions)null);
      }

      public static StatementType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (StatementType)XmlBeans.getContextTypeLoader().parse(xmlAsString, StatementType.type, options);
      }

      public static StatementType parse(File file) throws XmlException, IOException {
         return (StatementType)XmlBeans.getContextTypeLoader().parse(file, StatementType.type, (XmlOptions)null);
      }

      public static StatementType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (StatementType)XmlBeans.getContextTypeLoader().parse(file, StatementType.type, options);
      }

      public static StatementType parse(URL u) throws XmlException, IOException {
         return (StatementType)XmlBeans.getContextTypeLoader().parse(u, StatementType.type, (XmlOptions)null);
      }

      public static StatementType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (StatementType)XmlBeans.getContextTypeLoader().parse(u, StatementType.type, options);
      }

      public static StatementType parse(InputStream is) throws XmlException, IOException {
         return (StatementType)XmlBeans.getContextTypeLoader().parse(is, StatementType.type, (XmlOptions)null);
      }

      public static StatementType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (StatementType)XmlBeans.getContextTypeLoader().parse(is, StatementType.type, options);
      }

      public static StatementType parse(Reader r) throws XmlException, IOException {
         return (StatementType)XmlBeans.getContextTypeLoader().parse(r, StatementType.type, (XmlOptions)null);
      }

      public static StatementType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (StatementType)XmlBeans.getContextTypeLoader().parse(r, StatementType.type, options);
      }

      public static StatementType parse(XMLStreamReader sr) throws XmlException {
         return (StatementType)XmlBeans.getContextTypeLoader().parse(sr, StatementType.type, (XmlOptions)null);
      }

      public static StatementType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (StatementType)XmlBeans.getContextTypeLoader().parse(sr, StatementType.type, options);
      }

      public static StatementType parse(Node node) throws XmlException {
         return (StatementType)XmlBeans.getContextTypeLoader().parse(node, StatementType.type, (XmlOptions)null);
      }

      public static StatementType parse(Node node, XmlOptions options) throws XmlException {
         return (StatementType)XmlBeans.getContextTypeLoader().parse(node, StatementType.type, options);
      }

      /** @deprecated */
      public static StatementType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (StatementType)XmlBeans.getContextTypeLoader().parse(xis, StatementType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static StatementType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (StatementType)XmlBeans.getContextTypeLoader().parse(xis, StatementType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, StatementType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, StatementType.type, options);
      }

      private Factory() {
      }
   }
}
