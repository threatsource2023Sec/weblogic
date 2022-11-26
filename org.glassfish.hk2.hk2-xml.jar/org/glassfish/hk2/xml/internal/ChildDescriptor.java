package org.glassfish.hk2.xml.internal;

import java.io.Serializable;

public class ChildDescriptor implements Serializable {
   private static final long serialVersionUID = 4427931173669631514L;
   private ParentedModel parented;
   private ChildDataModel childData;

   public ChildDescriptor() {
   }

   public ChildDescriptor(ParentedModel parented) {
      this(parented, (ChildDataModel)null);
   }

   public ChildDescriptor(ChildDataModel childData) {
      this((ParentedModel)null, childData);
   }

   private ChildDescriptor(ParentedModel parented, ChildDataModel childData) {
      this.parented = parented;
      this.childData = childData;
   }

   public ParentedModel getParentedModel() {
      return this.parented;
   }

   public ChildDataModel getChildDataModel() {
      return this.childData;
   }

   public String toString() {
      return "ChildDescriptor(" + this.parented + "," + this.childData + "," + System.identityHashCode(this) + ")";
   }
}
