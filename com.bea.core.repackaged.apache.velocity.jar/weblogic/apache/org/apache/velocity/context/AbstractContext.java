package weblogic.apache.org.apache.velocity.context;

import java.io.Serializable;

public abstract class AbstractContext extends InternalContextBase implements Context, Serializable {
   private Context innerContext = null;

   public abstract Object internalGet(String var1);

   public abstract Object internalPut(String var1, Object var2);

   public abstract boolean internalContainsKey(Object var1);

   public abstract Object[] internalGetKeys();

   public abstract Object internalRemove(Object var1);

   public AbstractContext() {
   }

   public AbstractContext(Context inner) {
      this.innerContext = inner;
      if (this.innerContext instanceof InternalEventContext) {
         this.attachEventCartridge(((InternalEventContext)this.innerContext).getEventCartridge());
      }

   }

   public Object put(String key, Object value) {
      if (key == null) {
         return null;
      } else {
         return value == null ? null : this.internalPut(key, value);
      }
   }

   public Object get(String key) {
      if (key == null) {
         return null;
      } else {
         Object o = this.internalGet(key);
         if (o == null && this.innerContext != null) {
            o = this.innerContext.get(key);
         }

         return o;
      }
   }

   public boolean containsKey(Object key) {
      return key == null ? false : this.internalContainsKey(key);
   }

   public Object[] getKeys() {
      return this.internalGetKeys();
   }

   public Object remove(Object key) {
      return key == null ? null : this.internalRemove(key);
   }

   public Context getChainedContext() {
      return this.innerContext;
   }
}
