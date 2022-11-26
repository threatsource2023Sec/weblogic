package weblogic.jdbc.rmi.internal;

import java.rmi.Remote;

public interface Array extends Remote, java.sql.Array {
   void internalClose();
}
