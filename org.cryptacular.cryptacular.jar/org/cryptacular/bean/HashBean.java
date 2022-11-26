package org.cryptacular.bean;

import org.cryptacular.CryptoException;
import org.cryptacular.StreamException;

public interface HashBean {
   Object hash(Object... var1) throws CryptoException, StreamException;

   boolean compare(Object var1, Object... var2) throws CryptoException, StreamException;
}
