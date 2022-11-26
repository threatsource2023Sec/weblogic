package weblogic.utils.compiler.jdt;

import com.bea.core.repackaged.jdt.internal.compiler.batch.FileSystem;
import com.bea.core.repackaged.jdt.internal.compiler.env.NameEnvironmentAnswer;

public class EJBFileSystem extends FileSystem {
   public EJBFileSystem(String[] classpathNames, String[] initialFileNames, String encoding) {
      super(classpathNames, initialFileNames, encoding);
   }

   public NameEnvironmentAnswer findType(char[] typeName, char[][] packageName) {
      char[][] compoundName = new char[packageName.length + 1][];

      for(int i = 0; i < packageName.length; ++i) {
         compoundName[i] = packageName[i];
      }

      compoundName[packageName.length] = typeName;
      return super.findType(compoundName);
   }

   public NameEnvironmentAnswer findType(char[][] compoundName) {
      return super.findType(compoundName);
   }
}
