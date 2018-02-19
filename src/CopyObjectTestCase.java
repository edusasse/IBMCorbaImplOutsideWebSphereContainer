import java.util.Properties;
import javax.rmi.PortableRemoteObject;
import javax.rmi.CORBA.Tie;
import javax.rmi.CORBA.Util;
import org.omg.CORBA.ORB;
import java.rmi.Remote;
import java.rmi.RemoteException;

import java.util.List;


public class CopyObjectTestCase {
	
	Properties props = new Properties();
	org.omg.CORBA.ORB orb = ORB.init((String[])null,props);
		
	public String server(){

		ReImpl remoteRef = null;
		try {
			remoteRef = new ReImpl();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		Tie tie = Util.getTie(remoteRef);
		String ior = orb.object_to_string(tie.thisObject());
		return ior;
	}
	
	public void client(String ior){
		try {
			org.omg.CORBA.Object remoteRef = orb.string_to_object(ior);
			RmtInterface bI = null;
			bI = (RmtInterface) PortableRemoteObject.narrow(remoteRef, RmtInterface.class);
			
		// Do not change the logic above this, that is so ensure that ORB marshalls the data locally (Collocated server and client)
		// Change here to modify the payload for recreate.
		final List listOfObjects = new MainReduced().getListOfObjects();
		final A originalA = (A) listOfObjects.toArray()[0];
		final B originalB = (B) listOfObjects.toArray()[1];
		
		final List serializedList = bI.echoObject(listOfObjects);			
		System.out.println(serializedList);
		A sa = (A) serializedList.toArray()[0];
		B sb = (B) serializedList.toArray()[1];
		
		System.out.println("Original List\n====================");
		System.out.println("Original   -> Object A ["+ originalA + "] = r.A [" + originalA.r.os[0] /* A */ + "] == " + (originalA==originalA.r.os[0]));
		System.out.println("Original   -> Object B ["+ originalB + "] = r.B [" + originalB.r.os[1] /* B */ + "] == " + (originalB==originalB.r.os[1]));
		
		System.out.println("Serialized List\n====================");
		System.out.println("Serialized -> Object A ["+ sa + "] = r.A [" + sa.r.os[0] /* A */ + "] == " + (sa==sa.r.os[0]));
		System.out.println("Serialized -> Object B ["+ sb + "] = r.B [" + sb.r.os[1] /* B */ + "] == " + (sb==sb.r.os[1]));
			
		}catch (Exception ex) {
			ex.printStackTrace();    
		}	
	}
	

	public static void main(String[] args) {
		CopyObjectTestCase x = new CopyObjectTestCase();
		String ior = x.server();
		x.client(ior);
		System.exit(0);
	}
}
class ReImpl extends PortableRemoteObject implements RmtInterface {
    public ReImpl() throws RemoteException {
    	super();
    }
	
	// These method signatures can be modified. 
	// But the Stubs/Ties will have to regenerated, delete the stub and ties and then run -> rmic -keep -iiop ReImpl
	public List echoObject(List lo ) throws Exception {
    return lo;
  }
}

interface RmtInterface extends Remote {
	// These method signatures can be modified. 
	// But the Stubs/Ties will have to regenerated, delete the stub and ties and then run -> rmic -keep -iiop ReImpl
    public List echoObject (List lo) throws RemoteException, Exception;
}