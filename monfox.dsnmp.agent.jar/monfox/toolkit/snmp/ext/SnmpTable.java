package monfox.toolkit.snmp.ext;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import monfox.log.Logger;
import monfox.toolkit.snmp.NoSuchObjectException;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpNull;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.SnmpObjectInfo;
import monfox.toolkit.snmp.metadata.SnmpOidInfo;
import monfox.toolkit.snmp.metadata.SnmpTableInfo;
import monfox.toolkit.snmp.mgr.SnmpPeer;
import monfox.toolkit.snmp.mgr.SnmpPendingRequest;
import monfox.toolkit.snmp.mgr.SnmpResponseListener;
import monfox.toolkit.snmp.mgr.SnmpSession;
import monfox.toolkit.snmp.util.FormatUtil;

public class SnmpTable implements SnmpPollable, Serializable {
   public static final int DATA_VALID = 0;
   public static final int DATA_INVALID = 1;
   public static final int NOTIFY_NONE = 0;
   public static final int NOTIFY_UPDATES = 1;
   public static final int NOTIFY_CHANGES = 2;
   public static final int NOTIFY_ALL = 3;
   private static final int a = 1;
   private static final int b = 2;
   private static final int c = 4;
   private int d;
   private SnmpTableStatusChangeListener e;
   private static final SnmpNull f = new SnmpNull();
   private SnmpTableListener g;
   private SnmpObjectInfo[] h;
   private int[] i;
   private SnmpVarBindList j;
   private SnmpPeer k;
   private e l;
   private SnmpPendingRequest m;
   private boolean n;
   private SnmpMetadata o;
   private Hashtable p;
   private Vector q;
   private int r;
   private String s;
   private boolean t;
   private boolean u;
   private int v;
   Logger w;

   public SnmpTable() {
      int var1 = SnmpObjectSet.q;
      super();
      this.d = 1;
      this.e = null;
      this.g = null;
      this.h = new SnmpObjectInfo[0];
      this.i = null;
      this.j = null;
      this.k = null;
      this.l = null;
      this.m = null;
      this.n = true;
      this.o = null;
      this.p = new Hashtable();
      this.q = new Vector();
      this.r = 2;
      this.s = a("\u001cUm\u0001{\u001eU");
      this.t = false;
      this.u = false;
      this.v = 0;
      this.w = Logger.getInstance(a("-hH\"D"), a(",cR"), a(":Uk\u001f@\bYj\n"));
      this.l = new e(this);
      if (var1 != 0) {
         SnmpException.b = !SnmpException.b;
      }

   }

   public SnmpTable(SnmpMetadata var1, SnmpPeer var2, String var3) throws SnmpValueException {
      this();
      this.s = var3;
      this.setMetadata(var1);
      this.setPeer(var2);
      this.add(var3);
   }

   public SnmpTable(SnmpMetadata var1, SnmpPeer var2, String var3, String[] var4) throws SnmpValueException {
      this();
      this.s = var3;
      this.setMetadata(var1);
      this.setPeer(var2);
      this.add(var4);
   }

   public SnmpTable(SnmpPeer var1) {
      this();
      this.setPeer(var1);
   }

   public SnmpTable(SnmpPeer var1, String var2) throws SnmpValueException {
      this();
      this.s = var2;
      this.setPeer(var1);
      this.add(var2);
   }

   public SnmpTable(SnmpPeer var1, String var2, String[] var3) throws SnmpValueException {
      this();
      this.setPeer(var1);
      this.s = var2;
      this.add(var3);
   }

   public SnmpTable(SnmpPeer var1, String var2, SnmpVarBindList var3) {
      this();
      this.s = var2;
      this.setPeer(var1);
      this.setColumns(var3);
   }

   public String getName() {
      return this.s;
   }

   public SnmpMetadata getMetadata() {
      return this.o;
   }

   public void setMetadata(SnmpMetadata var1) {
      this.o = var1;
   }

   public void setPeer(SnmpPeer var1) {
      this.k = var1;
   }

   public SnmpPeer getPeer() {
      return this.k;
   }

   public SnmpTable add(String var1) throws SnmpValueException {
      return this.add(var1, true);
   }

   public SnmpTable add(String var1, boolean var2) throws SnmpValueException {
      SnmpOid var3 = new SnmpOid(this.getMetadata(), var1);
      return this.add(var3, var2);
   }

   public synchronized SnmpTable add(String[] var1) throws SnmpValueException {
      int var3 = SnmpObjectSet.q;
      int var2 = 0;

      SnmpTable var10000;
      while(true) {
         if (var2 < var1.length) {
            var10000 = this.add(var1[var2]);
            if (var3 != 0) {
               break;
            }

            ++var2;
            if (var3 == 0) {
               continue;
            }
         }

         var10000 = this;
         break;
      }

      return var10000;
   }

   public SnmpTable add(SnmpOid var1) throws SnmpValueException {
      return this.a(var1, true, 1);
   }

   public SnmpTable add(SnmpOid var1, boolean var2) throws SnmpValueException {
      return this.a(var1, var2, 1);
   }

   public SnmpTable remove(String var1) throws SnmpValueException {
      SnmpOid var2 = new SnmpOid(this.getMetadata(), var1);
      return this.remove(var2);
   }

   public synchronized SnmpTable remove(String[] var1) throws SnmpValueException {
      int var3 = SnmpObjectSet.q;
      int var2 = 0;

      SnmpTable var10000;
      while(true) {
         if (var2 < var1.length) {
            var10000 = this.remove(var1[var2]);
            if (var3 != 0) {
               break;
            }

            ++var2;
            if (var3 == 0) {
               continue;
            }
         }

         var10000 = this;
         break;
      }

      return var10000;
   }

   public void removeAll() throws SnmpValueException {
      if (this.j != null) {
         Enumeration var1 = this.j.getVarBinds();

         while(var1.hasMoreElements()) {
            SnmpVarBind var2 = (SnmpVarBind)var1.nextElement();
            this.a(var2.getOid(), true, 2);
            if (SnmpObjectSet.q != 0) {
               break;
            }
         }

      }
   }

   public SnmpTable remove(SnmpOid var1) throws SnmpValueException {
      return this.a(var1, true, 2);
   }

   public void monitor(SnmpOid var1) throws SnmpException {
      this.a(var1, true, 4);
   }

   public void ignore(SnmpOid var1) throws SnmpException {
      this.a(var1, false, 4);
   }

   public void monitor(String var1) throws SnmpException {
      SnmpOid var2 = new SnmpOid(this.getMetadata(), var1);
      this.a(var2, true, 4);
   }

   public void ignore(String var1) throws SnmpException {
      SnmpOid var2 = new SnmpOid(this.getMetadata(), var1);
      this.a(var2, false, 4);
   }

   private synchronized SnmpTable a(SnmpOid var1, boolean var2, int var3) throws SnmpValueException {
      int var14 = SnmpObjectSet.q;
      this.n = true;
      if (this.j == null) {
         this.j = new SnmpVarBindList(this.getMetadata());
      }

      SnmpOidInfo var5 = var1.getOidInfo();
      if (var5 == null) {
         throw new SnmpValueException(a(" Up\u000ex\u0000_& v\u0003^e\u001b.") + var1 + ".");
      } else {
         if (var5 instanceof SnmpTableInfo) {
            SnmpTableInfo var6 = (SnmpTableInfo)var5;
            SnmpObjectInfo[] var7 = var6.getColumns();
            SnmpNull var8 = var2 ? null : f;
            int var9 = 0;

            while(var9 < var7.length) {
               SnmpObjectInfo var10 = var7[var9];
               SnmpOid var11 = (SnmpOid)var10.getOid().clone();
               SnmpVarBind var12 = new SnmpVarBind(var11, var8, false);
               if (var14 != 0) {
                  return this;
               }

               label98: {
                  if (var3 == 1) {
                     if (!var10.isRead()) {
                        break label98;
                     }

                     this.j.add(var12);
                     if (var14 == 0) {
                        break label98;
                     }
                  }

                  if (var3 == 2) {
                     this.j.remove(var12);
                     if (var14 == 0) {
                        break label98;
                     }
                  }

                  SnmpVarBind var13 = this.j.get(var12.getOid());
                  if (var13 != null) {
                     var13.setValue((SnmpValue)var8);
                     if (var14 == 0) {
                        break label98;
                     }
                  }

                  this.j.add(var12);
               }

               ++var9;
               if (var14 != 0) {
                  break;
               }
            }

            if (var14 == 0) {
               return this;
            }
         }

         if (!(var5 instanceof SnmpObjectInfo)) {
            throw new SnmpValueException(a(" Up\u000ex\u0000_& v\u0003^e\u001b.") + var1 + ".");
         } else {
            label99: {
               SnmpNull var15 = var2 ? null : f;
               SnmpVarBind var16 = new SnmpVarBind(var1, var15, false);
               if (var3 == 1) {
                  this.j.add(var16);
                  if (var14 == 0) {
                     break label99;
                  }
               }

               if (var3 == 2) {
                  this.j.remove(var16);
                  if (var14 == 0) {
                     break label99;
                  }
               }

               SnmpVarBind var17 = this.j.get(var16.getOid());
               if (var17 != null) {
                  var17.setValue((SnmpValue)var15);
                  if (var14 == 0) {
                     break label99;
                  }
               }

               this.j.add(var16);
            }

            if (var14 != 0) {
               throw new SnmpValueException(a(" Up\u000ex\u0000_& v\u0003^e\u001b.") + var1 + ".");
            } else {
               return this;
            }
         }
      }
   }

   public void setColumns(SnmpVarBindList var1) {
      this.j = var1;
   }

   public void setMaxRepetitions(int var1) {
      this.v = var1;
   }

   public int getMaxRepetitions() {
      return this.v;
   }

   public void perform(SnmpSession var1) {
      try {
         SnmpObjectInfo[] var2;
         if (this.v > 0) {
            var2 = this.infoSet();
            this.m = var1.startBulkWalk(this.k, this.l, this.j, this.j.get(0).getOid(), this.v, false);
            this.m.setUserData(var2);
            if (SnmpObjectSet.q == 0) {
               return;
            }
         }

         var2 = this.infoSet();
         this.m = var1.startWalk(this.k, this.l, this.j, this.j.get(0).getOid(), false);
         this.m.setUserData(var2);
      } catch (SnmpException var3) {
         this.w.error(var3);
         this.a(new SnmpError(3, var3.getMessage(), var3));
      }

   }

   public void performUpdate(SnmpSession var1) throws SnmpException {
      try {
         SnmpObjectInfo[] var2;
         SnmpVarBindList var3;
         if (this.v > 0) {
            var2 = this.infoSet();
            var3 = var1.performBulkWalk(this.k, (SnmpResponseListener)null, this.j, this.j.get(0).getOid(), this.v, false);
            this.a(var2, var3, 0);
            if (SnmpObjectSet.q == 0) {
               return;
            }
         }

         var2 = this.infoSet();
         var3 = var1.performWalk(this.k, (SnmpResponseListener)null, this.j, this.j.get(0).getOid(), false);
         this.a(var2, var3, 0);
      } catch (SnmpException var4) {
         this.w.error(var4);
         this.a(new SnmpError(3, var4.getMessage(), var4));
         throw var4;
      }
   }

   public int getRowCount() {
      return this.q.size();
   }

   public Enumeration getRows() {
      return this.q.elements();
   }

   public SnmpRow getRow(int var1) {
      return (SnmpRow)this.q.get(var1);
   }

   public SnmpRow getRow(SnmpOid var1) {
      return (SnmpRow)this.p.get(var1);
   }

   public SnmpRow getRow(String var1) throws SnmpValueException {
      return this.getRow(new SnmpOid(var1));
   }

   public SnmpRow getRow(String var1, SnmpValue var2) throws SnmpValueException, NoSuchObjectException {
      Enumeration var3 = this.getRows();

      while(var3.hasMoreElements()) {
         SnmpRow var4 = (SnmpRow)var3.nextElement();
         SnmpValue var5 = var4.getByName(var1);
         if (var5.equals(var2)) {
            return var4;
         }

         if (SnmpObjectSet.q != 0) {
            break;
         }
      }

      return null;
   }

   public SnmpRow getRow(String var1, String var2) throws SnmpValueException, NoSuchObjectException {
      SnmpVarBind var3 = new SnmpVarBind(this.o, var1, var2);
      Enumeration var4 = this.getRows();

      while(var4.hasMoreElements()) {
         SnmpRow var5 = (SnmpRow)var4.nextElement();
         SnmpValue var6 = var5.getValue(var3.getOid());
         if (var6.equals(var3.getValue())) {
            return var5;
         }

         if (SnmpObjectSet.q != 0) {
            break;
         }
      }

      return null;
   }

   public Vector getRows(String var1, SnmpValue var2) throws SnmpValueException, NoSuchObjectException {
      Vector var3 = new Vector();
      Enumeration var4 = this.getRows();

      while(var4.hasMoreElements()) {
         SnmpRow var5 = (SnmpRow)var4.nextElement();
         SnmpValue var6 = var5.getByName(var1);
         if (var6.equals(var2)) {
            var3.addElement(var5);
         }

         if (SnmpObjectSet.q != 0) {
            break;
         }
      }

      return var3;
   }

   public Vector getRows(String var1, String var2) throws SnmpValueException, NoSuchObjectException {
      Vector var3 = new Vector();
      SnmpVarBind var4 = new SnmpVarBind(this.o, var1, var2);
      Enumeration var5 = this.getRows();

      while(var5.hasMoreElements()) {
         SnmpRow var6 = (SnmpRow)var5.nextElement();
         SnmpValue var7 = var6.getValue(var4.getOid());
         if (var7.equals(var4.getValue())) {
            var3.addElement(var6);
         }

         if (SnmpObjectSet.q != 0) {
            break;
         }
      }

      return var3;
   }

   public String toString() {
      return this.toString(true);
   }

   public String toString(boolean var1) {
      StringBuffer var2 = new StringBuffer();
      this.toString(var2, var1);
      return var2.toString();
   }

   public void toString(StringBuffer var1) {
      this.toString(var1, true);
   }

   public void toString(StringBuffer var1, boolean var2) {
      int var13 = SnmpObjectSet.q;
      SnmpObjectInfo[] var3 = this.infoSet();
      int var4 = var3.length;
      int var5 = this.p.size();
      String var6 = null;
      if (var2) {
         int var7 = this.getName().indexOf(a("=Zd\u0003q"));
         if (var7 > 0) {
            var6 = this.getName().substring(0, var7);
         }
      }

      Object[][] var15 = new Object[var5 + 1][var4];
      int var8 = 0;

      while(true) {
         if (var8 < var3.length) {
            String var9 = var3[var8].getName();
            if (var13 != 0) {
               break;
            }

            label65: {
               if (var6 != null && var9.startsWith(var6) && var9.length() > var6.length()) {
                  String var10 = var9.substring(var6.length());
                  var15[0][var8] = var10.substring(0, 1).toLowerCase() + var10.substring(1);
                  if (var13 == 0) {
                     break label65;
                  }
               }

               var15[0][var8] = var9;
            }

            ++var8;
            if (var13 == 0) {
               continue;
            }
         }

         var8 = 1;
         break;
      }

      Enumeration var16 = this.getRows();

      label54:
      while(true) {
         if (!var16.hasMoreElements()) {
            FormatUtil.formatTable(var15, var1);
            break;
         }

         SnmpRow var17 = (SnmpRow)var16.nextElement();
         if (var13 != 0) {
            break;
         }

         int var11 = 0;

         while(var11 < var4) {
            label48: {
               try {
                  SnmpVarBind var12 = var17.getVarBind(var11);
                  var15[var8][var11] = var12.getValueString();
               } catch (NoSuchObjectException var14) {
                  var15[var8][var11] = null;
                  break label48;
               }

               if (var13 != 0) {
                  continue label54;
               }
            }

            ++var11;
            if (var13 != 0) {
               break;
            }
         }

         ++var8;
         if (var13 != 0) {
         }
      }

   }

   protected synchronized SnmpObjectInfo[] infoSet() {
      if (!this.n) {
         return this.h;
      } else {
         this.a();
         return this.h;
      }
   }

   public synchronized int getColumnCount() {
      return this.infoSet().length;
   }

   public synchronized String[] getColumnNames() {
      int var4 = SnmpObjectSet.q;
      SnmpObjectInfo[] var1 = this.infoSet();
      String[] var2 = new String[var1.length];
      int var3 = 0;

      String[] var10000;
      while(true) {
         if (var3 < var1.length) {
            var10000 = var2;
            if (var4 != 0) {
               break;
            }

            var2[var3] = var1[var3].getName();
            ++var3;
            if (var4 == 0) {
               continue;
            }
         }

         var10000 = var2;
         break;
      }

      return var10000;
   }

   protected synchronized int[] varMask() {
      if (!this.n) {
         return this.i;
      } else {
         this.a();
         return this.i;
      }
   }

   private synchronized void a() {
      int var5 = SnmpObjectSet.q;
      int var1 = this.j.size();
      this.h = new SnmpObjectInfo[var1];
      this.i = null;
      int var2 = 0;

      while(true) {
         if (var2 < var1) {
            try {
               SnmpVarBind var3 = this.j.get(var2);
               this.h[var2] = (SnmpObjectInfo)var3.getOid().getOidInfo();
               SnmpValue var4 = var3.getValue();
               if (var5 != 0) {
                  break;
               }

               if (var4 == f) {
                  if (this.i == null) {
                     this.i = new int[var1];
                  }

                  this.i[var2] = 2;
               }
            } catch (ClassCastException var6) {
               this.h[var2] = null;
            }

            ++var2;
            if (var5 == 0) {
               continue;
            }
         }

         this.n = false;
         break;
      }

   }

   private synchronized void a(SnmpObjectInfo[] var1, SnmpVarBindList var2, int var3) {
      int var20 = SnmpObjectSet.q;
      if (var1 != this.infoSet()) {
         this.w.warn(a("\nTj\u001ay\u0007\u001bu\n`IXn\u000ez\u000e^bOp\u001cIo\u0001sINv\u000bu\u001d^*O}\u000eUi\u001d}\u0007\\&\u001ad\rZr\n"));
      } else {
         int[] var4 = this.varMask();
         int var5 = 0;
         int var6 = this.j.size();
         int var7 = var2.size();
         SnmpOid var8 = this.j.get(0).getOid();
         long var9 = System.currentTimeMillis();
         int var11 = 0;
         int var12 = 0;
         int var13 = this.p.size();

         int var10000;
         int var17;
         SnmpRow var28;
         while(true) {
            if (var5 + var6 <= var7) {
               label119: {
                  SnmpOid var15;
                  boolean var10001;
                  label118: {
                     try {
                        SnmpOid var14 = var2.get(var5).getOid();
                        var15 = var14.suboid(var8.getLength());
                        var10000 = this.u;
                        if (var20 != 0 || var20 != 0) {
                           break;
                        }

                        if (var10000 == 0) {
                           break label118;
                        }

                        boolean var16 = false;
                        var17 = 0;

                        boolean var27;
                        while(true) {
                           if (var17 < var6) {
                              label129: {
                                 SnmpOid var18 = var1[var17].getOid();
                                 var27 = var18.contains(var2.get(var5 + var17).getOid());
                                 if (var20 != 0) {
                                    break;
                                 }

                                 if (!var27) {
                                    this.w.error(a("\u0000Up\u000ex\u0000_& ]-\u001bg\u0003}\u000eUk\nz\u001d\u001bo\u00014\u000e^r!q\u0011O&\u001dq\u001aKi\u0001g\f\u001bg\u001b4N") + var2.get(var5 + var17).getOid() + a("N\u0017&\nl\u0019^e\u001bq\r\u001bgO3") + var18 + a("N\u0015&\u0006s\u0007Tt\u0006z\u000e\u0015(A4"));
                                    var16 = true;
                                    if (var20 == 0) {
                                       break label129;
                                    }
                                 }

                                 SnmpOid var19 = var2.get(var5 + var17).getOid().suboid(var8.getLength());
                                 if (!var15.equals(var19)) {
                                    this.w.error(a("\u0000Up\u000ex\u0000_&\f{\u0005Nk\u00014\u0000Ub\nlIRhOs\fOH\nl\u001d\u001bt\ng\u0019Th\u001cqIZrO3") + var2.get(var5 + var17).getOid() + a("N\u0017&\nl\u0019^e\u001bq\r\u001bg\u00014\u0000Ub\nlIT`O3") + var15 + a("N\u0017&\t{\u001cUbO3") + var19 + a("N\u0015&\u0006s\u0007Tt\u0006z\u000e\u0015(A4"));
                                    var16 = true;
                                    if (var20 == 0) {
                                       break label129;
                                    }
                                 }

                                 ++var17;
                                 if (var20 == 0) {
                                    continue;
                                 }
                              }
                           }

                           var27 = var16;
                           break;
                        }

                        if (!var27) {
                           break label118;
                        }

                        var5 += var6;
                     } catch (SnmpValueException var23) {
                        var10001 = false;
                        break label119;
                     }

                     if (var20 == 0) {
                        continue;
                     }
                  }

                  try {
                     if (var15.getLength() > 0) {
                        label134: {
                           var28 = this.getRow(var15);
                           if (var28 == null) {
                              var28 = new SnmpRow(this.o, this.infoSet(), this.varMask(), var15);
                              var28.setName(this.getName());
                              var28.updateValues(var2, var5, var9);
                              var28.setNotificationMask(this.r);
                              var28.addObjectSetListener(this.l);
                              var17 = this.q.size();
                              this.q.add(var28);
                              this.p.put(var15, var28);
                              ++var12;
                              this.a(var28, var17);
                              ++var11;
                              if (var20 == 0) {
                                 break label134;
                              }
                           }

                           if (var28.isEnabled()) {
                              var28.updateValues(var2, var5, var9, var1, var4);
                              ++var11;
                              if (var20 == 0) {
                                 break label134;
                              }
                           }

                           var28.updateValues((SnmpVarBindList)null, -1, var9, (SnmpObjectInfo[])null, (int[])null);
                        }
                     }
                  } catch (SnmpValueException var22) {
                     var10001 = false;
                  }
               }

               var5 += var6;
               if (var20 == 0) {
                  continue;
               }
            }

            var10000 = 0;
            break;
         }

         label73: {
            int var24 = var10000;
            if (var12 + var13 != var11) {
               Enumeration var25 = this.p.elements();

               while(var25.hasMoreElements()) {
                  var28 = (SnmpRow)var25.nextElement();
                  long var29;
                  var10000 = (var29 = var28.getTimestamp() - var9) == 0L ? 0 : (var29 < 0L ? -1 : 1);
                  if (var20 != 0) {
                     break label73;
                  }

                  if (var10000 != 0) {
                     var17 = this.q.indexOf(var28);
                     this.p.remove(var28.getRawIndex());
                     this.q.remove(var17);
                     ++var24;
                     this.b(var28, var17);
                  }

                  if (var20 != 0) {
                     break;
                  }
               }
            }

            var10000 = this.t;
         }

         if (var10000 != 0 && var11 == 0) {
            SnmpTableListener var26 = this.g;
            if (var26 != null) {
               try {
                  var26.handleUpdated(this, (SnmpRow)null, (int[])null, -1);
               } catch (Exception var21) {
                  this.w.error(a("\fIt\u0000fIRhO|\bUb\u0003q<Kb\u000e`\f_&\u0018}\u001dS&Hz\u001cWjH4\u001bTq"), var21);
               }
            }
         }

         this.a(0);
      }
   }

   void a(SnmpPendingRequest var1, int var2, int var3, SnmpVarBindList var4) {
      if (var2 == 0) {
         SnmpObjectInfo[] var5 = (SnmpObjectInfo[])((SnmpObjectInfo[])var1.getUserData());
         this.a(var5, var4, 0);
      }

      if (var2 == 5) {
         if (var3 - 1 >= var4.size()) {
            this.a(new SnmpError(2, a(",It\u0000fIic\u001cd\u0006Uu\n.2") + Snmp.errorStatusToString(var2) + "]", var1));
         } else {
            this.a(new SnmpError(2, a(",It\u0000fIic\u001cd\u0006Uu\n.2") + Snmp.errorStatusToString(var2) + ":" + var4.get(var3 - 1) + "]", var1));
         }
      }
   }

   void b(SnmpPendingRequest var1, int var2, int var3, SnmpVarBindList var4) {
      SnmpObjectInfo[] var5 = (SnmpObjectInfo[])((SnmpObjectInfo[])var1.getUserData());
      this.a(var5, var4, 0);
      if (var2 != 0) {
         if (var3 - 1 >= var4.size()) {
            this.a(new SnmpError(2, a(",It\u0000fIic\u001cd\u0006Uu\n.2") + Snmp.errorStatusToString(var2) + "]", var1));
         } else {
            this.a(new SnmpError(2, a(",It\u0000fIic\u001cd\u0006Uu\n.2") + Snmp.errorStatusToString(var2) + ":" + var4.get(var3 - 1) + "]", var1));
         }
      }
   }

   void a(SnmpPendingRequest var1) {
      this.a(new SnmpError(1, a("=Rk\n{\u001cO")));
   }

   void a(SnmpPendingRequest var1, Exception var2) {
      this.w.error(a("\u0001Zh\u000bx\f~~\fq\u0019Oo\u0000z"), var2);
      this.a(new SnmpError(3, var2.toString(), var2));
   }

   public void addTableListener(SnmpTableListener var1) {
      this.g = monfox.toolkit.snmp.ext.f.add(this.g, var1);
   }

   public void removeTableListener(SnmpTableListener var1) {
      this.g = monfox.toolkit.snmp.ext.f.remove(this.g, var1);
   }

   public int getNotificationMask() {
      return this.r;
   }

   public void setNotificationMask(int var1) {
      this.r = var1;
   }

   void a(SnmpError var1) {
      this.a(1);
      if (this.g != null) {
         this.g.handleError(this, var1);
      }

   }

   void a(SnmpRow var1, int[] var2) {
      if (this.p.containsKey(var1.getRawIndex()) && this.g != null) {
         int var3 = this.q.indexOf(var1);
         this.g.handleUpdated(this, var1, var2, var3);
      }

   }

   void a(SnmpRow var1, int var2) {
      if (this.g != null) {
         this.g.handleCreated(this, var1, var2);
      }

   }

   void b(SnmpRow var1, int var2) {
      if (this.g != null) {
         this.g.handleDeleted(this, var1, var2);
      }

   }

   public void isCheckAlignment(boolean var1) {
      this.u = var1;
   }

   public boolean isCheckAlignment() {
      return this.u;
   }

   public void isNotifyUpdatedOnEmpty(boolean var1) {
      this.t = var1;
   }

   public boolean isNotifyUpdatedOnEmpty() {
      return this.t;
   }

   public int getStatus() {
      return this.d;
   }

   void a(int var1) {
      synchronized(this) {
         if (this.d == var1) {
            return;
         }

         this.d = var1;
      }

      SnmpTableStatusChangeListener var2 = this.e;
      if (var2 != null) {
         try {
            var2.handleStatusChange(this);
         } catch (Throwable var4) {
            var4.printStackTrace();
         }
      }

   }

   public void setStatusChangeListener(SnmpTableStatusChangeListener var1) {
      this.e = var1;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 105;
               break;
            case 1:
               var10003 = 59;
               break;
            case 2:
               var10003 = 6;
               break;
            case 3:
               var10003 = 111;
               break;
            default:
               var10003 = 20;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
