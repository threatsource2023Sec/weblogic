package com.bea.staxb.buildtime;

import com.bea.util.jam.JClass;

public class ArrayNamespaceInfo {
   private JClass serviceClass;
   private JClass arrayClass;
   private String namespace;
   private final int hashCode;

   public ArrayNamespaceInfo(JClass serviceClass, JClass arrayClass, String namespace) {
      this.serviceClass = serviceClass;
      this.arrayClass = arrayClass;
      this.namespace = namespace;
      this.hashCode = arrayClass.hashCode() ^ namespace.hashCode();
   }

   public JClass getServiceClass() {
      return this.serviceClass;
   }

   public JClass getArrayClass() {
      return this.arrayClass;
   }

   public String getNamespace() {
      return this.namespace;
   }

   public boolean equals(Object o) {
      if (!(o instanceof ArrayNamespaceInfo)) {
         return false;
      } else {
         ArrayNamespaceInfo other = (ArrayNamespaceInfo)o;
         return this.arrayClass.equals(other.getArrayClass()) && this.namespace.equals(other.getNamespace());
      }
   }

   public int hashCode() {
      return this.hashCode;
   }
}
