package weblogic.ejb.container.cache;

import javax.ejb.EntityBean;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.manager.TTLManager;

public class QueryCacheElement {
   protected TTLManager beanManager;
   protected Object value;
   protected boolean isInvalidatable = true;
   protected boolean isIncludable = true;
   protected boolean isPrimaryKey = true;
   private int hashcode;

   public QueryCacheElement(Object v) {
      this.value = v == null ? weblogic.ejb.container.interfaces.QueryCache.NULL_VALUE : v;
      this.isInvalidatable = false;
      this.isPrimaryKey = false;
   }

   public QueryCacheElement(Object v, TTLManager rom) {
      this.value = v;
      this.beanManager = rom;
      this.hashcode = this.value.hashCode() ^ this.beanManager.hashCode();
   }

   protected QueryCacheElement(CacheKey ckey) {
      this.value = ckey.getPrimaryKey();
      this.beanManager = (TTLManager)ckey.getCallback();
      this.hashcode = this.value.hashCode() ^ this.beanManager.hashCode();
   }

   public Object getReturnValue(Object txOrThread, boolean isLocal) throws InternalException {
      if (this.isPrimaryKey) {
         EntityBean b = this.beanManager.enrollIfNotTimedOut(txOrThread, new CacheKey(this.value, this.beanManager));
         return b != null ? this.beanManager.finderGetEoFromBeanOrPk(b, (Object)null, isLocal) : null;
      } else {
         return this.value;
      }
   }

   public boolean enroll(Object txOrThread) throws InternalException {
      if (this.isPrimaryKey) {
         return this.beanManager.enrollIfNotTimedOut(txOrThread, new CacheKey(this.value, this.beanManager)) != null;
      } else {
         return true;
      }
   }

   public void setInvalidatable(boolean b) {
      this.isInvalidatable = b;
   }

   public void setIncludable(boolean b) {
      this.isIncludable = b;
   }

   public boolean isInvalidatable() {
      return this.isInvalidatable;
   }

   public boolean isIncludable() {
      return this.isIncludable;
   }

   public int hashCode() {
      return this.hashcode;
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof QueryCacheElement)) {
         return false;
      } else {
         QueryCacheElement other = (QueryCacheElement)o;
         return this.hashcode == other.hashcode && this.eq(this.value, other.value) && this.eq(this.beanManager, other.beanManager);
      }
   }

   private boolean eq(Object a, Object b) {
      return a == b || a.equals(b);
   }

   public String toString() {
      return this.beanManager != null ? this.value.toString() + "#" + this.beanManager.toString() : this.value.toString();
   }
}
