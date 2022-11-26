package weblogic.messaging.saf.utils;

import java.io.StreamCorruptedException;
import java.security.AccessController;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.messaging.ID;
import weblogic.messaging.common.IDFactory;
import weblogic.messaging.common.IDImpl;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public final class Util {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final IDFactory ID_FACTORY = new IDFactory();

   public static StreamCorruptedException versionIOException(int version, int minExpectedVersion, int maxExpectedVersion) {
      return SAFClientUtil.versionIOException(version, minExpectedVersion, maxExpectedVersion);
   }

   public static final WorkManager findOrCreateWorkManager(String name, int fairShare, int minThreadsConstraint, int maxThreadsConstraint) {
      ServerMBean server = ManagementService.getRuntimeAccess(KERNEL_ID).getServer();
      return server.getUse81StyleExecuteQueues() ? WorkManagerFactory.getInstance().getSystem() : WorkManagerFactory.getInstance().findOrCreate(name, fairShare, minThreadsConstraint, maxThreadsConstraint);
   }

   public static ID generateID() {
      return new IDImpl(ID_FACTORY);
   }
}
