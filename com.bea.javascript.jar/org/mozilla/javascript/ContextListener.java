package org.mozilla.javascript;

public interface ContextListener {
   void contextCreated(Context var1);

   void contextEntered(Context var1);

   void contextExited(Context var1);

   void contextReleased(Context var1);
}
