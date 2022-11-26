package weblogic.cluster.messaging.internal;

public class ProbeContextImpl implements ProbeContext {
   private SuspectedMemberInfo suspectedMember;
   private int nextAction = 0;
   private String message;
   private int result = 1;

   public ProbeContextImpl(SuspectedMemberInfo suspectedMember) {
      this.suspectedMember = suspectedMember;
   }

   public SuspectedMemberInfo getSuspectedMemberInfo() {
      return this.suspectedMember;
   }

   public int getNextAction() {
      return this.nextAction;
   }

   public void setNextAction(int nextAction) {
      this.nextAction = nextAction;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public String getMessage() {
      return this.message;
   }

   public int getResult() {
      return this.result;
   }

   public void setResult(int result) {
      this.result = result;
   }
}
