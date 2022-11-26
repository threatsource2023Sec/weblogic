package netscape.ldap.util;

public class LDIFModDNContent extends LDIFBaseContent {
   private String m_newParent = null;
   private String m_rdn = null;
   private boolean m_deleteOldRDN = false;
   static final long serialVersionUID = 1352504898614557791L;

   public int getType() {
      return 4;
   }

   public void setRDN(String var1) {
      this.m_rdn = var1;
   }

   public String getRDN() {
      return this.m_rdn;
   }

   public void setNewParent(String var1) {
      this.m_newParent = var1;
   }

   public String getNewParent() {
      return this.m_newParent;
   }

   public void setDeleteOldRDN(boolean var1) {
      this.m_deleteOldRDN = var1;
   }

   public boolean getDeleteOldRDN() {
      return this.m_deleteOldRDN;
   }

   public String toString() {
      String var1 = "";
      if (this.m_newParent == null) {
         var1 = var1 + "new parent() ";
      } else {
         var1 = var1 + "new parent( " + this.m_newParent + " ), ";
      }

      if (this.m_deleteOldRDN) {
         var1 = var1 + "deleteOldRDN( true ), ";
      } else {
         var1 = var1 + "deleteOldRDN( false ), ";
      }

      if (this.m_rdn == null) {
         var1 = var1 + "new rdn()";
      } else {
         var1 = var1 + "new rdn( " + this.m_rdn + " )";
      }

      if (this.getControls() != null) {
         var1 = var1 + this.getControlString();
      }

      return "LDIFModDNContent {" + var1 + "}";
   }
}
