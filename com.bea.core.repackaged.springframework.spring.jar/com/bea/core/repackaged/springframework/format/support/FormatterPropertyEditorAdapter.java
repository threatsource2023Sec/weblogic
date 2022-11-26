package com.bea.core.repackaged.springframework.format.support;

import com.bea.core.repackaged.springframework.context.i18n.LocaleContextHolder;
import com.bea.core.repackaged.springframework.format.Formatter;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.beans.PropertyEditorSupport;

public class FormatterPropertyEditorAdapter extends PropertyEditorSupport {
   private final Formatter formatter;

   public FormatterPropertyEditorAdapter(Formatter formatter) {
      Assert.notNull(formatter, (String)"Formatter must not be null");
      this.formatter = formatter;
   }

   public Class getFieldType() {
      return FormattingConversionService.getFieldType(this.formatter);
   }

   public void setAsText(String text) throws IllegalArgumentException {
      if (StringUtils.hasText(text)) {
         try {
            this.setValue(this.formatter.parse(text, LocaleContextHolder.getLocale()));
         } catch (IllegalArgumentException var3) {
            throw var3;
         } catch (Throwable var4) {
            throw new IllegalArgumentException("Parse attempt failed for value [" + text + "]", var4);
         }
      } else {
         this.setValue((Object)null);
      }

   }

   public String getAsText() {
      Object value = this.getValue();
      return value != null ? this.formatter.print(value, LocaleContextHolder.getLocale()) : "";
   }
}
