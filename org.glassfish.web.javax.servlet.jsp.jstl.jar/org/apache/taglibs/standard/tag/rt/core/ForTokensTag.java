package org.apache.taglibs.standard.tag.rt.core;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.LoopTag;
import javax.servlet.jsp.tagext.IterationTag;
import org.apache.taglibs.standard.tag.common.core.ForTokensSupport;

public class ForTokensTag extends ForTokensSupport implements LoopTag, IterationTag {
   public void setBegin(int begin) throws JspTagException {
      this.beginSpecified = true;
      this.begin = begin;
      this.validateBegin();
   }

   public void setEnd(int end) throws JspTagException {
      this.endSpecified = true;
      this.end = end;
      this.validateEnd();
   }

   public void setStep(int step) throws JspTagException {
      this.stepSpecified = true;
      this.step = step;
      this.validateStep();
   }

   public void setItems(Object s) throws JspTagException {
      this.items = s;
      if (s == null) {
         this.items = "";
      }

   }

   public void setDelims(String s) throws JspTagException {
      this.delims = s;
      if (s == null) {
         this.delims = "";
      }

   }
}
