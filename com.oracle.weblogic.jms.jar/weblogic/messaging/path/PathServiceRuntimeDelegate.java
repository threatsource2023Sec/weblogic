package weblogic.messaging.path;

import java.util.Iterator;
import java.util.LinkedList;
import weblogic.management.ManagementException;
import weblogic.management.runtime.PSAssemblyRuntimeMBean;
import weblogic.management.runtime.PathServiceRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.messaging.path.helper.KeyString;
import weblogic.store.xa.PersistentStoreXA;

public class PathServiceRuntimeDelegate extends RuntimeMBeanDelegate implements PathServiceRuntimeMBean {
   static final long serialVersionUID = 3061948838575555332L;
   private transient PathServiceMap pathService;

   public PathServiceRuntimeDelegate(String name, PathServiceMap pathService) throws ManagementException {
      super(name);
      this.pathService = pathService;
   }

   public PSAssemblyRuntimeMBean[] getAssemblies() throws ManagementException {
      PersistentStoreXA store = this.pathService.getStore();
      Iterator iterator = store.getMapConnectionNames();
      LinkedList mapList = new LinkedList();

      while(iterator.hasNext()) {
         PathServiceMap var10000 = this.pathService;
         KeyString keyString = PathServiceMap.sampleKeyFromMapName((String)iterator.next());
         if (keyString != null) {
            mapList.add(new PSAssemblyRuntimeDelegate(keyString, this, this.pathService));
         }
      }

      PSAssemblyRuntimeDelegate[] mapsRuntimeMbeans = new PSAssemblyRuntimeDelegate[mapList.size()];
      mapList.toArray(mapsRuntimeMbeans);
      return mapsRuntimeMbeans;
   }
}
