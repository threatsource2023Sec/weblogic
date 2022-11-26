package weblogic.management.security;

public class DeploymentModel {
   public static final String DD_ONLY = "DDOnly";
   public static final String CUSTOM_ROLES = "CustomRoles";
   public static final String CUSTOM_ROLES_POLICIES = "CustomRolesAndPolicies";
   public static final String ADVANCED = "Advanced";
   public static final String MODEL_LIST_DISPLAY = "DDOnly|CustomRoles|CustomRolesAndPolicies|Advanced";

   public static final boolean isValidModel(String model) {
      return model.equals("DDOnly") || model.equals("CustomRoles") || model.equals("CustomRolesAndPolicies") || model.equals("Advanced");
   }
}
