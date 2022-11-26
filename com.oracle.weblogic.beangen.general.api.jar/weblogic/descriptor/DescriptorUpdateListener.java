package weblogic.descriptor;

import java.util.EventListener;

public interface DescriptorUpdateListener extends EventListener {
   void prepareUpdate(DescriptorUpdateEvent var1) throws DescriptorUpdateRejectedException;

   void activateUpdate(DescriptorUpdateEvent var1) throws DescriptorUpdateFailedException;

   void rollbackUpdate(DescriptorUpdateEvent var1);
}
