package weblogic.jms.client;

import javax.jms.XAQueueSession;
import javax.jms.XASession;
import javax.jms.XATopicSession;

public interface XASessionInternal extends XAQueueSession, XASession, XATopicSession {
}
