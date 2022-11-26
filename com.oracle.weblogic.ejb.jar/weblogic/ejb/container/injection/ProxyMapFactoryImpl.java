package weblogic.ejb.container.injection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProxyMapFactoryImpl implements ProxyMapFactory {
   public Map createProxiesToCdiBeanInstancesMap() {
      return new ConcurrentHashMap();
   }
}
