package com.bea.util.jam.internal.elements;

import com.bea.util.jam.JElement;
import com.bea.util.jam.JImport;
import com.bea.util.jam.JPackage;
import com.bea.util.jam.JProperty;
import com.bea.util.jam.JSourcePosition;
import com.bea.util.jam.JamClassLoader;
import com.bea.util.jam.internal.JamServiceContextImpl;
import com.bea.util.jam.mutable.MElement;
import com.bea.util.jam.mutable.MSourcePosition;
import com.bea.util.jam.provider.JamLogger;
import com.sun.javadoc.Doc;

public abstract class ElementImpl implements Comparable, MElement {
   public static final ElementImpl[] NO_NODE = new ElementImpl[0];
   public static final ClassImpl[] NO_CLASS = new ClassImpl[0];
   public static final FieldImpl[] NO_FIELD = new FieldImpl[0];
   public static final ConstructorImpl[] NO_CONSTRUCTOR = new ConstructorImpl[0];
   public static final MethodImpl[] NO_METHOD = new MethodImpl[0];
   public static final ParameterImpl[] NO_PARAMETER = new ParameterImpl[0];
   public static final JPackage[] NO_PACKAGE = new JPackage[0];
   public static final AnnotationImpl[] NO_ANNOTATION = new AnnotationImpl[0];
   public static final TagImpl[] NO_TAG = new TagImpl[0];
   public static final JImport[] NO_IMPORT = new ImportImpl[0];
   public static final CommentImpl[] NO_COMMENT = new CommentImpl[0];
   public static final JProperty[] NO_PROPERTY = new JProperty[0];
   private ElementContext mContext;
   protected String mSimpleName;
   private MSourcePosition mPosition = null;
   private Object mArtifact = null;
   private ElementImpl mParent;

   protected ElementImpl(ElementImpl parent) {
      if (parent == null) {
         throw new IllegalArgumentException("null ctx");
      } else if (parent == this) {
         throw new IllegalArgumentException("An element cannot be its own parent");
      } else {
         for(JElement check = parent.getParent(); check != null; check = check.getParent()) {
            if (check == this) {
               throw new IllegalArgumentException("cycle detected");
            }
         }

         this.mContext = parent.getContext();
         this.mParent = parent;
      }
   }

   protected ElementImpl(ElementContext ctx) {
      if (ctx == null) {
         throw new IllegalArgumentException("null ctx");
      } else {
         this.mContext = ctx;
      }
   }

   public final JElement getParent() {
      return this.mParent;
   }

   public String getSimpleName() {
      return this.mSimpleName;
   }

   public JSourcePosition getSourcePosition() {
      return this.mPosition;
   }

   public Object getArtifact() {
      return this.mArtifact;
   }

   public boolean isSourceAvailable() {
      return this.mArtifact != null && this.mArtifact instanceof Doc;
   }

   public void setSimpleName(String name) {
      if (name == null) {
         throw new IllegalArgumentException("null name");
      } else {
         this.mSimpleName = name.trim();
      }
   }

   public MSourcePosition createSourcePosition() {
      return this.mPosition = new SourcePositionImpl();
   }

   public void removeSourcePosition() {
      this.mPosition = null;
   }

   public MSourcePosition getMutableSourcePosition() {
      return this.mPosition;
   }

   public void setArtifact(Object artifact) {
      if (artifact == null) {
      }

      if (this.mArtifact != null) {
         throw new IllegalStateException("artifact already set");
      } else {
         this.mArtifact = artifact;
      }
   }

   public JamClassLoader getClassLoader() {
      return this.mContext.getClassLoader();
   }

   public static String defaultName(int count) {
      return "unnamed_" + count;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof ElementImpl)) {
         return false;
      } else {
         ElementImpl eElement = (ElementImpl)o;
         String qn = this.getQualifiedName();
         if (qn == null) {
            return false;
         } else {
            String oqn = eElement.getQualifiedName();
            return oqn == null ? false : qn.equals(oqn);
         }
      }
   }

   public int hashCode() {
      String qn = this.getQualifiedName();
      return qn == null ? 0 : qn.hashCode();
   }

   public ElementContext getContext() {
      return this.mContext;
   }

   public String toString() {
      return this.getQualifiedName();
   }

   protected JamLogger getLogger() {
      return ((JamServiceContextImpl)this.mContext).getLogger();
   }

   public int compareTo(Object o) {
      return !(o instanceof JElement) ? -1 : this.getQualifiedName().compareTo(((JElement)o).getQualifiedName());
   }
}
