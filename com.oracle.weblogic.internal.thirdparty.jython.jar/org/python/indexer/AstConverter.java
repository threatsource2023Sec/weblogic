package org.python.indexer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import org.python.antlr.ast.Ellipsis;
import org.python.antlr.ast.ExceptHandler;
import org.python.antlr.ast.Exec;
import org.python.antlr.ast.Expr;
import org.python.antlr.ast.For;
import org.python.antlr.ast.FunctionDef;
import org.python.antlr.ast.GeneratorExp;
import org.python.antlr.ast.Global;
import org.python.antlr.ast.If;
import org.python.antlr.ast.IfExp;
import org.python.antlr.ast.Import;
import org.python.antlr.ast.ImportFrom;
import org.python.antlr.ast.Index;
import org.python.antlr.ast.Lambda;
import org.python.antlr.ast.ListComp;
import org.python.antlr.ast.Module;
import org.python.antlr.ast.Name;
import org.python.antlr.ast.Num;
import org.python.antlr.ast.Pass;
import org.python.antlr.ast.Print;
import org.python.antlr.ast.Raise;
import org.python.antlr.ast.Repr;
import org.python.antlr.ast.Return;
import org.python.antlr.ast.Slice;
import org.python.antlr.ast.Str;
import org.python.antlr.ast.Subscript;
import org.python.antlr.ast.TryExcept;
import org.python.antlr.ast.TryFinally;
import org.python.antlr.ast.Tuple;
import org.python.antlr.ast.UnaryOp;
import org.python.antlr.ast.While;
import org.python.antlr.ast.With;
import org.python.antlr.ast.Yield;
import org.python.antlr.ast.alias;
import org.python.antlr.ast.arguments;
import org.python.antlr.ast.boolopType;
import org.python.antlr.ast.cmpopType;
import org.python.antlr.ast.comprehension;
import org.python.antlr.ast.keyword;
import org.python.antlr.ast.operatorType;
import org.python.antlr.ast.unaryopType;
import org.python.antlr.base.excepthandler;
import org.python.antlr.base.expr;
import org.python.antlr.base.stmt;
import org.python.indexer.ast.NAlias;
import org.python.indexer.ast.NAssert;
import org.python.indexer.ast.NAssign;
import org.python.indexer.ast.NAttribute;
import org.python.indexer.ast.NAugAssign;
import org.python.indexer.ast.NBinOp;
import org.python.indexer.ast.NBlock;
import org.python.indexer.ast.NBoolOp;
import org.python.indexer.ast.NBreak;
import org.python.indexer.ast.NCall;
import org.python.indexer.ast.NClassDef;
import org.python.indexer.ast.NCompare;
import org.python.indexer.ast.NComprehension;
import org.python.indexer.ast.NContinue;
import org.python.indexer.ast.NDelete;
import org.python.indexer.ast.NDict;
import org.python.indexer.ast.NEllipsis;
import org.python.indexer.ast.NExceptHandler;
import org.python.indexer.ast.NExec;
import org.python.indexer.ast.NExprStmt;
import org.python.indexer.ast.NFor;
import org.python.indexer.ast.NFunctionDef;
import org.python.indexer.ast.NGeneratorExp;
import org.python.indexer.ast.NGlobal;
import org.python.indexer.ast.NIf;
import org.python.indexer.ast.NIfExp;
import org.python.indexer.ast.NImport;
import org.python.indexer.ast.NImportFrom;
import org.python.indexer.ast.NIndex;
import org.python.indexer.ast.NKeyword;
import org.python.indexer.ast.NLambda;
import org.python.indexer.ast.NList;
import org.python.indexer.ast.NListComp;
import org.python.indexer.ast.NModule;
import org.python.indexer.ast.NName;
import org.python.indexer.ast.NNode;
import org.python.indexer.ast.NNum;
import org.python.indexer.ast.NPass;
import org.python.indexer.ast.NPrint;
import org.python.indexer.ast.NQname;
import org.python.indexer.ast.NRaise;
import org.python.indexer.ast.NRepr;
import org.python.indexer.ast.NReturn;
import org.python.indexer.ast.NSlice;
import org.python.indexer.ast.NStr;
import org.python.indexer.ast.NSubscript;
import org.python.indexer.ast.NTryExcept;
import org.python.indexer.ast.NTryFinally;
import org.python.indexer.ast.NTuple;
import org.python.indexer.ast.NUnaryOp;
import org.python.indexer.ast.NWhile;
import org.python.indexer.ast.NWith;
import org.python.indexer.ast.NYield;

public class AstConverter extends Visitor {
   public String convOp(Object t) {
      if (t instanceof operatorType) {
         switch ((operatorType)t) {
            case Add:
               return "+";
            case Sub:
               return "-";
            case Mult:
               return "*";
            case Div:
               return "/";
            case Mod:
               return "%";
            case Pow:
               return "**";
            case LShift:
               return "<<";
            case RShift:
               return ">>";
            case BitOr:
               return "|";
            case BitXor:
               return "^";
            case BitAnd:
               return "&";
            case FloorDiv:
               return "//";
            default:
               return null;
         }
      } else if (t instanceof boolopType) {
         switch ((boolopType)t) {
            case And:
               return "and";
            case Or:
               return "or";
            default:
               return null;
         }
      } else if (t instanceof unaryopType) {
         switch ((unaryopType)t) {
            case Invert:
               return "~";
            case Not:
               return "not";
            case USub:
               return "-";
            case UAdd:
               return "+";
            default:
               return null;
         }
      } else if (t instanceof cmpopType) {
         switch ((cmpopType)t) {
            case Eq:
               return "==";
            case NotEq:
               return "!=";
            case Gt:
               return ">";
            case GtE:
               return ">=";
            case Lt:
               return "<";
            case LtE:
               return "<=";
            case In:
               return "in";
            case NotIn:
               return "not in";
            case Is:
               return "is";
            case IsNot:
               return "is not";
            default:
               return null;
         }
      } else {
         return null;
      }
   }

   private List convertListExceptHandler(List in) throws Exception {
      List out = new ArrayList(in == null ? 0 : in.size());
      if (in != null) {
         Iterator var3 = in.iterator();

         while(var3.hasNext()) {
            excepthandler e = (excepthandler)var3.next();
            NExceptHandler nxh = (NExceptHandler)e.accept(this);
            if (nxh != null) {
               out.add(nxh);
            }
         }
      }

      return out;
   }

   private List convertListExpr(List in) throws Exception {
      List out = new ArrayList(in == null ? 0 : in.size());
      if (in != null) {
         Iterator var3 = in.iterator();

         while(var3.hasNext()) {
            expr e = (expr)var3.next();
            NNode nx = (NNode)e.accept(this);
            if (nx != null) {
               out.add(nx);
            }
         }
      }

      return out;
   }

   private List convertListName(List in) throws Exception {
      List out = new ArrayList(in == null ? 0 : in.size());
      if (in != null) {
         Iterator var3 = in.iterator();

         while(var3.hasNext()) {
            expr e = (expr)var3.next();
            NName nn = (NName)e.accept(this);
            if (nn != null) {
               out.add(nn);
            }
         }
      }

      return out;
   }

   private NQname convertQname(List in) throws Exception {
      if (in == null) {
         return null;
      } else {
         NQname out = null;
         int end = -1;

         for(int i = in.size() - 1; i >= 0; --i) {
            Name n = (Name)in.get(i);
            if (end == -1) {
               end = n.getCharStopIndex();
            }

            NName nn = (NName)n.accept(this);
            out = new NQname(out, nn, n.getCharStartIndex(), end);
         }

         return out;
      }
   }

   private List convertListKeyword(List in) throws Exception {
      List out = new ArrayList(in == null ? 0 : in.size());
      if (in != null) {
         Iterator var3 = in.iterator();

         while(var3.hasNext()) {
            keyword e = (keyword)var3.next();
            NKeyword nkw = new NKeyword(e.getInternalArg(), this.convExpr(e.getInternalValue()));
            if (nkw != null) {
               out.add(nkw);
            }
         }
      }

      return out;
   }

   private NBlock convertListStmt(List in) throws Exception {
      List out = new ArrayList(in == null ? 0 : in.size());
      if (in != null) {
         Iterator var3 = in.iterator();

         while(var3.hasNext()) {
            stmt e = (stmt)var3.next();
            NNode nx = (NNode)e.accept(this);
            if (nx != null) {
               out.add(nx);
            }
         }
      }

      return new NBlock(out, 0, 0);
   }

   private NNode convExpr(PythonTree e) throws Exception {
      if (e == null) {
         return null;
      } else {
         Object o = e.accept(this);
         return o instanceof NNode ? (NNode)o : null;
      }
   }

   private int start(PythonTree tree) {
      return tree.getCharStartIndex();
   }

   private int stop(PythonTree tree) {
      return tree.getCharStopIndex();
   }

   public Object visitAssert(Assert n) throws Exception {
      return new NAssert(this.convExpr(n.getInternalTest()), this.convExpr(n.getInternalMsg()), this.start(n), this.stop(n));
   }

   public Object visitAssign(Assign n) throws Exception {
      return new NAssign(this.convertListExpr(n.getInternalTargets()), this.convExpr(n.getInternalValue()), this.start(n), this.stop(n));
   }

   public Object visitAttribute(Attribute n) throws Exception {
      return new NAttribute(this.convExpr(n.getInternalValue()), (NName)this.convExpr(n.getInternalAttrName()), this.start(n), this.stop(n));
   }

   public Object visitAugAssign(AugAssign n) throws Exception {
      return new NAugAssign(this.convExpr(n.getInternalTarget()), this.convExpr(n.getInternalValue()), this.convOp(n.getInternalOp()), this.start(n), this.stop(n));
   }

   public Object visitBinOp(BinOp n) throws Exception {
      return new NBinOp(this.convExpr(n.getInternalLeft()), this.convExpr(n.getInternalRight()), this.convOp(n.getInternalOp()), this.start(n), this.stop(n));
   }

   public Object visitBoolOp(BoolOp n) throws Exception {
      NBoolOp.OpType op;
      switch (n.getInternalOp()) {
         case And:
            op = NBoolOp.OpType.AND;
            break;
         case Or:
            op = NBoolOp.OpType.OR;
            break;
         default:
            op = NBoolOp.OpType.UNDEFINED;
      }

      return new NBoolOp(op, this.convertListExpr(n.getInternalValues()), this.start(n), this.stop(n));
   }

   public Object visitBreak(Break n) throws Exception {
      return new NBreak(this.start(n), this.stop(n));
   }

   public Object visitCall(Call n) throws Exception {
      return new NCall(this.convExpr(n.getInternalFunc()), this.convertListExpr(n.getInternalArgs()), this.convertListKeyword(n.getInternalKeywords()), this.convExpr(n.getInternalKwargs()), this.convExpr(n.getInternalStarargs()), this.start(n), this.stop(n));
   }

   public Object visitClassDef(ClassDef n) throws Exception {
      return new NClassDef((NName)this.convExpr(n.getInternalNameNode()), this.convertListExpr(n.getInternalBases()), this.convertListStmt(n.getInternalBody()), this.start(n), this.stop(n));
   }

   public Object visitCompare(Compare n) throws Exception {
      return new NCompare(this.convExpr(n.getInternalLeft()), (List)null, this.convertListExpr(n.getInternalComparators()), this.start(n), this.stop(n));
   }

   public Object visitContinue(Continue n) throws Exception {
      return new NContinue(this.start(n), this.stop(n));
   }

   public Object visitDelete(Delete n) throws Exception {
      return new NDelete(this.convertListExpr(n.getInternalTargets()), this.start(n), this.stop(n));
   }

   public Object visitDict(Dict n) throws Exception {
      return new NDict(this.convertListExpr(n.getInternalKeys()), this.convertListExpr(n.getInternalValues()), this.start(n), this.stop(n));
   }

   public Object visitEllipsis(Ellipsis n) throws Exception {
      return new NEllipsis(this.start(n), this.stop(n));
   }

   public Object visitExceptHandler(ExceptHandler n) throws Exception {
      return new NExceptHandler(this.convExpr(n.getInternalName()), this.convExpr(n.getInternalType()), this.convertListStmt(n.getInternalBody()), this.start(n), this.stop(n));
   }

   public Object visitExec(Exec n) throws Exception {
      return new NExec(this.convExpr(n.getInternalBody()), this.convExpr(n.getInternalGlobals()), this.convExpr(n.getInternalLocals()), this.start(n), this.stop(n));
   }

   public Object visitExpr(Expr n) throws Exception {
      return new NExprStmt(this.convExpr(n.getInternalValue()), this.start(n), this.stop(n));
   }

   public Object visitFor(For n) throws Exception {
      return new NFor(this.convExpr(n.getInternalTarget()), this.convExpr(n.getInternalIter()), this.convertListStmt(n.getInternalBody()), this.convertListStmt(n.getInternalOrelse()), this.start(n), this.stop(n));
   }

   public Object visitFunctionDef(FunctionDef n) throws Exception {
      arguments args = n.getInternalArgs();
      NFunctionDef fn = new NFunctionDef((NName)this.convExpr(n.getInternalNameNode()), this.convertListExpr(args.getInternalArgs()), this.convertListStmt(n.getInternalBody()), this.convertListExpr(args.getInternalDefaults()), (NName)this.convExpr(args.getInternalVarargName()), (NName)this.convExpr(args.getInternalKwargName()), this.start(n), this.stop(n));
      fn.setDecoratorList(this.convertListExpr(n.getInternalDecorator_list()));
      return fn;
   }

   public Object visitGeneratorExp(GeneratorExp n) throws Exception {
      List generators = new ArrayList(n.getInternalGenerators().size());
      Iterator var3 = n.getInternalGenerators().iterator();

      while(var3.hasNext()) {
         comprehension c = (comprehension)var3.next();
         generators.add(new NComprehension(this.convExpr(c.getInternalTarget()), this.convExpr(c.getInternalIter()), this.convertListExpr(c.getInternalIfs()), this.start(c), this.stop(c)));
      }

      return new NGeneratorExp(this.convExpr(n.getInternalElt()), generators, this.start(n), this.stop(n));
   }

   public Object visitGlobal(Global n) throws Exception {
      return new NGlobal(this.convertListName(n.getInternalNameNodes()), this.start(n), this.stop(n));
   }

   public Object visitIf(If n) throws Exception {
      return new NIf(this.convExpr(n.getInternalTest()), this.convertListStmt(n.getInternalBody()), this.convertListStmt(n.getInternalOrelse()), this.start(n), this.stop(n));
   }

   public Object visitIfExp(IfExp n) throws Exception {
      return new NIfExp(this.convExpr(n.getInternalTest()), this.convExpr(n.getInternalBody()), this.convExpr(n.getInternalOrelse()), this.start(n), this.stop(n));
   }

   public Object visitImport(Import n) throws Exception {
      List aliases = new ArrayList(n.getInternalNames().size());
      Iterator var3 = n.getInternalNames().iterator();

      while(var3.hasNext()) {
         alias e = (alias)var3.next();
         aliases.add(new NAlias(e.getInternalName(), this.convertQname(e.getInternalNameNodes()), (NName)this.convExpr(e.getInternalAsnameNode()), this.start(e), this.stop(e)));
      }

      return new NImport(aliases, this.start(n), this.stop(n));
   }

   public Object visitImportFrom(ImportFrom n) throws Exception {
      List aliases = new ArrayList(n.getInternalNames().size());
      Iterator var3 = n.getInternalNames().iterator();

      while(var3.hasNext()) {
         alias e = (alias)var3.next();
         aliases.add(new NAlias(e.getInternalName(), this.convertQname(e.getInternalNameNodes()), (NName)this.convExpr(e.getInternalAsnameNode()), this.start(e), this.stop(e)));
      }

      return new NImportFrom(n.getInternalModule(), this.convertQname(n.getInternalModuleNames()), aliases, this.start(n), this.stop(n));
   }

   public Object visitIndex(Index n) throws Exception {
      return new NIndex(this.convExpr(n.getInternalValue()), this.start(n), this.stop(n));
   }

   public Object visitLambda(Lambda n) throws Exception {
      arguments args = n.getInternalArgs();
      return new NLambda(this.convertListExpr(args.getInternalArgs()), this.convExpr(n.getInternalBody()), this.convertListExpr(args.getInternalDefaults()), (NName)this.convExpr(args.getInternalVarargName()), (NName)this.convExpr(args.getInternalKwargName()), this.start(n), this.stop(n));
   }

   public Object visitList(org.python.antlr.ast.List n) throws Exception {
      return new NList(this.convertListExpr(n.getInternalElts()), this.start(n), this.stop(n));
   }

   public Object visitListComp(ListComp n) throws Exception {
      List generators = new ArrayList(n.getInternalGenerators().size());
      Iterator var3 = n.getInternalGenerators().iterator();

      while(var3.hasNext()) {
         comprehension c = (comprehension)var3.next();
         generators.add(new NComprehension(this.convExpr(c.getInternalTarget()), this.convExpr(c.getInternalIter()), this.convertListExpr(c.getInternalIfs()), this.start(c), this.stop(c)));
      }

      return new NListComp(this.convExpr(n.getInternalElt()), generators, this.start(n), this.stop(n));
   }

   public Object visitModule(Module n) throws Exception {
      return new NModule(this.convertListStmt(n.getInternalBody()), this.start(n), this.stop(n));
   }

   public Object visitName(Name n) throws Exception {
      return new NName(n.getInternalId(), this.start(n), this.stop(n));
   }

   public Object visitNum(Num n) throws Exception {
      return new NNum(n.getInternalN(), this.start(n), this.stop(n));
   }

   public Object visitPass(Pass n) throws Exception {
      return new NPass(this.start(n), this.stop(n));
   }

   public Object visitPrint(Print n) throws Exception {
      return new NPrint(this.convExpr(n.getInternalDest()), this.convertListExpr(n.getInternalValues()), this.start(n), this.stop(n));
   }

   public Object visitRaise(Raise n) throws Exception {
      return new NRaise(this.convExpr(n.getInternalType()), this.convExpr(n.getInternalInst()), this.convExpr(n.getInternalTback()), this.start(n), this.stop(n));
   }

   public Object visitRepr(Repr n) throws Exception {
      return new NRepr(this.convExpr(n.getInternalValue()), this.start(n), this.stop(n));
   }

   public Object visitReturn(Return n) throws Exception {
      return new NReturn(this.convExpr(n.getInternalValue()), this.start(n), this.stop(n));
   }

   public Object visitSlice(Slice n) throws Exception {
      return new NSlice(this.convExpr(n.getInternalLower()), this.convExpr(n.getInternalStep()), this.convExpr(n.getInternalUpper()), this.start(n), this.stop(n));
   }

   public Object visitStr(Str n) throws Exception {
      return new NStr(n.getInternalS(), this.start(n), this.stop(n));
   }

   public Object visitSubscript(Subscript n) throws Exception {
      return new NSubscript(this.convExpr(n.getInternalValue()), this.convExpr(n.getInternalSlice()), this.start(n), this.stop(n));
   }

   public Object visitTryExcept(TryExcept n) throws Exception {
      return new NTryExcept(this.convertListExceptHandler(n.getInternalHandlers()), this.convertListStmt(n.getInternalBody()), this.convertListStmt(n.getInternalOrelse()), this.start(n), this.stop(n));
   }

   public Object visitTryFinally(TryFinally n) throws Exception {
      return new NTryFinally(this.convertListStmt(n.getInternalBody()), this.convertListStmt(n.getInternalFinalbody()), this.start(n), this.stop(n));
   }

   public Object visitTuple(Tuple n) throws Exception {
      return new NTuple(this.convertListExpr(n.getInternalElts()), this.start(n), this.stop(n));
   }

   public Object visitUnaryOp(UnaryOp n) throws Exception {
      return new NUnaryOp((NNode)null, this.convExpr(n.getInternalOperand()), this.start(n), this.stop(n));
   }

   public Object visitWhile(While n) throws Exception {
      return new NWhile(this.convExpr(n.getInternalTest()), this.convertListStmt(n.getInternalBody()), this.convertListStmt(n.getInternalOrelse()), this.start(n), this.stop(n));
   }

   public Object visitWith(With n) throws Exception {
      return new NWith(this.convExpr(n.getInternalOptional_vars()), this.convExpr(n.getInternalContext_expr()), this.convertListStmt(n.getInternalBody()), this.start(n), this.stop(n));
   }

   public Object visitYield(Yield n) throws Exception {
      return new NYield(this.convExpr(n.getInternalValue()), this.start(n), this.stop(n));
   }
}
