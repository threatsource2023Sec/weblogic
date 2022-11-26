package weblogic.timers.internal;

public interface TimerContext {
   void push();

   void pop();
}
