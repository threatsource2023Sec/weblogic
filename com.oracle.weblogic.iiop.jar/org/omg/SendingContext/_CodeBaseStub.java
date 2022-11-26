package org.omg.SendingContext;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.Repository;
import org.omg.CORBA.RepositoryHelper;
import org.omg.CORBA.RepositoryIdHelper;
import org.omg.CORBA.RepositoryIdSeqHelper;
import org.omg.CORBA.ValueDefPackage.FullValueDescription;
import org.omg.CORBA.ValueDefPackage.FullValueDescriptionHelper;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.SendingContext.CodeBasePackage.URLHelper;
import org.omg.SendingContext.CodeBasePackage.URLSeqHelper;
import org.omg.SendingContext.CodeBasePackage.ValueDescSeqHelper;

public class _CodeBaseStub extends ObjectImpl implements CodeBase {
   private static String[] __ids = new String[]{"IDL:omg.org/SendingContext/CodeBase:1.0", "IDL:omg.org/SendingContext/RunTime:1.0"};

   public Repository get_ir() {
      InputStream $in = null;

      Repository $result;
      try {
         OutputStream $out = this._request("get_ir", true);
         $in = this._invoke($out);
         $result = RepositoryHelper.read($in);
         Repository var4 = $result;
         return var4;
      } catch (ApplicationException var9) {
         $in = var9.getInputStream();
         String _id = var9.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var10) {
         $result = this.get_ir();
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public String implementation(String x) {
      InputStream $in = null;

      String _id;
      try {
         OutputStream $out = this._request("implementation", true);
         RepositoryIdHelper.write($out, x);
         $in = this._invoke($out);
         _id = URLHelper.read($in);
         String var5 = _id;
         return var5;
      } catch (ApplicationException var10) {
         $in = var10.getInputStream();
         _id = var10.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var11) {
         _id = this.implementation(x);
      } finally {
         this._releaseReply($in);
      }

      return _id;
   }

   public String implementationx(String x) {
      InputStream $in = null;

      String _id;
      try {
         OutputStream $out = this._request("implementationx", true);
         RepositoryIdHelper.write($out, x);
         $in = this._invoke($out);
         _id = URLHelper.read($in);
         String var5 = _id;
         return var5;
      } catch (ApplicationException var10) {
         $in = var10.getInputStream();
         _id = var10.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var11) {
         _id = this.implementationx(x);
      } finally {
         this._releaseReply($in);
      }

      return _id;
   }

   public String[] implementations(String[] x) {
      InputStream $in = null;

      String[] $result;
      try {
         OutputStream $out = this._request("implementations", true);
         RepositoryIdSeqHelper.write($out, x);
         $in = this._invoke($out);
         $result = URLSeqHelper.read($in);
         String[] var5 = $result;
         return var5;
      } catch (ApplicationException var10) {
         $in = var10.getInputStream();
         String _id = var10.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var11) {
         $result = this.implementations(x);
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public FullValueDescription meta(String x) {
      InputStream $in = null;

      FullValueDescription $result;
      try {
         OutputStream $out = this._request("meta", true);
         RepositoryIdHelper.write($out, x);
         $in = this._invoke($out);
         $result = FullValueDescriptionHelper.read($in);
         FullValueDescription var5 = $result;
         return var5;
      } catch (ApplicationException var10) {
         $in = var10.getInputStream();
         String _id = var10.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var11) {
         $result = this.meta(x);
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public FullValueDescription[] metas(String[] x) {
      InputStream $in = null;

      FullValueDescription[] $result;
      try {
         OutputStream $out = this._request("metas", true);
         RepositoryIdSeqHelper.write($out, x);
         $in = this._invoke($out);
         $result = ValueDescSeqHelper.read($in);
         FullValueDescription[] var5 = $result;
         return var5;
      } catch (ApplicationException var10) {
         $in = var10.getInputStream();
         String _id = var10.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var11) {
         $result = this.metas(x);
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public String[] bases(String x) {
      InputStream $in = null;

      String[] $result;
      try {
         OutputStream $out = this._request("bases", true);
         RepositoryIdHelper.write($out, x);
         $in = this._invoke($out);
         $result = RepositoryIdSeqHelper.read($in);
         String[] var5 = $result;
         return var5;
      } catch (ApplicationException var10) {
         $in = var10.getInputStream();
         String _id = var10.getId();
         throw new MARSHAL(_id);
      } catch (RemarshalException var11) {
         $result = this.bases(x);
      } finally {
         this._releaseReply($in);
      }

      return $result;
   }

   public String[] _ids() {
      return (String[])((String[])__ids.clone());
   }

   private void readObject(ObjectInputStream s) throws IOException {
      String str = s.readUTF();
      String[] args = null;
      Properties props = null;
      ORB orb = ORB.init((String[])args, (Properties)props);

      try {
         Object obj = orb.string_to_object(str);
         Delegate delegate = ((ObjectImpl)obj)._get_delegate();
         this._set_delegate(delegate);
      } finally {
         orb.destroy();
      }

   }

   private void writeObject(ObjectOutputStream s) throws IOException {
      String[] args = null;
      Properties props = null;
      ORB orb = ORB.init((String[])args, (Properties)props);

      try {
         String str = orb.object_to_string(this);
         s.writeUTF(str);
      } finally {
         orb.destroy();
      }

   }
}
