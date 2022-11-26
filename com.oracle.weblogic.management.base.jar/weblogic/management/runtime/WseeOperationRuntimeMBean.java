package weblogic.management.runtime;

public interface WseeOperationRuntimeMBean extends WseeBaseOperationRuntimeMBean {
   String getPolicySubjectResourcePattern();

   String getPolicySubjectName();

   String getPolicySubjectType();

   String getPolicyAttachmentSupport();
}
