package weblogic.jdbc.rmi;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.SQLException;
import java.sql.SQLXML;
import weblogic.jdbc.wrapper.JDBCWrapperFactory;

public class SerialCallableStatement extends SerialPreparedStatement {
   private static final long serialVersionUID = 915412605233701370L;
   private CallableStatement rmi_cstmt = null;

   public SerialCallableStatement() {
   }

   public SerialCallableStatement(CallableStatement s, SerialConnection c) {
      this.init(s, c);
   }

   public void init(CallableStatement s, SerialConnection c) {
      super.init(s, c);
      this.rmi_cstmt = s;
   }

   public static CallableStatement makeSerialCallableStatement(CallableStatement s, SerialConnection conn) {
      if (s == null) {
         return null;
      } else {
         SerialCallableStatement serial_stmt = (SerialCallableStatement)JDBCWrapperFactory.getWrapper("weblogic.jdbc.rmi.SerialCallableStatement", s, false);
         serial_stmt.init(s, conn);
         return (CallableStatement)serial_stmt;
      }
   }

   public Blob getBlob(int parameterIndex) throws SQLException {
      Blob ret = null;
      String methodName = "getBlob";
      Object[] params = new Object[]{new Integer(parameterIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rmi_cstmt.getBlob(parameterIndex);
         if (ret != null) {
            ret = SerialOracleBlob.makeSerialOracleBlob(ret);
            ((SerialConnection)this.getConnection()).addToLobSet(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public Clob getClob(int parameterIndex) throws SQLException {
      Clob ret = null;
      String methodName = "getClob";
      Object[] params = new Object[]{new Integer(parameterIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rmi_cstmt.getClob(parameterIndex);
         if (ret != null) {
            ret = SerialOracleClob.makeSerialOracleClob(ret);
            ((SerialConnection)this.getConnection()).addToLobSet(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public Array getArray(int parameterIndex) throws SQLException {
      Array ret = null;
      String methodName = "getArray";
      Object[] params = new Object[]{new Integer(parameterIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rmi_cstmt.getArray(parameterIndex);
         if (ret != null) {
            ret = SerialArray.makeSerialArrayFromStub(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public Array getArray(String parameterName) throws SQLException {
      Array ret = null;
      String methodName = "getArray";
      Object[] params = new Object[]{parameterName};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rmi_cstmt.getArray(parameterName);
         if (ret != null) {
            ret = SerialArray.makeSerialArrayFromStub(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public Blob getBlob(String parameterName) throws SQLException {
      Blob ret = null;
      String methodName = "getBlob";
      Object[] params = new Object[]{parameterName};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rmi_cstmt.getBlob(parameterName);
         if (ret == null) {
            ret = SerialOracleBlob.makeSerialOracleBlob(ret);
            ((SerialConnection)this.getConnection()).addToLobSet(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public Clob getClob(String parameterName) throws SQLException {
      Clob ret = null;
      String methodName = "getClob";
      Object[] params = new Object[]{parameterName};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rmi_cstmt.getClob(parameterName);
         if (ret == null) {
            ret = SerialOracleClob.makeSerialOracleClob(ret);
            ((SerialConnection)this.getConnection()).addToLobSet(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public NClob getNClob(int parameterIndex) throws SQLException {
      NClob ret = null;
      String methodName = "getNClob";
      Object[] params = new Object[]{new Integer(parameterIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rmi_cstmt.getNClob(parameterIndex);
         if (ret == null) {
            ret = SerialOracleNClob.makeSerialOracleNClob(ret);
            ((SerialConnection)this.getConnection()).addToLobSet(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public NClob getNClob(String parameterName) throws SQLException {
      NClob ret = null;
      String methodName = "getNClob";
      Object[] params = new Object[]{parameterName};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rmi_cstmt.getNClob(parameterName);
         if (ret == null) {
            ret = SerialOracleNClob.makeSerialOracleNClob(ret);
            ((SerialConnection)this.getConnection()).addToLobSet(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public Ref getRef(int parameterIndex) throws SQLException {
      Ref ret = null;
      String methodName = "getRef";
      Object[] params = new Object[]{new Integer(parameterIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rmi_cstmt.getRef(parameterIndex);
         if (ret != null) {
            ret = SerialRef.makeSerialRefFromStub(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public Ref getRef(String parameterName) throws SQLException {
      Ref ret = null;
      String methodName = "getRef";
      Object[] params = new Object[]{parameterName};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rmi_cstmt.getRef(parameterName);
         if (ret != null) {
            ret = SerialRef.makeSerialRefFromStub(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public SQLXML getSQLXML(int parameterIndex) throws SQLException {
      SQLXML ret = null;
      String methodName = "getSQLXML";
      Object[] params = new Object[]{new Integer(parameterIndex)};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rmi_cstmt.getSQLXML(parameterIndex);
         if (ret != null) {
            ret = SerialSQLXML.makeSerialSQLXMLFromStub(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }

   public SQLXML getSQLXML(String parameterName) throws SQLException {
      SQLXML ret = null;
      String methodName = "getSQLXML";
      Object[] params = new Object[]{parameterName};

      try {
         this.preInvocationHandler(methodName, params);
         ret = this.rmi_cstmt.getSQLXML(parameterName);
         if (ret != null) {
            ret = SerialSQLXML.makeSerialSQLXMLFromStub(ret);
         }

         this.postInvocationHandler(methodName, params, ret);
      } catch (Exception var6) {
         this.invocationExceptionHandler(methodName, params, var6);
      }

      return ret;
   }
}
