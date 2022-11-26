package weblogic.jms.dispatcher;

import java.io.IOException;
import java.security.AccessController;
import javax.security.auth.Subject;
import weblogic.jms.backend.BEBrowserCloseRequest;
import weblogic.jms.backend.BEBrowserCreateRequest;
import weblogic.jms.backend.BEBrowserGetEnumerationRequest;
import weblogic.jms.backend.BEConnectionConsumerCloseRequest;
import weblogic.jms.backend.BEConnectionConsumerCreateRequest;
import weblogic.jms.backend.BEConnectionStartRequest;
import weblogic.jms.backend.BEConnectionStopRequest;
import weblogic.jms.backend.BEConsumerCloseRequest;
import weblogic.jms.backend.BEConsumerCreateRequest;
import weblogic.jms.backend.BEConsumerIncrementWindowCurrentRequest;
import weblogic.jms.backend.BEConsumerIsActiveRequest;
import weblogic.jms.backend.BEConsumerReceiveRequest;
import weblogic.jms.backend.BEConsumerSetListenerRequest;
import weblogic.jms.backend.BEDestinationCreateRequest;
import weblogic.jms.backend.BEEnumerationNextElementRequest;
import weblogic.jms.backend.BEForwardRequest;
import weblogic.jms.backend.BEOrderUpdateRequest;
import weblogic.jms.backend.BEProducerSendRequest;
import weblogic.jms.backend.BERemoveSubscriptionRequest;
import weblogic.jms.backend.BEServerSessionGetRequest;
import weblogic.jms.backend.BEServerSessionPoolCloseRequest;
import weblogic.jms.backend.BEServerSessionPoolCreateRequest;
import weblogic.jms.backend.BESessionAcknowledgeRequest;
import weblogic.jms.backend.BESessionCloseRequest;
import weblogic.jms.backend.BESessionCreateRequest;
import weblogic.jms.backend.BESessionRecoverRequest;
import weblogic.jms.backend.BESessionSetRedeliveryDelayRequest;
import weblogic.jms.backend.BESessionStartRequest;
import weblogic.jms.backend.BESessionStopRequest;
import weblogic.jms.backend.BETemporaryDestinationDestroyRequest;
import weblogic.jms.common.DDMembershipCancelRequest;
import weblogic.jms.common.DDMembershipPushRequest;
import weblogic.jms.common.DDMembershipRequest;
import weblogic.jms.common.JMSPushExceptionRequest;
import weblogic.jms.common.JMSPushRequest;
import weblogic.jms.common.LeaderBindFailedRequest;
import weblogic.jms.common.LeaderBindRequest;
import weblogic.jms.frontend.FEBrowserCloseRequest;
import weblogic.jms.frontend.FEBrowserCreateRequest;
import weblogic.jms.frontend.FEBrowserGetEnumerationRequest;
import weblogic.jms.frontend.FEConnectionCloseRequest;
import weblogic.jms.frontend.FEConnectionConsumerCloseRequest;
import weblogic.jms.frontend.FEConnectionConsumerCreateRequest;
import weblogic.jms.frontend.FEConnectionSetClientIdRequest;
import weblogic.jms.frontend.FEConnectionStartRequest;
import weblogic.jms.frontend.FEConnectionStopRequest;
import weblogic.jms.frontend.FEConsumerCloseRequest;
import weblogic.jms.frontend.FEConsumerCreateRequest;
import weblogic.jms.frontend.FEConsumerIncrementWindowCurrentOneWayRequest;
import weblogic.jms.frontend.FEConsumerIncrementWindowCurrentRequest;
import weblogic.jms.frontend.FEConsumerReceiveRequest;
import weblogic.jms.frontend.FEConsumerSetListenerRequest;
import weblogic.jms.frontend.FEDestinationCreateRequest;
import weblogic.jms.frontend.FEEnumerationNextElementRequest;
import weblogic.jms.frontend.FEProducerCloseRequest;
import weblogic.jms.frontend.FEProducerCreateRequest;
import weblogic.jms.frontend.FEProducerSendRequest;
import weblogic.jms.frontend.FERemoveSubscriptionRequest;
import weblogic.jms.frontend.FEServerSessionPoolCloseRequest;
import weblogic.jms.frontend.FEServerSessionPoolCreateRequest;
import weblogic.jms.frontend.FESessionAcknowledgeRequest;
import weblogic.jms.frontend.FESessionCloseRequest;
import weblogic.jms.frontend.FESessionCreateRequest;
import weblogic.jms.frontend.FESessionRecoverRequest;
import weblogic.jms.frontend.FESessionSetRedeliveryDelayRequest;
import weblogic.jms.frontend.FETemporaryDestinationCreateRequest;
import weblogic.jms.frontend.FETemporaryDestinationDestroyRequest;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.subject.SubjectManager;

public class DispatcherObjectHandler extends weblogic.messaging.dispatcher.DispatcherObjectHandler {
   private AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static boolean disableAnonymous = false;

   public DispatcherObjectHandler() {
      super(16776960);
   }

   protected weblogic.messaging.dispatcher.Request instantiate(int tc) throws IOException {
      if (disableAnonymous) {
         Subject sub = SubjectManager.getSubjectManager().getCurrentSubject(this.kernelId).getSubject();
         if (SubjectUtils.isUserAnonymous(sub)) {
            throw new IOException("JMS layer doesn't allow anonymous access.");
         }
      }

      switch (tc) {
         case 256:
            return new FEBrowserCloseRequest();
         case 512:
            return new FEBrowserCreateRequest();
         case 768:
            return new FEBrowserGetEnumerationRequest();
         case 1024:
            return new FEConnectionCloseRequest();
         case 1280:
            return new FEConnectionConsumerCloseRequest();
         case 1536:
            return new FEConnectionConsumerCreateRequest();
         case 1792:
            return new FEConnectionSetClientIdRequest();
         case 2048:
            return new FEConnectionStartRequest();
         case 2304:
            return new FEConnectionStopRequest();
         case 2560:
            return new FEConsumerCloseRequest();
         case 2816:
            return new FEConsumerCreateRequest();
         case 3072:
            return new FEConsumerIncrementWindowCurrentRequest();
         case 3328:
            return new FEConsumerReceiveRequest();
         case 3584:
            return new FEConsumerSetListenerRequest();
         case 3840:
            return new FEDestinationCreateRequest();
         case 4096:
            return new FEEnumerationNextElementRequest();
         case 4608:
            return new FEProducerCloseRequest();
         case 4864:
            return new FEProducerCreateRequest();
         case 5120:
            return new FEProducerSendRequest();
         case 5376:
            return new FERemoveSubscriptionRequest();
         case 5632:
            return new FEServerSessionPoolCloseRequest();
         case 5888:
            return new FEServerSessionPoolCreateRequest();
         case 6144:
            return new FESessionAcknowledgeRequest();
         case 6400:
            return new FESessionCloseRequest();
         case 6656:
            return new FESessionCreateRequest();
         case 6912:
            return new FESessionRecoverRequest();
         case 7168:
            return new FESessionSetRedeliveryDelayRequest();
         case 7424:
            return new FETemporaryDestinationDestroyRequest();
         case 7680:
            return new FETemporaryDestinationCreateRequest();
         case 8192:
            return new BEBrowserCloseRequest();
         case 8448:
            return new BEBrowserCreateRequest();
         case 8704:
            return new BEBrowserGetEnumerationRequest();
         case 8960:
            return new BEConnectionConsumerCloseRequest();
         case 9216:
            return new BEConnectionConsumerCreateRequest();
         case 9472:
            return new BEConnectionStartRequest();
         case 9728:
            return new BEConnectionStopRequest();
         case 9984:
            return new BEConsumerCloseRequest();
         case 10240:
            return new BEConsumerCreateRequest();
         case 10496:
            return new BEConsumerIncrementWindowCurrentRequest();
         case 10752:
            return new BEConsumerIsActiveRequest();
         case 11008:
            return new BEConsumerReceiveRequest();
         case 11264:
            return new BEConsumerSetListenerRequest();
         case 11520:
            return new BEDestinationCreateRequest();
         case 11776:
            return new BEEnumerationNextElementRequest();
         case 12032:
            return new BEProducerSendRequest();
         case 12288:
            return new BEServerSessionGetRequest();
         case 12544:
            return new BEServerSessionPoolCloseRequest();
         case 12800:
            return new BEServerSessionPoolCreateRequest();
         case 13056:
            return new BESessionAcknowledgeRequest();
         case 13312:
            return new BESessionCloseRequest();
         case 13568:
            return new BESessionCreateRequest();
         case 13824:
            return new BESessionRecoverRequest();
         case 14080:
            return new BESessionSetRedeliveryDelayRequest();
         case 14336:
            return new BESessionStartRequest();
         case 14592:
            return new BESessionStopRequest();
         case 14848:
            return new BERemoveSubscriptionRequest();
         case 15104:
            return new BETemporaryDestinationDestroyRequest();
         case 15360:
            return new JMSPushExceptionRequest();
         case 15616:
            return new JMSPushRequest();
         case 16384:
            return new LeaderBindRequest();
         case 16640:
            return new LeaderBindFailedRequest();
         case 17408:
            return new FEConsumerIncrementWindowCurrentOneWayRequest();
         case 17664:
            return new BEForwardRequest();
         case 17920:
            return new BEOrderUpdateRequest();
         case 18432:
            return new DDMembershipRequest();
         case 18688:
            return new DDMembershipPushRequest();
         case 18944:
            return new DDMembershipCancelRequest();
         default:
            return super.instantiate(tc);
      }
   }

   static {
      try {
         String propValue = System.getProperty("weblogic.jms.client.disableAnonymous");
         if (propValue != null && propValue.equalsIgnoreCase("true")) {
            disableAnonymous = true;
         }
      } catch (RuntimeException var1) {
      }

   }
}
