package weblogic.ejb.container.ejbc.bytecodegen;

import weblogic.ejb.container.ejbc.EJBCException;

public interface Generator {
   Output generate() throws EJBCException;

   public interface Output {
      String relativeFilePath();

      byte[] bytes();
   }
}
