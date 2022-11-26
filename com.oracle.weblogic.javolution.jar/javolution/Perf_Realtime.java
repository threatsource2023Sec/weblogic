package javolution;

import javolution.realtime.ObjectFactory;
import javolution.realtime.PoolContext;
import javolution.realtime.RealtimeObject;

final class Perf_Realtime extends Javolution implements Runnable {
   volatile Object[] _objects = new Object[1000];
   private static final ObjectFactory CHAR128_FACTORY = new ObjectFactory() {
      public Object create() {
         return new char[128];
      }
   };
   private static final ObjectFactory CHAR256_FACTORY = new ObjectFactory() {
      public Object create() {
         return new char[256];
      }
   };

   public void run() {
      println("//////////////////////////////////");
      println("// Package: javolution.realtime //");
      println("//////////////////////////////////");
      println("");
      println("-- Heap versus Stack Allocation (Pool-Context) --");
      print("Small standard object heap creation: ");
      startTime();

      int var1;
      int var2;
      for(var1 = 0; var1 < 10000; ++var1) {
         for(var2 = 0; var2 < this._objects.length; this._objects[var2++] = new SmallObjectStandard()) {
         }
      }

      println(endTime(10000 * this._objects.length));
      print("Small real-time object stack creation: ");
      startTime();

      for(var1 = 0; var1 < 10000; ++var1) {
         PoolContext.enter();

         for(var2 = 0; var2 < this._objects.length; this._objects[var2++] = Perf_Realtime.SmallObjectRealtime.FACTORY.object()) {
         }

         PoolContext.exit();
      }

      println(endTime(10000 * this._objects.length));
      print("char[128] heap creation: ");
      startTime();

      for(var1 = 0; var1 < 1000; ++var1) {
         for(var2 = 0; var2 < this._objects.length; this._objects[var2++] = new char[128]) {
         }
      }

      println(endTime(1000 * this._objects.length));
      print("char[128] stack creation: ");
      startTime();

      for(var1 = 0; var1 < 1000; ++var1) {
         PoolContext.enter();

         for(var2 = 0; var2 < this._objects.length; this._objects[var2++] = CHAR128_FACTORY.object()) {
         }

         PoolContext.exit();
      }

      println(endTime(1000 * this._objects.length));
      print("char[256] heap creation: ");
      startTime();

      for(var1 = 0; var1 < 1000; ++var1) {
         for(var2 = 0; var2 < this._objects.length; this._objects[var2++] = new char[256]) {
         }
      }

      println(endTime(1000 * this._objects.length));
      print("char[256] stack creation: ");
      startTime();

      for(var1 = 0; var1 < 1000; ++var1) {
         PoolContext.enter();

         for(var2 = 0; var2 < this._objects.length; this._objects[var2++] = CHAR256_FACTORY.object()) {
         }

         PoolContext.exit();
      }

      println(endTime(1000 * this._objects.length));
      println("");
   }

   private static final class SmallObjectRealtime extends RealtimeObject {
      long longValue;
      int intValue;
      SmallObjectRealtime refValue;
      static final RealtimeObject.Factory FACTORY = new RealtimeObject.Factory() {
         public Object create() {
            return new SmallObjectRealtime();
         }
      };

      private SmallObjectRealtime() {
      }

      SmallObjectRealtime(Object var1) {
         this();
      }
   }

   private static final class SmallObjectStandard {
      long longValue;
      int intValue;
      SmallObjectStandard refValue;

      private SmallObjectStandard() {
      }

      SmallObjectStandard(Object var1) {
         this();
      }
   }
}
