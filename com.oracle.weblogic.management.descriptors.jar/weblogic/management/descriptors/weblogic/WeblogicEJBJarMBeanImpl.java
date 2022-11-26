package weblogic.management.descriptors.weblogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class WeblogicEJBJarMBeanImpl extends XMLElementMBeanDelegate implements WeblogicEJBJarMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_transactionIsolations = false;
   private List transactionIsolations;
   private boolean isSet_disableWarnings = false;
   private List disableWarnings;
   private boolean isSet_idempotentMethods = false;
   private IdempotentMethodsMBean idempotentMethods;
   private boolean isSet_description = false;
   private String description;
   private boolean isSet_securityRoleAssignments = false;
   private List securityRoleAssignments;
   private boolean isSet_encoding = false;
   private String encoding;
   private boolean isSet_enableBeanClassRedeploy = false;
   private boolean enableBeanClassRedeploy = false;
   private boolean isSet_version = false;
   private String version;
   private boolean isSet_runAsRoleAssignments = false;
   private List runAsRoleAssignments;
   private boolean isSet_weblogicEnterpriseBeans = false;
   private List weblogicEnterpriseBeans;
   private boolean isSet_securityPermission = false;
   private SecurityPermissionMBean securityPermission;

   public TransactionIsolationMBean[] getTransactionIsolations() {
      if (this.transactionIsolations == null) {
         return new TransactionIsolationMBean[0];
      } else {
         TransactionIsolationMBean[] result = new TransactionIsolationMBean[this.transactionIsolations.size()];
         result = (TransactionIsolationMBean[])((TransactionIsolationMBean[])this.transactionIsolations.toArray(result));
         return result;
      }
   }

   public void setTransactionIsolations(TransactionIsolationMBean[] value) {
      TransactionIsolationMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getTransactionIsolations();
      }

      this.isSet_transactionIsolations = true;
      if (this.transactionIsolations == null) {
         this.transactionIsolations = Collections.synchronizedList(new ArrayList());
      } else {
         this.transactionIsolations.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.transactionIsolations.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("TransactionIsolations", _oldVal, this.getTransactionIsolations());
      }

   }

   public void addTransactionIsolation(TransactionIsolationMBean value) {
      this.isSet_transactionIsolations = true;
      if (this.transactionIsolations == null) {
         this.transactionIsolations = Collections.synchronizedList(new ArrayList());
      }

      this.transactionIsolations.add(value);
   }

   public void removeTransactionIsolation(TransactionIsolationMBean value) {
      if (this.transactionIsolations != null) {
         this.transactionIsolations.remove(value);
      }
   }

   public String[] getDisableWarnings() {
      if (this.disableWarnings == null) {
         return new String[0];
      } else {
         String[] result = new String[this.disableWarnings.size()];
         result = (String[])((String[])this.disableWarnings.toArray(result));
         return result;
      }
   }

   public void setDisableWarnings(String[] value) {
      String[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getDisableWarnings();
      }

      this.isSet_disableWarnings = true;
      if (this.disableWarnings == null) {
         this.disableWarnings = Collections.synchronizedList(new ArrayList());
      } else {
         this.disableWarnings.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.disableWarnings.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("DisableWarnings", _oldVal, this.getDisableWarnings());
      }

   }

   public void addDisableWarning(String value) {
      this.isSet_disableWarnings = true;
      if (this.disableWarnings == null) {
         this.disableWarnings = Collections.synchronizedList(new ArrayList());
      }

      this.disableWarnings.add(value);
   }

   public IdempotentMethodsMBean getIdempotentMethods() {
      return this.idempotentMethods;
   }

   public void setIdempotentMethods(IdempotentMethodsMBean value) {
      IdempotentMethodsMBean old = this.idempotentMethods;
      this.idempotentMethods = value;
      this.isSet_idempotentMethods = value != null;
      this.checkChange("idempotentMethods", old, this.idempotentMethods);
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.description;
      this.description = value;
      this.isSet_description = value != null;
      this.checkChange("description", old, this.description);
   }

   public SecurityRoleAssignmentMBean[] getSecurityRoleAssignments() {
      if (this.securityRoleAssignments == null) {
         return new SecurityRoleAssignmentMBean[0];
      } else {
         SecurityRoleAssignmentMBean[] result = new SecurityRoleAssignmentMBean[this.securityRoleAssignments.size()];
         result = (SecurityRoleAssignmentMBean[])((SecurityRoleAssignmentMBean[])this.securityRoleAssignments.toArray(result));
         return result;
      }
   }

   public void setSecurityRoleAssignments(SecurityRoleAssignmentMBean[] value) {
      SecurityRoleAssignmentMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getSecurityRoleAssignments();
      }

      this.isSet_securityRoleAssignments = true;
      if (this.securityRoleAssignments == null) {
         this.securityRoleAssignments = Collections.synchronizedList(new ArrayList());
      } else {
         this.securityRoleAssignments.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.securityRoleAssignments.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("SecurityRoleAssignments", _oldVal, this.getSecurityRoleAssignments());
      }

   }

   public void addSecurityRoleAssignment(SecurityRoleAssignmentMBean value) {
      this.isSet_securityRoleAssignments = true;
      if (this.securityRoleAssignments == null) {
         this.securityRoleAssignments = Collections.synchronizedList(new ArrayList());
      }

      this.securityRoleAssignments.add(value);
   }

   public void removeSecurityRoleAssignment(SecurityRoleAssignmentMBean value) {
      if (this.securityRoleAssignments != null) {
         this.securityRoleAssignments.remove(value);
      }
   }

   public String getEncoding() {
      return this.encoding;
   }

   public void setEncoding(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.encoding;
      this.encoding = value;
      this.isSet_encoding = value != null;
      this.checkChange("encoding", old, this.encoding);
   }

   public boolean getEnableBeanClassRedeploy() {
      return this.enableBeanClassRedeploy;
   }

   public void setEnableBeanClassRedeploy(boolean value) {
      boolean old = this.enableBeanClassRedeploy;
      this.enableBeanClassRedeploy = value;
      this.isSet_enableBeanClassRedeploy = true;
      this.checkChange("enableBeanClassRedeploy", old, this.enableBeanClassRedeploy);
   }

   public String getVersion() {
      return this.version;
   }

   public void setVersion(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.version;
      this.version = value;
      this.isSet_version = value != null;
      this.checkChange("version", old, this.version);
   }

   public RunAsRoleAssignmentMBean[] getRunAsRoleAssignments() {
      if (this.runAsRoleAssignments == null) {
         return new RunAsRoleAssignmentMBean[0];
      } else {
         RunAsRoleAssignmentMBean[] result = new RunAsRoleAssignmentMBean[this.runAsRoleAssignments.size()];
         result = (RunAsRoleAssignmentMBean[])((RunAsRoleAssignmentMBean[])this.runAsRoleAssignments.toArray(result));
         return result;
      }
   }

   public void setRunAsRoleAssignments(RunAsRoleAssignmentMBean[] value) {
      RunAsRoleAssignmentMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getRunAsRoleAssignments();
      }

      this.isSet_runAsRoleAssignments = true;
      if (this.runAsRoleAssignments == null) {
         this.runAsRoleAssignments = Collections.synchronizedList(new ArrayList());
      } else {
         this.runAsRoleAssignments.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.runAsRoleAssignments.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("RunAsRoleAssignments", _oldVal, this.getRunAsRoleAssignments());
      }

   }

   public void addRunAsRoleAssignment(RunAsRoleAssignmentMBean value) {
      this.isSet_runAsRoleAssignments = true;
      if (this.runAsRoleAssignments == null) {
         this.runAsRoleAssignments = Collections.synchronizedList(new ArrayList());
      }

      this.runAsRoleAssignments.add(value);
   }

   public void removeRunAsRoleAssignment(RunAsRoleAssignmentMBean value) {
      if (this.runAsRoleAssignments != null) {
         this.runAsRoleAssignments.remove(value);
      }
   }

   public WeblogicEnterpriseBeanMBean[] getWeblogicEnterpriseBeans() {
      if (this.weblogicEnterpriseBeans == null) {
         return new WeblogicEnterpriseBeanMBean[0];
      } else {
         WeblogicEnterpriseBeanMBean[] result = new WeblogicEnterpriseBeanMBean[this.weblogicEnterpriseBeans.size()];
         result = (WeblogicEnterpriseBeanMBean[])((WeblogicEnterpriseBeanMBean[])this.weblogicEnterpriseBeans.toArray(result));
         return result;
      }
   }

   public void setWeblogicEnterpriseBeans(WeblogicEnterpriseBeanMBean[] value) {
      WeblogicEnterpriseBeanMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getWeblogicEnterpriseBeans();
      }

      this.isSet_weblogicEnterpriseBeans = true;
      if (this.weblogicEnterpriseBeans == null) {
         this.weblogicEnterpriseBeans = Collections.synchronizedList(new ArrayList());
      } else {
         this.weblogicEnterpriseBeans.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.weblogicEnterpriseBeans.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("WeblogicEnterpriseBeans", _oldVal, this.getWeblogicEnterpriseBeans());
      }

   }

   public void addWeblogicEnterpriseBean(WeblogicEnterpriseBeanMBean value) {
      this.isSet_weblogicEnterpriseBeans = true;
      if (this.weblogicEnterpriseBeans == null) {
         this.weblogicEnterpriseBeans = Collections.synchronizedList(new ArrayList());
      }

      this.weblogicEnterpriseBeans.add(value);
   }

   public void removeWeblogicEnterpriseBean(WeblogicEnterpriseBeanMBean value) {
      if (this.weblogicEnterpriseBeans != null) {
         this.weblogicEnterpriseBeans.remove(value);
      }
   }

   public SecurityPermissionMBean getSecurityPermission() {
      return this.securityPermission;
   }

   public void setSecurityPermission(SecurityPermissionMBean value) {
      SecurityPermissionMBean old = this.securityPermission;
      this.securityPermission = value;
      this.isSet_securityPermission = value != null;
      this.checkChange("securityPermission", old, this.securityPermission);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<weblogic-ejb-jar");
      result.append(">\n");
      if (null != this.getDescription()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<description>").append("<![CDATA[" + this.getDescription() + "]]>").append("</description>\n");
      }

      int i;
      if (null != this.getWeblogicEnterpriseBeans()) {
         for(i = 0; i < this.getWeblogicEnterpriseBeans().length; ++i) {
            result.append(this.getWeblogicEnterpriseBeans()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getSecurityRoleAssignments()) {
         for(i = 0; i < this.getSecurityRoleAssignments().length; ++i) {
            result.append(this.getSecurityRoleAssignments()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getRunAsRoleAssignments()) {
         for(i = 0; i < this.getRunAsRoleAssignments().length; ++i) {
            result.append(this.getRunAsRoleAssignments()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getSecurityPermission()) {
         result.append(this.getSecurityPermission().toXML(indentLevel + 2)).append("\n");
      }

      if (null != this.getTransactionIsolations()) {
         for(i = 0; i < this.getTransactionIsolations().length; ++i) {
            result.append(this.getTransactionIsolations()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getIdempotentMethods()) {
         result.append(this.getIdempotentMethods().toXML(indentLevel + 2)).append("\n");
      }

      if (this.isSet_enableBeanClassRedeploy || this.getEnableBeanClassRedeploy()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<enable-bean-class-redeploy>").append(ToXML.capitalize(Boolean.valueOf(this.getEnableBeanClassRedeploy()).toString())).append("</enable-bean-class-redeploy>\n");
      }

      for(i = 0; i < this.getDisableWarnings().length; ++i) {
         result.append(ToXML.indent(indentLevel + 2)).append("<disable-warning>").append(this.getDisableWarnings()[i]).append("</disable-warning>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</weblogic-ejb-jar>\n");
      return result.toString();
   }
}
