package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.lookup.InferenceContext18;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.InvocationSite;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ParameterizedGenericMethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ParameterizedMethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public interface Invocation extends InvocationSite {
   Expression[] arguments();

   MethodBinding binding();

   void registerInferenceContext(ParameterizedGenericMethodBinding var1, InferenceContext18 var2);

   InferenceContext18 getInferenceContext(ParameterizedMethodBinding var1);

   void cleanUpInferenceContexts();

   void registerResult(TypeBinding var1, MethodBinding var2);
}
