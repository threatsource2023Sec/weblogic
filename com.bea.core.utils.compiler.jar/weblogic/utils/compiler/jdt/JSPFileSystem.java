package weblogic.utils.compiler.jdt;

import com.bea.core.repackaged.jdt.internal.compiler.env.INameEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.env.NameEnvironmentAnswer;
import com.bea.core.repackaged.jdt.internal.compiler.util.SuffixConstants;

public class JSPFileSystem implements INameEnvironment, SuffixConstants {
   GeneratedClassesDirectory cp = null;
   INameEnvironment fs = null;

   public JSPFileSystem(GeneratedClassesDirectory cp, INameEnvironment fs) {
      this.cp = cp;
      this.fs = fs;
   }

   public void cleanup() {
      this.fs.cleanup();
      this.cp.cleanup();
   }

   public NameEnvironmentAnswer findType(char[] typeName, char[][] packageName) {
      NameEnvironmentAnswer answer = null;
      if (typeName != null) {
         answer = this.cp.findType(typeName, packageName);
      }

      if (answer != null) {
         return answer;
      } else {
         synchronized(this) {
            return this.fs.findType(typeName, packageName);
         }
      }
   }

   public NameEnvironmentAnswer findType(char[][] compoundName) {
      NameEnvironmentAnswer answer = null;
      if (compoundName != null) {
         answer = this.cp.findType(compoundName);
      }

      if (answer != null) {
         return answer;
      } else {
         synchronized(this) {
            return this.fs.findType(compoundName);
         }
      }
   }

   public boolean isPackage(char[][] compoundName, char[] packageName) {
      boolean isPackage = this.cp.isPackage(compoundName, packageName);
      if (isPackage) {
         return true;
      } else {
         synchronized(this) {
            return this.fs.isPackage(compoundName, packageName);
         }
      }
   }
}
