package com.googlecode.cqengine.index.support;

public interface CloseableIterable extends Iterable {
   CloseableIterator iterator();
}
