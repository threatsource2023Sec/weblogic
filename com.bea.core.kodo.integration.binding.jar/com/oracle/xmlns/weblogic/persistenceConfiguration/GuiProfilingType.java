package com.oracle.xmlns.weblogic.persistenceConfiguration;

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

public interface GuiProfilingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(GuiProfilingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("guiprofilingtypeaa18type");

   public static final class Factory {
      public static GuiProfilingType newInstance() {
         return (GuiProfilingType)XmlBeans.getContextTypeLoader().newInstance(GuiProfilingType.type, (XmlOptions)null);
      }

      public static GuiProfilingType newInstance(XmlOptions options) {
         return (GuiProfilingType)XmlBeans.getContextTypeLoader().newInstance(GuiProfilingType.type, options);
      }

      public static GuiProfilingType parse(String xmlAsString) throws XmlException {
         return (GuiProfilingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, GuiProfilingType.type, (XmlOptions)null);
      }

      public static GuiProfilingType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (GuiProfilingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, GuiProfilingType.type, options);
      }

      public static GuiProfilingType parse(File file) throws XmlException, IOException {
         return (GuiProfilingType)XmlBeans.getContextTypeLoader().parse(file, GuiProfilingType.type, (XmlOptions)null);
      }

      public static GuiProfilingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (GuiProfilingType)XmlBeans.getContextTypeLoader().parse(file, GuiProfilingType.type, options);
      }

      public static GuiProfilingType parse(URL u) throws XmlException, IOException {
         return (GuiProfilingType)XmlBeans.getContextTypeLoader().parse(u, GuiProfilingType.type, (XmlOptions)null);
      }

      public static GuiProfilingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (GuiProfilingType)XmlBeans.getContextTypeLoader().parse(u, GuiProfilingType.type, options);
      }

      public static GuiProfilingType parse(InputStream is) throws XmlException, IOException {
         return (GuiProfilingType)XmlBeans.getContextTypeLoader().parse(is, GuiProfilingType.type, (XmlOptions)null);
      }

      public static GuiProfilingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (GuiProfilingType)XmlBeans.getContextTypeLoader().parse(is, GuiProfilingType.type, options);
      }

      public static GuiProfilingType parse(Reader r) throws XmlException, IOException {
         return (GuiProfilingType)XmlBeans.getContextTypeLoader().parse(r, GuiProfilingType.type, (XmlOptions)null);
      }

      public static GuiProfilingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (GuiProfilingType)XmlBeans.getContextTypeLoader().parse(r, GuiProfilingType.type, options);
      }

      public static GuiProfilingType parse(XMLStreamReader sr) throws XmlException {
         return (GuiProfilingType)XmlBeans.getContextTypeLoader().parse(sr, GuiProfilingType.type, (XmlOptions)null);
      }

      public static GuiProfilingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (GuiProfilingType)XmlBeans.getContextTypeLoader().parse(sr, GuiProfilingType.type, options);
      }

      public static GuiProfilingType parse(Node node) throws XmlException {
         return (GuiProfilingType)XmlBeans.getContextTypeLoader().parse(node, GuiProfilingType.type, (XmlOptions)null);
      }

      public static GuiProfilingType parse(Node node, XmlOptions options) throws XmlException {
         return (GuiProfilingType)XmlBeans.getContextTypeLoader().parse(node, GuiProfilingType.type, options);
      }

      /** @deprecated */
      public static GuiProfilingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (GuiProfilingType)XmlBeans.getContextTypeLoader().parse(xis, GuiProfilingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static GuiProfilingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (GuiProfilingType)XmlBeans.getContextTypeLoader().parse(xis, GuiProfilingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, GuiProfilingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, GuiProfilingType.type, options);
      }

      private Factory() {
      }
   }
}
