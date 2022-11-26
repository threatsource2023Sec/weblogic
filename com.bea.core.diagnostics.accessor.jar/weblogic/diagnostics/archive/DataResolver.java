package weblogic.diagnostics.archive;

import java.util.HashMap;
import java.util.Map;
import weblogic.diagnostics.accessor.ColumnInfo;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.i18n.DiagnosticsTextTextFormatter;
import weblogic.diagnostics.query.UnknownVariableException;
import weblogic.diagnostics.query.VariableDeclarator;
import weblogic.diagnostics.query.VariableIndexResolver;
import weblogic.diagnostics.query.VariableResolver;

public class DataResolver implements VariableDeclarator, VariableResolver, VariableIndexResolver {
   private Map indexMap;
   private DataRecord dataRecord;
   private static final DiagnosticsTextTextFormatter DTF = DiagnosticsTextTextFormatter.getInstance();

   public DataResolver(Map indexMap) {
      this.indexMap = indexMap;
   }

   static Map computeIndex(ColumnInfo[] columns) {
      Map indexMap = new HashMap();

      for(int i = 0; i < columns.length; ++i) {
         ColumnInfo info = columns[i];
         String name = info.getColumnName();
         int type = info.getColumnType();
         byte type;
         switch (type) {
            case 1:
               type = 1;
               break;
            case 2:
               type = 2;
               break;
            case 3:
               type = 3;
               break;
            case 4:
               type = 4;
               break;
            default:
               type = 0;
         }

         int[] index = new int[]{i, type};
         indexMap.put(name, index);
      }

      return indexMap;
   }

   public int getVariableIndex(String varName) throws UnknownVariableException {
      int[] varInfo = (int[])((int[])this.indexMap.get(varName));
      if (varInfo == null) {
         throw new UnknownVariableException(DTF.getUnknownVariableMsg(varName));
      } else {
         return varInfo[0];
      }
   }

   public int getVariableType(String varName) throws UnknownVariableException {
      int[] varInfo = (int[])((int[])this.indexMap.get(varName));
      if (varInfo == null) {
         throw new UnknownVariableException(DTF.getUnknownVariableMsg(varName));
      } else {
         return varInfo[1];
      }
   }

   public void setDataRecord(DataRecord dataRecord) {
      this.dataRecord = dataRecord;
   }

   public DataRecord getDataRecord() {
      return this.dataRecord;
   }

   public Object resolveVariable(String name) throws UnknownVariableException {
      return this.resolveVariable(this.getVariableIndex(name));
   }

   public Object resolveVariable(int index) throws UnknownVariableException {
      return this.dataRecord != null ? this.dataRecord.get(index) : null;
   }

   public int resolveInteger(int index) throws UnknownVariableException {
      Object o = this.resolveVariable(index);
      int val = 0;
      if (o != null) {
         if (o instanceof Number) {
            return ((Number)o).intValue();
         }

         try {
            val = Integer.parseInt(o.toString());
         } catch (Exception var5) {
         }
      }

      return val;
   }

   public long resolveLong(int index) throws UnknownVariableException {
      Object o = this.resolveVariable(index);
      long val = 0L;
      if (o != null) {
         if (o instanceof Number) {
            return ((Number)o).longValue();
         }

         try {
            val = Long.parseLong(o.toString());
         } catch (Exception var6) {
         }
      }

      return val;
   }

   public float resolveFloat(int index) throws UnknownVariableException {
      Object o = this.resolveVariable(index);
      float val = 0.0F;
      if (o != null) {
         if (o instanceof Number) {
            return ((Number)o).floatValue();
         }

         try {
            val = Float.parseFloat(o.toString());
         } catch (Exception var5) {
         }
      }

      return val;
   }

   public double resolveDouble(int index) throws UnknownVariableException {
      Object o = this.resolveVariable(index);
      double val = 0.0;
      if (o != null) {
         if (o instanceof Number) {
            return ((Number)o).doubleValue();
         }

         try {
            val = Double.parseDouble(o.toString());
         } catch (Exception var6) {
         }
      }

      return val;
   }

   public String resolveString(int index) throws UnknownVariableException {
      Object o = this.resolveVariable(index);
      return o != null ? o.toString() : null;
   }

   public int resolveInteger(String varName) throws UnknownVariableException {
      return this.resolveInteger(this.getVariableIndex(varName));
   }

   public long resolveLong(String varName) throws UnknownVariableException {
      return this.resolveLong(this.getVariableIndex(varName));
   }

   public float resolveFloat(String varName) throws UnknownVariableException {
      return this.resolveFloat(this.getVariableIndex(varName));
   }

   public double resolveDouble(String varName) throws UnknownVariableException {
      return this.resolveDouble(this.getVariableIndex(varName));
   }

   public String resolveString(String varName) throws UnknownVariableException {
      return this.resolveString(this.getVariableIndex(varName));
   }
}
