package com.bea.core.repackaged.aspectj.apache.bcel.generic;

public final class LocalVariableTag extends Tag {
   private final String signature;
   private String name;
   private int slot;
   private final int startPosition;
   private boolean remapped = false;
   private int hashCode = 0;
   private Type type;

   public LocalVariableTag(String signature, String name, int slot, int startPosition) {
      this.signature = signature;
      this.name = name;
      this.slot = slot;
      this.startPosition = startPosition;
   }

   public LocalVariableTag(Type type, String signature, String name, int slot, int startPosition) {
      this.type = type;
      this.signature = signature;
      this.name = name;
      this.slot = slot;
      this.startPosition = startPosition;
   }

   public String getName() {
      return this.name;
   }

   public int getSlot() {
      return this.slot;
   }

   public String getType() {
      return this.signature;
   }

   public Type getRealType() {
      return this.type;
   }

   public void updateSlot(int newSlot) {
      this.slot = newSlot;
      this.remapped = true;
      this.hashCode = 0;
   }

   public void setName(String name) {
      this.name = name;
      this.hashCode = 0;
   }

   public boolean isRemapped() {
      return this.remapped;
   }

   public String toString() {
      return "local " + this.slot + ": " + this.signature + " " + this.name;
   }

   public boolean equals(Object other) {
      if (!(other instanceof LocalVariableTag)) {
         return false;
      } else {
         LocalVariableTag o = (LocalVariableTag)other;
         return o.slot == this.slot && o.startPosition == this.startPosition && o.signature.equals(this.signature) && o.name.equals(this.name);
      }
   }

   public int hashCode() {
      if (this.hashCode == 0) {
         int ret = this.signature.hashCode();
         ret = 37 * ret + this.name.hashCode();
         ret = 37 * ret + this.slot;
         ret = 37 * ret + this.startPosition;
         this.hashCode = ret;
      }

      return this.hashCode;
   }
}
