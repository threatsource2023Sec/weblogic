package com.oracle.weblogic.lifecycle.provisioning.core.handlers.buildxml;

import javax.el.ELException;
import javax.el.ImportHandler;
import javax.el.ValueExpression;
import javax.el.VariableMapper;
import javax.inject.Inject;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.PropertyHelper;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.Rank;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;

@ContractsProvided({PropertyHelper.PropertyEvaluator.class})
@PerLookup
@Rank(4)
@Service
public final class AntJavaxElPropertyEvaluator extends AbstractJavaxElPropertyEvaluator {
   @Inject
   public AntJavaxElPropertyEvaluator() {
      ImportHandler importHandler = this.elContext.getImportHandler();

      assert importHandler != null;

      importHandler.importClass(Project.class.getName());
   }

   public final Object evaluate(String propertyName, PropertyHelper callingPropertyHelper) {
      Object returnValue = null;
      if (propertyName != null && callingPropertyHelper != null && (propertyName.equals("project") || propertyName.startsWith("project."))) {
         Project project = callingPropertyHelper.getProject();
         if (project != null) {
            ValueExpression projectExpression = this.expressionFactory.createValueExpression(project, Project.class);

            assert projectExpression != null;

            VariableMapper variableMapper = this.elContext.getVariableMapper();

            assert variableMapper != null;

            variableMapper.setVariable("project", projectExpression);
            String realPropertyName = "${" + propertyName + "}";

            try {
               ValueExpression valueExpression = this.expressionFactory.createValueExpression(this.elContext, realPropertyName, Object.class);

               assert valueExpression != null;

               returnValue = valueExpression.getValue(this.elContext);
            } catch (ELException var9) {
               project.log(project.getThreadTask(Thread.currentThread()), var9.getMessage(), var9, 1);
            }
         }
      }

      return returnValue;
   }
}
