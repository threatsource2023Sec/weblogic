package weblogic.jdbc.rmi.internal;

import java.rmi.Remote;
import weblogic.jdbc.rmi.RmiStatement;

public interface Statement extends Remote, java.sql.Statement, RmiStatement {
}
