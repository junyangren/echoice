package org.echoice.modules.web.ztree;

public class ZTreeView {
	private String id;
	private String name;
	private String icon;
	private boolean isParent;
	private boolean checked;
	private String click;
	private String iconSkin;
	private boolean open;
	private String target="_self";
	private String url="";
	private String parentId;
	private String extUrl="";
	private String extNote;
	private String alias;
	private boolean reload;
	private String title;
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
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}
	public boolean getChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getClick() {
		return click;
	}
	public void setClick(String click) {
		this.click = click;
	}
	public String getIconSkin() {
		return iconSkin;
	}
	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}
	public boolean getOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getExtUrl() {
		return extUrl;
	}
	public void setExtUrl(String extUrl) {
		this.extUrl = extUrl;
	}
	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}
	public String getExtNote() {
		return extNote;
	}
	public void setExtNote(String extNote) {
		this.extNote = extNote;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public boolean getReload() {
		return reload;
	}
	public void setReload(boolean reload) {
		this.reload = reload;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
