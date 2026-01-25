package eubrunoo07.projects.varejo.api.catalog_service.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class ApiErrors {

    private List<String> errors;

    public ApiErrors(List<String> errors) {
        this.errors = errors;
    }

    public ApiErrors(String error) {
        this.errors = List.of(error);
    }

}
