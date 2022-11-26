package weblogic.diagnostics.image;

import java.security.AccessController;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.WLDFImageCreationTaskRuntimeMBean;
import weblogic.management.runtime.WLDFImageRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class ImageRuntimeMBeanImpl extends PartitionImageRuntimeMBeanImpl implements WLDFImageRuntimeMBean {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticImage");
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static ImageRuntimeMBeanImpl singleton;

   public static synchronized ImageRuntimeMBeanImpl getInstance() throws ManagementException {
      if (singleton == null) {
         singleton = new ImageRuntimeMBeanImpl();
      }

      return singleton;
   }

   public ImageRuntimeMBeanImpl() throws ManagementException {
      super("Image", ManagementService.getRuntimeAccess(kernelId).getServerRuntime().getWLDFRuntime());
      this.imHelper = ImageRuntimeHelper.getInstance();
      this.partitionName = null;
      this.partitionId = null;
   }

   public ImageRuntimeMBeanImpl(RuntimeMBean parent) throws ManagementException {
      super(parent.getName(), parent);
      this.imHelper = ImageRuntimeHelper.getInstance();
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      this.partitionName = this.name;
      this.partitionId = runtimeAccess.getDomain().lookupPartition(this.name).getPartitionID();
   }

   public WLDFImageCreationTaskRuntimeMBean captureImage(String destination) throws ManagementException {
      return this.setTaskRestParent(this.imHelper.captureImage(this.partitionName, this.partitionId, destination));
   }

   public WLDFImageCreationTaskRuntimeMBean captureImage(String destination, int lockoutMinutes) throws ManagementException {
      return this.setTaskRestParent(this.imHelper.captureImage(this.partitionName, this.partitionId, destination, lockoutMinutes));
   }
}
