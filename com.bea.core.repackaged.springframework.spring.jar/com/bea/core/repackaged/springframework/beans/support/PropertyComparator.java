package com.bea.core.repackaged.springframework.beans.support;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.BeanWrapperImpl;
import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class PropertyComparator implements Comparator {
   protected final Log logger = LogFactory.getLog(this.getClass());
   private final SortDefinition sortDefinition;
   private final BeanWrapperImpl beanWrapper = new BeanWrapperImpl(false);

   public PropertyComparator(SortDefinition sortDefinition) {
      this.sortDefinition = sortDefinition;
   }

   public PropertyComparator(String property, boolean ignoreCase, boolean ascending) {
      this.sortDefinition = new MutableSortDefinition(property, ignoreCase, ascending);
   }

   public final SortDefinition getSortDefinition() {
      return this.sortDefinition;
   }

   public int compare(Object o1, Object o2) {
      Object v1 = this.getPropertyValue(o1);
      Object v2 = this.getPropertyValue(o2);
      if (this.sortDefinition.isIgnoreCase() && v1 instanceof String && v2 instanceof String) {
         v1 = ((String)v1).toLowerCase();
         v2 = ((String)v2).toLowerCase();
      }

      int result;
      try {
         if (v1 != null) {
            result = v2 != null ? ((Comparable)v1).compareTo(v2) : -1;
         } else {
            result = v2 != null ? 1 : 0;
         }
      } catch (RuntimeException var7) {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Could not sort objects [" + o1 + "] and [" + o2 + "]", var7);
         }

         return 0;
      }

      return this.sortDefinition.isAscending() ? result : -result;
   }

   @Nullable
   private Object getPropertyValue(Object obj) {
      try {
         this.beanWrapper.setWrappedInstance(obj);
         return this.beanWrapper.getPropertyValue(this.sortDefinition.getProperty());
      } catch (BeansException var3) {
         this.logger.debug("PropertyComparator could not access property - treating as null for sorting", var3);
         return null;
      }
   }

   public static void sort(List source, SortDefinition sortDefinition) throws BeansException {
      if (StringUtils.hasText(sortDefinition.getProperty())) {
         source.sort(new PropertyComparator(sortDefinition));
      }

   }

   public static void sort(Object[] source, SortDefinition sortDefinition) throws BeansException {
      if (StringUtils.hasText(sortDefinition.getProperty())) {
         Arrays.sort(source, new PropertyComparator(sortDefinition));
      }

   }
}
