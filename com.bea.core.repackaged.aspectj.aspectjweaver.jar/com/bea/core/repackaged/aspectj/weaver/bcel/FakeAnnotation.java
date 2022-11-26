package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.AnnotationGen;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.NameValuePair;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.ObjectType;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class FakeAnnotation extends AnnotationGen {
   private String name;
   private String sig;
   private boolean isRuntimeVisible;

   public FakeAnnotation(String name, String sig, boolean isRuntimeVisible) {
      super((ObjectType)null, (List)null, true, (ConstantPool)null);
      this.name = name;
      this.sig = sig;
      this.isRuntimeVisible = isRuntimeVisible;
   }

   public String getTypeName() {
      return this.name;
   }

   public String getTypeSignature() {
      return this.sig;
   }

   public void addElementNameValuePair(NameValuePair evp) {
   }

   public void dump(DataOutputStream dos) throws IOException {
   }

   public int getTypeIndex() {
      return 0;
   }

   public List getValues() {
      return null;
   }

   public boolean isRuntimeVisible() {
      return this.isRuntimeVisible;
   }

   protected void setIsRuntimeVisible(boolean b) {
   }

   public String toShortString() {
      return "@" + this.name;
   }

   public String toString() {
      return this.name;
   }
}
