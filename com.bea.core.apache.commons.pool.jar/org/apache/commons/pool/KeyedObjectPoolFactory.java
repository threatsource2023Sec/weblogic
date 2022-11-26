package org.apache.commons.pool;

public interface KeyedObjectPoolFactory {
   KeyedObjectPool createPool() throws IllegalStateException;
}
