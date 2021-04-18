package com.logging.log4j.appeners;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.ThreadContext.ContextStack;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.db.jpa.AbstractLogEventWrapperEntity;
import org.apache.logging.log4j.core.impl.ThrowableProxy;
import org.apache.logging.log4j.core.time.Instant;
import org.apache.logging.log4j.message.Message;

/**
 * Extend org.apache.logging.log4j.core.appender.db.jpa.AbstractLogEventWrapperEntity
 * if you want to significantly customize the mappings, otherwise extend
 * org.apache.logging.log4j.core.appender.db.jpa.BasicLogEventEntity to use
 * the default mappings.
 */
@Entity
@Table(name="application_log", schema="dbo")
public class JpaLogEntity2 extends AbstractLogEventWrapperEntity {
    private static final long serialVersionUID = 1L;
    private long id = 0L;

    public JpaLogEntity2() {
        super();
    }
    public JpaLogEntity2(LogEvent wrappedEvent) {
        super(wrappedEvent);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "logEventId")
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public Map<String, String> getContextMap() {
        return null;
    }

    @Override
    public ContextStack getContextStack() {
        return null;
    }

    @Override
    public String getLoggerFqcn() {
        return null;
    }

    @Override
    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    public Level getLevel() {
        return this.getWrappedEvent().getLevel();
    }

    @Override
    @Column(name = "logger")
    public String getLoggerName() {
        return this.getWrappedEvent().getLoggerName();
    }

    @Override
    public Marker getMarker() {
        return null;
    }

    @Override
    @Column(name = "message")
    // @Convert(converter = MyMessageConverter.class)
    public Message getMessage() {
        return this.getWrappedEvent().getMessage();
    }

    @Override
    public long getTimeMillis() {
        return 0;
    }

    @Override
    public Instant getInstant() {
        return null;
    }

    @Override
    public StackTraceElement getSource() {
        return null;
    }

    @Override
    public String getThreadName() {
        return null;
    }

    @Override
    public long getThreadId() {
        return 0;
    }

    @Override
    public int getThreadPriority() {
        return 0;
    }

    @Override
    public Throwable getThrown() {
        return null;
    }

    @Override
    public ThrowableProxy getThrownProxy() {
        return null;
    }

    @Override
    public long getNanoTime() {
        return 0;
    }
}
