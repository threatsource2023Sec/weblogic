package org.apache.openjpa.util;

import java.io.ObjectStreamException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Vector;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public class java$util$Vector$proxy extends Vector implements ProxyCollection {
   private transient OpenJPAStateManager sm;
   private transient int field;
   private transient CollectionChangeTracker changeTracker;
   private transient Class elementType;

   public java$util$Vector$proxy(int var1, int var2) {
      super(var1, var2);
   }

   public java$util$Vector$proxy(int var1) {
      super(var1);
   }

   public java$util$Vector$proxy() {
   }

   public java$util$Vector$proxy(Collection var1) {
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
      return new Vector((Collection)var1);
   }

   public Class getElementType() {
      return this.elementType;
   }

   public ProxyCollection newInstance(Class var1, Comparator var2, boolean var3, boolean var4) {
      java$util$Vector$proxy var5 = new java$util$Vector$proxy();
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

   public void addElement(Object var1) {
      ProxyCollections.beforeAddElement(this, var1);
      super.addElement(var1);
      ProxyCollections.afterAddElement(this, var1);
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

   public Object set(int var1, Object var2) {
      ProxyCollections.beforeSet(this, var1, var2);
      Object var3 = super.set(var1, var2);
      return ProxyCollections.afterSet(this, var1, var2, var3);
   }

   public void insertElementAt(Object var1, int var2) {
      ProxyCollections.beforeInsertElementAt(this, var1, var2);
      super.insertElementAt(var1, var2);
   }

   public boolean removeAll(Collection var1) {
      return ProxyCollections.removeAll(this, var1);
   }

   public void removeAllElements() {
      ProxyCollections.beforeRemoveAllElements(this);
      super.removeAllElements();
   }

   public boolean removeElement(Object var1) {
      ProxyCollections.beforeRemoveElement(this, var1);
      boolean var2 = super.removeElement(var1);
      return ProxyCollections.afterRemoveElement(this, var1, var2);
   }

   public void removeElementAt(int var1) {
      ProxyCollections.beforeRemoveElementAt(this, var1);
      super.removeElementAt(var1);
   }

   public boolean retainAll(Collection var1) {
      return ProxyCollections.retainAll(this, var1);
   }

   public void setElementAt(Object var1, int var2) {
      ProxyCollections.beforeSetElementAt(this, var1, var2);
      super.setElementAt(var1, var2);
   }

   public Iterator iterator() {
      Iterator var1 = super.iterator();
      return ProxyCollections.afterIterator(this, var1);
   }

   public ListIterator listIterator() {
      ListIterator var1 = super.listIterator();
      return ProxyCollections.afterListIterator(this, var1);
   }

   public ListIterator listIterator(int var1) {
      ListIterator var2 = super.listIterator(var1);
      return ProxyCollections.afterListIterator(this, var1, var2);
   }

   public void setSize(int var1) {
      Proxies.dirty(this, true);
      super.setSize(var1);
   }

   protected Object writeReplace() throws ObjectStreamException {
      return Proxies.writeReplace(this, true);
   }
}
