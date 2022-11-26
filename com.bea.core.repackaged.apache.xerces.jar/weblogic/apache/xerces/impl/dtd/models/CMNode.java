package weblogic.apache.xerces.impl.dtd.models;

public abstract class CMNode {
   private final int fType;
   private CMStateSet fFirstPos = null;
   private CMStateSet fFollowPos = null;
   private CMStateSet fLastPos = null;
   private int fMaxStates = -1;
   private boolean fCompactedForUPA = false;

   public CMNode(int var1) {
      this.fType = var1;
   }

   public abstract boolean isNullable();

   public final int type() {
      return this.fType;
   }

   public final CMStateSet firstPos() {
      if (this.fFirstPos == null) {
         this.fFirstPos = new CMStateSet(this.fMaxStates);
         this.calcFirstPos(this.fFirstPos);
      }

      return this.fFirstPos;
   }

   public final CMStateSet lastPos() {
      if (this.fLastPos == null) {
         this.fLastPos = new CMStateSet(this.fMaxStates);
         this.calcLastPos(this.fLastPos);
      }

      return this.fLastPos;
   }

   final void setFollowPos(CMStateSet var1) {
      this.fFollowPos = var1;
   }

   public final void setMaxStates(int var1) {
      this.fMaxStates = var1;
   }

   public boolean isCompactedForUPA() {
      return this.fCompactedForUPA;
   }

   public void setIsCompactUPAModel(boolean var1) {
      this.fCompactedForUPA = var1;
   }

   protected abstract void calcFirstPos(CMStateSet var1);

   protected abstract void calcLastPos(CMStateSet var1);
}
