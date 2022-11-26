package weblogic.management.rest.lib.bean.utils;

import org.codehaus.jettison.json.JSONObject;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.jmx.modelmbean.WLSModelMBeanContext;
import weblogic.management.mbeanservers.edit.ActivationTaskMBean;
import weblogic.management.mbeanservers.edit.internal.ConfigurationManagerMBeanImpl;
import weblogic.management.rest.lib.utils.ActivationTaskWrapper;
import weblogic.management.rest.lib.utils.ConfigurationManagerWrapper;

public class BeanConfigurationManagerWrapper extends BaseWrapper implements ConfigurationManagerWrapper {
   public BeanConfigurationManagerWrapper(InvocationContext ic) throws Exception {
      super(ic.clone(new ConfigurationManagerMBeanImpl(EditUtils.getEditAccess(ic.request()), (WLSModelMBeanContext)null)));
      String onameTypeName = "weblogic.management.mbeanservers.edit.EditServiceMBean";
      String typeName = PartitionUtils.isPartitioned() ? "weblogic.management.mbeanservers.edit.EditSessionServiceMBean" : onameTypeName;
      AtzUtils.checkGetAccess(ic.request(), "EditService", typeName, onameTypeName, "configurationManager");
   }

   public void startEdit(int waitTimeInMillis, int timeOutInMillis) throws Exception {
      JSONObject jsonParams = new JSONObject();
      jsonParams.put("waitTimeInMillis", waitTimeInMillis);
      jsonParams.put("timeOutInMillis", timeOutInMillis);
      this.act("startEdit", jsonParams);
   }

   public void startEdit(int waitTimeInMillis, int timeOutInMillis, boolean exclusive) throws Exception {
      JSONObject jsonParams = new JSONObject();
      jsonParams.put("waitTimeInMillis", waitTimeInMillis);
      jsonParams.put("timeOutInMillis", timeOutInMillis);
      jsonParams.put("exclusive", exclusive);
      this.act("startEdit", jsonParams);
   }

   public void removeReferencesToBean(DescriptorBean configurationMBean) throws Exception {
      JSONObject jsonParams = new JSONObject();
      jsonParams.put("configurationMBean", PathUtils.getReferencedBeanJson(this.invocationContext().clone(configurationMBean)));
      this.act("removeReferencesToBean", jsonParams);
   }

   public boolean haveUnactivatedChanges() throws Exception {
      return (Boolean)this.act("haveUnactivatedChanges");
   }

   public void undoUnactivatedChanges() throws Exception {
      this.act("undoUnactivatedChanges");
   }

   public void save() throws Exception {
      this.act("save");
   }

   public ActivationTaskWrapper resolve(boolean stopOnConflict, long timeout) throws Exception {
      JSONObject jsonParams = new JSONObject();
      jsonParams.put("stopOnConflict", stopOnConflict);
      jsonParams.put("timeout", timeout);
      ActivationTaskMBean task = (ActivationTaskMBean)this.act("resolve", jsonParams);
      return new BeanActivationTaskWrapper(this.invocationContext().clone(task));
   }

   public ActivationTaskWrapper activate(long timeout) throws Exception {
      JSONObject jsonParams = new JSONObject();
      jsonParams.put("timeout", timeout);
      ActivationTaskMBean task = (ActivationTaskMBean)this.act("activate", jsonParams);
      return new BeanActivationTaskWrapper(this.invocationContext().clone(task));
   }

   public void stopEdit() throws Exception {
      this.act("stopEdit");
   }

   public void cancelEdit() throws Exception {
      this.act("cancelEdit");
   }

   public String getCurrentEditor() throws Exception {
      return (String)((String)this.getProperty("currentEditor"));
   }

   public boolean isCurrentEditorExpired() throws Exception {
      return (Boolean)((Boolean)this.getProperty("currentEditorExpired"));
   }

   public boolean isCurrentEditorExclusive() throws Exception {
      return (Boolean)((Boolean)this.getProperty("currentEditorExclusive"));
   }

   public boolean isEditor() throws Exception {
      return (Boolean)((Boolean)this.getProperty("editor"));
   }
}
