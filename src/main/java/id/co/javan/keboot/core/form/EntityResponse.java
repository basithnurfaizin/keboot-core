package id.co.javan.keboot.core.form;


public interface EntityResponse<T> {

    Object convertToResponse(T t);

}
