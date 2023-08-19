package com.example.xyz.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.util.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table("notification")
public class Notification implements Persistable<String> {
    @Id
    private String id;

    @Column("subject")
    private String subject;

    @Column("text")
    private String text;

    @Column("recipient_uid")
    private String recipientUid;

    @Transient
    private Recipient recipient;

    @Override
    public boolean isNew() {
        return !StringUtils.hasText(id);
    }
}
