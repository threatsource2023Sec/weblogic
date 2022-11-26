package weblogic.iiop;

import java.util.Iterator;
import weblogic.iiop.ior.ClusterComponent;
import weblogic.iiop.ior.IOR;

public class printior {
   public static void main(String[] args) throws Exception {
      if (args.length != 1) {
         System.out.println("weblogic.iiop.printior <ior>");
      } else {
         IOR ior = IOR.destringify(args[0]);
         prettyPrintIOR(ior);
         ClusterComponent cc = (ClusterComponent)ior.getProfile().getComponent(1111834883);
         if (cc != null) {
            System.out.println("Cluster replicas: " + cc.getIORs().size());
            int x = 0;
            Iterator var4 = cc.getIORs().iterator();

            while(var4.hasNext()) {
               IOR anIor = (IOR)var4.next();
               System.out.println("Replica " + x++);
               prettyPrintIOR(anIor);
            }
         }
      }

   }

   private static void prettyPrintIOR(IOR ior) {
      System.out.println("IOR for type: " + ior.getTypeId());
      System.out.println("IOP Profile:");
      System.out.println("\tversion:\t1." + ior.getProfile().getMinorVersion());
      System.out.println("\thost:\t" + ior.getProfile().getHost());
      System.out.println("\tport:\t" + ior.getProfile().getPort());
      System.out.println("\tSSL host:\t" + ior.getProfile().getSecureHost());
      System.out.println("\tSSL port:\t" + ior.getProfile().getSecurePort());
      System.out.println("\tCSIv2 support:\t" + ior.getProfile().useSAS());
      System.out.println("\ttransactional:\t" + ior.getProfile().isTransactional());
      System.out.println("\tclusterable:\t" + ior.getProfile().isClusterable());
      System.out.println("\tkey:\t" + ObjectKey.getObjectKey(ior).toString());
   }
}
