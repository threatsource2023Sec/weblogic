package org.apache.velocity;

import java.util.HashMap;
import java.util.Map;
import org.apache.velocity.context.AbstractContext;
import org.apache.velocity.context.Context;

public class VelocityContext extends AbstractContext implements Cloneable {
   private Map context;

   public VelocityContext() {
      this((Map)null, (Context)null);
   }

   public VelocityContext(Map context) {
      this(context, (Context)null);
   }

   public VelocityContext(Context innerContext) {
      this((Map)null, innerContext);
   }

   public VelocityContext(Map context, Context innerContext) {
      super(innerContext);
      this.context = null;
      this.context = (Map)(context == null ? new HashMap() : context);
   }

   public Object internalGet(String key) {
      return this.context.get(key);
   }

   public Object internalPut(String key, Object value) {
      return this.context.put(key, value);
   }

   public boolean internalContainsKey(Object key) {
      return this.context.containsKey(key);
   }

   public Object[] internalGetKeys() {
      return this.context.keySet().toArray();
   }

   public Object internalRemove(Object key) {
      return this.context.remove(key);
   }

   public Object clone() {
      VelocityContext clone = null;

      try {
         clone = (VelocityContext)super.clone();
         clone.context = new HashMap(this.context);
      } catch (CloneNotSupportedException var3) {
      }

      return clone;
   }
}
