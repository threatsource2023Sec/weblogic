package javolution.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import javax.realtime.MemoryArea;
import javolution.lang.PersistentReference;
import javolution.lang.Reusable;
import javolution.realtime.Realtime;
import javolution.realtime.RealtimeObject;

public class FastList extends FastCollection implements Reusable, List {
   private static final RealtimeObject.Factory FACTORY = new RealtimeObject.Factory() {
      public Object create() {
         return new FastList();
      }

      public void cleanup(Object var1) {
         ((FastList)var1).reset();
      }
   };
   private transient Node _head;
   private transient Node _tail;
   private transient int _size;

   public FastList() {
      this(4);
   }

   public FastList(String var1) {
      this(256);
      PersistentReference var2 = new PersistentReference(var1);
      FastList var3 = (FastList)var2.get();
      if (var3 != null) {
         this.addAll(var3);
      }

      var2.set(this);
   }

   public FastList(int var1) {
      this._head = new Node();
      this._tail = new Node();
      FastList.Node.access$102(this._head, this._tail);
      FastList.Node.access$202(this._tail, this._head);
      Node var2 = this._tail;

      Node var4;
      for(int var3 = 0; var3++ < var1; var2 = var4) {
         var4 = new Node();
         FastList.Node.access$202(var4, var2);
         FastList.Node.access$102(var2, var4);
      }

   }

   public FastList(Collection var1) {
      this(var1.size());
      this.addAll(var1);
   }

   public static FastList newInstance() {
      return (FastList)FACTORY.object();
   }

   public final boolean add(Object var1) {
      this.addLast(var1);
      return true;
   }

   public int hashCode() {
      FastComparator var1 = this.getValueComparator();
      int var2 = 1;
      Node var3 = this._head;

      for(Node var4 = this._tail; (var3 = FastList.Node.access$100(var3)) != var4; var2 = 31 * var2 + var1.hashCodeOf(FastList.Node.access$300(var3))) {
      }

      return var2;
   }

   public final Object get(int var1) {
      if (var1 >= 0 && var1 < this._size) {
         return FastList.Node.access$300(this.nodeAt(var1));
      } else {
         throw new IndexOutOfBoundsException("index: " + var1);
      }
   }

   public final Object set(int var1, Object var2) {
      if (var1 >= 0 && var1 < this._size) {
         Node var3 = this.nodeAt(var1);
         Object var4 = FastList.Node.access$300(var3);
         FastList.Node.access$302(var3, var2);
         return var4;
      } else {
         throw new IndexOutOfBoundsException("index: " + var1);
      }
   }

   public final void add(int var1, Object var2) {
      if (var1 >= 0 && var1 <= this._size) {
         this.addBefore(this.nodeAt(var1), var2);
      } else {
         throw new IndexOutOfBoundsException("index: " + var1);
      }
   }

   public final boolean addAll(int var1, Collection var2) {
      if (var1 >= 0 && var1 <= this._size) {
         Node var3 = this.nodeAt(var1);
         if (var2 instanceof FastList) {
            FastList var4 = (FastList)var2;
            Node var5 = var4._head;
            Node var6 = var4._tail;

            while((var5 = FastList.Node.access$100(var5)) != var6) {
               this.addBefore(var3, FastList.Node.access$300(var5));
            }
         } else {
            Iterator var7 = var2.iterator();

            while(var7.hasNext()) {
               this.addBefore(var3, var7.next());
            }
         }

         return var2.size() != 0;
      } else {
         throw new IndexOutOfBoundsException("index: " + var1);
      }
   }

   public final Object remove(int var1) {
      if (var1 >= 0 && var1 < this._size) {
         Node var2 = this.nodeAt(var1);
         Object var3 = FastList.Node.access$300(var2);
         this.delete(var2);
         return var3;
      } else {
         throw new IndexOutOfBoundsException("index: " + var1);
      }
   }

   public final int indexOf(Object var1) {
      FastComparator var2 = this.getValueComparator();
      int var3 = 0;
      Node var4 = this._head;

      for(Node var5 = this._tail; (var4 = FastList.Node.access$100(var4)) != var5; ++var3) {
         if (var2.areEqual(var1, FastList.Node.access$300(var4))) {
            return var3;
         }
      }

      return -1;
   }

   public final int lastIndexOf(Object var1) {
      FastComparator var2 = this.getValueComparator();
      int var3 = this.size() - 1;
      Node var4 = this._tail;

      for(Node var5 = this._head; (var4 = FastList.Node.access$200(var4)) != var5; --var3) {
         if (var2.areEqual(var1, FastList.Node.access$300(var4))) {
            return var3;
         }
      }

      return -1;
   }

   public final Iterator iterator() {
      FastListIterator var1 = (FastListIterator)FastList.FastListIterator.access$400().object();
      FastList.FastListIterator.access$502(var1, this);
      FastList.FastListIterator.access$602(var1, this._size);
      FastList.FastListIterator.access$702(var1, FastList.Node.access$100(this._head));
      FastList.FastListIterator.access$802(var1, 0);
      return var1;
   }

   public final ListIterator listIterator() {
      FastListIterator var1 = (FastListIterator)FastList.FastListIterator.access$400().object();
      FastList.FastListIterator.access$502(var1, this);
      FastList.FastListIterator.access$602(var1, this._size);
      FastList.FastListIterator.access$702(var1, FastList.Node.access$100(this._head));
      FastList.FastListIterator.access$802(var1, 0);
      return var1;
   }

   public final ListIterator listIterator(int var1) {
      if (var1 >= 0 && var1 <= this._size) {
         FastListIterator var2 = (FastListIterator)FastList.FastListIterator.access$400().object();
         FastList.FastListIterator.access$502(var2, this);
         FastList.FastListIterator.access$602(var2, this._size);
         FastList.FastListIterator.access$702(var2, this.nodeAt(var1));
         FastList.FastListIterator.access$802(var2, var1);
         return var2;
      } else {
         throw new IndexOutOfBoundsException("index: " + var1 + " for list of size: " + this._size);
      }
   }

   public final List subList(int var1, int var2) {
      if (var1 >= 0 && var2 <= this._size && var1 <= var2) {
         SubList var3 = (SubList)FastList.SubList.access$900().object();
         FastList.SubList.access$1002(var3, this);
         FastList.SubList.access$1102(var3, FastList.Node.access$200(this.nodeAt(var1)));
         FastList.SubList.access$1202(var3, this.nodeAt(var2));
         FastList.SubList.access$1302(var3, var2 - var1);
         return var3;
      } else {
         throw new IndexOutOfBoundsException("fromIndex: " + var1 + ", toIndex: " + var2 + " for list of size: " + this._size);
      }
   }

   public final Object getFirst() {
      Node var1 = FastList.Node.access$100(this._head);
      if (var1 == this._tail) {
         throw new NoSuchElementException();
      } else {
         return FastList.Node.access$300(var1);
      }
   }

   public final Object getLast() {
      Node var1 = FastList.Node.access$200(this._tail);
      if (var1 == this._head) {
         throw new NoSuchElementException();
      } else {
         return FastList.Node.access$300(var1);
      }
   }

   public final void addFirst(Object var1) {
      this.addBefore(FastList.Node.access$100(this._head), var1);
   }

   public void addLast(Object var1) {
      if (FastList.Node.access$100(this._tail) == null) {
         this.increaseCapacity();
      }

      FastList.Node.access$302(this._tail, var1);
      this._tail = FastList.Node.access$100(this._tail);
      ++this._size;
   }

   public final Object removeFirst() {
      Node var1 = FastList.Node.access$100(this._head);
      if (var1 == this._tail) {
         throw new NoSuchElementException();
      } else {
         Object var2 = FastList.Node.access$300(var1);
         this.delete(var1);
         return var2;
      }
   }

   public final Object removeLast() {
      if (this._size == 0) {
         throw new NoSuchElementException();
      } else {
         --this._size;
         Node var1 = FastList.Node.access$200(this._tail);
         Object var2 = FastList.Node.access$300(var1);
         this._tail = var1;
         FastList.Node.access$302(var1, (Object)null);
         return var2;
      }
   }

   public final void addBefore(Node var1, Object var2) {
      if (FastList.Node.access$100(this._tail) == null) {
         this.increaseCapacity();
      }

      ++this._size;
      Node var3 = FastList.Node.access$100(this._tail);
      Node var4 = FastList.Node.access$102(this._tail, FastList.Node.access$100(var3));
      if (var4 != null) {
         FastList.Node.access$202(var4, this._tail);
      }

      Node var5 = FastList.Node.access$200(var1);
      FastList.Node.access$102(var5, var3);
      FastList.Node.access$202(var1, var3);
      FastList.Node.access$102(var3, var1);
      FastList.Node.access$202(var3, var5);
      FastList.Node.access$302(var3, var2);
   }

   private final Node nodeAt(int var1) {
      int var2 = this._size;
      Node var3;
      int var4;
      if (var1 <= var2 >> 1) {
         var3 = this._head;

         for(var4 = var1; var4-- >= 0; var3 = FastList.Node.access$100(var3)) {
         }

         return var3;
      } else {
         var3 = this._tail;

         for(var4 = var2 - var1; var4-- > 0; var3 = FastList.Node.access$200(var3)) {
         }

         return var3;
      }
   }

   public final Node head() {
      return this._head;
   }

   public final Node tail() {
      return this._tail;
   }

   public final Object valueOf(FastCollection.Record var1) {
      return FastList.Node.access$300((Node)var1);
   }

   public final void delete(FastCollection.Record var1) {
      Node var2 = (Node)var1;
      --this._size;
      FastList.Node.access$302(var2, (Object)null);
      FastList.Node.access$102(FastList.Node.access$200(var2), FastList.Node.access$100(var2));
      FastList.Node.access$202(FastList.Node.access$100(var2), FastList.Node.access$200(var2));
      Node var3 = FastList.Node.access$100(this._tail);
      FastList.Node.access$202(var2, this._tail);
      FastList.Node.access$102(var2, var3);
      FastList.Node.access$102(this._tail, var2);
      if (var3 != null) {
         FastList.Node.access$202(var3, var2);
      }

   }

   public final int size() {
      return this._size;
   }

   public final void clear() {
      Node var1 = this._head;
      Node var2 = this._tail;

      while((var1 = FastList.Node.access$100(var1)) != var2) {
         FastList.Node.access$302(var1, (Object)null);
      }

      this._tail = FastList.Node.access$100(this._head);
      this._size = 0;
   }

   public List unmodifiable() {
      return (List)super.unmodifiable();
   }

   public void reset() {
      super.setValueComparator(FastComparator.DIRECT);
      this.clear();
   }

   public boolean move(Realtime.ObjectSpace var1) {
      if (super.move(var1)) {
         Node var2 = this._head;
         Node var3 = this._tail;

         while((var2 = FastList.Node.access$100(var2)) != var3) {
            if (FastList.Node.access$300(var2) instanceof Realtime) {
               ((Realtime)FastList.Node.access$300(var2)).move(var1);
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      this._head = new Node();
      this._tail = new Node();
      FastList.Node.access$102(this._head, this._tail);
      FastList.Node.access$202(this._tail, this._head);
      int var2 = var1.readInt();
      this.setValueComparator((FastComparator)var1.readObject());
      int var3 = var2;

      while(var3-- != 0) {
         this.addLast(var1.readObject());
      }

   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.writeInt(this._size);
      var1.writeObject(this.getValueComparator());
      Node var2 = this._head;
      int var3 = this._size;

      while(var3-- != 0) {
         var2 = FastList.Node.access$100(var2);
         var1.writeObject(FastList.Node.access$300(var2));
      }

   }

   private void increaseCapacity() {
      MemoryArea.getMemoryArea(this).executeInArea(new Runnable() {
         public void run() {
            Node var1 = new Node();
            FastList.Node.access$102(FastList.access$1400(FastList.this), var1);
            FastList.Node.access$202(var1, FastList.access$1400(FastList.this));
            Node var2 = new Node();
            FastList.Node.access$102(var1, var2);
            FastList.Node.access$202(var2, var1);
            Node var3 = new Node();
            FastList.Node.access$102(var2, var3);
            FastList.Node.access$202(var3, var2);
            Node var4 = new Node();
            FastList.Node.access$102(var3, var4);
            FastList.Node.access$202(var4, var3);
         }
      });
   }

   public Collection unmodifiable() {
      return this.unmodifiable();
   }

   public FastCollection.Record tail() {
      return this.tail();
   }

   public FastCollection.Record head() {
      return this.head();
   }

   static Node access$1400(FastList var0) {
      return var0._tail;
   }

   private static final class SubList extends FastCollection implements List, Serializable {
      private static final RealtimeObject.Factory FACTORY = new RealtimeObject.Factory() {
         protected Object create() {
            return new SubList();
         }

         protected void cleanup(Object var1) {
            SubList var2 = (SubList)var1;
            FastList.SubList.access$1002(var2, (FastList)null);
            FastList.SubList.access$1102(var2, (Node)null);
            FastList.SubList.access$1202(var2, (Node)null);
         }
      };
      private FastList _list;
      private Node _head;
      private Node _tail;
      private int _size;

      private SubList() {
      }

      public int size() {
         return this._size;
      }

      public FastCollection.Record head() {
         return this._head;
      }

      public FastCollection.Record tail() {
         return this._tail;
      }

      public Object valueOf(FastCollection.Record var1) {
         return this._list.valueOf(var1);
      }

      public void delete(FastCollection.Record var1) {
         this._list.delete(var1);
      }

      public boolean addAll(int var1, Collection var2) {
         if (var1 >= 0 && var1 <= this._size) {
            Node var3 = this.nodeAt(var1);
            Iterator var4 = var2.iterator();

            while(var4.hasNext()) {
               this._list.addBefore(var3, var4.next());
            }

            return var2.size() != 0;
         } else {
            throw new IndexOutOfBoundsException("index: " + var1);
         }
      }

      public Object get(int var1) {
         if (var1 >= 0 && var1 < this._size) {
            return FastList.Node.access$300(this.nodeAt(var1));
         } else {
            throw new IndexOutOfBoundsException("index: " + var1);
         }
      }

      public Object set(int var1, Object var2) {
         if (var1 >= 0 && var1 < this._size) {
            Node var3 = this.nodeAt(var1);
            Object var4 = FastList.Node.access$300(var3);
            FastList.Node.access$302(var3, var2);
            return var4;
         } else {
            throw new IndexOutOfBoundsException("index: " + var1);
         }
      }

      public void add(int var1, Object var2) {
         if (var1 >= 0 && var1 <= this._size) {
            this._list.addBefore(this.nodeAt(var1), var2);
         } else {
            throw new IndexOutOfBoundsException("index: " + var1);
         }
      }

      public Object remove(int var1) {
         if (var1 >= 0 && var1 < this._size) {
            Node var2 = this.nodeAt(var1);
            Object var3 = FastList.Node.access$300(var2);
            this._list.delete(var2);
            return var3;
         } else {
            throw new IndexOutOfBoundsException("index: " + var1);
         }
      }

      public int indexOf(Object var1) {
         FastComparator var2 = this._list.getValueComparator();
         int var3 = 0;
         Node var4 = this._head;

         for(Node var5 = this._tail; (var4 = FastList.Node.access$100(var4)) != var5; ++var3) {
            if (var2.areEqual(var1, FastList.Node.access$300(var4))) {
               return var3;
            }
         }

         return -1;
      }

      public int lastIndexOf(Object var1) {
         FastComparator var2 = this.getValueComparator();
         int var3 = this.size() - 1;
         Node var4 = this._tail;

         for(Node var5 = this._head; (var4 = FastList.Node.access$200(var4)) != var5; --var3) {
            if (var2.areEqual(var1, FastList.Node.access$300(var4))) {
               return var3;
            }
         }

         return -1;
      }

      public ListIterator listIterator() {
         return this.listIterator(0);
      }

      public ListIterator listIterator(int var1) {
         if (var1 >= 0 && var1 <= this._size) {
            FastListIterator var2 = (FastListIterator)FastList.FastListIterator.access$400().object();
            FastList.FastListIterator.access$502(var2, this._list);
            FastList.FastListIterator.access$602(var2, this._size);
            FastList.FastListIterator.access$702(var2, this.nodeAt(var1));
            FastList.FastListIterator.access$802(var2, var1);
            return var2;
         } else {
            throw new IndexOutOfBoundsException("index: " + var1 + " for list of size: " + this._size);
         }
      }

      public List subList(int var1, int var2) {
         if (var1 >= 0 && var2 <= this._size && var1 <= var2) {
            SubList var3 = (SubList)FACTORY.object();
            var3._list = this._list;
            var3._head = FastList.Node.access$200(this.nodeAt(var1));
            var3._tail = this.nodeAt(var2);
            var3._size = var2 - var1;
            return var3;
         } else {
            throw new IndexOutOfBoundsException("fromIndex: " + var1 + ", toIndex: " + var2 + " for list of size: " + this._size);
         }
      }

      private final Node nodeAt(int var1) {
         Node var2;
         int var3;
         if (var1 <= this._size >> 1) {
            var2 = this._head;

            for(var3 = var1; var3-- >= 0; var2 = FastList.Node.access$100(var2)) {
            }

            return var2;
         } else {
            var2 = this._tail;

            for(var3 = this._size - var1; var3-- > 0; var2 = FastList.Node.access$200(var2)) {
            }

            return var2;
         }
      }

      static RealtimeObject.Factory access$900() {
         return FACTORY;
      }

      static FastList access$1002(SubList var0, FastList var1) {
         return var0._list = var1;
      }

      static Node access$1102(SubList var0, Node var1) {
         return var0._head = var1;
      }

      static Node access$1202(SubList var0, Node var1) {
         return var0._tail = var1;
      }

      static int access$1302(SubList var0, int var1) {
         return var0._size = var1;
      }

      SubList(Object var1) {
         this();
      }
   }

   private static final class FastListIterator extends RealtimeObject implements ListIterator {
      private static final RealtimeObject.Factory FACTORY = new RealtimeObject.Factory() {
         protected Object create() {
            return new FastListIterator();
         }

         protected void cleanup(Object var1) {
            FastListIterator var2 = (FastListIterator)var1;
            FastList.FastListIterator.access$502(var2, (FastList)null);
            FastList.FastListIterator.access$1602(var2, (Node)null);
            FastList.FastListIterator.access$702(var2, (Node)null);
         }
      };
      private FastList _list;
      private Node _nextNode;
      private Node _currentNode;
      private int _length;
      private int _nextIndex;

      private FastListIterator() {
      }

      public boolean hasNext() {
         return this._nextIndex != this._length;
      }

      public Object next() {
         if (this._nextIndex == this._length) {
            throw new NoSuchElementException();
         } else {
            ++this._nextIndex;
            this._currentNode = this._nextNode;
            this._nextNode = FastList.Node.access$100(this._nextNode);
            return FastList.Node.access$300(this._currentNode);
         }
      }

      public int nextIndex() {
         return this._nextIndex;
      }

      public boolean hasPrevious() {
         return this._nextIndex != 0;
      }

      public Object previous() {
         if (this._nextIndex == 0) {
            throw new NoSuchElementException();
         } else {
            --this._nextIndex;
            this._currentNode = this._nextNode = FastList.Node.access$200(this._nextNode);
            return FastList.Node.access$300(this._currentNode);
         }
      }

      public int previousIndex() {
         return this._nextIndex - 1;
      }

      public void add(Object var1) {
         this._list.addBefore(this._nextNode, var1);
         this._currentNode = null;
         ++this._length;
         ++this._nextIndex;
      }

      public void set(Object var1) {
         if (this._currentNode != null) {
            FastList.Node.access$302(this._currentNode, var1);
         } else {
            throw new IllegalStateException();
         }
      }

      public void remove() {
         if (this._currentNode != null) {
            if (this._nextNode == this._currentNode) {
               this._nextNode = FastList.Node.access$100(this._nextNode);
            } else {
               --this._nextIndex;
            }

            this._list.delete(this._currentNode);
            this._currentNode = null;
            --this._length;
         } else {
            throw new IllegalStateException();
         }
      }

      static RealtimeObject.Factory access$400() {
         return FACTORY;
      }

      static FastList access$502(FastListIterator var0, FastList var1) {
         return var0._list = var1;
      }

      static int access$602(FastListIterator var0, int var1) {
         return var0._length = var1;
      }

      static Node access$702(FastListIterator var0, Node var1) {
         return var0._nextNode = var1;
      }

      static int access$802(FastListIterator var0, int var1) {
         return var0._nextIndex = var1;
      }

      FastListIterator(Object var1) {
         this();
      }

      static Node access$1602(FastListIterator var0, Node var1) {
         return var0._currentNode = var1;
      }
   }

   public static final class Node implements FastCollection.Record, Serializable {
      private Node _next;
      private Node _previous;
      private Object _value;

      private Node() {
      }

      public final Object getValue() {
         return this._value;
      }

      public final Node getNext() {
         return this._next;
      }

      public final Node getPrevious() {
         return this._previous;
      }

      public FastCollection.Record getNext() {
         return this.getNext();
      }

      public FastCollection.Record getPrevious() {
         return this.getPrevious();
      }

      Node(Object var1) {
         this();
      }

      static Node access$102(Node var0, Node var1) {
         return var0._next = var1;
      }

      static Node access$202(Node var0, Node var1) {
         return var0._previous = var1;
      }

      static Node access$100(Node var0) {
         return var0._next;
      }

      static Object access$300(Node var0) {
         return var0._value;
      }

      static Object access$302(Node var0, Object var1) {
         return var0._value = var1;
      }

      static Node access$200(Node var0) {
         return var0._previous;
      }
   }
}
