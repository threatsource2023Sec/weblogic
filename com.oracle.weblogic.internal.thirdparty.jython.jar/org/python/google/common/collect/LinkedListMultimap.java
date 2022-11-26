package org.python.google.common.collect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractSequentialList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible(
   serializable = true,
   emulated = true
)
public class LinkedListMultimap extends AbstractMultimap implements ListMultimap, Serializable {
   private transient Node head;
   private transient Node tail;
   private transient Map keyToKeyList;
   private transient int size;
   private transient int modCount;
   @GwtIncompatible
   private static final long serialVersionUID = 0L;

   public static LinkedListMultimap create() {
      return new LinkedListMultimap();
   }

   public static LinkedListMultimap create(int expectedKeys) {
      return new LinkedListMultimap(expectedKeys);
   }

   public static LinkedListMultimap create(Multimap multimap) {
      return new LinkedListMultimap(multimap);
   }

   LinkedListMultimap() {
      this.keyToKeyList = Maps.newHashMap();
   }

   private LinkedListMultimap(int expectedKeys) {
      this.keyToKeyList = new HashMap(expectedKeys);
   }

   private LinkedListMultimap(Multimap multimap) {
      this(multimap.keySet().size());
      this.putAll(multimap);
   }

   @CanIgnoreReturnValue
   private Node addNode(@Nullable Object key, @Nullable Object value, @Nullable Node nextSibling) {
      Node node = new Node(key, value);
      if (this.head == null) {
         this.head = this.tail = node;
         this.keyToKeyList.put(key, new KeyList(node));
         ++this.modCount;
      } else {
         KeyList keyList;
         if (nextSibling == null) {
            this.tail.next = node;
            node.previous = this.tail;
            this.tail = node;
            keyList = (KeyList)this.keyToKeyList.get(key);
            if (keyList == null) {
               this.keyToKeyList.put(key, new KeyList(node));
               ++this.modCount;
            } else {
               ++keyList.count;
               Node keyTail = keyList.tail;
               keyTail.nextSibling = node;
               node.previousSibling = keyTail;
               keyList.tail = node;
            }
         } else {
            keyList = (KeyList)this.keyToKeyList.get(key);
            ++keyList.count;
            node.previous = nextSibling.previous;
            node.previousSibling = nextSibling.previousSibling;
            node.next = nextSibling;
            node.nextSibling = nextSibling;
            if (nextSibling.previousSibling == null) {
               ((KeyList)this.keyToKeyList.get(key)).head = node;
            } else {
               nextSibling.previousSibling.nextSibling = node;
            }

            if (nextSibling.previous == null) {
               this.head = node;
            } else {
               nextSibling.previous.next = node;
            }

            nextSibling.previous = node;
            nextSibling.previousSibling = node;
         }
      }

      ++this.size;
      return node;
   }

   private void removeNode(Node node) {
      if (node.previous != null) {
         node.previous.next = node.next;
      } else {
         this.head = node.next;
      }

      if (node.next != null) {
         node.next.previous = node.previous;
      } else {
         this.tail = node.previous;
      }

      KeyList keyList;
      if (node.previousSibling == null && node.nextSibling == null) {
         keyList = (KeyList)this.keyToKeyList.remove(node.key);
         keyList.count = 0;
         ++this.modCount;
      } else {
         keyList = (KeyList)this.keyToKeyList.get(node.key);
         --keyList.count;
         if (node.previousSibling == null) {
            keyList.head = node.nextSibling;
         } else {
            node.previousSibling.nextSibling = node.nextSibling;
         }

         if (node.nextSibling == null) {
            keyList.tail = node.previousSibling;
         } else {
            node.nextSibling.previousSibling = node.previousSibling;
         }
      }

      --this.size;
   }

   private void removeAllNodes(@Nullable Object key) {
      Iterators.clear(new ValueForKeyIterator(key));
   }

   private static void checkElement(@Nullable Object node) {
      if (node == null) {
         throw new NoSuchElementException();
      }
   }

   public int size() {
      return this.size;
   }

   public boolean isEmpty() {
      return this.head == null;
   }

   public boolean containsKey(@Nullable Object key) {
      return this.keyToKeyList.containsKey(key);
   }

   public boolean containsValue(@Nullable Object value) {
      return this.values().contains(value);
   }

   @CanIgnoreReturnValue
   public boolean put(@Nullable Object key, @Nullable Object value) {
      this.addNode(key, value, (Node)null);
      return true;
   }

   @CanIgnoreReturnValue
   public List replaceValues(@Nullable Object key, Iterable values) {
      List oldValues = this.getCopy(key);
      ListIterator keyValues = new ValueForKeyIterator(key);
      Iterator newValues = values.iterator();

      while(keyValues.hasNext() && newValues.hasNext()) {
         keyValues.next();
         keyValues.set(newValues.next());
      }

      while(keyValues.hasNext()) {
         keyValues.next();
         keyValues.remove();
      }

      while(newValues.hasNext()) {
         keyValues.add(newValues.next());
      }

      return oldValues;
   }

   private List getCopy(@Nullable Object key) {
      return Collections.unmodifiableList(Lists.newArrayList((Iterator)(new ValueForKeyIterator(key))));
   }

   @CanIgnoreReturnValue
   public List removeAll(@Nullable Object key) {
      List oldValues = this.getCopy(key);
      this.removeAllNodes(key);
      return oldValues;
   }

   public void clear() {
      this.head = null;
      this.tail = null;
      this.keyToKeyList.clear();
      this.size = 0;
      ++this.modCount;
   }

   public List get(@Nullable final Object key) {
      return new AbstractSequentialList() {
         public int size() {
            KeyList keyList = (KeyList)LinkedListMultimap.this.keyToKeyList.get(key);
            return keyList == null ? 0 : keyList.count;
         }

         public ListIterator listIterator(int index) {
            return LinkedListMultimap.this.new ValueForKeyIterator(key, index);
         }
      };
   }

   Set createKeySet() {
      class KeySetImpl extends Sets.ImprovedAbstractSet {
         public int size() {
            return LinkedListMultimap.this.keyToKeyList.size();
         }

         public Iterator iterator() {
            return LinkedListMultimap.this.new DistinctKeyIterator();
         }

         public boolean contains(Object key) {
            return LinkedListMultimap.this.containsKey(key);
         }

         public boolean remove(Object o) {
            return !LinkedListMultimap.this.removeAll(o).isEmpty();
         }
      }

      return new KeySetImpl();
   }

   public List values() {
      return (List)super.values();
   }

   List createValues() {
      class ValuesImpl extends AbstractSequentialList {
         public int size() {
            return LinkedListMultimap.this.size;
         }

         public ListIterator listIterator(int index) {
            final NodeIterator nodeItr = LinkedListMultimap.this.new NodeIterator(index);
            return new TransformedListIterator(nodeItr) {
               Object transform(Map.Entry entry) {
                  return entry.getValue();
               }

               public void set(Object value) {
                  nodeItr.setValue(value);
               }
            };
         }
      }

      return new ValuesImpl();
   }

   public List entries() {
      return (List)super.entries();
   }

   List createEntries() {
      class EntriesImpl extends AbstractSequentialList {
         public int size() {
            return LinkedListMultimap.this.size;
         }

         public ListIterator listIterator(int index) {
            return LinkedListMultimap.this.new NodeIterator(index);
         }
      }

      return new EntriesImpl();
   }

   Iterator entryIterator() {
      throw new AssertionError("should never be called");
   }

   Map createAsMap() {
      return new Multimaps.AsMap(this);
   }

   @GwtIncompatible
   private void writeObject(ObjectOutputStream stream) throws IOException {
      stream.defaultWriteObject();
      stream.writeInt(this.size());
      Iterator var2 = this.entries().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         stream.writeObject(entry.getKey());
         stream.writeObject(entry.getValue());
      }

   }

   @GwtIncompatible
   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
      stream.defaultReadObject();
      this.keyToKeyList = Maps.newLinkedHashMap();
      int size = stream.readInt();

      for(int i = 0; i < size; ++i) {
         Object key = stream.readObject();
         Object value = stream.readObject();
         this.put(key, value);
      }

   }

   private class ValueForKeyIterator implements ListIterator {
      final Object key;
      int nextIndex;
      Node next;
      Node current;
      Node previous;

      ValueForKeyIterator(@Nullable Object key) {
         this.key = key;
         KeyList keyList = (KeyList)LinkedListMultimap.this.keyToKeyList.get(key);
         this.next = keyList == null ? null : keyList.head;
      }

      public ValueForKeyIterator(@Nullable Object key, int index) {
         KeyList keyList = (KeyList)LinkedListMultimap.this.keyToKeyList.get(key);
         int size = keyList == null ? 0 : keyList.count;
         Preconditions.checkPositionIndex(index, size);
         if (index >= size / 2) {
            this.previous = keyList == null ? null : keyList.tail;
            this.nextIndex = size;

            while(index++ < size) {
               this.previous();
            }
         } else {
            this.next = keyList == null ? null : keyList.head;

            while(index-- > 0) {
               this.next();
            }
         }

         this.key = key;
         this.current = null;
      }

      public boolean hasNext() {
         return this.next != null;
      }

      @CanIgnoreReturnValue
      public Object next() {
         LinkedListMultimap.checkElement(this.next);
         this.previous = this.current = this.next;
         this.next = this.next.nextSibling;
         ++this.nextIndex;
         return this.current.value;
      }

      public boolean hasPrevious() {
         return this.previous != null;
      }

      @CanIgnoreReturnValue
      public Object previous() {
         LinkedListMultimap.checkElement(this.previous);
         this.next = this.current = this.previous;
         this.previous = this.previous.previousSibling;
         --this.nextIndex;
         return this.current.value;
      }

      public int nextIndex() {
         return this.nextIndex;
      }

      public int previousIndex() {
         return this.nextIndex - 1;
      }

      public void remove() {
         CollectPreconditions.checkRemove(this.current != null);
         if (this.current != this.next) {
            this.previous = this.current.previousSibling;
            --this.nextIndex;
         } else {
            this.next = this.current.nextSibling;
         }

         LinkedListMultimap.this.removeNode(this.current);
         this.current = null;
      }

      public void set(Object value) {
         Preconditions.checkState(this.current != null);
         this.current.value = value;
      }

      public void add(Object value) {
         this.previous = LinkedListMultimap.this.addNode(this.key, value, this.next);
         ++this.nextIndex;
         this.current = null;
      }
   }

   private class DistinctKeyIterator implements Iterator {
      final Set seenKeys;
      Node next;
      Node current;
      int expectedModCount;

      private DistinctKeyIterator() {
         this.seenKeys = Sets.newHashSetWithExpectedSize(LinkedListMultimap.this.keySet().size());
         this.next = LinkedListMultimap.this.head;
         this.expectedModCount = LinkedListMultimap.this.modCount;
      }

      private void checkForConcurrentModification() {
         if (LinkedListMultimap.this.modCount != this.expectedModCount) {
            throw new ConcurrentModificationException();
         }
      }

      public boolean hasNext() {
         this.checkForConcurrentModification();
         return this.next != null;
      }

      public Object next() {
         this.checkForConcurrentModification();
         LinkedListMultimap.checkElement(this.next);
         this.current = this.next;
         this.seenKeys.add(this.current.key);

         do {
            this.next = this.next.next;
         } while(this.next != null && !this.seenKeys.add(this.next.key));

         return this.current.key;
      }

      public void remove() {
         this.checkForConcurrentModification();
         CollectPreconditions.checkRemove(this.current != null);
         LinkedListMultimap.this.removeAllNodes(this.current.key);
         this.current = null;
         this.expectedModCount = LinkedListMultimap.this.modCount;
      }

      // $FF: synthetic method
      DistinctKeyIterator(Object x1) {
         this();
      }
   }

   private class NodeIterator implements ListIterator {
      int nextIndex;
      Node next;
      Node current;
      Node previous;
      int expectedModCount;

      NodeIterator(int index) {
         this.expectedModCount = LinkedListMultimap.this.modCount;
         int size = LinkedListMultimap.this.size();
         Preconditions.checkPositionIndex(index, size);
         if (index >= size / 2) {
            this.previous = LinkedListMultimap.this.tail;
            this.nextIndex = size;

            while(index++ < size) {
               this.previous();
            }
         } else {
            this.next = LinkedListMultimap.this.head;

            while(index-- > 0) {
               this.next();
            }
         }

         this.current = null;
      }

      private void checkForConcurrentModification() {
         if (LinkedListMultimap.this.modCount != this.expectedModCount) {
            throw new ConcurrentModificationException();
         }
      }

      public boolean hasNext() {
         this.checkForConcurrentModification();
         return this.next != null;
      }

      @CanIgnoreReturnValue
      public Node next() {
         this.checkForConcurrentModification();
         LinkedListMultimap.checkElement(this.next);
         this.previous = this.current = this.next;
         this.next = this.next.next;
         ++this.nextIndex;
         return this.current;
      }

      public void remove() {
         this.checkForConcurrentModification();
         CollectPreconditions.checkRemove(this.current != null);
         if (this.current != this.next) {
            this.previous = this.current.previous;
            --this.nextIndex;
         } else {
            this.next = this.current.next;
         }

         LinkedListMultimap.this.removeNode(this.current);
         this.current = null;
         this.expectedModCount = LinkedListMultimap.this.modCount;
      }

      public boolean hasPrevious() {
         this.checkForConcurrentModification();
         return this.previous != null;
      }

      @CanIgnoreReturnValue
      public Node previous() {
         this.checkForConcurrentModification();
         LinkedListMultimap.checkElement(this.previous);
         this.next = this.current = this.previous;
         this.previous = this.previous.previous;
         --this.nextIndex;
         return this.current;
      }

      public int nextIndex() {
         return this.nextIndex;
      }

      public int previousIndex() {
         return this.nextIndex - 1;
      }

      public void set(Map.Entry e) {
         throw new UnsupportedOperationException();
      }

      public void add(Map.Entry e) {
         throw new UnsupportedOperationException();
      }

      void setValue(Object value) {
         Preconditions.checkState(this.current != null);
         this.current.value = value;
      }
   }

   private static class KeyList {
      Node head;
      Node tail;
      int count;

      KeyList(Node firstNode) {
         this.head = firstNode;
         this.tail = firstNode;
         firstNode.previousSibling = null;
         firstNode.nextSibling = null;
         this.count = 1;
      }
   }

   private static final class Node extends AbstractMapEntry {
      final Object key;
      Object value;
      Node next;
      Node previous;
      Node nextSibling;
      Node previousSibling;

      Node(@Nullable Object key, @Nullable Object value) {
         this.key = key;
         this.value = value;
      }

      public Object getKey() {
         return this.key;
      }

      public Object getValue() {
         return this.value;
      }

      public Object setValue(@Nullable Object newValue) {
         Object result = this.value;
         this.value = newValue;
         return result;
      }
   }
}
