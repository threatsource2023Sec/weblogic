package kodo.conf.descriptor;

public interface AggregateListenersBean {
   CustomAggregateListenerBean[] getCustomAggregateListeners();

   CustomAggregateListenerBean createCustomAggregateListener();

   void destroyCustomAggregateListener(CustomAggregateListenerBean var1);
}
