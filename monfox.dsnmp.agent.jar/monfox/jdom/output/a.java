package monfox.jdom.output;

import java.util.Stack;
import monfox.jdom.Namespace;

class a {
   private Stack a = new Stack();
   private Stack b = new Stack();

   public a() {
   }

   public void push(Namespace var1) {
      this.a.push(var1.getPrefix());
      this.b.push(var1.getURI());
   }

   public String pop() {
      String var1 = (String)this.a.pop();
      this.b.pop();
      return var1;
   }

   public int size() {
      return this.a.size();
   }

   public String getURI(String var1) {
      int var2 = this.a.lastIndexOf(var1);
      if (var2 == -1) {
         return null;
      } else {
         String var3 = (String)this.b.elementAt(var2);
         return var3;
      }
   }

   public void printStack() {
      System.out.println(a("ncPO}\u00077") + this.a.size());
      int var1 = 0;

      while(var1 < this.a.size()) {
         System.out.println(this.a.elementAt(var1) + "&" + this.b.elementAt(var1));
         ++var1;
         if (XMLOutputter.q) {
            break;
         }
      }

   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 61;
               break;
            case 1:
               var10003 = 23;
               break;
            case 2:
               var10003 = 49;
               break;
            case 3:
               var10003 = 44;
               break;
            default:
               var10003 = 22;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
