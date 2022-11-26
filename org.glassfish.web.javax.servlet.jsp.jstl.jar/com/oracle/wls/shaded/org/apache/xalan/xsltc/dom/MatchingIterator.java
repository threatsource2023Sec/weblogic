package com.oracle.wls.shaded.org.apache.xalan.xsltc.dom;

import com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.BasisLibrary;
import com.oracle.wls.shaded.org.apache.xml.dtm.DTMAxisIterator;
import com.oracle.wls.shaded.org.apache.xml.dtm.ref.DTMAxisIteratorBase;

public final class MatchingIterator extends DTMAxisIteratorBase {
   private DTMAxisIterator _source;
   private final int _match;

   public MatchingIterator(int match, DTMAxisIterator source) {
      this._source = source;
      this._match = match;
   }

   public void setRestartable(boolean isRestartable) {
      this._isRestartable = isRestartable;
      this._source.setRestartable(isRestartable);
   }

   public DTMAxisIterator cloneIterator() {
      try {
         MatchingIterator clone = (MatchingIterator)super.clone();
         clone._source = this._source.cloneIterator();
         clone._isRestartable = false;
         return clone.reset();
      } catch (CloneNotSupportedException var2) {
         BasisLibrary.runTimeError("ITERATOR_CLONE_ERR", (Object)var2.toString());
         return null;
      }
   }

   public DTMAxisIterator setStartNode(int node) {
      if (this._isRestartable) {
         this._source.setStartNode(node);

         for(this._position = 1; (node = this._source.next()) != -1 && node != this._match; ++this._position) {
         }
      }

      return this;
   }

   public DTMAxisIterator reset() {
      this._source.reset();
      return this.resetPosition();
   }

   public int next() {
      return this._source.next();
   }

   public int getLast() {
      if (this._last == -1) {
         this._last = this._source.getLast();
      }

      return this._last;
   }

   public int getPosition() {
      return this._position;
   }

   public void setMark() {
      this._source.setMark();
   }

   public void gotoMark() {
      this._source.gotoMark();
   }
}
