package com.oracle.wls.shaded.org.apache.xpath.axes;

import com.oracle.wls.shaded.org.apache.xml.dtm.DTMIterator;
import com.oracle.wls.shaded.org.apache.xml.utils.PrefixResolver;
import com.oracle.wls.shaded.org.apache.xpath.VariableStack;
import com.oracle.wls.shaded.org.apache.xpath.compiler.Compiler;
import com.oracle.wls.shaded.org.apache.xpath.compiler.OpMap;
import javax.xml.transform.TransformerException;

public abstract class BasicTestIterator extends LocPathIterator {
   static final long serialVersionUID = 3505378079378096623L;

   protected BasicTestIterator() {
   }

   protected BasicTestIterator(PrefixResolver nscontext) {
      super(nscontext);
   }

   protected BasicTestIterator(Compiler compiler, int opPos, int analysis) throws TransformerException {
      super(compiler, opPos, analysis, false);
      int firstStepPos = OpMap.getFirstChildPos(opPos);
      int whatToShow = compiler.getWhatToShow(firstStepPos);
      if (0 != (whatToShow & 4163) && whatToShow != -1) {
         this.initNodeTest(whatToShow, compiler.getStepNS(firstStepPos), compiler.getStepLocalName(firstStepPos));
      } else {
         this.initNodeTest(whatToShow);
      }

      this.initPredicateInfo(compiler, firstStepPos);
   }

   protected BasicTestIterator(Compiler compiler, int opPos, int analysis, boolean shouldLoadWalkers) throws TransformerException {
      super(compiler, opPos, analysis, shouldLoadWalkers);
   }

   protected abstract int getNextNode();

   public int nextNode() {
      if (this.m_foundLast) {
         this.m_lastFetched = -1;
         return -1;
      } else {
         if (-1 == this.m_lastFetched) {
            this.resetProximityPositions();
         }

         VariableStack vars;
         int savedStart;
         if (-1 != this.m_stackFrame) {
            vars = this.m_execContext.getVarStack();
            savedStart = vars.getStackFrame();
            vars.setStackFrame(this.m_stackFrame);
         } else {
            vars = null;
            savedStart = 0;
         }

         int var4;
         try {
            int next;
            do {
               next = this.getNextNode();
            } while(-1 != next && 1 != this.acceptNode(next) && next != -1);

            if (-1 == next) {
               this.m_foundLast = true;
               byte var9 = -1;
               return var9;
            }

            ++this.m_pos;
            var4 = next;
         } finally {
            if (-1 != this.m_stackFrame) {
               vars.setStackFrame(savedStart);
            }

         }

         return var4;
      }
   }

   public DTMIterator cloneWithReset() throws CloneNotSupportedException {
      ChildTestIterator clone = (ChildTestIterator)super.cloneWithReset();
      clone.resetProximityPositions();
      return clone;
   }
}
