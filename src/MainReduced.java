import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;


public class MainReduced implements Serializable {

	private static final long serialVersionUID = -5856740266678202547L;

	private List<java.lang.Object> listOfObjects;

	public MainReduced() {
		this.listOfObjects = new ArrayList<java.lang.Object>();

		R r = new R();
		A a = new A(r);
		B b = new B(r);

		this.listOfObjects.add(a);
		this.listOfObjects.add(b);

	}

	public List getListOfObjects() {
		return listOfObjects;
	}
}