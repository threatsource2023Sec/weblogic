package weblogic.jms.common;

import java.io.IOException;
import javax.naming.NamingException;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.messaging.dispatcher.DispatcherRemote;
import weblogic.messaging.dispatcher.Request;
import weblogic.security.subject.AbstractSubject;

public interface CrossDomainSecurityUtil {
   AbstractSubject getSubjectFromListener(CDSListListener var1) throws NamingException, IOException;

   void checkRole(DispatcherRemote var1, Request var2) throws javax.jms.JMSException;

   void checkRole(JMSDispatcher var1, Request var2) throws javax.jms.JMSException;

   AbstractSubject getRemoteSubject(JMSDispatcher var1, AbstractSubject var2, boolean var3) throws javax.jms.JMSException;

   AbstractSubject getRemoteSubject(String var1, AbstractSubject var2);

   AbstractSubject getRemoteSubject(JMSDispatcher var1) throws javax.jms.JMSException;

   boolean isRemoteDomain(String var1) throws IOException;

   boolean isRemoteDomain(JMSDispatcher var1) throws IOException;

   boolean ifRemoteSubjectExists(String var1);

   boolean isKernelIdentity(AbstractSubject var1);
}
