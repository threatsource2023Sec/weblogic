package com.oracle.wls.shaded.org.apache.xalan.processor;

import com.oracle.wls.shaded.org.apache.xalan.templates.ElemApplyImport;
import com.oracle.wls.shaded.org.apache.xalan.templates.ElemApplyTemplates;
import com.oracle.wls.shaded.org.apache.xalan.templates.ElemAttribute;
import com.oracle.wls.shaded.org.apache.xalan.templates.ElemCallTemplate;
import com.oracle.wls.shaded.org.apache.xalan.templates.ElemComment;
import com.oracle.wls.shaded.org.apache.xalan.templates.ElemCopy;
import com.oracle.wls.shaded.org.apache.xalan.templates.ElemCopyOf;
import com.oracle.wls.shaded.org.apache.xalan.templates.ElemElement;
import com.oracle.wls.shaded.org.apache.xalan.templates.ElemExsltFuncResult;
import com.oracle.wls.shaded.org.apache.xalan.templates.ElemExsltFunction;
import com.oracle.wls.shaded.org.apache.xalan.templates.ElemFallback;
import com.oracle.wls.shaded.org.apache.xalan.templates.ElemLiteralResult;
import com.oracle.wls.shaded.org.apache.xalan.templates.ElemMessage;
import com.oracle.wls.shaded.org.apache.xalan.templates.ElemNumber;
import com.oracle.wls.shaded.org.apache.xalan.templates.ElemPI;
import com.oracle.wls.shaded.org.apache.xalan.templates.ElemParam;
import com.oracle.wls.shaded.org.apache.xalan.templates.ElemTemplate;
import com.oracle.wls.shaded.org.apache.xalan.templates.ElemTemplateElement;
import com.oracle.wls.shaded.org.apache.xalan.templates.ElemText;
import com.oracle.wls.shaded.org.apache.xalan.templates.ElemTextLiteral;
import com.oracle.wls.shaded.org.apache.xalan.templates.ElemValueOf;
import com.oracle.wls.shaded.org.apache.xalan.templates.ElemVariable;
import com.oracle.wls.shaded.org.apache.xalan.templates.Stylesheet;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ProcessorExsltFunction extends ProcessorTemplateElem {
   static final long serialVersionUID = 2411427965578315332L;

   public void startElement(StylesheetHandler handler, String uri, String localName, String rawName, Attributes attributes) throws SAXException {
      String msg = "";
      if (!(handler.getElemTemplateElement() instanceof Stylesheet)) {
         msg = "func:function element must be top level.";
         handler.error(msg, new SAXException(msg));
      }

      super.startElement(handler, uri, localName, rawName, attributes);
      String val = attributes.getValue("name");
      int indexOfColon = val.indexOf(":");
      if (indexOfColon <= 0) {
         msg = "func:function name must have namespace";
         handler.error(msg, new SAXException(msg));
      }

   }

   protected void appendAndPush(StylesheetHandler handler, ElemTemplateElement elem) throws SAXException {
      super.appendAndPush(handler, elem);
      elem.setDOMBackPointer(handler.getOriginatingNode());
      handler.getStylesheet().setTemplate((ElemTemplate)elem);
   }

   public void endElement(StylesheetHandler handler, String uri, String localName, String rawName) throws SAXException {
      ElemTemplateElement function = handler.getElemTemplateElement();
      this.validate(function, handler);
      super.endElement(handler, uri, localName, rawName);
   }

   public void validate(ElemTemplateElement elem, StylesheetHandler handler) throws SAXException {
      ElemTemplateElement nextElem;
      for(String msg = ""; elem != null; elem = nextElem) {
         if (elem instanceof ElemExsltFuncResult && elem.getNextSiblingElem() != null && !(elem.getNextSiblingElem() instanceof ElemFallback)) {
            msg = "func:result has an illegal following sibling (only xsl:fallback allowed)";
            handler.error(msg, new SAXException(msg));
         }

         if ((elem instanceof ElemApplyImport || elem instanceof ElemApplyTemplates || elem instanceof ElemAttribute || elem instanceof ElemCallTemplate || elem instanceof ElemComment || elem instanceof ElemCopy || elem instanceof ElemCopyOf || elem instanceof ElemElement || elem instanceof ElemLiteralResult || elem instanceof ElemNumber || elem instanceof ElemPI || elem instanceof ElemText || elem instanceof ElemTextLiteral || elem instanceof ElemValueOf) && !this.ancestorIsOk(elem)) {
            msg = "misplaced literal result in a func:function container.";
            handler.error(msg, new SAXException(msg));
         }

         nextElem = elem.getFirstChildElem();

         while(nextElem == null) {
            nextElem = elem.getNextSiblingElem();
            if (nextElem == null) {
               elem = elem.getParentElem();
            }

            if (elem == null || elem instanceof ElemExsltFunction) {
               return;
            }
         }
      }

   }

   boolean ancestorIsOk(ElemTemplateElement child) {
      while(true) {
         if (child.getParentElem() != null && !(child.getParentElem() instanceof ElemExsltFunction)) {
            ElemTemplateElement parent = child.getParentElem();
            if (!(parent instanceof ElemExsltFuncResult) && !(parent instanceof ElemVariable) && !(parent instanceof ElemParam) && !(parent instanceof ElemMessage)) {
               child = parent;
               continue;
            }

            return true;
         }

         return false;
      }
   }
}
