package weblogic.ejb.container.ejbc.bytecodegen;

import java.io.IOException;
import weblogic.ejb.container.ejbc.EJBCException;

class RemoteBusIntfGenerator implements Generator {
   private final Class busIntf;
   private final String clsName;

   RemoteBusIntfGenerator(String rbiName, Class busIntf) {
      this.clsName = BCUtil.binName(rbiName);
      this.busIntf = busIntf;
   }

   public Generator.Output generate() throws EJBCException {
      try {
         byte[] bytes = RemoteBusIntfClassAdapter.getRBIBytes(this.busIntf, this.clsName);
         return new ClassFileOutput(this.clsName, bytes);
      } catch (IOException var2) {
         throw new EJBCException("Error reading the class file of " + this.busIntf, var2);
      }
   }
}
