package by.bsac.services;


import org.springframework.lang.Nullable;

public interface CrudService<T, I> {

    T create(T entity);


    @Nullable T getById(I id);

    void delete(T entity);

}
