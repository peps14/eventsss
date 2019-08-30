package io.github.jhipster.application.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link io.github.jhipster.application.domain.LoginEvent} entity. This class is used
 * in {@link io.github.jhipster.application.web.rest.LoginEventResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /login-events?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LoginEventCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter username;

    private StringFilter application;

    private InstantFilter loginDate;

    private StringFilter ipAddress;

    private LongFilter delegationId;

    private StringFilter delegator;

    public LoginEventCriteria(){
    }

    public LoginEventCriteria(LoginEventCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.username = other.username == null ? null : other.username.copy();
        this.application = other.application == null ? null : other.application.copy();
        this.loginDate = other.loginDate == null ? null : other.loginDate.copy();
        this.ipAddress = other.ipAddress == null ? null : other.ipAddress.copy();
        this.delegationId = other.delegationId == null ? null : other.delegationId.copy();
        this.delegator = other.delegator == null ? null : other.delegator.copy();
    }

    @Override
    public LoginEventCriteria copy() {
        return new LoginEventCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getUsername() {
        return username;
    }

    public void setUsername(StringFilter username) {
        this.username = username;
    }

    public StringFilter getApplication() {
        return application;
    }

    public void setApplication(StringFilter application) {
        this.application = application;
    }

    public InstantFilter getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(InstantFilter loginDate) {
        this.loginDate = loginDate;
    }

    public StringFilter getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(StringFilter ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LongFilter getDelegationId() {
        return delegationId;
    }

    public void setDelegationId(LongFilter delegationId) {
        this.delegationId = delegationId;
    }

    public StringFilter getDelegator() {
        return delegator;
    }

    public void setDelegator(StringFilter delegator) {
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
        final LoginEventCriteria that = (LoginEventCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(username, that.username) &&
            Objects.equals(application, that.application) &&
            Objects.equals(loginDate, that.loginDate) &&
            Objects.equals(ipAddress, that.ipAddress) &&
            Objects.equals(delegationId, that.delegationId) &&
            Objects.equals(delegator, that.delegator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        username,
        application,
        loginDate,
        ipAddress,
        delegationId,
        delegator
        );
    }

    @Override
    public String toString() {
        return "LoginEventCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (username != null ? "username=" + username + ", " : "") +
                (application != null ? "application=" + application + ", " : "") +
                (loginDate != null ? "loginDate=" + loginDate + ", " : "") +
                (ipAddress != null ? "ipAddress=" + ipAddress + ", " : "") +
                (delegationId != null ? "delegationId=" + delegationId + ", " : "") +
                (delegator != null ? "delegator=" + delegator + ", " : "") +
            "}";
    }

}
