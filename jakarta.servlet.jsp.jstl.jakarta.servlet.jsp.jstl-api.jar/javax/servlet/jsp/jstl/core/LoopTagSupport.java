package javax.servlet.jsp.jstl.core;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.el.ELException;
import javax.el.ValueExpression;
import javax.el.VariableMapper;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.IterationTag;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

public abstract class LoopTagSupport extends TagSupport implements LoopTag, IterationTag, TryCatchFinally {
   protected int begin;
   protected int end;
   protected int step;
   protected boolean beginSpecified;
   protected boolean endSpecified;
   protected boolean stepSpecified;
   protected String itemId;
   protected String statusId;
   protected ValueExpression deferredExpression;
   private ValueExpression oldMappedValue;
   private LoopTagStatus status;
   private Object item;
   private int index;
   private int count;
   private boolean last;
   private IteratedExpression iteratedExpression;

   public LoopTagSupport() {
      this.init();
   }

   protected abstract Object next() throws JspTagException;

   protected abstract boolean hasNext() throws JspTagException;

   protected abstract void prepare() throws JspTagException;

   public void release() {
      super.release();
      this.init();
   }

   public int doStartTag() throws JspException {
      if (this.end != -1 && this.begin > this.end) {
         return 0;
      } else {
         this.index = 0;
         this.count = 1;
         this.last = false;
         this.iteratedExpression = null;
         this.deferredExpression = null;
         this.prepare();
         this.discardIgnoreSubset(this.begin);
         if (this.hasNext()) {
            this.item = this.next();
            this.discard(this.step - 1);
            this.exposeVariables(true);
            this.calibrateLast();
            return 1;
         } else {
            return 0;
         }
      }
   }

   public int doAfterBody() throws JspException {
      this.index += this.step - 1;
      ++this.count;
      if (this.hasNext() && !this.atEnd()) {
         ++this.index;
         this.item = this.next();
         this.discard(this.step - 1);
         this.exposeVariables(false);
         this.calibrateLast();
         return 2;
      } else {
         return 0;
      }
   }

   public void doFinally() {
      this.unExposeVariables();
   }

   public void doCatch(Throwable t) throws Throwable {
      throw t;
   }

   public Object getCurrent() {
      return this.item;
   }

   public LoopTagStatus getLoopStatus() {
      if (this.status == null) {
         class Status implements LoopTagStatus {
            public Object getCurrent() {
               return LoopTagSupport.this.getCurrent();
            }

            public int getIndex() {
               return LoopTagSupport.this.index + LoopTagSupport.this.begin;
            }

            public int getCount() {
               return LoopTagSupport.this.count;
            }

            public boolean isFirst() {
               return LoopTagSupport.this.index == 0;
            }

            public boolean isLast() {
               return LoopTagSupport.this.last;
            }

            public Integer getBegin() {
               return LoopTagSupport.this.beginSpecified ? LoopTagSupport.this.begin : null;
            }

            public Integer getEnd() {
               return LoopTagSupport.this.endSpecified ? LoopTagSupport.this.end : null;
            }

            public Integer getStep() {
               return LoopTagSupport.this.stepSpecified ? LoopTagSupport.this.step : null;
            }
         }

         this.status = new Status();
      }

      return this.status;
   }

   protected String getDelims() {
      return ",";
   }

   public void setVar(String id) {
      this.itemId = id;
   }

   public void setVarStatus(String statusId) {
      this.statusId = statusId;
   }

   protected void validateBegin() throws JspTagException {
      if (this.begin < 0) {
         throw new JspTagException("'begin' < 0");
      }
   }

   protected void validateEnd() throws JspTagException {
      if (this.end < 0) {
         throw new JspTagException("'end' < 0");
      }
   }

   protected void validateStep() throws JspTagException {
      if (this.step < 1) {
         throw new JspTagException("'step' <= 0");
      }
   }

   private void init() {
      this.index = 0;
      this.count = 1;
      this.status = null;
      this.item = null;
      this.last = false;
      this.beginSpecified = false;
      this.endSpecified = false;
      this.stepSpecified = false;
      this.begin = 0;
      this.end = -1;
      this.step = 1;
      this.itemId = null;
      this.statusId = null;
   }

   private void calibrateLast() throws JspTagException {
      this.last = !this.hasNext() || this.atEnd() || this.end != -1 && this.begin + this.index + this.step > this.end;
   }

   private void exposeVariables(boolean firstTime) throws JspTagException {
      if (this.itemId != null) {
         if (this.getCurrent() == null) {
            this.pageContext.removeAttribute(this.itemId, 1);
         } else if (this.deferredExpression != null) {
            VariableMapper vm = this.pageContext.getELContext().getVariableMapper();
            if (vm != null) {
               ValueExpression ve = this.getVarExpression(this.deferredExpression);
               ValueExpression tmpValue = vm.setVariable(this.itemId, ve);
               if (firstTime) {
                  this.oldMappedValue = tmpValue;
               }
            }
         } else {
            this.pageContext.setAttribute(this.itemId, this.getCurrent());
         }
      }

      if (this.statusId != null) {
         if (this.getLoopStatus() == null) {
            this.pageContext.removeAttribute(this.statusId, 1);
         } else {
            this.pageContext.setAttribute(this.statusId, this.getLoopStatus());
         }
      }

   }

   private void unExposeVariables() {
      if (this.itemId != null) {
         this.pageContext.removeAttribute(this.itemId, 1);
         VariableMapper vm = this.pageContext.getELContext().getVariableMapper();
         if (vm != null) {
            vm.setVariable(this.itemId, this.oldMappedValue);
         }
      }

      if (this.statusId != null) {
         this.pageContext.removeAttribute(this.statusId, 1);
      }

   }

   private void discard(int n) throws JspTagException {
      int oldIndex = this.index;

      while(n-- > 0 && !this.atEnd() && this.hasNext()) {
         ++this.index;
         this.next();
      }

      this.index = oldIndex;
   }

   private void discardIgnoreSubset(int n) throws JspTagException {
      while(n-- > 0 && this.hasNext()) {
         this.next();
      }

   }

   private boolean atEnd() {
      return this.end != -1 && this.begin + this.index >= this.end;
   }

   private ValueExpression getVarExpression(ValueExpression expr) {
      Object o = expr.getValue(this.pageContext.getELContext());
      if (o == null) {
         return null;
      } else if (!o.getClass().isArray() && !(o instanceof List)) {
         if (!(o instanceof Collection) && !(o instanceof Iterator) && !(o instanceof Enumeration) && !(o instanceof Map) && !(o instanceof String)) {
            throw new ELException("Don't know how to iterate over supplied items in forEach");
         } else {
            if (this.iteratedExpression == null) {
               this.iteratedExpression = new IteratedExpression(this.deferredExpression, this.getDelims());
            }

            return new IteratedValueExpression(this.iteratedExpression, this.index + this.begin);
         }
      } else {
         return new IndexedValueExpression(this.deferredExpression, this.index + this.begin);
      }
   }
}
