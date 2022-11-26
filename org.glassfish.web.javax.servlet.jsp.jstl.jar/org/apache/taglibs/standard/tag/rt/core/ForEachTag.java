package org.apache.taglibs.standard.tag.rt.core;

import java.util.ArrayList;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.LoopTag;
import javax.servlet.jsp.tagext.IterationTag;
import org.apache.taglibs.standard.tag.common.core.ForEachSupport;

public class ForEachTag extends ForEachSupport implements LoopTag, IterationTag {
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

   public void setItems(Object o) throws JspTagException {
      if (o == null) {
         this.rawItems = new ArrayList();
      } else {
         this.rawItems = o;
      }

   }
}
