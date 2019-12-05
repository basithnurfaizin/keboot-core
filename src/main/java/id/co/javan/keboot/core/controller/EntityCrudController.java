package id.co.javan.keboot.core.controller;

import id.co.javan.keboot.core.exception.ErrorCode;
import id.co.javan.keboot.core.exception.ValidationException;
import id.co.javan.keboot.core.form.EntityCrudForm;
import id.co.javan.keboot.core.form.ValidationErrorFormatter;
import id.co.javan.keboot.core.service.EntityCrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class EntityCrudController<T, ID, FORM extends EntityCrudForm<T>> {
    protected EntityCrudService<T, ID, FORM> entityCrudService;

    public Page<T> list(int page, int size, Object... keyword) {
        Pageable pageable = PageRequest.of(page, size);
        return entityCrudService.search(pageable, keyword);
    }

    public T create(HttpServletRequest request, FORM form, BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(ErrorCode.ERROR_VALIDATION.name(), ValidationErrorFormatter.errorsAsMap(bindingResult.getFieldErrors()));
        }

        return entityCrudService.create(form);
    }

    public T view(ID id) {
        return entityCrudService.get(id);
    }

    public T update(ID id, HttpServletRequest request, FORM form, BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(ErrorCode.ERROR_VALIDATION.name(), ValidationErrorFormatter.errorsAsMap(bindingResult.getFieldErrors()));
        }

        return entityCrudService.update(id, form);
    }

    public ResponseEntity<Map> delete(ID id) {
        entityCrudService.delete(id);
        Map response = new HashMap<>();
        response.put("status", "Berhasil dihapus");
        response.put("id", id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
