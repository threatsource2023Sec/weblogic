package weblogic.management.runtime;

public interface WseePortConfigurationRuntimeMBean extends RuntimeMBean {
   String getPolicySubjectResourcePattern();

   String getPolicySubjectName();

   String getPolicySubjectType();

   String getPolicyAttachmentSupport();

   String getPolicySubjectAbsolutePortableExpression();

   WseeOperationConfigurationRuntimeMBean[] getOperations();
}
