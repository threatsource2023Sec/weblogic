package org.apache.openjpa.jdbc.kernel;

import java.util.Collection;
import java.util.Set;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.kernel.DelegatingFetchConfiguration;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.util.RuntimeExceptionTranslator;

public class DelegatingJDBCFetchConfiguration extends DelegatingFetchConfiguration implements JDBCFetchConfiguration {
   public DelegatingJDBCFetchConfiguration(JDBCFetchConfiguration delegate) {
      super(delegate);
   }

   public DelegatingJDBCFetchConfiguration(JDBCFetchConfiguration delegate, RuntimeExceptionTranslator trans) {
      super(delegate, trans);
   }

   public JDBCFetchConfiguration getJDBCDelegate() {
      return (JDBCFetchConfiguration)this.getDelegate();
   }

   public int getEagerFetchMode() {
      try {
         return this.getJDBCDelegate().getEagerFetchMode();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public JDBCFetchConfiguration setEagerFetchMode(int mode) {
      try {
         this.getJDBCDelegate().setEagerFetchMode(mode);
         return this;
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public int getSubclassFetchMode() {
      try {
         return this.getJDBCDelegate().getSubclassFetchMode();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public int getSubclassFetchMode(ClassMapping cls) {
      try {
         return this.getJDBCDelegate().getSubclassFetchMode(cls);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public JDBCFetchConfiguration setSubclassFetchMode(int mode) {
      try {
         this.getJDBCDelegate().setSubclassFetchMode(mode);
         return this;
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public int getResultSetType() {
      try {
         return this.getJDBCDelegate().getResultSetType();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public JDBCFetchConfiguration setResultSetType(int type) {
      try {
         this.getJDBCDelegate().setResultSetType(type);
         return this;
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public int getFetchDirection() {
      try {
         return this.getJDBCDelegate().getFetchDirection();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public JDBCFetchConfiguration setFetchDirection(int direction) {
      try {
         this.getJDBCDelegate().setFetchDirection(direction);
         return this;
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public int getLRSSize() {
      try {
         return this.getJDBCDelegate().getLRSSize();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public JDBCFetchConfiguration setLRSSize(int lrsSize) {
      try {
         this.getJDBCDelegate().setLRSSize(lrsSize);
         return this;
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public int getJoinSyntax() {
      try {
         return this.getJDBCDelegate().getJoinSyntax();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public JDBCFetchConfiguration setJoinSyntax(int syntax) {
      try {
         this.getJDBCDelegate().setJoinSyntax(syntax);
         return this;
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public Set getJoins() {
      try {
         return this.getJDBCDelegate().getJoins();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public boolean hasJoin(String field) {
      try {
         return this.getJDBCDelegate().hasJoin(field);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public JDBCFetchConfiguration addJoin(String field) {
      try {
         this.getJDBCDelegate().addJoin(field);
         return this;
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public JDBCFetchConfiguration addJoins(Collection fields) {
      try {
         this.getJDBCDelegate().addJoins(fields);
         return this;
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public JDBCFetchConfiguration removeJoin(String field) {
      try {
         this.getJDBCDelegate().removeJoin(field);
         return this;
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public JDBCFetchConfiguration removeJoins(Collection fields) {
      try {
         this.getJDBCDelegate().removeJoins(fields);
         return this;
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public JDBCFetchConfiguration clearJoins() {
      try {
         this.getJDBCDelegate().clearJoins();
         return this;
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public int getIsolation() {
      try {
         return this.getJDBCDelegate().getIsolation();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public JDBCFetchConfiguration setIsolation(int level) {
      try {
         this.getJDBCDelegate().setIsolation(level);
         return this;
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public JDBCFetchConfiguration traverseJDBC(FieldMetaData fm) {
      try {
         return this.getJDBCDelegate().traverseJDBC(fm);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public Set getFetchInnerJoins() {
      try {
         return this.getJDBCDelegate().getFetchInnerJoins();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public boolean hasFetchInnerJoin(String field) {
      try {
         return this.getJDBCDelegate().hasFetchInnerJoin(field);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public JDBCFetchConfiguration addFetchInnerJoin(String field) {
      try {
         this.getJDBCDelegate().addFetchInnerJoin(field);
         return this;
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public JDBCFetchConfiguration addFetchInnerJoins(Collection fields) {
      try {
         this.getJDBCDelegate().addFetchInnerJoins(fields);
         return this;
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }
}
