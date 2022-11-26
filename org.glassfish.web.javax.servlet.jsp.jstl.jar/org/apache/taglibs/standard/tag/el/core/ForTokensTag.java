package org.apache.taglibs.standard.tag.el.core;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.jstl.core.LoopTag;
import javax.servlet.jsp.tagext.IterationTag;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.apache.taglibs.standard.tag.common.core.ForTokensSupport;
import org.apache.taglibs.standard.tag.common.core.NullAttributeException;

public class ForTokensTag extends ForTokensSupport implements LoopTag, IterationTag {
   private String begin_;
   private String end_;
   private String step_;
   private String items_;
   private String delims_;

   public ForTokensTag() {
      this.init();
   }

   public int doStartTag() throws JspException {
      this.evaluateExpressions();
      return super.doStartTag();
   }

   public void release() {
      super.release();
      this.init();
   }

   public void setBegin(String begin_) {
      this.begin_ = begin_;
      this.beginSpecified = true;
   }

   public void setEnd(String end_) {
      this.end_ = end_;
      this.endSpecified = true;
   }

   public void setStep(String step_) {
      this.step_ = step_;
      this.stepSpecified = true;
   }

   public void setItems(String items_) {
      this.items_ = items_;
   }

   public void setDelims(String delims_) {
      this.delims_ = delims_;
   }

   private void init() {
      this.begin_ = null;
      this.end_ = null;
      this.step_ = null;
      this.items_ = null;
      this.delims_ = null;
   }

   private void evaluateExpressions() throws JspException {
      Object r;
      if (this.begin_ != null) {
         r = ExpressionEvaluatorManager.evaluate("begin", this.begin_, Integer.class, this, this.pageContext);
         if (r == null) {
            throw new NullAttributeException("forTokens", "begin");
         }

         this.begin = (Integer)r;
         this.validateBegin();
      }

      if (this.end_ != null) {
         r = ExpressionEvaluatorManager.evaluate("end", this.end_, Integer.class, this, this.pageContext);
         if (r == null) {
            throw new NullAttributeException("forTokens", "end");
         }

         this.end = (Integer)r;
         this.validateEnd();
      }

      if (this.step_ != null) {
         r = ExpressionEvaluatorManager.evaluate("step", this.step_, Integer.class, this, this.pageContext);
         if (r == null) {
            throw new NullAttributeException("forTokens", "step");
         }

         this.step = (Integer)r;
         this.validateStep();
      }

      if (this.items_ != null) {
         this.items = (String)ExpressionEvaluatorManager.evaluate("items", this.items_, String.class, this, this.pageContext);
         if (this.items == null) {
            this.items = "";
         }
      }

      if (this.delims_ != null) {
         this.delims = (String)ExpressionEvaluatorManager.evaluate("delims", this.delims_, String.class, this, this.pageContext);
         if (this.delims == null) {
            this.delims = "";
         }
      }

   }
}
