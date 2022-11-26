package weblogic.iiop;

import javax.rmi.CORBA.Util;

public class repid {
   public static final void main(String[] args) throws Exception {
      if (args.length == 0) {
         System.out.println("weblogic.iiop.repid <classname>");
      }

      for(int i = 0; i < args.length; ++i) {
         Class c = Class.forName(args[i]);
         System.out.println(c.getName() + ": " + Util.createValueHandler().getRMIRepositoryID(c));
      }

   }
}
