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

public interface WlsAction extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WlsAction.class.getClassLoader(), "schemacom_bea_xml.system.weblogic_diagnostics_instrumentation_config").resolveHandle("wlsactionc6e4type");

   String getType();

   XmlString xgetType();

   void setType(String var1);

   void xsetType(XmlString var1);

   String getClassName();

   XmlString xgetClassName();

   void setClassName(String var1);

   void xsetClassName(XmlString var1);

   public static final class Factory {
      public static WlsAction newInstance() {
         return (WlsAction)XmlBeans.getContextTypeLoader().newInstance(WlsAction.type, (XmlOptions)null);
      }

      public static WlsAction newInstance(XmlOptions options) {
         return (WlsAction)XmlBeans.getContextTypeLoader().newInstance(WlsAction.type, options);
      }

      public static WlsAction parse(String xmlAsString) throws XmlException {
         return (WlsAction)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsAction.type, (XmlOptions)null);
      }

      public static WlsAction parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WlsAction)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsAction.type, options);
      }

      public static WlsAction parse(File file) throws XmlException, IOException {
         return (WlsAction)XmlBeans.getContextTypeLoader().parse(file, WlsAction.type, (XmlOptions)null);
      }

      public static WlsAction parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WlsAction)XmlBeans.getContextTypeLoader().parse(file, WlsAction.type, options);
      }

      public static WlsAction parse(URL u) throws XmlException, IOException {
         return (WlsAction)XmlBeans.getContextTypeLoader().parse(u, WlsAction.type, (XmlOptions)null);
      }

      public static WlsAction parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WlsAction)XmlBeans.getContextTypeLoader().parse(u, WlsAction.type, options);
      }

      public static WlsAction parse(InputStream is) throws XmlException, IOException {
         return (WlsAction)XmlBeans.getContextTypeLoader().parse(is, WlsAction.type, (XmlOptions)null);
      }

      public static WlsAction parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WlsAction)XmlBeans.getContextTypeLoader().parse(is, WlsAction.type, options);
      }

      public static WlsAction parse(Reader r) throws XmlException, IOException {
         return (WlsAction)XmlBeans.getContextTypeLoader().parse(r, WlsAction.type, (XmlOptions)null);
      }

      public static WlsAction parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WlsAction)XmlBeans.getContextTypeLoader().parse(r, WlsAction.type, options);
      }

      public static WlsAction parse(XMLStreamReader sr) throws XmlException {
         return (WlsAction)XmlBeans.getContextTypeLoader().parse(sr, WlsAction.type, (XmlOptions)null);
      }

      public static WlsAction parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WlsAction)XmlBeans.getContextTypeLoader().parse(sr, WlsAction.type, options);
      }

      public static WlsAction parse(Node node) throws XmlException {
         return (WlsAction)XmlBeans.getContextTypeLoader().parse(node, WlsAction.type, (XmlOptions)null);
      }

      public static WlsAction parse(Node node, XmlOptions options) throws XmlException {
         return (WlsAction)XmlBeans.getContextTypeLoader().parse(node, WlsAction.type, options);
      }

      /** @deprecated */
      public static WlsAction parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WlsAction)XmlBeans.getContextTypeLoader().parse(xis, WlsAction.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WlsAction parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WlsAction)XmlBeans.getContextTypeLoader().parse(xis, WlsAction.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsAction.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsAction.type, options);
      }

      private Factory() {
      }
   }
}
