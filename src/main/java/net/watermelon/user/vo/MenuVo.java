package net.watermelon.user.vo;

public class MenuVo {
	private String id;
	private String parent;
	private String position;

	private String oldParent;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}

	public String getOldParent() {
		return oldParent;
	}
	public void setOldParent(String oldParent) {
		this.oldParent = oldParent;
	}
	

	
}
