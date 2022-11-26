package org.python.compiler;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.python.antlr.ParseException;
import org.python.antlr.ast.Expr;
import org.python.antlr.ast.ImportFrom;
import org.python.antlr.ast.Interactive;
import org.python.antlr.ast.Str;
import org.python.antlr.ast.alias;
import org.python.antlr.base.mod;
import org.python.antlr.base.stmt;
import org.python.core.CodeFlag;
import org.python.core.CompilerFlags;
import org.python.core.FutureFeature;
import org.python.core.Pragma;
import org.python.core.PragmaReceiver;

public class Future {
   Set featureSet = EnumSet.noneOf(FutureFeature.class);
   private final PragmaReceiver features = new PragmaReceiver() {
      public void add(Pragma pragma) {
         if (pragma instanceof FutureFeature) {
            FutureFeature feature = (FutureFeature)pragma;
            Future.this.featureSet.add(feature);
         }

      }
   };

   private boolean check(ImportFrom cand) throws Exception {
      if (!cand.getInternalModule().equals("__future__")) {
         return false;
      } else if (cand.getInternalNames().isEmpty()) {
         throw new ParseException("future statement does not support import *", cand);
      } else {
         try {
            Iterator var2 = cand.getInternalNames().iterator();

            while(var2.hasNext()) {
               alias feature = (alias)var2.next();
               FutureFeature.addFeature(feature.getInternalName(), this.features);
            }

            return true;
         } catch (ParseException var4) {
            throw new ParseException(var4.getMessage(), cand);
         }
      }
   }

   public void preprocessFutures(mod node, CompilerFlags cflags) throws Exception {
      if (cflags != null) {
         if (cflags.isFlagSet(CodeFlag.CO_FUTURE_DIVISION)) {
            FutureFeature.division.addTo(this.features);
         }

         if (cflags.isFlagSet(CodeFlag.CO_FUTURE_WITH_STATEMENT)) {
            FutureFeature.with_statement.addTo(this.features);
         }

         if (cflags.isFlagSet(CodeFlag.CO_FUTURE_ABSOLUTE_IMPORT)) {
            FutureFeature.absolute_import.addTo(this.features);
         }

         if (cflags.isFlagSet(CodeFlag.CO_FUTURE_PRINT_FUNCTION)) {
            FutureFeature.print_function.addTo(this.features);
         }

         if (cflags.isFlagSet(CodeFlag.CO_FUTURE_UNICODE_LITERALS)) {
            FutureFeature.unicode_literals.addTo(this.features);
         }
      }

      int beg = 0;
      List suite = null;
      if (node instanceof org.python.antlr.ast.Module) {
         suite = ((org.python.antlr.ast.Module)node).getInternalBody();
         if (suite.size() > 0 && suite.get(0) instanceof Expr && ((Expr)suite.get(0)).getInternalValue() instanceof Str) {
            ++beg;
         }
      } else {
         if (!(node instanceof Interactive)) {
            return;
         }

         suite = ((Interactive)node).getInternalBody();
      }

      for(int i = beg; i < suite.size(); ++i) {
         stmt s = (stmt)suite.get(i);
         if (!(s instanceof ImportFrom)) {
            break;
         }

         s.from_future_checked = true;
         if (!this.check((ImportFrom)s)) {
            break;
         }
      }

      if (cflags != null) {
         Iterator var7 = this.featureSet.iterator();

         while(var7.hasNext()) {
            FutureFeature feature = (FutureFeature)var7.next();
            feature.setFlag(cflags);
         }
      }

   }

   public static void checkFromFuture(ImportFrom node) throws Exception {
      if (!node.from_future_checked) {
         if (node.getInternalModule().equals("__future__")) {
            throw new ParseException("from __future__ imports must occur at the beginning of the file", node);
         } else {
            node.from_future_checked = true;
         }
      }
   }

   public boolean areDivisionOn() {
      return this.featureSet.contains(FutureFeature.division);
   }

   public boolean withStatementSupported() {
      return this.featureSet.contains(FutureFeature.with_statement);
   }

   public boolean isAbsoluteImportOn() {
      return this.featureSet.contains(FutureFeature.absolute_import);
   }
}
