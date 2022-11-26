package weblogic.utils.cmm.serverservice;

import com.bea.staxb.buildtime.internal.tylar.RuntimeTylar;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.provider.BeanInfoAccessFactory;
import weblogic.management.provider.internal.BeanInfoAccessImpl;
import weblogic.platform.VM;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;
import weblogic.utils.cmm.MemoryPressureService;

@Service
@Named
@RunLevel(20)
public class CMMService extends AbstractServerService {
   @Inject
   private MemoryPressureService memoryPressureService;
   @Inject
   private ServiceLocator locator;
   private CMMGCRunner runner;
   private CMMHeapRatio ratifier;

   public void start() throws ServiceFailureException {
      RuntimeTylar.setupCMMListeners(this.locator);
      ((BeanInfoAccessImpl)BeanInfoAccessFactory.getBeanInfoAccess()).setupMemoryListeners(this.locator);
      this.runner = new CMMGCRunner();
      this.memoryPressureService.registerListener("CMM GC Thread", this.runner);
      if (VM.getVM().isHotSpot()) {
         this.ratifier = new CMMHeapRatio();
         this.memoryPressureService.registerListener("CMM Heap Ratio", this.ratifier);
      }

   }

   public void halt() {
      if (this.ratifier != null) {
         this.memoryPressureService.unregisterListener(this.ratifier);
         this.ratifier = null;
      }

      if (this.runner != null) {
         this.memoryPressureService.unregisterListener(this.runner);
         this.runner.close();
         this.runner = null;
      }

   }

   public void stop() {
      this.halt();
   }
}
