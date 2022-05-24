package bdtc.lab2.util;

import bdtc.lab2.controller.model.FlightAggregation;
import bdtc.lab2.model.FlightEntity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class MapComputeTaskArg {
    private List<FlightEntity> arg;
    private Method method;
    private Object object;

    public void invoke(List<FlightAggregation> flightAggregations) throws InvocationTargetException, IllegalAccessException {
        Object[] parameters = new Object[1];
        parameters[0] = flightAggregations;
        method.invoke(object,parameters);
    }
    public MapComputeTaskArg(List<FlightEntity> arg, Method method, Object object){
        this.arg = arg;
        this.method = method;
        this.object = object;
    }

    public MapComputeTaskArg(Method method, Object object){
        this.method = method;
        this.object = object;
    }

    public List<FlightEntity> getArg() {
        return arg;
    }

    public void setArg(List<FlightEntity> arg) {
        this.arg = arg;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
