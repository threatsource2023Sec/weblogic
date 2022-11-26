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

public interface InferredIdType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(InferredIdType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("inferredidtype3167type");

   StateType[] getStateArray();

   StateType getStateArray(int var1);

   int sizeOfStateArray();

   void setStateArray(StateType[] var1);

   void setStateArray(int var1, StateType var2);

   StateType insertNewState(int var1);

   StateType addNewState();

   void removeState(int var1);

   String getId();

   XmlString xgetId();

   void setId(String var1);

   void xsetId(XmlString var1);

   public static final class Factory {
      public static InferredIdType newInstance() {
         return (InferredIdType)XmlBeans.getContextTypeLoader().newInstance(InferredIdType.type, (XmlOptions)null);
      }

      public static InferredIdType newInstance(XmlOptions options) {
         return (InferredIdType)XmlBeans.getContextTypeLoader().newInstance(InferredIdType.type, options);
      }

      public static InferredIdType parse(String xmlAsString) throws XmlException {
         return (InferredIdType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InferredIdType.type, (XmlOptions)null);
      }

      public static InferredIdType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (InferredIdType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InferredIdType.type, options);
      }

      public static InferredIdType parse(File file) throws XmlException, IOException {
         return (InferredIdType)XmlBeans.getContextTypeLoader().parse(file, InferredIdType.type, (XmlOptions)null);
      }

      public static InferredIdType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (InferredIdType)XmlBeans.getContextTypeLoader().parse(file, InferredIdType.type, options);
      }

      public static InferredIdType parse(URL u) throws XmlException, IOException {
         return (InferredIdType)XmlBeans.getContextTypeLoader().parse(u, InferredIdType.type, (XmlOptions)null);
      }

      public static InferredIdType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (InferredIdType)XmlBeans.getContextTypeLoader().parse(u, InferredIdType.type, options);
      }

      public static InferredIdType parse(InputStream is) throws XmlException, IOException {
         return (InferredIdType)XmlBeans.getContextTypeLoader().parse(is, InferredIdType.type, (XmlOptions)null);
      }

      public static InferredIdType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (InferredIdType)XmlBeans.getContextTypeLoader().parse(is, InferredIdType.type, options);
      }

      public static InferredIdType parse(Reader r) throws XmlException, IOException {
         return (InferredIdType)XmlBeans.getContextTypeLoader().parse(r, InferredIdType.type, (XmlOptions)null);
      }

      public static InferredIdType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (InferredIdType)XmlBeans.getContextTypeLoader().parse(r, InferredIdType.type, options);
      }

      public static InferredIdType parse(XMLStreamReader sr) throws XmlException {
         return (InferredIdType)XmlBeans.getContextTypeLoader().parse(sr, InferredIdType.type, (XmlOptions)null);
      }

      public static InferredIdType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (InferredIdType)XmlBeans.getContextTypeLoader().parse(sr, InferredIdType.type, options);
      }

      public static InferredIdType parse(Node node) throws XmlException {
         return (InferredIdType)XmlBeans.getContextTypeLoader().parse(node, InferredIdType.type, (XmlOptions)null);
      }

      public static InferredIdType parse(Node node, XmlOptions options) throws XmlException {
         return (InferredIdType)XmlBeans.getContextTypeLoader().parse(node, InferredIdType.type, options);
      }

      /** @deprecated */
      public static InferredIdType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (InferredIdType)XmlBeans.getContextTypeLoader().parse(xis, InferredIdType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static InferredIdType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (InferredIdType)XmlBeans.getContextTypeLoader().parse(xis, InferredIdType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InferredIdType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InferredIdType.type, options);
      }

      private Factory() {
      }
   }
}
