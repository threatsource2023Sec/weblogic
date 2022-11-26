package com.bea.util.jam.internal.elements;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JParameter;
import com.bea.util.jam.internal.classrefs.DirectJClassRef;
import com.bea.util.jam.internal.classrefs.JClassRef;
import com.bea.util.jam.internal.classrefs.JClassRefContext;
import com.bea.util.jam.internal.classrefs.QualifiedJClassRef;
import com.bea.util.jam.internal.classrefs.UnqualifiedJClassRef;
import com.bea.util.jam.mutable.MInvokable;
import com.bea.util.jam.mutable.MParameter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public abstract class InvokableImpl extends MemberImpl implements MInvokable {
   private List mExceptionClassRefs = null;
   private List mParameters = null;

   protected InvokableImpl(ClassImpl containingClass) {
      super((ElementImpl)containingClass);
   }

   public void addException(JClass exceptionClass) {
      if (exceptionClass == null) {
         throw new IllegalArgumentException("null exception class");
      } else {
         if (this.mExceptionClassRefs == null) {
            this.mExceptionClassRefs = new ArrayList();
         }

         this.mExceptionClassRefs.add(DirectJClassRef.create(exceptionClass));
      }
   }

   public void addException(String qcname) {
      if (qcname == null) {
         throw new IllegalArgumentException("null qcname");
      } else {
         if (this.mExceptionClassRefs == null) {
            this.mExceptionClassRefs = new ArrayList();
         }

         this.mExceptionClassRefs.add(QualifiedJClassRef.create(qcname, (JClassRefContext)((ClassImpl)this.getContainingClass())));
      }
   }

   public void addUnqualifiedException(String ucname) {
      if (ucname == null) {
         throw new IllegalArgumentException("null qcname");
      } else {
         if (this.mExceptionClassRefs == null) {
            this.mExceptionClassRefs = new ArrayList();
         }

         this.mExceptionClassRefs.add(UnqualifiedJClassRef.create(ucname, (ClassImpl)this.getContainingClass()));
      }
   }

   public void removeException(String exceptionClassName) {
      if (exceptionClassName == null) {
         throw new IllegalArgumentException("null classname");
      } else {
         if (this.mExceptionClassRefs != null) {
            this.mExceptionClassRefs.remove(exceptionClassName);
         }

      }
   }

   public void removeException(JClass exceptionClass) {
      this.removeException(exceptionClass.getQualifiedName());
   }

   public MParameter addNewParameter() {
      if (this.mParameters == null) {
         this.mParameters = new ArrayList();
      }

      MParameter param = new ParameterImpl(defaultName(this.mParameters.size()), this, "java.lang.Object");
      this.mParameters.add(param);
      return param;
   }

   public void removeParameter(MParameter parameter) {
      if (this.mParameters != null) {
         this.mParameters.remove(parameter);
      }

   }

   public MParameter[] getMutableParameters() {
      if (this.mParameters != null && this.mParameters.size() != 0) {
         MParameter[] out = new MParameter[this.mParameters.size()];
         this.mParameters.toArray(out);
         return out;
      } else {
         return new MParameter[0];
      }
   }

   public JParameter[] getParameters() {
      return this.getMutableParameters();
   }

   public JClass[] getExceptionTypes() {
      if (this.mExceptionClassRefs != null && this.mExceptionClassRefs.size() != 0) {
         JClass[] out = new JClass[this.mExceptionClassRefs.size()];

         for(int i = 0; i < out.length; ++i) {
            out[i] = ((JClassRef)this.mExceptionClassRefs.get(i)).getRefClass();
         }

         return out;
      } else {
         return new JClass[0];
      }
   }

   public String getQualifiedName() {
      StringWriter out = new StringWriter();
      out.write(this.getContainingClass().getQualifiedName());
      out.write(46);
      out.write(this.getSimpleName());
      out.write(40);
      JParameter[] params = this.getParameters();

      for(int i = 0; i < params.length; ++i) {
         out.write(params[i].getType().getQualifiedName());
         if (i < params.length - 1) {
            out.write(", ");
         }
      }

      out.write(41);
      return out.toString();
   }

   public void setUnqualifiedThrows(List classnames) {
      if (classnames != null && classnames.size() != 0) {
         this.mExceptionClassRefs = new ArrayList(classnames.size());

         for(int i = 0; i < classnames.size(); ++i) {
            this.mExceptionClassRefs.add(UnqualifiedJClassRef.create((String)classnames.get(i), (ClassImpl)this.getContainingClass()));
         }

      } else {
         this.mExceptionClassRefs = null;
      }
   }
}
