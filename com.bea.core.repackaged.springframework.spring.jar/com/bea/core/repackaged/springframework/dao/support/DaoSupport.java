package com.bea.core.repackaged.springframework.dao.support;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanInitializationException;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;

public abstract class DaoSupport implements InitializingBean {
   protected final Log logger = LogFactory.getLog(this.getClass());

   public final void afterPropertiesSet() throws IllegalArgumentException, BeanInitializationException {
      this.checkDaoConfig();

      try {
         this.initDao();
      } catch (Exception var2) {
         throw new BeanInitializationException("Initialization of DAO failed", var2);
      }
   }

   protected abstract void checkDaoConfig() throws IllegalArgumentException;

   protected void initDao() throws Exception {
   }
}
