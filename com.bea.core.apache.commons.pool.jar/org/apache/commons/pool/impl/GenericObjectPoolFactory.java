package org.apache.commons.pool.impl;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.ObjectPoolFactory;
import org.apache.commons.pool.PoolableObjectFactory;

public class GenericObjectPoolFactory implements ObjectPoolFactory {
   /** @deprecated */
   @Deprecated
   protected int _maxIdle;
   /** @deprecated */
   @Deprecated
   protected int _minIdle;
   /** @deprecated */
   @Deprecated
   protected int _maxActive;
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
   protected long _softMinEvictableIdleTimeMillis;
   /** @deprecated */
   @Deprecated
   protected boolean _lifo;
   /** @deprecated */
   @Deprecated
   protected PoolableObjectFactory _factory;

   public GenericObjectPoolFactory(PoolableObjectFactory factory) {
      this(factory, 8, (byte)1, -1L, 8, 0, false, false, -1L, 3, 1800000L, false);
   }

   public GenericObjectPoolFactory(PoolableObjectFactory factory, GenericObjectPool.Config config) throws NullPointerException {
      this(factory, config.maxActive, config.whenExhaustedAction, config.maxWait, config.maxIdle, config.minIdle, config.testOnBorrow, config.testOnReturn, config.timeBetweenEvictionRunsMillis, config.numTestsPerEvictionRun, config.minEvictableIdleTimeMillis, config.testWhileIdle, config.softMinEvictableIdleTimeMillis, config.lifo);
   }

   public GenericObjectPoolFactory(PoolableObjectFactory factory, int maxActive) {
      this(factory, maxActive, (byte)1, -1L, 8, 0, false, false, -1L, 3, 1800000L, false);
   }

   public GenericObjectPoolFactory(PoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait) {
      this(factory, maxActive, whenExhaustedAction, maxWait, 8, 0, false, false, -1L, 3, 1800000L, false);
   }

   public GenericObjectPoolFactory(PoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait, boolean testOnBorrow, boolean testOnReturn) {
      this(factory, maxActive, whenExhaustedAction, maxWait, 8, 0, testOnBorrow, testOnReturn, -1L, 3, 1800000L, false);
   }

   public GenericObjectPoolFactory(PoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle) {
      this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, 0, false, false, -1L, 3, 1800000L, false);
   }

   public GenericObjectPoolFactory(PoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, boolean testOnBorrow, boolean testOnReturn) {
      this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, 0, testOnBorrow, testOnReturn, -1L, 3, 1800000L, false);
   }

   public GenericObjectPoolFactory(PoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, boolean testOnBorrow, boolean testOnReturn, long timeBetweenEvictionRunsMillis, int numTestsPerEvictionRun, long minEvictableIdleTimeMillis, boolean testWhileIdle) {
      this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, 0, testOnBorrow, testOnReturn, timeBetweenEvictionRunsMillis, numTestsPerEvictionRun, minEvictableIdleTimeMillis, testWhileIdle, -1L);
   }

   public GenericObjectPoolFactory(PoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, int minIdle, boolean testOnBorrow, boolean testOnReturn, long timeBetweenEvictionRunsMillis, int numTestsPerEvictionRun, long minEvictableIdleTimeMillis, boolean testWhileIdle) {
      this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, minIdle, testOnBorrow, testOnReturn, timeBetweenEvictionRunsMillis, numTestsPerEvictionRun, minEvictableIdleTimeMillis, testWhileIdle, -1L);
   }

   public GenericObjectPoolFactory(PoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, int minIdle, boolean testOnBorrow, boolean testOnReturn, long timeBetweenEvictionRunsMillis, int numTestsPerEvictionRun, long minEvictableIdleTimeMillis, boolean testWhileIdle, long softMinEvictableIdleTimeMillis) {
      this(factory, maxActive, whenExhaustedAction, maxWait, maxIdle, minIdle, testOnBorrow, testOnReturn, timeBetweenEvictionRunsMillis, numTestsPerEvictionRun, minEvictableIdleTimeMillis, testWhileIdle, softMinEvictableIdleTimeMillis, true);
   }

   public GenericObjectPoolFactory(PoolableObjectFactory factory, int maxActive, byte whenExhaustedAction, long maxWait, int maxIdle, int minIdle, boolean testOnBorrow, boolean testOnReturn, long timeBetweenEvictionRunsMillis, int numTestsPerEvictionRun, long minEvictableIdleTimeMillis, boolean testWhileIdle, long softMinEvictableIdleTimeMillis, boolean lifo) {
      this._maxIdle = 8;
      this._minIdle = 0;
      this._maxActive = 8;
      this._maxWait = -1L;
      this._whenExhaustedAction = 1;
      this._testOnBorrow = false;
      this._testOnReturn = false;
      this._testWhileIdle = false;
      this._timeBetweenEvictionRunsMillis = -1L;
      this._numTestsPerEvictionRun = 3;
      this._minEvictableIdleTimeMillis = 1800000L;
      this._softMinEvictableIdleTimeMillis = 1800000L;
      this._lifo = true;
      this._factory = null;
      this._maxIdle = maxIdle;
      this._minIdle = minIdle;
      this._maxActive = maxActive;
      this._maxWait = maxWait;
      this._whenExhaustedAction = whenExhaustedAction;
      this._testOnBorrow = testOnBorrow;
      this._testOnReturn = testOnReturn;
      this._testWhileIdle = testWhileIdle;
      this._timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
      this._numTestsPerEvictionRun = numTestsPerEvictionRun;
      this._minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
      this._softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
      this._lifo = lifo;
      this._factory = factory;
   }

   public ObjectPool createPool() {
      return new GenericObjectPool(this._factory, this._maxActive, this._whenExhaustedAction, this._maxWait, this._maxIdle, this._minIdle, this._testOnBorrow, this._testOnReturn, this._timeBetweenEvictionRunsMillis, this._numTestsPerEvictionRun, this._minEvictableIdleTimeMillis, this._testWhileIdle, this._softMinEvictableIdleTimeMillis, this._lifo);
   }

   public int getMaxIdle() {
      return this._maxIdle;
   }

   public int getMinIdle() {
      return this._minIdle;
   }

   public int getMaxActive() {
      return this._maxActive;
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

   public long getSoftMinEvictableIdleTimeMillis() {
      return this._softMinEvictableIdleTimeMillis;
   }

   public boolean getLifo() {
      return this._lifo;
   }

   public PoolableObjectFactory getFactory() {
      return this._factory;
   }
}
