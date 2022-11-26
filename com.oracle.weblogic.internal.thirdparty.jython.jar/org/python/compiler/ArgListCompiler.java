package org.python.compiler;

import java.util.ArrayList;
import java.util.List;
import org.python.antlr.ParseException;
import org.python.antlr.PythonTree;
import org.python.antlr.Visitor;
import org.python.antlr.ast.Assign;
import org.python.antlr.ast.Name;
import org.python.antlr.ast.Suite;
import org.python.antlr.ast.Tuple;
import org.python.antlr.ast.arguments;
import org.python.antlr.ast.expr_contextType;

public class ArgListCompiler extends Visitor {
   public boolean arglist;
   public boolean keywordlist;
   public List defaults;
   public List names;
   public List fpnames;
   public List init_code;

   public ArgListCompiler() {
      this.arglist = this.keywordlist = false;
      this.defaults = null;
      this.names = new ArrayList();
      this.fpnames = new ArrayList();
      this.init_code = new ArrayList();
   }

   public void reset() {
      this.arglist = this.keywordlist = false;
      this.defaults = null;
      this.names.clear();
      this.init_code.clear();
   }

   public void appendInitCode(Suite node) {
      node.getInternalBody().addAll(0, this.init_code);
   }

   public List getDefaults() {
      return this.defaults;
   }

   public void visitArgs(arguments args) throws Exception {
      int i;
      for(i = 0; i < args.getInternalArgs().size(); ++i) {
         String name = (String)this.visit((PythonTree)args.getInternalArgs().get(i));
         this.names.add(name);
         if (args.getInternalArgs().get(i) instanceof Tuple) {
            List targets = new ArrayList();
            targets.add(args.getInternalArgs().get(i));
            Assign ass = new Assign((PythonTree)args.getInternalArgs().get(i), targets, new Name((PythonTree)args.getInternalArgs().get(i), name, expr_contextType.Load));
            this.init_code.add(ass);
         }
      }

      if (args.getInternalVararg() != null) {
         this.arglist = true;
         this.names.add(args.getInternalVararg());
      }

      if (args.getInternalKwarg() != null) {
         this.keywordlist = true;
         this.names.add(args.getInternalKwarg());
      }

      this.defaults = args.getInternalDefaults();

      for(i = 0; i < this.defaults.size(); ++i) {
         if (this.defaults.get(i) == null) {
            throw new ParseException("non-default argument follows default argument", (PythonTree)args.getInternalArgs().get(args.getInternalArgs().size() - this.defaults.size() + i));
         }
      }

   }

   public Object visitName(Name node) throws Exception {
      if (node.getInternalCtx() != expr_contextType.Store && node.getInternalCtx() != expr_contextType.Param) {
         return null;
      } else if (this.fpnames.contains(node.getInternalId())) {
         throw new ParseException("duplicate argument name found: " + node.getInternalId(), node);
      } else {
         this.fpnames.add(node.getInternalId());
         return node.getInternalId();
      }
   }

   public Object visitTuple(Tuple node) throws Exception {
      StringBuffer name = new StringBuffer("(");
      int n = node.getInternalElts().size();

      for(int i = 0; i < n - 1; ++i) {
         name.append(this.visit((PythonTree)node.getInternalElts().get(i)));
         name.append(", ");
      }

      name.append(this.visit((PythonTree)node.getInternalElts().get(n - 1)));
      name.append(")");
      return name.toString();
   }
}
