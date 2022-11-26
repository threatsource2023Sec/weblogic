package antlr.debug;

public interface InputBufferListener extends ListenerBase {
   void inputBufferConsume(InputBufferEvent var1);

   void inputBufferLA(InputBufferEvent var1);

   void inputBufferMark(InputBufferEvent var1);

   void inputBufferRewind(InputBufferEvent var1);
}
