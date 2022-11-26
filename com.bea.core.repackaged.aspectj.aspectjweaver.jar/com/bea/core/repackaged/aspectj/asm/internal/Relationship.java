package com.bea.core.repackaged.aspectj.asm.internal;

import com.bea.core.repackaged.aspectj.asm.IRelationship;
import java.util.List;

public class Relationship implements IRelationship {
   private static final long serialVersionUID = 3855166397957609120L;
   private String name;
   private IRelationship.Kind kind;
   private boolean isAffects;
   private String sourceHandle;
   private List targets;
   private boolean hasRuntimeTest;

   public Relationship(String name, IRelationship.Kind kind, String sourceHandle, List targets, boolean runtimeTest) {
      this.name = name;
      this.isAffects = name.equals("advises") || name.equals("declares on") || name.equals("softens") || name.equals("matched by") || name.equals("declared on") || name.equals("annotates");
      this.kind = kind;
      this.sourceHandle = sourceHandle;
      this.targets = targets;
      this.hasRuntimeTest = runtimeTest;
   }

   public String getName() {
      return this.name;
   }

   public IRelationship.Kind getKind() {
      return this.kind;
   }

   public String toString() {
      return this.name;
   }

   public String getSourceHandle() {
      return this.sourceHandle;
   }

   public List getTargets() {
      return this.targets;
   }

   public void addTarget(String handle) {
      if (!this.targets.contains(handle)) {
         this.targets.add(handle);
      }
   }

   public boolean hasRuntimeTest() {
      return this.hasRuntimeTest;
   }

   public boolean isAffects() {
      return this.isAffects;
   }
}
