package org.python.modules.jffi;

import org.python.core.PyObject;

public abstract class NativeDataConverter {
   private final boolean referenceRequired;
   private final boolean postInvokeRequired;

   public NativeDataConverter() {
      this.referenceRequired = false;
      this.postInvokeRequired = false;
   }

   public NativeDataConverter(boolean referenceRequired, boolean postInvokeRequired) {
      this.referenceRequired = referenceRequired;
      this.postInvokeRequired = postInvokeRequired;
   }

   public final boolean isReferenceRequired() {
      return this.referenceRequired;
   }

   public final boolean isPostInvokeRequired() {
      return this.postInvokeRequired;
   }

   public abstract PyObject fromNative(PyObject var1);

   public abstract PyObject toNative(PyObject var1);

   public abstract NativeType nativeType();
}
