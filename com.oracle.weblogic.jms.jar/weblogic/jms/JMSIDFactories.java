package weblogic.jms;

import weblogic.messaging.common.IDFactory;
import weblogic.messaging.common.MessageIDFactory;

public class JMSIDFactories {
   public static final IDFactory idFactory = new IDFactory();
   public static final MessageIDFactory messageIDFactory = new MessageIDFactory();
}
