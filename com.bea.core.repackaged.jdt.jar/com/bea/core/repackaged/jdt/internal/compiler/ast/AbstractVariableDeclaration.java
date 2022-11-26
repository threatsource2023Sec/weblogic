package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.InferenceContext18;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.InvocationSite;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public abstract class AbstractVariableDeclaration extends Statement implements InvocationSite {
   public int declarationEnd;
   public int declarationSourceEnd;
   public int declarationSourceStart;
   public int hiddenVariableDepth;
   public Expression initialization;
   public int modifiers;
   public int modifiersSourceStart;
   public Annotation[] annotations;
   public char[] name;
   public TypeReference type;
   public static final int FIELD = 1;
   public static final int INITIALIZER = 2;
   public static final int ENUM_CONSTANT = 3;
   public static final int LOCAL_VARIABLE = 4;
   public static final int PARAMETER = 5;
   public static final int TYPE_PARAMETER = 6;

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      return flowInfo;
   }

   public TypeBinding[] genericTypeArguments() {
      return null;
   }

   public abstract int getKind();

   public InferenceContext18 freshInferenceContext(Scope scope) {
      return null;
   }

   public boolean isSuperAccess() {
      return false;
   }

   public boolean isTypeAccess() {
      return false;
   }

   public StringBuffer printStatement(int indent, StringBuffer output) {
      this.printAsExpression(indent, output);
      switch (this.getKind()) {
         case 3:
            return output.append(',');
         default:
            return output.append(';');
      }
   }

   public StringBuffer printAsExpression(int indent, StringBuffer output) {
      printIndent(indent, output);
      printModifiers(this.modifiers, output);
      if (this.annotations != null) {
         printAnnotations(this.annotations, output);
         output.append(' ');
      }

      if (this.type != null) {
         this.type.print(0, output).append(' ');
      }

      output.append(this.name);
      switch (this.getKind()) {
         case 3:
            if (this.initialization != null) {
               this.initialization.printExpression(indent, output);
            }
            break;
         default:
            if (this.initialization != null) {
               output.append(" = ");
               this.initialization.printExpression(indent, output);
            }
      }

      return output;
   }

   public void resolve(BlockScope scope) {
   }

   public void setActualReceiverType(ReferenceBinding receiverType) {
   }

   public void setDepth(int depth) {
      this.hiddenVariableDepth = depth;
   }

   public void setFieldIndex(int depth) {
   }
}
