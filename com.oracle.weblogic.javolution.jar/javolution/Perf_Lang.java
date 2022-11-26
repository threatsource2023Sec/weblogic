package javolution;

import javolution.lang.MathLib;
import javolution.lang.Text;
import javolution.realtime.PoolContext;

final class Perf_Lang extends Javolution implements Runnable {
   static volatile int COUNT = 1000;
   private final String STRING = "Concatenates this line 1000 times (resulting in a text of about 80,000 characters)";
   private final Text TEXT = Text.valueOf((Object)"Concatenates this line 1000 times (resulting in a text of about 80,000 characters)");
   private final Text ONE_CHAR = Text.valueOf('X');

   public void run() {
      println("//////////////////////////////");
      println("// Package: javolution.lang //");
      println("//////////////////////////////");
      println("");
      println("-- String/StringBuffer versus Text --");
      println("");
      println("\"Concatenates this line 1000 times (resulting in a text of about 80,000 characters)\"");
      print("String \"+\" operator: ");
      startTime();
      String var1 = "";
      int var2 = COUNT;

      while(true) {
         --var2;
         if (var2 < 0) {
            println(endTime(1));
            print("StringBuffer \"append\" : ");
            startTime();

            StringBuffer var3;
            int var4;
            for(var2 = 0; var2 < 100; ++var2) {
               var3 = new StringBuffer();
               var4 = COUNT;

               while(true) {
                  --var4;
                  if (var4 < 0) {
                     break;
                  }

                  var3.append("Concatenates this line 1000 times (resulting in a text of about 80,000 characters)");
               }
            }

            println(endTime(100));
            print("Text \"concat\" (heap): ");
            startTime();

            Text var6;
            for(var2 = 0; var2 < 100; ++var2) {
               var6 = Text.EMPTY;
               var4 = COUNT;

               while(true) {
                  --var4;
                  if (var4 < 0) {
                     break;
                  }

                  var6 = var6.concat(this.TEXT);
               }
            }

            println(endTime(100));
            print("Text \"concat\" (stack): ");
            startTime();

            for(var2 = 0; var2 < 100; ++var2) {
               PoolContext.enter();
               var6 = Text.EMPTY;
               var4 = COUNT;

               while(true) {
                  --var4;
                  if (var4 < 0) {
                     PoolContext.exit();
                     break;
                  }

                  var6 = var6.concat(this.TEXT);
               }
            }

            println(endTime(100));
            println("");
            println("Inserts one character at random locations 1,000 times to the 80,000 characters text.");
            print("StringBuffer insert: ");
            startTime();

            int var5;
            for(var2 = 0; var2 < 100; ++var2) {
               var3 = new StringBuffer(var1);
               var4 = COUNT;

               while(true) {
                  --var4;
                  if (var4 < 0) {
                     break;
                  }

                  var5 = MathLib.randomInt(0, var3.length());
                  var3.insert(var5, 'X');
               }
            }

            println(endTime(100));
            print("Text insert (heap): ");
            startTime();

            for(var2 = 0; var2 < 100; ++var2) {
               var6 = Text.valueOf((Object)var1);
               var4 = COUNT;

               while(true) {
                  --var4;
                  if (var4 < 0) {
                     break;
                  }

                  var5 = MathLib.randomInt(0, var6.length());
                  var6 = var6.insert(var5, this.ONE_CHAR);
               }
            }

            println(endTime(100));
            print("Text insert (stack): ");
            startTime();

            for(var2 = 0; var2 < 100; ++var2) {
               PoolContext.enter();
               var6 = Text.valueOf((Object)var1);
               var4 = COUNT;

               while(true) {
                  --var4;
                  if (var4 < 0) {
                     PoolContext.exit();
                     break;
                  }

                  var5 = MathLib.randomInt(0, var6.length());
                  var6 = var6.insert(var5, this.ONE_CHAR);
               }
            }

            println(endTime(100));
            println("");
            println("Delete 1,000 times one character at random location from the 80,000 characters text.");
            print("StringBuffer delete: ");
            startTime();

            for(var2 = 0; var2 < 100; ++var2) {
               var3 = new StringBuffer(var1);
               var4 = COUNT;

               while(true) {
                  --var4;
                  if (var4 < 0) {
                     break;
                  }

                  var5 = MathLib.randomInt(0, var3.length() - 1);
                  var3.deleteCharAt(var5);
               }
            }

            println(endTime(100));
            print("Text delete (heap): ");
            startTime();

            for(var2 = 0; var2 < 100; ++var2) {
               var6 = Text.valueOf((Object)var1);
               var4 = COUNT;

               while(true) {
                  --var4;
                  if (var4 < 0) {
                     break;
                  }

                  var5 = MathLib.randomInt(0, var6.length() - 1);
                  var6 = var6.delete(var5, var5 + 1);
               }
            }

            println(endTime(100));
            print("Text delete (stack): ");
            startTime();

            for(var2 = 0; var2 < 100; ++var2) {
               PoolContext.enter();
               var6 = Text.valueOf((Object)var1);
               var4 = COUNT;

               while(true) {
                  --var4;
                  if (var4 < 0) {
                     PoolContext.exit();
                     break;
                  }

                  var5 = MathLib.randomInt(0, var6.length() - 1);
                  var6 = var6.delete(var5, var5 + 1);
               }
            }

            println(endTime(100));
            println("");
            return;
         }

         var1 = var1 + "Concatenates this line 1000 times (resulting in a text of about 80,000 characters)";
      }
   }
}
