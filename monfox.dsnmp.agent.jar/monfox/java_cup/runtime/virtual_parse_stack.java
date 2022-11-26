package monfox.java_cup.runtime;

import java.util.Stack;

public class virtual_parse_stack {
   protected Stack real_stack;
   protected int real_next;
   protected Stack vstack;

   public virtual_parse_stack(Stack var1) throws Exception {
      if (var1 == null) {
         throw new Exception(a("{_.\u001d:\\P6X8SC)\u001d:\u0012T(\n'@\u000bz\u0019<FT7\b<\u0012E5X+@T;\f-\u0012_/\u0014$\u0012G3\n<GP6X;FP9\u0013"));
      } else {
         this.real_stack = var1;
         this.vstack = new Stack();
         this.real_next = 0;
         this.get_from_real();
      }
   }

   protected void get_from_real() {
      if (this.real_next < this.real_stack.size()) {
         Symbol var1 = (Symbol)this.real_stack.elementAt(this.real_stack.size() - 1 - this.real_next);
         ++this.real_next;
         this.vstack.push(new Integer(var1.parse_state));
      }
   }

   public boolean empty() {
      return this.vstack.empty();
   }

   public int top() throws Exception {
      if (this.vstack.empty()) {
         throw new Exception(a("{_.\u001d:\\P6X8SC)\u001d:\u0012T(\n'@\u000bz\f'B\u0019sX+S]6\u001d,\u0012^4X-_A.\u0001hDX(\f=S]z\u000b<SR1"));
      } else {
         return (Integer)this.vstack.peek();
      }
   }

   public void pop() throws Exception {
      if (this.vstack.empty()) {
         throw new Exception(a("{_.\u001d:\\P6X8SC)\u001d:\u0012T(\n'@\u000bz\b'B\u0011<\n'_\u0011?\u00158FHz\u000e!@E/\u0019$\u0012B.\u0019+Y"));
      } else {
         this.vstack.pop();
         if (this.vstack.empty()) {
            this.get_from_real();
         }

      }
   }

   public void push(int var1) {
      this.vstack.push(new Integer(var1));
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 50;
               break;
            case 1:
               var10003 = 49;
               break;
            case 2:
               var10003 = 90;
               break;
            case 3:
               var10003 = 120;
               break;
            default:
               var10003 = 72;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
