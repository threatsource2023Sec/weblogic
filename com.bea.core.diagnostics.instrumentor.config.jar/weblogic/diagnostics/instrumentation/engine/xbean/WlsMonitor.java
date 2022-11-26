package weblogic.diagnostics.instrumentation.engine.xbean;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
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

public interface WlsMonitor extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WlsMonitor.class.getClassLoader(), "schemacom_bea_xml.system.weblogic_diagnostics_instrumentation_config").resolveHandle("wlsmonitor8e70type");

   String getType();

   XmlString xgetType();

   void setType(String var1);

   void xsetType(XmlString var1);

   String getScope();

   XmlString xgetScope();

   void setScope(String var1);

   void xsetScope(XmlString var1);

   String getLocation();

   XmlString xgetLocation();

   void setLocation(String var1);

   void xsetLocation(XmlString var1);

   String getPointcut();

   XmlString xgetPointcut();

   void setPointcut(String var1);

   void xsetPointcut(XmlString var1);

   String getCodeGenerator();

   XmlString xgetCodeGenerator();

   boolean isSetCodeGenerator();

   void setCodeGenerator(String var1);

   void xsetCodeGenerator(XmlString var1);

   void unsetCodeGenerator();

   String getAttributeNames();

   XmlString xgetAttributeNames();

   boolean isSetAttributeNames();

   void setAttributeNames(String var1);

   void xsetAttributeNames(XmlString var1);

   void unsetAttributeNames();

   String getActionGroup();

   XmlString xgetActionGroup();

   boolean isSetActionGroup();

   void setActionGroup(String var1);

   void xsetActionGroup(XmlString var1);

   void unsetActionGroup();

   boolean getCaptureArgs();

   XmlBoolean xgetCaptureArgs();

   boolean isSetCaptureArgs();

   void setCaptureArgs(boolean var1);

   void xsetCaptureArgs(XmlBoolean var1);

   void unsetCaptureArgs();

   boolean getCaptureReturn();

   XmlBoolean xgetCaptureReturn();

   boolean isSetCaptureReturn();

   void setCaptureReturn(boolean var1);

   void xsetCaptureReturn(XmlBoolean var1);

   void unsetCaptureReturn();

   String getDiagnosticVolume();

   XmlString xgetDiagnosticVolume();

   boolean isSetDiagnosticVolume();

   void setDiagnosticVolume(String var1);

   void xsetDiagnosticVolume(XmlString var1);

   void unsetDiagnosticVolume();

   boolean getServerManaged();

   XmlBoolean xgetServerManaged();

   boolean isSetServerManaged();

   void setServerManaged(boolean var1);

   void xsetServerManaged(XmlBoolean var1);

   void unsetServerManaged();

   String getEventClassName();

   XmlString xgetEventClassName();

   boolean isSetEventClassName();

   void setEventClassName(String var1);

   void xsetEventClassName(XmlString var1);

   void unsetEventClassName();

   public static final class Factory {
      public static WlsMonitor newInstance() {
         return (WlsMonitor)XmlBeans.getContextTypeLoader().newInstance(WlsMonitor.type, (XmlOptions)null);
      }

      public static WlsMonitor newInstance(XmlOptions options) {
         return (WlsMonitor)XmlBeans.getContextTypeLoader().newInstance(WlsMonitor.type, options);
      }

      public static WlsMonitor parse(String xmlAsString) throws XmlException {
         return (WlsMonitor)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsMonitor.type, (XmlOptions)null);
      }

      public static WlsMonitor parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WlsMonitor)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsMonitor.type, options);
      }

      public static WlsMonitor parse(File file) throws XmlException, IOException {
         return (WlsMonitor)XmlBeans.getContextTypeLoader().parse(file, WlsMonitor.type, (XmlOptions)null);
      }

      public static WlsMonitor parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WlsMonitor)XmlBeans.getContextTypeLoader().parse(file, WlsMonitor.type, options);
      }

      public static WlsMonitor parse(URL u) throws XmlException, IOException {
         return (WlsMonitor)XmlBeans.getContextTypeLoader().parse(u, WlsMonitor.type, (XmlOptions)null);
      }

      public static WlsMonitor parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WlsMonitor)XmlBeans.getContextTypeLoader().parse(u, WlsMonitor.type, options);
      }

      public static WlsMonitor parse(InputStream is) throws XmlException, IOException {
         return (WlsMonitor)XmlBeans.getContextTypeLoader().parse(is, WlsMonitor.type, (XmlOptions)null);
      }

      public static WlsMonitor parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WlsMonitor)XmlBeans.getContextTypeLoader().parse(is, WlsMonitor.type, options);
      }

      public static WlsMonitor parse(Reader r) throws XmlException, IOException {
         return (WlsMonitor)XmlBeans.getContextTypeLoader().parse(r, WlsMonitor.type, (XmlOptions)null);
      }

      public static WlsMonitor parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WlsMonitor)XmlBeans.getContextTypeLoader().parse(r, WlsMonitor.type, options);
      }

      public static WlsMonitor parse(XMLStreamReader sr) throws XmlException {
         return (WlsMonitor)XmlBeans.getContextTypeLoader().parse(sr, WlsMonitor.type, (XmlOptions)null);
      }

      public static WlsMonitor parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WlsMonitor)XmlBeans.getContextTypeLoader().parse(sr, WlsMonitor.type, options);
      }

      public static WlsMonitor parse(Node node) throws XmlException {
         return (WlsMonitor)XmlBeans.getContextTypeLoader().parse(node, WlsMonitor.type, (XmlOptions)null);
      }

      public static WlsMonitor parse(Node node, XmlOptions options) throws XmlException {
         return (WlsMonitor)XmlBeans.getContextTypeLoader().parse(node, WlsMonitor.type, options);
      }

      /** @deprecated */
      public static WlsMonitor parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WlsMonitor)XmlBeans.getContextTypeLoader().parse(xis, WlsMonitor.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WlsMonitor parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WlsMonitor)XmlBeans.getContextTypeLoader().parse(xis, WlsMonitor.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsMonitor.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsMonitor.type, options);
      }

      private Factory() {
      }
   }
}
