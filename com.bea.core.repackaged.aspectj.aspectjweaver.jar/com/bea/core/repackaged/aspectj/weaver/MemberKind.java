package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.util.TypeSafeEnum;
import java.io.DataInputStream;
import java.io.IOException;

public class MemberKind extends TypeSafeEnum {
   public MemberKind(String name, int key) {
      super(name, key);
   }

   public static MemberKind read(DataInputStream s) throws IOException {
      int key = s.readByte();
      switch (key) {
         case 1:
            return Member.METHOD;
         case 2:
            return Member.FIELD;
         case 3:
            return Member.CONSTRUCTOR;
         case 4:
            return Member.STATIC_INITIALIZATION;
         case 5:
            return Member.POINTCUT;
         case 6:
            return Member.ADVICE;
         case 7:
            return Member.HANDLER;
         case 8:
            return Member.MONITORENTER;
         case 9:
            return Member.MONITOREXIT;
         default:
            throw new BCException("Unexpected memberkind, should be (1-9) but was " + key);
      }
   }
}
