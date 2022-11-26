package weblogic.management.runtime;

import javax.xml.ws.WebServiceFeature;

public interface WseePortRuntimeMBean extends WseeBasePortRuntimeMBean {
   WseeOperationRuntimeMBean[] getOperations();

   String getPolicySubjectResourcePattern();

   String getPolicySubjectName();

   String getPolicySubjectType();

   String getPolicyAttachmentSupport();

   String getPolicySubjectAbsolutePortableExpression();

   void updateEndpoint(WebServiceFeature[] var1);
}
