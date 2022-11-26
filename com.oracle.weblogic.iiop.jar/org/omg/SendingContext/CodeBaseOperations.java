package org.omg.SendingContext;

import org.omg.CORBA.Repository;
import org.omg.CORBA.ValueDefPackage.FullValueDescription;

public interface CodeBaseOperations extends RunTimeOperations {
   Repository get_ir();

   String implementation(String var1);

   String implementationx(String var1);

   String[] implementations(String[] var1);

   FullValueDescription meta(String var1);

   FullValueDescription[] metas(String[] var1);

   String[] bases(String var1);
}
