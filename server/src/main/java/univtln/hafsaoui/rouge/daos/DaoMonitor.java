package univtln.hafsaoui.rouge.daos;

import jakarta.persistence.*;

public interface DaoMonitor {
    org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DaoMonitor.class);

    @PrePersist
    public default void logPrePersist() {
        log.info("[DAO] About to persist: %s " , this);
    }

    @PostPersist
    public default void logPostPersist() {
        log.info("[DAO] Persisted: %s " , this);
    }

    @PreUpdate
    public default void logPPreUpdate() {
        log.info("[DAO] About to update: %s " , this);
    }

    @PostUpdate
    public default void logPostUpdate() {
        log.info("[DAO] Removed: %s " , this);
    }

    @PreRemove
    public default void logPPreRemove() {
        log.info("[DAO] About to remove: %s " , this);
    }

    @PostRemove
    public default void logPostRemove() {
        log.info("[DAO] Removed: %s " , this);
    }

    @PostLoad
    public default void logPostLoad() {
        log.info("[DAO] Loaded: %s " , this);
    }

}
