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

public interface WlsInstrumentationSupport extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WlsInstrumentationSupport.class.getClassLoader(), "schemacom_bea_xml.system.weblogic_diagnostics_instrumentation_config").resolveHandle("wlsinstrumentationsupportb41atype");

   String getClassName();

   XmlString xgetClassName();

   boolean isSetClassName();

   void setClassName(String var1);

   void xsetClassName(XmlString var1);

   void unsetClassName();

   public static final class Factory {
      public static WlsInstrumentationSupport newInstance() {
         return (WlsInstrumentationSupport)XmlBeans.getContextTypeLoader().newInstance(WlsInstrumentationSupport.type, (XmlOptions)null);
      }

      public static WlsInstrumentationSupport newInstance(XmlOptions options) {
         return (WlsInstrumentationSupport)XmlBeans.getContextTypeLoader().newInstance(WlsInstrumentationSupport.type, options);
      }

      public static WlsInstrumentationSupport parse(String xmlAsString) throws XmlException {
         return (WlsInstrumentationSupport)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsInstrumentationSupport.type, (XmlOptions)null);
      }

      public static WlsInstrumentationSupport parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WlsInstrumentationSupport)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsInstrumentationSupport.type, options);
      }

      public static WlsInstrumentationSupport parse(File file) throws XmlException, IOException {
         return (WlsInstrumentationSupport)XmlBeans.getContextTypeLoader().parse(file, WlsInstrumentationSupport.type, (XmlOptions)null);
      }

      public static WlsInstrumentationSupport parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WlsInstrumentationSupport)XmlBeans.getContextTypeLoader().parse(file, WlsInstrumentationSupport.type, options);
      }

      public static WlsInstrumentationSupport parse(URL u) throws XmlException, IOException {
         return (WlsInstrumentationSupport)XmlBeans.getContextTypeLoader().parse(u, WlsInstrumentationSupport.type, (XmlOptions)null);
      }

      public static WlsInstrumentationSupport parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WlsInstrumentationSupport)XmlBeans.getContextTypeLoader().parse(u, WlsInstrumentationSupport.type, options);
      }

      public static WlsInstrumentationSupport parse(InputStream is) throws XmlException, IOException {
         return (WlsInstrumentationSupport)XmlBeans.getContextTypeLoader().parse(is, WlsInstrumentationSupport.type, (XmlOptions)null);
      }

      public static WlsInstrumentationSupport parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WlsInstrumentationSupport)XmlBeans.getContextTypeLoader().parse(is, WlsInstrumentationSupport.type, options);
      }

      public static WlsInstrumentationSupport parse(Reader r) throws XmlException, IOException {
         return (WlsInstrumentationSupport)XmlBeans.getContextTypeLoader().parse(r, WlsInstrumentationSupport.type, (XmlOptions)null);
      }

      public static WlsInstrumentationSupport parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WlsInstrumentationSupport)XmlBeans.getContextTypeLoader().parse(r, WlsInstrumentationSupport.type, options);
      }

      public static WlsInstrumentationSupport parse(XMLStreamReader sr) throws XmlException {
         return (WlsInstrumentationSupport)XmlBeans.getContextTypeLoader().parse(sr, WlsInstrumentationSupport.type, (XmlOptions)null);
      }

      public static WlsInstrumentationSupport parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WlsInstrumentationSupport)XmlBeans.getContextTypeLoader().parse(sr, WlsInstrumentationSupport.type, options);
      }

      public static WlsInstrumentationSupport parse(Node node) throws XmlException {
         return (WlsInstrumentationSupport)XmlBeans.getContextTypeLoader().parse(node, WlsInstrumentationSupport.type, (XmlOptions)null);
      }

      public static WlsInstrumentationSupport parse(Node node, XmlOptions options) throws XmlException {
         return (WlsInstrumentationSupport)XmlBeans.getContextTypeLoader().parse(node, WlsInstrumentationSupport.type, options);
      }

      /** @deprecated */
      public static WlsInstrumentationSupport parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WlsInstrumentationSupport)XmlBeans.getContextTypeLoader().parse(xis, WlsInstrumentationSupport.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WlsInstrumentationSupport parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WlsInstrumentationSupport)XmlBeans.getContextTypeLoader().parse(xis, WlsInstrumentationSupport.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsInstrumentationSupport.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsInstrumentationSupport.type, options);
      }

      private Factory() {
      }
   }
}
