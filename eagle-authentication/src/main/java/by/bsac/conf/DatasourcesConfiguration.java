package by.bsac.conf;


import by.bsac.conf.properties.DatasourcesProperties;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.naming.NamingException;
import javax.sql.DataSource;

import static by.bsac.conf.LoggerDefaultLogs.*;

@Configuration
public class DatasourcesConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(DatasourcesConfiguration.class);

    private final DatasourcesProperties datasource_properties = new DatasourcesProperties();

    @Bean("DevelopmentDataSource")
    @Profile("DEVELOPMENT")
    public DataSource devDataSource() {

        //Create spring driver manager datasource
        DriverManagerDataSource ds = new DriverManagerDataSource();

        //Set database server URL
        final String DATABASE_URL = "jdbc:mysql://10.8.8.100:3306/eagle_users";
        ds.setUrl(DATABASE_URL);

        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        //Credentials
        ds.setUsername("eagle-admin");
        ds.setPassword("12345678");

        return ds;
    }

    @Bean("DataSource")
    @Profile("TEST")
    public DataSource testDataSource() {

        BasicDataSource ds = new BasicDataSource();

        final String DATABASE_URL = "jdbc:mysql://10.8.8.110:3306/eagle_users";
        ds.setUrl(DATABASE_URL);
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUsername("eagle-admin");
        ds.setPassword("12345678");

        return ds;
    }

    @Bean("DataSource")
    @Profile("PRODUCTION")
    public DataSource prodDataSource() throws NamingException {

        LOGGER.info(CREATION.beanCreationStartForProfile(DataSource.class, "PRODUCTION"));

        JndiObjectFactoryBean factory_bean = new JndiObjectFactoryBean();
        final String JNDI_CONTEXT = "java:comp/env/";

        factory_bean.setJndiName(JNDI_CONTEXT + this.datasource_properties.forProduction().getJndiName());
        factory_bean.setProxyInterface(DataSource.class);
        factory_bean.setLookupOnStartup(false);
        factory_bean.afterPropertiesSet();

        LOGGER.info(CREATION.beanCreationFinishForProfile(DataSource.class, "PRODUCTION"));
        return (DataSource) factory_bean.getObject();
    }

}
