package com.oracle.wls.shaded.org.apache.xalan.xsltc.dom;

import com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.BasisLibrary;
import com.oracle.wls.shaded.org.apache.xml.dtm.DTMAxisIterator;
import com.oracle.wls.shaded.org.apache.xml.dtm.ref.DTMAxisIteratorBase;

public final class AbsoluteIterator extends DTMAxisIteratorBase {
   private DTMAxisIterator _source;

   public AbsoluteIterator(DTMAxisIterator source) {
      this._source = source;
   }

   public void setRestartable(boolean isRestartable) {
      this._isRestartable = isRestartable;
      this._source.setRestartable(isRestartable);
   }

   public DTMAxisIterator setStartNode(int node) {
      this._startNode = 0;
      if (this._isRestartable) {
         this._source.setStartNode(this._startNode);
         this.resetPosition();
      }

      return this;
   }

   public int next() {
      return this.returnNode(this._source.next());
   }

   public DTMAxisIterator cloneIterator() {
      try {
         AbsoluteIterator clone = (AbsoluteIterator)super.clone();
         clone._source = this._source.cloneIterator();
         clone.resetPosition();
         clone._isRestartable = false;
         return clone;
      } catch (CloneNotSupportedException var2) {
         BasisLibrary.runTimeError("ITERATOR_CLONE_ERR", (Object)var2.toString());
         return null;
      }
   }

   public DTMAxisIterator reset() {
      this._source.reset();
      return this.resetPosition();
   }

   public void setMark() {
      this._source.setMark();
   }

   public void gotoMark() {
      this._source.gotoMark();
   }
}
