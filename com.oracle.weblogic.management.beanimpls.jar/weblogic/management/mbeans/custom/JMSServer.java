package weblogic.management.mbeans.custom;

import java.util.HashSet;
import java.util.Set;
import javax.management.InvalidAttributeValueException;
import weblogic.management.DistributedManagementException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSDestinationMBean;
import weblogic.management.configuration.JMSLegalHelper;
import weblogic.management.configuration.JMSQueueMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.JMSSessionPoolMBean;
import weblogic.management.configuration.JMSTemplateMBean;
import weblogic.management.configuration.JMSTopicMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;
import weblogic.management.provider.internal.AttributeAggregator;
import weblogic.utils.ArrayUtils;

public class JMSServer extends ConfigurationMBeanCustomizer {
   private static final String TT_PROP = "TemporaryTemplate";
   private static final String TR_PROP = "TemporaryTemplateResource";
   private static final String TN_PROP = "TemporaryTemplateName";
   private transient DomainMBean delegate;

   public JMSServer(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public void useDelegates(DomainMBean delegate) {
      this.delegate = delegate;
   }

   public Set getServerNames() {
      TargetMBean[] targets = ((JMSServerMBean)this.getMbean()).getTargets();
      Set serverNames = new HashSet();

      for(int j = 0; j < targets.length; ++j) {
         serverNames.addAll(targets[j].getServerNames());
      }

      return serverNames;
   }

   public JMSQueueMBean createJMSQueue(String name, JMSQueueMBean toClone) {
      JMSQueueMBean newBean = (JMSQueueMBean)this.getMbean().createChildCopyIncludingObsolete("JMSQueue", toClone);
      return newBean;
   }

   public JMSTopicMBean createJMSTopic(String name, JMSTopicMBean toClone) {
      JMSTopicMBean newBean = (JMSTopicMBean)this.getMbean().createChildCopyIncludingObsolete("JMSTopic", toClone);
      return newBean;
   }

   public JMSSessionPoolMBean createJMSSessionPool(String name, JMSSessionPoolMBean toClone) {
      JMSSessionPoolMBean newBean = (JMSSessionPoolMBean)this.getMbean().createChildCopyIncludingObsolete("JMSSessionPool", toClone);
      return newBean;
   }

   public void setSessionPools(JMSSessionPoolMBean[] pools) throws InvalidAttributeValueException {
      ArrayUtils.computeDiff(this.getSessionPools(), pools, new ArrayUtils.DiffHandler() {
         public void addObject(Object toAdd) {
            try {
               JMSServer.this.addSessionPool((JMSSessionPoolMBean)toAdd);
            } catch (InvalidAttributeValueException var3) {
               throw new RuntimeException(var3);
            } catch (DistributedManagementException var4) {
               throw new RuntimeException(var4);
            }
         }

         public void removeObject(Object toRemove) {
            try {
               JMSServer.this.removeSessionPool((JMSSessionPoolMBean)toRemove);
            } catch (InvalidAttributeValueException var3) {
               throw new RuntimeException(var3);
            } catch (DistributedManagementException var4) {
               throw new RuntimeException(var4);
            }
         }
      });
   }

   public boolean addSessionPool(JMSSessionPoolMBean sessionPool) throws InvalidAttributeValueException, DistributedManagementException {
      JMSServerMBean bean = (JMSServerMBean)this.getMbean();
      if (bean.lookupJMSSessionPool(sessionPool.getName()) != null) {
         return true;
      } else {
         this.getMbean().createChildCopyIncludingObsolete("JMSSessionPool", sessionPool);
         DomainMBean domain = (DomainMBean)bean.getParentBean();
         domain.destroyJMSSessionPool(sessionPool);
         return true;
      }
   }

   public boolean removeSessionPool(JMSSessionPoolMBean sessionPool) throws InvalidAttributeValueException, DistributedManagementException {
      JMSServerMBean bean = (JMSServerMBean)this.getMbean();
      DomainMBean domain = (DomainMBean)bean.getParentBean();
      if (bean.lookupJMSSessionPool(sessionPool.getName()) == null) {
         return true;
      } else {
         domain.createJMSSessionPool(sessionPool.getName(), sessionPool);
         bean.destroyJMSSessionPool(sessionPool);
         return true;
      }
   }

   public JMSSessionPoolMBean[] getSessionPools() {
      JMSServerMBean bean = (JMSServerMBean)this.getMbean();
      return bean.getJMSSessionPools();
   }

   public JMSTemplateMBean getTemporaryTemplate() {
      if (this.delegate == null) {
         Object retVal = this.getValue("TemporaryTemplate");
         return retVal != null && retVal instanceof JMSTemplateMBean ? (JMSTemplateMBean)retVal : null;
      } else {
         JMSServerMBean jmsServer = (JMSServerMBean)this.getMbean();
         String templateName = jmsServer.getTemporaryTemplateName();
         String resourceName = jmsServer.getTemporaryTemplateResource();
         if (resourceName != null && resourceName.equals("interop-jms")) {
            JMSTemplateMBean[] templates = this.delegate.getJMSTemplates();
            if (templates != null) {
               for(int lcv = 0; lcv < templates.length; ++lcv) {
                  JMSTemplateMBean template = templates[lcv];
                  if (templateName.equals(template.getName())) {
                     return template;
                  }
               }
            }

            return null;
         } else {
            return null;
         }
      }
   }

   public void setTemporaryTemplate(JMSTemplateMBean temporaryTemplate) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("TemporaryTemplate", temporaryTemplate);
      } else {
         JMSServerMBean jmsServer = (JMSServerMBean)this.getMbean();
         if (temporaryTemplate == null) {
            jmsServer.unSet("TemporaryTemplateResource");
            jmsServer.unSet("TemporaryTemplateName");
         } else {
            jmsServer.setTemporaryTemplateResource("interop-jms");
            jmsServer.setTemporaryTemplateName(temporaryTemplate.getName());
         }
      }
   }

   public JMSDestinationMBean[] getDestinations() {
      return (JMSDestinationMBean[])((JMSDestinationMBean[])JMSServer.DESTINATIONAGGREGATOR.instance.getAll(this.getMbean()));
   }

   public JMSDestinationMBean lookupDestination(String name) {
      return (JMSDestinationMBean)JMSServer.DESTINATIONAGGREGATOR.instance.lookup(this.getMbean(), name);
   }

   public boolean addDestination(JMSDestinationMBean destination) {
      JMSServerMBean bean = (JMSServerMBean)this.getMbean();
      DomainMBean domain = (DomainMBean)bean.getParentBean();
      if (destination instanceof JMSQueueMBean) {
         bean.createChildCopyIncludingObsolete("JMSQueue", destination);
         domain.destroyJMSQueue((JMSQueueMBean)destination);
      } else {
         bean.createChildCopyIncludingObsolete("JMSTopic", destination);
         domain.destroyJMSTopic((JMSTopicMBean)destination);
      }

      return true;
   }

   public boolean removeDestination(JMSDestinationMBean destination) {
      JMSServerMBean bean = (JMSServerMBean)this.getMbean();
      DomainMBean domain = (DomainMBean)bean.getParentBean();
      if (destination instanceof JMSQueueMBean) {
         if (bean.lookupJMSQueue(destination.getName()) == null) {
            return true;
         }

         domain.createChildCopyIncludingObsolete("JMSQueue", destination);
         bean.destroyJMSQueue((JMSQueueMBean)destination);
      } else {
         if (bean.lookupJMSTopic(destination.getName()) == null) {
            return true;
         }

         domain.createChildCopyIncludingObsolete("JMSTopic", destination);
         bean.destroyJMSTopic((JMSTopicMBean)destination);
      }

      return true;
   }

   public void setDestinations(JMSDestinationMBean[] destinations) throws InvalidAttributeValueException {
      ArrayUtils.computeDiff(this.getDestinations(), destinations, new ArrayUtils.DiffHandler() {
         public void addObject(Object toAdd) {
            JMSServer.this.addDestination((JMSDestinationMBean)toAdd);
         }

         public void removeObject(Object toRemove) {
            JMSServer.this.removeDestination((JMSDestinationMBean)toRemove);
         }
      });
   }

   public void _preDestroy() {
      DomainMBean domainToTry;
      if (this.delegate == null) {
         domainToTry = JMSLegalHelper.getDomain(this.getMbean());
      } else {
         domainToTry = this.delegate;
      }

   }

   private static class DESTINATIONAGGREGATOR {
      static AttributeAggregator instance = new AttributeAggregator("weblogic.management.configuration.JMSServerMBean", JMSDestinationMBean.class);
   }
}
