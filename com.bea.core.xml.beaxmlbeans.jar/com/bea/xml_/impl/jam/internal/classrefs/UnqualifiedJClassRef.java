package com.bea.xml_.impl.jam.internal.classrefs;

import com.bea.xml_.impl.jam.JClass;
import java.io.StringWriter;

public class UnqualifiedJClassRef implements JClassRef {
   private static final boolean VERBOSE = false;
   private static final String PREFIX = "[UnqualifiedJClassRef]";
   private String mUnqualifiedClassname;
   private String mQualifiedClassname = null;
   private JClassRefContext mContext;

   public static JClassRef create(String qualifiedClassname, JClassRefContext ctx) {
      throw new IllegalStateException("Unqualified names currently disabled.");
   }

   private UnqualifiedJClassRef(String ucname, JClassRefContext ctx) {
      if (ctx == null) {
         throw new IllegalArgumentException("null ctx");
      } else if (ucname == null) {
         throw new IllegalArgumentException("null ucname");
      } else {
         this.mContext = ctx;
         this.mUnqualifiedClassname = ucname;
      }
   }

   public JClass getRefClass() {
      return this.mContext.getClassLoader().loadClass(this.getQualifiedName());
   }

   public String getQualifiedName() {
      if (this.mQualifiedClassname != null) {
         return this.mQualifiedClassname;
      } else {
         int arrayDimensions = 0;
         int bracket = this.mUnqualifiedClassname.indexOf(91);
         String candidateName;
         if (bracket != -1) {
            candidateName = this.mUnqualifiedClassname.substring(0, bracket);

            do {
               ++arrayDimensions;
               bracket = this.mUnqualifiedClassname.indexOf(91, bracket + 1);
            } while(bracket != -1);
         } else {
            candidateName = this.mUnqualifiedClassname;
         }

         String name = this.qualifyName(candidateName);
         if (name == null) {
            throw new IllegalStateException("unable to handle unqualified java type reference '" + candidateName + " [" + this.mUnqualifiedClassname + "]'. " + "This is still partially NYI.");
         } else {
            if (arrayDimensions > 0) {
               StringWriter out = new StringWriter();

               for(int i = 0; i < arrayDimensions; ++i) {
                  out.write(91);
               }

               out.write(76);
               out.write(name);
               out.write(59);
               this.mQualifiedClassname = out.toString();
            } else {
               this.mQualifiedClassname = name;
            }

            return this.mQualifiedClassname;
         }
      }
   }

   private String qualifyName(String ucname) {
      String out = null;
      if ((out = this.checkExplicitImport(ucname)) != null) {
         return out;
      } else if ((out = this.checkJavaLang(ucname)) != null) {
         return out;
      } else if ((out = this.checkSamePackage(ucname)) != null) {
         return out;
      } else {
         return (out = this.checkAlreadyQualified(ucname)) != null ? out : null;
      }
   }

   private String checkSamePackage(String ucname) {
      String name = this.mContext.getPackageName() + "." + ucname;
      JClass clazz = this.mContext.getClassLoader().loadClass(name);
      return clazz.isUnresolvedType() ? null : clazz.getQualifiedName();
   }

   private String checkJavaLang(String ucname) {
      String name = "java.lang." + ucname;
      JClass clazz = this.mContext.getClassLoader().loadClass(name);
      return clazz.isUnresolvedType() ? null : clazz.getQualifiedName();
   }

   private String checkAlreadyQualified(String ucname) {
      JClass clazz = this.mContext.getClassLoader().loadClass(ucname);
      return clazz.isUnresolvedType() ? null : clazz.getQualifiedName();
   }

   private String checkExplicitImport(String ucname) {
      String[] imports = this.mContext.getImportSpecs();

      for(int i = 0; i < imports.length; ++i) {
         String last = lastSegment(imports[i]);
         if (last.equals(ucname)) {
            return imports[i];
         }
      }

      return null;
   }

   private static String lastSegment(String s) {
      int lastDot = s.lastIndexOf(".");
      return lastDot == -1 ? s : s.substring(lastDot + 1);
   }

   private static String firstSegment(String s) {
      int lastDot = s.indexOf(".");
      return lastDot == -1 ? s : s.substring(0, lastDot);
   }
}
