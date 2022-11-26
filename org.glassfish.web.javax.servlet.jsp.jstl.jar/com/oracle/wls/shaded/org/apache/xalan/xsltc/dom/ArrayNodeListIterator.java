package com.oracle.wls.shaded.org.apache.xalan.xsltc.dom;

import com.oracle.wls.shaded.org.apache.xml.dtm.DTMAxisIterator;

public class ArrayNodeListIterator implements DTMAxisIterator {
   private int _pos = 0;
   private int _mark = 0;
   private int[] _nodes;
   private static final int[] EMPTY = new int[0];

   public ArrayNodeListIterator(int[] nodes) {
      this._nodes = nodes;
   }

   public int next() {
      return this._pos < this._nodes.length ? this._nodes[this._pos++] : -1;
   }

   public DTMAxisIterator reset() {
      this._pos = 0;
      return this;
   }

   public int getLast() {
      return this._nodes.length;
   }

   public int getPosition() {
      return this._pos;
   }

   public void setMark() {
      this._mark = this._pos;
   }

   public void gotoMark() {
      this._pos = this._mark;
   }

   public DTMAxisIterator setStartNode(int node) {
      if (node == -1) {
         this._nodes = EMPTY;
      }

      return this;
   }

   public int getStartNode() {
      return -1;
   }

   public boolean isReverse() {
      return false;
   }

   public DTMAxisIterator cloneIterator() {
      return new ArrayNodeListIterator(this._nodes);
   }

   public void setRestartable(boolean isRestartable) {
   }

   public int getNodeByPosition(int position) {
      return this._nodes[position - 1];
   }
}
