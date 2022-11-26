package com.bea.wls.redef;

public class LocalVariable {
   private int startPc;
   private int length;
   private int local;
   private String name;
   private String typeName;

   public LocalVariable(serp.bytecode.LocalVariable lv) {
      this.startPc = lv.getStartPc();
      this.length = lv.getLength();
      this.local = lv.getLocal();
      this.name = lv.getName();
      this.typeName = lv.getTypeName();
   }

   public boolean updateScope(int start, int previousEnd, int newEnd) {
      int myEnd = this.startPc + this.length;
      if (myEnd <= start) {
         return false;
      } else {
         int padding = newEnd - previousEnd;
         if (this.startPc >= previousEnd) {
            this.startPc += padding;
            return true;
         } else {
            this.length += padding;
            return true;
         }
      }
   }

   public void insert(serp.bytecode.LocalVariableTable table) {
      serp.bytecode.LocalVariable lv = table.addLocalVariable(this.name, this.typeName);
      lv.setStartPc(this.startPc);
      lv.setLength(this.length);
      lv.setLocal(this.local);
   }

   public String getName() {
      return this.name;
   }

   public int getStartPc() {
      return this.startPc;
   }

   public int getLength() {
      return this.length;
   }
}
