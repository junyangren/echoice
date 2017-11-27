package org.echoice.codegen;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库字段封装类
 * @author 
 *
 */
public class ColumnClass {
	/** 数据库字段名称 **/
    private String columnName;
    /** 数据库字段类型 **/
    private String columnType;
    /** 数据库字段首字母小写且去掉下划线字符串 **/
    private String changeColumnName;
    /** 数据库字段注释 **/
    private String columnComment;
    
    /** 数据库字段注释 **/
    private String columnShortComment;
    
    private int columnSize;
    
    private boolean pk;

    private String javaType;
    
    private String formType;
    
    private boolean required=false;
    
    private List<Option> options;
    
    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getChangeColumnName() {
        return changeColumnName;
    }

    public void setChangeColumnName(String changeColumnName) {
        this.changeColumnName = changeColumnName;
    }

	public boolean getPk() {
		return pk;
	}

	public void setPk(boolean pk) {
		this.pk = pk;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public String getColumnShortComment() {
		return columnShortComment;
	}

	public void setColumnShortComment(String columnShortComment) {
		this.columnShortComment = columnShortComment;
	}

	public boolean getRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public int getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}
    
	public void addOption(String name,String value){
		if(this.options==null){
			this.options=new ArrayList<Option>();
		}
		this.options.add(new Option(name, value));
	}
}
