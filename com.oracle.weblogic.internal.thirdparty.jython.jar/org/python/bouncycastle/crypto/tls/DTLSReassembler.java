package org.python.bouncycastle.crypto.tls;

import java.util.Vector;

class DTLSReassembler {
   private short msg_type;
   private byte[] body;
   private Vector missing = new Vector();

   DTLSReassembler(short var1, int var2) {
      this.msg_type = var1;
      this.body = new byte[var2];
      this.missing.addElement(new Range(0, var2));
   }

   short getMsgType() {
      return this.msg_type;
   }

   byte[] getBodyIfComplete() {
      return this.missing.isEmpty() ? this.body : null;
   }

   void contributeFragment(short var1, int var2, byte[] var3, int var4, int var5, int var6) {
      int var7 = var5 + var6;
      if (this.msg_type == var1 && this.body.length == var2 && var7 <= var2) {
         if (var6 == 0) {
            if (var5 == 0 && !this.missing.isEmpty()) {
               Range var13 = (Range)this.missing.firstElement();
               if (var13.getEnd() == 0) {
                  this.missing.removeElementAt(0);
               }
            }

         } else {
            for(int var8 = 0; var8 < this.missing.size(); ++var8) {
               Range var9 = (Range)this.missing.elementAt(var8);
               if (var9.getStart() >= var7) {
                  break;
               }

               if (var9.getEnd() > var5) {
                  int var10 = Math.max(var9.getStart(), var5);
                  int var11 = Math.min(var9.getEnd(), var7);
                  int var12 = var11 - var10;
                  System.arraycopy(var3, var4 + var10 - var5, this.body, var10, var12);
                  if (var10 == var9.getStart()) {
                     if (var11 == var9.getEnd()) {
                        this.missing.removeElementAt(var8--);
                     } else {
                        var9.setStart(var11);
                     }
                  } else {
                     if (var11 != var9.getEnd()) {
                        Vector var10000 = this.missing;
                        Range var14 = new Range(var11, var9.getEnd());
                        ++var8;
                        var10000.insertElementAt(var14, var8);
                     }

                     var9.setEnd(var10);
                  }
               }
            }

         }
      }
   }

   void reset() {
      this.missing.removeAllElements();
      this.missing.addElement(new Range(0, this.body.length));
   }

   private static class Range {
      private int start;
      private int end;

      Range(int var1, int var2) {
         this.start = var1;
         this.end = var2;
      }

      public int getStart() {
         return this.start;
      }

      public void setStart(int var1) {
         this.start = var1;
      }

      public int getEnd() {
         return this.end;
      }

      public void setEnd(int var1) {
         this.end = var1;
      }
   }
}
