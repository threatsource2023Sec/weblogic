package com.bea.core.repackaged.springframework.scripting.support;

import com.bea.core.repackaged.springframework.aop.target.dynamic.BeanFactoryRefreshableTargetSource;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.scripting.ScriptFactory;
import com.bea.core.repackaged.springframework.scripting.ScriptSource;
import com.bea.core.repackaged.springframework.util.Assert;

public class RefreshableScriptTargetSource extends BeanFactoryRefreshableTargetSource {
   private final ScriptFactory scriptFactory;
   private final ScriptSource scriptSource;
   private final boolean isFactoryBean;

   public RefreshableScriptTargetSource(BeanFactory beanFactory, String beanName, ScriptFactory scriptFactory, ScriptSource scriptSource, boolean isFactoryBean) {
      super(beanFactory, beanName);
      Assert.notNull(scriptFactory, (String)"ScriptFactory must not be null");
      Assert.notNull(scriptSource, (String)"ScriptSource must not be null");
      this.scriptFactory = scriptFactory;
      this.scriptSource = scriptSource;
      this.isFactoryBean = isFactoryBean;
   }

   protected boolean requiresRefresh() {
      return this.scriptFactory.requiresScriptedObjectRefresh(this.scriptSource);
   }

   protected Object obtainFreshBean(BeanFactory beanFactory, String beanName) {
      return super.obtainFreshBean(beanFactory, this.isFactoryBean ? "&" + beanName : beanName);
   }
}
