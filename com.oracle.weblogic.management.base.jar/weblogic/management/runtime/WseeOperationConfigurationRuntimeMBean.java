package weblogic.management.runtime;

public interface WseeOperationConfigurationRuntimeMBean extends RuntimeMBean {
   String getPolicySubjectResourcePattern();

   String getPolicySubjectName();

   String getPolicySubjectType();

   String getPolicyAttachmentSupport();
}
