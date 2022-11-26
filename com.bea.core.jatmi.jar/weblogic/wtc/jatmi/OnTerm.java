package weblogic.wtc.jatmi;

public interface OnTerm {
   int SEND_TERMINATED = 0;
   int RECV_TERMINATED = 1;
   int ABRT_TERMINATED = 2;
   int NAKA_TERMINATED = 3;
   int RECV_TERMINATED_DUPLICATE = 4;

   void onTerm(int var1);
}
