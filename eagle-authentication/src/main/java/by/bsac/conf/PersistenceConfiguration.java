package by.bsac.conf;

import by.bsac.services.parsers.PropertiesFileParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"by.bsac.repositories"})
public class PersistenceConfiguration {

    //LOGGER
    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceConfiguration.class);

    //Constructor
    public PersistenceConfiguration() {
        LOGGER.info("Start to initialize " +getClass().getSimpleName() +" configuration class.");
    }

    @Bean
    @Autowired
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource a_datasource) {

        //Create entity manager factory
        final LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

        //Autowire datasource
        LOGGER.debug("Autowire " +a_datasource.getClass().getSimpleName() + " TO " +emf.getClass().getSimpleName() +" bean.");
        emf.setDataSource(a_datasource);

        //Set Hibernate as default JPA vendor adapter
        JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(adapter);

        //Set hibernate properties;
        try {
            emf.setJpaProperties(new PropertiesFileParser(getClass().getClassLoader().getResourceAsStream("hibernate.properties")).getProperties());
        } catch (IOException e) {
            LOGGER.warn("Hibernate.properties file not found or IO error occur.");
            e.printStackTrace();
        }

        //Packages of JPA entities
        emf.setPackagesToScan("by.bsac.models");

        return emf;
    }

    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(EntityManagerFactory a_emf) {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(a_emf);
        return tm;
    }

}
