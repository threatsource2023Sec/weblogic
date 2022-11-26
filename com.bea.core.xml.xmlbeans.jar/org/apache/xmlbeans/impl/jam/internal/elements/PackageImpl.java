package org.apache.xmlbeans.impl.jam.internal.elements;

import java.util.ArrayList;
import java.util.List;
import org.apache.xmlbeans.impl.jam.JClass;
import org.apache.xmlbeans.impl.jam.JPackage;
import org.apache.xmlbeans.impl.jam.mutable.MClass;
import org.apache.xmlbeans.impl.jam.mutable.MPackage;
import org.apache.xmlbeans.impl.jam.visitor.JVisitor;
import org.apache.xmlbeans.impl.jam.visitor.MVisitor;

public class PackageImpl extends AnnotatedElementImpl implements MPackage {
   private List mRootClasses = new ArrayList();
   private String mName;

   public PackageImpl(ElementContext ctx, String name) {
      super(ctx);
      this.mName = name;
      int lastDot = this.mName.lastIndexOf(46);
      this.setSimpleName(lastDot == -1 ? this.mName : this.mName.substring(lastDot + 1));
   }

   public String getQualifiedName() {
      return this.mName;
   }

   public void accept(MVisitor visitor) {
      visitor.visit((MPackage)this);
   }

   public void accept(JVisitor visitor) {
      visitor.visit((JPackage)this);
   }

   public JClass[] getClasses() {
      JClass[] out = new JClass[this.mRootClasses.size()];
      this.mRootClasses.toArray(out);
      return out;
   }

   public MClass[] getMutableClasses() {
      MClass[] out = new MClass[this.mRootClasses.size()];
      this.mRootClasses.toArray(out);
      return out;
   }
}
