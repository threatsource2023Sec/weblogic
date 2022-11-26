package com.bea.core.repackaged.jdt.internal.compiler.env;

import com.bea.core.repackaged.jdt.internal.compiler.lookup.ModuleBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import java.util.Arrays;

public class NameEnvironmentAnswer {
   IBinaryType binaryType;
   ICompilationUnit compilationUnit;
   ISourceType[] sourceTypes;
   ReferenceBinding binding;
   AccessRestriction accessRestriction;
   char[] moduleName;
   public ModuleBinding moduleBinding;
   String externalAnnotationPath;

   public NameEnvironmentAnswer(IBinaryType binaryType, AccessRestriction accessRestriction) {
      this(binaryType, accessRestriction, binaryType.getModule());
   }

   public NameEnvironmentAnswer(IBinaryType binaryType, AccessRestriction accessRestriction, char[] module) {
      this.binaryType = binaryType;
      this.accessRestriction = accessRestriction;
      this.moduleName = module;
   }

   public NameEnvironmentAnswer(ICompilationUnit compilationUnit, AccessRestriction accessRestriction) {
      this(compilationUnit, accessRestriction, compilationUnit.getModuleName());
   }

   public NameEnvironmentAnswer(ICompilationUnit compilationUnit, AccessRestriction accessRestriction, char[] module) {
      this.compilationUnit = compilationUnit;
      this.accessRestriction = accessRestriction;
      this.moduleName = module;
   }

   public NameEnvironmentAnswer(ISourceType[] sourceTypes, AccessRestriction accessRestriction, String externalAnnotationPath, char[] module) {
      this.sourceTypes = sourceTypes;
      this.accessRestriction = accessRestriction;
      this.externalAnnotationPath = externalAnnotationPath;
      this.moduleName = module;
   }

   public NameEnvironmentAnswer(ReferenceBinding binding, ModuleBinding module) {
      this.binding = binding;
      this.moduleBinding = module;
   }

   public String toString() {
      String baseString = "";
      if (this.binaryType != null) {
         char[] fileNameChars = this.binaryType.getFileName();
         String fileName = fileNameChars == null ? "" : new String(fileNameChars);
         baseString = "IBinaryType " + fileName;
      }

      if (this.compilationUnit != null) {
         baseString = "ICompilationUnit " + this.compilationUnit.toString();
      }

      if (this.sourceTypes != null) {
         baseString = Arrays.toString(this.sourceTypes);
      }

      if (this.accessRestriction != null) {
         baseString = baseString + " " + this.accessRestriction.toString();
      }

      if (this.externalAnnotationPath != null) {
         baseString = baseString + " extPath=" + this.externalAnnotationPath.toString();
      }

      if (this.moduleName != null) {
         baseString = baseString + " module=" + this.moduleName;
      }

      return baseString;
   }

   public AccessRestriction getAccessRestriction() {
      return this.accessRestriction;
   }

   public void setBinaryType(IBinaryType newType) {
      this.binaryType = newType;
   }

   public IBinaryType getBinaryType() {
      return this.binaryType;
   }

   public ICompilationUnit getCompilationUnit() {
      return this.compilationUnit;
   }

   public String getExternalAnnotationPath() {
      return this.externalAnnotationPath;
   }

   public ISourceType[] getSourceTypes() {
      return this.sourceTypes;
   }

   public ReferenceBinding getResolvedBinding() {
      return this.binding;
   }

   public boolean isBinaryType() {
      return this.binaryType != null;
   }

   public boolean isCompilationUnit() {
      return this.compilationUnit != null;
   }

   public boolean isSourceType() {
      return this.sourceTypes != null;
   }

   public boolean isResolvedBinding() {
      return this.binding != null;
   }

   public boolean ignoreIfBetter() {
      return this.accessRestriction != null && this.accessRestriction.ignoreIfBetter();
   }

   public char[] moduleName() {
      return this.moduleName;
   }

   public boolean isBetter(NameEnvironmentAnswer otherAnswer) {
      if (otherAnswer == null) {
         return true;
      } else if (this.accessRestriction == null) {
         return true;
      } else {
         return otherAnswer.accessRestriction != null && this.accessRestriction.getProblemId() < otherAnswer.accessRestriction.getProblemId();
      }
   }
}
