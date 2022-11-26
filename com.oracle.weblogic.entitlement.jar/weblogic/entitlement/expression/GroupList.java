package weblogic.entitlement.expression;

public class GroupList extends UnionList {
   public GroupList(EExprRep[] groups) {
      super(groups);
   }

   public String getListTag() {
      return "Grp";
   }

   char getTypeId() {
      return 'G';
   }
}
