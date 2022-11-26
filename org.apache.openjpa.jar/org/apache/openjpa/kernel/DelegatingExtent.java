package org.apache.openjpa.kernel;

import java.util.Iterator;
import java.util.List;
import org.apache.openjpa.util.RuntimeExceptionTranslator;

public class DelegatingExtent implements Extent {
   private final Extent _extent;
   private final DelegatingExtent _del;
   private final RuntimeExceptionTranslator _trans;

   public DelegatingExtent(Extent extent) {
      this(extent, (RuntimeExceptionTranslator)null);
   }

   public DelegatingExtent(Extent extent, RuntimeExceptionTranslator trans) {
      this._extent = extent;
      if (extent instanceof DelegatingExtent) {
         this._del = (DelegatingExtent)extent;
      } else {
         this._del = null;
      }

      this._trans = trans;
   }

   public Extent getDelegate() {
      return this._extent;
   }

   public Extent getInnermostDelegate() {
      return this._del == null ? this._extent : this._del.getInnermostDelegate();
   }

   public int hashCode() {
      return this.getInnermostDelegate().hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         if (other instanceof DelegatingExtent) {
            other = ((DelegatingExtent)other).getInnermostDelegate();
         }

         return this.getInnermostDelegate().equals(other);
      }
   }

   protected RuntimeException translate(RuntimeException re) {
      return this._trans == null ? re : this._trans.translate(re);
   }

   public Class getElementType() {
      try {
         return this._extent.getElementType();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public boolean hasSubclasses() {
      try {
         return this._extent.hasSubclasses();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public Broker getBroker() {
      try {
         return this._extent.getBroker();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public FetchConfiguration getFetchConfiguration() {
      try {
         return this._extent.getFetchConfiguration();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public boolean getIgnoreChanges() {
      try {
         return this._extent.getIgnoreChanges();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setIgnoreChanges(boolean ignoreCache) {
      try {
         this._extent.setIgnoreChanges(ignoreCache);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public List list() {
      try {
         return this._extent.list();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public Iterator iterator() {
      try {
         return this._extent.iterator();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void closeAll() {
      try {
         this._extent.closeAll();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void lock() {
      try {
         this._extent.lock();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void unlock() {
      try {
         this._extent.unlock();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }
}
