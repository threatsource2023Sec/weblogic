package com.bea.core.repackaged.jdt.internal.compiler.parser;

import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Annotation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Expression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.MarkerAnnotation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.MemberValuePair;
import com.bea.core.repackaged.jdt.internal.compiler.ast.NormalAnnotation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.SingleMemberAnnotation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.SingleNameReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeReference;

public class RecoveredAnnotation extends RecoveredElement {
   public static final int MARKER = 0;
   public static final int NORMAL = 1;
   public static final int SINGLE_MEMBER = 2;
   private int kind = 0;
   private int identifierPtr;
   private int identifierLengthPtr;
   private int sourceStart;
   public boolean hasPendingMemberValueName;
   public int memberValuPairEqualEnd = -1;
   public Annotation annotation;

   public RecoveredAnnotation(int identifierPtr, int identifierLengthPtr, int sourceStart, RecoveredElement parent, int bracketBalance) {
      super(parent, bracketBalance);
      this.identifierPtr = identifierPtr;
      this.identifierLengthPtr = identifierLengthPtr;
      this.sourceStart = sourceStart;
   }

   public RecoveredElement add(TypeDeclaration typeDeclaration, int bracketBalanceValue) {
      return (RecoveredElement)(this.annotation == null && (typeDeclaration.bits & 512) != 0 ? this : super.add(typeDeclaration, bracketBalanceValue));
   }

   public RecoveredElement addAnnotationName(int identPtr, int identLengthPtr, int annotationStart, int bracketBalanceValue) {
      RecoveredAnnotation element = new RecoveredAnnotation(identPtr, identLengthPtr, annotationStart, this, bracketBalanceValue);
      return element;
   }

   public RecoveredElement addAnnotation(Annotation annot, int index) {
      this.annotation = annot;
      return (RecoveredElement)(this.parent != null ? this.parent : this);
   }

   public void updateFromParserState() {
      Parser parser = this.parser();
      if (this.annotation == null && this.identifierPtr <= parser.identifierPtr) {
         Annotation annot = null;
         boolean needUpdateRParenPos = false;
         MemberValuePair pendingMemberValueName = null;
         int argStart;
         int annotationEnd;
         if (this.hasPendingMemberValueName && this.identifierPtr < parser.identifierPtr) {
            char[] memberValueName = parser.identifierStack[this.identifierPtr + 1];
            long pos = parser.identifierPositionStack[this.identifierPtr + 1];
            argStart = (int)(pos >>> 32);
            annotationEnd = (int)pos;
            int valueEnd = this.memberValuPairEqualEnd > -1 ? this.memberValuPairEqualEnd : annotationEnd;
            SingleNameReference fakeExpression = new SingleNameReference(RecoveryScanner.FAKE_IDENTIFIER, ((long)valueEnd + 1L << 32) + (long)valueEnd);
            pendingMemberValueName = new MemberValuePair(memberValueName, argStart, annotationEnd, fakeExpression);
         }

         parser.identifierPtr = this.identifierPtr;
         parser.identifierLengthPtr = this.identifierLengthPtr;
         TypeReference typeReference = parser.getAnnotationType();
         Expression memberValue;
         switch (this.kind) {
            case 1:
               if (parser.astPtr > -1 && parser.astStack[parser.astPtr] instanceof MemberValuePair) {
                  memberValue = null;
                  int argLength = parser.astLengthStack[parser.astLengthPtr];
                  argStart = parser.astPtr - argLength + 1;
                  if (argLength > 0) {
                     MemberValuePair[] memberValuePairs;
                     if (pendingMemberValueName != null) {
                        memberValuePairs = new MemberValuePair[argLength + 1];
                        System.arraycopy(parser.astStack, argStart, memberValuePairs, 0, argLength);
                        --parser.astLengthPtr;
                        parser.astPtr -= argLength;
                        memberValuePairs[argLength] = pendingMemberValueName;
                        annotationEnd = pendingMemberValueName.sourceEnd;
                     } else {
                        memberValuePairs = new MemberValuePair[argLength];
                        System.arraycopy(parser.astStack, argStart, memberValuePairs, 0, argLength);
                        --parser.astLengthPtr;
                        parser.astPtr -= argLength;
                        MemberValuePair lastMemberValuePair = memberValuePairs[memberValuePairs.length - 1];
                        annotationEnd = lastMemberValuePair.value != null ? (lastMemberValuePair.value instanceof Annotation ? ((Annotation)lastMemberValuePair.value).declarationSourceEnd : lastMemberValuePair.value.sourceEnd) : lastMemberValuePair.sourceEnd;
                     }

                     NormalAnnotation normalAnnotation = new NormalAnnotation(typeReference, this.sourceStart);
                     normalAnnotation.memberValuePairs = memberValuePairs;
                     normalAnnotation.declarationSourceEnd = annotationEnd;
                     normalAnnotation.bits |= 32;
                     annot = normalAnnotation;
                     needUpdateRParenPos = true;
                  }
               }
               break;
            case 2:
               if (parser.expressionPtr > -1) {
                  memberValue = parser.expressionStack[parser.expressionPtr--];
                  SingleMemberAnnotation singleMemberAnnotation = new SingleMemberAnnotation(typeReference, this.sourceStart);
                  singleMemberAnnotation.memberValue = memberValue;
                  singleMemberAnnotation.declarationSourceEnd = memberValue.sourceEnd;
                  singleMemberAnnotation.bits |= 32;
                  annot = singleMemberAnnotation;
                  needUpdateRParenPos = true;
               }
         }

         if (!needUpdateRParenPos) {
            if (pendingMemberValueName != null) {
               NormalAnnotation normalAnnotation = new NormalAnnotation(typeReference, this.sourceStart);
               normalAnnotation.memberValuePairs = new MemberValuePair[]{pendingMemberValueName};
               normalAnnotation.declarationSourceEnd = pendingMemberValueName.value.sourceEnd;
               normalAnnotation.bits |= 32;
               annot = normalAnnotation;
            } else {
               MarkerAnnotation markerAnnotation = new MarkerAnnotation(typeReference, this.sourceStart);
               markerAnnotation.declarationSourceEnd = markerAnnotation.sourceEnd;
               markerAnnotation.bits |= 32;
               annot = markerAnnotation;
            }
         }

         parser.currentElement = this.addAnnotation((Annotation)annot, this.identifierPtr);
         parser.annotationRecoveryCheckPoint(((Annotation)annot).sourceStart, ((Annotation)annot).declarationSourceEnd);
         if (this.parent != null) {
            this.parent.updateFromParserState();
         }
      }

   }

   public ASTNode parseTree() {
      return this.annotation;
   }

   public void resetPendingModifiers() {
      if (this.parent != null) {
         this.parent.resetPendingModifiers();
      }

   }

   public void setKind(int kind) {
      this.kind = kind;
   }

   public int sourceEnd() {
      if (this.annotation == null) {
         Parser parser = this.parser();
         return this.identifierPtr < parser.identifierPositionStack.length ? (int)parser.identifierPositionStack[this.identifierPtr] : this.sourceStart;
      } else {
         return this.annotation.declarationSourceEnd;
      }
   }

   public String toString(int tab) {
      return this.annotation != null ? this.tabString(tab) + "Recovered annotation:\n" + this.annotation.print(tab + 1, new StringBuffer(10)) : this.tabString(tab) + "Recovered annotation: identiferPtr=" + this.identifierPtr + " identiferlengthPtr=" + this.identifierLengthPtr + "\n";
   }

   public Annotation updatedAnnotationReference() {
      return this.annotation;
   }

   public RecoveredElement updateOnClosingBrace(int braceStart, int braceEnd) {
      if (this.bracketBalance > 0) {
         --this.bracketBalance;
         return this;
      } else {
         return (RecoveredElement)(this.parent != null ? this.parent.updateOnClosingBrace(braceStart, braceEnd) : this);
      }
   }

   public void updateParseTree() {
      this.updatedAnnotationReference();
   }
}
