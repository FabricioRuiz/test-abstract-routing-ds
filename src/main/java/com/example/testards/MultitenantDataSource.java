package com.example.testards;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@Primary
public class MultitenantDataSource extends AbstractRoutingDataSource {

	private final AtomicBoolean initialized = new AtomicBoolean();
	private final Map<Object, Object> dataSources = new HashMap<>();
	public MultitenantDataSource(){
		var companyDs = createDataSource(Schema.company.name());
		var userDs = createDataSource(Schema.user.name());

		dataSources.put(Schema.company.name(),companyDs);
		dataSources.put(Schema.user.name(),userDs);
		setTargetDataSources(dataSources);
	}
	@Override
	protected Object determineCurrentLookupKey() {
		if(this.initialized.compareAndSet(false, true)){
			afterPropertiesSet();
		}
		return MultitenantContext.getCurrentTenant();
	}
	private DataSource createDataSource(String schema){
		return DataSourceBuilder
				.create().driverClassName("com.mysql.cj.jdbc.Driver")
				.username("root")
				.password("root")
				.url("jdbc:mysql://localhost:3306/" + schema).build();
	}
}