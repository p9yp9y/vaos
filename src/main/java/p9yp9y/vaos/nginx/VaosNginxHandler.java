package p9yp9y.vaos.nginx;

import static nginx.clojure.MiniConstants.CONTENT_TYPE;
import static nginx.clojure.MiniConstants.NGX_HTTP_FORBIDDEN;

import java.util.Map;

import nginx.clojure.java.ArrayMap;
import nginx.clojure.java.Constants;
import nginx.clojure.java.NginxJavaRingHandler;

public class VaosNginxHandler implements NginxJavaRingHandler {
	@Override
	public Object[] invoke(Map<String, Object> request) {
		System.out.println("!!!!!!!!!!!!!AAA");
		if (isRequestForbidden(request)) {
			return new Object[] { NGX_HTTP_FORBIDDEN, // http status 404
					ArrayMap.create(CONTENT_TYPE, "text/plain"), // headers map
					"Forbidden" };
		} else {
			return Constants.PHRASE_DONE;
		}
	}

	private boolean isRequestForbidden(Map<String, Object> request) {
		String serverName = (String) request.get(Constants.URI);
		System.out.println("!!!!!!!!!!!!!:" + serverName);
		return true;
	}
}
