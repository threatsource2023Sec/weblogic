package weblogic.diagnostics.instrumentation.engine.xbean;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
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

public interface WlsEngineDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WlsEngineDocument.class.getClassLoader(), "schemacom_bea_xml.system.weblogic_diagnostics_instrumentation_config").resolveHandle("wlsenginee848doctype");

   WlsEngine getWlsEngine();

   void setWlsEngine(WlsEngine var1);

   WlsEngine addNewWlsEngine();

   public static final class Factory {
      public static WlsEngineDocument newInstance() {
         return (WlsEngineDocument)XmlBeans.getContextTypeLoader().newInstance(WlsEngineDocument.type, (XmlOptions)null);
      }

      public static WlsEngineDocument newInstance(XmlOptions options) {
         return (WlsEngineDocument)XmlBeans.getContextTypeLoader().newInstance(WlsEngineDocument.type, options);
      }

      public static WlsEngineDocument parse(String xmlAsString) throws XmlException {
         return (WlsEngineDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsEngineDocument.type, (XmlOptions)null);
      }

      public static WlsEngineDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WlsEngineDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsEngineDocument.type, options);
      }

      public static WlsEngineDocument parse(File file) throws XmlException, IOException {
         return (WlsEngineDocument)XmlBeans.getContextTypeLoader().parse(file, WlsEngineDocument.type, (XmlOptions)null);
      }

      public static WlsEngineDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WlsEngineDocument)XmlBeans.getContextTypeLoader().parse(file, WlsEngineDocument.type, options);
      }

      public static WlsEngineDocument parse(URL u) throws XmlException, IOException {
         return (WlsEngineDocument)XmlBeans.getContextTypeLoader().parse(u, WlsEngineDocument.type, (XmlOptions)null);
      }

      public static WlsEngineDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WlsEngineDocument)XmlBeans.getContextTypeLoader().parse(u, WlsEngineDocument.type, options);
      }

      public static WlsEngineDocument parse(InputStream is) throws XmlException, IOException {
         return (WlsEngineDocument)XmlBeans.getContextTypeLoader().parse(is, WlsEngineDocument.type, (XmlOptions)null);
      }

      public static WlsEngineDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WlsEngineDocument)XmlBeans.getContextTypeLoader().parse(is, WlsEngineDocument.type, options);
      }

      public static WlsEngineDocument parse(Reader r) throws XmlException, IOException {
         return (WlsEngineDocument)XmlBeans.getContextTypeLoader().parse(r, WlsEngineDocument.type, (XmlOptions)null);
      }

      public static WlsEngineDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WlsEngineDocument)XmlBeans.getContextTypeLoader().parse(r, WlsEngineDocument.type, options);
      }

      public static WlsEngineDocument parse(XMLStreamReader sr) throws XmlException {
         return (WlsEngineDocument)XmlBeans.getContextTypeLoader().parse(sr, WlsEngineDocument.type, (XmlOptions)null);
      }

      public static WlsEngineDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WlsEngineDocument)XmlBeans.getContextTypeLoader().parse(sr, WlsEngineDocument.type, options);
      }

      public static WlsEngineDocument parse(Node node) throws XmlException {
         return (WlsEngineDocument)XmlBeans.getContextTypeLoader().parse(node, WlsEngineDocument.type, (XmlOptions)null);
      }

      public static WlsEngineDocument parse(Node node, XmlOptions options) throws XmlException {
         return (WlsEngineDocument)XmlBeans.getContextTypeLoader().parse(node, WlsEngineDocument.type, options);
      }

      /** @deprecated */
      public static WlsEngineDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WlsEngineDocument)XmlBeans.getContextTypeLoader().parse(xis, WlsEngineDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WlsEngineDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WlsEngineDocument)XmlBeans.getContextTypeLoader().parse(xis, WlsEngineDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsEngineDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsEngineDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface WlsEngine extends XmlObject {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WlsEngine.class.getClassLoader(), "schemacom_bea_xml.system.weblogic_diagnostics_instrumentation_config").resolveHandle("wlsengine33d5elemtype");

      WlsInstrumentationSupport getWlsInstrumentationSupport();

      boolean isSetWlsInstrumentationSupport();

      void setWlsInstrumentationSupport(WlsInstrumentationSupport var1);

      WlsInstrumentationSupport addNewWlsInstrumentationSupport();

      void unsetWlsInstrumentationSupport();

      WlsEntryClasses getWlsEntryClasses();

      boolean isSetWlsEntryClasses();

      void setWlsEntryClasses(WlsEntryClasses var1);

      WlsEntryClasses addNewWlsEntryClasses();

      void unsetWlsEntryClasses();

      WlsDyeFlags getWlsDyeFlags();

      boolean isSetWlsDyeFlags();

      void setWlsDyeFlags(WlsDyeFlags var1);

      WlsDyeFlags addNewWlsDyeFlags();

      void unsetWlsDyeFlags();

      WlsPackages getWlsPackages();

      void setWlsPackages(WlsPackages var1);

      WlsPackages addNewWlsPackages();

      WlsActions getWlsActions();

      void setWlsActions(WlsActions var1);

      WlsActions addNewWlsActions();

      WlsActionGroups getWlsActionGroups();

      void setWlsActionGroups(WlsActionGroups var1);

      WlsActionGroups addNewWlsActionGroups();

      WlsValueRenderers getWlsValueRenderers();

      boolean isSetWlsValueRenderers();

      void setWlsValueRenderers(WlsValueRenderers var1);

      WlsValueRenderers addNewWlsValueRenderers();

      void unsetWlsValueRenderers();

      String getWlsPointcuts();

      XmlString xgetWlsPointcuts();

      void setWlsPointcuts(String var1);

      void xsetWlsPointcuts(XmlString var1);

      WlsMonitors getWlsMonitors();

      void setWlsMonitors(WlsMonitors var1);

      WlsMonitors addNewWlsMonitors();

      String getName();

      XmlString xgetName();

      boolean isSetName();

      void setName(String var1);

      void xsetName(XmlString var1);

      void unsetName();

      String getParent();

      XmlString xgetParent();

      boolean isSetParent();

      void setParent(String var1);

      void xsetParent(XmlString var1);

      void unsetParent();

      public static final class Factory {
         public static WlsEngine newInstance() {
            return (WlsEngine)XmlBeans.getContextTypeLoader().newInstance(WlsEngineDocument.WlsEngine.type, (XmlOptions)null);
         }

         public static WlsEngine newInstance(XmlOptions options) {
            return (WlsEngine)XmlBeans.getContextTypeLoader().newInstance(WlsEngineDocument.WlsEngine.type, options);
         }

         private Factory() {
         }
      }
   }
}
