package kodo.jdbc.conf.descriptor;

public interface JDBCListenersBean {
   CustomJDBCListenerBean[] getCustomJDBCListeners();

   CustomJDBCListenerBean createCustomJDBCListener();

   void destroyCustomJDBCListener(CustomJDBCListenerBean var1);
}
