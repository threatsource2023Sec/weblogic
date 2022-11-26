package com.bea.core.repackaged.aspectj.apache.bcel.generic;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Attribute;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Modifiers;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Utility;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.AnnotationGen;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class FieldGenOrMethodGen extends Modifiers {
   protected String name;
   protected Type type;
   protected ConstantPool cp;
   private ArrayList attributeList = new ArrayList();
   protected ArrayList annotationList = new ArrayList();

   protected FieldGenOrMethodGen() {
   }

   public void setType(Type type) {
      this.type = type;
   }

   public Type getType() {
      return this.type;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public ConstantPool getConstantPool() {
      return this.cp;
   }

   public void setConstantPool(ConstantPool cp) {
      this.cp = cp;
   }

   public void addAttribute(Attribute a) {
      this.attributeList.add(a);
   }

   public void removeAttribute(Attribute a) {
      this.attributeList.remove(a);
   }

   public void removeAttributes() {
      this.attributeList.clear();
   }

   public List getAnnotations() {
      return this.annotationList;
   }

   public void addAnnotation(AnnotationGen ag) {
      this.annotationList.add(ag);
   }

   public void removeAnnotation(AnnotationGen ag) {
      this.annotationList.remove(ag);
   }

   public void removeAnnotations() {
      this.annotationList.clear();
   }

   public List getAttributes() {
      return this.attributeList;
   }

   public Attribute[] getAttributesImmutable() {
      Attribute[] attributes = new Attribute[this.attributeList.size()];
      this.attributeList.toArray(attributes);
      return attributes;
   }

   protected void addAnnotationsAsAttribute(ConstantPool cp) {
      Collection attrs = Utility.getAnnotationAttributes(cp, this.annotationList);
      if (attrs != null) {
         Iterator var3 = attrs.iterator();

         while(var3.hasNext()) {
            Attribute attr = (Attribute)var3.next();
            this.addAttribute(attr);
         }
      }

   }

   public abstract String getSignature();
}
