/**
 * 
 */
package projets4.basic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sbt
 *
 */
public class Slider implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8761861085505064472L;

	private Map<Object, VideoInfoBasic> basicInfos;
	
	private Map<Object, VideoInfoVariant> variantInfos;
	
	public Slider(){
		this.basicInfos = new HashMap<Object, VideoInfoBasic>();
		this.variantInfos = new HashMap<Object, VideoInfoVariant>();
	}
	
	public void add(Object o, VideoInfoBasic basicInfo, VideoInfoVariant variantInfo){
		basicInfos.put(o, basicInfo);
		variantInfos.put(o, variantInfo);
	}
	
	public boolean contains(Object o){
		return variantInfos.containsKey(o);
	}
	
	public long getNextValue(Object o){
		return variantInfos.get(o).getStreamCount();
	}
	
	public void updateVariantInfo(Object o, VideoInfoVariant variantInfo){
		variantInfos.put(o, variantInfo);
	}
	
	public VideoInfoBasic getBasicInfo(Object o){
		return basicInfos.get(o);
	}
	
	public Map<Object, Long> getNextValues(){
		Map<Object, Long> result = new HashMap<Object, Long>();
		for (Object object : variantInfos.keySet()) {
			result.put(object, variantInfos.get(object).getStreamCount());
		}
		return result;
	}
	
	public Map<Object, VideoInfoVariant> getVariantInfos(){
		return this.variantInfos;
	}
}
