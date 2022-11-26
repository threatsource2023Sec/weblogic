package com.oracle.wls.shaded.org.apache.xalan.xsltc.dom;

import com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.BasisLibrary;
import com.oracle.wls.shaded.org.apache.xml.dtm.DTMAxisIterator;
import com.oracle.wls.shaded.org.apache.xml.dtm.DTMFilter;
import com.oracle.wls.shaded.org.apache.xml.dtm.ref.DTMAxisIteratorBase;

public final class FilterIterator extends DTMAxisIteratorBase {
   private DTMAxisIterator _source;
   private final DTMFilter _filter;
   private final boolean _isReverse;

   public FilterIterator(DTMAxisIterator source, DTMFilter filter) {
      this._source = source;
      this._filter = filter;
      this._isReverse = source.isReverse();
   }

   public boolean isReverse() {
      return this._isReverse;
   }

   public void setRestartable(boolean isRestartable) {
      this._isRestartable = isRestartable;
      this._source.setRestartable(isRestartable);
   }

   public DTMAxisIterator cloneIterator() {
      try {
         FilterIterator clone = (FilterIterator)super.clone();
         clone._source = this._source.cloneIterator();
         clone._isRestartable = false;
         return clone.reset();
      } catch (CloneNotSupportedException var2) {
         BasisLibrary.runTimeError("ITERATOR_CLONE_ERR", (Object)var2.toString());
         return null;
      }
   }

   public DTMAxisIterator reset() {
      this._source.reset();
      return this.resetPosition();
   }

   public int next() {
      while(true) {
         int node;
         if ((node = this._source.next()) != -1) {
            if (this._filter.acceptNode(node, -1) != 1) {
               continue;
            }

            return this.returnNode(node);
         }

         return -1;
      }
   }

   public DTMAxisIterator setStartNode(int node) {
      if (this._isRestartable) {
         this._source.setStartNode(this._startNode = node);
         return this.resetPosition();
      } else {
         return this;
      }
   }

   public void setMark() {
      this._source.setMark();
   }

   public void gotoMark() {
      this._source.gotoMark();
   }
}
