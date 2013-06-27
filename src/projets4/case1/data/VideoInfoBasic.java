package projets4.case1.data;

import java.util.ArrayList;

public class VideoInfoBasic {
public long streamId;
	
	public String streamName;
	
	public long channelId;
	
	public String channelName;

	public long categoryId;
	
	public String categoryName;
	
	public long uploaderId;
	
	public String uploaderName;
	
	public ArrayList<String> tags;
	
	public VideoInfoBasic(String[] inputs) {
		this.streamId = Long.parseLong(inputs[0]);
		this.streamName = inputs[1];
		this.channelId = Long.parseLong(inputs[2]);
		this.channelName = inputs[3];
		this.categoryId = Long.parseLong(inputs[4]);
		this.categoryName = inputs[5];
		this.uploaderId = Long.parseLong(inputs[6]);
		this.uploaderName = inputs[7];
		// todo: tags
	}
	
}