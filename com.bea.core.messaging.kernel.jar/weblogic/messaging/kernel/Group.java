package weblogic.messaging.kernel;

public interface Group {
   Queue getQueue();

   String getName();

   int size();
}
