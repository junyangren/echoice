package ${package_name}.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;

/**
* 描述：${table_annotation}模型
* @author ${author}
* @date ${date}
*/
@Entity
@Table(name = "${table_name_small}")
@NamedQuery(name="${table_name}.findAll", query="SELECT a FROM ${table_name} a")
public class ${table_name} extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	// Fields
    <#if model_column?exists>
        <#list model_column as model>
    /**
    *${model.columnComment!}
    *${model.columnType}
    */
    <#if (model.pk) >
    @TableGenerator(name="ID_${table_name}",
            table="ec_common_seq",
            pkColumnName="pk_key",
            valueColumnName="pk_value",
            pkColumnValue="${table_name_small}",
            allocationSize=1,
            initialValue=1)
    @GeneratedValue(strategy=GenerationType.TABLE,generator="ID_${table_name}")    
    @Id
    </#if>
    <#if (model.javaType = 'Date') >
	@Temporal(TemporalType.TIMESTAMP)
	   <#if (!(model.changeColumnName = 'CreateTime'|| model.changeColumnName = 'OpTime')) >
	@JSONField(format = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	   <#else>
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")	
	   </#if>
	</#if>
    <#if (model.required && !(model.pk) && !(model.changeColumnName = 'CreateTime'|| model.changeColumnName = 'OpTime'||model.changeColumnName = 'CreateUser'|| model.changeColumnName = 'OpUser')) >
        <#if (model.javaType = 'String') >
    @NotBlank(message="${model.columnShortComment!}不能为空")
    @Size(min=1,max=${model.columnSize},message="${model.columnShortComment!}1~${model.columnSize}个字符")
            <#else>
    @NotNull(message="${model.columnShortComment!}不能为空")
         </#if>
    </#if>
    @Column(name = "${model.columnName}" ,length=${model.columnSize})
    private ${model.javaType} ${model.changeColumnName?uncap_first};
    
        </#list>
    </#if>
    
    <#if model_column?exists>
        <#list model_column as model>
	public ${model.javaType} get${model.changeColumnName}() {
        return this.${model.changeColumnName?uncap_first};
    }

    public void set${model.changeColumnName}(${model.javaType} ${model.changeColumnName?uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
    }
    
        </#list>
    </#if>        
}