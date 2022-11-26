package netscape.ldap;

public class LDAPResponseListener extends LDAPMessageQueue {
   static final long serialVersionUID = 901897097111294329L;

   LDAPResponseListener(boolean var1) {
      super(var1);
   }

   public LDAPResponse getResponse() throws LDAPException {
      return (LDAPResponse)this.nextMessage();
   }

   public void merge(LDAPResponseListener var1) {
      super.merge(var1);
   }

   public boolean isResponseReceived() {
      return super.isMessageReceived();
   }

   public int[] getMessageIDs() {
      return super.getMessageIDs();
   }
}
