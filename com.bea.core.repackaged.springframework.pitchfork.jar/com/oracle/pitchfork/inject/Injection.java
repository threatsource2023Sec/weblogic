package com.oracle.pitchfork.inject;

import com.oracle.pitchfork.interfaces.inject.InjectionI;
import com.oracle.pitchfork.interfaces.inject.InjectionInfo;
import java.lang.reflect.Member;

public abstract class Injection implements InjectionI {
   private final InjectionInfo info;
   private final Member member;
   private Object value;
   private boolean valueAssigned;
   private boolean isOptional;

   public Injection(Member member, InjectionInfo resourceInfo, boolean isOptional) {
      this.info = resourceInfo;
      this.member = member;
      this.isOptional = isOptional;
   }

   public InjectionInfo getInfo() {
      return this.info;
   }

   public Object getValue() {
      return this.value;
   }

   public boolean isOptional() {
      return this.isOptional;
   }

   public void setValue(Object value) {
      this.valueAssigned = true;
      this.value = value;
   }

   public boolean containsValue() {
      return this.valueAssigned;
   }

   public String getName() {
      return this.info.getName() != null ? this.info.getName() : this.getDefaultName();
   }

   public Class getType() {
      return this.info.getType() != null ? this.info.getType() : this.getMemberType();
   }

   public Member getMember() {
      return this.member;
   }

   protected String getDefaultName() {
      return this.member.getName();
   }

   public String toString() {
      return this.getClass().getName() + "; member=" + this.member.getName() + "; " + this.info;
   }

   protected abstract Class getMemberType();

   public abstract void apply(Object var1, Object var2);
}
