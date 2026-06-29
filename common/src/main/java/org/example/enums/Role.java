package org.example.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Role {
    ROLE_ADMIN(Set.of(
            Permission.CREATE_EMPLOYEE, Permission.UPDATE_EMPLOYEE, Permission.VIEW_EMPLOYEE,
            Permission.DELETE_EMPLOYEE, Permission.RUN_PAYROLL, Permission.VIEW_PAYROLL,
            Permission.APPLY_LEAVE, Permission.APPROVE_LEAVE, Permission.VIEW_ANALYTIC
    )),
    ROLE_HR(Set.of(
            Permission.CREATE_EMPLOYEE, Permission.UPDATE_EMPLOYEE, Permission.VIEW_EMPLOYEE,
            Permission.RUN_PAYROLL, Permission.VIEW_PAYROLL, Permission.APPROVE_LEAVE
    )),
    ROLE_MANAGER(Set.of(
            Permission.VIEW_EMPLOYEE, Permission.APPROVE_LEAVE, Permission.VIEW_ANALYTIC
    )),
    ROLE_EMPLOYEE(Set.of(
            Permission.VIEW_EMPLOYEE, Permission.VIEW_PAYROLL, Permission.APPLY_LEAVE
    ));

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority(this.name()));
        return authorities;
    }
}
