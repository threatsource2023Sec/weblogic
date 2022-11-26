package org.apache.commons.pool.impl;

import org.apache.commons.pool.KeyedObjectPool;
import org.apache.commons.pool.KeyedObjectPoolFactory;
import org.apache.commons.pool.KeyedPoolableObjectFactory;

public class GenericKeyedObjectPoolFactory implements KeyedObjectPoolFactory {
   /** @deprecated */
   @Deprecated
   protected int _maxIdle;
   /** @deprecated */
   @Deprecated
   protected int _maxActive;
   /** @deprecated */
   @Deprecated
   protected int _maxTotal;
   /** @deprecated */
   @Deprecated
   protected int _minIdle;
   /** @deprecated */
   @Deprecated
   protected long _maxWait;
   /** @deprecated */
   @Deprecated
   protected byte _whenExhaustedAction;
   /** @deprecated */
   @Deprecated
   protected boolean _testOnBorrow;
   /** @deprecated */
   @Deprecated
   protected boolean _testOnReturn;
   /** @deprecated */
   @Deprecated
   protected boolean _testWhileIdle;
   /** @deprecated */
   @Deprecated
   protected long _timeBetweenEvictionRunsMillis;
   /** @deprecated */
   @Deprecated
   protected int _numTestsPerEvictionRun;
   /** @deprecated */
   @Deprecated
   protected long _minEvictableIdleTimeMillis;
   /** @deprecated */
   @Deprecated
   protected KeyedPoolableObjectFactory _factory;
   /** @deprecated */
   @Deprecated
   protected boolean _lifo;

   public GenericKeyedObjectPoolFactory(KeyedPoolableObjectFactory factory) {
      this(factory, 8, (byte)1, -1L, 8, false, false, -1L, 3, 1800000L, false);
   }

   public GenericKeyedObjectPoolFactory(KeyedPoolableObjectFactory factory, GenericKeyedObjectPool.Config config) throws NullPointerException {
      this(factory, config.maxActive, config.whenExhaustedAction, config.maxWait, config.maxIdle, config.maxTotal, config.minIdle, config.testOnBorrow, config.testOnReturn, config.timeBetweenEvictionRunsMillis, config.numTestsPerEvictionRun, config.minEvictableIdleTimeMillis, config.testWhileIdle, config.lifo);
   }

   public GenericKeyedObjectPoolFactory(KeyedPoolableObjectFactory factory, int maxActive) {
      this(factory, maxActive, (byte)1, -1L, 8, -1, false, false, -1L, 3, 1800000L, false);
   }

   public GenericKeyedObjectPoolFactory(KeyedPoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait) {
      this(factory, maxActive, whenExhaustedAction, maxWait, 8, -1, false, false, -1L, 3, 1800000L, false);
   }

   public GenericKeyedObjectPoolFactory(KeyedPoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait, boolean testOnBorrow, boolean testOnReturn) {
      this(factory, maxActive, whenExhaustedAction, maxWait, 8, -1, testOnBorrow, testOnReturn, -1L, 3, 1800000L, false);
   }

   public GenericKeyedObjectPoolFactory(KeyedPoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle) {
      this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, -1, false, false, -1L, 3, 1800000L, false);
   }

   public GenericKeyedObjectPoolFactory(KeyedPoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, int maxTotal) {
      this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, maxTotal, false, false, -1L, 3, 1800000L, false);
   }

   public GenericKeyedObjectPoolFactory(KeyedPoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, boolean testOnBorrow, boolean testOnReturn) {
      this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, -1, testOnBorrow, testOnReturn, -1L, 3, 1800000L, false);
   }

   public GenericKeyedObjectPoolFactory(KeyedPoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, boolean testOnBorrow, boolean testOnReturn, long timeBetweenEvictionRunsMillis, int numTestsPerEvictionRun, long minEvictableIdleTimeMillis, boolean testWhileIdle) {
      this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, -1, testOnBorrow, testOnReturn, timeBetweenEvictionRunsMillis, numTestsPerEvictionRun, minEvictableIdleTimeMillis, testWhileIdle);
   }

   public GenericKeyedObjectPoolFactory(KeyedPoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, int maxTotal, boolean testOnBorrow, boolean testOnReturn, long timeBetweenEvictionRunsMillis, int numTestsPerEvictionRun, long minEvictableIdleTimeMillis, boolean testWhileIdle) {
      this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, maxTotal, 0, testOnBorrow, testOnReturn, timeBetweenEvictionRunsMillis, numTestsPerEvictionRun, minEvictableIdleTimeMillis, testWhileIdle);
   }

   public GenericKeyedObjectPoolFactory(KeyedPoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, int maxTotal, int minIdle, boolean testOnBorrow, boolean testOnReturn, long timeBetweenEvictionRunsMillis, int numTestsPerEvictionRun, long minEvictableIdleTimeMillis, boolean testWhileIdle) {
      this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, maxTotal, minIdle, testOnBorrow, testOnReturn, timeBetweenEvictionRunsMillis, numTestsPerEvictionRun, minEvictableIdleTimeMillis, testWhileIdle, true);
   }

   public GenericKeyedObjectPoolFactory(KeyedPoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, int maxTotal, int minIdle, boolean testOnBorrow, boolean testOnReturn, long timeBetweenEvictionRunsMillis, int numTestsPerEvictionRun, long minEvictableIdleTimeMillis, boolean testWhileIdle, boolean lifo) {
      this._maxIdle = 8;
      this._maxActive = 8;
      this._maxTotal = -1;
      this._minIdle = 0;
      this._maxWait = -1L;
      this._whenExhaustedAction = 1;
      this._testOnBorrow = false;
      this._testOnReturn = false;
      this._testWhileIdle = false;
      this._timeBetweenEvictionRunsMillis = -1L;
      this._numTestsPerEvictionRun = 3;
      this._minEvictableIdleTimeMillis = 1800000L;
      this._factory = null;
      this._lifo = true;
      this._maxIdle = maxIdle;
      this._maxActive = maxActive;
      this._maxTotal = maxTotal;
      this._minIdle = minIdle;
      this._maxWait = maxWait;
      this._whenExhaustedAction = whenExhaustedAction;
      this._testOnBorrow = testOnBorrow;
      this._testOnReturn = testOnReturn;
      this._testWhileIdle = testWhileIdle;
      this._timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
      this._numTestsPerEvictionRun = numTestsPerEvictionRun;
      this._minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
      this._factory = factory;
      this._lifo = lifo;
   }

   public KeyedObjectPool createPool() {
      return new GenericKeyedObjectPool(this._factory, this._maxActive, this._whenExhaustedAction, this._maxWait, this._maxIdle, this._maxTotal, this._minIdle, this._testOnBorrow, this._testOnReturn, this._timeBetweenEvictionRunsMillis, this._numTestsPerEvictionRun, this._minEvictableIdleTimeMillis, this._testWhileIdle, this._lifo);
   }

   public int getMaxIdle() {
      return this._maxIdle;
   }

   public int getMaxActive() {
      return this._maxActive;
   }

   public int getMaxTotal() {
      return this._maxTotal;
   }

   public int getMinIdle() {
      return this._minIdle;
   }

   public long getMaxWait() {
      return this._maxWait;
   }

   public byte getWhenExhaustedAction() {
      return this._whenExhaustedAction;
   }

   public boolean getTestOnBorrow() {
      return this._testOnBorrow;
   }

   public boolean getTestOnReturn() {
      return this._testOnReturn;
   }

   public boolean getTestWhileIdle() {
      return this._testWhileIdle;
   }

   public long getTimeBetweenEvictionRunsMillis() {
      return this._timeBetweenEvictionRunsMillis;
   }

   public int getNumTestsPerEvictionRun() {
      return this._numTestsPerEvictionRun;
   }

   public long getMinEvictableIdleTimeMillis() {
      return this._minEvictableIdleTimeMillis;
   }

   public KeyedPoolableObjectFactory getFactory() {
      return this._factory;
   }

   public boolean getLifo() {
      return this._lifo;
   }
}
