package org.apache.xmlbeans.impl.jam.internal.elements;

import java.io.StringWriter;
import java.lang.reflect.Modifier;
import org.apache.xmlbeans.impl.jam.JConstructor;
import org.apache.xmlbeans.impl.jam.JParameter;
import org.apache.xmlbeans.impl.jam.mutable.MConstructor;
import org.apache.xmlbeans.impl.jam.visitor.JVisitor;
import org.apache.xmlbeans.impl.jam.visitor.MVisitor;

public final class ConstructorImpl extends InvokableImpl implements MConstructor {
   ConstructorImpl(ClassImpl containingClass) {
      super(containingClass);
      this.setSimpleName(containingClass.getSimpleName());
   }

   public void accept(MVisitor visitor) {
      visitor.visit((MConstructor)this);
   }

   public void accept(JVisitor visitor) {
      visitor.visit((JConstructor)this);
   }

   public String getQualifiedName() {
      StringWriter sbuf = new StringWriter();
      sbuf.write(Modifier.toString(this.getModifiers()));
      sbuf.write(32);
      sbuf.write(this.getSimpleName());
      sbuf.write(40);
      JParameter[] params = this.getParameters();
      if (params != null && params.length > 0) {
         for(int i = 0; i < params.length; ++i) {
            sbuf.write(params[i].getType().getQualifiedName());
            if (i < params.length - 1) {
               sbuf.write(44);
            }
         }
      }

      sbuf.write(41);
      return sbuf.toString();
   }
}
