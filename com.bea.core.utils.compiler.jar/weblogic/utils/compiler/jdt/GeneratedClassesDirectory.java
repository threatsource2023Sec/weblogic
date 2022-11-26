package weblogic.utils.compiler.jdt;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.classfmt.ClassFileReader;
import com.bea.core.repackaged.jdt.internal.compiler.env.AccessRestriction;
import com.bea.core.repackaged.jdt.internal.compiler.env.INameEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.env.NameEnvironmentAnswer;
import com.bea.core.repackaged.jdt.internal.compiler.util.SuffixConstants;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.utils.StringUtils;

public class GeneratedClassesDirectory implements INameEnvironment, SuffixConstants {
   private String path;
   private char[][] packagePrefix;
   private Map directoryCache;
   private String[] missingPackageHolder = new String[0];

   public GeneratedClassesDirectory(File directory, String prefix) {
      this.path = directory.getAbsolutePath();
      if (!this.path.endsWith(File.separator)) {
         this.path = this.path + File.separator;
      }

      this.directoryCache = new ConcurrentHashMap();
      String[] units = StringUtils.splitCompletely(prefix, ".");
      this.packagePrefix = new char[units.length][];

      for(int i = 0; i < this.packagePrefix.length; ++i) {
         this.packagePrefix[i] = units[i].toCharArray();
      }

   }

   String[] directoryList(String qualifiedPackageName) {
      String[] dirList = (String[])this.directoryCache.get(qualifiedPackageName);
      return dirList != null ? dirList : null;
   }

   public synchronized void addFile(String qualifiedPackageName, String file) {
      String[] dirList = (String[])this.directoryCache.get(qualifiedPackageName);
      if (dirList != this.missingPackageHolder && dirList != null) {
         String[] tmpList = new String[dirList.length + 1];
         System.arraycopy(dirList, 0, tmpList, 0, dirList.length);
         tmpList[dirList.length] = file;
         this.directoryCache.put(qualifiedPackageName, tmpList);
      } else {
         this.directoryCache.put(qualifiedPackageName, new String[]{file});
      }

      int index;
      while((index = qualifiedPackageName.lastIndexOf(File.separatorChar)) > 0) {
         qualifiedPackageName = qualifiedPackageName.substring(0, index);
         if (this.directoryCache.get(qualifiedPackageName) != null) {
            break;
         }

         this.directoryCache.put(qualifiedPackageName, this.missingPackageHolder);
      }

   }

   public boolean isPackage(char[][] compoundName, char[] packageName) {
      if (compoundName == null && !this.isJspServletPackage(packageName)) {
         return false;
      } else if (compoundName != null && !this.isJspServletPackage(compoundName)) {
         return false;
      } else {
         String qualifiedPackageName = new String(CharOperation.concatWith(compoundName, packageName, '/'));
         String qp2 = File.separatorChar == '/' ? qualifiedPackageName : qualifiedPackageName.replace('/', File.separatorChar);
         return this.isPackage(qp2);
      }
   }

   public void cleanup() {
      this.directoryCache = new ConcurrentHashMap();
   }

   public String toString() {
      return "JspServletDir: " + this.path;
   }

   public NameEnvironmentAnswer findType(char[] typeName, char[][] packageName) {
      if (packageName != null && packageName.length != 0) {
         if (!this.isJspServletPackage(packageName)) {
            return null;
         } else {
            String qualifiedPackageName = "";
            StringBuilder buffer = new StringBuilder();

            for(int i = 0; i < packageName.length - 1; ++i) {
               buffer.append(packageName[i]);
               buffer.append(File.separatorChar);
            }

            buffer.append(packageName[packageName.length - 1]);
            qualifiedPackageName = buffer.toString();
            buffer.append(File.separatorChar);
            buffer.append(typeName).append(".class");
            String qualifiedBinaryFileName = buffer.toString();
            return this.findType(typeName, qualifiedPackageName, qualifiedBinaryFileName);
         }
      } else {
         return null;
      }
   }

   public NameEnvironmentAnswer findType(char[][] compoundName) {
      if (!this.isJspServletPackage(compoundName)) {
         return null;
      } else {
         String qualifiedPackageName = "";
         StringBuilder buffer = new StringBuilder();
         if (compoundName.length > 1) {
            for(int i = 0; i < compoundName.length - 3; ++i) {
               buffer.append(compoundName[i]);
               buffer.append(File.separatorChar);
            }

            buffer.append(compoundName[compoundName.length - 2]);
            qualifiedPackageName = buffer.toString();
            buffer.append(File.separatorChar);
         }

         buffer.append(compoundName[compoundName.length - 1]).append(".class");
         String qualifiedBinaryFileName = buffer.toString();
         return this.findType(compoundName[compoundName.length - 1], qualifiedPackageName, qualifiedBinaryFileName);
      }
   }

   private boolean isJspServletPackage(char[][] packageName) {
      if (packageName.length < this.packagePrefix.length) {
         return false;
      } else {
         for(int i = 0; i < this.packagePrefix.length; ++i) {
            if (!CharOperation.equals(packageName[i], this.packagePrefix[i])) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean isJspServletPackage(char[] packageName) {
      return this.packagePrefix.length != 1 ? false : CharOperation.equals(packageName, this.packagePrefix[0]);
   }

   private NameEnvironmentAnswer findType(char[] typeName, String qualifiedPackageName, String qualifiedBinaryFileName) {
      if (!this.isPackage(qualifiedPackageName)) {
         return null;
      } else {
         String fileName = new String(typeName);
         boolean binaryExists = this.doesFileExist(fileName + ".class", qualifiedPackageName);
         if (binaryExists) {
            try {
               ClassFileReader reader = ClassFileReader.read(this.path + qualifiedBinaryFileName);
               if (reader != null) {
                  return new NameEnvironmentAnswer(reader, (AccessRestriction)null);
               }
            } catch (Exception var7) {
            }
         }

         return null;
      }
   }

   private boolean isPackage(String qualifiedPackageName) {
      return this.directoryList(qualifiedPackageName) != null;
   }

   private boolean doesFileExist(String fileName, String qualifiedPackageName) {
      String[] dirList = this.directoryList(qualifiedPackageName);
      if (dirList == null) {
         return false;
      } else {
         int i = dirList.length;

         do {
            --i;
            if (i < 0) {
               return false;
            }
         } while(!fileName.equals(dirList[i]));

         return true;
      }
   }
}
