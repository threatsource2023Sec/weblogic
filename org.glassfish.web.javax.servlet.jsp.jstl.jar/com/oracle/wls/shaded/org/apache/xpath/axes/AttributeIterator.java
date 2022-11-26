package com.oracle.wls.shaded.org.apache.xpath.axes;

import com.oracle.wls.shaded.org.apache.xpath.compiler.Compiler;
import javax.xml.transform.TransformerException;

public class AttributeIterator extends ChildTestIterator {
   static final long serialVersionUID = -8417986700712229686L;

   AttributeIterator(Compiler compiler, int opPos, int analysis) throws TransformerException {
      super(compiler, opPos, analysis);
   }

   protected int getNextNode() {
      this.m_lastFetched = -1 == this.m_lastFetched ? this.m_cdtm.getFirstAttribute(this.m_context) : this.m_cdtm.getNextAttribute(this.m_lastFetched);
      return this.m_lastFetched;
   }

   public int getAxis() {
      return 2;
   }
}
