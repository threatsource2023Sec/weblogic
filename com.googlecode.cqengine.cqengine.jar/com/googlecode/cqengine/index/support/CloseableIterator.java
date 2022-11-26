package com.googlecode.cqengine.index.support;

import java.io.Closeable;
import java.util.Iterator;

public interface CloseableIterator extends Iterator, Closeable {
   void close();
}
