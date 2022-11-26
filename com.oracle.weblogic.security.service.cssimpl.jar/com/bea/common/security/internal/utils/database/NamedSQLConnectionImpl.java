package com.bea.common.security.internal.utils.database;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NamedSQLConnectionImpl implements NamedSQLConnection, NamedSQLProxy.Interceptor {
   private String name;
   private Connection connection;
   private ASIDBPoolConnection asiConnection;

   private NamedSQLConnectionImpl(String name, ASIDBPoolConnection asiConnection) throws SQLException {
      this.name = name;
      this.asiConnection = asiConnection;
      this.connection = asiConnection.getConnection();
   }

   public boolean handle(Method method) {
      String name = method.getName();
      return "prepareStatement".equals(name) || "getName".equals(name) || "getASIConnection".equals(name);
   }

   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      if (AccessController.class.isAssignableFrom(method.getDeclaringClass())) {
         throw new IllegalArgumentException("Unable to invoke method");
      } else {
         String name = method.getName();
         if ("prepareStatement".equals(name)) {
            PreparedStatement statement = (PreparedStatement)method.invoke(this.connection, args);
            return NamedSQLProxy.createProxy(this.asiConnection, statement, new Class[]{PreparedStatement.class}, (NamedSQLProxy.Interceptor)null);
         } else {
            return method.invoke(this, args);
         }
      }
   }

   public String getName() {
      return this.name;
   }

   public ASIDBPoolConnection getASIConnection() {
      return this.asiConnection;
   }

   public static Connection createNamedSQLConnectionImpl(String name, ASIDBPoolConnection asiConnection) throws SQLException {
      NamedSQLConnectionImpl proxyConnection = new NamedSQLConnectionImpl(name, asiConnection);
      Connection proxy = (Connection)NamedSQLProxy.createProxy(proxyConnection.getASIConnection(), asiConnection.getConnection(), new Class[]{NamedSQLConnection.class, Connection.class}, proxyConnection);
      return proxy;
   }
}
