package org.python.indexer.ast;

import java.util.ArrayList;
import java.util.List;

public abstract class NSequence extends NNode {
   static final long serialVersionUID = 7996591535766850065L;
   public List elts;

   public NSequence(List elts) {
      this(elts, 0, 1);
   }

   public NSequence(List elts, int start, int end) {
      super(start, end);
      this.elts = (List)(elts != null ? elts : new ArrayList());
      this.addChildren(elts);
   }

   public List getElements() {
      return this.elts;
   }
}
