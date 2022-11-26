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

public interface Flow extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Flow.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("flow0217type");

   Decision[] getDecisionArray();

   Decision getDecisionArray(int var1);

   int sizeOfDecisionArray();

   void setDecisionArray(Decision[] var1);

   void setDecisionArray(int var1, Decision var2);

   Decision insertNewDecision(int var1);

   Decision addNewDecision();

   void removeDecision(int var1);

   Flow[] getFlowArray();

   Flow getFlowArray(int var1);

   int sizeOfFlowArray();

   void setFlowArray(Flow[] var1);

   void setFlowArray(int var1, Flow var2);

   Flow insertNewFlow(int var1);

   Flow addNewFlow();

   void removeFlow(int var1);

   Split[] getSplitArray();

   Split getSplitArray(int var1);

   int sizeOfSplitArray();

   void setSplitArray(Split[] var1);

   void setSplitArray(int var1, Split var2);

   Split insertNewSplit(int var1);

   Split addNewSplit();

   void removeSplit(int var1);

   Step[] getStepArray();

   Step getStepArray(int var1);

   int sizeOfStepArray();

   void setStepArray(Step[] var1);

   void setStepArray(int var1, Step var2);

   Step insertNewStep(int var1);

   Step addNewStep();

   void removeStep(int var1);

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

   java.lang.String getNext2();

   XmlString xgetNext2();

   boolean isSetNext2();

   void setNext2(java.lang.String var1);

   void xsetNext2(XmlString var1);

   void unsetNext2();

   public static final class Factory {
      public static Flow newInstance() {
         return (Flow)XmlBeans.getContextTypeLoader().newInstance(Flow.type, (XmlOptions)null);
      }

      public static Flow newInstance(XmlOptions options) {
         return (Flow)XmlBeans.getContextTypeLoader().newInstance(Flow.type, options);
      }

      public static Flow parse(java.lang.String xmlAsString) throws XmlException {
         return (Flow)XmlBeans.getContextTypeLoader().parse(xmlAsString, Flow.type, (XmlOptions)null);
      }

      public static Flow parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (Flow)XmlBeans.getContextTypeLoader().parse(xmlAsString, Flow.type, options);
      }

      public static Flow parse(File file) throws XmlException, IOException {
         return (Flow)XmlBeans.getContextTypeLoader().parse(file, Flow.type, (XmlOptions)null);
      }

      public static Flow parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Flow)XmlBeans.getContextTypeLoader().parse(file, Flow.type, options);
      }

      public static Flow parse(URL u) throws XmlException, IOException {
         return (Flow)XmlBeans.getContextTypeLoader().parse(u, Flow.type, (XmlOptions)null);
      }

      public static Flow parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Flow)XmlBeans.getContextTypeLoader().parse(u, Flow.type, options);
      }

      public static Flow parse(InputStream is) throws XmlException, IOException {
         return (Flow)XmlBeans.getContextTypeLoader().parse(is, Flow.type, (XmlOptions)null);
      }

      public static Flow parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Flow)XmlBeans.getContextTypeLoader().parse(is, Flow.type, options);
      }

      public static Flow parse(Reader r) throws XmlException, IOException {
         return (Flow)XmlBeans.getContextTypeLoader().parse(r, Flow.type, (XmlOptions)null);
      }

      public static Flow parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Flow)XmlBeans.getContextTypeLoader().parse(r, Flow.type, options);
      }

      public static Flow parse(XMLStreamReader sr) throws XmlException {
         return (Flow)XmlBeans.getContextTypeLoader().parse(sr, Flow.type, (XmlOptions)null);
      }

      public static Flow parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Flow)XmlBeans.getContextTypeLoader().parse(sr, Flow.type, options);
      }

      public static Flow parse(Node node) throws XmlException {
         return (Flow)XmlBeans.getContextTypeLoader().parse(node, Flow.type, (XmlOptions)null);
      }

      public static Flow parse(Node node, XmlOptions options) throws XmlException {
         return (Flow)XmlBeans.getContextTypeLoader().parse(node, Flow.type, options);
      }

      /** @deprecated */
      public static Flow parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Flow)XmlBeans.getContextTypeLoader().parse(xis, Flow.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Flow parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Flow)XmlBeans.getContextTypeLoader().parse(xis, Flow.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Flow.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Flow.type, options);
      }

      private Factory() {
      }
   }
}
