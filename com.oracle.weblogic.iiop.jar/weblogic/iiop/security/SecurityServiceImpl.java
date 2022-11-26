package weblogic.iiop.security;

import java.rmi.RemoteException;
import weblogic.iiop.Connection;
import weblogic.iiop.EndPoint;
import weblogic.protocol.configuration.ChannelHelper;
import weblogic.rmi.spi.InboundRequest;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.SecurityService;
import weblogic.security.acl.UserInfo;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.AuthenticatedUser;

public final class SecurityServiceImpl implements SecurityService {
   public AuthenticatedUser authenticate(UserInfo ui) throws RemoteException {
      throw new AssertionError("authenticate()");
   }

   public AuthenticatedUser authenticate(UserInfo ui, InboundRequest request) throws RemoteException {
      Connection connection = ((EndPoint)request.getEndPoint()).getConnection();
      connection.authenticate(ui);
      AuthenticatedSubject subject = connection.getUser();
      if (ChannelHelper.isLocalAdminChannelEnabled() && SubjectUtils.isUserAnAdministrator(subject) && connection.getChannel().getProtocol().getQOS() != 103) {
         throw new SecurityException("All administrative tasks must go through an Administration Port.");
      } else {
         return subject;
      }
   }
}
