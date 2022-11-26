package javolution.realtime;

import javax.realtime.MemoryArea;
import javolution.Configuration;
import javolution.JavolutionError;
import javolution.lang.ClassInitializer;

public abstract class ObjectFactory {
   static final int MAX = Configuration.factories();
   static ObjectFactory[] INSTANCES;
   static volatile int Count;
   final int _index = add(this);
   private final HeapPool _heapPool = new HeapPool();

   protected ObjectFactory() {
   }

   private static synchronized int add(ObjectFactory var0) {
      int var1 = Count;
      if (var1 >= MAX) {
         throw new UnsupportedOperationException("Maximum number of factories (system property \"javolution.factories\", value " + MAX + ") has been reached");
      } else {
         Class var2 = var0.getClass();

         for(int var3 = 0; var3 < var1; ++var3) {
            if (var2 == INSTANCES[var3].getClass()) {
               throw new UnsupportedOperationException(var2 + "  cannot have more than one instance");
            }
         }

         INSTANCES[var1] = var0;
         return Count++;
      }
   }

   protected abstract Object create();

   public Object object() {
      PoolContext var1 = Context.poolContext(Thread.currentThread());
      return var1 == null ? this.create() : var1.getLocalPool(this._index).next();
   }

   public final ObjectPool currentPool() {
      PoolContext var1 = Context.poolContext(Thread.currentThread());
      return (ObjectPool)(var1 != null ? var1.getLocalPool(this._index) : this._heapPool);
   }

   public final ObjectPool heapPool() {
      return this._heapPool;
   }

   protected void cleanup(Object var1) {
      throw new UnsupportedOperationException();
   }

   protected ObjectPool newPool() {
      return new LocalPool();
   }

   static ObjectFactory getInstance(Class var0) {
      String var1 = var0.getName();
      int var2 = var1.lastIndexOf(36);
      if (var2 > 0) {
         ClassInitializer.initialize(var1.substring(0, var2));
      }

      ClassInitializer.initialize(var0);

      for(int var3 = 0; var3 < Count; ++var3) {
         if (INSTANCES[var3].getClass().equals(var0)) {
            return INSTANCES[var3];
         }
      }

      return null;
   }

   static {
      INSTANCES = new ObjectFactory[MAX];
   }

   static final class Node {
      Object _object;
      Node _next;
   }

   private final class LocalPool extends ObjectPool {
      private final MemoryArea _memoryArea;
      private boolean _doCleanup;
      private int _size;
      private Node _usedNodes;
      private Node _availNodes;
      private Node _usedNodesTail;
      private Node _node;

      private LocalPool() {
         this._doCleanup = true;
         this._memoryArea = MemoryArea.getMemoryArea(this);
      }

      public int size() {
         return this._size;
      }

      public Object next() {
         if (this._availNodes != null) {
            this._node = this._availNodes;
            this._availNodes = this._node._next;
         } else {
            this._memoryArea.executeInArea(new Runnable() {
               public void run() {
                  ObjectFactory.LocalPool.access$202(LocalPool.this, new Node());
                  ObjectFactory.LocalPool.access$200(LocalPool.this)._object = ObjectFactory.this.create();
               }
            });
            ++this._size;
         }

         if (this._usedNodes == null) {
            this._usedNodesTail = this._node;
         }

         this._node._next = this._usedNodes;
         this._usedNodes = this._node;
         return this._node._object;
      }

      public void recycle(Object var1) {
         if (this._doCleanup) {
            try {
               ObjectFactory.this.cleanup(var1);
            } catch (UnsupportedOperationException var4) {
               this._doCleanup = false;
            }
         }

         Node var2;
         if (this._usedNodes._object == var1) {
            var2 = this._usedNodes;
            if (var2 == this._usedNodesTail) {
               this._usedNodesTail = null;
               if (var2._next != null) {
                  throw new JavolutionError("Pool Corrupted");
               }
            }

            this._usedNodes = var2._next;
            var2._next = this._availNodes;
            this._availNodes = var2;
         } else {
            var2 = this._usedNodes;

            for(Node var3 = var2._next; var3 != null; var3 = var3._next) {
               if (var3._object == var1) {
                  if (var3 == this._usedNodesTail) {
                     this._usedNodesTail = var2;
                  }

                  var2._next = var3._next;
                  var3._next = this._availNodes;
                  this._availNodes = var3;
                  return;
               }

               var2 = var3;
            }

            throw new IllegalArgumentException("Object not in the pool");
         }
      }

      protected void recycleAll() {
         if (this._doCleanup) {
            try {
               for(Node var1 = this._usedNodes; var1 != null; var1 = var1._next) {
                  ObjectFactory.this.cleanup(var1._object);
               }
            } catch (UnsupportedOperationException var2) {
               this._doCleanup = false;
            }
         }

         if (this._usedNodes != null) {
            this._usedNodesTail._next = this._availNodes;
            this._availNodes = this._usedNodes;
            this._usedNodes = null;
            this._usedNodesTail = null;
         }

      }

      protected void clearAll() {
         this._availNodes = null;
         this._usedNodes = null;
         this._usedNodesTail = null;
      }

      LocalPool(Object var2) {
         this();
      }

      static Node access$202(LocalPool var0, Node var1) {
         return var0._node = var1;
      }

      static Node access$200(LocalPool var0) {
         return var0._node;
      }
   }

   private final class HeapPool extends ObjectPool {
      private HeapPool() {
      }

      public int size() {
         return 0;
      }

      public Object next() {
         return ObjectFactory.this.create();
      }

      public void recycle(Object var1) {
      }

      protected void recycleAll() {
      }

      protected void clearAll() {
      }

      HeapPool(Object var2) {
         this();
      }
   }
}
