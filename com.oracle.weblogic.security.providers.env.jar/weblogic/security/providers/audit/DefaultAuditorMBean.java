package weblogic.security.providers.audit;

import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.security.IdentityDomainAwareProviderMBean;
import weblogic.management.security.audit.AuditorMBean;
import weblogic.management.security.audit.ContextHandlerMBean;

public interface DefaultAuditorMBean extends StandardInterface, DescriptorBean, AuditorMBean, ContextHandlerMBean, IdentityDomainAwareProviderMBean {
   String getProviderClassName();

   String getDescription();

   String getVersion();

   String getSeverity();

   void setSeverity(String var1) throws InvalidAttributeValueException;

   boolean getInformationAuditSeverityEnabled();

   void setInformationAuditSeverityEnabled(boolean var1) throws InvalidAttributeValueException;

   boolean getWarningAuditSeverityEnabled();

   void setWarningAuditSeverityEnabled(boolean var1) throws InvalidAttributeValueException;

   boolean getErrorAuditSeverityEnabled();

   void setErrorAuditSeverityEnabled(boolean var1) throws InvalidAttributeValueException;

   boolean getSuccessAuditSeverityEnabled();

   void setSuccessAuditSeverityEnabled(boolean var1) throws InvalidAttributeValueException;

   boolean getFailureAuditSeverityEnabled();

   void setFailureAuditSeverityEnabled(boolean var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   String getOutputMedium();

   /** @deprecated */
   @Deprecated
   void setOutputMedium(String var1) throws InvalidAttributeValueException;

   int getRotationMinutes();

   void setRotationMinutes(int var1) throws InvalidAttributeValueException;

   String[] getSupportedContextHandlerEntries();

   String getBeginMarker();

   void setBeginMarker(String var1) throws InvalidAttributeValueException;

   String getEndMarker();

   void setEndMarker(String var1) throws InvalidAttributeValueException;

   String getFieldPrefix();

   void setFieldPrefix(String var1) throws InvalidAttributeValueException;

   String getFieldSuffix();

   void setFieldSuffix(String var1) throws InvalidAttributeValueException;

   String getName();
}
