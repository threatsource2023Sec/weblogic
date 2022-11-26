package com.kenai.jffi;

public final class Function {
   private final CallContext callContext;
   final long functionAddress;
   final long contextAddress;

   public Function(long address, Type returnType, Type... paramTypes) {
      this(address, returnType, paramTypes, CallingConvention.DEFAULT, true);
   }

   public Function(long address, CallContext callContext) {
      this.functionAddress = address;
      this.callContext = callContext;
      this.contextAddress = callContext.getAddress();
   }

   public Function(long address, Type returnType, Type[] paramTypes, CallingConvention convention) {
      this(address, returnType, paramTypes, convention, true);
   }

   public Function(long address, Type returnType, Type[] paramTypes, CallingConvention convention, boolean saveErrno) {
      this.functionAddress = address;
      this.callContext = CallContext.getCallContext(returnType, paramTypes, convention, saveErrno);
      this.contextAddress = this.callContext.getAddress();
   }

   public final int getParameterCount() {
      return this.callContext.getParameterCount();
   }

   public final int getRawParameterSize() {
      return this.callContext.getRawParameterSize();
   }

   public final CallContext getCallContext() {
      return this.callContext;
   }

   final long getContextAddress() {
      return this.contextAddress;
   }

   public final long getFunctionAddress() {
      return this.functionAddress;
   }

   public final Type getReturnType() {
      return this.callContext.getReturnType();
   }

   public final Type getParameterType(int index) {
      return this.callContext.getParameterType(index);
   }

   /** @deprecated */
   @Deprecated
   public final void dispose() {
   }
}
