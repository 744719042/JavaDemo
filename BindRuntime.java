import java.util.HashMap;
import java.util.Map;

public class BindRuntime {
	private static Map<String, IBind> sCache = new HashMap<>();
	public static final String CLASS_PREFIX = "$$BindListener";
	
	public static void bind(Object object) {
		String key = object.getClass().getName();
		IBind bind = sCache.get(key);
		if (bind == null) {
			try {
				Class<? extends IBind> clazz = (Class<? extends IBind>) Class.forName(key + CLASS_PREFIX);
				bind = clazz.newInstance();
				sCache.put(key, (IBind<?>) bind);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		
		bind.bind(object);
	}
	
	public static void unbind(Object object) {
		String key = object.getClass().getName();
		IBind bind = sCache.get(key);
		if (bind != null) {
			bind.unbind(object);
		}
		sCache.remove(key);
	}
}
