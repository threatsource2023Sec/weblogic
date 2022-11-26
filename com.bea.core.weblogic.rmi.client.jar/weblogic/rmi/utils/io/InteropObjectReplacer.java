package weblogic.rmi.utils.io;

import java.io.IOException;
import javax.management.ObjectName;
import weblogic.common.internal.InteropWriteReplaceable;
import weblogic.common.internal.PeerInfo;
import weblogic.management.interop.JMXInteropHelper;
import weblogic.rmi.internal.RMIEnvironment;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.utils.InteropStackTraceUtils;
import weblogic.utils.io.Replacer;

public final class InteropObjectReplacer implements Replacer {
   private static final Replacer delegate = RemoteObjectReplacer.getReplacer();
   private final PeerInfo peerInfo;

   public InteropObjectReplacer(PeerInfo peerInfo) {
      this.peerInfo = peerInfo;
   }

   public void insertReplacer(Replacer replacer) {
      delegate.insertReplacer(replacer);
   }

   public Object replaceObject(Object o) throws IOException {
      if (o instanceof StubInfoIntf) {
         o = ((StubInfoIntf)o).getStubInfo();
      }

      if (o instanceof InteropWriteReplaceable) {
         o = ((InteropWriteReplaceable)o).interopWriteReplace(this.peerInfo);
      }

      if (o instanceof ObjectName && this.peerInfo.compareTo(PeerInfo.VERSION_DIABLO) < 0 && !JMXInteropHelper.isSunInteropPropertySpecified()) {
         ObjectName oName = (ObjectName)o;
         o = new weblogic.management.interop.ObjectName(oName.getCanonicalName());
      }

      if (o instanceof Throwable && RMIEnvironment.getEnvironment().isInstrumentStackTrace() && this.peerInfo.compareTo(PeerInfo.VERSION_81) < 0) {
         o = InteropStackTraceUtils.getThrowableWithStackTrace((Throwable)o, false);
      }

      return delegate.replaceObject(o);
   }

   public Object resolveObject(Object o) throws IOException {
      return delegate.resolveObject(o);
   }
}
