package com.bea.core.repackaged.jdt.internal.compiler.parser;

import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Block;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ForeachStatement;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Statement;
import java.util.HashSet;
import java.util.Set;

public class RecoveredStatement extends RecoveredElement {
   public Statement statement;
   RecoveredBlock nestedBlock;

   public RecoveredStatement(Statement statement, RecoveredElement parent, int bracketBalance) {
      super(parent, bracketBalance);
      this.statement = statement;
   }

   public ASTNode parseTree() {
      return this.statement;
   }

   public int sourceEnd() {
      return this.statement.sourceEnd;
   }

   public String toString(int tab) {
      return this.tabString(tab) + "Recovered statement:\n" + this.statement.print(tab + 1, new StringBuffer(10));
   }

   public Statement updatedStatement(int depth, Set knownTypes) {
      if (this.nestedBlock != null) {
         this.nestedBlock.updatedStatement(depth, knownTypes);
      }

      return this.statement;
   }

   public void updateParseTree() {
      this.updatedStatement(0, new HashSet());
   }

   public void updateSourceEndIfNecessary(int bodyStart, int bodyEnd) {
      if (this.statement.sourceEnd == 0) {
         this.statement.sourceEnd = bodyEnd;
      }

   }

   public RecoveredElement updateOnClosingBrace(int braceStart, int braceEnd) {
      if (--this.bracketBalance <= 0 && this.parent != null) {
         this.updateSourceEndIfNecessary(braceStart, braceEnd);
         return this.parent.updateOnClosingBrace(braceStart, braceEnd);
      } else {
         return this;
      }
   }

   public RecoveredElement add(Block nestedBlockDeclaration, int bracketBalanceValue) {
      if (this.statement instanceof ForeachStatement) {
         ForeachStatement foreach = (ForeachStatement)this.statement;
         this.resetPendingModifiers();
         if (foreach.sourceEnd != 0 && foreach.action != null && nestedBlockDeclaration.sourceStart > foreach.sourceEnd) {
            return this.parent.add(nestedBlockDeclaration, bracketBalanceValue);
         } else {
            foreach.action = nestedBlockDeclaration;
            RecoveredBlock element = new RecoveredBlock(nestedBlockDeclaration, this, bracketBalanceValue);
            if (this.parser().statementRecoveryActivated) {
               this.addBlockStatement(element);
            }

            this.nestedBlock = element;
            return (RecoveredElement)(nestedBlockDeclaration.sourceEnd == 0 ? element : this);
         }
      } else {
         return super.add(nestedBlockDeclaration, bracketBalanceValue);
      }
   }
}
