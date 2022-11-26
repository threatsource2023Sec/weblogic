package weblogic.spring.monitoring.instrumentation;

import weblogic.diagnostics.instrumentation.InvalidPointcutException;
import weblogic.diagnostics.instrumentation.engine.InstrumentorEngine;
import weblogic.diagnostics.instrumentation.engine.MonitorSpecification;
import weblogic.diagnostics.instrumentation.engine.base.PointcutExpression;
import weblogic.diagnostics.instrumentation.engine.base.PointcutExpression.Factory;

public class SpringInstrumentorEngineCreatorImpl implements SpringInstrumentorEngineCreator {
   public InstrumentorEngine createSpringInstrumentorEngine() {
      return this.createSpringInstrumentorEngine(false);
   }

   public InstrumentorEngine createSpringInstrumentorEngine(boolean isHotswapAvailable) {
      InstrumentorEngine instEng = null;

      try {
         String[] includePatterns = new String[]{"org.springframework.context.support.AbstractApplicationContext", "org.springframework.context.support.ClassPathXmlApplicationContext", "org.springframework.context.support.FileSystemXmlApplicationContext", "org.springframework.context.support.GenericApplicationContext", "org.springframework.jca.context.ResourceAdapterApplicationContext", "org.springframework.web.context.support.XmlWebApplicationContext", "org.springframework.web.context.support.GenericWebApplicationContext", "org.springframework.web.context.support.StaticWebApplicationContext", "org.springframework.web.portlet.context.StaticPortletApplicationContext", "org.springframework.web.portlet.context.XmlPortletApplicationContext", "org.springframework.beans.factory.support.AbstractBeanFactory", "org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory", "org.springframework.beans.factory.support.DefaultListableBeanFactory", "org.springframework.beans.factory.xml.XmlBeanFactory", "org.springframework.transaction.PlatformTransactionManager", "org.springframework.transaction.support.AbstractPlatformTransactionManager"};
         String pointcut = "execution(% +org.springframework.beans.factory.config.ConfigurableListableBeanFactory % +org.springframework.context.support.AbstractApplicationContext obtainFreshBeanFactory())";
         PointcutExpression expr = Factory.parse(pointcut);
         MonitorSpecification mon1 = new MonitorSpecification("Spring_Around_Internal_Application_Context_Obtain_Fresh_Bean_Factory", 3, expr, true, true);
         pointcut = "execution(* % +org.springframework.context.support.AbstractApplicationContext refresh())";
         expr = Factory.parse(pointcut);
         MonitorSpecification mon2 = new MonitorSpecification("Spring_Around_Internal_Abstract_Application_Context_Refresh", 3, expr, true, false);
         pointcut = "execution(protected java.lang.Object % +org.springframework.beans.factory.support.AbstractBeanFactory createBean(java.lang.String, % org.springframework.beans.factory.support.RootBeanDefinition, java.lang.Object[]))";
         expr = Factory.parse(pointcut);
         MonitorSpecification mon3 = new MonitorSpecification("Spring_Around_Internal_Abstract_Bean_Factory_Create_Bean", 3, expr, true, false);
         pointcut = "execution(public java.lang.Object % +org.springframework.beans.factory.support.AbstractBeanFactory getBean(java.lang.String, java.lang.Class, java.lang.Object[]))";
         expr = Factory.parse(pointcut);
         MonitorSpecification mon4 = new MonitorSpecification("Spring_Around_Internal_Abstract_Bean_Factory_Get_Bean", 3, expr, true, false);
         pointcut = "execution(public void % +org.springframework.beans.factory.support.AbstractBeanFactory registerScope(% java.lang.String, % org.springframework.beans.factory.config.Scope))";
         expr = Factory.parse(pointcut);
         MonitorSpecification mon5 = new MonitorSpecification("Spring_Around_Internal_Abstract_Bean_Factory_Register_Scope", 3, expr, true, false);
         pointcut = "execution(public void % +org.springframework.transaction.support.AbstractPlatformTransactionManager commit(org.springframework.transaction.TransactionStatus))";
         expr = Factory.parse(pointcut);
         MonitorSpecification mon6 = new MonitorSpecification("Spring_Around_Internal_Abstract_Platform_Transaction_Manager_Commit", 3, expr, true, false);
         pointcut = "execution(* % +org.springframework.transaction.support.AbstractPlatformTransactionManager resume(java.lang.Object, *))";
         expr = Factory.parse(pointcut);
         MonitorSpecification mon7 = new MonitorSpecification("Spring_Around_Internal_Abstract_Platform_Transaction_Manager_Resume", 3, expr, true, false);
         pointcut = "execution(public void % +org.springframework.transaction.support.AbstractPlatformTransactionManager rollback(org.springframework.transaction.TransactionStatus))";
         expr = Factory.parse(pointcut);
         MonitorSpecification mon8 = new MonitorSpecification("Spring_Around_Internal_Abstract_Platform_Transaction_Manager_Rollback", 3, expr, true, false);
         pointcut = "execution(* % +org.springframework.transaction.support.AbstractPlatformTransactionManager suspend(java.lang.Object))";
         expr = Factory.parse(pointcut);
         MonitorSpecification mon9 = new MonitorSpecification("Spring_Around_Internal_Abstract_Platform_Transaction_Manager_Suspend", 3, expr, true, false);
         pointcut = "execution(* % +org.springframework.beans.factory.support.DefaultListableBeanFactory getBeanNamesForType(java.lang.Class, boolean, boolean))";
         expr = Factory.parse(pointcut);
         MonitorSpecification mon10 = new MonitorSpecification("Spring_Around_Internal_Default_Listable_Bean_Factory_Get_Bean_Names_For_Type", 3, expr, true, false);
         pointcut = "execution(* % +org.springframework.beans.factory.support.DefaultListableBeanFactory getBeansOfType(java.lang.Class, boolean, boolean))";
         expr = Factory.parse(pointcut);
         MonitorSpecification mon11 = new MonitorSpecification("Spring_Around_Internal_Default_Listable_Bean_Factory_Get_Beans_Of_Type", 3, expr, true, false);
         MonitorSpecification[] mSpecs = new MonitorSpecification[]{mon1, mon2, mon3, mon4, mon5, mon6, mon7, mon8, mon9, mon10, mon11};
         instEng = new InstrumentorEngine("SpringInstrument", mSpecs, isHotswapAvailable, "weblogic.diagnostics.instrumentation.rtsupport.SpringInstSupportImpl");
         instEng.setIncludePatterns(includePatterns);
      } catch (InvalidPointcutException var18) {
      }

      return instEng;
   }
}
