package org.apache.openjpa.util;

import java.io.ObjectStreamException;
import java.util.Collection;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public class java$util$IdentityHashMap$proxy extends IdentityHashMap implements ProxyMap {
   private transient OpenJPAStateManager sm;
   private transient int field;
   private transient MapChangeTracker changeTracker;
   private transient Class keyType;
   private transient Class valueType;

   public java$util$IdentityHashMap$proxy() {
   }

   public java$util$IdentityHashMap$proxy(int var1) {
      super(var1);
   }

   public java$util$IdentityHashMap$proxy(Map var1) {
      super(var1);
   }

   public void setOwner(OpenJPAStateManager var1, int var2) {
      this.sm = var1;
      this.field = var2;
   }

   public OpenJPAStateManager getOwner() {
      return this.sm;
   }

   public int getOwnerField() {
      return this.field;
   }

   public Object clone() {
      Proxy var1 = (Proxy)super.clone();
      var1.setOwner((OpenJPAStateManager)null, 0);
      return var1;
   }

   public ChangeTracker getChangeTracker() {
      return this.changeTracker;
   }

   public Object copy(Object var1) {
      return new IdentityHashMap((Map)var1);
   }

   public Class getKeyType() {
      return this.keyType;
   }

   public Class getValueType() {
      return this.valueType;
   }

   public ProxyMap newInstance(Class var1, Class var2, Comparator var3, boolean var4, boolean var5) {
      java$util$IdentityHashMap$proxy var6 = new java$util$IdentityHashMap$proxy();
      var6.keyType = var1;
      var6.valueType = var2;
      if (var4) {
         var6.changeTracker = new MapChangeTrackerImpl(var6, var5);
      }

      return var6;
   }

   public Object put(Object var1, Object var2) {
      boolean var3 = ProxyMaps.beforePut(this, var1, var2);
      Object var4 = super.put(var1, var2);
      return ProxyMaps.afterPut(this, var1, var2, var4, var3);
   }

   public void clear() {
      ProxyMaps.beforeClear(this);
      super.clear();
   }

   public Set entrySet() {
      Set var1 = super.entrySet();
      return ProxyMaps.afterEntrySet(this, var1);
   }

   public void putAll(Map var1) {
      ProxyMaps.putAll(this, var1);
   }

   public Collection values() {
      return ProxyMaps.values(this);
   }

   public Object remove(Object var1) {
      boolean var2 = ProxyMaps.beforeRemove(this, var1);
      Object var3 = super.remove(var1);
      return ProxyMaps.afterRemove(this, var1, var3, var2);
   }

   public Set keySet() {
      return ProxyMaps.keySet(this);
   }

   protected Object writeReplace() throws ObjectStreamException {
      return Proxies.writeReplace(this, true);
   }
}
