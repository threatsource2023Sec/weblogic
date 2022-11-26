package weblogic.wtc.jatmi;

public class atnctxtd implements atncontext {
   public int context_state = 1;
   public atncredtd context_credential;
   public int[] context_challenge;
   static final int ED_WSP_PHASE1_INITIATE = 1;
   static final int ED_WSP_PHASE1_ACCEPT = 2;
   static final int ED_WSP_PHASE2_INITIATE = 3;
   static final int ED_WSP_PHASE2_ACCEPT = 4;
   static final int ED_WSP_PHASE3_INITIATE = 5;
   static final int ED_WSP_FINISHED = 6;
   static final int RANDOM_SIZE = 8;
   private static final int SALT_SIZE = 8;

   public atnctxtd(atncredtd cred) {
      this.context_credential = cred;
      this.context_challenge = new int[8];
   }
}
