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

public interface ProfilingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ProfilingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("profilingtypef246type");

   NoneProfilingType getNoneProfiling();

   boolean isNilNoneProfiling();

   boolean isSetNoneProfiling();

   void setNoneProfiling(NoneProfilingType var1);

   NoneProfilingType addNewNoneProfiling();

   void setNilNoneProfiling();

   void unsetNoneProfiling();

   LocalProfilingType getLocalProfiling();

   boolean isNilLocalProfiling();

   boolean isSetLocalProfiling();

   void setLocalProfiling(LocalProfilingType var1);

   LocalProfilingType addNewLocalProfiling();

   void setNilLocalProfiling();

   void unsetLocalProfiling();

   ExportProfilingType getExportProfiling();

   boolean isNilExportProfiling();

   boolean isSetExportProfiling();

   void setExportProfiling(ExportProfilingType var1);

   ExportProfilingType addNewExportProfiling();

   void setNilExportProfiling();

   void unsetExportProfiling();

   GuiProfilingType getGuiProfiling();

   boolean isNilGuiProfiling();

   boolean isSetGuiProfiling();

   void setGuiProfiling(GuiProfilingType var1);

   GuiProfilingType addNewGuiProfiling();

   void setNilGuiProfiling();

   void unsetGuiProfiling();

   public static final class Factory {
      public static ProfilingType newInstance() {
         return (ProfilingType)XmlBeans.getContextTypeLoader().newInstance(ProfilingType.type, (XmlOptions)null);
      }

      public static ProfilingType newInstance(XmlOptions options) {
         return (ProfilingType)XmlBeans.getContextTypeLoader().newInstance(ProfilingType.type, options);
      }

      public static ProfilingType parse(String xmlAsString) throws XmlException {
         return (ProfilingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ProfilingType.type, (XmlOptions)null);
      }

      public static ProfilingType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ProfilingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ProfilingType.type, options);
      }

      public static ProfilingType parse(File file) throws XmlException, IOException {
         return (ProfilingType)XmlBeans.getContextTypeLoader().parse(file, ProfilingType.type, (XmlOptions)null);
      }

      public static ProfilingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ProfilingType)XmlBeans.getContextTypeLoader().parse(file, ProfilingType.type, options);
      }

      public static ProfilingType parse(URL u) throws XmlException, IOException {
         return (ProfilingType)XmlBeans.getContextTypeLoader().parse(u, ProfilingType.type, (XmlOptions)null);
      }

      public static ProfilingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ProfilingType)XmlBeans.getContextTypeLoader().parse(u, ProfilingType.type, options);
      }

      public static ProfilingType parse(InputStream is) throws XmlException, IOException {
         return (ProfilingType)XmlBeans.getContextTypeLoader().parse(is, ProfilingType.type, (XmlOptions)null);
      }

      public static ProfilingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ProfilingType)XmlBeans.getContextTypeLoader().parse(is, ProfilingType.type, options);
      }

      public static ProfilingType parse(Reader r) throws XmlException, IOException {
         return (ProfilingType)XmlBeans.getContextTypeLoader().parse(r, ProfilingType.type, (XmlOptions)null);
      }

      public static ProfilingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ProfilingType)XmlBeans.getContextTypeLoader().parse(r, ProfilingType.type, options);
      }

      public static ProfilingType parse(XMLStreamReader sr) throws XmlException {
         return (ProfilingType)XmlBeans.getContextTypeLoader().parse(sr, ProfilingType.type, (XmlOptions)null);
      }

      public static ProfilingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ProfilingType)XmlBeans.getContextTypeLoader().parse(sr, ProfilingType.type, options);
      }

      public static ProfilingType parse(Node node) throws XmlException {
         return (ProfilingType)XmlBeans.getContextTypeLoader().parse(node, ProfilingType.type, (XmlOptions)null);
      }

      public static ProfilingType parse(Node node, XmlOptions options) throws XmlException {
         return (ProfilingType)XmlBeans.getContextTypeLoader().parse(node, ProfilingType.type, options);
      }

      /** @deprecated */
      public static ProfilingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ProfilingType)XmlBeans.getContextTypeLoader().parse(xis, ProfilingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ProfilingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ProfilingType)XmlBeans.getContextTypeLoader().parse(xis, ProfilingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ProfilingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ProfilingType.type, options);
      }

      private Factory() {
      }
   }
}
