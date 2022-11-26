package com.oracle.wls.shaded.org.apache.xalan.xsltc.dom;

import com.oracle.wls.shaded.org.apache.xalan.xsltc.DOM;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.Translet;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.util.IntegerArray;
import com.oracle.wls.shaded.org.apache.xml.dtm.DTMAxisIterator;

public abstract class MultipleNodeCounter extends NodeCounter {
   private DTMAxisIterator _precSiblings = null;

   public MultipleNodeCounter(Translet translet, DOM document, DTMAxisIterator iterator) {
      super(translet, document, iterator);
   }

   public NodeCounter setStartNode(int node) {
      this._node = node;
      this._nodeType = this._document.getExpandedTypeID(node);
      this._precSiblings = this._document.getAxisIterator(12);
      return this;
   }

   public String getCounter() {
      if (this._value != -2.147483648E9) {
         if (this._value == 0.0) {
            return "0";
         } else if (Double.isNaN(this._value)) {
            return "NaN";
         } else if (this._value < 0.0 && Double.isInfinite(this._value)) {
            return "-Infinity";
         } else {
            return Double.isInfinite(this._value) ? "Infinity" : this.formatNumbers((int)this._value);
         }
      } else {
         IntegerArray ancestors = new IntegerArray();
         int next = this._node;
         ancestors.add(next);

         while((next = this._document.getParent(next)) > -1 && !this.matchesFrom(next)) {
            ancestors.add(next);
         }

         int nAncestors = ancestors.cardinality();
         int[] counters = new int[nAncestors];

         int j;
         for(j = 0; j < nAncestors; ++j) {
            counters[j] = Integer.MIN_VALUE;
         }

         j = 0;

         for(int i = nAncestors - 1; i >= 0; ++j) {
            int var10000 = counters[j];
            int ancestor = ancestors.at(i);
            if (this.matchesCount(ancestor)) {
               this._precSiblings.setStartNode(ancestor);

               while((next = this._precSiblings.next()) != -1) {
                  if (this.matchesCount(next)) {
                     counters[j] = counters[j] == Integer.MIN_VALUE ? 1 : counters[j] + 1;
                  }
               }

               counters[j] = counters[j] == Integer.MIN_VALUE ? 1 : counters[j] + 1;
            }

            --i;
         }

         return this.formatNumbers(counters);
      }
   }

   public static NodeCounter getDefaultNodeCounter(Translet translet, DOM document, DTMAxisIterator iterator) {
      return new DefaultMultipleNodeCounter(translet, document, iterator);
   }

   static class DefaultMultipleNodeCounter extends MultipleNodeCounter {
      public DefaultMultipleNodeCounter(Translet translet, DOM document, DTMAxisIterator iterator) {
         super(translet, document, iterator);
      }
   }
}
