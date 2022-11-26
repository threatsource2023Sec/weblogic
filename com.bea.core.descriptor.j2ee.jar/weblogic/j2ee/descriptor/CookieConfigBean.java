package weblogic.j2ee.descriptor;

public interface CookieConfigBean {
   String getName();

   void setName(String var1);

   String getDomain();

   void setDomain(String var1);

   String getPath();

   void setPath(String var1);

   String getComment();

   void setComment(String var1);

   boolean isHttpOnly();

   void setHttpOnly(boolean var1);

   boolean isSecure();

   void setSecure(boolean var1);

   int getMaxAge();

   void setMaxAge(int var1);

   String getId();

   void setId(String var1);
}
