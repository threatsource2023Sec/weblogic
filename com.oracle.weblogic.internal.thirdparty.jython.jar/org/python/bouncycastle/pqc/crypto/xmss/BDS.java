package org.python.bouncycastle.pqc.crypto.xmss;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

public final class BDS implements Serializable {
   private static final long serialVersionUID = 1L;
   private transient XMSS xmss;
   private transient WOTSPlus wotsPlus;
   private final int treeHeight;
   private int k;
   private XMSSNode root;
   private List authenticationPath;
   private Map retain;
   private Stack stack;
   private List treeHashInstances;
   private Map keep;
   private int index;

   protected BDS(XMSS var1) {
      if (var1 == null) {
         throw new NullPointerException("xmss == null");
      } else {
         this.xmss = var1;
         this.wotsPlus = var1.getWOTSPlus();
         this.treeHeight = var1.getParams().getHeight();
         this.k = var1.getParams().getK();
         if (this.k <= this.treeHeight && this.k >= 2 && (this.treeHeight - this.k) % 2 == 0) {
            this.authenticationPath = new ArrayList();
            this.retain = new TreeMap();
            this.stack = new Stack();
            this.initializeTreeHashInstances();
            this.keep = new TreeMap();
            this.index = 0;
         } else {
            throw new IllegalArgumentException("illegal value for BDS parameter k");
         }
      }
   }

   private void initializeTreeHashInstances() {
      this.treeHashInstances = new ArrayList();

      for(int var1 = 0; var1 < this.treeHeight - this.k; ++var1) {
         this.treeHashInstances.add(new TreeHash(var1));
      }

   }

   protected XMSSNode initialize(OTSHashAddress var1) {
      if (var1 == null) {
         throw new NullPointerException("otsHashAddress == null");
      } else {
         LTreeAddress var2 = (LTreeAddress)((LTreeAddress.Builder)((LTreeAddress.Builder)(new LTreeAddress.Builder()).withLayerAddress(var1.getLayerAddress())).withTreeAddress(var1.getTreeAddress())).build();
         HashTreeAddress var3 = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)(new HashTreeAddress.Builder()).withLayerAddress(var1.getLayerAddress())).withTreeAddress(var1.getTreeAddress())).build();

         for(int var4 = 0; var4 < 1 << this.treeHeight; ++var4) {
            var1 = (OTSHashAddress)((OTSHashAddress.Builder)((OTSHashAddress.Builder)((OTSHashAddress.Builder)(new OTSHashAddress.Builder()).withLayerAddress(var1.getLayerAddress())).withTreeAddress(var1.getTreeAddress())).withOTSAddress(var4).withChainAddress(var1.getChainAddress()).withHashAddress(var1.getHashAddress()).withKeyAndMask(var1.getKeyAndMask())).build();
            this.wotsPlus.importKeys(this.xmss.getWOTSPlusSecretKey(var1), this.xmss.getPublicSeed());
            WOTSPlusPublicKeyParameters var5 = this.wotsPlus.getPublicKey(var1);
            var2 = (LTreeAddress)((LTreeAddress.Builder)((LTreeAddress.Builder)((LTreeAddress.Builder)(new LTreeAddress.Builder()).withLayerAddress(var2.getLayerAddress())).withTreeAddress(var2.getTreeAddress())).withLTreeAddress(var4).withTreeHeight(var2.getTreeHeight()).withTreeIndex(var2.getTreeIndex()).withKeyAndMask(var2.getKeyAndMask())).build();
            XMSSNode var6 = this.xmss.lTree(var5, var2);

            for(var3 = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)((HashTreeAddress.Builder)(new HashTreeAddress.Builder()).withLayerAddress(var3.getLayerAddress())).withTreeAddress(var3.getTreeAddress())).withTreeIndex(var4).withKeyAndMask(var3.getKeyAndMask())).build(); !this.stack.isEmpty() && ((XMSSNode)this.stack.peek()).getHeight() == var6.getHeight(); var3 = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)((HashTreeAddress.Builder)(new HashTreeAddress.Builder()).withLayerAddress(var3.getLayerAddress())).withTreeAddress(var3.getTreeAddress())).withTreeHeight(var3.getTreeHeight() + 1).withTreeIndex(var3.getTreeIndex()).withKeyAndMask(var3.getKeyAndMask())).build()) {
               int var7 = (int)Math.floor((double)(var4 / (1 << var6.getHeight())));
               if (var7 == 1) {
                  this.authenticationPath.add(var6.clone());
               }

               if (var7 == 3 && var6.getHeight() < this.treeHeight - this.k) {
                  ((TreeHash)this.treeHashInstances.get(var6.getHeight())).setNode(var6.clone());
               }

               if (var7 >= 3 && (var7 & 1) == 1 && var6.getHeight() >= this.treeHeight - this.k && var6.getHeight() <= this.treeHeight - 2) {
                  if (this.retain.get(var6.getHeight()) == null) {
                     LinkedList var8 = new LinkedList();
                     var8.add(var6.clone());
                     this.retain.put(var6.getHeight(), var8);
                  } else {
                     ((LinkedList)this.retain.get(var6.getHeight())).add(var6.clone());
                  }
               }

               var3 = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)((HashTreeAddress.Builder)(new HashTreeAddress.Builder()).withLayerAddress(var3.getLayerAddress())).withTreeAddress(var3.getTreeAddress())).withTreeHeight(var3.getTreeHeight()).withTreeIndex((var3.getTreeIndex() - 1) / 2).withKeyAndMask(var3.getKeyAndMask())).build();
               var6 = this.xmss.randomizeHash((XMSSNode)this.stack.pop(), var6, var3);
               var6 = new XMSSNode(var6.getHeight() + 1, var6.getValue());
            }

            this.stack.push(var6);
         }

         this.root = (XMSSNode)this.stack.pop();
         return this.root.clone();
      }
   }

   protected void nextAuthenticationPath(OTSHashAddress var1) {
      if (var1 == null) {
         throw new NullPointerException("otsHashAddress == null");
      } else if (this.index > (1 << this.treeHeight) - 2) {
         throw new IllegalStateException("index out of bounds");
      } else {
         LTreeAddress var2 = (LTreeAddress)((LTreeAddress.Builder)((LTreeAddress.Builder)(new LTreeAddress.Builder()).withLayerAddress(var1.getLayerAddress())).withTreeAddress(var1.getTreeAddress())).build();
         HashTreeAddress var3 = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)(new HashTreeAddress.Builder()).withLayerAddress(var1.getLayerAddress())).withTreeAddress(var1.getTreeAddress())).build();
         int var4 = XMSSUtil.calculateTau(this.index, this.treeHeight);
         if ((this.index >> var4 + 1 & 1) == 0 && var4 < this.treeHeight - 1) {
            this.keep.put(var4, ((XMSSNode)this.authenticationPath.get(var4)).clone());
         }

         if (var4 == 0) {
            var1 = (OTSHashAddress)((OTSHashAddress.Builder)((OTSHashAddress.Builder)((OTSHashAddress.Builder)(new OTSHashAddress.Builder()).withLayerAddress(var1.getLayerAddress())).withTreeAddress(var1.getTreeAddress())).withOTSAddress(this.index).withChainAddress(var1.getChainAddress()).withHashAddress(var1.getHashAddress()).withKeyAndMask(var1.getKeyAndMask())).build();
            this.wotsPlus.importKeys(this.xmss.getWOTSPlusSecretKey(var1), this.xmss.getPublicSeed());
            WOTSPlusPublicKeyParameters var5 = this.wotsPlus.getPublicKey(var1);
            var2 = (LTreeAddress)((LTreeAddress.Builder)((LTreeAddress.Builder)((LTreeAddress.Builder)(new LTreeAddress.Builder()).withLayerAddress(var2.getLayerAddress())).withTreeAddress(var2.getTreeAddress())).withLTreeAddress(this.index).withTreeHeight(var2.getTreeHeight()).withTreeIndex(var2.getTreeIndex()).withKeyAndMask(var2.getKeyAndMask())).build();
            XMSSNode var6 = this.xmss.lTree(var5, var2);
            this.authenticationPath.set(0, var6);
         } else {
            var3 = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)((HashTreeAddress.Builder)(new HashTreeAddress.Builder()).withLayerAddress(var3.getLayerAddress())).withTreeAddress(var3.getTreeAddress())).withTreeHeight(var4 - 1).withTreeIndex(this.index >> var4).withKeyAndMask(var3.getKeyAndMask())).build();
            XMSSNode var9 = this.xmss.randomizeHash((XMSSNode)this.authenticationPath.get(var4 - 1), (XMSSNode)this.keep.get(var4 - 1), var3);
            var9 = new XMSSNode(var9.getHeight() + 1, var9.getValue());
            this.authenticationPath.set(var4, var9);
            this.keep.remove(var4 - 1);

            int var11;
            for(var11 = 0; var11 < var4; ++var11) {
               if (var11 < this.treeHeight - this.k) {
                  this.authenticationPath.set(var11, ((TreeHash)this.treeHashInstances.get(var11)).tailNode.clone());
               } else {
                  this.authenticationPath.set(var11, ((LinkedList)this.retain.get(var11)).removeFirst());
               }
            }

            var11 = Math.min(var4, this.treeHeight - this.k);

            for(int var7 = 0; var7 < var11; ++var7) {
               int var8 = this.index + 1 + 3 * (1 << var7);
               if (var8 < 1 << this.treeHeight) {
                  ((TreeHash)this.treeHashInstances.get(var7)).initialize(var8);
               }
            }
         }

         for(int var10 = 0; var10 < this.treeHeight - this.k >> 1; ++var10) {
            TreeHash var12 = this.getTreeHashInstanceForUpdate();
            if (var12 != null) {
               var12.update(var1);
            }
         }

         ++this.index;
      }
   }

   private TreeHash getTreeHashInstanceForUpdate() {
      TreeHash var1 = null;
      Iterator var2 = this.treeHashInstances.iterator();

      while(var2.hasNext()) {
         TreeHash var3 = (TreeHash)var2.next();
         if (!var3.isFinished() && var3.isInitialized()) {
            if (var1 == null) {
               var1 = var3;
            } else if (var3.getHeight() < var1.getHeight()) {
               var1 = var3;
            } else if (var3.getHeight() == var1.getHeight() && var3.getIndexLeaf() < var1.getIndexLeaf()) {
               var1 = var3;
            }
         }
      }

      return var1;
   }

   protected void validate() {
      if (this.treeHeight != this.xmss.getParams().getHeight()) {
         throw new IllegalStateException("wrong height");
      } else if (this.authenticationPath == null) {
         throw new IllegalStateException("authenticationPath == null");
      } else if (this.retain == null) {
         throw new IllegalStateException("retain == null");
      } else if (this.stack == null) {
         throw new IllegalStateException("stack == null");
      } else if (this.treeHashInstances == null) {
         throw new IllegalStateException("treeHashInstances == null");
      } else if (this.keep == null) {
         throw new IllegalStateException("keep == null");
      } else if (!XMSSUtil.isIndexValid(this.treeHeight, (long)this.index)) {
         throw new IllegalStateException("index in BDS state out of bounds");
      }
   }

   protected int getTreeHeight() {
      return this.treeHeight;
   }

   protected XMSSNode getRoot() {
      return this.root.clone();
   }

   protected List getAuthenticationPath() {
      ArrayList var1 = new ArrayList();
      Iterator var2 = this.authenticationPath.iterator();

      while(var2.hasNext()) {
         XMSSNode var3 = (XMSSNode)var2.next();
         var1.add(var3.clone());
      }

      return var1;
   }

   protected void setXMSS(XMSS var1) {
      this.xmss = var1;
      this.wotsPlus = var1.getWOTSPlus();
   }

   protected int getIndex() {
      return this.index;
   }

   private final class TreeHash implements Serializable {
      private static final long serialVersionUID = 1L;
      private XMSSNode tailNode;
      private final int initialHeight;
      private int height;
      private int nextIndex;
      private boolean initialized;
      private boolean finished;

      private TreeHash(int var2) {
         this.initialHeight = var2;
         this.initialized = false;
         this.finished = false;
      }

      private void initialize(int var1) {
         this.tailNode = null;
         this.height = this.initialHeight;
         this.nextIndex = var1;
         this.initialized = true;
         this.finished = false;
      }

      private void update(OTSHashAddress var1) {
         if (var1 == null) {
            throw new NullPointerException("otsHashAddress == null");
         } else if (!this.finished && this.initialized) {
            var1 = (OTSHashAddress)((OTSHashAddress.Builder)((OTSHashAddress.Builder)((OTSHashAddress.Builder)(new OTSHashAddress.Builder()).withLayerAddress(var1.getLayerAddress())).withTreeAddress(var1.getTreeAddress())).withOTSAddress(this.nextIndex).withChainAddress(var1.getChainAddress()).withHashAddress(var1.getHashAddress()).withKeyAndMask(var1.getKeyAndMask())).build();
            LTreeAddress var2 = (LTreeAddress)((LTreeAddress.Builder)((LTreeAddress.Builder)(new LTreeAddress.Builder()).withLayerAddress(var1.getLayerAddress())).withTreeAddress(var1.getTreeAddress())).withLTreeAddress(this.nextIndex).build();
            HashTreeAddress var3 = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)(new HashTreeAddress.Builder()).withLayerAddress(var1.getLayerAddress())).withTreeAddress(var1.getTreeAddress())).withTreeIndex(this.nextIndex).build();
            BDS.this.wotsPlus.importKeys(BDS.this.xmss.getWOTSPlusSecretKey(var1), BDS.this.xmss.getPublicSeed());
            WOTSPlusPublicKeyParameters var4 = BDS.this.wotsPlus.getPublicKey(var1);

            XMSSNode var5;
            for(var5 = BDS.this.xmss.lTree(var4, var2); !BDS.this.stack.isEmpty() && ((XMSSNode)BDS.this.stack.peek()).getHeight() == var5.getHeight() && ((XMSSNode)BDS.this.stack.peek()).getHeight() != this.initialHeight; var3 = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)((HashTreeAddress.Builder)(new HashTreeAddress.Builder()).withLayerAddress(var3.getLayerAddress())).withTreeAddress(var3.getTreeAddress())).withTreeHeight(var3.getTreeHeight() + 1).withTreeIndex(var3.getTreeIndex()).withKeyAndMask(var3.getKeyAndMask())).build()) {
               var3 = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)((HashTreeAddress.Builder)(new HashTreeAddress.Builder()).withLayerAddress(var3.getLayerAddress())).withTreeAddress(var3.getTreeAddress())).withTreeHeight(var3.getTreeHeight()).withTreeIndex((var3.getTreeIndex() - 1) / 2).withKeyAndMask(var3.getKeyAndMask())).build();
               var5 = BDS.this.xmss.randomizeHash((XMSSNode)BDS.this.stack.pop(), var5, var3);
               var5 = new XMSSNode(var5.getHeight() + 1, var5.getValue());
            }

            if (this.tailNode == null) {
               this.tailNode = var5;
            } else if (this.tailNode.getHeight() == var5.getHeight()) {
               var3 = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)((HashTreeAddress.Builder)(new HashTreeAddress.Builder()).withLayerAddress(var3.getLayerAddress())).withTreeAddress(var3.getTreeAddress())).withTreeHeight(var3.getTreeHeight()).withTreeIndex((var3.getTreeIndex() - 1) / 2).withKeyAndMask(var3.getKeyAndMask())).build();
               var5 = BDS.this.xmss.randomizeHash(this.tailNode, var5, var3);
               var5 = new XMSSNode(this.tailNode.getHeight() + 1, var5.getValue());
               this.tailNode = var5;
               var3 = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)((HashTreeAddress.Builder)(new HashTreeAddress.Builder()).withLayerAddress(var3.getLayerAddress())).withTreeAddress(var3.getTreeAddress())).withTreeHeight(var3.getTreeHeight() + 1).withTreeIndex(var3.getTreeIndex()).withKeyAndMask(var3.getKeyAndMask())).build();
            } else {
               BDS.this.stack.push(var5);
            }

            if (this.tailNode.getHeight() == this.initialHeight) {
               this.finished = true;
            } else {
               this.height = var5.getHeight();
               ++this.nextIndex;
            }

         } else {
            throw new IllegalStateException("finished or not initialized");
         }
      }

      private int getHeight() {
         return this.initialized && !this.finished ? this.height : Integer.MAX_VALUE;
      }

      private int getIndexLeaf() {
         return this.nextIndex;
      }

      private void setNode(XMSSNode var1) {
         this.tailNode = var1;
         this.height = var1.getHeight();
         if (this.height == this.initialHeight) {
            this.finished = true;
         }

      }

      private boolean isFinished() {
         return this.finished;
      }

      private boolean isInitialized() {
         return this.initialized;
      }

      // $FF: synthetic method
      TreeHash(int var2, Object var3) {
         this(var2);
      }
   }
}
