package projets4.case1.database;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author Ahmed
 *
 */

public class Stream {
	
	
	private Channel channel;

	public Stream(JSONObject stream) throws JSONException {
		JSONObject s=stream.getJSONObject("channel");
		this.channel = new Channel (s);
	
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}


}
