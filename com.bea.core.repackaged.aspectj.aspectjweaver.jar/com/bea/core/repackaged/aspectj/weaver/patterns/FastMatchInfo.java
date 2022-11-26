package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.World;

public class FastMatchInfo {
   private Shadow.Kind kind;
   private ResolvedType type;
   public World world;

   public FastMatchInfo(ResolvedType type, Shadow.Kind kind, World world) {
      this.type = type;
      this.kind = kind;
      this.world = world;
   }

   public Shadow.Kind getKind() {
      return this.kind;
   }

   public ResolvedType getType() {
      return this.type;
   }

   public String toString() {
      return "FastMatchInfo [type=" + this.type.getName() + "] [" + (this.kind == null ? "AllKinds" : "Kind=" + this.kind) + "]";
   }
}
