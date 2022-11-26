package com.bea.core.repackaged.springframework.transaction.interceptor;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.PatternMatchUtils;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class NameMatchTransactionAttributeSource implements TransactionAttributeSource, Serializable {
   protected static final Log logger = LogFactory.getLog(NameMatchTransactionAttributeSource.class);
   private Map nameMap = new HashMap();

   public void setNameMap(Map nameMap) {
      nameMap.forEach(this::addTransactionalMethod);
   }

   public void setProperties(Properties transactionAttributes) {
      TransactionAttributeEditor tae = new TransactionAttributeEditor();
      Enumeration propNames = transactionAttributes.propertyNames();

      while(propNames.hasMoreElements()) {
         String methodName = (String)propNames.nextElement();
         String value = transactionAttributes.getProperty(methodName);
         tae.setAsText(value);
         TransactionAttribute attr = (TransactionAttribute)tae.getValue();
         this.addTransactionalMethod(methodName, attr);
      }

   }

   public void addTransactionalMethod(String methodName, TransactionAttribute attr) {
      if (logger.isDebugEnabled()) {
         logger.debug("Adding transactional method [" + methodName + "] with attribute [" + attr + "]");
      }

      this.nameMap.put(methodName, attr);
   }

   @Nullable
   public TransactionAttribute getTransactionAttribute(Method method, @Nullable Class targetClass) {
      if (!ClassUtils.isUserLevelMethod(method)) {
         return null;
      } else {
         String methodName = method.getName();
         TransactionAttribute attr = (TransactionAttribute)this.nameMap.get(methodName);
         if (attr == null) {
            String bestNameMatch = null;
            Iterator var6 = this.nameMap.keySet().iterator();

            while(true) {
               String mappedName;
               do {
                  do {
                     if (!var6.hasNext()) {
                        return attr;
                     }

                     mappedName = (String)var6.next();
                  } while(!this.isMatch(methodName, mappedName));
               } while(bestNameMatch != null && bestNameMatch.length() > mappedName.length());

               attr = (TransactionAttribute)this.nameMap.get(mappedName);
               bestNameMatch = mappedName;
            }
         } else {
            return attr;
         }
      }
   }

   protected boolean isMatch(String methodName, String mappedName) {
      return PatternMatchUtils.simpleMatch(mappedName, methodName);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof NameMatchTransactionAttributeSource)) {
         return false;
      } else {
         NameMatchTransactionAttributeSource otherTas = (NameMatchTransactionAttributeSource)other;
         return ObjectUtils.nullSafeEquals(this.nameMap, otherTas.nameMap);
      }
   }

   public int hashCode() {
      return NameMatchTransactionAttributeSource.class.hashCode();
   }

   public String toString() {
      return this.getClass().getName() + ": " + this.nameMap;
   }
}
