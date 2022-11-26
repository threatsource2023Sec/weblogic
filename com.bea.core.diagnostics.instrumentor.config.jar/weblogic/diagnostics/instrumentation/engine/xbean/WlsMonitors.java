package weblogic.diagnostics.instrumentation.engine.xbean;

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

public interface WlsMonitors extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WlsMonitors.class.getClassLoader(), "schemacom_bea_xml.system.weblogic_diagnostics_instrumentation_config").resolveHandle("wlsmonitors4621type");

   WlsMonitor[] getWlsMonitorArray();

   WlsMonitor getWlsMonitorArray(int var1);

   int sizeOfWlsMonitorArray();

   void setWlsMonitorArray(WlsMonitor[] var1);

   void setWlsMonitorArray(int var1, WlsMonitor var2);

   WlsMonitor insertNewWlsMonitor(int var1);

   WlsMonitor addNewWlsMonitor();

   void removeWlsMonitor(int var1);

   public static final class Factory {
      public static WlsMonitors newInstance() {
         return (WlsMonitors)XmlBeans.getContextTypeLoader().newInstance(WlsMonitors.type, (XmlOptions)null);
      }

      public static WlsMonitors newInstance(XmlOptions options) {
         return (WlsMonitors)XmlBeans.getContextTypeLoader().newInstance(WlsMonitors.type, options);
      }

      public static WlsMonitors parse(String xmlAsString) throws XmlException {
         return (WlsMonitors)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsMonitors.type, (XmlOptions)null);
      }

      public static WlsMonitors parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WlsMonitors)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsMonitors.type, options);
      }

      public static WlsMonitors parse(File file) throws XmlException, IOException {
         return (WlsMonitors)XmlBeans.getContextTypeLoader().parse(file, WlsMonitors.type, (XmlOptions)null);
      }

      public static WlsMonitors parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WlsMonitors)XmlBeans.getContextTypeLoader().parse(file, WlsMonitors.type, options);
      }

      public static WlsMonitors parse(URL u) throws XmlException, IOException {
         return (WlsMonitors)XmlBeans.getContextTypeLoader().parse(u, WlsMonitors.type, (XmlOptions)null);
      }

      public static WlsMonitors parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WlsMonitors)XmlBeans.getContextTypeLoader().parse(u, WlsMonitors.type, options);
      }

      public static WlsMonitors parse(InputStream is) throws XmlException, IOException {
         return (WlsMonitors)XmlBeans.getContextTypeLoader().parse(is, WlsMonitors.type, (XmlOptions)null);
      }

      public static WlsMonitors parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WlsMonitors)XmlBeans.getContextTypeLoader().parse(is, WlsMonitors.type, options);
      }

      public static WlsMonitors parse(Reader r) throws XmlException, IOException {
         return (WlsMonitors)XmlBeans.getContextTypeLoader().parse(r, WlsMonitors.type, (XmlOptions)null);
      }

      public static WlsMonitors parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WlsMonitors)XmlBeans.getContextTypeLoader().parse(r, WlsMonitors.type, options);
      }

      public static WlsMonitors parse(XMLStreamReader sr) throws XmlException {
         return (WlsMonitors)XmlBeans.getContextTypeLoader().parse(sr, WlsMonitors.type, (XmlOptions)null);
      }

      public static WlsMonitors parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WlsMonitors)XmlBeans.getContextTypeLoader().parse(sr, WlsMonitors.type, options);
      }

      public static WlsMonitors parse(Node node) throws XmlException {
         return (WlsMonitors)XmlBeans.getContextTypeLoader().parse(node, WlsMonitors.type, (XmlOptions)null);
      }

      public static WlsMonitors parse(Node node, XmlOptions options) throws XmlException {
         return (WlsMonitors)XmlBeans.getContextTypeLoader().parse(node, WlsMonitors.type, options);
      }

      /** @deprecated */
      public static WlsMonitors parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WlsMonitors)XmlBeans.getContextTypeLoader().parse(xis, WlsMonitors.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WlsMonitors parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WlsMonitors)XmlBeans.getContextTypeLoader().parse(xis, WlsMonitors.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsMonitors.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsMonitors.type, options);
      }

      private Factory() {
      }
   }
}
