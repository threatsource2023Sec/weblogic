package org.apache.openjpa.event;

import java.util.EventObject;
import org.apache.openjpa.kernel.BrokerFactory;

public class BrokerFactoryEvent extends EventObject {
   public static final int BROKER_FACTORY_CREATED = 0;
   private int eventType;

   public BrokerFactoryEvent(BrokerFactory brokerFactory, int eventType) {
      super(brokerFactory);
      this.eventType = eventType;
   }

   public BrokerFactory getBrokerFactory() {
      return (BrokerFactory)this.getSource();
   }

   public int getEventType() {
      return this.eventType;
   }
}
