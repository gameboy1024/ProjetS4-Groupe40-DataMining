package projets4.case1.database;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * 
 * @author Ahmed
 *
 */
public class Channel {
	
	private Long id ;
	private Long  views_count ;
	
	
	public Channel(JSONObject channel) throws JSONException{
		
		id=channel.getLong("id");
		views_count=channel.getLong("views_count");
		
	}
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getViews_count() {
		return views_count;
	}
	public void setViews_count(Long views_count) {
		this.views_count = views_count;
	}
	

}
