package org.python.compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import org.python.antlr.PythonTree;
import org.python.antlr.Visitor;
import org.python.antlr.ast.ClassDef;
import org.python.antlr.ast.DictComp;
import org.python.antlr.ast.Exec;
import org.python.antlr.ast.Expression;
import org.python.antlr.ast.FunctionDef;
import org.python.antlr.ast.GeneratorExp;
import org.python.antlr.ast.Global;
import org.python.antlr.ast.Import;
import org.python.antlr.ast.ImportFrom;
import org.python.antlr.ast.Interactive;
import org.python.antlr.ast.Lambda;
import org.python.antlr.ast.ListComp;
import org.python.antlr.ast.Name;
import org.python.antlr.ast.Return;
import org.python.antlr.ast.SetComp;
import org.python.antlr.ast.Tuple;
import org.python.antlr.ast.With;
import org.python.antlr.ast.Yield;
import org.python.antlr.ast.alias;
import org.python.antlr.ast.arguments;
import org.python.antlr.ast.comprehension;
import org.python.antlr.ast.expr_contextType;
import org.python.antlr.base.expr;
import org.python.antlr.base.stmt;
import org.python.core.ParserFacade;

public class ScopesCompiler extends Visitor implements ScopeConstants {
   private CompilationContext code_compiler;
   private Stack scopes;
   private ScopeInfo cur = null;
   private Hashtable nodeScopes;
   private int level = 0;
   private int func_level = 0;

   public ScopesCompiler(CompilationContext code_compiler, Hashtable nodeScopes) {
      this.code_compiler = code_compiler;
      this.nodeScopes = nodeScopes;
      this.scopes = new Stack();
   }

   public void beginScope(String name, int kind, PythonTree node, ArgListCompiler ac) {
      if (this.cur != null) {
         this.scopes.push(this.cur);
      }

      if (kind == 1) {
         ++this.func_level;
      }

      this.cur = new ScopeInfo(name, node, this.level++, kind, this.func_level, ac);
      this.nodeScopes.put(node, this.cur);
   }

   public void endScope() throws Exception {
      if (this.cur.kind == 1) {
         --this.func_level;
      }

      --this.level;
      ScopeInfo up = null;
      if (!this.scopes.empty()) {
         up = (ScopeInfo)this.scopes.pop();
      }

      int dist = 1;
      ScopeInfo referenceable = up;

      for(int i = this.scopes.size() - 1; i >= 0 && referenceable.kind == 2; ++dist) {
         referenceable = (ScopeInfo)this.scopes.get(i);
         --i;
      }

      this.cur.cook(referenceable, dist, this.code_compiler);
      this.cur.dump();
      this.cur = up;
   }

   public void parse(PythonTree node) throws Exception {
      try {
         this.visit(node);
      } catch (Throwable var3) {
         throw ParserFacade.fixParseError((ParserFacade.ExpectedEncodingBufferedReader)null, var3, this.code_compiler.getFilename());
      }
   }

   public Object visitInteractive(Interactive node) throws Exception {
      this.beginScope("<single-top>", 0, node, (ArgListCompiler)null);
      this.suite(node.getInternalBody());
      this.endScope();
      return null;
   }

   public Object visitModule(org.python.antlr.ast.Module node) throws Exception {
      this.beginScope("<file-top>", 0, node, (ArgListCompiler)null);
      this.suite(node.getInternalBody());
      this.endScope();
      return null;
   }

   public Object visitExpression(Expression node) throws Exception {
      this.beginScope("<eval-top>", 0, node, (ArgListCompiler)null);
      this.visit(new Return(node, node.getInternalBody()));
      this.endScope();
      return null;
   }

   private void def(String name) {
      this.cur.addBound(name);
   }

   public Object visitFunctionDef(FunctionDef node) throws Exception {
      this.def(node.getInternalName());
      ArgListCompiler ac = new ArgListCompiler();
      ac.visitArgs(node.getInternalArgs());
      List defaults = ac.getDefaults();

      for(int i = 0; i < defaults.size(); ++i) {
         this.visit((PythonTree)defaults.get(i));
      }

      List decs = node.getInternalDecorator_list();

      int n;
      for(n = decs.size() - 1; n >= 0; --n) {
         this.visit((PythonTree)decs.get(n));
      }

      this.beginScope(node.getInternalName(), 1, node, ac);
      n = ac.names.size();

      int i;
      for(i = 0; i < n; ++i) {
         this.cur.addParam((String)ac.names.get(i));
      }

      for(i = 0; i < ac.init_code.size(); ++i) {
         this.visit((PythonTree)ac.init_code.get(i));
      }

      this.cur.markFromParam();
      this.suite(node.getInternalBody());
      this.endScope();
      return null;
   }

   public Object visitLambda(Lambda node) throws Exception {
      ArgListCompiler ac = new ArgListCompiler();
      ac.visitArgs(node.getInternalArgs());
      List defaults = ac.getDefaults();

      for(int i = 0; i < defaults.size(); ++i) {
         this.visit((PythonTree)defaults.get(i));
      }

      this.beginScope("<lambda>", 1, node, ac);
      Iterator var6 = ac.names.iterator();

      Object o;
      while(var6.hasNext()) {
         o = var6.next();
         this.cur.addParam((String)o);
      }

      var6 = ac.init_code.iterator();

      while(var6.hasNext()) {
         o = var6.next();
         this.visit((stmt)o);
      }

      this.cur.markFromParam();
      this.visit(node.getInternalBody());
      this.endScope();
      return null;
   }

   public void suite(List stmts) throws Exception {
      for(int i = 0; i < stmts.size(); ++i) {
         this.visit((PythonTree)stmts.get(i));
      }

   }

   public Object visitImport(Import node) throws Exception {
      for(int i = 0; i < node.getInternalNames().size(); ++i) {
         if (((alias)node.getInternalNames().get(i)).getInternalAsname() != null) {
            this.cur.addBound(((alias)node.getInternalNames().get(i)).getInternalAsname());
         } else {
            String name = ((alias)node.getInternalNames().get(i)).getInternalName();
            if (name.indexOf(46) > 0) {
               name = name.substring(0, name.indexOf(46));
            }

            this.cur.addBound(name);
         }
      }

      return null;
   }

   public Object visitImportFrom(ImportFrom node) throws Exception {
      Future.checkFromFuture(node);
      int n = node.getInternalNames().size();
      if (n == 0) {
         this.cur.from_import_star = true;
         return null;
      } else {
         for(int i = 0; i < n; ++i) {
            if (((alias)node.getInternalNames().get(i)).getInternalAsname() != null) {
               this.cur.addBound(((alias)node.getInternalNames().get(i)).getInternalAsname());
            } else {
               this.cur.addBound(((alias)node.getInternalNames().get(i)).getInternalName());
            }
         }

         return null;
      }
   }

   public Object visitGlobal(Global node) throws Exception {
      int n = node.getInternalNames().size();

      for(int i = 0; i < n; ++i) {
         String name = (String)node.getInternalNames().get(i);
         int prev = this.cur.addGlobal(name);
         if (prev >= 0) {
            if ((prev & 8) != 0) {
               this.code_compiler.error("name '" + name + "' is local and global", true, node);
            }

            if ((prev & 66) == 0) {
               String what;
               if ((prev & 1) != 0) {
                  what = "assignment";
               } else {
                  what = "use";
               }

               this.code_compiler.error("name '" + name + "' declared global after " + what, false, node);
            }
         }
      }

      return null;
   }

   public Object visitExec(Exec node) throws Exception {
      this.cur.exec = true;
      if (node.getInternalGlobals() == null && node.getInternalLocals() == null) {
         this.cur.unqual_exec = true;
      }

      this.traverse(node);
      return null;
   }

   public Object visitClassDef(ClassDef node) throws Exception {
      List decs = node.getInternalDecorator_list();

      int n;
      for(n = decs.size() - 1; n >= 0; --n) {
         this.visit((PythonTree)decs.get(n));
      }

      this.def(node.getInternalName());
      n = node.getInternalBases().size();

      for(int i = 0; i < n; ++i) {
         this.visit((PythonTree)node.getInternalBases().get(i));
      }

      this.beginScope(node.getInternalName(), 2, node, (ArgListCompiler)null);
      this.suite(node.getInternalBody());
      this.endScope();
      return null;
   }

   public Object visitName(Name node) throws Exception {
      String name = node.getInternalId();
      if (node.getInternalCtx() != expr_contextType.Load) {
         if (name.equals("__debug__")) {
            this.code_compiler.error("can not assign to __debug__", true, node);
         }

         this.cur.addBound(name);
      } else {
         this.cur.addUsed(name);
      }

      return null;
   }

   public Object visitListComp(ListComp node) throws Exception {
      String tmp = "_[" + node.getLine() + "_" + node.getCharPositionInLine() + "]";
      this.cur.addBound(tmp);
      this.traverse(node);
      return null;
   }

   public Object visitDictComp(DictComp node) throws Exception {
      List kv = Arrays.asList(node.getInternalKey(), node.getInternalValue());
      return this.visitInternalGenerators(node, new Tuple(node, kv, expr_contextType.UNDEFINED), node.getInternalGenerators());
   }

   public Object visitSetComp(SetComp node) throws Exception {
      return this.visitInternalGenerators(node, node.getInternalElt(), node.getInternalGenerators());
   }

   public Object visitYield(Yield node) throws Exception {
      this.cur.defineAsGenerator(node);
      ++this.cur.yield_count;
      this.traverse(node);
      return null;
   }

   public Object visitReturn(Return node) throws Exception {
      if (node.getInternalValue() != null) {
         this.cur.noteReturnValue(node);
      }

      this.traverse(node);
      return null;
   }

   private Object visitInternalGenerators(expr node, expr elt, List generators) throws Exception {
      if (generators != null && generators.size() > 0) {
         this.visit(((comprehension)generators.get(0)).getInternalIter());
      }

      String bound_exp = "_(x)";
      String tmp = "_(" + node.getLine() + "_" + node.getCharPositionInLine() + ")";
      this.def(tmp);
      ArgListCompiler ac = new ArgListCompiler();
      List args = new ArrayList();
      args.add(new Name(node.getToken(), bound_exp, expr_contextType.Param));
      ac.visitArgs(new arguments(node, args, (String)null, (String)null, new ArrayList()));
      this.beginScope(tmp, 1, node, ac);
      this.cur.addParam(bound_exp);
      this.cur.markFromParam();
      this.cur.defineAsGenerator(node);
      ++this.cur.yield_count;
      if (elt != null) {
         this.visit(elt);
      }

      if (generators != null) {
         for(int i = 0; i < generators.size(); ++i) {
            if (generators.get(i) != null) {
               if (i == 0) {
                  this.visit(((comprehension)generators.get(i)).getInternalTarget());
                  if (((comprehension)generators.get(i)).getInternalIfs() != null) {
                     Iterator var9 = ((comprehension)generators.get(i)).getInternalIfs().iterator();

                     while(var9.hasNext()) {
                        expr cond = (expr)var9.next();
                        if (cond != null) {
                           this.visit(cond);
                        }
                     }
                  }
               } else {
                  this.visit((PythonTree)generators.get(i));
               }
            }
         }
      }

      this.endScope();
      return null;
   }

   public Object visitGeneratorExp(GeneratorExp node) throws Exception {
      return this.visitInternalGenerators(node, node.getInternalElt(), node.getInternalGenerators());
   }

   public Object visitWith(With node) throws Exception {
      ++this.cur.max_with_count;
      this.traverse(node);
      return null;
   }
}
