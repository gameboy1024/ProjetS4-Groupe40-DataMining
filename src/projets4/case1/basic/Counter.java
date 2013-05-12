package projets4.case1.basic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class Counter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6235195673452495445L;


	private final long counterSize;
	private Map<Object, LinkedList<VideoInfoVariant>> counter;

	public Counter(long counterSize) {
		this.counter = new HashMap<Object, LinkedList<VideoInfoVariant>>();
		this.counterSize = counterSize;
	}
	
	public void addEntry(Object o, VideoInfoVariant variantInfo){
		if (!counter.containsKey(o)) {
			counter.put(o, new LinkedList<VideoInfoVariant>());
		}
		LinkedList<VideoInfoVariant> current = counter.get(o); 
		current.addLast(variantInfo);
		if (current.size() > counterSize) {
			current.removeFirst();
		}
	}
	
	public LinkedList<VideoInfoVariant> getVariantInfo(Object o){
		return counter.get(o);
	}
	
	public Map<Object, LinkedList<VideoInfoVariant>> getVariantInfos(){
		return counter;
	}
}