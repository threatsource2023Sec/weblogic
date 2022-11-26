package javolution;

import edu.emory.mathcs.backport.java.util.concurrent.ConcurrentHashMap;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.RandomAccess;
import javolution.util.FastCollection;
import javolution.util.FastList;
import javolution.util.FastMap;
import javolution.util.FastSet;
import javolution.util.FastTable;

final class Perf_Util extends Javolution implements Runnable {
   private static final int MAX_COLLECTION_SIZE = 10000;
   private final Object[] _objects = new Object[10000];

   public void run() throws JavolutionError {
      println("//////////////////////////////");
      println("// Package: javolution.util //");
      println("//////////////////////////////");
      println("");
      println("(new)      : The collection is created (using the new keyword), populated, then discarded (throw-away collections).");
      println("(recycled) : The collection is cleared, populated, then reused (static collections or throw-away collections in PoolContext).");
      println("");

      for(int var1 = 0; var1 < 10000; ++var1) {
         this._objects[var1] = new Object();
      }

      println("-- FastTable versus ArrayList -- ");
      setOutputStream((PrintStream)null);
      this.benchmarkFastTable();
      setOutputStream(System.out);
      this.benchmarkFastTable();
      setOutputStream((PrintStream)null);
      this.benchmarkArrayList();
      setOutputStream(System.out);
      this.benchmarkArrayList();
      println("-- FastList versus LinkedList -- ");
      setOutputStream((PrintStream)null);
      this.benchmarkFastList();
      setOutputStream(System.out);
      this.benchmarkFastList();
      setOutputStream((PrintStream)null);
      this.benchmarkLinkedList();
      setOutputStream(System.out);
      this.benchmarkLinkedList();
      println("");
      println("-- FastMap versus HashMap  --");
      setOutputStream((PrintStream)null);
      this.benchmarkFastMap();
      setOutputStream(System.out);
      this.benchmarkFastMap();
      setOutputStream((PrintStream)null);
      this.benchmarkHashMap();
      setOutputStream(System.out);
      this.benchmarkHashMap();
      setOutputStream((PrintStream)null);
      this.benchmarkLinkedHashMap();
      setOutputStream(System.out);
      this.benchmarkLinkedHashMap();
      println("");
      println("-- FastMap.setShared(true) versus ConcurrentHashMap  --");
      setOutputStream((PrintStream)null);
      this.benchmarkSharedFastMap();
      setOutputStream(System.out);
      this.benchmarkSharedFastMap();
      setOutputStream((PrintStream)null);
      this.benchmarkConcurrentHashMap();
      setOutputStream(System.out);
      this.benchmarkConcurrentHashMap();
      println("");
      println("-- FastSet versus HashSet --");
      setOutputStream((PrintStream)null);
      this.benchmarkFastSet();
      setOutputStream(System.out);
      this.benchmarkFastSet();
      setOutputStream((PrintStream)null);
      this.benchmarkHashSet();
      setOutputStream(System.out);
      this.benchmarkHashSet();
      setOutputStream((PrintStream)null);
      this.benchmarkLinkedHashSet();
      setOutputStream(System.out);
      this.benchmarkLinkedHashSet();
      println("");
   }

   private void benchmarkFastTable() {
      FastTable var1 = new FastTable();
      println(var1.getClass());

      for(int var2 = 10; var2 <= 10000; var2 *= 10) {
         int var3 = 1000000 / var2;
         print("    Size: " + var2);
         print(", add (new): ");
         startTime();

         int var4;
         int var5;
         for(var4 = 0; var4 < var3; ++var4) {
            var1 = new FastTable();
            var5 = 0;

            while(var5 < var2) {
               var1.add(this._objects[var5++]);
            }
         }

         print(endTime(var3 * var2));
         print(", add (recycled): ");
         startTime();

         for(var4 = 0; var4 < var3; ++var4) {
            var1.clear();
            var5 = 0;

            while(var5 < var2) {
               var1.add(this._objects[var5++]);
            }
         }

         print(endTime(var3 * var2));
         print(", iteration (iterator): ");
         startTime();

         for(var4 = 0; var4 < var3 * 10; ++var4) {
            Iterator var6 = var1.iterator();

            while(var6.hasNext()) {
               if (var6.next() == var1) {
                  throw new Error();
               }
            }
         }

         print(endTime(var3 * var2 * 10));
         if (var1 instanceof RandomAccess) {
            print(", get(int): ");
            startTime();
            var4 = 0;

            label87:
            while(true) {
               if (var4 >= var3 * 10) {
                  print(endTime(var3 * var2 * 10));
                  break;
               }

               var5 = var1.size();

               do {
                  --var5;
                  if (var5 <= 0) {
                     ++var4;
                     continue label87;
                  }
               } while(var1.get(var5) != var1);

               throw new Error();
            }
         }

         println("");
      }

      println("");
   }

   private void benchmarkFastList() {
      FastList var1 = new FastList();
      println(var1.getClass());

      for(int var2 = 10; var2 <= 10000; var2 *= 10) {
         int var3 = 1000000 / var2;
         print("    Size: " + var2);
         print(", add (new): ");
         startTime();

         int var4;
         int var5;
         for(var4 = 0; var4 < var3; ++var4) {
            var1 = new FastList();
            var5 = 0;

            while(var5 < var2) {
               var1.add(this._objects[var5++]);
            }
         }

         print(endTime(var3 * var2));
         print(", add (recycled): ");
         startTime();

         for(var4 = 0; var4 < var3; ++var4) {
            var1.clear();
            var5 = 0;

            while(var5 < var2) {
               var1.add(this._objects[var5++]);
            }
         }

         print(endTime(var3 * var2));
         print(", iteration (iterator): ");
         startTime();

         for(var4 = 0; var4 < var3 * 10; ++var4) {
            Iterator var9 = var1.iterator();

            while(var9.hasNext()) {
               if (var9.next() == var1) {
                  throw new Error();
               }
            }
         }

         print(endTime(var3 * var2 * 10));
         print(", iteration (node): ");
         FastList var8 = var1;
         startTime();

         for(var5 = 0; var5 < var3 * 10; ++var5) {
            FastList.Node var6 = var8.head();
            FastList.Node var7 = var8.tail();

            while((var6 = var6.getNext()) != var7) {
               if (var6.getValue() == var1) {
                  throw new Error();
               }
            }
         }

         print(endTime(var3 * var2 * 10));
         if (var1 instanceof RandomAccess) {
            print(", get(int): ");
            startTime();
            var5 = 0;

            label107:
            while(true) {
               if (var5 >= var3 * 10) {
                  print(endTime(var3 * var2 * 10));
                  break;
               }

               int var10 = var1.size();

               do {
                  --var10;
                  if (var10 <= 0) {
                     ++var5;
                     continue label107;
                  }
               } while(var1.get(var10) != var1);

               throw new Error();
            }
         }

         println("");
      }

      println("");
   }

   private void benchmarkArrayList() {
      ArrayList var1 = new ArrayList();
      if (var1.getClass().getName().equals("java.util.ArrayList")) {
         println(var1.getClass());

         for(int var2 = 10; var2 <= 10000; var2 *= 10) {
            int var3 = 1000000 / var2;
            print("    Size: " + var2);
            print(", add (new): ");
            startTime();

            int var4;
            int var5;
            for(var4 = 0; var4 < var3; ++var4) {
               var1 = new ArrayList();
               var5 = 0;

               while(var5 < var2) {
                  var1.add(this._objects[var5++]);
               }
            }

            print(endTime(var3 * var2));
            print(", add (recycled): ");
            startTime();

            for(var4 = 0; var4 < var3; ++var4) {
               var1.clear();
               var5 = 0;

               while(var5 < var2) {
                  var1.add(this._objects[var5++]);
               }
            }

            print(endTime(var3 * var2));
            print(", iteration (iterator): ");
            startTime();

            for(var4 = 0; var4 < var3 * 10; ++var4) {
               Iterator var6 = var1.iterator();

               while(var6.hasNext()) {
                  if (var6.next() == var1) {
                     throw new Error();
                  }
               }
            }

            print(endTime(var3 * var2 * 10));
            if (var1 instanceof RandomAccess) {
               print(", get(int): ");
               startTime();
               var4 = 0;

               label91:
               while(true) {
                  if (var4 >= var3 * 10) {
                     print(endTime(var3 * var2 * 10));
                     break;
                  }

                  var5 = var1.size();

                  do {
                     --var5;
                     if (var5 <= 0) {
                        ++var4;
                        continue label91;
                     }
                  } while(var1.get(var5) != var1);

                  throw new Error();
               }
            }

            println("");
         }

         println("");
      }
   }

   private void benchmarkLinkedList() {
      LinkedList var1 = new LinkedList();
      if (var1.getClass().getName().equals("java.util.LinkedList")) {
         println(var1.getClass());

         for(int var2 = 10; var2 <= 10000; var2 *= 10) {
            int var3 = 1000000 / var2;
            print("    Size: " + var2);
            print(", add (new): ");
            startTime();

            int var4;
            int var5;
            for(var4 = 0; var4 < var3; ++var4) {
               var1 = new LinkedList();
               var5 = 0;

               while(var5 < var2) {
                  var1.add(this._objects[var5++]);
               }
            }

            print(endTime(var3 * var2));
            print(", add (recycled): ");
            startTime();

            for(var4 = 0; var4 < var3; ++var4) {
               var1.clear();
               var5 = 0;

               while(var5 < var2) {
                  var1.add(this._objects[var5++]);
               }
            }

            print(endTime(var3 * var2));
            print(", iteration (iterator): ");
            startTime();

            for(var4 = 0; var4 < var3 * 10; ++var4) {
               Iterator var6 = var1.iterator();

               while(var6.hasNext()) {
                  if (var6.next() == var1) {
                     throw new Error();
                  }
               }
            }

            print(endTime(var3 * var2 * 10));
            if (var1 instanceof RandomAccess) {
               print(", get(int): ");
               startTime();
               var4 = 0;

               label91:
               while(true) {
                  if (var4 >= var3 * 10) {
                     print(endTime(var3 * var2 * 10));
                     break;
                  }

                  var5 = var1.size();

                  do {
                     --var5;
                     if (var5 <= 0) {
                        ++var4;
                        continue label91;
                     }
                  } while(var1.get(var5) != var1);

                  throw new Error();
               }
            }

            println("");
         }

         println("");
      }
   }

   private void benchmarkFastMap() {
      FastMap var1 = new FastMap();
      println(var1.getClass());

      for(int var2 = 10; var2 <= 10000; var2 *= 10) {
         int var3 = 1000000 / var2;
         print("    Size: " + var2);
         print(", put (new): ");
         startTime();

         int var4;
         int var5;
         for(var4 = 0; var4 < var3; ++var4) {
            var1 = new FastMap();
            var5 = 0;

            while(var5 < var2) {
               var1.put(this._objects[var5++], "");
            }
         }

         print(endTime(var3 * var2));
         print(", put (recycled): ");
         startTime();

         for(var4 = 0; var4 < var3; ++var4) {
            var1.clear();
            var5 = 0;

            while(var5 < var2) {
               var1.put(this._objects[var5++], "");
            }
         }

         print(endTime(var3 * var2));
         print(", get: ");
         startTime();

         for(var4 = 0; var4 < var3; ++var4) {
            var5 = 0;

            while(var5 < var2) {
               if (var1.get(this._objects[var5++]) != "") {
                  throw new Error();
               }
            }
         }

         print(endTime(var3 * var2));
         print(", iteration (iterator): ");
         startTime();

         for(var4 = 0; var4 < var3 * 10; ++var4) {
            Iterator var7 = var1.entrySet().iterator();

            while(var7.hasNext()) {
               if (var7.next() == var1) {
                  throw new Error();
               }
            }
         }

         print(endTime(var3 * var2 * 10));
         print(", iteration (entry): ");
         startTime();

         for(var4 = 0; var4 < var3 * 10; ++var4) {
            FastMap.Entry var8 = var1.head();
            FastMap.Entry var6 = var1.tail();

            while((var8 = var8.getNext()) != var6) {
               if (var8.getValue() == var1) {
                  throw new Error();
               }
            }
         }

         print(endTime(var3 * var2 * 10));
         println("");
      }

      println("");
   }

   private void benchmarkHashMap() {
      HashMap var1 = new HashMap();
      if (var1.getClass().getName().equals("java.util.HashMap")) {
         println(var1.getClass());

         for(int var2 = 10; var2 <= 10000; var2 *= 10) {
            int var3 = 1000000 / var2;
            print("    Size: " + var2);
            print(", put (new): ");
            startTime();

            int var4;
            int var5;
            for(var4 = 0; var4 < var3; ++var4) {
               var1 = new HashMap();
               var5 = 0;

               while(var5 < var2) {
                  var1.put(this._objects[var5++], "");
               }
            }

            print(endTime(var3 * var2));
            print(", put (recycled): ");
            startTime();

            for(var4 = 0; var4 < var3; ++var4) {
               var1.clear();
               var5 = 0;

               while(var5 < var2) {
                  var1.put(this._objects[var5++], "");
               }
            }

            print(endTime(var3 * var2));
            print(", get: ");
            startTime();

            for(var4 = 0; var4 < var3; ++var4) {
               var5 = 0;

               while(var5 < var2) {
                  if (var1.get(this._objects[var5++]) != "") {
                     throw new Error();
                  }
               }
            }

            print(endTime(var3 * var2));
            print(", iteration (iterator): ");
            startTime();

            for(var4 = 0; var4 < var3 * 10; ++var4) {
               Iterator var6 = var1.entrySet().iterator();

               while(var6.hasNext()) {
                  if (var6.next() == var1) {
                     throw new Error();
                  }
               }
            }

            print(endTime(var3 * var2 * 10));
            println("");
         }

         println("");
      }
   }

   private void benchmarkSharedFastMap() {
      FastMap var1 = (new FastMap()).setShared(true);
      println("Shared FastMap");

      for(int var2 = 10; var2 <= 10000; var2 *= 10) {
         int var3 = 1000000 / var2;
         print("    Size: " + var2);
         print(", put (new): ");
         startTime();

         int var4;
         int var5;
         for(var4 = 0; var4 < var3; ++var4) {
            var1 = (new FastMap()).setShared(true);
            var5 = 0;

            while(var5 < var2) {
               var1.put(this._objects[var5++], "");
            }
         }

         print(endTime(var3 * var2));
         print(", put (recycled): ");
         startTime();

         for(var4 = 0; var4 < var3; ++var4) {
            var1.clear();
            var5 = 0;

            while(var5 < var2) {
               var1.put(this._objects[var5++], "");
            }
         }

         print(endTime(var3 * var2));
         print(", get: ");
         startTime();

         for(var4 = 0; var4 < var3; ++var4) {
            var5 = 0;

            while(var5 < var2) {
               if (var1.get(this._objects[var5++]) != "") {
                  throw new Error();
               }
            }
         }

         print(endTime(var3 * var2));
         print(", iteration (iterator): ");
         startTime();

         for(var4 = 0; var4 < var3 * 10; ++var4) {
            Iterator var7 = var1.entrySet().iterator();

            while(var7.hasNext()) {
               if (var7.next() == var1) {
                  throw new Error();
               }
            }
         }

         print(endTime(var3 * var2 * 10));
         print(", iteration (entry): ");
         startTime();

         for(var4 = 0; var4 < var3 * 10; ++var4) {
            FastMap.Entry var8 = var1.head();
            FastMap.Entry var6 = var1.tail();

            while((var8 = var8.getNext()) != var6) {
               if (var8.getValue() == var1) {
                  throw new Error();
               }
            }
         }

         print(endTime(var3 * var2 * 10));
         println("");
      }

      println("");
   }

   private void benchmarkConcurrentHashMap() {
      ConcurrentHashMap var1 = new ConcurrentHashMap();
      if (var1.getClass().getName().equals("java.util.concurrent.ConcurrentHashMap")) {
         println(var1.getClass());

         for(int var2 = 10; var2 <= 10000; var2 *= 10) {
            int var3 = 1000000 / var2;
            print("    Size: " + var2);
            print(", put (new): ");
            startTime();

            int var4;
            int var5;
            for(var4 = 0; var4 < var3; ++var4) {
               var1 = new ConcurrentHashMap();
               var5 = 0;

               while(var5 < var2) {
                  var1.put(this._objects[var5++], "");
               }
            }

            print(endTime(var3 * var2));
            print(", put (recycled): ");
            startTime();

            for(var4 = 0; var4 < var3; ++var4) {
               var1.clear();
               var5 = 0;

               while(var5 < var2) {
                  var1.put(this._objects[var5++], "");
               }
            }

            print(endTime(var3 * var2));
            print(", get: ");
            startTime();

            for(var4 = 0; var4 < var3; ++var4) {
               var5 = 0;

               while(var5 < var2) {
                  if (var1.get(this._objects[var5++]) != "") {
                     throw new Error();
                  }
               }
            }

            print(endTime(var3 * var2));
            print(", iteration (iterator): ");
            startTime();

            for(var4 = 0; var4 < var3 * 10; ++var4) {
               Iterator var6 = var1.entrySet().iterator();

               while(var6.hasNext()) {
                  if (var6.next() == var1) {
                     throw new Error();
                  }
               }
            }

            print(endTime(var3 * var2 * 10));
            println("");
         }

         println("");
      }
   }

   private void benchmarkLinkedHashMap() {
      LinkedHashMap var1 = new LinkedHashMap();
      if (var1.getClass().getName().equals("java.util.LinkedHashMap")) {
         println(var1.getClass());

         for(int var2 = 10; var2 <= 10000; var2 *= 10) {
            int var3 = 1000000 / var2;
            print("    Size: " + var2);
            print(", put (new): ");
            startTime();

            int var4;
            int var5;
            for(var4 = 0; var4 < var3; ++var4) {
               var1 = new LinkedHashMap();
               var5 = 0;

               while(var5 < var2) {
                  var1.put(this._objects[var5++], "");
               }
            }

            print(endTime(var3 * var2));
            print(", put (recycled): ");
            startTime();

            for(var4 = 0; var4 < var3; ++var4) {
               var1.clear();
               var5 = 0;

               while(var5 < var2) {
                  var1.put(this._objects[var5++], "");
               }
            }

            print(endTime(var3 * var2));
            print(", get: ");
            startTime();

            for(var4 = 0; var4 < var3; ++var4) {
               var5 = 0;

               while(var5 < var2) {
                  if (var1.get(this._objects[var5++]) != "") {
                     throw new Error();
                  }
               }
            }

            print(endTime(var3 * var2));
            print(", iteration (iterator): ");
            startTime();

            for(var4 = 0; var4 < var3 * 10; ++var4) {
               Iterator var6 = var1.entrySet().iterator();

               while(var6.hasNext()) {
                  if (var6.next() == var1) {
                     throw new Error();
                  }
               }
            }

            print(endTime(var3 * var2 * 10));
            println("");
         }

         println("");
      }
   }

   private void benchmarkFastSet() {
      FastSet var1 = new FastSet();
      println(var1.getClass());

      for(int var2 = 10; var2 <= 10000; var2 *= 10) {
         int var3 = 1000000 / var2;
         print("    Size: " + var2);
         print(", add (new): ");
         startTime();

         int var4;
         int var5;
         for(var4 = 0; var4 < var3; ++var4) {
            var1 = new FastSet();
            var5 = 0;

            while(var5 < var2) {
               var1.add(this._objects[var5++]);
            }
         }

         print(endTime(var3 * var2));
         print(", add (recycled): ");
         startTime();

         for(var4 = 0; var4 < var3; ++var4) {
            var1.clear();
            var5 = 0;

            while(var5 < var2) {
               var1.add(this._objects[var5++]);
            }
         }

         print(endTime(var3 * var2));
         print(", contain: ");
         startTime();

         for(var4 = 0; var4 < var3; ++var4) {
            var5 = 0;

            while(var5 < var2) {
               if (!var1.contains(this._objects[var5++])) {
                  throw new Error();
               }
            }
         }

         print(endTime(var3 * var2));
         print(", iteration (iterator): ");
         startTime();

         for(var4 = 0; var4 < var3 * 10; ++var4) {
            Iterator var7 = var1.iterator();

            while(var7.hasNext()) {
               if (var7.next() == var1) {
                  throw new Error();
               }
            }
         }

         print(endTime(var3 * var2 * 10));
         print(", iteration (record): ");
         startTime();

         for(var4 = 0; var4 < var3 * 10; ++var4) {
            FastCollection.Record var8 = var1.head();
            FastCollection.Record var6 = var1.tail();

            while((var8 = var8.getNext()) != var6) {
               if (var1.valueOf(var8) == var1) {
                  throw new Error();
               }
            }
         }

         print(endTime(var3 * var2 * 10));
         println("");
      }

      println("");
   }

   private void benchmarkHashSet() {
      HashSet var1 = new HashSet();
      if (var1.getClass().getName().equals("java.util.HashSet")) {
         println(var1.getClass());

         for(int var2 = 10; var2 <= 10000; var2 *= 10) {
            int var3 = 1000000 / var2;
            print("    Size: " + var2);
            print(", add (new): ");
            startTime();

            int var4;
            int var5;
            for(var4 = 0; var4 < var3; ++var4) {
               var1 = new HashSet();
               var5 = 0;

               while(var5 < var2) {
                  var1.add(this._objects[var5++]);
               }
            }

            print(endTime(var3 * var2));
            print(", add (recycled): ");
            startTime();

            for(var4 = 0; var4 < var3; ++var4) {
               var1.clear();
               var5 = 0;

               while(var5 < var2) {
                  var1.add(this._objects[var5++]);
               }
            }

            print(endTime(var3 * var2));
            print(", contain: ");
            startTime();

            for(var4 = 0; var4 < var3; ++var4) {
               var5 = 0;

               while(var5 < var2) {
                  if (!var1.contains(this._objects[var5++])) {
                     throw new Error();
                  }
               }
            }

            print(endTime(var3 * var2));
            print(", iteration (iterator): ");
            startTime();

            for(var4 = 0; var4 < var3 * 10; ++var4) {
               Iterator var6 = var1.iterator();

               while(var6.hasNext()) {
                  if (var6.next() == var1) {
                     throw new Error();
                  }
               }
            }

            print(endTime(var3 * var2 * 10));
            println("");
         }

         println("");
      }
   }

   private void benchmarkLinkedHashSet() {
      LinkedHashSet var1 = new LinkedHashSet();
      if (var1.getClass().getName().equals("java.util.LinkedHashSet")) {
         println(var1.getClass());

         for(int var2 = 10; var2 <= 10000; var2 *= 10) {
            int var3 = 1000000 / var2;
            print("    Size: " + var2);
            print(", add (new): ");
            startTime();

            int var4;
            int var5;
            for(var4 = 0; var4 < var3; ++var4) {
               var1 = new LinkedHashSet();
               var5 = 0;

               while(var5 < var2) {
                  var1.add(this._objects[var5++]);
               }
            }

            print(endTime(var3 * var2));
            print(", add (recycled): ");
            startTime();

            for(var4 = 0; var4 < var3; ++var4) {
               var1.clear();
               var5 = 0;

               while(var5 < var2) {
                  var1.add(this._objects[var5++]);
               }
            }

            print(endTime(var3 * var2));
            print(", contain: ");
            startTime();

            for(var4 = 0; var4 < var3; ++var4) {
               var5 = 0;

               while(var5 < var2) {
                  if (!var1.contains(this._objects[var5++])) {
                     throw new Error();
                  }
               }
            }

            print(endTime(var3 * var2));
            print(", iteration (iterator): ");
            startTime();

            for(var4 = 0; var4 < var3 * 10; ++var4) {
               Iterator var6 = var1.iterator();

               while(var6.hasNext()) {
                  if (var6.next() == var1) {
                     throw new Error();
                  }
               }
            }

            print(endTime(var3 * var2 * 10));
            println("");
         }

         println("");
      }
   }
}
