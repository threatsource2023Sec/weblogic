package weblogic.spring.monitoring;

import java.util.ArrayList;
import java.util.Iterator;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.SpringApplicationContextRuntimeMBean;
import weblogic.management.runtime.SpringBeanDefinitionRuntimeMBean;
import weblogic.management.runtime.SpringRuntimeMBean;
import weblogic.management.runtime.SpringTransactionManagerRuntimeMBean;
import weblogic.management.runtime.SpringTransactionTemplateRuntimeMBean;
import weblogic.management.runtime.SpringViewResolverRuntimeMBean;
import weblogic.management.runtime.SpringViewRuntimeMBean;

public class SpringRuntimeMBeanImpl extends RuntimeMBeanDelegate implements SpringRuntimeMBean {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugSpringStatistics");
   private String version;
   private SpringBeanDefinitionRuntimeMBean[] springBeanDefinitionRuntiemMBeans;
   private ArrayList springApplicationContextRuntimeMBeans;
   private ArrayList springTransactionManagerRuntimeMBeans;
   private ArrayList springTransactionTemplateRuntimeMBeans;
   private ArrayList springViewRuntimeMBeans;
   private ArrayList springViewResolverRuntimeMBeans;

   public SpringRuntimeMBeanImpl(String name, RuntimeMBean parent) throws ManagementException {
      super(name, parent);
   }

   public void setSpringVersion(String version) {
      this.version = version;
   }

   public String getSpringVersion() {
      return this.version;
   }

   public void setSpringBeanDefinitionRuntimeMBeans(SpringBeanDefinitionRuntimeMBean[] springBeanDefinitionRuntiemMBeans) {
      this.springBeanDefinitionRuntiemMBeans = springBeanDefinitionRuntiemMBeans;
   }

   public SpringBeanDefinitionRuntimeMBean[] getSpringBeanDefinitionRuntimeMBeans() {
      return this.springBeanDefinitionRuntiemMBeans;
   }

   public void unregister() throws ManagementException {
      super.unregister();
   }

   public SpringApplicationContextRuntimeMBean[] getSpringApplicationContextRuntimeMBeans() {
      return this.springApplicationContextRuntimeMBeans == null ? null : (SpringApplicationContextRuntimeMBean[])this.springApplicationContextRuntimeMBeans.toArray(new SpringApplicationContextRuntimeMBean[this.springApplicationContextRuntimeMBeans.size()]);
   }

   public SpringTransactionManagerRuntimeMBean[] getSpringTransactionManagerRuntimeMBeans() {
      return this.springTransactionManagerRuntimeMBeans == null ? null : (SpringTransactionManagerRuntimeMBean[])this.springTransactionManagerRuntimeMBeans.toArray(new SpringTransactionManagerRuntimeMBean[this.springTransactionManagerRuntimeMBeans.size()]);
   }

   public SpringTransactionTemplateRuntimeMBean[] getSpringTransactionTemplateRuntimeMBeans() {
      return this.springTransactionTemplateRuntimeMBeans == null ? null : (SpringTransactionTemplateRuntimeMBean[])this.springTransactionTemplateRuntimeMBeans.toArray(new SpringTransactionTemplateRuntimeMBean[this.springTransactionTemplateRuntimeMBeans.size()]);
   }

   public SpringViewResolverRuntimeMBean[] getSpringViewResolverRuntimeMBeans() {
      return this.springViewResolverRuntimeMBeans == null ? null : (SpringViewResolverRuntimeMBean[])this.springViewResolverRuntimeMBeans.toArray(new SpringViewResolverRuntimeMBean[this.springViewResolverRuntimeMBeans.size()]);
   }

   public SpringViewRuntimeMBean[] getSpringViewRuntimeMBeans() {
      return this.springViewRuntimeMBeans == null ? null : (SpringViewRuntimeMBean[])this.springViewRuntimeMBeans.toArray(new SpringViewRuntimeMBean[this.springViewRuntimeMBeans.size()]);
   }

   public void addSpringApplicationContextRuntimeMBean(SpringApplicationContextRuntimeMBean mbean) {
      if (this.springApplicationContextRuntimeMBeans == null) {
         this.springApplicationContextRuntimeMBeans = new ArrayList();
      }

      this.springApplicationContextRuntimeMBeans.add(mbean);
   }

   public void addSpringTransactionManagerRuntimeMBean(SpringTransactionManagerRuntimeMBean mbean) {
      if (this.springTransactionManagerRuntimeMBeans == null) {
         this.springTransactionManagerRuntimeMBeans = new ArrayList();
      }

      this.springTransactionManagerRuntimeMBeans.add(mbean);
   }

   public void addSpringTransactionTemplateRuntimeMBean(SpringTransactionTemplateRuntimeMBean mbean) {
      if (this.springTransactionTemplateRuntimeMBeans == null) {
         this.springTransactionTemplateRuntimeMBeans = new ArrayList();
      }

      this.springTransactionTemplateRuntimeMBeans.add(mbean);
   }

   public void addSpringViewResolverRuntimeMBean(SpringViewResolverRuntimeMBean mbean) {
      if (this.springViewResolverRuntimeMBeans == null) {
         this.springViewResolverRuntimeMBeans = new ArrayList();
      }

      this.springViewResolverRuntimeMBeans.add(mbean);
   }

   public void addSpringViewRuntimeMBean(SpringViewRuntimeMBean mbean) {
      if (this.springViewRuntimeMBeans == null) {
         this.springViewRuntimeMBeans = new ArrayList();
      }

      this.springViewRuntimeMBeans.add(mbean);
   }

   public void removeSpringApplicationContextRuntimeMBean(SpringApplicationContextRuntimeMBean mbean) {
      if (this.springApplicationContextRuntimeMBeans != null) {
         this.springApplicationContextRuntimeMBeans.remove(mbean);
      }

   }

   public void removeSpringTransactionManagerRuntimeMBean(SpringTransactionManagerRuntimeMBean mbean) {
      if (this.springTransactionManagerRuntimeMBeans != null) {
         this.springTransactionManagerRuntimeMBeans.remove(mbean);
      }

   }

   public void removeSpringTransactionTemplateRuntimeMBean(SpringTransactionTemplateRuntimeMBean mbean) {
      if (this.springTransactionTemplateRuntimeMBeans != null) {
         this.springTransactionTemplateRuntimeMBeans.remove(mbean);
      }

   }

   public void removeSpringViewResolverRuntimeMBean(SpringViewResolverRuntimeMBean mbean) {
      if (this.springViewResolverRuntimeMBeans != null) {
         this.springViewResolverRuntimeMBeans.remove(mbean);
      }

   }

   public void removeSpringViewRuntimeMBean(SpringViewRuntimeMBean mbean) {
      if (this.springViewRuntimeMBeans != null) {
         this.springViewRuntimeMBeans.remove(mbean);
      }

   }

   public void removeRegisteredApplicationContextRuntimeMBeans(Object applicationContext) {
      this.removeRegisteredMBeans(applicationContext, this.springApplicationContextRuntimeMBeans);
   }

   public void removeRegisteredTransactionTemplateRuntimeMBeans(Object applicationContext) {
      this.removeRegisteredMBeans(applicationContext, this.springTransactionTemplateRuntimeMBeans);
   }

   public void removeRegisteredViewRuntimeMBeans(Object applicationContext) {
      this.removeRegisteredMBeans(applicationContext, this.springViewRuntimeMBeans);
   }

   public void removeRegisteredViewResolverRuntimeMBeans(Object applicationContext) {
      this.removeRegisteredMBeans(applicationContext, this.springViewResolverRuntimeMBeans);
   }

   public void removeRegisteredTransactionManagerRuntimeMBeans(Object applicationContext) {
      this.removeRegisteredMBeans(applicationContext, this.springTransactionManagerRuntimeMBeans);
   }

   private void removeRegisteredMBeans(Object applicationContext, ArrayList list) {
      if (list != null && !list.isEmpty()) {
         Iterator iter = list.iterator();

         while(true) {
            while(iter.hasNext()) {
               SpringBaseRuntimeMBeanImpl mbean = (SpringBaseRuntimeMBeanImpl)iter.next();
               if (applicationContext != null && (mbean.getApplicationContext() == null || mbean.getApplicationContext() != applicationContext)) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("SpringRuntimeMBeanImpl skip unregister for: " + mbean.getName());
                  }
               } else if (mbean.isRegistered()) {
                  try {
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("SpringRuntimeMBeanImpl unregister: " + mbean.getName());
                     }

                     mbean.unregister();
                  } catch (ManagementException var6) {
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("removeRegisteredMBeans ignoring", var6);
                     }
                  }

                  iter.remove();
               }
            }

            return;
         }
      }
   }
}
