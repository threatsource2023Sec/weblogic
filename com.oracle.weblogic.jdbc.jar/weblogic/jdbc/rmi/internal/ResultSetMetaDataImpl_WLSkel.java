package weblogic.jdbc.rmi.internal;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.UnmarshalException;
import java.sql.Wrapper;
import weblogic.rmi.internal.Skeleton;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.OutboundResponse;

public final class ResultSetMetaDataImpl_WLSkel extends Skeleton {
   // $FF: synthetic field
   private static Class class$java$lang$Class;
   // $FF: synthetic field
   private static Class class$java$lang$Object;
   // $FF: synthetic field
   private static Class class$java$lang$String;

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public OutboundResponse invoke(int var1, InboundRequest var2, OutboundResponse var3, Object var4) throws Exception {
      Class var5;
      int var77;
      switch (var1) {
         case 0:
            try {
               MsgInput var6 = var2.getMsgInput();
               var77 = var6.readInt();
            } catch (IOException var76) {
               throw new UnmarshalException("error unmarshalling arguments", var76);
            }

            String var78 = ((java.sql.ResultSetMetaData)var4).getCatalogName(var77);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var78, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var75) {
               throw new MarshalException("error marshalling return", var75);
            }
         case 1:
            try {
               MsgInput var7 = var2.getMsgInput();
               var77 = var7.readInt();
            } catch (IOException var74) {
               throw new UnmarshalException("error unmarshalling arguments", var74);
            }

            String var79 = ((java.sql.ResultSetMetaData)var4).getColumnClassName(var77);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var79, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var73) {
               throw new MarshalException("error marshalling return", var73);
            }
         case 2:
            var77 = ((java.sql.ResultSetMetaData)var4).getColumnCount();
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeInt(var77);
               break;
            } catch (IOException var72) {
               throw new MarshalException("error marshalling return", var72);
            }
         case 3:
            try {
               MsgInput var8 = var2.getMsgInput();
               var77 = var8.readInt();
            } catch (IOException var71) {
               throw new UnmarshalException("error unmarshalling arguments", var71);
            }

            int var80 = ((java.sql.ResultSetMetaData)var4).getColumnDisplaySize(var77);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeInt(var80);
               break;
            } catch (IOException var70) {
               throw new MarshalException("error marshalling return", var70);
            }
         case 4:
            try {
               MsgInput var9 = var2.getMsgInput();
               var77 = var9.readInt();
            } catch (IOException var69) {
               throw new UnmarshalException("error unmarshalling arguments", var69);
            }

            String var81 = ((java.sql.ResultSetMetaData)var4).getColumnLabel(var77);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var81, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var68) {
               throw new MarshalException("error marshalling return", var68);
            }
         case 5:
            try {
               MsgInput var10 = var2.getMsgInput();
               var77 = var10.readInt();
            } catch (IOException var67) {
               throw new UnmarshalException("error unmarshalling arguments", var67);
            }

            String var82 = ((java.sql.ResultSetMetaData)var4).getColumnName(var77);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var82, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var66) {
               throw new MarshalException("error marshalling return", var66);
            }
         case 6:
            try {
               MsgInput var11 = var2.getMsgInput();
               var77 = var11.readInt();
            } catch (IOException var65) {
               throw new UnmarshalException("error unmarshalling arguments", var65);
            }

            int var83 = ((java.sql.ResultSetMetaData)var4).getColumnType(var77);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeInt(var83);
               break;
            } catch (IOException var64) {
               throw new MarshalException("error marshalling return", var64);
            }
         case 7:
            try {
               MsgInput var12 = var2.getMsgInput();
               var77 = var12.readInt();
            } catch (IOException var63) {
               throw new UnmarshalException("error unmarshalling arguments", var63);
            }

            String var84 = ((java.sql.ResultSetMetaData)var4).getColumnTypeName(var77);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var84, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var62) {
               throw new MarshalException("error marshalling return", var62);
            }
         case 8:
            try {
               MsgInput var13 = var2.getMsgInput();
               var77 = var13.readInt();
            } catch (IOException var61) {
               throw new UnmarshalException("error unmarshalling arguments", var61);
            }

            int var85 = ((java.sql.ResultSetMetaData)var4).getPrecision(var77);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeInt(var85);
               break;
            } catch (IOException var60) {
               throw new MarshalException("error marshalling return", var60);
            }
         case 9:
            try {
               MsgInput var14 = var2.getMsgInput();
               var77 = var14.readInt();
            } catch (IOException var59) {
               throw new UnmarshalException("error unmarshalling arguments", var59);
            }

            int var86 = ((java.sql.ResultSetMetaData)var4).getScale(var77);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeInt(var86);
               break;
            } catch (IOException var58) {
               throw new MarshalException("error marshalling return", var58);
            }
         case 10:
            try {
               MsgInput var15 = var2.getMsgInput();
               var77 = var15.readInt();
            } catch (IOException var57) {
               throw new UnmarshalException("error unmarshalling arguments", var57);
            }

            String var87 = ((java.sql.ResultSetMetaData)var4).getSchemaName(var77);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var87, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var56) {
               throw new MarshalException("error marshalling return", var56);
            }
         case 11:
            try {
               MsgInput var16 = var2.getMsgInput();
               var77 = var16.readInt();
            } catch (IOException var55) {
               throw new UnmarshalException("error unmarshalling arguments", var55);
            }

            String var88 = ((java.sql.ResultSetMetaData)var4).getTableName(var77);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var88, class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String);
               break;
            } catch (IOException var54) {
               throw new MarshalException("error marshalling return", var54);
            }
         case 12:
            try {
               MsgInput var17 = var2.getMsgInput();
               var77 = var17.readInt();
            } catch (IOException var53) {
               throw new UnmarshalException("error unmarshalling arguments", var53);
            }

            boolean var89 = ((java.sql.ResultSetMetaData)var4).isAutoIncrement(var77);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var89);
               break;
            } catch (IOException var52) {
               throw new MarshalException("error marshalling return", var52);
            }
         case 13:
            try {
               MsgInput var18 = var2.getMsgInput();
               var77 = var18.readInt();
            } catch (IOException var51) {
               throw new UnmarshalException("error unmarshalling arguments", var51);
            }

            boolean var90 = ((java.sql.ResultSetMetaData)var4).isCaseSensitive(var77);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var90);
               break;
            } catch (IOException var50) {
               throw new MarshalException("error marshalling return", var50);
            }
         case 14:
            try {
               MsgInput var19 = var2.getMsgInput();
               var77 = var19.readInt();
            } catch (IOException var49) {
               throw new UnmarshalException("error unmarshalling arguments", var49);
            }

            boolean var91 = ((java.sql.ResultSetMetaData)var4).isCurrency(var77);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var91);
               break;
            } catch (IOException var48) {
               throw new MarshalException("error marshalling return", var48);
            }
         case 15:
            try {
               MsgInput var20 = var2.getMsgInput();
               var77 = var20.readInt();
            } catch (IOException var47) {
               throw new UnmarshalException("error unmarshalling arguments", var47);
            }

            boolean var92 = ((java.sql.ResultSetMetaData)var4).isDefinitelyWritable(var77);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var92);
               break;
            } catch (IOException var46) {
               throw new MarshalException("error marshalling return", var46);
            }
         case 16:
            try {
               MsgInput var21 = var2.getMsgInput();
               var77 = var21.readInt();
            } catch (IOException var45) {
               throw new UnmarshalException("error unmarshalling arguments", var45);
            }

            int var93 = ((java.sql.ResultSetMetaData)var4).isNullable(var77);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeInt(var93);
               break;
            } catch (IOException var44) {
               throw new MarshalException("error marshalling return", var44);
            }
         case 17:
            try {
               MsgInput var22 = var2.getMsgInput();
               var77 = var22.readInt();
            } catch (IOException var43) {
               throw new UnmarshalException("error unmarshalling arguments", var43);
            }

            boolean var94 = ((java.sql.ResultSetMetaData)var4).isReadOnly(var77);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var94);
               break;
            } catch (IOException var42) {
               throw new MarshalException("error marshalling return", var42);
            }
         case 18:
            try {
               MsgInput var23 = var2.getMsgInput();
               var77 = var23.readInt();
            } catch (IOException var41) {
               throw new UnmarshalException("error unmarshalling arguments", var41);
            }

            boolean var95 = ((java.sql.ResultSetMetaData)var4).isSearchable(var77);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var95);
               break;
            } catch (IOException var40) {
               throw new MarshalException("error marshalling return", var40);
            }
         case 19:
            try {
               MsgInput var24 = var2.getMsgInput();
               var77 = var24.readInt();
            } catch (IOException var39) {
               throw new UnmarshalException("error unmarshalling arguments", var39);
            }

            boolean var96 = ((java.sql.ResultSetMetaData)var4).isSigned(var77);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var96);
               break;
            } catch (IOException var38) {
               throw new MarshalException("error marshalling return", var38);
            }
         case 20:
            try {
               MsgInput var25 = var2.getMsgInput();
               var5 = (Class)var25.readObject(class$java$lang$Class == null ? (class$java$lang$Class = class$("java.lang.Class")) : class$java$lang$Class);
            } catch (IOException var36) {
               throw new UnmarshalException("error unmarshalling arguments", var36);
            } catch (ClassNotFoundException var37) {
               throw new UnmarshalException("error unmarshalling arguments", var37);
            }

            boolean var97 = ((Wrapper)var4).isWrapperFor(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var97);
               break;
            } catch (IOException var35) {
               throw new MarshalException("error marshalling return", var35);
            }
         case 21:
            try {
               MsgInput var26 = var2.getMsgInput();
               var77 = var26.readInt();
            } catch (IOException var34) {
               throw new UnmarshalException("error unmarshalling arguments", var34);
            }

            boolean var98 = ((java.sql.ResultSetMetaData)var4).isWritable(var77);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeBoolean(var98);
               break;
            } catch (IOException var33) {
               throw new MarshalException("error marshalling return", var33);
            }
         case 22:
            try {
               MsgInput var27 = var2.getMsgInput();
               var5 = (Class)var27.readObject(class$java$lang$Class == null ? (class$java$lang$Class = class$("java.lang.Class")) : class$java$lang$Class);
            } catch (IOException var31) {
               throw new UnmarshalException("error unmarshalling arguments", var31);
            } catch (ClassNotFoundException var32) {
               throw new UnmarshalException("error unmarshalling arguments", var32);
            }

            Object var28 = ((Wrapper)var4).unwrap(var5);
            this.associateResponseData(var2, var3);

            try {
               var3.getMsgOutput().writeObject(var28, class$java$lang$Object == null ? (class$java$lang$Object = class$("java.lang.Object")) : class$java$lang$Object);
               break;
            } catch (IOException var30) {
               throw new MarshalException("error marshalling return", var30);
            }
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }

      return var3;
   }

   public Object invoke(int var1, Object[] var2, Object var3) throws Exception {
      switch (var1) {
         case 0:
            return ((java.sql.ResultSetMetaData)var3).getCatalogName((Integer)var2[0]);
         case 1:
            return ((java.sql.ResultSetMetaData)var3).getColumnClassName((Integer)var2[0]);
         case 2:
            return new Integer(((java.sql.ResultSetMetaData)var3).getColumnCount());
         case 3:
            return new Integer(((java.sql.ResultSetMetaData)var3).getColumnDisplaySize((Integer)var2[0]));
         case 4:
            return ((java.sql.ResultSetMetaData)var3).getColumnLabel((Integer)var2[0]);
         case 5:
            return ((java.sql.ResultSetMetaData)var3).getColumnName((Integer)var2[0]);
         case 6:
            return new Integer(((java.sql.ResultSetMetaData)var3).getColumnType((Integer)var2[0]));
         case 7:
            return ((java.sql.ResultSetMetaData)var3).getColumnTypeName((Integer)var2[0]);
         case 8:
            return new Integer(((java.sql.ResultSetMetaData)var3).getPrecision((Integer)var2[0]));
         case 9:
            return new Integer(((java.sql.ResultSetMetaData)var3).getScale((Integer)var2[0]));
         case 10:
            return ((java.sql.ResultSetMetaData)var3).getSchemaName((Integer)var2[0]);
         case 11:
            return ((java.sql.ResultSetMetaData)var3).getTableName((Integer)var2[0]);
         case 12:
            return new Boolean(((java.sql.ResultSetMetaData)var3).isAutoIncrement((Integer)var2[0]));
         case 13:
            return new Boolean(((java.sql.ResultSetMetaData)var3).isCaseSensitive((Integer)var2[0]));
         case 14:
            return new Boolean(((java.sql.ResultSetMetaData)var3).isCurrency((Integer)var2[0]));
         case 15:
            return new Boolean(((java.sql.ResultSetMetaData)var3).isDefinitelyWritable((Integer)var2[0]));
         case 16:
            return new Integer(((java.sql.ResultSetMetaData)var3).isNullable((Integer)var2[0]));
         case 17:
            return new Boolean(((java.sql.ResultSetMetaData)var3).isReadOnly((Integer)var2[0]));
         case 18:
            return new Boolean(((java.sql.ResultSetMetaData)var3).isSearchable((Integer)var2[0]));
         case 19:
            return new Boolean(((java.sql.ResultSetMetaData)var3).isSigned((Integer)var2[0]));
         case 20:
            return new Boolean(((Wrapper)var3).isWrapperFor((Class)var2[0]));
         case 21:
            return new Boolean(((java.sql.ResultSetMetaData)var3).isWritable((Integer)var2[0]));
         case 22:
            return ((Wrapper)var3).unwrap((Class)var2[0]);
         default:
            throw new UnmarshalException("Method identifier [" + var1 + "] out of range");
      }
   }
}
