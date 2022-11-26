package weblogic.management.rest.lib.bean.resources;

import org.glassfish.admin.rest.utils.StringUtil;

public abstract class ActionResourceMetaData extends ResourceMetaData {
   public abstract String actionName() throws Exception;

   public String path() throws Exception {
      return this.actionName();
   }

   protected void addMethods() throws Exception {
   }

   protected boolean supportsGET() throws Exception {
      return false;
   }

   protected boolean supportsPOST() throws Exception {
      return true;
   }

   protected Object[] defaultDescriptionArgs() throws Exception {
      return this.args(new Object[]{this.entityDisplayName(), this.path()});
   }

   protected Object[] defaultSummaryArgs() throws Exception {
      return this.args(new Object[]{StringUtil.lowerCaseWordsToUpperCaseWords(this.entityDisplayName()), StringUtil.camelCaseToUpperCaseWords(this.path())});
   }

   protected String defaultPOSTSummary() throws Exception {
      return "actionPOSTSummary";
   }

   protected String defaultPOSTReqDesc() {
      return "actionReqDesc";
   }

   protected String defaultPOSTRespDesc() {
      return "actionRespDesc";
   }

   public boolean isActionsCategory() throws Exception {
      return true;
   }
}
