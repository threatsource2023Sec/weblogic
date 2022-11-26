package com.bea.util.annogen.override.internal.jam;

import com.bea.util.annogen.override.ElementId;
import com.bea.util.annogen.override.JamElementIdPool;
import com.bea.util.annogen.override.internal.ElementIdImpl;
import com.bea.util.annogen.view.internal.IndigenousAnnoExtractor;
import com.bea.util.annogen.view.internal.jam.JamIAE;
import com.bea.util.annogen.view.internal.javadoc.JavadocAnnogenTigerDelegate;
import com.bea.util.annogen.view.internal.reflect.ReflectAnnogenTigerDelegate;
import com.bea.util.jam.JAnnotatedElement;
import com.bea.util.jam.JAnnotation;
import com.bea.util.jam.JClass;
import com.bea.util.jam.JComment;
import com.bea.util.jam.JConstructor;
import com.bea.util.jam.JField;
import com.bea.util.jam.JInvokable;
import com.bea.util.jam.JMethod;
import com.bea.util.jam.JPackage;
import com.bea.util.jam.JParameter;
import com.bea.util.jam.provider.JamLogger;
import com.bea.util.jam.visitor.JVisitor;

public class JamElementIdPoolImpl implements JamElementIdPool {
   private ReflectAnnogenTigerDelegate mRTiger;
   private JavadocAnnogenTigerDelegate mJTiger;
   private JamLogger mLogger;

   public JamElementIdPoolImpl(JamLogger logger) {
      this.mRTiger = ReflectAnnogenTigerDelegate.create(logger);
      this.mJTiger = JavadocAnnogenTigerDelegate.create(logger);
      this.mLogger = logger;
   }

   public ElementId getIdFor(final JAnnotatedElement element) {
      final IndigenousAnnoExtractor iae = JamIAE.create(element, this.mLogger, this.mRTiger, this.mJTiger);

      class MyVisitor extends JVisitor {
         public ElementId result = null;

         public void visit(JPackage pkg) {
            this.result = ElementIdImpl.forPackage(iae, element.getQualifiedName());
         }

         public void visit(JClass clazz) {
            this.result = ElementIdImpl.forClass(iae, element.getQualifiedName());
         }

         public void visit(JConstructor ctor) {
            this.result = ElementIdImpl.forConstructor(iae, ctor.getContainingClass().getQualifiedName(), JamElementIdPoolImpl.this.getSignature(ctor));
         }

         public void visit(JField field) {
            this.result = ElementIdImpl.forField(iae, field.getContainingClass().getQualifiedName(), field.getSimpleName());
         }

         public void visit(JMethod method) {
            this.result = ElementIdImpl.forMethod(iae, method.getContainingClass().getQualifiedName(), method.getSimpleName(), JamElementIdPoolImpl.this.getSignature(method));
         }

         public void visit(JParameter param) {
            JInvokable parent = (JInvokable)param.getParent();
            this.result = ElementIdImpl.forParameter(iae, parent.getContainingClass().getQualifiedName(), parent.getSimpleName(), JamElementIdPoolImpl.this.getSignature(parent), JamIAE.getParameterNumber(param));
         }

         public void visit(JAnnotation ann) {
            throw new IllegalStateException("annotations not supported here");
         }

         public void visit(JComment comment) {
            throw new IllegalStateException("comments not supported here");
         }
      }

      MyVisitor myVisitor = new MyVisitor();
      element.accept(myVisitor);
      if (myVisitor.result == null) {
         throw new IllegalStateException("no result");
      } else {
         return myVisitor.result;
      }
   }

   private String[] getSignature(JInvokable ji) {
      if (ji == null) {
         throw new IllegalArgumentException();
      } else {
         JParameter[] params = ji.getParameters();
         if (params != null && params.length != 0) {
            String[] out = new String[params.length];

            for(int i = 0; i < out.length; ++i) {
               out[i] = params[i].getType().getQualifiedName();
            }

            return out;
         } else {
            return null;
         }
      }
   }
}
