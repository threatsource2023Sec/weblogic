package kodo.conf.descriptor;

public interface FilterListenersBean {
   CustomFilterListenerBean[] getCustomFilterListeners();

   CustomFilterListenerBean createCustomFilterListener();

   void destroyCustomFilterListener(CustomFilterListenerBean var1);
}
