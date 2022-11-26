package org.apache.openjpa.util;

import java.io.ObjectStreamException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.SortedSet;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public class java$util$PriorityQueue$proxy extends PriorityQueue implements ProxyCollection {
   private transient OpenJPAStateManager sm;
   private transient int field;
   private transient CollectionChangeTracker changeTracker;
   private transient Class elementType;

   public java$util$PriorityQueue$proxy() {
   }

   public java$util$PriorityQueue$proxy(int var1) {
      super(var1);
   }

   public java$util$PriorityQueue$proxy(int var1, Comparator var2) {
      super(var1, var2);
   }

   public java$util$PriorityQueue$proxy(Collection var1) {
      super(var1);
   }

   public java$util$PriorityQueue$proxy(PriorityQueue var1) {
      super(var1);
   }

   public java$util$PriorityQueue$proxy(SortedSet var1) {
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
      return new PriorityQueue((PriorityQueue)var1);
   }

   public Class getElementType() {
      return this.elementType;
   }

   public ProxyCollection newInstance(Class var1, Comparator var2, boolean var3, boolean var4) {
      java$util$PriorityQueue$proxy var5 = new java$util$PriorityQueue$proxy();
      var5.elementType = var1;
      if (var3) {
         var5.changeTracker = new CollectionChangeTrackerImpl(var5, true, false, var4);
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

   public Object poll() {
      ProxyCollections.beforePoll(this);
      Object var1 = super.poll();
      return ProxyCollections.afterPoll(this, var1);
   }

   public boolean offer(Object var1) {
      ProxyCollections.beforeOffer(this, var1);
      boolean var2 = super.offer(var1);
      return ProxyCollections.afterOffer(this, var1, var2);
   }

   public boolean addAll(Collection var1) {
      return ProxyCollections.addAll(this, var1);
   }

   public Object remove() {
      ProxyCollections.beforeRemove(this);
      Object var1 = super.remove();
      return ProxyCollections.afterRemove(this, var1);
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
