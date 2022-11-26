package com.bea.core.repackaged.jdt.internal.compiler.parser;

import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Block;
import com.bea.core.repackaged.jdt.internal.compiler.ast.CompilationUnitDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ExportsStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.FieldDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ImportReference;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Initializer;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ModuleDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeDeclaration;
import java.util.HashSet;
import java.util.Set;

public class RecoveredUnit extends RecoveredElement {
   public CompilationUnitDeclaration unitDeclaration;
   public RecoveredImport[] imports;
   public int importCount;
   public RecoveredModule module;
   public RecoveredType[] types;
   public int typeCount;
   int pendingModifiers;
   int pendingModifersSourceStart = -1;
   RecoveredAnnotation[] pendingAnnotations;
   int pendingAnnotationCount;

   public RecoveredUnit(CompilationUnitDeclaration unitDeclaration, int bracketBalance, Parser parser) {
      super((RecoveredElement)null, bracketBalance, parser);
      this.unitDeclaration = unitDeclaration;
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

   public RecoveredElement add(AbstractMethodDeclaration methodDeclaration, int bracketBalanceValue) {
      if (this.typeCount > 0) {
         RecoveredType type = this.types[this.typeCount - 1];
         int start = type.bodyEnd;
         int end = type.typeDeclaration.bodyEnd;
         type.bodyEnd = 0;
         type.typeDeclaration.declarationSourceEnd = 0;
         type.typeDeclaration.bodyEnd = 0;
         int kind = TypeDeclaration.kind(type.typeDeclaration.modifiers);
         if (start > 0 && start < end && kind != 2 && kind != 4) {
            Block block = new Block(0);
            block.sourceStart = block.sourceEnd = end;
            Initializer initializer = new Initializer(block, 0);
            initializer.bodyStart = end;
            initializer.bodyEnd = end;
            initializer.declarationSourceStart = end;
            initializer.declarationSourceEnd = end;
            initializer.sourceStart = end;
            initializer.sourceEnd = end;
            type.add((FieldDeclaration)initializer, bracketBalanceValue);
         }

         this.resetPendingModifiers();
         return type.add(methodDeclaration, bracketBalanceValue);
      } else {
         return this;
      }
   }

   public RecoveredElement add(FieldDeclaration fieldDeclaration, int bracketBalanceValue) {
      if (this.typeCount > 0) {
         RecoveredType type = this.types[this.typeCount - 1];
         type.bodyEnd = 0;
         type.typeDeclaration.declarationSourceEnd = 0;
         type.typeDeclaration.bodyEnd = 0;
         this.resetPendingModifiers();
         return type.add(fieldDeclaration, bracketBalanceValue);
      } else {
         return this;
      }
   }

   public RecoveredElement add(ExportsStatement exportReference, int bracketBalanceValue) {
      return this.module != null ? this.module.add(exportReference, bracketBalanceValue) : null;
   }

   public RecoveredElement add(ImportReference importReference, int bracketBalanceValue) {
      this.resetPendingModifiers();
      if (this.imports == null) {
         this.imports = new RecoveredImport[5];
         this.importCount = 0;
      } else if (this.importCount == this.imports.length) {
         System.arraycopy(this.imports, 0, this.imports = new RecoveredImport[2 * this.importCount], 0, this.importCount);
      }

      RecoveredImport element = new RecoveredImport(importReference, this, bracketBalanceValue);
      this.imports[this.importCount++] = element;
      return (RecoveredElement)(importReference.declarationSourceEnd == 0 ? element : this);
   }

   public RecoveredElement add(ModuleDeclaration moduleDeclaration, int bracketBalanceValue) {
      this.module = new RecoveredModule(moduleDeclaration, this, bracketBalanceValue);
      return this.module;
   }

   public RecoveredElement add(TypeDeclaration typeDeclaration, int bracketBalanceValue) {
      RecoveredType element;
      if ((typeDeclaration.bits & 512) != 0 && this.typeCount > 0) {
         element = this.types[this.typeCount - 1];
         element.bodyEnd = 0;
         element.typeDeclaration.bodyEnd = 0;
         element.typeDeclaration.declarationSourceEnd = 0;
         element.bracketBalance = element.bracketBalance + 1;
         this.resetPendingModifiers();
         return element.add(typeDeclaration, bracketBalanceValue);
      } else {
         if (this.types == null) {
            this.types = new RecoveredType[5];
            this.typeCount = 0;
         } else if (this.typeCount == this.types.length) {
            System.arraycopy(this.types, 0, this.types = new RecoveredType[2 * this.typeCount], 0, this.typeCount);
         }

         element = new RecoveredType(typeDeclaration, this, bracketBalanceValue);
         this.types[this.typeCount++] = element;
         if (this.pendingAnnotationCount > 0) {
            element.attach(this.pendingAnnotations, this.pendingAnnotationCount, this.pendingModifiers, this.pendingModifersSourceStart);
         }

         this.resetPendingModifiers();
         return (RecoveredElement)(typeDeclaration.declarationSourceEnd == 0 ? element : this);
      }
   }

   public ASTNode parseTree() {
      return this.unitDeclaration;
   }

   public void resetPendingModifiers() {
      this.pendingAnnotations = null;
      this.pendingAnnotationCount = 0;
      this.pendingModifiers = 0;
      this.pendingModifersSourceStart = -1;
   }

   public int sourceEnd() {
      return this.unitDeclaration.sourceEnd;
   }

   public int getLastStart() {
      int lastTypeStart = -1;
      if (this.typeCount > 0) {
         TypeDeclaration lastType = this.types[this.typeCount - 1].typeDeclaration;
         if (lastTypeStart < lastType.declarationSourceStart && lastType.declarationSourceStart != 0) {
            lastTypeStart = lastType.declarationSourceStart;
         }
      }

      return lastTypeStart;
   }

   public String toString(int tab) {
      StringBuffer result = new StringBuffer(this.tabString(tab));
      result.append("Recovered unit: [\n");
      this.unitDeclaration.print(tab + 1, result);
      result.append(this.tabString(tab + 1));
      result.append("]");
      int i;
      if (this.imports != null) {
         for(i = 0; i < this.importCount; ++i) {
            result.append("\n");
            result.append(this.imports[i].toString(tab + 1));
         }
      }

      if (this.types != null) {
         for(i = 0; i < this.typeCount; ++i) {
            result.append("\n");
            result.append(this.types[i].toString(tab + 1));
         }
      }

      return result.toString();
   }

   public CompilationUnitDeclaration updatedCompilationUnitDeclaration() {
      if (this.importCount > 0) {
         ImportReference[] importRefences = new ImportReference[this.importCount];

         for(int i = 0; i < this.importCount; ++i) {
            importRefences[i] = this.imports[i].updatedImportReference();
         }

         this.unitDeclaration.imports = importRefences;
      }

      if (this.module != null) {
         this.unitDeclaration.moduleDeclaration = this.module.updatedModuleDeclaration();
      }

      if (this.typeCount > 0) {
         int existingCount = this.unitDeclaration.types == null ? 0 : this.unitDeclaration.types.length;
         TypeDeclaration[] typeDeclarations = new TypeDeclaration[existingCount + this.typeCount];
         if (existingCount > 0) {
            System.arraycopy(this.unitDeclaration.types, 0, typeDeclarations, 0, existingCount);
         }

         if (this.types[this.typeCount - 1].typeDeclaration.declarationSourceEnd == 0) {
            this.types[this.typeCount - 1].typeDeclaration.declarationSourceEnd = this.unitDeclaration.sourceEnd;
            this.types[this.typeCount - 1].typeDeclaration.bodyEnd = this.unitDeclaration.sourceEnd;
         }

         Set knownTypes = new HashSet();
         int actualCount = existingCount;

         for(int i = 0; i < this.typeCount; ++i) {
            TypeDeclaration typeDecl = this.types[i].updatedTypeDeclaration(0, knownTypes);
            if (typeDecl != null && (typeDecl.bits & 256) == 0) {
               typeDeclarations[actualCount++] = typeDecl;
            }
         }

         if (actualCount != this.typeCount) {
            System.arraycopy(typeDeclarations, 0, typeDeclarations = new TypeDeclaration[existingCount + actualCount], 0, existingCount + actualCount);
         }

         this.unitDeclaration.types = typeDeclarations;
      }

      return this.unitDeclaration;
   }

   public void updateParseTree() {
      this.updatedCompilationUnitDeclaration();
   }

   public void updateSourceEndIfNecessary(int bodyStart, int bodyEnd) {
      if (this.unitDeclaration.sourceEnd == 0) {
         this.unitDeclaration.sourceEnd = bodyEnd;
      }

   }
}
