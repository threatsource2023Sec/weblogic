package weblogic.management.configuration;

public interface ScriptInterceptorMBean extends InterceptorMBean {
   String SCRIPT_INTERCEPTOR_TYPE = "ScriptInterceptor";

   String getInterceptorTypeName();

   String[] getApplicableClusterNames();

   void setApplicableClusterNames(String[] var1);

   PreProcessorScriptMBean getPreProcessor();

   PostProcessorScriptMBean getPostProcessor();
}
