package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

/** @deprecated */
@Deprecated
public interface JMSDistributedDestinationMBean extends JMSVirtualDestinationMBean {
   /** @deprecated */
   @Deprecated
   JMSTemplateMBean createJMSTemplate(String var1);

   /** @deprecated */
   @Deprecated
   void destroyJMSTemplate(JMSTemplateMBean var1);

   /** @deprecated */
   @Deprecated
   JMSTemplateMBean getJMSTemplate();

   /** @deprecated */
   @Deprecated
   void setJMSTemplate(JMSTemplateMBean var1);

   JMSTemplateMBean getTemplate();

   void setTemplate(JMSTemplateMBean var1);

   String getLoadBalancingPolicy();

   void setLoadBalancingPolicy(String var1) throws InvalidAttributeValueException;
}
