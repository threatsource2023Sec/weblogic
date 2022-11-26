package org.apache.openjpa.util;

import java.io.ObjectStreamException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public class java$util$LinkedList$proxy extends LinkedList implements ProxyCollection {
   private transient OpenJPAStateManager sm;
   private transient int field;
   private transient CollectionChangeTracker changeTracker;
   private transient Class elementType;

   public java$util$LinkedList$proxy() {
   }

   public java$util$LinkedList$proxy(Collection var1) {
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
      return new LinkedList((Collection)var1);
   }

   public Class getElementType() {
      return this.elementType;
   }

   public ProxyCollection newInstance(Class var1, Comparator var2, boolean var3, boolean var4) {
      java$util$LinkedList$proxy var5 = new java$util$LinkedList$proxy();
      var5.elementType = var1;
      if (var3) {
         var5.changeTracker = new CollectionChangeTrackerImpl(var5, true, true, var4);
      }

      return var5;
   }

   public boolean add(Object var1) {
      ProxyCollections.beforeAdd(this, var1);
      boolean var2 = super.add(var1);
      return ProxyCollections.afterAdd(this, var1, var2);
   }

   public void add(int var1, Object var2) {
      ProxyCollections.beforeAdd(this, var1, var2);
      super.add(var1, var2);
   }

   public void clear() {
      ProxyCollections.beforeClear(this);
      super.clear();
   }

   public boolean addAll(Collection var1) {
      return ProxyCollections.addAll(this, var1);
   }

   public boolean addAll(int var1, Collection var2) {
      return ProxyCollections.addAll(this, var1, var2);
   }

   public boolean remove(Object var1) {
      ProxyCollections.beforeRemove(this, var1);
      boolean var2 = super.remove(var1);
      return ProxyCollections.afterRemove(this, var1, var2);
   }

   public Object remove(int var1) {
      ProxyCollections.beforeRemove(this, var1);
      Object var2 = super.remove(var1);
      return ProxyCollections.afterRemove(this, var1, var2);
   }

   public Object remove() {
      ProxyCollections.beforeRemove(this);
      Object var1 = super.remove();
      return ProxyCollections.afterRemove(this, var1);
   }

   public Object set(int var1, Object var2) {
      ProxyCollections.beforeSet(this, var1, var2);
      Object var3 = super.set(var1, var2);
      return ProxyCollections.afterSet(this, var1, var2, var3);
   }

   public ListIterator listIterator(int var1) {
      ListIterator var2 = super.listIterator(var1);
      return ProxyCollections.afterListIterator(this, var1, var2);
   }

   public Object poll() {
      ProxyCollections.beforePoll(this);
      Object var1 = super.poll();
      return ProxyCollections.afterPoll(this, var1);
   }

   public void addFirst(Object var1) {
      ProxyCollections.beforeAddFirst(this, var1);
      super.addFirst(var1);
   }

   public void addLast(Object var1) {
      ProxyCollections.beforeAddLast(this, var1);
      super.addLast(var1);
      ProxyCollections.afterAddLast(this, var1);
   }

   public boolean offer(Object var1) {
      ProxyCollections.beforeOffer(this, var1);
      boolean var2 = super.offer(var1);
      return ProxyCollections.afterOffer(this, var1, var2);
   }

   public Object removeFirst() {
      ProxyCollections.beforeRemoveFirst(this);
      Object var1 = super.removeFirst();
      return ProxyCollections.afterRemoveFirst(this, var1);
   }

   public Object removeLast() {
      ProxyCollections.beforeRemoveLast(this);
      Object var1 = super.removeLast();
      return ProxyCollections.afterRemoveLast(this, var1);
   }

   public Iterator iterator() {
      Iterator var1 = super.iterator();
      return ProxyCollections.afterIterator(this, var1);
   }

   public ListIterator listIterator() {
      ListIterator var1 = super.listIterator();
      return ProxyCollections.afterListIterator(this, var1);
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
