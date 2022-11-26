package monfox.toolkit.snmp.util;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

public class AsciiReader extends FilterReader {
   private int a = 1;
   private int b = 0;
   private static final String c = "$Id: AsciiReader.java,v 1.5 2003/07/14 15:25:56 sking Exp $";

   public AsciiReader(Reader var1) {
      super(var1);
      this.in = var1;
      this.a = 1;
      this.b = 0;
   }

   public int getLineNum() {
      return this.a;
   }

   public int getCharNum() {
      return this.b;
   }

   public int read() throws IOException {
      boolean var1 = false;

      int var10000;
      while(true) {
         int var2;
         if ((var2 = super.read()) == -1) {
            var10000 = var2;
            break;
         }

         ++this.b;
         if (var2 >= 32 && var2 < 128 || var2 == 10 || var2 == 9) {
            var10000 = var2;
            if (WorkItem.d == 0) {
               if (var2 == 10) {
                  ++this.a;
               }

               return var2;
            }
            break;
         }
      }

      return var10000;
   }

   public int read(char[] var1, int var2, int var3) throws IOException, NullPointerException, IndexOutOfBoundsException {
      if (var1 == null) {
         throw new NullPointerException();
      } else if (var2 >= 0 && var3 >= 0 && var2 + var3 <= var1.length) {
         int var4 = 0;

         int var10000;
         while(true) {
            if (var4 >= var3) {
               var10000 = var4;
               break;
            }

            try {
               int var5 = this.read();
               var10000 = var5;
               if (WorkItem.d != 0) {
                  break;
               }

               if (var5 == -1) {
                  if (var4 > 0) {
                     return var4;
                  }

                  return -1;
               }

               var1[var2 + var4++] = (char)var5;
            } catch (IOException var6) {
               if (var4 > 0) {
                  return var4;
               }

               return -1;
            }
         }

         return var10000;
      } else {
         throw new IndexOutOfBoundsException();
      }
   }
}
