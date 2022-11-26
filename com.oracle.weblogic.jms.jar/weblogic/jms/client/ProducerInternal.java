package weblogic.jms.client;

import javax.jms.QueueSender;
import javax.jms.TopicPublisher;
import weblogic.jms.extensions.WLInternalProducer;

public interface ProducerInternal extends TopicPublisher, QueueSender, WLInternalProducer, ClientRuntimeInfo {
   String getPartitionName();
}
