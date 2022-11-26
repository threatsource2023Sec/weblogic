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

public interface WlsActionGroup extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WlsActionGroup.class.getClassLoader(), "schemacom_bea_xml.system.weblogic_diagnostics_instrumentation_config").resolveHandle("wlsactiongroup8b32type");

   String getName();

   XmlString xgetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   String getActionTypes();

   XmlString xgetActionTypes();

   void setActionTypes(String var1);

   void xsetActionTypes(XmlString var1);

   public static final class Factory {
      public static WlsActionGroup newInstance() {
         return (WlsActionGroup)XmlBeans.getContextTypeLoader().newInstance(WlsActionGroup.type, (XmlOptions)null);
      }

      public static WlsActionGroup newInstance(XmlOptions options) {
         return (WlsActionGroup)XmlBeans.getContextTypeLoader().newInstance(WlsActionGroup.type, options);
      }

      public static WlsActionGroup parse(String xmlAsString) throws XmlException {
         return (WlsActionGroup)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsActionGroup.type, (XmlOptions)null);
      }

      public static WlsActionGroup parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WlsActionGroup)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsActionGroup.type, options);
      }

      public static WlsActionGroup parse(File file) throws XmlException, IOException {
         return (WlsActionGroup)XmlBeans.getContextTypeLoader().parse(file, WlsActionGroup.type, (XmlOptions)null);
      }

      public static WlsActionGroup parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WlsActionGroup)XmlBeans.getContextTypeLoader().parse(file, WlsActionGroup.type, options);
      }

      public static WlsActionGroup parse(URL u) throws XmlException, IOException {
         return (WlsActionGroup)XmlBeans.getContextTypeLoader().parse(u, WlsActionGroup.type, (XmlOptions)null);
      }

      public static WlsActionGroup parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WlsActionGroup)XmlBeans.getContextTypeLoader().parse(u, WlsActionGroup.type, options);
      }

      public static WlsActionGroup parse(InputStream is) throws XmlException, IOException {
         return (WlsActionGroup)XmlBeans.getContextTypeLoader().parse(is, WlsActionGroup.type, (XmlOptions)null);
      }

      public static WlsActionGroup parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WlsActionGroup)XmlBeans.getContextTypeLoader().parse(is, WlsActionGroup.type, options);
      }

      public static WlsActionGroup parse(Reader r) throws XmlException, IOException {
         return (WlsActionGroup)XmlBeans.getContextTypeLoader().parse(r, WlsActionGroup.type, (XmlOptions)null);
      }

      public static WlsActionGroup parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WlsActionGroup)XmlBeans.getContextTypeLoader().parse(r, WlsActionGroup.type, options);
      }

      public static WlsActionGroup parse(XMLStreamReader sr) throws XmlException {
         return (WlsActionGroup)XmlBeans.getContextTypeLoader().parse(sr, WlsActionGroup.type, (XmlOptions)null);
      }

      public static WlsActionGroup parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WlsActionGroup)XmlBeans.getContextTypeLoader().parse(sr, WlsActionGroup.type, options);
      }

      public static WlsActionGroup parse(Node node) throws XmlException {
         return (WlsActionGroup)XmlBeans.getContextTypeLoader().parse(node, WlsActionGroup.type, (XmlOptions)null);
      }

      public static WlsActionGroup parse(Node node, XmlOptions options) throws XmlException {
         return (WlsActionGroup)XmlBeans.getContextTypeLoader().parse(node, WlsActionGroup.type, options);
      }

      /** @deprecated */
      public static WlsActionGroup parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WlsActionGroup)XmlBeans.getContextTypeLoader().parse(xis, WlsActionGroup.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WlsActionGroup parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WlsActionGroup)XmlBeans.getContextTypeLoader().parse(xis, WlsActionGroup.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsActionGroup.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsActionGroup.type, options);
      }

      private Factory() {
      }
   }
}
