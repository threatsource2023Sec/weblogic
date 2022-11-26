package weblogic.entitlement.expression;

public class UserList extends UnionList {
   public UserList(EExprRep[] users) {
      super(users);
   }

   public String getListTag() {
      return "Usr";
   }

   char getTypeId() {
      return 'U';
   }
}
