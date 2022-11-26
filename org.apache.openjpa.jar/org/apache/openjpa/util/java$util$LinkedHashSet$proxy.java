package org.apache.openjpa.util;

import java.io.ObjectStreamException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public class java$util$LinkedHashSet$proxy extends LinkedHashSet implements ProxyCollection {
   private transient OpenJPAStateManager sm;
   private transient int field;
   private transient CollectionChangeTracker changeTracker;
   private transient Class elementType;

   public java$util$LinkedHashSet$proxy(int var1, float var2) {
      super(var1, var2);
   }

   public java$util$LinkedHashSet$proxy(int var1) {
      super(var1);
   }

   public java$util$LinkedHashSet$proxy() {
   }

   public java$util$LinkedHashSet$proxy(Collection var1) {
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
      return new LinkedHashSet((Collection)var1);
   }

   public Class getElementType() {
      return this.elementType;
   }

   public ProxyCollection newInstance(Class var1, Comparator var2, boolean var3, boolean var4) {
      java$util$LinkedHashSet$proxy var5 = new java$util$LinkedHashSet$proxy();
      var5.elementType = var1;
      if (var3) {
         var5.changeTracker = new CollectionChangeTrackerImpl(var5, false, true, var4);
      }

      return var5;
   }

   public boolean add(Object var1) {
      ProxyCollections.beforeAdd(this, var1);
      boolean var2 = super.add(var1);
      return ProxyCollections.afterAdd(this, var1, var2);
   }

   public void clear() {
      ProxyCollections.beforeClear(this);
      super.clear();
   }

   public Iterator iterator() {
      Iterator var1 = super.iterator();
      return ProxyCollections.afterIterator(this, var1);
   }

   public boolean remove(Object var1) {
      ProxyCollections.beforeRemove(this, var1);
      boolean var2 = super.remove(var1);
      return ProxyCollections.afterRemove(this, var1, var2);
   }

   public boolean removeAll(Collection var1) {
      return ProxyCollections.removeAll(this, var1);
   }

   public boolean addAll(Collection var1) {
      return ProxyCollections.addAll(this, var1);
   }

   public boolean retainAll(Collection var1) {
      return ProxyCollections.retainAll(this, var1);
   }

   protected Object writeReplace() throws ObjectStreamException {
      return Proxies.writeReplace(this, true);
   }
}
