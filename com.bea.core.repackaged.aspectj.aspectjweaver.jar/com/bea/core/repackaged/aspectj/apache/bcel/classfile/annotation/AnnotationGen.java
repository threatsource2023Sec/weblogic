package com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantUtf8;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Utility;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.ObjectType;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class AnnotationGen {
   public static final AnnotationGen[] NO_ANNOTATIONS = new AnnotationGen[0];
   private int typeIndex;
   private List pairs = Collections.emptyList();
   private ConstantPool cpool;
   private boolean isRuntimeVisible = false;

   public AnnotationGen(AnnotationGen a, ConstantPool cpool, boolean copyPoolEntries) {
      this.cpool = cpool;
      if (copyPoolEntries) {
         this.typeIndex = cpool.addUtf8(a.getTypeSignature());
      } else {
         this.typeIndex = a.getTypeIndex();
      }

      this.isRuntimeVisible = a.isRuntimeVisible();
      this.pairs = this.copyValues(a.getValues(), cpool, copyPoolEntries);
   }

   private List copyValues(List in, ConstantPool cpool, boolean copyPoolEntries) {
      List out = new ArrayList();
      Iterator var5 = in.iterator();

      while(var5.hasNext()) {
         NameValuePair nvp = (NameValuePair)var5.next();
         out.add(new NameValuePair(nvp, cpool, copyPoolEntries));
      }

      return out;
   }

   private AnnotationGen(ConstantPool cpool) {
      this.cpool = cpool;
   }

   public AnnotationGen(ObjectType type, List pairs, boolean runtimeVisible, ConstantPool cpool) {
      this.cpool = cpool;
      if (type != null) {
         this.typeIndex = cpool.addUtf8(type.getSignature());
      }

      this.pairs = pairs;
      this.isRuntimeVisible = runtimeVisible;
   }

   public static AnnotationGen read(DataInputStream dis, ConstantPool cpool, boolean b) throws IOException {
      AnnotationGen a = new AnnotationGen(cpool);
      a.typeIndex = dis.readUnsignedShort();
      int elemValuePairCount = dis.readUnsignedShort();

      for(int i = 0; i < elemValuePairCount; ++i) {
         int nidx = dis.readUnsignedShort();
         a.addElementNameValuePair(new NameValuePair(nidx, ElementValue.readElementValue(dis, cpool), cpool));
      }

      a.isRuntimeVisible(b);
      return a;
   }

   public void dump(DataOutputStream dos) throws IOException {
      dos.writeShort(this.typeIndex);
      dos.writeShort(this.pairs.size());

      for(int i = 0; i < this.pairs.size(); ++i) {
         NameValuePair envp = (NameValuePair)this.pairs.get(i);
         envp.dump(dos);
      }

   }

   public void addElementNameValuePair(NameValuePair evp) {
      if (this.pairs == Collections.EMPTY_LIST) {
         this.pairs = new ArrayList();
      }

      this.pairs.add(evp);
   }

   public int getTypeIndex() {
      return this.typeIndex;
   }

   public String getTypeSignature() {
      ConstantUtf8 utf8 = (ConstantUtf8)this.cpool.getConstant(this.typeIndex);
      return utf8.getValue();
   }

   public String getTypeName() {
      return Utility.signatureToString(this.getTypeSignature());
   }

   public List getValues() {
      return this.pairs;
   }

   public String toString() {
      StringBuffer s = new StringBuffer();
      s.append("AnnotationGen:[" + this.getTypeName() + " #" + this.pairs.size() + " {");

      for(int i = 0; i < this.pairs.size(); ++i) {
         s.append(this.pairs.get(i));
         if (i + 1 < this.pairs.size()) {
            s.append(",");
         }
      }

      s.append("}]");
      return s.toString();
   }

   public String toShortString() {
      StringBuffer s = new StringBuffer();
      s.append("@").append(this.getTypeName());
      if (this.pairs.size() != 0) {
         s.append("(");

         for(int i = 0; i < this.pairs.size(); ++i) {
            s.append(this.pairs.get(i));
            if (i + 1 < this.pairs.size()) {
               s.append(",");
            }
         }

         s.append(")");
      }

      return s.toString();
   }

   private void isRuntimeVisible(boolean b) {
      this.isRuntimeVisible = b;
   }

   public boolean isRuntimeVisible() {
      return this.isRuntimeVisible;
   }

   public boolean hasNameValuePair(String name, String value) {
      Iterator var3 = this.pairs.iterator();

      NameValuePair pair;
      do {
         if (!var3.hasNext()) {
            return false;
         }

         pair = (NameValuePair)var3.next();
      } while(!pair.getNameString().equals(name) || !pair.getValue().stringifyValue().equals(value));

      return true;
   }

   public boolean hasNamedValue(String name) {
      Iterator var2 = this.pairs.iterator();

      while(var2.hasNext()) {
         NameValuePair pair = (NameValuePair)var2.next();
         if (pair.getNameString().equals(name)) {
            return true;
         }
      }

      return false;
   }
}
