package org.python.objectweb.asm;

class CurrentFrame extends Frame {
   void a(int var1, int var2, ClassWriter var3, Item var4) {
      super.a(var1, var2, var3, var4);
      Frame var5 = new Frame();
      this.a(var3, var5, 0);
      this.b(var5);
      this.b.f = 0;
   }
}
