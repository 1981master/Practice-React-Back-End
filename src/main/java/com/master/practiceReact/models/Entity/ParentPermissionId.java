package com.master.practiceReact.models.Entity;

import java.io.Serializable;
import java.util.Objects;

public class ParentPermissionId implements Serializable {

    private Long parentId;
    private Long permissionId;

    public ParentPermissionId() {}

    public ParentPermissionId(Long parentId, Long permissionId) {
        this.parentId = parentId;
        this.permissionId = permissionId;
    }

    // hashCode and equals are mandatory for composite key
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParentPermissionId)) return false;
        ParentPermissionId that = (ParentPermissionId) o;
        return Objects.equals(parentId, that.parentId) &&
                Objects.equals(permissionId, that.permissionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parentId, permissionId);
    }
}
