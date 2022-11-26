package monfox.toolkit.snmp.agent;

import java.io.IOException;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.SnmpOidInfo;
import monfox.toolkit.snmp.util.OidTree;

public class SnmpMib {
   private String a;
   private SnmpMetadata b;
   private OidTree c;
   private boolean d;
   private Logger e;

   public SnmpMib() {
      this((SnmpMetadata)null, new OidTree(), (String)null);
   }

   public SnmpMib(String var1) throws SnmpException, IOException {
      this(SnmpMetadata.load(var1), new OidTree(), var1);
   }

   public SnmpMib(SnmpMetadata var1) {
      this(var1, new OidTree(), var1.getName());
   }

   private SnmpMib(SnmpMetadata var1, OidTree var2, String var3) {
      this.d = false;
      if (var1 == null) {
         var1 = SnmpFramework.getMetadata();
      }

      this.b = var1;
      this.c = var2;
      this.a = var3;
      this.e = Logger.getInstance(a("\u0002{A"));
      if (this.e.isDebugEnabled()) {
         this.e.debug(this.toString());
      }

   }

   public SnmpMetadata getMetadata() {
      return this.b;
   }

   public synchronized SnmpMibLeaf add(String var1, String var2) throws SnmpMibException, SnmpValueException {
      SnmpMibLeaf var3 = new SnmpMibLeaf(this.b, var1);
      var3.setValue(var2);
      this.add(var3);
      return var3;
   }

   public synchronized SnmpMibLeaf add(String var1, SnmpValue var2) throws SnmpMibException, SnmpValueException {
      SnmpMibLeaf var3 = new SnmpMibLeaf(this.b, var1);
      var3.setValue(var2);
      this.add(var3);
      return var3;
   }

   public synchronized SnmpMibLeaf add(SnmpOid var1, SnmpValue var2) throws SnmpMibException, SnmpValueException {
      if (var1.getMetadata() == null) {
         var1.setMetadata(this.b);
      }

      SnmpMibLeaf var3 = new SnmpMibLeaf(var1);
      var3.setValue(var2);
      this.add(var3);
      return var3;
   }

   public synchronized SnmpMibLeaf addInstance(SnmpOid var1, SnmpValue var2) throws SnmpMibException, SnmpValueException {
      SnmpOid var3 = var1.getParent();
      SnmpOidInfo var4 = var1.getOidInfo();
      if (var4 != null) {
         var3 = var4.getOid();
      }

      if (var1.getMetadata() == null) {
         var1.setMetadata(this.b);
      }

      if (var3.getMetadata() == null) {
         var3.setMetadata(this.b);
      }

      SnmpMibLeaf var5 = new SnmpMibLeaf(var3, var1);
      var5.setValue(var2);
      this.add(var5);
      return var5;
   }

   public synchronized SnmpMibLeaf addInstance(String var1, String var2) throws SnmpMibException, SnmpValueException {
      SnmpOid var3 = new SnmpOid(this.b, var1);
      SnmpOid var4 = var3.getParent();
      SnmpOidInfo var5 = var3.getOidInfo();
      if (var5 != null) {
         var4 = var5.getOid();
      }

      if (var3.getMetadata() == null) {
         var3.setMetadata(this.b);
      }

      if (var4.getMetadata() == null) {
         var4.setMetadata(this.b);
      }

      SnmpMibLeaf var6 = new SnmpMibLeaf(var4, var3);
      var6.setValue(var2);
      this.add(var6);
      return var6;
   }

   public synchronized void add(SnmpMibNode var1, boolean var2) throws SnmpMibException {
      if (var2) {
         this.removeSubtree(var1.getOid());
      }

      this.add(var1);
   }

   public synchronized void add(SnmpMibNode var1) throws SnmpMibException {
      SnmpOid var2 = var1.getOid();
      long[] var3 = var2.toLongArray(false);
      int var4 = var2.getLength();
      OidTree.Node var5 = this.c.get(var3, var4);
      if (var5 != null && var5.info != null) {
         throw new SnmpMibException(a("\u0000{GJ\b#@f\u000b\r6\u0012j\u0004I:AfPI") + var2);
      } else {
         long[] var6 = null;
         int var7 = 0;
         if (var1.getNodeType() == 2) {
            SnmpOid var8 = var1.getMaxOid();
            var6 = var8.toLongArray(false);
            var7 = var8.getLength();
            OidTree.Node var9 = this.c.get(var6, var7);
            if (var9 != null && var9.info != null) {
               throw new SnmpMibException(a("\u0000{GJ\b#@f\u000b\r6\u0012j\u0004I:AfPI") + var2);
            }
         }

         this.c.add(var3, var4, (String)null, var1);
         var1.setMib(this);
         if (var1.getNodeType() == 2) {
            this.c.add(var6, var7, (String)null, new e(var1));
         }

         if (this.e.isDebugEnabled()) {
            this.e.debug(a("\u000eVg\u000f\ro") + var1);
         }

      }
   }

   public synchronized SnmpMibNode remove(SnmpMibNode var1) {
      SnmpOid var2 = var1.getOid();
      return this.remove(var2);
   }

   public synchronized SnmpMibNode remove(SnmpOid var1) {
      OidTree.Node var2 = this.c.get(var1.toLongArray(false), var1.getLength());
      if (var2 == null) {
         return null;
      } else if (var2.info == null) {
         var2.remove(false);
         return null;
      } else {
         SnmpMibNode var3 = (SnmpMibNode)var2.info;
         var3.setMib((SnmpMib)null);
         var2.info = null;
         var2.remove(false);
         if (var3.getNodeType() == 2) {
            SnmpOid var4 = var3.getMaxOid();
            OidTree.Node var5 = this.c.get(var4.toLongArray(false), var4.getLength());
            if (var5 != null) {
               var5.info = null;
               var5.remove(false);
            }
         }

         if (this.e.isDebugEnabled()) {
            this.e.debug(a("\u001dWn\u0005\u001f*V#") + var3);
         }

         return var3;
      }
   }

   public synchronized void removeSubtree(SnmpMibNode var1) {
      this.removeSubtree(var1.getOid());
   }

   public synchronized void removeSubtree(SnmpOid var1) {
      OidTree.Node var2 = this.c.get(var1.toLongArray(false), var1.getLength());
      if (var2 != null) {
         if (var2.info != null) {
            SnmpMibNode var3 = (SnmpMibNode)var2.info;
            var3.setMib((SnmpMib)null);
            var2.info = null;
            if (var3.getNodeType() == 2) {
               SnmpOid var4 = var3.getMaxOid();
               OidTree.Node var5 = this.c.get(var4.toLongArray(false), var4.getLength());
               if (var5 != null) {
                  var5.info = null;
                  var5.remove(false);
               }
            }
         }

         var2.remove(true);
         if (this.e.isDebugEnabled()) {
            this.e.debug(a("\u001dWn\u0005\u001f*V#9\u001c-Fq\u000f\fo") + var2);
         }

      }
   }

   public SnmpMibNode get(String var1) throws SnmpValueException {
      return this.get(new SnmpOid(this.getMetadata(), var1));
   }

   public synchronized SnmpMibNode get(SnmpOid var1) {
      return this.get(var1, false);
   }

   public synchronized SnmpMibNode get(SnmpOid var1, boolean var2) {
      boolean var9 = SnmpMibNode.b;
      OidTree.Node var3;
      SnmpMibNode var4;
      if (this.isPrepareForAccessSupported()) {
         var3 = null;
         if (var1 == null) {
            var3 = this.c.getRoot();
         } else {
            var3 = this.c.getClosest(var1.toLongArray(false), var1.getLength());
         }

         while(var3 != null) {
            if (var3.info != null) {
               var4 = (SnmpMibNode)var3.info;
               var4.prepareForAccess();
               if (!var9) {
                  break;
               }
            }

            var3 = var3.parent;
            if (var9) {
               break;
            }
         }
      }

      var3 = this.c.get(var1.toLongArray(false), var1.getLength());
      if (var3 != null && var3.info != null) {
         var4 = (SnmpMibNode)var3.info;
         return var4 instanceof e ? ((e)var4).getStartNode() : var4;
      } else {
         if (var3 == null) {
            var3 = this.c.getClosest(var1.toLongArray(false), var1.getLength());
         }

         if (var3 == null) {
            return null;
         } else {
            OidTree.Node var10 = var3;

            SnmpMibNode var5;
            SnmpOid var6;
            Object var10000;
            label135:
            do {
               while(true) {
                  if (var10 != null) {
                     var10000 = var10.info;
                     if (var9 || var9) {
                        break;
                     }

                     if (var10000 == null) {
                        var10 = var10.parent;
                        if (!var9) {
                           continue;
                        }
                     }
                  }

                  if (var10 == null) {
                     break label135;
                  }

                  var10000 = var10.info;
                  break;
               }

               var5 = (SnmpMibNode)var10000;
               if (this.e.isDebugEnabled()) {
                  this.e.debug(a("gQk\u000f\n$[m\rI!]g\u000fSo") + var5.getOid());
               }

               switch (var5.getNodeType()) {
                  case 1:
                     if (!(var5 instanceof SnmpMibLeaf)) {
                        return null;
                     }

                     SnmpMibLeaf var7 = (SnmpMibLeaf)var5;
                     if (var7.isInTable()) {
                        return var7.getTable();
                     }

                     if (var9) {
                        return null;
                     }
                     break;
                  case 2:
                     var6 = var5.getMaxOid();
                     if (var6.compareTo(var1) >= 0) {
                        return var5;
                     }
                     break;
                  case 3:
                     return var5;
                  default:
                     return null;
               }
            } while(!var9);

            while(true) {
               var3 = var3.nextNode();

               label157: {
                  while(var3 != null) {
                     var10000 = var3.info;
                     if (var9) {
                        break label157;
                     }

                     if (var10000 != null) {
                        break;
                     }

                     var3 = var3.nextNode();
                     if (var9) {
                        break;
                     }
                  }

                  if (var3 == null) {
                     return null;
                  }

                  var10000 = var3.info;
               }

               var5 = (SnmpMibNode)var10000;
               SnmpOid var12;
               if (var5.getNodeType() == 22) {
                  SnmpMibNode var11 = ((e)var5).getStartNode();
                  var12 = var11.getOid();
                  if (var12.compareTo(var1) >= 0) {
                     return null;
                  }

                  SnmpOid var8 = var11.getMaxOid();
                  if (var8.compareTo(var1) >= 0) {
                     return var11;
                  }

                  if (!var9) {
                     continue;
                  }
               }

               if (var5.getNodeType() == 2) {
                  var6 = var5.getOid();
                  if (var6.compareTo(var1) >= 0) {
                     return null;
                  }

                  var12 = var5.getMaxOid();
                  if (var12.compareTo(var1) >= 0) {
                     return var5;
                  }

                  if (!var9) {
                     continue;
                  }
               }

               if (var5.getOid().compareTo(var1) >= 0) {
                  return null;
               }
            }
         }
      }
   }

   public synchronized SnmpMibNode getNext(SnmpOid var1) {
      return this.getNext(var1, true);
   }

   public synchronized SnmpMibNode getNext(SnmpOid var1, boolean var2) {
      OidTree.Node var3;
      boolean var10;
      Object var16;
      label155: {
         label151: {
            OidTree.Node var10000;
            label156: {
               SnmpOid var17;
               label149: {
                  var10 = SnmpMibNode.b;
                  if (this.isPrepareForAccessSupported() && var2) {
                     label145: {
                        if (var1 == null) {
                           var3 = this.c.getRoot();
                           if (!var10) {
                              break label145;
                           }
                        }

                        var3 = this.c.getClosest(var1.toLongArray(false), var1.getLength());
                     }

                     OidTree.Node var4 = var3;
                     boolean var5 = false;

                     while(var4 != null) {
                        if (var4.info != null) {
                           SnmpMibNode var6 = (SnmpMibNode)var4.info;
                           if (var6.getNodeType() != 3) {
                              break;
                           }

                           var6.prepareForAccess();
                           if (!var10) {
                              break;
                           }
                        }

                        var4 = var4.parent;
                        if (var10) {
                           break;
                        }
                     }

                     if (!var5) {
                        OidTree.Node var13 = var3;

                        while(var13 != null) {
                           var10000 = var13.nextNode();
                           if (var10) {
                              break label156;
                           }

                           var13 = var10000;

                           while(var13 != null) {
                              var16 = var13.info;
                              if (var10) {
                                 break label155;
                              }

                              if (var16 != null) {
                                 break;
                              }

                              var13 = var13.nextNode();
                              if (var10) {
                                 break;
                              }
                           }

                           if (var13 != null) {
                              SnmpMibNode var7 = (SnmpMibNode)var13.info;
                              var17 = var7.getOid();
                              if (var10) {
                                 break label149;
                              }

                              if (var17.compareTo(var1) > 0) {
                                 if (var7.getNodeType() != 3) {
                                    break;
                                 }

                                 var7.prepareForAccess();
                              }

                              if (var10) {
                                 break;
                              }
                           }
                        }
                     }
                  }

                  var17 = var1;
               }

               if (var17 == null) {
                  var3 = this.c.getRoot();
                  if (!var10) {
                     break label151;
                  }
               }

               var10000 = this.c.getClosest(var1.toLongArray(false), var1.getLength());
            }

            var3 = var10000;
         }

         if (var3 == null) {
            return null;
         }

         var16 = var3.info;
      }

      SnmpMibNode var11;
      SnmpOid var12;
      if (var16 != null) {
         var11 = (SnmpMibNode)var3.info;
         switch (var11.getNodeType()) {
            case 2:
               var12 = var11.getMaxOid();
               if (var12.compareTo(var1) > 0) {
                  return var11;
               }
               break;
            case 3:
               return var11;
         }
      }

      while(true) {
         var3 = var3.nextNode();

         label163: {
            while(var3 != null) {
               var16 = var3.info;
               if (var10) {
                  break label163;
               }

               if (var16 != null) {
                  break;
               }

               var3 = var3.nextNode();
               if (var10) {
                  break;
               }
            }

            if (var3 == null) {
               return null;
            }

            var16 = var3.info;
         }

         var11 = (SnmpMibNode)var16;
         if (var1 == null) {
            return var11;
         }

         switch (var11.getNodeType()) {
            case 1:
               var12 = var11.getOid();
               if (var12.compareTo(var1) > 0) {
                  return var11;
               }
               break;
            case 2:
               SnmpOid var14 = var11.getMaxOid();
               if (var14.compareTo(var1) > 0) {
                  return var11;
               }
               break;
            case 3:
               SnmpOid var15 = var11.getOid();
               if (var15.compareTo(var1) >= 0) {
                  return var11;
               }
               break;
            case 22:
               SnmpMibNode var8 = ((e)var11).getStartNode();
               SnmpOid var9 = var8.getMaxOid();
               if (var9.compareTo(var1) > 0) {
                  return var8;
               }
         }
      }
   }

   public synchronized SnmpMibNode getNextLeaf(SnmpOid var1) {
      SnmpMibNode var2 = null;

      Object var10000;
      while(true) {
         SnmpMibNode var3 = this.getNextNode(var1);
         if (var3 == null) {
            var10000 = null;
            if (!SnmpMibNode.b) {
               return null;
            }
            break;
         }

         if (var3.getNodeType() == 1) {
            return var3;
         }

         if (var2 == var3) {
            var10000 = null;
            break;
         }

         var1 = var3.getOid();
         var2 = var3;
      }

      return (SnmpMibNode)var10000;
   }

   public synchronized SnmpMibNode getNextNode(SnmpMibNode var1) {
      SnmpOid var2 = null;
      if (var1 != null) {
         var2 = var1.getOid();
      }

      return this.getNextNode(var2);
   }

   public synchronized SnmpMibNode getNextNode(SnmpOid var1) {
      OidTree.Node var2;
      boolean var4;
      label46: {
         var4 = SnmpMibNode.b;
         if (var1 == null) {
            var2 = this.c.getRoot();
            if (!var4) {
               break label46;
            }
         }

         var2 = this.c.get(var1.toLongArray(false), var1.getLength());
      }

      if (var2 == null) {
         return null;
      } else {
         SnmpMibNode var3;
         do {
            var2 = var2.nextNode();

            Object var10000;
            label50: {
               while(var2 != null) {
                  var10000 = var2.info;
                  if (var4) {
                     break label50;
                  }

                  if (var10000 != null) {
                     break;
                  }

                  var2 = var2.nextNode();
                  if (var4) {
                     break;
                  }
               }

               if (var2 == null) {
                  return null;
               }

               var10000 = var2.info;
            }

            var3 = (SnmpMibNode)var10000;
         } while(var3 instanceof e && !var4);

         return var3;
      }
   }

   public boolean isPrepareForAccessSupported() {
      return this.d;
   }

   public void isPrepareForAccessSupported(boolean var1) {
      this.d = var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append('{');
      var1.append(a("\"V>"));
      var1.append(this.a);
      var1.append('}');
      return var1.toString();
   }

   public String getString() {
      StringBuffer var1 = new StringBuffer();
      SnmpMibNode var2 = null;

      do {
         var2 = this.getNextNode(var2);
         if (var2 == null) {
            break;
         }

         var1.append(var2);
         var1.append('\n');
      } while(!SnmpMibNode.b);

      return var1.toString();
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 79;
               break;
            case 1:
               var10003 = 50;
               break;
            case 2:
               var10003 = 3;
               break;
            case 3:
               var10003 = 106;
               break;
            default:
               var10003 = 105;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
