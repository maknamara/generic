package br.com.maknamara.model.validator;

import java.util.ArrayList;
import java.util.List;

import br.com.maknamara.model.exceptions.RuleException;

public abstract class BaseValidator<T> {
    private List<Integer> resourceMessage = new ArrayList<>();

    public static String getEmptyIfNull(String value) {
        String newValue = value;
        if (newValue == null) {
            newValue = "";
        }
        return newValue;
    }

    public abstract void validateRecording(T t);

    public abstract void validateDeletion(T t);

    protected void verify() {
        if (!resourceMessage.isEmpty()) {
            RuleException re = new RuleException(resourceMessage.toString().replaceAll("[\\[\\] ]", ""));
            resourceMessage.clear();
            throw re;
        }
    }

    protected void check(boolean isInvalid, int res) {
        if (isInvalid) {
            resourceMessage.add(res);
        }
    }
}