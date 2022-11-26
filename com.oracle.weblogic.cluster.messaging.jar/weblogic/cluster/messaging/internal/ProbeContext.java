package weblogic.cluster.messaging.internal;

public interface ProbeContext {
   int STOP = 0;
   int INVOKE_NEXT_PROBE = 1;
   int FAILURE = -1;
   int UNKNOWN = 0;
   int SUCCESS = 1;

   SuspectedMemberInfo getSuspectedMemberInfo();

   int getNextAction();

   void setNextAction(int var1);

   void setMessage(String var1);

   String getMessage();

   int getResult();

   void setResult(int var1);
}
