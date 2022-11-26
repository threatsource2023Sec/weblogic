package weblogic.management.patching.model;

public interface TargetGroup extends Target {
   int getNumTargetedMembers();

   int getNumUntargetedMembers();
}
