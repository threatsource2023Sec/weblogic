package weblogic.j2ee.descriptor.wl;

public interface ForeignJNDIObjectBean extends NamedEntityBean {
   String getLocalJNDIName();

   void setLocalJNDIName(String var1) throws IllegalArgumentException;

   String getRemoteJNDIName();

   void setRemoteJNDIName(String var1) throws IllegalArgumentException;
}
