package javolution.realtime;

import javax.realtime.MemoryArea;
import javolution.lang.Text;

public abstract class RealtimeObject implements Realtime {
   private transient Pool _pool;
   private transient RealtimeObject _next;
   private transient RealtimeObject _previous;
   private transient int _preserved;

   protected RealtimeObject() {
   }

   public final String toString() {
      return this.toText().stringValue();
   }

   public Text toText() {
      return Text.valueOf((Object)this.getClass().getName()).concat(Text.valueOf('@')).concat(Text.valueOf((int)System.identityHashCode(this), 16));
   }

   public final Object export() {
      this.move(Realtime.ObjectSpace.OUTER);
      return this;
   }

   public final Object moveHeap() {
      this.move(Realtime.ObjectSpace.HEAP);
      return this;
   }

   public final Object preserve() {
      this.move(Realtime.ObjectSpace.HOLD);
      return this;
   }

   public final Object unpreserve() {
      this.move(Realtime.ObjectSpace.STACK);
      return this;
   }

   public boolean move(Realtime.ObjectSpace var1) {
      if (var1 == Realtime.ObjectSpace.OUTER) {
         if (this._pool != null && this._pool.isLocal()) {
            Pool var2 = (Pool)this._pool.outer;
            if (var2 == null) {
               return this.move(Realtime.ObjectSpace.HEAP);
            } else {
               this.detach();
               synchronized(var2) {
                  RealtimeObject var4 = (RealtimeObject)var2.next();
                  var4.detach();
                  var4.insertBefore(RealtimeObject.Pool.access$000(this._pool));
                  var4._pool = this._pool;
                  this.insertBefore(RealtimeObject.Pool.access$100(var2));
                  this._pool = var2;
                  return true;
               }
            }
         } else {
            return false;
         }
      } else if (var1 == Realtime.ObjectSpace.HEAP) {
         synchronized(this) {
            if (this._pool == null) {
               return false;
            } else {
               boolean var10000;
               synchronized(this._pool) {
                  this.detach();
                  RealtimeObject.Pool.access$210(this._pool);
                  this._next = null;
                  this._previous = null;
                  this._pool = null;
                  var10000 = true;
               }

               return var10000;
            }
         }
      } else if (var1 == Realtime.ObjectSpace.HOLD) {
         synchronized(this) {
            if (this._pool == null) {
               return false;
            } else if (this._preserved++ == 0) {
               synchronized(this._pool) {
                  this.detach();
                  this.insertBefore(RealtimeObject.Pool.access$300(this._pool));
               }

               return true;
            } else {
               return false;
            }
         }
      } else if (var1 == Realtime.ObjectSpace.STACK) {
         synchronized(this) {
            if (this._preserved != 0 && --this._preserved == 0) {
               if (this._pool != null) {
                  synchronized(this._pool) {
                     this.detach();
                     this.insertBefore(RealtimeObject.Pool.access$100(this._pool));
                  }
               }

               return true;
            } else {
               return false;
            }
         }
      } else {
         return true;
      }
   }

   protected void recycle() {
      if (this._pool != null && this._pool.isLocal()) {
         this._pool.recycle(this);
      }

   }

   final void insertBefore(RealtimeObject var1) {
      this._previous = var1._previous;
      this._next = var1;
      this._next._previous = this;
      this._previous._next = this;
   }

   final void detach() {
      this._next._previous = this._previous;
      this._previous._next = this._next;
   }

   static RealtimeObject access$500(RealtimeObject var0) {
      return var0._next;
   }

   static RealtimeObject access$502(RealtimeObject var0, RealtimeObject var1) {
      return var0._next = var1;
   }

   static RealtimeObject access$802(RealtimeObject var0, RealtimeObject var1) {
      return var0._previous = var1;
   }

   static Pool access$1002(RealtimeObject var0, Pool var1) {
      return var0._pool = var1;
   }

   static RealtimeObject access$800(RealtimeObject var0) {
      return var0._previous;
   }

   static Pool access$1000(RealtimeObject var0) {
      return var0._pool;
   }

   private static final class Bound extends RealtimeObject {
      private Bound() {
      }

      Bound(Object var1) {
         this();
      }
   }

   private static final class Pool extends ObjectPool {
      private final Factory _factory;
      private final MemoryArea _memoryArea;
      private int _size;
      private boolean _doCleanup;
      private final RealtimeObject _activeHead;
      private final RealtimeObject _activeTail;
      private final RealtimeObject _holdHead;
      private final RealtimeObject _holdTail;
      private RealtimeObject _next;

      private Pool(Factory var1) {
         this._doCleanup = true;
         this._factory = var1;
         this._memoryArea = MemoryArea.getMemoryArea(this);
         this._activeHead = new Bound();
         this._activeTail = new Bound();
         RealtimeObject.access$502(this._activeHead, this._activeTail);
         RealtimeObject.access$802(this._activeTail, this._activeHead);
         this._holdHead = new Bound();
         this._holdTail = new Bound();
         RealtimeObject.access$502(this._holdHead, this._holdTail);
         RealtimeObject.access$802(this._holdTail, this._holdHead);
         this._next = this._activeTail;
      }

      public int size() {
         return this._size;
      }

      public Object next() {
         RealtimeObject var1 = this._next;
         this._next = RealtimeObject.access$500(var1);
         return this._next != null ? var1 : this.allocate();
      }

      private RealtimeObject allocate() {
         this._next = this._activeTail;
         this._memoryArea.executeInArea(new Runnable() {
            public void run() {
               RealtimeObject var1 = (RealtimeObject)RealtimeObject.Pool.access$900(Pool.this).create();
               RealtimeObject.Pool.access$208(Pool.this);
               var1.insertBefore(RealtimeObject.Pool.access$000(Pool.this));
               RealtimeObject.access$1002(var1, Pool.this);
            }
         });
         return RealtimeObject.access$800(this._activeTail);
      }

      public void recycle(Object var1) {
         if (this._doCleanup) {
            try {
               this._factory.cleanup(var1);
            } catch (UnsupportedOperationException var3) {
               this._doCleanup = false;
            }
         }

         RealtimeObject var2 = (RealtimeObject)var1;
         if (RealtimeObject.access$1000(var2) == this) {
            var2.detach();
            var2.insertBefore(this._next);
            this._next = RealtimeObject.access$800(this._next);
         } else {
            throw new IllegalArgumentException("Object not in the pool");
         }
      }

      protected void recycleAll() {
         if (this._doCleanup) {
            try {
               for(RealtimeObject var1 = RealtimeObject.access$500(this._activeHead); var1 != this._next; var1 = RealtimeObject.access$500(var1)) {
                  this._factory.cleanup(var1);
               }
            } catch (UnsupportedOperationException var2) {
               this._doCleanup = false;
            }
         }

         this._next = RealtimeObject.access$500(this._activeHead);
      }

      protected void clearAll() {
         RealtimeObject.access$502(this._activeHead, this._activeTail);
         RealtimeObject.access$802(this._activeTail, this._activeHead);
      }

      static RealtimeObject access$000(Pool var0) {
         return var0._activeTail;
      }

      static RealtimeObject access$100(Pool var0) {
         return var0._next;
      }

      static int access$210(Pool var0) {
         return var0._size--;
      }

      static RealtimeObject access$300(Pool var0) {
         return var0._holdTail;
      }

      Pool(Factory var1, Object var2) {
         this(var1);
      }

      static RealtimeObject access$102(Pool var0, RealtimeObject var1) {
         return var0._next = var1;
      }

      static RealtimeObject access$600(Pool var0) {
         return var0.allocate();
      }

      static Factory access$900(Pool var0) {
         return var0._factory;
      }

      static int access$208(Pool var0) {
         return var0._size++;
      }
   }

   public abstract static class Factory extends ObjectFactory {
      private Pool _cachedPool = new Pool((Factory)null);

      protected Factory() {
      }

      public final RealtimeObject object() {
         Thread var1 = Thread.currentThread();
         Pool var2 = this._cachedPool;
         if (var2.user == var1) {
            RealtimeObject var5 = RealtimeObject.Pool.access$100(var2);
            return RealtimeObject.Pool.access$102(var2, RealtimeObject.access$500(var5)) != null ? var5 : RealtimeObject.Pool.access$600(var2);
         } else {
            PoolContext var3 = Context.poolContext(var1);
            if (var3 == null) {
               return (RealtimeObject)this.create();
            } else {
               var2 = (Pool)var3.getLocalPool(this._index);
               RealtimeObject var4 = (RealtimeObject)var2.next();
               this._cachedPool = var2;
               return var4;
            }
         }
      }

      protected ObjectPool newPool() {
         Pool var1 = new Pool(this);
         return var1;
      }

      public Object object() {
         return this.object();
      }
   }
}
