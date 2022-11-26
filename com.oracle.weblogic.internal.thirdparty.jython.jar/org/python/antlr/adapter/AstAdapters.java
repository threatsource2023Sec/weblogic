package org.python.antlr.adapter;

import java.util.List;
import org.python.antlr.ast.arguments;
import org.python.antlr.ast.boolopType;
import org.python.antlr.ast.cmpopType;
import org.python.antlr.ast.expr_contextType;
import org.python.antlr.ast.operatorType;
import org.python.antlr.ast.unaryopType;
import org.python.antlr.base.expr;
import org.python.antlr.base.slice;
import org.python.antlr.base.stmt;
import org.python.antlr.op.Add;
import org.python.antlr.op.And;
import org.python.antlr.op.AugLoad;
import org.python.antlr.op.AugStore;
import org.python.antlr.op.BitAnd;
import org.python.antlr.op.BitOr;
import org.python.antlr.op.BitXor;
import org.python.antlr.op.Del;
import org.python.antlr.op.Div;
import org.python.antlr.op.Eq;
import org.python.antlr.op.FloorDiv;
import org.python.antlr.op.Gt;
import org.python.antlr.op.GtE;
import org.python.antlr.op.In;
import org.python.antlr.op.Invert;
import org.python.antlr.op.Is;
import org.python.antlr.op.IsNot;
import org.python.antlr.op.LShift;
import org.python.antlr.op.Load;
import org.python.antlr.op.Lt;
import org.python.antlr.op.LtE;
import org.python.antlr.op.Mod;
import org.python.antlr.op.Mult;
import org.python.antlr.op.Not;
import org.python.antlr.op.NotEq;
import org.python.antlr.op.NotIn;
import org.python.antlr.op.Or;
import org.python.antlr.op.Param;
import org.python.antlr.op.Pow;
import org.python.antlr.op.RShift;
import org.python.antlr.op.Store;
import org.python.antlr.op.Sub;
import org.python.antlr.op.UAdd;
import org.python.antlr.op.USub;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyString;

public class AstAdapters {
   public static final AliasAdapter aliasAdapter = new AliasAdapter();
   public static final CmpopAdapter cmpopAdapter = new CmpopAdapter();
   public static final ComprehensionAdapter comprehensionAdapter = new ComprehensionAdapter();
   public static final ExcepthandlerAdapter excepthandlerAdapter = new ExcepthandlerAdapter();
   public static final ExprAdapter exprAdapter = new ExprAdapter();
   public static final IdentifierAdapter identifierAdapter = new IdentifierAdapter();
   public static final KeywordAdapter keywordAdapter = new KeywordAdapter();
   public static final SliceAdapter sliceAdapter = new SliceAdapter();
   public static final StmtAdapter stmtAdapter = new StmtAdapter();

   public static List py2aliasList(PyObject o) {
      return aliasAdapter.iter2ast(o);
   }

   public static List py2cmpopList(PyObject o) {
      return cmpopAdapter.iter2ast(o);
   }

   public static List py2comprehensionList(PyObject o) {
      return comprehensionAdapter.iter2ast(o);
   }

   public static List py2excepthandlerList(PyObject o) {
      return excepthandlerAdapter.iter2ast(o);
   }

   public static List py2exprList(PyObject o) {
      return exprAdapter.iter2ast(o);
   }

   public static List py2identifierList(PyObject o) {
      return identifierAdapter.iter2ast(o);
   }

   public static List py2keywordList(PyObject o) {
      return keywordAdapter.iter2ast(o);
   }

   public static List py2sliceList(PyObject o) {
      return sliceAdapter.iter2ast(o);
   }

   public static List py2stmtList(PyObject o) {
      return stmtAdapter.iter2ast(o);
   }

   public static expr py2expr(PyObject o) {
      return (expr)exprAdapter.py2ast(o);
   }

   public static Integer py2int(Object o) {
      return o != null && !(o instanceof Integer) ? null : (Integer)o;
   }

   public static String py2identifier(PyObject o) {
      return (String)identifierAdapter.py2ast(o);
   }

   public static expr_contextType py2expr_context(Object o) {
      if (o != null && !(o instanceof expr_contextType)) {
         if (o instanceof PyObject && o != Py.None) {
            switch (((PyObject)o).asInt()) {
               case 1:
                  return expr_contextType.Load;
               case 2:
                  return expr_contextType.Store;
               case 3:
                  return expr_contextType.Del;
               case 4:
                  return expr_contextType.AugLoad;
               case 5:
                  return expr_contextType.AugStore;
               case 6:
                  return expr_contextType.Param;
               default:
                  return expr_contextType.UNDEFINED;
            }
         } else {
            return expr_contextType.UNDEFINED;
         }
      } else {
         return (expr_contextType)o;
      }
   }

   public static slice py2slice(PyObject o) {
      return (slice)sliceAdapter.py2ast(o);
   }

   public static stmt py2stmt(PyObject o) {
      return (stmt)stmtAdapter.py2ast(o);
   }

   public static Object py2string(Object o) {
      return o instanceof PyString ? o : null;
   }

   public static operatorType py2operator(Object o) {
      if (o != null && !(o instanceof operatorType)) {
         if (o instanceof PyObject && o != Py.None) {
            switch (((PyObject)o).asInt()) {
               case 1:
                  return operatorType.Add;
               case 2:
                  return operatorType.Sub;
               case 3:
                  return operatorType.Mult;
               case 4:
                  return operatorType.Div;
               case 5:
                  return operatorType.Mod;
               case 6:
                  return operatorType.Pow;
               case 7:
                  return operatorType.LShift;
               case 8:
                  return operatorType.RShift;
               case 9:
                  return operatorType.BitOr;
               case 10:
                  return operatorType.BitXor;
               case 11:
                  return operatorType.BitAnd;
               case 12:
                  return operatorType.FloorDiv;
               default:
                  return operatorType.UNDEFINED;
            }
         } else {
            return operatorType.UNDEFINED;
         }
      } else {
         return (operatorType)o;
      }
   }

   public static PyObject operator2py(operatorType o) {
      switch (o) {
         case Add:
            return new Add();
         case Sub:
            return new Sub();
         case Mult:
            return new Mult();
         case Div:
            return new Div();
         case Mod:
            return new Mod();
         case Pow:
            return new Pow();
         case LShift:
            return new LShift();
         case RShift:
            return new RShift();
         case BitOr:
            return new BitOr();
         case BitXor:
            return new BitXor();
         case BitAnd:
            return new BitAnd();
         case FloorDiv:
            return new FloorDiv();
         default:
            return Py.None;
      }
   }

   public static PyObject boolop2py(boolopType o) {
      switch (o) {
         case And:
            return new And();
         case Or:
            return new Or();
         default:
            return Py.None;
      }
   }

   public static PyObject cmpop2py(cmpopType o) {
      switch (o) {
         case Eq:
            return new Eq();
         case NotEq:
            return new NotEq();
         case Lt:
            return new Lt();
         case LtE:
            return new LtE();
         case Gt:
            return new Gt();
         case GtE:
            return new GtE();
         case Is:
            return new Is();
         case IsNot:
            return new IsNot();
         case In:
            return new In();
         case NotIn:
            return new NotIn();
         default:
            return Py.None;
      }
   }

   public static PyObject unaryop2py(unaryopType o) {
      switch (o) {
         case Invert:
            return new Invert();
         case Not:
            return new Not();
         case UAdd:
            return new UAdd();
         case USub:
            return new USub();
         default:
            return Py.None;
      }
   }

   public static PyObject expr_context2py(expr_contextType o) {
      switch (o) {
         case Load:
            return new Load();
         case Store:
            return new Store();
         case Del:
            return new Del();
         case AugLoad:
            return new AugLoad();
         case AugStore:
            return new AugStore();
         case Param:
            return new Param();
         default:
            return Py.None;
      }
   }

   public static boolopType py2boolop(Object o) {
      if (o != null && !(o instanceof boolopType)) {
         if (o instanceof PyObject && o != Py.None) {
            switch (((PyObject)o).asInt()) {
               case 1:
                  return boolopType.And;
               case 2:
                  return boolopType.Or;
               default:
                  return boolopType.UNDEFINED;
            }
         } else {
            return boolopType.UNDEFINED;
         }
      } else {
         return (boolopType)o;
      }
   }

   public static arguments py2arguments(Object o) {
      return o instanceof arguments ? (arguments)o : null;
   }

   public static Object py2object(Object o) {
      return o;
   }

   public static Boolean py2bool(Object o) {
      return o instanceof Boolean ? (Boolean)o : null;
   }

   public static unaryopType py2unaryop(Object o) {
      if (o != null && !(o instanceof unaryopType)) {
         if (o instanceof PyObject && o != Py.None) {
            switch (((PyObject)o).asInt()) {
               case 1:
                  return unaryopType.Invert;
               case 2:
                  return unaryopType.Not;
               case 3:
                  return unaryopType.UAdd;
               case 4:
                  return unaryopType.USub;
               default:
                  return unaryopType.UNDEFINED;
            }
         } else {
            return unaryopType.UNDEFINED;
         }
      } else {
         return (unaryopType)o;
      }
   }
}
