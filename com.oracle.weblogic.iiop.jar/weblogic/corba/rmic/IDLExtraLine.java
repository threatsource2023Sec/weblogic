package weblogic.corba.rmic;

public final class IDLExtraLine {
   String m_line;
   String m_mangledName;

   public IDLExtraLine() {
      this((String)null, (String)null);
   }

   public IDLExtraLine(String mangledName, String line) {
      this.m_line = null;
      this.m_mangledName = null;
      this.m_mangledName = mangledName;
      this.m_line = line;
   }

   public String getMangledName() {
      return this.m_mangledName;
   }

   public String toIDL() {
      return this.m_line;
   }
}
