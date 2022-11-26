package com.oracle.wls.shaded.org.apache.xalan.templates;

import com.oracle.wls.shaded.org.apache.xalan.transformer.TransformerImpl;
import javax.xml.transform.TransformerException;

public class ElemFallback extends ElemTemplateElement {
   static final long serialVersionUID = 1782962139867340703L;

   public int getXSLToken() {
      return 57;
   }

   public String getNodeName() {
      return "fallback";
   }

   public void execute(TransformerImpl transformer) throws TransformerException {
   }

   public void executeFallback(TransformerImpl transformer) throws TransformerException {
      int parentElemType = this.m_parentNode.getXSLToken();
      if (79 != parentElemType && -1 != parentElemType) {
         System.out.println("Error!  parent of xsl:fallback must be an extension or unknown element!");
      } else {
         if (transformer.getDebug()) {
            transformer.getTraceManager().fireTraceEvent((ElemTemplateElement)this);
         }

         transformer.executeChildTemplates(this, true);
         if (transformer.getDebug()) {
            transformer.getTraceManager().fireTraceEndEvent((ElemTemplateElement)this);
         }
      }

   }
}
