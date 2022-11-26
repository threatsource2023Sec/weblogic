package com.oracle.wls.shaded.org.apache.xpath.functions;

import com.oracle.wls.shaded.org.apache.xml.dtm.DTMIterator;
import com.oracle.wls.shaded.org.apache.xml.utils.WrappedRuntimeException;
import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.axes.SubContextList;
import com.oracle.wls.shaded.org.apache.xpath.compiler.Compiler;
import com.oracle.wls.shaded.org.apache.xpath.objects.XNumber;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import java.util.Vector;
import javax.xml.transform.TransformerException;

public class FuncPosition extends Function {
   static final long serialVersionUID = -9092846348197271582L;
   private boolean m_isTopLevel;

   public void postCompileStep(Compiler compiler) {
      this.m_isTopLevel = compiler.getLocationPathDepth() == -1;
   }

   public int getPositionInContextNodeList(XPathContext xctxt) {
      SubContextList iter = this.m_isTopLevel ? null : xctxt.getSubContextList();
      if (null != iter) {
         int prox = iter.getProximityPosition(xctxt);
         return prox;
      } else {
         DTMIterator cnl = xctxt.getContextNodeList();
         if (null == cnl) {
            return -1;
         } else {
            int n = cnl.getCurrentNode();
            if (n == -1) {
               if (cnl.getCurrentPos() == 0) {
                  return 0;
               }

               try {
                  cnl = cnl.cloneWithReset();
               } catch (CloneNotSupportedException var6) {
                  throw new WrappedRuntimeException(var6);
               }

               int currentNode = xctxt.getContextNode();

               while(-1 != (n = cnl.nextNode()) && n != currentNode) {
               }
            }

            return cnl.getCurrentPos();
         }
      }
   }

   public XObject execute(XPathContext xctxt) throws TransformerException {
      double pos = (double)this.getPositionInContextNodeList(xctxt);
      return new XNumber(pos);
   }

   public void fixupVariables(Vector vars, int globalsSize) {
   }
}
