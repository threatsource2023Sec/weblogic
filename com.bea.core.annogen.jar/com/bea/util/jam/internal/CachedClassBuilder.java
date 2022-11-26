package com.bea.util.jam.internal;

import com.bea.util.jam.mutable.MClass;
import com.bea.util.jam.provider.JamClassBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.tools.ant.taskdefs.Javac;

public class CachedClassBuilder extends JamClassBuilder {
   private Map mQcname2jclass = null;
   private List mClassNames = new ArrayList();

   public MClass build(String packageName, String className) {
      return this.build((Javac)null, packageName, className);
   }

   public MClass build(Javac javacTask, String packageName, String className) {
      if (this.mQcname2jclass == null) {
         return null;
      } else {
         if (packageName.trim().length() > 0) {
            className = packageName + '.' + className;
         }

         return (MClass)this.mQcname2jclass.get(className);
      }
   }

   public MClass createClassToBuild(String packageName, String className, String[] importSpecs) {
      String qualifiedName;
      if (packageName.trim().length() > 0) {
         qualifiedName = packageName + '.' + className;
      } else {
         qualifiedName = className;
      }

      MClass out;
      if (this.mQcname2jclass != null) {
         out = (MClass)this.mQcname2jclass.get(qualifiedName);
         if (out != null) {
            return out;
         }
      } else {
         this.mQcname2jclass = new HashMap();
      }

      out = super.createClassToBuild(packageName, className, importSpecs);
      this.mQcname2jclass.put(qualifiedName, out);
      this.mClassNames.add(qualifiedName);
      return out;
   }

   public String[] getClassNames() {
      String[] out = new String[this.mClassNames.size()];
      this.mClassNames.toArray(out);
      return out;
   }
}
