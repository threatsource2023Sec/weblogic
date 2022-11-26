package weblogic.apache.xerces.impl.dtd;

import java.util.ArrayList;
import java.util.Vector;
import weblogic.apache.xerces.util.XMLResourceIdentifierImpl;
import weblogic.apache.xerces.xni.XMLResourceIdentifier;
import weblogic.apache.xerces.xni.grammars.XMLGrammarDescription;
import weblogic.apache.xerces.xni.parser.XMLInputSource;

public class XMLDTDDescription extends XMLResourceIdentifierImpl implements weblogic.apache.xerces.xni.grammars.XMLDTDDescription {
   protected String fRootName = null;
   protected ArrayList fPossibleRoots = null;

   public XMLDTDDescription(XMLResourceIdentifier var1, String var2) {
      this.setValues(var1.getPublicId(), var1.getLiteralSystemId(), var1.getBaseSystemId(), var1.getExpandedSystemId());
      this.fRootName = var2;
      this.fPossibleRoots = null;
   }

   public XMLDTDDescription(String var1, String var2, String var3, String var4, String var5) {
      this.setValues(var1, var2, var3, var4);
      this.fRootName = var5;
      this.fPossibleRoots = null;
   }

   public XMLDTDDescription(XMLInputSource var1) {
      this.setValues(var1.getPublicId(), (String)null, var1.getBaseSystemId(), var1.getSystemId());
      this.fRootName = null;
      this.fPossibleRoots = null;
   }

   public String getGrammarType() {
      return "http://www.w3.org/TR/REC-xml";
   }

   public String getRootName() {
      return this.fRootName;
   }

   public void setRootName(String var1) {
      this.fRootName = var1;
      this.fPossibleRoots = null;
   }

   public void setPossibleRoots(ArrayList var1) {
      this.fPossibleRoots = var1;
   }

   public void setPossibleRoots(Vector var1) {
      this.fPossibleRoots = var1 != null ? new ArrayList(var1) : null;
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof XMLGrammarDescription)) {
         return false;
      } else if (!this.getGrammarType().equals(((XMLGrammarDescription)var1).getGrammarType())) {
         return false;
      } else {
         XMLDTDDescription var2 = (XMLDTDDescription)var1;
         if (this.fRootName != null) {
            if (var2.fRootName != null && !var2.fRootName.equals(this.fRootName)) {
               return false;
            }

            if (var2.fPossibleRoots != null && !var2.fPossibleRoots.contains(this.fRootName)) {
               return false;
            }
         } else if (this.fPossibleRoots != null) {
            if (var2.fRootName != null) {
               if (!this.fPossibleRoots.contains(var2.fRootName)) {
                  return false;
               }
            } else {
               if (var2.fPossibleRoots == null) {
                  return false;
               }

               boolean var3 = false;
               int var4 = this.fPossibleRoots.size();

               for(int var5 = 0; var5 < var4; ++var5) {
                  String var6 = (String)this.fPossibleRoots.get(var5);
                  var3 = var2.fPossibleRoots.contains(var6);
                  if (var3) {
                     break;
                  }
               }

               if (!var3) {
                  return false;
               }
            }
         }

         if (this.fExpandedSystemId != null) {
            if (!this.fExpandedSystemId.equals(var2.fExpandedSystemId)) {
               return false;
            }
         } else if (var2.fExpandedSystemId != null) {
            return false;
         }

         if (this.fPublicId != null) {
            if (!this.fPublicId.equals(var2.fPublicId)) {
               return false;
            }
         } else if (var2.fPublicId != null) {
            return false;
         }

         return true;
      }
   }

   public int hashCode() {
      if (this.fExpandedSystemId != null) {
         return this.fExpandedSystemId.hashCode();
      } else {
         return this.fPublicId != null ? this.fPublicId.hashCode() : 0;
      }
   }
}
