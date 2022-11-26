package com.oracle.wls.shaded.org.apache.xalan.templates;

import com.oracle.wls.shaded.org.apache.xml.utils.QName;
import com.oracle.wls.shaded.org.apache.xpath.ExpressionOwner;
import com.oracle.wls.shaded.org.apache.xpath.XPathVisitor;
import com.oracle.wls.shaded.org.apache.xpath.operations.Variable;
import java.util.Vector;

public class VarNameCollector extends XPathVisitor {
   Vector m_refs = new Vector();

   public void reset() {
      this.m_refs.removeAllElements();
   }

   public int getVarCount() {
      return this.m_refs.size();
   }

   boolean doesOccur(QName refName) {
      return this.m_refs.contains(refName);
   }

   public boolean visitVariableRef(ExpressionOwner owner, Variable var) {
      this.m_refs.addElement(var.getQName());
      return true;
   }
}
