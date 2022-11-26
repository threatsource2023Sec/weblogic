package weblogic.entitlement.rules;

import weblogic.security.providers.authorization.IllegalPredicateArgumentException;
import weblogic.security.providers.authorization.PredicateArgument;
import weblogic.security.shared.LoggerWrapper;

public abstract class IDCSAppRoleNamePredicate extends BasePredicate {
   private static final String VERSION = "1.0";
   private static PredicateArgument APPNAME_ARG = new StringPredicateArgument("IDCSAppRoleNamePredicateAppNameArgumentName", "IDCSAppRoleNamePredicateAppNameArgumentDescription", (String)null);
   private static PredicateArgument ROLENAME_ARG = new StringPredicateArgument("IDCSAppRoleNamePredicateAppRoleArgumentName", "IDCSAppRoleNamePredicateAppRoleArgumentDescription", (String)null);
   private static PredicateArgument[] arguments;
   private String appName = null;
   private String roleName = null;
   protected static final LoggerWrapper log;

   public IDCSAppRoleNamePredicate(String displayNameId, String descriptionId) {
      super(displayNameId, descriptionId);
   }

   public void init(String[] args) throws IllegalPredicateArgumentException {
      if (args != null && args.length == 2) {
         this.roleName = args[0];
         this.appName = args[1];
         if (log.isDebugEnabled()) {
            log.debug("IDCSAppRoleNamePredicate.init: roleName=" + this.roleName + ", appName=" + this.appName);
         }

      } else {
         throw new IllegalPredicateArgumentException("Two arguments are expected");
      }
   }

   protected String getAppName() {
      return this.appName;
   }

   protected String getRoleName() {
      return this.roleName;
   }

   public String getVersion() {
      return "1.0";
   }

   public int getArgumentCount() {
      return arguments.length;
   }

   public PredicateArgument getArgument(int index) {
      return arguments[index];
   }

   static {
      arguments = new PredicateArgument[]{ROLENAME_ARG, APPNAME_ARG};
      log = LoggerWrapper.getInstance("SecurityPredicate");
   }
}
