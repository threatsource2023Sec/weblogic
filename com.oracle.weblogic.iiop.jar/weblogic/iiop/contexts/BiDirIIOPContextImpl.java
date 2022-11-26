package weblogic.iiop.contexts;

import java.util.Arrays;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.iiop.protocol.ListenPoint;
import weblogic.protocol.LocalServerIdentity;
import weblogic.rjvm.JVMID;

public final class BiDirIIOPContextImpl extends ServiceContext {
   private ListenPoint[] listenPoints;
   private static BiDirIIOPContextImpl context = new BiDirIIOPContextImpl();

   public BiDirIIOPContextImpl() {
      this(JVMID.localID().getAddress(), -1);
   }

   BiDirIIOPContextImpl(String address, int port) {
      super(5);
      this.listenPoints = new ListenPoint[]{new ListenPoint(address, port)};
   }

   protected BiDirIIOPContextImpl(CorbaInputStream in) {
      super(5);
      this.readEncapsulatedContext(in);
   }

   public static BiDirIIOPContextImpl getContext() {
      return context;
   }

   public ListenPoint[] getListenPoints() {
      return this.listenPoints;
   }

   public void write(CorbaOutputStream out) {
      this.writeEncapsulatedContext(out);
   }

   protected void readEncapsulation(CorbaInputStream in) {
      int len = in.read_ulong();
      this.listenPoints = new ListenPoint[len];

      for(int i = 0; i < len; ++i) {
         this.listenPoints[i] = new ListenPoint(in);
      }

   }

   protected void writeEncapsulation(CorbaOutputStream out) {
      if (this.listenPoints == null) {
         out.write_ulong(0);
      } else {
         out.write_ulong(this.listenPoints.length);
         ListenPoint[] var2 = this.listenPoints;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            ListenPoint listenPoint = var2[var4];
            listenPoint.writeForChannel(out, LocalServerIdentity.getIdentity());
         }
      }

   }

   public String toString() {
      return "BiDirIIOPContextImpl: listenPoints=" + Arrays.toString(this.listenPoints);
   }
}
