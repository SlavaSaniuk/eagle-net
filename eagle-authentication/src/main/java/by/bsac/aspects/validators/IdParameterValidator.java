package by.bsac.aspects.validators;

import by.bsac.core.validation.ParameterValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Basic {@link ParameterValidator} for entity id validation.
 * Entity ID must not be null and must be above zero.
 */
public class IdParameterValidator implements ParameterValidator {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(IdParameterValidator.class);


    @Override
    public boolean validate(List<Object> list) {

        //Cast to Integer
         Integer ID =  (Integer) list.get(0);

        return this.validate(ID);
    }

    public boolean validate(Integer ID) {
        //Check at null
        if (ID == null) {
            LOGGER.warn("ID of required entity is [null];");
            return false;
        }

        //Check at account id above 0
        if (ID > 0 ) return true;
        else {
            LOGGER.warn(String.format("Id [%d] is in invalid value;", ID));
            return false;
        }
    }
}
