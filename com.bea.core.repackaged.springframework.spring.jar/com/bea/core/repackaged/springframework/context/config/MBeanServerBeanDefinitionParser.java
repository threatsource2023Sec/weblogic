package com.bea.core.repackaged.springframework.context.config;

import com.bea.core.repackaged.springframework.beans.factory.support.AbstractBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.RootBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import com.bea.core.repackaged.springframework.beans.factory.xml.ParserContext;
import com.bea.core.repackaged.springframework.jmx.support.MBeanServerFactoryBean;
import com.bea.core.repackaged.springframework.jmx.support.WebSphereMBeanServerFactoryBean;
import com.bea.core.repackaged.springframework.jndi.JndiObjectFactoryBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import org.w3c.dom.Element;

class MBeanServerBeanDefinitionParser extends AbstractBeanDefinitionParser {
   private static final String MBEAN_SERVER_BEAN_NAME = "mbeanServer";
   private static final String AGENT_ID_ATTRIBUTE = "agent-id";
   private static final boolean weblogicPresent;
   private static final boolean webspherePresent;

   protected String resolveId(Element element, AbstractBeanDefinition definition, ParserContext parserContext) {
      String id = element.getAttribute("id");
      return StringUtils.hasText(id) ? id : "mbeanServer";
   }

   protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
      String agentId = element.getAttribute("agent-id");
      if (StringUtils.hasText(agentId)) {
         RootBeanDefinition bd = new RootBeanDefinition(MBeanServerFactoryBean.class);
         bd.getPropertyValues().add("agentId", agentId);
         return bd;
      } else {
         AbstractBeanDefinition specialServer = findServerForSpecialEnvironment();
         if (specialServer != null) {
            return specialServer;
         } else {
            RootBeanDefinition bd = new RootBeanDefinition(MBeanServerFactoryBean.class);
            bd.getPropertyValues().add("locateExistingServerIfPossible", Boolean.TRUE);
            bd.setRole(2);
            bd.setSource(parserContext.extractSource(element));
            return bd;
         }
      }
   }

   @Nullable
   static AbstractBeanDefinition findServerForSpecialEnvironment() {
      if (weblogicPresent) {
         RootBeanDefinition bd = new RootBeanDefinition(JndiObjectFactoryBean.class);
         bd.getPropertyValues().add("jndiName", "java:comp/env/jmx/runtime");
         return bd;
      } else {
         return webspherePresent ? new RootBeanDefinition(WebSphereMBeanServerFactoryBean.class) : null;
      }
   }

   static {
      ClassLoader classLoader = MBeanServerBeanDefinitionParser.class.getClassLoader();
      weblogicPresent = ClassUtils.isPresent("weblogic.management.Helper", classLoader);
      webspherePresent = ClassUtils.isPresent("com.ibm.websphere.management.AdminServiceFactory", classLoader);
   }
}
