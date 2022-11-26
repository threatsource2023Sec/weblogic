package weblogic.management.patching.model;

public interface Target {
   boolean isTargeted();

   void setTargeted(boolean var1);

   void applyTargetedAndPropagateValue();
}
