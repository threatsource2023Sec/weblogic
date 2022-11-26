package weblogic.wtc.gwt;

import java.io.Serializable;
import weblogic.wtc.jatmi.CallDescriptor;

class TuxedoCallDescriptor implements CallDescriptor, Serializable {
   private static final long serialVersionUID = 8224018558301123807L;
   private CallDescriptor internalCallDescriptor;
   private int internalIndex;
   private boolean asynchronous;
   private boolean inTransaction;

   public TuxedoCallDescriptor(CallDescriptor cd, int index, boolean asynchronous, boolean inTransaction) {
      this.internalCallDescriptor = cd;
      this.internalIndex = index;
      this.asynchronous = asynchronous;
      this.inTransaction = inTransaction;
   }

   public int getIndex() {
      return this.internalIndex;
   }

   public CallDescriptor getCallDescriptor() {
      return this.internalCallDescriptor;
   }

   public boolean isAsynchronous() {
      return this.asynchronous;
   }

   public boolean isAssociatedWithATransaction() {
      return this.inTransaction;
   }

   public boolean equals(Object compareTo) {
      if (compareTo == null) {
         return false;
      } else {
         return compareTo instanceof CallDescriptor ? CallDescriptorUtil.isCallDescriptorEqual(this, (CallDescriptor)compareTo) : false;
      }
   }

   public String toString() {
      return new String(this.internalCallDescriptor + ":" + this.internalIndex + ":" + this.asynchronous);
   }

   public int hashCode() {
      return this.internalCallDescriptor.hashCode() ^ this.internalIndex;
   }
}
