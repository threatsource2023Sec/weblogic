package com.oracle.wls.shaded.org.apache.xalan.templates;

import com.oracle.wls.shaded.org.apache.xalan.extensions.ExtensionNamespaceSupport;
import com.oracle.wls.shaded.org.apache.xalan.transformer.TransformerImpl;
import com.oracle.wls.shaded.org.apache.xpath.VariableStack;
import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import javax.xml.transform.TransformerException;
import org.w3c.dom.NodeList;

public class ElemExsltFunction extends ElemTemplate {
   static final long serialVersionUID = 272154954793534771L;

   public int getXSLToken() {
      return 88;
   }

   public String getNodeName() {
      return "function";
   }

   public void execute(TransformerImpl transformer, XObject[] args) throws TransformerException {
      XPathContext xctxt = transformer.getXPathContext();
      VariableStack vars = xctxt.getVarStack();
      int thisFrame = vars.getStackFrame();
      int nextFrame = vars.link(this.m_frameSize);
      if (this.m_inArgsSize < args.length) {
         throw new TransformerException("function called with too many args");
      } else {
         if (this.m_inArgsSize > 0) {
            vars.clearLocalSlots(0, this.m_inArgsSize);
            if (args.length > 0) {
               vars.setStackFrame(thisFrame);
               NodeList children = this.getChildNodes();

               for(int i = 0; i < args.length; ++i) {
                  children.item(i);
                  if (children.item(i) instanceof ElemParam) {
                     ElemParam param = (ElemParam)children.item(i);
                     vars.setLocalVariable(param.getIndex(), args[i], nextFrame);
                  }
               }

               vars.setStackFrame(nextFrame);
            }
         }

         if (transformer.getDebug()) {
            transformer.getTraceManager().fireTraceEvent((ElemTemplateElement)this);
         }

         vars.setStackFrame(nextFrame);
         transformer.executeChildTemplates(this, true);
         vars.unlink(thisFrame);
         if (transformer.getDebug()) {
            transformer.getTraceManager().fireTraceEndEvent((ElemTemplateElement)this);
         }

      }
   }

   public void compose(StylesheetRoot sroot) throws TransformerException {
      super.compose(sroot);
      String namespace = this.getName().getNamespace();
      String handlerClass = sroot.getExtensionHandlerClass();
      Object[] args = new Object[]{namespace, sroot};
      ExtensionNamespaceSupport extNsSpt = new ExtensionNamespaceSupport(namespace, handlerClass, args);
      sroot.getExtensionNamespacesManager().registerExtension(extNsSpt);
      if (!namespace.equals("http://exslt.org/functions")) {
         namespace = "http://exslt.org/functions";
         args = new Object[]{namespace, sroot};
         extNsSpt = new ExtensionNamespaceSupport(namespace, handlerClass, args);
         sroot.getExtensionNamespacesManager().registerExtension(extNsSpt);
      }

   }
}
