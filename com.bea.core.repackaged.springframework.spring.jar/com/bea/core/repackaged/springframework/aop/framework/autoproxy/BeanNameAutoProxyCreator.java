package com.bea.core.repackaged.springframework.aop.framework.autoproxy;

import com.bea.core.repackaged.springframework.aop.TargetSource;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.PatternMatchUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BeanNameAutoProxyCreator extends AbstractAutoProxyCreator {
   @Nullable
   private List beanNames;

   public void setBeanNames(String... beanNames) {
      Assert.notEmpty((Object[])beanNames, (String)"'beanNames' must not be empty");
      this.beanNames = new ArrayList(beanNames.length);
      String[] var2 = beanNames;
      int var3 = beanNames.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String mappedName = var2[var4];
         this.beanNames.add(StringUtils.trimWhitespace(mappedName));
      }

   }

   @Nullable
   protected Object[] getAdvicesAndAdvisorsForBean(Class beanClass, String beanName, @Nullable TargetSource targetSource) {
      if (this.beanNames != null) {
         Iterator var4 = this.beanNames.iterator();

         while(true) {
            String mappedName;
            BeanFactory beanFactory;
            do {
               while(true) {
                  if (!var4.hasNext()) {
                     return DO_NOT_PROXY;
                  }

                  mappedName = (String)var4.next();
                  if (!FactoryBean.class.isAssignableFrom(beanClass)) {
                     break;
                  }

                  if (mappedName.startsWith("&")) {
                     mappedName = mappedName.substring("&".length());
                     break;
                  }
               }

               if (this.isMatch(beanName, mappedName)) {
                  return PROXY_WITHOUT_ADDITIONAL_INTERCEPTORS;
               }

               beanFactory = this.getBeanFactory();
            } while(beanFactory == null);

            String[] aliases = beanFactory.getAliases(beanName);
            String[] var8 = aliases;
            int var9 = aliases.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               String alias = var8[var10];
               if (this.isMatch(alias, mappedName)) {
                  return PROXY_WITHOUT_ADDITIONAL_INTERCEPTORS;
               }
            }
         }
      } else {
         return DO_NOT_PROXY;
      }
   }

   protected boolean isMatch(String beanName, String mappedName) {
      return PatternMatchUtils.simpleMatch(mappedName, beanName);
   }
}
