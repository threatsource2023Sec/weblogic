package org.apache.taglibs.standard.tag.rt.fmt;

import javax.servlet.jsp.JspTagException;
import org.apache.taglibs.standard.tag.common.fmt.FormatNumberSupport;

public class FormatNumberTag extends FormatNumberSupport {
   public void setValue(Object value) throws JspTagException {
      this.value = value;
      this.valueSpecified = true;
   }

   public void setType(String type) throws JspTagException {
      this.type = type;
   }

   public void setPattern(String pattern) throws JspTagException {
      this.pattern = pattern;
   }

   public void setCurrencyCode(String currencyCode) throws JspTagException {
      this.currencyCode = currencyCode;
   }

   public void setCurrencySymbol(String currencySymbol) throws JspTagException {
      this.currencySymbol = currencySymbol;
   }

   public void setGroupingUsed(boolean isGroupingUsed) throws JspTagException {
      this.isGroupingUsed = isGroupingUsed;
      this.groupingUsedSpecified = true;
   }

   public void setMaxIntegerDigits(int maxDigits) throws JspTagException {
      this.maxIntegerDigits = maxDigits;
      this.maxIntegerDigitsSpecified = true;
   }

   public void setMinIntegerDigits(int minDigits) throws JspTagException {
      this.minIntegerDigits = minDigits;
      this.minIntegerDigitsSpecified = true;
   }

   public void setMaxFractionDigits(int maxDigits) throws JspTagException {
      this.maxFractionDigits = maxDigits;
      this.maxFractionDigitsSpecified = true;
   }

   public void setMinFractionDigits(int minDigits) throws JspTagException {
      this.minFractionDigits = minDigits;
      this.minFractionDigitsSpecified = true;
   }
}
