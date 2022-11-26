package com.bea.core.repackaged.jdt.internal.compiler.parser;

import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Annotation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Block;
import com.bea.core.repackaged.jdt.internal.compiler.ast.FieldDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Initializer;
import com.bea.core.repackaged.jdt.internal.compiler.ast.QualifiedAllocationExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Statement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeParameter;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeReference;
import java.util.HashSet;
import java.util.Set;

public class RecoveredType extends RecoveredStatement implements TerminalTokens {
   public static final int MAX_TYPE_DEPTH = 256;
   public TypeDeclaration typeDeclaration;
   public RecoveredAnnotation[] annotations;
   public int annotationCount;
   public int modifiers;
   public int modifiersStart;
   public RecoveredType[] memberTypes;
   public int memberTypeCount;
   public RecoveredField[] fields;
   public int fieldCount;
   public RecoveredMethod[] methods;
   public int methodCount;
   public boolean preserveContent = false;
   public int bodyEnd;
   public boolean insideEnumConstantPart = false;
   public TypeParameter[] pendingTypeParameters;
   public int pendingTypeParametersStart;
   int pendingModifiers;
   int pendingModifersSourceStart = -1;
   RecoveredAnnotation[] pendingAnnotations;
   int pendingAnnotationCount;

   public RecoveredType(TypeDeclaration typeDeclaration, RecoveredElement parent, int bracketBalance) {
      super(typeDeclaration, parent, bracketBalance);
      this.typeDeclaration = typeDeclaration;
      if (typeDeclaration.allocation != null && typeDeclaration.allocation.type == null) {
         this.foundOpeningBrace = true;
      } else {
         this.foundOpeningBrace = !this.bodyStartsAtHeaderEnd();
      }

      this.insideEnumConstantPart = TypeDeclaration.kind(typeDeclaration.modifiers) == 3;
      if (this.foundOpeningBrace) {
         ++this.bracketBalance;
      }

      this.preserveContent = this.parser().methodRecoveryActivated || this.parser().statementRecoveryActivated;
   }

   public RecoveredElement add(AbstractMethodDeclaration methodDeclaration, int bracketBalanceValue) {
      if (this.typeDeclaration.declarationSourceEnd != 0 && methodDeclaration.declarationSourceStart > this.typeDeclaration.declarationSourceEnd) {
         this.pendingTypeParameters = null;
         this.resetPendingModifiers();
         return this.parent.add(methodDeclaration, bracketBalanceValue);
      } else {
         if (this.methods == null) {
            this.methods = new RecoveredMethod[5];
            this.methodCount = 0;
         } else if (this.methodCount == this.methods.length) {
            System.arraycopy(this.methods, 0, this.methods = new RecoveredMethod[2 * this.methodCount], 0, this.methodCount);
         }

         RecoveredMethod element = new RecoveredMethod(methodDeclaration, this, bracketBalanceValue, this.recoveringParser);
         this.methods[this.methodCount++] = element;
         if (this.pendingTypeParameters != null) {
            element.attach(this.pendingTypeParameters, this.pendingTypeParametersStart);
            this.pendingTypeParameters = null;
         }

         if (this.pendingAnnotationCount > 0 || this.pendingModifiers != 0) {
            element.attach(this.pendingAnnotations, this.pendingAnnotationCount, this.pendingModifiers, this.pendingModifersSourceStart);
         }

         this.resetPendingModifiers();
         this.insideEnumConstantPart = false;
         if (!this.foundOpeningBrace) {
            this.foundOpeningBrace = true;
            ++this.bracketBalance;
         }

         return (RecoveredElement)(methodDeclaration.declarationSourceEnd == 0 ? element : this);
      }
   }

   public RecoveredElement add(Block nestedBlockDeclaration, int bracketBalanceValue) {
      this.pendingTypeParameters = null;
      this.resetPendingModifiers();
      int mods = 0;
      if (this.parser().recoveredStaticInitializerStart != 0) {
         mods = 8;
      }

      return this.add((FieldDeclaration)(new Initializer(nestedBlockDeclaration, mods)), bracketBalanceValue);
   }

   public RecoveredElement add(FieldDeclaration fieldDeclaration, int bracketBalanceValue) {
      this.pendingTypeParameters = null;
      if (this.typeDeclaration.declarationSourceEnd != 0 && fieldDeclaration.declarationSourceStart > this.typeDeclaration.declarationSourceEnd) {
         this.resetPendingModifiers();
         return this.parent.add(fieldDeclaration, bracketBalanceValue);
      } else {
         if (this.fields == null) {
            this.fields = new RecoveredField[5];
            this.fieldCount = 0;
         } else if (this.fieldCount == this.fields.length) {
            System.arraycopy(this.fields, 0, this.fields = new RecoveredField[2 * this.fieldCount], 0, this.fieldCount);
         }

         Object element;
         switch (fieldDeclaration.getKind()) {
            case 1:
            case 3:
               element = new RecoveredField(fieldDeclaration, this, bracketBalanceValue);
               break;
            case 2:
               element = new RecoveredInitializer(fieldDeclaration, this, bracketBalanceValue);
               break;
            default:
               return this;
         }

         this.fields[this.fieldCount++] = (RecoveredField)element;
         if (this.pendingAnnotationCount > 0) {
            ((RecoveredField)element).attach(this.pendingAnnotations, this.pendingAnnotationCount, this.pendingModifiers, this.pendingModifersSourceStart);
         }

         this.resetPendingModifiers();
         if (!this.foundOpeningBrace) {
            this.foundOpeningBrace = true;
            ++this.bracketBalance;
         }

         return (RecoveredElement)(fieldDeclaration.declarationSourceEnd == 0 ? element : this);
      }
   }

   public RecoveredElement add(TypeDeclaration memberTypeDeclaration, int bracketBalanceValue) {
      this.pendingTypeParameters = null;
      if (this.typeDeclaration.declarationSourceEnd != 0 && memberTypeDeclaration.declarationSourceStart > this.typeDeclaration.declarationSourceEnd) {
         this.resetPendingModifiers();
         return this.parent.add(memberTypeDeclaration, bracketBalanceValue);
      } else {
         this.insideEnumConstantPart = false;
         if ((memberTypeDeclaration.bits & 512) != 0) {
            if (this.methodCount > 0) {
               RecoveredMethod lastMethod = this.methods[this.methodCount - 1];
               lastMethod.methodDeclaration.bodyEnd = 0;
               lastMethod.methodDeclaration.declarationSourceEnd = 0;
               lastMethod.bracketBalance = lastMethod.bracketBalance + 1;
               this.resetPendingModifiers();
               return lastMethod.add(memberTypeDeclaration, bracketBalanceValue);
            } else {
               return this;
            }
         } else {
            if (this.memberTypes == null) {
               this.memberTypes = new RecoveredType[5];
               this.memberTypeCount = 0;
            } else if (this.memberTypeCount == this.memberTypes.length) {
               System.arraycopy(this.memberTypes, 0, this.memberTypes = new RecoveredType[2 * this.memberTypeCount], 0, this.memberTypeCount);
            }

            RecoveredType element = new RecoveredType(memberTypeDeclaration, this, bracketBalanceValue);
            this.memberTypes[this.memberTypeCount++] = element;
            if (this.pendingAnnotationCount > 0) {
               element.attach(this.pendingAnnotations, this.pendingAnnotationCount, this.pendingModifiers, this.pendingModifersSourceStart);
            }

            this.resetPendingModifiers();
            if (!this.foundOpeningBrace) {
               this.foundOpeningBrace = true;
               ++this.bracketBalance;
            }

            return memberTypeDeclaration.declarationSourceEnd == 0 ? element : this;
         }
      }
   }

   public void add(TypeParameter[] parameters, int startPos) {
      this.pendingTypeParameters = parameters;
      this.pendingTypeParametersStart = startPos;
   }

   public RecoveredElement addAnnotationName(int identifierPtr, int identifierLengthPtr, int annotationStart, int bracketBalanceValue) {
      if (this.pendingAnnotations == null) {
         this.pendingAnnotations = new RecoveredAnnotation[5];
         this.pendingAnnotationCount = 0;
      } else if (this.pendingAnnotationCount == this.pendingAnnotations.length) {
         System.arraycopy(this.pendingAnnotations, 0, this.pendingAnnotations = new RecoveredAnnotation[2 * this.pendingAnnotationCount], 0, this.pendingAnnotationCount);
      }

      RecoveredAnnotation element = new RecoveredAnnotation(identifierPtr, identifierLengthPtr, annotationStart, this, bracketBalanceValue);
      this.pendingAnnotations[this.pendingAnnotationCount++] = element;
      return element;
   }

   public void addModifier(int flag, int modifiersSourceStart) {
      this.pendingModifiers |= flag;
      if (this.pendingModifersSourceStart < 0) {
         this.pendingModifersSourceStart = modifiersSourceStart;
      }

   }

   public void attach(RecoveredAnnotation[] annots, int annotCount, int mods, int modsSourceStart) {
      if (annotCount > 0) {
         Annotation[] existingAnnotations = this.typeDeclaration.annotations;
         if (existingAnnotations != null) {
            this.annotations = new RecoveredAnnotation[annotCount];
            this.annotationCount = 0;

            label33:
            for(int i = 0; i < annotCount; ++i) {
               for(int j = 0; j < existingAnnotations.length; ++j) {
                  if (annots[i].annotation == existingAnnotations[j]) {
                     continue label33;
                  }
               }

               this.annotations[this.annotationCount++] = annots[i];
            }
         } else {
            this.annotations = annots;
            this.annotationCount = annotCount;
         }
      }

      if (mods != 0) {
         this.modifiers = mods;
         this.modifiersStart = modsSourceStart;
      }

   }

   public int bodyEnd() {
      return this.bodyEnd == 0 ? this.typeDeclaration.declarationSourceEnd : this.bodyEnd;
   }

   public boolean bodyStartsAtHeaderEnd() {
      if (this.typeDeclaration.superInterfaces == null) {
         if (this.typeDeclaration.superclass == null) {
            if (this.typeDeclaration.typeParameters == null) {
               return this.typeDeclaration.bodyStart == this.typeDeclaration.sourceEnd + 1;
            } else {
               return this.typeDeclaration.bodyStart == this.typeDeclaration.typeParameters[this.typeDeclaration.typeParameters.length - 1].sourceEnd + 1;
            }
         } else {
            return this.typeDeclaration.bodyStart == this.typeDeclaration.superclass.sourceEnd + 1;
         }
      } else {
         return this.typeDeclaration.bodyStart == this.typeDeclaration.superInterfaces[this.typeDeclaration.superInterfaces.length - 1].sourceEnd + 1;
      }
   }

   public RecoveredType enclosingType() {
      for(RecoveredElement current = this.parent; current != null; current = current.parent) {
         if (current instanceof RecoveredType) {
            return (RecoveredType)current;
         }
      }

      return null;
   }

   public int lastMemberEnd() {
      int lastMemberEnd = this.typeDeclaration.bodyStart;
      if (this.fieldCount > 0) {
         FieldDeclaration lastField = this.fields[this.fieldCount - 1].fieldDeclaration;
         if (lastMemberEnd < lastField.declarationSourceEnd && lastField.declarationSourceEnd != 0) {
            lastMemberEnd = lastField.declarationSourceEnd;
         }
      }

      if (this.methodCount > 0) {
         AbstractMethodDeclaration lastMethod = this.methods[this.methodCount - 1].methodDeclaration;
         if (lastMemberEnd < lastMethod.declarationSourceEnd && lastMethod.declarationSourceEnd != 0) {
            lastMemberEnd = lastMethod.declarationSourceEnd;
         }
      }

      if (this.memberTypeCount > 0) {
         TypeDeclaration lastType = this.memberTypes[this.memberTypeCount - 1].typeDeclaration;
         if (lastMemberEnd < lastType.declarationSourceEnd && lastType.declarationSourceEnd != 0) {
            lastMemberEnd = lastType.declarationSourceEnd;
         }
      }

      return lastMemberEnd;
   }

   public int getLastStart() {
      int lastMemberStart = this.typeDeclaration.bodyStart;
      if (this.fieldCount > 0) {
         FieldDeclaration lastField = this.fields[this.fieldCount - 1].fieldDeclaration;
         if (lastMemberStart < lastField.declarationSourceStart && lastField.declarationSourceStart != 0) {
            lastMemberStart = lastField.declarationSourceStart;
         }
      }

      if (this.methodCount > 0) {
         AbstractMethodDeclaration lastMethod = this.methods[this.methodCount - 1].methodDeclaration;
         if (lastMemberStart < lastMethod.declarationSourceStart && lastMethod.declarationSourceStart != 0) {
            lastMemberStart = lastMethod.declarationSourceStart;
         }
      }

      if (this.memberTypeCount > 0) {
         TypeDeclaration lastType = this.memberTypes[this.memberTypeCount - 1].typeDeclaration;
         if (lastMemberStart < lastType.declarationSourceStart && lastType.declarationSourceStart != 0) {
            lastMemberStart = lastType.declarationSourceStart;
         }
      }

      return lastMemberStart;
   }

   public char[] name() {
      return this.typeDeclaration.name;
   }

   public ASTNode parseTree() {
      return this.typeDeclaration;
   }

   public void resetPendingModifiers() {
      this.pendingAnnotations = null;
      this.pendingAnnotationCount = 0;
      this.pendingModifiers = 0;
      this.pendingModifersSourceStart = -1;
   }

   public int sourceEnd() {
      return this.typeDeclaration.declarationSourceEnd;
   }

   public String toString(int tab) {
      StringBuffer result = new StringBuffer(this.tabString(tab));
      result.append("Recovered type:\n");
      if ((this.typeDeclaration.bits & 512) != 0) {
         result.append(this.tabString(tab));
         result.append(" ");
      }

      this.typeDeclaration.print(tab + 1, result);
      int i;
      if (this.annotations != null) {
         for(i = 0; i < this.annotationCount; ++i) {
            result.append("\n");
            result.append(this.annotations[i].toString(tab + 1));
         }
      }

      if (this.memberTypes != null) {
         for(i = 0; i < this.memberTypeCount; ++i) {
            result.append("\n");
            result.append(this.memberTypes[i].toString(tab + 1));
         }
      }

      if (this.fields != null) {
         for(i = 0; i < this.fieldCount; ++i) {
            result.append("\n");
            result.append(this.fields[i].toString(tab + 1));
         }
      }

      if (this.methods != null) {
         for(i = 0; i < this.methodCount; ++i) {
            result.append("\n");
            result.append(this.methods[i].toString(tab + 1));
         }
      }

      return result.toString();
   }

   public void updateBodyStart(int bodyStart) {
      this.foundOpeningBrace = true;
      this.typeDeclaration.bodyStart = bodyStart;
   }

   public Statement updatedStatement(int depth, Set knownTypes) {
      if ((this.typeDeclaration.bits & 512) != 0 && !this.preserveContent) {
         return null;
      } else {
         TypeDeclaration updatedType = this.updatedTypeDeclaration(depth + 1, knownTypes);
         if (updatedType != null && (updatedType.bits & 512) != 0) {
            QualifiedAllocationExpression allocation = updatedType.allocation;
            if (allocation.statementEnd == -1) {
               allocation.statementEnd = updatedType.declarationSourceEnd;
            }

            return allocation;
         } else {
            return updatedType;
         }
      }
   }

   public TypeDeclaration updatedTypeDeclaration(int depth, Set knownTypes) {
      if (depth >= 256) {
         return null;
      } else if (knownTypes.contains(this.typeDeclaration)) {
         return null;
      } else {
         knownTypes.add(this.typeDeclaration);
         int lastEnd = this.typeDeclaration.bodyStart;
         TypeDeclaration var10000;
         if (this.modifiers != 0) {
            var10000 = this.typeDeclaration;
            var10000.modifiers |= this.modifiers;
            if (this.modifiersStart < this.typeDeclaration.declarationSourceStart) {
               this.typeDeclaration.declarationSourceStart = this.modifiersStart;
            }
         }

         int existingCount;
         int i;
         if (this.annotationCount > 0) {
            existingCount = this.typeDeclaration.annotations == null ? 0 : this.typeDeclaration.annotations.length;
            Annotation[] annotationReferences = new Annotation[existingCount + this.annotationCount];
            if (existingCount > 0) {
               System.arraycopy(this.typeDeclaration.annotations, 0, annotationReferences, this.annotationCount, existingCount);
            }

            for(i = 0; i < this.annotationCount; ++i) {
               annotationReferences[i] = this.annotations[i].updatedAnnotationReference();
            }

            this.typeDeclaration.annotations = annotationReferences;
            i = this.annotations[0].annotation.sourceStart;
            if (i < this.typeDeclaration.declarationSourceStart) {
               this.typeDeclaration.declarationSourceStart = i;
            }
         }

         if (this.memberTypeCount > 0) {
            existingCount = this.typeDeclaration.memberTypes == null ? 0 : this.typeDeclaration.memberTypes.length;
            TypeDeclaration[] memberTypeDeclarations = new TypeDeclaration[existingCount + this.memberTypeCount];
            if (existingCount > 0) {
               System.arraycopy(this.typeDeclaration.memberTypes, 0, memberTypeDeclarations, 0, existingCount);
            }

            if (this.memberTypes[this.memberTypeCount - 1].typeDeclaration.declarationSourceEnd == 0) {
               i = this.bodyEnd();
               this.memberTypes[this.memberTypeCount - 1].typeDeclaration.declarationSourceEnd = i;
               this.memberTypes[this.memberTypeCount - 1].typeDeclaration.bodyEnd = i;
            }

            i = 0;

            int length;
            for(length = 0; length < this.memberTypeCount; ++length) {
               TypeDeclaration updatedTypeDeclaration = this.memberTypes[length].updatedTypeDeclaration(depth + 1, knownTypes);
               if (updatedTypeDeclaration != null) {
                  memberTypeDeclarations[existingCount + i++] = updatedTypeDeclaration;
               }
            }

            if (i < this.memberTypeCount) {
               length = existingCount + i;
               System.arraycopy(memberTypeDeclarations, 0, memberTypeDeclarations = new TypeDeclaration[length], 0, length);
            }

            if (memberTypeDeclarations.length > 0) {
               this.typeDeclaration.memberTypes = memberTypeDeclarations;
               if (memberTypeDeclarations[memberTypeDeclarations.length - 1].declarationSourceEnd > lastEnd) {
                  lastEnd = memberTypeDeclarations[memberTypeDeclarations.length - 1].declarationSourceEnd;
               }
            }
         }

         if (this.fieldCount > 0) {
            existingCount = this.typeDeclaration.fields == null ? 0 : this.typeDeclaration.fields.length;
            FieldDeclaration[] fieldDeclarations = new FieldDeclaration[existingCount + this.fieldCount];
            if (existingCount > 0) {
               System.arraycopy(this.typeDeclaration.fields, 0, fieldDeclarations, 0, existingCount);
            }

            if (this.fields[this.fieldCount - 1].fieldDeclaration.declarationSourceEnd == 0) {
               i = this.bodyEnd();
               FieldDeclaration fieldDeclaration = this.fields[this.fieldCount - 1].fieldDeclaration;
               if (i == 0 && fieldDeclaration.sourceEnd > 0) {
                  i = fieldDeclaration.sourceEnd;
                  if (lastEnd > i) {
                     lastEnd = i;
                  }
               }

               fieldDeclaration.declarationSourceEnd = i;
               fieldDeclaration.declarationEnd = i;
            }

            for(i = 0; i < this.fieldCount; ++i) {
               fieldDeclarations[existingCount + i] = this.fields[i].updatedFieldDeclaration(depth, knownTypes);
            }

            for(i = this.fieldCount - 1; i > 0; --i) {
               if (fieldDeclarations[existingCount + i - 1].declarationSourceStart == fieldDeclarations[existingCount + i].declarationSourceStart) {
                  fieldDeclarations[existingCount + i - 1].declarationSourceEnd = fieldDeclarations[existingCount + i].declarationSourceEnd;
                  fieldDeclarations[existingCount + i - 1].declarationEnd = fieldDeclarations[existingCount + i].declarationEnd;
               }
            }

            this.typeDeclaration.fields = fieldDeclarations;
            if (fieldDeclarations[fieldDeclarations.length - 1].declarationSourceEnd > lastEnd) {
               lastEnd = fieldDeclarations[fieldDeclarations.length - 1].declarationSourceEnd;
            }
         }

         existingCount = this.typeDeclaration.methods == null ? 0 : this.typeDeclaration.methods.length;
         boolean hasConstructor = false;
         boolean hasRecoveredConstructor = false;
         boolean hasAbstractMethods = false;
         int defaultConstructorIndex = -1;
         AbstractMethodDeclaration[] methodDeclarations;
         int totalMethods;
         int kind;
         if (this.methodCount > 0) {
            methodDeclarations = new AbstractMethodDeclaration[existingCount + this.methodCount];

            for(totalMethods = 0; totalMethods < existingCount; ++totalMethods) {
               AbstractMethodDeclaration m = this.typeDeclaration.methods[totalMethods];
               if (m.isDefaultConstructor()) {
                  defaultConstructorIndex = totalMethods;
               }

               if (m.isAbstract()) {
                  hasAbstractMethods = true;
               }

               methodDeclarations[totalMethods] = m;
            }

            if (this.methods[this.methodCount - 1].methodDeclaration.declarationSourceEnd == 0) {
               totalMethods = this.bodyEnd();
               this.methods[this.methodCount - 1].methodDeclaration.declarationSourceEnd = totalMethods;
               this.methods[this.methodCount - 1].methodDeclaration.bodyEnd = totalMethods;
            }

            totalMethods = existingCount;

            label241:
            for(int i = 0; i < this.methodCount; ++i) {
               for(int j = 0; j < existingCount; ++j) {
                  if (methodDeclarations[j] == this.methods[i].methodDeclaration) {
                     continue label241;
                  }
               }

               AbstractMethodDeclaration updatedMethod = this.methods[i].updatedMethodDeclaration(depth, knownTypes);
               if (updatedMethod.isConstructor()) {
                  hasRecoveredConstructor = true;
               }

               if (updatedMethod.isAbstract()) {
                  hasAbstractMethods = true;
               }

               methodDeclarations[totalMethods++] = updatedMethod;
            }

            if (totalMethods != methodDeclarations.length) {
               System.arraycopy(methodDeclarations, 0, methodDeclarations = new AbstractMethodDeclaration[totalMethods], 0, totalMethods);
            }

            this.typeDeclaration.methods = methodDeclarations;
            if (methodDeclarations[methodDeclarations.length - 1].declarationSourceEnd > lastEnd) {
               lastEnd = methodDeclarations[methodDeclarations.length - 1].declarationSourceEnd;
            }

            if (hasAbstractMethods) {
               var10000 = this.typeDeclaration;
               var10000.bits |= 2048;
            }

            hasConstructor = this.typeDeclaration.checkConstructors(this.parser());
         } else {
            for(kind = 0; kind < existingCount; ++kind) {
               if (this.typeDeclaration.methods[kind].isConstructor()) {
                  hasConstructor = true;
               }
            }
         }

         if (this.typeDeclaration.needClassInitMethod()) {
            boolean alreadyHasClinit = false;

            for(totalMethods = 0; totalMethods < existingCount; ++totalMethods) {
               if (this.typeDeclaration.methods[totalMethods].isClinit()) {
                  alreadyHasClinit = true;
                  break;
               }
            }

            if (!alreadyHasClinit) {
               this.typeDeclaration.addClinit();
            }
         }

         if (defaultConstructorIndex >= 0 && hasRecoveredConstructor) {
            methodDeclarations = new AbstractMethodDeclaration[this.typeDeclaration.methods.length - 1];
            if (defaultConstructorIndex != 0) {
               System.arraycopy(this.typeDeclaration.methods, 0, methodDeclarations, 0, defaultConstructorIndex);
            }

            if (defaultConstructorIndex != this.typeDeclaration.methods.length - 1) {
               System.arraycopy(this.typeDeclaration.methods, defaultConstructorIndex + 1, methodDeclarations, defaultConstructorIndex, this.typeDeclaration.methods.length - defaultConstructorIndex - 1);
            }

            this.typeDeclaration.methods = methodDeclarations;
         } else {
            kind = TypeDeclaration.kind(this.typeDeclaration.modifiers);
            if (!hasConstructor && kind != 2 && kind != 4 && this.typeDeclaration.allocation == null) {
               boolean insideFieldInitializer = false;

               for(RecoveredElement parentElement = this.parent; parentElement != null; parentElement = parentElement.parent) {
                  if (parentElement instanceof RecoveredField) {
                     insideFieldInitializer = true;
                     break;
                  }
               }

               this.typeDeclaration.createDefaultConstructor(!this.parser().diet || insideFieldInitializer, true);
            }
         }

         if (this.parent instanceof RecoveredType) {
            var10000 = this.typeDeclaration;
            var10000.bits |= 1024;
         } else if (this.parent instanceof RecoveredMethod) {
            var10000 = this.typeDeclaration;
            var10000.bits |= 256;
         }

         if (this.typeDeclaration.declarationSourceEnd == 0) {
            this.typeDeclaration.declarationSourceEnd = lastEnd;
            this.typeDeclaration.bodyEnd = lastEnd;
         }

         return this.typeDeclaration;
      }
   }

   public void updateFromParserState() {
      if (this.bodyStartsAtHeaderEnd() && this.typeDeclaration.allocation == null) {
         Parser parser = this.parser();
         int length;
         int genericsPtr;
         boolean canConsume;
         int i;
         if (parser.listLength > 0 && parser.astLengthPtr > 0) {
            length = parser.astLengthStack[parser.astLengthPtr];
            genericsPtr = parser.astPtr - length;
            canConsume = genericsPtr >= 0;
            if (canConsume) {
               if (!(parser.astStack[genericsPtr] instanceof TypeDeclaration)) {
                  canConsume = false;
               }

               i = 1;

               for(int max = length + 1; i < max; ++i) {
                  if (!(parser.astStack[genericsPtr + i] instanceof TypeReference)) {
                     canConsume = false;
                  }
               }
            }

            if (canConsume) {
               parser.consumeClassHeaderImplements();
            }
         } else if (parser.listTypeParameterLength > 0) {
            length = parser.listTypeParameterLength;
            genericsPtr = parser.genericsPtr;
            canConsume = genericsPtr + 1 >= length && parser.astPtr > -1;
            if (canConsume) {
               if (!(parser.astStack[parser.astPtr] instanceof TypeDeclaration)) {
                  canConsume = false;
               }

               while(genericsPtr + 1 > length && !(parser.genericsStack[genericsPtr] instanceof TypeParameter)) {
                  --genericsPtr;
               }

               for(i = 0; i < length; ++i) {
                  if (!(parser.genericsStack[genericsPtr - i] instanceof TypeParameter)) {
                     canConsume = false;
                  }
               }
            }

            if (canConsume) {
               TypeDeclaration typeDecl = (TypeDeclaration)parser.astStack[parser.astPtr];
               System.arraycopy(parser.genericsStack, genericsPtr - length + 1, typeDecl.typeParameters = new TypeParameter[length], 0, length);
               typeDecl.bodyStart = typeDecl.typeParameters[length - 1].declarationSourceEnd + 1;
               parser.listTypeParameterLength = 0;
               parser.lastCheckPoint = typeDecl.bodyStart;
            }
         }
      }

   }

   public RecoveredElement updateOnClosingBrace(int braceStart, int braceEnd) {
      if (--this.bracketBalance <= 0 && this.parent != null) {
         this.updateSourceEndIfNecessary(braceStart, braceEnd);
         this.bodyEnd = braceStart - 1;
         return this.parent;
      } else {
         return this;
      }
   }

   public RecoveredElement updateOnOpeningBrace(int braceStart, int braceEnd) {
      if (this.bracketBalance == 0) {
         Parser parser = this.parser();
         switch (parser.lastIgnoredToken) {
            case -1:
            case 14:
            case 15:
            case 16:
            case 86:
            case 123:
               if (parser.recoveredStaticInitializerStart == 0) {
                  break;
               }
            default:
               this.foundOpeningBrace = true;
               this.bracketBalance = 1;
         }
      }

      if (this.bracketBalance == 1) {
         Block block = new Block(0);
         Parser parser = this.parser();
         block.sourceStart = parser.scanner.startPosition;
         Initializer init;
         if (parser.recoveredStaticInitializerStart == 0) {
            init = new Initializer(block, 0);
         } else {
            init = new Initializer(block, 8);
            init.declarationSourceStart = parser.recoveredStaticInitializerStart;
         }

         init.bodyStart = parser.scanner.currentPosition;
         return this.add((FieldDeclaration)init, 1);
      } else {
         return super.updateOnOpeningBrace(braceStart, braceEnd);
      }
   }

   public void updateParseTree() {
      this.updatedTypeDeclaration(0, new HashSet());
   }

   public void updateSourceEndIfNecessary(int start, int end) {
      if (this.typeDeclaration.declarationSourceEnd == 0) {
         this.bodyEnd = 0;
         this.typeDeclaration.declarationSourceEnd = end;
         this.typeDeclaration.bodyEnd = end;
      }

   }

   public void annotationsConsumed(Annotation[] consumedAnnotations) {
      RecoveredAnnotation[] keep = new RecoveredAnnotation[this.pendingAnnotationCount];
      int numKeep = 0;
      int pendingCount = this.pendingAnnotationCount;
      int consumedLength = consumedAnnotations.length;

      label28:
      for(int i = 0; i < pendingCount; ++i) {
         Annotation pendingAnnotationAST = this.pendingAnnotations[i].annotation;

         for(int j = 0; j < consumedLength; ++j) {
            if (consumedAnnotations[j] == pendingAnnotationAST) {
               continue label28;
            }
         }

         keep[numKeep++] = this.pendingAnnotations[i];
      }

      if (numKeep != this.pendingAnnotationCount) {
         this.pendingAnnotations = keep;
         this.pendingAnnotationCount = numKeep;
      }

   }
}
