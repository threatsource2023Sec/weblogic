package com.bea.core.repackaged.jdt.internal.compiler.parser;

import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ExportsStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ModuleReference;

public class RecoveredExport extends RecoveredElement {
   public ExportsStatement exportReference;
   RecoveredModuleReference[] targets;
   int targetCount = 0;

   public RecoveredExport(ExportsStatement exportReference, RecoveredElement parent, int bracketBalance) {
      super(parent, bracketBalance);
      this.exportReference = exportReference;
   }

   public RecoveredElement add(ModuleReference target, int bracketBalance1) {
      if (this.targets == null) {
         this.targets = new RecoveredModuleReference[5];
         this.targetCount = 0;
      } else if (this.targetCount == this.targets.length) {
         System.arraycopy(this.targets, 0, this.targets = new RecoveredModuleReference[2 * this.targetCount], 0, this.targetCount);
      }

      RecoveredModuleReference element = new RecoveredModuleReference(target, this, bracketBalance1);
      this.targets[this.targetCount++] = element;
      return (RecoveredElement)(target.sourceEnd == 0 ? element : this);
   }

   public ASTNode parseTree() {
      return this.exportReference;
   }

   public int sourceEnd() {
      return this.exportReference.declarationSourceEnd;
   }

   public String toString(int tab) {
      return this.tabString(tab) + "Recovered export: " + this.exportReference.toString();
   }

   public ExportsStatement updatedExportReference() {
      if (this.targetCount > 0) {
         int existingCount = this.exportReference.targets != null ? this.exportReference.targets.length : 0;
         int actualCount = 0;
         ModuleReference[] moduleRef1 = new ModuleReference[existingCount + this.targetCount];
         if (existingCount > 0) {
            System.arraycopy(this.exportReference.targets, 0, moduleRef1, 0, existingCount);
            actualCount = existingCount;
         }

         int i = 0;

         for(int l = this.targetCount; i < l; ++i) {
            moduleRef1[actualCount++] = this.targets[i].updatedModuleReference();
         }

         this.exportReference.targets = moduleRef1;
      }

      return this.exportReference;
   }

   public void updateParseTree() {
      this.updatedExportReference();
   }

   public void updateSourceEndIfNecessary(int bodyStart, int bodyEnd) {
      if (this.exportReference.declarationSourceEnd == 0) {
         this.exportReference.declarationSourceEnd = bodyEnd;
         this.exportReference.declarationEnd = bodyEnd;
      }

   }
}
