package com.bea.staxb.buildtime.internal;

import com.bea.util.jam.JClass;
import javax.xml.namespace.QName;

public class Java2SchemaWrapperElement {
   private JClass mServiceClass;
   private QName mWrapperName;
   private JClass[] mElementTypes;
   private String[] mElementNames;

   public Java2SchemaWrapperElement(JClass serviceClass, QName wrapperElementName, JClass[] elementJavaTypes, String[] elementNames) {
      if (serviceClass == null) {
         throw new IllegalArgumentException("Java2SchemaWrapperElement:  null serviceClass");
      } else if (wrapperElementName == null) {
         throw new IllegalArgumentException("Java2SchemaWrapperElement:  null wrapperElementName");
      } else if (elementJavaTypes == null) {
         throw new IllegalArgumentException("Java2SchemaWrapperElement:  null elementJavaTypes");
      } else if (elementNames == null) {
         throw new IllegalArgumentException("Java2SchemaWrapperElement:  null elementNames");
      } else if (elementJavaTypes.length != elementNames.length) {
         throw new IllegalArgumentException("different number of classes and elements [" + elementJavaTypes.length + "," + elementNames.length + "]");
      } else {
         for(int i = 0; i < elementJavaTypes.length; ++i) {
            if (elementJavaTypes[i].isVoidType() || elementJavaTypes[i].isEnumType()) {
               throw new IllegalArgumentException("invalid element java type: '" + elementJavaTypes[i].getQualifiedName() + "'");
            }
         }

         this.mServiceClass = serviceClass;
         this.mWrapperName = wrapperElementName;
         this.mElementTypes = elementJavaTypes;
         this.mElementNames = elementNames;
      }
   }

   public JClass getServiceClass() {
      return this.mServiceClass;
   }

   public QName getWrapperName() {
      return this.mWrapperName;
   }

   public JClass[] getElementJavaTypes() {
      return this.mElementTypes;
   }

   public String[] getElementNames() {
      return this.mElementNames;
   }
}
