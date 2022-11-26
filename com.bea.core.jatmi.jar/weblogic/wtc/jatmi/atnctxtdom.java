package weblogic.wtc.jatmi;

public class atnctxtdom implements atncontext {
   public int context_state;
   private atncredtdom context_credential;
   public static final int SEC_STATE_NEW = -1;
   public static final int SEC_STATE_INIT_1 = 0;
   public static final int SEC_STATE_ACCEPT_1 = 1;
   public static final int SEC_STATE_INIT_2 = 2;
   public static final int SEC_STATE_ACCEPT_3 = 3;
   public static final int SEC_STATE_INIT_4 = 4;
   public static final int SEC_STATE_DONE = 5;

   public atnctxtdom(atncredtdom credential) {
      this.context_credential = credential;
      this.context_state = -1;
   }
}
