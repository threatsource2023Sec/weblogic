package weblogic.iiop.server;

import weblogic.iiop.EndPoint;
import weblogic.iiop.messages.ReplyMessage;
import weblogic.iiop.messages.RequestMessage;
import weblogic.security.acl.internal.AuthenticatedSubject;

public interface ServerEndPoint extends EndPoint {
   void putSecurityContext(long var1, SecurityContext var3);

   SecurityContext getSecurityContext(long var1);

   void removeSecurityContext(long var1);

   AuthenticatedSubject getSubject(RequestMessage var1);

   Object getInboundRequestTxContext(RequestMessage var1);

   void setOutboundResponseTxContext(ReplyMessage var1, Object var2);
}
