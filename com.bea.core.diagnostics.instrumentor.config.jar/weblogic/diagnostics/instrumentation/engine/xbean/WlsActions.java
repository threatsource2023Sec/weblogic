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

public interface WlsActions extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WlsActions.class.getClassLoader(), "schemacom_bea_xml.system.weblogic_diagnostics_instrumentation_config").resolveHandle("wlsactions1c2dtype");

   WlsAction[] getWlsActionArray();

   WlsAction getWlsActionArray(int var1);

   int sizeOfWlsActionArray();

   void setWlsActionArray(WlsAction[] var1);

   void setWlsActionArray(int var1, WlsAction var2);

   WlsAction insertNewWlsAction(int var1);

   WlsAction addNewWlsAction();

   void removeWlsAction(int var1);

   public static final class Factory {
      public static WlsActions newInstance() {
         return (WlsActions)XmlBeans.getContextTypeLoader().newInstance(WlsActions.type, (XmlOptions)null);
      }

      public static WlsActions newInstance(XmlOptions options) {
         return (WlsActions)XmlBeans.getContextTypeLoader().newInstance(WlsActions.type, options);
      }

      public static WlsActions parse(String xmlAsString) throws XmlException {
         return (WlsActions)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsActions.type, (XmlOptions)null);
      }

      public static WlsActions parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WlsActions)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsActions.type, options);
      }

      public static WlsActions parse(File file) throws XmlException, IOException {
         return (WlsActions)XmlBeans.getContextTypeLoader().parse(file, WlsActions.type, (XmlOptions)null);
      }

      public static WlsActions parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WlsActions)XmlBeans.getContextTypeLoader().parse(file, WlsActions.type, options);
      }

      public static WlsActions parse(URL u) throws XmlException, IOException {
         return (WlsActions)XmlBeans.getContextTypeLoader().parse(u, WlsActions.type, (XmlOptions)null);
      }

      public static WlsActions parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WlsActions)XmlBeans.getContextTypeLoader().parse(u, WlsActions.type, options);
      }

      public static WlsActions parse(InputStream is) throws XmlException, IOException {
         return (WlsActions)XmlBeans.getContextTypeLoader().parse(is, WlsActions.type, (XmlOptions)null);
      }

      public static WlsActions parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WlsActions)XmlBeans.getContextTypeLoader().parse(is, WlsActions.type, options);
      }

      public static WlsActions parse(Reader r) throws XmlException, IOException {
         return (WlsActions)XmlBeans.getContextTypeLoader().parse(r, WlsActions.type, (XmlOptions)null);
      }

      public static WlsActions parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WlsActions)XmlBeans.getContextTypeLoader().parse(r, WlsActions.type, options);
      }

      public static WlsActions parse(XMLStreamReader sr) throws XmlException {
         return (WlsActions)XmlBeans.getContextTypeLoader().parse(sr, WlsActions.type, (XmlOptions)null);
      }

      public static WlsActions parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WlsActions)XmlBeans.getContextTypeLoader().parse(sr, WlsActions.type, options);
      }

      public static WlsActions parse(Node node) throws XmlException {
         return (WlsActions)XmlBeans.getContextTypeLoader().parse(node, WlsActions.type, (XmlOptions)null);
      }

      public static WlsActions parse(Node node, XmlOptions options) throws XmlException {
         return (WlsActions)XmlBeans.getContextTypeLoader().parse(node, WlsActions.type, options);
      }

      /** @deprecated */
      public static WlsActions parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WlsActions)XmlBeans.getContextTypeLoader().parse(xis, WlsActions.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WlsActions parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WlsActions)XmlBeans.getContextTypeLoader().parse(xis, WlsActions.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsActions.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsActions.type, options);
      }

      private Factory() {
      }
   }
}
