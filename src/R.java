import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;

public class R implements Serializable {
	private static final long serialVersionUID = 7924411737659001497L;

	public Object[] os = new Object[2];

	public void add(Object o) {
		if (os == null) {
			os = new Object[2];
		}
		if (os[0] == null) {
			os[0] = o;
		} else {
			os[1] = o;
		}
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		// Only when putFields are written that the problem happens.
		// ====================================================
		ObjectOutputStream.PutField fields = s.putFields();		
		fields.put("propertyChangeSupportSerializedDataVersion", 2);
		s.writeFields();
		// ====================================================
		
		s.writeObject(os[0]);
		s.writeObject(os[1]);
		s.writeObject(null);
	}

	private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
		Object listenerOrNull;

		ObjectInputStream.GetField fields = s.readFields();
		fields.get("propertyChangeSupportSerializedDataVersion", 2);

		while (null != (listenerOrNull = s.readObject())) {
			add(listenerOrNull);
		}

	}

	private static final ObjectStreamField[] serialPersistentFields = {
			new ObjectStreamField("propertyChangeSupportSerializedDataVersion", Integer.TYPE) };

}
