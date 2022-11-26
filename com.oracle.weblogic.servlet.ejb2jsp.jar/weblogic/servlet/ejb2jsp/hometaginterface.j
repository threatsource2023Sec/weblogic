@start rule: main
/*
 * This code was automatically generated at @time on @date
 * by @generator -- do not edit.
 */
@packageStatement

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.rmi.RemoteException;
import javax.ejb.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

// User imports
@importStatements
/* all Tags corresponding to find/create methods on the
 * entity bean home implement this interface.  Enclosed
 * entity bean calls operate on the EJB found/created
 * by these home tags.
 */
public interface @homeTagInterfaceName
{
    public abstract @remoteType getEJBFromHome() throws Exception;
}
@end rule: main
