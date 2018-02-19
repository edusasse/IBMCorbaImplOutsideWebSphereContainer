import java.io.Serializable;

public class A implements Serializable {
	private static final long serialVersionUID = 4540940650435697056L;

	public R r;

	public A(R r) {
		this.r = r;
		r.add(this);
	}
}
