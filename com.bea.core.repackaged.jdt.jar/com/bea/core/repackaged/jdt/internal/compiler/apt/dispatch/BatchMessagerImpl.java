package com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch;

import com.bea.core.repackaged.jdt.core.compiler.CategorizedProblem;
import com.bea.core.repackaged.jdt.internal.compiler.batch.Main;
import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import javax.tools.Diagnostic.Kind;

public class BatchMessagerImpl extends BaseMessagerImpl implements Messager {
   private final Main _compiler;
   private final BaseProcessingEnvImpl _processingEnv;

   public BatchMessagerImpl(BaseProcessingEnvImpl processingEnv, Main compiler) {
      this._compiler = compiler;
      this._processingEnv = processingEnv;
   }

   public void printMessage(Diagnostic.Kind kind, CharSequence msg) {
      this.printMessage(kind, msg, (Element)null, (AnnotationMirror)null, (AnnotationValue)null);
   }

   public void printMessage(Diagnostic.Kind kind, CharSequence msg, Element e) {
      this.printMessage(kind, msg, e, (AnnotationMirror)null, (AnnotationValue)null);
   }

   public void printMessage(Diagnostic.Kind kind, CharSequence msg, Element e, AnnotationMirror a) {
      this.printMessage(kind, msg, e, a, (AnnotationValue)null);
   }

   public void printMessage(Diagnostic.Kind kind, CharSequence msg, Element e, AnnotationMirror a, AnnotationValue v) {
      if (kind == Kind.ERROR) {
         this._processingEnv.setErrorRaised(true);
      }

      CategorizedProblem problem = createProblem(kind, msg, e, a, v);
      if (problem != null) {
         this._compiler.addExtraProblems(problem);
      }

   }
}
