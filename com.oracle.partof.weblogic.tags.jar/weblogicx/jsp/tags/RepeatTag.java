package weblogicx.jsp.tags;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class RepeatTag extends BodyTagSupport {
   private String id;
   private Object set;
   private String type;
   private int count = -1;
   private int startCount;
   private Enumeration enum_;

   public void release() {
      this.id = null;
      this.set = null;
      this.type = null;
      this.count = -1;
      this.startCount = 0;
      this.enum_ = null;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getId() {
      return this.id;
   }

   public void setSet(Object set) {
      this.set = set;
   }

   public Object getSet() {
      return this.set;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getType() {
      return this.type;
   }

   public void setCount(int count) {
      this.count = count;
   }

   public int getCount() {
      return this.count;
   }

   public int doStartTag() throws JspException {
      if (this.set == null && this.count <= 0) {
         return 0;
      } else if (this.set == null && this.count != -1) {
         return 2;
      } else {
         this.enum_ = null;
         if (this.set instanceof String) {
            this.enum_ = new StringTokenizer((String)this.set, ",");
         } else if (this.set instanceof Enumeration) {
            this.enum_ = (Enumeration)this.set;
         } else if (this.set instanceof Vector) {
            this.enum_ = ((Vector)this.set).elements();
         } else if (this.set.getClass().isArray()) {
            final Object[] finalset = (Object[])((Object[])this.set);
            this.enum_ = new Enumeration() {
               private Object[] array = finalset;
               private int idx = 0;

               public boolean hasMoreElements() {
                  return this.idx < this.array.length;
               }

               public Object nextElement() {
                  try {
                     return this.array[this.idx++];
                  } catch (ArrayIndexOutOfBoundsException var2) {
                     throw new NoSuchElementException();
                  }
               }
            };
         } else if (this.set instanceof ResultSet) {
            final ResultSet _jsprs = (ResultSet)this.set;
            this.enum_ = new Enumeration() {
               public boolean hasMoreElements() {
                  try {
                     return _jsprs.next();
                  } catch (SQLException var2) {
                     return false;
                  }
               }

               public Object nextElement() {
                  return _jsprs;
               }
            };
         } else if (this.set instanceof ResultSetMetaData) {
            final ResultSetMetaData rsmd = (ResultSetMetaData)this.set;
            this.enum_ = new Enumeration() {
               private int i = 1;

               public boolean hasMoreElements() {
                  try {
                     return this.i <= rsmd.getColumnCount();
                  } catch (SQLException var2) {
                     return false;
                  }
               }

               public Object nextElement() {
                  try {
                     return rsmd.getColumnName(this.i++);
                  } catch (SQLException var2) {
                     return null;
                  }
               }
            };
         } else if (this.set instanceof Hashtable) {
            this.enum_ = ((Hashtable)this.set).keys();
         } else {
            final Iterator iterator;
            if (this.set instanceof Iterator) {
               iterator = (Iterator)this.set;
               this.enum_ = new Enumeration() {
                  public boolean hasMoreElements() {
                     return iterator.hasNext();
                  }

                  public Object nextElement() {
                     return iterator.next();
                  }
               };
            } else if (this.set instanceof Collection) {
               iterator = ((Collection)this.set).iterator();
               this.enum_ = new Enumeration() {
                  public boolean hasMoreElements() {
                     return iterator.hasNext();
                  }

                  public Object nextElement() {
                     return iterator.next();
                  }
               };
            }
         }

         return this.enum_.hasMoreElements() ? 2 : 0;
      }
   }

   public void doInitBody() throws JspException {
      if (this.count != -1) {
         this.startCount = this.count;
      }

      if (this.enum_ != null) {
         this.pageContext.setAttribute(this.id, this.enum_.nextElement());
      } else {
         this.pageContext.setAttribute(this.id, new Integer(1));
      }

   }

   public int doAfterBody() throws JspException {
      try {
         this.getBodyContent().writeOut(this.getBodyContent().getEnclosingWriter());
         this.getBodyContent().clearBody();
      } catch (IOException var2) {
      }

      if (this.count == -1 || --this.count > 0) {
         if (this.enum_ == null) {
            this.pageContext.setAttribute(this.id, new Integer(this.startCount - this.count + 1));
            return 2;
         }

         if (this.enum_.hasMoreElements()) {
            this.pageContext.setAttribute(this.id, this.enum_.nextElement());
            return 2;
         }
      }

      this.pageContext.removeAttribute(this.id);
      return 0;
   }
}
