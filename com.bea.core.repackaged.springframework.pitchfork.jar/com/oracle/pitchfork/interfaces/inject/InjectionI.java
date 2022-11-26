package com.oracle.pitchfork.interfaces.inject;

import java.lang.reflect.Member;

public interface InjectionI {
   InjectionInfo getInfo();

   Object getValue();

   void setValue(Object var1);

   boolean containsValue();

   boolean isOptional();

   String getName();

   Class getType();

   Member getMember();

   void apply(Object var1, Object var2);
}
