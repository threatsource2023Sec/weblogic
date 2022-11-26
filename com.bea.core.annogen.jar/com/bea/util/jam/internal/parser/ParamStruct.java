package com.bea.util.jam.internal.parser;

import com.bea.util.jam.mutable.MInvokable;
import com.bea.util.jam.mutable.MParameter;

class ParamStruct {
   private String mName;
   private String mType;

   public ParamStruct(String type, String name) {
      this.init(type, name);
   }

   public void init(String type, String name) {
      this.mType = type;
      this.mName = name;
   }

   public MParameter createParameter(MInvokable e) {
      if (e == null) {
         throw new IllegalArgumentException("null invokable");
      } else {
         MParameter param = e.addNewParameter();
         param.setSimpleName(this.mName);
         param.setUnqualifiedType(this.mType);
         return param;
      }
   }
}
