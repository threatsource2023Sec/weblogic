package org.jboss.weld.bootstrap.enablement;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.enterprise.inject.spi.Extension;
import org.jboss.weld.exceptions.UnsupportedOperationException;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.util.Preconditions;
import org.jboss.weld.util.collections.ListView;

abstract class EnablementListView extends ListView {
   private static final String ADD_OPERATION = "adds";
   private static final String REMOVE_OPERATION = "removes";
   private static final String SET_OPERATION = "sets";
   private static final String RETAIN_OPERATION = "retains";
   private static final int DEFAULT_PRIORITY = 2500;

   protected abstract ViewType getViewType();

   protected abstract Extension getExtension();

   public boolean add(Class element) {
      Preconditions.checkNotNull(element);
      List list = this.getDelegate();
      synchronized(list) {
         if (this.getExtension() != null) {
            BootstrapLogger.LOG.typeModifiedInAfterTypeDiscovery(this.getExtension(), element, "adds", this.getViewType());
         }

         return list.add(this.createSource(element, list.isEmpty() ? null : (Item)list.get(list.size() - 1), (Item)null));
      }
   }

   public Class set(int index, Class element) {
      Preconditions.checkNotNull(element);
      List list = this.getDelegate();
      synchronized(list) {
         if (index >= 0 && index < list.size()) {
            if (this.getExtension() != null) {
               BootstrapLogger.LOG.typeModifiedInAfterTypeDiscovery(this.getExtension(), element, "sets", this.getViewType());
            }

            return this.toView((Item)this.getDelegate().set(index, this.createSource(element, ((Item)list.get(index)).getPriority())));
         } else {
            throw new IndexOutOfBoundsException();
         }
      }
   }

   public void add(int index, Class element) {
      Preconditions.checkNotNull(element);
      List list = this.getDelegate();
      synchronized(list) {
         if (index >= 0 && index < list.size()) {
            Item previous = index > 0 ? (Item)list.get(index - 1) : null;
            Item next = index <= list.size() - 1 ? (Item)list.get(index) : null;
            if (this.getExtension() != null) {
               BootstrapLogger.LOG.typeModifiedInAfterTypeDiscovery(this.getExtension(), element, "adds", this.getViewType());
            }

            list.add(index, this.createSource(element, previous, next));
         } else {
            throw new IndexOutOfBoundsException();
         }
      }
   }

   public Class remove(int index) {
      Item removedItem = (Item)this.getDelegate().remove(index);
      if (this.getExtension() != null) {
         BootstrapLogger.LOG.typeModifiedInAfterTypeDiscovery(this.getExtension(), removedItem.getClass(), "removes", this.getViewType());
      }

      return this.toView(removedItem);
   }

   public boolean removeAll(Collection c) {
      if (this.getExtension() != null) {
         BootstrapLogger.LOG.typeModifiedInAfterTypeDiscovery(this.getExtension(), c, "removes", this.getViewType());
      }

      return super.removeAll(c);
   }

   public boolean remove(Object o) {
      if (this.getExtension() != null) {
         BootstrapLogger.LOG.typeModifiedInAfterTypeDiscovery(this.getExtension(), o, "removes", this.getViewType());
      }

      return this.getDelegate().remove(this.objectToItemIfNeeded(o));
   }

   public boolean retainAll(Collection c) {
      if (this.getExtension() != null) {
         BootstrapLogger.LOG.typeModifiedInAfterTypeDiscovery(this.getExtension(), c, "retains", this.getViewType());
      }

      return super.retainAll(c);
   }

   public void clear() {
      if (this.getExtension() != null) {
         BootstrapLogger.LOG.typeModifiedInAfterTypeDiscovery(this.getExtension(), "", "removes all classes", this.getViewType());
      }

      this.getDelegate().clear();
   }

   protected Class toView(Item source) {
      return source.getJavaClass();
   }

   protected Item createSource(Class view) {
      throw new UnsupportedOperationException();
   }

   private Item createSource(Class view, Item previous, Item next) {
      return this.createSource(view, this.getPriority(previous, next));
   }

   private Item createSource(Class view, int priority) {
      return new Item(view, priority);
   }

   private int getPriority(Item previous, Item next) {
      int priority;
      if (previous == null && next == null) {
         priority = 2500;
      } else if (previous != null && next != null) {
         int gap = next.getPriority() - previous.getPriority();
         if (gap == 0) {
            priority = next.getPriority();
         } else if (gap == 1) {
            Iterator var5 = this.getDelegate().iterator();

            while(var5.hasNext()) {
               Item item = (Item)var5.next();
               item.scalePriority();
            }

            priority = this.getPriority(previous, next);
         } else {
            priority = gap / 2 + previous.getPriority();
         }
      } else if (previous != null) {
         priority = previous.getPriority() + 10;
      } else {
         priority = next.getPriority() - 10;
      }

      return priority;
   }

   public ListIterator listIterator() {
      return new EnablementListViewIterator(this.getDelegate().listIterator());
   }

   public ListIterator listIterator(int index) {
      return new EnablementListViewIterator(this.getDelegate().listIterator(index));
   }

   public boolean contains(Object o) {
      return this.getDelegate().contains(this.objectToItemIfNeeded(o));
   }

   public int indexOf(Object o) {
      return this.getDelegate().indexOf(this.objectToItemIfNeeded(o));
   }

   private Object objectToItemIfNeeded(Object o) {
      return o instanceof Item ? o : this.createSource((Class)o, 0);
   }

   static enum ViewType {
      ALTERNATIVES("getAlternatives()"),
      INTERCEPTORS("getInterceptors()"),
      DECORATORS("getDecorators()");

      private final String name;

      private ViewType(String s) {
         this.name = s;
      }

      public String toString() {
         return this.name;
      }
   }

   class EnablementListViewIterator extends ListView.ListViewIterator {
      private Item lastItem;

      public EnablementListViewIterator(ListIterator delegate) {
         super(delegate);
      }

      public Class next() {
         this.lastItem = (Item)this.delegate.next();
         return EnablementListView.this.toView(this.lastItem);
      }

      public Class previous() {
         this.lastItem = (Item)this.delegate.previous();
         return EnablementListView.this.toView(this.lastItem);
      }

      public void set(Class clazz) {
         if (EnablementListView.this.getExtension() != null) {
            BootstrapLogger.LOG.typeModifiedInAfterTypeDiscovery(EnablementListView.this.getExtension(), clazz, "sets", EnablementListView.this.getViewType());
         }

         this.delegate.set(EnablementListView.this.createSource(clazz, this.lastItem.getPriority()));
      }

      public void add(Class clazz) {
         Item previous = this.hasPrevious() ? (Item)EnablementListView.this.getDelegate().get(this.previousIndex()) : null;
         Item next = this.hasNext() ? (Item)EnablementListView.this.getDelegate().get(this.nextIndex()) : null;
         if (EnablementListView.this.getExtension() != null) {
            BootstrapLogger.LOG.typeModifiedInAfterTypeDiscovery(EnablementListView.this.getExtension(), clazz, "adds", EnablementListView.this.getViewType());
         }

         this.delegate.add(EnablementListView.this.createSource(clazz, previous, next));
      }

      public void remove() {
         if (EnablementListView.this.getExtension() != null) {
            BootstrapLogger.LOG.typeModifiedInAfterTypeDiscovery(EnablementListView.this.getExtension(), ((Item)EnablementListView.this.getDelegate().get(this.delegate.nextIndex())).getJavaClass(), "removes", EnablementListView.this.getViewType());
         }

         this.delegate.remove();
      }
   }
}
