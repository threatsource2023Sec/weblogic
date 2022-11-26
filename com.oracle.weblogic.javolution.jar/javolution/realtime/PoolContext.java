package javolution.realtime;

import javax.realtime.MemoryArea;
import javolution.lang.Reflection;
import javolution.util.FastMap;
import javolution.xml.XmlElement;
import javolution.xml.XmlException;
import javolution.xml.XmlFormat;

public class PoolContext extends Context {
   protected static final XmlFormat XML = new XmlFormat("javolution.realtime.PoolContext") {
      public void format(Object var1, XmlElement var2) {
         PoolContext var3 = (PoolContext)var1;
         FastMap var4 = FastMap.newInstance();
         int var5 = ObjectFactory.Count;

         while(true) {
            --var5;
            if (var5 < 0) {
               var2.add(var4);
               return;
            }

            int var6 = var3._pools[var5].size();
            if (var6 > 0) {
               var4.put(ObjectFactory.INSTANCES[var5].getClass(), new Integer(var6));
            }
         }
      }

      public Object parse(XmlElement var1) {
         PoolContext var2 = (PoolContext)var1.object();
         FastMap var3 = (FastMap)var1.getNext();
         FastMap.Entry var4 = var3.head();

         ObjectFactory var8;
         ObjectPool var9;
         for(FastMap.Entry var5 = var3.tail(); (var4 = var4.getNext()) != var5; var2._pools[var8._index] = var9) {
            Class var6 = (Class)var4.getKey();
            int var7 = (Integer)var4.getValue();
            var8 = ObjectFactory.getInstance(var6);
            if (var8 == null) {
               throw new XmlException("Factory for class " + var6 + " not found");
            }

            var9 = var8.newPool();

            while(var9.size() < var7) {
               var9.next();
            }

            var9.recycleAll();
         }

         return var2;
      }
   };
   private static final Class CLASS = Reflection.getClass("javolution.realtime.PoolContext");
   final ObjectPool[] _pools;
   private final ObjectPool[] _inUsePools;
   private int _inUsePoolsLength;

   public PoolContext() {
      this._pools = new ObjectPool[ObjectFactory.MAX];
      this._inUsePools = new ObjectPool[ObjectFactory.MAX];

      for(int var1 = this._pools.length; var1 > 0; this._pools[var1] = ObjectPool.NULL) {
         --var1;
      }

   }

   public static PoolContext current() {
      return Context.poolContext(Thread.currentThread());
   }

   public static void enter() {
      Context.enter(CLASS);
   }

   public static void exit() {
      Context.exit(CLASS);
   }

   public void clear() {
      super.clear();
      int var1 = ObjectFactory.Count;

      while(var1 > 0) {
         --var1;
         ObjectPool var2 = this._pools[var1];
         if (var2 != ObjectPool.NULL) {
            var2.clearAll();
         }
      }

      this._inUsePoolsLength = 0;
   }

   protected void enterAction() {
      this.inheritedPoolContext = this;
      PoolContext var1 = this.getOuter().inheritedPoolContext;
      if (var1 != null) {
         var1.setInUsePoolsLocal(false);
      }

   }

   protected void exitAction() {
      this.recyclePools();
      PoolContext var1 = this.getOuter().inheritedPoolContext;
      if (var1 != null) {
         var1.setInUsePoolsLocal(true);
      }

   }

   void setInUsePoolsLocal(boolean var1) {
      Thread var2 = var1 ? this.getOwner() : null;

      for(int var3 = this._inUsePoolsLength; var3 > 0; this._inUsePools[var3].user = var2) {
         --var3;
      }

   }

   ObjectPool getLocalPool(int var1) {
      ObjectPool var2 = this._pools[var1];
      return var2.user != null ? var2 : this.getLocalPool2(var1);
   }

   private ObjectPool getLocalPool2(int var1) {
      ObjectPool var2 = this.getPool(var1);
      var2.user = this.getOwner();
      return var2;
   }

   private ObjectPool getPool(final int var1) {
      ObjectPool var2 = this._pools[var1];
      if (var2 == ObjectPool.NULL) {
         MemoryArea.getMemoryArea(this).executeInArea(new Runnable() {
            public void run() {
               PoolContext.this._pools[var1] = ObjectFactory.INSTANCES[var1].newPool();
            }
         });
      }

      var2 = this._pools[var1];
      if (!var2.inUse) {
         var2.inUse = true;
         this._inUsePools[this._inUsePoolsLength++] = var2;
         PoolContext var3 = this.getOuter().inheritedPoolContext;
         if (var3 != null) {
            synchronized(var3) {
               var2.outer = var3.getPool(var1);
            }
         } else {
            var2.outer = null;
         }
      }

      return var2;
   }

   void recyclePools() {
      ObjectPool var2;
      for(int var1 = this._inUsePoolsLength; var1 > 0; var2.inUse = false) {
         --var1;
         var2 = this._inUsePools[var1];
         var2.recycleAll();
         var2.user = null;
      }

      this._inUsePoolsLength = 0;
   }
}
