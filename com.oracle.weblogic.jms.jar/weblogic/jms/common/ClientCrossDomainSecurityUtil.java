package weblogic.jms.common;

import java.io.IOException;
import javax.naming.NamingException;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.messaging.dispatcher.DispatcherRemote;
import weblogic.messaging.dispatcher.Request;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;

public final class ClientCrossDomainSecurityUtil implements CrossDomainSecurityUtil {
   public AbstractSubject getSubjectFromListener(CDSListListener listener) throws NamingException, IOException {
      AbstractSubject subject = listener.getSubject();
      if (subject == null) {
         subject = SubjectManager.getSubjectManager().getAnonymousSubject();
      }

      return subject;
   }

   public AbstractSubject getRemoteSubject(String url, AbstractSubject suggestedLocalSubject) {
      return suggestedLocalSubject != null ? suggestedLocalSubject : SubjectManager.getSubjectManager().getAnonymousSubject();
   }

   public void checkRole(DispatcherRemote dispatcherRemote, Request request) throws javax.jms.JMSException {
   }

   public void checkRole(JMSDispatcher dispatcher, Request request) throws javax.jms.JMSException {
   }

   public AbstractSubject getRemoteSubject(JMSDispatcher dispatcher) throws javax.jms.JMSException {
      return SubjectManager.getSubjectManager().getAnonymousSubject();
   }

   public AbstractSubject getRemoteSubject(JMSDispatcher dispatcher, AbstractSubject suggestedSubject, boolean suggestedSubjectGoodForRemoteDomain) throws javax.jms.JMSException {
      return suggestedSubject != null ? suggestedSubject : SubjectManager.getSubjectManager().getAnonymousSubject();
   }

   public boolean isRemoteDomain(String url) throws IOException {
      return false;
   }

   public boolean isRemoteDomain(JMSDispatcher dispatcher) throws IOException {
      return false;
   }

   public boolean ifRemoteSubjectExists(String url) {
      return false;
   }

   public boolean isKernelIdentity(AbstractSubject subject) {
      return false;
   }
}
