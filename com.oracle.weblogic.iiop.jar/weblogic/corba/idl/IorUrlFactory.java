package weblogic.corba.idl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.ior.Target;

class IorUrlFactory {
   static String createUrl(List iors) {
      String prefix = ((IOR)iors.get(0)).isSecure() ? "iiops://" : "iiop://";
      Set targets = toUniqueTargets(iors);
      StringBuilder sb = new StringBuilder();
      Iterator var4 = targets.iterator();

      while(var4.hasNext()) {
         Target target = (Target)var4.next();
         sb.append(sb.length() == 0 ? prefix : ",").append(target);
      }

      return sb.toString();
   }

   private static Set toUniqueTargets(List iors) {
      Set result = new HashSet();
      Iterator var2 = iors.iterator();

      while(var2.hasNext()) {
         IOR ior = (IOR)var2.next();
         result.add(ior.getTarget());
      }

      return result;
   }
}
