package monfox.toolkit.snmp.agent.proxy;

import java.util.Iterator;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpInt;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpString;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpAgent;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibLeaf;
import monfox.toolkit.snmp.agent.SnmpMibTable;
import monfox.toolkit.snmp.agent.SnmpMibTableRow;
import monfox.toolkit.snmp.agent.target.SnmpTargetParamsTable;
import monfox.toolkit.snmp.agent.tc.StorageType;
import monfox.toolkit.snmp.engine.SnmpContext;
import monfox.toolkit.snmp.engine.SnmpEngineID;
import monfox.toolkit.snmp.metadata.SnmpMetadata;

public class SnmpProxyTable extends SnmpMibTable {
   public static final int READ_TYPE = 1;
   public static final int WRITE_TYPE = 2;
   public static final int TRAP_TYPE = 3;
   public static final int INFORM_TYPE = 4;
   private static final int LEAF = 1;
   private static final int RANGE = 2;
   private static final int RANGE_END = 3;
   private static final int SUB_TREE = 4;
   private static final int a = 5;
   private static final int b = 6;
   private static final int c = 7;
   private static final int d = 8;
   private static final int e = 9;
   private SnmpAgent f;
   private Logger l = Logger.getInstance(a("lp\u001eUb"), a("id\u0015Vf\u0005s\u0002Wjq"), a("{M=hbZL(afIA<}"));

   SnmpProxyTable(SnmpAgent var1, SnmpMetadata var2) throws SnmpMibException, SnmpValueException {
      super(new SnmpOid(var2, a("[M=hbZL(afIA<}")));
      this.isSettableWhenActive(false);
      this.f = var1;
      this.setFactory(8, StorageType.getFactory(3));
   }

   public Row addWriteProxy(String var1, SnmpEngineID var2, String var3, String var4, String var5) throws SnmpMibException, SnmpValueException {
      return this.addRequestProxy(var1, 2, var2, var3, var4, var5);
   }

   public Row addReadProxy(String var1, SnmpEngineID var2, String var3, String var4, String var5) throws SnmpMibException, SnmpValueException {
      return this.addRequestProxy(var1, 1, var2, var3, var4, var5);
   }

   public Row addRequestProxy(String var1, int var2, SnmpEngineID var3, String var4, String var5, String var6) throws SnmpMibException, SnmpValueException {
      int var8 = SnmpProxyForwarder.g;
      switch (var2) {
         case 1:
         case 2:
            if (var8 == 0) {
               if (var3 == null) {
                  var3 = this.f.getEngine().getEngineID();
               }

               if (var1 == null) {
                  throw new NullPointerException(a("EJ#k[FDpvSEF"));
               }

               SnmpMibTableRow var7 = this.initRow(new SnmpValue[]{new SnmpString(var1)});
               var7.getLeaf(2).setValue((SnmpValue)(new SnmpInt(var2)));
               var7.getLeaf(3).setValue((SnmpValue)(new SnmpString(var3.toByteArray())));
               if (var4 != null) {
                  var7.getLeaf(4).setValue((SnmpValue)(new SnmpString(var4)));
               }

               var7.getLeaf(5).setValue((SnmpValue)(new SnmpString(var5)));
               var7.getLeaf(6).setValue((SnmpValue)(new SnmpString(var6)));
               this.addRow(var7);
               var7.getRowStatusLeaf().setActive();
               return this.b(var7);
            } else {
               SnmpException.b = !SnmpException.b;
            }
         default:
            throw new SnmpValueException(a("AM&y^AGph@G[)LKXF|8A@L%tV\bA58`mb\u0014Gfqs\u00150\u0003\u0001\u000fpO`aw\u0015Gfqs\u00150\u0000\u0001"));
      }
   }

   public void remove(String var1) throws SnmpMibException, SnmpValueException {
      SnmpMibTableRow var2 = this.removeRow(new SnmpValue[]{new SnmpString(var1)});
      if (var2 != null) {
         var2.destroy();
      }

   }

   public SnmpMibTableRow get(String var1) throws SnmpMibException, SnmpValueException {
      return this.getRow(new SnmpValue[]{new SnmpString(var1)});
   }

   public Row getProxyRowByName(String var1) throws SnmpMibException, SnmpValueException {
      return this.b(this.get(var1));
   }

   private Row b(SnmpMibTableRow var1) {
      if (var1 == null) {
         return null;
      } else if (var1.getUserObject() != null && var1.getUserObject() instanceof Row) {
         return (Row)var1.getUserObject();
      } else {
         Row var2 = new Row(var1);
         if (var1.getUserObject() == null) {
            var1.setUserObject(var1);
         }

         return var2;
      }
   }

   public Row getProxyRow(SnmpContext var1, int var2, int var3, String var4, int var5, int var6) {
      int var17 = SnmpProxyForwarder.g;
      SnmpEngineID var7 = var1.getContextEngineID();
      String var8 = var1.getContextName();
      SnmpString var9 = null;
      byte var10 = 0;
      switch (var6) {
         case 0:
            var10 = 1;
            if (var17 == 0) {
               break;
            }
         case 1:
            var10 = 2;
            if (var17 == 0) {
               break;
            }
         case 3:
            var10 = 3;
         case 2:
      }

      if (var7 != null) {
         var9 = new SnmpString(var7.toByteArray());
      }

      Iterator var11 = this.getRows();

      while(var11.hasNext()) {
         SnmpMibTableRow var12 = (SnmpMibTableRow)var11.next();
         if (var12.getRowStatusLeaf().isActive()) {
            if (this.l.isDebugEnabled()) {
               this.l.debug(a("KK5{YAM78BZL(a\u0012MM$jK\u0012\u0003") + var12);
            }

            Row var13 = this.b(var12);
            if (var13.getType() != var2) {
               this.l.debug(a("_Q?vU\bS\"wJQ\u0003$aBM\u0019p") + var13.getType() + a("\b\u0002m8") + var2);
               if (var17 == 0) {
                  continue;
               }
            }

            label116: {
               label131: {
                  boolean var10001;
                  label114: {
                     try {
                        if (var13.getContextEngineIDValue().equals(var9)) {
                           break label114;
                        }

                        this.l.debug(a("\bM?8QGM$}J\\f>\u007f[FF\u0019\\\u0012EB${Z"));
                     } catch (Exception var21) {
                        var10001 = false;
                        break label131;
                     }

                     if (var17 == 0) {
                        continue;
                     }
                  }

                  label108: {
                     try {
                        label136: {
                           SnmpMibLeaf var14 = var12.getLeaf(4);
                           if (var14 != null && (var8 == null || !var14.getValue().equals(var8))) {
                              break label116;
                           }

                           if (this.l.isDebugEnabled()) {
                              this.l.debug(a("NL%vV\bp>uBxQ?`K|B2tW\bQ?o\u0012EB${ZAM78WFD9vWag\u007f{]FW5`FfB=}\b") + var13.getName());
                           }

                           String var15 = var13.getTargetParamsIn();
                           if (var15 == null || var15.trim().equals("")) {
                              break label108;
                           }

                           SnmpTargetParamsTable.Row var16 = this.f.getTarget().getParamsTable().getRowByName(var15);
                           if (var16 == null) {
                              this.l.warn(a("FLpLSZD5lbIQ1uA|B2tW\bF>l@Q\u00036w@\u0012\u0003") + var15);
                              if (var17 == 0) {
                                 break label136;
                              }
                           }

                           if (!var16.getSecurityName().equals(var4)) {
                              this.l.debug(a("[F3m@AW)8\\IN5k\u0012LLpv]\\\u0003=yFKKj8") + var16.getSecurityName() + a("\b\u0002m8") + var4);
                              if (var17 == 0) {
                                 break label136;
                              }
                           }

                           if (var16.getMPModel() != var3) {
                              this.l.debug(a("EF#kSOFph@G@5kAAM78_GG5tA\bG?8\\GWpuS\\@8\"\u0012") + var16.getMPModel() + a("\b\u0002m8") + var3);
                              if (var17 == 0) {
                                 break label136;
                              }
                           }

                           if (var16.getSecurityModel() != var5) {
                              this.l.debug(a("[F3m@AW)8_GG5tA\bG?8\\GWpuS\\@8\"\u0012") + var16.getSecurityModel() + a("\b\u0002m8") + var5);
                              if (var17 == 0) {
                                 break label136;
                              }
                           }

                           if (var16.getSecurityLevel() > var10) {
                              this.l.debug(a("[F3m@AW)8^MU5tA\bG?8\\GWpuS\\@8\"\u0012") + var16.getSecurityLevel() + a("\b\u001dp") + var10);
                              if (var17 == 0) {
                                 break label136;
                              }
                           }

                           this.l.debug(a("EB${ZMPpLSZD5l{FS%lbIQ1uA\u0012\u0003") + var16.getName());
                           return new Row(var12);
                        }
                     } catch (Exception var20) {
                        var10001 = false;
                        break label131;
                     }

                     try {
                        if (var17 == 0) {
                           break label116;
                        }
                     } catch (Exception var19) {
                        var10001 = false;
                        break label131;
                     }
                  }

                  try {
                     this.l.debug(a("\u0005\u000epv]\bw1jUMW\u0000y@IN#LSJO58WFW\"a\u0012AG5vFAE9}V\b\u000b>wF\b@8}QCJ>\u007f\u001b"));
                     return new Row(var12);
                  } catch (Exception var18) {
                     var10001 = false;
                  }
               }

               this.l.debug(a("M[3}B\\J?v\u0012AMpt]KB$q\\O\u0003 j]PZp}\\\\Q)"));
            }

            if (var17 != 0) {
               break;
            }
         }
      }

      return null;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 40;
               break;
            case 1:
               var10003 = 35;
               break;
            case 2:
               var10003 = 80;
               break;
            case 3:
               var10003 = 24;
               break;
            default:
               var10003 = 50;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   public class Row {
      SnmpMibTableRow a;

      Row(SnmpMibTableRow var2) {
         this.a = var2;
      }

      public String getName() {
         try {
            return this.a.getLeaf(1).getValue().getString();
         } catch (Exception var2) {
            return null;
         }
      }

      public int getType() {
         try {
            return this.a.getLeaf(2).getValue().intValue();
         } catch (Exception var2) {
            return -1;
         }
      }

      public SnmpEngineID getContextEngineID() {
         try {
            return new SnmpEngineID(((SnmpString)this.a.getLeaf(3).getValue()).toByteArray());
         } catch (Exception var2) {
            return null;
         }
      }

      public SnmpString getContextEngineIDValue() {
         try {
            return (SnmpString)((SnmpString)this.a.getLeaf(3).getValue());
         } catch (Exception var2) {
            return null;
         }
      }

      public String getContextName() {
         try {
            return this.a.getLeaf(4).getValue().getString();
         } catch (Exception var2) {
            return null;
         }
      }

      public String getTargetParamsIn() {
         try {
            return this.a.getLeaf(5).getValue().getString();
         } catch (Exception var2) {
            return null;
         }
      }

      public String getSingleTargetOut() {
         try {
            return this.a.getLeaf(6).getValue().getString();
         } catch (Exception var2) {
            return null;
         }
      }

      public String getMultipleTargetOut() {
         try {
            return this.a.getLeaf(7).getValue().getString();
         } catch (Exception var2) {
            return null;
         }
      }

      public String toString() {
         return this.a.toString();
      }
   }
}
