package com.oracle.wls.shaded.org.apache.bcel.verifier.structurals;

import com.oracle.wls.shaded.org.apache.bcel.Constants;
import com.oracle.wls.shaded.org.apache.bcel.generic.ObjectType;
import com.oracle.wls.shaded.org.apache.bcel.generic.ReferenceType;

public class UninitializedObjectType extends ReferenceType implements Constants {
   private ObjectType initialized;

   public UninitializedObjectType(ObjectType t) {
      super((byte)15, "<UNINITIALIZED OBJECT OF TYPE '" + t.getClassName() + "'>");
      this.initialized = t;
   }

   public ObjectType getInitialized() {
      return this.initialized;
   }

   public boolean equals(Object o) {
      return !(o instanceof UninitializedObjectType) ? false : this.initialized.equals(((UninitializedObjectType)o).initialized);
   }
}
