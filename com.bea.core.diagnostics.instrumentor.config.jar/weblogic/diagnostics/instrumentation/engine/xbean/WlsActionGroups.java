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

public interface WlsActionGroups extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WlsActionGroups.class.getClassLoader(), "schemacom_bea_xml.system.weblogic_diagnostics_instrumentation_config").resolveHandle("wlsactiongroupse19ftype");

   WlsActionGroup[] getWlsActionGroupArray();

   WlsActionGroup getWlsActionGroupArray(int var1);

   int sizeOfWlsActionGroupArray();

   void setWlsActionGroupArray(WlsActionGroup[] var1);

   void setWlsActionGroupArray(int var1, WlsActionGroup var2);

   WlsActionGroup insertNewWlsActionGroup(int var1);

   WlsActionGroup addNewWlsActionGroup();

   void removeWlsActionGroup(int var1);

   public static final class Factory {
      public static WlsActionGroups newInstance() {
         return (WlsActionGroups)XmlBeans.getContextTypeLoader().newInstance(WlsActionGroups.type, (XmlOptions)null);
      }

      public static WlsActionGroups newInstance(XmlOptions options) {
         return (WlsActionGroups)XmlBeans.getContextTypeLoader().newInstance(WlsActionGroups.type, options);
      }

      public static WlsActionGroups parse(String xmlAsString) throws XmlException {
         return (WlsActionGroups)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsActionGroups.type, (XmlOptions)null);
      }

      public static WlsActionGroups parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WlsActionGroups)XmlBeans.getContextTypeLoader().parse(xmlAsString, WlsActionGroups.type, options);
      }

      public static WlsActionGroups parse(File file) throws XmlException, IOException {
         return (WlsActionGroups)XmlBeans.getContextTypeLoader().parse(file, WlsActionGroups.type, (XmlOptions)null);
      }

      public static WlsActionGroups parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WlsActionGroups)XmlBeans.getContextTypeLoader().parse(file, WlsActionGroups.type, options);
      }

      public static WlsActionGroups parse(URL u) throws XmlException, IOException {
         return (WlsActionGroups)XmlBeans.getContextTypeLoader().parse(u, WlsActionGroups.type, (XmlOptions)null);
      }

      public static WlsActionGroups parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WlsActionGroups)XmlBeans.getContextTypeLoader().parse(u, WlsActionGroups.type, options);
      }

      public static WlsActionGroups parse(InputStream is) throws XmlException, IOException {
         return (WlsActionGroups)XmlBeans.getContextTypeLoader().parse(is, WlsActionGroups.type, (XmlOptions)null);
      }

      public static WlsActionGroups parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WlsActionGroups)XmlBeans.getContextTypeLoader().parse(is, WlsActionGroups.type, options);
      }

      public static WlsActionGroups parse(Reader r) throws XmlException, IOException {
         return (WlsActionGroups)XmlBeans.getContextTypeLoader().parse(r, WlsActionGroups.type, (XmlOptions)null);
      }

      public static WlsActionGroups parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WlsActionGroups)XmlBeans.getContextTypeLoader().parse(r, WlsActionGroups.type, options);
      }

      public static WlsActionGroups parse(XMLStreamReader sr) throws XmlException {
         return (WlsActionGroups)XmlBeans.getContextTypeLoader().parse(sr, WlsActionGroups.type, (XmlOptions)null);
      }

      public static WlsActionGroups parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WlsActionGroups)XmlBeans.getContextTypeLoader().parse(sr, WlsActionGroups.type, options);
      }

      public static WlsActionGroups parse(Node node) throws XmlException {
         return (WlsActionGroups)XmlBeans.getContextTypeLoader().parse(node, WlsActionGroups.type, (XmlOptions)null);
      }

      public static WlsActionGroups parse(Node node, XmlOptions options) throws XmlException {
         return (WlsActionGroups)XmlBeans.getContextTypeLoader().parse(node, WlsActionGroups.type, options);
      }

      /** @deprecated */
      public static WlsActionGroups parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WlsActionGroups)XmlBeans.getContextTypeLoader().parse(xis, WlsActionGroups.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WlsActionGroups parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WlsActionGroups)XmlBeans.getContextTypeLoader().parse(xis, WlsActionGroups.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsActionGroups.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WlsActionGroups.type, options);
      }

      private Factory() {
      }
   }
}
