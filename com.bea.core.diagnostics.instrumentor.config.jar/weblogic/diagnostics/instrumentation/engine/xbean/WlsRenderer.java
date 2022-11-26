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

public interface WlsRenderer extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WlsRenderer.class.getClassLoader(), "schemacom_bea_xml.system.weblogic_diagnostics_instrumentation_config").resolveHandle("wlsrendererc6f7type");

   String getName();

   XmlString xgetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   String getClassName();

   XmlString xgetClassName();

   void setClassName(String var1);

   void xsetClassName(XmlString var1);

   String getTypeClassName();

   XmlString xgetTypeClassName();

   boolean isSetTypeClassName();

   void setTypeClassName(String var1);

   void xsetTypeClassName(XmlString var1);

   void unsetTypeClassName();

   public static final class Factory {
      public static WlsRenderer newInstance() {
         return (WlsRenderer)XmlBeans.getContextTypeLoader().newInstance(WlsRenderer.type, (XmlOptions)null);
      }

      public static WlsRenderer newInstance(XmlOptions options) {
         return (WlsRenderer)XmlBeans.getContextTypeLoader().newInstance(WlsRenderer.type, options);
      }

      public static WlsRenderer parse(String xmlAsString) throws XmlException {
         return (WlsRenderer)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsRenderer.type, (XmlOptions)null);
      }

      public static WlsRenderer parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WlsRenderer)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsRenderer.type, options);
      }

      public static WlsRenderer parse(File file) throws XmlException, IOException {
         return (WlsRenderer)XmlBeans.getContextTypeLoader().parse(file, WlsRenderer.type, (XmlOptions)null);
      }

      public static WlsRenderer parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WlsRenderer)XmlBeans.getContextTypeLoader().parse(file, WlsRenderer.type, options);
      }

      public static WlsRenderer parse(URL u) throws XmlException, IOException {
         return (WlsRenderer)XmlBeans.getContextTypeLoader().parse(u, WlsRenderer.type, (XmlOptions)null);
      }

      public static WlsRenderer parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WlsRenderer)XmlBeans.getContextTypeLoader().parse(u, WlsRenderer.type, options);
      }

      public static WlsRenderer parse(InputStream is) throws XmlException, IOException {
         return (WlsRenderer)XmlBeans.getContextTypeLoader().parse(is, WlsRenderer.type, (XmlOptions)null);
      }

      public static WlsRenderer parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WlsRenderer)XmlBeans.getContextTypeLoader().parse(is, WlsRenderer.type, options);
      }

      public static WlsRenderer parse(Reader r) throws XmlException, IOException {
         return (WlsRenderer)XmlBeans.getContextTypeLoader().parse(r, WlsRenderer.type, (XmlOptions)null);
      }

      public static WlsRenderer parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WlsRenderer)XmlBeans.getContextTypeLoader().parse(r, WlsRenderer.type, options);
      }

      public static WlsRenderer parse(XMLStreamReader sr) throws XmlException {
         return (WlsRenderer)XmlBeans.getContextTypeLoader().parse(sr, WlsRenderer.type, (XmlOptions)null);
      }

      public static WlsRenderer parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WlsRenderer)XmlBeans.getContextTypeLoader().parse(sr, WlsRenderer.type, options);
      }

      public static WlsRenderer parse(Node node) throws XmlException {
         return (WlsRenderer)XmlBeans.getContextTypeLoader().parse(node, WlsRenderer.type, (XmlOptions)null);
      }

      public static WlsRenderer parse(Node node, XmlOptions options) throws XmlException {
         return (WlsRenderer)XmlBeans.getContextTypeLoader().parse(node, WlsRenderer.type, options);
      }

      /** @deprecated */
      public static WlsRenderer parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WlsRenderer)XmlBeans.getContextTypeLoader().parse(xis, WlsRenderer.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WlsRenderer parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WlsRenderer)XmlBeans.getContextTypeLoader().parse(xis, WlsRenderer.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsRenderer.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsRenderer.type, options);
      }

      private Factory() {
      }
   }
}
