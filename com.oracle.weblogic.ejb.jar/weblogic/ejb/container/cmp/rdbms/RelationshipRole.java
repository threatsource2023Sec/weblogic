package weblogic.ejb.container.cmp.rdbms;

public final class RelationshipRole {
   private String m_roleName = null;
   private String m_groupName = null;
   private ObjectLink m_columnMap = null;

   public RelationshipRole(String rn, String gn, ObjectLink cm) {
      this.m_roleName = rn;
      this.m_groupName = gn;
      this.m_columnMap = cm;
   }

   public String toString() {
      return "[RelationshipRole name:" + this.m_roleName + " group:" + this.m_groupName + " map:" + this.m_columnMap + "]";
   }
}
