package org.apache.openjpa.util;

import java.io.ObjectStreamException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public class java$util$TreeSet$proxy extends TreeSet implements ProxyCollection {
   private transient OpenJPAStateManager sm;
   private transient int field;
   private transient CollectionChangeTracker changeTracker;
   private transient Class elementType;

   public java$util$TreeSet$proxy() {
   }

   public java$util$TreeSet$proxy(Comparator var1) {
      super(var1);
   }

   public java$util$TreeSet$proxy(Collection var1) {
      super(var1);
   }

   public java$util$TreeSet$proxy(SortedSet var1) {
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
      return new TreeSet((SortedSet)var1);
   }

   public Class getElementType() {
      return this.elementType;
   }

   public ProxyCollection newInstance(Class var1, Comparator var2, boolean var3, boolean var4) {
      java$util$TreeSet$proxy var5 = new java$util$TreeSet$proxy(var2);
      var5.elementType = var1;
      if (var3) {
         var5.changeTracker = new CollectionChangeTrackerImpl(var5, false, false, var4);
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

   public boolean addAll(Collection var1) {
      return ProxyCollections.addAll(this, var1);
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

   public boolean retainAll(Collection var1) {
      return ProxyCollections.retainAll(this, var1);
   }

   protected Object writeReplace() throws ObjectStreamException {
      return Proxies.writeReplace(this, true);
   }
}
