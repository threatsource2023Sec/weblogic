package weblogic.wtc.jatmi;

public final class DefaultUserRec implements UserRec {
   private static final long serialVersionUID = -6991653037617144080L;
   private int key;
   private String name;

   public DefaultUserRec(String usrname, int usrkey) {
      this.name = new String(usrname);
      this.key = usrkey;
   }

   public int getAppKey() {
      return this.key;
   }

   public String getRemoteUserName() {
      return this.name;
   }

   public String getLocalUserName() {
      return this.name;
   }

   public String toString() {
      return new String("DefaultUserRec(name " + this.name + ", appkey " + this.key + ")");
   }
}
