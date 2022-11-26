package com.bea.core.repackaged.springframework.ui.context.support;

import com.bea.core.repackaged.springframework.context.MessageSource;
import com.bea.core.repackaged.springframework.ui.context.Theme;
import com.bea.core.repackaged.springframework.util.Assert;

public class SimpleTheme implements Theme {
   private final String name;
   private final MessageSource messageSource;

   public SimpleTheme(String name, MessageSource messageSource) {
      Assert.notNull(name, (String)"Name must not be null");
      Assert.notNull(messageSource, (String)"MessageSource must not be null");
      this.name = name;
      this.messageSource = messageSource;
   }

   public final String getName() {
      return this.name;
   }

   public final MessageSource getMessageSource() {
      return this.messageSource;
   }
}
