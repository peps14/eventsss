package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.LoginEventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LoginEvent} and its DTO {@link LoginEventDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LoginEventMapper extends EntityMapper<LoginEventDTO, LoginEvent> {



    default LoginEvent fromId(Long id) {
        if (id == null) {
            return null;
        }
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setId(id);
        return loginEvent;
    }
}
