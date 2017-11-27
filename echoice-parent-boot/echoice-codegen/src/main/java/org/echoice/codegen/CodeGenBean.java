package org.echoice.codegen;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="app.code")
public class CodeGenBean {
	private String dbIp;
	private String dbName;
	private String dbUser;
	private String dbPasswd;
	private String dbTable;
	private String dbTablePrefix;
	private String dbTableNameCn;
	private String appPackageName;
	private String author;
	public String getDbIp() {
		return dbIp;
	}
	public void setDbIp(String dbIp) {
		this.dbIp = dbIp;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getDbUser() {
		return dbUser;
	}
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}
	public String getDbPasswd() {
		return dbPasswd;
	}
	public void setDbPasswd(String dbPasswd) {
		this.dbPasswd = dbPasswd;
	}
	public String getDbTable() {
		return dbTable;
	}
	public void setDbTable(String dbTable) {
		this.dbTable = dbTable;
	}
	public String getDbTablePrefix() {
		return dbTablePrefix;
	}
	public void setDbTablePrefix(String dbTablePrefix) {
		this.dbTablePrefix = dbTablePrefix;
	}
	public String getAppPackageName() {
		return appPackageName;
	}
	public void setAppPackageName(String appPackageName) {
		this.appPackageName = appPackageName;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDbTableNameCn() {
		return dbTableNameCn;
	}
	public void setDbTableNameCn(String dbTableNameCn) {
		this.dbTableNameCn = dbTableNameCn;
	}

	
}
