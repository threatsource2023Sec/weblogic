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

public interface WlsValueRenderers extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WlsValueRenderers.class.getClassLoader(), "schemacom_bea_xml.system.weblogic_diagnostics_instrumentation_config").resolveHandle("wlsvaluerenderers0256type");

   WlsRenderer[] getWlsRendererArray();

   WlsRenderer getWlsRendererArray(int var1);

   int sizeOfWlsRendererArray();

   void setWlsRendererArray(WlsRenderer[] var1);

   void setWlsRendererArray(int var1, WlsRenderer var2);

   WlsRenderer insertNewWlsRenderer(int var1);

   WlsRenderer addNewWlsRenderer();

   void removeWlsRenderer(int var1);

   public static final class Factory {
      public static WlsValueRenderers newInstance() {
         return (WlsValueRenderers)XmlBeans.getContextTypeLoader().newInstance(WlsValueRenderers.type, (XmlOptions)null);
      }

      public static WlsValueRenderers newInstance(XmlOptions options) {
         return (WlsValueRenderers)XmlBeans.getContextTypeLoader().newInstance(WlsValueRenderers.type, options);
      }

      public static WlsValueRenderers parse(String xmlAsString) throws XmlException {
         return (WlsValueRenderers)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsValueRenderers.type, (XmlOptions)null);
      }

      public static WlsValueRenderers parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WlsValueRenderers)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsValueRenderers.type, options);
      }

      public static WlsValueRenderers parse(File file) throws XmlException, IOException {
         return (WlsValueRenderers)XmlBeans.getContextTypeLoader().parse(file, WlsValueRenderers.type, (XmlOptions)null);
      }

      public static WlsValueRenderers parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WlsValueRenderers)XmlBeans.getContextTypeLoader().parse(file, WlsValueRenderers.type, options);
      }

      public static WlsValueRenderers parse(URL u) throws XmlException, IOException {
         return (WlsValueRenderers)XmlBeans.getContextTypeLoader().parse(u, WlsValueRenderers.type, (XmlOptions)null);
      }

      public static WlsValueRenderers parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WlsValueRenderers)XmlBeans.getContextTypeLoader().parse(u, WlsValueRenderers.type, options);
      }

      public static WlsValueRenderers parse(InputStream is) throws XmlException, IOException {
         return (WlsValueRenderers)XmlBeans.getContextTypeLoader().parse(is, WlsValueRenderers.type, (XmlOptions)null);
      }

      public static WlsValueRenderers parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WlsValueRenderers)XmlBeans.getContextTypeLoader().parse(is, WlsValueRenderers.type, options);
      }

      public static WlsValueRenderers parse(Reader r) throws XmlException, IOException {
         return (WlsValueRenderers)XmlBeans.getContextTypeLoader().parse(r, WlsValueRenderers.type, (XmlOptions)null);
      }

      public static WlsValueRenderers parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WlsValueRenderers)XmlBeans.getContextTypeLoader().parse(r, WlsValueRenderers.type, options);
      }

      public static WlsValueRenderers parse(XMLStreamReader sr) throws XmlException {
         return (WlsValueRenderers)XmlBeans.getContextTypeLoader().parse(sr, WlsValueRenderers.type, (XmlOptions)null);
      }

      public static WlsValueRenderers parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WlsValueRenderers)XmlBeans.getContextTypeLoader().parse(sr, WlsValueRenderers.type, options);
      }

      public static WlsValueRenderers parse(Node node) throws XmlException {
         return (WlsValueRenderers)XmlBeans.getContextTypeLoader().parse(node, WlsValueRenderers.type, (XmlOptions)null);
      }

      public static WlsValueRenderers parse(Node node, XmlOptions options) throws XmlException {
         return (WlsValueRenderers)XmlBeans.getContextTypeLoader().parse(node, WlsValueRenderers.type, options);
      }

      /** @deprecated */
      public static WlsValueRenderers parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WlsValueRenderers)XmlBeans.getContextTypeLoader().parse(xis, WlsValueRenderers.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WlsValueRenderers parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WlsValueRenderers)XmlBeans.getContextTypeLoader().parse(xis, WlsValueRenderers.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsValueRenderers.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsValueRenderers.type, options);
      }

      private Factory() {
      }
   }
}
