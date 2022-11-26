package com.oracle.wls.shaded.org.apache.xalan.xsltc.dom;

import com.oracle.wls.shaded.org.apache.xalan.xsltc.DOM;
import com.oracle.wls.shaded.org.apache.xml.dtm.DTMAxisIterator;

public final class UnionIterator extends MultiValuedNodeHeapIterator {
   private final DOM _dom;

   public UnionIterator(DOM dom) {
      this._dom = dom;
   }

   public UnionIterator addIterator(DTMAxisIterator iterator) {
      this.addHeapNode(new LookAheadIterator(iterator));
      return this;
   }

   private final class LookAheadIterator extends MultiValuedNodeHeapIterator.HeapNode {
      public DTMAxisIterator iterator;

      public LookAheadIterator(DTMAxisIterator iterator) {
         super();
         this.iterator = iterator;
      }

      public int step() {
         this._node = this.iterator.next();
         return this._node;
      }

      public MultiValuedNodeHeapIterator.HeapNode cloneHeapNode() {
         LookAheadIterator clone = (LookAheadIterator)super.cloneHeapNode();
         clone.iterator = this.iterator.cloneIterator();
         return clone;
      }

      public void setMark() {
         super.setMark();
         this.iterator.setMark();
      }

      public void gotoMark() {
         super.gotoMark();
         this.iterator.gotoMark();
      }

      public boolean isLessThan(MultiValuedNodeHeapIterator.HeapNode heapNode) {
         LookAheadIterator comparand = (LookAheadIterator)heapNode;
         return UnionIterator.this._dom.lessThan(this._node, heapNode._node);
      }

      public MultiValuedNodeHeapIterator.HeapNode setStartNode(int node) {
         this.iterator.setStartNode(node);
         return this;
      }

      public MultiValuedNodeHeapIterator.HeapNode reset() {
         this.iterator.reset();
         return this;
      }
   }
}
