
public class FWApplication {

	private String id;
	private String name;
	private String alias;
	private Integer category;
	private String fortinetWebLink;
	private Integer vendor;
	private Integer popularity;
	private Integer risk;
	private Integer shaping;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	public String getFortinetWebLink() {
		return fortinetWebLink;
	}
	public void setFortinetWebLink(String fortinetWebLink) {
		this.fortinetWebLink = fortinetWebLink;
	}
	public Integer getVendor() {
		return vendor;
	}
	public void setVendor(Integer vendor) {
		this.vendor = vendor;
	}
	public Integer getPopularity() {
		return popularity;
	}
	public void setPopularity(Integer popularity) {
		this.popularity = popularity;
	}
	public Integer getRisk() {
		return risk;
	}
	public void setRisk(Integer risk) {
		this.risk = risk;
	}
	public Integer getShaping() {
		return shaping;
	}
	public void setShaping(Integer shaping) {
		this.shaping = shaping;
	}
	public FWApplication(String id, String name, String alias, Integer category, String fortinetWebLink, Integer vendor,
			Integer popularity, Integer risk, Integer shaping) {
		super();
		this.id = id;
		this.name = name;
		this.alias = alias;
		this.category = category;
		this.fortinetWebLink = fortinetWebLink;
		this.vendor = vendor;
		this.popularity = popularity;
		this.risk = risk;
		this.shaping = shaping;
	}
	
	@Override
	public String toString() {
		
		return "id="+id+",name="+name+",alias="+alias+",category="+category+",fortinetweblink="+null+",vendor="+vendor+",popularity="+popularity+",risk="+risk+",shaping="+shaping;
	}
	

}
