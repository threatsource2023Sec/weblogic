package com.bea.util.jam.internal.javadoc;

import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.SourcePosition;

public class JavadocParsingException extends Exception {
   private AnnotationDesc mBad;
   private SourcePosition mSp;

   public JavadocParsingException(AnnotationDesc desc, SourcePosition sp, Throwable nested) {
      super("Parsing failure in " + sp.file() + " at line " + sp.line() + ".  Most likely, an annotation is declared whose type has not been imported.");
      this.mBad = desc;
      this.mSp = sp;
   }

   public SourcePosition getSourcePosition() {
      return this.mSp;
   }

   public AnnotationDesc getBadAnnotationDesc() {
      return this.mBad;
   }
}
