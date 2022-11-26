package weblogic.jms.dispatcher;

import java.io.IOException;
import weblogic.jms.JMSEnvironment;
import weblogic.jms.frontend.FEBrowserCloseRequest;
import weblogic.jms.frontend.FEBrowserCreateRequest;
import weblogic.jms.frontend.FEBrowserGetEnumerationRequest;
import weblogic.jms.frontend.FEConnectionCloseRequest;
import weblogic.jms.frontend.FEConnectionSetClientIdRequest;
import weblogic.jms.frontend.FEConnectionStartRequest;
import weblogic.jms.frontend.FEConnectionStopRequest;
import weblogic.jms.frontend.FEConsumerCloseRequest;
import weblogic.jms.frontend.FEConsumerCreateRequest;
import weblogic.jms.frontend.FEConsumerIncrementWindowCurrentRequest;
import weblogic.jms.frontend.FEConsumerReceiveRequest;
import weblogic.jms.frontend.FEConsumerSetListenerRequest;
import weblogic.jms.frontend.FEDestinationCreateRequest;
import weblogic.jms.frontend.FEEnumerationNextElementRequest;
import weblogic.jms.frontend.FEProducerCloseRequest;
import weblogic.jms.frontend.FEProducerCreateRequest;
import weblogic.jms.frontend.FEProducerSendRequest;
import weblogic.jms.frontend.FERemoveSubscriptionRequest;
import weblogic.jms.frontend.FESessionAcknowledgeRequest;
import weblogic.jms.frontend.FESessionCloseRequest;
import weblogic.jms.frontend.FESessionCreateRequest;
import weblogic.jms.frontend.FESessionRecoverRequest;
import weblogic.jms.frontend.FESessionSetRedeliveryDelayRequest;
import weblogic.jms.frontend.FETemporaryDestinationCreateRequest;
import weblogic.jms.frontend.FETemporaryDestinationDestroyRequest;

public class FEDispatcherObjectHandler extends weblogic.messaging.dispatcher.DispatcherObjectHandler {
   public FEDispatcherObjectHandler() {
      super(16776960);
   }

   protected weblogic.messaging.dispatcher.Request instantiate(int tc) throws IOException {
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
            return JMSEnvironment.getJMSEnvironment().createFEConnectionConsumerCloseRequest();
         case 1536:
            return JMSEnvironment.getJMSEnvironment().createFEConnectionConsumerCreateRequest();
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
            return JMSEnvironment.getJMSEnvironment().createFEServerSessionPoolCloseRequest();
         case 5888:
            return JMSEnvironment.getJMSEnvironment().createFEServerSessionPoolCreateRequest();
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
         default:
            return super.instantiate(tc);
      }
   }
}
