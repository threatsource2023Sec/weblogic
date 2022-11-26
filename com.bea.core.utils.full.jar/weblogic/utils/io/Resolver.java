package weblogic.utils.io;

import java.io.IOException;
import java.io.ObjectStreamClass;

public interface Resolver {
   Class NOT_FOUND = null;

   Class resolveClass(ObjectStreamClass var1) throws IOException, ClassNotFoundException;
}
