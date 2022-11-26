package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.springframework.beans.BeanMetadataElement;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryUtils;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;

public class BeanDefinitionHolder implements BeanMetadataElement {
   private final BeanDefinition beanDefinition;
   private final String beanName;
   @Nullable
   private final String[] aliases;

   public BeanDefinitionHolder(BeanDefinition beanDefinition, String beanName) {
      this(beanDefinition, beanName, (String[])null);
   }

   public BeanDefinitionHolder(BeanDefinition beanDefinition, String beanName, @Nullable String[] aliases) {
      Assert.notNull(beanDefinition, (String)"BeanDefinition must not be null");
      Assert.notNull(beanName, (String)"Bean name must not be null");
      this.beanDefinition = beanDefinition;
      this.beanName = beanName;
      this.aliases = aliases;
   }

   public BeanDefinitionHolder(BeanDefinitionHolder beanDefinitionHolder) {
      Assert.notNull(beanDefinitionHolder, (String)"BeanDefinitionHolder must not be null");
      this.beanDefinition = beanDefinitionHolder.getBeanDefinition();
      this.beanName = beanDefinitionHolder.getBeanName();
      this.aliases = beanDefinitionHolder.getAliases();
   }

   public BeanDefinition getBeanDefinition() {
      return this.beanDefinition;
   }

   public String getBeanName() {
      return this.beanName;
   }

   @Nullable
   public String[] getAliases() {
      return this.aliases;
   }

   @Nullable
   public Object getSource() {
      return this.beanDefinition.getSource();
   }

   public boolean matchesName(@Nullable String candidateName) {
      return candidateName != null && (candidateName.equals(this.beanName) || candidateName.equals(BeanFactoryUtils.transformedBeanName(this.beanName)) || ObjectUtils.containsElement(this.aliases, candidateName));
   }

   public String getShortDescription() {
      StringBuilder sb = new StringBuilder();
      sb.append("Bean definition with name '").append(this.beanName).append("'");
      if (this.aliases != null) {
         sb.append(" and aliases [").append(StringUtils.arrayToCommaDelimitedString(this.aliases)).append("]");
      }

      return sb.toString();
   }

   public String getLongDescription() {
      StringBuilder sb = new StringBuilder(this.getShortDescription());
      sb.append(": ").append(this.beanDefinition);
      return sb.toString();
   }

   public String toString() {
      return this.getLongDescription();
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof BeanDefinitionHolder)) {
         return false;
      } else {
         BeanDefinitionHolder otherHolder = (BeanDefinitionHolder)other;
         return this.beanDefinition.equals(otherHolder.beanDefinition) && this.beanName.equals(otherHolder.beanName) && ObjectUtils.nullSafeEquals(this.aliases, otherHolder.aliases);
      }
   }

   public int hashCode() {
      int hashCode = this.beanDefinition.hashCode();
      hashCode = 29 * hashCode + this.beanName.hashCode();
      hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode((Object[])this.aliases);
      return hashCode;
   }
}
