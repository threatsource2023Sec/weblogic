package weblogic.jdbc.rmi.internal;

import java.sql.SQLException;

public class ParameterMetaDataImpl implements ParameterMetaData {
   private java.sql.ParameterMetaData t2_pmd = null;

   public ParameterMetaDataImpl(java.sql.ParameterMetaData pmd) {
      this.t2_pmd = pmd;
   }

   public ParameterMetaDataImpl() {
   }

   public String getParameterClassName(int param) throws SQLException {
      return this.t2_pmd.getParameterClassName(param);
   }

   public int getParameterCount() throws SQLException {
      return this.t2_pmd.getParameterCount();
   }

   public int getParameterMode(int param) throws SQLException {
      return this.t2_pmd.getParameterMode(param);
   }

   public int getParameterType(int param) throws SQLException {
      return this.t2_pmd.getParameterType(param);
   }

   public String getParameterTypeName(int param) throws SQLException {
      return this.t2_pmd.getParameterTypeName(param);
   }

   public int getPrecision(int param) throws SQLException {
      return this.t2_pmd.getPrecision(param);
   }

   public int getScale(int param) throws SQLException {
      return this.t2_pmd.getScale(param);
   }

   public int isNullable(int param) throws SQLException {
      return this.t2_pmd.isNullable(param);
   }

   public boolean isSigned(int param) throws SQLException {
      return this.t2_pmd.isSigned(param);
   }

   public Object unwrap(Class iface) throws SQLException {
      if (iface.isInstance(this)) {
         return iface.cast(this);
      } else {
         throw new SQLException(this + " is not an instance of " + iface);
      }
   }

   public boolean isWrapperFor(Class iface) throws SQLException {
      return iface.isInstance(this);
   }
}
