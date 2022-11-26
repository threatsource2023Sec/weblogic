package weblogic.application.ready;

public interface Ready {
   int APPLICATION_READY = 0;
   int APPLICATION_OFFLINE = 1;

   void ready();

   void notReady();
}
