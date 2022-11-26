package weblogic.utils.classfile.expr;

import java.util.Iterator;
import java.util.LinkedList;
import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.Label;
import weblogic.utils.classfile.Op;
import weblogic.utils.classfile.cp.ConstantPool;
import weblogic.utils.classfile.ops.BranchOp;
import weblogic.utils.classfile.ops.TableswitchOp;

public class SwitchStatement implements Statement {
   Expression expression;
   int beginPoint = -1;
   LinkedList cases = new LinkedList();
   Statement defaultCase;

   public SwitchStatement(Expression expression) {
      this.expression = expression;
   }

   public void code(CodeAttribute ca, Bytecodes code) {
      ConstantPool cp = ca.getConstantPool();
      int numBranches = this.cases.size();
      TableswitchOp tableSwitch = new TableswitchOp(170, 0);
      tableSwitch.targets = new Op[numBranches];
      tableSwitch.offsets = new int[numBranches];
      tableSwitch.low = 0;
      tableSwitch.high = numBranches - 1;
      if (this.beginPoint == -1) {
         tableSwitch.low = 0;
         tableSwitch.high = numBranches - 1;
      } else {
         tableSwitch.low = this.beginPoint;
         tableSwitch.high = numBranches - 1 + this.beginPoint;
      }

      this.expression.code(ca, code);
      code.add(tableSwitch);
      Label branchTarget = null;

      for(int i = 0; i < numBranches; ++i) {
         CaseStatement b = (CaseStatement)this.cases.get(i);
         Label label = new Label();
         code.add(label);
         b.caseStatement.code(ca, code);
         tableSwitch.targets[i] = label;
         if (b.doBreak) {
            if (branchTarget == null) {
               branchTarget = new Label();
            }

            BranchOp breakOp = new BranchOp(200);
            breakOp.target = branchTarget;
            code.add(breakOp);
         }
      }

      if (this.defaultCase != null) {
         Label defaultLabel = new Label();
         code.add(defaultLabel);
         this.defaultCase.code(ca, code);
         tableSwitch.default_target = defaultLabel;
      }

      if (branchTarget != null) {
         code.add(branchTarget);
      }

   }

   public void addCase(int label, Statement caseStatement, boolean doBreak) {
      if (this.beginPoint == -1) {
         this.beginPoint = label;
      }

      this.cases.add(new CaseStatement(caseStatement, doBreak));
   }

   public void setDefault(Statement s) {
      this.defaultCase = s;
   }

   public int getMaxStack() {
      int expressionStack = this.expression.getMaxStack();
      int maxStack = 0;
      Iterator i = this.cases.iterator();

      while(i.hasNext()) {
         CaseStatement c = (CaseStatement)i.next();
         if (c.caseStatement.getMaxStack() > maxStack) {
            maxStack = c.caseStatement.getMaxStack();
         }
      }

      if (this.defaultCase.getMaxStack() > maxStack) {
         maxStack = this.defaultCase.getMaxStack();
      }

      if (maxStack >= expressionStack) {
         return maxStack + (this.expression.getType().isWide() ? 2 : 1);
      } else {
         return expressionStack;
      }
   }

   class CaseStatement {
      Statement caseStatement;
      boolean doBreak;

      CaseStatement(Statement caseStatement, boolean doBreak) {
         this.caseStatement = caseStatement;
         this.doBreak = doBreak;
      }
   }
}
