package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.util.FileUtil;
import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.Advice;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.CrosscuttingMembers;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.IntMap;
import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.NameMangler;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMemberImpl;
import com.bea.core.repackaged.aspectj.weaver.ResolvedPointcutDefinition;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.WeaverMessages;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.ast.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CflowPointcut extends Pointcut {
   private final Pointcut entry;
   boolean isBelow;
   private int[] freeVars;
   public static final ResolvedPointcutDefinition CFLOW_MARKER;

   public CflowPointcut(Pointcut entry, boolean isBelow, int[] freeVars) {
      this.entry = entry;
      this.isBelow = isBelow;
      this.freeVars = freeVars;
      this.pointcutKind = 10;
   }

   public boolean isCflowBelow() {
      return this.isBelow;
   }

   public int couldMatchKinds() {
      return Shadow.ALL_SHADOW_KINDS_BITS;
   }

   public Pointcut getEntry() {
      return this.entry;
   }

   public FuzzyBoolean fastMatch(FastMatchInfo type) {
      return FuzzyBoolean.MAYBE;
   }

   protected FuzzyBoolean matchInternal(Shadow shadow) {
      return FuzzyBoolean.MAYBE;
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(10);
      this.entry.write(s);
      s.writeBoolean(this.isBelow);
      FileUtil.writeIntArray(this.freeVars, s);
      this.writeLocation(s);
   }

   public static Pointcut read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      CflowPointcut ret = new CflowPointcut(Pointcut.read(s, context), s.readBoolean(), FileUtil.readIntArray(s));
      ret.readLocation(context, s);
      return ret;
   }

   public Pointcut parameterizeWith(Map typeVariableMap, World w) {
      CflowPointcut ret = new CflowPointcut(this.entry.parameterizeWith(typeVariableMap, w), this.isBelow, this.freeVars);
      ret.copyLocationFrom(this);
      return ret;
   }

   public void resolveBindings(IScope scope, Bindings bindings) {
      if (bindings == null) {
         this.entry.resolveBindings(scope, (Bindings)null);
         this.entry.state = RESOLVED;
         this.freeVars = new int[0];
      } else {
         Bindings entryBindings = new Bindings(bindings.size());
         this.entry.resolveBindings(scope, entryBindings);
         this.entry.state = RESOLVED;
         this.freeVars = entryBindings.getUsedFormals();
         bindings.mergeIn(entryBindings, scope);
      }

   }

   public boolean equals(Object other) {
      if (!(other instanceof CflowPointcut)) {
         return false;
      } else {
         CflowPointcut o = (CflowPointcut)other;
         return o.entry.equals(this.entry) && o.isBelow == this.isBelow;
      }
   }

   public int hashCode() {
      int result = 17;
      result = 37 * result + this.entry.hashCode();
      result = 37 * result + (this.isBelow ? 0 : 1);
      return result;
   }

   public String toString() {
      return "cflow" + (this.isBelow ? "below" : "") + "(" + this.entry + ")";
   }

   protected Test findResidueInternal(Shadow shadow, ExposedState state) {
      throw new RuntimeException("unimplemented - did concretization fail?");
   }

   public Pointcut concretize1(ResolvedType inAspect, ResolvedType declaringType, IntMap bindings) {
      if (this.isDeclare(bindings.getEnclosingAdvice())) {
         inAspect.getWorld().showMessage(IMessage.ERROR, WeaverMessages.format("cflowInDeclare", this.isBelow ? "below" : ""), bindings.getEnclosingAdvice().getSourceLocation(), (ISourceLocation)null);
         return Pointcut.makeMatchesNothing(Pointcut.CONCRETE);
      } else {
         IntMap entryBindings = new IntMap();
         if (this.freeVars != null) {
            int i = 0;

            for(int len = this.freeVars.length; i < len; ++i) {
               int freeVar = this.freeVars[i];
               entryBindings.put(freeVar, i);
            }
         }

         entryBindings.copyContext(bindings);
         World world = inAspect.getWorld();
         ResolvedType concreteAspect = bindings.getConcreteAspect();
         CrosscuttingMembers xcut = concreteAspect.crosscuttingMembers;
         Collection previousCflowEntries = xcut.getCflowEntries();
         entryBindings.pushEnclosingDefinition(CFLOW_MARKER);

         Pointcut concreteEntry;
         try {
            concreteEntry = this.entry.concretize(inAspect, declaringType, entryBindings);
         } finally {
            entryBindings.popEnclosingDefinitition();
         }

         ArrayList innerCflowEntries = new ArrayList(xcut.getCflowEntries());
         innerCflowEntries.removeAll(previousCflowEntries);
         Object localCflowField;
         if (this.freeVars != null && this.freeVars.length != 0) {
            List slots = new ArrayList();
            int i = 0;

            for(int len = this.freeVars.length; i < len; ++i) {
               int freeVar = this.freeVars[i];
               if (bindings.hasKey(freeVar)) {
                  int formalIndex = bindings.get(freeVar);
                  ResolvedPointcutDefinition enclosingDef = bindings.peekEnclosingDefinition();
                  ResolvedType formalType = null;
                  if (enclosingDef != null && enclosingDef.getParameterTypes().length > 0) {
                     formalType = enclosingDef.getParameterTypes()[freeVar].resolve(world);
                  } else {
                     formalType = bindings.getAdviceSignature().getParameterTypes()[formalIndex].resolve(world);
                  }

                  ConcreteCflowPointcut.Slot slot = new ConcreteCflowPointcut.Slot(formalIndex, formalType, i);
                  slots.add(slot);
               }
            }

            localCflowField = null;
            Object field = this.getCflowfield(xcut, concreteEntry, concreteAspect, "stack");
            if (field != null) {
               localCflowField = (ResolvedMember)field;
            } else {
               localCflowField = new ResolvedMemberImpl(Member.FIELD, concreteAspect, 25, NameMangler.cflowStack(xcut), UnresolvedType.forName("com.bea.core.repackaged.aspectj.runtime.internal.CFlowStack").getSignature());
               concreteAspect.crosscuttingMembers.addConcreteShadowMunger(Advice.makeCflowEntry(world, concreteEntry, this.isBelow, (Member)localCflowField, this.freeVars.length, innerCflowEntries, inAspect));
               concreteAspect.crosscuttingMembers.addTypeMunger(world.getWeavingSupport().makeCflowStackFieldAdder((ResolvedMember)localCflowField));
               this.putCflowfield(xcut, concreteEntry, concreteAspect, localCflowField, "stack");
            }

            Pointcut ret = new ConcreteCflowPointcut(concreteAspect, (Member)localCflowField, slots, false);
            ret.copyLocationFrom(this);
            return ret;
         } else {
            ResolvedMember localCflowField = null;
            localCflowField = this.getCflowfield(xcut, concreteEntry, concreteAspect, "counter");
            if (localCflowField != null) {
               localCflowField = (ResolvedMember)localCflowField;
            } else {
               localCflowField = new ResolvedMemberImpl(Member.FIELD, concreteAspect, 25, NameMangler.cflowCounter(xcut), UnresolvedType.forName("com.bea.core.repackaged.aspectj.runtime.internal.CFlowCounter").getSignature());
               concreteAspect.crosscuttingMembers.addTypeMunger(world.getWeavingSupport().makeCflowCounterFieldAdder((ResolvedMember)localCflowField));
               concreteAspect.crosscuttingMembers.addConcreteShadowMunger(Advice.makeCflowEntry(world, concreteEntry, this.isBelow, (Member)localCflowField, this.freeVars == null ? 0 : this.freeVars.length, innerCflowEntries, inAspect));
               this.putCflowfield(xcut, concreteEntry, concreteAspect, localCflowField, "counter");
            }

            Pointcut ret = new ConcreteCflowPointcut(concreteAspect, (Member)localCflowField, (List)null, true);
            ret.copyLocationFrom(this);
            return ret;
         }
      }
   }

   private String getKey(Pointcut p, ResolvedType a, String stackOrCounter) {
      StringBuffer sb = new StringBuffer();
      sb.append(a.getName());
      sb.append("::");
      sb.append(p.toString());
      sb.append("::");
      sb.append(stackOrCounter);
      return sb.toString();
   }

   private Object getCflowfield(CrosscuttingMembers xcut, Pointcut pcutkey, ResolvedType concreteAspect, String stackOrCounter) {
      String key = this.getKey(pcutkey, concreteAspect, stackOrCounter);
      Object o = null;
      if (this.isBelow) {
         o = xcut.getCflowBelowFields().get(key);
      } else {
         o = xcut.getCflowFields().get(key);
      }

      return o;
   }

   private void putCflowfield(CrosscuttingMembers xcut, Pointcut pcutkey, ResolvedType concreteAspect, Object o, String stackOrCounter) {
      String key = this.getKey(pcutkey, concreteAspect, stackOrCounter);
      if (this.isBelow) {
         xcut.getCflowBelowFields().put(key, o);
      } else {
         xcut.getCflowFields().put(key, o);
      }

   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   static {
      CFLOW_MARKER = new ResolvedPointcutDefinition((UnresolvedType)null, 0, (String)null, UnresolvedType.NONE, Pointcut.makeMatchesNothing(Pointcut.RESOLVED));
   }
}
