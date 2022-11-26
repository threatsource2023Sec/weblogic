package weblogic.utils.classfile.expr;

import java.util.Iterator;
import java.util.LinkedList;
import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;

public class CompoundStatement implements Statement {
   private LinkedList statements = new LinkedList();

   public void add(Statement statement) {
      this.statements.add(statement);
   }

   public void code(CodeAttribute ca, Bytecodes code) {
      Iterator i = this.statements.iterator();

      while(i.hasNext()) {
         Statement s = (Statement)i.next();
         s.code(ca, code);
      }

   }

   void codeAllButLast(CodeAttribute ca, Bytecodes code) {
      int size = this.statements.size();
      if (size != 0) {
         for(int i = 0; i < size - 1; ++i) {
            Statement s = (Statement)this.statements.get(i);
            s.code(ca, code);
         }

         Statement s = (Statement)this.statements.get(size - 1);
         if (s instanceof CompoundStatement) {
            ((CompoundStatement)s).codeAllButLast(ca, code);
         }

      }
   }

   Statement getLastStatement() {
      if (this.statements.size() > 0) {
         Statement s = (Statement)this.statements.get(this.statements.size() - 1);
         return s instanceof CompoundStatement ? ((CompoundStatement)s).getLastStatement() : s;
      } else {
         return null;
      }
   }

   public int getMaxStack() {
      int maxStack = 0;
      Iterator i = this.statements.iterator();

      while(i.hasNext()) {
         Statement s = (Statement)i.next();
         if (s.getMaxStack() > maxStack) {
            maxStack = s.getMaxStack();
         }
      }

      return maxStack;
   }
}
