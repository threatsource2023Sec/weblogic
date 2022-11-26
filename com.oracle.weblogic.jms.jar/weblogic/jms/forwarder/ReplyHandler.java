package weblogic.jms.forwarder;

import weblogic.jms.common.MessageProcessor;

public interface ReplyHandler extends MessageProcessor {
   void setReplyToSAFRemoteContextName(String var1);
}
