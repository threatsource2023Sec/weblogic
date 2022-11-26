package com.bea.staxb.buildtime.internal.bts;

import com.bea.util.jam.JClass;
import com.bea.xml.SchemaType;
import java.io.Serializable;

public final class BindingTypeName implements Serializable {
   private final JavaTypeName jName;
   private final XmlTypeName xName;
   private static final long serialVersionUID = 1L;

   public static BindingTypeName forPair(JavaTypeName jName, XmlTypeName xName) {
      return new BindingTypeName(jName, xName);
   }

   public static BindingTypeName forTypes(JClass jClass, SchemaType sType) {
      return forPair(JavaTypeName.forJClass(jClass), XmlTypeName.forSchemaType(sType));
   }

   private BindingTypeName(JavaTypeName jName, XmlTypeName xName) {
      if (jName == null) {
         throw new IllegalArgumentException("null jName");
      } else if (xName == null) {
         throw new IllegalArgumentException("null xName");
      } else {
         this.jName = jName;
         this.xName = xName;
      }
   }

   public JavaTypeName getJavaName() {
      return this.jName;
   }

   public XmlTypeName getXmlName() {
      return this.xName;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof BindingTypeName)) {
         return false;
      } else {
         BindingTypeName bindingTypeName = (BindingTypeName)o;
         if (!this.jName.equals(bindingTypeName.jName)) {
            return false;
         } else {
            return this.xName.equals(bindingTypeName.xName);
         }
      }
   }

   public int hashCode() {
      int result = this.jName.hashCode();
      result = 29 * result + this.xName.hashCode();
      return result;
   }

   public String toString() {
      return "BindingTypeName[" + this.jName + ";" + this.xName + "]";
   }
}
