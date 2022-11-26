package com.bea.util.jam.internal.elements;

import com.bea.util.jam.JAnnotation;
import com.bea.util.jam.JAnnotationValue;
import com.bea.util.jam.JComment;
import com.bea.util.jam.annotation.DefaultAnnotationProxy;
import com.bea.util.jam.mutable.MAnnotatedElement;
import com.bea.util.jam.mutable.MAnnotation;
import com.bea.util.jam.mutable.MComment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AnnotatedElementImpl extends ElementImpl implements MAnnotatedElement {
   private Map mName2Annotation = null;
   private MComment mComment = null;
   private List mAllAnnotations = null;

   protected AnnotatedElementImpl(ElementContext ctx) {
      super(ctx);
   }

   protected AnnotatedElementImpl(ElementImpl parent) {
      super(parent);
   }

   public JAnnotation[] getAnnotations() {
      return this.getMutableAnnotations();
   }

   public JAnnotation getAnnotation(Class jsr175type) {
      return this.getMutableAnnotation(jsr175type.getName());
   }

   public JAnnotation getAnnotation(String named) {
      return this.getMutableAnnotation(named);
   }

   public JAnnotationValue getAnnotationValue(String valueId) {
      if (this.mName2Annotation == null) {
         return null;
      } else {
         valueId = valueId.trim();
         int delim = valueId.indexOf(64);
         JAnnotation ann;
         if (delim != -1 && delim != valueId.length() - 1) {
            ann = this.getAnnotation(valueId.substring(0, delim));
            return ann == null ? null : ann.getValue(valueId.substring(delim + 1));
         } else {
            ann = this.getAnnotation(valueId);
            return ann == null ? null : ann.getValue("value");
         }
      }
   }

   public JComment getComment() {
      return this.getMutableComment();
   }

   /** @deprecated */
   @Deprecated
   public JAnnotation[] getAllJavadocTags() {
      if (this.mAllAnnotations == null) {
         return NO_ANNOTATION;
      } else {
         JAnnotation[] out = new JAnnotation[this.mAllAnnotations.size()];
         this.mAllAnnotations.toArray(out);
         return out;
      }
   }

   public void removeAnnotation(MAnnotation ann) {
      if (this.mName2Annotation != null) {
         this.mName2Annotation.values().remove(ann);
      }

   }

   public MAnnotation[] getMutableAnnotations() {
      if (this.mName2Annotation == null) {
         return new MAnnotation[0];
      } else {
         MAnnotation[] out = new MAnnotation[this.mName2Annotation.values().size()];
         this.mName2Annotation.values().toArray(out);
         return out;
      }
   }

   public MAnnotation getMutableAnnotation(String named) {
      if (this.mName2Annotation == null) {
         return null;
      } else {
         named = named.trim();
         return (MAnnotation)this.mName2Annotation.get(named);
      }
   }

   public MAnnotation findOrCreateAnnotation(String annotationName) {
      MAnnotation ann = this.getMutableAnnotation(annotationName);
      if (ann != null) {
         return ann;
      } else {
         DefaultAnnotationProxy proxy = this.getContext().createAnnotationProxy(annotationName);
         MAnnotation ann = new AnnotationImpl(this.getContext(), proxy, annotationName);
         if (this.mName2Annotation == null) {
            this.mName2Annotation = new HashMap();
         }

         this.mName2Annotation.put(ann.getQualifiedName(), ann);
         return ann;
      }
   }

   public MAnnotation addLiteralAnnotation(String annName) {
      if (annName == null) {
         throw new IllegalArgumentException("null tagname");
      } else {
         annName = annName.trim();
         DefaultAnnotationProxy proxy = this.getContext().createAnnotationProxy(annName);
         MAnnotation ann = new AnnotationImpl(this.getContext(), proxy, annName);
         if (this.mAllAnnotations == null) {
            this.mAllAnnotations = new ArrayList();
         }

         this.mAllAnnotations.add(ann);
         if (this.getMutableAnnotation(annName) == null) {
            if (this.mName2Annotation == null) {
               this.mName2Annotation = new HashMap();
            }

            this.mName2Annotation.put(annName, ann);
         }

         return ann;
      }
   }

   public MComment getMutableComment() {
      return this.mComment;
   }

   public MComment createComment() {
      return this.mComment = new CommentImpl(this);
   }

   public void removeComment() {
      this.mComment = null;
   }

   protected void addAnnotation(JAnnotation ann) {
      if (this.mName2Annotation == null) {
         this.mName2Annotation = new HashMap();
         this.mName2Annotation.put(ann.getQualifiedName(), ann);
      } else if (this.mName2Annotation.get(ann.getQualifiedName()) == null) {
         this.mName2Annotation.put(ann.getQualifiedName(), ann);
      }

      if (this.mAllAnnotations == null) {
         this.mAllAnnotations = new ArrayList();
      }

      this.mAllAnnotations.add(ann);
   }

   /** @deprecated */
   @Deprecated
   public MAnnotation addAnnotationForProxy(Class proxyClass, DefaultAnnotationProxy proxy) {
      String annotationName = proxyClass.getName();
      MAnnotation ann = this.getMutableAnnotation(annotationName);
      if (ann != null) {
         return ann;
      } else {
         MAnnotation ann = new AnnotationImpl(this.getContext(), proxy, annotationName);
         if (this.mName2Annotation == null) {
            this.mName2Annotation = new HashMap();
         }

         this.mName2Annotation.put(ann.getQualifiedName(), ann);
         return ann;
      }
   }
}
