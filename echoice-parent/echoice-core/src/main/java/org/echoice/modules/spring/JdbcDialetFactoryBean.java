package org.echoice.modules.spring;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.dialect.Dialect;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class JdbcDialetFactoryBean implements FactoryBean,InitializingBean{
	private Dialect dialect;
	private String hibernateDialect;
	public Object getObject() throws Exception {
		// TODO Auto-generated method stub
		return dialect;
	}

	public Class getObjectType() {
		// TODO Auto-generated method stub
		return Dialect.class;
	}

	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return true;
	}

	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		if(StringUtils.isNotBlank(hibernateDialect)){
			dialect=(Dialect)Class.forName(hibernateDialect).newInstance();
		}else{
			throw new IllegalArgumentException("hibernateDialect unNull or blank!!");
		}
	}

	public String getHibernateDialect() {
		return hibernateDialect;
	}

	public void setHibernateDialect(String hibernateDialect) {
		this.hibernateDialect = hibernateDialect;
	}

}
