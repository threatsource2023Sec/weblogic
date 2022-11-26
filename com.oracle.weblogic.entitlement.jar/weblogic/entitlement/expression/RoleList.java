package weblogic.entitlement.expression;

public class RoleList extends UnionList {
   public RoleList(EExprRep[] roles) {
      super(roles);
   }

   public String getListTag() {
      return "Rol";
   }

   char getTypeId() {
      return 'R';
   }
}
