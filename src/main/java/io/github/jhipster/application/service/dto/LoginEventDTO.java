package io.github.jhipster.application.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link io.github.jhipster.application.domain.LoginEvent} entity.
 */
public class LoginEventDTO implements Serializable {

    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String application;

    @NotNull
    private Instant loginDate;

    @NotNull
    private String ipAddress;

    private Long delegationId;

    private String delegator;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public Instant getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Instant loginDate) {
        this.loginDate = loginDate;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Long getDelegationId() {
        return delegationId;
    }

    public void setDelegationId(Long delegationId) {
        this.delegationId = delegationId;
    }

    public String getDelegator() {
        return delegator;
    }

    public void setDelegator(String delegator) {
        this.delegator = delegator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LoginEventDTO loginEventDTO = (LoginEventDTO) o;
        if (loginEventDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), loginEventDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LoginEventDTO{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", application='" + getApplication() + "'" +
            ", loginDate='" + getLoginDate() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            ", delegationId=" + getDelegationId() +
            ", delegator='" + getDelegator() + "'" +
            "}";
    }
}
