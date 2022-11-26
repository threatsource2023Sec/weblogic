package weblogic.elasticity.interceptor;

import com.oracle.core.interceptor.ConfigurableMethodInterceptor;
import com.oracle.core.interceptor.MethodInvocationContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.inject.Inject;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.api.HK2Invocation;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.elasticity.ScalingOperation;
import weblogic.elasticity.i18n.ElasticityLogger;
import weblogic.elasticity.i18n.ElasticityTextTextFormatter;
import weblogic.management.configuration.ScriptInterceptorMBean;
import weblogic.management.configuration.ScriptMBean;
import weblogic.management.workflow.FailurePlan;
import weblogic.management.workflow.command.CommandInterface;
import weblogic.management.workflow.command.CommandRevertInterface;
import weblogic.management.workflow.command.SharedState;
import weblogic.management.workflow.command.WorkflowContext;

@Service(
   name = "ScriptInterceptor"
)
@Interceptor
@ScalingOperation
@PerLookup
@ContractsProvided({MethodInterceptor.class, ConfigurableMethodInterceptor.class})
public class ScriptInterceptor implements MethodInterceptor, ConfigurableMethodInterceptor {
   @Inject
   ServiceLocator serviceLocator;
   @Inject
   MethodInvocationContext invCtx;
   private ScriptInterceptorMBean scriptInterceptorMBean;

   public void configure(Object config) {
      if (config instanceof ScriptInterceptorMBean) {
         this.scriptInterceptorMBean = (ScriptInterceptorMBean)config;
      }

   }

   public Object invoke(MethodInvocation inv) throws Throwable {
      if (this.scriptInterceptorMBean == null) {
         throw new IllegalStateException(ElasticityTextTextFormatter.getInstance().getMissingScriptInterceptorConfiguration());
      } else if (!this.isApplicable(inv)) {
         return inv.proceed();
      } else {
         HK2Invocation hk2Invocation = (HK2Invocation)inv;
         boolean isFirstScriptInterceptor = hk2Invocation.getUserData("firstScriptInterceptorName") == null;
         if (isFirstScriptInterceptor) {
            hk2Invocation.setUserData("firstScriptInterceptorName", this.scriptInterceptorMBean.getName());
            this.invCtx.getWorkflowBuilder().add(new TemporaryDirAndFileCreator());
         }

         ScriptMBean preProcessorScriptMBean = this.scriptInterceptorMBean.getPreProcessor();
         ScriptMBean postProcessorScriptMBean = this.scriptInterceptorMBean.getPostProcessor();
         if (preProcessorScriptMBean != null && preProcessorScriptMBean.getPathToScript() != null) {
            ScriptExecutorState preProcessorScriptExecutorState = ScriptAndCommandUtil.createScriptExecutorState(preProcessorScriptMBean, this.invCtx);
            preProcessorScriptExecutorState.setScriptMBeanName(this.scriptInterceptorMBean.getName());
            preProcessorScriptExecutorState.setIsPreProcessor(true);
            ScriptExecutorCommand preProcessorCommand = new ScriptExecutorCommand(preProcessorScriptExecutorState);
            FailurePlan fp = preProcessorScriptExecutorState.isIgnoreFailures() ? null : ScriptAndCommandUtil.createFailurePlan(preProcessorScriptExecutorState);
            logWorkflowAddition(preProcessorScriptExecutorState);
            this.invCtx.getWorkflowBuilder().add(preProcessorCommand, fp);
         }

         Object result = inv.proceed();
         if (postProcessorScriptMBean != null && postProcessorScriptMBean.getPathToScript() != null) {
            ScriptExecutorState postProcessorScriptExecutorState = ScriptAndCommandUtil.createScriptExecutorState(postProcessorScriptMBean, this.invCtx);
            postProcessorScriptExecutorState.setScriptMBeanName(this.scriptInterceptorMBean.getName());
            postProcessorScriptExecutorState.setIsPreProcessor(false);
            ScriptExecutorCommand postProcessorCommand = new ScriptExecutorCommand(postProcessorScriptExecutorState);
            FailurePlan fp = postProcessorScriptExecutorState.isIgnoreFailures() ? null : ScriptAndCommandUtil.createFailurePlan(postProcessorScriptExecutorState);
            logWorkflowAddition(postProcessorScriptExecutorState);
            this.invCtx.getWorkflowBuilder().add(postProcessorCommand, fp);
         }

         if (isFirstScriptInterceptor) {
            this.invCtx.getWorkflowBuilder().add(new TemporaryDirAndFileCleanupCommand());
         }

         return result;
      }
   }

   private static void logWorkflowAddition(ScriptExecutorState state) {
      if (state.isPreProcessor()) {
         if (state.isScaleUp()) {
            ElasticityLogger.reportScriptInterceptorAddingPreProcessorScriptForScaleUp(state.getPathToScript(), state.getScriptMBeanName(), state.getDynamicClusterName());
         } else {
            ElasticityLogger.reportScriptInterceptorAddingPreProcessorScriptForScaleDown(state.getPathToScript(), state.getScriptMBeanName(), state.getDynamicClusterName());
         }
      } else if (state.isScaleUp()) {
         ElasticityLogger.reportScriptInterceptorAddingPostProcessorScriptForScaleUp(state.getPathToScript(), state.getScriptMBeanName(), state.getDynamicClusterName());
      } else {
         ElasticityLogger.reportScriptInterceptorAddingPostProcessorScriptForScaleDown(state.getPathToScript(), state.getScriptMBeanName(), state.getDynamicClusterName());
      }

   }

   private boolean isApplicable(MethodInvocation inv) {
      String operationName = inv.getMethod().getName();
      if (operationName.equals("scaleUp") || operationName.equals("scaleDown")) {
         String[] configuredOperationNames = this.scriptInterceptorMBean.getInterceptedOperationNames();
         if (configuredOperationNames != null && configuredOperationNames.length > 0) {
            boolean matched = false;
            String[] var5 = configuredOperationNames;
            int var6 = configuredOperationNames.length;
            int var7 = 0;

            while(true) {
               if (var7 < var6) {
                  String configuredOperationName = var5[var7];
                  if (!configuredOperationName.equalsIgnoreCase(operationName)) {
                     ++var7;
                     continue;
                  }

                  matched = true;
               }

               if (!matched) {
                  return false;
               }
               break;
            }
         }

         String[] clusterNames = this.scriptInterceptorMBean.getApplicableClusterNames();
         if (clusterNames == null || clusterNames.length <= 0) {
            return true;
         }

         Object[] args = inv.getArguments();
         String clusterName = (String)args[0];
         String[] var14 = clusterNames;
         int var15 = clusterNames.length;

         for(int var9 = 0; var9 < var15; ++var9) {
            String clusterNamePattern = var14[var9];
            if (this.matches(clusterName, clusterNamePattern)) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean matches(String clusterName, String pattern) {
      StringBuffer buf = new StringBuffer();
      boolean lastStar = false;
      int patternLen = pattern.length();

      for(int i = 0; i < patternLen; ++i) {
         char ch = pattern.charAt(i);
         if (ch == '*') {
            if (!lastStar) {
               buf.append("(.*)");
            }

            lastStar = true;
         } else {
            if (i == 0) {
               buf.append("^");
            }

            buf.append(ch);
            if (i == patternLen - 1) {
               buf.append("$");
            }

            lastStar = false;
         }
      }

      try {
         return Pattern.matches(buf.toString(), clusterName);
      } catch (Exception var8) {
         return false;
      }
   }

   private void transferOutputPropertiesToSharedData(HK2Invocation inv, Properties props) {
      Iterator var3 = props.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry e = (Map.Entry)var3.next();
         inv.setUserData(e.getKey().toString(), e.getValue());
      }

   }

   public static final class TemporaryDirAndFileCleanupCommand extends AbstractScriptCleanupCommand {
      public boolean execute() throws Exception {
         this.doCleanup();
         return true;
      }

      public boolean revert() throws Exception {
         this.doCleanup();
         return true;
      }
   }

   public static final class TemporaryDirAndFileCreator extends AbstractScriptCleanupCommand {
      public boolean execute() throws Exception {
         this.createTemporaryDirAndFile();
         return true;
      }

      public boolean revert() throws Exception {
         this.doCleanup();
         return true;
      }
   }

   public abstract static class AbstractScriptCleanupCommand implements CommandInterface, CommandRevertInterface {
      protected static final Logger _logger = Logger.getLogger(AbstractScriptCleanupCommand.class.getName());
      @SharedState(
         name = "InterceptorSharedDataConstants_workflow_shared_data_map_key"
      )
      private transient Map sharedMap;
      private transient WorkflowContext context;

      public void initialize(WorkflowContext context) {
         this.context = context;
      }

      protected void createTemporaryDirAndFile() throws IOException {
         Path tempDirPath = ScriptAndCommandUtil.createTemporaryDirForScript(this.sharedMap, "script-temp-dir-" + this.context.getId());
         ScriptAndCommandUtil.createTemporaryFileForScript(this.sharedMap, tempDirPath);
      }

      protected void doCleanup() throws IOException {
         String tempFileName = ScriptAndCommandUtil.getTemporaryFileNameForScript(this.sharedMap);
         if (tempFileName != null) {
            this.deleteDirOrFile(new File(tempFileName));
         }

         String tempDirName = ScriptAndCommandUtil.getTemporaryDirNameForScript(this.sharedMap);
         if (tempDirName != null) {
            this.deleteDirOrFile(new File(tempDirName));
         }

      }

      private void deleteDirOrFile(File dirOrFile) {
         if (dirOrFile.exists()) {
            if (dirOrFile.isFile()) {
               dirOrFile.delete();
            } else if (dirOrFile.isDirectory()) {
               String[] var2 = dirOrFile.list();
               int var3 = var2.length;

               for(int var4 = 0; var4 < var3; ++var4) {
                  String f = var2[var4];
                  this.deleteDirOrFile(new File(dirOrFile, f));
               }

               dirOrFile.delete();
            }
         }

      }
   }
}
