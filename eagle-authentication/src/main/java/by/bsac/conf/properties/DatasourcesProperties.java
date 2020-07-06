package by.bsac.conf.properties;

import lombok.Getter;

public class DatasourcesProperties {

    private PRODUCTION production = new PRODUCTION();

    public PRODUCTION forProduction() {
        return production;
    }

    public class PRODUCTION {

        @Getter
        private String jndi_name;

        PRODUCTION() {
            this.jndi_name = PropertiesInitializer.getCurrentApplicationProperties().getProperty("eagle.persistence.datasource.production.jndi-name");
        }
    }

}
