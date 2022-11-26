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
public class @simpleClassName
implements IterationTag,
@homeTagType
{
    Tag parent;
    PageContext pageContext;

    /* methods from Tag interface */
    public Tag getParent() { return parent; }
    public void setParent(Tag t) { parent = t; }
    public void setPageContext(PageContext p) { pageContext = p; }
    public int doEndTag() { return EVAL_PAGE; }
    
  @varDeclaration
  @boolDeclaration
  @remoteType theEJB;
  char[] buf = new char[512];
  private Iterator I = null;
  private static final String HOME_JNDI_NAME = @homeJNDIName ;

  @returnAttribute

  /* iteration tag */
  public int doAfterBody() throws JspException {
      /* write the contents of our body to the
       * enclosing writer
       */
      try {
          /*
	  Reader rdr = bodyContent.getReader();
	  Writer out = bodyContent.getEnclosingWriter();
	  int r;
	  while ((r = rdr.read(buf)) > 0) out.write(buf, 0, r);
	  bodyContent.clearBody();
          */
	  if (I.hasNext()) {
	      iterateEJB();
	      return EVAL_BODY_AGAIN;
	  } else {
	      I = null;
	      theEJB = null;
	      return SKIP_BODY;
	  }
      } catch (RuntimeException re) {
	  throw re;
      } catch (Exception e) {
	  throw new JspException("Error in doAfterBody(): " + e);
      }
  }

  static void p(String s) { System.err.println("[homecollectiontag]: " + s); }

  public int doStartTag() throws JspException {
      p("doStartTag()");
      if (I == null) {
	  verifyAllAttributesSet();
	  try {
	      @homeType home = getHome();
	      Object obj = home.@invoke ;
	      if (obj == null) {
		  I = (new java.util.ArrayList()).iterator();
	      } else if (obj instanceof java.util.Collection) {
		  java.util.Collection coll = (java.util.Collection)obj;
		  I = coll.iterator();
	      } else if (obj instanceof java.util.Enumeration) {
		  java.util.Enumeration enum = (java.util.Enumeration)obj;
		  I = new weblogic.utils.collections.EnumerationIterator(enum);
	      } else {
		  throw new Error("type " + obj.getClass().getName() + " is " +
				  "neither Collection nor Enumeration");
	      }
	  } catch (RuntimeException e) {
	      throw e;
	  } catch (Exception e) {
	      throw new JspException("error invoking bean: " + e);
	  }
      }
      if (I.hasNext()) {
	  iterateEJB();
	  return EVAL_BODY_INCLUDE;
      } else {
	  theEJB = null;
	  I = null;
	  return SKIP_BODY;
      }
  }

  private void iterateEJB() {
      theEJB = (@remoteType)I.next();
      if (_return != null) pageContext.setAttribute(_return, theEJB);
  }

  private @homeType getHome() throws Exception {
       Context ctx = new InitialContext();
	try {
	    Object obj = ctx.lookup(HOME_JNDI_NAME);
	    @homeType home = (@homeType)PortableRemoteObject.narrow(
		obj, @homeType.class);
	    return home;
	} finally {
	    ctx.close();
	}
  }

  public @remoteType getEJBFromHome() throws Exception {
      return theEJB;
  }

  private void verifyAllAttributesSet() throws JspException {
    @verifyAttributes
  }

  public void release() {
      theEJB = null;
      I = null;
      pageContext = null;
      parent = null;
     @releaseBody
  }

  
  /* setter methods for all attributes */
  @setterMethods

}
@end rule: main
