package netscape.ldap;

public class LDAPSearchListener extends LDAPMessageQueue {
   static final long serialVersionUID = -7163312406176592277L;
   private Long m_key = null;
   private LDAPSearchConstraints m_constraints;

   LDAPSearchListener(boolean var1, LDAPSearchConstraints var2) {
      super(var1);
      this.m_constraints = var2;
   }

   LDAPResponse completeSearchOperation() throws LDAPException {
      return this.completeRequest();
   }

   public LDAPMessage getResponse() throws LDAPException {
      return this.nextMessage();
   }

   LDAPMessage nextMessage() throws LDAPException {
      LDAPMessage var1 = super.nextMessage();
      if (var1 instanceof LDAPSearchResult || var1 instanceof LDAPSearchResultReference) {
         LDAPConnThread var2 = this.getConnThread(var1.getMessageID());
         if (var2 != null) {
            var2.resultRetrieved();
         }
      }

      return var1;
   }

   public void merge(LDAPSearchListener var1) {
      super.merge(var1);
   }

   public boolean isResponseReceived() {
      return super.isMessageReceived();
   }

   public int[] getMessageIDs() {
      return super.getMessageIDs();
   }

   LDAPSearchConstraints getSearchConstraints() {
      return this.m_constraints;
   }

   void setSearchConstraints(LDAPSearchConstraints var1) {
      this.m_constraints = var1;
   }

   void reset() {
      super.reset();
      this.m_constraints = null;
   }

   void setKey(Long var1) {
      this.m_key = var1;
   }

   Long getKey() {
      return this.m_key;
   }
}
