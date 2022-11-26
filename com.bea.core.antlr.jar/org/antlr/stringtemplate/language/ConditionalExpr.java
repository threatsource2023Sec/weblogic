package org.antlr.stringtemplate.language;

import antlr.RecognitionException;
import antlr.collections.AST;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateWriter;

public class ConditionalExpr extends ASTExpr {
   StringTemplate subtemplate = null;
   List elseIfSubtemplates = null;
   StringTemplate elseSubtemplate = null;

   public ConditionalExpr(StringTemplate enclosingTemplate, AST tree) {
      super(enclosingTemplate, tree, (Map)null);
   }

   public void setSubtemplate(StringTemplate subtemplate) {
      this.subtemplate = subtemplate;
   }

   public void addElseIfSubtemplate(final ASTExpr conditionalTree, final StringTemplate subtemplate) {
      if (this.elseIfSubtemplates == null) {
         this.elseIfSubtemplates = new ArrayList();
      }

      ElseIfClauseData d = new ElseIfClauseData() {
         {
            this.expr = conditionalTree;
            this.st = subtemplate;
         }
      };
      this.elseIfSubtemplates.add(d);
   }

   public StringTemplate getSubtemplate() {
      return this.subtemplate;
   }

   public StringTemplate getElseSubtemplate() {
      return this.elseSubtemplate;
   }

   public void setElseSubtemplate(StringTemplate elseSubtemplate) {
      this.elseSubtemplate = elseSubtemplate;
   }

   public int write(StringTemplate self, StringTemplateWriter out) throws IOException {
      if (this.exprTree != null && self != null && out != null) {
         ActionEvaluator eval = new ActionEvaluator(self, this, out);
         int n = 0;

         try {
            boolean testedTrue = false;
            AST cond = this.exprTree.getFirstChild();
            boolean includeSubtemplate = eval.ifCondition(cond);
            if (includeSubtemplate) {
               n = this.writeSubTemplate(self, out, this.subtemplate);
               testedTrue = true;
            } else if (this.elseIfSubtemplates != null && this.elseIfSubtemplates.size() > 0) {
               for(int i = 0; i < this.elseIfSubtemplates.size(); ++i) {
                  ElseIfClauseData elseIfClause = (ElseIfClauseData)this.elseIfSubtemplates.get(i);
                  includeSubtemplate = eval.ifCondition(elseIfClause.expr.exprTree);
                  if (includeSubtemplate) {
                     this.writeSubTemplate(self, out, elseIfClause.st);
                     testedTrue = true;
                     break;
                  }
               }
            }

            if (!testedTrue && this.elseSubtemplate != null) {
               StringTemplate s = this.elseSubtemplate.getInstanceOf();
               s.setEnclosingInstance(self);
               s.setGroup(self.getGroup());
               s.setNativeGroup(self.getNativeGroup());
               n = s.write(out);
            }

            if (!testedTrue && this.elseSubtemplate == null) {
               n = -1;
            }
         } catch (RecognitionException var10) {
            self.error("can't evaluate tree: " + this.exprTree.toStringList(), var10);
         }

         return n;
      } else {
         return 0;
      }
   }

   protected int writeSubTemplate(StringTemplate self, StringTemplateWriter out, StringTemplate subtemplate) throws IOException {
      StringTemplate s = subtemplate.getInstanceOf();
      s.setEnclosingInstance(self);
      s.setGroup(self.getGroup());
      s.setNativeGroup(self.getNativeGroup());
      return s.write(out);
   }

   protected static class ElseIfClauseData {
      ASTExpr expr;
      StringTemplate st;
   }
}
