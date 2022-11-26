package com.bea.wls.redef;

public abstract class MemberMetaData {
   private final String _name;
   private final String _descriptor;
   private final int _access;
   private int _index = -1;
   private boolean _added;
   private boolean _current;

   MemberMetaData(String name, String descriptor, int access) {
      this._name = name;
      this._descriptor = descriptor;
      this._access = access;
   }

   public String getName() {
      return this._name;
   }

   public String getDescriptor() {
      return this._descriptor;
   }

   public int getAccess() {
      return this._access;
   }

   public boolean isStatic() {
      return (this.getAccess() & 8) == 8;
   }

   public boolean isPrivate() {
      return (this.getAccess() & 2) == 2;
   }

   public boolean isAbstract() {
      return (this.getAccess() & 1024) == 1024;
   }

   public boolean isSynchronized() {
      return (this.getAccess() & 32) == 32;
   }

   public int getIndex() {
      return this._index;
   }

   void setIndex(int index) {
      this._index = index;
   }

   public boolean isAdded() {
      return this._added;
   }

   void setAdded(boolean added) {
      this._added = added;
   }

   public boolean isCurrent() {
      return this._current;
   }

   void setCurrent(boolean current) {
      this._current = current;
   }
}
