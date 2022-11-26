package kodo.jdo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JDOProperties {
   private static final Map _translation = new HashMap();

   public static Map toKodoProperties(Map props) {
      if (props != null && !props.isEmpty()) {
         Map copy = null;
         Iterator itr = props.keySet().iterator();

         while(itr.hasNext()) {
            Object key = itr.next();
            Object trans = _translation.get(key);
            if (trans != null) {
               if (copy == null) {
                  copy = new HashMap(props);
               }

               copy.put(trans, copy.remove(key));
            }
         }

         return (Map)(copy == null ? props : copy);
      } else {
         return props;
      }
   }

   static {
      _translation.put("javax.jdo.option.Optimistic", "openjpa.Optimistic");
      _translation.put("javax.jdo.option.RetainValues", "openjpa.RetainState");
      _translation.put("javax.jdo.option.RestoreValues", "openjpa.RestoreState");
      _translation.put("javax.jdo.option.IgnoreCache", "openjpa.IgnoreChanges");
      _translation.put("javax.jdo.option.NontransactionalRead", "openjpa.NontransactionalRead");
      _translation.put("javax.jdo.option.NontransactionalWrite", "openjpa.NontransactionalWrite");
      _translation.put("javax.jdo.option.NontransactionalWrite", "openjpa.NontransactionalWrite");
      _translation.put("javax.jdo.option.Multithreaded", "openjpa.Multithreaded");
      _translation.put("javax.jdo.option.ConnectionDriverName", "openjpa.ConnectionDriverName");
      _translation.put("javax.jdo.option.ConnectionUserName", "openjpa.ConnectionUserName");
      _translation.put("javax.jdo.option.ConnectionPassword", "openjpa.ConnectionPassword");
      _translation.put("javax.jdo.option.ConnectionURL", "openjpa.ConnectionURL");
      _translation.put("javax.jdo.option.ConnectionFactoryName", "openjpa.ConnectionFactoryName");
      _translation.put("javax.jdo.option.ConnectionFactory2Name", "openjpa.ConnectionFactory2Name");
      _translation.put("javax.jdo.option.Mapping", "openjpa.Mapping");
      _translation.put("javax.jdo.option.DetachAllOnClose", "openjpa.AutoDetach");
      _translation.put("javax.jdo.mapping.Schema", "openjpa.jdbc.Schema");
   }
}
