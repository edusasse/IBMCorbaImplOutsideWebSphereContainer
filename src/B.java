import java.io.Serializable;

public class B implements Serializable {
	private static final long serialVersionUID = 6496067415353595838L;

	public R r;
	
	public B(R r) {
		this.r = r;
		r.add(this);
	}
}
