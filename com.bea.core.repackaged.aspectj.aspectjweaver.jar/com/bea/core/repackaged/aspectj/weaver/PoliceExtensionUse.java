package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.weaver.patterns.AbstractPatternNodeVisitor;
import com.bea.core.repackaged.aspectj.weaver.patterns.AndPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.KindedPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.NotPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.OrPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.Pointcut;

public class PoliceExtensionUse extends AbstractPatternNodeVisitor {
   private boolean synchronizationDesignatorEncountered;
   private World world;
   private Pointcut p;

   public PoliceExtensionUse(World w, Pointcut p) {
      this.world = w;
      this.p = p;
      this.synchronizationDesignatorEncountered = false;
   }

   public boolean synchronizationDesignatorEncountered() {
      return this.synchronizationDesignatorEncountered;
   }

   public Object visit(KindedPointcut node, Object data) {
      if (this.world == null) {
         return super.visit(node, data);
      } else {
         if (node.getKind() == Shadow.SynchronizationLock || node.getKind() == Shadow.SynchronizationUnlock) {
            this.synchronizationDesignatorEncountered = true;
         }

         if (!this.world.isJoinpointSynchronizationEnabled()) {
            IMessage m;
            if (node.getKind() == Shadow.SynchronizationLock) {
               m = MessageUtil.warn("lock() pointcut designator cannot be used without the option -Xjoinpoints:synchronization", this.p.getSourceLocation());
               this.world.getMessageHandler().handleMessage(m);
            } else if (node.getKind() == Shadow.SynchronizationUnlock) {
               m = MessageUtil.warn("unlock() pointcut designator cannot be used without the option -Xjoinpoints:synchronization", this.p.getSourceLocation());
               this.world.getMessageHandler().handleMessage(m);
            }
         }

         return super.visit(node, data);
      }
   }

   public Object visit(AndPointcut node, Object data) {
      node.getLeft().accept(this, data);
      node.getRight().accept(this, data);
      return node;
   }

   public Object visit(NotPointcut node, Object data) {
      node.getNegatedPointcut().accept(this, data);
      return node;
   }

   public Object visit(OrPointcut node, Object data) {
      node.getLeft().accept(this, data);
      node.getRight().accept(this, data);
      return node;
   }
}
