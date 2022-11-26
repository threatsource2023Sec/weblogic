package com.bea.xml_.impl.jam.internal.elements;

import com.bea.xml_.impl.jam.JClass;
import com.bea.xml_.impl.jam.JPackage;
import com.bea.xml_.impl.jam.mutable.MClass;
import com.bea.xml_.impl.jam.mutable.MPackage;
import com.bea.xml_.impl.jam.visitor.JVisitor;
import com.bea.xml_.impl.jam.visitor.MVisitor;
import java.util.ArrayList;
import java.util.List;

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
