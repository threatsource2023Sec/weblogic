package com.kenai.jffi;

import java.math.BigDecimal;

public abstract class Invoker {
   private final Foreign foreign;
   private final ObjectParameterInvoker objectParameterInvoker;

   public static Invoker getInstance() {
      return Invoker.SingletonHolder.INSTANCE;
   }

   private Invoker() {
      this(Foreign.getInstance(), ObjectParameterInvoker.getInstance());
   }

   Invoker(Foreign foreign, ObjectParameterInvoker objectParameterInvoker) {
      this.foreign = foreign;
      this.objectParameterInvoker = objectParameterInvoker;
   }

   public final ObjectParameterInvoker getObjectParameterInvoker() {
      return this.objectParameterInvoker;
   }

   public final int invokeI0(CallContext context, long function) {
      return Foreign.invokeI0(context.contextAddress, function);
   }

   public final int invokeI1(CallContext context, long function, int arg1) {
      return Foreign.invokeI1(context.contextAddress, function, arg1);
   }

   public final int invokeI2(CallContext context, long function, int arg1, int arg2) {
      return Foreign.invokeI2(context.contextAddress, function, arg1, arg2);
   }

   public final int invokeI3(CallContext context, long function, int arg1, int arg2, int arg3) {
      return Foreign.invokeI3(context.contextAddress, function, arg1, arg2, arg3);
   }

   public final int invokeI4(CallContext context, long function, int arg1, int arg2, int arg3, int arg4) {
      return Foreign.invokeI4(context.contextAddress, function, arg1, arg2, arg3, arg4);
   }

   public final int invokeI5(CallContext context, long function, int arg1, int arg2, int arg3, int arg4, int arg5) {
      return Foreign.invokeI5(context.contextAddress, function, arg1, arg2, arg3, arg4, arg5);
   }

   public final int invokeI6(CallContext context, long function, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6) {
      return Foreign.invokeI6(context.contextAddress, function, arg1, arg2, arg3, arg4, arg5, arg6);
   }

   public final int invokeI0NoErrno(CallContext context, long function) {
      return Foreign.invokeI0NoErrno(context.contextAddress, function);
   }

   public final int invokeI1NoErrno(CallContext context, long function, int arg1) {
      return Foreign.invokeI1NoErrno(context.contextAddress, function, arg1);
   }

   public final int invokeI2NoErrno(CallContext context, long function, int arg1, int arg2) {
      return Foreign.invokeI2NoErrno(context.contextAddress, function, arg1, arg2);
   }

   public final int invokeI3NoErrno(CallContext context, long function, int arg1, int arg2, int arg3) {
      return Foreign.invokeI3NoErrno(context.contextAddress, function, arg1, arg2, arg3);
   }

   public final int invokeI4NoErrno(CallContext context, long function, int arg1, int arg2, int arg3, int arg4) {
      return Foreign.invokeI4NoErrno(context.contextAddress, function, arg1, arg2, arg3, arg4);
   }

   public final int invokeI5NoErrno(CallContext context, long function, int arg1, int arg2, int arg3, int arg4, int arg5) {
      return Foreign.invokeI5NoErrno(context.contextAddress, function, arg1, arg2, arg3, arg4, arg5);
   }

   public final int invokeI6NoErrno(CallContext context, long function, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6) {
      return Foreign.invokeI6NoErrno(context.contextAddress, function, arg1, arg2, arg3, arg4, arg5, arg6);
   }

   /** @deprecated */
   @Deprecated
   public final int invokeVrI(Function function) {
      return Foreign.invokeI0(function.contextAddress, function.functionAddress);
   }

   /** @deprecated */
   @Deprecated
   public final int invokeNoErrnoVrI(Function function) {
      return Foreign.invokeI0NoErrno(function.contextAddress, function.functionAddress);
   }

   /** @deprecated */
   @Deprecated
   public final int invokeIrI(Function function, int arg1) {
      return Foreign.invokeI1(function.contextAddress, function.functionAddress, arg1);
   }

   /** @deprecated */
   @Deprecated
   public final int invokeNoErrnoIrI(Function function, int arg1) {
      return Foreign.invokeI1NoErrno(function.contextAddress, function.functionAddress, arg1);
   }

   /** @deprecated */
   @Deprecated
   public final int invokeIIrI(Function function, int arg1, int arg2) {
      return Foreign.invokeI2(function.contextAddress, function.functionAddress, arg1, arg2);
   }

   /** @deprecated */
   @Deprecated
   public final int invokeNoErrnoIIrI(Function function, int arg1, int arg2) {
      return Foreign.invokeI2NoErrno(function.contextAddress, function.functionAddress, arg1, arg2);
   }

   /** @deprecated */
   @Deprecated
   public final int invokeIIIrI(Function function, int arg1, int arg2, int arg3) {
      return Foreign.invokeI3(function.contextAddress, function.functionAddress, arg1, arg2, arg3);
   }

   /** @deprecated */
   @Deprecated
   public final int invokeNoErrnoIIIrI(Function function, int arg1, int arg2, int arg3) {
      return Foreign.invokeI3NoErrno(function.contextAddress, function.functionAddress, arg1, arg2, arg3);
   }

   /** @deprecated */
   @Deprecated
   public final int invokeIIIIrI(Function function, int arg1, int arg2, int arg3, int arg4) {
      return Foreign.invokeI4(function.contextAddress, function.functionAddress, arg1, arg2, arg3, arg4);
   }

   /** @deprecated */
   @Deprecated
   public final int invokeIIIIIrI(Function function, int arg1, int arg2, int arg3, int arg4, int arg5) {
      return Foreign.invokeI5(function.contextAddress, function.functionAddress, arg1, arg2, arg3, arg4, arg5);
   }

   /** @deprecated */
   @Deprecated
   public final int invokeIIIIIIrI(Function function, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6) {
      return Foreign.invokeI6(function.contextAddress, function.functionAddress, arg1, arg2, arg3, arg4, arg5, arg6);
   }

   public final long invokeL0(CallContext context, long function) {
      return Foreign.invokeL0(context.contextAddress, function);
   }

   public final long invokeL1(CallContext context, long function, long arg1) {
      return Foreign.invokeL1(context.contextAddress, function, arg1);
   }

   public final long invokeL2(CallContext context, long function, long arg1, long arg2) {
      return Foreign.invokeL2(context.contextAddress, function, arg1, arg2);
   }

   public final long invokeL3(CallContext context, long function, long arg1, long arg2, long arg3) {
      return Foreign.invokeL3(context.contextAddress, function, arg1, arg2, arg3);
   }

   public final long invokeL4(CallContext context, long function, long arg1, long arg2, long arg3, long arg4) {
      return Foreign.invokeL4(context.contextAddress, function, arg1, arg2, arg3, arg4);
   }

   public final long invokeL5(CallContext context, long function, long arg1, long arg2, long arg3, long arg4, long arg5) {
      return Foreign.invokeL5(context.contextAddress, function, arg1, arg2, arg3, arg4, arg5);
   }

   public final long invokeL6(CallContext context, long function, long arg1, long arg2, long arg3, long arg4, long arg5, long arg6) {
      return Foreign.invokeL6(context.contextAddress, function, arg1, arg2, arg3, arg4, arg5, arg6);
   }

   public final long invokeL0NoErrno(CallContext context, long function) {
      return Foreign.invokeL0NoErrno(context.contextAddress, function);
   }

   public final long invokeL1NoErrno(CallContext context, long function, long arg1) {
      return Foreign.invokeL1NoErrno(context.contextAddress, function, arg1);
   }

   public final long invokeL2NoErrno(CallContext context, long function, long arg1, long arg2) {
      return Foreign.invokeL2NoErrno(context.contextAddress, function, arg1, arg2);
   }

   public final long invokeL3NoErrno(CallContext context, long function, long arg1, long arg2, long arg3) {
      return Foreign.invokeL3NoErrno(context.contextAddress, function, arg1, arg2, arg3);
   }

   public final long invokeL4NoErrno(CallContext context, long function, long arg1, long arg2, long arg3, long arg4) {
      return Foreign.invokeL4NoErrno(context.contextAddress, function, arg1, arg2, arg3, arg4);
   }

   public final long invokeL5NoErrno(CallContext context, long function, long arg1, long arg2, long arg3, long arg4, long arg5) {
      return Foreign.invokeL5NoErrno(context.contextAddress, function, arg1, arg2, arg3, arg4, arg5);
   }

   public final long invokeL6NoErrno(CallContext context, long function, long arg1, long arg2, long arg3, long arg4, long arg5, long arg6) {
      return Foreign.invokeL6NoErrno(context.contextAddress, function, arg1, arg2, arg3, arg4, arg5, arg6);
   }

   public final long invokeVrL(Function function) {
      return Foreign.invokeL0(function.contextAddress, function.functionAddress);
   }

   public final long invokeLrL(Function function, long arg1) {
      return Foreign.invokeL1(function.contextAddress, function.functionAddress, arg1);
   }

   public final long invokeLLrL(Function function, long arg1, long arg2) {
      return Foreign.invokeL2(function.contextAddress, function.functionAddress, arg1, arg2);
   }

   public final long invokeLLLrL(Function function, long arg1, long arg2, long arg3) {
      return Foreign.invokeL3(function.contextAddress, function.functionAddress, arg1, arg2, arg3);
   }

   public final long invokeLLLLrL(Function function, long arg1, long arg2, long arg3, long arg4) {
      return Foreign.invokeL4(function.contextAddress, function.functionAddress, arg1, arg2, arg3, arg4);
   }

   public final long invokeLLLLLrL(Function function, long arg1, long arg2, long arg3, long arg4, long arg5) {
      return Foreign.invokeL5(function.contextAddress, function.functionAddress, arg1, arg2, arg3, arg4, arg5);
   }

   public final long invokeLLLLLLrL(Function function, long arg1, long arg2, long arg3, long arg4, long arg5, long arg6) {
      return Foreign.invokeL6(function.contextAddress, function.functionAddress, arg1, arg2, arg3, arg4, arg5, arg6);
   }

   public final long invokeN0(CallContext context, long function) {
      return Foreign.invokeN0(context.contextAddress, function);
   }

   public final long invokeN1(CallContext context, long function, long arg1) {
      return Foreign.invokeN1(context.contextAddress, function, arg1);
   }

   public final long invokeN2(CallContext context, long function, long arg1, long arg2) {
      return Foreign.invokeN2(context.contextAddress, function, arg1, arg2);
   }

   public final long invokeN3(CallContext context, long function, long arg1, long arg2, long arg3) {
      return Foreign.invokeN3(context.contextAddress, function, arg1, arg2, arg3);
   }

   public final long invokeN4(CallContext context, long function, long arg1, long arg2, long arg3, long arg4) {
      return Foreign.invokeN4(context.contextAddress, function, arg1, arg2, arg3, arg4);
   }

   public final long invokeN5(CallContext context, long function, long arg1, long arg2, long arg3, long arg4, long arg5) {
      return Foreign.invokeN5(context.contextAddress, function, arg1, arg2, arg3, arg4, arg5);
   }

   public final long invokeN6(CallContext context, long function, long arg1, long arg2, long arg3, long arg4, long arg5, long arg6) {
      return Foreign.invokeN6(context.contextAddress, function, arg1, arg2, arg3, arg4, arg5, arg6);
   }

   public final long invokeVrN(Function function) {
      return Foreign.invokeN0(function.contextAddress, function.functionAddress);
   }

   public final long invokeNrN(Function function, long arg1) {
      return Foreign.invokeN1(function.contextAddress, function.functionAddress, arg1);
   }

   public final long invokeNNrN(Function function, long arg1, long arg2) {
      return Foreign.invokeN2(function.contextAddress, function.functionAddress, arg1, arg2);
   }

   public final long invokeNNNrN(Function function, long arg1, long arg2, long arg3) {
      return Foreign.invokeN3(function.contextAddress, function.functionAddress, arg1, arg2, arg3);
   }

   public final long invokeNNNNrN(Function function, long arg1, long arg2, long arg3, long arg4) {
      return Foreign.invokeN4(function.contextAddress, function.functionAddress, arg1, arg2, arg3, arg4);
   }

   public final long invokeNNNNNrN(Function function, long arg1, long arg2, long arg3, long arg4, long arg5) {
      return Foreign.invokeN5(function.contextAddress, function.functionAddress, arg1, arg2, arg3, arg4, arg5);
   }

   public final long invokeNNNNNNrN(Function function, long arg1, long arg2, long arg3, long arg4, long arg5, long arg6) {
      return Foreign.invokeN6(function.contextAddress, function.functionAddress, arg1, arg2, arg3, arg4, arg5, arg6);
   }

   /** @deprecated */
   @Deprecated
   public final long invokeNNO1rN(Function function, long n1, long n2, Object o1, int o1off, int o1len, ObjectParameterInfo o1flags) {
      return Foreign.invokeN2O1(function.contextAddress, function.functionAddress, n1, n2, o1, o1flags.asObjectInfo(), o1off, o1len);
   }

   /** @deprecated */
   @Deprecated
   public final long invokeNNO2rN(Function function, long n1, long n2, Object o1, int o1off, int o1len, ObjectParameterInfo o1flags, Object o2, int o2off, int o2len, ObjectParameterInfo o2flags) {
      return Foreign.invokeN2O2(function.contextAddress, function.functionAddress, n1, n2, o1, o1flags.asObjectInfo(), o1off, o1len, o2, o2flags.asObjectInfo(), o2off, o2len);
   }

   /** @deprecated */
   @Deprecated
   public final long invokeNNNO1rN(Function function, long n1, long n2, long n3, Object o1, int o1off, int o1len, ObjectParameterInfo o1flags) {
      return Foreign.invokeN3O1(function.contextAddress, function.functionAddress, n1, n2, n3, o1, o1flags.asObjectInfo(), o1off, o1len);
   }

   /** @deprecated */
   @Deprecated
   public final long invokeNNNO2rN(Function function, long n1, long n2, long n3, Object o1, int o1off, int o1len, ObjectParameterInfo o1flags, Object o2, int o2off, int o2len, ObjectParameterInfo o2flags) {
      return Foreign.invokeN3O2(function.contextAddress, function.functionAddress, n1, n2, n3, o1, o1flags.asObjectInfo(), o1off, o1len, o2, o2flags.asObjectInfo(), o2off, o2len);
   }

   private static RuntimeException newObjectCountError(int objCount) {
      return new RuntimeException("invalid object count: " + objCount);
   }

   private static RuntimeException newInsufficientObjectCountError(int objCount) {
      return new RuntimeException("invalid object count: " + objCount);
   }

   private static RuntimeException newHeapObjectCountError(int objCount) {
      return new RuntimeException("insufficient number of heap objects supplied (" + objCount + " required)");
   }

   public final long invokeN1O1(CallContext ctx, long function, long n1, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info) {
      return Foreign.invokeN1O1(ctx.contextAddress, function, n1, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1));
   }

   public final long invokeN2O1(CallContext ctx, long function, long n1, long n2, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info) {
      return Foreign.invokeN2O1(ctx.contextAddress, function, n1, n2, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1));
   }

   public final long invokeN2O2(CallContext ctx, long function, long n1, long n2, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info, Object o2, ObjectParameterStrategy s2, ObjectParameterInfo o2info) {
      return Foreign.invokeN2O2(ctx.contextAddress, function, n1, n2, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2));
   }

   public final long invokeN3O1(CallContext ctx, long function, long n1, long n2, long n3, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info) {
      return Foreign.invokeN3O1(ctx.contextAddress, function, n1, n2, n3, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1));
   }

   public final long invokeN3O2(CallContext ctx, long function, long n1, long n2, long n3, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info, Object o2, ObjectParameterStrategy s2, ObjectParameterInfo o2info) {
      return Foreign.invokeN3O2(ctx.contextAddress, function, n1, n2, n3, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2));
   }

   public final long invokeN3O3(CallContext ctx, long function, long n1, long n2, long n3, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info, Object o2, ObjectParameterStrategy s2, ObjectParameterInfo o2info, Object o3, ObjectParameterStrategy s3, ObjectParameterInfo o3info) {
      return Foreign.invokeN3O3(ctx.contextAddress, function, n1, n2, n3, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2), s3.object(o3), s3.objectInfo(o3info), s3.offset(o3), s3.length(o3));
   }

   public final long invokeN4O1(CallContext ctx, long function, long n1, long n2, long n3, long n4, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info) {
      return Foreign.invokeN4O1(ctx.contextAddress, function, n1, n2, n3, n4, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1));
   }

   public final long invokeN4O2(CallContext ctx, long function, long n1, long n2, long n3, long n4, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info, Object o2, ObjectParameterStrategy s2, ObjectParameterInfo o2info) {
      return Foreign.invokeN4O2(ctx.contextAddress, function, n1, n2, n3, n4, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2));
   }

   public final long invokeN4O3(CallContext ctx, long function, long n1, long n2, long n3, long n4, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info, Object o2, ObjectParameterStrategy s2, ObjectParameterInfo o2info, Object o3, ObjectParameterStrategy s3, ObjectParameterInfo o3info) {
      return Foreign.invokeN4O3(ctx.contextAddress, function, n1, n2, n3, n4, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2), s3.object(o3), s3.objectInfo(o3info), s3.offset(o3), s3.length(o3));
   }

   public final long invokeN5O1(CallContext ctx, long function, long n1, long n2, long n3, long n4, long n5, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info) {
      return Foreign.invokeN5O1(ctx.contextAddress, function, n1, n2, n3, n4, n5, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1));
   }

   public final long invokeN5O2(CallContext ctx, long function, long n1, long n2, long n3, long n4, long n5, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info, Object o2, ObjectParameterStrategy s2, ObjectParameterInfo o2info) {
      return Foreign.invokeN5O2(ctx.contextAddress, function, n1, n2, n3, n4, n5, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2));
   }

   public final long invokeN5O3(CallContext ctx, long function, long n1, long n2, long n3, long n4, long n5, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info, Object o2, ObjectParameterStrategy s2, ObjectParameterInfo o2info, Object o3, ObjectParameterStrategy s3, ObjectParameterInfo o3info) {
      return Foreign.invokeN5O3(ctx.contextAddress, function, n1, n2, n3, n4, n5, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2), s3.object(o3), s3.objectInfo(o3info), s3.offset(o3), s3.length(o3));
   }

   public final long invokeN6O1(CallContext ctx, long function, long n1, long n2, long n3, long n4, long n5, long n6, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info) {
      return Foreign.invokeN6O1(ctx.contextAddress, function, n1, n2, n3, n4, n5, n6, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1));
   }

   public final long invokeN6O2(CallContext ctx, long function, long n1, long n2, long n3, long n4, long n5, long n6, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info, Object o2, ObjectParameterStrategy s2, ObjectParameterInfo o2info) {
      return Foreign.invokeN6O2(ctx.contextAddress, function, n1, n2, n3, n4, n5, n6, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2));
   }

   public final long invokeN6O3(CallContext ctx, long function, long n1, long n2, long n3, long n4, long n5, long n6, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info, Object o2, ObjectParameterStrategy s2, ObjectParameterInfo o2info, Object o3, ObjectParameterStrategy s3, ObjectParameterInfo o3info) {
      return Foreign.invokeN6O3(ctx.contextAddress, function, n1, n2, n3, n4, n5, n6, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2), s3.object(o3), s3.objectInfo(o3info), s3.offset(o3), s3.length(o3));
   }

   public final long invokeN1(CallContext ctx, long function, long n1, int objCount, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info) {
      if (objCount == 0) {
         return Foreign.invokeN1(ctx.contextAddress, function, n1);
      } else if (objCount == 1) {
         return Foreign.invokeN1O1(ctx.contextAddress, function, n1, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1));
      } else {
         throw newObjectCountError(objCount);
      }
   }

   public final long invokeN2(CallContext ctx, long function, long n1, long n2, int objCount, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info) {
      if (objCount == 0) {
         return Foreign.invokeN2(ctx.contextAddress, function, n1, n2);
      } else if (objCount == 1) {
         return Foreign.invokeN2O1(ctx.contextAddress, function, n1, n2, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1));
      } else {
         throw newObjectCountError(objCount);
      }
   }

   public final long invokeN2(CallContext ctx, long function, long n1, long n2, int objCount, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info, Object o2, ObjectParameterStrategy s2, ObjectParameterInfo o2info) {
      if (objCount == 0) {
         return Foreign.invokeN2(ctx.contextAddress, function, n1, n2);
      } else if (objCount == 1) {
         if (s1.isDirect() && !s2.isDirect()) {
            o1 = o2;
            s1 = s2;
            o1info = o2info;
         }

         return Foreign.invokeN2O1(ctx.contextAddress, function, n1, n2, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1));
      } else if (objCount == 2) {
         return Foreign.invokeN2O2(ctx.contextAddress, function, n1, n2, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2));
      } else {
         throw newObjectCountError(objCount);
      }
   }

   public final long invokeN3(CallContext ctx, long function, long n1, long n2, long n3, int objCount, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info) {
      if (objCount == 0) {
         return Foreign.invokeN3(ctx.contextAddress, function, n1, n2, n3);
      } else if (objCount == 1) {
         if (s1.isDirect()) {
            throw newInsufficientObjectCountError(objCount);
         } else {
            return Foreign.invokeN3O1(ctx.contextAddress, function, n1, n2, n3, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1));
         }
      } else {
         throw newObjectCountError(objCount);
      }
   }

   public final long invokeN3(CallContext ctx, long function, long n1, long n2, long n3, int objCount, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info, Object o2, ObjectParameterStrategy s2, ObjectParameterInfo o2info) {
      if (objCount == 0) {
         return Foreign.invokeN3(ctx.contextAddress, function, n1, n2, n3);
      } else if (objCount == 1) {
         if (s1.isDirect() && !s2.isDirect()) {
            o1 = o2;
            s1 = s2;
            o1info = o2info;
         }

         return Foreign.invokeN3O1(ctx.contextAddress, function, n1, n2, n3, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1));
      } else if (objCount == 2) {
         return Foreign.invokeN3O2(ctx.contextAddress, function, n1, n2, n3, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2));
      } else {
         throw newObjectCountError(objCount);
      }
   }

   public final long invokeN3(CallContext ctx, long function, long n1, long n2, long n3, int objCount, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info, Object o2, ObjectParameterStrategy s2, ObjectParameterInfo o2info, Object o3, ObjectParameterStrategy s3, ObjectParameterInfo o3info) {
      if (objCount == 0) {
         return Foreign.invokeN3(ctx.contextAddress, function, n1, n2, n3);
      } else if (objCount < 3) {
         byte next;
         if (!s1.isDirect()) {
            next = 2;
         } else if (!s2.isDirect()) {
            o1 = o2;
            s1 = s2;
            o1info = o2info;
            next = 3;
         } else {
            o1 = o3;
            s1 = s3;
            o1info = o3info;
            next = 4;
         }

         if (objCount == 1) {
            return Foreign.invokeN3O1(ctx.contextAddress, function, n1, n2, n3, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1));
         } else if (objCount != 2) {
            throw newObjectCountError(objCount);
         } else {
            if ((next > 2 || s2.isDirect()) && next <= 3) {
               o2 = o3;
               s2 = s3;
               o2info = o3info;
            }

            return Foreign.invokeN3O2(ctx.contextAddress, function, n1, n2, n3, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2));
         }
      } else {
         return Foreign.invokeN3O3(ctx.contextAddress, function, n1, n2, n3, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2), s3.object(o3), s3.objectInfo(o3info), s3.offset(o3), s3.length(o3));
      }
   }

   public final long invokeN4(CallContext ctx, long function, long n1, long n2, long n3, long n4, int objCount, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info) {
      if (objCount == 0) {
         return Foreign.invokeN4(ctx.contextAddress, function, n1, n2, n3, n4);
      } else if (objCount == 1) {
         return Foreign.invokeN4O1(ctx.contextAddress, function, n1, n2, n3, n4, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1));
      } else {
         throw newObjectCountError(objCount);
      }
   }

   public final long invokeN4(CallContext ctx, long function, long n1, long n2, long n3, long n4, int objCount, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info, Object o2, ObjectParameterStrategy s2, ObjectParameterInfo o2info) {
      if (objCount == 0) {
         return Foreign.invokeN4(ctx.contextAddress, function, n1, n2, n3, n4);
      } else if (objCount == 1) {
         if (s1.isDirect() && !s2.isDirect()) {
            o1 = o2;
            s1 = s2;
            o1info = o2info;
         }

         return Foreign.invokeN4O1(ctx.contextAddress, function, n1, n2, n3, n4, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1));
      } else if (objCount == 2) {
         return Foreign.invokeN4O2(ctx.contextAddress, function, n1, n2, n3, n4, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2));
      } else {
         throw newObjectCountError(objCount);
      }
   }

   public final long invokeN4(CallContext ctx, long function, long n1, long n2, long n3, long n4, int objCount, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info, Object o2, ObjectParameterStrategy s2, ObjectParameterInfo o2info, Object o3, ObjectParameterStrategy s3, ObjectParameterInfo o3info) {
      if (objCount == 0) {
         return Foreign.invokeN4(ctx.contextAddress, function, n1, n2, n3, n4);
      } else {
         int next = 1;
         switch (next) {
            case 1:
               ++next;
               if (!s1.isDirect()) {
                  break;
               }
            case 2:
               ++next;
               if (!s2.isDirect()) {
                  o1 = o2;
                  s1 = s2;
                  o1info = o2info;
                  break;
               }
            case 3:
               ++next;
               if (!s3.isDirect()) {
                  o1 = o3;
                  s1 = s3;
                  o1info = o3info;
               }
         }

         if (objCount == 1) {
            return Foreign.invokeN4O1(ctx.contextAddress, function, n1, n2, n3, n4, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1));
         } else {
            switch (next) {
               case 2:
                  ++next;
                  if (!s2.isDirect()) {
                     break;
                  }
               case 3:
                  ++next;
                  if (!s3.isDirect()) {
                     o2 = o3;
                     s2 = s3;
                     o2info = o3info;
                  }
            }

            if (objCount == 2) {
               return Foreign.invokeN4O2(ctx.contextAddress, function, n1, n2, n3, n4, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2));
            } else {
               switch (next) {
                  case 3:
                     if (!s3.isDirect()) {
                        if (objCount == 3) {
                           return Foreign.invokeN4O3(ctx.contextAddress, function, n1, n2, n3, n4, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2), s3.object(o3), s3.objectInfo(o3info), s3.offset(o3), s3.length(o3));
                        }

                        throw newObjectCountError(objCount);
                     }
                  default:
                     throw newInsufficientObjectCountError(objCount);
               }
            }
         }
      }
   }

   public final long invokeN4(CallContext ctx, long function, long n1, long n2, long n3, long n4, int objCount, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info, Object o2, ObjectParameterStrategy s2, ObjectParameterInfo o2info, Object o3, ObjectParameterStrategy s3, ObjectParameterInfo o3info, Object o4, ObjectParameterStrategy s4, ObjectParameterInfo o4info) {
      if (objCount == 0) {
         return Foreign.invokeN4(ctx.contextAddress, function, n1, n2, n3, n4);
      } else {
         int next = 1;
         switch (next) {
            case 1:
               ++next;
               if (!s1.isDirect()) {
                  break;
               }
            case 2:
               ++next;
               if (!s2.isDirect()) {
                  o1 = o2;
                  s1 = s2;
                  o1info = o2info;
                  break;
               }
            case 3:
               ++next;
               if (!s3.isDirect()) {
                  o1 = o3;
                  s1 = s3;
                  o1info = o3info;
                  break;
               }
            case 4:
               ++next;
               if (!s4.isDirect()) {
                  o1 = o4;
                  s1 = s4;
                  o1info = o4info;
               }
         }

         if (objCount == 1) {
            return Foreign.invokeN4O1(ctx.contextAddress, function, n1, n2, n3, n4, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1));
         } else {
            switch (next) {
               case 2:
                  ++next;
                  if (!s2.isDirect()) {
                     break;
                  }
               case 3:
                  ++next;
                  if (!s3.isDirect()) {
                     o2 = o3;
                     s2 = s3;
                     o2info = o3info;
                     break;
                  }
               case 4:
                  ++next;
                  if (!s4.isDirect()) {
                     o2 = o4;
                     s2 = s4;
                     o2info = o4info;
                  }
            }

            if (objCount == 2) {
               return Foreign.invokeN4O2(ctx.contextAddress, function, n1, n2, n3, n4, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2));
            } else {
               switch (next) {
                  case 3:
                     ++next;
                     if (!s3.isDirect()) {
                        break;
                     }
                  case 4:
                     ++next;
                     if (!s4.isDirect()) {
                        o3 = o4;
                        s3 = s4;
                        o3info = o4info;
                     }
               }

               if (objCount == 3) {
                  return Foreign.invokeN4O3(ctx.contextAddress, function, n1, n2, n3, n4, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2), s3.object(o3), s3.objectInfo(o3info), s3.offset(o3), s3.length(o3));
               } else if (next == 4 && !s4.isDirect()) {
                  if (objCount == 4) {
                     return Foreign.invokeN4O4(ctx.contextAddress, function, n1, n2, n3, n4, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2), s3.object(o3), s3.objectInfo(o3info), s3.offset(o3), s3.length(o3), s4.object(o4), s4.objectInfo(o4info), s4.offset(o4), s4.length(o4));
                  } else {
                     throw newObjectCountError(objCount);
                  }
               } else {
                  throw newInsufficientObjectCountError(objCount);
               }
            }
         }
      }
   }

   public final long invokeN5(CallContext ctx, long function, long n1, long n2, long n3, long n4, long n5, int objCount, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info) {
      if (objCount == 0) {
         return Foreign.invokeN5(ctx.contextAddress, function, n1, n2, n3, n4, n5);
      } else if (objCount == 1) {
         return Foreign.invokeN5O1(ctx.contextAddress, function, n1, n2, n3, n4, n5, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1));
      } else {
         throw newObjectCountError(objCount);
      }
   }

   public final long invokeN5(CallContext ctx, long function, long n1, long n2, long n3, long n4, long n5, int objCount, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info, Object o2, ObjectParameterStrategy s2, ObjectParameterInfo o2info) {
      if (objCount == 0) {
         return Foreign.invokeN5(ctx.contextAddress, function, n1, n2, n3, n4, n5);
      } else if (objCount == 1) {
         if (s1.isDirect()) {
            o1 = o2;
            s1 = s2;
            o1info = o2info;
         }

         return Foreign.invokeN5O1(ctx.contextAddress, function, n1, n2, n3, n4, n5, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1));
      } else if (objCount == 2) {
         return Foreign.invokeN5O2(ctx.contextAddress, function, n1, n2, n3, n4, n5, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2));
      } else {
         throw newObjectCountError(objCount);
      }
   }

   public final long invokeN5(CallContext ctx, long function, long n1, long n2, long n3, long n4, long n5, int objCount, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info, Object o2, ObjectParameterStrategy s2, ObjectParameterInfo o2info, Object o3, ObjectParameterStrategy s3, ObjectParameterInfo o3info) {
      if (objCount == 0) {
         return Foreign.invokeN5(ctx.contextAddress, function, n1, n2, n3, n4, n5);
      } else {
         int next = 1;
         switch (next) {
            case 1:
               ++next;
               if (!s1.isDirect()) {
                  break;
               }
            case 2:
               ++next;
               if (!s2.isDirect()) {
                  o1 = o2;
                  s1 = s2;
                  o1info = o2info;
                  break;
               }
            case 3:
               ++next;
               if (!s3.isDirect()) {
                  o1 = o3;
                  s1 = s3;
                  o1info = o3info;
               }
         }

         if (objCount == 1) {
            return Foreign.invokeN5O1(ctx.contextAddress, function, n1, n2, n3, n4, n5, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1));
         } else {
            switch (next) {
               case 2:
                  ++next;
                  if (!s2.isDirect()) {
                     break;
                  }
               case 3:
                  ++next;
                  if (!s3.isDirect()) {
                     o2 = o3;
                     s2 = s3;
                     o2info = o3info;
                  }
            }

            if (objCount == 2) {
               return Foreign.invokeN5O2(ctx.contextAddress, function, n1, n2, n3, n4, n5, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2));
            } else {
               switch (next) {
                  case 3:
                     if (!s3.isDirect()) {
                     }
                  default:
                     if (objCount == 3) {
                        return Foreign.invokeN5O3(ctx.contextAddress, function, n1, n2, n3, n4, n5, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2), s3.object(o3), s3.objectInfo(o3info), s3.offset(o3), s3.length(o3));
                     } else {
                        throw newObjectCountError(objCount);
                     }
               }
            }
         }
      }
   }

   public final long invokeN5(CallContext ctx, long function, long n1, long n2, long n3, long n4, long n5, int objCount, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info, Object o2, ObjectParameterStrategy s2, ObjectParameterInfo o2info, Object o3, ObjectParameterStrategy s3, ObjectParameterInfo o3info, Object o4, ObjectParameterStrategy s4, ObjectParameterInfo o4info) {
      if (objCount == 0) {
         return Foreign.invokeN5(ctx.contextAddress, function, n1, n2, n3, n4, n5);
      } else {
         int next = 1;
         switch (next) {
            case 1:
               ++next;
               if (!s1.isDirect()) {
                  break;
               }
            case 2:
               ++next;
               if (!s2.isDirect()) {
                  o1 = o2;
                  s1 = s2;
                  o1info = o2info;
                  break;
               }
            case 3:
               ++next;
               if (!s3.isDirect()) {
                  o1 = o3;
                  s1 = s3;
                  o1info = o3info;
                  break;
               }
            case 4:
               ++next;
               if (!s4.isDirect()) {
                  o1 = o4;
                  s1 = s4;
                  o1info = o4info;
               }
         }

         if (objCount == 1) {
            return Foreign.invokeN5O1(ctx.contextAddress, function, n1, n2, n3, n4, n5, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1));
         } else {
            switch (next) {
               case 2:
                  ++next;
                  if (!s2.isDirect()) {
                     break;
                  }
               case 3:
                  ++next;
                  if (!s3.isDirect()) {
                     o2 = o3;
                     s2 = s3;
                     o2info = o3info;
                     break;
                  }
               case 4:
                  ++next;
                  if (!s4.isDirect()) {
                     o2 = o4;
                     s2 = s4;
                     o2info = o4info;
                  }
            }

            if (objCount == 2) {
               return Foreign.invokeN5O2(ctx.contextAddress, function, n1, n2, n3, n4, n5, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2));
            } else {
               switch (next) {
                  case 3:
                     if (!s3.isDirect()) {
                        break;
                     }
                  case 4:
                     if (!s4.isDirect()) {
                        o3 = o4;
                        s3 = s4;
                        o3info = o4info;
                     }
               }

               if (objCount == 3) {
                  return Foreign.invokeN5O3(ctx.contextAddress, function, n1, n2, n3, n4, n5, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2), s3.object(o3), s3.objectInfo(o3info), s3.offset(o3), s3.length(o3));
               } else if (objCount == 4) {
                  return Foreign.invokeN5O4(ctx.contextAddress, function, n1, n2, n3, n4, n5, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2), s3.object(o3), s3.objectInfo(o3info), s3.offset(o3), s3.length(o3), s4.object(o4), s4.objectInfo(o4info), s4.offset(o4), s4.length(o4));
               } else {
                  throw newObjectCountError(objCount);
               }
            }
         }
      }
   }

   public final long invokeN5(CallContext ctx, long function, long n1, long n2, long n3, long n4, long n5, int objCount, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info, Object o2, ObjectParameterStrategy s2, ObjectParameterInfo o2info, Object o3, ObjectParameterStrategy s3, ObjectParameterInfo o3info, Object o4, ObjectParameterStrategy s4, ObjectParameterInfo o4info, Object o5, ObjectParameterStrategy s5, ObjectParameterInfo o5info) {
      if (objCount == 0) {
         return Foreign.invokeN5(ctx.contextAddress, function, n1, n2, n3, n4, n5);
      } else {
         int next = 1;
         switch (next) {
            case 1:
               ++next;
               if (!s1.isDirect()) {
                  break;
               }
            case 2:
               ++next;
               if (!s2.isDirect()) {
                  o1 = o2;
                  s1 = s2;
                  o1info = o2info;
                  break;
               }
            case 3:
               ++next;
               if (!s3.isDirect()) {
                  o1 = o3;
                  s1 = s3;
                  o1info = o3info;
                  break;
               }
            case 4:
               ++next;
               if (!s4.isDirect()) {
                  o1 = o4;
                  s1 = s4;
                  o1info = o4info;
                  break;
               }
            case 5:
               ++next;
               if (!s5.isDirect()) {
                  o1 = o5;
                  s1 = s5;
                  o1info = o5info;
               }
         }

         if (objCount == 1) {
            return Foreign.invokeN5O1(ctx.contextAddress, function, n1, n2, n3, n4, n5, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1));
         } else {
            switch (next) {
               case 2:
                  ++next;
                  if (!s2.isDirect()) {
                     break;
                  }
               case 3:
                  ++next;
                  if (!s3.isDirect()) {
                     o2 = o3;
                     s2 = s3;
                     o2info = o3info;
                     break;
                  }
               case 4:
                  ++next;
                  if (!s4.isDirect()) {
                     o2 = o4;
                     s2 = s4;
                     o2info = o4info;
                     break;
                  }
               case 5:
                  ++next;
                  if (!s5.isDirect()) {
                     o2 = o5;
                     s2 = s5;
                     o2info = o5info;
                  }
            }

            if (objCount == 2) {
               return Foreign.invokeN5O2(ctx.contextAddress, function, n1, n2, n3, n4, n5, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2));
            } else {
               switch (next) {
                  case 3:
                     ++next;
                     if (!s3.isDirect()) {
                        break;
                     }
                  case 4:
                     ++next;
                     if (!s4.isDirect()) {
                        o3 = o4;
                        s3 = s4;
                        o3info = o4info;
                        break;
                     }
                  case 5:
                     ++next;
                     if (!s5.isDirect()) {
                        o3 = o5;
                        s3 = s5;
                        o3info = o5info;
                     }
               }

               if (objCount == 3) {
                  return Foreign.invokeN5O3(ctx.contextAddress, function, n1, n2, n3, n4, n5, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2), s3.object(o3), s3.objectInfo(o3info), s3.offset(o3), s3.length(o3));
               } else {
                  switch (next) {
                     case 4:
                        if (!s4.isDirect()) {
                           break;
                        }
                     case 5:
                        if (!s5.isDirect()) {
                           o4 = o5;
                           s4 = s5;
                           o4info = o5info;
                        }
                  }

                  if (objCount == 4) {
                     return Foreign.invokeN5O4(ctx.contextAddress, function, n1, n2, n3, n4, n5, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2), s3.object(o3), s3.objectInfo(o3info), s3.offset(o3), s3.length(o3), s4.object(o4), s4.objectInfo(o4info), s4.offset(o4), s4.length(o4));
                  } else if (objCount == 5) {
                     return Foreign.invokeN5O5(ctx.contextAddress, function, n1, n2, n3, n4, n5, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2), s3.object(o3), s3.objectInfo(o3info), s3.offset(o3), s3.length(o3), s4.object(o4), s4.objectInfo(o4info), s4.offset(o4), s4.length(o4), s5.object(o5), s5.objectInfo(o5info), s5.offset(o5), s5.length(o5));
                  } else {
                     throw newObjectCountError(objCount);
                  }
               }
            }
         }
      }
   }

   public final long invokeN6(CallContext ctx, long function, long n1, long n2, long n3, long n4, long n5, long n6, int objCount, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info) {
      if (objCount == 0) {
         return Foreign.invokeN6(ctx.contextAddress, function, n1, n2, n3, n4, n5, n6);
      } else if (objCount == 1) {
         return Foreign.invokeN6O1(ctx.contextAddress, function, n1, n2, n3, n4, n5, n6, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1));
      } else {
         throw newObjectCountError(objCount);
      }
   }

   public final long invokeN6(CallContext ctx, long function, long n1, long n2, long n3, long n4, long n5, long n6, int objCount, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info, Object o2, ObjectParameterStrategy s2, ObjectParameterInfo o2info) {
      if (objCount == 0) {
         return Foreign.invokeN6(ctx.contextAddress, function, n1, n2, n3, n4, n5, n6);
      } else if (objCount == 1) {
         if (s1.isDirect()) {
            o1 = o2;
            s1 = s2;
            o1info = o2info;
         }

         return Foreign.invokeN6O1(ctx.contextAddress, function, n1, n2, n3, n4, n5, n6, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1));
      } else if (objCount == 2) {
         return Foreign.invokeN6O2(ctx.contextAddress, function, n1, n2, n3, n4, n5, n6, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2));
      } else {
         throw newObjectCountError(objCount);
      }
   }

   public final long invokeN6(CallContext ctx, long function, long n1, long n2, long n3, long n4, long n5, long n6, int objCount, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info, Object o2, ObjectParameterStrategy s2, ObjectParameterInfo o2info, Object o3, ObjectParameterStrategy s3, ObjectParameterInfo o3info) {
      if (objCount == 0) {
         return Foreign.invokeN6(ctx.contextAddress, function, n1, n2, n3, n4, n5, n6);
      } else {
         int next = 1;
         switch (next) {
            case 1:
               ++next;
               if (!s1.isDirect()) {
                  break;
               }
            case 2:
               ++next;
               if (!s2.isDirect()) {
                  o1 = o2;
                  s1 = s2;
                  o1info = o2info;
                  break;
               }
            case 3:
               ++next;
               if (!s3.isDirect()) {
                  o1 = o3;
                  s1 = s3;
                  o1info = o3info;
               }
         }

         if (objCount == 1) {
            return Foreign.invokeN6O1(ctx.contextAddress, function, n1, n2, n3, n4, n5, n6, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1));
         } else {
            switch (next) {
               case 2:
                  ++next;
                  if (!s2.isDirect()) {
                     break;
                  }
               case 3:
                  ++next;
                  if (!s3.isDirect()) {
                     o2 = o3;
                     s2 = s3;
                     o2info = o3info;
                  }
            }

            if (objCount == 2) {
               return Foreign.invokeN6O2(ctx.contextAddress, function, n1, n2, n3, n4, n5, n6, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2));
            } else {
               switch (next) {
                  case 3:
                     if (!s3.isDirect()) {
                     }
                  default:
                     if (objCount == 3) {
                        return Foreign.invokeN6O3(ctx.contextAddress, function, n1, n2, n3, n4, n5, n6, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2), s3.object(o3), s3.objectInfo(o3info), s3.offset(o3), s3.length(o3));
                     } else {
                        throw newObjectCountError(objCount);
                     }
               }
            }
         }
      }
   }

   public final long invokeN6(CallContext ctx, long function, long n1, long n2, long n3, long n4, long n5, long n6, int objCount, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info, Object o2, ObjectParameterStrategy s2, ObjectParameterInfo o2info, Object o3, ObjectParameterStrategy s3, ObjectParameterInfo o3info, Object o4, ObjectParameterStrategy s4, ObjectParameterInfo o4info) {
      if (objCount == 0) {
         return Foreign.invokeN6(ctx.contextAddress, function, n1, n2, n3, n4, n5, n6);
      } else {
         int next = 1;
         switch (next) {
            case 1:
               ++next;
               if (!s1.isDirect()) {
                  break;
               }
            case 2:
               ++next;
               if (!s2.isDirect()) {
                  o1 = o2;
                  s1 = s2;
                  o1info = o2info;
                  break;
               }
            case 3:
               ++next;
               if (!s3.isDirect()) {
                  o1 = o3;
                  s1 = s3;
                  o1info = o3info;
                  break;
               }
            case 4:
               ++next;
               if (!s4.isDirect()) {
                  o1 = o4;
                  s1 = s4;
                  o1info = o4info;
               }
         }

         if (objCount == 1) {
            return Foreign.invokeN6O1(ctx.contextAddress, function, n1, n2, n3, n4, n5, n6, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1));
         } else {
            switch (next) {
               case 2:
                  ++next;
                  if (!s2.isDirect()) {
                     break;
                  }
               case 3:
                  ++next;
                  if (!s3.isDirect()) {
                     o2 = o3;
                     s2 = s3;
                     o2info = o3info;
                     break;
                  }
               case 4:
                  ++next;
                  if (!s4.isDirect()) {
                     o2 = o4;
                     s2 = s4;
                     o2info = o4info;
                  }
            }

            if (objCount == 2) {
               return Foreign.invokeN6O2(ctx.contextAddress, function, n1, n2, n3, n4, n5, n6, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2));
            } else {
               switch (next) {
                  case 3:
                     if (!s3.isDirect()) {
                        break;
                     }
                  case 4:
                     if (!s4.isDirect()) {
                        o3 = o4;
                        s3 = s4;
                        o3info = o4info;
                     }
               }

               if (objCount == 3) {
                  return Foreign.invokeN6O3(ctx.contextAddress, function, n1, n2, n3, n4, n5, n6, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2), s3.object(o3), s3.objectInfo(o3info), s3.offset(o3), s3.length(o3));
               } else if (objCount == 4) {
                  return Foreign.invokeN6O4(ctx.contextAddress, function, n1, n2, n3, n4, n5, n6, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2), s3.object(o3), s3.objectInfo(o3info), s3.offset(o3), s3.length(o3), s4.object(o4), s4.objectInfo(o4info), s4.offset(o4), s4.length(o4));
               } else {
                  throw newObjectCountError(objCount);
               }
            }
         }
      }
   }

   public final long invokeN6(CallContext ctx, long function, long n1, long n2, long n3, long n4, long n5, long n6, int objCount, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info, Object o2, ObjectParameterStrategy s2, ObjectParameterInfo o2info, Object o3, ObjectParameterStrategy s3, ObjectParameterInfo o3info, Object o4, ObjectParameterStrategy s4, ObjectParameterInfo o4info, Object o5, ObjectParameterStrategy s5, ObjectParameterInfo o5info) {
      if (objCount == 0) {
         return Foreign.invokeN6(ctx.contextAddress, function, n1, n2, n3, n4, n5, n6);
      } else {
         int next = 1;
         switch (next) {
            case 1:
               ++next;
               if (!s1.isDirect()) {
                  break;
               }
            case 2:
               ++next;
               if (!s2.isDirect()) {
                  o1 = o2;
                  s1 = s2;
                  o1info = o2info;
                  break;
               }
            case 3:
               ++next;
               if (!s3.isDirect()) {
                  o1 = o3;
                  s1 = s3;
                  o1info = o3info;
                  break;
               }
            case 4:
               ++next;
               if (!s4.isDirect()) {
                  o1 = o4;
                  s1 = s4;
                  o1info = o4info;
                  break;
               }
            case 5:
               ++next;
               if (!s5.isDirect()) {
                  o1 = o5;
                  s1 = s5;
                  o1info = o5info;
               }
         }

         if (objCount == 1) {
            return Foreign.invokeN6O1(ctx.contextAddress, function, n1, n2, n3, n4, n5, n6, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1));
         } else {
            switch (next) {
               case 2:
                  ++next;
                  if (!s2.isDirect()) {
                     break;
                  }
               case 3:
                  ++next;
                  if (!s3.isDirect()) {
                     o2 = o3;
                     s2 = s3;
                     o2info = o3info;
                     break;
                  }
               case 4:
                  ++next;
                  if (!s4.isDirect()) {
                     o2 = o4;
                     s2 = s4;
                     o2info = o4info;
                     break;
                  }
               case 5:
                  ++next;
                  if (!s5.isDirect()) {
                     o2 = o5;
                     s2 = s5;
                     o2info = o5info;
                  }
            }

            if (objCount == 2) {
               return Foreign.invokeN6O2(ctx.contextAddress, function, n1, n2, n3, n4, n5, n6, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2));
            } else {
               switch (next) {
                  case 3:
                     ++next;
                     if (!s3.isDirect()) {
                        break;
                     }
                  case 4:
                     ++next;
                     if (!s4.isDirect()) {
                        o3 = o4;
                        s3 = s4;
                        o3info = o4info;
                        break;
                     }
                  case 5:
                     ++next;
                     if (!s5.isDirect()) {
                        o3 = o5;
                        s3 = s5;
                        o3info = o5info;
                     }
               }

               if (objCount == 3) {
                  return Foreign.invokeN6O3(ctx.contextAddress, function, n1, n2, n3, n4, n5, n6, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2), s3.object(o3), s3.objectInfo(o3info), s3.offset(o3), s3.length(o3));
               } else {
                  switch (next) {
                     case 4:
                        if (!s4.isDirect()) {
                           break;
                        }
                     case 5:
                        if (!s5.isDirect()) {
                           o4 = o5;
                           s4 = s5;
                           o4info = o5info;
                        }
                  }

                  if (objCount == 4) {
                     return Foreign.invokeN6O4(ctx.contextAddress, function, n1, n2, n3, n4, n5, n6, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2), s3.object(o3), s3.objectInfo(o3info), s3.offset(o3), s3.length(o3), s4.object(o4), s4.objectInfo(o4info), s4.offset(o4), s4.length(o4));
                  } else if (objCount == 5) {
                     return Foreign.invokeN6O5(ctx.contextAddress, function, n1, n2, n3, n4, n5, n6, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2), s3.object(o3), s3.objectInfo(o3info), s3.offset(o3), s3.length(o3), s4.object(o4), s4.objectInfo(o4info), s4.offset(o4), s4.length(o4), s5.object(o5), s5.objectInfo(o5info), s5.offset(o5), s5.length(o5));
                  } else {
                     throw newObjectCountError(objCount);
                  }
               }
            }
         }
      }
   }

   public final long invokeN6(CallContext ctx, long function, long n1, long n2, long n3, long n4, long n5, long n6, int objCount, Object o1, ObjectParameterStrategy s1, ObjectParameterInfo o1info, Object o2, ObjectParameterStrategy s2, ObjectParameterInfo o2info, Object o3, ObjectParameterStrategy s3, ObjectParameterInfo o3info, Object o4, ObjectParameterStrategy s4, ObjectParameterInfo o4info, Object o5, ObjectParameterStrategy s5, ObjectParameterInfo o5info, Object o6, ObjectParameterStrategy s6, ObjectParameterInfo o6info) {
      if (objCount == 0) {
         return Foreign.invokeN6(ctx.contextAddress, function, n1, n2, n3, n4, n5, n6);
      } else {
         int next = 1;
         switch (next) {
            case 1:
               ++next;
               if (!s1.isDirect()) {
                  break;
               }
            case 2:
               ++next;
               if (!s2.isDirect()) {
                  o1 = o2;
                  s1 = s2;
                  o1info = o2info;
                  break;
               }
            case 3:
               ++next;
               if (!s3.isDirect()) {
                  o1 = o3;
                  s1 = s3;
                  o1info = o3info;
                  break;
               }
            case 4:
               ++next;
               if (!s4.isDirect()) {
                  o1 = o4;
                  s1 = s4;
                  o1info = o4info;
                  break;
               }
            case 5:
               ++next;
               if (!s5.isDirect()) {
                  o1 = o5;
                  s1 = s5;
                  o1info = o5info;
                  break;
               }
            case 6:
               ++next;
               if (!s6.isDirect()) {
                  o1 = o6;
                  s1 = s6;
                  o1info = o6info;
               }
         }

         if (objCount == 1) {
            return Foreign.invokeN6O1(ctx.contextAddress, function, n1, n2, n3, n4, n5, n6, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1));
         } else {
            switch (next) {
               case 2:
                  ++next;
                  if (!s2.isDirect()) {
                     break;
                  }
               case 3:
                  ++next;
                  if (!s3.isDirect()) {
                     o2 = o3;
                     s2 = s3;
                     o2info = o3info;
                     break;
                  }
               case 4:
                  ++next;
                  if (!s4.isDirect()) {
                     o2 = o4;
                     s2 = s4;
                     o2info = o4info;
                     break;
                  }
               case 5:
                  ++next;
                  if (!s5.isDirect()) {
                     o2 = o5;
                     s2 = s5;
                     o2info = o5info;
                     break;
                  }
               case 6:
                  ++next;
                  if (!s6.isDirect()) {
                     o2 = o6;
                     s2 = s6;
                     o2info = o6info;
                  }
            }

            if (objCount == 2) {
               return Foreign.invokeN6O2(ctx.contextAddress, function, n1, n2, n3, n4, n5, n6, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2));
            } else {
               switch (next) {
                  case 3:
                     ++next;
                     if (!s3.isDirect()) {
                        break;
                     }
                  case 4:
                     ++next;
                     if (!s4.isDirect()) {
                        o3 = o4;
                        s3 = s4;
                        o3info = o4info;
                        break;
                     }
                  case 5:
                     ++next;
                     if (!s5.isDirect()) {
                        o3 = o5;
                        s3 = s5;
                        o3info = o5info;
                        break;
                     }
                  case 6:
                     ++next;
                     if (!s6.isDirect()) {
                        o3 = o6;
                        s3 = s6;
                        o3info = o6info;
                     }
               }

               if (objCount == 3) {
                  return Foreign.invokeN6O3(ctx.contextAddress, function, n1, n2, n3, n4, n5, n6, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2), s3.object(o3), s3.objectInfo(o3info), s3.offset(o3), s3.length(o3));
               } else {
                  switch (next) {
                     case 4:
                        ++next;
                        if (!s4.isDirect()) {
                           break;
                        }
                     case 5:
                        ++next;
                        if (!s5.isDirect()) {
                           o4 = o5;
                           s4 = s5;
                           o4info = o5info;
                           break;
                        }
                     case 6:
                        ++next;
                        if (!s6.isDirect()) {
                           o4 = o6;
                           s4 = s6;
                           o4info = o6info;
                        }
                  }

                  if (objCount == 4) {
                     return Foreign.invokeN6O4(ctx.contextAddress, function, n1, n2, n3, n4, n5, n6, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2), s3.object(o3), s3.objectInfo(o3info), s3.offset(o3), s3.length(o3), s4.object(o4), s4.objectInfo(o4info), s4.offset(o4), s4.length(o4));
                  } else {
                     switch (next) {
                        case 5:
                           if (!s5.isDirect()) {
                              break;
                           }
                        case 6:
                           if (!s6.isDirect()) {
                              o5 = o6;
                              s5 = s6;
                              o5info = o6info;
                           }
                     }

                     if (objCount == 5) {
                        return Foreign.invokeN6O5(ctx.contextAddress, function, n1, n2, n3, n4, n5, n6, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2), s3.object(o3), s3.objectInfo(o3info), s3.offset(o3), s3.length(o3), s4.object(o4), s4.objectInfo(o4info), s4.offset(o4), s4.length(o4), s5.object(o5), s5.objectInfo(o5info), s5.offset(o5), s5.length(o5));
                     } else if (objCount == 6) {
                        return Foreign.invokeN6O6(ctx.contextAddress, function, n1, n2, n3, n4, n5, n6, s1.object(o1), s1.objectInfo(o1info), s1.offset(o1), s1.length(o1), s2.object(o2), s2.objectInfo(o2info), s2.offset(o2), s2.length(o2), s3.object(o3), s3.objectInfo(o3info), s3.offset(o3), s3.length(o3), s4.object(o4), s4.objectInfo(o4info), s4.offset(o4), s4.length(o4), s5.object(o5), s5.objectInfo(o5info), s5.offset(o5), s5.length(o5), s6.object(o6), s6.objectInfo(o6info), s6.offset(o6), s6.length(o6));
                     } else {
                        throw newObjectCountError(objCount);
                     }
                  }
               }
            }
         }
      }
   }

   public long invokeAddress(Function function, HeapInvocationBuffer buffer) {
      return this.invokeAddress(function.getCallContext(), function.getFunctionAddress(), buffer);
   }

   public abstract long invokeAddress(CallContext var1, long var2, HeapInvocationBuffer var4);

   public final int invokeInt(Function function, HeapInvocationBuffer buffer) {
      return this.invokeInt(function.getCallContext(), function.getFunctionAddress(), buffer);
   }

   public final int invokeInt(CallContext ctx, long function, HeapInvocationBuffer buffer) {
      ObjectBuffer objectBuffer = buffer.objectBuffer();
      return objectBuffer != null ? this.invokeArrayWithObjectsInt32(ctx.contextAddress, function, buffer, objectBuffer) : Foreign.invokeArrayReturnInt(ctx.contextAddress, function, buffer.array());
   }

   public final long invokeLong(Function function, HeapInvocationBuffer buffer) {
      return this.invokeLong(function.getCallContext(), function.getFunctionAddress(), buffer);
   }

   public final long invokeLong(CallContext ctx, long function, HeapInvocationBuffer buffer) {
      ObjectBuffer objectBuffer = buffer.objectBuffer();
      return objectBuffer != null ? this.invokeArrayWithObjectsInt64(ctx.contextAddress, function, buffer, objectBuffer) : Foreign.invokeArrayReturnLong(ctx.contextAddress, function, buffer.array());
   }

   public final float invokeFloat(Function function, HeapInvocationBuffer buffer) {
      return this.invokeFloat(function.getCallContext(), function.getFunctionAddress(), buffer);
   }

   public final float invokeFloat(CallContext ctx, long function, HeapInvocationBuffer buffer) {
      ObjectBuffer objectBuffer = buffer.objectBuffer();
      return objectBuffer != null ? Foreign.invokeArrayWithObjectsFloat(ctx.contextAddress, function, buffer.array(), objectBuffer.objectCount(), objectBuffer.info(), objectBuffer.objects()) : Foreign.invokeArrayReturnFloat(ctx.contextAddress, function, buffer.array());
   }

   public final double invokeDouble(Function function, HeapInvocationBuffer buffer) {
      return this.invokeDouble(function.getCallContext(), function.getFunctionAddress(), buffer);
   }

   public final double invokeDouble(CallContext ctx, long function, HeapInvocationBuffer buffer) {
      ObjectBuffer objectBuffer = buffer.objectBuffer();
      return objectBuffer != null ? Foreign.invokeArrayWithObjectsDouble(ctx.contextAddress, function, buffer.array(), objectBuffer.objectCount(), objectBuffer.info(), objectBuffer.objects()) : Foreign.invokeArrayReturnDouble(ctx.contextAddress, function, buffer.array());
   }

   public final BigDecimal invokeBigDecimal(Function function, HeapInvocationBuffer buffer) {
      return this.invokeBigDecimal(function.getCallContext(), function.getFunctionAddress(), buffer);
   }

   public final BigDecimal invokeBigDecimal(CallContext ctx, long function, HeapInvocationBuffer buffer) {
      byte[] rval = this.invokeStruct(ctx, function, buffer);
      return new BigDecimal(this.foreign.longDoubleToString(rval, 0, rval.length));
   }

   public final byte[] invokeStruct(Function function, HeapInvocationBuffer buffer) {
      return this.invokeStruct(function.getCallContext(), function.getFunctionAddress(), buffer);
   }

   public final byte[] invokeStruct(CallContext ctx, long function, HeapInvocationBuffer buffer) {
      byte[] returnBuffer = new byte[ctx.getReturnType().size()];
      this.invokeStruct(ctx, function, buffer, returnBuffer, 0);
      return returnBuffer;
   }

   public final void invokeStruct(Function function, HeapInvocationBuffer buffer, byte[] returnBuffer, int offset) {
      this.invokeStruct(function.getCallContext(), function.getFunctionAddress(), buffer, returnBuffer, offset);
   }

   public final void invokeStruct(CallContext ctx, long function, HeapInvocationBuffer buffer, byte[] returnBuffer, int offset) {
      ObjectBuffer objectBuffer = buffer.objectBuffer();
      if (objectBuffer != null) {
         Foreign.invokeArrayWithObjectsReturnStruct(ctx.contextAddress, function, buffer.array(), objectBuffer.objectCount(), objectBuffer.info(), objectBuffer.objects(), returnBuffer, offset);
      } else {
         Foreign.invokeArrayReturnStruct(ctx.contextAddress, function, buffer.array(), returnBuffer, offset);
      }

   }

   public final Object invokeObject(Function function, HeapInvocationBuffer buffer) {
      ObjectBuffer objectBuffer = buffer.objectBuffer();
      return Foreign.invokeArrayWithObjectsReturnObject(function.contextAddress, function.functionAddress, buffer.array(), objectBuffer.objectCount(), objectBuffer.info(), objectBuffer.objects());
   }

   public final void invoke(Function function, long returnBuffer, long[] parameters) {
      Foreign.invokePointerParameterArray(function.contextAddress, function.functionAddress, returnBuffer, parameters);
   }

   public final void invoke(CallContext ctx, long function, long returnBuffer, long[] parameters) {
      Foreign.invokePointerParameterArray(ctx.contextAddress, function, returnBuffer, parameters);
   }

   private int invokeArrayWithObjectsInt32(long ctx, long function, HeapInvocationBuffer buffer, ObjectBuffer objectBuffer) {
      Object[] objects = objectBuffer.objects();
      int[] info = objectBuffer.info();
      int objectCount = objectBuffer.objectCount();
      switch (objectCount) {
         case 1:
            return Foreign.invokeArrayO1Int32(ctx, function, buffer.array(), objects[0], info[0], info[1], info[2]);
         case 2:
            return Foreign.invokeArrayO2Int32(ctx, function, buffer.array(), objects[0], info[0], info[1], info[2], objects[1], info[3], info[4], info[5]);
         default:
            return Foreign.invokeArrayWithObjectsInt32(ctx, function, buffer.array(), objectCount, info, objects);
      }
   }

   private long invokeArrayWithObjectsInt64(long ctx, long function, HeapInvocationBuffer buffer, ObjectBuffer objectBuffer) {
      Object[] objects = objectBuffer.objects();
      int[] info = objectBuffer.info();
      int objectCount = objectBuffer.objectCount();
      switch (objectCount) {
         case 1:
            return Foreign.invokeArrayO1Int64(ctx, function, buffer.array(), objects[0], info[0], info[1], info[2]);
         case 2:
            return Foreign.invokeArrayO2Int64(ctx, function, buffer.array(), objects[0], info[0], info[1], info[2], objects[1], info[3], info[4], info[5]);
         default:
            return Foreign.invokeArrayWithObjectsInt64(ctx, function, buffer.array(), objectCount, info, objects);
      }
   }

   // $FF: synthetic method
   Invoker(Object x0) {
      this();
   }

   private static final class LP64 extends Invoker {
      private static final Invoker INSTANCE = new LP64();

      private LP64() {
         super(null);
      }

      public final long invokeAddress(CallContext ctx, long function, HeapInvocationBuffer buffer) {
         return this.invokeLong(ctx, function, buffer);
      }
   }

   private static final class ILP32 extends Invoker {
      private static final Invoker INSTANCE = new ILP32();
      private static final long ADDRESS_MASK = 4294967295L;

      private ILP32() {
         super(null);
      }

      public final long invokeAddress(CallContext ctx, long function, HeapInvocationBuffer buffer) {
         return (long)this.invokeInt(ctx, function, buffer) & 4294967295L;
      }
   }

   private static final class SingletonHolder {
      private static final Invoker INSTANCE;

      static {
         INSTANCE = Platform.getPlatform().addressSize() == 64 ? Invoker.LP64.INSTANCE : Invoker.ILP32.INSTANCE;
      }
   }
}
