package by.bsac.webmvc.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ExceptionResponseBody {

    private final LocalDateTime exception_timestamp = LocalDateTime.now();

    @Setter
    private int status_code;

    private Exception root_exception;

    @Setter
    private String error_message;

    public ExceptionResponseBody(Exception a_root_exception) {
        this.root_exception = a_root_exception;
        this.error_message = a_root_exception.getMessage();
    }

    @JsonIgnore
    public void setRootException(Exception root_exception) {
        this.root_exception = root_exception;
    }
}
