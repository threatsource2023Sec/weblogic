package org.apache.commons.pool;

public interface ObjectPoolFactory {
   ObjectPool createPool() throws IllegalStateException;
}
