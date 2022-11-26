package com.bea.core.repackaged.springframework.beans.factory;

import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.Arrays;
import java.util.Collection;

public class NoUniqueBeanDefinitionException extends NoSuchBeanDefinitionException {
   private final int numberOfBeansFound;
   @Nullable
   private final Collection beanNamesFound;

   public NoUniqueBeanDefinitionException(Class type, int numberOfBeansFound, String message) {
      super(type, message);
      this.numberOfBeansFound = numberOfBeansFound;
      this.beanNamesFound = null;
   }

   public NoUniqueBeanDefinitionException(Class type, Collection beanNamesFound) {
      super(type, "expected single matching bean but found " + beanNamesFound.size() + ": " + StringUtils.collectionToCommaDelimitedString(beanNamesFound));
      this.numberOfBeansFound = beanNamesFound.size();
      this.beanNamesFound = beanNamesFound;
   }

   public NoUniqueBeanDefinitionException(Class type, String... beanNamesFound) {
      this((Class)type, (Collection)Arrays.asList(beanNamesFound));
   }

   public NoUniqueBeanDefinitionException(ResolvableType type, Collection beanNamesFound) {
      super(type, "expected single matching bean but found " + beanNamesFound.size() + ": " + StringUtils.collectionToCommaDelimitedString(beanNamesFound));
      this.numberOfBeansFound = beanNamesFound.size();
      this.beanNamesFound = beanNamesFound;
   }

   public NoUniqueBeanDefinitionException(ResolvableType type, String... beanNamesFound) {
      this((ResolvableType)type, (Collection)Arrays.asList(beanNamesFound));
   }

   public int getNumberOfBeansFound() {
      return this.numberOfBeansFound;
   }

   @Nullable
   public Collection getBeanNamesFound() {
      return this.beanNamesFound;
   }
}
