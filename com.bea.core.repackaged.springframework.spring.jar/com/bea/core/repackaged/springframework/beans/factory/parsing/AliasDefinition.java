package com.bea.core.repackaged.springframework.beans.factory.parsing;

import com.bea.core.repackaged.springframework.beans.BeanMetadataElement;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;

public class AliasDefinition implements BeanMetadataElement {
   private final String beanName;
   private final String alias;
   @Nullable
   private final Object source;

   public AliasDefinition(String beanName, String alias) {
      this(beanName, alias, (Object)null);
   }

   public AliasDefinition(String beanName, String alias, @Nullable Object source) {
      Assert.notNull(beanName, (String)"Bean name must not be null");
      Assert.notNull(alias, (String)"Alias must not be null");
      this.beanName = beanName;
      this.alias = alias;
      this.source = source;
   }

   public final String getBeanName() {
      return this.beanName;
   }

   public final String getAlias() {
      return this.alias;
   }

   @Nullable
   public final Object getSource() {
      return this.source;
   }
}
