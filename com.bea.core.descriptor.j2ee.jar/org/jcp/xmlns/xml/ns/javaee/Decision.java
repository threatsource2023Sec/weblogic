package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface Decision extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Decision.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("decisione129type");

   Properties getProperties();

   boolean isSetProperties();

   void setProperties(Properties var1);

   Properties addNewProperties();

   void unsetProperties();

   End[] getEndArray();

   End getEndArray(int var1);

   int sizeOfEndArray();

   void setEndArray(End[] var1);

   void setEndArray(int var1, End var2);

   End insertNewEnd(int var1);

   End addNewEnd();

   void removeEnd(int var1);

   Fail[] getFailArray();

   Fail getFailArray(int var1);

   int sizeOfFailArray();

   void setFailArray(Fail[] var1);

   void setFailArray(int var1, Fail var2);

   Fail insertNewFail(int var1);

   Fail addNewFail();

   void removeFail(int var1);

   Next[] getNextArray();

   Next getNextArray(int var1);

   int sizeOfNextArray();

   void setNextArray(Next[] var1);

   void setNextArray(int var1, Next var2);

   Next insertNewNext(int var1);

   Next addNewNext();

   void removeNext(int var1);

   Stop[] getStopArray();

   Stop getStopArray(int var1);

   int sizeOfStopArray();

   void setStopArray(Stop[] var1);

   void setStopArray(int var1, Stop var2);

   Stop insertNewStop(int var1);

   Stop addNewStop();

   void removeStop(int var1);

   java.lang.String getId();

   XmlID xgetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   java.lang.String getRef();

   ArtifactRef xgetRef();

   void setRef(java.lang.String var1);

   void xsetRef(ArtifactRef var1);

   public static final class Factory {
      public static Decision newInstance() {
         return (Decision)XmlBeans.getContextTypeLoader().newInstance(Decision.type, (XmlOptions)null);
      }

      public static Decision newInstance(XmlOptions options) {
         return (Decision)XmlBeans.getContextTypeLoader().newInstance(Decision.type, options);
      }

      public static Decision parse(java.lang.String xmlAsString) throws XmlException {
         return (Decision)XmlBeans.getContextTypeLoader().parse(xmlAsString, Decision.type, (XmlOptions)null);
      }

      public static Decision parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (Decision)XmlBeans.getContextTypeLoader().parse(xmlAsString, Decision.type, options);
      }

      public static Decision parse(File file) throws XmlException, IOException {
         return (Decision)XmlBeans.getContextTypeLoader().parse(file, Decision.type, (XmlOptions)null);
      }

      public static Decision parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Decision)XmlBeans.getContextTypeLoader().parse(file, Decision.type, options);
      }

      public static Decision parse(URL u) throws XmlException, IOException {
         return (Decision)XmlBeans.getContextTypeLoader().parse(u, Decision.type, (XmlOptions)null);
      }

      public static Decision parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Decision)XmlBeans.getContextTypeLoader().parse(u, Decision.type, options);
      }

      public static Decision parse(InputStream is) throws XmlException, IOException {
         return (Decision)XmlBeans.getContextTypeLoader().parse(is, Decision.type, (XmlOptions)null);
      }

      public static Decision parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Decision)XmlBeans.getContextTypeLoader().parse(is, Decision.type, options);
      }

      public static Decision parse(Reader r) throws XmlException, IOException {
         return (Decision)XmlBeans.getContextTypeLoader().parse(r, Decision.type, (XmlOptions)null);
      }

      public static Decision parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Decision)XmlBeans.getContextTypeLoader().parse(r, Decision.type, options);
      }

      public static Decision parse(XMLStreamReader sr) throws XmlException {
         return (Decision)XmlBeans.getContextTypeLoader().parse(sr, Decision.type, (XmlOptions)null);
      }

      public static Decision parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Decision)XmlBeans.getContextTypeLoader().parse(sr, Decision.type, options);
      }

      public static Decision parse(Node node) throws XmlException {
         return (Decision)XmlBeans.getContextTypeLoader().parse(node, Decision.type, (XmlOptions)null);
      }

      public static Decision parse(Node node, XmlOptions options) throws XmlException {
         return (Decision)XmlBeans.getContextTypeLoader().parse(node, Decision.type, options);
      }

      /** @deprecated */
      public static Decision parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Decision)XmlBeans.getContextTypeLoader().parse(xis, Decision.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Decision parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Decision)XmlBeans.getContextTypeLoader().parse(xis, Decision.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Decision.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Decision.type, options);
      }

      private Factory() {
      }
   }
}
