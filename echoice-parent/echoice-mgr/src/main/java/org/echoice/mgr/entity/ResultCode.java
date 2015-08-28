package org.echoice.mgr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

/**
 * VcResultCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "cfg_result_code")
public class ResultCode implements java.io.Serializable {

	// Fields
	@NotBlank
	private String code;
	@NotBlank
	private String note;
	private String errmessages;
	private String type;

	// Constructors

	/** default constructor */
	public ResultCode() {
	}

	/** minimal constructor */
	public ResultCode(String code) {
		this.code = code;
	}

	/** full constructor */
	public ResultCode(String code, String note, String errmessages, String type) {
		this.code = code;
		this.note = note;
		this.errmessages = errmessages;
		this.type = type;
	}

	// Property accessors
	@Id
	@Column(name = "code", unique = true, nullable = false, length = 6)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "note", length = 100)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "errmessages", length = 128)
	public String getErrmessages() {
		return this.errmessages;
	}

	public void setErrmessages(String errmessages) {
		this.errmessages = errmessages;
	}

	@Column(name = "type", length = 6)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}