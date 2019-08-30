package io.github.jhipster.application.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A LoginEvent.
 */
@Entity
@Table(name = "login_event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LoginEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull
    @Column(name = "application", nullable = false)
    private String application;

    @NotNull
    @Column(name = "login_date", nullable = false)
    private Instant loginDate;

    @NotNull
    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Column(name = "delegation_id")
    private Long delegationId;

    @Column(name = "delegator")
    private String delegator;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public LoginEvent username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApplication() {
        return application;
    }

    public LoginEvent application(String application) {
        this.application = application;
        return this;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public Instant getLoginDate() {
        return loginDate;
    }

    public LoginEvent loginDate(Instant loginDate) {
        this.loginDate = loginDate;
        return this;
    }

    public void setLoginDate(Instant loginDate) {
        this.loginDate = loginDate;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public LoginEvent ipAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Long getDelegationId() {
        return delegationId;
    }

    public LoginEvent delegationId(Long delegationId) {
        this.delegationId = delegationId;
        return this;
    }

    public void setDelegationId(Long delegationId) {
        this.delegationId = delegationId;
    }

    public String getDelegator() {
        return delegator;
    }

    public LoginEvent delegator(String delegator) {
        this.delegator = delegator;
        return this;
    }

    public void setDelegator(String delegator) {
        this.delegator = delegator;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoginEvent)) {
            return false;
        }
        return id != null && id.equals(((LoginEvent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LoginEvent{" +
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
