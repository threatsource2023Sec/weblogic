package weblogic.apache.org.apache.log;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Hashtable;

public final class ContextMap implements Serializable {
   private static final ThreadLocal c_context = new InheritableThreadLocal();
   private final ContextMap m_parent;
   private Hashtable m_map;
   private transient boolean m_readOnly;

   public static final ContextMap getCurrentContext() {
      return getCurrentContext(true);
   }

   public static final ContextMap getCurrentContext(boolean autocreate) {
      ContextMap context = (ContextMap)c_context.get();
      if (null == context && autocreate) {
         context = new ContextMap();
         c_context.set(context);
      }

      return context;
   }

   public static final void bind(ContextMap context) {
      c_context.set(context);
   }

   public ContextMap() {
      this((ContextMap)null);
   }

   public ContextMap(ContextMap parent) {
      this.m_map = new Hashtable();
      this.m_parent = parent;
   }

   public void makeReadOnly() {
      this.m_readOnly = true;
   }

   public boolean isReadOnly() {
      return this.m_readOnly;
   }

   public void clear() {
      this.checkReadable();
      this.m_map.clear();
   }

   public Object get(String key, Object defaultObject) {
      Object object = this.get(key);
      return null != object ? object : defaultObject;
   }

   public Object get(String key) {
      Object result = this.m_map.get(key);
      return null == result && null != this.m_parent ? this.m_parent.get(key) : result;
   }

   public void set(String key, Object value) {
      this.checkReadable();
      if (value == null) {
         this.m_map.remove(key);
      } else {
         this.m_map.put(key, value);
      }

   }

   public int getSize() {
      return this.m_map.size();
   }

   private Object readResolve() throws ObjectStreamException {
      this.makeReadOnly();
      return this;
   }

   private void checkReadable() {
      if (this.isReadOnly()) {
         throw new IllegalStateException("ContextMap is read only and can not be modified");
      }
   }
}
