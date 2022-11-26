package com.bea.xml_.impl.jam.internal.parser;

import com.bea.xml_.impl.jam.mutable.MInvokable;
import java.util.ArrayList;
import java.util.List;

public class ParamStructPool {
   private static final boolean VERBOSE = true;
   private List mList = new ArrayList();
   private int mLength = 0;

   public void setParametersOn(MInvokable e) {
      for(int i = 0; i < this.mLength; ++i) {
         ParamStruct struct = (ParamStruct)this.mList.get(i);
         struct.createParameter(e);
      }

   }

   public void add(String type, String name) {
      ++this.mLength;
      if (this.mLength >= this.mList.size()) {
         this.mList.add(new ParamStruct(type, name));
      } else {
         ((ParamStruct)this.mList.get(this.mLength)).init(type, name);
      }

   }

   public void clear() {
      this.mLength = 0;
   }
}
