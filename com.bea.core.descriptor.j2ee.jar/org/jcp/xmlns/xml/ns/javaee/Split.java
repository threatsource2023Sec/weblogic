package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface Split extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Split.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("split38ddtype");

   Flow[] getFlowArray();

   Flow getFlowArray(int var1);

   int sizeOfFlowArray();

   void setFlowArray(Flow[] var1);

   void setFlowArray(int var1, Flow var2);

   Flow insertNewFlow(int var1);

   Flow addNewFlow();

   void removeFlow(int var1);

   java.lang.String getId();

   XmlID xgetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   java.lang.String getNext();

   XmlString xgetNext();

   boolean isSetNext();

   void setNext(java.lang.String var1);

   void xsetNext(XmlString var1);

   void unsetNext();

   public static final class Factory {
      public static Split newInstance() {
         return (Split)XmlBeans.getContextTypeLoader().newInstance(Split.type, (XmlOptions)null);
      }

      public static Split newInstance(XmlOptions options) {
         return (Split)XmlBeans.getContextTypeLoader().newInstance(Split.type, options);
      }

      public static Split parse(java.lang.String xmlAsString) throws XmlException {
         return (Split)XmlBeans.getContextTypeLoader().parse(xmlAsString, Split.type, (XmlOptions)null);
      }

      public static Split parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (Split)XmlBeans.getContextTypeLoader().parse(xmlAsString, Split.type, options);
      }

      public static Split parse(File file) throws XmlException, IOException {
         return (Split)XmlBeans.getContextTypeLoader().parse(file, Split.type, (XmlOptions)null);
      }

      public static Split parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Split)XmlBeans.getContextTypeLoader().parse(file, Split.type, options);
      }

      public static Split parse(URL u) throws XmlException, IOException {
         return (Split)XmlBeans.getContextTypeLoader().parse(u, Split.type, (XmlOptions)null);
      }

      public static Split parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Split)XmlBeans.getContextTypeLoader().parse(u, Split.type, options);
      }

      public static Split parse(InputStream is) throws XmlException, IOException {
         return (Split)XmlBeans.getContextTypeLoader().parse(is, Split.type, (XmlOptions)null);
      }

      public static Split parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Split)XmlBeans.getContextTypeLoader().parse(is, Split.type, options);
      }

      public static Split parse(Reader r) throws XmlException, IOException {
         return (Split)XmlBeans.getContextTypeLoader().parse(r, Split.type, (XmlOptions)null);
      }

      public static Split parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Split)XmlBeans.getContextTypeLoader().parse(r, Split.type, options);
      }

      public static Split parse(XMLStreamReader sr) throws XmlException {
         return (Split)XmlBeans.getContextTypeLoader().parse(sr, Split.type, (XmlOptions)null);
      }

      public static Split parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Split)XmlBeans.getContextTypeLoader().parse(sr, Split.type, options);
      }

      public static Split parse(Node node) throws XmlException {
         return (Split)XmlBeans.getContextTypeLoader().parse(node, Split.type, (XmlOptions)null);
      }

      public static Split parse(Node node, XmlOptions options) throws XmlException {
         return (Split)XmlBeans.getContextTypeLoader().parse(node, Split.type, options);
      }

      /** @deprecated */
      public static Split parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Split)XmlBeans.getContextTypeLoader().parse(xis, Split.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Split parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Split)XmlBeans.getContextTypeLoader().parse(xis, Split.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Split.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Split.type, options);
      }

      private Factory() {
      }
   }
}
