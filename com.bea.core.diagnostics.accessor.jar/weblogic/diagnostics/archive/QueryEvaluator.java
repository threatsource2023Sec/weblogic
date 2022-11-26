package weblogic.diagnostics.archive;

import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.query.Query;
import weblogic.diagnostics.query.QueryException;
import weblogic.diagnostics.query.QueryFactory;
import weblogic.diagnostics.query.UnknownVariableException;
import weblogic.diagnostics.query.VariableResolver;

public final class QueryEvaluator implements VariableResolver {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticArchive");
   private DataArchive archive;
   private Query query;
   private DataRecord currentDataRecord;

   public QueryEvaluator(DataArchive archive, String queryString) throws QueryException {
      this.archive = archive;
      if (queryString != null && queryString.trim().length() > 0) {
         this.query = QueryFactory.createQuery(archive, queryString);
      }

   }

   public boolean evaluate(DataRecord record) {
      try {
         this.currentDataRecord = record;
         return this.query == null || this.query.executeQuery(this);
      } catch (Exception var3) {
         return false;
      }
   }

   public Object resolveVariable(String name) throws UnknownVariableException {
      return this.currentDataRecord != null ? this.archive.resolveVariable(this.currentDataRecord, name) : null;
   }

   public Object resolveVariable(int index) throws UnknownVariableException {
      return this.currentDataRecord != null ? this.archive.resolveVariable(this.currentDataRecord, index) : null;
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
      return this.resolveInteger(this.archive.getVariableIndex(varName));
   }

   public long resolveLong(String varName) throws UnknownVariableException {
      return this.resolveLong(this.archive.getVariableIndex(varName));
   }

   public float resolveFloat(String varName) throws UnknownVariableException {
      return this.resolveFloat(this.archive.getVariableIndex(varName));
   }

   public double resolveDouble(String varName) throws UnknownVariableException {
      return this.resolveDouble(this.archive.getVariableIndex(varName));
   }

   public String resolveString(String varName) throws UnknownVariableException {
      return this.resolveString(this.archive.getVariableIndex(varName));
   }
}
