package kodo.ee;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;
import javax.resource.cci.ConnectionFactory;
import org.apache.openjpa.lib.util.Localizer;

public class KodoObjectFactory implements ObjectFactory {
   private static Localizer _loc = Localizer.forPackage(KodoObjectFactory.class);
   public static final String OBJECT_KEY = "kodo.name";
   private static Map refs = new HashMap();

   public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable environment) {
      if (!(obj instanceof Reference)) {
         throw new IllegalArgumentException(_loc.get("expected-reference", obj.getClass().getName()).getMessage());
      } else {
         Reference reference = (Reference)obj;
         String content = (String)reference.get("kodo.name").getContent();
         if (content == null) {
            throw new IllegalStateException(_loc.get("empty-reference", "kodo.name").getMessage());
         } else {
            Object ref = refs.get(content);
            if (ref == null) {
               throw new IllegalStateException(_loc.get("no-reference", content).getMessage());
            } else {
               return ref;
            }
         }
      }
   }

   public static synchronized void ref(String name, ConnectionFactory factory) {
      refs.put(name, factory);
   }
}
