package com.googlecode.cqengine.resultset.iterator;

import java.util.Iterator;

public abstract class UnmodifiableIterator implements Iterator {
   public final void remove() {
      throw new UnsupportedOperationException("Modification not supported");
   }
}
