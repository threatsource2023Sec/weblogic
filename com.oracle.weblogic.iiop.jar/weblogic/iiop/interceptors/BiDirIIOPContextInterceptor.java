package weblogic.iiop.interceptors;

import weblogic.iiop.EndPoint;
import weblogic.iiop.EndPointManager;
import weblogic.iiop.contexts.BiDirIIOPContextImpl;
import weblogic.iiop.contexts.ServiceContextList;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.ListenPoint;
import weblogic.utils.SyncKeyTable;

public class BiDirIIOPContextInterceptor implements ServerContextInterceptor {
   public void handleReceivedRequest(ServiceContextList serviceContexts, EndPoint endPoint, CorbaInputStream inputStream) {
      BiDirIIOPContextImpl bi = (BiDirIIOPContextImpl)serviceContexts.getServiceContext(5);
      if (bi != null) {
         endPoint.setFlag(64);
         SyncKeyTable keys = (SyncKeyTable)endPoint.getConnection().getProperty("weblogic.iiop.BiDirKeys");
         if (keys == null) {
            keys = new SyncKeyTable();
            endPoint.getConnection().setProperty("weblogic.iiop.BiDirKeys", keys);
         }

         ListenPoint[] points = bi.getListenPoints();
         ListenPoint[] var7 = points;
         int var8 = points.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            ListenPoint point = var7[var9];
            keys.put(point);
         }

         if (points.length > 0) {
            EndPointManager.updateConnection(endPoint.getConnection(), points[0]);
         }
      }

   }
}
