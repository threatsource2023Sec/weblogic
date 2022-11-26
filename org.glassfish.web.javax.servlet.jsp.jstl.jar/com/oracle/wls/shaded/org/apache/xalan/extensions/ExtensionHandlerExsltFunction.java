package com.oracle.wls.shaded.org.apache.xalan.extensions;

import com.oracle.wls.shaded.org.apache.xalan.res.XSLMessages;
import com.oracle.wls.shaded.org.apache.xalan.templates.ElemExsltFuncResult;
import com.oracle.wls.shaded.org.apache.xalan.templates.ElemExsltFunction;
import com.oracle.wls.shaded.org.apache.xalan.templates.ElemTemplate;
import com.oracle.wls.shaded.org.apache.xalan.templates.ElemTemplateElement;
import com.oracle.wls.shaded.org.apache.xalan.templates.Stylesheet;
import com.oracle.wls.shaded.org.apache.xalan.templates.StylesheetRoot;
import com.oracle.wls.shaded.org.apache.xalan.transformer.TransformerImpl;
import com.oracle.wls.shaded.org.apache.xml.utils.QName;
import com.oracle.wls.shaded.org.apache.xpath.ExpressionNode;
import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.functions.FuncExtFunction;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import com.oracle.wls.shaded.org.apache.xpath.objects.XString;
import java.io.IOException;
import java.util.Vector;
import javax.xml.transform.TransformerException;

public class ExtensionHandlerExsltFunction extends ExtensionHandler {
   private String m_namespace;
   private StylesheetRoot m_stylesheet;
   private static final QName RESULTQNAME = new QName("http://exslt.org/functions", "result");

   public ExtensionHandlerExsltFunction(String ns, StylesheetRoot stylesheet) {
      super(ns, "xml");
      this.m_namespace = ns;
      this.m_stylesheet = stylesheet;
   }

   public void processElement(String localPart, ElemTemplateElement element, TransformerImpl transformer, Stylesheet stylesheetTree, Object methodKey) throws TransformerException, IOException {
   }

   public ElemExsltFunction getFunction(String funcName) {
      QName qname = new QName(this.m_namespace, funcName);
      ElemTemplate templ = this.m_stylesheet.getTemplateComposed(qname);
      return templ != null && templ instanceof ElemExsltFunction ? (ElemExsltFunction)templ : null;
   }

   public boolean isFunctionAvailable(String funcName) {
      return this.getFunction(funcName) != null;
   }

   public boolean isElementAvailable(String elemName) {
      if (!(new QName(this.m_namespace, elemName)).equals(RESULTQNAME)) {
         return false;
      } else {
         ElemTemplateElement nextElem;
         for(ElemTemplateElement elem = this.m_stylesheet.getFirstChildElem(); elem != null && elem != this.m_stylesheet; elem = nextElem) {
            if (elem instanceof ElemExsltFuncResult && this.ancestorIsFunction(elem)) {
               return true;
            }

            nextElem = elem.getFirstChildElem();
            if (nextElem == null) {
               nextElem = elem.getNextSiblingElem();
            }

            if (nextElem == null) {
               nextElem = elem.getParentElem();
            }
         }

         return false;
      }
   }

   private boolean ancestorIsFunction(ElemTemplateElement child) {
      while(child.getParentElem() != null && !(child.getParentElem() instanceof StylesheetRoot)) {
         if (child.getParentElem() instanceof ElemExsltFunction) {
            return true;
         }

         child = child.getParentElem();
      }

      return false;
   }

   public Object callFunction(String funcName, Vector args, Object methodKey, ExpressionContext exprContext) throws TransformerException {
      throw new TransformerException("This method should not be called.");
   }

   public Object callFunction(FuncExtFunction extFunction, Vector args, ExpressionContext exprContext) throws TransformerException {
      ExpressionNode parent;
      for(parent = extFunction.exprGetParent(); parent != null && !(parent instanceof ElemTemplate); parent = parent.exprGetParent()) {
      }

      ElemTemplate var10000;
      if (parent != null) {
         var10000 = (ElemTemplate)parent;
      } else {
         var10000 = null;
      }

      XObject[] methodArgs = new XObject[args.size()];

      try {
         for(int i = 0; i < methodArgs.length; ++i) {
            methodArgs[i] = XObject.create(args.get(i));
         }

         ElemExsltFunction elemFunc = this.getFunction(extFunction.getFunctionName());
         if (null != elemFunc) {
            XPathContext context = exprContext.getXPathContext();
            TransformerImpl transformer = (TransformerImpl)context.getOwnerObject();
            transformer.pushCurrentFuncResult((Object)null);
            elemFunc.execute(transformer, methodArgs);
            XObject val = (XObject)transformer.popCurrentFuncResult();
            return val == null ? new XString("") : val;
         } else {
            throw new TransformerException(XSLMessages.createMessage("ER_FUNCTION_NOT_FOUND", new Object[]{extFunction.getFunctionName()}));
         }
      } catch (TransformerException var11) {
         throw var11;
      } catch (Exception var12) {
         throw new TransformerException(var12);
      }
   }
}
