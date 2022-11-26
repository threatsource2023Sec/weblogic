package weblogic.utils.classfile.expr;

import java.util.Iterator;
import java.util.LinkedList;
import weblogic.utils.classfile.Bytecodes;
import weblogic.utils.classfile.CodeAttribute;
import weblogic.utils.classfile.Label;
import weblogic.utils.classfile.Scope;
import weblogic.utils.classfile.Type;
import weblogic.utils.classfile.cp.CPClass;
import weblogic.utils.classfile.cp.ConstantPool;
import weblogic.utils.classfile.ops.BranchOp;

public class TryCatchStatement implements Statement {
   Statement body;
   boolean addBodyBreak;
   LinkedList handlers = new LinkedList();
   Statement finallyBody;

   public void setBody(Statement body) {
      this.body = body;
      this.addBodyBreak = this.addBreak(body);
   }

   public void addHandler(String exc, Statement handler) {
      this.handlers.add(new ExceptionHandler(exc, handler));
   }

   public void setFinally(Statement finallyBody) {
      this.finallyBody = finallyBody;
   }

   private boolean addBreak(Statement body) {
      while(body instanceof CompoundStatement) {
         body = ((CompoundStatement)body).getLastStatement();
      }

      if (!(body instanceof ReturnStatement) && !(body instanceof ThrowStatement)) {
         return true;
      } else {
         return false;
      }
   }

   public void code(CodeAttribute ca, Bytecodes code) {
      ConstantPool cp = ca.getConstantPool();
      Label bodyStart = new Label();
      Label bodyEnd = new Label();
      Label finallyStart = new Label();
      Label finallyHandler = new Label();
      Label breakLabel = null;
      Label handlersEnd = new Label();
      code.add(bodyStart);
      if (this.addBodyBreak) {
         this.body.code(ca, code);
         breakLabel = new Label();
         code.add(new BranchOp(200, breakLabel));
      } else if (this.finallyBody == null) {
         this.body.code(ca, code);
      } else {
         this.codeStatementForFinally(ca, code, this.body, finallyHandler);
      }

      code.add(bodyEnd);
      Iterator i = this.handlers.iterator();

      while(i.hasNext()) {
         ExceptionHandler handler = (ExceptionHandler)i.next();
         Label handlerStart = new Label();
         code.add(handlerStart);
         CPClass exc = cp.getClass(handler.cls);
         ca.addException(bodyStart, bodyEnd, handlerStart, exc);
         Statement s = handler.handler;
         if (handler.addBreak) {
            s.code(ca, code);
            if (i.hasNext()) {
               if (breakLabel == null) {
                  breakLabel = new Label();
               }

               code.add(new BranchOp(200, breakLabel));
            }
         } else if (this.finallyBody == null) {
            s.code(ca, code);
         } else {
            this.codeStatementForFinally(ca, code, s, finallyHandler);
         }
      }

      code.add(handlersEnd);
      if (this.finallyBody != null) {
         if (breakLabel != null) {
            code.add(breakLabel);
            code.add(new BranchOp(201, finallyHandler));
            breakLabel = new Label();
            code.add(new BranchOp(200, breakLabel));
         }

         Scope scope = ca.getScope();
         code.add(finallyStart);
         LocalVariableExpression ex = scope.createLocalVar(Type.OBJECT);
         Statement s = new AssignStatement(ex, new CatchExceptionExpression());
         s.code(ca, code);
         code.add(new BranchOp(201, finallyHandler));
         Statement s = new ThrowStatement(ex);
         s.code(ca, code);
         code.add(finallyHandler);
         LocalVariableExpression retAddr = scope.createLocalVar(Type.OBJECT);
         s = new AssignStatement(retAddr, new CatchExceptionExpression());
         s.code(ca, code);
         this.finallyBody.code(ca, code);
         code.add(retAddr.getLocalVar().getReturnOp());
         ca.addException(bodyStart, handlersEnd, finallyStart, (CPClass)null);
      }

      if (breakLabel != null) {
         code.add(breakLabel);
      }

   }

   private void codeCallFinally(Bytecodes code, Label finallyHandler) {
      code.add(new BranchOp(201, finallyHandler));
   }

   private void codeStatementForFinally(CodeAttribute ca, Bytecodes code, Statement s, Label finallyHandler) {
      if (s instanceof CompoundStatement) {
         CompoundStatement cs = (CompoundStatement)s;
         Statement lastStatement = cs.getLastStatement();
         if (lastStatement instanceof ReturnStatement) {
            cs.codeAllButLast(ca, code);
            ReturnStatement ret = (ReturnStatement)lastStatement;
            ret.codeForFinally(ca, code, finallyHandler);
            return;
         }
      } else if (s instanceof ReturnStatement) {
         ReturnStatement ret = (ReturnStatement)s;
         ret.codeForFinally(ca, code, finallyHandler);
         return;
      }

      s.code(ca, code);
   }

   public int getMaxStack() {
      int maxStack = this.body.getMaxStack();
      Iterator i = this.handlers.iterator();

      while(i.hasNext()) {
         ExceptionHandler h = (ExceptionHandler)i.next();
         if (h.handler.getMaxStack() > maxStack) {
            maxStack = h.handler.getMaxStack();
         }
      }

      if (this.finallyBody != null && this.finallyBody.getMaxStack() + 1 > maxStack) {
         maxStack = this.finallyBody.getMaxStack() + 1;
      }

      return maxStack;
   }

   class ExceptionHandler {
      String cls;
      Statement handler;
      boolean addBreak;

      ExceptionHandler(String cls, Statement handler) {
         this.cls = cls;
         this.handler = handler;
         this.addBreak = TryCatchStatement.this.addBreak(handler);
      }
   }
}
