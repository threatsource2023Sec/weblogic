package com.oracle.weblogic.lifecycle.provisioning.core.handlers.buildxml;

import java.util.Objects;
import javax.el.BeanNameELResolver;
import javax.el.ELException;
import javax.el.ImportHandler;
import javax.el.StandardELContext;
import javax.el.ValueExpression;
import javax.el.VariableMapper;
import javax.inject.Inject;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.PropertyHelper;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.HK2RuntimeException;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.Rank;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;

@ContractsProvided({PropertyHelper.PropertyEvaluator.class})
@PerLookup
@Rank(0)
@Service
public final class HK2JavaxElPropertyEvaluator extends AbstractJavaxElPropertyEvaluator {
   private final ServiceLocator serviceLocator;

   @Inject
   public HK2JavaxElPropertyEvaluator(ServiceLocator serviceLocator) {
      Objects.requireNonNull(serviceLocator);
      this.serviceLocator = serviceLocator;
      ((StandardELContext)this.elContext).addELResolver(new BeanNameELResolver(new HK2BeanNameResolver(serviceLocator)));
      ValueExpression serviceLocatorExpression = this.expressionFactory.createValueExpression(serviceLocator, ServiceLocator.class);

      assert serviceLocatorExpression != null;

      VariableMapper variableMapper = this.elContext.getVariableMapper();

      assert variableMapper != null;

      variableMapper.setVariable("serviceLocator", serviceLocatorExpression);
      ImportHandler importHandler = this.elContext.getImportHandler();

      assert importHandler != null;

      importHandler.importPackage("org.glassfish.hk2.api");
      importHandler.importClass(ServiceLocatorUtilities.class.getName());
      importHandler.importPackage("org.glassfish.hk2.utilities");
      importHandler.importStatic(HK2JavaxElPropertyEvaluator.class.getName() + ".getService");
   }

   public final Object evaluate(String propertyName, PropertyHelper callingPropertyHelper) {
      Object returnValue = null;
      if (propertyName != null && propertyName.startsWith("hk2:") && propertyName.length() > "hk2:".length()) {
         String realPropertyName = "${" + propertyName.substring("hk2:".length()) + "}";

         try {
            ValueExpression valueExpression = this.expressionFactory.createValueExpression(this.elContext, realPropertyName, Object.class);

            assert valueExpression != null;

            returnValue = valueExpression.getValue(this.elContext);
         } catch (HK2RuntimeException | ELException var7) {
            if (callingPropertyHelper == null) {
               throw var7;
            }

            Project project = callingPropertyHelper.getProject();
            if (project == null) {
               throw var7;
            }

            project.log(project.getThreadTask(Thread.currentThread()), var7.getMessage(), var7, 1);
         }
      }

      return returnValue;
   }

   public static Object getService(ServiceLocator serviceLocator, String tokenizedDescription) {
      Object returnValue = null;
      if (serviceLocator != null && tokenizedDescription != null) {
         Filter filter = BuilderHelper.createTokenizedFilter(tokenizedDescription);

         assert filter != null;

         Descriptor descriptor = serviceLocator.getBestDescriptor(filter);
         if (descriptor != null) {
            returnValue = ServiceLocatorUtilities.getService(serviceLocator, serviceLocator.getBestDescriptor(BuilderHelper.createTokenizedFilter(tokenizedDescription)));
         }
      }

      return returnValue;
   }
}
