package id.co.javan.keboot.core.form;

public interface EntityCrudForm<T> {
    T toCreateEntity();

    T toUpdateEntity(T oldEntity);
}
