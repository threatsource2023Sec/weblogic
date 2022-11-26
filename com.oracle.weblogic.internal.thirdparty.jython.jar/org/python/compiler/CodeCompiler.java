package org.python.compiler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;
import org.python.antlr.ParseException;
import org.python.antlr.PythonTree;
import org.python.antlr.Visitor;
import org.python.antlr.ast.Assert;
import org.python.antlr.ast.Assign;
import org.python.antlr.ast.Attribute;
import org.python.antlr.ast.AugAssign;
import org.python.antlr.ast.BinOp;
import org.python.antlr.ast.BoolOp;
import org.python.antlr.ast.Break;
import org.python.antlr.ast.Call;
import org.python.antlr.ast.ClassDef;
import org.python.antlr.ast.Compare;
import org.python.antlr.ast.Continue;
import org.python.antlr.ast.Delete;
import org.python.antlr.ast.Dict;
import org.python.antlr.ast.DictComp;
import org.python.antlr.ast.Ellipsis;
import org.python.antlr.ast.ExceptHandler;
import org.python.antlr.ast.Exec;
import org.python.antlr.ast.Expr;
import org.python.antlr.ast.Expression;
import org.python.antlr.ast.ExtSlice;
import org.python.antlr.ast.For;
import org.python.antlr.ast.FunctionDef;
import org.python.antlr.ast.GeneratorExp;
import org.python.antlr.ast.Global;
import org.python.antlr.ast.If;
import org.python.antlr.ast.IfExp;
import org.python.antlr.ast.Import;
import org.python.antlr.ast.ImportFrom;
import org.python.antlr.ast.Index;
import org.python.antlr.ast.Interactive;
import org.python.antlr.ast.Lambda;
import org.python.antlr.ast.ListComp;
import org.python.antlr.ast.Name;
import org.python.antlr.ast.Num;
import org.python.antlr.ast.Pass;
import org.python.antlr.ast.Print;
import org.python.antlr.ast.Raise;
import org.python.antlr.ast.Repr;
import org.python.antlr.ast.Return;
import org.python.antlr.ast.Set;
import org.python.antlr.ast.SetComp;
import org.python.antlr.ast.Slice;
import org.python.antlr.ast.Str;
import org.python.antlr.ast.Subscript;
import org.python.antlr.ast.Suite;
import org.python.antlr.ast.TryExcept;
import org.python.antlr.ast.TryFinally;
import org.python.antlr.ast.Tuple;
import org.python.antlr.ast.UnaryOp;
import org.python.antlr.ast.While;
import org.python.antlr.ast.With;
import org.python.antlr.ast.Yield;
import org.python.antlr.ast.alias;
import org.python.antlr.ast.cmpopType;
import org.python.antlr.ast.comprehension;
import org.python.antlr.ast.expr_contextType;
import org.python.antlr.ast.keyword;
import org.python.antlr.ast.operatorType;
import org.python.antlr.base.expr;
import org.python.antlr.base.mod;
import org.python.antlr.base.stmt;
import org.python.core.CompilerFlags;
import org.python.core.ContextGuard;
import org.python.core.ContextManager;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyComplex;
import org.python.core.PyDictionary;
import org.python.core.PyException;
import org.python.core.PyFloat;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyLong;
import org.python.core.PyObject;
import org.python.core.PySet;
import org.python.core.PySlice;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;
import org.python.objectweb.asm.Label;
import org.python.objectweb.asm.Opcodes;
import org.python.objectweb.asm.Type;
import org.python.objectweb.asm.commons.Method;
import org.python.util.CodegenUtils;

public class CodeCompiler extends Visitor implements Opcodes, ClassConstants {
   private static final Object Exit = new Integer(1);
   private static final Object NoExit = null;
   private Module module;
   private Code code;
   private CompilerFlags cflags;
   private int temporary;
   private expr_contextType augmode;
   private int augtmp1;
   private int augtmp2;
   private int augtmp3;
   private int augtmp4;
   private boolean fast_locals;
   private boolean print_results;
   private Map tbl;
   private ScopeInfo my_scope;
   private boolean optimizeGlobals = true;
   private String className;
   private Stack continueLabels;
   private Stack breakLabels;
   private Stack exceptionHandlers;
   private Vector yields = new Vector();
   private int bcfLevel = 0;
   private int yield_count = 0;
   private Stack stack = new Stack();

   public CodeCompiler(Module module, boolean print_results) {
      this.module = module;
      this.print_results = print_results;
      this.continueLabels = new Stack();
      this.breakLabels = new Stack();
      this.exceptionHandlers = new Stack();
   }

   public void getNone() throws IOException {
      this.code.getstatic(CodegenUtils.p(Py.class), "None", CodegenUtils.ci(PyObject.class));
   }

   public void loadFrame() throws Exception {
      this.code.aload(1);
   }

   public void loadThreadState() throws Exception {
      this.code.aload(2);
   }

   public void setLastI(int idx) throws Exception {
      this.loadFrame();
      this.code.iconst(idx);
      this.code.putfield(CodegenUtils.p(PyFrame.class), "f_lasti", "I");
   }

   private void loadf_back() throws Exception {
      this.code.getfield(CodegenUtils.p(PyFrame.class), "f_back", CodegenUtils.ci(PyFrame.class));
   }

   public int storeTop() throws Exception {
      int tmp = this.code.getLocal(CodegenUtils.p(PyObject.class));
      this.code.astore(tmp);
      return tmp;
   }

   public void setline(int line) throws Exception {
      if (this.module.linenumbers) {
         this.code.setline(line);
         this.loadFrame();
         this.code.iconst(line);
         this.code.invokevirtual(CodegenUtils.p(PyFrame.class), "setline", CodegenUtils.sig(Void.TYPE, Integer.TYPE));
      }

   }

   public void setline(PythonTree node) throws Exception {
      this.setline(node.getLine());
   }

   public void set(PythonTree node) throws Exception {
      int tmp = this.storeTop();
      this.set(node, tmp);
      this.code.aconst_null();
      this.code.astore(tmp);
      this.code.freeLocal(tmp);
   }

   public void set(PythonTree node, int tmp) throws Exception {
      this.temporary = tmp;
      this.visit(node);
   }

   private void saveAugTmps(PythonTree node, int count) throws Exception {
      if (count >= 4) {
         this.augtmp4 = this.code.getLocal(CodegenUtils.ci(PyObject.class));
         this.code.astore(this.augtmp4);
      }

      if (count >= 3) {
         this.augtmp3 = this.code.getLocal(CodegenUtils.ci(PyObject.class));
         this.code.astore(this.augtmp3);
      }

      if (count >= 2) {
         this.augtmp2 = this.code.getLocal(CodegenUtils.ci(PyObject.class));
         this.code.astore(this.augtmp2);
      }

      this.augtmp1 = this.code.getLocal(CodegenUtils.ci(PyObject.class));
      this.code.astore(this.augtmp1);
      this.code.aload(this.augtmp1);
      if (count >= 2) {
         this.code.aload(this.augtmp2);
      }

      if (count >= 3) {
         this.code.aload(this.augtmp3);
      }

      if (count >= 4) {
         this.code.aload(this.augtmp4);
      }

   }

   private void restoreAugTmps(PythonTree node, int count) throws Exception {
      this.code.aload(this.augtmp1);
      this.code.freeLocal(this.augtmp1);
      if (count != 1) {
         this.code.aload(this.augtmp2);
         this.code.freeLocal(this.augtmp2);
         if (count != 2) {
            this.code.aload(this.augtmp3);
            this.code.freeLocal(this.augtmp3);
            if (count != 3) {
               this.code.aload(this.augtmp4);
               this.code.freeLocal(this.augtmp4);
            }
         }
      }
   }

   static boolean checkOptimizeGlobals(boolean fast_locals, ScopeInfo scope) {
      return fast_locals && !scope.exec && !scope.from_import_star;
   }

   void parse(mod node, Code code, boolean fast_locals, String className, Str classDoc, boolean classBody, ScopeInfo scope, CompilerFlags cflags) throws Exception {
      this.fast_locals = fast_locals;
      this.className = className;
      this.code = code;
      this.cflags = cflags;
      this.my_scope = scope;
      this.tbl = scope.tbl;
      if (classBody) {
         this.loadFrame();
         code.ldc("__module__");
         this.loadFrame();
         code.ldc("__name__");
         code.invokevirtual(CodegenUtils.p(PyFrame.class), "getname", CodegenUtils.sig(PyObject.class, String.class));
         code.invokevirtual(CodegenUtils.p(PyFrame.class), "setlocal", CodegenUtils.sig(Void.TYPE, String.class, PyObject.class));
         if (classDoc != null) {
            this.loadFrame();
            code.ldc("__doc__");
            this.visit(classDoc);
            code.invokevirtual(CodegenUtils.p(PyFrame.class), "setlocal", CodegenUtils.sig(Void.TYPE, String.class, PyObject.class));
         }
      }

      Label genswitch = new Label();
      if (this.my_scope.generator) {
         code.goto_(genswitch);
      }

      Label start = new Label();
      code.label(start);
      int nparamcell = this.my_scope.jy_paramcells.size();
      if (nparamcell > 0) {
         List paramcells = this.my_scope.jy_paramcells;

         for(int i = 0; i < nparamcell; ++i) {
            code.aload(1);
            SymInfo syminf = (SymInfo)this.tbl.get(paramcells.get(i));
            code.iconst(syminf.locals_index);
            code.iconst(syminf.env_index);
            code.invokevirtual(CodegenUtils.p(PyFrame.class), "to_cell", CodegenUtils.sig(Void.TYPE, Integer.TYPE, Integer.TYPE));
         }
      }

      this.optimizeGlobals = checkOptimizeGlobals(fast_locals, this.my_scope);
      if (this.my_scope.max_with_count > 0) {
         this.loadFrame();
         code.iconst(this.my_scope.max_with_count);
         code.anewarray(CodegenUtils.p(PyObject.class));
         code.putfield(CodegenUtils.p(PyFrame.class), "f_exits", CodegenUtils.ci(PyObject[].class));
      }

      Object exit = this.visit(node);
      if (classBody) {
         this.loadFrame();
         code.invokevirtual(CodegenUtils.p(PyFrame.class), "getf_locals", CodegenUtils.sig(PyObject.class));
         code.areturn();
      } else if (exit == null) {
         this.setLastI(-1);
         this.getNone();
         code.areturn();
      }

      if (this.my_scope.generator) {
         code.label(genswitch);
         code.aload(1);
         code.getfield(CodegenUtils.p(PyFrame.class), "f_lasti", "I");
         Label[] y = new Label[this.yields.size() + 1];
         y[0] = start;

         for(int i = 1; i < y.length; ++i) {
            y[i] = (Label)this.yields.get(i - 1);
         }

         code.tableswitch(0, y.length - 1, start, y);
      }

   }

   public Object visitInteractive(Interactive node) throws Exception {
      this.traverse(node);
      return null;
   }

   public Object visitModule(org.python.antlr.ast.Module suite) throws Exception {
      Str docStr = this.getDocStr(suite.getInternalBody());
      if (docStr != null) {
         this.loadFrame();
         this.code.ldc("__doc__");
         this.visit(docStr);
         this.code.invokevirtual(CodegenUtils.p(PyFrame.class), "setglobal", CodegenUtils.sig(Void.TYPE, String.class, PyObject.class));
      }

      this.traverse(suite);
      return null;
   }

   public Object visitExpression(Expression node) throws Exception {
      if (this.my_scope.generator && node.getInternalBody() != null) {
         this.module.error("'return' with argument inside generator", true, node);
      }

      return this.visitReturn(new Return(node, node.getInternalBody()), true);
   }

   public void loadArray(Code code, List nodes) throws Exception {
      int n;
      if (nodes == null) {
         n = 0;
      } else {
         n = nodes.size();
      }

      if (n == 0) {
         code.getstatic(CodegenUtils.p(Py.class), "EmptyObjects", CodegenUtils.ci(PyObject[].class));
      } else if (!this.module.emitPrimitiveArraySetters(nodes, code)) {
         code.iconst(n);
         code.anewarray(CodegenUtils.p(PyObject.class));

         for(int i = 0; i < n; ++i) {
            code.dup();
            code.iconst(i);
            this.visit((PythonTree)nodes.get(i));
            code.aastore();
         }

      }
   }

   public int makeArray(List nodes) throws Exception {
      int n;
      if (nodes == null) {
         n = 0;
      } else {
         n = nodes.size();
      }

      int array = this.code.getLocal(CodegenUtils.ci(PyObject[].class));
      if (n == 0) {
         this.code.getstatic(CodegenUtils.p(Py.class), "EmptyObjects", CodegenUtils.ci(PyObject[].class));
         this.code.astore(array);
      } else {
         this.code.iconst(n);
         this.code.anewarray(CodegenUtils.p(PyObject.class));
         this.code.astore(array);

         for(int i = 0; i < n; ++i) {
            this.visit((PythonTree)nodes.get(i));
            this.code.aload(array);
            this.code.swap();
            this.code.iconst(i);
            this.code.swap();
            this.code.aastore();
         }
      }

      return array;
   }

   public void freeArray(int array) {
      this.code.aload(array);
      this.code.aconst_null();
      this.code.invokestatic(CodegenUtils.p(Arrays.class), "fill", CodegenUtils.sig(Void.TYPE, Object[].class, Object.class));
      this.code.freeLocal(array);
   }

   public void freeArrayRef(int array) {
      this.code.aconst_null();
      this.code.astore(array);
      this.code.freeLocal(array);
   }

   public Str getDocStr(List suite) {
      if (suite.size() > 0) {
         stmt stmt = (stmt)suite.get(0);
         if (stmt instanceof Expr && ((Expr)stmt).getInternalValue() instanceof Str) {
            return (Str)((Expr)stmt).getInternalValue();
         }
      }

      return null;
   }

   public boolean makeClosure(ScopeInfo scope) throws Exception {
      if (scope != null && scope.freevars != null) {
         int n = scope.freevars.size();
         if (n == 0) {
            return false;
         } else {
            int tmp = this.code.getLocal(CodegenUtils.ci(PyObject[].class));
            this.code.iconst(n);
            this.code.anewarray(CodegenUtils.p(PyObject.class));
            this.code.astore(tmp);
            Map upTbl = scope.up.tbl;

            for(int i = 0; i < n; ++i) {
               this.code.aload(tmp);
               this.code.iconst(i);
               this.loadFrame();

               for(int j = 1; j < scope.distance; ++j) {
                  this.loadf_back();
               }

               SymInfo symInfo = (SymInfo)upTbl.get(scope.freevars.elementAt(i));
               this.code.iconst(symInfo.env_index);
               this.code.invokevirtual(CodegenUtils.p(PyFrame.class), "getclosure", CodegenUtils.sig(PyObject.class, Integer.TYPE));
               this.code.aastore();
            }

            this.code.aload(tmp);
            this.code.freeLocal(tmp);
            return true;
         }
      } else {
         return false;
      }
   }

   public Object visitFunctionDef(FunctionDef node) throws Exception {
      String name = this.getName(node.getInternalName());
      this.setline(node);
      ScopeInfo scope = this.module.getScopeInfo(node);
      int defaults = this.makeArray(scope.ac.getDefaults());
      this.code.new_(CodegenUtils.p(PyFunction.class));
      this.code.dup();
      this.loadFrame();
      this.code.getfield(CodegenUtils.p(PyFrame.class), "f_globals", CodegenUtils.ci(PyObject.class));
      this.code.aload(defaults);
      this.code.freeLocal(defaults);
      scope.setup_closure();
      scope.dump();
      this.module.codeConstant(new Suite(node, node.getInternalBody()), name, true, this.className, false, false, node.getLine(), scope, this.cflags).get(this.code);
      Str docStr = this.getDocStr(node.getInternalBody());
      if (docStr != null) {
         this.visit(docStr);
      } else {
         this.code.aconst_null();
      }

      if (!this.makeClosure(scope)) {
         this.code.invokespecial(CodegenUtils.p(PyFunction.class), "<init>", CodegenUtils.sig(Void.TYPE, PyObject.class, PyObject[].class, PyCode.class, PyObject.class));
      } else {
         this.code.invokespecial(CodegenUtils.p(PyFunction.class), "<init>", CodegenUtils.sig(Void.TYPE, PyObject.class, PyObject[].class, PyCode.class, PyObject.class, PyObject[].class));
      }

      this.applyDecorators(node.getInternalDecorator_list());
      this.set(new Name(node, node.getInternalName(), expr_contextType.Store));
      return null;
   }

   private void applyDecorators(List decorators) throws Exception {
      if (decorators != null && !decorators.isEmpty()) {
         int res = this.storeTop();
         Iterator var3 = decorators.iterator();

         while(var3.hasNext()) {
            expr decorator = (expr)var3.next();
            this.visit(decorator);
            this.stackProduce();
         }

         for(int i = decorators.size(); i > 0; --i) {
            this.stackConsume();
            this.loadThreadState();
            this.code.aload(res);
            this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__call__", CodegenUtils.sig(PyObject.class, ThreadState.class, PyObject.class));
            this.code.astore(res);
         }

         this.code.aload(res);
         this.code.freeLocal(res);
      }

   }

   public Object visitExpr(Expr node) throws Exception {
      this.setline(node);
      this.visit(node.getInternalValue());
      if (this.print_results) {
         this.code.invokestatic(CodegenUtils.p(Py.class), "printResult", CodegenUtils.sig(Void.TYPE, PyObject.class));
      } else {
         this.code.pop();
      }

      return null;
   }

   public Object visitAssign(Assign node) throws Exception {
      this.setline(node);
      this.visit(node.getInternalValue());
      if (node.getInternalTargets().size() == 1) {
         this.set((PythonTree)node.getInternalTargets().get(0));
      } else {
         int tmp = this.storeTop();
         Iterator var3 = node.getInternalTargets().iterator();

         while(var3.hasNext()) {
            expr target = (expr)var3.next();
            this.set(target, tmp);
         }

         this.code.freeLocal(tmp);
      }

      return null;
   }

   public Object visitPrint(Print node) throws Exception {
      this.setline(node);
      int tmp = -1;
      if (node.getInternalDest() != null) {
         this.visit(node.getInternalDest());
         tmp = this.storeTop();
      }

      if (node.getInternalValues() != null && node.getInternalValues().size() != 0) {
         for(int i = 0; i < node.getInternalValues().size(); ++i) {
            if (node.getInternalDest() != null) {
               this.code.aload(tmp);
               this.visit((PythonTree)node.getInternalValues().get(i));
               if (node.getInternalNl() && i == node.getInternalValues().size() - 1) {
                  this.code.invokestatic(CodegenUtils.p(Py.class), "println", CodegenUtils.sig(Void.TYPE, PyObject.class, PyObject.class));
               } else {
                  this.code.invokestatic(CodegenUtils.p(Py.class), "printComma", CodegenUtils.sig(Void.TYPE, PyObject.class, PyObject.class));
               }
            } else {
               this.visit((PythonTree)node.getInternalValues().get(i));
               if (node.getInternalNl() && i == node.getInternalValues().size() - 1) {
                  this.code.invokestatic(CodegenUtils.p(Py.class), "println", CodegenUtils.sig(Void.TYPE, PyObject.class));
               } else {
                  this.code.invokestatic(CodegenUtils.p(Py.class), "printComma", CodegenUtils.sig(Void.TYPE, PyObject.class));
               }
            }
         }
      } else if (node.getInternalDest() != null) {
         this.code.aload(tmp);
         this.code.invokestatic(CodegenUtils.p(Py.class), "printlnv", CodegenUtils.sig(Void.TYPE, PyObject.class));
      } else {
         this.code.invokestatic(CodegenUtils.p(Py.class), "println", CodegenUtils.sig(Void.TYPE));
      }

      if (node.getInternalDest() != null) {
         this.code.freeLocal(tmp);
      }

      return null;
   }

   public Object visitDelete(Delete node) throws Exception {
      this.setline(node);
      this.traverse(node);
      return null;
   }

   public Object visitPass(Pass node) throws Exception {
      this.setline(node);
      return null;
   }

   public Object visitBreak(Break node) throws Exception {
      if (this.breakLabels.empty()) {
         throw new ParseException("'break' outside loop", node);
      } else {
         this.doFinallysDownTo(this.bcfLevel);
         this.code.goto_((Label)this.breakLabels.peek());
         return null;
      }
   }

   public Object visitContinue(Continue node) throws Exception {
      if (this.continueLabels.empty()) {
         throw new ParseException("'continue' not properly in loop", node);
      } else {
         this.doFinallysDownTo(this.bcfLevel);
         this.code.goto_((Label)this.continueLabels.peek());
         return Exit;
      }
   }

   public Object visitYield(Yield node) throws Exception {
      this.setline(node);
      if (!this.fast_locals) {
         throw new ParseException("'yield' outside function", node);
      } else {
         int stackState = this.saveStack();
         if (node.getInternalValue() != null) {
            this.visit(node.getInternalValue());
         } else {
            this.getNone();
         }

         this.setLastI(++this.yield_count);
         this.saveLocals();
         this.code.areturn();
         Label restart = new Label();
         this.yields.addElement(restart);
         this.code.label(restart);
         this.restoreLocals();
         this.restoreStack(stackState);
         this.loadFrame();
         this.code.invokevirtual(CodegenUtils.p(PyFrame.class), "getGeneratorInput", CodegenUtils.sig(Object.class));
         this.code.dup();
         this.code.instanceof_(CodegenUtils.p(PyException.class));
         Label done2 = new Label();
         this.code.ifeq(done2);
         this.code.checkcast(CodegenUtils.p(Throwable.class));
         this.code.athrow();
         this.code.label(done2);
         this.code.checkcast(CodegenUtils.p(PyObject.class));
         return null;
      }
   }

   private void stackProduce() {
      this.stackProduce(CodegenUtils.p(PyObject.class));
   }

   private void stackProduce(String signature) {
      this.stack.push(signature);
   }

   private void stackConsume() {
      this.stackConsume(1);
   }

   private void stackConsume(int numItems) {
      for(int i = 0; i < numItems; ++i) {
         this.stack.pop();
      }

   }

   private int saveStack() throws Exception {
      if (this.stack.size() > 0) {
         int array = this.code.getLocal(CodegenUtils.ci(Object[].class));
         this.code.iconst(this.stack.size());
         this.code.anewarray(CodegenUtils.p(Object.class));
         this.code.astore(array);
         ListIterator content = this.stack.listIterator(this.stack.size());

         for(int i = 0; content.hasPrevious(); ++i) {
            String signature = (String)content.previous();
            if (CodegenUtils.p(ThreadState.class).equals(signature)) {
               this.code.pop();
            } else {
               this.code.aload(array);
               this.code.swap();
               this.code.iconst(i++);
               this.code.swap();
               this.code.aastore();
            }
         }

         return array;
      } else {
         return -1;
      }
   }

   private void restoreStack(int array) throws Exception {
      if (this.stack.size() > 0) {
         int i = this.stack.size() - 1;
         Iterator var3 = this.stack.iterator();

         while(var3.hasNext()) {
            String signature = (String)var3.next();
            if (CodegenUtils.p(ThreadState.class).equals(signature)) {
               this.loadThreadState();
            } else {
               this.code.aload(array);
               this.code.iconst(i--);
               this.code.aaload();
               this.code.checkcast(signature);
            }
         }

         this.code.freeLocal(array);
      }

   }

   private void restoreLocals() throws Exception {
      this.endExceptionHandlers();
      Vector v = this.code.getActiveLocals();
      this.loadFrame();
      this.code.getfield(CodegenUtils.p(PyFrame.class), "f_savedlocals", CodegenUtils.ci(Object[].class));
      int locals = this.code.getLocal(CodegenUtils.ci(Object[].class));
      this.code.astore(locals);

      for(int i = 0; i < v.size(); ++i) {
         String type = (String)v.elementAt(i);
         if (type != null) {
            this.code.aload(locals);
            this.code.iconst(i);
            this.code.aaload();
            this.code.checkcast(type);
            this.code.astore(i);
         }
      }

      this.code.freeLocal(locals);
      this.restartExceptionHandlers();
   }

   private void endExceptionHandlers() {
      Label end = new Label();
      this.code.label(end);

      for(int i = 0; i < this.exceptionHandlers.size(); ++i) {
         ExceptionHandler handler = (ExceptionHandler)this.exceptionHandlers.elementAt(i);
         handler.exceptionEnds.addElement(end);
      }

   }

   private void restartExceptionHandlers() {
      Label start = new Label();
      this.code.label(start);

      for(int i = 0; i < this.exceptionHandlers.size(); ++i) {
         ExceptionHandler handler = (ExceptionHandler)this.exceptionHandlers.elementAt(i);
         handler.exceptionStarts.addElement(start);
      }

   }

   private void saveLocals() throws Exception {
      Vector v = this.code.getActiveLocals();
      this.code.iconst(v.size());
      this.code.anewarray(CodegenUtils.p(Object.class));
      int locals = this.code.getLocal(CodegenUtils.ci(Object[].class));
      this.code.astore(locals);

      for(int i = 0; i < v.size(); ++i) {
         String type = (String)v.elementAt(i);
         if (type != null) {
            this.code.aload(locals);
            this.code.iconst(i);
            if (i == 2222) {
               this.code.aconst_null();
            } else {
               this.code.aload(i);
            }

            this.code.aastore();
         }
      }

      this.loadFrame();
      this.code.aload(locals);
      this.code.putfield(CodegenUtils.p(PyFrame.class), "f_savedlocals", CodegenUtils.ci(Object[].class));
      this.code.freeLocal(locals);
   }

   public Object visitReturn(Return node) throws Exception {
      return this.visitReturn(node, false);
   }

   public Object visitReturn(Return node, boolean inEval) throws Exception {
      this.setline(node);
      if (!inEval && !this.fast_locals) {
         throw new ParseException("'return' outside function", node);
      } else {
         int tmp = 0;
         if (node.getInternalValue() != null) {
            if (this.my_scope.generator && !(node instanceof LambdaSyntheticReturn)) {
               throw new ParseException("'return' with argument inside generator", node);
            }

            this.visit(node.getInternalValue());
            tmp = this.code.getReturnLocal();
            this.code.astore(tmp);
         }

         this.doFinallysDownTo(0);
         this.setLastI(-1);
         if (node.getInternalValue() != null) {
            this.code.aload(tmp);
         } else {
            this.getNone();
         }

         this.code.areturn();
         return Exit;
      }
   }

   public Object visitRaise(Raise node) throws Exception {
      this.setline(node);
      if (node.getInternalType() != null) {
         this.visit(node.getInternalType());
         this.stackProduce();
      }

      if (node.getInternalInst() != null) {
         this.visit(node.getInternalInst());
         this.stackProduce();
      }

      if (node.getInternalTback() != null) {
         this.visit(node.getInternalTback());
         this.stackProduce();
      }

      if (node.getInternalType() == null) {
         this.code.invokestatic(CodegenUtils.p(Py.class), "makeException", CodegenUtils.sig(PyException.class));
      } else if (node.getInternalInst() == null) {
         this.stackConsume();
         this.code.invokestatic(CodegenUtils.p(Py.class), "makeException", CodegenUtils.sig(PyException.class, PyObject.class));
      } else if (node.getInternalTback() == null) {
         this.stackConsume(2);
         this.code.invokestatic(CodegenUtils.p(Py.class), "makeException", CodegenUtils.sig(PyException.class, PyObject.class, PyObject.class));
      } else {
         this.stackConsume(3);
         this.code.invokestatic(CodegenUtils.p(Py.class), "makeException", CodegenUtils.sig(PyException.class, PyObject.class, PyObject.class, PyObject.class));
      }

      this.code.athrow();
      return Exit;
   }

   private int impliedImportLevel(int level) {
      return level == 0 && !this.module.getFutures().isAbsoluteImportOn() ? -1 : level;
   }

   public Object visitImport(Import node) throws Exception {
      this.setline(node);

      alias a;
      String asname;
      for(Iterator var2 = node.getInternalNames().iterator(); var2.hasNext(); this.set(new Name(a, asname, expr_contextType.Store))) {
         a = (alias)var2.next();
         asname = null;
         String name;
         if (a.getInternalAsname() != null) {
            name = a.getInternalName();
            asname = a.getInternalAsname();
            this.code.ldc(name);
            this.loadFrame();
            this.code.iconst(this.impliedImportLevel(0));
            this.code.invokestatic(CodegenUtils.p(imp.class), "importOneAs", CodegenUtils.sig(PyObject.class, String.class, PyFrame.class, Integer.TYPE));
         } else {
            name = a.getInternalName();
            asname = name;
            if (name.indexOf(46) > 0) {
               asname = name.substring(0, name.indexOf(46));
            }

            this.code.ldc(name);
            this.loadFrame();
            this.code.iconst(this.impliedImportLevel(0));
            this.code.invokestatic(CodegenUtils.p(imp.class), "importOne", CodegenUtils.sig(PyObject.class, String.class, PyFrame.class, Integer.TYPE));
         }
      }

      return null;
   }

   public Object visitImportFrom(ImportFrom node) throws Exception {
      Future.checkFromFuture(node);
      this.setline(node);
      this.code.ldc(node.getInternalModule());
      List aliases = node.getInternalNames();
      if (aliases != null && aliases.size() != 0) {
         if (aliases.size() == 1 && ((alias)aliases.get(0)).getInternalName().equals("*")) {
            if (this.my_scope.func_level > 0) {
               this.module.error("import * only allowed at module level", false, node);
               if (this.my_scope.contains_ns_free_vars) {
                  this.module.error("import * is not allowed in function '" + this.my_scope.scope_name + "' because it contains a nested function with free variables", true, node);
               }
            }

            if (this.my_scope.func_level > 1) {
               this.module.error("import * is not allowed in function '" + this.my_scope.scope_name + "' because it is a nested function", true, node);
            }

            this.loadFrame();
            this.code.iconst(this.impliedImportLevel(node.getInternalLevel()));
            this.code.invokestatic(CodegenUtils.p(imp.class), "importAll", CodegenUtils.sig(Void.TYPE, String.class, PyFrame.class, Integer.TYPE));
         } else {
            List fromNames = new ArrayList();
            List asnames = new ArrayList();

            int i;
            for(i = 0; i < aliases.size(); ++i) {
               fromNames.add(((alias)aliases.get(i)).getInternalName());
               asnames.add(((alias)aliases.get(i)).getInternalAsname());
               if (asnames.get(i) == null) {
                  asnames.set(i, fromNames.get(i));
               }
            }

            i = makeStrings(this.code, fromNames);
            this.code.aload(i);
            this.code.freeLocal(i);
            this.loadFrame();
            this.code.iconst(this.impliedImportLevel(node.getInternalLevel()));
            this.code.invokestatic(CodegenUtils.p(imp.class), "importFrom", CodegenUtils.sig(PyObject[].class, String.class, String[].class, PyFrame.class, Integer.TYPE));
            int tmp = this.storeTop();

            for(int i = 0; i < aliases.size(); ++i) {
               this.code.aload(tmp);
               this.code.iconst(i);
               this.code.aaload();
               this.set(new Name((PythonTree)aliases.get(i), (String)asnames.get(i), expr_contextType.Store));
            }

            this.code.freeLocal(tmp);
         }

         return null;
      } else {
         throw new ParseException("Internel parser error", node);
      }
   }

   public Object visitGlobal(Global node) throws Exception {
      return null;
   }

   public Object visitExec(Exec node) throws Exception {
      this.setline(node);
      this.visit(node.getInternalBody());
      this.stackProduce();
      if (node.getInternalGlobals() != null) {
         this.visit(node.getInternalGlobals());
      } else {
         this.code.aconst_null();
      }

      this.stackProduce();
      if (node.getInternalLocals() != null) {
         this.visit(node.getInternalLocals());
      } else {
         this.code.aconst_null();
      }

      this.stackProduce();
      this.stackConsume(3);
      this.code.invokestatic(CodegenUtils.p(Py.class), "exec", CodegenUtils.sig(Void.TYPE, PyObject.class, PyObject.class, PyObject.class));
      return null;
   }

   public Object visitAssert(Assert node) throws Exception {
      this.setline(node);
      Label end_of_assert = new Label();
      this.loadFrame();
      this.emitGetGlobal("__debug__");
      this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__nonzero__", CodegenUtils.sig(Boolean.TYPE));
      this.code.ifeq(end_of_assert);
      this.visit(node.getInternalTest());
      this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__nonzero__", CodegenUtils.sig(Boolean.TYPE));
      this.code.ifne(end_of_assert);
      if (node.getInternalMsg() != null) {
         this.visit(node.getInternalMsg());
      } else {
         this.getNone();
      }

      this.loadFrame();
      this.emitGetGlobal("AssertionError");
      this.code.swap();
      this.code.invokestatic(CodegenUtils.p(Py.class), "makeException", CodegenUtils.sig(PyException.class, PyObject.class, PyObject.class));
      this.code.athrow();
      this.code.label(end_of_assert);
      return null;
   }

   public Object doTest(Label end_of_if, If node, int index) throws Exception {
      Label end_of_suite = new Label();
      this.setline(node.getInternalTest());
      this.visit(node.getInternalTest());
      this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__nonzero__", CodegenUtils.sig(Boolean.TYPE));
      this.code.ifeq(end_of_suite);
      Object exit = this.suite(node.getInternalBody());
      if (end_of_if != null && exit == null) {
         this.code.goto_(end_of_if);
      }

      this.code.label(end_of_suite);
      if (node.getInternalOrelse() != null) {
         return this.suite(node.getInternalOrelse()) != null ? exit : null;
      } else {
         return null;
      }
   }

   public Object visitIf(If node) throws Exception {
      Label end_of_if = null;
      if (node.getInternalOrelse() != null) {
         end_of_if = new Label();
      }

      Object exit = this.doTest(end_of_if, node, 0);
      if (end_of_if != null) {
         this.code.label(end_of_if);
      }

      return exit;
   }

   public Object visitIfExp(IfExp node) throws Exception {
      this.setline(node.getInternalTest());
      Label end = new Label();
      Label end_of_else = new Label();
      this.visit(node.getInternalTest());
      this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__nonzero__", CodegenUtils.sig(Boolean.TYPE));
      this.code.ifeq(end_of_else);
      this.visit(node.getInternalBody());
      this.code.goto_(end);
      this.code.label(end_of_else);
      this.visit(node.getInternalOrelse());
      this.code.label(end);
      return null;
   }

   public int beginLoop() {
      this.continueLabels.push(new Label());
      this.breakLabels.push(new Label());
      int savebcf = this.bcfLevel;
      this.bcfLevel = this.exceptionHandlers.size();
      return savebcf;
   }

   public void finishLoop(int savebcf) {
      this.continueLabels.pop();
      this.breakLabels.pop();
      this.bcfLevel = savebcf;
   }

   public Object visitWhile(While node) throws Exception {
      int savebcf = this.beginLoop();
      Label continue_loop = (Label)this.continueLabels.peek();
      Label break_loop = (Label)this.breakLabels.peek();
      Label start_loop = new Label();
      this.code.goto_(continue_loop);
      this.code.label(start_loop);
      this.suite(node.getInternalBody());
      this.code.label(continue_loop);
      this.setline(node);
      this.visit(node.getInternalTest());
      this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__nonzero__", CodegenUtils.sig(Boolean.TYPE));
      this.code.ifne(start_loop);
      this.finishLoop(savebcf);
      if (node.getInternalOrelse() != null) {
         this.suite(node.getInternalOrelse());
      }

      this.code.label(break_loop);
      return null;
   }

   public Object visitFor(For node) throws Exception {
      int savebcf = this.beginLoop();
      Label continue_loop = (Label)this.continueLabels.peek();
      Label break_loop = (Label)this.breakLabels.peek();
      Label start_loop = new Label();
      Label next_loop = new Label();
      this.setline(node);
      this.visit(node.getInternalIter());
      int iter_tmp = this.code.getLocal(CodegenUtils.p(PyObject.class));
      int expr_tmp = this.code.getLocal(CodegenUtils.p(PyObject.class));
      this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__iter__", CodegenUtils.sig(PyObject.class));
      this.code.astore(iter_tmp);
      this.code.goto_(next_loop);
      this.code.label(start_loop);
      this.set(node.getInternalTarget(), expr_tmp);
      this.suite(node.getInternalBody());
      this.code.label(continue_loop);
      this.code.label(next_loop);
      this.setline(node);
      this.code.aload(iter_tmp);
      this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__iternext__", CodegenUtils.sig(PyObject.class));
      this.code.astore(expr_tmp);
      this.code.aload(expr_tmp);
      this.code.ifnonnull(start_loop);
      this.finishLoop(savebcf);
      if (node.getInternalOrelse() != null) {
         this.suite(node.getInternalOrelse());
      }

      this.code.label(break_loop);
      this.code.freeLocal(iter_tmp);
      this.code.freeLocal(expr_tmp);
      return null;
   }

   public void exceptionTest(int exc, Label end_of_exceptions, TryExcept node, int index) throws Exception {
      for(int i = 0; i < node.getInternalHandlers().size(); ++i) {
         ExceptHandler handler = (ExceptHandler)node.getInternalHandlers().get(i);
         Label end_of_self = new Label();
         if (handler.getInternalType() != null) {
            this.code.aload(exc);
            this.visit(handler.getInternalType());
            this.code.invokevirtual(CodegenUtils.p(PyException.class), "match", CodegenUtils.sig(Boolean.TYPE, PyObject.class));
            this.code.ifeq(end_of_self);
         } else if (i != node.getInternalHandlers().size() - 1) {
            throw new ParseException("default 'except:' must be last", handler);
         }

         if (handler.getInternalName() != null) {
            this.code.aload(exc);
            this.code.getfield(CodegenUtils.p(PyException.class), "value", CodegenUtils.ci(PyObject.class));
            this.set(handler.getInternalName());
         }

         this.suite(handler.getInternalBody());
         this.code.goto_(end_of_exceptions);
         this.code.label(end_of_self);
      }

      this.code.aload(exc);
      this.code.athrow();
   }

   public Object visitTryFinally(TryFinally node) throws Exception {
      Label start = new Label();
      Label end = new Label();
      Label handlerStart = new Label();
      Label finallyEnd = new Label();
      ExceptionHandler inFinally = new ExceptionHandler(node);
      this.exceptionHandlers.push(inFinally);
      int excLocal = this.code.getLocal(CodegenUtils.p(Throwable.class));
      this.code.aconst_null();
      this.code.astore(excLocal);
      this.code.label(start);
      inFinally.exceptionStarts.addElement(start);
      Object ret = this.suite(node.getInternalBody());
      this.code.label(end);
      inFinally.exceptionEnds.addElement(end);
      inFinally.bodyDone = true;
      this.exceptionHandlers.pop();
      if (ret == NoExit) {
         this.inlineFinally(inFinally);
         this.code.goto_(finallyEnd);
      }

      this.code.label(handlerStart);
      this.code.astore(excLocal);
      this.code.aload(excLocal);
      this.loadFrame();
      this.code.invokestatic(CodegenUtils.p(Py.class), "addTraceback", CodegenUtils.sig(Void.TYPE, Throwable.class, PyFrame.class));
      this.inlineFinally(inFinally);
      this.code.aload(excLocal);
      this.code.checkcast(CodegenUtils.p(Throwable.class));
      this.code.athrow();
      this.code.label(finallyEnd);
      this.code.freeLocal(excLocal);
      inFinally.addExceptionHandlers(handlerStart);
      return null;
   }

   private void inlineFinally(ExceptionHandler handler) throws Exception {
      if (!handler.bodyDone) {
         Label end = new Label();
         this.code.label(end);
         handler.exceptionEnds.addElement(end);
      }

      if (handler.isFinallyHandler()) {
         handler.finalBody(this);
      }

   }

   private void reenterProtectedBody(ExceptionHandler handler) throws Exception {
      Label restart = new Label();
      this.code.label(restart);
      handler.exceptionStarts.addElement(restart);
   }

   private void doFinallysDownTo(int level) throws Exception {
      Stack poppedHandlers = new Stack();

      ExceptionHandler handler;
      while(this.exceptionHandlers.size() > level) {
         handler = (ExceptionHandler)this.exceptionHandlers.pop();
         this.inlineFinally(handler);
         poppedHandlers.push(handler);
      }

      while(poppedHandlers.size() > 0) {
         handler = (ExceptionHandler)poppedHandlers.pop();
         this.reenterProtectedBody(handler);
         this.exceptionHandlers.push(handler);
      }

   }

   public Object visitTryExcept(TryExcept node) throws Exception {
      Label start = new Label();
      Label end = new Label();
      Label handler_start = new Label();
      Label handler_end = new Label();
      ExceptionHandler handler = new ExceptionHandler();
      this.code.label(start);
      handler.exceptionStarts.addElement(start);
      this.exceptionHandlers.push(handler);
      Object exit = this.suite(node.getInternalBody());
      this.exceptionHandlers.pop();
      this.code.label(end);
      handler.exceptionEnds.addElement(end);
      if (exit == null) {
         this.code.goto_(handler_end);
      }

      this.code.label(handler_start);
      this.loadFrame();
      this.code.invokestatic(CodegenUtils.p(Py.class), "setException", CodegenUtils.sig(PyException.class, Throwable.class, PyFrame.class));
      int exc = this.code.getFinallyLocal(CodegenUtils.p(Throwable.class));
      this.code.astore(exc);
      if (node.getInternalOrelse() == null) {
         this.exceptionTest(exc, handler_end, node, 1);
         this.code.label(handler_end);
      } else {
         Label else_end = new Label();
         this.exceptionTest(exc, else_end, node, 1);
         this.code.label(handler_end);
         this.suite(node.getInternalOrelse());
         this.code.label(else_end);
      }

      this.code.freeFinallyLocal(exc);
      handler.addExceptionHandlers(handler_start);
      return null;
   }

   public Object visitSuite(Suite node) throws Exception {
      return this.suite(node.getInternalBody());
   }

   public Object suite(List stmts) throws Exception {
      Iterator var2 = stmts.iterator();

      Object exit;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         stmt s = (stmt)var2.next();
         exit = this.visit(s);
      } while(exit == null);

      return Exit;
   }

   public Object visitBoolOp(BoolOp node) throws Exception {
      Label end = new Label();
      this.visit((PythonTree)node.getInternalValues().get(0));

      for(int i = 1; i < node.getInternalValues().size(); ++i) {
         this.code.dup();
         this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__nonzero__", CodegenUtils.sig(Boolean.TYPE));
         switch (node.getInternalOp()) {
            case Or:
               this.code.ifne(end);
               break;
            case And:
               this.code.ifeq(end);
         }

         this.code.pop();
         this.visit((PythonTree)node.getInternalValues().get(i));
      }

      this.code.label(end);
      return null;
   }

   public Object visitCompare(Compare node) throws Exception {
      int last = this.code.getLocal(CodegenUtils.p(PyObject.class));
      int result = this.code.getLocal(CodegenUtils.p(PyObject.class));
      Label end = new Label();
      this.visit(node.getInternalLeft());
      this.code.astore(last);
      int n = node.getInternalOps().size();

      for(int i = 0; i < n - 1; ++i) {
         this.visit((PythonTree)node.getInternalComparators().get(i));
         this.code.aload(last);
         this.code.swap();
         this.code.dup();
         this.code.astore(last);
         this.visitCmpop((cmpopType)node.getInternalOps().get(i));
         this.code.dup();
         this.code.astore(result);
         this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__nonzero__", CodegenUtils.sig(Boolean.TYPE));
         this.code.ifeq(end);
      }

      this.visit((PythonTree)node.getInternalComparators().get(n - 1));
      this.code.aload(last);
      this.code.swap();
      this.visitCmpop((cmpopType)node.getInternalOps().get(n - 1));
      if (n > 1) {
         this.code.astore(result);
         this.code.label(end);
         this.code.aload(result);
      }

      this.code.aconst_null();
      this.code.astore(last);
      this.code.freeLocal(last);
      this.code.freeLocal(result);
      return null;
   }

   public void visitCmpop(cmpopType op) throws Exception {
      String name = null;
      switch (op) {
         case Eq:
            name = "_eq";
            break;
         case NotEq:
            name = "_ne";
            break;
         case Lt:
            name = "_lt";
            break;
         case LtE:
            name = "_le";
            break;
         case Gt:
            name = "_gt";
            break;
         case GtE:
            name = "_ge";
            break;
         case Is:
            name = "_is";
            break;
         case IsNot:
            name = "_isnot";
            break;
         case In:
            name = "_in";
            break;
         case NotIn:
            name = "_notin";
      }

      this.code.invokevirtual(CodegenUtils.p(PyObject.class), name, CodegenUtils.sig(PyObject.class, PyObject.class));
   }

   public Object visitBinOp(BinOp node) throws Exception {
      this.visit(node.getInternalLeft());
      this.stackProduce();
      this.visit(node.getInternalRight());
      this.stackConsume();
      String name = null;
      switch (node.getInternalOp()) {
         case Add:
            name = "_add";
            break;
         case Sub:
            name = "_sub";
            break;
         case Mult:
            name = "_mul";
            break;
         case Div:
            name = "_div";
            break;
         case Mod:
            name = "_mod";
            break;
         case Pow:
            name = "_pow";
            break;
         case LShift:
            name = "_lshift";
            break;
         case RShift:
            name = "_rshift";
            break;
         case BitOr:
            name = "_or";
            break;
         case BitXor:
            name = "_xor";
            break;
         case BitAnd:
            name = "_and";
            break;
         case FloorDiv:
            name = "_floordiv";
      }

      if (node.getInternalOp() == operatorType.Div && this.module.getFutures().areDivisionOn()) {
         name = "_truediv";
      }

      this.code.invokevirtual(CodegenUtils.p(PyObject.class), name, CodegenUtils.sig(PyObject.class, PyObject.class));
      return null;
   }

   public Object visitUnaryOp(UnaryOp node) throws Exception {
      this.visit(node.getInternalOperand());
      String name = null;
      switch (node.getInternalOp()) {
         case Invert:
            name = "__invert__";
            break;
         case Not:
            name = "__not__";
            break;
         case UAdd:
            name = "__pos__";
            break;
         case USub:
            name = "__neg__";
      }

      this.code.invokevirtual(CodegenUtils.p(PyObject.class), name, CodegenUtils.sig(PyObject.class));
      return null;
   }

   public Object visitAugAssign(AugAssign node) throws Exception {
      this.setline(node);
      this.augmode = expr_contextType.Load;
      this.visit(node.getInternalTarget());
      int target = this.storeTop();
      this.visit(node.getInternalValue());
      this.code.aload(target);
      this.code.swap();
      String name = null;
      switch (node.getInternalOp()) {
         case Add:
            name = "_iadd";
            break;
         case Sub:
            name = "_isub";
            break;
         case Mult:
            name = "_imul";
            break;
         case Div:
            name = "_idiv";
            break;
         case Mod:
            name = "_imod";
            break;
         case Pow:
            name = "_ipow";
            break;
         case LShift:
            name = "_ilshift";
            break;
         case RShift:
            name = "_irshift";
            break;
         case BitOr:
            name = "_ior";
            break;
         case BitXor:
            name = "_ixor";
            break;
         case BitAnd:
            name = "_iand";
            break;
         case FloorDiv:
            name = "_ifloordiv";
      }

      if (node.getInternalOp() == operatorType.Div && this.module.getFutures().areDivisionOn()) {
         name = "_itruediv";
      }

      this.code.invokevirtual(CodegenUtils.p(PyObject.class), name, CodegenUtils.sig(PyObject.class, PyObject.class));
      this.code.freeLocal(target);
      this.temporary = this.storeTop();
      this.augmode = expr_contextType.Store;
      this.visit(node.getInternalTarget());
      this.code.freeLocal(this.temporary);
      return null;
   }

   static int makeStrings(Code c, Collection names) throws IOException {
      if (names != null) {
         c.iconst(names.size());
      } else {
         c.iconst_0();
      }

      c.anewarray(CodegenUtils.p(String.class));
      int strings = c.getLocal(CodegenUtils.ci(String[].class));
      c.astore(strings);
      if (names != null) {
         int i = 0;

         for(Iterator var4 = names.iterator(); var4.hasNext(); ++i) {
            String name = (String)var4.next();
            c.aload(strings);
            c.iconst(i);
            c.ldc(name);
            c.aastore();
         }
      }

      return strings;
   }

   public Object invokeNoKeywords(Attribute node, List values) throws Exception {
      String name = this.getName(node.getInternalAttr());
      this.visit(node.getInternalValue());
      this.stackProduce();
      this.code.ldc(name);
      this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__getattr__", CodegenUtils.sig(PyObject.class, String.class));
      this.loadThreadState();
      this.stackProduce(CodegenUtils.p(ThreadState.class));
      switch (values.size()) {
         case 0:
            this.stackConsume(2);
            this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__call__", CodegenUtils.sig(PyObject.class, ThreadState.class));
            break;
         case 1:
            this.visit((PythonTree)values.get(0));
            this.stackConsume(2);
            this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__call__", CodegenUtils.sig(PyObject.class, ThreadState.class, PyObject.class));
            break;
         case 2:
            this.visit((PythonTree)values.get(0));
            this.stackProduce();
            this.visit((PythonTree)values.get(1));
            this.stackConsume(3);
            this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__call__", CodegenUtils.sig(PyObject.class, ThreadState.class, PyObject.class, PyObject.class));
            break;
         case 3:
            this.visit((PythonTree)values.get(0));
            this.stackProduce();
            this.visit((PythonTree)values.get(1));
            this.stackProduce();
            this.visit((PythonTree)values.get(2));
            this.stackConsume(4);
            this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__call__", CodegenUtils.sig(PyObject.class, ThreadState.class, PyObject.class, PyObject.class, PyObject.class));
            break;
         case 4:
            this.visit((PythonTree)values.get(0));
            this.stackProduce();
            this.visit((PythonTree)values.get(1));
            this.stackProduce();
            this.visit((PythonTree)values.get(2));
            this.stackProduce();
            this.visit((PythonTree)values.get(3));
            this.stackConsume(5);
            this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__call__", CodegenUtils.sig(PyObject.class, ThreadState.class, PyObject.class, PyObject.class, PyObject.class, PyObject.class));
            break;
         default:
            int argArray = this.makeArray(values);
            this.code.aload(argArray);
            this.code.freeLocal(argArray);
            this.stackConsume(2);
            this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__call__", CodegenUtils.sig(PyObject.class, ThreadState.class, PyObject[].class));
      }

      return null;
   }

   public Object visitCall(Call node) throws Exception {
      List keys = new ArrayList();
      List values = new ArrayList();

      int argArray;
      for(argArray = 0; argArray < node.getInternalArgs().size(); ++argArray) {
         values.add(node.getInternalArgs().get(argArray));
      }

      for(argArray = 0; argArray < node.getInternalKeywords().size(); ++argArray) {
         keys.add(((keyword)node.getInternalKeywords().get(argArray)).getInternalArg());
         values.add(((keyword)node.getInternalKeywords().get(argArray)).getInternalValue());
      }

      if ((node.getInternalKeywords() == null || node.getInternalKeywords().size() == 0) && node.getInternalStarargs() == null && node.getInternalKwargs() == null && node.getInternalFunc() instanceof Attribute) {
         return this.invokeNoKeywords((Attribute)node.getInternalFunc(), values);
      } else {
         this.visit(node.getInternalFunc());
         this.stackProduce();
         int strArray;
         if (node.getInternalStarargs() == null && node.getInternalKwargs() == null) {
            if (keys.size() > 0) {
               this.loadThreadState();
               this.stackProduce(CodegenUtils.p(ThreadState.class));
               argArray = this.makeArray(values);
               strArray = makeStrings(this.code, keys);
               this.code.aload(argArray);
               this.code.aload(strArray);
               this.code.freeLocal(strArray);
               this.stackConsume(2);
               this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__call__", CodegenUtils.sig(PyObject.class, ThreadState.class, PyObject[].class, String[].class));
               this.freeArrayRef(argArray);
            } else {
               this.loadThreadState();
               this.stackProduce(CodegenUtils.p(ThreadState.class));
               switch (values.size()) {
                  case 0:
                     this.stackConsume(2);
                     this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__call__", CodegenUtils.sig(PyObject.class, ThreadState.class));
                     break;
                  case 1:
                     this.visit((PythonTree)values.get(0));
                     this.stackConsume(2);
                     this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__call__", CodegenUtils.sig(PyObject.class, ThreadState.class, PyObject.class));
                     break;
                  case 2:
                     this.visit((PythonTree)values.get(0));
                     this.stackProduce();
                     this.visit((PythonTree)values.get(1));
                     this.stackConsume(3);
                     this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__call__", CodegenUtils.sig(PyObject.class, ThreadState.class, PyObject.class, PyObject.class));
                     break;
                  case 3:
                     this.visit((PythonTree)values.get(0));
                     this.stackProduce();
                     this.visit((PythonTree)values.get(1));
                     this.stackProduce();
                     this.visit((PythonTree)values.get(2));
                     this.stackConsume(4);
                     this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__call__", CodegenUtils.sig(PyObject.class, ThreadState.class, PyObject.class, PyObject.class, PyObject.class));
                     break;
                  case 4:
                     this.visit((PythonTree)values.get(0));
                     this.stackProduce();
                     this.visit((PythonTree)values.get(1));
                     this.stackProduce();
                     this.visit((PythonTree)values.get(2));
                     this.stackProduce();
                     this.visit((PythonTree)values.get(3));
                     this.stackConsume(5);
                     this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__call__", CodegenUtils.sig(PyObject.class, ThreadState.class, PyObject.class, PyObject.class, PyObject.class, PyObject.class));
                     break;
                  default:
                     argArray = this.makeArray(values);
                     this.code.aload(argArray);
                     this.code.freeLocal(argArray);
                     this.stackConsume(2);
                     this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__call__", CodegenUtils.sig(PyObject.class, ThreadState.class, PyObject[].class));
               }
            }
         } else {
            argArray = this.makeArray(values);
            strArray = makeStrings(this.code, keys);
            if (node.getInternalStarargs() == null) {
               this.code.aconst_null();
            } else {
               this.visit(node.getInternalStarargs());
            }

            this.stackProduce();
            if (node.getInternalKwargs() == null) {
               this.code.aconst_null();
            } else {
               this.visit(node.getInternalKwargs());
            }

            this.stackProduce();
            this.code.aload(argArray);
            this.code.aload(strArray);
            this.code.freeLocal(strArray);
            this.code.dup2_x2();
            this.code.pop2();
            this.stackConsume(3);
            this.code.invokevirtual(CodegenUtils.p(PyObject.class), "_callextra", CodegenUtils.sig(PyObject.class, PyObject[].class, String[].class, PyObject.class, PyObject.class));
            this.freeArrayRef(argArray);
         }

         return null;
      }
   }

   public Object Slice(Subscript node, Slice slice) throws Exception {
      expr_contextType ctx = node.getInternalCtx();
      if (ctx == expr_contextType.AugStore && this.augmode == expr_contextType.Store) {
         this.restoreAugTmps(node, 4);
         ctx = expr_contextType.Store;
      } else {
         this.visit(node.getInternalValue());
         this.stackProduce();
         if (slice.getInternalLower() != null) {
            this.visit(slice.getInternalLower());
         } else {
            this.code.aconst_null();
         }

         this.stackProduce();
         if (slice.getInternalUpper() != null) {
            this.visit(slice.getInternalUpper());
         } else {
            this.code.aconst_null();
         }

         this.stackProduce();
         if (slice.getInternalStep() != null) {
            this.visit(slice.getInternalStep());
         } else {
            this.code.aconst_null();
         }

         this.stackProduce();
         if (node.getInternalCtx() == expr_contextType.AugStore && this.augmode == expr_contextType.Load) {
            this.saveAugTmps(node, 4);
            ctx = expr_contextType.Load;
         }

         this.stackConsume(4);
      }

      switch (ctx) {
         case Del:
            this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__delslice__", CodegenUtils.sig(Void.TYPE, PyObject.class, PyObject.class, PyObject.class));
            return null;
         case Load:
            this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__getslice__", CodegenUtils.sig(PyObject.class, PyObject.class, PyObject.class, PyObject.class));
            return null;
         case Param:
         case Store:
            this.code.aload(this.temporary);
            this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__setslice__", CodegenUtils.sig(Void.TYPE, PyObject.class, PyObject.class, PyObject.class, PyObject.class));
            return null;
         default:
            return null;
      }
   }

   public Object visitSubscript(Subscript node) throws Exception {
      if (node.getInternalSlice() instanceof Slice) {
         return this.Slice(node, (Slice)node.getInternalSlice());
      } else {
         int value = this.temporary;
         expr_contextType ctx = node.getInternalCtx();
         if (node.getInternalCtx() == expr_contextType.AugStore && this.augmode == expr_contextType.Store) {
            this.restoreAugTmps(node, 2);
            ctx = expr_contextType.Store;
         } else {
            this.visit(node.getInternalValue());
            this.stackProduce();
            this.visit(node.getInternalSlice());
            this.stackConsume();
            if (node.getInternalCtx() == expr_contextType.AugStore && this.augmode == expr_contextType.Load) {
               this.saveAugTmps(node, 2);
               ctx = expr_contextType.Load;
            }
         }

         switch (ctx) {
            case Del:
               this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__delitem__", CodegenUtils.sig(Void.TYPE, PyObject.class));
               return null;
            case Load:
               this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__getitem__", CodegenUtils.sig(PyObject.class, PyObject.class));
               return null;
            case Param:
            case Store:
               this.code.aload(value);
               this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__setitem__", CodegenUtils.sig(Void.TYPE, PyObject.class, PyObject.class));
               return null;
            default:
               return null;
         }
      }
   }

   public Object visitIndex(Index node) throws Exception {
      this.traverse(node);
      return null;
   }

   public Object visitExtSlice(ExtSlice node) throws Exception {
      int dims = this.makeArray(node.getInternalDims());
      this.code.new_(CodegenUtils.p(PyTuple.class));
      this.code.dup();
      this.code.aload(dims);
      this.code.invokespecial(CodegenUtils.p(PyTuple.class), "<init>", CodegenUtils.sig(Void.TYPE, PyObject[].class));
      this.freeArray(dims);
      return null;
   }

   public Object visitAttribute(Attribute node) throws Exception {
      expr_contextType ctx = node.getInternalCtx();
      if (node.getInternalCtx() == expr_contextType.AugStore && this.augmode == expr_contextType.Store) {
         this.restoreAugTmps(node, 2);
         ctx = expr_contextType.Store;
      } else {
         this.visit(node.getInternalValue());
         this.code.ldc(this.getName(node.getInternalAttr()));
         if (node.getInternalCtx() == expr_contextType.AugStore && this.augmode == expr_contextType.Load) {
            this.saveAugTmps(node, 2);
            ctx = expr_contextType.Load;
         }
      }

      switch (ctx) {
         case Del:
            this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__delattr__", CodegenUtils.sig(Void.TYPE, String.class));
            return null;
         case Load:
            this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__getattr__", CodegenUtils.sig(PyObject.class, String.class));
            return null;
         case Param:
         case Store:
            this.code.aload(this.temporary);
            this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__setattr__", CodegenUtils.sig(Void.TYPE, String.class, PyObject.class));
            return null;
         default:
            return null;
      }
   }

   public Object seqSet(List nodes) throws Exception {
      this.code.aload(this.temporary);
      this.code.iconst(nodes.size());
      this.code.invokestatic(CodegenUtils.p(Py.class), "unpackSequence", CodegenUtils.sig(PyObject[].class, PyObject.class, Integer.TYPE));
      int tmp = this.code.getLocal("[org/python/core/PyObject");
      this.code.astore(tmp);

      for(int i = 0; i < nodes.size(); ++i) {
         this.code.aload(tmp);
         this.code.iconst(i);
         this.code.aaload();
         this.set((PythonTree)nodes.get(i));
      }

      this.code.freeLocal(tmp);
      return null;
   }

   public Object seqDel(List nodes) throws Exception {
      Iterator var2 = nodes.iterator();

      while(var2.hasNext()) {
         expr e = (expr)var2.next();
         this.visit(e);
      }

      return null;
   }

   public Object visitTuple(Tuple node) throws Exception {
      if (node.getInternalCtx() == expr_contextType.Store) {
         return this.seqSet(node.getInternalElts());
      } else if (node.getInternalCtx() == expr_contextType.Del) {
         return this.seqDel(node.getInternalElts());
      } else {
         if (this.my_scope.generator) {
            int content = this.makeArray(node.getInternalElts());
            this.code.new_(CodegenUtils.p(PyTuple.class));
            this.code.dup();
            this.code.aload(content);
            this.code.invokespecial(CodegenUtils.p(PyTuple.class), "<init>", CodegenUtils.sig(Void.TYPE, PyObject[].class));
            this.freeArray(content);
         } else {
            this.code.new_(CodegenUtils.p(PyTuple.class));
            this.code.dup();
            this.loadArray(this.code, node.getInternalElts());
            this.code.invokespecial(CodegenUtils.p(PyTuple.class), "<init>", CodegenUtils.sig(Void.TYPE, PyObject[].class));
         }

         return null;
      }
   }

   public Object visitList(org.python.antlr.ast.List node) throws Exception {
      if (node.getInternalCtx() == expr_contextType.Store) {
         return this.seqSet(node.getInternalElts());
      } else if (node.getInternalCtx() == expr_contextType.Del) {
         return this.seqDel(node.getInternalElts());
      } else {
         if (this.my_scope.generator) {
            int content = this.makeArray(node.getInternalElts());
            this.code.new_(CodegenUtils.p(PyList.class));
            this.code.dup();
            this.code.aload(content);
            this.code.invokespecial(CodegenUtils.p(PyList.class), "<init>", CodegenUtils.sig(Void.TYPE, PyObject[].class));
            this.freeArray(content);
         } else {
            this.code.new_(CodegenUtils.p(PyList.class));
            this.code.dup();
            this.loadArray(this.code, node.getInternalElts());
            this.code.invokespecial(CodegenUtils.p(PyList.class), "<init>", CodegenUtils.sig(Void.TYPE, PyObject[].class));
         }

         return null;
      }
   }

   public Object visitListComp(ListComp node) throws Exception {
      this.code.new_(CodegenUtils.p(PyList.class));
      this.code.dup();
      this.code.invokespecial(CodegenUtils.p(PyList.class), "<init>", CodegenUtils.sig(Void.TYPE));
      this.code.dup();
      this.code.ldc("append");
      this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__getattr__", CodegenUtils.sig(PyObject.class, String.class));
      String tmp_append = "_[" + node.getLine() + "_" + node.getCharPositionInLine() + "]";
      List args = new ArrayList();
      args.add(node.getInternalElt());
      this.finishComp(node, args, node.getInternalGenerators(), tmp_append);
      return null;
   }

   public Object visitSetComp(SetComp node) throws Exception {
      this.code.new_(CodegenUtils.p(PySet.class));
      this.code.dup();
      this.visitInternalGenerators(node, node.getInternalElt(), node.getInternalGenerators());
      this.code.invokespecial(CodegenUtils.p(PySet.class), "<init>", CodegenUtils.sig(Void.TYPE, PyObject.class));
      return null;
   }

   public Object visitDictComp(DictComp node) throws Exception {
      this.code.new_(CodegenUtils.p(PyDictionary.class));
      this.code.dup();
      this.code.invokespecial(CodegenUtils.p(PyDictionary.class), "<init>", CodegenUtils.sig(Void.TYPE));
      this.code.dup();
      List kv = Arrays.asList(node.getInternalKey(), node.getInternalValue());
      this.visitInternalGenerators(node, new Tuple(node, kv, expr_contextType.UNDEFINED), node.getInternalGenerators());
      this.code.invokevirtual(CodegenUtils.p(PyDictionary.class), "update", CodegenUtils.sig(Void.TYPE, PyObject.class));
      return null;
   }

   private void finishComp(expr node, List args, List generators, String tmp_append) throws Exception {
      this.set(new Name(node, tmp_append, expr_contextType.Store));
      stmt n = new Expr(node, new Call(node, new Name(node, tmp_append, expr_contextType.Load), args, new ArrayList(), (expr)null, (expr)null));

      for(int i = generators.size() - 1; i >= 0; --i) {
         comprehension lc = (comprehension)generators.get(i);

         for(int j = lc.getInternalIfs().size() - 1; j >= 0; --j) {
            List body = new ArrayList();
            body.add(n);
            n = new If((PythonTree)lc.getInternalIfs().get(j), (expr)lc.getInternalIfs().get(j), body, new ArrayList());
         }

         List body = new ArrayList();
         body.add(n);
         n = new For(lc, lc.getInternalTarget(), lc.getInternalIter(), body, new ArrayList());
      }

      this.visit((PythonTree)n);
      List targets = new ArrayList();
      targets.add(new Name((PythonTree)n, tmp_append, expr_contextType.Del));
      this.visit(new Delete((PythonTree)n, targets));
   }

   public Object visitDict(Dict node) throws Exception {
      List elts = new ArrayList();

      int content;
      for(content = 0; content < node.getInternalKeys().size(); ++content) {
         elts.add(node.getInternalKeys().get(content));
         elts.add(node.getInternalValues().get(content));
      }

      if (this.my_scope.generator) {
         content = this.makeArray(elts);
         this.code.new_(CodegenUtils.p(PyDictionary.class));
         this.code.dup();
         this.code.aload(content);
         this.code.invokespecial(CodegenUtils.p(PyDictionary.class), "<init>", CodegenUtils.sig(Void.TYPE, PyObject[].class));
         this.freeArray(content);
      } else {
         this.code.new_(CodegenUtils.p(PyDictionary.class));
         this.code.dup();
         this.loadArray(this.code, elts);
         this.code.invokespecial(CodegenUtils.p(PyDictionary.class), "<init>", CodegenUtils.sig(Void.TYPE, PyObject[].class));
      }

      return null;
   }

   public Object visitSet(Set node) throws Exception {
      if (this.my_scope.generator) {
         int content = this.makeArray(node.getInternalElts());
         this.code.new_(CodegenUtils.p(PySet.class));
         this.code.dup();
         this.code.aload(content);
         this.code.invokespecial(CodegenUtils.p(PySet.class), "<init>", CodegenUtils.sig(Void.TYPE, PyObject[].class));
         this.freeArray(content);
      } else {
         this.code.new_(CodegenUtils.p(PySet.class));
         this.code.dup();
         this.loadArray(this.code, node.getInternalElts());
         this.code.invokespecial(CodegenUtils.p(PySet.class), "<init>", CodegenUtils.sig(Void.TYPE, PyObject[].class));
      }

      return null;
   }

   public Object visitRepr(Repr node) throws Exception {
      this.visit(node.getInternalValue());
      this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__repr__", CodegenUtils.sig(PyString.class));
      return null;
   }

   public Object visitLambda(Lambda node) throws Exception {
      String name = "<lambda>";
      List bod = new ArrayList();
      bod.add(new LambdaSyntheticReturn(node, node.getInternalBody()));
      mod retSuite = new Suite(node, bod);
      this.setline(node);
      ScopeInfo scope = this.module.getScopeInfo(node);
      int defaultsArray = this.makeArray(scope.ac.getDefaults());
      this.code.new_(CodegenUtils.p(PyFunction.class));
      this.code.dup();
      this.code.aload(defaultsArray);
      this.code.freeLocal(defaultsArray);
      this.loadFrame();
      this.code.getfield(CodegenUtils.p(PyFrame.class), "f_globals", CodegenUtils.ci(PyObject.class));
      this.code.swap();
      scope.setup_closure();
      scope.dump();
      this.module.codeConstant(retSuite, name, true, this.className, false, false, node.getLine(), scope, this.cflags).get(this.code);
      if (!this.makeClosure(scope)) {
         this.code.invokespecial(CodegenUtils.p(PyFunction.class), "<init>", CodegenUtils.sig(Void.TYPE, PyObject.class, PyObject[].class, PyCode.class));
      } else {
         this.code.invokespecial(CodegenUtils.p(PyFunction.class), "<init>", CodegenUtils.sig(Void.TYPE, PyObject.class, PyObject[].class, PyCode.class, PyObject[].class));
      }

      return null;
   }

   public Object visitEllipsis(Ellipsis node) throws Exception {
      this.code.getstatic(CodegenUtils.p(Py.class), "Ellipsis", CodegenUtils.ci(PyObject.class));
      return null;
   }

   public Object visitSlice(Slice node) throws Exception {
      if (node.getInternalLower() == null) {
         this.getNone();
      } else {
         this.visit(node.getInternalLower());
      }

      this.stackProduce();
      if (node.getInternalUpper() == null) {
         this.getNone();
      } else {
         this.visit(node.getInternalUpper());
      }

      this.stackProduce();
      if (node.getInternalStep() == null) {
         this.getNone();
      } else {
         this.visit(node.getInternalStep());
      }

      int step = this.storeTop();
      this.stackConsume(2);
      this.code.new_(CodegenUtils.p(PySlice.class));
      this.code.dup();
      this.code.dup2_x2();
      this.code.pop2();
      this.code.aload(step);
      this.code.freeLocal(step);
      this.code.invokespecial(CodegenUtils.p(PySlice.class), "<init>", CodegenUtils.sig(Void.TYPE, PyObject.class, PyObject.class, PyObject.class));
      return null;
   }

   public Object visitClassDef(ClassDef node) throws Exception {
      this.setline(node);
      int baseArray = this.makeArray(node.getInternalBases());
      String name = this.getName(node.getInternalName());
      this.code.ldc(name);
      this.code.aload(baseArray);
      ScopeInfo scope = this.module.getScopeInfo(node);
      scope.setup_closure();
      scope.dump();
      this.module.codeConstant(new Suite(node, node.getInternalBody()), name, false, name, this.getDocStr(node.getInternalBody()), true, false, node.getLine(), scope, this.cflags).get(this.code);
      if (!this.makeClosure(scope)) {
         this.code.invokestatic(CodegenUtils.p(Py.class), "makeClass", CodegenUtils.sig(PyObject.class, String.class, PyObject[].class, PyCode.class));
      } else {
         this.code.invokestatic(CodegenUtils.p(Py.class), "makeClass", CodegenUtils.sig(PyObject.class, String.class, PyObject[].class, PyCode.class, PyObject[].class));
      }

      this.applyDecorators(node.getInternalDecorator_list());
      this.set(new Name(node, node.getInternalName(), expr_contextType.Store));
      this.freeArray(baseArray);
      return null;
   }

   public Object visitNum(Num node) throws Exception {
      if (node.getInternalN() instanceof PyInteger) {
         this.module.integerConstant(((PyInteger)node.getInternalN()).getValue()).get(this.code);
      } else if (node.getInternalN() instanceof PyLong) {
         this.module.longConstant(((PyObject)node.getInternalN()).__str__().toString()).get(this.code);
      } else if (node.getInternalN() instanceof PyFloat) {
         this.module.floatConstant(((PyFloat)node.getInternalN()).getValue()).get(this.code);
      } else if (node.getInternalN() instanceof PyComplex) {
         this.module.complexConstant(((PyComplex)node.getInternalN()).imag).get(this.code);
      }

      return null;
   }

   private String getName(String name) {
      if (this.className != null && name.startsWith("__") && !name.endsWith("__")) {
         int i;
         for(i = 0; this.className.charAt(i) == '_'; ++i) {
         }

         return "_" + this.className.substring(i) + name;
      } else {
         return name;
      }
   }

   void emitGetGlobal(String name) throws Exception {
      this.code.ldc(name);
      this.code.invokevirtual(CodegenUtils.p(PyFrame.class), "getglobal", CodegenUtils.sig(PyObject.class, String.class));
   }

   public Object visitName(Name node) throws Exception {
      String name;
      if (this.fast_locals) {
         name = node.getInternalId();
      } else {
         name = this.getName(node.getInternalId());
      }

      SymInfo syminf = (SymInfo)this.tbl.get(name);
      expr_contextType ctx = node.getInternalCtx();
      if (ctx == expr_contextType.AugStore) {
         ctx = this.augmode;
      }

      switch (ctx) {
         case Del:
            this.loadFrame();
            if (syminf != null && (syminf.flags & 66) != 0) {
               this.code.ldc(name);
               this.code.invokevirtual(CodegenUtils.p(PyFrame.class), "delglobal", CodegenUtils.sig(Void.TYPE, String.class));
            } else if (!this.fast_locals) {
               this.code.ldc(name);
               this.code.invokevirtual(CodegenUtils.p(PyFrame.class), "dellocal", CodegenUtils.sig(Void.TYPE, String.class));
            } else {
               if (syminf == null) {
                  throw new ParseException("internal compiler error", node);
               }

               if ((syminf.flags & 16) != 0) {
                  this.module.error("can not delete variable '" + name + "' referenced in nested scope", true, node);
               }

               this.code.iconst(syminf.locals_index);
               this.code.invokevirtual(CodegenUtils.p(PyFrame.class), "dellocal", CodegenUtils.sig(Void.TYPE, Integer.TYPE));
            }

            return null;
         case Load:
            this.loadFrame();
            if (syminf != null) {
               int flags = syminf.flags;
               if ((flags & 66) != 0 || this.optimizeGlobals && (flags & 49) == 0) {
                  this.emitGetGlobal(name);
                  return null;
               }

               if (this.fast_locals) {
                  if ((flags & 16) != 0) {
                     this.code.iconst(syminf.env_index);
                     this.code.invokevirtual(CodegenUtils.p(PyFrame.class), "getderef", CodegenUtils.sig(PyObject.class, Integer.TYPE));
                     return null;
                  }

                  if ((flags & 1) != 0) {
                     this.code.iconst(syminf.locals_index);
                     this.code.invokevirtual(CodegenUtils.p(PyFrame.class), "getlocal", CodegenUtils.sig(PyObject.class, Integer.TYPE));
                     return null;
                  }
               }

               if ((flags & 32) != 0 && (flags & 1) == 0) {
                  this.code.iconst(syminf.env_index);
                  this.code.invokevirtual(CodegenUtils.p(PyFrame.class), "getderef", CodegenUtils.sig(PyObject.class, Integer.TYPE));
                  return null;
               }
            }

            this.code.ldc(name);
            this.code.invokevirtual(CodegenUtils.p(PyFrame.class), "getname", CodegenUtils.sig(PyObject.class, String.class));
            return null;
         case Param:
         case Store:
            this.loadFrame();
            if (syminf != null && (syminf.flags & 66) != 0) {
               this.code.ldc(name);
               this.code.aload(this.temporary);
               this.code.invokevirtual(CodegenUtils.p(PyFrame.class), "setglobal", CodegenUtils.sig(Void.TYPE, String.class, PyObject.class));
            } else if (!this.fast_locals) {
               this.code.ldc(name);
               this.code.aload(this.temporary);
               this.code.invokevirtual(CodegenUtils.p(PyFrame.class), "setlocal", CodegenUtils.sig(Void.TYPE, String.class, PyObject.class));
            } else {
               if (syminf == null) {
                  throw new ParseException("internal compiler error", node);
               }

               if ((syminf.flags & 16) != 0) {
                  this.code.iconst(syminf.env_index);
                  this.code.aload(this.temporary);
                  this.code.invokevirtual(CodegenUtils.p(PyFrame.class), "setderef", CodegenUtils.sig(Void.TYPE, Integer.TYPE, PyObject.class));
               } else {
                  this.code.iconst(syminf.locals_index);
                  this.code.aload(this.temporary);
                  this.code.invokevirtual(CodegenUtils.p(PyFrame.class), "setlocal", CodegenUtils.sig(Void.TYPE, Integer.TYPE, PyObject.class));
               }
            }

            return null;
         default:
            return null;
      }
   }

   public Object visitStr(Str node) throws Exception {
      PyString s = (PyString)node.getInternalS();
      if (s instanceof PyUnicode) {
         this.module.unicodeConstant(s.asString()).get(this.code);
      } else {
         this.module.stringConstant(s.asString()).get(this.code);
      }

      return null;
   }

   private Object visitInternalGenerators(expr node, expr elt, List generators) throws Exception {
      String bound_exp = "_(x)";
      this.setline(node);
      this.code.new_(CodegenUtils.p(PyFunction.class));
      this.code.dup();
      this.loadFrame();
      this.code.getfield(CodegenUtils.p(PyFrame.class), "f_globals", CodegenUtils.ci(PyObject.class));
      ScopeInfo scope = this.module.getScopeInfo(node);
      int emptyArray = this.makeArray(new ArrayList());
      this.code.aload(emptyArray);
      scope.setup_closure();
      scope.dump();
      stmt n = new Expr(node, new Yield(node, elt));
      expr iter = null;

      for(int i = generators.size() - 1; i >= 0; --i) {
         comprehension comp = (comprehension)generators.get(i);

         for(int j = comp.getInternalIfs().size() - 1; j >= 0; --j) {
            List bod = new ArrayList();
            bod.add(n);
            n = new If((PythonTree)comp.getInternalIfs().get(j), (expr)comp.getInternalIfs().get(j), bod, new ArrayList());
         }

         List bod = new ArrayList();
         bod.add(n);
         if (i != 0) {
            n = new For(comp, comp.getInternalTarget(), comp.getInternalIter(), bod, new ArrayList());
         } else {
            n = new For(comp, comp.getInternalTarget(), new Name(node, bound_exp, expr_contextType.Load), bod, new ArrayList());
            iter = comp.getInternalIter();
         }
      }

      List bod = new ArrayList();
      bod.add(n);
      this.module.codeConstant(new Suite(node, bod), "<genexpr>", true, this.className, false, false, node.getLine(), scope, this.cflags).get(this.code);
      this.code.aconst_null();
      if (!this.makeClosure(scope)) {
         this.code.invokespecial(CodegenUtils.p(PyFunction.class), "<init>", CodegenUtils.sig(Void.TYPE, PyObject.class, PyObject[].class, PyCode.class, PyObject.class));
      } else {
         this.code.invokespecial(CodegenUtils.p(PyFunction.class), "<init>", CodegenUtils.sig(Void.TYPE, PyObject.class, PyObject[].class, PyCode.class, PyObject.class, PyObject[].class));
      }

      int genExp = this.storeTop();
      this.visit(iter);
      this.code.aload(genExp);
      this.code.freeLocal(genExp);
      this.code.swap();
      this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__iter__", CodegenUtils.sig(PyObject.class));
      this.loadThreadState();
      this.code.swap();
      this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__call__", CodegenUtils.sig(PyObject.class, ThreadState.class, PyObject.class));
      this.freeArray(emptyArray);
      return null;
   }

   public Object visitGeneratorExp(GeneratorExp node) throws Exception {
      String bound_exp = "_(x)";
      this.setline(node);
      this.code.new_(CodegenUtils.p(PyFunction.class));
      this.code.dup();
      this.loadFrame();
      this.code.getfield(CodegenUtils.p(PyFrame.class), "f_globals", CodegenUtils.ci(PyObject.class));
      ScopeInfo scope = this.module.getScopeInfo(node);
      int emptyArray = this.makeArray(new ArrayList());
      this.code.aload(emptyArray);
      scope.setup_closure();
      scope.dump();
      stmt n = new Expr(node, new Yield(node, node.getInternalElt()));
      expr iter = null;

      for(int i = node.getInternalGenerators().size() - 1; i >= 0; --i) {
         comprehension comp = (comprehension)node.getInternalGenerators().get(i);

         for(int j = comp.getInternalIfs().size() - 1; j >= 0; --j) {
            List bod = new ArrayList();
            bod.add(n);
            n = new If((PythonTree)comp.getInternalIfs().get(j), (expr)comp.getInternalIfs().get(j), bod, new ArrayList());
         }

         List bod = new ArrayList();
         bod.add(n);
         if (i != 0) {
            n = new For(comp, comp.getInternalTarget(), comp.getInternalIter(), bod, new ArrayList());
         } else {
            n = new For(comp, comp.getInternalTarget(), new Name(node, bound_exp, expr_contextType.Load), bod, new ArrayList());
            iter = comp.getInternalIter();
         }
      }

      List bod = new ArrayList();
      bod.add(n);
      this.module.codeConstant(new Suite(node, bod), "<genexpr>", true, this.className, false, false, node.getLine(), scope, this.cflags).get(this.code);
      this.code.aconst_null();
      if (!this.makeClosure(scope)) {
         this.code.invokespecial(CodegenUtils.p(PyFunction.class), "<init>", CodegenUtils.sig(Void.TYPE, PyObject.class, PyObject[].class, PyCode.class, PyObject.class));
      } else {
         this.code.invokespecial(CodegenUtils.p(PyFunction.class), "<init>", CodegenUtils.sig(Void.TYPE, PyObject.class, PyObject[].class, PyCode.class, PyObject.class, PyObject[].class));
      }

      int genExp = this.storeTop();
      this.visit(iter);
      this.code.aload(genExp);
      this.code.freeLocal(genExp);
      this.code.swap();
      this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__iter__", CodegenUtils.sig(PyObject.class));
      this.loadThreadState();
      this.code.swap();
      this.code.invokevirtual(CodegenUtils.p(PyObject.class), "__call__", CodegenUtils.sig(PyObject.class, ThreadState.class, PyObject.class));
      this.freeArray(emptyArray);
      return null;
   }

   public Object visitWith(With node) throws Exception {
      if (!this.module.getFutures().withStatementSupported()) {
         throw new ParseException("'with' will become a reserved keyword in Python 2.6", node);
      } else {
         Label label_body_start = new Label();
         Label label_body_end = new Label();
         Label label_catch = new Label();
         Label label_end = new Label();
         Method contextGuard_getManager = Method.getMethod("org.python.core.ContextManager getManager (org.python.core.PyObject)");
         Method __enter__ = Method.getMethod("org.python.core.PyObject __enter__ (org.python.core.ThreadState)");
         final Method __exit__ = Method.getMethod("boolean __exit__ (org.python.core.ThreadState,org.python.core.PyException)");
         this.visit(node.getInternalContext_expr());
         this.code.invokestatic(Type.getType(ContextGuard.class).getInternalName(), contextGuard_getManager.getName(), contextGuard_getManager.getDescriptor());
         this.code.dup();
         final int mgr_tmp = this.code.getLocal(Type.getType(ContextManager.class).getInternalName());
         this.code.astore(mgr_tmp);
         this.loadThreadState();
         this.code.invokeinterface(Type.getType(ContextManager.class).getInternalName(), __enter__.getName(), __enter__.getDescriptor(), true);
         int value_tmp = this.code.getLocal(CodegenUtils.p(PyObject.class));
         this.code.astore(value_tmp);
         ExceptionHandler normalExit = new ExceptionHandler() {
            public boolean isFinallyHandler() {
               return true;
            }

            public void finalBody(CodeCompiler compiler) throws Exception {
               compiler.code.aload(mgr_tmp);
               CodeCompiler.this.loadThreadState();
               compiler.code.aconst_null();
               compiler.code.invokeinterface(Type.getType(ContextManager.class).getInternalName(), __exit__.getName(), __exit__.getDescriptor(), true);
               compiler.code.pop();
            }
         };
         this.exceptionHandlers.push(normalExit);
         ExceptionHandler handler = new ExceptionHandler();
         this.exceptionHandlers.push(handler);
         handler.exceptionStarts.addElement(label_body_start);
         this.code.label(label_body_start);
         if (node.getInternalOptional_vars() != null) {
            this.set(node.getInternalOptional_vars(), value_tmp);
         }

         this.code.freeLocal(value_tmp);
         Object blockResult = this.suite(node.getInternalBody());
         normalExit.bodyDone = true;
         this.exceptionHandlers.pop();
         this.exceptionHandlers.pop();
         this.code.label(label_body_end);
         handler.exceptionEnds.addElement(label_body_end);
         if (blockResult == NoExit) {
            this.inlineFinally(normalExit);
            this.code.goto_(label_end);
         }

         this.code.label(label_catch);
         this.loadFrame();
         this.code.invokestatic(CodegenUtils.p(Py.class), "setException", CodegenUtils.sig(PyException.class, Throwable.class, PyFrame.class));
         this.code.aload(mgr_tmp);
         this.code.swap();
         this.loadThreadState();
         this.code.swap();
         this.code.invokeinterface(Type.getType(ContextManager.class).getInternalName(), __exit__.getName(), __exit__.getDescriptor(), true);
         this.code.ifne(label_end);
         this.code.invokestatic(CodegenUtils.p(Py.class), "makeException", CodegenUtils.sig(PyException.class));
         this.code.checkcast(CodegenUtils.p(Throwable.class));
         this.code.athrow();
         this.code.label(label_end);
         this.code.freeLocal(mgr_tmp);
         handler.addExceptionHandlers(label_catch);
         return null;
      }
   }

   protected Object unhandled_node(PythonTree node) throws Exception {
      throw new Exception("Unhandled node " + node);
   }

   class ExceptionHandler {
      public Vector exceptionStarts = new Vector();
      public Vector exceptionEnds = new Vector();
      public boolean bodyDone = false;
      public PythonTree node = null;

      public ExceptionHandler() {
      }

      public ExceptionHandler(PythonTree n) {
         this.node = n;
      }

      public boolean isFinallyHandler() {
         return this.node != null;
      }

      public void addExceptionHandlers(Label handlerStart) throws Exception {
         for(int i = 0; i < this.exceptionStarts.size(); ++i) {
            Label start = (Label)this.exceptionStarts.elementAt(i);
            Label end = (Label)this.exceptionEnds.elementAt(i);
            if (start.getOffset() != end.getOffset()) {
               CodeCompiler.this.code.trycatch((Label)this.exceptionStarts.elementAt(i), (Label)this.exceptionEnds.elementAt(i), handlerStart, CodegenUtils.p(Throwable.class));
            }
         }

      }

      public void finalBody(CodeCompiler compiler) throws Exception {
         if (this.node instanceof TryFinally) {
            CodeCompiler.this.suite(((TryFinally)this.node).getInternalFinalbody());
         }

      }
   }

   private class LambdaSyntheticReturn extends Return {
      private LambdaSyntheticReturn(PythonTree tree, expr value) {
         super(tree, value);
      }

      // $FF: synthetic method
      LambdaSyntheticReturn(PythonTree x1, expr x2, Object x3) {
         this(x1, x2);
      }
   }
}
