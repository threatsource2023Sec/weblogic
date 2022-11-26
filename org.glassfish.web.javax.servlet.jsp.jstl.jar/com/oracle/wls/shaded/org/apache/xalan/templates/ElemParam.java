package com.oracle.wls.shaded.org.apache.xalan.templates;

import com.oracle.wls.shaded.org.apache.xalan.transformer.TransformerImpl;
import com.oracle.wls.shaded.org.apache.xpath.VariableStack;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import javax.xml.transform.TransformerException;

public class ElemParam extends ElemVariable {
   static final long serialVersionUID = -1131781475589006431L;
   int m_qnameID;

   public ElemParam() {
   }

   public int getXSLToken() {
      return 41;
   }

   public String getNodeName() {
      return "param";
   }

   public ElemParam(ElemParam param) throws TransformerException {
      super(param);
   }

   public void compose(StylesheetRoot sroot) throws TransformerException {
      super.compose(sroot);
      this.m_qnameID = sroot.getComposeState().getQNameID(this.m_qname);
      int parentToken = this.m_parentNode.getXSLToken();
      if (parentToken == 19 || parentToken == 88) {
         ++((ElemTemplate)this.m_parentNode).m_inArgsSize;
      }

   }

   public void execute(TransformerImpl transformer) throws TransformerException {
      if (transformer.getDebug()) {
         transformer.getTraceManager().fireTraceEvent((ElemTemplateElement)this);
      }

      VariableStack vars = transformer.getXPathContext().getVarStack();
      if (!vars.isLocalSet(this.m_index)) {
         int sourceNode = transformer.getXPathContext().getCurrentNode();
         XObject var = this.getValue(transformer, sourceNode);
         transformer.getXPathContext().getVarStack().setLocalVariable(this.m_index, var);
      }

      if (transformer.getDebug()) {
         transformer.getTraceManager().fireTraceEndEvent((ElemTemplateElement)this);
      }

   }
}
