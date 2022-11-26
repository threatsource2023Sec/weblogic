package weblogic.j2ee.descriptor.wl;

public interface QueueBean extends DestinationBean {
   int getForwardDelay();

   void setForwardDelay(int var1) throws IllegalArgumentException;

   boolean getResetDeliveryCountOnForward();

   void setResetDeliveryCountOnForward(boolean var1) throws IllegalArgumentException;
}
