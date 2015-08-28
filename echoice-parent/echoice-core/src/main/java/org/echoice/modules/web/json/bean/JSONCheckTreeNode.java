package org.echoice.modules.web.json.bean;

public class JSONCheckTreeNode extends JSONTreeNode{
    private boolean checked;
    private boolean isDisabled;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isDisabled() {
		return isDisabled;
	}

	public void setDisabled(boolean isDisabled) {
		this.isDisabled = isDisabled;
	}
    
}
