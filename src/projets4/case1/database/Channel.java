package projets4.case1.database;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * 
 * @author Ahmed
 *
 */
public class Channel {
	
	private int  id ;
	private String  views_count ;
	
	
	public Channel(JSONObject channel) throws JSONException{
		
		id=channel.getInt("id");
		views_count=channel.getString("views_count");
		
	}
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getViews_count() {
		return views_count;
	}
	public void setViews_count(String views_count) {
		this.views_count = views_count;
	}
	

}
