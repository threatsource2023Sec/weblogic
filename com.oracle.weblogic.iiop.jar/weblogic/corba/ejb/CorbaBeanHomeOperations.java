package weblogic.corba.ejb;

import javax.ejb.CreateException;

public interface CorbaBeanHomeOperations {
   CorbaBeanObject create() throws CreateException;
}
