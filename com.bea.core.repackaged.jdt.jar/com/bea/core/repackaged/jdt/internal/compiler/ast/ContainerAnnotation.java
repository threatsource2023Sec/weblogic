package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeConstants;

public class ContainerAnnotation extends SingleMemberAnnotation {
   private Annotation[] containees;
   private ArrayInitializer memberValues;

   public ContainerAnnotation(Annotation repeatingAnnotation, ReferenceBinding containerAnnotationType, BlockScope scope) {
      char[][] containerTypeName = containerAnnotationType.compoundName;
      if (containerTypeName.length == 1) {
         this.type = new SingleTypeReference(containerTypeName[0], 0L);
      } else {
         this.type = new QualifiedTypeReference(containerTypeName, new long[containerTypeName.length]);
      }

      this.sourceStart = repeatingAnnotation.sourceStart;
      this.sourceEnd = repeatingAnnotation.sourceEnd;
      this.resolvedType = containerAnnotationType;
      this.recipient = repeatingAnnotation.recipient;
      this.containees = new Annotation[0];
      this.memberValue = this.memberValues = new ArrayInitializer();
      this.addContainee(repeatingAnnotation);
   }

   public void addContainee(Annotation repeatingAnnotation) {
      int length = this.containees.length;
      System.arraycopy(this.containees, 0, this.containees = new Annotation[length + 1], 0, length);
      this.containees[length] = repeatingAnnotation;
      this.memberValues.expressions = this.containees;
      repeatingAnnotation.setPersistibleAnnotation(length == 0 ? this : null);
   }

   public TypeBinding resolveType(BlockScope scope) {
      if (this.compilerAnnotation != null) {
         return this.resolvedType;
      } else {
         this.constant = Constant.NotAConstant;
         ReferenceBinding containerAnnotationType = (ReferenceBinding)this.resolvedType;
         if (!containerAnnotationType.isValidBinding()) {
            containerAnnotationType = (ReferenceBinding)containerAnnotationType.closestMatch();
         }

         Annotation repeatingAnnotation = this.containees[0];
         ReferenceBinding repeatingAnnotationType = (ReferenceBinding)repeatingAnnotation.resolvedType;
         if (!repeatingAnnotationType.isDeprecated() && this.isTypeUseDeprecated(containerAnnotationType, scope)) {
            scope.problemReporter().deprecatedType(containerAnnotationType, repeatingAnnotation);
         }

         checkContainerAnnotationType(repeatingAnnotation, scope, containerAnnotationType, repeatingAnnotationType, true);
         this.resolvedType = containerAnnotationType = repeatingAnnotationType.containerAnnotationType();
         if (!this.resolvedType.isValidBinding()) {
            return this.resolvedType;
         } else {
            MethodBinding[] methods = containerAnnotationType.methods();
            MemberValuePair pair = this.memberValuePairs()[0];
            int i = 0;

            for(int length = methods.length; i < length; ++i) {
               MethodBinding method = methods[i];
               if (CharOperation.equals(method.selector, TypeConstants.VALUE)) {
                  pair.binding = method;
                  pair.resolveTypeExpecting(scope, method.returnType);
               }
            }

            this.compilerAnnotation = scope.environment().createAnnotation((ReferenceBinding)this.resolvedType, this.computeElementValuePairs());
            return this.resolvedType;
         }
      }
   }
}
