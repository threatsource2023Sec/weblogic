package weblogic.descriptor;

import java.util.EventObject;
import java.util.Set;

public abstract class BeanUpdateEvent extends EventObject {
   private final DescriptorBean proposedBean;
   private final int updateID;
   private ParentEntity parentEntity;
   private boolean isParentEntitySet;

   protected BeanUpdateEvent(DescriptorBean sourceBean, DescriptorBean proposedBean, int updateID) {
      super(sourceBean);
      this.parentEntity = BeanUpdateEvent.ParentEntity.none;
      this.proposedBean = proposedBean;
      this.updateID = updateID;
   }

   public int getUpdateID() {
      return this.updateID;
   }

   public Object getSource() {
      return super.getSource();
   }

   public DescriptorBean getSourceBean() {
      return (DescriptorBean)this.getSource();
   }

   public DescriptorBean getProposedBean() {
      return this.proposedBean;
   }

   public boolean isParentEntitySet() {
      return this.isParentEntitySet;
   }

   private void setParentEntitySet(boolean isSet) {
      this.isParentEntitySet = isSet;
   }

   public void setParentEntity(ParentEntity parent) {
      this.parentEntity = parent;
      this.setParentEntitySet(true);
   }

   public ParentEntity getParentEntity() {
      return this.parentEntity;
   }

   public abstract PropertyUpdate[] getUpdateList();

   public static class PropertyUpdate {
      private String propertyName;
      private int updateType;
      private Object addedOrRemoved;
      private boolean isDynamic;
      private boolean originalSetBit;
      private boolean proposedSetBit;
      private Set restartElements;
      private boolean isRestartAnnotationDefined;
      public static final int CHANGE = 1;
      public static final int ADD = 2;
      public static final int REMOVE = 3;

      public PropertyUpdate(String propertyName, int updateType, Object addedOrRemoved, boolean isDynamic, boolean originalSetBit, boolean proposedSetBit, Set restartElements, boolean isRestartAnnotationDefined) {
         this(propertyName, updateType, addedOrRemoved, isDynamic, originalSetBit, proposedSetBit);
         this.restartElements = restartElements;
         this.isRestartAnnotationDefined = isRestartAnnotationDefined;
      }

      public PropertyUpdate(String propertyName, boolean isDynamic, boolean originalSetBit, boolean proposedSetBit) {
         this.propertyName = propertyName;
         this.updateType = 1;
         this.isDynamic = isDynamic;
         this.originalSetBit = originalSetBit;
         this.proposedSetBit = proposedSetBit;
      }

      public PropertyUpdate(String propertyName, int updateType, Object addedOrRemoved, boolean isDynamic, boolean originalSetBit, boolean proposedSetBit) {
         this(propertyName, isDynamic, originalSetBit, proposedSetBit);
         this.updateType = updateType;
         this.addedOrRemoved = addedOrRemoved;
      }

      public String getPropertyName() {
         return this.propertyName;
      }

      public int getUpdateType() {
         return this.updateType;
      }

      public Object getAddedObject() {
         return this.updateType == 2 ? this.addedOrRemoved : null;
      }

      public void resetAddedObject(Object added) {
         this.addedOrRemoved = added;
      }

      public void resetRemovedObject(Object removed) {
         this.addedOrRemoved = removed;
      }

      public Object getRemovedObject() {
         return this.updateType == 3 ? this.addedOrRemoved : null;
      }

      public String toString() {
         switch (this.updateType) {
            case 1:
               return this.propertyName + " (CHANGE)(Dynamic=" + this.isDynamic() + ")";
            case 2:
               return this.propertyName + " (ADD " + this.addedOrRemoved + ")(Dynamic=" + this.isDynamic() + ")";
            case 3:
               return this.propertyName + " (REMOVE " + this.addedOrRemoved + ")(Dynamic=" + this.isDynamic() + ")";
            default:
               throw new AssertionError("Change type " + this.updateType + " illegal");
         }
      }

      public int hashCode() {
         return this.propertyName.hashCode();
      }

      public Set getRestartElements() {
         return this.restartElements;
      }

      public boolean isRestartAnnotationDefined() {
         return this.isRestartAnnotationDefined;
      }

      public boolean equals(Object o) {
         if (!(o instanceof PropertyUpdate)) {
            return false;
         } else {
            PropertyUpdate other = (PropertyUpdate)o;
            if (!this.propertyName.equals(other.propertyName)) {
               return false;
            } else if (this.updateType != other.updateType) {
               return false;
            } else {
               return this.addedOrRemoved == other.addedOrRemoved;
            }
         }
      }

      public boolean isDynamic() {
         return this.isDynamic;
      }

      public void resetDynamic() {
         this.isDynamic = true;
      }

      public boolean isDerivedUpdate() {
         return !this.originalSetBit && !this.proposedSetBit;
      }

      public boolean isUnsetUpdate() {
         return this.originalSetBit && !this.proposedSetBit;
      }

      public boolean isRemoveUpdate() {
         return this.updateType == 3;
      }

      public boolean isChangeUpdate() {
         return this.updateType == 1;
      }
   }

   public static enum ParentEntity {
      server,
      partition,
      none;
   }
}
