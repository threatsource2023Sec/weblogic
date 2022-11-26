package com.oracle.xmlns.weblogic.multiVersionState;

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

public interface StateType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(StateType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("statetype86bbtype");

   String[] getTargetArray();

   String getTargetArray(int var1);

   XmlString[] xgetTargetArray();

   XmlString xgetTargetArray(int var1);

   int sizeOfTargetArray();

   void setTargetArray(String[] var1);

   void setTargetArray(int var1, String var2);

   void xsetTargetArray(XmlString[] var1);

   void xsetTargetArray(int var1, XmlString var2);

   void insertTarget(int var1, String var2);

   void addTarget(String var1);

   XmlString insertNewTarget(int var1);

   XmlString addNewTarget();

   void removeTarget(int var1);

   String getValue();

   XmlString xgetValue();

   void setValue(String var1);

   void xsetValue(XmlString var1);

   public static final class Factory {
      public static StateType newInstance() {
         return (StateType)XmlBeans.getContextTypeLoader().newInstance(StateType.type, (XmlOptions)null);
      }

      public static StateType newInstance(XmlOptions options) {
         return (StateType)XmlBeans.getContextTypeLoader().newInstance(StateType.type, options);
      }

      public static StateType parse(String xmlAsString) throws XmlException {
         return (StateType)XmlBeans.getContextTypeLoader().parse(xmlAsString, StateType.type, (XmlOptions)null);
      }

      public static StateType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (StateType)XmlBeans.getContextTypeLoader().parse(xmlAsString, StateType.type, options);
      }

      public static StateType parse(File file) throws XmlException, IOException {
         return (StateType)XmlBeans.getContextTypeLoader().parse(file, StateType.type, (XmlOptions)null);
      }

      public static StateType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (StateType)XmlBeans.getContextTypeLoader().parse(file, StateType.type, options);
      }

      public static StateType parse(URL u) throws XmlException, IOException {
         return (StateType)XmlBeans.getContextTypeLoader().parse(u, StateType.type, (XmlOptions)null);
      }

      public static StateType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (StateType)XmlBeans.getContextTypeLoader().parse(u, StateType.type, options);
      }

      public static StateType parse(InputStream is) throws XmlException, IOException {
         return (StateType)XmlBeans.getContextTypeLoader().parse(is, StateType.type, (XmlOptions)null);
      }

      public static StateType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (StateType)XmlBeans.getContextTypeLoader().parse(is, StateType.type, options);
      }

      public static StateType parse(Reader r) throws XmlException, IOException {
         return (StateType)XmlBeans.getContextTypeLoader().parse(r, StateType.type, (XmlOptions)null);
      }

      public static StateType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (StateType)XmlBeans.getContextTypeLoader().parse(r, StateType.type, options);
      }

      public static StateType parse(XMLStreamReader sr) throws XmlException {
         return (StateType)XmlBeans.getContextTypeLoader().parse(sr, StateType.type, (XmlOptions)null);
      }

      public static StateType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (StateType)XmlBeans.getContextTypeLoader().parse(sr, StateType.type, options);
      }

      public static StateType parse(Node node) throws XmlException {
         return (StateType)XmlBeans.getContextTypeLoader().parse(node, StateType.type, (XmlOptions)null);
      }

      public static StateType parse(Node node, XmlOptions options) throws XmlException {
         return (StateType)XmlBeans.getContextTypeLoader().parse(node, StateType.type, options);
      }

      /** @deprecated */
      public static StateType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (StateType)XmlBeans.getContextTypeLoader().parse(xis, StateType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static StateType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (StateType)XmlBeans.getContextTypeLoader().parse(xis, StateType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, StateType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, StateType.type, options);
      }

      private Factory() {
      }
   }
}
